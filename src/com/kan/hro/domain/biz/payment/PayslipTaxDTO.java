package com.kan.hro.domain.biz.payment;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.BaseDTO;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.sb.SpecialDTO;

public class PayslipTaxDTO extends BaseDTO implements Serializable, SpecialDTO< Object, List< ? > >, Comparator< Object >
{

   // Serial Version UID
   private static final long serialVersionUID = 7767108279279098227L;

   private PayslipTaxHeaderView payslipTaxHeaderView;

   public PayslipTaxHeaderView getPayslipTaxHeaderView()
   {
      return payslipTaxHeaderView;
   }

   public void setPayslipTaxHeaderView( PayslipTaxHeaderView payslipTaxHeaderView )
   {
      this.payslipTaxHeaderView = payslipTaxHeaderView;
   }

   @Override
   public Object getHeaderVO()
   {
      return getPayslipTaxHeaderView();
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return null;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

   @Override
   public int compare( final Object o1, final Object o2 )
   {
      try
      {
         final PayslipTaxDTO dto1 = ( PayslipTaxDTO ) o1;

         final PayslipTaxDTO dto2 = ( PayslipTaxDTO ) o2;

         final String field1 = ( ( String ) KANUtil.getValue( dto1.getPayslipTaxHeaderView(), sortColumn ) );

         final String field2 = ( ( String ) KANUtil.getValue( dto2.getPayslipTaxHeaderView(), sortColumn ) );

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

}
