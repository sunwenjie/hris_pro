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
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderDetailService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

/**   
 * �����ƣ�SettlementAction  
 * ���������������
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class SettlementAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH";

   /**
    * list_estimation
    * 
    *	��ʾԤ�������б�
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final BatchService batchService = ( BatchService ) getService( "batchService" );

         // ���Action Form
         final BatchVO batchVO = ( BatchVO ) form;
         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, batchVO );
         // �����������
         batchVO.setPageFlag( BatchTempService.BATCH );
         // ��Ҫ���õ�ǰ�û�AccountId
         batchVO.setAccountId( getAccountId( request, response ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ����Զ�����������
         batchVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         decodedObject( batchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder batchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         batchHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( batchVO.getSortColumn() == null || batchVO.getSortColumn().trim().equals( "" ) )
         {
            batchVO.setSortColumn( "batchId" );
            batchVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         batchHolder.setObject( batchVO );
         // ����ҳ���¼����
         batchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         batchService.getBatchVOsByCondition( batchHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( batchHolder, request );

         // Holder��д��Request����
         request.setAttribute( "batchHolder", batchHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", BatchTempService.BATCH );

         // �����ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBatch" );
   }

   /**
    * to_estimationNew
    * 
    * ת�򴴽�Ԥ�����ν��棬����TEMP
    * ѡ�������ο�Batch
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no user
      return null;
   }

   /**
    * add_estimation
    * 
    * ����Ԥ�����Σ�����TEMP
    * ������Ա���԰����λ����Э���˻أ��κ��˻�����ɾ��TEMP������
    * TEMP���ж�Ӧ�ķ���Э�鲻�ܱ��޸ģ���������Э�飬ֻ�ܲ鿴��
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no user
      return null;
   }

   /**
    * submit_estimation
    * 
    * �ύԤ�����Σ�TEMP�����ݱ����Ƶ����ñ���TEMP���ݱ�ɾ��
    * �ύ�������޷����޸ģ��κβ��ֻ�ܵ���
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   /**
    * rollback_estimation
    * 
    * �˻�Ԥ�����λ����Э�� - TEMP�����ݱ��������
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   /**
    * To Batch Detail
    * 
    * ��ʾ���μ������б�
    * ��Ҫ��ʾ��ǰ���εĻ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_batchDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );

         // ��õ�ǰ������������Ϣ��
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��õ�ǰ��������
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // ����ҳ���PageFlag
         batchVO.setPageFlag( BatchTempService.HEADER );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getCorpId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         /**
          * ��ȡ�����б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         headerListHolder.setPage( page );
         // ��ʼ����ѯ����
         OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
         // �����������ֵ
         orderHeaderVO.setBatchId( batchId );
         orderHeaderVO.setAccountId( getAccountId( request, response ) );
         // ������������ֶ�
         orderHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� BatchId����
         if ( orderHeaderVO.getSortColumn() == null || orderHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderVO.setSortColumn( "batchId" );
         }

         // ���뵱ǰֵ����
         headerListHolder.setObject( orderHeaderVO );
         // ����ҳ���¼����
         headerListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         orderHeaderService.getOrderHeaderVOsByCondition( headerListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "headerListHolder", headerListHolder );
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeader" );
   }

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
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_headerDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         // �����������ֵ
         serviceContractVO.setOrderHeaderId( orderHeaderId );
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

   /**
    * to_contractDetail
    * 
    * ��ʾ���Ρ������ͷ���Э�������Ϣ����ϸ�б�
    * ��Ҫ��ʾ��ǰ����Э��Ļ�����
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_contractDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );
         final ServiceContractService serviceContractService = ( ServiceContractService ) getService( "serviceContractService" );
         final OrderDetailService orderDetailService = ( OrderDetailService ) getService( "orderDetailService" );

         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // ����ҳ���PageFlag
         batchVO.setPageFlag( BatchTempService.DETAIL );
         // ��ʼ�����󼰹��ʻ�
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getCorpId() );
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

         // ��÷���Э����ˮID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // ��÷���Э����ˮ����
         final ServiceContractVO serviceContractVO = serviceContractService.getServiceContractVOByContractId( contractId );
         // ��ʼ�����󼰹��ʻ�
         serviceContractVO.reset( null, request );
         request.setAttribute( "serviceContractVO", serviceContractVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder orderDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         orderDetailHolder.setPage( page );
         // ��ʼ����ѯ����
         final OrderDetailVO orderDetailVO = new OrderDetailVO();
         // �����������ֵ
         orderDetailVO.setContractId( contractId );
         // ������������ֶ�
         orderDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ�����Э����ˮID����
         if ( orderDetailVO.getSortColumn() == null || orderDetailVO.getSortColumn().trim().equals( "" ) )
         {
            orderDetailVO.setSortColumn( "itemId" );
         }

         // ���뵱ǰֵ����
         orderDetailHolder.setObject( orderDetailVO );
         // ����ҳ���¼����
         orderDetailHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         orderDetailVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         orderDetailService.getOrderDetailVOsByCondition( orderDetailHolder, true );
         refreshHolder( orderDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "orderDetailHolder", orderDetailHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listDetail" );
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
