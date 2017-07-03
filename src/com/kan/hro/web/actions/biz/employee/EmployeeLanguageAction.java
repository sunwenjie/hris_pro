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
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLanguageService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeLanguageAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-9-18 下午05:08:49  
* 修改人：Jixiang  
* 修改时间：2013-9-18 下午05:08:49  
* 修改备注：  
* @version   
*   
*/
public class EmployeeLanguageAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_LANGUAGE";

   /**
    * List employeeLanguage
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         // 获得Action Form
         final EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;

         dealSubAction( employeeLanguageVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeLanguageVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeLanguageService.getEmployeeLanguageVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeLanguage", mapping, form, request, response );
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
      EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;
      employeeLanguageVO.setEmployeeId( employeeId );
      // 设置Sub Action
      employeeLanguageVO.setSubAction( CREATE_OBJECT );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeLanguageVO.setEmployeeName( employeeVO.getName() );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeLanguage" );
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeLanguageVO ) form ).getEmployeeLanguageId();
         }
         // 获得主键对应对象
         final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeLanguageVO.reset( null, request );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeLanguageVO.getEmployeeId() );
         employeeLanguageVO.setEmployeeName( employeeVO.getName() );

         employeeLanguageVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeLanguageForm", employeeLanguageVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeLanguage" );
   }

   /**
    * Add employeeLanguage
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
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
            // 获得ActionForm
            final EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;

            employeeLanguageVO.setAccountId( getAccountId( request, response ) );
            employeeLanguageVO.setCreateBy( getUserId( request, response ) );
            employeeLanguageVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeLanguageVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_LANGUAGE" ) );

            // 新建对象
            employeeLanguageService.insertEmployeeLanguage( employeeLanguageVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeLanguageVO, Operate.ADD, employeeLanguageVO.getEmployeeLanguageId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeLanguage
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
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
            // 获得当前主键
            String employeeLanguageId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageId );

            // 获取登录用户
            employeeLanguageVO.update( ( EmployeeLanguageVO ) form );

            employeeLanguageVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeLanguageVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_LANGUAGE" ) );

            // 修改对象
            employeeLanguageService.updateEmployeeLanguage( employeeLanguageVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeLanguageVO, Operate.MODIFY, employeeLanguageVO.getEmployeeLanguageId(), null );
         }
         // 清空Form条件
         ( ( EmployeeLanguageVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeLanguage
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         final EmployeeLanguageVO employeeLanguageVO = new EmployeeLanguageVO();
         // 获得当前主键
         String employeeLanguageId = request.getParameter( "employeeLanguageId" );

         // 删除主键对应对象
         employeeLanguageVO.setEmployeeLanguageId( KANUtil.decodeStringFromAjax( employeeLanguageId ) );
         employeeLanguageVO.setModifyBy( getUserId( request, response ) );
         employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );
         insertlog( request, employeeLanguageVO, Operate.DELETE, employeeLanguageId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeLanguage list
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         // 获得Action Form
         EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;
         // 存在选中的ID
         if ( employeeLanguageVO.getSelectedIds() != null && !employeeLanguageVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeLanguageVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeLanguageVO.setEmployeeLanguageId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeLanguageVO.setModifyBy( getUserId( request, response ) );
               employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );
            }

            insertlog( request, employeeLanguageVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeLanguageVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeLanguageVO.setSelectedIds( "" );
         employeeLanguageVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Language by Ajax
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

         // 获得当前主键
         final String employeeLanguageId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLanguageId" ) );

         // 初始化EmployeeLanguageVO
         final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageId );
         employeeLanguageVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeLanguageVO, Operate.DELETE, employeeLanguageId, null );
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
