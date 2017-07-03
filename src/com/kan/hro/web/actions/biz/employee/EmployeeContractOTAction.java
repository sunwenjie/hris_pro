/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractOTAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-8-23 上午11:01:46  
*   
*/
public class EmployeeContractOTAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_OT";

   /**
    * List employeeContractOT
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         // 获得Action Form
         final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;

         // 添加自定义搜索内容######
         // employeeContractOTVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );

         // 如果子SubAction不为空
         if ( employeeContractOTVO.getSubAction() != null && !employeeContractOTVO.getSubAction().trim().equals( "" ) )
         {
            // 如果子SubAction是删除列表操作
            if ( employeeContractOTVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( employeeContractOTVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeContractOTVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractOTService.getEmployeeContractOTVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listEmployeeContractOT" );
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
      final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;
      employeeContractOTVO.setSubAction( CREATE_OBJECT );
      employeeContractOTVO.setContractId( contractId );
      employeeContractOTVO.setBase( "0.00" );
      employeeContractOTVO.setDiscount( "100.00" );
      employeeContractOTVO.setMultiple( "1" );
      employeeContractOTVO.setResultCap( "0.00" );
      employeeContractOTVO.setResultFloor( "0.00" );
      employeeContractOTVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractOTVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractOTVO.setStatus( "1" );
      employeeContractOTVO.setDescription( "" );

      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractOT" );
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
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractOTVO ) form ).getEmployeeOTId();
         }

         // 获得EmployeeContractOTVO
         final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( id );
         employeeContractOTVO.reset( null, request );
         employeeContractOTVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractOTVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 设置Attribute
         request.setAttribute( "employeeContractOTForm", employeeContractOTVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractOT" );
   }

   /**
    * Add Employee Contract OT
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
            final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

            // 获得ActionForm
            final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;
            employeeContractOTVO.setCreateBy( getUserId( request, response ) );
            employeeContractOTVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractOTVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );

            // 新建对象
            employeeContractOTService.insertEmployeeContractOT( employeeContractOTVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractOTVO, Operate.ADD, employeeContractOTVO.getEmployeeOTId(), null );
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractOTVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract OT
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
            final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

            // 获得当前主键
            final String employeeOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得EmployeeContractOTVO
            final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( employeeOTId );
            employeeContractOTVO.update( ( EmployeeContractOTVO ) form );
            employeeContractOTVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractOTVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );

            // 修改对象
            employeeContractOTService.updateEmployeeContractOT( employeeContractOTVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractOTVO, Operate.MODIFY, employeeContractOTVO.getEmployeeOTId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract OT list
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
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

         // 获得Action Form
         final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;

         // 存在选中的ID
         if ( employeeContractOTVO.getSelectedIds() != null && !employeeContractOTVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractOTVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractOTVO.setEmployeeOTId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractOTVO.setModifyBy( getUserId( request, response ) );
               employeeContractOTService.deleteEmployeeContractOT( employeeContractOTVO );
            }

            insertlog( request, employeeContractOTVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractOTVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         employeeContractOTVO.setSelectedIds( "" );
         employeeContractOTVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract OT by Ajax
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
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

         // 获得当前主键
         String employeeOTId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeOTId" ) );

         // 初始化EmployeeContractOTVO
         final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( employeeOTId );
         employeeContractOTVO.setEmployeeOTId( employeeOTId );
         employeeContractOTVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeContractOTService.deleteEmployeeContractOT( employeeContractOTVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractOTVO, Operate.DELETE, employeeOTId, null );
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

}
