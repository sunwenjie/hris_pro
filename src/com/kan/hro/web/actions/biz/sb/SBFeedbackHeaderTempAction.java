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
import com.kan.hro.service.inf.biz.sb.SBFeedbackHeaderTempService;

public class SBFeedbackHeaderTempAction extends BaseAction
{
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
         String batchId = new String();

         // �����������ID
         if ( new Boolean( ajax ) )
         {
            batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         }
         else
         {
            batchId = KANUtil.decodeString( request.getParameter( "batchId" ) );
         }

         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );
         commonBatchVO.reset( null, request );
         request.setAttribute( "commonBatchVO", commonBatchVO );

         // ��ʼ��Service�ӿ�
         final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );
         final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( sbHeaderTempVO );
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
            sbHeaderTempVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         }
         else
         {
            sbHeaderTempVO.setBatchId( batchId );
         }

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( sbHeaderTempVO.getSortColumn() == null || sbHeaderTempVO.getSortColumn().isEmpty() )
         {
            sbHeaderTempVO.setSortColumn( "headerId" );
            sbHeaderTempVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( sbHeaderTempVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbFeedbackHeaderTempService.getSBHeaderTempVOsByCondition( pagedListHolder, true );
         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbHeaderTempHolder", pagedListHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeaderTemp" );
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
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "sbFeedbackHeaderTempService" );
      final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );

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
      sbFeedbackHeaderTempService.getSBHeaderTempVOsByCondition( sbHeaderTempHolder, true );
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
         final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );
         // ���Action Form
         final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) form;

         // ����ѡ�е�ID
         if ( sbHeaderTempVO.getSelectedIds() != null && !sbHeaderTempVO.getSelectedIds().trim().isEmpty() )
         {
            int rows = 0;

            // �ָ�
            for ( String selectedId : sbHeaderTempVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��SBHeaderTempVO
                  final SBHeaderTempVO tempSBHeaderTempVO = sbFeedbackHeaderTempService.getSBHeaderTempVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
                  // ����ɾ���ӿ�
                  tempSBHeaderTempVO.setModifyBy( getUserId( request, response ) );
                  tempSBHeaderTempVO.setModifyDate( new Date() );
                  tempSBHeaderTempVO.setCorpId( getCorpId( request, response ) );
                  rows = rows + sbFeedbackHeaderTempService.updateSBHeaderTemp( tempSBHeaderTempVO );
               }

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

         // ���Selected IDs����Action
         sbHeaderTempVO.setSelectedIds( "" );
         sbHeaderTempVO.setSubAction( "" );
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
         final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );
         // ���Action Form
         final SBHeaderTempVO sbHeaderTempVO = ( SBHeaderTempVO ) form;

         // ����ѡ�е�ID
         if ( sbHeaderTempVO.getSelectedIds() != null && !sbHeaderTempVO.getSelectedIds().trim().isEmpty() )
         {

            // �ָ�
            for ( String selectedId : sbHeaderTempVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ����ɾ���ӿ�
                  sbFeedbackHeaderTempService.deleteSBHeaderTemp( KANUtil.decodeStringFromAjax( selectedId ) );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );

         }

         // ���Selected IDs����Action
         sbHeaderTempVO.setSelectedIds( "" );
         sbHeaderTempVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
