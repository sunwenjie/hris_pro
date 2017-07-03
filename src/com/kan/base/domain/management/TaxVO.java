package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TaxVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 972704772098520245L;

   /**
    * For DB
    */
   // 税率ID
   private String taxId;

   // 法务实体ID
   private String entityId;

   // 业务类型ID
   private String businessTypeId;

   // 税率名称（中文）
   private String nameZH;

   // 税率名称（英文）
   private String nameEN;

   // 销售税率
   private String saleTax;

   // 成本税率
   private String costTax;

   // 实缴税率
   private String actualTax;

   // 描述
   private String description;

   /**
    * For Application
    */

   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();
   
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage() );
      if ( entities != null )
      {
         entities.add( 0, getEmptyMappingVO() );
      }
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage() );
      if ( businessTypes != null )
      {
         businessTypes.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.taxId = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.saleTax = "";
      this.costTax = "";
      this.actualTax = "";
      this.description = "";
      super.setStatus( "0" );

   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final TaxVO itemTaxVO = ( TaxVO ) object;
      this.entityId = itemTaxVO.getEntityId();
      this.businessTypeId = itemTaxVO.getBusinessTypeId();
      this.nameZH = itemTaxVO.getNameZH();
      this.nameEN = itemTaxVO.getNameEN();
      this.saleTax = itemTaxVO.getSaleTax();
      this.costTax = itemTaxVO.getCostTax();
      this.actualTax = itemTaxVO.getActualTax();
      this.description = itemTaxVO.getDescription();
      super.setStatus( itemTaxVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeEntity()
   {
      return decodeField( entityId, entities );
   }

   public String getDecodeBusinessType()
   {
      return decodeField( businessTypeId, businessTypes );
   }

   public String getTaxId()
   {
      return taxId;
   }

   public void setTaxId( String taxId )
   {
      this.taxId = taxId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNameZH();
         }
         else
         {
            return this.getNameEN();
         }
      }
      else
      {
         return this.getNameZH();
      }
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

   public String getSaleTax()
   {
      return KANUtil.filterEmpty( formatNumber( saleTax ) );
   }

   public void setSaleTax( String saleTax )
   {
      this.saleTax = saleTax;
   }

   public String getCostTax()
   {
      return KANUtil.filterEmpty( formatNumber( costTax ) );
   }

   public void setCostTax( String costTax )
   {
      this.costTax = costTax;
   }

   public String getActualTax()
   {
      return KANUtil.filterEmpty( formatNumber( actualTax ) );
   }

   public void setActualTax( String actualTax )
   {
      this.actualTax = actualTax;
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
      return encodedField( taxId );
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

}
