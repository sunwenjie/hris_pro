/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

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
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEducationService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeEducationAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-9-18 下午05:05:52  
* 修改人：Jixiang  
* 修改时间：2013-9-18 下午05:05:52  
* 修改备注：  
* @version   
*   
*/
public class EmployeeEducationAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_EDUCATION";

   /**
    * List employeeEducation
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // 获得Action Form
         final EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;

         dealSubAction( accessAction, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeEducationVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeEducationService.getEmployeeEducationVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeEducation", mapping, form, request, response );
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
      EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;
      employeeEducationVO.setEmployeeId( employeeId );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
      employeeEducationVO.setEmployeeName( employeeVO.getName() );

      // 设置Sub Action
      employeeEducationVO.setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeEducation" );
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // 获得当前主键
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeEducationVO ) form ).getEmployeeEducationId();
         }
         // 获得主键对应对象
         final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeEducationVO.reset( null, request );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
         employeeEducationVO.setEmployeeName( employeeVO.getName() );

         employeeEducationVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeEducationForm", employeeEducationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeEducation" );
   }

   /**
    * Add employeeEducation
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
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获得ActionForm
            final EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;

            employeeEducationVO.setAccountId( getAccountId( request, response ) );
            employeeEducationVO.setCreateBy( getUserId( request, response ) );
            employeeEducationVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeEducationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EDUCATION" ) );

            // 新建对象
            employeeEducationService.insertEmployeeEducation( employeeEducationVO );

            List< Object > listEducation = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeEducationVO.getEmployeeId() );
            if ( listEducation != null && listEducation.size() != 0 )
            {
               EmployeeEducationVO educationVO = ( EmployeeEducationVO ) listEducation.get( 0 );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
               employeeVO.setHighestEducation( educationVO.getEducationId() );
               employeeVO.setGraduationDate( educationVO.getEndDate() );
               employeeService.updateEmployee( employeeVO );
            }
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeEducationVO, Operate.ADD, employeeEducationVO.getEmployeeEducationId(), null );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeEducation
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
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
            // 获得当前主键
            String employeeEducationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( employeeEducationId );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // 获取登录用户
            employeeEducationVO.update( ( EmployeeEducationVO ) form );

            employeeEducationVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeEducationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EDUCATION" ) );

            // 修改对象
            employeeEducationService.updateEmployeeEducation( employeeEducationVO );

            List< Object > listEducation = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeEducationVO.getEmployeeId() );
            if ( listEducation != null && listEducation.size() != 0 )
            {
               EmployeeEducationVO educationVO = ( EmployeeEducationVO ) listEducation.get( 0 );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
               employeeVO.setHighestEducation( educationVO.getEducationId() );
               employeeVO.setGraduationDate( educationVO.getEndDate() );
               employeeService.updateEmployee( employeeVO );
            }

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, employeeEducationVO, Operate.MODIFY, employeeEducationVO.getEmployeeEducationId(), null );
         }
         ( ( EmployeeEducationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeEducation
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         final EmployeeEducationVO employeeEducationVO = new EmployeeEducationVO();
         // 获得当前主键
         String employeeEducationId = request.getParameter( "employeeEducationId" );

         // 删除主键对应对象
         employeeEducationVO.setEmployeeEducationId( KANUtil.decodeStringFromAjax( employeeEducationId ) );
         employeeEducationVO.setModifyBy( getUserId( request, response ) );
         employeeEducationService.deleteEmployeeEducation( employeeEducationVO );
         insertlog( request, employeeEducationVO, Operate.DELETE, employeeEducationId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeEducation list
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // 获得Action Form
         EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;
         // 存在选中的ID
         if ( employeeEducationVO.getSelectedIds() != null && !employeeEducationVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeEducationVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeEducationVO.setEmployeeEducationId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeEducationVO.setModifyBy( getUserId( request, response ) );
               employeeEducationService.deleteEmployeeEducation( employeeEducationVO );
            }

            insertlog( request, employeeEducationVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeEducationVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeEducationVO.setSelectedIds( "" );
         employeeEducationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Education by Ajax
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // 获得当前主键
         final String employeeEducationId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeEducationId" ) );

         // 初始化EmployeeEducationVO
         final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( employeeEducationId );
         employeeEducationVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeEducationService.deleteEmployeeEducation( employeeEducationVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeEducationVO, Operate.DELETE, employeeEducationId, "delete_object_ajax" );
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

   public ActionForward list_schoolName_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // 初始化 JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeEducationService.getSchoolNameBySchoolName( q ) );

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

   public ActionForward list_major_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // 初始化 JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeEducationService.getMajorByMajor( q ) );

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
