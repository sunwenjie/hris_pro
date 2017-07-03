package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class MappingDetailVO extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = 1798794654984156L;

   /**
    * For DB
    */
   // 匹配从表ID
   private String mappingDetailId;

   // 匹配主表ID
   private String mappingHeaderId;

   // 字段ID
   private String columnId;

   // 参数名
   private String propertyName;

   // 匹配字段中文名
   private String nameZH;

   //匹配字段英文名
   private String nameEN;

   //匹配字段宽度
   private String columnWidth;

   //匹配字段排列顺序
   private String columnIndex;

   // 匹配字段字体大小
   private String fontSize;

   // 字段是否需要转译
   private String isDecoded;

   // 日期格式，空为使用默认
   private String datetimeFormat;

   // 保留小数位
   private String accuracy;

   // 截取方式
   private String round;

   // 字段对齐
   private String align;

   // 描述
   private String description;

   /**
    * For Application
    */
   // Action中进行初始化
   // 字段列表
   private List< MappingVO > columns = new ArrayList< MappingVO >();

   // 字体大小
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();

   // 日期格式
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();

   // 对齐方式
   private List< MappingVO > aligns = new ArrayList< MappingVO >();

   // 保留小数位
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // 截取方式
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      this.datetimeFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
      this.aligns = KANUtil.getMappings( request.getLocale(), "def.list.detail.align" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
   }

   @Override
   public void reset() throws KANException
   {
      this.mappingDetailId = "";
      this.mappingHeaderId = "";
      this.columnId = "";
      this.propertyName = "";
      this.nameZH = "";
      this.nameEN = "";
      this.columnWidth = "";
      this.columnIndex = "";
      this.fontSize = "";
      this.datetimeFormat = "";
      this.accuracy = "";
      this.round = "";
      this.align = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final MappingDetailVO mappingDeatail = ( MappingDetailVO ) object;
      this.columnId = mappingDeatail.getColumnId();
      this.propertyName = mappingDeatail.getPropertyName();
      this.nameZH = mappingDeatail.getNameZH();
      this.nameEN = mappingDeatail.getNameEN();
      this.columnWidth = mappingDeatail.getColumnWidth();
      this.columnIndex = mappingDeatail.getColumnIndex();
      this.fontSize = mappingDeatail.getFontSize();
      this.isDecoded = mappingDeatail.getIsDecoded();
      this.datetimeFormat = mappingDeatail.getDatetimeFormat();
      this.accuracy = mappingDeatail.getAccuracy();
      this.round = mappingDeatail.getRound();
      this.align = mappingDeatail.getAlign();
      this.description = mappingDeatail.getDescription();
      super.setStatus( mappingDeatail.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getMappingDetailId()
   {
      return mappingDetailId;
   }

   public void setMappingDetailId( String mappingDetailId )
   {
      this.mappingDetailId = mappingDetailId;
   }

   public String getMappingHeaderId()
   {
      return mappingHeaderId;
   }

   public void setMappingHeaderId( String mappingHeaderId )
   {
      this.mappingHeaderId = mappingHeaderId;
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
      return KANUtil.filterEmpty( columnWidth );
   }

   public void setColumnWidth( String columnWidth )
   {
      this.columnWidth = columnWidth;
   }

   public String getColumnIndex()
   {
      return KANUtil.filterEmpty( columnIndex );
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.mappingDetailId );
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

}
