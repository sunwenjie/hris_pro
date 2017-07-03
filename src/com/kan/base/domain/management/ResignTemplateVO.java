package com.kan.base.domain.management;

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

public class ResignTemplateVO extends BaseVO
{

   /**  
    * serialVersionUID
    *  
    */
   private static final long serialVersionUID = -8519079994671963727L;

   // 退工单模板Id
   private String templateId;

   private String nameZH;

   private String nameEN;

   private String content;

   private String contentType;

   private String description;

   private String templateType;

   /**
    *    For App
    */
   @JsonIgnore
   // 模板内容类型数组
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > templateTypes = new ArrayList< MappingVO >();

   public ResignTemplateVO()
   {
      super();
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.templateId );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.content = "";
      this.contentType = "0";
      this.templateType = "0";
      this.description = "";
      super.setStatus( "0" );

   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ResignTemplateVO resignTemplateVO = ( ResignTemplateVO ) object;
      this.nameZH = resignTemplateVO.getNameZH();
      this.nameEN = resignTemplateVO.getNameEN();
      this.content = resignTemplateVO.getContent();
      this.contentType = resignTemplateVO.getContentType();
      this.templateType = resignTemplateVO.getTemplateType();
      this.description = resignTemplateVO.getDescription();
      super.setStatus( resignTemplateVO.getStatus() );
      super.setModifyDate( new Date() );

   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.contentTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.contentType" );
      this.templateTypes = KANUtil.getMappings( this.getLocale(), "resignTemplate.templateType" );
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
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

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getContentType()
   {
      return contentType;
   }

   public void setContentType( String contentType )
   {
      this.contentType = contentType;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getContentTypes()
   {
      return contentTypes;
   }

   public void setContentTypes( List< MappingVO > contentTypes )
   {
      this.contentTypes = contentTypes;
   }

   public String getTemplateType()
   {
      return templateType;
   }

   public void setTemplateType( String templateType )
   {
      this.templateType = templateType;
   }

   public List< MappingVO > getTemplateTypes()
   {
      return templateTypes;
   }

   public void setTemplateTypes( List< MappingVO > templateTypes )
   {
      this.templateTypes = templateTypes;
   }

   // 转意获得合同类型
   public String getDecodeContentType()
   {
      if ( this.contentTypes != null )
      {
         for ( MappingVO mappingVO : this.contentTypes )
         {
            if ( mappingVO.getMappingId().equals( this.contentType ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   // 转意获得合同类型
   public String getDecodeTemplateType()
   {
      return decodeField( templateType, templateTypes );
   }
}
