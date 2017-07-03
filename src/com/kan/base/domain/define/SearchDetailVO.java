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

public class SearchDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 5348656159085042795L;

   /**
    * For DB
    */
   // 主键ID
   private String searchDetailId;

   // 关联HeaderID
   private String searchHeaderId;

   // 字段
   private String columnId;

   // 参数名称
   private String propertyName;

   // 搜索字段中文名
   private String nameZH;

   // 搜索字段英文名
   private String nameEN;

   // 搜索字段排列顺序
   private String columnIndex;

   // 搜索字段字体大小
   private String fontSize;

   // 是否使用联想功能
   private String useThinking;

   // 联想功能访问Action
   private String thinkingAction;

   // 字段值类型
   private String contentType;

   // 字段值，用于报表搜索
   private String content;

   // 字段值 - Range，Range的MIN和MAX用下划线分割
   @SuppressWarnings("unused")
   private String range;

   // 当前搜索条件是否需要显示
   private String display;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 验证字段范围最小值
   private String rangeMin;

   // 验证字段范围最大值
   private String rangeMax;
   @JsonIgnore
   // Action中进行初始化
   private List< MappingVO > columns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字体大小
   private List< MappingVO > fontSizes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字段值类型
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();

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

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) request.getAttribute( "searchHeaderForm" );
      super.reset( mapping, request );
      this.fontSizes = KANUtil.getMappings( request.getLocale(), "def.list.detail.font.size" );
      this.contentTypes = KANUtil.getMappings( request.getLocale(), "def.search.detail.content.type" );
      if ( searchHeaderVO != null && !"0".equals( searchHeaderVO.getTableId() ) && KANUtil.filterEmpty( searchHeaderVO.getTableId() ) != null )
      {
         this.columns = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByTableId( searchHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), super.getCorpId(), true );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.searchHeaderId = "";
      this.columnId = "";
      this.propertyName = "";
      this.nameZH = "";
      this.nameEN = "";
      this.columnIndex = "";
      this.fontSize = "";
      this.useThinking = "";
      this.thinkingAction = "";
      this.range = "";
      this.content = "";
      this.display = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SearchDetailVO searchDetailVO = ( SearchDetailVO ) object;
      this.columnIndex = searchDetailVO.getColumnIndex();
      this.propertyName = searchDetailVO.getPropertyName();
      this.nameZH = searchDetailVO.getNameZH();
      this.nameEN = searchDetailVO.getNameEN();
      this.fontSize = searchDetailVO.getFontSize();
      this.useThinking = searchDetailVO.getUseThinking();
      this.thinkingAction = searchDetailVO.getThinkingAction();
      this.contentType = searchDetailVO.getContentType();
      this.range = searchDetailVO.getRange();
      this.content = searchDetailVO.getContent();
      this.display = searchDetailVO.getDisplay();
      this.description = searchDetailVO.getDescription();
      super.setStatus( searchDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   // 加密主键
   public String getEncodedId() throws KANException
   {
      return encodedField( searchDetailId );
   }

   // 解译字段
   public String getDecodeColumn()
   {
      return decodeField( columnId, columns, true );
   }

   // 解译是否启用联想
   public String getDecodeUseThinking()
   {
      return decodeField( useThinking, super.getFlags() );
   }

   // 解译是否显示
   public String getDecodeDisplay()
   {
      return decodeField( display, super.getFlags() );
   }

   public String getSearchDetailId()
   {
      return searchDetailId;
   }

   public void setSearchDetailId( String searchDetailId )
   {
      this.searchDetailId = searchDetailId;
   }

   public String getSearchHeaderId()
   {
      return searchHeaderId;
   }

   public void setSearchHeaderId( String searchHeaderId )
   {
      this.searchHeaderId = searchHeaderId;
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

   public String getUseThinking()
   {
      return useThinking;
   }

   public void setUseThinking( String useThinking )
   {
      if ( "0".equals( useThinking ) )
      {
         this.useThinking = "2";
      }
      else
      {
         this.useThinking = useThinking;
      }
   }

   public String getThinkingAction()
   {
      return thinkingAction;
   }

   public void setThinkingAction( String thinkingAction )
   {
      this.thinkingAction = thinkingAction;
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

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public void setDisplay( String display )
   {
      this.display = display;
   }

   public String getDisplay()
   {
      return display;
   }

   public List< MappingVO > getFontSizes()
   {
      return fontSizes;
   }

   public void setFontSizes( List< MappingVO > fontSizes )
   {
      this.fontSizes = fontSizes;
   }

   public String getContentType()
   {
      return contentType;
   }

   public void setContentType( String contentType )
   {
      this.contentType = contentType;
   }

   public List< MappingVO > getContentTypes()
   {
      return contentTypes;
   }

   public void setContentTypes( List< MappingVO > contentTypes )
   {
      this.contentTypes = contentTypes;
   }

}
