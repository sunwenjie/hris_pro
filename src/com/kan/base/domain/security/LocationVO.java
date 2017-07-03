package com.kan.base.domain.security;

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
import com.kan.base.util.KANUtil;

public class LocationVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7588472621138905844L;

   // 地址ID（通常是指一个办公地点），Position与Location关联
   private String locationId;

   // 城市ID
   private String cityId;

   // 英文名称
   private String nameZH;

   // 中文名称
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
   @JsonIgnore
   private String provinceId;
   @JsonIgnore
   private String cityIdTemp;
   @JsonIgnore
   private List< MappingVO > provinces = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > locationStatuses = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.locationStatuses = KANUtil.getMappings( this.getLocale(), "security.location.statuses" );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
   }

   public String getDecodeCity()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( locationId );
   }

   public String getDecodeLocationStatus()
   {
      return decodeField( super.getStatus(), locationStatuses );
   }

   public void update( Object object )
   {
      final LocationVO locationVO = ( LocationVO ) object;
      this.cityId = locationVO.cityId;
      this.nameZH = locationVO.nameZH;
      this.nameEN = locationVO.nameEN;
      this.addressEN = locationVO.addressEN;
      this.addressZH = locationVO.addressZH;
      this.telephone = locationVO.telephone;
      this.fax = locationVO.fax;
      this.postcode = locationVO.postcode;
      this.email = locationVO.email;
      this.website = locationVO.website;
      this.description = locationVO.description;
      super.setStatus( locationVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public void reset()
   {
      this.cityId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.addressEN = "";
      this.addressZH = "";
      this.telephone = "";
      this.fax = "";
      this.postcode = "";
      this.email = "";
      this.website = "";
      this.description = "";
      super.setStatus( "0" );
   }

   public String getLocationId()
   {
      return locationId;
   }

   public void setLocationId( String locationId )
   {
      this.locationId = locationId;
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

   public List< MappingVO > getLocationStatuses()
   {
      return locationStatuses;
   }

   public void setLocationStatuses( List< MappingVO > locationStatuses )
   {
      this.locationStatuses = locationStatuses;
   }

}
