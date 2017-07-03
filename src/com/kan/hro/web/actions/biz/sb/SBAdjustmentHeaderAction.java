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
    * �걨����
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // ���Action Form
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getOrderId(), "0" ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( sbAdjustmentHeaderVO.getSortColumn() == null || sbAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            sbAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         //String accessAction = "HRO_SB_ADJUSTMENT_HEADER";
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbAdjustmentHeaderVO );
         setDataAuth( request, response, sbAdjustmentHeaderVO );

         // ����ɾ������
         if ( sbAdjustmentHeaderVO.getSubAction() != null && sbAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( sbAdjustmentHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentHeaderHolder.setObject( sbAdjustmentHeaderVO );
         // ����ҳ���¼����
         sbAdjustmentHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentHeaderService.getSBAdjustmentHeaderVOsByCondition( sbAdjustmentHeaderHolder, true );
         refreshHolder( sbAdjustmentHeaderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentHeaderHolder", sbAdjustmentHeaderHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentHeader" );
   }

   /**
    * ����ȷ��
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // ���Action Form
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getOrderId(), "0" ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( sbAdjustmentHeaderVO.getSortColumn() == null || sbAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            sbAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // ����ȷ�ϣ�ֻ��ѯ������ˡ�״̬������
         sbAdjustmentHeaderVO.setStatus( "2" );

         //String accessAction = "HRO_SB_ADJUSTMENT_HEADER_CONFIRM";
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbAdjustmentHeaderVO );
         setDataAuth( request, response, sbAdjustmentHeaderVO );

         // ����ɾ������
         if ( sbAdjustmentHeaderVO.getSubAction() != null && sbAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( sbAdjustmentHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentHeaderHolder.setObject( sbAdjustmentHeaderVO );
         // ����ҳ���¼����
         sbAdjustmentHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentHeaderService.getSBAdjustmentHeaderVOsByCondition( sbAdjustmentHeaderHolder, true );
         refreshHolder( sbAdjustmentHeaderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentHeaderHolder", sbAdjustmentHeaderHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentHeaderConfirmTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentHeaderConfirm" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( SBAdjustmentHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( SBAdjustmentHeaderVO ) form ).setStatus( "1" );
      ( ( SBAdjustmentHeaderVO ) form ).setAmountCompany( "0" );
      ( ( SBAdjustmentHeaderVO ) form ).setAmountPersonal( "0" );

      request.setAttribute( "sbAdjustmentDetailHolder", new PagedListHolder() );

      // �����In House��¼��������������
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // ��ת���½�����
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
            // ��ʼ��Service�ӿ�
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // ��õ�ǰFORM
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

            // ��ʼ��EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbAdjustmentHeaderVO.getEmployeeSBId() );

            // ��ʼ���籣���˲��ֹ�˾�е�
            String personalSBBurden = employeeContractSBVO.getPersonalSBBurden();

            // ���ԴӶ����籣�����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               // ��ȡClientOrderSBVO�б�
               final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( sbAdjustmentHeaderVO.getOrderId() );

               if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
               {
                  for ( Object clientOrderSBVOObject : clientOrderSBVOs )
                  {
                     // ��ȡClientOrderSBVO
                     final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOObject;

                     if ( clientOrderSBVO != null && clientOrderSBVO.getSbSolutionId().equals( employeeContractSBVO.getSbSolutionId() ) )
                     {
                        personalSBBurden = clientOrderSBVO.getPersonalSBBurden();
                     }
                  }
               }
            }

            // ���ԴӶ����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( sbAdjustmentHeaderVO.getOrderId() );
               personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
            }

            // ���Դ��籣�����л�ȡ
            if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
            {
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutionDTOByHeaderId( employeeContractSBVO.getSbSolutionId() );
               personalSBBurden = socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getPersonalSBBurden();
            }

            // �����籣���˲��ֹ�˾�е�
            sbAdjustmentHeaderVO.setPersonalSBBurden( personalSBBurden );

            // ������ӷ���
            sbAdjustmentHeaderService.insertSBAdjustmentHeader( sbAdjustmentHeaderVO );

            sbAdjustmentDetailVO.setAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

            // ���سɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, sbAdjustmentHeaderVO, Operate.ADD, sbAdjustmentHeaderVO.getAdjustmentHeaderId(), null );
         }
         else
         {
            // ���FORM
            ( ( SBAdjustmentHeaderVO ) form ).reset();
            // ��������ظ��ύ�ľ���
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

            // ������ȡ�����
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ��ȡSBAdjustmentHeaderVO����
            final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

            // װ�ؽ��洫ֵ
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

         // ���Action Form
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
         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         // ������������
         final String adjustmentHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���SBAdjustmentHeaderVO
         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
         sbAdjustmentHeaderVO.setStatus( "2" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // ���FORM
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
         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setStatus( "3" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // ���FORM
         ( ( SBAdjustmentHeaderVO ) form ).reset();
         ( ( SBAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );

         success( request, null, "��׼�ɹ���" );

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
         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );

         final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         sbAdjustmentHeaderVO.setStatus( "4" );
         sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         sbAdjustmentHeaderService.updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // ���FORM
         ( ( SBAdjustmentHeaderVO ) form ).reset();
         ( ( SBAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );

         success( request, null, "�˻سɹ���" );

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
         // ��ʼ��Service�ӿ�
         final SBAdjustmentHeaderService sbAdjustmentHeaderService = ( SBAdjustmentHeaderService ) getService( "sbAdjustmentHeaderService" );
         // ���Action Form
         SBAdjustmentHeaderVO sbAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) form;
         // ����ѡ�е�ID
         if ( sbAdjustmentHeaderVO.getSelectedIds() != null && !sbAdjustmentHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : sbAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               sbAdjustmentHeaderVO = sbAdjustmentHeaderService.getSBAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               sbAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               sbAdjustmentHeaderVO.setModifyDate( new Date() );
               sbAdjustmentHeaderService.deleteSBAdjustmentHeader( sbAdjustmentHeaderVO );
            }

            insertlog( request, sbAdjustmentHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( sbAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         ( ( SBAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBAdjustmentHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
