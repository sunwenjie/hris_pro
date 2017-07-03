package com.kan.hro.web.actions.biz.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;


public class SystemInvoiceAction extends BaseAction
{
   /**
    * ��ʾϵͳ��Ʊ�����б�
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag=request.getParameter( "pageFlag" );
         // ��ʼ��Service�ӿ�
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );

         // ���Action Form
         final SystemInvoiceBatchVO systemInvoiceBatchVO = ( SystemInvoiceBatchVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         systemInvoiceBatchVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceBatchVO.setCorpId( getCorpId( request, response ) );
         decodedObject( systemInvoiceBatchVO );
         
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder batchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         batchHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( systemInvoiceBatchVO.getSortColumn() == null || systemInvoiceBatchVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceBatchVO.setSortColumn( "batchId" );
            systemInvoiceBatchVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         batchHolder.setObject( systemInvoiceBatchVO );
         // ����ҳ���¼����
         batchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         systemInvoiceBatchService.getInvoiceBatchVOsByBatch( batchHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( batchHolder, request );
         
         // Holder��д��Request����
         request.setAttribute( "batchHolder", batchHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", pageFlag );
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
      //�����б�
      return mapping.findForward( "listBatch" );
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
