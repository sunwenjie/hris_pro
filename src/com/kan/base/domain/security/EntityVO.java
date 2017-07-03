package com.kan.base.domain.security;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class EntityVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 4911212493340552781L;

   /**
    * For DB
    */
   private String entityId;

   // 独立报税
   private String independenceTax;

   private String locationId;

   private String title;

   private String nameZH;

   private String nameEN;

   private String bizType;

   // 用户劳动合同模板和文件模板
   private String logoFile;

   private String logoSize;

   private String description;

   /**
    * For Application
    */
   private List< MappingVO > locations;

   private List< MappingVO > bizTypes;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.locations = KANConstants.getKANAccountConstants( super.getAccountId() ).getLocations( this.getLocale().getLanguage(), getCorpId() );
      if ( this.locations != null )
      {
         this.locations.add( 0, super.getEmptyMappingVO() );
      }
      this.bizTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), getCorpId() );
      if ( bizTypes != null )
      {
         bizTypes.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.locationId = "";
      this.title = "";
      this.nameZH = "";
      this.nameEN = "";
      this.bizType = "";
      this.description = "";
      this.independenceTax = "";
      this.logoFile = "";
      this.logoSize = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EntityVO entityVO = ( EntityVO ) object;
      this.independenceTax = entityVO.getIndependenceTax();
      this.locationId = entityVO.getLocationId();
      this.title = entityVO.getTitle();
      this.nameZH = entityVO.getNameZH();
      this.nameEN = entityVO.getNameEN();
      this.bizType = entityVO.getBizType();
      this.logoFile = entityVO.getLogoFile();
      this.logoSize = entityVO.getLogoSize();
      this.description = entityVO.getDescription();
      super.setStatus( entityVO.getStatus() );
      super.setModifyBy( entityVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeLocationId()
   {
      return decodeField( locationId, locations );
   }

   public String getDecodeBizType()
   {
      return decodeField( bizType, bizTypes );
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getLocationId()
   {
      return locationId;
   }

   public void setLocationId( String locationId )
   {
      this.locationId = locationId;
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

   public String getBizType()
   {
      return bizType;
   }

   public void setBizType( String bizType )
   {
      this.bizType = bizType;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( entityId );
   }

   public List< MappingVO > getLocations()
   {
      return locations;
   }

   public void setLocations( List< MappingVO > locations )
   {
      this.locations = locations;
   }

   public List< MappingVO > getBizTypes()
   {
      return bizTypes;
   }

   public void setBizTypes( List< MappingVO > bizTypes )
   {
      this.bizTypes = bizTypes;
   }

   public String getEntityName( final String localeLanguage )
   {
      if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
      {
         return nameZH;
      }
      else
      {
         return nameEN;
      }
   }

   public String getIndependenceTax()
   {
      return independenceTax;
   }

   public void setIndependenceTax( String independenceTax )
   {
      this.independenceTax = independenceTax;
   }

   public String getLogoFile()
   {
      return logoFile;
   }

   public void setLogoFile( String logoFile )
   {
      this.logoFile = logoFile;
   }

   public String getLogoSize()
   {
      return logoSize;
   }

   public void setLogoSize( String logoSize )
   {
      this.logoSize = logoSize;
   }

}
