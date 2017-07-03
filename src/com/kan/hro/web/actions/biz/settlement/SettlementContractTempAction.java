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
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractTempService;

public class SettlementContractTempAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );

         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO == null )
         {
            BatchTempVO batchTempVOs = new BatchTempVO();
            batchTempVOs.reset();
            return new SettlementTempAction().to_batchDetail( mapping, batchTempVOs, request, response );
         }
         // ����ҳ���PageFlag
         batchTempVO.setPageFlag( BatchTempService.CONTRACT );
         // ��ʼ�����󼰹��ʻ�
         batchTempVO.reset( null, request );
         request.setAttribute( "batchTempForm", batchTempVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // ��ö�����ˮID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // ��ö�����ˮ����
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // ��ʼ�����󼰹��ʻ�
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );
            // �����������TaxId��װ��Tax����
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder contractTempHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         contractTempHolder.setPage( page );
         // ��ʼ����ѯ����
         final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( serviceContractTempVO );
         }
         // �����������ֵ
         serviceContractTempVO.setOrderHeaderId( orderHeaderId );
         serviceContractTempVO.setBatchId( orderHeaderTempVO.getBatchId() );
         // ������������ֶ�
         serviceContractTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� ������ˮID����
         if ( serviceContractTempVO.getSortColumn() == null || serviceContractTempVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractTempVO.setSortColumn( "orderHeaderId" );
            serviceContractTempVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         contractTempHolder.setObject( serviceContractTempVO );
         // ����ҳ���¼����
         contractTempHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         serviceContractTempVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         serviceContractTempService.getServiceContractTempVOsByCondition( contractTempHolder, true );
         refreshHolder( contractTempHolder, request );
         // Holder��д��Request����
         request.setAttribute( "contractTempHolder", contractTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTempTable" );
         }

         if ( contractTempHolder == null || contractTempHolder.getHolderSize() == 0 )
         {
            OrderHeaderTempVO orderHeaderTemp = new OrderHeaderTempVO();
            orderHeaderTemp.reset();
            orderHeaderTemp.setBatchId( "" );
            return new SettlementHeaderTempAction().list_object( mapping, orderHeaderTemp, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listContractTemp" );
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
