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
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLogService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**
 * @author Kevin Jin
 */

public class EmployeeLogAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_LOG";

   /**
    * List employeeLog
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
         final String page = getPage( request );
         // 初始化Service接口
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // 获得Action Form
         final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;

         dealSubAction( employeeLogVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeLogVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeLogService.getEmployeeLogVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeLog", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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

      final String employeeId = KANUtil.decodeString( request.getParameter( "employeeId" ) );
      final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;
      employeeLogVO.setEmployeeId( employeeId );

      // 加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeLogVO.setEmployeeName( employeeVO.getName() );

      // 设置默认工作状态为离职
      employeeLogVO.setStatus( "1" );

      // 设置Sub Action
      employeeLogVO.setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeLog" );
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
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // 获得当前主键
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeLogVO ) form ).getEmployeeLogId();
         }

         // 获得主键对应对象
         final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( id );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeLogVO.getEmployeeId() );
         employeeLogVO.setEmployeeName( employeeVO.getName() );

         // 刷新对象，初始化对象列表及国际化
         employeeLogVO.reset( null, request );

         employeeLogVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeLogForm", employeeLogVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeLog" );
   }

   /**
    * Add employeeLog
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
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            // 获得ActionForm
            final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;

            employeeLogVO.setAccountId( getAccountId( request, response ) );
            employeeLogVO.setCreateBy( getUserId( request, response ) );
            employeeLogVO.setModifyBy( getUserId( request, response ) );
            employeeLogVO.setType( "1" );
            employeeLogVO.setStatus( "1" );

            // 新建对象
            employeeLogService.insertEmployeeLog( employeeLogVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeLog
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
            // 初始化Service接口
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            // 获得当前主键
            String employeeLogId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( employeeLogId );
            // 获取登录用户
            employeeLogVO.update( ( EmployeeLogVO ) form );
            employeeLogVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            employeeLogService.updateEmployeeLog( employeeLogVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         ( ( EmployeeLogVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeLog
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
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         final EmployeeLogVO employeeLogVO = new EmployeeLogVO();
         // 获得当前主键
         String employeeLogId = request.getParameter( "employeeLogId" );

         // 删除主键对应对象
         employeeLogVO.setEmployeeLogId( KANUtil.decodeStringFromAjax( employeeLogId ) );
         employeeLogVO.setModifyBy( getUserId( request, response ) );
         employeeLogService.deleteEmployeeLog( employeeLogVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeLog list
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
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // 获得Action Form
         EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;
         // 存在选中的ID
         if ( employeeLogVO.getSelectedIds() != null && !employeeLogVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeLogVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeLogVO.setEmployeeLogId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeLogVO.setModifyBy( getUserId( request, response ) );
               employeeLogService.deleteEmployeeLog( employeeLogVO );
            }
         }
         // 清除Selected IDs和子Action
         employeeLogVO.setSelectedIds( "" );
         employeeLogVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Log by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Siuvan at 2014-11-20
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );

         // 获得当前主键
         final String employeeLogId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLogId" ) );

         // 初始化EmployeeLogVO
         final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( employeeLogId );
         employeeLogVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeLogService.deleteEmployeeLog( employeeLogVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
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
