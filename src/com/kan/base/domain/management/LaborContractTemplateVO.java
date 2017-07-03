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

   // �����ͬģ��Id
   private String templateId;

   // ��ͬ���ͣ������� - ���� - ��ͬ���ͣ�
   private String contractTypeId;

   // ģ��������
   private String nameZH;

   // ģ��Ӣ����
   private String nameEN;

   // ����ʵ��
   private String entityIds;

   // ҵ������
   private String businessTypeIds;

   // ģ������
   private String content;

   // ģ����������
   private String contentType;

   // ģ������
   private String description;

   /**
    *    For App
    */
   @JsonIgnore
   // ģ��������������
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ǰ�˻���Ӧ�ķ���ʵ�弯��
   private List< MappingVO > entities = new ArrayList< MappingVO >();
   @JsonIgnore
   // �����˻���Ӧ��ҵ�����ͼ���
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // ����ʵ������
   private String[] entityIdArray = new String[] {};

   // ҵ����������
   private String[] businessTypeIdArray = new String[] {};

   // �ͻ�����
   private String[] clientIdsArray = new String[] {};

   private String clientIds;
   @JsonIgnore
   // ��ͬ��������
   private List< MappingVO > contractTypes = new ArrayList< MappingVO >();

   // ����
   private String constantId;

   // ��ѯ��ҵ������Id
   private String businessTypeId;

   // ��ѯ�÷���ʵ��Id
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

   // ת����ģ����������
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

   // ת���ú�ͬ����
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
         // ����ʵ�� - ����
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
         // ҵ������ - ����
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
