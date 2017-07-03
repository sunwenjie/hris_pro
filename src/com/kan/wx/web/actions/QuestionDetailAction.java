package com.kan.wx.web.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.QuestionDetailService;
import com.kan.wx.service.inf.QuestionHeaderService;

public class QuestionDetailAction extends BaseAction
{
   public final static String accessAction = "HRO_QUESITION_DETAIL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;

         // ���û��ָ��������Ĭ�ϰ� QuestionIndex����
         if ( questionDetailVO.getSortColumn() == null || questionDetailVO.getSortColumn().isEmpty() )
         {
            questionDetailVO.setSortColumn( "optionIndex" );
            questionDetailVO.setSortOrder( "asc" );
         }

         // ����SubAction
         dealSubAction( questionDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );

         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = questionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ��ȡQuestionHeaderVO
         final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( headerId );
         questionHeaderVO.setSubAction( VIEW_OBJECT );
         questionHeaderVO.reset( null, request );
         // ����request����
         request.setAttribute( "questionHeaderForm", questionHeaderVO );

         questionDetailVO.setHeaderId( headerId );
         // �˴���ҳ����
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( questionDetailVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         questionDetailService.getQuestionDetailVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "questionDetailHolder", pagedListHolder );
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listQuestionDetailTable" );
         }

         ( ( QuestionDetailVO ) form ).setSubAction( "" );
         ( ( QuestionDetailVO ) form ).setStatus( QuestionDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listQuestionDetail" );
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
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

            // ������headerId
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���ActionForm
            final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;
            // ��ȡ��¼�û�
            questionDetailVO.setHeaderId( headerId );
            questionDetailVO.setAccountId( getAccountId( request, response ) );
            questionDetailVO.setCreateBy( getUserId( request, response ) );
            questionDetailVO.setModifyBy( getUserId( request, response ) );

            String optionIndex = questionDetailService.getMaxOptionIndexByHeaderId( headerId );
            if ( KANUtil.filterEmpty( optionIndex ) == null )
            {
               optionIndex = "0";
            }

            //ѡ��ֵ�ۼ�
            questionDetailVO.setOptionIndex( String.valueOf( ( Integer.parseInt( optionIndex ) + 1 ) ) );
            questionDetailService.insertQuestionDetail( questionDetailVO );
            insertlog( request, questionDetailVO, Operate.ADD, questionDetailVO.getDetailId(), null );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( QuestionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );
         final QuestionHeaderService questionHeaderService = ( QuestionHeaderService ) getService( "questionHeaderService" );
         // ������ȡ�����
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ���QuestionDetailVO����
         final QuestionDetailVO questionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( detailId );
         // ���QuestionHeaderVO����
         final QuestionHeaderVO questionHeaderVO = questionHeaderService.getQuestionHeaderVOByHeaderId( questionDetailVO.getHeaderId() );

         questionDetailVO.reset( null, request );
         questionDetailVO.setSubAction( VIEW_OBJECT );
         // ����request����
         request.setAttribute( "questionHeaderForm", questionHeaderVO );
         request.setAttribute( "questionDetailForm", questionDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "managerQuestionDetail" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡ��������
            final QuestionDetailVO questionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( detailId );

            ( ( QuestionDetailVO ) form ).setOptionIndex( questionDetailVO.getOptionIndex() );
            // װ�ؽ��洫ֵ
            questionDetailVO.update( ( QuestionDetailVO ) form );
            // ��ȡ��¼�û�
            questionDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            questionDetailService.updateQuestionDetail( questionDetailVO );
            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, questionDetailVO, Operate.MODIFY, questionDetailVO.getDetailId(), null );
         }

         // ���Form
         ( ( QuestionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final QuestionDetailService questionDetailService = ( QuestionDetailService ) getService( "questionDetailService" );

         // ��õ�ǰform
         QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) form;
         // ����ѡ�е�ID
         if ( questionDetailVO.getSelectedIds() != null && !questionDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : questionDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final QuestionDetailVO tempQuestionDetailVO = questionDetailService.getQuestionDetailVOByDetailId( selectedId );
               tempQuestionDetailVO.setModifyBy( getUserId( request, response ) );
               tempQuestionDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               questionDetailService.deleteQuestionDetail( tempQuestionDetailVO );
            }

            insertlog( request, questionDetailVO, Operate.DELETE, null, questionDetailVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         questionDetailVO.setSelectedIds( "" );
         questionDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
