package com.kan.hro.domain.biz.client;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientInvoiceVO extends BaseVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 5122172742219538022L;

   // �˵���ַ����
   private String clientInvoiceId;

   // �ͻ�ID
   private String clientId;

   // ��Ʊ̨ͷ
   private String title;

   // �ƺ�
   private String salutation;

   // ���������ռ��ˣ�
   private String nameZH;

   // Ӣ�������ռ��ˣ�
   private String nameEN;

   // ְλ
   private String position;

   // ����
   private String department;

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

   // ����ʵ��
   private String legalEntity;

   // ����
   private String description;

   /**
    * For Application
    */
   // �ͻ���������
   private String clientNameZH;

   // �ͻ�Ӣ������
   private String clientNameEN;

   // �ͻ����
   private String clientNumber;

   private String provinceId;

   private String cityIdTemp;

   private List< MappingVO > provinces;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
   }

   // ��ȡ��Ʊ��ַ״̬
   public String getDecodeStatus()
   {
      return decodeField( super.getStatus(), super.getStatuses() );
   }

   // �ͻ�ID����
   public String getEncodedClientId() throws KANException
   {
      return encodedField( clientId );
   }

   // ��ó���
   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   // ��ȡ�ƺ�
   public String getDecodeSalutation()
   {
      return decodeField( salutation, KANUtil.getMappings( this.getLocale(), "salutation" ) );
   }

   @Override
   public void update( Object object )
   {
      final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) object;
      this.clientId = clientInvoiceVO.getClientId();
      this.title = clientInvoiceVO.getTitle();
      this.nameZH = clientInvoiceVO.getNameZH();
      this.nameEN = clientInvoiceVO.getNameEN();
      this.position = clientInvoiceVO.getPosition();
      this.salutation = clientInvoiceVO.getSalutation();
      this.department = clientInvoiceVO.getDepartment();
      this.cityId = clientInvoiceVO.getCityId();
      this.address = clientInvoiceVO.getAddress();
      this.postcode = clientInvoiceVO.getPostcode();
      this.email = clientInvoiceVO.getEmail();
      this.phone = clientInvoiceVO.getPhone();
      this.mobile = clientInvoiceVO.getMobile();
      this.invoiceDate = clientInvoiceVO.getInvoiceDate();
      this.paymentTerms = clientInvoiceVO.getPaymentTerms();
      this.legalEntity = clientInvoiceVO.getLegalEntity();
      this.description = clientInvoiceVO.getDescription();
      super.setStatus( clientInvoiceVO.getStatus() );
      super.setClientId( clientInvoiceVO.getClientId() );
      super.setModifyBy( clientInvoiceVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( clientInvoiceVO.getCorpId() );
   }

   @Override
   public void reset() throws KANException
   {
      this.clientId = "";
      this.title = "";
      this.nameZH = "";
      this.nameEN = "";
      this.position = "";
      this.department = "";
      this.cityId = "";
      this.address = "";
      this.postcode = "";
      this.email = "";
      this.phone = "";
      this.mobile = "";
      this.invoiceDate = "0";
      this.paymentTerms = "";
      this.legalEntity = "";
      this.description = "";
      this.salutation = "0";
      super.setStatus( "0" );
      super.setClientId( "" );
      super.setCorpId( "" );
      super.setCorpId( "" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.clientInvoiceId );
   }

   public String getClientInvoiceId()
   {
      return clientInvoiceId;
   }

   public void setClientInvoiceId( String clientInvoiceId )
   {
      this.clientInvoiceId = clientInvoiceId;
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
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

   public final String getName()
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

   public String getPosition()
   {
      return position;
   }

   public void setPosition( String position )
   {
      this.position = position;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
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

   public String getLegalEntity()
   {
      return legalEntity;
   }

   public void setLegalEntity( String legalEntity )
   {
      this.legalEntity = legalEntity;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCityIdTemp()
   {
      return cityIdTemp;
   }

   public void setCityIdTemp( String cityIdTemp )
   {
      this.cityIdTemp = cityIdTemp;
   }

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
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

   public final String getClientName()
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
