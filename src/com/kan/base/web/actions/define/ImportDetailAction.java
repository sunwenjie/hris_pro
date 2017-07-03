package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.service.inf.define.ImportDetailService;
import com.kan.base.service.inf.define.ImportHeaderService;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ImportDetailAction extends BaseAction
{

   @Override
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
         final ImportDetailVO importDetailVO = ( ImportDetailVO ) form;
         final boolean isDelete = importDetailVO.getSubAction() != null && importDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // �����Action��ɾ��
         if ( isDelete )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��ImportDetailService�ӿ�
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

         // �������       
         String importHeaderId = request.getParameter( "importHeaderId" );
         if ( KANUtil.filterEmpty( importHeaderId ) == null )
         {
            importHeaderId = importDetailVO.getImportHeaderId();
         }
         else
         {
            importHeaderId = KANUtil.decodeStringFromAjax( importHeaderId );
         }

         // ����������� ImportHeaderVO����
         final ImportHeaderVO importHeaderVO = new ImportHeaderVO();
         importHeaderVO.setImportHeaderId( importHeaderId );
         // ����request����
         request.setAttribute( "importHeaderForm", importHeaderVO );

         // ����������ң��õ�ImportDetailVO����
         importDetailVO.setImportHeaderId( importHeaderId );
         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( importDetailVO.getSortColumn() == null || importDetailVO.getSortColumn().isEmpty() )
         {
            importDetailVO.setSortColumn( "columnIndex" );
         }

         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         // ��ʼ���ֶ�
         final List< MappingVO > columns = tableDTO.getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
         importDetailVO.setColumns( columns );

         // ��ʼ��ColumnVO����
         final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );//columnService.getColumnVOByColumnId( importDetailVO.getColumnId() );
         request.setAttribute( "columnVO", columnVO );

         // ��ӿյ�����ѡ�������ѡ��
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         importDetailVO.reset( mapping, request );
         importDetailVO.setSubAction( CREATE_OBJECT );
         // ����ӱ�Ҫ���˵��Ѿ�ʹ�õ�Column
         if ( KANUtil.filterEmpty( importDTO.getImportHeaderVO().getParentId(), "0" ) == null )
         {
            importDetailVO.setColumns( filterUsedColumns( columns, importDTO.getImportDetailVOs() ) );
         }

         // �˴���ҳ����
         final PagedListHolder importDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         importDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         importDetailHolder.setObject( importDetailVO );
         // ����ҳ���¼����
         importDetailHolder.setPageSize( listPageSize_large );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         importDetailService.getImportDetailVOsByCondition( importDetailHolder, true );
         // ��ʼ���б���ImportDetail��Columns
         for ( Object importDetailVOObject : importDetailHolder.getSource() )
         {
            ( ( ImportDetailVO ) importDetailVOObject ).setColumns( columns );
         }
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( importDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "importDetailHolder", importDetailHolder );

         importDetailVO.setSubAction( "" );
         importDetailVO.setImportHeaderId( importHeaderId );
         importDetailVO.setColumnWidth( "14" );
         importDetailVO.setFontSize( "13" );
         importDetailVO.setIsDecoded( ImportDetailVO.FALSE );
         importDetailVO.setAlign( "1" );
         importDetailVO.setSort( ImportDetailVO.TRUE );
         importDetailVO.setStatus( ImportDetailVO.TRUE );
         // ImportDetailд��Request����
         request.setAttribute( "importDetailForm", importDetailVO );

         if ( isDelete )
         {
            return mapping.findForward( "manageImportSelectColumn" );
         }
         else if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "manageImportDetailTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "manageImportSelectColumn" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {

            //��ʼ��Service �ӿ�
            final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

            // ���ImportHeaderId
            final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

            // ��õ�ǰForm
            final ImportDetailVO importDetailVO = ( ImportDetailVO ) form;

            // ����SubAction
            dealSubAction( importDetailVO, mapping, form, request, response );
            // ��ʼ��ImportDetailVO����
            importDetailVO.setImportHeaderId( importHeaderId );
            importDetailVO.setCreateBy( getUserId( request, response ) );
            importDetailVO.setModifyBy( getUserId( request, response ) );
            importDetailVO.setAccountId( getAccountId( request, response ) );
            importDetailService.insertImportDetail( importDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            importDetailVO.reset();

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, importDetailVO, Operate.ADD, importDetailVO.getImportDetailId(), null );

            list_object( mapping, importDetailVO, request, response );
            this.saveToken( request );

            return mapping.findForward( "manageImportSelectColumn" );
         }
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
      // No Use
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
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         // ��Ҫ�õ�һ��ImportHeader��������Ҫ��ʼ��ImportHeaderService�ӿ�
         final ImportHeaderService importHeaderService = ( ImportHeaderService ) getService( "importHeaderService" );

         // ��ȡ���� - �����
         final String importDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "importDetailId" ) );

         // ��ʼ��ImportDetailVO����
         ImportDetailVO importDetailVO = new ImportDetailVO();
         if ( importDetailId != null && !importDetailId.trim().equals( "" ) )
         {
            importDetailVO = importDetailService.getImportDetailVOByImportDetailId( importDetailId );
         }

         importDetailVO.reset( null, request );

         // ����������� ImportHeaderVO����
         final ImportHeaderVO importHeaderVO = importHeaderService.getImportHeaderVOByImportHeaderId( importDetailVO.getImportHeaderId() );
         request.setAttribute( "importHeaderForm", importHeaderVO );
         if ( importHeaderVO != null )
         {
            final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( importHeaderVO.getTableId() ).getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
            importDetailVO.setColumns( columns );
         }

         importDetailVO.setSubAction( VIEW_OBJECT );
         // д��Request
         request.setAttribute( "importDetailForm", importDetailVO );

         // ��ʼ��ColumnVO����
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( importDetailVO.getColumnId() );
         request.setAttribute( "columnVO", columnVO );

         return mapping.findForward( "manageImportSelectColumnForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

            // ��ȡ���� - �����
            final String importDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "importDetailId" ) );
            // ���ImportDetailVO����
            final ImportDetailVO importDetailVO = importDetailService.getImportDetailVOByImportDetailId( importDetailId );
            // װ�ؽ��洫ֵ
            importDetailVO.update( ( ImportDetailVO ) form );
            // ����SubAction
            dealSubAction( importDetailVO, mapping, form, request, response );
            // ��ȡ��¼�û�
            importDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            importDetailService.updateImportDetail( importDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initImportHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, importDetailVO, Operate.MODIFY, importDetailVO.getImportDetailId(), null );

            importDetailVO.reset();

            list_object( mapping, importDetailVO, request, response );

            return mapping.findForward( "manageImportSelectColumn" );
         }

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
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );

         // ��õ�ǰform
         ImportDetailVO importDetailVO = ( ImportDetailVO ) form;
         // ����ѡ�е�ID
         if ( importDetailVO.getSelectedIds() != null && !importDetailVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, importDetailVO, Operate.DELETE, null, importDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : importDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               importDetailVO = importDetailService.getImportDetailVOByImportDetailId( selectedId );
               importDetailVO.setModifyBy( getUserId( request, response ) );
               importDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               importDetailService.deleteImportDetail( importDetailVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initImportHeader", getAccountId( request, response ) );
         }

         success( request, MESSAGE_TYPE_DELETE );

         // ���Selected IDs����Action
         importDetailVO.setSelectedIds( "" );
         importDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * ѡ���ֶ�change
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward columnId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡcolumnId
         final String columnId = request.getParameter( "columnId" );

         // ���ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // ��ʼ��StringBuffer
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", columnVO.getNameZH() );
            jsonObject.put( "nameEN", columnVO.getNameEN() );
            jsonObject.put( "valueType", columnVO.getValueType() );
         }
         else
         {
            jsonObject.put( "success", "fasle" );
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /***
    * ���˵��Ѿ���ӵ��ֶ�
   *  filterUsedColumns
   *  
   *  @param allColumns
   *  @param usedColumns
   *  @return
    */
   public static List< MappingVO > filterUsedColumns( final List< MappingVO > allColumns, final List< ImportDetailVO > usedColumns )
   {
      List< MappingVO > columns = new ArrayList< MappingVO >();
      if ( allColumns != null )
      {
         if ( usedColumns == null || usedColumns.size() == 0 )
         {
            columns.addAll( allColumns );
         }
         else
         {
            for ( MappingVO columnMappingVO : allColumns )
            {
               boolean isExist = false;
               for ( ImportDetailVO importDetailVO : usedColumns )
               {
                  if ( columnMappingVO.getMappingId().equals( importDetailVO.getColumnId() ) )
                  {
                     isExist = true;
                  }
               }
               if ( !isExist )
               {
                  columns.add( columnMappingVO );
               }
            }
         }
      }
      return columns;
   }

   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );

         // ��ʼ��Service�ӿ�
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         final List< Object > importDetails = importDetailService.getImportDetailVOsByImportHeaderId( importHeaderId );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         if ( importDTO == null )
         {
            return null;
         }
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         StringBuffer result = new StringBuffer();
         // ��ʼ���ֶ�
         final List< MappingVO > columns = tableDTO.getCanImportColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
         for ( int i = 0; i < columns.size(); i++ )
         {
            for ( int j = 0; j < importDetails.size(); j++ )
            {
               // ��ѡ
               if ( columns.get( i ).getMappingId().equals( ( ( ImportDetailVO ) importDetails.get( j ) ).getColumnId() ) )
               {
                  result.append( "<li>" );
                  result.append( "<input type='checkbox' name='selectColumns' checked='checked'  value='" + columns.get( i ).getMappingId() + "'>" );
                  result.append( columns.get( i ).getMappingValue() );
                  result.append( "</li>" );
                  break;
               }
               // ����ѡ
               if ( j == importDetails.size() - 1 )
               {
                  result.append( "<li>" );
                  result.append( "<input type='checkbox' name='selectColumns'  value='" + columns.get( i ).getMappingId() + "'>" );
                  result.append( columns.get( i ).getMappingValue() );
                  result.append( "</li>" );
               }
            }
            if ( importDetails.size() == 0 )
            {
               result.append( "<li>" );
               result.append( "<input type='checkbox' name='selectColumns'  value='" + columns.get( i ).getMappingId() + "'>" );
               result.append( columns.get( i ).getMappingValue() );
               result.append( "</li>" );
            }

         }
         out.print( result.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public ActionForward quickChoose( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         String[] selectColumns = request.getParameterValues( "selectColumns" );
         final String importHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "importHeaderId" ) );
         // ��ʼ��Service�ӿ�
         final ImportDetailService importDetailService = ( ImportDetailService ) getService( "importDetailService" );
         // �޸�detailΪɾ��
         importDetailService.deleteImportDetail( importHeaderId );
         // ��ȡ���е�table DTO
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, BaseAction.getCorpId( request, null ) );
         TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );
         for ( String selectColumn : selectColumns )
         {
            // ��ȡ��ǰcolumn��Ӧ��columnVO
            final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( selectColumn );

            final ImportDetailVO importDetailVO = new ImportDetailVO();

            // ��ʼ��ImportDetailVO����
            importDetailVO.setColumnId( columnVO.getColumnId() );
            importDetailVO.setImportHeaderId( importHeaderId );
            importDetailVO.setNameZH( columnVO.getNameZH() );
            importDetailVO.setNameEN( columnVO.getNameEN() );
            importDetailVO.setColumnWidth( "14" );
            importDetailVO.setColumnIndex( columnVO.getColumnIndex() );
            importDetailVO.setFontSize( "13" );
            importDetailVO.setIsDecoded( "2" );
            importDetailVO.setAlign( "1" );
            importDetailVO.setStatus( "1" );
            importDetailVO.setDescription( columnVO.getDescription() );
            importDetailVO.setCreateBy( getUserId( request, response ) );
            importDetailVO.setModifyBy( getUserId( request, response ) );
            importDetailVO.setAccountId( getAccountId( request, response ) );
            importDetailService.insertImportDetail( importDetailVO );
         }
         // ��ʼ�������־ö���
         constantsInit( "initImportHeader", getAccountId( request, response ) );

         // ������ӳɹ����
         success( request, MESSAGE_TYPE_ADD );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      list_object( mapping, form, request, response );
      return mapping.findForward( "manageImportSelectColumn" );
   }
}