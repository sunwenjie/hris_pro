package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class BusinessContractTemplateVO extends BaseVO
{
   /**  
   * serialVersionUID
   *  
   */
   private static final long serialVersionUID = -3449276682947256888L;

   // 商务合同模板Id
   private String templateId;

   // 模板中文名
   private String nameZH;

   // 模板英文名
   private String nameEN;

   // 法务实体
   private String entityIds;

   // 业务类型
   private String businessTypeIds;

   // 模板内容
   private String content;

   // 模板内容类型
   private String contentType;

   // 模板描述
   private String description;

   /**
    *    For App
    */
   // 模板内容类型数组
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();

   // 法务实体数组
   private String[] entityIdArray = new String[] {};

   // 业务类型数组
   private String[] businessTypeIdArray = new String[] {};

   // 当前账户对应的法务实体集合
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // 包含账户对应的业务类型集合
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 参数
   private String constantId;

   // 查询用业务类型Id
   private String businessTypeId;

   // 查询用法务实体Id
   private String entityId;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( templateId );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.contentTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.contentType" );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      if ( this.entities != null )
      {
         this.entities.add( 0, super.getEmptyMappingVO() );
      }
      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, super.getEmptyMappingVO() );
      }
   }

   // 转意获得模板内容类型
   public String getDecodeContentType()
   {
      return decodeField( this.contentType, this.contentTypes );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.entityIds = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.businessTypeIds = "";
      this.content = "";
      this.contentType = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) object;
      this.nameZH = businessContractTemplateVO.getNameZH();
      this.nameEN = businessContractTemplateVO.getNameEN();
      this.entityIds = businessContractTemplateVO.getEntityIds();
      this.businessTypeIds = businessContractTemplateVO.getBusinessTypeIds();
      this.content = businessContractTemplateVO.getContent();
      this.contentType = businessContractTemplateVO.getContentType();
      this.description = businessContractTemplateVO.getDescription();
      super.setStatus( businessContractTemplateVO.getStatus() );
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

   public String getEntityIds()
   {
      if ( this.entityIdArray != null && this.entityIdArray.length > 0 )
      {
         this.entityIds = KANUtil.toJasonArray( this.entityIdArray );
      }
      return entityIds;
   }

   public void setEntityIds( String entityIds )
   {
      this.entityIds = entityIds;
   }

   public String getBusinessTypeIds()
   {
      if ( this.businessTypeIdArray != null && this.businessTypeIdArray.length > 0 )
      {
         this.businessTypeIds = KANUtil.toJasonArray( this.businessTypeIdArray );
      }
      return businessTypeIds;
   }

   public void setBusinessTypeIds( String businessTypeIds )
   {
      this.businessTypeIds = businessTypeIds;
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

   public String[] getEntityIdArray()
   {
      if ( this.entityIds != null && !this.entityIds.trim().equals( "" ) )
      {
         return KANUtil.jasonArrayToStringArray( this.entityIds );
      }
      return new String[] {};
   }

   public void setEntityIdArray( String[] entityIdArray )
   {
      this.entityIdArray = entityIdArray;
   }

   public String[] getBusinessTypeIdArray()
   {
      if ( this.businessTypeIds != null && !this.businessTypeIds.trim().equals( "" ) )
      {
         return KANUtil.jasonArrayToStringArray( this.businessTypeIds );
      }
      return new String[] {};
   }

   public void setBusinessTypeIdArray( String[] businessTypeIdArray )
   {
      this.businessTypeIdArray = businessTypeIdArray;
   }

   public String getConstantId()
   {
      return constantId;
   }

   public void setConstantId( String constantId )
   {
      this.constantId = constantId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public String getDecodeEntityIds()
   {
      String entityNames = "";

      if ( this.getEntityIdArray() != null && this.getEntityIdArray().length > 0 && this.entities != null && this.entities.size() > 0 )
      {
         // 业务类型 - 解码
         for ( String entityId : this.getEntityIdArray() )
         {
            if ( entityNames != null && !entityNames.trim().isEmpty() )
            {
               entityNames = entityNames + "，";
            }

            entityNames = entityNames + decodeField( entityId, this.entities );
         }
      }

      return entityNames;
   }

   public String getDecodeBusinessTypeIds()
   {
      String businessTypeNames = "";

      if ( this.getBusinessTypeIdArray() != null && this.getBusinessTypeIdArray().length > 0 && this.businessTypes != null && this.businessTypes.size() > 0 )
      {
         // 业务类型 - 解码
         for ( String businessTypeId : this.getBusinessTypeIdArray() )
         {
            if ( businessTypeNames != null && !businessTypeNames.trim().isEmpty() )
            {
               businessTypeNames = businessTypeNames + "，";
            }

            businessTypeNames = businessTypeNames + decodeField( businessTypeId, this.businessTypes );
         }
      }

      return businessTypeNames;
   }

}
