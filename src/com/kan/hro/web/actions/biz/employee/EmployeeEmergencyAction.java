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
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEmergencyService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeEmergencyAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-10-8 下午01:46:52  
* 修改人：Jixiang  
* 修改时间：2013-10-8 下午01:46:52  
* 修改备注：  
* @version   
*   
*/
public class EmployeeEmergencyAction extends BaseAction
{
   public final static String accessAction = "HRO_BIZ_EMPLOYEE_EMERGENCY";

   /**
    * List employeeEmergency
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // 获得Action Form
         final EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;

         dealSubAction( employeeEmergencyVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeEmergencyVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeEmergencyService.getEmployeeEmergencyVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeEmergency", mapping, form, request, response );
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
      EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;
      employeeEmergencyVO.setEmployeeId( employeeId );
      // 设置Sub Action
      employeeEmergencyVO.setSubAction( CREATE_OBJECT );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEmergencyVO.getEmployeeId() );
      employeeEmergencyVO.setEmployeeName( employeeVO.getName() );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeEmergency" );
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || "".equals( id ) )
         {
            id = ( ( EmployeeEmergencyVO ) form ).getEmployeeEmergencyId();
         }
         // 获得主键对应对象
         final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeEmergencyVO.reset( null, request );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEmergencyVO.getEmployeeId() );
         employeeEmergencyVO.setEmployeeName( employeeVO.getName() );

         employeeEmergencyVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeEmergencyForm", employeeEmergencyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeEmergency" );
   }

   /**
    * Add employeeEmergency
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
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
            // 获得ActionForm
            final EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;

            employeeEmergencyVO.setAccountId( getAccountId( request, response ) );
            employeeEmergencyVO.setCreateBy( getUserId( request, response ) );
            employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeEmergencyVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EMERGENCY" ) );

            // 新建对象
            employeeEmergencyService.insertEmployeeEmergency( employeeEmergencyVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeEmergencyVO, Operate.ADD, employeeEmergencyVO.getEmployeeEmergencyId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeEmergency
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
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
            // 获得当前主键
            String employeeEmergencyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyId );

            // 获取登录用户
            employeeEmergencyVO.update( ( EmployeeEmergencyVO ) form );

            employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeEmergencyVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EMERGENCY" ) );

            // 修改对象
            employeeEmergencyService.updateEmployeeEmergency( employeeEmergencyVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeEmergencyVO, Operate.MODIFY, employeeEmergencyVO.getEmployeeEmergencyId(), null );
         }
         ( ( EmployeeEmergencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeEmergency
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         final EmployeeEmergencyVO employeeEmergencyVO = new EmployeeEmergencyVO();
         // 获得当前主键
         String employeeEmergencyId = request.getParameter( "employeeEmergencyId" );

         // 删除主键对应对象
         employeeEmergencyVO.setEmployeeEmergencyId( KANUtil.decodeStringFromAjax( employeeEmergencyId ) );
         employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
         employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );
         insertlog( request, employeeEmergencyVO, Operate.DELETE, employeeEmergencyId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeEmergency list
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // 获得Action Form
         EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;
         // 存在选中的ID
         if ( employeeEmergencyVO.getSelectedIds() != null && !employeeEmergencyVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeEmergencyVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeEmergencyVO.setEmployeeEmergencyId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
               employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );
            }

            insertlog( request, employeeEmergencyVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeEmergencyVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeEmergencyVO.setSelectedIds( "" );
         employeeEmergencyVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Emergency by Ajax
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );

         // 获得当前主键
         final String employeeEmergencyId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeEmergencyId" ) );

         // 初始化EmployeeEmergencyVO
         final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyId );
         employeeEmergencyVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeEmergencyVO, Operate.DELETE, employeeEmergencyId, "delete_object_ajax" );
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
