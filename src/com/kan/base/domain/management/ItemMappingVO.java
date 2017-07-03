package com.kan.base.domain.management;

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

public class ItemMappingVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 3353162172063632849L;

   /**
    * For DB
    */
   // 科目映射ID
   private String mappingId;

   // 科目ID
   private String itemId;

   // 法务实体ID
   private String entityId;

   // 业务类型ID
   private String businessTypeId;

   // 销售 - 借
   private String saleDebit;

   // 销售 - 借（按部门），是/否
   private String saleDebitBranch;

   // 销售 - 借（按部门），是/否
   private String saleCredit;

   // 销售 - 贷（按部门），是/否
   private String saleCreditBranch;

   // 成本 - 借
   private String costDebit;

   // 成本 - 借（按部门），是/否
   private String costDebitBranch;

   // 成本 - 贷
   private String costCredit;

   // 成本 - 贷（按部门），是/否
   private String costCreditBranch;
   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 科目
   private List< MappingVO > items = new ArrayList< MappingVO >();
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( request.getLocale().getLanguage(), getCorpId() );
      if ( items != null )
      {
         items.add( 0, getEmptyMappingVO() );
      }
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), getCorpId() );
      if ( entitys != null )
      {
         entitys.add( 0, getEmptyMappingVO() );
      }
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), getCorpId() );
      if ( businessTypes != null )
      {
         businessTypes.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.itemId = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.saleDebit = "";
      this.saleDebitBranch = "";
      this.saleCredit = "";
      this.saleCreditBranch = "";
      this.costDebit = "";
      this.costDebitBranch = "";
      this.costCredit = "";
      this.costCreditBranch = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ItemMappingVO itemMappingVO = ( ItemMappingVO ) object;
      this.itemId = itemMappingVO.getItemId();
      this.entityId = itemMappingVO.getEntityId();
      this.businessTypeId = itemMappingVO.getBusinessTypeId();
      this.saleDebit = itemMappingVO.getSaleDebit();
      this.saleDebitBranch = itemMappingVO.getSaleDebitBranch();
      this.saleCredit = itemMappingVO.getSaleCredit();
      this.saleCreditBranch = itemMappingVO.getSaleCreditBranch();
      this.costDebit = itemMappingVO.getCostDebit();
      this.costDebitBranch = itemMappingVO.getCostDebitBranch();
      this.costCredit = itemMappingVO.getCostCredit();
      this.costCreditBranch = itemMappingVO.getCostCreditBranch();
      this.description = itemMappingVO.getDescription();
      super.setStatus( itemMappingVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeItem()
   {
      return decodeField( itemId, items );
   }

   public String getDecodeEntity()
   {
      return decodeField( businessTypeId, businessTypes );
   }

   public String getDecodeBusinessType()
   {
      return decodeField( entityId, entitys );
   }

   public String getMappingId()
   {
      return mappingId;
   }

   public void setMappingId( String mappingId )
   {
      this.mappingId = mappingId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
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

   public String getSaleDebit()
   {
      return saleDebit;
   }

   public void setSaleDebit( String saleDebit )
   {
      this.saleDebit = saleDebit;
   }

   public String getSaleDebitBranch()
   {
      return saleDebitBranch;
   }

   public void setSaleDebitBranch( String saleDebitBranch )
   {
      this.saleDebitBranch = saleDebitBranch;
   }

   public String getSaleCredit()
   {
      return saleCredit;
   }

   public void setSaleCredit( String saleCredit )
   {
      this.saleCredit = saleCredit;
   }

   public String getSaleCreditBranch()
   {
      return saleCreditBranch;
   }

   public void setSaleCreditBranch( String saleCreditBranch )
   {
      this.saleCreditBranch = saleCreditBranch;
   }

   public String getCostDebit()
   {
      return costDebit;
   }

   public void setCostDebit( String costDebit )
   {
      this.costDebit = costDebit;
   }

   public String getCostDebitBranch()
   {
      return costDebitBranch;
   }

   public void setCostDebitBranch( String costDebitBranch )
   {
      this.costDebitBranch = costDebitBranch;
   }

   public String getCostCredit()
   {
      return costCredit;
   }

   public void setCostCredit( String costCredit )
   {
      this.costCredit = costCredit;
   }

   public String getCostCreditBranch()
   {
      return costCreditBranch;
   }

   public void setCostCreditBranch( String costCreditBranch )
   {
      this.costCreditBranch = costCreditBranch;
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
      return encodedField( mappingId );
   }

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

}
