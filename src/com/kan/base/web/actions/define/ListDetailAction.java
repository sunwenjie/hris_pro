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

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListDetailService;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ListDetailAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_LIST";

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�AJAX����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final ListDetailVO listDetailVO = ( ListDetailVO ) form;

         // �����Action��ɾ��
         if ( listDetailVO.getSubAction() != null && listDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // �����������
         String listHeaderId = request.getParameter( "listHeaderId" );
         if ( KANUtil.filterEmpty( listHeaderId ) == null )
         {
            listHeaderId = listDetailVO.getListHeaderId();
         }
         else
         {
            listHeaderId = KANUtil.decodeStringFromAjax( listHeaderId );
         }

         // ����������� ListHeaderVO����
         final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listHeaderId );
         listHeaderVO.setSubAction( VIEW_OBJECT );
         listHeaderVO.reset( null, request );
         // ���÷�ҳ�ֶε�Checkbox
         listHeaderVO.setUsePagination( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         listHeaderVO.setUseJavaObject( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         // ����request����
         request.setAttribute( "listHeaderForm", listHeaderVO );

         // ����������ң��õ�ListDetailVO����
         listDetailVO.setListHeaderId( listHeaderId );
         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( listDetailVO.getSortColumn() == null || listDetailVO.getSortColumn().isEmpty() )
         {
            listDetailVO.setSortColumn( "columnIndex" );
         }

         // �˴���ҳ����
         final PagedListHolder listDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         listDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         listDetailHolder.setObject( listDetailVO );
         // ����ҳ���¼����
         listDetailHolder.setPageSize( listPageSize_large );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         listDetailService.getListDetailVOsByCondition( listDetailHolder, true );

         // ��ʼ���ֶ�
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // ����table�ֶ�
         if ( KANUtil.filterEmpty( listHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( listHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // ��Ӳ�����ѡ��
            columns.add( 0, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // ��ӿյ�����ѡ�������ѡ��
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         listDetailVO.setColumns( columns );
         listDetailVO.setSubAction( "" );
         listDetailVO.setListHeaderId( listHeaderId );
         listDetailVO.setColumnWidthType( "1" );
         listDetailVO.setFontSize( "13" );
         listDetailVO.setIsDecoded( ListDetailVO.FALSE );
         listDetailVO.setIsLinked( ListDetailVO.FALSE );
         listDetailVO.setAlign( "1" );
         listDetailVO.setSort( ListDetailVO.TRUE );
         listDetailVO.setDisplay( ListDetailVO.TRUE );
         listDetailVO.setStatus( ListDetailVO.TRUE );

         listDetailVO.reset( null, request );

         columnIsExist( listDetailVO.getColumns(), listHeaderId, listDetailService );

         // ListDetailд��Request����
         request.setAttribute( "listDetailForm", listDetailVO );

         // ��ʼ���б���ListDetail��Columns
         for ( Object listDetailVOObject : listDetailHolder.getSource() )
         {
            ( ( ListDetailVO ) listDetailVOObject ).setColumns( columns );
         }
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( listDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "listDetailHolder", listDetailHolder );

         // �����AJAX���ã���ֱ�Ӵ�ֵ��table JSPҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listListDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listListDetail" );
   }

   private void columnIsExist( final List< MappingVO > columns, final String listHeaderId, final ListDetailService listDetailService ) throws KANException
   {
      if ( columns != null && columns.size() > 0 )
      {
         ListDetailVO listDetailVO = null;
         for ( MappingVO column : columns )
         {
            listDetailVO = new ListDetailVO();
            listDetailVO.setListHeaderId( listHeaderId );
            listDetailVO.setColumnId( column.getMappingId() );

            final List< Object > objects = listDetailService.getListDetailVOsByCondition( listDetailVO );
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
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            //��ʼ��Service �ӿ�
            final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

            // ���ListHeaderId
            final String listHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listHeaderId" ), "UTF-8" ) );
            // ��õ�ǰForm
            final ListDetailVO listDetailVO = ( ListDetailVO ) form;
            // ��ʼ��ListDetailVO����
            listDetailVO.setListHeaderId( listHeaderId );
            listDetailVO.setCreateBy( getUserId( request, response ) );
            listDetailVO.setModifyBy( getUserId( request, response ) );
            listDetailVO.setAccountId( getAccountId( request, response ) );
            listDetailService.insertListDetail( listDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, listDetailVO, Operate.ADD, listDetailVO.getListDetailId(), null );
         }

         // ���Action Form
         ( ( ListDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // ��ȡ���� - �����
         final String listDetailId = KANUtil.decodeString( request.getParameter( "listDetailId" ) );

         // ��ȡListDetailVO
         final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );

         // ��ȡListHeaderVO
         final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listDetailVO.getListHeaderId() );

         // ���ʻ���ֵ
         listDetailVO.reset( null, request );

         // ��ʼ���ֶ�
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // ����table�ֶ�
         if ( KANUtil.filterEmpty( listHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( listHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // ��Ӳ�����ѡ��
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // ��ӿյ�����ѡ�������ѡ��
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         listDetailVO.setColumns( columns );

         // ����SubAction
         listDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "listDetailForm", listDetailVO );
         request.setAttribute( "listHeaderForm", listHeaderVO );

         // AJAX������תFORMҳ��
         return mapping.findForward( "manageListDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

            // ��ȡ���� - �����
            final String listDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listDetailId" ), "UTF-8" ) );
            // ���ListDetailVO����
            final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );
            // װ�ؽ��洫ֵ
            listDetailVO.update( ( ListDetailVO ) form );
            // ��ȡ��¼�û�
            listDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            listDetailService.updateListDetail( listDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, listDetailVO, Operate.MODIFY, listDetailVO.getListDetailId(), null );
         }

         // ���Form
         ( ( ListDetailVO ) form ).reset();
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
   // Code reviewed by Kevin Jin at 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

         // ��õ�ǰform
         ListDetailVO listDetailVO = ( ListDetailVO ) form;
         // ����ѡ�е�ID
         if ( listDetailVO.getSelectedIds() != null && !listDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : listDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final ListDetailVO tempListDetailVO = listDetailService.getListDetailVOByListDetailId( selectedId );
               tempListDetailVO.setModifyBy( getUserId( request, response ) );
               tempListDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               listDetailService.deleteListDetail( tempListDetailVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            insertlog( request, listDetailVO, Operate.DELETE, null, listDetailVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         listDetailVO.setSelectedIds( "" );
         listDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��������ListDetailVO��columnIndex
   /* Add by siuvan @2014-7-24 */
   public void quick_column_index( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         // ��ȡ�µ��б�˳��
         final String newColumnIndex = request.getParameter( "newColumnIndex" );

         // ��������籣�������������б�
         int addIndex = 0;

         if ( KANUtil.filterEmpty( newColumnIndex ) != null )
         {
            final String[] newIndexArray = newColumnIndex.split( "," );
            if ( newIndexArray != null && newIndexArray.length > 0 )
            {
               for ( String s : newIndexArray )
               {
                  if ( s.split( "_" ).length == 2 )
                  {
                     String listDetailId = s.split( "_" )[ 0 ];
                     String columnIndex = s.split( "_" )[ 1 ];
                     final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );
                     if ( listDetailVO != null )
                     {
                        listDetailVO.setColumnIndex( listDetailVO.isSBItem() ? columnIndex : String.valueOf( Integer.valueOf( columnIndex ) + addIndex ) );
                        listDetailVO.setModifyBy( getUserId( request, null ) );
                        listDetailVO.setModifyDate( new Date() );

                        listDetailService.updateListDetail( listDetailVO );

                        // ������籣��Ŀ����ͬʱ�޸Ķ�Ӧ�����ˡ���columnIndex
                        if ( listDetailVO.isSBItem() )
                        {
                           addIndex = 20;

                           final ListDetailVO searchListDetailVO = new ListDetailVO();
                           searchListDetailVO.setListHeaderId( listDetailVO.getListHeaderId() );
                           searchListDetailVO.setPropertyName( listDetailVO.getPropertyName().replace( "c", "p" ) );
                           searchListDetailVO.setStatus( BaseVO.TRUE );

                           final List< Object > listDetailVOs = listDetailService.getListDetailVOsByCondition( searchListDetailVO );
                           if ( listDetailVOs != null && listDetailVOs.size() > 0 )
                           {
                              for ( Object vo : listDetailVOs )
                              {
                                 ( ( ListDetailVO ) vo ).setColumnIndex( String.valueOf( Integer.valueOf( columnIndex ) + addIndex ) );
                                 ( ( ListDetailVO ) vo ).setModifyBy( getUserId( request, null ) );
                                 ( ( ListDetailVO ) vo ).setModifyDate( new Date() );

                                 listDetailService.updateListDetail( ( ( ListDetailVO ) vo ) );
                              }
                           }
                        }
                     }
                  }
               }
            }

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}