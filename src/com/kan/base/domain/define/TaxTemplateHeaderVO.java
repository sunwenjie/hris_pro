package com.kan.base.domain.define;

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

public class TaxTemplateHeaderVO extends BaseVO
{

   /**  
   * Serial Version UID:
   */
   private static final long serialVersionUID = -1831092271855675777L;

   // 个税模板主表ID
   private String templateHeaderId;

   // 城市ID
   private String cityId;

   // 个税模板中文名
   private String nameZH;

   // 个税模板英文名
   private String nameEN;

   // 描述
   private String description;

   /**
    * For Application
    */

   // 省ID
   private String provinceId;

   private String cityIdTemp;
   @JsonIgnore
   // 省份 - Mapping
   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
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
      return encodedField( templateHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.cityId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) object;
      this.cityId = taxTemplateHeaderVO.getCityId();
      this.nameZH = taxTemplateHeaderVO.getNameZH();
      this.nameEN = taxTemplateHeaderVO.getNameEN();
      this.description = taxTemplateHeaderVO.getDescription();
      super.setStatus( taxTemplateHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getTemplateHeaderId()
   {
      return templateHeaderId;
   }

   public void setTemplateHeaderId( String templateHeaderId )
   {
      this.templateHeaderId = templateHeaderId;
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
