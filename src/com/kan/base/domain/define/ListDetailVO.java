package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ListDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1347177077290784085L;

   /**
    * For DB
    */

   private String listDetailId;

   private String listHeaderId;

   private String columnId;

   // 参数名
   private String propertyName;

   private String nameZH;

   private String nameEN;

   private String columnWidth;

   private String columnWidthType;

   private String columnIndex;

   private String fontSize;

   private String isDecoded;

   private String isLinked;

   private String linkedAction;

   private String linkedTarget;

   private String appendContent;

   private String properties;

   private String datetimeFormat;

   private String accuracy;

   private String align;

   private String round;

   private String sort;

   private String display;

   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // Action中进行初始化
   private List< MappingVO > columns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字体大小
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 链接目标
   private List< MappingVO > linkedTargets = new ArrayList< MappingVO >();
   @JsonIgnore
   // 日期格式
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   // 对齐方式
   private List< MappingVO > aligns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 精度
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 取舍
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字段宽度显示类型
   private List< MappingVO > columnWidthTypes = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      this.linkedTargets = KANUtil.getMappings( request.getLocale(), "def.list.detail.link.target" );
      this.datetimeFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
      this.aligns = KANUtil.getMappings( request.getLocale(), "def.list.detail.align" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.columnWidthTypes = KANUtil.getMappings( request.getLocale(), "def.list.detail.column.width.type" );
   }

   @Override
   public void reset() throws KANException
   {
      this.listHeaderId = "";
      this.columnId = "";
      this.propertyName = "";
      this.nameZH = "";
      this.nameEN = "";
      this.columnWidth = "";
      this.columnWidthType = "";
      this.columnIndex = "";
      this.fontSize = "";
      this.isDecoded = "";
      this.isLinked = "";
      this.linkedAction = "";
      this.linkedTarget = "0";
      this.appendContent = "";
      this.properties = "";
      this.datetimeFormat = "";
      this.accuracy = "";
      this.align = "";
      this.round = "";
      this.sort = "";
      this.display = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ListDetailVO listDetailVO = ( ListDetailVO ) object;
      super.setStatus( listDetailVO.getStatus() );
      super.setModifyDate( new Date() );
      this.nameZH = listDetailVO.getNameZH();
      this.nameEN = listDetailVO.getNameEN();
      this.propertyName = listDetailVO.getPropertyName();
      this.columnWidth = listDetailVO.getColumnWidth();
      this.columnWidthType = listDetailVO.getColumnWidthType();
      this.fontSize = listDetailVO.getFontSize();
      this.isDecoded = listDetailVO.getIsDecoded();
      this.isLinked = listDetailVO.getIsLinked();
      this.linkedAction = listDetailVO.getLinkedAction();
      this.linkedTarget = listDetailVO.getLinkedTarget();
      this.appendContent = listDetailVO.getAppendContent();
      this.properties = listDetailVO.getProperties();
      this.datetimeFormat = listDetailVO.getDatetimeFormat();
      this.accuracy = listDetailVO.getAccuracy();
      this.align = listDetailVO.getAlign();
      this.round = listDetailVO.getRound();
      this.sort = listDetailVO.getSort();
      this.display = listDetailVO.getDisplay();
      this.description = listDetailVO.getDescription();
      this.columnIndex = listDetailVO.getColumnIndex();
   }

   public String getDecodeColumn()
   {
      return decodeField( this.columnId, this.columns, true );
   }

   public String getDecodeIsLinked()
   {
      return decodeField( this.isLinked, super.getFlags() );
   }

   public String getDecodeDatetimeFormat()
   {
      return decodeField( this.datetimeFormat, this.datetimeFormats );
   }

   public String getDecodeDatetimeFormatTemp()
   {
      return decodeField( this.datetimeFormat, this.datetimeFormats, true );
   }

   public String getDecodeIsDecoded()
   {
      return decodeField( this.isDecoded, super.getFlags() );
   }

   public String getDecodeAlign()
   {
      return decodeField( this.align, this.aligns );
   }

   public String getDecodeAlignTemp()
   {
      return decodeField( this.align, this.aligns, true );
   }

   public String getDecodeAccuracy()
   {
      return decodeField( this.accuracy, this.accuracys );
   }

   public String getDecodeAccuracyTemp()
   {
      return decodeField( this.accuracy, this.accuracys, true );
   }

   public String getDecodeRound()
   {
      return decodeField( this.round, this.rounds );
   }

   public String getDecodeDisplay()
   {
      return decodeField( this.display, super.getFlags() );
   }

   public String getDecodeSort()
   {
      return decodeField( this.sort, super.getFlags() );
   }

   public String getListDetailId()
   {
      return listDetailId;
   }

   public void setListDetailId( String listDetailId )
   {
      this.listDetailId = listDetailId;
   }

   public String getListHeaderId()
   {
      return listHeaderId;
   }

   public void setListHeaderId( String listHeaderId )
   {
      this.listHeaderId = listHeaderId;
   }

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   public void setPropertyName( String propertyName )
   {
      this.propertyName = propertyName;
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

   public String getAlign()
   {
      return align;
   }

   public void setAlign( String align )
   {
      this.align = align;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getColumns()
   {
      return columns;
   }

   public void setColumns( List< MappingVO > columns )
   {
      this.columns = columns;
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

   public List< MappingVO > getFontSizes()
   {
      return fontSizes;
   }

   public void setFontSizes( List< MappingVO > fontSizes )
   {
      this.fontSizes = fontSizes;
   }

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   public String getDisplay()
   {
      return display;
   }

   public void setDisplay( String display )
   {
      this.display = display;
   }

   public String getColumnWidthType()
   {
      return columnWidthType;
   }

   public void setColumnWidthType( String columnWidthType )
   {
      this.columnWidthType = columnWidthType;
   }

   public List< MappingVO > getColumnWidthTypes()
   {
      return columnWidthTypes;
   }

   public void setColumnWidthTypes( List< MappingVO > columnWidthTypes )
   {
      this.columnWidthTypes = columnWidthTypes;
   }

   public String getAppendContent()
   {
      return appendContent;
   }

   public void setAppendContent( String appendContent )
   {
      this.appendContent = appendContent;
   }

   public String getProperties()
   {
      return properties;
   }

   public void setProperties( String properties )
   {
      this.properties = properties;
   }

   public final String getSort()
   {
      return sort;
   }

   public final void setSort( String sort )
   {
      this.sort = sort;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( listDetailId );
   }

   /***
    * 以下方法仅限于特殊的List
    */

   public String getItemId()
   {
      if ( this.getLength() == 0 )
         return null;
      return propertyName.split( "_" )[ 1 ];
   }

   public int getLength()
   {
      if ( KANUtil.filterEmpty( propertyName ) != null && propertyName.startsWith( "item" ) )
      {
         return propertyName.split( "_" ).length;
      }

      return 0;
   }

   public String getLastCharOfPropertyName()
   {
      if ( KANUtil.filterEmpty( propertyName ) == null )
      {
         return null;
      }
      return String.valueOf( propertyName.charAt( propertyName.length() - 1 ) );
   }

   // 是否科目为社保
   public boolean isSBItem()
   {
      return getLength() == 3;
   }

   // 筛选公司
   public boolean filterCompany()
   {
      return getLength() == 3 && propertyName.split( "_" )[ 2 ].equals( "c" );
   }

   // 获取表头名称
   public String getHeaderName( final HttpServletRequest request )
   {
      if ( KANUtil.filterEmpty( propertyName ) != null )
      {
         if ( isSBItem() )
         {
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return this.getNameZH().split( "-" )[ 0 ];
            }
            else
            {
               return this.getNameEN();
            }
         }

         return request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? getNameZH() : getNameEN();
      }

      return "";
   }

}
