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

public class ReportSearchDetailVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 1407738809687812123L;

   /**
    * For DB
    */

   // 报表搜索字段ID
   private String reportSearchDetailId;

   // 报表ID
   private String reportHeaderId;

   // 字段ID
   private String columnId;

   // 搜索字段名（中文）
   private String nameZH;

   // 搜索字段名（英文）
   private String nameEN;

   // 搜索字段顺序
   private String columnIndex;

   // 字体大小
   private String fontSize;

   // 使用联想？
   private String useThinking;

   // 联想访问Action
   private String thinkingAction;

   // 组合条件
   private String combineType;

   // 搜索条件
   private String condition;

   // 搜索匹配值
   private String content;

   // 搜索匹配值（条件between用到，minVal_maxVal）
   @SuppressWarnings("unused")
   private String range;

   // 是否显示？
   private String display;

   // 描述
   private String description;

   /**
    * For Application
    */
   // decoded字段
   private String decodeColumnId;
   @JsonIgnore
   // 字体大小Mapping
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 搜索条件
   private List< MappingVO > conditions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 组合条件
   private List< MappingVO > combineTypies = new ArrayList< MappingVO >();

   // 验证字段范围最小值
   private String rangeMin;

   // 验证字段范围最大值
   private String rangeMax;
   @JsonIgnore
   // 字段列表MappingVO
   private List< MappingVO > columns = new ArrayList< MappingVO >();

   //表id
   private String tableId;

   //用于临时传递省的值
   private String tempStr;

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      if ( KANUtil.filterEmpty( this.columnId ) != null )
      {
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnVOByColumnId( columnId );
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
         {
            decodeColumnId = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
         }
         else
         {
            decodeColumnId = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
         }
      }
      fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      combineTypies = KANUtil.getMappings( request.getLocale(), "def.report.search.detail.combine.type" );
      conditions = KANUtil.getMappings( request.getLocale(), "def.report.search.detail.condition" );
   }

   @Override
   public void reset() throws KANException
   {
      this.reportHeaderId = "";
      this.columnId = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.columnIndex = "0";
      this.fontSize = "13";
      this.useThinking = "0";
      this.thinkingAction = "";
      this.combineType = "0";
      this.condition = "1";
      this.content = "";
      this.range = "";
      this.display = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ReportSearchDetailVO reportSearchDetailVO = ( ReportSearchDetailVO ) object;
      this.columnId = reportSearchDetailVO.getColumnId();
      this.nameZH = reportSearchDetailVO.getNameZH();
      this.nameEN = reportSearchDetailVO.getNameEN();
      this.columnIndex = reportSearchDetailVO.getColumnIndex();
      this.fontSize = reportSearchDetailVO.getFontSize();
      this.useThinking = reportSearchDetailVO.getUseThinking();
      this.thinkingAction = reportSearchDetailVO.getThinkingAction();
      this.combineType = reportSearchDetailVO.getCombineType();
      this.condition = reportSearchDetailVO.getCondition();
      this.content = reportSearchDetailVO.getContent();
      this.range = reportSearchDetailVO.getRange();
      this.display = reportSearchDetailVO.getDisplay();
      this.description = reportSearchDetailVO.getDescription();
      super.setStatus( reportSearchDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // 解译字段
   public String getDecodeColumn()
   {
      return decodeColumnId;
   }

   // 解译条件
   public String getDecodeCondition()
   {
      return decodeField( this.condition, this.conditions );
   }

   // 解译组合条件类型
   public String getDecodeCombineType()
   {
      return decodeField( this.combineType, this.combineTypies );
   }

   public String getRange()
   {
      if ( ( rangeMin == null ) && ( rangeMax == null ) )
      {
         return null;
      }
      else if ( rangeMin.trim().equals( "" ) && rangeMax.trim().equals( "" ) )
      {
         return null;
      }
      else
      {
         return rangeMin + '_' + rangeMax;
      }
   }

   public void setRange( String range )
   {
      this.range = range;

      if ( range != null && !range.equals( "" ) && range.contains( "_" ) )
      {
         this.rangeMin = range.split( "_" )[ 0 ];
         this.rangeMax = range.split( "_" )[ 1 ];
      }
   }

   public String getReportSearchDetailId()
   {
      return reportSearchDetailId;
   }

   public void setReportSearchDetailId( String reportSearchDetailId )
   {
      this.reportSearchDetailId = reportSearchDetailId;
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

   public String getUseThinking()
   {
      return useThinking;
   }

   public void setUseThinking( String useThinking )
   {
      this.useThinking = useThinking;
   }

   public String getThinkingAction()
   {
      return thinkingAction;
   }

   public void setThinkingAction( String thinkingAction )
   {
      this.thinkingAction = thinkingAction;
   }

   public String getCombineType()
   {
      return combineType;
   }

   public void setCombineType( String combineType )
   {
      this.combineType = combineType;
   }

   public String getCondition()
   {
      return condition;
   }

   public void setCondition( String condition )
   {
      this.condition = condition;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
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

   public List< MappingVO > getConditions()
   {
      return conditions;
   }

   public void setConditions( List< MappingVO > conditions )
   {
      this.conditions = conditions;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( reportSearchDetailId );
   }

   public String getRangeMin()
   {
      return rangeMin;
   }

   public void setRangeMin( String rangeMin )
   {
      this.rangeMin = rangeMin;
   }

   public String getRangeMax()
   {
      return rangeMax;
   }

   public void setRangeMax( String rangeMax )
   {
      this.rangeMax = rangeMax;
   }

   public List< MappingVO > getCombineTypies()
   {
      return combineTypies;
   }

   public void setCombineTypies( List< MappingVO > combineTypies )
   {
      this.combineTypies = combineTypies;
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

   public List< MappingVO > getColumns()
   {
      return columns;
   }

   public void setColumns( List< MappingVO > columns )
   {
      this.columns = columns;
   }

   public String getTempStr()
   {
      return tempStr;
   }

   public void setTempStr( String tempStr )
   {
      this.tempStr = tempStr;
   }

}
