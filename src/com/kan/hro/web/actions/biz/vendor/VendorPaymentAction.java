package com.kan.hro.web.actions.biz.vendor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.service.inf.biz.sb.SBDetailService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

/**   
 * �����ƣ�SBAction  
 * ���������籣����
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class VendorPaymentAction extends BaseAction
{

   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
   
   public static String accessAction = "HRO_BIZ_VENDOR_PAYMENT";

   /**
    * List Estimation
    * 
    *	��ʾ��Ӧ���籣���˵��б�
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         // ����pageFlag
         ( ( SBHeaderVO ) form ).setPageFlag( SBHeaderService.PAGE_FLAG_VENDOR );

         // ���Action Form
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         decodedObject( sbHeaderVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( getPage( request ) );

         // ���û��ָ��������Ĭ�ϰ��·�����
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // ��ӹ�Ӧ��������
         sbHeaderVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );

         // ����ǵ���
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // ����Service���������ö��󷵻�
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder��д��Request����
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sbHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", SBBatchService.PAGE_FLAG_BATCH );

         // �����Ajax����
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listVendorPaymentTable" );
         }

         return mapping.findForward( "listVendorPayment" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Ajax
    *	ajax��ȡ��Ӧ�̶�Ӧ���˵�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // ���Action Form
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );
         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // �����������
         sbHeaderVO.setVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         decodedObject( sbHeaderVO );
         // ���ʻ�
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         if ( sbHeaderVO.getSubAction() != null )
         {
            // ����ɾ������
            if ( sbHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               delete_objectList( mapping, form, request, response );
            }
            else if ( sbHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) )
            {
               modify_objectListStatus( mapping, form, request, response );
            }
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( sbHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( getPage( request ) );
         // ������������ֶ�
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ��·�����
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );

         // ����ǵ���
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {

            // ����ǹ�Ӧ�̵�¼�����Ƶ���״ֻ̬��Ϊ����׼��״̬����
            if ( getRole( request, response ) != null && getRole( request, response ).equals( KANConstants.ROLE_VENDOR ) )
            {
               ( ( SBHeaderVO ) sbHeaderHolder.getObject() ).setAdditionalStatus( "2" );
            }

            // ����Service���������ö��󷵻�
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder��д��Request����
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ(��ѯ��Ӧ��ĳ���·ݵ������籣����)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // д��Role
         request.setAttribute( "role", getRole( request, response ) );
         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listHeaderTableForTab" );
   }

   /**  
    * Modify ObjectListStatus
    *	Ajax�޸Ĺ�Ӧ�̶��˵�״̬
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    * @throws KANException 
    */
   private void modify_objectListStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� SBHeaderVO
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // �ύ����׼
         sbHeaderService.submit( sbHeaderVO );

         // ����ѡ�е�ID
         if ( sbHeaderVO.getSelectedIds() != null && !sbHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : sbHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��ClientVO
                  final SBHeaderVO tempSBHeaderVO = sbHeaderService.getSBHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ���ýӿ�
                  tempSBHeaderVO.setModifyBy( getUserId( request, response ) );
                  tempSBHeaderVO.setModifyDate( new Date() );

                  // �޸�SBHeaderVO״̬Ϊ��ȷ�ϡ�
                  if ( sbHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) )
                  {
                     sbHeaderService.submit( sbHeaderVO );
                  }

               }
            }
         }

         // ����ѡ�����SubAction
         ( ( SBHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBHeaderVO ) form ).setSubAction( SEARCH_OBJECT );

         // ��ȷ�ϳɹ���
         success( request, null, "ȷ�ϳɹ�" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To Vendor Detail
    * 
    * ��ʾ������Ӧ��ָ���·ݵ��籣��ϸ
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_vendorDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // ��ʼ����ѯ����SBHeaderVO
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // �����������
         sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setMonthly( request.getParameter( "monthly" ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setPageFlag( SBHeaderService.PAGE_FLAG_HEADER );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );
         setDataAuth( request, response, sbHeaderVO );
         // ���ָ����Ӧ�̶�Ӧ�·�SBHeaderVO��Ϣ
         final List< Object > amountVendorPaymentHeaderVOs = sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );
         // ��ʼ�����Ͷ���
         SBHeaderVO vendorPaymentHeaderVO = new SBHeaderVO();

         if ( amountVendorPaymentHeaderVOs != null && amountVendorPaymentHeaderVOs.size() > 0 )
         {
            vendorPaymentHeaderVO = ( SBHeaderVO ) amountVendorPaymentHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         if ( vendorPaymentHeaderVO != null )
         {
            vendorPaymentHeaderVO.reset( null, request );
         }

         request.setAttribute( "vendorPaymentHeaderVO", vendorPaymentHeaderVO );

         // ����������򵼳���ѯ������������
         if ( ( ( SBHeaderVO ) form ).getSubAction() != null
               && ( ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( SEARCH_OBJECT ) || ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) ) )
         {
            sbHeaderVO.update( ( SBHeaderVO ) form );

            // ��Ӧ��ID��������
            sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         }

         // ���ʻ�
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbHeaderHolder.setPage( getPage( request ) );
         // ������������ֶ�
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // ���û��ָ��������Ĭ�ϰ��·�����
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         sbHeaderHolder.setObject( sbHeaderVO );

         // ����ǵ���
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // ����Service���������ö��󷵻�
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder��д��Request����
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // ����ҳ���¼����
         sbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ(��ѯ��Ӧ��ĳ���·ݵ������籣����)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );

         // Ajax����
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeader" );
   }

   /**
    * To SBDetail
    * 
    * ��ʾ������Ӧ��ָ���·�ָ���籣����������ϸ
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_sbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // �ж��б��Ƿ���Ҫ��ӵ�������
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // ��ʼ����ѯ����SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // �����������
         sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setMonthly( request.getParameter( "monthly" ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setPageFlag( SBHeaderService.PAGE_FLAG_HEADER );
         sbHeaderVO.setAdditionalStatus( request.getParameter( "additionalStatus" ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );
         setDataAuth( request, response, sbHeaderVO );
         // ���ָ����Ӧ�̶�Ӧ�·�SBHeaderVO��Ϣ
         final List< Object > vendorPaymentHeaderVOs = sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );
         // ��ʼ�����Ͷ��� - ��Ӧ����Ϣ
         SBHeaderVO vendorPaymentHeaderVO = new SBHeaderVO();

         if ( vendorPaymentHeaderVOs != null && vendorPaymentHeaderVOs.size() > 0 )
         {
            vendorPaymentHeaderVO = ( SBHeaderVO ) vendorPaymentHeaderVOs.get( 0 );
         }

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         if ( vendorPaymentHeaderVO != null )
         {
            vendorPaymentHeaderVO.reset( null, request );
         }

         request.setAttribute( "vendorPaymentHeaderVO", vendorPaymentHeaderVO );

         // ���SBHeader ID
         final String headerId = ( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );
         // ����SBHeader ID
         sbHeaderVO.setHeaderId( headerId );

         // ��ʼ�����Ͷ��� - �籣������Ϣ
         SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
         setDataAuth( request, response, tempSBHeaderVO );
         final List< Object > sbHeaderVOs = sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderVO );

         // ��ù�Ӧ�̶�Ӧ�·ݵ����籣������Ϣ
         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }
         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         tempSBHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderVO", tempSBHeaderVO );

         // �����������ѯ������������
         if ( ( ( SBHeaderVO ) form ).getSubAction() != null && ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            sbHeaderVO.update( ( SBHeaderVO ) form );

            // SBHeader ID �� SBBatch ID ���ý���ֵ
            sbHeaderVO.setBatchId( KANUtil.decodeString( request.getParameter( "batchId" ) ) );
            sbHeaderVO.setHeaderId( KANUtil.decodeString( request.getParameter( "headerId" ) ) );
         }

         // ���ʻ�
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbDetailHolder.setPage( getPage( request ) );

         // ��ʼ��SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
         sbDetailVO.setStatus( sbHeaderVO.getAdditionalStatus() );

         // ������������ֶ�
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );
         sbDetailVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // ���û��ָ��������Ĭ�ϰ���ĿID����
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // ���뵱ǰֵ����
         sbDetailHolder.setObject( sbDetailVO );
         // ����ҳ���¼����
         sbDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( getAjax( request ) ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   /**  
    * Submit_Confirmation
    *	ȷ���籣����
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� SBHeaderVO
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // �ύ����׼
         sbHeaderService.submit( sbHeaderVO );

         // ����ѡ�����SubAction
         ( ( SBHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBHeaderVO ) form ).setSubAction( SEARCH_OBJECT );

         // ��ȷ�ϳɹ���
         success( request, null, "ȷ�ϳɹ�" );
         // ����pageFlag ��ת
         return forward( sbHeaderVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    * ����Page Flag��ת
    *
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_HEADER ) )
      {
         return to_vendorDetail( mapping, form, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_DETAIL ) )
      {
         return to_sbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}
