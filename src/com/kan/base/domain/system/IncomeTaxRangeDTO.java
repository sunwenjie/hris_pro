package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANUtil;

public class IncomeTaxRangeDTO implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -909272977533697890L;

   // IncomeTaxRangeHeaderVO
   private IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO;

   // IncomeTaxRangeDetailVOs
   private List< IncomeTaxRangeDetailVO > incomeTaxRangeDetailVOs = new ArrayList< IncomeTaxRangeDetailVO >();

   public final IncomeTaxRangeHeaderVO getIncomeTaxRangeHeaderVO()
   {
      return incomeTaxRangeHeaderVO;
   }

   public final void setIncomeTaxRangeHeaderVO( IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO )
   {
      this.incomeTaxRangeHeaderVO = incomeTaxRangeHeaderVO;
   }

   public final List< IncomeTaxRangeDetailVO > getIncomeTaxRangeDetailVOs()
   {
      return incomeTaxRangeDetailVOs;
   }

   public final void setIncomeTaxRangeDetailVOs( List< IncomeTaxRangeDetailVO > incomeTaxRangeDetailVOs )
   {
      this.incomeTaxRangeDetailVOs = incomeTaxRangeDetailVOs;
   }

   public IncomeTaxRangeDetailVO getIncomeTaxRangeDetailVOByBase( final String base )
   {
      if ( KANUtil.filterEmpty( base ) != null && incomeTaxRangeDetailVOs != null && incomeTaxRangeDetailVOs.size() > 0 )
      {
         for ( IncomeTaxRangeDetailVO incomeTaxRangeDetailVO : this.incomeTaxRangeDetailVOs )
         {
            double rangeFrom = 0;
            double rangeTo = 0;

            if ( KANUtil.filterEmpty( incomeTaxRangeDetailVO.getRangeFrom() ) != null )
            {
               rangeFrom = Double.valueOf( incomeTaxRangeDetailVO.getRangeFrom() );
            }

            if ( KANUtil.filterEmpty( incomeTaxRangeDetailVO.getRangeTo() ) != null )
            {
               rangeTo = Double.valueOf( incomeTaxRangeDetailVO.getRangeTo() );
            }

            if ( Double.valueOf( base ) > rangeFrom && ( Double.valueOf( base ) <= rangeTo || rangeTo == 0 ) )
            {
               return incomeTaxRangeDetailVO;
            }
         }
      }

      return null;
   }

   public double getPercentageByBase( final String base )
   {
      // 初始化IncomeTaxRangeDetailVO
      final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = getIncomeTaxRangeDetailVOByBase( base );

      if ( incomeTaxRangeDetailVO != null && KANUtil.filterEmpty( incomeTaxRangeDetailVO.getPercentage() ) != null )
      {
         return Double.valueOf( incomeTaxRangeDetailVO.getPercentage() );
      }
      else
      {
         return 0;
      }
   }

   public double getPercentageByBase( final double base )
   {
      return getPercentageByBase( String.valueOf( base ) );
   }

   public double getDeductByBase( final String base )
   {
      // 初始化IncomeTaxRangeDetailVO
      final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = getIncomeTaxRangeDetailVOByBase( base );

      if ( incomeTaxRangeDetailVO != null && KANUtil.filterEmpty( incomeTaxRangeDetailVO.getDeduct() ) != null )
      {
         return Double.valueOf( incomeTaxRangeDetailVO.getDeduct() );
      }
      else
      {
         return 0;
      }
   }

   public double getDeductByBase( final double base )
   {
      return getDeductByBase( String.valueOf( base ) );
   }
}
