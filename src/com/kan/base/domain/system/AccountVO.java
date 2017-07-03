/*
 * Created on 2013-5-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin 
 */
public class AccountVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3059334367806042611L;

   /**
    * For DB
    */
   private String accountId;

   private String nameCN;

   private String nameEN;

   private String entityName;

   private String linkman;

   private String salutation;

   private String title;

   private String department;

   private String bizPhone;

   private String personalPhone;

   private String bizMobile;

   private String personalMobile;

   private String otherPhone;

   private String fax;

   private String bizEmail;

   private String personalEmail;

   private String website;

   private String canAdvBizEmail;

   private String canAdvPersonalEmail;

   private String canAdvBizSMS;

   private String canAdvPersonalSMS;

   private String cityId;

   private String address;

   private String postcode;

   private String bindIP;

   private String logoFile;

   private String imageFile;

   private String description;

   private String comment;

   private String initialized;
   
   private String accountType;

   /**
    * For Application
    */
   private String[] moduleIdArray;

   private String provinceId;

   private String cityIdTemp;

   private List< MappingVO > salutations;

   private List< MappingVO > initializeds;

   private List< MappingVO > provinces;
   
   private List< MappingVO > accountTypes;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.initializeds = KANUtil.getMappings( this.getLocale(), "account.initialized" );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.accountTypes=KANUtil.getMappings( this.getLocale(), "system.account.type" );
      
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
   }

   public String getDecodeCity()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getDecodeSalutation()
   {
      if ( this.salutations != null )
      {
         for ( MappingVO mappingVO : this.salutations )
         {
            if ( mappingVO.getMappingId().equals( salutation ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeInitialized()
   {
      if ( this.initializeds != null )
      {
         for ( MappingVO mappingVO : this.initializeds )
         {
            if ( mappingVO.getMappingId().equals( initialized ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   @Override
   public void update( final Object object )
   {
      update( object, false );
   }

   public void update( final Object object, final boolean isInternal )
   {
      final AccountVO accountVO = ( AccountVO ) object;

      if ( !isInternal )
      {
         this.nameCN = accountVO.getNameCN();
         this.nameEN = accountVO.getNameEN();
         this.entityName = accountVO.getEntityName();
      }

      this.linkman = accountVO.getLinkman();
      this.salutation = accountVO.getSalutation();
      this.title = accountVO.getTitle();
      this.department = accountVO.getDepartment();
      this.bizPhone = accountVO.getBizPhone();
      this.personalPhone = accountVO.getPersonalPhone();
      this.bizMobile = accountVO.getBizMobile();
      this.personalMobile = accountVO.getPersonalMobile();
      this.otherPhone = accountVO.getOtherPhone();
      this.fax = accountVO.getFax();
      this.bizEmail = accountVO.getBizEmail();
      this.personalEmail = accountVO.getPersonalEmail();
      this.website = accountVO.getPersonalEmail();
      this.accountType=accountVO.getAccountType();
      if ( !isInternal )
      {
         this.canAdvBizEmail = accountVO.getCanAdvBizEmail();
         this.canAdvPersonalEmail = accountVO.getCanAdvPersonalEmail();
         this.canAdvBizSMS = accountVO.getCanAdvBizSMS();
         this.canAdvPersonalSMS = accountVO.getCanAdvPersonalSMS();
      }

      this.cityId = accountVO.getCityId();
      this.address = accountVO.getAddress();
      this.postcode = accountVO.getPostcode();
      this.bindIP = accountVO.getBindIP();
      this.description = accountVO.getDescription();

      if ( !isInternal )
      {
         this.comment = accountVO.getComment();
      }

      this.moduleIdArray = accountVO.getModuleIdArray();

      if ( !isInternal && this.accountId != null && !this.accountId.trim().equals( "1" ) )
      {
         super.setStatus( accountVO.getStatus() );
      }

      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.nameCN = "";
      this.nameEN = "";
      this.entityName = "";
      this.linkman = "";
      this.salutation = "0";
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
      this.website = "";
      this.canAdvBizEmail = "";
      this.canAdvPersonalEmail = "";
      this.canAdvBizSMS = "";
      this.canAdvPersonalSMS = "";
      this.cityId = "0";
      this.address = "";
      this.postcode = "";
      this.bindIP = "";
      this.description = "";
      this.comment = "";
      this.moduleIdArray = null;
      this.provinceId = "";
      this.cityIdTemp = "";
      this.accountType="";
      super.setStatus( "0" );
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getNameCN()
   {
      return nameCN;
   }

   public void setNameCN( String nameCN )
   {
      this.nameCN = nameCN;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   public String getLinkman()
   {
      return linkman;
   }

   public void setLinkman( String linkman )
   {
      this.linkman = linkman;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
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

   public String getBindIP()
   {
      return bindIP;
   }

   public void setBindIP( String bindIP )
   {
      this.bindIP = bindIP;
   }

   public String getLogoFile()
   {
      return logoFile;
   }

   public void setLogoFile( String logoFile )
   {
      this.logoFile = logoFile;
   }

   public String getImageFile()
   {
      return imageFile;
   }

   public void setImageFile( String imageFile )
   {
      this.imageFile = imageFile;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment( String comment )
   {
      this.comment = comment;
   }

   public List< MappingVO > getSalutations()
   {
      return salutations;
   }

   public void setSalutations( List< MappingVO > salutations )
   {
      this.salutations = salutations;
   }

   public List< MappingVO > getInitializeds()
   {
      return initializeds;
   }

   public void setInitializeds( List< MappingVO > initializeds )
   {
      this.initializeds = initializeds;
   }

   public String getWebsite()
   {
      return website;
   }

   public void setWebsite( String website )
   {
      this.website = website;
   }

   public String getInitialized()
   {
      return initialized;
   }

   public void setInitialized( String initialized )
   {
      this.initialized = initialized;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( accountId == null || accountId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( accountId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
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

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   public String getAccountType()
   {
      return accountType;
   }

   public void setAccountType( String accountType )
   {
      this.accountType = accountType;
   }

   public List< MappingVO > getAccountTypes()
   {
      return accountTypes;
   }

   public void setAccountTypes( List< MappingVO > accountTypes )
   {
      this.accountTypes = accountTypes;
   }
   
}