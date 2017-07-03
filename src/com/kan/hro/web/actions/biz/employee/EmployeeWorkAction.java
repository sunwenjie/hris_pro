/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.employee.EmployeeWorkService;

/**
 * @author Kevin Jin
 */

public class EmployeeWorkAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_WORK";

   /**
    * List employeeWork
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // 获得Action Form
         final EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;

         dealSubAction( employeeWorkVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeWorkVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeWorkService.getEmployeeWorkVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeWork", mapping, form, request, response );
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
      EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;
      employeeWorkVO.setEmployeeId( employeeId );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeWorkVO.setEmployeeName( employeeVO.getName() );

      // 设置默认工作状态为离职
      employeeWorkVO.setStatus( "3" );

      // 设置Sub Action
      employeeWorkVO.setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeWork" );
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // 获得当前主键
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeWorkVO ) form ).getEmployeeWorkId();
         }

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeWorkVO ) form ).getEmployeeWorkId();
         }
         // 获得主键对应对象
         final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( id );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeWorkVO.getEmployeeId() );
         employeeWorkVO.setEmployeeName( employeeVO.getName() );

         // 刷新对象，初始化对象列表及国际化
         employeeWorkVO.reset( null, request );

         employeeWorkVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeWorkForm", employeeWorkVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeWork" );
   }

   /**
    * Add employeeWork
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
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
            // 获得ActionForm
            final EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;

            employeeWorkVO.setAccountId( getAccountId( request, response ) );
            employeeWorkVO.setCreateBy( getUserId( request, response ) );
            employeeWorkVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeWorkVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_WORK" ) );

            // 新建对象
            employeeWorkService.insertEmployeeWork( employeeWorkVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeWorkVO, Operate.ADD, employeeWorkVO.getEmployeeWorkId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeWork
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
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
            // 获得当前主键
            String employeeWorkId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( employeeWorkId );

            // 获取登录用户
            employeeWorkVO.update( ( EmployeeWorkVO ) form );

            employeeWorkVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeWorkVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_WORK" ) );

            // 修改对象
            employeeWorkService.updateEmployeeWork( employeeWorkVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeWorkVO, Operate.MODIFY, employeeWorkVO.getEmployeeWorkId(), null );
         }
         ( ( EmployeeWorkVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeWork
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         final EmployeeWorkVO employeeWorkVO = new EmployeeWorkVO();
         // 获得当前主键
         String employeeWorkId = request.getParameter( "employeeWorkId" );

         // 删除主键对应对象
         employeeWorkVO.setEmployeeWorkId( KANUtil.decodeStringFromAjax( employeeWorkId ) );
         employeeWorkVO.setModifyBy( getUserId( request, response ) );
         employeeWorkService.deleteEmployeeWork( employeeWorkVO );
         insertlog( request, employeeWorkVO, Operate.DELETE, employeeWorkVO.getEmployeeWorkId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeWork list
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // 获得Action Form
         EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;
         // 存在选中的ID
         if ( employeeWorkVO.getSelectedIds() != null && !employeeWorkVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeWorkVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeWorkVO.setEmployeeWorkId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeWorkVO.setModifyBy( getUserId( request, response ) );
               employeeWorkService.deleteEmployeeWork( employeeWorkVO );
            }

            insertlog( request, employeeWorkVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeWorkVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeWorkVO.setSelectedIds( "" );
         employeeWorkVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Work by Ajax
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

         // 获得当前主键
         final String employeeWorkId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeWorkId" ) );

         // 初始化EmployeeWorkVO
         final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( employeeWorkId );
         employeeWorkVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeWorkService.deleteEmployeeWork( employeeWorkVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeWorkVO, Operate.DELETE, employeeWorkId, "delete_object_ajax" );
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

   public ActionForward list_companyName_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得要联想的属性  参数是固定的“q” 且用URL加密过
         final String q = URLDecoder.decode( request.getParameter( "q" ), "UTF-8" );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

         // 初始化 JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeWorkService.getCompanyNameByName( q ) );

         // Send to client
         out.println( array.toString() );
         System.out.println( "response:" + array.toString() );
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

}
