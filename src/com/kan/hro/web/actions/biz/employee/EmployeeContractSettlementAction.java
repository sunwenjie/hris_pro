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

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSettlementService;

/**  
*   
* 项目名称：HRO_V1  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-10-10 上午10:02:20   
*   
*/
public class EmployeeContractSettlementAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
      final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;
      employeeContractSettlementVO.setSubAction( CREATE_OBJECT );
      employeeContractSettlementVO.setContractId( contractId );
      employeeContractSettlementVO.setResultCap( "0.00" );
      employeeContractSettlementVO.setResultFloor( "0.00" );
      employeeContractSettlementVO.setStatus( "1" );

      // 设置Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // 跳转到新建界面
      return mapping.findForward( "manageEmployeeContractSettlement" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
            // 获得ActionForm
            final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;

            employeeContractSettlementVO.setCreateBy( getUserId( request, response ) );
            employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义字段
            employeeContractSettlementVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT" ) );

            // 新建对象
            employeeContractSettlementService.insertEmployeeContractSettlement( employeeContractSettlementVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form条件
         ( ( EmployeeContractSettlementVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 获得当前主键
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSettlementVO ) form ).getEmployeeSettlementId();
         }

         final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( id );
         employeeContractSettlementVO.reset( null, request );
         employeeContractSettlementVO.setSubAction( VIEW_OBJECT );

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSettlementVO.getContractId() );
         employeeContractVO.reset( null, request );

         // 设置Attribute
         request.setAttribute( "employeeContractSettlementForm", employeeContractSettlementVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageEmployeeContractSettlement" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

            // 获得当前主键
            String employeeContractSettlementId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得VO
            final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( employeeContractSettlementId );
            employeeContractSettlementVO.update( ( EmployeeContractSettlementVO ) form );
            employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            employeeContractSettlementVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT" ) );

            // 修改对象
            employeeContractSettlementService.updateEmployeeContractSettlement( employeeContractSettlementVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( EmployeeContractSettlementVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
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
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

         // 获得当前主键
         final String employeeSettlementId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSettlementId" ) );

         final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( employeeSettlementId );
         employeeContractSettlementVO.setEmployeeSettlementId( employeeSettlementId );
         employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

         // 删除主键对应对象
         final long rows = employeeContractSettlementService.deleteEmployeeContractSettlement( employeeContractSettlementVO );

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

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

         // 获得Action Form
         final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;
         // 存在选中的ID
         if ( employeeContractSettlementVO.getSelectedIds() != null && !employeeContractSettlementVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeContractSettlementVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeContractSettlementVO.setEmployeeSettlementId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );
               employeeContractSettlementService.deleteEmployeeContractSettlement( employeeContractSettlementVO );
            }
         }

         // 清除Selected IDs和子Action
         employeeContractSettlementVO.setSelectedIds( "" );
         employeeContractSettlementVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
