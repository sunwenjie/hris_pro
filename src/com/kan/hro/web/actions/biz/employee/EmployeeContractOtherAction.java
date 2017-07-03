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
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：EmployeeContractOtherAction  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-10-10 上午10:02:20   
*   
*/
public class EmployeeContractOtherAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER";

   /**
    * List Employee Contract Other
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
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         // 获得Action Form
         final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;

         // 添加自定义搜索内容######
         // employeeContractOtherVO.setRemark1( generateDefineListSearches(
         // request, "HRO_BIZ_EMPLOYEE_CONTRACT_Other" ) );

         // 如果子SubAction不为空
         if ( employeeContractOtherVO.getSubAction() != null && !employeeContractOtherVO.getSubAction().trim().equals( "" ) )
         {
            // 如果子SubAction是删除列表操作
            if ( employeeContractOtherVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( employeeContractOtherVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 传入当前页
         pagedListHolder.setPage( page );

         // 传入当前值对象
         pagedListHolder.setObject( employeeContractOtherVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractOtherService.getEmployeeContractOtherVOsByCondition( pagedListHolder, true );
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
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listEmployeeContractOther" );
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
   // Reviewed by Kevin Jin at 2013-11-17
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
      final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;
      employeeContractOtherVO.setSubAction( CREATE_OBJECT );
      employeeContractOtherVO.setContractId( contractId );
      employeeContractOtherVO.setBase( "0.00" );
      employeeContractOtherVO.setResultCap( "0.00" );
      employeeContractOtherVO.setResultFloor( "0.00" );
      employeeContractOtherVO.setCycle( "1" );
      employeeContractOtherVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractOtherVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractOtherVO.setStatus( "1" );
      employeeContractOtherVO.setDescription( "" );

      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractOther" );
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
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractOtherVO ) form ).getEmployeeOtherId();
         }

         // 获得EmployeeContractOtherVO
         final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( id );
         employeeContractOtherVO.reset( null, request );
         employeeContractOtherVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractOtherVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 设置Attribute
         request.setAttribute( "employeeContractOtherForm", employeeContractOtherVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractOther" );
   }

   /**
    * Add Employee Contract Other
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
            // 获得ActionForm
            final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;

            employeeContractOtherVO.setCreateBy( getUserId( request, response ) );
            employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义字段
            employeeContractOtherVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER" ) );

            // 新建对象
            employeeContractOtherService.insertEmployeeContractOther( employeeContractOtherVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractOtherVO, Operate.ADD, employeeContractOtherVO.getEmployeeOtherId(), null );
         }
         // 重复提交处理 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractOtherVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // 清空Form条件
         ( ( EmployeeContractOtherVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Other
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

            // 获得当前主键
            String employeeContractOtherId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得EmployeeContractOtherVO
            final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( employeeContractOtherId );
            employeeContractOtherVO.update( ( EmployeeContractOtherVO ) form );
            employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractOtherVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_Other" ) );

            // 修改对象
            employeeContractOtherService.updateEmployeeContractOther( employeeContractOtherVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractOtherVO, Operate.MODIFY, employeeContractOtherVO.getEmployeeOtherId(), null );
         }

         // 清空Form条件
         ( ( EmployeeContractOtherVO ) form ).reset();
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
    * Delete Employee Contract Other list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

         // 获得Action Form
         final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;
         // 存在选中的ID
         if ( employeeContractOtherVO.getSelectedIds() != null && !employeeContractOtherVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractOtherVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractOtherVO.setEmployeeOtherId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractOtherVO.setModifyBy( getUserId( request, response ) );
               employeeContractOtherService.deleteEmployeeContractOther( employeeContractOtherVO );
            }

            insertlog( request, employeeContractOtherVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractOtherVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         employeeContractOtherVO.setSelectedIds( "" );
         employeeContractOtherVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Other by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

         // 获得当前主键
         final String employeeOtherId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeOtherId" ) );

         final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( employeeOtherId );
         employeeContractOtherVO.setEmployeeOtherId( employeeOtherId );
         employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

         // 删除主键对应对象
         final long rows = employeeContractOtherService.deleteEmployeeContractOther( employeeContractOtherVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractOtherVO, Operate.DELETE, employeeOtherId, "delete_object_ajax" );
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
