package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ImportDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1347177077290784085L;

   /**
    * For DB
    */

   private String importDetailId;

   private String importHeaderId;

   private String columnId;

   private String nameZH;

   private String nameEN;

   private String isPrimaryKey;

   private String isForeignKey;

   private String columnWidth;

   private String columnIndex;

   private String fontSize;

   private String specialField;

   //# 示例值
   private String tempValue;

   private String isDecoded;

   // 导入时是否忽略默认验证 ，让handler来处理
   private String isIgnoreDefaultValidate;

   private String datetimeFormat;

   private String accuracy;

   private String align;

   private String round;

   private String sort;

   private String description;

   /**
    * For Application
    */
   // 临时使用
   private String tableId;
   @JsonIgnore
   // Action中进行初始化
   private List< MappingVO > columns = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > linkedTargets = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > aligns = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > columnWidthTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > specialFields = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > flags = new ArrayList< MappingVO >();

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
      this.specialFields = KANUtil.getMappings( request.getLocale(), "def.import.header.special.field.type" );
      this.flags = KANUtil.getMappings( request.getLocale(), "def.import.detail.field.required" );
   }

   @Override
   public void reset() throws KANException
   {
      this.importHeaderId = "";
      this.columnId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.isPrimaryKey = "";
      this.isForeignKey = "";
      this.columnWidth = "";
      this.columnIndex = "";
      this.fontSize = "";
      this.specialField = "";
      this.tempValue = "";
      this.isDecoded = "";
      this.isIgnoreDefaultValidate = "";
      this.datetimeFormat = "";
      this.accuracy = "";
      this.align = "";
      this.round = "";
      this.sort = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ImportDetailVO importDetailVO = ( ImportDetailVO ) object;
      super.setStatus( importDetailVO.getStatus() );
      this.nameZH = importDetailVO.getNameZH();
      this.nameEN = importDetailVO.getNameEN();
      this.isPrimaryKey = importDetailVO.getIsPrimaryKey();
      this.isForeignKey = importDetailVO.getIsForeignKey();
      this.columnWidth = importDetailVO.getColumnWidth();
      this.fontSize = importDetailVO.getFontSize();
      this.specialField = importDetailVO.getSpecialField();
      this.tempValue = importDetailVO.getTempValue();
      this.isDecoded = importDetailVO.getIsDecoded();
      this.isIgnoreDefaultValidate = importDetailVO.getIsIgnoreDefaultValidate();
      this.datetimeFormat = importDetailVO.getDatetimeFormat();
      this.accuracy = importDetailVO.getAccuracy();
      this.align = importDetailVO.getAlign();
      this.round = importDetailVO.getRound();
      this.sort = importDetailVO.getSort();
      this.description = importDetailVO.getDescription();
      this.columnIndex = importDetailVO.getColumnIndex();
   }

   public String getDecodeColumn()
   {
      return decodeField( this.columnId, this.columns, true );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.importDetailId );
   }

   public String getImportDetailId()
   {
      return importDetailId;
   }

   public void setImportDetailId( String importDetailId )
   {
      this.importDetailId = importDetailId;
   }

   public String getImportHeaderId()
   {
      return importHeaderId;
   }

   public void setImportHeaderId( String importHeaderId )
   {
      this.importHeaderId = importHeaderId;
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

   public final String getIsPrimaryKey()
   {
      return isPrimaryKey;
   }

   public final void setIsPrimaryKey( String isPrimaryKey )
   {
      this.isPrimaryKey = isPrimaryKey;
   }

   public final String getIsForeignKey()
   {
      return isForeignKey;
   }

   public final void setIsForeignKey( String isForeignKey )
   {
      this.isForeignKey = isForeignKey;
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

   public String getTempValue()
   {
      return tempValue;
   }

   public void setTempValue( String tempValue )
   {
      this.tempValue = tempValue;
   }

   public String getIsDecoded()
   {
      return isDecoded;
   }

   public void setIsDecoded( String isDecoded )
   {
      this.isDecoded = isDecoded;
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

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
   }

   public String getSort()
   {
      return sort;
   }

   public void setSort( String sort )
   {
      this.sort = sort;
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

   public List< MappingVO > getColumnWidthTypes()
   {
      return columnWidthTypes;
   }

   public void setColumnWidthTypes( List< MappingVO > columnWidthTypes )
   {
      this.columnWidthTypes = columnWidthTypes;
   }

   public String getSpecialField()
   {
      return specialField;
   }

   public void setSpecialField( String specialField )
   {
      this.specialField = specialField;
   }

   public List< MappingVO > getSpecialFields()
   {
      return specialFields;
   }

   public void setSpecialFields( List< MappingVO > specialFields )
   {
      this.specialFields = specialFields;
   }

   public final String getTableId()
   {
      return tableId;
   }

   public final void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getIsIgnoreDefaultValidate()
   {
      return isIgnoreDefaultValidate;
   }

   public void setIsIgnoreDefaultValidate( String isIgnoreDefaultValidate )
   {
      this.isIgnoreDefaultValidate = isIgnoreDefaultValidate;
   }

   public List< MappingVO > getFlags()
   {
      return flags;
   }

   public void setFlags( List< MappingVO > flags )
   {
      this.flags = flags;
   }

   public String getDecodeFlag( final String flag )
   {
      return decodeField( flag, this.flags );
   }
}
