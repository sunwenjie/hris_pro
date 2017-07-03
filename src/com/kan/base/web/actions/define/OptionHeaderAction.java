package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.OptionDetailVO;
import com.kan.base.domain.define.OptionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.OptionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class OptionHeaderAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_OPTION";

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;

         // ����SubAction
         dealSubAction( optionHeaderVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder optionHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         optionHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         optionHeaderHolder.setObject( optionHeaderVO );
         // ����ҳ���¼����
         optionHeaderHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         optionHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         optionHeaderService.getOptionHeaderVOsByCondition( optionHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( optionHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "optionHeaderHolder", optionHeaderHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listOptionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listOptionHeader" );
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action��Ĭ��״̬Ϊ����
      ( ( OptionHeaderVO ) form ).setStatus( OptionHeaderVO.TRUE );
      ( ( OptionHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "managerOptionHeader" );
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��OptionDetailVO
         final OptionDetailVO optionDetailVO = new OptionDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

            // ��õ�ǰFORM
            final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;
            optionHeaderVO.setAccountId( getAccountId( request, response ) );
            optionHeaderVO.setCreateBy( getUserId( request, response ) );
            optionHeaderVO.setModifyBy( getUserId( request, response ) );
            optionHeaderService.insertOptionHeader( optionHeaderVO );

            optionDetailVO.setOptionHeaderId( optionHeaderVO.getOptionHeaderId() );
            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // ���ر���ɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, optionHeaderVO, Operate.ADD, optionHeaderVO.getOptionHeaderId(), null );
         }
         else
         {
            // ���Form����
            ( ( OptionHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // ��ת���б����
            return list_object( mapping, form, request, response );
         }

         return new OptionDetailAction().list_object( mapping, optionDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

            // �������
            final String optionHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            final OptionHeaderVO optionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( optionHeaderId );

            // װ�ؽ��洫ֵ
            optionHeaderVO.update( ( OptionHeaderVO ) form );
            // ��ȡ��¼�û�
            optionHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            optionHeaderService.updateOptionHeader( optionHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, optionHeaderVO, Operate.MODIFY, optionHeaderVO.getOptionHeaderId(), null );
         }

         // ���ActionForm
         ( ( OptionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new OptionDetailAction().list_object( mapping, new OptionDetailVO(), request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code review by Kevin Jin at 2013-07-01
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final OptionHeaderService optionHeaderService = ( OptionHeaderService ) getService( "optionHeaderService" );

         // ���Action Form
         OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) form;

         // ����ѡ�е�ID
         if ( optionHeaderVO.getSelectedIds() != null && !optionHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : optionHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final OptionHeaderVO tempOptionHeaderVO = optionHeaderService.getOptionHeaderVOByOptionHeaderId( selectedId );
               tempOptionHeaderVO.setModifyBy( getUserId( request, response ) );
               tempOptionHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               optionHeaderService.deleteOptionHeader( tempOptionHeaderVO );
            }

            insertlog( request, optionHeaderVO, Operate.DELETE, null, optionHeaderVO.getSelectedIds() );
            // ��ʼ�������־ö���
            constantsInit( "initColumnOption", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         optionHeaderVO.setSelectedIds( "" );
         optionHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
