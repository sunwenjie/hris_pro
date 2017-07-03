package com.kan.hro.domain.biz.client;

import java.net.URLEncoder;

import com.kan.base.domain.BaseView;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ClientFullView extends BaseView
{
   // serialVersionUID
   private static final long serialVersionUID = 3888156337681358552L;

   // 客户中文名
   private String clientNameZH;

   // 客户英文名
   private String clientNameEN;

   // 集团ID
   private String groupId;

   // 账户ID
   private String accountId;

   // 账单地址中文名
   private String nameZH;

   // 账单地址英文名
   private String nameEN;

   // 部门
   private String department;

   // 职位
   private String position;

   // 称呼
   private String salutation;

   // 城市
   private String cityId;

   // 地址
   private String address;

   // 邮编
   private String postcode;

   // 邮箱
   private String email;

   // 电话
   private String phone;

   // 手机
   private String mobile;

   // 发票日期
   private String invoiceDate;

   // 付款周期
   private String paymentTerms;

   // 是否需要绑定合同
   private String orderBindContract;

   public String getEncodedClientId() throws KANException
   {
      if ( super.getId() == null || super.getId().trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( super.getId() ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
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

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getPosition()
   {
      return position;
   }

   public void setPosition( String position )
   {
      this.position = position;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress( String address )
   {
      this.address = address;
   }

   public String getPostcode()
   {
      return postcode;
   }

   public void setPostcode( String postcode )
   {
      this.postcode = postcode;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone( String phone )
   {
      this.phone = phone;
   }

   public String getMobile()
   {
      return mobile;
   }

   public void setMobile( String mobile )
   {
      this.mobile = mobile;
   }

   public String getInvoiceDate()
   {
      return invoiceDate;
   }

   public void setInvoiceDate( String invoiceDate )
   {
      this.invoiceDate = invoiceDate;
   }

   public String getPaymentTerms()
   {
      return paymentTerms;
   }

   public void setPaymentTerms( String paymentTerms )
   {
      this.paymentTerms = paymentTerms;
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

   public String getOrderBindContract()
   {
      return orderBindContract;
   }

   public void setOrderBindContract( String orderBindContract )
   {
      this.orderBindContract = orderBindContract;
   }

}
