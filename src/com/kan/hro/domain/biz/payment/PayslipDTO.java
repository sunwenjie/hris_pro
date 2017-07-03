package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kan.base.domain.BaseDTO;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.sb.SpecialDTO;

public class PayslipDTO extends BaseDTO implements SpecialDTO< Object, List< ? > >, Comparator< Object >
{
   // serialVersionUID
   private static final long serialVersionUID = 7196406254222941115L;

   private PayslipHeaderView payslipHeaderView;

   // 包含的PaymentHeaderVO的ID集合
   private Set< String > paymentHeaderIds = new HashSet< String >();

   private List< PayslipDetailView > payslipDetailViews = new ArrayList< PayslipDetailView >();

   public PayslipHeaderView getPayslipHeaderView()
   {
      return payslipHeaderView;
   }

   public Set< String > getPaymentHeaderIds()
   {
      return paymentHeaderIds;
   }

   public void setPaymentHeaderIds( Set< String > paymentHeaderIds )
   {
      this.paymentHeaderIds = paymentHeaderIds;
   }

   public void setPayslipHeaderView( PayslipHeaderView payslipHeaderView )
   {
      this.payslipHeaderView = payslipHeaderView;
   }

   public List< PayslipDetailView > getPayslipDetailViews()
   {

      // PayslipDetailView集合排序
      if ( payslipDetailViews != null && payslipDetailViews.size() > 1 )
      {
         Collections.sort( payslipDetailViews, new Comparator< PayslipDetailView >()
         {

            @Override
            public int compare( PayslipDetailView o1, PayslipDetailView o2 )
            {
               if ( o1 != null && o2 != null && KANUtil.filterEmpty( o1.getItemType() ) != null && KANUtil.filterEmpty( o2.getItemType() ) != null )
               {
                  if ( !o1.getItemType().equals( o2.getItemType() ) )
                  {
                     return o1.getItemType().compareTo( o2.getItemType() );
                  }
                  else
                  {
                     return o1.getItemId().compareTo( o2.getItemId() );
                  }
               }

               return 0;
            }

         } );
      }

      return payslipDetailViews;
   }

   public void setPayslipDetailViews( List< PayslipDetailView > payslipDetailViews )
   {
      this.payslipDetailViews = payslipDetailViews;
   }

   @Override
   public Object getHeaderVO()
   {
      return getPayslipHeaderView();
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return getPayslipDetailViews();
   }

   @Override
   public int compare( final Object o1, final Object o2 )
   {
      try
      {
         final PayslipDTO dto1 = ( PayslipDTO ) o1;

         final PayslipDTO dto2 = ( PayslipDTO ) o2;

         final String field1 = ( ( String ) KANUtil.getValue( dto1.getPayslipHeaderView(), sortColumn ) );

         final String field2 = ( ( String ) KANUtil.getValue( dto2.getPayslipHeaderView(), sortColumn ) );

         if ( KANUtil.filterEmpty( field1 ) != null && KANUtil.filterEmpty( field2 ) != null )
         {
            if ( sortOrder.equals( "asc" ) )
            {
               return -field1.compareTo( field2 );
            }
            else
            {
               return field1.compareTo( field2 );
            }
         }

         return 0;
      }
      catch ( final Exception e )
      {
      }

      return 0;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

   public String getAmountCompany( final String itemId )
   {
      if ( payslipDetailViews != null && payslipDetailViews.size() > 0 )
      {
         for ( PayslipDetailView payslipDetailView : payslipDetailViews )
         {
            if ( KANUtil.filterEmpty( payslipDetailView.getItemId() ) != null && KANUtil.filterEmpty( payslipDetailView.getItemId() ).equals( itemId ) )
            {
               return payslipDetailView.formatNumber( payslipDetailView.getAmountCompany() );
            }
         }
      }

      return payslipHeaderView.formatNumber( "0" );
   }

   public String getAmountPersonal( final String itemId )
   {
      if ( payslipDetailViews != null && payslipDetailViews.size() > 0 )
      {
         for ( PayslipDetailView paymentDetailVO : payslipDetailViews )
         {
            if ( KANUtil.filterEmpty( paymentDetailVO.getItemId() ) != null && KANUtil.filterEmpty( paymentDetailVO.getItemId() ).equals( itemId ) )
            {
               return paymentDetailVO.formatNumber( paymentDetailVO.getAmountPersonal() );
            }
         }
      }

      return payslipHeaderView.formatNumber( "0" );
   }
}
