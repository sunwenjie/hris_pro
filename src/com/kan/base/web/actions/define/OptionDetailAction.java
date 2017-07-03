package com.kan.base.web.actions.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionDetailService;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class OptionDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_OPTION";

   @Override
   // Code reviewed by Kevin at 2013-07-02
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
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
         final OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;

         // ���û��ָ��������Ĭ�ϰ� optionIndex����
         if ( optionDetailVO.getSortColumn() == null || optionDetailVO.getSortColumn().isEmpty() )
         {
            optionDetailVO.setSortColumn( "optionIndex" );
            optionDetailVO.setSortOrder( "asc" );
         }

         // ����SubAction
         dealSubAction( optionDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = optionDetailVO.getOptionHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ��ȡOptionHeaderVO
         final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( headerId );
         optionHeaderVO.setSubAction( VIEW_OBJECT );
         optionHeaderVO.reset( null, request );
         // ����request����
         request.setAttribute( "optionHeaderForm", optionHeaderVO );

         // ����OptionHeaderId���Ҳ��õ�OptionDetailVO����
         optionDetailVO.setOptionHeaderId( headerId );

         // �˴���ҳ����
         final PagedListHolder optionDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         optionDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         optionDetailHolder.setObject( optionDetailVO );
         // ����ҳ���¼����
         optionDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         optionDetailService.getOptionDetailVOsByCondition( optionDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( optionDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "optionDetailHolder", optionDetailHolder );
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listOptionDetailTable" );
         }

         ( ( OptionDetailVO ) form ).setSubAction( "" );
         ( ( OptionDetailVO ) form ).setStatus( OptionDetailVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listOptionDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No use
      return null;
   }

   @Override
   // Code reviewed by Kevin at 2013-07-01
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

            // ������optionHeaderId
            final String optionHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���ActionForm
            final OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;
            // ��ȡ��¼�û�
            optionDetailVO.setOptionHeaderId( optionHeaderId );
            optionDetailVO.setAccountId( getAccountId( request, response ) );
            optionDetailVO.setCreateBy( getUserId( request, response ) );
            optionDetailVO.setModifyBy( getUserId( request, response ) );

            String optionId = optionDetailService.getMaxOptionId( optionHeaderId );

            if ( KANUtil.filterEmpty( optionId ) == null )
            {
               optionId = "0";
            }

            //ѡ��ֵ�ۼ�
            optionDetailVO.setOptionId( String.valueOf( ( Integer.parseInt( optionId ) + 1 ) ) );
            optionDetailVO.setOptionValue( optionDetailVO.getOptionId() );
            optionDetailService.insertOptionDetail( optionDetailVO );
            insertlog( request, optionDetailVO, Operate.ADD, optionDetailVO.getOptionDetailId(), null );
            
            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

          
         }

         // ���Form
         ( ( OptionDetailVO ) form ).reset();
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

   // Code reviewed by Kevin at 2013-07-01
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );
         // ������ȡ�����
         final String optionDetailId = KANUtil.decodeString( request.getParameter( "optionDetailId" ) );
         // ���OptionDetailVO����
         final OptionDetailVO optionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( optionDetailId );
         // ���OptionHeaderVO����
         final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( optionDetailVO.getOptionHeaderId() );

         optionDetailVO.reset( null, request );
         optionDetailVO.setSubAction( VIEW_OBJECT );
         // ����request����
         request.setAttribute( "optionHeaderForm", optionHeaderVO );
         request.setAttribute( "optionDetailForm", optionDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageOptionDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin at 2013-07-01  �޸�
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

            // ������ȡ�����
            final String optionDetailId = KANUtil.decodeString( request.getParameter( "optionDetailId" ) );
            // ��ȡ��������
            final OptionDetailVO optionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( optionDetailId );

            ( ( OptionDetailVO ) form ).setOptionId( optionDetailVO.getOptionId() );
            ( ( OptionDetailVO ) form ).setOptionValue( optionDetailVO.getOptionValue() );
            // װ�ؽ��洫ֵ
            optionDetailVO.update( ( OptionDetailVO ) form );
            // ��ȡ��¼�û�
            optionDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            optionDetailService.updateOptionDetail( optionDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, optionDetailVO, Operate.MODIFY, optionDetailVO.getOptionDetailId(), null );
         }

         // ���Form
         ( ( OptionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code review by Kevin Jin at 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OptionDetailService optionDetailService = ( OptionDetailService ) getService( "optionDetailService" );

         // ��õ�ǰform
         OptionDetailVO optionDetailVO = ( OptionDetailVO ) form;
         // ����ѡ�е�ID
         if ( optionDetailVO.getSelectedIds() != null && !optionDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : optionDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final OptionDetailVO tempOptionDetailVO = optionDetailService.getOptionDetailVOByOptionDetailId( selectedId );
               tempOptionDetailVO.setModifyBy( getUserId( request, response ) );
               tempOptionDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               optionDetailService.deleteOptionDetail( tempOptionDetailVO );
            }

            insertlog( request, optionDetailVO, Operate.DELETE, null, optionDetailVO.getSelectedIds() );
            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         optionDetailVO.setSelectedIds( "" );
         optionDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
