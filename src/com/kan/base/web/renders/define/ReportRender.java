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
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId );

      // 初始化PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );

      // 初始化ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );

      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      // new HashMap<String,ColumnVO>();

      // TableDTO subTableDTO = null;
      // 转载所有columnVO 包括 子表
      // //主表
      // for(ColumnVO columnVO:tableDTO.getAllColumnVO()){
      // columnMap.put(columnVO.getColumnId(), columnVO);
      // }
      //
      // 遍历Search
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && tableDTO.getReportDTOs().size() > 0 )
      {
         // 初始化ReportDTO
         final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

         if ( reportDTO != null )
         {
            // 初始化ReportHeaderVO
            final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();

            // 初始化Search Name
            String searchName = "";
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               searchName = reportHeaderVO.getNameZH();
            }
            else
            {
               searchName = reportHeaderVO.getNameEN();
            }

            // 初始化搜索对应的链接
            String listAction = "reportHeaderAction.do?proc=execute_object";

            rs.append( "<div class=\"box searchForm toggableForm\" id=\"Search-Information\">" );
            rs.append( "<div class=\"head\">" );
            rs.append( "<label>" + searchName + "</label>" );
            rs.append( "</div>" );
            rs.append( "<a class=\"toggle tiptip\" title=\"" + KANUtil.getProperty( request.getLocale(), "public.hideoptions" ) + "\">&gt;</a>" );

            // 如果存在搜索条件
            //            if ( reportDTO.getReportSearchDetailVOs() != null && reportDTO.getReportSearchDetailVOs().size() > 0 )
            //            {
            // 初始化Search Div样式
            String searchDivStyle = "";
            // 如果不是搜索优先，则隐藏Search Div
            if ( !reportDTO.isSearchFirst() )
            {
               searchDivStyle = " style=\"display: none;\" ";
            }

            // SubAction处理
            String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
            if ( subAction == null || subAction.trim().equalsIgnoreCase( "null" ) )
            {
               subAction = "";
            }

            rs.append( "<div id=\"searchDiv\" class=\"inner\" " + searchDivStyle + ">" );
            rs.append( "<div class=\"top\"> " );

            // 如果导出方式是直接导出&&是搜索优先
            if ( reportDTO.isExportFirst() && reportDTO.isSearchFirst() )
            {
               rs.append( "<input type=\"button\" id=\"searchBtn\" name=\"searchBtn\" class=\"searchBtn\" value=\"导出Excel文件\" onclick=\"linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction="
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

            // 遍历报表搜索字段
            for ( ReportSearchDetailVO reportSearchDetailVO : reportDTO.getReportSearchDetailVOs() )
            {
               // 如果为空或显示
               if ( reportSearchDetailVO.getDisplay() == null || !reportSearchDetailVO.getDisplay().trim().equals( "2" ) )
               {
                  // 初始化字段对象
                  final ColumnVO columnVO = columnMap.get( reportSearchDetailVO.getColumnId() );

                  // 初始化Clomun Name
                  String columnName = "";
                  if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                  {
                     columnName = reportSearchDetailVO.getNameZH();
                  }
                  else
                  {
                     columnName = reportSearchDetailVO.getNameEN();
                  }

                  // 初始化控件的ID和Name
                  String id_name = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();

                  // 初始化控件的Length
                  String maxLenght = "";
                  if ( KANUtil.filterEmpty( columnVO.getValidateLengthMax(), "0" ) != null )
                  {
                     maxLenght = columnVO.getValidateLengthMax();
                  }

                  // 初始化MappingVO列表
                  final List< MappingVO > mappingVOs = getMappingVOsByCondition( request, columnVO );

                  // 当前Input的Style
                  String styleInput = "";
                  if ( KANUtil.filterEmpty( columnVO.getCssStyle() ) != null )
                  {
                     styleInput = columnVO.getCssStyle();
                  }

                  // 初始化当前Column的Value
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

                  // 如果是城市
                  if ( columnVO.getNameDB().equals( "cityId" ) && ( KANUtil.filterEmpty( request.getAttribute( id_name + "_provinceId" ) ) != null ) )
                  {
                     // 如果选择了城市 为cityId 只选择了省为选城市 值加上省的前缀
                     provinceId = ( String ) request.getAttribute( id_name + "_provinceId" );
                  }
                  rs.append( "<li>" );
                  rs.append( "<label>" + columnName + "</label>" );

                  // 如果当前字段是文本框
                  if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "1" ) )
                  {

                     // 日期型Column的处理
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
                  // 如果当前字段是下拉框
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "2" ) )
                  {

                     // 省市 要特殊处理 id 初始 省-市 临时处理以后看看有为有好办法
                     if ( columnVO.getNameDB().equals( "cityId" ) )
                     {
                        if ( KANUtil.filterEmpty( value ) == null )
                        {
                           value = "0";
                        }
                        rs.append( "<input type='hidden' id='" + id_name + "_city_value" + "' value='" + value + "'/>" );

                        rs.append( "<select id=\"" + id_name + "_provinceId" + "\" name=\"" + id_name + "_provinceId" + "\" class=\"" + id_name + "_provinceId" + "\" style=\""
                              + styleInput + "\">" );
                        // 初始化MappingVO列表 要获取省的select
                        ColumnVO tempColumnVO = new ColumnVO();
                        tempColumnVO.setOptionType( "2" );
                        tempColumnVO.setOptionValue( "3" );
                        final List< MappingVO > tempMappingVOs = getMappingVOsByCondition( request, tempColumnVO );

                        for ( MappingVO mappingVO : tempMappingVOs )
                        {
                           // 是否需要选中
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
                              // 是否需要选中
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
                  // 如果当前字段是单选框
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "3" ) )
                  {
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        rs.append( "<span>" );
                        for ( MappingVO mappingVO : mappingVOs )
                        {
                           // 是否需要选中
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
                  // 如果当前字段是复选框
                  else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "4" ) )
                  {
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        rs.append( "<span>" );
                        for ( MappingVO mappingVO : mappingVOs )
                        {
                           // 是否需要选中
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
                  // 如果当前字段是文本域
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
      // 初始化TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId );

      // 初始化ReportDTO
      final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      rs.append( "<div class=\"box noHeader\" id=\"search-results\">" );
      rs.append( "<div class=\"inner\">" );

      // <!-- -->

      rs.append( "<div class=\"top\">" );
      rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );

      // 判断报表列表是否需要添加导出功能
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
      // 初始化PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );
      // 列值
      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      //
      String sortColumn = ( String ) request.getAttribute( "sortColumn" );
      String sortOrder = ( String ) request.getAttribute( "sortOrder" );
      String defaultSortColumn = ( String ) request.getAttribute( "defaultSortColumn" );

      String nextSortOrder = "";
      String columnId = "";
      String thStyle = "";

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();
      // 遍历Column Group
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && pagedListHolder != null )
      {
         // 初始化ReportDTO
         final ReportDTO reportDTO = getReportDTOByReportHeaderId( reportHeaderId, tableDTO.getReportDTOs() );

         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            // 初始化ReportHeaderVO
            final ReportHeaderVO reportHeaderVO = reportDTO.getReportHeaderVO();

            // 遍历生成Table Head
            rs.append( "<thead>" );
            rs.append( "<tr>" );

            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               nextSortOrder = "";
               columnId = "";
               thStyle = "";
               if ( reportDetailVO.getDisplay() == null || !reportDetailVO.getDisplay().trim().equals( "2" ) )
               {
                  // 初始化ReportDetailVO
                  reportDetailVO.reset( null, request );

                  // 初始化字段对象
                  final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                  columnId = "T_" + columnVO.getTableId() + "." + columnVO.getNameDB();
                  // tableDTO.getColumnVOByColumnId(
                  // reportDetailVO.getColumnId() );

                  // 初始化列表字段样式

                  if ( reportDetailVO.getColumnWidth() != null && !reportDetailVO.getColumnWidth().trim().equals( "" ) )
                  {
                     // 初始化Width Type，默认是百分比
                     String widthType = "%";
                     if ( reportDetailVO.getColumnWidthType() != null )
                     {
                        // 列宽按照象素显示
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

                  // 初始化列表字体大小
                  String valueStyle = "";
                  if ( KANUtil.filterEmpty( reportDetailVO.getFontSize(), "0" ) != null )
                  {
                     valueStyle = valueStyle + "font-size: " + reportDetailVO.getFontSize() + "px;";
                  }
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     valueStyle = " style=\"" + valueStyle + "\" ";
                  }

                  // 初始化排序与否
                  boolean sort = true;
                  if ( KANUtil.filterEmpty( reportDetailVO.getSort() ) != null && reportDetailVO.getSort().trim().equals( "2" ) )
                  {
                     sort = false;
                  }

                  // 列标题
                  rs.append( "<th " + thStyle + " class=\"" );

                  // 排序设置
                  if ( sort )
                  {
                     //页面排序
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

                        //解析默认排序
                        if ( StringUtils.isNotBlank( defaultSortColumn ) && StringUtils.isBlank( sortColumn ) )
                        {

                           // 默认排序
                           final JSONObject jsonObject = JSONObject.fromObject( defaultSortColumn );
                           // 遍历排序字段
                           for ( Object objKey : jsonObject.keySet() )
                           {
                              //如果排序字段等于当前的 th 对应的列
                              if ( ( ( String ) objKey ).equals( columnVO.getColumnId() ) )
                              {
                                 nextSortOrder = pagedListHolder.getNextSortOrder( ( ( String ) jsonObject.get( objKey ) ).replace( "（升）", "" ).replace( "（降）", "" ) );
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

                  // 无关联的情况不操作
                  if ( columnVO != null )
                  {
                     rs.append( pagedListHolder.getCurrentSortClass( columnVO.getNameDB() ) );
                  }
                  rs.append( "\">" );

                  // 无关联的情况不添加链接
                  if ( columnVO != null && sort )
                  {
                     // T_3.coulunName
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + columnId + "', '" + nextSortOrder + "', 'tableWrapper');\">" );
                  }

                  // 如果需要设置样式
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // 调用内部方法获得列名
                  rs.append( getColumnName( request, reportDetailVO ) );

                  // 如果需要设置样式
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "</span>" );
                  }

                  // 无关联的情况不添加链接
                  if ( columnVO != null && sort )
                  {
                     rs.append( "</a>" );
                  }
                  rs.append( "</th>" );

               }
            }

            rs.append( "</tr>" );
            rs.append( "</thead>" );

            // 遍历生成Table Body
            if ( pagedListHolder.getSourceMap() != null && pagedListHolder.getSourceMap().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index 用以判断当前的行号，区分列表不同行的样式
               int index = 0;
               // 遍历行
               for ( Object object : pagedListHolder.getSourceMap() )
               {
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  rs.append( "<tr class='" + trClass + "'>" );

                  // 遍历列
                  for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
                  {
                     if ( reportDetailVO.getDisplay() == null || !reportDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // 初始化字段对象
                        final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                        // tableDTO.getColumnVOByColumnId(
                        // reportDetailVO.getColumnId() );

                        // 初始化列表字段样式
                        String valueStyle = "";
                        if ( KANUtil.filterEmpty( reportDetailVO.getFontSize(), "0" ) != null )
                        {
                           valueStyle = valueStyle + "font-size: " + reportDetailVO.getFontSize() + "px;";
                        }
                        if ( KANUtil.filterEmpty( valueStyle ) != null )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }
                        // 初始化动态参数<!---->

                        rs.append( "<td align=\"" + reportDetailVO.getDecodeAlignTemp() + "\">" );

                        // 如果当前字段是带链接的<!---->

                        if ( columnVO != null )
                        {
                           // 如果需要设置样式
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( getColumnValue( request, columnVO, reportDetailVO, object ) );

                           // 如果需要设置样式
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "</span>" );
                           }
                        }

                        // 如果当前字段是带链接的<!---->

                        // 如果存在附加内容<!---->

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
               // 生成Table Foot
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( reportDTO.getReportDetailVOs().size() ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "： " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "：" + pagedListHolder.getIndexStart() + " - "
                     + pagedListHolder.getIndexEnd() + "</label> " );
               rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getFirstPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.first" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getPreviousPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.previous" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getNextPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.next" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getLastPage() + "', null, null, 'tableWrapper');\">"
                     + KANUtil.getProperty( request.getLocale(), "page.last" ) + "</a></label> " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "：" + pagedListHolder.getRealPage() + "/"
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
      // 添加Log Start跟踪
      LogFactory.getLog( reportHeaderId ).info( "SQL Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // 初始化StringBuffer
      final StringBuffer rsColumn = new StringBuffer();
      final StringBuffer rsTable = new StringBuffer();
      final StringBuffer rsWhere = new StringBuffer();
      // 初始化 Service接口
      final DBColumnUtilService dbColumnUtilService = ( DBColumnUtilService ) BaseAction.getService( "dbColumnUtilService" );
      // 表dto
      if ( tableDTO != null && tableDTO.getReportDTOs() != null && tableDTO.getReportDTOs().size() > 0 )
      {

         // ReportDTO tempReportDTO = tableDTO.getReportDTOByReportHeaderId(
         // reportHeaderId );
         // 报表DTO
         final ReportDTO reportDTO = tableDTO.getReportDTOByReportHeaderId( reportHeaderId );

         if ( reportDTO != null )
         {
            // 初始化主表名 别名
            String tableAccess = "T_" + tableDTO.getTableVO().getTableId();

            String tableName = tableDTO.getTableVO().getAccessName();
            // 初始化主表id
            String tableId = tableDTO.getTableVO().getTableId();
            // 初始主表的where条件
            rsWhere.append( " WHERE " + tableAccess + ".deleted = 1 " );

            rsWhere.append( " AND " + tableAccess + ".accountId = " + reportHeaderVO.getAccountId() );
            //增加权限
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

            // 主表的from
            rsTable.append( " FROM " + tableName + " " + tableAccess );
            // column
            rsColumn.append( " SELECT " );
            // 子表表名
            String subTableAccess = null;

            // 遍历查询字段 select
            if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
            {
               for ( ReportDetailVO tempReportDetailVO : reportDTO.getReportDetailVOs() )
               {
                  // 初始化字段对象
                  final ColumnVO columnVO = columnMap.get( tempReportDetailVO.getColumnId() );

                  if ( columnVO != null )
                  {
                     tableAccess = "T_" + columnVO.getTableId();

                     // 如果是自定义字段
                     if ( !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                     {
                        // Remark1只查询一次
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
                        //处理聚合函数
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
                           //正常列
                        }
                        else
                        {
                           rsColumn.append( tableAccess + "." + columnVO.getNameDB() + " AS " + tableAccess + "_" + columnVO.getColumnId() );
                        }
                     }
                     rsColumn.append( "," );
                  }

               }

               // 去除最后逗号
               rsColumn.deleteCharAt( rsColumn.length() - 1 );
            }
            // join
            for ( String subTId : subTableMap.keySet() )
            {
               for ( TableRelationVO rableRelationVO : tableDTO.getTableRelationVOs() )
               {
                  // 找到对应的表关系
                  if ( tableId.equals( rableRelationVO.getMasterTableId() ) && subTId.equals( rableRelationVO.getSlaveTableId() ) )
                  {
                     // rableRelationVO.getJoinOn();
                     // 拼接join join 格式 {masterTableAs}.userId =
                     // {slaveTableAs}.userId 替换表名 替换别名
                     String subTableName = KANUtil.filterEmpty( subTableMap.get( subTId ).getTableVO().getAccessTempName() ) != null ? subTableMap.get( subTId ).getTableVO().getAccessTempName()
                           : subTableMap.get( subTId ).getTableVO().getAccessName();
                     rsTable.append( " " + rableRelationVO.getJoinType() + " " + subTableName + " AS T_" + rableRelationVO.getSlaveTableId() + " ON T_"
                           + rableRelationVO.getMasterTableId() + "." + rableRelationVO.getMasterColumn() + " = T_" + rableRelationVO.getSlaveTableId() + "."
                           + rableRelationVO.getSlaveColumn() );
                     // 拼接 where
                     // 子表的别名
                     subTableAccess = "T_" + rableRelationVO.getSlaveTableId();
                     rsTable.append( " AND " + subTableAccess + ".deleted = 1 " );

                     rsTable.append( dbColumnUtilService.getAuthStr( request, response, subTableName, subTableAccess ) );
                     break;
                  }
               }
            }

            String groupByAccess = null;
            String orderByAccess = null;
            // 遍历查询条件
            if ( reportSearchDetailVOList != null && reportSearchDetailVOList.size() > 0 )
            {
               for ( ReportSearchDetailVO tempReportSearchDetailVO : reportSearchDetailVOList )
               {
                  // 初始化字段对象
                  final ColumnVO columnVO = columnMap.get( tempReportSearchDetailVO.getColumnId() );
                  final String columnDB = columnVO.getNameDB();

                  tableAccess = "T_" + columnVO.getTableId();
                  // temString 为省份值，
                  if ( ( KANUtil.filterEmpty( tempReportSearchDetailVO.getRange() ) != null || KANUtil.filterEmpty( tempReportSearchDetailVO.getContent() ) != null )
                        && KANUtil.filterEmpty( columnDB ) != null )
                  {

                     // 当前字段为下拉框
                     if ( KANUtil.filterEmpty( columnVO.getInputType(), "0" ) != null && columnVO.getInputType().equals( "2" ) )
                     {
                        //tempStr 存放省份的值
                        if ( KANUtil.filterEmpty( tempReportSearchDetailVO.getTempStr() ) != null && KANUtil.filterEmpty( tempReportSearchDetailVO.getTempStr(), "0" ) != null
                              && KANUtil.filterEmpty( columnDB ) != null )
                        {
                           //只有省份时 拼接处 exist 语句
                           if ( columnVO.getNameDB().equals( "cityId" ) )
                           {
                              rsWhere.append( " AND " + getProvinceExistSql( tempReportSearchDetailVO.getTempStr(), tableAccess + "." + columnDB ) );
                           }
                        }

                        // 附加字段
                        rsWhere.append( " AND (" + tableAccess + "." + columnDB + " = " + tempReportSearchDetailVO.getContent() + " OR 0 = "
                              + tempReportSearchDetailVO.getContent() + ")" );
                     }
                     else
                     {
                        // 如果条件是 “=”
                        if ( tempReportSearchDetailVO.getCondition().equals( "1" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " = '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // 如果条件是 “>”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "2" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " < '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // 如果条件是 “>”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "3" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " <= '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // 如果条件是 “>”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "4" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " > '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // 如果条件是 “>”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "5" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " >= '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                        // 如果条件是 “like”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "6" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " LIKE CONCAT('%', '" + tempReportSearchDetailVO.getContent() + "', '%')" );
                        }
                        // 如果条件是 “in”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "7" ) )
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " IN (" + tempReportSearchDetailVO.getContent() + ")" );
                        }
                        // 如果条件是 “between”
                        else if ( tempReportSearchDetailVO.getCondition().equals( "8" ) )
                        {
                           final String[] temp = tempReportSearchDetailVO.getRange().split( "_" );
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " BETWEEN " + temp[ 0 ] + " AND " + temp[ 1 ] );
                        }
                        // 其他
                        else
                        {
                           rsWhere.append( " AND " + tableAccess + "." + columnDB + " = '" + tempReportSearchDetailVO.getContent() + "'" );
                        }
                     }
                  }
               }
            }
            // 存在分组字段
            if ( KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
            {
               rsWhere.append( " GROUP BY " );

               // 初始化GroupColumns数组
               final String[] groupColumns = KANUtil.jasonArrayToStringArray( reportHeaderVO.getGroupColumns() );

               // 遍历分组字段
               for ( String tempGroupColumn : groupColumns )
               {
                  // 初始化字段对象
                  final ColumnVO columnVO = columnMap.get( tempGroupColumn );
                  groupByAccess = "T_" + columnVO.getTableId();
                  rsWhere.append( groupByAccess + "." + columnVO.getNameDB() + "," );
               }

               // 去除最后逗号
               rsWhere.deleteCharAt( rsWhere.length() - 1 );
            }

            // 存在排序字段
            if ( KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null || KANUtil.filterEmpty( sortColumns ) != null )
            {
               rsWhere.append( " ORDER BY " );

               // 初始化JSONObject 页面排序为空则只用原始排序
               if ( KANUtil.filterEmpty( sortColumns ) != null )
               {
                  rsWhere.append( sortColumns );
               }
               else
               {
                  // 默认排序
                  final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );

                  // 遍历排序字段
                  for ( Object objKey : jsonObject.keySet() )
                  {
                     // 初始化字段对象
                     final ColumnVO columnVO = columnMap.get( String.valueOf( objKey ) );
                     orderByAccess = "T_" + columnVO.getTableId();

                     // 排序字段名{'4401':'desc'} {'nameZH':'desc'}
                     final String sortColumnName = columnVO != null ? orderByAccess + "." + columnVO.getNameDB() : String.valueOf( objKey );
                     rsWhere.append( sortColumnName + " " + String.valueOf( jsonObject.get( objKey ) ).replace( "（升）", "" ).replace( "（降）", "" ) + "," );
                  }
                  rsWhere.deleteCharAt( rsWhere.length() - 1 );
               }

               // 去除最后逗号
            }
         }
      }

      String sqlStr = rsColumn.toString() + " " + rsTable.toString() + " " + rsWhere.toString();
      System.out.println( sqlStr );
      // 添加Log Start跟踪
      LogFactory.getLog( reportHeaderId ).info( "SQL Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );
      return sqlStr.toString();
   }

   // 获得字段名
   private static String getColumnName( final HttpServletRequest request, final ReportDetailVO reportDetailVO ) throws KANException
   {
      return request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) ? reportDetailVO.getNameZH() : reportDetailVO.getNameEN();
   }

   // 获得字段值
   private static String getColumnValue( final HttpServletRequest request, final ColumnVO columnVO, final ReportDetailVO reportDetailVO, final Object object ) throws KANException
   {
      // 列表字段名
      String fieldName = columnVO.getNameDB();
      String fieldId = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();

      // 初始化当前Column的Value
      String value = "";
      // 如果是系统定义的字段
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
      // 如果是用户自定义的字段
      else
      {
         try
         {
            // 如果数据对象不为空
            if ( object != null )
            {
               // 从Object的Remark1字段获取Jason字符串
               final Object jsonString = ( ( Map ) object ).get( "T_" + columnVO.getTableId() + "_remark1" );
               if ( jsonString != null && !( ( String ) jsonString ).trim().equals( "" ) )
               {
                  // 初始化Jason对象 - 含有当前字段的值
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

      // 如果需要转译
      if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && reportDetailVO.getIsDecoded() != null && reportDetailVO.getIsDecoded().equals( ListDetailVO.TRUE )
            && KANUtil.filterEmpty( fieldName ) != null && KANUtil.filterEmpty( value ) != null )
      {
         // 下拉框转译
         if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().equals( "2" ) )
         {
            if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
               value = new ReportHeaderVO().decodeField( value, getMappingVOsByCondition( request, columnVO ), true );
            else
               value = new ReportHeaderVO().decodeField( value, getMappingVOsByCondition( request, columnVO ) );
         }
         // 文本框转译（特殊处理）
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().equals( "1" ) )
         {
            String result = "";
            for ( String str : value.split( "," ) )
            {
               if ( KANUtil.filterEmpty( result ) == null )
               {
                  // _temp字段 部门类型
                  if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
                  {
                     result = new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ), true );
                  }
                  // _temp字段 所属人类型
                  else if ( fieldName.contains( "owner" ) || fieldName.contains( "Owner" ) )
                  {
                     result = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffNamesByPositionId( request.getLocale().getLanguage(), str );
                  }
                  // 其他
                  else
                  {
                     result = new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ) );
                  }
               }
               else
               {
                  if ( fieldName.contains( "branch" ) || fieldName.contains( "Branch" ) )
                  {
                     result = result + "、" + new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ), true );
                  }
                  // _temp字段 所属人类型
                  else if ( fieldName.contains( "owner" ) || fieldName.contains( "Owner" ) )
                  {
                     result = result + "、"
                           + KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getStaffNamesByPositionId( request.getLocale().getLanguage(), str );
                  }
                  else
                  {
                     result = result + "、" + new ReportHeaderVO().decodeField( str, getMappingVOsByCondition( request, columnVO ) );
                  }
               }
            }

            value = result;
         }
      }

      // 例外字符传处理
      if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
      {
         value = "";
      }

      // 如果用户设置日期格式化
      if ( KANUtil.filterEmpty( reportDetailVO.getDatetimeFormat(), "0" ) != null && KANUtil.filterEmpty( value ) != null )
      {
         value = new SimpleDateFormat( reportDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
      }

      // 如果用户设置小数格式化
      if ( KANUtil.filterEmpty( reportDetailVO.getAccuracy(), "0" ) != null && KANUtil.filterEmpty( value ) != null
            && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
      {
         // 默认为四舍五入
         int roundType = BigDecimal.ROUND_HALF_UP;

         if ( reportDetailVO.getRound() != null )
         {
            // 截取
            if ( reportDetailVO.getRound().trim().equals( "2" ) )
            {
               roundType = BigDecimal.ROUND_DOWN;
            }
            // 带符号向上进位，正数进位，负数截取
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
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getReportHeaderId() ) != null )
      {
         // 获得TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( reportHeaderVO.getAccountId() ).getTableDTOByTableId( reportHeaderVO.getTableId() );
         tableDTO.getReportDTOByReportHeaderId( reportHeaderVO.getReportHeaderId() );
         Map< String, ColumnVO > columnVOMap = getSubTableColumnsMap( reportHeaderVO, request, null );

         if ( tableDTO != null && KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null )
         {
            // 初始化JSONObject
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );

            // 遍历排序字段
            for ( Object key : jsonObject.keySet() )
            {
               // 获取ColumnVO
               ColumnVO columnVO = columnVO = columnVOMap.get( String.valueOf( key ) );
               if ( columnVO != null )
               {
                  rs.append( "<li>" );
                  rs.append( "<input type=\"hidden\" id=\"sort_" + key + " \" value=\"" + key + "\" />" );
                  rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeSortColumn('" + key
                        + "');\"/>" );
                  rs.append( "&nbsp;&nbsp;" );
                  rs.append( ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? columnVO.getNameZH() : columnVO.getNameEN() ) + "：" + jsonObject.get( key ) );
                  rs.append( "</li>" );
               }
            }
         }
      }

      return rs.toString();
   }

   public static String getReportHeaderGroupColumn( final HttpServletRequest request, final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getReportHeaderId() ) != null )
      {
         // 获得TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( reportHeaderVO.getAccountId() ).getTableDTOByTableId( reportHeaderVO.getTableId() );
         Map< String, ColumnVO > columnVOMap = getSubTableColumnsMap( reportHeaderVO, request, null );
         if ( tableDTO != null && KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
         {
            final String[] groupColumnArray = reportHeaderVO.getGroupColumns().replace( "{", "" ).replace( "}", "" ).split( ":" );

            // 遍历分组字段
            for ( String groupColumn : groupColumnArray )
            {
               // 获取ColumnVO
               ColumnVO columnVO = columnVO = columnVOMap.get( groupColumn );
               if ( columnVO != null )
               {
                  rs.append( "<li>" );
                  rs.append( "<input type=\"hidden\" id=\"group_" + groupColumn + " \" value=\"" + groupColumn + "\" />" );
                  rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" onClick=\"removeGroupColumn('"
                        + groupColumn + "');\"/>" );
                  rs.append( "&nbsp;&nbsp;" );
                  rs.append( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? columnVO.getNameZH() : columnVO.getNameEN() );

                  // 存在统计字段  隐藏
                  if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
                  {
                     // 初始化JSONObject
                     final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );

                     for ( Object key : jsonObject.keySet() )
                     {
                        if ( key.equals( groupColumn ) )
                        {
                           //rs.append("：" + jsonObject.get(key));
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

            // 按照语言设置取Title
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

   // 判断当前节点是否需要选中
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
      // 添加Log Start跟踪
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // 初始化PagedListHolder
      final PagedReportListHolder pagedListHolder = ( PagedReportListHolder ) request.getAttribute( "pagedListHolder" );

      // 列值
      Map< String, ColumnVO > columnMap = ( Map< String, ColumnVO > ) request.getAttribute( "columnMap" );

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( tableDTO != null && tableDTO.getReportDTOByReportHeaderId( reportHeaderId ) != null && pagedListHolder != null )
      {
         // 初始化ReportDTO
         final ReportDTO reportDTO = tableDTO.getReportDTOByReportHeaderId( reportHeaderId );

         // 创建字体
         final XSSFFont font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // 创建单元格样式
         final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // 创建单元格样式
         final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // 创建单元格样式
         final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // 创建表格
         final XSSFSheet sheet = workbook.createSheet( reportDTO.getReportName( request ) );
         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         if ( reportDTO.getReportDetailVOs() != null && reportDTO.getReportDetailVOs().size() > 0 )
         {
            // 生成Excel Header Row
            final XSSFRow rowHeader = sheet.createRow( 0 );

            // 用以标识Header列序号
            int headerColumnIndex = 0;

            for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
            {
               // 初始化ReportDetailVO
               reportDetailVO.reset( null, request );

               final XSSFCell cell = rowHeader.createCell( headerColumnIndex );
               final XSSFRichTextString text = new XSSFRichTextString( getColumnName( request, reportDetailVO ) );
               cell.setCellValue( text );
               cell.setCellStyle( cellStyleLeft );

               // 初始化Excel列宽，针对用户定义固定列宽的情况
               if ( reportDetailVO.getColumnWidthType() != null && reportDetailVO.getColumnWidthType().trim().equals( "2" ) && reportDetailVO.getColumnWidth() != null
                     && reportDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // 换算Excel列宽并取整
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( reportDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // 设置Excel列宽
                  sheet.setColumnWidth( headerColumnIndex, columnWidth.intValue() );
               }

               headerColumnIndex++;
            }

            // 遍历生成Excel Body
            if ( pagedListHolder.getSourceMap() != null && pagedListHolder.getSourceMap().size() > 0 )
            {
               // 用以标识Body行序号
               int bodyRowIndex = 1;

               // 遍历行
               for ( Object object : pagedListHolder.getSourceMap() )
               {
                  // 生成Excel Body Row
                  final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

                  // 用以标识Body列序号
                  int bodyColumnIndex = 0;

                  // 遍历列
                  for ( ReportDetailVO reportDetailVO : reportDTO.getReportDetailVOs() )
                  {
                     // 初始化字段对象
                     final ColumnVO columnVO = columnMap.get( reportDetailVO.getColumnId() );
                     // tableDTO.getColumnVOByColumnId(
                     // reportDetailVO.getColumnId() );

                     final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                     final XSSFRichTextString text = new XSSFRichTextString( columnVO != null ? getColumnValue( request, columnVO, reportDetailVO, object ) : "" );
                     // 设置单元格值
                     cell.setCellValue( text );

                     // 设置单元格对齐
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

      // 添加Log End跟踪
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

   // 获取下拉框MappingVO形式
   @SuppressWarnings("unchecked")
   private static List< MappingVO > getMappingVOsByCondition( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // 初始化ClientId
      final String corpId = BaseAction.getCorpId( request, null );

      // 初始化MappingVO列表
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // 下拉框类型 - 系统常量
      if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
      {
         // 获得系统常量选项列表
         final List< MappingVO > systemOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
         // 遍历系统常量选项
         if ( systemOptions != null && systemOptions.size() > 0 )
         {
            for ( MappingVO systemOption : systemOptions )
            {
               // 获得系统常量选项
               if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  mappingVOs = KANUtil.getMappings( request.getLocale(), systemOption.getMappingTemp() );
                  break;
               }
            }
         }
      }
      // 下拉框类型 - 账户常量
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "2" ) )
      {
         // 获得账户常量选项列表
         final List< MappingVO > accountOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
         // 遍历账户常量选项
         if ( accountOptions != null && accountOptions.size() > 0 )
         {
            for ( MappingVO accountOption : accountOptions )
            {
               // 获得账户常量选项
               if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
               {
                  // 初始化Parameter Array
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
                  // 添加空的MappingVO对象
                  mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                  break;
               }
            }
         }
      }
      // 下拉框类型 - 用户自定义
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "3" ) )
      {
         mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
      }
      // 下拉框类型 - 直接值
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "4" ) )
      {
         // 如果用户定义的直接值并且不为空
         if ( KANUtil.filterEmpty( columnVO.getOptionValue() ) != null )
         {
            // 将用户定义的直接值转为JSONObject
            final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
            // 遍历JSONObject
            final Iterator< ? > keyIterator = optionsJSONObject.keys();
            while ( keyIterator.hasNext() )
            {
               final String key = ( String ) keyIterator.next();
               // 初始化MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( key );
               mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
               // 添加MappingVO至List
               mappingVOs.add( mappingVO );
            }
         }
      }
      // 下拉框类型 - 预留
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
    * 获取子表的column 的集合
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
      // 初始化Service接口

      TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // 所有子表
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();
      List columnVOList = null;
      ColumnVO columnVO = null;
      columnVOList = tableDTO.getAllColumnVO();
      // 主表所有子表的column
      if ( columnVOList != null && columnVOList.size() > 0 )
      {
         for ( Object object : columnVOList )
         {
            columnVO = ( ColumnVO ) object;
            columnMap.put( columnVO.getColumnId(), columnVO );
         }
      }
      // 装配所有子表的column
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
    * 获取city 的子查询
    * 
    * @param cityId
    * @return
    */
   static public String getCitySql( String cityId )
   {

      return " (SELECT cityNameZH FROM hro_sys_city city WHERE city.cityId = " + cityId + ") ";
   }

   /**
    * 获取province 子查询
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
    * 获取province exist sql
    * 
    * @param cityId,provinceId
    * @return
    */
   static public String getProvinceExistSql( String provinceId, String cityId )
   {
      return " EXISTS (SELECT 1 FROM hro_sys_city city WHERE city.cityId = " + cityId + " AND city.provinceId = " + provinceId + ") ";
   }

}
