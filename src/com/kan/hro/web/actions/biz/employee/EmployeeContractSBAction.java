/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBDetailService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractSBAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-8-23 上午11:01:46   
*   
*/
public class EmployeeContractSBAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SB";

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
         this.saveToken( request );
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // 获得Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         employeeContractSBVO.setOrderId( KANUtil.filterEmpty( employeeContractSBVO.getOrderId(), "0" ) );

         // 设置“导出”权限
         request.setAttribute( "isExportExcel", "1" );

         // 处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSBVO );
         setDataAuth( request, response, employeeContractSBVO );

         // 调用删除方法
         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax提交的搜索内容需要解码。
         decodedObject( employeeContractSBVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder employeeContractSBHolder = new PagedListHolder();
         // 传入当前页
         employeeContractSBHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( employeeContractSBVO.getSortColumn() == null || employeeContractSBVO.getSortColumn().isEmpty() )
         {
            employeeContractSBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractSBVO.setSortOrder( "desc" );
         }

         // 如果In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSBVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 多选框社保状态处理
         if ( employeeContractSBVO.getStatusArray() != null && employeeContractSBVO.getStatusArray().length > 0 )
         {
            employeeContractSBVO.setStatus( KANUtil.toJasonArray( employeeContractSBVO.getStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }

         // 传入当前值对象
         employeeContractSBHolder.setObject( employeeContractSBVO );
         // 设置页面记录条数
         employeeContractSBHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractSBService.getFullEmployeeContractSBVOsByCondition( employeeContractSBHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeContractSBHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "employeeContractSBHolder", employeeContractSBHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( getAjax( request ) ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractSBHolder" );
               request.setAttribute( "fileName", "社保公积金花名册" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            else
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listEmployeeContractSBTable" );
            }
         }

         // 跳转到列表界面
         return mapping.findForward( "listEmployeeContractSB" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Vendor
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_vendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // 获得Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         employeeContractSBVO.setOrderId( KANUtil.filterEmpty( employeeContractSBVO.getOrderId(), "0" ) );

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSBVO );
         setDataAuth( request, response, employeeContractSBVO );

         // 调用删除方法
         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax提交的搜索内容需要解码。
         decodedObject( employeeContractSBVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder employeeContractSBHolder = new PagedListHolder();
         // 传入当前页
         employeeContractSBHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( employeeContractSBVO.getSortColumn() == null || employeeContractSBVO.getSortColumn().isEmpty() )
         {
            employeeContractSBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractSBVO.setSortOrder( "desc" );
         }

         // 如果In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSBVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前值对象
         employeeContractSBHolder.setObject( employeeContractSBVO );
         // 设置页面记录条数
         employeeContractSBHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractSBService.getVendorEmployeeContractSBVOsByCondition( employeeContractSBHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeContractSBHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "employeeContractSBHolder", employeeContractSBHolder );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listVendorSBTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      return mapping.findForward( "listVendorSB" );
   }

   /**  
    * Get Object Json
    *	ajax获取EmployeeContractSBVO
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 获得主键employeeSBId
         final String employeeSBId = request.getParameter( "employeeSBId" );
         // 获得主键对应VO
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getFullEmployeeContractSBVOByEmployeeSBId( employeeSBId );

         if ( employeeContractSBVO != null )
         {
            employeeContractSBVO.setSubAction( VIEW_OBJECT );
         }

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractSBVO != null )
         {
            employeeContractSBVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractSBVO );
            jsonObject.put( "success", "true" );

            // 储存Token
            request.setAttribute( "token", CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) );
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

      // 获得ContractId
      final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

      // 获得EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // 默认值处理
      final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
      employeeContractSBVO.setSubAction( CREATE_OBJECT );
      employeeContractSBVO.setContractId( contractId );
      employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractSBVO.setEndDate( "" );
      employeeContractSBVO.setNeedSBCard( "2" );
      employeeContractSBVO.setNeedMedicalCard( "2" );
      employeeContractSBVO.setStatus( "0" );
      employeeContractSBVO.setDescription( "" );

      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractSB" );
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSBVO ) form ).getEmployeeSBId();
         }

         // 获得EmployeeContractSBVO
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( id );
         employeeContractSBVO.reset( null, request );
         employeeContractSBVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSBVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 设置Attribute
         request.setAttribute( "employeeContractSBForm", employeeContractSBVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractSB" );
   }

   /**
    * Add Employee Contract SB
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
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

            // 获得ActionForm
            final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
            employeeContractSBVO.setCreateBy( getUserId( request, response ) );
            employeeContractSBVO.setModifyBy( getUserId( request, response ) );

            // 获取EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSBVO.getContractId() );
            if ( employeeContractVO != null )
            {
               employeeContractSBVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
               employeeContractSBVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
            }

            // 保存自定义Column
            employeeContractSBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getVendorId(), "0" ) == null )
            {
               employeeContractSBVO.setVendorServiceId( null );
            }
            // 保存对象
            if ( employeeContractSBService.insertEmployeeContractSB( employeeContractSBVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, employeeContractSBVO, Operate.ADD, employeeContractSBVO.getEmployeeSBId(), null );
            }
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractSBVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // 清空Form条件
         ( ( EmployeeContractSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract SB
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 获得当前主键
            final String employeeSBId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
            employeeContractSBVO.reset( mapping, request );
            employeeContractSBVO.update( ( EmployeeContractSBVO ) form );
            employeeContractSBVO.setModifyDate( new Date() );
            employeeContractSBVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractSBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getVendorId(), "0" ) == null )
            {
               employeeContractSBVO.setVendorServiceId( null );
            }

            // 如果是社保方案提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( employeeContractSBService.submitEmployeeContractSB( employeeContractSBVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
               }
            }
            else
            {
               employeeContractSBService.updateEmployeeContractSB( employeeContractSBVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
            }
         }

         // 清空form
         ( ( EmployeeContractSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Popup
    *	模态框修改
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         //         if ( this.isTokenValid( request, true ) )
         //         {
         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // 获得ActionForm
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );

         // 模态框修改社保方案
         final String actFlag = employeeContractSBService.modifyEmployeeContractSBVO( employeeContractSBVO );

         // 如果是新增记录
         if ( actFlag.equals( "addObject" ) )
         {
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.ADD, employeeContractSBVO.getEmployeeSBId(), null );
         }
         else if ( actFlag.equals( "updateObject" ) )
         {
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
         }
         else if ( actFlag.equals( "submitObject" ) )
         {
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
         }

         // 清空form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSBVO ) form ).setStatus( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      final String pageFlag = ( String ) request.getParameter( "pageFlag" );
      if ( pageFlag != null && pageFlag.equals( "vendor" ) )
      {
         // 跳转到“供应商选择”界面
         return list_object_vendor( mapping, form, request, response );
      }
      else
      {
         // 跳转到“雇员加减保”界面
         return list_object( mapping, form, request, response );
      }
   }

   /**  
    * Modify Objects Popup
    * 模态框多条数据修改
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_objects_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );
         // 修改
         employeeContractSBService.modifyEmployeeContractSBVOs( employeeContractSBVO );
         // 返回编辑成功标记
         success( request, null, "操作成功", MESSAGE_HEADER );
         insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), "modify_objects_popup" );
         // 清空form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract SB list
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 获得Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         // 存在选中的ID
         if ( employeeContractSBVO.getSelectedIds() != null && !employeeContractSBVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractSBVO.getSelectedIds().split( "," ) )
            {
               // 删除主键对应对象
               final EmployeeContractSBVO tempEmployeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
               tempEmployeeContractSBVO.setModifyDate( new Date() );
               employeeContractSBService.deleteEmployeeContractSB( tempEmployeeContractSBVO );
            }

            insertlog( request, employeeContractSBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractSBVO.getSelectedIds() ) );
         }

         // 清空form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract SB Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-22
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 获得当前主键
         final String employeeSBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSBId" ) );

         // 删除主键对应对象
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );
         employeeContractSBVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = employeeContractSBService.deleteEmployeeContractSB( employeeContractSBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractSBVO, Operate.DELETE, employeeSBId, "delete_object_ajax" );
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

   /**  
    *  List Special Info Html
    *  
    *  @param mapping
    *  @param form
    *  @param request
    *  @param response
    *  @return
    *  @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-19
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取是否要tab
         final String noTab = request.getParameter( "noTab" );

         // 获得当前主键
         final String employeeSBId = request.getParameter( "employeeSBId" );

         // 获得社保方案
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // 初始化KANAccountConstants
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

         // Used for return
         final List< EmployeeContractSBDetailVO > tempEmployeeContractSBDetailVOs = new ArrayList< EmployeeContractSBDetailVO >();

         // 从缓存获取社保方案DTO
         final SocialBenefitSolutionDTO socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByHeaderId( sbSolutionId );

         if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() != null
               && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() > 0 )
         {
            // 初始化Service接口
            final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );
            final List< Object > employeeContractSBDetailVOs = employeeContractSBDetailService.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );

            // 循环方案明细
            for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
            {
               // 社保方案ItemVO
               final ItemVO itemVO = accountConstants.getItemVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

               final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
               employeeContractSBDetailVO.setItemId( itemVO.getItemId() );
               employeeContractSBDetailVO.setItemNo( itemVO.getItemNo() );
               employeeContractSBDetailVO.setNameZH( itemVO.getNameZH() );
               employeeContractSBDetailVO.setNameEN( itemVO.getNameZH() );
               employeeContractSBDetailVO.setSolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );
               employeeContractSBDetailVO.setCompanyCap( socialBenefitSolutionDetailVO.getCompanyCap() );
               employeeContractSBDetailVO.setCompanyFloor( socialBenefitSolutionDetailVO.getCompanyFloor() );
               employeeContractSBDetailVO.setPersonalCap( socialBenefitSolutionDetailVO.getPersonalCap() );
               employeeContractSBDetailVO.setPersonalFloor( socialBenefitSolutionDetailVO.getPersonalFloor() );
               employeeContractSBDetailVO.setCompanyPercent( socialBenefitSolutionDetailVO.getCompanyPercent() );
               employeeContractSBDetailVO.setPersonalPercent( socialBenefitSolutionDetailVO.getPersonalPercent() );
               employeeContractSBDetailVO.setCompanyFixAmount( socialBenefitSolutionDetailVO.getCompanyFixAmount() );
               employeeContractSBDetailVO.setPersonalFixAmount( socialBenefitSolutionDetailVO.getPersonalFixAmount() );
               employeeContractSBDetailVO.setStartDateLimit( socialBenefitSolutionDetailVO.getStartDateLimit() );
               employeeContractSBDetailVO.setEndDateLimit( socialBenefitSolutionDetailVO.getEndDateLimit() );

               // 已经存在商保明细
               if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
               {
                  for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
                  {
                     if ( socialBenefitSolutionDetailVO.getDetailId().equals( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getSolutionDetailId() ) )
                     {
                        // 重新设置公司和个人基数
                        employeeContractSBDetailVO.setBaseCompany( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getBaseCompany() );
                        employeeContractSBDetailVO.setBasePersonal( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getBasePersonal() );
                     }
                  }
               }
               else
               {
                  // 初始化EmployeeContractSalaryService接口
                  final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

                  String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

                  final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );

                  if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
                  {
                     // 遍历同一服务协议下的工资方案
                     for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
                     {
                        final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;

                        // 如果是基本工资（月薪），初始化公司和个人社保基数
                        if ( employeeContractSalaryVO.getItemId() != null && employeeContractSalaryVO.getItemId().equals( "1" )
                              && KANUtil.filterEmpty( employeeContractSalaryVO.getBase() ) != null )
                        {
                           double baseCompany = Double.valueOf( employeeContractSalaryVO.getBase() );
                           double basePersonal = Double.valueOf( employeeContractSalaryVO.getBase() );

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getCompanyCap() ) != null
                                 && baseCompany > Double.valueOf( employeeContractSBDetailVO.getCompanyCap() ) )
                           {
                              baseCompany = Double.valueOf( employeeContractSBDetailVO.getCompanyCap() );
                           }

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getCompanyFloor() ) != null
                                 && baseCompany < Double.valueOf( employeeContractSBDetailVO.getCompanyFloor() ) )
                           {
                              baseCompany = Double.valueOf( employeeContractSBDetailVO.getCompanyFloor() );
                           }
                           employeeContractSBDetailVO.setBaseCompany( String.valueOf( baseCompany ) );

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getPersonalCap() ) != null
                                 && basePersonal > Double.valueOf( employeeContractSBDetailVO.getPersonalCap() ) )
                           {
                              basePersonal = Double.valueOf( employeeContractSBDetailVO.getPersonalCap() );
                           }

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getPersonalFloor() ) != null
                                 && basePersonal < Double.valueOf( employeeContractSBDetailVO.getPersonalFloor() ) )
                           {
                              basePersonal = Double.valueOf( employeeContractSBDetailVO.getPersonalFloor() );
                           }

                           employeeContractSBDetailVO.setBasePersonal( String.valueOf( basePersonal ) );
                        }
                     }
                  }

                  // 如果公司和个人社保基数为空则默认设置为最小值
                  if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getBaseCompany() ) == null
                        || KANUtil.filterEmpty( employeeContractSBDetailVO.getBaseCompany() ).equals( "0" ) )
                  {
                     employeeContractSBDetailVO.setBaseCompany( employeeContractSBDetailVO.getCompanyFloor() );
                  }
                  if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getBasePersonal() ) == null
                        || KANUtil.filterEmpty( employeeContractSBDetailVO.getBasePersonal() ).equals( "0" ) )
                  {
                     employeeContractSBDetailVO.setBasePersonal( employeeContractSBDetailVO.getPersonalFloor() );
                  }

               }

               tempEmployeeContractSBDetailVOs.add( employeeContractSBDetailVO );
            }
         }
         // 按照科目ID排序
         for ( int i = 0; i < tempEmployeeContractSBDetailVOs.size(); i++ )
         {
            for ( int j = i + 1; j < tempEmployeeContractSBDetailVOs.size(); j++ )
            {
               if ( Integer.parseInt( tempEmployeeContractSBDetailVOs.get( i ).getItemId() ) > Integer.parseInt( tempEmployeeContractSBDetailVOs.get( j ).getItemId() ) )
               {
                  final EmployeeContractSBDetailVO tempVO = tempEmployeeContractSBDetailVOs.get( i );
                  tempEmployeeContractSBDetailVOs.set( i, tempEmployeeContractSBDetailVOs.get( j ) );
                  tempEmployeeContractSBDetailVOs.set( j, tempVO );
               }
            }
         }

         // Attribute设置
         request.setAttribute( "employeeContractSBDetailVOs", tempEmployeeContractSBDetailVOs );

         request.setAttribute( "countEmployeeContractSBDetailVOs", tempEmployeeContractSBDetailVOs.size() );

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "listEmployeeContractSBDetailTable" );
         }

         return mapping.findForward( "listEmployeeContractSBDetail" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * list_object_options_ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 获得服务协议ID
         final String contractId = request.getParameter( "contractId" );

         // 获得EmployeeContractSBVO列表
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         String employeeSBId = "";
         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            // 遍历
            for ( Object employeeContractSBVOObject : employeeContractSBVOs )
            {
               final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
               employeeContractSBVO.reset( null, request );
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( employeeContractSBVO.getEmployeeSBId() );
               mappingVO.setMappingValue( employeeContractSBVO.getDecodeSbSolutionId() );
               mappingVOs.add( mappingVO );
            }

            // 如果只有一个选项则默认被选中
            if ( employeeContractSBVOs.size() == 1 )
            {
               employeeSBId = ( ( EmployeeContractSBVO ) employeeContractSBVOs.get( 0 ) ).getEmployeeSBId();
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "employeeSBId", employeeSBId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**  
    * List Object Options Manage Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_manage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // 获得服务协议ID
         String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // 添加所有社保
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 根据劳动合同ID好到对应订单定义的社保
         List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByEmployeeContractId( contractId );
         // 获得ContractId已有社保
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         if ( clientOrderSBVOs.size() > 0 )
         {
            boolean isZH = request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" );
            for ( Object o : clientOrderSBVOs )
            {
               ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) o;
               MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( clientOrderSBVO.getSbSolutionId() );
               if ( isZH )
               {
                  mappingVO.setMappingValue( clientOrderSBVO.getSbSolutionNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( clientOrderSBVO.getSbSolutionNameEN() );
               }
               allMappingVOs.add( mappingVO );
            }
         }
         else
         {

            // 如果是In House登录
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
            }
            // 如果是Hr Service登录
            else
            {
               allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
            }

            // 添加super的
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // 获取重复的。
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         EmployeeContractSBVO employeeContractSBVO = null;
         for ( int i = 0; i < employeeContractSBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOs.get( i );
               if ( employeeContractSBVO.getSbSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {

                  // 如果是修改则保留
                  if ( KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ) != null )
                  {
                     if ( KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ).equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
                     {
                        continue;
                     }
                  }

                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( EmployeeContractSBVO ) form ).getEmptyMappingVO() );

         out.println( KANUtil.getOptionHTML( allMappingVOs, "sbSolutionId", KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ) ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public void sbSolution_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化公司是否承担社保
         String personalSBBurden = "2";

         // 获取订单ID
         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         // 获得服务协议ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // 获得社保方案ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // 初始化Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 根据劳动合同ID好到对应订单定义的社保
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByEmployeeContractId( contractId );

         // 如果存在clientOrderSBVOs
         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            for ( Object clientOrderSBVOObject : clientOrderSBVOs )
            {
               final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOObject;
               if ( KANUtil.filterEmpty( clientOrderSBVO.getSbSolutionId() ) != null && KANUtil.filterEmpty( clientOrderSBVO.getSbSolutionId() ).equals( sbSolutionId ) )
               {
                  personalSBBurden = KANUtil.filterEmpty( clientOrderSBVO.getPersonalSBBurden() ) == null ? personalSBBurden
                        : KANUtil.filterEmpty( clientOrderSBVO.getPersonalSBBurden() );
                  break;
               }
            }
         }
         else
         {
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
            if ( clientOrderHeaderVO != null )
            {
               personalSBBurden = KANUtil.filterEmpty( clientOrderHeaderVO.getPersonalSBBurden() ) == null ? personalSBBurden
                     : KANUtil.filterEmpty( clientOrderHeaderVO.getPersonalSBBurden() );
            }
         }

         out.println( personalSBBurden.trim() );
         // Send to client
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "序号ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中）" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英）" );
      nameZHs.add( "社保公积金方案" );
      nameZHs.add( "加保日期" );
      nameZHs.add( "退保日期" );
      nameZHs.add( "社保公积金状态" );
      nameZHs.add( ( role.equals( "1" ) ? "订单" : "结算规则" ) + "名称" );
      nameZHs.add( "合同状态" );
      nameZHs.add( "身份证号码" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "客户ID" );
      }

      return KANUtil.stringListToArray( nameZHs );
   }

   // 导出属性
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );

      nameSyses.add( "employeeSBId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "decodeSbSolutionId" );
      nameSyses.add( "startDate" );
      nameSyses.add( "endDate" );
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "orderDescription" );
      nameSyses.add( "decodeContractStatus" );
      nameSyses.add( "certificateNumber" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      return KANUtil.stringListToArray( nameSyses );
   }
}
