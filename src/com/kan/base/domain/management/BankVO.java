package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class BankVO extends BaseVO
{

   /**  
   * SerialVersionUID
   */

   private static final long serialVersionUID = 4448513902972052698L;

   // 银行Id
   private String bankId;

   // 城市ID
   private String cityId;

   // 银行名称（中文）
   private String nameZH;

   // 银行名称（英文）
   private String nameEN;

   // 中文地址
   private String addressZH;

   // 英文地址
   private String addressEN;

   // 电话
   private String telephone;

   // 传真 
   private String fax;

   // 邮编
   private String postcode;

   // 邮件地址
   private String email;

   // 网址
   private String website;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 省ID
   private String provinceId;

   private String cityIdTemp;
   @JsonIgnore
   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( bankId );
   }

   @Override
   public void reset() throws KANException
   {
      this.cityId = "0";
      this.nameZH = "";
      this.nameEN = "";
      this.addressZH = "";
      this.addressEN = "";
      this.telephone = "";
      this.fax = "";
      this.postcode = "";
      this.email = "";
      this.website = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final BankVO bankVO = ( BankVO ) object;
      this.cityId = bankVO.getCityId();
      this.nameZH = bankVO.getNameZH();
      this.nameEN = bankVO.getNameEN();
      this.addressZH = bankVO.getAddressZH();
      this.addressEN = bankVO.getAddressEN();
      this.telephone = bankVO.getTelephone();
      this.fax = bankVO.getFax();
      this.postcode = bankVO.getPostcode();
      this.email = bankVO.getEmail();
      this.website = bankVO.getWebsite();
      this.description = bankVO.getDescription();
      super.setStatus( bankVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeCity()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
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

   public String getAddressZH()
   {
      return addressZH;
   }

   public void setAddressZH( String addressZH )
   {
      this.addressZH = addressZH;
   }

   public String getAddressEN()
   {
      return addressEN;
   }

   public void setAddressEN( String addressEN )
   {
      this.addressEN = addressEN;
   }

   public String getTelephone()
   {
      return telephone;
   }

   public void setTelephone( String telephone )
   {
      this.telephone = telephone;
   }

   public String getFax()
   {
      return fax;
   }

   public void setFax( String fax )
   {
      this.fax = fax;
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

   public String getWebsite()
   {
      return website;
   }

   public void setWebsite( String website )
   {
      this.website = website;
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

}
