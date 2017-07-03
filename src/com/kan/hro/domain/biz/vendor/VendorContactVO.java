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

   // 供应商联系人ID
   private String vendorContactId;

   // 供应商ID
   private String vendorId;

   // 称呼，男士/女士
   private String salutation;

   // 联系人名（中文）
   private String nameZH;

   // 联系人名（英文）
   private String nameEN;

   // 职位
   private String title;

   // 部门
   private String department;

   // 工作电话号
   private String bizPhone;

   // 私人电话号
   private String personalPhone;

   // 工作手机号
   private String bizMobile;

   // 私人手机号
   private String personalMobile;

   // 其他联系方式
   private String otherPhone;

   // 传真
   private String fax;

   // 工作电子邮箱
   private String bizEmail;

   // 私人电子邮箱
   private String personalEmail;

   // 描述/说明
   private String comment;

   // 工作邮箱推送
   private String canAdvBizEmail;

   // 私人邮箱推送
   private String canAdvPersonalEmail;

   // 工作短信推送
   private String canAdvBizSMS;

   // 私人短信推送
   private String canAdvPersonalSMS;

   // 所在城市ID
   private String cityId;

   // 地址
   private String address;

   // 邮编
   private String postcode;

   // 所属人部门
   private String branch;

   // 所属人 
   private String owner;

   /**
    * For Application
    */
   @JsonIgnore
   // 供应商联系人称谓
   private List< MappingVO > salutations;

   // 供应商名称
   private String vendorName;

   // 供应商名称（中文）
   private String vendorNameZH;

   // 供应商名称（英文）
   private String vendorNameEN;

   // 所属人部门 - 名称
   private String ownerName;

   // 省ID
   private String provinceId;

   private String cityIdTemp;
   @JsonIgnore
   private List< MappingVO > provinces;

   // 用户名
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

   // 解译称呼
   public String getDecodeSalutation()
   {
      return decodeField( salutation, salutations );
   }

   // 解译所属人
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

   // 加密供应商ID
   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
   }

   // 加密主键
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
