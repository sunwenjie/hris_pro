package com.kan.hro.domain.biz.client;

import com.kan.base.domain.BaseView;

public class ClientOrderHeaderBaseView extends BaseView
{
   // serialVersionUID:TODO����һ�仰�������������ʾʲô��  
   private static final long serialVersionUID = 1L;
   
   private String accountId;
   // ��������Id������
   private String orderHeaderId;

   // �ͻ�Id
   private String clientId;
   
   // ����ʵ��Id��Ĭ��ѡ�������ͬ������Ҳ���Ը���
   private String entityId;

   // ҵ������Id��Ĭ��ѡ�������ͬ������Ҳ���Ը���
   private String businessTypeId;
   
   // �������ƣ����ģ�
   private String nameZH;

   // �������ƣ�Ӣ�ģ�
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
