package com.kan.hro.web.actions.biz.settlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;

public class SettlementHeaderAction extends BaseAction
{
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH";

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
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getClientId() );
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
         OrderHeaderVO orderHeaderVO = ( OrderHeaderVO ) form;
         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SettlementAction.accessAction, orderHeaderVO );

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( orderHeaderVO );
         }
         // �����������ֵ
         orderHeaderVO.setBatchId( batchId );
         orderHeaderVO.setCorpId( getCorpId( request, response ) );
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
