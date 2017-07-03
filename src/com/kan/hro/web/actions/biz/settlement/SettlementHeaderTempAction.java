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
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;

public class SettlementHeaderTempAction extends BaseAction
{
    public final static String accessAction = "HRO_SETTLE_ORDER_BATCH_TEMP";
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
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );

         // ��õ�ǰ������������Ϣ��
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��õ�ǰ��������
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // ����ҳ���PageFlag
            batchTempVO.setPageFlag( BatchTempService.HEADER );
            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         /**
          * ��ȡ�����б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder headerTempListHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���뵱ǰҳ
         headerTempListHolder.setPage( page );
         // ��ʼ����ѯ����
         OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) form;
         
         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SettlementTempAction.accessAction, orderHeaderTempVO );
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( orderHeaderTempVO );
         }
         // �����������ֵ
         orderHeaderTempVO.setBatchId( batchId );
         orderHeaderTempVO.setCorpId( getCorpId( request, response ) );
         orderHeaderTempVO.setAccountId( getAccountId( request, response ) );
         // ������������ֶ�
         orderHeaderTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderTempVO.setSortOrder( request.getParameter( "sortOrder" ) );
       
         // Ĭ�ϰ� BatchId����
         if ( orderHeaderTempVO.getSortColumn() == null || orderHeaderTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderTempVO.setSortColumn( "batchId" );
         }

         // ���뵱ǰֵ����
         headerTempListHolder.setObject( orderHeaderTempVO );
         // ����ҳ���¼����
         headerTempListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         orderHeaderTempService.getOrderHeaderTempVOsByCondition( headerTempListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerTempListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "headerTempListHolder", headerTempListHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTempTable" );
         }

         final String boolSearchHeader=request.getParameter( "searchHeader" );

         if ( headerTempListHolder == null || headerTempListHolder.getHolderSize() == 0 )
         {
            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
            {
               BatchTempVO batchTemp = new BatchTempVO();
               batchTemp.reset( null, request );
               batchTemp.setBatchId( "" );
               request.setAttribute( "messageInfo", true );
               return new SettlementTempAction().list_estimation( mapping, batchTemp, request, response );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeaderTemp" );
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
