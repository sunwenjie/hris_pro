package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ConstantVO extends BaseVO
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = -3526014878016896398L;

   /**
    * For DB
    */

   // 参数Id
   private String constantId;

   // 参数中文名
   private String nameZH;

   // 参数英文名
   private String nameEN;

   // 参数类型（信息，商务合同，劳动合同）
   private String scopeType;

   // 字段名（映射动态常量）
   private String propertyName;

   // 字段值的类型（使用自定义字段中的值类型）
   private String valueType;

   // 参数性质（系统常量，账户常量，动态变量）
   private String characterType;

   // 参数内容
   private String content;

   // 控件大小
   private String lengthType;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 参数类型
   private List< MappingVO > scopeTypies = new ArrayList< MappingVO >();

   // 值类型
   private List< MappingVO > valueTypies = new ArrayList< MappingVO >();

   // 参数性质
   private List< MappingVO > characterTypies = new ArrayList< MappingVO >();

   // 控件大小
   private List< MappingVO > lengthTypies = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.scopeTypies = KANUtil.getMappings( request.getLocale(), "sys.constant.scope.type" );
      this.valueTypies = KANUtil.getMappings( request.getLocale(), "def.column.value.type" );
      this.characterTypies = KANUtil.getMappings( request.getLocale(), "sys.constant.character.type" );
      this.lengthTypies = KANUtil.getMappings( request.getLocale(), "sys.constant.length.type" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.scopeType = "0";
      this.propertyName = "";
      this.valueType = "0";
      this.characterType = "0";
      this.content = "";
      this.lengthType = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ConstantVO constantVO = ( ConstantVO ) object;
      this.nameZH = constantVO.getNameZH();
      this.nameEN = constantVO.getNameEN();
      this.scopeType = constantVO.getScopeType();
      this.propertyName = constantVO.getPropertyName();
      this.valueType = constantVO.getValueType();
      this.characterType = constantVO.getCharacterType();
      this.content = constantVO.getContent();
      this.lengthType = constantVO.getLengthType();
      this.description = constantVO.getDescription();
      super.setStatus( constantVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeScopeType()
   {
      if ( this.scopeTypies != null )
      {
         for ( MappingVO mappingVO : this.scopeTypies )
         {
            if ( mappingVO.getMappingId().equals( this.scopeType ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeCharacterType()
   {
      if ( this.characterTypies != null )
      {
         for ( MappingVO mappingVO : this.characterTypies )
         {
            if ( mappingVO.getMappingId().equals( this.characterType ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeValueType()
   {
      if ( this.valueTypies != null )
      {
         for ( MappingVO mappingVO : this.valueTypies )
         {
            if ( mappingVO.getMappingId().equals( this.valueType ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }
   
   public String getDecodeLengthType()
   {
      if ( this.lengthTypies != null )
      {
         for ( MappingVO mappingVO : this.lengthTypies )
         {
            if ( mappingVO.getMappingId().equals( this.lengthType ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getConstantId()
   {
      return constantId;
   }

   public void setConstantId( String constantId )
   {
      this.constantId = constantId;
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

   public String getScopeType()
   {
      return scopeType;
   }

   public void setScopeType( String scopeType )
   {
      this.scopeType = scopeType;
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   public void setPropertyName( String propertyName )
   {
      this.propertyName = propertyName;
   }

   public String getValueType()
   {
      return valueType;
   }

   public void setValueType( String valueType )
   {
      this.valueType = valueType;
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
      if ( constantId == null || constantId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( constantId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public List< MappingVO > getValueTypies()
   {
      return valueTypies;
   }

   public void setValueTypies( List< MappingVO > valueTypies )
   {
      this.valueTypies = valueTypies;
   }

   public List< MappingVO > getScopeTypies()
   {
      return scopeTypies;
   }

   public void setScopeTypies( List< MappingVO > scopeTypies )
   {
      this.scopeTypies = scopeTypies;
   }

   public String getCharacterType()
   {
      return characterType;
   }

   public void setCharacterType( String characterType )
   {
      this.characterType = characterType;
   }

   public List< MappingVO > getCharacterTypies()
   {
      return characterTypies;
   }

   public void setCharacterTypies( List< MappingVO > characterTypies )
   {
      this.characterTypies = characterTypies;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getLengthType()
   {
      return lengthType;
   }

   public void setLengthType( String lengthType )
   {
      this.lengthType = lengthType;
   }

   public List< MappingVO > getLengthTypies()
   {
      return lengthTypies;
   }

   public void setLengthTypies( List< MappingVO > lengthTypies )
   {
      this.lengthTypies = lengthTypies;
   }

}
