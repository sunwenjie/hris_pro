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
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SearchHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_SEARCH";

   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ��TableId
         final String tableId = request.getParameter( "tableId" );
         // ����TableId��ʼ������ѡ��
         final List< MappingVO > mappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSearchHeadersByTableId( tableId, KANUtil.filterEmpty( getCorpId( request, null ) ), request.getLocale().getLanguage() );

         if ( mappingVOs != null )
         {
            mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "searchId", "" ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

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
         final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;
         // ����subAction
         dealSubAction( searchHeaderVO, mapping, form, request, response );
         // ��ʼ��Service�ӿ�
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder searchHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         searchHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         searchHeaderHolder.setObject( searchHeaderVO );
         // ����ҳ���¼����
         searchHeaderHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         searchHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         searchHeaderService.getSearchHeaderVOsByCondition( searchHeaderHolder, true );
         refreshHolder( searchHeaderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "searchHeaderHolder", searchHeaderHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listSearchHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listSearchHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( SearchHeaderVO ) form ).setStatus( SearchHeaderVO.TRUE );
      ( ( SearchHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageSearchHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��SearchDetailVO
         final SearchDetailVO searchDetailVO = new SearchDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

            // ��õ�ǰFORM
            final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;

            // Checkbox����
            if ( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            searchHeaderVO.setCreateBy( getUserId( request, response ) );
            searchHeaderVO.setModifyBy( getUserId( request, response ) );
            searchHeaderVO.setAccountId( getAccountId( request, response ) );
            searchHeaderService.insertSearchHeader( searchHeaderVO );

            searchDetailVO.setSearchHeaderId( searchHeaderVO.getSearchHeaderId() );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, searchHeaderVO, Operate.ADD, searchHeaderVO.getSearchHeaderId(), null );
         }
         else
         {
            // ���form
            ( ( SearchHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new SearchDetailAction().list_object( mapping, searchDetailVO, request, response );
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
   // Code reviewed by Kevin at 2013-07-09
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

            // ��ȡ���� - �����
            final String searchHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���SearchHeaderVO����
            final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchHeaderId );
            // װ�ؽ��洫ֵ
            searchHeaderVO.update( ( SearchHeaderVO ) form );

            // Checkbox����
            if ( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // ��ȡ��¼�û�
            searchHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            searchHeaderService.updateSearchHeader( searchHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, searchHeaderVO, Operate.MODIFY, searchHeaderVO.getSearchHeaderId(), null );
         }

         // ���Action Form
         ( ( SearchHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new SearchDetailAction().list_object( mapping, new SearchDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // ���Action Form
         final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;

         // ����ѡ�е�ID
         if ( searchHeaderVO.getSelectedIds() != null && !searchHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : searchHeaderVO.getSelectedIds().split( "," ) )
            {
               // ����ID��ȡ��Ӧ��searchHeaderVO
               final SearchHeaderVO searchHeaderVOForDel = searchHeaderService.getSearchHeaderVOBySearchHeaderId( selectedId );
               searchHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               searchHeaderVOForDel.setModifyDate( new Date() );
               searchHeaderService.deleteSearchHeader( searchHeaderVOForDel );
            }

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            insertlog( request, searchHeaderVO, Operate.DELETE, null, searchHeaderVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         searchHeaderVO.setSelectedIds( "" );
         searchHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
