package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.LanguageVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.LanguageService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class LanguageAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_LANGUAGES";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );
         // ���Action Form
         final LanguageVO languageVO = ( LanguageVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         languageVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( languageVO.getSubAction() != null && languageVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder languageHolder = new PagedListHolder();
         // ���뵱ǰҳ
         languageHolder.setPage( page );
         // ���뵱ǰֵ����
         languageHolder.setObject( languageVO );
         // ����ҳ���¼����
         languageHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         languageService.getLanguageVOsByCondition( languageHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( languageHolder, request );
         // Holder��д��Request����
         request.setAttribute( "languageHolder", languageHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listLanguageTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listLanguage" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      // ����Sub Action
      ( ( LanguageVO ) form ).setStatus( LanguageVO.TRUE );
      ( ( LanguageVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageLanguage" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final LanguageService languageService = ( LanguageService ) getService( "languageService" );

            // ��õ�ǰFORM
            final LanguageVO languageVO = ( LanguageVO ) form;
            languageVO.setCreateBy( getUserId( request, response ) );
            languageVO.setModifyBy( getUserId( request, response ) );
            languageVO.setAccountId( getAccountId( request, response ) );
            languageService.insertLanguage( languageVO );

            // ��ʼ�������־ö���
            constantsInit( "initLanguage", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, languageVO, Operate.ADD, languageVO.getLanguageId(), null );
         }
         else
         {
            // ���FORM
            ( ( LanguageVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ���Action Form
         ( ( LanguageVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );
         // ������ȡ�����
         String languageId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( languageId ) == null )
         {
            languageId = ( ( LanguageVO ) form ).getLanguageId();
         }
         // ���LanguageVO����
         LanguageVO languageVO = languageService.getLanguageVOByLanguageId( languageId );
         languageVO.reset( null, request );
         // ����Add��Update
         languageVO.setSubAction( VIEW_OBJECT );
         // ��LanguageVO����request����
         request.setAttribute( "languageForm", languageVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageLanguage" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final LanguageService languageService = ( LanguageService ) getService( "languageService" );

            // ������ȡ�����
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ��ȡLanguageVO����
            final LanguageVO languageVO = languageService.getLanguageVOByLanguageId( entityId );
            // װ�ؽ��洫ֵ
            languageVO.update( ( LanguageVO ) form );
            // ��ȡ��¼�û�
            languageVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            languageService.updateLanguage( languageVO );

            // ��ʼ�������־ö���
            constantsInit( "initLanguage", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, languageVO, Operate.MODIFY, languageVO.getLanguageId(), null );
         }

         // ���Action Form
         ( ( LanguageVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return to_objectModify( mapping, form, request, response );
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
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );

         // ���Action Form
         final LanguageVO languageVO = ( LanguageVO ) form;
         // ����ѡ�е�ID
         if ( languageVO.getSelectedIds() != null && !languageVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : languageVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               languageVO.setLanguageId( selectedId );
               languageVO.setModifyBy( getUserId( request, response ) );
               languageVO.setAccountId( getAccountId( request, response ) );
               languageService.deleteLanguage( languageVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initLanguage", getAccountId( request, response ) );
            insertlog( request, languageVO, Operate.DELETE, null, languageVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         languageVO.setSelectedIds( "" );
         languageVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
