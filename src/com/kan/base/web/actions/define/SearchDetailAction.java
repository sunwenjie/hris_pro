package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.kan.base.service.inf.define.SearchDetailService;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SearchDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_SEARCH";

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         //��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;

         // ����ɾ������
         if ( searchDetailVO.getSubAction() != null && searchDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // ��õ�������  
         String searchHeaderId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( searchHeaderId ) == null )
         {
            searchHeaderId = searchDetailVO.getSearchHeaderId();
         }
         else
         {
            searchHeaderId = KANUtil.decodeStringFromAjax( searchHeaderId );
         }

         // ��ȡSerachDetailVO
         final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchHeaderId );
         searchHeaderVO.setSubAction( VIEW_OBJECT );
         searchHeaderVO.reset( null, request );
         // ����Checkbox
         searchHeaderVO.setUseJavaObject( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         // ����request����
         request.setAttribute( "searchHeaderForm", searchHeaderVO );

         // ����SearchHeaderId���ң��õ�SearchDetailVO����
         searchDetailVO.setSearchHeaderId( searchHeaderId );
         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( searchDetailVO.getSortColumn() == null || searchDetailVO.getSortColumn().isEmpty() )
         {
            searchDetailVO.setSortColumn( "columnIndex" );
         }

         //�˴���ҳ����
         final PagedListHolder searchDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         searchDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         searchDetailHolder.setObject( searchDetailVO );
         // ����ҳ���¼����
         searchDetailHolder.setPageSize( listPageSize_large );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         searchDetailService.getSearchDetailVOsByCondition( searchDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( searchDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "searchDetailHolder", searchDetailHolder );

         // ��ʼ���ֶ�
         // ��ʼ���ֶ�MappingVO List
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // ��������JAVA���󣬼���table�ֶ�
         if ( KANUtil.filterEmpty( searchHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( searchHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // ��Ӳ�����ѡ��
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // ��ӿյ�����ѡ�������ѡ��
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         searchDetailVO.setColumns( columns );
         searchDetailVO.setSubAction( "" );
         searchDetailVO.setSearchHeaderId( searchHeaderId );
         searchDetailVO.setColumnIndex( "0" );
         searchDetailVO.setFontSize( "13" );
         searchDetailVO.setUseThinking( SearchDetailVO.FALSE );
         searchDetailVO.setDisplay( SearchDetailVO.TRUE );
         searchDetailVO.setStatus( SearchDetailVO.TRUE );
         searchDetailVO.reset( null, request );

         columnIsExist( searchDetailVO.getColumns(), searchHeaderId, searchDetailService );

         // SearchDetailд��Request����
         request.setAttribute( "searchDetailForm", searchDetailVO );

         // �����AJAX���ã���ֱ�Ӵ�ֵ��table JSPҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listSearchDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSearchDetail" );
   }

   private void columnIsExist( final List< MappingVO > columns, final String searchHeaderId, final SearchDetailService searchDetailService ) throws KANException
   {
      if ( columns != null && columns.size() > 0 )
      {
         SearchDetailVO searchDetailVO = null;
         for ( MappingVO column : columns )
         {
            searchDetailVO = new SearchDetailVO();
            searchDetailVO.setSearchHeaderId( searchHeaderId );
            searchDetailVO.setColumnId( column.getMappingId() );

            final List< Object > objects = searchDetailService.getSearchDetailVOsByCondition( searchDetailVO );
            column.setMappingStatus( ( objects != null && objects.size() > 0 ) ? "2" : "1" );
         }
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

            // ���SearchHeaderId
            final String searchHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��õ�ǰform
            final SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;
            // ��ʼ��SearchDetailVO����
            searchDetailVO.setSearchHeaderId( searchHeaderId );
            searchDetailVO.setCreateBy( getUserId( request, response ) );
            searchDetailVO.setModifyBy( getUserId( request, response ) );
            searchDetailVO.setAccountId( getAccountId( request, response ) );
            searchDetailService.insertSearchDetail( searchDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, searchDetailVO, Operate.ADD, searchDetailVO.getSearchDetailId(), null );
         }

         // ���Action Form
         ( ( SearchDetailVO ) form ).reset();
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
      // no use
      return null;
   }

   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // ��ȡ���� - �����
         final String searchDetailId = KANUtil.decodeString( request.getParameter( "searchDetailId" ) );

         // ��ȡSearchDetailVO����
         final SearchDetailVO searchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( searchDetailId );

         // ��ȡSearchHeaderVO����
         final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchDetailVO.getSearchHeaderId() );

         // ���ʻ���ֵ
         searchDetailVO.reset( null, request );

         // ��ʼ���ֶ�MappingVO List
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // ��������JAVA���󣬼���table�ֶ�
         if ( KANUtil.filterEmpty( searchHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( searchHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // ��Ӳ�����ѡ��
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // ��ӿյ�����ѡ�������ѡ��
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         searchDetailVO.setColumns( columns );

         // ����SubAction
         searchDetailVO.setSubAction( VIEW_OBJECT );

         // д��Request
         request.setAttribute( "searchHeaderForm", searchHeaderVO );
         request.setAttribute( "searchDetailForm", searchDetailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // AJAX������תFORMҳ��
      return mapping.findForward( "manageSearchDetailForm" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

            // ��ȡ���� - �����
            final String searchDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "searchDetailId" ), "UTF-8" ) );
            // ���SearchDetailVO����
            final SearchDetailVO searchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( searchDetailId );
            // װ�ؽ��洫ֵ
            searchDetailVO.update( ( SearchDetailVO ) form );
            // ��ȡ��¼�û�
            searchDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            searchDetailService.updateSearchDetail( searchDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, searchDetailVO, Operate.MODIFY, searchDetailVO.getSearchDetailId(), null );
         }

         // ���Form
         ( ( SearchDetailVO ) form ).reset();
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
   // Code reviewed by Kevin Jin at 2013-07-09
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

         // ��õ�ǰform
         SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;
         // ����ѡ�е�ID
         if ( searchDetailVO.getSelectedIds() != null && !searchDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : searchDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final SearchDetailVO tempSearchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( selectedId );
               tempSearchDetailVO.setModifyBy( getUserId( request, response ) );
               tempSearchDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               searchDetailService.deleteSearchDetail( tempSearchDetailVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            insertlog( request, searchDetailVO, Operate.DELETE, null, searchDetailVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         searchDetailVO.setSelectedIds( "" );
         searchDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
