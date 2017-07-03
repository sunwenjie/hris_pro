package com.kan.hro.domain.biz.vendor;

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

public class VendorServiceVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7551555050431288867L;

   /**
    * For DB
    */

   // ����ID
   private String serviceId;

   // ��Ӧ��ID
   private String vendorId;

   // ����ID
   private String cityId;

   // �籣����ID
   private String sbHeaderId;

   // �籣����ID - {1:2:3:4:5}
   private String serviceIds;

   // �����
   private String serviceFee;

   // ����
   private String description;

   /**
    * For Application
    */

   // ��������ID
   private String[] serviceArray;
   @JsonIgnore
   // �籣����
   private List< MappingVO > sbs = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������
   private List< MappingVO > serviceContents = new ArrayList< MappingVO >();

   // ʡID
   private String provinceId;

   private String cityIdTemp;
   @JsonIgnore
   private List< MappingVO > provinces;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.serviceContents = KANUtil.getMappings( request.getLocale(), "business.vendor.service.item" );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
      this.sbs = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() );
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( this.cityId, super.getLocale().getLanguage() );
   }

   public String getDecodeServiceIds()
   {
      String returnString = "";
      if ( this.serviceContents != null && serviceIds != null )
      {
         String serviceArray[] = KANUtil.jasonArrayToString( serviceIds ).split( "," );

         for ( String arrayItem : serviceArray )
         {
            for ( MappingVO mappingVO : this.serviceContents )
            {
               if ( mappingVO.getMappingId().equals( arrayItem ) )
               {
                  if ( returnString.equals( "" ) )
                  {
                     returnString = mappingVO.getMappingValue();
                  }
                  else
                  {
                     returnString = returnString + " + " + mappingVO.getMappingValue();
                  }
                  break;
               }
            }
         }
      }
      return returnString;
   }

   public String getDecodeSBs()
   {
      return decodeField( sbHeaderId, sbs );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( serviceId );
   }

   @Override
   public void reset() throws KANException
   {
      this.vendorId = "";
      this.cityId = "";
      this.sbHeaderId = "";
      this.serviceIds = "";
      this.serviceFee = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final VendorServiceVO vendorServiceVO = ( VendorServiceVO ) object;
      this.sbHeaderId = vendorServiceVO.getSbHeaderId();
      this.serviceIds = vendorServiceVO.getServiceIds();
      this.serviceFee = vendorServiceVO.getServiceFee();
      this.description = vendorServiceVO.getDescription();
      super.setStatus( vendorServiceVO.getStatus() );
      super.setModifyDate( new Date() );
      this.serviceArray = vendorServiceVO.getServiceArray();
   }

   public String getServiceId()
   {
      return serviceId;
   }

   public void setServiceId( String serviceId )
   {
      this.serviceId = serviceId;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getSbHeaderId()
   {
      return sbHeaderId;
   }

   public void setSbHeaderId( String sbHeaderId )
   {
      this.sbHeaderId = sbHeaderId;
   }

   public String getServiceIds()
   {
      return serviceIds;
   }

   public void setServiceIds( String serviceIds )
   {
      this.serviceIds = serviceIds;
   }

   public String getServiceFee()
   {
      return serviceFee;
   }

   public void setServiceFee( String serviceFee )
   {
      this.serviceFee = serviceFee;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String[] getServiceArray()
   {
      return serviceArray;
   }

   public void setServiceArray( String[] serviceArray )
   {
      this.serviceArray = serviceArray;
   }

   public List< MappingVO > getServiceContents()
   {
      return serviceContents;
   }

   public void setServiceContents( List< MappingVO > serviceContents )
   {
      this.serviceContents = serviceContents;
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

   public List< MappingVO > getSbs()
   {
      return sbs;
   }

   public void setSbs( List< MappingVO > sbs )
   {
      this.sbs = sbs;
   }

}
