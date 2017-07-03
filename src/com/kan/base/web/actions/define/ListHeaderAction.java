package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.ListRender;

public class ListHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_LIST";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;
         // ����subAction
         dealSubAction( listHeaderVO, mapping, form, request, response );
         // ��ʼ��Service�ӿ�
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder listHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         listHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         listHeaderHolder.setObject( listHeaderVO );
         // ����ҳ���¼����
         listHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         listHeaderService.getListHeaderVOsByCondition( listHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( listHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "listHeaderHolder", listHeaderHolder );
         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listListHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listListHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // �ҵ���ǰAccountId������TableDTO
      final List< TableDTO > tableDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).TABLE_DTO;

      // ��ϵͳ�����Table�Ƚϣ����Table�Ѵ�����List�����Ƴ���
      if ( ( ( ListHeaderVO ) form ).getTables() != null && ( ( ListHeaderVO ) form ).getTables().size() > 0 && tableDTOs != null && tableDTOs.size() > 0 )
      {
         for ( int i = ( ( ListHeaderVO ) form ).getTables().size() - 1; i > 0; i-- )
         {
            for ( TableDTO tableDTO : tableDTOs )
            {
               // ��ʼ��ListDTO
               final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getTableId() != null
                     && ( ( ListHeaderVO ) form ).getTables().get( i ).getMappingId().equals( listDTO.getListHeaderVO().getTableId() ) )
               {
                  if ( !"118".equals( listDTO.getListHeaderVO().getTableId() ) )
                  {
                     ( ( ListHeaderVO ) form ).getTables().remove( i );
                  }

                  break;
               }
            }
         }
      }

      final List< MappingVO > parents = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getLists( getLocale( request ).getLanguage(), true, getCorpId( request, null ) );
      if ( parents != null )
      {
         parents.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      ( ( ListHeaderVO ) form ).setParents( parents );
      // ���ó�ʼ���ֶ�
      ( ( ListHeaderVO ) form ).setStatus( ListHeaderVO.TRUE );
      ( ( ListHeaderVO ) form ).setIsSearchFirst( ListHeaderVO.FALSE );
      ( ( ListHeaderVO ) form ).setUsePagination( "on" );
      ( ( ListHeaderVO ) form ).setPageSize( String.valueOf( this.listPageSize ) );
      ( ( ListHeaderVO ) form ).setLoadPages( String.valueOf( this.loadPages ) );
      ( ( ListHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageListHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ�� ListDetailVO
         final ListDetailVO listDetailVO = new ListDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

            // ��õ�ǰFORM
            final ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;

            // Checkbox����
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUsePagination( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUsePagination( ListHeaderVO.FALSE );
            }

            if ( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // ��ȡ��¼�û����˻�
            listHeaderVO.setCreateBy( getUserId( request, response ) );
            listHeaderVO.setModifyBy( getUserId( request, response ) );
            listHeaderVO.setAccountId( getAccountId( request, response ) );
            listHeaderService.insertListHeader( listHeaderVO );

            listDetailVO.setListHeaderId( listHeaderVO.getListHeaderId() );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, listHeaderVO, Operate.ADD, listHeaderVO.getListHeaderId(), null );
         }
         else
         {
            // ���form
            ( ( ListHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new ListDetailAction().list_object( mapping, listDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

            // �������
            final String listHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listHeaderId" ), "UTF-8" ) );
            // ���ListHeaderVO����
            final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listHeaderId );

            // װ�ؽ��洫ֵ
            listHeaderVO.update( ( ListHeaderVO ) form );
            // Checkbox����
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUsePagination( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUsePagination( ListHeaderVO.FALSE );
            }

            if ( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // ��ȡ��¼�û�
            listHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            listHeaderService.updateListHeader( listHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, listHeaderVO, Operate.MODIFY, listHeaderVO.getListHeaderId(), null );
         }

         // ���Form
         ( ( ListHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new ListDetailAction().list_object( mapping, new ListDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // ���Action Form
         ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;

         // ����ѡ�е�ID
         if ( listHeaderVO.getSelectedIds() != null && !listHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : listHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final ListHeaderVO tempListHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( selectedId );
               // ����ɾ���ӿ�
               tempListHeaderVO.setModifyBy( getUserId( request, response ) );
               tempListHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               listHeaderService.deleteListHeader( tempListHeaderVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            insertlog( request, listHeaderVO, Operate.DELETE, null, listHeaderVO.getListHeaderId() );
         }

         // ���Selected IDs����Action
         listHeaderVO.setSelectedIds( "" );
         listHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void load_popup_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡlistHeaderId
         final String listHeaderId = request.getParameter( "listHeaderId" );
         // ��ȡListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( listHeaderId );

         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( listDTO != null )
         {
            rs.append( ListRender.generateQuickColumnIndexPopup( request, listHeaderId, "listHeaderAction.do?proc=list_object" ) );
         }

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
