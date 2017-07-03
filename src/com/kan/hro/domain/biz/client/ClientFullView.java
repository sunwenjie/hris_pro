package com.kan.hro.domain.biz.client;

import java.net.URLEncoder;

import com.kan.base.domain.BaseView;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ClientFullView extends BaseView
{
   // serialVersionUID
   private static final long serialVersionUID = 3888156337681358552L;

   // �ͻ�������
   private String clientNameZH;

   // �ͻ�Ӣ����
   private String clientNameEN;

   // ����ID
   private String groupId;

   // �˻�ID
   private String accountId;

   // �˵���ַ������
   private String nameZH;

   // �˵���ַӢ����
   private String nameEN;

   // ����
   private String department;

   // ְλ
   private String position;

   // �ƺ�
   private String salutation;

   // ����
   private String cityId;

   // ��ַ
   private String address;

   // �ʱ�
   private String postcode;

   // ����
   private String email;

   // �绰
   private String phone;

   // �ֻ�
   private String mobile;

   // ��Ʊ����
   private String invoiceDate;

   // ��������
   private String paymentTerms;

   // �Ƿ���Ҫ�󶨺�ͬ
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
