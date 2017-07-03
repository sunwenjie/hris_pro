package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PositionGradeCurrencyVO extends BaseVO
{

   /**
    * For Serial Version UID
    */
   private static final long serialVersionUID = -4986453620030868479L;

   /**
    * For DB
    */
   private String positionGradeId;

   private String currencyId;

   private float minSalary;

   private float maxSalary;

   @Override
   public void reset() throws KANException
   {
      this.currencyId = "";
      this.minSalary = 0F;
      this.maxSalary = 0F;
      super.setStatus( "" );

   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) object;
      this.currencyId = positionGradeCurrencyVO.getCurrencyId();
      this.minSalary = positionGradeCurrencyVO.getMinSalary();
      this.maxSalary = positionGradeCurrencyVO.getMaxSalary();
      super.setStatus( positionGradeCurrencyVO.getStatus() );
      super.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   /**
    * For Application
    */
   private List< MappingVO > currencyIds;

   public String getDecodeCurrencyId()
   {
      if ( this.currencyIds != null )
      {
         for ( MappingVO mappingVO : this.currencyIds )
         {
            if ( mappingVO.getMappingId().equals( currencyId ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public void update( final PositionGradeCurrencyVO positionGradeCurrencyVO )
   {
      this.currencyId = positionGradeCurrencyVO.getCurrencyId();
      this.maxSalary = positionGradeCurrencyVO.getMaxSalary();
      this.minSalary = positionGradeCurrencyVO.getMinSalary();
      super.setStatus( positionGradeCurrencyVO.getStatus() );
      super.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      this.currencyIds = KANUtil.getMappings( request.getLocale(), "positionGradeCurrency" );
      super.reset( mapping, request );
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public String getCurrencyId()
   {
      return currencyId;
   }

   public void setCurrencyId( String currencyId )
   {
      this.currencyId = currencyId;
   }

   public float getMinSalary()
   {
      return minSalary;
   }

   public void setMinSalary( float minSalary )
   {
      this.minSalary = minSalary;
   }

   public float getMaxSalary()
   {
      return maxSalary;
   }

   public void setMaxSalary( float maxSalary )
   {
      this.maxSalary = maxSalary;
   }

   public List< MappingVO > getCurrencyIds()
   {
      return currencyIds;
   }

   public void setCurrencyIds( List< MappingVO > currencyIds )
   {
      this.currencyIds = currencyIds;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   public String getEncodedPositionGradeId() throws KANException
   {
      if ( positionGradeId == null || positionGradeId.trim().equals( "" ) )
      {
         return "";
      }
      
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( positionGradeId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getencodedCurrencyId() throws KANException
   {
      if ( currencyId == null || currencyId.trim().equals( "" ) )
      {
         return "";
      }
      
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( currencyId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
