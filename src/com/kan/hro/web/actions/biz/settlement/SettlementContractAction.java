package com.kan.hro.web.actions.biz.settlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class SettlementContractAction extends BaseAction
{
   /**
    * to_HeaderDetail
    * 
    * ��ʾ���Ρ����������Ϣ������Э���б�
    * ��Ҫ��ʾ��ǰ�������Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );
         final ServiceContractService serviceContractService = ( ServiceContractService ) getService( "serviceContractService" );

         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // ����ҳ���PageFlag
         batchVO.setPageFlag( BatchTempService.CONTRACT );
         // ��ʼ�����󼰹��ʻ�
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // ��ö�����ˮID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // ��ö�����ˮ����
         final OrderHeaderVO orderHeaderVO = orderHeaderService.getOrderHeaderVOByOrderHeaderId( orderHeaderId );
         // ��ʼ�����󼰹��ʻ�
         orderHeaderVO.reset( null, request );
         request.setAttribute( "orderHeaderVO", orderHeaderVO );

         // �����������TaxId��װ��Tax����
         if ( orderHeaderVO.getTaxId() != null && !orderHeaderVO.getTaxId().trim().equals( "" ) )
         {
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderVO.getTaxId() );
            taxVO.reset( null, request );
            request.setAttribute( "taxVO", taxVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder contractHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         contractHolder.setPage( page );
         // ��ʼ����ѯ����
         final ServiceContractVO serviceContractVO = ( ServiceContractVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( serviceContractVO );
         }
         // �����������ֵ
         serviceContractVO.setOrderHeaderId( orderHeaderId );
         serviceContractVO.setBatchId( orderHeaderVO.getBatchId() );

         // ������������ֶ�
         serviceContractVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� ������ˮID����
         if ( serviceContractVO.getSortColumn() == null || serviceContractVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractVO.setSortColumn( "orderHeaderId" );
            serviceContractVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         contractHolder.setObject( serviceContractVO );
         // ����ҳ���¼����
         contractHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         serviceContractVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         serviceContractService.getServiceContractVOsByCondition( contractHolder, true );
         refreshHolder( contractHolder, request );
         // Holder��д��Request����
         request.setAttribute( "contractHolder", contractHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listContract" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

}
