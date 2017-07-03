package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ReportDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7683442992753554770L;

   /**
    * For DB
    */

   // 报表从表ID
   private String reportDetailId;

   // 主表ID
   private String reportHeaderId;

   // 字段ID
   private String columnId;

   // 字段名（中文）
   private String nameZH;

   // 字段名（英文）
   private String nameEN;

   // 字段宽度
   private String columnWidth;

   // 宽度类型
   private String columnWidthType;

   // 顺序
   private String columnIndex;

   // 字体大小
   private String fontSize;

   // 是否转译？
   private String isDecoded;

   // 是否链接？
   private String isLinked;

   // 连接Action
   private String linkedAction;

   // 链接目标方式  
   private String linkedTarget;

   // 日期格式化？
   private String datetimeFormat;

   // 保留小数位
   private String accuracy;

   // 截取方式
   private String round;

   // 对齐方式
   private String align;

   // 是否排序
   private String sort;

   // 是否显示
   private String display;

   // 描述
   private String description;

   //tableId
   private String tableId;

   //统计函数
   private String statisticsFun;

   /**
    * For Application
    */
   // Action中进行初始化

   // decoded字段
   private String decodeColumnId;
   @JsonIgnore
   // 字体大小Mapping
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 连接目标Mapping
   private List< MappingVO > linkedTargets = new ArrayList< MappingVO >();
   @JsonIgnore
   // 日期格式Mapping
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   // 对齐方式Mapping
   private List< MappingVO > aligns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 保留小数Mapping
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 截取位数Mapping
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   // 宽度类型Mapping
   private List< MappingVO > columnWidthTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 聚合函数
   private List< MappingVO > statisticsColumnses = new ArrayList< MappingVO >();

   // 使用聚合函数
   private String statisticsColumns;
   @JsonIgnore
   // 字段列表Mapping
   private List< MappingVO > columns = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      if ( KANUtil.filterEmpty( this.columnId ) != null )
      {
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnVOByColumnId( columnId );
         if ( columnVO != null )
         {
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
            {
               decodeColumnId = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
            }
            else
            {
               decodeColumnId = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
            }
         }
      }
      this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      this.linkedTargets = KANUtil.getMappings( request.getLocale(), "def.list.detail.link.target" );
      this.datetimeFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
      this.aligns = KANUtil.getMappings( request.getLocale(), "def.list.detail.align" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.columnWidthTypies = KANUtil.getMappings( request.getLocale(), "def.list.detail.column.width.type" );
      this.statisticsColumnses = KANUtil.getMappings( request.getLocale(), "def.report.header.statistics.column" );
      this.columns.add( 0, getEmptyMappingVO() );
   }

   @Override
   public void reset() throws KANException
   {
      this.reportHeaderId = "";
      this.columnId = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.columnWidth = "";
      this.columnWidthType = "1";
      this.columnIndex = "0";
      this.fontSize = "13";
      this.isDecoded = "0";
      this.isLinked = "0";
      this.linkedAction = "";
      this.linkedTarget = "";
      this.datetimeFormat = "0";
      this.accuracy = "0";
      this.round = "0";
      this.align = "1";
      this.sort = "1";
      this.display = "1";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ReportDetailVO reportDetailVO = ( ReportDetailVO ) object;
      this.columnId = reportDetailVO.getColumnId();
      this.nameZH = reportDetailVO.getNameZH();
      this.nameEN = reportDetailVO.getNameEN();
      this.columnWidth = reportDetailVO.getColumnWidth();
      this.columnWidthType = reportDetailVO.getColumnWidthType();
      this.columnIndex = reportDetailVO.getColumnIndex();
      this.fontSize = reportDetailVO.getFontSize();
      this.isDecoded = reportDetailVO.getIsDecoded();
      this.isLinked = reportDetailVO.getIsLinked();
      this.linkedAction = reportDetailVO.getLinkedAction();
      this.linkedTarget = reportDetailVO.getLinkedTarget();
      this.datetimeFormat = reportDetailVO.getDatetimeFormat();
      this.accuracy = reportDetailVO.getAccuracy();
      this.round = reportDetailVO.getRound();
      this.sort = reportDetailVO.getSort();
      this.display = reportDetailVO.getDisplay();
      this.align = reportDetailVO.getAlign();
      this.description = reportDetailVO.getDescription();
      super.setStatus( reportDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // 解译字段
   public String getDecodeColumn()
   {
      return decodeColumnId;
   }

   // 解译是否链接？
   public String getDecodeIsLinked()
   {
      return decodeField( isLinked, super.getFlags() );
   }

   // 解译是否转译？
   public String getDecodeIsDecoded()
   {
      return decodeField( isDecoded, super.getFlags() );
   }

   // 解译排列方式
   public String getDecodeAlign()
   {
      return decodeField( align, aligns );
   }

   public String getDecodeAlignTemp()
   {
      return decodeField( align, aligns, true );
   }

   // 解译日期类型
   public String getDecodeDatetimeFormatTemp()
   {
      return decodeField( this.datetimeFormat, this.datetimeFormats, true );
   }

   public String getDecodeAccuracyTemp()
   {
      return decodeField( this.accuracy, this.accuracys, true );
   }

   public String getDecodeStatisticsColumns()
   {
      if ( !"0".equals( this.statisticsColumns ) )
      {
         return decodeField( this.statisticsColumns, this.statisticsColumnses );
      }
      return "";
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( reportDetailId );
   }

   public String getReportDetailId()
   {
      return reportDetailId;
   }

   public void setReportDetailId( String reportDetailId )
   {
      this.reportDetailId = reportDetailId;
   }

   public String getReportHeaderId()
   {
      return reportHeaderId;
   }

   public void setReportHeaderId( String reportHeaderId )
   {
      this.reportHeaderId = reportHeaderId;
   }

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getColumnWidth()
   {
      return columnWidth;
   }

   public void setColumnWidth( String columnWidth )
   {
      this.columnWidth = columnWidth;
   }

   public String getColumnWidthType()
   {
      return columnWidthType;
   }

   public void setColumnWidthType( String columnWidthType )
   {
      this.columnWidthType = columnWidthType;
   }

   public String getColumnIndex()
   {
      return columnIndex;
   }

   public void setColumnIndex( String columnIndex )
   {
      this.columnIndex = columnIndex;
   }

   public String getFontSize()
   {
      return fontSize;
   }

   public void setFontSize( String fontSize )
   {
      this.fontSize = fontSize;
   }

   public String getIsDecoded()
   {
      return isDecoded;
   }

   public void setIsDecoded( String isDecoded )
   {
      this.isDecoded = isDecoded;
   }

   public String getIsLinked()
   {
      return isLinked;
   }

   public void setIsLinked( String isLinked )
   {
      this.isLinked = isLinked;
   }

   public String getLinkedAction()
   {
      return linkedAction;
   }

   public void setLinkedAction( String linkedAction )
   {
      this.linkedAction = linkedAction;
   }

   public String getLinkedTarget()
   {
      return linkedTarget;
   }

   public void setLinkedTarget( String linkedTarget )
   {
      this.linkedTarget = linkedTarget;
   }

   public String getDatetimeFormat()
   {
      return datetimeFormat;
   }

   public void setDatetimeFormat( String datetimeFormat )
   {
      this.datetimeFormat = datetimeFormat;
   }

   public String getAccuracy()
   {
      return accuracy;
   }

   public void setAccuracy( String accuracy )
   {
      this.accuracy = accuracy;
   }

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
   }

   public String getAlign()
   {
      return align;
   }

   public void setAlign( String align )
   {
      this.align = align;
   }

   public String getSort()
   {
      return sort;
   }

   public void setSort( String sort )
   {
      this.sort = sort;
   }

   public String getDisplay()
   {
      return display;
   }

   public void setDisplay( String display )
   {
      this.display = display;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDecodeColumnId()
   {
      return decodeColumnId;
   }

   public void setDecodeColumnId( String decodeColumnId )
   {
      this.decodeColumnId = decodeColumnId;
   }

   public List< MappingVO > getFontSizes()
   {
      return fontSizes;
   }

   public void setFontSizes( List< MappingVO > fontSizes )
   {
      this.fontSizes = fontSizes;
   }

   public List< MappingVO > getLinkedTargets()
   {
      return linkedTargets;
   }

   public void setLinkedTargets( List< MappingVO > linkedTargets )
   {
      this.linkedTargets = linkedTargets;
   }

   public List< MappingVO > getDatetimeFormats()
   {
      return datetimeFormats;
   }

   public void setDatetimeFormats( List< MappingVO > datetimeFormats )
   {
      this.datetimeFormats = datetimeFormats;
   }

   public List< MappingVO > getAligns()
   {
      return aligns;
   }

   public void setAligns( List< MappingVO > aligns )
   {
      this.aligns = aligns;
   }

   public List< MappingVO > getAccuracys()
   {
      return accuracys;
   }

   public void setAccuracys( List< MappingVO > accuracys )
   {
      this.accuracys = accuracys;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   public List< MappingVO > getColumnWidthTypies()
   {
      return columnWidthTypies;
   }

   public void setColumnWidthTypies( List< MappingVO > columnWidthTypies )
   {
      this.columnWidthTypies = columnWidthTypies;
   }

   public List< MappingVO > getStatisticsColumnses()
   {
      return statisticsColumnses;
   }

   public void setStatisticsColumnses( List< MappingVO > statisticsColumnses )
   {
      this.statisticsColumnses = statisticsColumnses;
   }

   public String getStatisticsColumns()
   {
      return statisticsColumns;
   }

   public void setStatisticsColumns( String statisticsColumns )
   {
      this.statisticsColumns = statisticsColumns;
   }

   public List< MappingVO > getColumns()
   {
      return columns;
   }

   public void setColumns( List< MappingVO > columns )
   {
      this.columns = columns;
   }

   public void setValue( final ReportColumnVO reportColumnVO ) throws KANException
   {
      this.reportHeaderId = reportColumnVO.getReportHeaderId();
      this.reportDetailId = reportColumnVO.getReportDetailId();
      this.columnId = reportColumnVO.getColumnId();
      this.nameZH = reportColumnVO.getNameZH();
      this.nameEN = reportColumnVO.getNameEN();
      this.columnWidth = reportColumnVO.getColumnWidth();
      this.columnWidthType = reportColumnVO.getColumnWidthType();
      this.columnIndex = reportColumnVO.getColumnIndex();
      this.fontSize = reportColumnVO.getFontSize();
      this.isDecoded = reportColumnVO.getIsDecoded();
      this.isLinked = reportColumnVO.getIsLinked();
      this.linkedAction = reportColumnVO.getLinkedAction();
      this.linkedTarget = reportColumnVO.getLinkedTarget();
      this.datetimeFormat = reportColumnVO.getDatetimeFormat();
      this.accuracy = reportColumnVO.getAccuracy();
      this.round = reportColumnVO.getRound();
      this.sort = reportColumnVO.getSort();
      this.display = reportColumnVO.getDisplay();
      this.align = reportColumnVO.getAlign();
      this.description = reportColumnVO.getDescription();
      this.tableId = reportColumnVO.getTableId();
      this.statisticsFun = reportColumnVO.getStatisticsFun();
      super.setDeleted( reportColumnVO.getDeleted() );
      super.setStatus( reportColumnVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public void getStatisticsIndex( final String value )
   {
      if ( this.getStatisticsColumnses() != null && this.getStatisticsColumnses().size() > 0 )
      {
         for ( MappingVO mappingVO : this.getStatisticsColumnses() )
         {
            if ( mappingVO.getMappingValue().equals( value ) )
            {
               this.statisticsColumns = mappingVO.getMappingId();
               break;
            }
         }
      }
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getStatisticsFun()
   {
      return statisticsFun;
   }

   public void setStatisticsFun( String statisticsFun )
   {
      this.statisticsFun = statisticsFun;
   }

}
