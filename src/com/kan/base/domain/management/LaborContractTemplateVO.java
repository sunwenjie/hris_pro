package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class LaborContractTemplateVO extends BaseVO
{

   /**  
    * serialVersionUID
    *  
    */
   private static final long serialVersionUID = -8196351907999467727L;

   // 劳务合同模板Id
   private String templateId;

   // 合同类型（绑定设置 - 工作 - 合同类型）
   private String contractTypeId;

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
   @JsonIgnore
   // 模板内容类型数组
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 当前账户对应的法务实体集合
   private List< MappingVO > entities = new ArrayList< MappingVO >();
   @JsonIgnore
   // 包含账户对应的业务类型集合
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 法务实体数组
   private String[] entityIdArray = new String[] {};

   // 业务类型数组
   private String[] businessTypeIdArray = new String[] {};

   // 客户数组
   private String[] clientIdsArray = new String[] {};

   private String clientIds;
   @JsonIgnore
   // 合同类型数组
   private List< MappingVO > contractTypes = new ArrayList< MappingVO >();

   // 参数
   private String constantId;

   // 查询用业务类型Id
   private String businessTypeId;

   // 查询用法务实体Id
   private String entityId;

   @Override
   public String getEncodedId() throws KANException
   {
      if ( templateId == null || templateId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( templateId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.contentTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.contentType" );
      this.contractTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.contractTypes" );
      this.entities = KANConstants.getKANAccountConstants( this.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( this.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

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
   public String getDecodeContractTypeId()
   {
      if ( this.contractTypes != null )
      {
         for ( MappingVO mappingVO : this.contractTypes )
         {
            if ( mappingVO.getMappingId().equals( this.contractTypeId ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
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

   @Override
   public void reset() throws KANException
   {
      this.contractTypeId = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.entityIds = "";
      this.clientIds = "";
      this.businessTypeIds = "";
      this.content = "";
      this.contentType = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) object;
      this.contractTypeId = laborContractTemplateVO.getContractTypeId();
      this.nameZH = laborContractTemplateVO.getNameZH();
      this.nameEN = laborContractTemplateVO.getNameEN();
      this.entityIds = laborContractTemplateVO.getEntityIds();
      this.businessTypeIds = laborContractTemplateVO.getBusinessTypeIds();
      this.content = laborContractTemplateVO.getContent();
      this.contentType = laborContractTemplateVO.getContentType();
      this.description = laborContractTemplateVO.getDescription();
      super.setStatus( laborContractTemplateVO.getStatus() );
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
      return null;
   }

   public void setEntityIdArray( String[] entityIdArray )
   {
      if ( this.businessTypeIds != null && !this.businessTypeIds.trim().equals( "" ) )
      {
         this.entityIdArray = KANUtil.jasonArrayToStringArray( this.businessTypeIds );
      }
      else
      {
         this.entityIdArray = entityIdArray;
      }
   }

   public String[] getBusinessTypeIdArray()
   {
      if ( this.businessTypeIds != null && !this.businessTypeIds.trim().equals( "" ) )
      {
         return KANUtil.jasonArrayToStringArray( this.businessTypeIds );
      }
      return null;
   }

   public void setBusinessTypeIdArray( String[] businessTypeIdArray )
   {
      if ( this.businessTypeIds != null && !this.businessTypeIds.trim().equals( "" ) )
      {
         this.businessTypeIdArray = KANUtil.jasonArrayToStringArray( this.businessTypeIds );
      }
      else
      {
         this.businessTypeIdArray = businessTypeIdArray;
      }
   }

   public String getContractTypeId()
   {
      return contractTypeId;
   }

   public void setContractTypeId( String contractTypeId )
   {
      this.contractTypeId = contractTypeId;
   }

   public List< MappingVO > getContractTypes()
   {
      return contractTypes;
   }

   public void setContractTypes( List< MappingVO > contractTypes )
   {
      this.contractTypes = contractTypes;
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

   public List< String > getDecodeEntityIds()
   {
      if ( this.getEntityIdArray() != null && this.getEntityIdArray().length > 0 && this.entities != null && this.entities.size() > 0 )
      {
         // 法务实体 - 解码
         final List< String > entityIdsString = new ArrayList< String >();

         for ( String entityId : this.getEntityIdArray() )
         {
            for ( MappingVO entityMappingVO : this.entities )
            {
               if ( entityId.trim().equals( entityMappingVO.getMappingId() ) )
               {
                  entityIdsString.add( entityMappingVO.getMappingValue() );
                  continue;
               }
            }
         }
         if ( entityIdsString.size() == 0 )
         {
            return null;
         }
         else
         {
            return entityIdsString;
         }
      }

      return null;
   }

   public String getStringDecodeEntityIds()
   {
      if ( getDecodeEntityIds() == null )
      {
         return null;
      }
      else
      {
         String result = "";
         for ( String s : getDecodeEntityIds() )
         {
            result += s;
            result += ",";
         }
         return result.substring( 0, result.length() - 1 );
      }
   }

   public List< String > getDecodeBusinessTypeIds()
   {
      if ( this.getBusinessTypeIdArray() != null && this.getBusinessTypeIdArray().length > 0 && this.businessTypes != null && this.businessTypes.size() > 0 )
      {
         // 业务类型 - 解码
         final List< String > businessTypeIdsString = new ArrayList< String >();

         for ( String businessTypeId : this.getBusinessTypeIdArray() )
         {
            for ( MappingVO businessTypeMappingVO : this.businessTypes )
            {
               if ( businessTypeId.trim().equals( businessTypeMappingVO.getMappingId() ) )
               {
                  businessTypeIdsString.add( businessTypeMappingVO.getMappingValue() );
               }
            }
         }
         if ( businessTypeIdsString.size() == 0 )
         {
            return null;
         }
         else
         {
            return businessTypeIdsString;
         }
      }

      return null;
   }

   public String getStringDecodeBusinessTypeIds()
   {
      if ( getDecodeBusinessTypeIds() == null )
      {
         return null;
      }
      else
      {
         String result = "";
         for ( String s : getDecodeBusinessTypeIds() )
         {
            result += s;
            result += ",";
         }
         return result.substring( 0, result.length() - 1 );
      }
   }

   public String[] getClientIdsArray()
   {
      return clientIdsArray;
   }

   public void setClientIdsArray( String[] clientIdsArray )
   {
      this.clientIdsArray = clientIdsArray;
   }

   public String getClientIds()
   {
      return clientIds;
   }

   public void setClientIds( String clientIds )
   {
      this.clientIds = clientIds;
   }
}
