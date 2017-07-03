/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.employee.EmployeeSkillService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeSkillAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-9-18 下午05:08:49  
* 修改人：Jixiang  
* 修改时间：2013-9-18 下午05:08:49  
* 修改备注：  
* @version   
*   
*/
public class EmployeeSkillAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_SKILL";

   /**
    * List employeeSkill
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // 获得Action Form
         final EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;

         dealSubAction( employeeSkillVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeSkillVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeSkillService.getEmployeeSkillVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeSkill", mapping, form, request, response );
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
      EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;
      employeeSkillVO.setEmployeeId( employeeId );
      // 设置Sub Action
      employeeSkillVO.setSubAction( CREATE_OBJECT );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeSkillVO.setEmployeeName( employeeVO.getName() );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeSkill" );
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // 获得当前主键
         final String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得主键对应对象
         final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeSkillVO.reset( null, request );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeSkillVO.getEmployeeId() );
         employeeSkillVO.setEmployeeName( employeeVO.getName() );

         employeeSkillVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeSkillForm", employeeSkillVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeSkill" );
   }

   /**
    * Add employeeSkill
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
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
            // 获得ActionForm
            final EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;

            employeeSkillVO.setAccountId( getAccountId( request, response ) );
            employeeSkillVO.setCreateBy( getUserId( request, response ) );
            employeeSkillVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeSkillVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_SKILL" ) );

            // 新建对象
            employeeSkillService.insertEmployeeSkill( employeeSkillVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeSkillVO, Operate.ADD, employeeSkillVO.getEmployeeSkillId(), null );
            final String employeeId = request.getParameter( "employeeId" );
            final String id = URLEncoder.encode( Cryptogram.encodeString( employeeId ), "UTF-8" );

            // 跳转到雇员信息修改页面
            response.sendRedirect( request.getContextPath() + "/employeeAction.do?proc=to_objectModify&id=" + id );
         }
         // 清空Form条件
         ( ( EmployeeSkillVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      //return list_object( mapping, form, request, response );
      return null;
   }

   /**
    * Modify employeeSkill
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
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
            // 获得当前主键
            String employeeSkillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( employeeSkillId );

            // 获取登录用户
            employeeSkillVO.update( ( EmployeeSkillVO ) form );

            employeeSkillVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeSkillVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_SKILL" ) );

            // 修改对象
            employeeSkillService.updateEmployeeSkill( employeeSkillVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeSkillVO, Operate.MODIFY, employeeSkillVO.getEmployeeSkillId(), null );
         }
         // 清空Form条件
         ( ( EmployeeSkillVO ) form ).reset();
         final String employeeId = request.getParameter( "employeeId" );
         final String id = URLEncoder.encode( Cryptogram.encodeString( employeeId ), "UTF-8" );
         // 跳转到雇员信息修改页面
         response.sendRedirect( request.getContextPath() + "/employeeAction.do?proc=to_objectModify&id=" + id );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      //return list_object( mapping, form, request, response );
      return null;
   }

   /**
    * Delete employeeSkill
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         final EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
         // 获得当前主键
         String employeeSkillId = request.getParameter( "employeeSkillId" );

         // 删除主键对应对象
         employeeSkillVO.setEmployeeSkillId( KANUtil.decodeStringFromAjax( employeeSkillId ) );
         employeeSkillVO.setModifyBy( getUserId( request, response ) );
         employeeSkillService.deleteEmployeeSkill( employeeSkillVO );
         insertlog( request, employeeSkillVO, Operate.DELETE, employeeSkillId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeSkill list
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // 获得Action Form
         EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;
         // 存在选中的ID
         if ( employeeSkillVO.getSelectedIds() != null && !employeeSkillVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeSkillVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeSkillVO.setEmployeeSkillId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeSkillVO.setModifyBy( getUserId( request, response ) );
               employeeSkillService.deleteEmployeeSkill( employeeSkillVO );
            }

            insertlog( request, employeeSkillVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeSkillVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeSkillVO.setSelectedIds( "" );
         employeeSkillVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Skill by Ajax
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

         // 获得当前主键
         final String employeeSkillId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSkillId" ) );

         // 初始化EmployeeSkillVO
         final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( employeeSkillId );
         employeeSkillVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeSkillService.deleteEmployeeSkill( employeeSkillVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeSkillVO, Operate.DELETE, employeeSkillId, "delete_object_ajax" );
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
