package com.kan.hro.web.actions.biz.finance;

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
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceDetailService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceHeaderService;

/**
 * 
 * @author : ian.huang
 *	@Date   : 2014-5-13
 */
public class SystemInvoiceDetailAction extends BaseAction
{
   
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );
         final SystemInvoiceDetailService systemInvoiceDetailService = ( SystemInvoiceDetailService ) getService( "systemInvoiceDetailService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // �������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ������ζ���
         final SystemInvoiceBatchVO systemInvoiceBatchVO = systemInvoiceBatchService.getInvoiceBatchVOByBatchId( batchId );

         // ��ʼ�����󼰹��ʻ�
         systemInvoiceBatchVO.reset( null, request );
         
         request.setAttribute( "batchForm", systemInvoiceBatchVO );

         // ��������ǰ��ͻ�����ģ�װ�ؿͻ�����
         if ( systemInvoiceBatchVO.getClientId() != null && !systemInvoiceBatchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( systemInvoiceBatchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // ��ö�����ˮID
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );
         // ��ö�����ˮ����
         SystemInvoiceHeaderVO  invoiceHeaderVO=new SystemInvoiceHeaderVO();
         invoiceHeaderVO.setInvoiceId( invoiceId );
         invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         invoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         invoiceHeaderVO.setExtendInvoiceId( invoiceId );
         
         final SystemInvoiceHeaderVO systemInvoiceHeaderVO = systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( invoiceHeaderVO );

         // ��ʼ�����󼰹��ʻ�
         systemInvoiceHeaderVO.reset( null, request );
         request.setAttribute( "HeaderForm", systemInvoiceHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder listDetailHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag = request.getParameter( "pageFlag" );
         // ���뵱ǰҳ
         listDetailHolder.setPage( page );
         // ��ʼ����ѯ����
         final SystemInvoiceDetailVO systemInvoiceDetailVO = (SystemInvoiceDetailVO)form;
         
         // �����������ֵ
         systemInvoiceDetailVO.setInvoiceId( invoiceId );
         // ������������ֶ�
         systemInvoiceDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );
         
         // Ĭ�ϰ� ������ˮID����
         if ( systemInvoiceDetailVO.getSortColumn() == null || systemInvoiceDetailVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceDetailVO.setSortColumn( "invoiceDetailId" );
            systemInvoiceDetailVO.setSortOrder( "desc" );
         }
         // ���뵱ǰֵ����
         listDetailHolder.setObject( systemInvoiceDetailVO );
         // ����ҳ���¼����
         listDetailHolder.setPageSize( listPageSize );
         systemInvoiceDetailVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         systemInvoiceDetailService.getInvoiceDetailVOsByheaderId( listDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( listDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "listDetailHolder", listDetailHolder );
         //д��ҳ������
         request.setAttribute( "pageFlag", pageFlag );
         
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
