package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ReportDetailService;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ReportDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ���Action Form
         final ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;

         // �����Action��ɾ��
         if ( reportDetailVO.getSubAction() != null && reportDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��ReportDetailService�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // ��������������������������ã���form��ȡ������ajax��������Σ�
         String headerId = request.getParameter( "reportHeaderId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( ReportDetailVO ) form ).getReportHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ���ReportHeaderVO
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
         reportHeaderVO.reset( null, request );
         reportHeaderVO.setSubAction( VIEW_OBJECT );
         // ���÷�ҳ�ֶε�Checkbox
         reportHeaderVO.setUsePagination( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equals( ReportHeaderVO.TRUE ) ? "on" : "" );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ����������ң��õ�ReportDetailVO����
         reportDetailVO.setReportHeaderId( headerId );
         if ( KANUtil.filterEmpty( reportDetailVO.getSortColumn() ) == null )
         {
            reportDetailVO.setSortColumn( "columnIndex" );
         }
         // �˴���ҳ����
         final PagedListHolder reportDetailPagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         reportDetailPagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         reportDetailPagedListHolder.setObject( reportDetailVO );
         // ����ҳ���¼����
         reportDetailPagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         reportDetailService.getReportDetailVOsByCondition( reportDetailPagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( reportDetailPagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "reportDetailPagedListHolder", reportDetailPagedListHolder );

         // Ajax Table���ã�ֱ�Ӵ���manageReportSelectColumn.jsp
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "manageReportDetail" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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
            // ��ʼ��Service �ӿ�
            final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

            // ���headerId
            final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );
            // ��õ�ǰform
            final ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;
            reportDetailVO.setReportHeaderId( headerId );
            // ����SubAction
            dealSubAction( reportDetailVO, mapping, form, request, response );
            reportDetailVO.setCreateBy( getUserId( request, response ) );
            reportDetailVO.setModifyBy( getUserId( request, response ) );
            reportDetailService.insertReportDetail( reportDetailVO );

            // ���������ɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, reportDetailVO, Operate.ADD, reportDetailVO.getReportDetailId(), null );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );

            this.saveToken( request );
         }

         // ���Form
         ( ( ReportDetailVO ) form ).reset();
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
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // ����detailId
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���ReportDetailVO����
         final ReportDetailVO reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( detailId );

         // ���ReportHeaderVO����
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportDetailVO.getReportHeaderId() );

         // ���ʻ���ֵ
         reportDetailVO.reset( null, request );

         // ��ȡtableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // ����columns
         if ( tableDTO != null )
         {
            // ���ColumnVO MappingVO��ʽ�б�
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               reportDetailVO.getColumns().addAll( columnMappingVOs );
            }
         }

         // �����ֶ�
         if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
         {
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );

            if ( jsonObject.get( reportDetailVO.getColumnId() ) != null )
            {
               reportDetailVO.getStatisticsIndex( String.valueOf( jsonObject.get( reportDetailVO.getColumnId() ) ) );
            }
         }

         // �����޸����
         reportDetailVO.setSubAction( VIEW_OBJECT );
         // ����request����
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
         request.setAttribute( "reportDetailForm", reportDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageReportDetailForm" );
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
            final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

            // ������ȡ�����
            final String reportDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "reportDetailId" ) );
            // ��õ�ǰform
            final ReportDetailVO reportDetailForm = ( ReportDetailVO ) form;
            // ����SubAction
            dealSubAction( reportDetailForm, mapping, form, request, response );

            // ���ReportDetailVO����
            final ReportDetailVO reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( reportDetailId );
            // װ�ؽ��洫ֵ
            reportDetailVO.update( reportDetailForm );
            // ��ȡ��¼�û�
            reportDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            reportDetailService.updateReportDetail( reportDetailVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, reportDetailVO, Operate.MODIFY, reportDetailVO.getReportDetailId(), null );

            this.saveToken( request );

            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
         }
         // ���Form
         ( ( ReportDetailVO ) form ).reset();
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
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );

         // ��õ�ǰform
         ReportDetailVO reportDetailVO = ( ReportDetailVO ) form;
         final String selectedIds = reportDetailVO.getSelectedIds();
         if ( selectedIds != null && !selectedIds.equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : selectedIds.split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ʵ����VendorContactVO��ɾ��
                  reportDetailVO = reportDetailService.getReportDetailVOByReportDetailId( selectedId );
                  // ����ɾ���ӿ�
                  reportDetailVO.setModifyBy( getUserId( request, response ) );
                  reportDetailVO.setModifyDate( new Date() );
                  reportDetailService.deleteReportDetail( reportDetailVO );
               }
            }

            insertlog( request, reportDetailVO, Operate.DELETE, null, selectedIds );
            // ��ʼ�������־ö���
            constantsInit( "initTable", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         reportDetailVO.setSelectedIds( "" );
         reportDetailVO.setSubAction( "" );

         // ����ɾ���ɹ����
         success( request, MESSAGE_TYPE_DELETE, null, MESSAGE_DETAIL );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * �����Ч��ѡ���ֶ� -����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_available_options_ajax_bak( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡheaderId
         final String headerId = KANUtil.decodeString( request.getParameter( "reportHeaderId" ) );

         // ��ȡtableId
         final String tableId = request.getParameter( "tableId" );

         // 1��ѡ���ֶΣ�2�������ֶ�
         final String flag = request.getParameter( "flag" );

         // ��ʼ��Service�ӿ�
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );
         // final ReportSearchDetailService reportSearchDetailService = (
         // ReportSearchDetailService ) getService(
         // "reportSearchDetailService" );

         // ��ʼ��ReportDetailVO�б�
         List< Object > reportDetailVOs = null;

         // ��ʼ��ReportSearchDetailVO�б�
         List< Object > reportSearchDetailVOs = null;

         if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "1" ) )
         {
            reportDetailVOs = reportDetailService.getReportDetailVOsByReportHeaderId( headerId );
         }
         else if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "2" ) )
         {
            // reportSearchDetailVOs =
            // reportSearchDetailService.getReportSearchDetailVOsByReportHeaderId(
            // headerId );
         }

         // ��ʼ��tempColumnMappingVOs
         final List< MappingVO > tempColumnMappingVOs = new ArrayList< MappingVO >();
         tempColumnMappingVOs.add( 0, ( ( ReportDetailVO ) form ).getEmptyMappingVO() );

         // ��ȡtableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         if ( tableDTO != null )
         {
            // ���ColumnVO MappingVO��ʽ�б�
            final List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

            final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

            // �˵��Ѿ����ڵ��ֶ�
            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               for ( MappingVO tempColumnMappingVO : columnMappingVOs )
               {
                  // ����Ƿ����
                  boolean exist = false;

                  // ��ȡColumnVO
                  final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( tempColumnMappingVO.getMappingId() );

                  // �ֶα���Ϊ���ݿ����л����Զ����ֶ�
                  if ( columnVO == null || KANUtil.filterEmpty( columnVO.getAccountId() ) == null || KANUtil.filterEmpty( columnVO.getIsDBColumn() ) == null
                        || columnVO.getIsDBColumn().trim().equals( BaseVO.FALSE ) )
                  {
                     continue;
                  }

                  if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID )
                        || ( !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && ( ( corpId == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( corpId != null && corpId.equals( columnVO.getCorpId() ) ) ) ) )
                  {
                     // ѡ���ֶ�
                     if ( flag.equals( "1" ) && reportDetailVOs != null && reportDetailVOs.size() > 0 )
                     {
                        for ( Object reportDetailVOObject : reportDetailVOs )
                        {
                           if ( ( ( ReportDetailVO ) reportDetailVOObject ).getColumnId().equals( tempColumnMappingVO.getMappingId() ) )
                           {
                              exist = true;
                              break;
                           }
                        }
                     }
                     // �����ֶ�
                     else if ( flag.equals( "2" ) && reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
                     {
                        for ( Object reportSearchDetailVOObject : reportSearchDetailVOs )
                        {
                           if ( ( ( ReportSearchDetailVO ) reportSearchDetailVOObject ).getColumnId().equals( tempColumnMappingVO.getMappingId() ) )
                           {
                              exist = true;
                              break;
                           }
                        }
                     }

                     if ( !exist )
                     {
                        tempColumnMappingVOs.add( tempColumnMappingVO );
                     }
                  }
               }
            }
         }

         // Send to client
         out.println( KANUtil.getSelectHTML( tempColumnMappingVOs, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * �����Ч��ѡ���ֶ�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // siuvan updated to 2014-10-13
   public ActionForward list_available_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡheaderId
         final String reportHeaderId = KANUtil.decodeString( request.getParameter( "reportHeaderId" ) );

         // ��ȡtableId
         final String tableId = request.getParameter( "tableId" );

         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         List< MappingVO > columnMappingVOAll = new ArrayList< MappingVO >();

         columnMappingVOAll.add( 0, ( ( ReportDetailVO ) form ).getEmptyMappingVO() );

         // ReportHeaderVO reportHeaderVO =
         // reportHeaderService.getReportHeaderVOByReportHeaderId(reportHeaderId);
         //����dto
         TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );
         // List reportColumnList =
         // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());
         // tableDTO.getAllColumnVO();
         List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), null, BaseVO.TRUE );

         setColumnNameWithTableName( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? tableDTO.getTableVO().getNameZH() : tableDTO.getTableVO().getNameEN() ) + " ("
               + KANUtil.getProperty( request.getLocale(), "define.report.parent" ) + ") ", columnMappingVOs );

         columnMappingVOAll.addAll( columnMappingVOs );

         List< Object > reportRealtionVOList = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderId );

         ReportRelationVO reportRelationVO = null;
         // �ӱ�
         for ( Object object : reportRealtionVOList )
         {

            reportRelationVO = ( ReportRelationVO ) object;
            tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
            // List reportColumnList =
            // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());
            // �ӱ��������
            columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage(), null, BaseVO.TRUE );
            if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
            {
               setColumnNameWithTableName( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? tableDTO.getTableVO().getNameZH() : tableDTO.getTableVO().getNameEN() )
                     + " ", columnMappingVOs );

               columnMappingVOAll.addAll( columnMappingVOs );
            }
         }

         // reportSearchDetailVO.setColumns(columnMappingVOAll);

         // Send to client
         out.println( KANUtil.getSelectHTML( columnMappingVOAll, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * ��ʼ���ֶ�������
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_column_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ȡtableId
         final String tableId = request.getParameter( "tableId" );

         // �������ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
         // ��ʼ��Service �ӿ�
         final ReportDetailService reportDetailService = ( ReportDetailService ) getService( "reportDetailService" );
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         List< Object > reportRelationList = reportHeaderService.getReportRelationVOsByReportHeaderId( headerId );

         // ��ʼ����������
         final ReportDetailVO reportDetailVO = new ReportDetailVO();
         reportDetailVO.setReportHeaderId( headerId );
         reportDetailVO.setStatus( BaseVO.TRUE );
         PagedListHolder reportDetailHolder = new PagedListHolder();
         reportDetailHolder.setObject( reportDetailVO );
         reportDetailService.getReportDetailVOsByCondition( reportDetailHolder, false );

         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( reportDetailHolder, request );

         Map< String, TableDTO > tableDTOMap = new HashMap< String, TableDTO >();
         // ��ȡtableDTO
         TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         tableDTOMap.put( tableDTO.getTableVO().getTableId(), tableDTO );
         // ѡ���ӱ�
         if ( reportRelationList != null && reportRelationList.size() > 0 )
         {
            for ( Object object : reportRelationList )
            {
               tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( ( ( ReportRelationVO ) object ).getSlaveTableId() );
               tableDTOMap.put( tableDTO.getTableVO().getTableId(), tableDTO );
            }
         }

         // ��ʼ���ֶ��б�
         final List< MappingVO > columnMappingVOs = new ArrayList< MappingVO >();
         columnMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         if ( reportDetailHolder != null && reportDetailHolder.getSource().size() > 0 )
         {
            for ( Object objReportDetailVO : reportDetailHolder.getSource() )
            {
               final ReportDetailVO tempVO = ( ReportDetailVO ) objReportDetailVO;

               // ��ȡColumnVO
               final ColumnVO columnVO = tableDTOMap.get( tempVO.getTableId() ).getColumnVOByColumnId( tempVO.getColumnId() );

               if ( columnVO != null && columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && columnVO.getIsDBColumn().trim().equals( BaseVO.TRUE ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempVO.getColumnId() );
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
                  {
                     mappingVO.setMappingValue( tableDTOMap.get( tempVO.getTableId() ).getTableVO().getNameZH() + " " + tempVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tableDTOMap.get( tempVO.getTableId() ).getTableVO().getNameEN() + " " + tempVO.getNameEN() );
                  }
                  columnMappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getSelectHTML( columnMappingVOs, "columnId", "columnId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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

         // ��ʼ��Service�ӿ�
         // final ReportDetailService reportDetailService = (
         // ReportDetailService ) getService( "reportDetailService" );

         // ���ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // ��ʼ��StringBuffer
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "accountId", columnVO.getAccountId() );
            jsonObject.put( "nameZH", columnVO.getNameZH() );
            jsonObject.put( "nameEN", columnVO.getNameEN() );
            jsonObject.put( "valueType", columnVO.getValueType() );
            jsonObject.put( "inputType", columnVO.getInputType() );
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

   /**
    * @param tableName
    * @param columnMappingVOs
    */
   private void setColumnNameWithTableName( String tableName, List< MappingVO > columnMappingVOs )
   {

      if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
      {
         for ( MappingVO tempColumnMappingVO : columnMappingVOs )
         {

            // if(columnVO != null
            // && columnVO.getAccountId().equals(
            // KANConstants.SUPER_ACCOUNT_ID)
            // && columnVO.getIsDBColumn().equals(BaseVO.TRUE))
            tempColumnMappingVO.setMappingValue( tableName + tempColumnMappingVO.getMappingValue() );
         }
      }
   }

   /**
    * ��ʼѡ�������� ����
    * 
    * @param reportSearchDetailVO
    * @param request
    * @param response
    * @throws KANException
    */
   protected void getColumns( ReportSearchDetailVO reportSearchDetailVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
      String reportHeaderId = reportSearchDetailVO.getReportHeaderId();

      List< MappingVO > columnMappingVOAll = new ArrayList< MappingVO >();

      ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( reportHeaderId );

      TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // List reportColumnList =
      // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());

      List< MappingVO > columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage() );

      setColumnNameWithTableName( tableDTO.getTableVO().getNameZH() + "(����)", columnMappingVOs );

      columnMappingVOAll.addAll( columnMappingVOs );

      List< Object > reportRealtionVOList = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderId );

      ReportRelationVO reportRelationVO = null;

      for ( Object object : reportRealtionVOList )
      {

         reportRelationVO = ( ReportRelationVO ) object;
         tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
         // List reportColumnList =
         // reportHeaderService.getReportColumnVOsByReportHeaderId(reportHeaderVO.getReportHeaderId());

         columnMappingVOs = tableDTO.getColumns( request.getLocale().getLanguage() );
         if ( columnMappingVOs != null && columnMappingVOs.size() > 0 )
         {

            setColumnNameWithTableName( tableDTO.getTableVO().getNameZH(), columnMappingVOs );

            columnMappingVOAll.addAll( columnMappingVOs );
         }
      }

      reportSearchDetailVO.setColumns( columnMappingVOAll );
   }
}
