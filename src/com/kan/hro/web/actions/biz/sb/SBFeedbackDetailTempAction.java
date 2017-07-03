package com.kan.hro.web.actions.biz.sb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;
import com.kan.hro.service.inf.biz.sb.SBFeedbackDetailTempService;
import com.kan.hro.service.inf.biz.sb.SBFeedbackHeaderTempService;

public class SBFeedbackDetailTempAction extends BaseAction
{
   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         final SBFeedbackHeaderTempService sbFeedbackHeaderTempService = ( SBFeedbackHeaderTempService ) getService( "sbFeedbackHeaderTempService" );
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         // �����������ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );
         final SBHeaderTempVO sbHeaderTempVO = sbFeedbackHeaderTempService.getSBHeaderTempVOByHeaderId( headerId );
         final String batchId = sbHeaderTempVO.getBatchId();
         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

         // ���ʻ�
         commonBatchVO.reset( null, request );
         sbHeaderTempVO.reset( null, request );
         request.setAttribute( "commonBatchVO", commonBatchVO );
         request.setAttribute( "sbHeaderTempVO", sbHeaderTempVO );

         // ��ʼ����ѯ����SBDetailTempVO
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) form;
         sbDetailTempVO.setHeaderId( headerId );

         if ( new Boolean( ajax ) )
         {
            decodedObject( sbDetailTempVO );
            // �����SubAction��ɾ���б����deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               rollback_objectList( mapping, form, request, response );
            }
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbDetailTempHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbDetailTempHolder.setPage( page );

         // ������������ֶ�
         sbDetailTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         sbDetailTempVO.setAccountId( getAccountId( request, response ) );

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailTempVO.setCorpId( getCorpId( request, response ) );
         }

         // ���û��ָ��������Ĭ�ϰ�����Э����ˮ������
         if ( sbDetailTempVO.getSortColumn() == null || sbDetailTempVO.getSortColumn().isEmpty() )
         {
            sbDetailTempVO.setSortColumn( "detailId" );
            sbDetailTempVO.setSortOrder( "desc" );
         }

         // ���ʻ�
         sbDetailTempVO.reset( null, request );
         request.setAttribute( "sbDetailTempForm", sbDetailTempVO );
         // ���뵱ǰֵ����
         sbDetailTempHolder.setObject( sbDetailTempVO );
         // ����ҳ���¼����
         sbDetailTempHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbFeedbackDetailTempService.getSBDetailTempVOsByCondition( sbDetailTempHolder, true );
         refreshHolder( sbDetailTempHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbDetailTempHolder", sbDetailTempHolder );

         // Ajax
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "listDetailTemp" );
   }

   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         final String detailId = KANUtil.decodeStringFromAjax( request.getParameter( "detailId" ) );
         final SBDetailTempVO sbDetailTempVO = sbFeedbackDetailTempService.getSBDetailTempVOByDetailId( detailId );

         // ����ֵ
         request.setAttribute( "sbDetailTempForm", sbDetailTempVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "handleSBDetailTempPopup" );
   }

   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );

         final String detailId = KANUtil.decodeStringFromAjax( request.getParameter( "detailId" ) );
         final SBDetailTempVO sbDetailTempVO = sbFeedbackDetailTempService.getSBDetailTempVOByDetailId( detailId );

         SBDetailTempVO tempSBSBDetailTempVO = ( SBDetailTempVO ) form;

         sbDetailTempVO.setAmountCompany( tempSBSBDetailTempVO.getAmountCompany() );
         sbDetailTempVO.setAmountPersonal( tempSBSBDetailTempVO.getAmountPersonal() );

         sbFeedbackDetailTempService.updateSBDetailTemp( sbDetailTempVO );
         // ���͸��³ɹ����
         success( request, MESSAGE_TYPE_UPDATE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      ( ( SBDetailTempVO ) form ).setDetailId( "" );
      // ��ת����ѯ
      return list_object( mapping, form, request, response );
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SBFeedbackDetailTempService sbFeedbackDetailTempService = ( SBFeedbackDetailTempService ) getService( "sbFeedbackDetailTempService" );
         // ���Action Form
         final SBDetailTempVO sbDetailTempVO = ( SBDetailTempVO ) form;

         // ����ѡ�е�ID
         if ( sbDetailTempVO.getSelectedIds() != null && !sbDetailTempVO.getSelectedIds().trim().isEmpty() )
         {

            // �ָ�
            for ( String selectedId : sbDetailTempVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ����ɾ���ӿ�
                  sbFeedbackDetailTempService.deleteSBDetailTemp( KANUtil.decodeStringFromAjax( selectedId ) );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );

         }

         // ���Selected IDs����Action
         sbDetailTempVO.setSelectedIds( "" );
         sbDetailTempVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
