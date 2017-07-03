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
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractCBAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-10-10 上午10:01:14  
* 修改人：Jixiang  
* 修改时间：2013-10-10 上午10:01:14  
* 修改备注：  
* @version   
*   
*/
public class EmployeeContractCBAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CB";

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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // 获得Action Form
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;

         // 设置“导出”权限
         request.setAttribute( "isExportExcel", "1" );

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractCBVO );
         setDataAuth( request, response, employeeContractCBVO );

         employeeContractCBVO.setOrderId( KANUtil.filterEmpty( employeeContractCBVO.getOrderId(), "0" ) );

         // 调用删除方法
         if ( employeeContractCBVO.getSubAction() != null && employeeContractCBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax提交的搜索内容需要解码。
         decodedObject( employeeContractCBVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder employeeContractCBHolder = new PagedListHolder();
         // 传入当前页
         employeeContractCBHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( employeeContractCBVO.getSortColumn() == null || employeeContractCBVO.getSortColumn().isEmpty() )
         {
            employeeContractCBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractCBVO.setSortOrder( "desc" );
         }

         // 如果In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractCBVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前值对象
         employeeContractCBHolder.setObject( employeeContractCBVO );
         // 设置页面记录条数
         employeeContractCBHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractCBService.getFullEmployeeContractCBVOsByCondition( employeeContractCBHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeContractCBHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "employeeContractCBHolder", employeeContractCBHolder );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractCBHolder" );
               request.setAttribute( "fileName", "商保花名册" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            else
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listEmployeeContractCBTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      return mapping.findForward( "listEmployeeContractCB" );
   }

   /**
    * to_objectNew
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
      EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
      employeeContractCBVO.setContractId( contractId );
      employeeContractCBVO.setSubAction( CREATE_OBJECT );
      employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractCBVO.setEndDate( "" );
      employeeContractCBVO.setFreeShortOfMonth( "2" );
      employeeContractCBVO.setChargeFullMonth( "2" );
      employeeContractCBVO.setStatus( "0" );
      employeeContractCBVO.setDescription( "" );

      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractCB" );
   }

   /**  
    * Get Months Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward get_object_json_manage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // 获得ContractId
         final String contractId = request.getParameter( "contractId" );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.reset( null, request );

         // 初始化EmployeeContractCBVO
         EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
         employeeContractCBVO.setContractId( contractId );
         employeeContractCBVO.setSubAction( CREATE_OBJECT );
         employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
         employeeContractCBVO.setFreeShortOfMonth( "2" );
         employeeContractCBVO.setChargeFullMonth( "2" );

         // 获得商保方案solutionId
         final String solutionId = request.getParameter( "solutionId" );

         // 如果商保方案ID不为空
         if ( KANUtil.filterEmpty( solutionId, "0" ) != null )
         {
            // 设置SolutionId
            employeeContractCBVO.setSolutionId( solutionId );
            // 获得协议ID ContractId对应的商保方案集合
            final List< Object > clientOrderCBVOObjects = clientOrderCBService.getClientOrderCBVOsByEmployeeContractId( contractId );
            for ( Object clientOrderCBVOObject : clientOrderCBVOObjects )
            {
               ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) clientOrderCBVOObject;

               if ( solutionId.equals( clientOrderCBVO.getCbSolutionId() ) )
               {
                  // 如果订单有设置
                  if ( KANUtil.filterEmpty( clientOrderCBVO.getFreeShortOfMonth(), "0" ) != null )
                  {
                     employeeContractCBVO.setFreeShortOfMonth( clientOrderCBVO.getFreeShortOfMonth() );
                  }
                  else
                  {
                     // 获得商保方案DTO
                     final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutionDTOByHeaderId( solutionId );
                     final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO();

                     // 如果常量有设置
                     if ( KANUtil.filterEmpty( commercialBenefitSolutionHeaderVO.getFreeShortOfMonth() ) != null )
                     {
                        employeeContractCBVO.setFreeShortOfMonth( commercialBenefitSolutionHeaderVO.getFreeShortOfMonth() );
                     }
                     else
                     {
                        employeeContractCBVO.setFreeShortOfMonth( "2" );
                     }

                  }

                  // 如果订单有设置
                  if ( KANUtil.filterEmpty( clientOrderCBVO.getChargeFullMonth(), "0" ) != null )
                  {
                     employeeContractCBVO.setChargeFullMonth( clientOrderCBVO.getChargeFullMonth() );
                  }
                  else
                  {
                     // 获得商保方案DTO
                     final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutionDTOByHeaderId( solutionId );
                     final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO();

                     // 如果常量有设置
                     if ( KANUtil.filterEmpty( commercialBenefitSolutionHeaderVO.getChargeFullMonth() ) != null )
                     {
                        employeeContractCBVO.setChargeFullMonth( commercialBenefitSolutionHeaderVO.getChargeFullMonth() );
                     }
                     else
                     {
                        employeeContractCBVO.setChargeFullMonth( "2" );
                     }

                  }
                  break;
               }

            }
         }

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();
         employeeContractCBVO.reset( mapping, request );
         jsonObject = JSONObject.fromObject( employeeContractCBVO );

         // 储存Token
         request.setAttribute( "token", CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) );

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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // 获得主键employeeCBId
         final String employeeCBId = request.getParameter( "employeeCBId" );
         // 获得主键对应VO
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getFullEmployeeContractCBVOByEmployeeCBId( employeeCBId );

         if ( employeeContractCBVO != null )
         {
            employeeContractCBVO.setSubAction( VIEW_OBJECT );
         }

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractCBVO != null )
         {
            employeeContractCBVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractCBVO );
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
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeContractCBVO ) form ).getEmployeeCBId();
         }

         // 获得主键对应对象
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( id );
         employeeContractCBVO.reset( null, request );
         employeeContractCBVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );
         employeeContractVO.reset( null, request );

         request.setAttribute( "employeeContractCBForm", employeeContractCBVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractCB" );
   }

   /**
    * Add employeeContractCB
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // 获得ActionForm
            final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;

            // 获取EmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );
            if ( employeeContractVO != null )
            {
               employeeContractCBVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
               employeeContractCBVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
            }

            employeeContractCBVO.setAccountId( getAccountId( request, response ) );
            employeeContractCBVO.setCorpId( getCorpId( request, response ) );
            employeeContractCBVO.setCreateBy( getUserId( request, response ) );
            employeeContractCBVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeContractCBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 新建对象
            if ( employeeContractCBService.insertEmployeeContractCB( employeeContractCBVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, employeeContractCBVO, Operate.ADD, employeeContractCBVO.getEmployeeCBId(), null );
            }
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractCBVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // 清空Form条件
         ( ( EmployeeContractCBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeContractCB
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 初始化Service接口
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
            // 获得当前主键
            String employeeContractCBId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得EmployeeContractCBVO对象
            final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeContractCBId );
            employeeContractCBVO.reset( mapping, request );
            employeeContractCBVO.update( ( EmployeeContractCBVO ) form );
            employeeContractCBVO.setModifyDate( new Date() );
            employeeContractCBVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeContractCBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 如果是商保方案提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( employeeContractCBService.submitEmployeeContractCB( employeeContractCBVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
               }
            }
            else
            {
               employeeContractCBService.updateEmployeeContractCB( employeeContractCBVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
            }

         }

         // 清空Form条件
         ( ( EmployeeContractCBVO ) form ).reset();

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
    *	模态框单条修改
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
         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // 获得ActionForm
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );

         // 模态框修改商保方案
         final String actFlag = employeeContractCBService.modifyEmployeeContractCBVO( employeeContractCBVO );

         // 如果是新增记录
         if ( actFlag.equals( "addObject" ) )
         {
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.ADD, employeeContractCBVO.getEmployeeCBId(), null );
         }
         else if ( actFlag.equals( "updateObject" ) )
         {
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
         }
         else if ( actFlag.equals( "submitObject" ) )
         {
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
         }

         // 清空form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return list_object( mapping, form, request, response );
   }

   /**  
    * Modify Objects Popup
    *	模态框多条数据修改
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_objects_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );
         // 修改
         employeeContractCBService.modifyEmployeeContractCBVOs( employeeContractCBVO );
         // 返回编辑成功标记
         success( request, null, "操作成功", MESSAGE_HEADER );
         insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
         // 清空form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete employeeContractCB
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // 获得当前主键
         final String employeeCBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCBId" ) );

         // 删除主键对应对象
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeCBId );
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );
         employeeContractCBVO.setModifyDate( new Date() );

         employeeContractCBService.deleteEmployeeContractCB( employeeContractCBVO );
         insertlog( request, employeeContractCBVO, Operate.DELETE, employeeCBId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeContractCB list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // 获得Action Form
         EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         // 存在选中的ID
         if ( employeeContractCBVO.getSelectedIds() != null && !employeeContractCBVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractCBVO.getSelectedIds().split( "," ) )
            {
               // 删除主键对应对象
               final EmployeeContractCBVO tempEmployeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
               tempEmployeeContractCBVO.setModifyDate( new Date() );
               employeeContractCBService.deleteEmployeeContractCB( tempEmployeeContractCBVO );
            }

            insertlog( request, employeeContractCBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractCBVO.getSelectedIds() ) );
         }
         // 清空form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
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
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // 获得当前主键
         final String employeeCBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCBId" ) );

         // 初始化EmployeeContractSalaryVO
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeCBId );
         employeeContractCBVO.setEmployeeCBId( employeeCBId );
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeContractCBService.deleteEmployeeContractCB( employeeContractCBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractCBVO, Operate.DELETE, employeeCBId, "delete_object_ajax" );
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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // 获得服务协议ID
         String contractId = request.getParameter( "contractId" );
         final String solutionId = request.getParameter( "solutionId" );

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            contractId = KANUtil.decodeStringFromAjax( contractId );
         }

         // 获得ContractId已有社保
         final List< Object > employeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );

         // 添加所有社保
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         // 如果是In House登录
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // 如果是Hr Service登录
         else
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         }

         //添加super的
         //allMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );

         // 获取重复的。
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         EmployeeContractCBVO employeeContractCBVO = null;
         for ( int i = 0; i < employeeContractCBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOs.get( i );
               if ( employeeContractCBVO.getSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {
                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( EmployeeContractCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( allMappingVOs, "solutionId", ( KANUtil.filterEmpty( solutionId, "0" ) != null ) ? solutionId
               : ( KANUtil.filterEmpty( solutionId, "0" ) ) ) );

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

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "序号ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中）" );
      nameZHs.add( ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英）" );
      nameZHs.add( "备注" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "客户ID" );
      }
      nameZHs.add( ( role.equals( "1" ) ? "订单" : "结算规则" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "派送协议" : "劳动合同" ) + "ID" );
      nameZHs.add( "合同状态" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // 导出属性
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );

      nameSyses.add( "employeeCBId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "decodeRemark" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      nameSyses.add( "orderId" );
      nameSyses.add( "contractId" );
      nameSyses.add( "decodeContractStatus" );

      return KANUtil.stringListToArray( nameSyses );
   }
}
