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

public class BankTemplateDetailVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -7917576346395653860L;

   // 工资模板从表ID
   private String templateDetailId;

   // 工资模板主表ID
   private String templateHeaderId;

   // propertyName
   private String propertyName;

   // 字段中文名
   private String nameZH;

   // 字段英文名
   private String nameEN;

   // 值类型
   private String valueType;

   // 字段宽度
   private String columnWidth;

   // 字段排列顺序
   private String columnIndex;

   // 字段字体大小
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
   @JsonIgnore
   // 值类型
   private List< MappingVO > valueTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字体大小
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 日期格式
   private List< MappingVO > datetimeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   // 保留小数位
   private List< MappingVO > accuracies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 截取方式
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   // 对齐方式
   private List< MappingVO > aligns = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.valueTypes = KANUtil.getMappings( request.getLocale(), "def.column.value.type" );
      this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      this.datetimeFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
      this.accuracies = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.aligns = KANUtil.getMappings( request.getLocale(), "def.list.detail.align" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( templateDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.templateHeaderId = "";
      this.propertyName = "";
      this.nameZH = "";
      this.nameEN = "";
      this.valueType = "0";
      this.columnWidth = "";
      this.columnIndex = "";
      this.fontSize = "0";
      this.isDecoded = "0";
      this.datetimeFormat = "0";
      this.accuracy = "0";
      this.round = "0";
      this.align = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) object;
      this.propertyName = bankTemplateDetailVO.getPropertyName();
      this.nameZH = bankTemplateDetailVO.getNameZH();
      this.nameEN = bankTemplateDetailVO.getNameEN();
      this.valueType = bankTemplateDetailVO.getValueType();
      this.columnWidth = bankTemplateDetailVO.getColumnWidth();
      this.columnIndex = bankTemplateDetailVO.getColumnIndex();
      this.fontSize = bankTemplateDetailVO.getFontSize();
      this.isDecoded = bankTemplateDetailVO.getIsDecoded();
      this.datetimeFormat = bankTemplateDetailVO.getDatetimeFormat();
      this.accuracy = bankTemplateDetailVO.getAccuracy();
      this.round = bankTemplateDetailVO.getRound();
      this.align = bankTemplateDetailVO.getAlign();
      this.description = bankTemplateDetailVO.getDescription();
      super.setStatus( bankTemplateDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeValueType()
   {
      return decodeField( this.valueType, this.valueTypes );
   }

   public String getDecodeIsDecoded()
   {
      return decodeField( this.isDecoded, super.getFlags() );
   }

   public String getDecodeDatetimeFormat()
   {
      return decodeField( this.datetimeFormat, this.datetimeFormats );
   }

   public String getDecodeAccuracy()
   {
      return decodeField( this.accuracy, this.accuracies );
   }

   public String getDecodeRound()
   {
      return decodeField( this.round, this.rounds );
   }

   public String getDecodeAlign()
   {
      return decodeField( this.align, this.aligns );
   }

   public String getTemplateDetailId()
   {
      return templateDetailId;
   }

   public void setTemplateDetailId( String templateDetailId )
   {
      this.templateDetailId = templateDetailId;
   }

   public String getTemplateHeaderId()
   {
      return templateHeaderId;
   }

   public void setTemplateHeaderId( String templateHeaderId )
   {
      this.templateHeaderId = templateHeaderId;
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

   public String getValueType()
   {
      return valueType;
   }

   public void setValueType( String valueType )
   {
      this.valueType = valueType;
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

   public List< MappingVO > getValueTypes()
   {
      return valueTypes;
   }

   public void setValueTypes( List< MappingVO > valueTypes )
   {
      this.valueTypes = valueTypes;
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

   public List< MappingVO > getAccuracies()
   {
      return accuracies;
   }

   public void setAccuracies( List< MappingVO > accuracies )
   {
      this.accuracies = accuracies;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   public List< MappingVO > getAligns()
   {
      return aligns;
   }

   public void setAligns( List< MappingVO > aligns )
   {
      this.aligns = aligns;
   }

}
