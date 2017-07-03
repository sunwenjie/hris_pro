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

   // �����ͬģ��Id
   private String templateId;

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
   // ģ��������������
   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();

   // ����ʵ������
   private String[] entityIdArray = new String[] {};

   // ҵ����������
   private String[] businessTypeIdArray = new String[] {};

   // ��ǰ�˻���Ӧ�ķ���ʵ�弯��
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // �����˻���Ӧ��ҵ�����ͼ���
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // ����
   private String constantId;

   // ��ѯ��ҵ������Id
   private String businessTypeId;

   // ��ѯ�÷���ʵ��Id
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

   // ת����ģ����������
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
         // ҵ������ - ����
         for ( String entityId : this.getEntityIdArray() )
         {
            if ( entityNames != null && !entityNames.trim().isEmpty() )
            {
               entityNames = entityNames + "��";
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
         // ҵ������ - ����
         for ( String businessTypeId : this.getBusinessTypeIdArray() )
         {
            if ( businessTypeNames != null && !businessTypeNames.trim().isEmpty() )
            {
               businessTypeNames = businessTypeNames + "��";
            }

            businessTypeNames = businessTypeNames + decodeField( businessTypeId, this.businessTypes );
         }
      }

      return businessTypeNames;
   }

}
