package com.kan.hro.web.actions.biz.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceHeaderService;

public class SystemInvoiceHeaderAction extends BaseAction
{
   /**
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
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         // ��õ�ǰ������������Ϣ��
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // ��õ�ǰ��������
         final SystemInvoiceBatchVO  systemInvoiceBatchVO =systemInvoiceBatchService.getInvoiceBatchVOByBatchId( batchId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         systemInvoiceBatchVO.reset( null, request );
         request.setAttribute( "batchForm", systemInvoiceBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag = request.getParameter( "pageFlag" );
         // ���뵱ǰҳ
         headerListHolder.setPage( page );
         // ��ʼ����ѯ����
         SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( systemInvoiceHeaderVO );
         }
         // �����������ֵ
         systemInvoiceHeaderVO.setBatchId( systemInvoiceBatchVO.getBatchId() );
         systemInvoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         // ������������ֶ�
         systemInvoiceHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );
         
         // Ĭ�ϰ� BatchId����
         if ( systemInvoiceHeaderVO.getSortColumn() == null || systemInvoiceHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeaderVO.setSortColumn( "invoiceId" );
         }

         // ���뵱ǰֵ����
         headerListHolder.setObject( systemInvoiceHeaderVO );
         // ����ҳ���¼����
         headerListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         systemInvoiceHeaderService.getInvoiceHeaderVOsByCondition( headerListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", pageFlag );
         if ( new Boolean( ajax ) )
         {
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
    *  ����б�
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_objectByHeaderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         // ��õ�ǰ����
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );
         
         SystemInvoiceHeaderVO  systemInvoiceHeaderVO=new SystemInvoiceHeaderVO();
         systemInvoiceHeaderVO.setInvoiceId( invoiceId );
         systemInvoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setExtendInvoiceId( invoiceId );
         // ��õ�ǰ��������
         final SystemInvoiceHeaderVO  invoiceHeaderVO =systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( systemInvoiceHeaderVO );
         
         request.setAttribute( "headerForm", invoiceHeaderVO );
         
         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }
         
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
        
         // ���뵱ǰҳ
         headerListHolder.setPage( page );
         // ��ʼ����ѯ����
         SystemInvoiceHeaderVO systemInvoiceHeader = new SystemInvoiceHeaderVO();
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( systemInvoiceHeader );
         }
         
         this.saveToken( request );
         // �����������ֵ
         systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeader.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeader.setParentInvoiceId( invoiceHeaderVO.getInvoiceId() );
         systemInvoiceHeader.setExtendInvoiceId( invoiceHeaderVO.getInvoiceId() );
         systemInvoiceHeader.setBatchId( invoiceHeaderVO.getBatchId() );
         // ������������ֶ�
         systemInvoiceHeader.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeader.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� BatchId����
         if ( systemInvoiceHeader.getSortColumn() == null || systemInvoiceHeader.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeader.setSortColumn( "invoiceId" );
         }

         // ���뵱ǰֵ����
         headerListHolder.setObject( systemInvoiceHeader );
         // ����ҳ���¼����
         headerListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         systemInvoiceHeaderService.getSubSystemInvoiceHeaderByHeaderId( headerListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", "Split" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      //������ֽ���
      return mapping.findForward( "listSubInvoice" );
   }
   
   /**
    * ���
    */
   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

      // ��õ�ǰ����
      final String invoiceId =KANUtil.decodeStringFromAjax(request.getParameter( "invoiceId" )) ;
      
