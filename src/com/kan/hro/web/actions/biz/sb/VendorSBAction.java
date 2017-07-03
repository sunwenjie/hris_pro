package com.kan.hro.web.actions.biz.sb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderTempService;
import com.kan.hro.service.inf.biz.sb.VendorSBTempService;

public class VendorSBAction extends BaseAction
{
   public final static String ACCESSACTION = "HRO_SB_HEADER";

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
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( commonBatchVO );
            // �����SubAction��ɾ���б����deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               rollback_objectList( mapping, form, request, response );
            }
            // �����SubAction�Ǹ����б����submitObjects
            else if ( getSubAction( form ).equalsIgnoreCase( SUBMIT_OBJECTS ) )
            {
               // �����޸��б��SubAction
               submit_objectList( mapping, form, request, response );
            }
            // ���SubActionΪ�գ�ͨ����������������򡢷�ҳ�򵼳�������Ajax�ύ������������Ҫ���롣
         }

         commonBatchVO.setAccessAction( ACCESSACTION );
         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortColumn( "batchId" );
            commonBatchVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( commonBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         commonBatchService.getCommonBatchVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "vendorSBBatchHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCommonBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listCommonBatch" );
   }

   /**  
    * To SBHeaderTemp
    *	��Ӧ���籣��ʱ��Headerҳ��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_sbHeaderTemp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����������ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // ��ʼ��Service�ӿ�
      final SBHeaderTempService sbHeaderTempService = ( SBHeaderTempService ) getService( "sbHeaderTempService" );
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

      final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // ��õ�ǰҳ
      final String page = getPage( request );
      // ����Ƿ�Ajax����
      final String ajax = getAjax( request );
      final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) form;
      sbHeaderTempVO.setBatchId( batchId );

      // ���õ�ǰ�û�AccountId
      sbHeaderTempVO.setAccountId( getAccountId( request, response ) );

      // ���õ�ǰ��clientId
      sbHeaderTempVO.setClientId( getCorpId( request, response ) );

      // ��ʼ��PagedListHolder���������÷�ʽ����Service
      final PagedListHolder sbHeaderTempHolder = new PagedListHolder();

      // ���뵱ǰҳ
      sbHeaderTempHolder.setPage( page );

      // ���û��ָ��������Ĭ�ϰ� BatchId����
      if ( sbHeaderTempVO.getSortColumn() == null || sbHeaderTempVO.getSortColumn().isEmpty() )
      {
         sbHeaderTempVO.setSortColumn( "headerId" );
         sbHeaderTempVO.setSortOrder( "desc" );
      }

      // ���뵱ǰֵ����
      sbHeaderTempHolder.setObject( sbHeaderTempVO );
      // ����ҳ���¼����
      sbHeaderTempHolder.setPageSize( listPageSize_medium );
      // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
      sbHeaderTempService.getSBHeaderTempVOsByCondition( sbHeaderTempHolder, true );
      // ˢ��Holder�����ʻ���ֵ
      refreshHolder( sbHeaderTempHolder, request );
      // Holder��д��Request����
      request.setAttribute( "sbHeaderTempHolder", sbHeaderTempHolder );

      if ( new Boolean( ajax ) )
      {
         // д��Role
         return mapping.findForward( "listSBHeaderTempTable" );
      }

      return mapping.findForward( "listSBHeaderTempBody" );
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

   public ActionForward modify_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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

   protected void submit_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final VendorSBTempService vendorSBTempService = ( VendorSBTempService ) getService( "vendorSBTempService" );

         // ���Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // ����ѡ�е�ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            int rows = 0;

            // �ָ�
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  // ���ýӿ�
                  rows += vendorSBTempService.updateBatch( tempCommonBatchVO );
               }

               if ( rows == 0 )
               {
                  warning( request, null, "δ������������", MESSAGE_HEADER );
               }
               else
               {
                  success( request, null, "�����ɹ�  " + String.valueOf( rows ) + " ����������!", MESSAGE_HEADER );
               }

            }

         }

         // ���Selected IDs����Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final VendorSBTempService vendorSBTempService = ( VendorSBTempService ) getService( "vendorSBTempService" );

         // ���Action Form
         final CommonBatchVO commonBatchVO = ( CommonBatchVO ) form;

         // ����ѡ�е�ID
         if ( commonBatchVO.getSelectedIds() != null && !commonBatchVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : commonBatchVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��CommonBatchVO
                  final CommonBatchVO tempCommonBatchVO = commonBatchService.getCommonBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectedId ) );
                  // ����ɾ���ӿ�
                  tempCommonBatchVO.setModifyBy( getUserId( request, response ) );
                  tempCommonBatchVO.setModifyDate( new Date() );

                  vendorSBTempService.rollbackBatch( tempCommonBatchVO );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );
         }

         // ���Selected IDs����Action
         commonBatchVO.setSelectedIds( "" );
         commonBatchVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
