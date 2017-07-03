package com.kan.hro.domain.biz.vendor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class VendorContactVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -5081129290001694113L;

   /**
    * For DB
    */

   // ��Ӧ����ϵ��ID
   private String vendorContactId;

   // ��Ӧ��ID
   private String vendorId;

   // �ƺ�����ʿ/Ůʿ
   private String salutation;

   // ��ϵ���������ģ�
   private String nameZH;

   // ��ϵ������Ӣ�ģ�
   private String nameEN;

   // ְλ
   private String title;

   // ����
   private String department;

   // �����绰��
   private String bizPhone;

   // ˽�˵绰��
   private String personalPhone;

   // �����ֻ���
   private String bizMobile;

   // ˽���ֻ���
   private String personalMobile;

   // ������ϵ��ʽ
   private String otherPhone;

   // ����
   private String fax;

   // ������������
   private String bizEmail;

   // ˽�˵�������
   private String personalEmail;

   // ����/˵��
   private String comment;

   // ������������
   private String canAdvBizEmail;

   // ˽����������
   private String canAdvPersonalEmail;

   // ������������
   private String canAdvBizSMS;

   // ˽�˶�������
   private String canAdvPersonalSMS;

   // ���ڳ���ID
   private String cityId;

   // ��ַ
   private String address;

   // �ʱ�
   private String postcode;

   // �����˲���
   private String branch;

   // ������ 
   private String owner;

   /**
    * For Application
    */
   @JsonIgnore
   // ��Ӧ����ϵ�˳�ν
   private List< MappingVO > salutations;

   // ��Ӧ������
   private String vendorName;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   // �����˲��� - ����
   private String ownerName;

   // ʡID
   private String provinceId;

   private String cityIdTemp;
   @JsonIgnore
   private List< MappingVO > provinces;

   // �û���
   private String username;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
      this.ownerName = KANConstants.getKANAccountConstants( super.getAccountId() ).getStaffNameByStaffId( owner );
      this.vendorName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? this.vendorNameZH : this.vendorNameEN;
   }

   @Override
   public void reset() throws KANException
   {
      this.vendorId = "";
      this.salutation = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.title = "";
      this.department = "";
      this.bizPhone = "";
      this.personalPhone = "";
      this.bizMobile = "";
      this.personalMobile = "";
      this.otherPhone = "";
      this.fax = "";
      this.bizEmail = "";
      this.personalEmail = "";
      this.comment = "";
      this.canAdvBizEmail = "";
      this.canAdvPersonalEmail = "";
      this.canAdvBizSMS = "";
      this.canAdvPersonalSMS = "";
      this.cityId = "";
      this.address = "";
      this.postcode = "";
      this.branch = "";
      this.owner = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final VendorContactVO vendorContactVO = ( VendorContactVO ) object;
      this.vendorId = vendorContactVO.getVendorId();
      this.salutation = vendorContactVO.getSalutation();
      this.nameZH = vendorContactVO.getNameZH();
      this.nameEN = vendorContactVO.getNameEN();
      this.title = vendorContactVO.getTitle();
      this.department = vendorContactVO.getDepartment();
      this.bizPhone = vendorContactVO.getBizPhone();
      this.personalPhone = vendorContactVO.getPersonalPhone();
      this.bizMobile = vendorContactVO.getBizMobile();
      this.personalMobile = vendorContactVO.getPersonalMobile();
      this.otherPhone = vendorContactVO.getOtherPhone();
      this.fax = vendorContactVO.getFax();
      this.bizEmail = vendorContactVO.getBizEmail();
      this.personalEmail = vendorContactVO.getPersonalEmail();
      this.comment = vendorContactVO.getComment();
      this.canAdvBizEmail = vendorContactVO.getCanAdvBizEmail();
      this.canAdvPersonalEmail = vendorContactVO.getCanAdvPersonalEmail();
      this.canAdvBizSMS = vendorContactVO.getCanAdvBizSMS();
      this.canAdvPersonalSMS = vendorContactVO.getCanAdvPersonalSMS();
      this.cityId = vendorContactVO.getCityId();
      this.address = vendorContactVO.getAddress();
      this.postcode = vendorContactVO.getPostcode();
      this.branch = vendorContactVO.getBranch();
      this.owner = vendorContactVO.getOwner();
      super.setStatus( vendorContactVO.getStatus() );
      super.setModifyDate( new Date() );
      this.username = vendorContactVO.getUsername();
   }

   // ����ƺ�
   public String getDecodeSalutation()
   {
      return decodeField( salutation, salutations );
   }

   // ����������
   public String getDecodeOwner()
   {
      return this.ownerName;
   }

   public String getDecodeCityId()
   {
      if ( KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() ) == null )
      {
         return "";
      }
      else
      {
         return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
      }
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   // ���ܹ�Ӧ��ID
   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
   }

   // ��������
   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( vendorContactId );
   }

   public String getVendorContactId()
   {
      return vendorContactId;
   }

   public void setVendorContactId( String vendorContactId )
   {
      this.vendorContactId = vendorContactId;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
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

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getBizPhone()
   {
      return bizPhone;
   }

   public void setBizPhone( String bizPhone )
   {
      this.bizPhone = bizPhone;
   }

   public String getPersonalPhone()
   {
      return personalPhone;
   }

   public void setPersonalPhone( String personalPhone )
   {
      this.personalPhone = personalPhone;
   }

   public String getBizMobile()
   {
      return bizMobile;
   }

   public void setBizMobile( String bizMobile )
   {
      this.bizMobile = bizMobile;
   }

   public String getPersonalMobile()
   {
      return personalMobile;
   }

   public void setPersonalMobile( String personalMobile )
   {
      this.personalMobile = personalMobile;
   }

   public String getOtherPhone()
   {
      return otherPhone;
   }

   public void setOtherPhone( String otherPhone )
   {
      this.otherPhone = otherPhone;
   }

   public String getFax()
   {
      return fax;
   }

   public void setFax( String fax )
   {
      this.fax = fax;
   }

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getPersonalEmail()
   {
      return personalEmail;
   }

   public void setPersonalEmail( String personalEmail )
   {
      this.personalEmail = personalEmail;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment( String comment )
   {
      this.comment = comment;
   }

   public String getCanAdvBizEmail()
   {
      return canAdvBizEmail;
   }

   public void setCanAdvBizEmail( String canAdvBizEmail )
   {
      this.canAdvBizEmail = canAdvBizEmail;
   }

   public String getCanAdvPersonalEmail()
   {
      return canAdvPersonalEmail;
   }

   public void setCanAdvPersonalEmail( String canAdvPersonalEmail )
   {
      this.canAdvPersonalEmail = canAdvPersonalEmail;
   }

   public String getCanAdvBizSMS()
   {
      return canAdvBizSMS;
   }

   public void setCanAdvBizSMS( String canAdvBizSMS )
   {
      this.canAdvBizSMS = canAdvBizSMS;
   }

   public String getCanAdvPersonalSMS()
   {
      return canAdvPersonalSMS;
   }

   public void setCanAdvPersonalSMS( String canAdvPersonalSMS )
   {
      this.canAdvPersonalSMS = canAdvPersonalSMS;
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

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public List< MappingVO > getSalutations()
   {
      return salutations;
   }

   public void setSalutations( List< MappingVO > salutations )
   {
      this.salutations = salutations;
   }

   public String getVendorName()
   {
      return vendorName;
   }

   public void setVendorName( String vendorName )
   {
      this.vendorName = vendorName;
   }

   public String getOwnerName()
   {
      return ownerName;
   }

   public void setOwnerName( String ownerName )
   {
      this.ownerName = ownerName;
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

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

}
