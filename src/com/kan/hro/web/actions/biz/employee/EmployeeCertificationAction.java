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
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeCertificationService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeCertificationAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-10-8 下午03:49:23  
* 修改人：Jixiang  
* 修改时间：2013-10-8 下午03:49:23  
* 修改备注：  
* @version   
*   
*/
public class EmployeeCertificationAction extends BaseAction
{

   public final static String accessAction = "HRO_BIZ_EMPLOYEE_CERTIIFICATION";

   /**
    * List employeeCertification
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // 获得Action Form
         final EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;

         // 添加自定义搜索内容######
         // employeeCertificationVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

         dealSubAction( employeeCertificationVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeCertificationVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeCertificationService.getEmployeeCertificationVOsByCondition( pagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( pagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // 跳转到列表界面
         return dealReturn( accessAction, "listEmployeeCertification", mapping, form, request, response );
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
      EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;
      employeeCertificationVO.setEmployeeId( employeeId );
      // 设置Sub Action
      employeeCertificationVO.setSubAction( CREATE_OBJECT );

      //加载雇员姓名
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeCertificationVO.getEmployeeId() );
      employeeCertificationVO.setEmployeeName( employeeVO.getName() );
      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeCertification" );
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // 获得当前主键
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeCertificationVO ) form ).getEmployeeCertificationId();
         }
         // 获得主键对应对象
         final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( id );
         // 刷新对象，初始化对象列表及国际化
         employeeCertificationVO.reset( null, request );

         employeeCertificationVO.setSubAction( VIEW_OBJECT );

         //加载雇员姓名
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeCertificationVO.getEmployeeId() );
         employeeCertificationVO.setEmployeeName( employeeVO.getName() );

         request.setAttribute( "employeeCertificationForm", employeeCertificationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeCertification" );
   }

   /**
    * Add employeeCertification
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
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
            // 获得ActionForm
            final EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;

            employeeCertificationVO.setAccountId( getAccountId( request, response ) );
            employeeCertificationVO.setCreateBy( getUserId( request, response ) );
            employeeCertificationVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column###
            employeeCertificationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

            // 新建对象
            employeeCertificationService.insertEmployeeCertification( employeeCertificationVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeCertificationVO, Operate.ADD, employeeCertificationVO.getEmployeeCertificationId(), null );
         }
         ( ( EmployeeCertificationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeCertification
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
            final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
            // 获得当前主键
            String employeeCertificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationId );

            // 获取登录用户
            employeeCertificationVO.update( ( EmployeeCertificationVO ) form );

            employeeCertificationVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            employeeCertificationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION" ) );

            // 修改对象
            employeeCertificationService.updateEmployeeCertification( employeeCertificationVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            
            insertlog( request, employeeCertificationVO, Operate.MODIFY, employeeCertificationVO.getEmployeeCertificationId(), null );
         }
         // 清空Form条件
         ( ( EmployeeCertificationVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeCertification
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         final EmployeeCertificationVO employeeCertificationVO = new EmployeeCertificationVO();
         // 获得当前主键
         String employeeCertificationId = request.getParameter( "employeeCertificationId" );

         // 删除主键对应对象
         employeeCertificationVO.setEmployeeCertificationId( KANUtil.decodeStringFromAjax( employeeCertificationId ) );
         employeeCertificationVO.setModifyBy( getUserId( request, response ) );
         employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );
         insertlog( request, employeeCertificationVO, Operate.DELETE, employeeCertificationId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Cerfication by Ajax
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );

         // 获得当前主键
         final String employeeCertificationId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCertificationId" ) );

         // 初始化EmployeeCertificationVO
         final EmployeeCertificationVO employeeCertificationVO = employeeCertificationService.getEmployeeCertificationVOByEmployeeCertificationId( employeeCertificationId );
         employeeCertificationVO.setModifyBy( getUserId( request, response ) );

         // 调用删除接口
         final long rows = employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeCertificationVO, Operate.DELETE, employeeCertificationId, "delete_object_ajax" );
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
    * Delete employeeCertification list
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         // 获得Action Form
         EmployeeCertificationVO employeeCertificationVO = ( EmployeeCertificationVO ) form;
         // 存在选中的ID
         if ( employeeCertificationVO.getSelectedIds() != null && !employeeCertificationVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeCertificationVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeCertificationVO.setEmployeeCertificationId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeCertificationVO.setModifyBy( getUserId( request, response ) );
               employeeCertificationService.deleteEmployeeCertification( employeeCertificationVO );
            }
         }
         // 清除Selected IDs和子Action
         employeeCertificationVO.setSelectedIds( "" );
         employeeCertificationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 验证证书 奖项是否存在
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward is_employeeCertification_exist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         //获取当前雇员的所有证书 奖项
         List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );
         //获取当前选择的证书 奖项
         final String certificationId = request.getParameter( "certificationId" );
         boolean flag = false;
         //判断当前选择的证书 奖项是否存在于当前雇员已有的活动
         EmployeeCertificationVO c = null;
         EmployeeCertificationVO theExistEmployeeCertificationVO = null;
         for ( Object o : employeeCertificationVOs )
         {
            c = ( EmployeeCertificationVO ) o;
            if ( c.getCertificationId().equals( certificationId ) )
            {//如果存在
               theExistEmployeeCertificationVO = c;
               flag = true;
            }
         }

         if ( flag )
         {
            out.print( theExistEmployeeCertificationVO.encodedField( theExistEmployeeCertificationVO.getEmployeeCertificationId() ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * 获取已存在的证书 奖项，存在的活动背景色变红
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_employeeCertificationsIDs_jsonArray( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         //初始化接口
         final EmployeeCertificationService employeeCertificationService = ( EmployeeCertificationService ) getService( "employeeCertificationService" );
         //获取雇员id
         final String employeeId = request.getParameter( "employeeId" );
         //获取当前雇员的所有证书 奖项
         List< Object > employeeCertificationVOs = employeeCertificationService.getEmployeeCertificationVOsByEmployeeId( employeeId );

         JSONArray jsonArray = new JSONArray();
         if ( employeeCertificationVOs != null )
         {
            for ( Object o : employeeCertificationVOs )
            {

               jsonArray.add( ( ( EmployeeCertificationVO ) o ).getCertificationId() );
            }
         }
         out.print( jsonArray.toString() );
         out.flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }
}
