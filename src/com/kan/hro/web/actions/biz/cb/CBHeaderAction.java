package com.kan.hro.web.actions.biz.cb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;

public class CBHeaderAction extends BaseAction
{

   // ��ǰAction��Ӧ��JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.cb.CBDTO";

   // ��ǰAction��Ӧ��Access Action
   public static final String ACCESSACTION_CBBILL = "HRO_CB_BILL";

   // ����StatusFlag��ȡ״ֵ̬Status
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // ��ʼ����Ĭ��Ϊ��Ԥ����
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * ��ȡ������Ϣ�ͷ���Э����Ϣ
          */
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // ��õ�ǰ��������ID
         String cbBatchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         // ��ʼ��CBBatchVO
         CBBatchVO cbBatchVO = new CBBatchVO();
         cbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbBatchVO.setBatchId( cbBatchId );
         cbBatchVO.setCorpId( getCorpId( request, response ) );
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         final List< Object > cbBatchVOs = cbBatchService.getCBBatchVOsByCondition( cbBatchVO );

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            cbBatchVO = ( CBBatchVO ) cbBatchVOs.get( 0 );
         }

         // ��ʼ��CBHeaderVO
         CBHeaderVO cbHeaderVO = ( CBHeaderVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( cbHeaderVO );
         }

         cbHeaderVO.setBatchId( cbBatchId );
         cbHeaderVO.setCorpId( getCorpId( request, response ) );
         cbHeaderVO.setAccountId( getAccountId( request, response ) );
         cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );

         // ����ҳ���PageFlag��StatusFlag
         cbBatchVO.setPageFlag( CBBatchService.PAGE_FLAG_HEADER );
         cbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         cbBatchVO.reset( null, request );

         request.setAttribute( "cbBatchForm", cbBatchVO );

         /**
          * ��ȡ�̱������б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder cbHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         cbHeaderHolder.setPage( page );

         // ��������
         cbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            passClientOrders( request, response );
         }

         // ���û��ָ��������Ĭ�ϰ� �̱�������ˮ������
         if ( cbHeaderVO.getSortColumn() == null || cbHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            cbHeaderVO.setSortColumn( "employeeCBId,monthly" );
            cbHeaderVO.setSortOrder( "" );
         }

         if ( KANUtil.filterEmpty( cbHeaderVO.getStatus() ) == null )
         {
            if ( request.getParameter( "statusFlag" ) != null )
            {
               cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
            }
         }

         // ���ʻ�
         cbHeaderVO.reset( null, request );

         if ( cbHeaderVO.getStatus().equals( "1" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 1 ) );
            cbHeaderVO.setStatuses( statuses );
         }
         else if ( cbHeaderVO.getStatus().equals( "2" ) )
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 2 ) );
            cbHeaderVO.setStatuses( statuses );
         }
         else
         {
            final List< MappingVO > statuses = new ArrayList< MappingVO >();
            statuses.add( cbHeaderVO.getStatuses().get( 3 ) );
            statuses.add( cbHeaderVO.getStatuses().get( 4 ) );
            statuses.add( cbHeaderVO.getStatuses().get( 5 ) );
            cbHeaderVO.setStatuses( statuses );
         }

         if ( KANUtil.filterEmpty( cbHeaderVO.getCbStatusArray() ) != null && cbHeaderVO.getCbStatusArray().length > 0 )
         {
            cbHeaderVO.setCbStatus( KANUtil.toJasonArray( cbHeaderVO.getCbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         cbHeaderVO.getCbStatuses().remove( 0 ); // Add by siuxia
         // ���뵱ǰֵ����
         cbHeaderHolder.setObject( cbHeaderVO );
         // ����ҳ���¼����
         cbHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         cbHeaderService.getCBHeaderVOsByCondition( cbHeaderHolder, true );
         refreshHolder( cbHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "cbHeaderHolder", cbHeaderHolder );

         // �Ƿ���ʾ������ť
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTable" );
         }
         final String boolSearchHeader = request.getParameter( "searchHeader" );
         // ����������̱���ϸ����
         if ( cbHeaderHolder == null || cbHeaderHolder.getHolderSize() == 0 )
         {
            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
            {
               CBBatchVO cbBatch = new CBBatchVO();
               cbBatch.reset( null, request );
               cbBatch.setBatchId( "" );
               request.setAttribute( "cbBatchForm", cbBatch );
               request.setAttribute( "messageInfo", true );
               return new CBAction().list_estimation( mapping, cbBatch, request, response );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listHeader" );
   }

   public ActionForward list_cbBill( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * ��ȡ������Ϣ�ͷ���Э����Ϣ
          */
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );

         // ��ʼ��CBHeaderVO
         CBHeaderVO cbHeaderVO = ( CBHeaderVO ) form;
         cbHeaderVO.setAccountId( getAccountId( request, response ) );

         /**
          * ��ȡ�̱������б�
          */
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder cbBillHolder = new PagedListHolder();
         // ���뵱ǰҳ
         cbBillHolder.setPage( page );

         // ��������
         cbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
            passClientOrders( request, response );
         }
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            cbHeaderVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // ���û��ָ��������Ĭ�ϰ� �̱�������ˮ������
         if ( cbHeaderVO.getSortColumn() == null || cbHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            cbHeaderVO.setSortColumn( "employeeCBId,monthly" );
            cbHeaderVO.setSortOrder( "" );
         }
         // ���ʻ�
         cbHeaderVO.reset( null, request );
         if ( KANUtil.filterEmpty( cbHeaderVO.getCbStatusArray() ) != null && cbHeaderVO.getCbStatusArray().length > 0 )
         {
            cbHeaderVO.setCbStatus( KANUtil.toJasonArray( cbHeaderVO.getCbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         cbHeaderVO.getCbStatuses().remove( 0 );
         // ���뵱ǰֵ����
         cbBillHolder.setObject( cbHeaderVO );
         // ����ҳ���¼����
         cbBillHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         cbHeaderService.getCBHeaderVOsByCondition( cbBillHolder, true );
         refreshHolder( cbBillHolder, request );

         // Holder��д��Request����
         request.setAttribute( "cbBillListHolder", cbBillHolder );

         // �Ƿ���ʾ������ť
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCBBillTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listCBBill" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }
}
