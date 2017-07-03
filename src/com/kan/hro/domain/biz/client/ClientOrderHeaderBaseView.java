package com.kan.hro.domain.biz.client;

import com.kan.base.domain.BaseView;

public class ClientOrderHeaderBaseView extends BaseView
{
   // serialVersionUID:TODO（用一句话描述这个变量表示什么）  
   private static final long serialVersionUID = 1L;
   
   private String accountId;
   // 订单主表Id，主键
   private String orderHeaderId;

   // 客户Id
   private String clientId;
   
   // 法务实体Id，默认选择商务合同带出，也可以更换
   private String entityId;

   // 业务类型Id，默认选择商务合同带出，也可以更换
   private String businessTypeId;
   
   // 订单名称（中文）
   private String nameZH;

   // 订单名称（英文）
   private String nameEN;
   
   private String startDate;
   
   private String endDate;

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
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

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getStartDate()
   {
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }
   
   
}
