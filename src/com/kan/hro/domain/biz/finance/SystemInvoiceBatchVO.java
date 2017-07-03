package com.kan.hro.domain.biz.finance;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SystemInvoiceBatchVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -3355845589608305908L;
   
      // 批次Id
   private String batchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 客户Id
   private String clientId;

   // 订单Id
   private String orderId;


   // 月份（例如2013/9）
   private String monthly;

   // 开始时间 - 指Batch运行的时间
   private String startDate;

   // 结束时间 - 指Batch运行的时间
   private String endDate;

   // 描述
   private String description;

   /**
    * For App
    */

   // 客户名称
   private String clientNameZH;

   // 客户英文名称
   private String clientNameEN;

   // 包含客户数量
   private String countClientId;

   // 包含订单数量
   private String countOrderId;

   // 包含科目数量
   private String countItemId;
   
   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（公司成本）
   private String costAmountCompany;
   
   //
   private String taxAmount;

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 当前页面类型标记(Preview, Split, Merge)
   private String pageFlag;
   
   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast36Months( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( this.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( this.getLocale().getLanguage(), super.getCorpId() );

      // 添加"请选择"选项
      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }
      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }
      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }
   }
   
   
   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.clientId = "";
      this.orderId = "";
      this.monthly = "";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SystemInvoiceBatchVO systemInvoiceBatchVO = ( SystemInvoiceBatchVO ) object;
      this.entityId = systemInvoiceBatchVO.getEntityId();
      this.businessTypeId = systemInvoiceBatchVO.getBusinessTypeId();
      this.clientId = systemInvoiceBatchVO.getClientId();
      this.orderId = systemInvoiceBatchVO.getOrderId();
      this.monthly = systemInvoiceBatchVO.getMonthly();
      this.startDate = systemInvoiceBatchVO.getStartDate();
      this.endDate = systemInvoiceBatchVO.getEndDate();
      this.description = systemInvoiceBatchVO.getDescription();
      super.setStatus( systemInvoiceBatchVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setModifyBy( systemInvoiceBatchVO.getModifyBy() );
   }

   
   @Override
   public String getEncodedId() throws KANException
   {
      if ( batchId == null || batchId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( batchId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 法务实体ID编码
   public String getEncodedEntityId() throws KANException
   {
      return encodedField( entityId );
   }

   // 业务类型编码
   public String getEncodedBusinessTypeId() throws KANException
   {
      return encodedField( businessTypeId );
   }

   // 客户ID编码
   public String getEncodedClientId() throws KANException
   {
      return encodedField( clientId );
   }

   // 订单ID编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 获得法务实体名称
   public String getDecodeEntityId() throws KANException
   {
      if ( this.getEntityId() == null || this.getEntityId().trim().equals( "0" ) )
      {
         return null;
      }
      return decodeField( this.getEntityId(), this.entitys );
   }

   // 获得业务类型名称
   public String getDecodeBusinessTypeId() throws KANException
   {
      if ( this.getBusinessTypeId() == null || this.getBusinessTypeId().trim().equals( "0" ) )
      {
         return null;
      }
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }
   
   
   
   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = KANUtil.filterEmpty( entityId );
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = KANUtil.filterEmpty( businessTypeId );
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = KANUtil.filterEmpty( clientId );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = KANUtil.filterEmpty( orderId );
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }
   
   public String getBillAmountCompany()
   {
      return formatNumber(billAmountCompany);
   }


   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }


   public String getCostAmountCompany()
   {
      return formatNumber(costAmountCompany);
   }


   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   
   public String getPageFlag()
   {
      return pageFlag;
   }


   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }
   
   

   public String getTaxAmount()
   {
      return taxAmount;
   }


   public void setTaxAmount( String taxAmount )
   {
      this.taxAmount = taxAmount;
   }


   public String getClientName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getClientNameZH();
         }
         else
         {
            return this.getClientNameEN();
         }
      }
      else
      {
         return this.getClientNameZH();
      }
   }
}
