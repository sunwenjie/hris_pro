package com.kan.hro.domain.biz.employee;

import java.io.Serializable;

public class EmployeeContractBaseView implements Serializable
{
   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = 4864618017827788671L;

   // 雇员合同ID
   private String id;

   // 账户ID
   private String accountId;

   // 法务实体
   private String entityId;

   // 业务类型
   private String businessTypeId;

   // 客户ID
   private String clientId;

   // 客户名
   private String clientName;

   // 雇员ID
   private String employeeId;

   // 雇员名（中文）
   private String employeeNameZH;

   // 雇员名（英文）
   private String employeeNameEN;

   // 订单ID
   private String orderId;

   // 服务协议名 
   private String name;

   public EmployeeContractBaseView()
   {
      super();
   }

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
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

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

}
