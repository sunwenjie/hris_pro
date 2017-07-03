package com.kan.base.web.renders.define;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationSubVO;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.page.PagedReportListHolder;
import com.kan.base.service.inf.define.DBColumnUtilService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ReportRender
{

   // Report Form Name
   private final static String formName = "reportHeaderForm";

   public static String generateReportSearch( final HttpServletRequest request, final String tableId, final String reportHeaderId ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId );

      // ��ʼ��PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );

      // ��ʼ��ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );

      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      // new HashMap<String,ColumnVO>();

      // TableDTO subTableDTO = null;
      // ת������columnVO ���� �ӱ�
      // //����
      // for(ColumnVO columnVO:tableDTO.getAllColumnVO()){
      // columnMap.put(columnVO.getColumnId(), columnVO);
      // }
      //
      // ����Search
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && tableDTO.getReportDTOs().size() > 0 )
      {
         // ��ʼ��ReportDTO
         final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

         if ( reportDTO != null )
         {
            // ��ʼ��ReportHeaderVO
            final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();

            // ��ʼ��Search Name
            String searchName = "";
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               searchName = reportHeaderVO.getNameZH();
            }
            else
            {
               searchName = reportHeaderVO.getNameEN();
            }

            // ��ʼ��������Ӧ������
            String listAction = "reportHeaderAction.do?proc=execute_object";

            rs.append( "<div class=\"box searchForm toggableForm\" id=\"Search-Information\">" );
            rs.append( "<div class=\"head\">" );
            rs.append( "<label>" + searchName + "</label>" );
            rs.append( "</div>" );
            rs.append( "<a class=\"toggle tiptip\" title=\"" + KANUtil.getProperty( request.getLocale(), "public.hideoptions" ) + "\">&gt;</a>" );

            // ���������������
            //            if ( reportDTO.getReportSearchDetailVOs() != null && reportDTO.getReportSearchDetailVOs().size() > 0 )
            //            {
            // ��ʼ��Search Div��ʽ
            String searchDivStyle = "";
            // ��������������ȣ�������Search Div
            if ( !reportDTO.isSearchFirst() )
            {
               searchDivStyle = " style=\"display: none;\" ";
            }

            // SubAction����
            String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
            if ( subAction == null || subAction.trim().equalsIgnoreCase( "null" ) )
            {
               subAction = "";
            }

            rs.append( "<div id=\"searchDiv\" class=\"inner\" " + searchDivStyle + ">" );
            rs.append( "<div class=\"top\"> " );

            // ���������ʽ��ֱ�ӵ���&&����������
            if ( reportDTO.isExportFirst() && reportDTO.isSearchFirst() )
            {
               rs.append( "<input type=\"button\" id=\"searchBtn\" name=\"searchBtn\" class=\"searchBtn\" value=\"����Excel�ļ�\" onclick=\"linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction="
                     + tableDTO.getTableVO().getAccessAction() + "&reportHeaderId=" + reportHeaderId + "');\" />" );
            }
            else
            {
               rs.append( "<input type=\"button\" id=\"searchBtn\" name=\"searchBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.search" )
                     + "\" onclick=\"submitForm('list_form', 'searchObject', null, null, null, null);\" />" );
            }
            if ( reportDTO.getReportSearchDetailVOs() != null && reportDTO.getReportSearchDetailVOs().size() > 0 )
            {
               rs.append( "<input type=\"button\" class=\"reset\" id=\"resetBtn\" name=\"resetBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.reset" )
                     + "\" onclick=\"resetForm();\" />" );
            }
            rs.append( "<input type=\"button\" class=\"reset\" id=\"btnList\" name=\"btnList\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.list" )
                  + "\" style=\"display:none;\" />" );
            rs.append( "</div>" );
            rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + listAction + "\" class=\"list_form\">" );
            rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"" + reportHeaderVO.getEncodedId() + "\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortColumn\" name=\"sortColumn\" value=\"" + pagedListHolder.getSortColumn() + "\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortOrder\" name=\"sortOrder\" value=\"" + pagedListHolder.getSortOrder() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"page\" name=\"page\" value=\"" + pagedListHolder.getPage() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"selectedIds\" name=\"selectedIds\" value=\"" + pagedListHolder.getSelectedIds() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" value=\"" + subAction + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"moduleName\" name=\"moduleName\" value=\"" + request.getAttribute( "moduleName" ) + "\" />" );
            rs.append( "<fieldset>" );
            rs.append( "<ol class=\"auto\">" );

            // �������������ֶ�
            for ( ReportSearchDetailVO reportSearchDetailVO : reportDTO.getReportSearchDetailVOs() )
            {
               // ���Ϊ�ջ���ʾ
               if ( reportSearchDetailVO.getDisplay() == null || !reportSearchDetailVO.getDisplay().trim().equals( "2" ) )
               {
                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = columnMap.get( reportSearchDetailVO.getColumnId() );

                  // ��ʼ��Clomun Name
                  String columnName = "";
                  if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                  {
                     columnName = reportSearchDetailVO.getNameZH();
                  }
                  else
                  {
                     columnName = reportSearchDetailVO.getNameEN();
                  }

                  // ��ʼ���ؼ���ID��Name
                  String id_name = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();

                  // ��ʼ���ؼ���Length
                  String maxLenght = "";
                  if ( KANUtil.filterEmpty( columnVO.getValidateLengthMax(), "0" ) != null )
                  {
                     maxLenght = columnVO.getValidateLengthMax();
                  }

                  // ��ʼ��MappingVO�б�
                  final List< MappingVO > mappingVOs = getMappingVOsByCondition( request, columnVO );

                  // ��ǰInput��Style
                  String styleInput = "";
                  if ( KANUtil.filterEmpty( columnVO.getCssStyle() ) != null )
                  {
                     styleInput = columnVO.getCssStyle();
                  }

                  // ��ʼ����ǰColumn��Value
                  String value = "";
                  String provinceId = "0";

                  if ( KANUtil.filterEmpty( request.getAttribute( id_name ) ) != null )
                  {
                     value = ( String ) request.getAttribute( id_name );
                  }
                  else
                  {
                     value = reportSearchDetailVO.getContent();
                  }

                  // ����ǳ���
                  if ( columnVO.getNameDB().equals( "cityId" ) && ( KANUtil.filterEmpty( request.getAttribute( id_name + "_provinceId" ) ) != null ) )
                  {
                     // ���ѡ���˳��� ΪcityId ֻѡ����ʡΪѡ���� ֵ����ʡ��ǰ׺
                     provinceId = ( String ) request.getAttribute( id_name + "_provinceId" );
                  }
                  rs.append( "<li>" );
                  rs.append( "<label>" + columnName + "</label>" );

                  // �����ǰ�ֶ����ı���
                  if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "1" ) )
                  {

                     // ������Column�Ĵ���
                     String datetime = "";
                     String dateClass = "";
                     if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                     {
                        datetime = " onFocus=\"WdatePicker()\" ";
                        dateClass = " Wdate ";
                     }

                     rs.append( "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" maxlength=\"" + maxLenght + "\" class=\"" + "REPORT" + "_" + id_name
                           + "\" style=\"" + styleInput + "\" value=\"" + value + "\" " + dateClass + " " + datetime + " />" );
                  }
                  // �����ǰ�ֶ���������
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "2" ) )
                  {

                     // ʡ�� Ҫ���⴦�� id ��ʼ ʡ-�� ��ʱ�����Ժ󿴿���Ϊ�кð취
                     if ( columnVO.getNameDB().equals( "cityId" ) )
                     {
                        if ( KANUtil.filterEmpty( value ) == null )
                        {
                           value = "0";
                        }
                        rs.append( "<input type='hidden' id='" + id_name + "_city_value" + "' value='" + value + "'/>" );

                        rs.append( "<select id=\"" + id_name + "_provinceId" + "\" name=\"" + id_name + "_provinceId" + "\" class=\"" + id_name + "_provinceId" + "\" style=\""
                              + styleInput + "\">" );
                        // ��ʼ��MappingVO�б� Ҫ��ȡʡ��select
                        ColumnVO tempColumnVO = new ColumnVO();
                        tempColumnVO.setOptionType( "2" );
                        tempColumnVO.setOptionValue( "3" );
                        final List< MappingVO > tempMappingVOs = getMappingVOsByCondition( request, tempColumnVO );

                        for ( MappingVO mappingVO : tempMappingVOs )
                        {
                           // �Ƿ���Ҫѡ��
                           String selected = "";
                           if ( provinceId != null && provinceId.trim().equals( mappingVO.getMappingId() ) )
                           {
                              selected = " selected ";
                           }
                           rs.append( "<option id=\"option_" + "REPORT" + "_" + id_name + "_" + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" "
                                 + selected + ">" + mappingVO.getMappingValue() + "</option>" );
                        }

                        rs.append( "</select>" );

                     }
                     else
                     {
                        rs.append( "<select id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + "REPORT" + "_" + id_name + "\" style=\"" + styleInput + "\">" );

                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // �Ƿ���Ҫѡ��
                              String selected = "";
                              if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                              {
                                 selected = " selected ";
                              }
                              rs.append( "<option id=\"option_" + "REPORT" + "_" + id_name + "_" + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" "
                                    + selected + ">" + mappingVO.getMappingValue() + "</option>" );
                           }
                        }

                        rs.append( "</select>" );
                     }
                  }
                  // �����ǰ�ֶ��ǵ�ѡ��
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "3" ) )
                  {
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        rs.append( "<span>" );
                        for ( MappingVO mappingVO : mappingVOs )
                        {
                           // �Ƿ���Ҫѡ��
                           String checked = "";
                           if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                           {
                              checked = "checked ";
                           }
                           rs.append( "<input type=\"radio\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + "REPORT" + "_" + id_name + "\" style=\"" + styleInput
                                 + "\" value=\"" + value + "\" " + checked + " />" + mappingVO.getMappingValue() + " &nbsp; " );
                        }
                        rs.append( "</span>" );
                     }
                  }
                  // �����ǰ�ֶ��Ǹ�ѡ��
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "4" ) )
                  {
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        rs.append( "<span>" );
                        for ( MappingVO mappingVO : mappingVOs )
                        {
                           // �Ƿ���Ҫѡ��
                           String checked = "";
                           if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                           {
                              checked = "checked ";
                           }
                           rs.append( "<input type=\"checkbox\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + "REPORT" + "_" + id_name + "\" style=\"" + styleInput
                                 + "\" value=\"" + value + "\" " + checked + " />" + mappingVO.getMappingValue() + " &nbsp; " );
                        }
                        rs.append( "</span>" );
                     }

                  }
                  // �����ǰ�ֶ����ı���
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "5" ) )
                  {
                     rs.append( "<textarea id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + "REPORT" + "_" + id_name + "\" style=\"" + styleInput + "\" />" + value
                           + "</textarea>" );
                  }
                  rs.append( "</li>" );
               }
            }
            rs.append( "</ol>" );
            rs.append( "</fieldset>" );
            rs.append( "</form>" );
            rs.append( "</div>" );
            //            }

            rs.append( "</div>" );
         }
      }

      return rs.toString();
   }

   public static String generateReportList( final HttpServletRequest request, final String tableId, final String reportHeaderId ) throws KANException
   {
      // ��ʼ��TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId );

      // ��ʼ��ReportDTO
      final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      rs.append( "<div class=\"box noHeader\" id=\"search-results\">" );
      rs.append( "<div class=\"inner\">" );

      // <!-- -->

      rs.append( "<div class=\"top\">" );
      rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );

      // �жϱ����б��Ƿ���Ҫ��ӵ�������
      if ( reportDTO != null && reportDTO.getReportHeaderVO() != null && reportDTO.getReportHeaderVO().getExportExcelType() != null
            && reportDTO.getReportHeaderVO().getExportExcelType().trim().equals( "1" ) )
      {
         rs.append( "<a id=\"exportExcel\" name=\"exportExcel\" class=\"commonTools\" title=\"" + KANUtil.getProperty( request.getLocale(), "img.title.tips.export.excel" )
               + "\" onclick=\"linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=" + tableDTO.getTableVO().getAccessAction() + "&reportHeaderId="
               + reportHeaderId + "');\"><img src=\"images/appicons/excel_16.png\" /></a>" );
      }

      rs.append( "</div>" );

      rs.append( "<div id=\"tableWrapper\">" );
      rs.append( ReportRender.generateReportListTable( request, tableDTO, reportHeaderId ) );
      rs.append( "</div>" );
      rs.append( "<div class=\"bottom\"><p></div>" );
      rs.append( "</div>" );
      rs.append( "</div>" );

      return rs.toString();
   }

   public static String generateReportListTable( final HttpServletRequest request, final TableDTO tableDTO, final String reportHeaderId ) throws KANException
   {
      // ��ʼ��PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );
      // ��ֵ
      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      //
      String sortColumn = ( String ) request.getAttribute( "sortColumn" );
      String sortOrder = ( String ) request.getAttribute( "sortOrder" );
      String defaultSortColumn = ( String ) request.getAttribute( "defaultSortColumn" );

      String nextSortOrder = "";
      String columnId = "";
      String thStyle = "";

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      // ����Column Group
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && pagedListHolder != null )
      {
         // ��ʼ��ReportDTO
         final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            // ��ʼ��ReportHeaderVO
            final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();

            // ��������Table Head
            rs.append( "<thead>" );
            rs.append( "<tr>" );

            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               nextSortOrder = "";
               columnId = "";
               thStyle = "";
               if ( reportDetailVO.getDisplay() == null || !reportDetailVO.getDisplay().trim().equals( "2" ) )
               {
                  // ��ʼ��ReportDetailVO
                  reportDetailVO.reset( null, request );

                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                  columnId = "T_" + columnVO.getTableId() + "." + columnVO.getNameDB();
                  // tableDTO.getColumnVOByColumnId(
                  // reportDetailVO.getColumnId() );

                  // ��ʼ���б��ֶ���ʽ

                  if ( reportDetailVO.getColumnWidth() != null && !reportDetailVO.getColumnWidth().trim().equals( "" ) )
                  {
                     // ��ʼ��Width Type��Ĭ���ǰٷֱ�
                     String widthType = "%";
                     if ( reportDetailVO.getColumnWidthType() != null )
                     {
                        // �п���������ʾ
                        if ( reportDetailVO.getColumnWidthType().trim().equals( "2" ) )
                        {
                           widthType = "px";
                        }
                     }
                     thStyle = "width: " + reportDetailVO.getColumnWidth() + widthType + ";";
                  }
                  if ( KANUtil.filterEmpty( thStyle ) != null )
                  {
                     thStyle = " style=\"" + thStyle + "\" ";
                  }

                  // ��ʼ���б������С
                  String valueStyle = "";
                  if ( KANUtil.filterEmpty( reportDetailVO.getFontSize(), "0" ) != null )
                  {
                     valueStyle = valueStyle + "font-size: " + reportDetailVO.getFontSize() + "px;";
                  }
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     valueStyle = " style=\"" + valueStyle + "\" ";
                  }

                  // ��ʼ���������
                  boolean sort = true;
                  if ( KANUtil.filterEmpty( reportDetailVO.getSort() ) != null && reportDetailVO.getSort().trim().equals( "2" ) )
                  {
                     sort = false;
                  }

                  // �б���
                  rs.append( "<th " + thStyle + " class=\"" );

                  // ��������
                  if ( sort )
                  {
                     //ҳ������
                     if ( StringUtils.isNotBlank( sortColumn ) && StringUtils.isNotBlank( sortOrder ) && sortColumn.equals( columnId ) )
                     {
                        nextSortOrder = pagedListHolder.getNextSortOrder( sortOrder );
                        if ( nextSortOrder.equals( "asc" ) )
                        {
                           rs.append( "header headerSortDown" );
                        }
                        else if ( nextSortOrder.equals( "desc" ) )
                        {
                           rs.append( "header headerSortUp" );
                        }
                        else
                        {
                           rs.append( "header" );
                        }
                     }
                     else if ( StringUtils.isNotBlank( defaultSortColumn ) )
                     {

                        //����Ĭ������
                        if ( StringUtils.isNotBlank( defaultSortColumn ) && StringUtils.isBlank( sortColumn ) )
                        {

                           // Ĭ������
                           final JSONObject jsonObject = JSONObject.fromObject( defaultSortColumn );
                           // ���������ֶ�
                           for ( Object objKey : jsonObject.keySet() )
                           {
                              //��������ֶε��ڵ�ǰ�� th ��Ӧ����
                              if ( ( ( String ) objKey ).equals( columnVO.getColumnId() ) )
                              {
                                 nextSortOrder = pagedListHolder.getNextSortOrder( ( ( String ) jsonObject.get( objKey ) ).replace( "������", "" ).replace( "������", "" ) );
                              }
                           }
                           if ( nextSortOrder.equals( "asc" ) )
                           {
                              rs.append( "header headerSortDown" );
                           }
                           else if ( nextSortOrder.equals( "desc" ) )
                           {
                              rs.append( "header headerSortUp" );
                           }
                           else
                           {
                              rs.append( "header" );
                           }
                        }
                        else
                        {
                           rs.append( "header" );
                        }
                     }
                     else
                     {
                        rs.append( "header " );
                     }

                  }
                  else
                  {
                     rs.append( "header-nosort " );
                  }

                  // �޹��������������
                  if ( columnVO != null )
                  {
                     rs.append( pagedListHolder.getCurrentSortClass( columnVO.getNameDB() ) );
                  }
                  rs.append( "\">" );

                  // �޹�����������������
                  if ( columnVO != null && sort )
                  {
                     // T_3.coulunName
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + columnId + "', '" + nextSortOrder + "', 'tableWrapper');\">" );
                  }

                  // �����Ҫ������ʽ
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // �����ڲ������������
                  rs.append( getColumnName( request, reportDetailVO ) );

                  // �����Ҫ������ʽ
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "</span>" );
                  }

                  // �޹�����������������
                  if ( columnVO != null && sort )
                  {
                     rs.append( "</a>" );
                  }
                  rs.append( "</th>" );

               }
            }

            rs.append( "</tr>" );
            rs.append( "</thead>" );

            // ��������Table Body
            if ( pagedListHolder.getSourceMap() != null && pagedListHolder.getSourceMap().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index �����жϵ�ǰ���кţ������б�ͬ�е���ʽ
               int index = 0;
               // ������
               for ( Object object : pagedListHolder.getSourceMap() )
               {
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  rs.append( "<tr class='" + trClass + "'>" );

                  // ������
                  for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
                  {
                     if ( reportDetailVO.getDisplay() == null || !reportDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // ��ʼ���ֶζ���
                        final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                        // tableDTO.getColumnVOByColumnId(
                        // reportDetailVO.getColumnId() );

                        // ��ʼ���б��ֶ���ʽ
                        String valueStyle = "";
                        if ( KANUtil.filterEmpty( reportDetailVO.getFontSize(), "0" ) != null )
                        {
                           valueStyle = valueStyle + "font-size: " + reportDetailVO.getFontSize() + "px;";
                        }
                        if ( KANUtil.filterEmpty( valueStyle ) != null )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }
                        // ��ʼ����̬����<!---->

                        rs.append( "<td align=\"" + reportDetailVO.getDecodeAlignTemp() + "\">" );

                        // �����ǰ�ֶ��Ǵ����ӵ�<!---->

                        if ( columnVO != null )
                        {
                           // �����Ҫ������ʽ
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( getColumnValue( request, columnVO, reportDetailVO, object ) );

                           // �����Ҫ������ʽ
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "</span>" );
                           }
                        }

                        // �����ǰ�ֶ��Ǵ����ӵ�<!---->

                        // ������ڸ�������<!---->

                        rs.append( "</td>" );
                     }
                  }

                  rs.append( "</tr>" );
                  index++;
               }

               rs.append( "</tbody>" );
            }

            if ( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().trim().equals( "1" ) )
            {
               // ����Table Foot
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( reportDTO.getReportDetailVOs().size() ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "�� " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "��" + pagedListHolder.getIndexStart() + " - "
                     + pagedListHolder.getIndexEnd() + "</label> " );
               rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getFirstPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.first" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getPreviousPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.previous" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getNextPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.next" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getLastPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.last" ) + "</a></label> " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "��" + pagedListHolder.getRealPage() + "/"
                     + pagedListHolder.getPageCount() + "</label>&nbsp;" );
               rs.append( "</td>" );
               rs.append( "</tr>" );
               rs.append( "</tfoot>" );
            }
         }

         rs.append( "</table>" );
      }

      return rs.toString();
   }

   public static String generateReportSQL( final HttpServletRequest request, final HttpServletResponse response, final ReportHeaderVO reportHeaderVO, final TableDTO tableDTO,
         final Map< String, TableDTO > subTableMap, final Map< String, ColumnVO > columnMap, final List< ReportSearchDetailVO > reportSearchDetailVOList, String sortColumns )
         throws KANException
   {
      if ( reportHeaderVO == null || StringUtils.isBlank( reportHeaderVO.getReportHeaderId() ) )
      {
         return null;
      }
      final String reportHeaderId = reportHeaderVO.getReportHeaderId();
      // ���Log Start����
      LogFactory.getLog( reportHeaderId ).info( "SQL Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // ��ʼ��StringBuffer
      final StringBuffer rsColumn = new StringBuffer();
      final StringBuffer rsTable = new StringBuffer();
      final StringBuffer rsWhere = new StringBuffer();
      // ��ʼ�� Service�ӿ�
      final DBColumnUtilService dbColumnUtilService = ( DBColumnUtilService ) BaseAction.getService( "dbColumnUtilService" );
      // ��dto
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && tableDTO.getReportDTOs().size() > 0 )
      {

         // ReportDTO tempReportDTO = tableDTO.getReportDTOByReportHeaderId(
         // reportHeaderId );
         // ����DTO
         final ReportDTO reportDTO = tableDTO.getReportDTOByReportHeaderId( reportHeaderId );

         if ( reportDTO != null )
         {
            // ��ʼ�������� ����
            String tableAccess = "T_" + tableDTO.getTableVO().getTableId();

            String tableName = tableDTO.getTableVO().getAccessName();
            // ��ʼ������id
            String tableId = tableDTO.getTableVO().getTableId();
            // ��ʼ�����where����
            rsWhere.append( " WHERE " + tableAccess + ".deleted = 1 " );

            rsWhere.append( " AND " + tableAccess + ".accountId = " + reportHeaderVO.getAccountId() );
            //����Ȩ��
            rsWhere.append( dbColumnUtilService.getAuthStr( request, response, tableName, tableAccess ) );

            final List< ColumnVO > clolumnVOList = tableDTO.getAllColumnVO();

            boolean haveCorpIdColumn = false;
            if ( clolumnVOList != null && clolumnVOList.size() > 0 )
            {
               for ( ColumnVO columnVO : clolumnVOList )
               {
                  if ( columnVO.getNameDB().equals( "corpId" ) )
                  {
                     haveCorpIdColumn = true;
                  }

               }
            }
            if ( haveCorpIdColumn )
            {
               if ( StringUtils.isNotBlank( reportHeaderVO.getCorpId() ) )
               {
                  rsWhere.append( " AND (" + tableAccess + ".corpId = " + reportHeaderVO.getCorpId() + "  or 0 = " + reportHeaderVO.getCorpId() + ")" );
               }
               else
               {
                  rsWhere.append( " AND " + tableAccess + ".corpId  IS NULL " );
               }
            }

            // �����from
            rsTable.append( " FROM " + tableName + " " + tableAccess );
            // column
            rsColumn.append( " SELECT " );
            // �ӱ����
            String subTableAccess = null;

            // ������ѯ�ֶ� select
            if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
            {
               for ( ReportDetailVO tempReportDetailVO : reportDTO.getReportDetailVOs() )
               {
                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = columnMap.get( tempReportDetailVO.getColumnId() );

                  if ( columnVO != null )
                  {
                     tableAccess = "T_" + columnVO.getTableId();

                     // ������Զ����ֶ�
                     if ( !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                     {
                        // Remark1ֻ��ѯһ��
                        if ( !rsColumn.toString().contains( tableAccess + ".remark1" + " AS " + tableAccess + "_remark1, " ) )
                        {
                           rsColumn.append( tableAccess + ".remark1" + " AS " + tableAccess + "_remark1, " );
                        }
                        continue;
                     }

                     if ( columnVO.getNameDB().equals( "cityId" ) )
                     {
                        rsColumn.append( getCitySql( tableAccess + "." + columnVO.getNameDB() ) + " AS " + tableAccess + "_" + columnVO.getColumnId() );
                     }
                     else
                     {
                        //����ۺϺ���
                        if ( StringUtils.isNotBlank( tempReportDetailVO.getStatisticsFun() ) && !"0".equals( tempReportDetailVO.getStatisticsFun() ) )
                        {
                           String statisticsFun = null;
                           for ( MappingVO mapVO : KANUtil.getMappings( request.getLocale(), "def.report.header.statistics.column" ) )
                           {
                              if ( mapVO.getMappingId().equals( tempReportDetailVO.getStatisticsFun() ) )
                              {
                                 statisticsFun = mapVO.getMappingValue();
                                 break;
                              }
                           }
                           if ( StringUtils.isNotBlank( statisticsFun ) )
                           {
                              rsColumn.append( statisticsFun + "(" + tableAccess + "." + columnVO.getNameDB() + ") AS " + tableAccess + "_" + columnVO.getColumnId() );
                           }
                           //������
                        }
                        else
                        {
                           rsColumn.append( tableAccess + "." + columnVO.getNameDB() + " AS " + tableAccess + "_" + columnVO.getColumnId() );
                        }
                     }
                     rsColumn.append( "," );
                  }

               }

               // ȥ����󶺺�
               rsColumn.deleteCharAt( rsColumn.length() - 1 );
            }
            // join
            for ( String subTId : subTableMap.keySet() )
            {
               for ( TableRelationVO rableRelationVO : tableDTO.getTableRelationVOs() )
               {
                  // �ҵ���Ӧ�ı��ϵ
                  if ( tableId.equals( rableRelationVO.getMasterTableId() ) && subTId.equals( rableRelationVO.getSlaveTableId() ) )
                  {
                     // rableRelationVO.getJoinOn();
                     // ƴ��join join ��ʽ {masterTableAs}.userId =
                     // {slaveTableAs}.userId �滻���� �滻����
                     String subTableName = KANUtil.filterEmpty( subTableMap.get( subTId ).getTableVO().getAccessTempName() ) != null ? subTableMap.get( subTId ).getTableVO().getAccessTempName()
                           : subTableMap.get( subTId ).getTableVO().getAccessName();
                     rsTable.append( " " + rableRelationVO.getJoinType() + " " + subTableName + " AS T_" + rableRelationVO.getSlaveTableId() + " ON T_"
                           + rableRelationVO.getMasterTableId() + "." + rableRelationVO.getMasterColumn() + " = T_" + rableRelationVO.getSlaveTableId() + "."
                           + rableRelationVO.getSlaveColumn() );
                     // ƴ�� where
                     // �ӱ�ı���
                     subTableAccess = "T_" + rableRelationVO.getSlaveTableId();
                     rsTable.append( " AND " + subTableAccess + ".deleted = 1 " );

                     rsTable.append( dbColumnUtilService.getAuthStr( request, response, subTableName, subTableAccess ) );
                     break;
                  }
               }
            }

            String groupByAccess = null;
            String orderByAccess = null;
            // ������ѯ����
            if ( reportSearchDetailVOList != null && reportSearchDetailVOList.size() > 0 )
            {
               for ( ReportSearchDetailVO tempReportSearchDetailVO : reportSearchDetailVOList )
               {
                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = columnMap.get( tempReportSearchDetailVO.getColumnId() );
                  final String columnDB = columnVO.getNameDB();

                  tableAccess = "T_" + columnVO.getTableId();
                  // temString Ϊʡ��ֵ��
                  if ( ( KANUtil.filterEmpty( tempReportSearchDetailVO.getRange() ) != null || KANUtil.filterEmpty( tempReportSearchDetailVO.getContent() ) != null )
                        && KANUtil.filterEmpty( columnDB ) != null )
                  {

                     // ��ǰ�ֶ�Ϊ������
                     if ( KANUtil.filterEmpty( columnVO.getInputType(), "0" ) != null && columnVO.getInputType().equals( "2" ) )
                     {
                        //tempStr ���ʡ�ݵ�ֵ
                        if ( KANUtil.filterEmpty( tempReportSearchDetailVO.getTempStr() ) != null && KANUtil.filterEmpty( tempReportSearchDetailVO.getTempStr(), "0" ) != null
                              && KANUtil.filterEmpty( columnDB ) != null )
                        {
                           //ֻ��ʡ��ʱ ƴ�Ӵ� exist ���
                           if ( columnVO.getNameDB().equals( "cityId" ) )
                           {
                              rsWhere.append( " AND " + getProvinceExistSql( tempReportSearchDetailVO.getTempStr(), tableAccess + "." + columnDB ) );
                           }
                        }

                        // �����ֶ�
                        rsWhere.append( " AND (" + tableAccess + "." + columnDB + " = " + tempReportSearchDetailVO.getContent() + " OR 0 = "
                              + tempReportSearchDetailVO.getContent() + ")" );
                     }
                     else
                     {
                        // ��������� ��=��
                        if ( tempReportSearchDetailVO.getCondition().equals( "1" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " = '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // ��������� ��>��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "2" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " < '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // ��������� ��>��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "3" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " <= '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // ��������� ��>��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "4" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " > '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // ��������� ��>��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "5" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " >= '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // ��������� ��like��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "6" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " LIKE CONCAT('%', '" + tempReportSearchDetailVO.getContent() + "', '%')" );
                        }
                        // ��������� ��in��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "7" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " IN (" + tempReportSearchDetailVO.getContent() + ")" );
                        }
                        // ��������� ��between��
                        else if ( tempReportSearchDetailVO.getCondition().equals( "8" ) )
                        {
                           final String[] temp = tempReportSearchDetailVO.getRange().split( "_" );
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " BETWEEN " + temp[ 0 ] + " AND " + temp[ 1 ] );
                        }
                        // ����
                        else
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " = '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                     }
                  }
               }
            }
            // ���ڷ����ֶ�
            if ( KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
            {
               rsWhere.append( " GROUP BY " );

               // ��ʼ��GroupColumns����
               final String[] groupColumns = KANUtil.jasonArrayToStringArray( reportHeaderVO.getGroupColumns() );

               // ���������ֶ�
               for ( String tempGroupColumn : groupColumns )
               {
                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = columnMap.get( tempGroupColumn );
                  groupByAccess = "T_" + columnVO.getTableId();
                  rsWhere.append( groupByAccess + "." + columnVO.getNameDB() + "," );
               }

               // ȥ����󶺺�
               rsWhere.deleteCharAt( rsWhere.length() - 1 );
            }

            // ���������ֶ�
            if ( KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null || KANUtil.filterEmpty( sortColumns ) != null )
            {
               rsWhere.append( " ORDER BY " );

               // ��ʼ��JSONObject ҳ������Ϊ����ֻ��ԭʼ����
               if ( KANUtil.filterEmpty( sortColumns ) != null )
               {
                  rsWhere.append( sortColumns );
               }
               else
               {
                  // Ĭ������
                  final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );

                  // ���������ֶ�
                  for ( Object objKey : jsonObject.keySet() )
                  {
                     // ��ʼ���ֶζ���
                     final ColumnVO columnVO = columnMap.get( String.valueOf( objKey ) );
                     orderByAccess = "T_" + columnVO.getTableId();

                     // �����ֶ���{'4401':'desc'} {'nameZH':'desc'}
                     final String sortColumnName = columnVO != null ? orderByAccess + "." + columnVO.getNameDB() : String.valueOf( objKey );
                     rsWhere.append( sortColumnName + " " + String.valueOf( jsonObject.get( objKey ) ).replace( "������", "" ).replace( "������", "" ) + "," );
                  }
                  rsWhere.deleteCharAt( rsWhere.length() - 1 );
               }

               // ȥ����󶺺�
            }
         }
      }

      String sqlStr = rsColumn.toString() + " " + rsTable.toString() + " " + rsWhere.toString();
      System.out.println( sqlStr );
      // ���Log Start����
      LogFactory.getLog( reportHeaderId ).info( "SQL Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );
      return sqlStr.toString();
   }

   // ����ֶ���
   private static String getColumnName( final HttpServletRequest request, final ReportDetailVO reportDetailVO ) throws KANException
   {
      return request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) ? reportDetailVO.getNameZH() : reportDetailVO.getNameEN();
   }

   // ����ֶ�ֵ
   private static String getColumnValue( final HttpServletRequest request, final ColumnVO columnVO, final ReportDetailVO reportDetailVO, final Object object ) throws KANException
   {
      // �б��ֶ���
      String fieldName = columnVO.getNameDB();
      String fieldId = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();

      // ��ʼ����ǰColumn��Value
      String value = "";
      // �����ϵͳ������ֶ�
      if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         if ( ( ( Map ) object ).get( fieldId ) == null )
         {
            value = "";
         }
         else
         {
            value = ( ( Map ) object ).get( fieldId ).toString();
         }
      }
      // ������û��Զ�����ֶ�
      else
      {
         try
         {
            // ������ݶ���Ϊ��
            if ( object != null )
            {
               // ��Object��Remark1�ֶλ�ȡJason�ַ���
               final Object jsonString = ( ( Map ) object ).get( "T_" + columnVO.getTableId() + "_remark1" );
               if ( jsonString != null && !( ( String ) jsonString ).trim().equals( "" ) )
               {
                  // ��ʼ��Jason���� - ���е�ǰ�ֶε�ֵ
                  final JSONObject jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );

                  if ( jsonObject != null && jsonObject.containsKey( fieldName ) )
                  {
                     value = jsonObject.getString( fieldName );
                  }
               }
            }
         }
         catch ( final Exception e )
         {
            throw new KANException( e );
         }
      }

      // �����Ҫת��
      if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && reportDetailVO.getIsDecoded() != null && reportDetailVO.getIsDecoded().equals( ListDetailVO.TRUE )
            && KANUtil.filterEmpty( fieldName ) != null && KANUtil.filterEmpty( value ) != null )
      {
         // ������ת��
         if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().equals( "2" ) )
         {
            if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
               value = new ReportHeaderVO().decodeField( value, getMappingVOsByCondition( request, columnVO ), true );
            else
               value = new ReportHeaderVO().decodeField( value, getMappingVOsByCondition( request, columnVO ) );
         }
         // �ı���ת�루���⴦��
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().equals( "1" ) )
         {
            String result = "";
            for ( String str : value.split( "," ) )
            {
               if ( KANUtil.filterEmpty( result ) == null )
               {
                  // _temp�ֶ� ��������
                  if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
                  {
                     result = new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ), true );
                  }
                  // _temp�ֶ� ����������
                  else if ( fieldName.contains( "owner" ) || fieldName.contains( "Owner" ) )
                  {
                     result = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffNamesByPositionId( request.getLocale().getLanguage(), str );
                  }
                  // ����
                  else
                  {
                     result = new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ) );
                  }
               }
               else
               {
                  if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
                  {
                     result = result + "��" + new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ), true );
                  }
                  // _temp�ֶ� ����������
                  else if ( fieldName.contains( "owner" ) || fieldName.contains( "Owner" ) )
                  {
                     result = result + "��"
                           + KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffNamesByPositionId( request.getLocale().getLanguage(), str );
                  }
                  else
                  {
                     result = result + "��" + new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ) );
                  }
               }
            }

            value = result;
         }
      }

      // �����ַ�������
      if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
      {
         value = "";
      }

      // ����û��������ڸ�ʽ��
      if ( KANUtil.filterEmpty( reportDetailVO.getDatetimeFormat(), "0" ) != null && KANUtil.filterEmpty( value ) != null )
      {
         value = new SimpleDateFormat( reportDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
      }

      // ����û�����С����ʽ��
      if ( KANUtil.filterEmpty( reportDetailVO.getAccuracy(), "0" ) != null && KANUtil.filterEmpty( value ) != null
            && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
      {
         // Ĭ��Ϊ��������
         int roundType = BigDecimal.ROUND_HALF_UP;

         if ( reportDetailVO.getRound() != null )
         {
            // ��ȡ
            if ( reportDetailVO.getRound().trim().equals( "2" ) )
            {
               roundType = BigDecimal.ROUND_DOWN;
            }
            // ���������Ͻ�λ��������λ��������ȡ
            else if ( reportDetailVO.getRound().trim().equals( "3" ) )
            {
               roundType = BigDecimal.ROUND_CEILING;
            }
         }

         value = new BigDecimal( value ).setScale( Integer.valueOf( reportDetailVO.getDecodeAccuracyTemp() ), roundType ).toString();
      }

      return value;
   }

   public static String getReportHeaderSortColumn( final HttpServletRequest request, final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getReportHeaderId() ) != null )
      {
         // ���TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( reportHeaderVO.getAccountId() ).getTableDTOByTableId( reportHeaderVO.getTableId() );
         tableDTO.getReportDTOByReportHeaderId( reportHeaderVO.getReportHeaderId() );
         Map< String, ColumnVO > columnVOMap = getSubTableColumnsMap( reportHeaderVO, request, null );

         if ( tableDTO != null && KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null )
         {
            // ��ʼ��JSONObject
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );

            // ���������ֶ�
            for ( Object key : jsonObject.keySet() )
            {
               // ��ȡColumnVO
               ColumnVO columnVO = columnVO = columnVOMap.get( String.valueOf( key ) );
               if ( columnVO != null )
               {
                  rs.append( "<li>" );
                  rs.append( "<input type=\"hidden\" id=\"sort_" + key + " \" value=\"" + key + "\" />" );
                  rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeSortColumn('" + key
                        + "');\"/>" );
                  rs.append( "&nbsp;&nbsp;" );
                  rs.append( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? columnVO.getNameZH() : columnVO.getNameEN() ) + "��" + jsonObject.get( key ) );
                  rs.append( "</li>" );
               }
            }
         }
      }

      return rs.toString();
   }

   public static String getReportHeaderGroupColumn( final HttpServletRequest request, final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getReportHeaderId() ) != null )
      {
         // ���TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( reportHeaderVO.getAccountId() ).getTableDTOByTableId( reportHeaderVO.getTableId() );
         Map< String, ColumnVO > columnVOMap = getSubTableColumnsMap( reportHeaderVO, request, null );
         if ( tableDTO != null && KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
         {
            final String[] groupColumnArray = reportHeaderVO.getGroupColumns().replace( "{", "" ).replace( "}", "" ).split( ":" );

            // ���������ֶ�
            for ( String groupColumn : groupColumnArray )
            {
               // ��ȡColumnVO
               ColumnVO columnVO = columnVO = columnVOMap.get( groupColumn );
               if ( columnVO != null )
               {
                  rs.append( "<li>" );
                  rs.append( "<input type=\"hidden\" id=\"group_" + groupColumn + " \" value=\"" + groupColumn + "\" />" );
                  rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeGroupColumn('"
                        + groupColumn + "');\"/>" );
                  rs.append( "&nbsp;&nbsp;" );
                  rs.append( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? columnVO.getNameZH() : columnVO.getNameEN() );

                  // ����ͳ���ֶ�  ����
                  if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
                  {
                     // ��ʼ��JSONObject
                     final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );

                     for ( Object key : jsonObject.keySet() )
                     {
                        if ( key.equals( groupColumn ) )
                        {
                           //rs.append("��" + jsonObject.get(key));
                           break;
                        }
                     }
                  }

                  rs.append( "</li>" );
               }
            }
         }
      }

      return rs.toString();
   }

   public static String getPositionGradeMultipleChoice( final HttpServletRequest request, final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      final List< PositionGradeVO > positionGraderVOs = KANConstants.getKANAccountConstants( reportHeaderVO.getAccountId() ).POSITION_GRADE_VO;
      final StringBuffer rs = new StringBuffer();

      if ( positionGraderVOs != null && positionGraderVOs.size() > 0 )
      {
         for ( PositionGradeVO positionGradeVO : positionGraderVOs )
         {
            String gradeName = "";

            // ������������ȡTitle
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               gradeName = positionGradeVO.getGradeNameZH();
            }
            else
            {
               gradeName = positionGradeVO.getGradeNameEN();
            }

            rs.append( "<li>" );
            rs.append( "<input type=\"checkbox\" id=\"positionGradeIdArray\" name=\"positionGradeIdArray\" value=\"" + positionGradeVO.getPositionGradeId() + "\" "
                  + ( checked( positionGradeVO.getPositionGradeId(), reportHeaderVO.getPositionGradeIds() ) ? "checked" : "" ) + "/>" + gradeName );
            rs.append( "</li>" );
         }
      }

      return rs.toString();
   }

   // �жϵ�ǰ�ڵ��Ƿ���Ҫѡ��
   private static boolean checked( final String positionGradeId, final String positionGradeIds )
   {
      if ( KANUtil.filterEmpty( positionGradeIds ) != null )
      {
         final String[] positionGradeIdArray = KANUtil.jasonArrayToStringArray( positionGradeIds );
         for ( String temp : positionGradeIdArray )
         {
            if ( temp != null && temp.trim().equals( positionGradeId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public static XSSFWorkbook generateReportExcel( final HttpServletRequest request, final String accessAction, final String reportHeaderId ) throws KANException
   {
      // ���Log Start����
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );

      // ��ֵ
      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( tableDTO != null && tableDTO.getReportDTOByReportHeaderId( reportHeaderId ) != null && pagedListHolder != null )
      {
         // ��ʼ��ReportDTO
         final ReportDTO reportDTO = tableDTO.getReportDTOByReportHeaderId( reportHeaderId );

         // ��������
         final XSSFFont font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // ������Ԫ����ʽ
         final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // ������Ԫ����ʽ
         final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // ������Ԫ����ʽ
         final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // �������
         final XSSFSheet sheet = workbook.createSheet( reportDTO.getReportName( request ) );
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            // ����Excel Header Row
            final XSSFRow rowHeader = sheet.createRow( 0 );

            // ���Ա�ʶHeader�����
            int headerColumnIndex = 0;

            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               // ��ʼ��ReportDetailVO
               reportDetailVO.reset( null, request );

               final XSSFCell cell = rowHeader.createCell( headerColumnIndex );
               final XSSFRichTextString text = new XSSFRichTextString( getColumnName( request, reportDetailVO ) );
               cell.setCellValue( text );
               cell.setCellStyle( cellStyleLeft );

               // ��ʼ��Excel�п�����û�����̶��п�����
               if ( reportDetailVO.getColumnWidthType() != null && reportDetailVO.getColumnWidthType().trim().equals( "2" ) && reportDetailVO.getColumnWidth() != null
                     && reportDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // ����Excel�п�ȡ��
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( reportDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // ����Excel�п�
                  sheet.setColumnWidth( headerColumnIndex, columnWidth.intValue() );
               }

               headerColumnIndex++;
            }

            // ��������Excel Body
            if ( pagedListHolder.getSourceMap() != null && pagedListHolder.getSourceMap().size() > 0 )
            {
               // ���Ա�ʶBody�����
               int bodyRowIndex = 1;

               // ������
               for ( Object object : pagedListHolder.getSourceMap() )
               {
                  // ����Excel Body Row
                  final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

                  // ���Ա�ʶBody�����
                  int bodyColumnIndex = 0;

                  // ������
                  for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
                  {
                     // ��ʼ���ֶζ���
                     final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                     // tableDTO.getColumnVOByColumnId(
                     // reportDetailVO.getColumnId() );

                     final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                     final XSSFRichTextString text = new XSSFRichTextString( columnVO != null ? getColumnValue( request, columnVO, reportDetailVO, object ) : "" );
                     // ���õ�Ԫ��ֵ
                     cell.setCellValue( text );

                     // ���õ�Ԫ�����
                     cell.setCellStyle( cellStyleLeft );
                     if ( reportDetailVO.getAlign() != null )
                     {
                        if ( reportDetailVO.getAlign().trim().equals( "2" ) )
                        {
                           cell.setCellStyle( cellStyleCenter );
                        }
                        else if ( reportDetailVO.getAlign().trim().equals( "3" ) )
                        {
                           cell.setCellStyle( cellStyleRight );
                        }
                     }

                     bodyColumnIndex++;
                  }

                  bodyRowIndex++;
               }
            }
         }
      }

      // ���Log End����
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") End." );

      return workbook;
   }

   private static ReportDTO getReportDTOByReportHeaderId( final String reportHeaderId, final List< ReportDTO > reportDTOs ) throws KANException
   {
      if ( reportDTOs != null && reportDTOs.size() > 0 )
      {
         for ( ReportDTO reportDTO : reportDTOs )
         {
            if ( reportDTO.getReportHeaderVO().getReportHeaderId().equals( reportHeaderId ) )
            {
               return reportDTO;
            }
         }
      }

      return null;
   }

   // ��ȡ������MappingVO��ʽ
   @SuppressWarnings("unchecked")
   private static List< MappingVO > getMappingVOsByCondition( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // ��ʼ��ClientId
      final String corpId = BaseAction.getCorpId( request, null );

      // ��ʼ��MappingVO�б�
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // ���������� - ϵͳ����
      if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
      {
         // ���ϵͳ����ѡ���б�
         final List< MappingVO > systemOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
         // ����ϵͳ����ѡ��
         if ( systemOptions != null && systemOptions.size() > 0 )
         {
            for ( MappingVO systemOption : systemOptions )
            {
               // ���ϵͳ����ѡ��
               if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  mappingVOs = KANUtil.getMappings( request.getLocale(), systemOption.getMappingTemp() );
                  break;
               }
            }
         }
      }
      // ���������� - �˻�����
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "2" ) )
      {
         // ����˻�����ѡ���б�
         final List< MappingVO > accountOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
         // �����˻�����ѡ��
         if ( accountOptions != null && accountOptions.size() > 0 )
         {
            for ( MappingVO accountOption : accountOptions )
            {
               // ����˻�����ѡ��
               if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  // ��ʼ��Parameter Array
                  String parameters[];

                  if ( KANUtil.filterEmpty( corpId ) != null )
                  {
                     parameters = new String[] { request.getLocale().getLanguage(), corpId };
                  }
                  else
                  {
                     parameters = new String[] { request.getLocale().getLanguage() };
                  }

                  mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                  // ��ӿյ�MappingVO����
                  mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                  break;
               }
            }
         }
      }
      // ���������� - �û��Զ���
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "3" ) )
      {
         mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
      }
      // ���������� - ֱ��ֵ
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "4" ) )
      {
         // ����û������ֱ��ֵ���Ҳ�Ϊ��
         if ( KANUtil.filterEmpty( columnVO.getOptionValue() ) != null )
         {
            // ���û������ֱ��ֵתΪJSONObject
            final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
            // ����JSONObject
            final Iterator< ? > keyIterator = optionsJSONObject.keys();
            while ( keyIterator.hasNext() )
            {
               final String key = ( String ) keyIterator.next();
               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( key );
               mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
               // ���MappingVO��List
               mappingVOs.add( mappingVO );
            }
         }
      }
      // ���������� - Ԥ��
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "5" ) )
      {
         if ( columnVO.getNameDB().equals( "businessTypeId" ) )
         {
            mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBusinessTypes( request.getLocale().getLanguage(), corpId );
         }
         else if ( columnVO.getNameDB().equals( "entityId" ) )
         {
            mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getEntities( request.getLocale().getLanguage(), corpId );
         }
         else if ( columnVO.getNameDB().equals( "templateId" ) )
         {
            mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLaborContractTemplates( request.getLocale().getLanguage(), corpId );
         }
      }

      return mappingVOs;
   }

   /**
    * ��ȡ�ӱ��column �ļ���
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   static private Map< String, ColumnVO > getSubTableColumnsMap( final ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      Map< String, ColumnVO > columnMap = new HashMap< String, ColumnVO >();
      // ��ʼ��Service�ӿ�

      TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // �����ӱ�
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();
      List columnVOList = null;
      ColumnVO columnVO = null;
      columnVOList = tableDTO.getAllColumnVO();
      // ���������ӱ��column
      if ( columnVOList != null && columnVOList.size() > 0 )
      {
         for ( Object object : columnVOList )
         {
            columnVO = ( ColumnVO ) object;
            columnMap.put( columnVO.getColumnId(), columnVO );
         }
      }
      // װ�������ӱ��column
      if ( tableRelationSubVOs != null && tableRelationSubVOs.size() > 0 )
      {
         for ( TableRelationSubVO tableRelationSubVO : tableRelationSubVOs )
         {
            tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, response ) ).getTableDTOByTableId( tableRelationSubVO.getSlaveTableId() );
            columnVOList = tableDTO.getAllColumnVO();
            if ( columnVOList != null && columnVOList.size() > 0 )
            {
               for ( Object object : columnVOList )
               {
                  columnVO = ( ColumnVO ) object;
                  columnMap.put( columnVO.getColumnId(), columnVO );
               }
            }
         }
      }

      return columnMap;

   }

   /**
    * ��ȡcity ���Ӳ�ѯ
    * 
    * @param cityId
    * @return
    */
   static public String getCitySql( String cityId )
   {

      return " (SELECT cityNameZH FROM hro_sys_city city WHERE city.cityId = " + cityId + ") ";
   }

   /**
    * ��ȡprovince �Ӳ�ѯ
    * 
    * @param cityId
    * @return
    */
   static public String getProvinceSql( String cityId )
   {

      return " (SELECT province.provinceNameZH FROM hro_sys_city city LEFT JOIN hro_sys_province province ON province.provinceId = city.provinceId WHERE city.cityId =  " + cityId
            + ") ";
   }

   /**
    * ��ȡprovince exist sql
    * 
    * @param cityId,provinceId
    * @return
    */
   static public String getProvinceExistSql( String provinceId, String cityId )
   {
      return " EXISTS (SELECT 1 FROM hro_sys_city city WHERE city.cityId = " + cityId + " AND city.provinceId = " + provinceId + ") ";
   }

}
