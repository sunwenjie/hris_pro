/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractSalaryAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-8-23 上午11:01:31  
*   
*/
public class EmployeeContractSalaryAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY";
   //工资花名册用accessAction
   public static String accessActionForEmployeeSalary = "HRO_BIZ_EMPLOYEE_SALARY";

   /**
    * List Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 存储Token
         saveToken( request );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // 获得Action Form
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSalaryVO );
         setDataAuth( request, response, employeeContractSalaryVO );

         employeeContractSalaryVO.setOrderId( KANUtil.filterEmpty( employeeContractSalaryVO.getOrderId(), "0" ) );

         // Ajax提交的搜索内容需要解码。
         decodedObject( employeeContractSalaryVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder employeeContractSalaryHolder = new PagedListHolder();
         // 传入当前页
         employeeContractSalaryHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( employeeContractSalaryVO.getSortColumn() == null || employeeContractSalaryVO.getSortColumn().isEmpty() )
         {
            employeeContractSalaryVO.setSortColumn( "a.status,a.contractId,a.modifyDate" );
            employeeContractSalaryVO.setSortOrder( "desc" );
         }

         // 如果In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSalaryVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前值对象
         employeeContractSalaryHolder.setObject( employeeContractSalaryVO );
         // 设置页面记录条数
         employeeContractSalaryHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractSalaryService.getEmployeeContractSalaryVOsByCondition( employeeContractSalaryHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
               : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( employeeContractSalaryHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "employeeContractSalaryHolder", employeeContractSalaryHolder );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractSalaryHolder" );
               request.setAttribute( "fileName", "工资花名册" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listEmployeeContractSalaryTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      return mapping.findForward( "listEmployeeContractSalary" );
   }

   /**  
    * Get Object Json
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 存储Token
         saveToken( request );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 Service
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得主键employeeSalaryId
         final String employeeSalaryId = request.getParameter( "employeeSalaryId" );
         // 获得主键对应VO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractSalaryVO != null )
         {
            employeeContractSalaryVO.reset( mapping, request );
            employeeContractSalaryVO.setSubAction( MODIFY_OBJECT );
            jsonObject = JSONObject.fromObject( employeeContractSalaryVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to front
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**  
    * Get Object Ajax Popup
    * popup获取对象
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );
         EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.reset( null, request );
         // 获得OrderId
         final String orderId = employeeContractVO.getOrderId();
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
         // 获得订单的SalaryType
         final String salaryType = clientOrderHeaderVO.getSalaryType();
         final String divideType = clientOrderHeaderVO.getDivideType();

         // 设置默认值（开始时间、结束时间、计薪方式、折算方式）
         employeeContractSalaryVO.setContractId( contractId );
         employeeContractSalaryVO.setBase( "0.00" );
         employeeContractSalaryVO.setResultCap( "0.00" );
         employeeContractSalaryVO.setResultFloor( "0.00" );
         employeeContractSalaryVO.setCycle( "1" );
         employeeContractSalaryVO.setShowToTS( "2" );
         employeeContractSalaryVO.setStatus( "1" );
         employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
         employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );

         if ( salaryType != null && !"0".equals( salaryType ) )
         {
            employeeContractSalaryVO.setSalaryType( salaryType );
         }
         else
         {
            employeeContractSalaryVO.setSalaryType( "1" );
         }
         if ( divideType != null && !"0".equals( divideType ) )
         {
            employeeContractSalaryVO.setDivideType( divideType );
         }
         else
         {
            employeeContractSalaryVO.setDivideType( "2" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "manageEmployeeContractSalaryPopup" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // 添加页面Token
      this.saveToken( request );

      // 初始化Service
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // 获得ContractId
      String contractId = ( String ) request.getAttribute( "contractId" );
      if ( contractId == null )
      {
         contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );
      }

      // 获得EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // 获得OrderId
      final String orderId = employeeContractVO.getOrderId();
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
      // 获得订单的SalaryType
      final String salaryType = clientOrderHeaderVO.getSalaryType();
      final String divideType = clientOrderHeaderVO.getDivideType();

      // 默认值处理
      final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
      employeeContractSalaryVO.setSubAction( CREATE_OBJECT );
      employeeContractSalaryVO.setContractId( contractId );
      employeeContractSalaryVO.setBase( "0.00" );
      employeeContractSalaryVO.setResultCap( "0.00" );
      employeeContractSalaryVO.setResultFloor( "0.00" );
      employeeContractSalaryVO.setCycle( "1" );
      employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractSalaryVO.setShowToTS( "2" );
      employeeContractSalaryVO.setProbationUsing( "2" );
      employeeContractSalaryVO.setDescription( "" );

      // 如果劳动合同是非新建状态，需要走工作流，状态初始化为停用
      if ( employeeContractVO != null && ( employeeContractVO.getStatus().equals( "1" ) || employeeContractVO.getStatus().equals( "4" ) ) )
      {
         employeeContractSalaryVO.setStatus( "1" );
      }
      else
      {
         employeeContractSalaryVO.setStatus( "2" );
      }

      if ( salaryType != null && !"0".equals( salaryType ) )
      {
         employeeContractSalaryVO.setSalaryType( salaryType );
      }
      else
      {
         employeeContractSalaryVO.setSalaryType( "1" );
      }
      if ( divideType != null && !"0".equals( divideType ) )
      {
         employeeContractSalaryVO.setDivideType( divideType );
      }
      else
      {
         employeeContractSalaryVO.setDivideType( "2" );
      }
      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
      request.setAttribute( "ItemType", 0 );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractSalary" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSalaryVO ) form ).getEmployeeSalaryId();
         }

         // 获得EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( id );
         employeeContractSalaryVO.reset( null, request );
         employeeContractSalaryVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 设置Attribute
         request.setAttribute( "employeeContractSalaryForm", employeeContractSalaryVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );

         ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( employeeContractSalaryVO.getItemId() );
         request.setAttribute( "ItemType", itemVO.getItemType() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractSalary" );
   }

   /**
    * Add Employee Contract Salary
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

            // 获得ActionForm
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
            employeeContractSalaryVO.setCreateBy( getUserId( request, response ) );
            employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
            // 验证日期是否重复
            if ( employeeContractSalaryService.hasConflictContractSalaryInOneItem( employeeContractSalaryVO ) )
            {
               error( request, null, "时间段内已存在该科目的薪酬方案。" );
               request.setAttribute( "contractId", employeeContractSalaryVO.getContractId() );
               return to_objectNew( mapping, form, request, response );
            }
            // 不折算科目
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               employeeContractSalaryVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            // 保存自定义Column
            employeeContractSalaryVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );

            // 新建对象
            employeeContractSalaryService.insertEmployeeContractSalary( employeeContractSalaryVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), null );
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractSalaryVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // 清空Form条件
         ( ( EmployeeContractSalaryVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Add Object Popup
    *	模态框添加薪酬方案_快速操作
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward add_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得ActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // 新建对象
         employeeContractSalaryService.insertEmployeeContractSalaryPopup( employeeContractSalaryVO );
         // 返回添加成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), "add_object_popup" );
         // 清空Form条件
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Salary
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   // Modify by siuvan.xia at 2014-07-02
   /* (non-Javadoc)
    * @see com.kan.base.web.action.BaseAction#modify_object(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // 获得当前主键
            final String employeeSalaryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 获得EmployeeContractSalaryVO
            final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
            employeeContractSalaryVO.update( ( EmployeeContractSalaryVO ) form );
            employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
            //如果非全勤结算规则，没有选择，则默认为结算规则中的
            if ( "0".equals( employeeContractSalaryVO.getDivideType() ) )
            {
               EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );
               if ( employeeContractVO != null && employeeContractVO.getOrderId() != null && !"".equals( employeeContractVO.getOrderId() ) )
               {
                  ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
                  employeeContractSalaryVO.setDivideType( clientOrderHeaderVO.getDivideType() );
               }
            }
            // 保存自定义Column
            employeeContractSalaryVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );
            // 不折算科目
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               employeeContractSalaryVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }

            // 修改对象
            employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );

            // 如果是客户提交
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               employeeContractSalaryVO.reset( null, request );
               // 状态改为启用
               employeeContractSalaryVO.setStatus( "1" );
               employeeContractSalaryVO.setRole( getRole( request, response ) );
               employeeContractSalaryVO.setIp( getIPAddress( request ) );
               employeeContractSalaryService.generateHistoryVOForWorkflow( employeeContractSalaryVO );
               employeeContractSalaryService.submitEmployeeContractSalary( employeeContractSalaryVO );
               // 返回提交成功的标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractSalaryVO, Operate.SUBMIT, employeeContractSalaryVO.getEmployeeSalaryId(), null );
            }
            else
            {
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeContractSalaryVO.getEmployeeSalaryId(), null );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * modify_object_popup
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         //         if ( this.isTokenValid( request, true ) )
         //         {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得当前主键
         final String employeeSalaryId = ( ( EmployeeContractSalaryVO ) form ).getEmployeeSalaryId();

         // 获得EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.update( ( EmployeeContractSalaryVO ) form );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // 修改对象
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // 清空Form条件
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );

         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "modify_object_popup" );
         //         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
      return list_object( mapping, form, request, response );
   }

   /**  
    * Enable Object
    *	修改状态为启用
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward enable_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // 获得当前主键
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );
         // 获得EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setStatus( EmployeeContractSalaryVO.TRUE );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // 清除Selected IDs和子Action
         ( ( EmployeeContractSalaryVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "enable_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到查询界面
      return list_object( mapping, form, request, response );
   }

   /**  
    * Disable Object
    *	修改状态为停用
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward disable_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // 获得当前主键
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );
         // 获得EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setStatus( EmployeeContractSalaryVO.FALSE );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // 清除Selected IDs和子Action
         ( ( EmployeeContractSalaryVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "disable_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到查询界面
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract Salary list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得Action Form
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         // 存在选中的ID
         if ( employeeContractSalaryVO.getSelectedIds() != null && !employeeContractSalaryVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractSalaryVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractSalaryVO.setEmployeeSalaryId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
               employeeContractSalaryService.deleteEmployeeContractSalary( employeeContractSalaryVO );
            }

            insertlog( request, employeeContractSalaryVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractSalaryVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         employeeContractSalaryVO.setSelectedIds( "" );
         employeeContractSalaryVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Salary by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得当前主键
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );

         // 初始化EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeContractSalaryService.deleteEmployeeContractSalary( employeeContractSalaryVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractSalaryVO, Operate.DELETE, employeeSalaryId, "delete_object_ajax" );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward checkHasConflictContractSalaryInOneItem( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {

      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         // 初始化 Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         employeeContractSalaryVO.setEmployeeSalaryId( id );
         if ( employeeContractSalaryService.hasConflictContractSalaryInOneItem( ( EmployeeContractSalaryVO ) form ) )
         {
            out.print( "1" );
         }
         else
         {
            out.print( "0" );
         }
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public void getEmployeeContractSalaryByContractId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String contractId = request.getParameter( "contractId" );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         List< Object > employeeVOList = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );
         for ( Object object : employeeVOList )
         {
            EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) object;
            Map< String, String > mapReturn = new HashMap< String, String >();
            mapReturn.put( "itemId", employeeContractSalaryVO.getItemId() );
            listReturn.add( mapReturn );
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 去审核页面工作流
   public ActionForward list_object_workflow_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得批次主键ID
         String historyId = request.getParameter( "historyId" );

         // 初始化Service接口
         final HistoryService historyService = ( HistoryService ) getService( "historyService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
         if ( tempEmployeeContractSalaryVO != null )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( tempEmployeeContractSalaryVO.getEmployeeSalaryId() );
            // 刷新对象，初始化对象列表及国际化
            employeeContractSalaryVO.reset( null, request );
            employeeContractSalaryVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "employeeContractSalaryForm", employeeContractSalaryVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax调用
      return mapping.findForward( "manageEmployeeContractSalaryWorkflow" );
   }

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );
      nameZHs.add( "序号ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中文）" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英文）" );
      nameZHs.add( "备注" );
      nameZHs.add( "薪酬方案" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "客户ID" );
      }
      nameZHs.add( ( role.equals( "1" ) ? "订单" : "结算规则" ) + "名称" );
      nameZHs.add( ( role.equals( "1" ) ? "协议" : "合同" ) + "开始时间" );
      nameZHs.add( ( role.equals( "1" ) ? "协议" : "合同" ) + "结束时间" );
      nameZHs.add( ( role.equals( "1" ) ? "协议" : "合同" ) + "状态" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // 导出属性
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );
      nameSyses.add( "employeeSalaryId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "remark" );
      nameSyses.add( "decodeItemId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      nameSyses.add( "orderName" );
      nameSyses.add( "contractStartDate" );
      nameSyses.add( "contractEndDate" );
      nameSyses.add( "decodeStatus" );

      return KANUtil.stringListToArray( nameSyses );
   }

   /***
    * 显示添加薪酬方案界面(调薪界面用到) show_addEmployeeSalaryPage_ajax
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public ActionForward show_addEmployeeSalaryPage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         //this.saveToken( request );

         // 获得ContractId
         final String contractId = request.getParameter( "contractId" );
         // 初始化Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取ActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;

         String salaryType = "1";
         String divideType = "2";
         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null )
         {
            employeeContractSalaryVO.setContractId( contractId );
            employeeContractSalaryVO.setEmployeeId( employeeContractVO.getEmployeeId() );
            employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
            employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
            // 获取ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
            if ( clientOrderHeaderVO != null )
            {
               if ( clientOrderHeaderVO.getSalaryType() != null && !"0".equals( clientOrderHeaderVO.getSalaryType() ) )
               {
                  salaryType = clientOrderHeaderVO.getSalaryType();
               }

               if ( clientOrderHeaderVO.getDivideType() != null && !"0".equals( clientOrderHeaderVO.getDivideType() ) )
               {
                  divideType = clientOrderHeaderVO.getDivideType();
               }

               employeeContractSalaryVO.setExcludeDivideItemIds( clientOrderHeaderVO.getExcludeDivideItemIds() );
            }
         }

         employeeContractSalaryVO.setSubAction( CREATE_OBJECT );
         employeeContractSalaryVO.setSalaryType( salaryType );
         employeeContractSalaryVO.setDivideType( divideType );
         employeeContractSalaryVO.setBase( "0.00" );
         employeeContractSalaryVO.setResultCap( "0.00" );
         employeeContractSalaryVO.setResultFloor( "0.00" );
         employeeContractSalaryVO.setCycle( "1" );
         employeeContractSalaryVO.setShowToTS( "2" );
         employeeContractSalaryVO.setProbationUsing( "2" );
         employeeContractSalaryVO.setDescription( "" );
         employeeContractSalaryVO.setStatus( "1" );

         // 跳转到新建界面
         return mapping.findForward( "manageEmployeeContractSalaryForAdjustment" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   /*** 
    * add_object_popup_ajax(调薪用到) 模态框添加薪酬方案_快速操作金额为“0”
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void add_object_popup_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得ActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // 新建对象
         employeeContractSalaryService.insertEmployeeContractSalaryPopup( employeeContractSalaryVO );
         // 返回添加成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), "add_object_popup" );
         // 清空Form条件
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
