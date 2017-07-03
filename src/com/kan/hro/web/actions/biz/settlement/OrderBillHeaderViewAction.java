package com.kan.hro.web.actions.biz.settlement;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.OrderBillHeaderView;
import com.kan.hro.domain.biz.settlement.OrderDTO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.OrderBillHeaderViewService;

/**   
 * �����ƣ�SettlementAction  
 * ���������������
 * �����ˣ�Kevin  
 * ����ʱ�䣺2013-9-13  
 */
public class OrderBillHeaderViewAction extends BaseAction
{

   // ��Ӧjava����
   public final static String JAVA_OBJECT_NAME = "com.kan.hro.domain.biz.settlement.OrderDTO";

   public final static String JAVA_OBJECT_DETAIL_NAME = "com.kan.hro.domain.biz.settlement.SettlementDTO";

   public final static String accessAction = "JAVA_OBJECT_SETTLEMENT";

   // ��һ���б�
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final OrderBillHeaderViewService orderBillHeaderViewService = ( OrderBillHeaderViewService ) getService( "orderBillHeaderViewService" );
         // ���Action Form
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) form;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            orderBillHeaderView.setClientId( BaseAction.getClientId( request, response ) );
            orderBillHeaderView.setStatus( "3" );
         }
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_NAME, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         PagedListHolder pagedListHolder = new PagedListHolder();
         
         // �������������
         if ( listDTO != null && !listDTO.isSearchFirst() || getSubAction( orderBillHeaderView ).equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            if ( KANUtil.filterEmpty( orderBillHeaderView.getMonthly() ) == null )
            {
               orderBillHeaderView.setMonthly( KANUtil.getMonthly( new Date() ) );
            }
         }

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listDTO.getPageSize() );

         pagedListHolder.setObject( orderBillHeaderView );

         orderBillHeaderViewService.getSumOrderBillHeaderViewsByCondition( pagedListHolder, true );

         refresh( pagedListHolder, request, true );

         request.setAttribute( "pagedListHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().specialExportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_NAME, accessAction ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listOrderBillHeaderView" );
   }

   //�鿴�ڶ���
   public ActionForward to_object_detail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
         final String monthly = KANUtil.decodeStringFromAjax( request.getParameter( "monthly" ) );
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) );
         final String status = request.getParameter( "status" );
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final OrderBillHeaderViewService orderBillHeaderViewService = ( OrderBillHeaderViewService ) getService( "orderBillHeaderViewService" );
         // ���Action Form
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) form;
         orderBillHeaderView.setClientId( clientId );
         orderBillHeaderView.setMonthly( monthly );
         orderBillHeaderView.setStatus( status );
         orderBillHeaderView.setOrderId( orderId );
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            orderBillHeaderView.setClientId( BaseAction.getClientId( request, response ) );
         }
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByJavaObjectName( JAVA_OBJECT_DETAIL_NAME, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listDTO != null ? listDTO.getPageSize() : listPageSize );

         pagedListHolder.setObject( orderBillHeaderView );

         orderBillHeaderViewService.getOrderBillDetailViewsByCondition( pagedListHolder, true );

         refresh( pagedListHolder, request, false );

         request.setAttribute( "pagedListHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().specialExportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateSpecialListTable( request, JAVA_OBJECT_DETAIL_NAME ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listOrderBillDetailView" );
   }

   public void load_special_html( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         final String monthly = request.getParameter( "monthly" );
         final String status = request.getParameter( "status" );
         final String entityId = request.getParameter( "entityId" );
         final String businessTypeId = request.getParameter( "businessTypeId" );
         final List< MappingVO > statuses = new ArrayList< MappingVO >();
         if ( !BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            statuses.add( new MappingVO( "0", "��ѡ��" ) );
            statuses.add( new MappingVO( "1", "�½�" ) );
         }
         statuses.add( new MappingVO( "3", "�ѹ���" ) );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ�·�
         final List< MappingVO > monthlies = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );

         // ��ȡ����ʵ��
         final List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntities( getLocale( request ).getLanguage() );
         entities.add( 0, new MappingVO( "0", "��ѡ��" ) );

         // ��ȡҵ������
         final List< MappingVO > businessTypes = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBusinessTypes( getLocale( request ).getLanguage() );
         businessTypes.add( 0, new MappingVO( "0", "��ѡ��" ) );

         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( !BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            rs.append( "<li>" );
            rs.append( "<label>����ʵ��</label>" );
            rs.append( KANUtil.getSelectHTML( entities, "entityId", "entityId", entityId, null, null ) );
            rs.append( "</li>" );
   
            rs.append( "<li>" );
            rs.append( "<label>ҵ������</label>" );
            rs.append( KANUtil.getSelectHTML( businessTypes, "businessTypeId", "businessTypeId", businessTypeId, null, null ) );
            rs.append( "</li>" );
         }

         rs.append( "<li>" );
         rs.append( "<label>�����·�</label>" );
         rs.append( KANUtil.getSelectHTML( monthlies, "monthly", "monthly", monthly, null, null ) );
         rs.append( "</li>" );

         rs.append( "<li>" );
         rs.append( "<label>״̬</label>" );
         rs.append( KANUtil.getSelectHTML( statuses, "status", "status", status, null, null ) );
         rs.append( "</li>" );

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OrderBillHeaderViewService orderBillHeaderViewService = ( OrderBillHeaderViewService ) getService( "orderBillHeaderViewService" );

         // ���Action Form
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) form;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            orderBillHeaderView.setClientId( BaseAction.getClientId( request, response ) );
            orderBillHeaderView.setStatus( "3" );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( orderBillHeaderView );
         if ( "detail".equalsIgnoreCase( request.getParameter( "exportPage" ) ) )
         {
            // �ڶ�������
            orderBillHeaderView.setClientId( KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) ) );
            orderBillHeaderView.setMonthly( KANUtil.decodeStringFromAjax( request.getParameter( "monthly" ) ) );
            orderBillHeaderView.setStatus( request.getParameter( "status" ) );
            orderBillHeaderView.setOrderId( KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) ) );
            orderBillHeaderViewService.getOrderBillDetailViewsByCondition( pagedListHolder, false );
         }
         else
         {
            orderBillHeaderViewService.getOrderBillHeaderViewsByCondition( pagedListHolder, false );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         return new DownloadFileAction().specialExportList( mapping, form, request, response );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��һ�㵼����������excel
   public ActionForward export_complicated_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OrderBillHeaderViewService orderBillHeaderViewService = ( OrderBillHeaderViewService ) getService( "orderBillHeaderViewService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // ���Action Form
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) form;
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( orderBillHeaderView );
         // �ڶ�������
         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "_clientId" ) );
         final String monthly = KANUtil.decodeStringFromAjax( request.getParameter( "_monthly" ) );
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "_orderId" ) );
         final String status = request.getParameter( "_status" );
         orderBillHeaderView.setClientId( clientId );
         orderBillHeaderView.setMonthly( monthly );
         orderBillHeaderView.setStatus( status );
         orderBillHeaderView.setOrderId( orderId );
         orderBillHeaderViewService.getOrderBillDetailViewsByConditionForExport( pagedListHolder, false );
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "clientVO", clientVO );
         // �б�����ġ�
         return new DownloadFileAction().exportListForOrderBillHeaderView( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private void refresh( PagedListHolder pagedListHolder, HttpServletRequest request, boolean isOrderDTO )
   {
      if ( isOrderDTO )
      {
         if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               ( ( OrderDTO ) object ).getOrderHeaderVO().reset( null, request );
            }
         }
      }
      // settlementDTO
      else
      {
         if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               ( ( SettlementDTO ) object ).getServiceContractVO().reset( null, request );
            }
         }
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