      SystemInvoiceHeaderVO  invoiceHeaderVO=new SystemInvoiceHeaderVO();
      invoiceHeaderVO.setInvoiceId( invoiceId );
      invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
      invoiceHeaderVO.setAccountId( getAccountId( request, response ) );
      invoiceHeaderVO.setExtendInvoiceId( invoiceId );
      // ��õ�ǰ����Ʊ
      final SystemInvoiceHeaderVO  systemInvoiceHeader =systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( invoiceHeaderVO );
      // �����ظ��ύ
      if ( this.isTokenValid( request ,true) && systemInvoiceHeader!=null)
      {
         // ���ActionForm
         SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setExtendInvoiceId( systemInvoiceHeader.getInvoiceId());
         systemInvoiceHeaderVO.setParentInvoiceId( systemInvoiceHeader.getInvoiceId());
         systemInvoiceHeaderVO.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         if(systemInvoiceHeaderVO.getBranch()==null)
         systemInvoiceHeaderVO.setBranch( systemInvoiceHeader.getBranch() );
         if(systemInvoiceHeaderVO.getOwner()==null)
         systemInvoiceHeaderVO.setOwner( systemInvoiceHeader.getOwner() );
         systemInvoiceHeaderVO.setBatchId( systemInvoiceHeader.getBatchId() );
         systemInvoiceHeaderVO.setCreateBy( getUserId( request, response ) );
         systemInvoiceHeaderVO.setModifyBy( getUserId( request, response ) );
         // �½�����
         systemInvoiceHeaderService.insertInvoiceHeader( systemInvoiceHeaderVO );
         
         //�������˼�¼
         SystemInvoiceHeaderVO strikeInfo=systemInvoiceHeaderVO;
         strikeInfo.setBillAmountCompany( "-"+systemInvoiceHeaderVO.getBillAmountCompany() );
         strikeInfo.setCostAmountCompany("-"+ systemInvoiceHeaderVO.getCostAmountCompany() );
         strikeInfo.setTaxAmount( "-"+systemInvoiceHeaderVO.getTaxAmount() );
         strikeInfo.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setParentInvoiceId(null);
         //�������˼�¼
         systemInvoiceHeaderService.insertInvoiceHeader( strikeInfo );
         // ������ӳɹ����
         success( request );
      }
      // ���Form����
      ( ( SystemInvoiceHeaderVO ) form ).reset();
      return list_objectByHeaderId( mapping, form, request, response );
   }

   /**
    * �ϲ���Ʊ�б�
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward combineInvoice( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         final PagedListHolder headerListHolder = new PagedListHolder();
         String invoiceId="";
         // ��õ�ǰ����
         final String invoiceIdArray =request.getParameter( "invoiceId" ) ;
         for ( String string : invoiceIdArray.split( "," ) )
         {
            if(invoiceId!=""&&string!=""){
               invoiceId=invoiceId+","+ KANUtil.decodeStringFromAjax(string);
            }else{
               invoiceId= KANUtil.decodeStringFromAjax(string);
            }
         }
        
         // ��ʼ����ѯ����
         SystemInvoiceHeaderVO systemInvoiceHeader = new SystemInvoiceHeaderVO();
         // �����������ֵ
         systemInvoiceHeader.setInvoiceId( invoiceId );
         systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeader.setAccountId( getAccountId( request, response ) );
         // ������������ֶ�
         systemInvoiceHeader.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeader.setSortOrder( request.getParameter( "sortOrder" ) );

         // Ĭ�ϰ� BatchId����
         if ( systemInvoiceHeader.getSortColumn() == null || systemInvoiceHeader.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeader.setSortColumn( "invoiceId" );
         }
         systemInvoiceHeader.reset( null, request );
         
         SystemInvoiceHeaderVO systemInvoiceHeaderVO=systemInvoiceHeaderService.getSystemInvoiceHeaderById(systemInvoiceHeader);
         if(systemInvoiceHeaderVO!=null){
            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            systemInvoiceHeaderVO.reset( null, request );
            request.setAttribute( "headerForm", systemInvoiceHeaderVO );
         }
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         
         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }
         
         // ���뵱ǰҳ
         headerListHolder.setPage( page );
         // ���뵱ǰֵ����
         headerListHolder.setObject( systemInvoiceHeader );
         // ����ҳ���¼����
         headerListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         systemInvoiceHeaderService.getComSystemInvoiceHeaderById( headerListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( headerListHolder, request );
         
         this.saveToken( request );
         
         // Holder��д��Request����
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", "Merge");
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listMagerHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      //�����ϲ�ҳ��
      return mapping.findForward( "listCompound" );
   }
   
   
   /**
    * �ϲ�ϵͳ��Ʊ
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_objectBycombine( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

      // �����ظ��ύ
      if ( this.isTokenValid( request, true ))
      {
         // ���ActionForm
         final SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         //ת��
         decodedObject( systemInvoiceHeaderVO );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( systemInvoiceHeaderVO.getBatchId()) );
         if(systemInvoiceHeaderVO.getBranch()==null)
            systemInvoiceHeaderVO.setBranch( "0" );
         if(systemInvoiceHeaderVO.getOwner()==null)
            systemInvoiceHeaderVO.setOwner( "0");
         systemInvoiceHeaderVO.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         systemInvoiceHeaderVO.setExtendInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         systemInvoiceHeaderVO.setCreateBy( getUserId( request, response ) );
         systemInvoiceHeaderVO.setModifyBy( getUserId( request, response ) );
         // �½�����
         systemInvoiceHeaderService.insertInvoiceHeader( systemInvoiceHeaderVO );
            
         //�������˼�¼
         SystemInvoiceHeaderVO strikeInfo=systemInvoiceHeaderVO;
         strikeInfo.setBillAmountCompany( "-"+systemInvoiceHeaderVO.getBillAmountCompany() );
         strikeInfo.setCostAmountCompany("-"+ systemInvoiceHeaderVO.getCostAmountCompany() );
         strikeInfo.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setExtendInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setTaxAmount("-"+ systemInvoiceHeaderVO.getTaxAmount() );
         strikeInfo.setParentInvoiceId( null);
         //�������˼�¼
         systemInvoiceHeaderService.insertInvoiceHeader( strikeInfo );
         // ������ӳɹ����
         success( request );
         // ���Form����
         ( ( SystemInvoiceHeaderVO ) form ).reset();
      }  
      request.setAttribute( "pageFlag", "Merge" );
      return  list_object( mapping, form, request, response );
   }
   
   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
