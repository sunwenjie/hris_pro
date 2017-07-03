package com.kan.hro.web.actions.biz.sb;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

public class SBAdjustmentHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_SB_ADJUSTMENT_HEADER";

   /**
    * 申报调整
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // 获得Action Form
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getOrderId(), "0" ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( sbAdjustmentHeaderVO.getSortColumn() == null || sbAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            sbAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         //String accessAction = "HRO_SB_ADJUSTMENT_HEADER";
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbAdjustmentHeaderVO );
         setDataAuth( request, response, sbAdjustmentHeaderVO );

         // 调用删除方法
         if ( sbAdjustmentHeaderVO.getSubAction() != null && sbAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( sbAdjustmentHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentHeaderHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentHeaderHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentHeaderHolder.setObject( sbAdjustmentHeaderVO );
         // 设置页面记录条数
         sbAdjustmentHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentHeaderService.getSBAdjustmentHeaderVOsByCondition( sbAdjustmentHeaderHolder, true );
         refreshHolder( sbAdjustmentHeaderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentHeaderHolder", sbAdjustmentHeaderHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSBAdjustmentHeader" );
   }

   /**
    * 调整确认
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward list_object_confirm( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // 获得Action Form
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getOrderId(), "0" ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( sbAdjustmentHeaderVO.getSortColumn() == null || sbAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            sbAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // 调整确认，只查询“待审核”状态的数据
         sbAdjustmentHeaderVO.setStatus( "2" );

         //String accessAction = "HRO_SB_ADJUSTMENT_HEADER_CONFIRM";
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbAdjustmentHeaderVO );
         setDataAuth( request, response, sbAdjustmentHeaderVO );

         // 调用删除方法
         if ( sbAdjustmentHeaderVO.getSubAction() != null && sbAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( sbAdjustmentHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentHeaderHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentHeaderHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentHeaderHolder.setObject( sbAdjustmentHeaderVO );
         // 设置页面记录条数
         sbAdjustmentHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentHeaderService.getSBAdjustmentHeaderVOsByCondition( sbAdjustmentHeaderHolder, true );
         refreshHolder( sbAdjustmentHeaderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentHeaderHolder", sbAdjustmentHeaderHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentHeaderConfirmTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSBAdjustmentHeaderConfirm" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( SBAdjustmentHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( SBAdjustmentHeaderVO ) form ).setStatus( "1" );
      ( ( SBAdjustmentHeaderVO ) form ).setAmountCompany( "0" );
      ( ( SBAdjustmentHeaderVO ) form ).setAmountPersonal( "0" );

      request.setAttribute( "sbAdjustmentDetailHolder", new PagedListHolder() );

      // 如果是In House登录，设置帐套数据
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // 跳转到新建界面
      return mapping.findForward( "listSBAdjustmentDetail" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-07
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final SBAdjustmentDetailVO sbAdjustmentDetailVO = new SBAdjustmentDetailVO();

      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // 获得当前FORM
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
            sbAdjustmentHeaderVO.setCreateBy( getUserId( request, response ) );
            sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );

            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( sbAdjustmentHeaderVO.getVendorId() );
            final ClientVO clientVO = clientService.getClientVOByClientId( sbAdjustmentHeaderVO.getClientId() );

            if ( vendorVO != null )
            {
               sbAdjustmentHeaderVO.setVendorNameZH( vendorVO.getNameZH() );
               sbAdjustmentHeaderVO.setVendorNameEN( vendorVO.getNameEN() );
            }

            if ( clientVO != null )
            {
               sbAdjustmentHeaderVO.setClientNo( clientVO.getNumber() );
               sbAdjustmentHeaderVO.setClientNameZH( clientVO.getNameZH() );
               sbAdjustmentHeaderVO.setClientNameEN( clientVO.getNameEN() );
            }

            // 初始化EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

            // 初始化社保个人部分公司承担
            String personalSBBurden = employeeContractSBVO.getPersonalSBBurden();

            // 尝试从订单社保方案中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               // 获取ClientOrderSBVO列表
               final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( sbAdjustmentHeaderVO.getOrderId() );

               if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
               {
                  for ( Object clientOrderSBVOObject : clientOrderSBVOs )
                  {
                     // 获取ClientOrderSBVO
                     final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOObject;

                     if ( clientOrderSBVO != null && clientOrderSBVO.getSbSolutionId().equals( employeeContractSBVO.getSbSolutionId() ) )
                     {
                        personalSBBurden = clientOrderSBVO.getPersonalSBBurden();
                     }
                  }
               }
            }

            // 尝试从订单中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( sbAdjustmentHeaderVO.getOrderId() );
               personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
            }

            // 尝试从社保方案中获取
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
               personalSBBurden = socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getPersonalSBBurden();
            }

            // 设置社保个人部分公司承担
            sbAdjustmentHeaderVO.setPersonalSBBurden( personalSBBurden );

            // 调用添加方法
            sbAdjustmentHeaderService.insertSBAdjustmentHeader( sbAdjustmentHeaderVO );

            sbAdjustmentDetailVO.setAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

            // 返回成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, sbAdjustmentHeaderVO, Operate.ADD, sbAdjustmentHeaderVO.getAdjustmentHeaderId(), null );
         }
         else
         {
            // 清空FORM
            ( ( SBAdjustmentHeaderVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SBAdjustmentDetailAction().list_object( mapping, sbAdjustmentDetailVO, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-07
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

            // 主键获取需解码
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获取SBAdjustmentHeaderVO对象
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

            // 装载界面传值
            sbAdjustmentHeaderVO.update( ( SBAdjustmentHeaderVO ) form );
            sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
            sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, sbAdjustmentHeaderVO, Operate.MODIFY, sbAdjustmentHeaderVO.getAdjustmentHeaderId(), null );
         }
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ), MESSAGE_HEADER );
         }

         // 清空Action Form
         ( ( SBAdjustmentHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SBAdjustmentDetailAction().list_object( mapping, new SBAdjustmentDetailVO(), request, response );
   }

   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // 获得主键需解码
         final String adjustmentHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得SBAdjustmentHeaderVO
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
         sbAdjustmentHeaderVO.setStatus( "2" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 清空FORM
         ( ( SBAdjustmentHeaderVO ) form ).reset();
         ( ( SBAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( "" );

         success( request, MESSAGE_TYPE_SUBMIT );

         insertlog( request, sbAdjustmentHeaderVO, Operate.SUBMIT, adjustmentHeaderId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   // Added by Kevin Jin at 2013-12-06
   public ActionForward approve_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setStatus( "3" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 清空FORM
         ( ( SBAdjustmentHeaderVO ) form ).reset();
         ( ( SBAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );

         success( request, null, "批准成功！" );

         insertlog( request, sbAdjustmentHeaderVO, Operate.SUBMIT, sbAdjustmentHeaderVO.getAdjustmentHeaderId(), "approve_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   // Added by Kevin Jin at 2013-12-06
   public ActionForward rollback_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setStatus( "4" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 清空FORM
         ( ( SBAdjustmentHeaderVO ) form ).reset();
         ( ( SBAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );

         success( request, null, "退回成功！" );

         insertlog( request, sbAdjustmentHeaderVO, Operate.ROllBACK, sbAdjustmentHeaderVO.getAdjustmentHeaderId(), "rollback_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         // 获得Action Form
         SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         // 存在选中的ID
         if ( sbAdjustmentHeaderVO.getSelectedIds() != null && !sbAdjustmentHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : sbAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               sbAdjustmentHeaderVO.setModifyDate( new Date() );
               sbAdjustmentHeaderService.deleteSBAdjustmentHeader( sbAdjustmentHeaderVO );
            }

            insertlog( request, sbAdjustmentHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( sbAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
