/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeMembershipService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeMembershipAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-9-18 下午05:08:07  
* 修改人：Jixiang  
* 修改时间：2013-9-18 下午05:08:07  
* 修改备注：  
* @version   
*   
*/
public class EmployeeMembershipAction extends BaseAction
{
   public static final String accessAction = "HRO_BIZ_EMPLOYEE_MEMBERSHIP";

   /**
    * List employeeMembership
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // 获得Action Form
         final EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;

         dealSubAction( employeeMembershipVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeMembershipVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeMembershipService.getEmployeeMembershipVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeMembership", mapping, form, request, response );
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
      EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;
      employeeMembershipVO.setEmployeeId( employeeId );
      // 设置Sub Action
      ( ( EmployeeMembershipVO ) form ).setSubAction( CREATE_OBJECT );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeMembershipVO.getEmployeeId() );
      employeeMembershipVO.setEmployeeName( employeeVO.getName() );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeMembership" );
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // 获得当前主键
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeMembershipVO ) form ).getEmployeeMembershipId();
         }
         // 获得主键对应对象
         final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeMembershipVO.reset( null, request );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeMembershipVO.getEmployeeId() );
         employeeMembershipVO.setEmployeeName( employeeVO.getName() );

         employeeMembershipVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeMembershipForm", employeeMembershipVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeMembership" );
   }

   /**
    * Add employeeMembership
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
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
            // 获得ActionForm
            final EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;

            employeeMembershipVO.setAccountId( getAccountId( request, response ) );
            employeeMembershipVO.setCreateBy( getUserId( request, response ) );
            employeeMembershipVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeMembershipVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP" ) );

            // 新建对象
            employeeMembershipService.insertEmployeeMembership( employeeMembershipVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeMembershipVO, Operate.ADD, employeeMembershipVO.getEmployeeMembershipId(), null );
         }
         ( ( EmployeeMembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeMembership
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
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
            // 获得当前主键
            String employeeMembershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipId );

            // 获取登录用户
            employeeMembershipVO.update( ( EmployeeMembershipVO ) form );

            employeeMembershipVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeMembershipVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP" ) );

            // 修改对象
            employeeMembershipService.updateEmployeeMembership( employeeMembershipVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeMembershipVO, Operate.MODIFY, employeeMembershipVO.getEmployeeMembershipId(), null );
         }
         // 清空Form条件
         ( ( EmployeeMembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeMembership
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         final EmployeeMembershipVO employeeMembershipVO = new EmployeeMembershipVO();
         // 获得当前主键
         String employeeMembershipId = request.getParameter( "employeeMembershipId" );

         // 删除主键对应对象
         employeeMembershipVO.setEmployeeMembershipId( KANUtil.decodeStringFromAjax( employeeMembershipId ) );
         employeeMembershipVO.setModifyBy( getUserId( request, response ) );
         employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );
         insertlog( request, employeeMembershipVO, Operate.DELETE, employeeMembershipId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeMembership list
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // 获得Action Form
         EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;
         // 存在选中的ID
         if ( employeeMembershipVO.getSelectedIds() != null && !employeeMembershipVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeMembershipVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeMembershipVO.setEmployeeMembershipId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeMembershipVO.setModifyBy( getUserId( request, response ) );
               employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );
            }

            insertlog( request, employeeMembershipVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeMembershipVO.getSelectedIds() ) );
         }
         // 清除Selected IDs和子Action
         employeeMembershipVO.setSelectedIds( "" );
         employeeMembershipVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Membership by Ajax
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );

         // 获得当前主键
         final String employeeMembershipId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeMembershipId" ) );

         // 初始化EmployeeMembershipVO
         final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipId );
         employeeMembershipVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeMembershipVO, Operate.DELETE, employeeMembershipId, "delete_object_ajax" );
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
    * 验证社会活动是否存在
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward is_employeeMembership_exist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String employeeId = request.getParameter( "employeeId" );
         //初始化接口
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         //获取当前雇员的所有社会活动
         List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );
         //获取当前选择的社会活动
         final String membershipId = request.getParameter( "membershipId" );
         boolean flag = false;
         //判断当前选择的社会活动是否存在于当前雇员已有的活动
         EmployeeMembershipVO m = null;
         EmployeeMembershipVO theExistEmployeeMembershipVO = null;
         for ( Object o : employeeMembershipVOs )
         {
            m = ( EmployeeMembershipVO ) o;
            if ( m.getMembershipId().equals( membershipId ) )
            {//如果存在
               theExistEmployeeMembershipVO = m;
               flag = true;
            }
         }

         if ( flag )
         {
            out.print( theExistEmployeeMembershipVO.encodedField( theExistEmployeeMembershipVO.getEmployeeMembershipId() ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * 获取已存在的社会活动，存在的活动背景色变红
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_exist_employeeMemberships( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         //初始化接口
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         //获取雇员id
         final String employeeId = request.getParameter( "employeeId" );
         //获取所有的社会活动
         final List< MappingVO > membershipMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getMemberships( request.getLocale().getLanguage() );
         //获取当前雇员的所有社会活动
         List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );

         MappingVO mappingVO = null;
         EmployeeMembershipVO employeeMembershipVO = null;
         List< MappingVO > existMappingVO = new ArrayList< MappingVO >();
         for ( Object o : membershipMappingVOs )
         {
            mappingVO = ( MappingVO ) o;
            for ( Object emo : employeeMembershipVOs )
            {
               employeeMembershipVO = ( EmployeeMembershipVO ) emo;
               //如果存在
               if ( employeeMembershipVO.getMembershipId().equals( mappingVO.getMappingId() ) )
               {
                  existMappingVO.add( mappingVO );
               }
            }
         }

         StringBuffer result = new StringBuffer();
         //遍历用户存在社会活动
         for ( MappingVO m : existMappingVO )
         {
            result.append( m.getMappingId() ).append( ":" ).append( m.getMappingValue() ).append( "##" );
         }
         out.print( result.length() >= 2 ? result.toString().substring( 0, result.length() - 2 ) : "" );
         existMappingVO = null;

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

}
