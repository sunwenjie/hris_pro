package com.kan.hro.domain.biz.client;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class ClientOrderSBVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = 7440694497772277760L;

   // 订单社保Id，主键
   private String orderSbId;

   // 订单主表Id
   private String orderHeaderId;

   // 社保方案Id   
   private String sbSolutionId;

   // 供应商Id
   private String vendorId;

   // 供应商服务Id
   private String vendorServiceId;

   // 社保个人部分公司承担
   private String personalSBBurden;

   // 描述
   private String description;

   /**
    * For App
    */
   @JsonIgnore
   // 订单名称（中文）
   private String orderHeaderNameZH;
   @JsonIgnore
   // 订单名称（英文）
   private String orderHeaderNameEN;
   @JsonIgnore
   // 社保方案名称（中文）
   private String sbSolutionNameZH;
   @JsonIgnore
   // 社保方案名称（英文）
   private String sbSolutionNameEN;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderSbId );
   }

   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.sbSolutionId = "";
      this.vendorId = "";
      this.vendorServiceId = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderSBVO clientOrderHeaderRuleVO = ( ClientOrderSBVO ) object;
      this.orderHeaderId = clientOrderHeaderRuleVO.getOrderHeaderId();
      this.sbSolutionId = clientOrderHeaderRuleVO.getSbSolutionId();
      this.vendorId = clientOrderHeaderRuleVO.getVendorId();
      this.vendorServiceId = clientOrderHeaderRuleVO.getVendorServiceId();
      this.personalSBBurden = clientOrderHeaderRuleVO.getPersonalSBBurden();
      this.description = clientOrderHeaderRuleVO.getDescription();
      super.setStatus( clientOrderHeaderRuleVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getOrderSbId()
   {
      return orderSbId;
   }

   public void setOrderSbId( String orderSbId )
   {
      this.orderSbId = orderSbId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getOrderHeaderName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getOrderHeaderNameZH();
         }
         else
         {
            return this.getOrderHeaderNameEN();
         }
      }
      else
      {
         return this.getOrderHeaderNameZH();
      }
   }

   public String getOrderHeaderNameZH()
   {
      return orderHeaderNameZH;
   }

   public void setOrderHeaderNameZH( String orderHeaderNameZH )
   {
      this.orderHeaderNameZH = orderHeaderNameZH;
   }

   public String getOrderHeaderNameEN()
   {
      return orderHeaderNameEN;
   }

   public void setOrderHeaderNameEN( String orderHeaderNameEN )
   {
      this.orderHeaderNameEN = orderHeaderNameEN;
   }

   public String getSbSolutionName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getSbSolutionNameZH();
         }
         else
         {
            return this.getSbSolutionNameEN();
         }
      }
      else
      {
         return this.getSbSolutionNameZH();
      }
   }

   public String getSbSolutionNameZH()
   {
      return sbSolutionNameZH;
   }

   public void setSbSolutionNameZH( String sbSolutionNameZH )
   {
      this.sbSolutionNameZH = sbSolutionNameZH;
   }

   public String getSbSolutionNameEN()
   {
      return sbSolutionNameEN;
   }

   public void setSbSolutionNameEN( String sbSolutionNameEN )
   {
      this.sbSolutionNameEN = sbSolutionNameEN;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getVendorServiceId()
   {
      return vendorServiceId;
   }

   public void setVendorServiceId( String vendorServiceId )
   {
      this.vendorServiceId = vendorServiceId;
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

}
