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

   // ����ӱ�ID
   private String reportDetailId;

   // ����ID
   private String reportHeaderId;

   // �ֶ�ID
   private String columnId;

   // �ֶ��������ģ�
   private String nameZH;

   // �ֶ�����Ӣ�ģ�
   private String nameEN;

   // �ֶο��
   private String columnWidth;

   // �������
   private String columnWidthType;

   // ˳��
   private String columnIndex;

   // �����С
   private String fontSize;

   // �Ƿ�ת�룿
   private String isDecoded;

   // �Ƿ����ӣ�
   private String isLinked;

   // ����Action
   private String linkedAction;

   // ����Ŀ�귽ʽ  
   private String linkedTarget;

   // ���ڸ�ʽ����
   private String datetimeFormat;

   // ����С��λ
   private String accuracy;

   // ��ȡ��ʽ
   private String round;

   // ���뷽ʽ
   private String align;

   // �Ƿ�����
   private String sort;

   // �Ƿ���ʾ
   private String display;

   // ����
   private String description;

   //tableId
   private String tableId;

   //ͳ�ƺ���
   private String statisticsFun;

   /**
    * For Application
    */
   // Action�н��г�ʼ��

   // decoded�ֶ�
   private String decodeColumnId;
   @JsonIgnore
   // �����СMapping
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����Ŀ��Mapping
   private List< MappingVO > linkedTargets = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���ڸ�ʽMapping
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���뷽ʽMapping
   private List< MappingVO > aligns = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����С��Mapping
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ȡλ��Mapping
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   // �������Mapping
   private List< MappingVO > columnWidthTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ۺϺ���
   private List< MappingVO > statisticsColumnses = new ArrayList< MappingVO >();

   // ʹ�þۺϺ���
   private String statisticsColumns;
   @JsonIgnore
   // �ֶ��б�Mapping
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

   // �����ֶ�
   public String getDecodeColumn()
   {
      return decodeColumnId;
   }

   // �����Ƿ����ӣ�
   public String getDecodeIsLinked()
   {
      return decodeField( isLinked, super.getFlags() );
   }

   // �����Ƿ�ת�룿
   public String getDecodeIsDecoded()
   {
      return decodeField( isDecoded, super.getFlags() );
   }

   // �������з�ʽ
   public String getDecodeAlign()
   {
      return decodeField( align, aligns );
   }

   public String getDecodeAlignTemp()
   {
      return decodeField( align, aligns, true );
   }

   // ������������
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
