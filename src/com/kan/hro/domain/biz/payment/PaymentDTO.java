package com.kan.hro.domain.biz.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.sb.SpecialDTO;

/**  
 * 项目名称：HRO_V1  
 * 类名称：PaymentDTO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-11-23  
 */
public class PaymentDTO implements Serializable, SpecialDTO< Object, List< ? >>
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1030287169254622050L;

   // PaymentHeaderVO
   private PaymentHeaderVO paymentHeaderVO;

   // PaymentDetailVO List
   private List< PaymentDetailVO > paymentDetailVOs = new ArrayList< PaymentDetailVO >();

   public PaymentHeaderVO getPaymentHeaderVO()
   {
      return paymentHeaderVO;
   }

   public void setPaymentHeaderVO( PaymentHeaderVO paymentHeaderVO )
   {
      this.paymentHeaderVO = paymentHeaderVO;
   }

   public List< PaymentDetailVO > getPaymentDetailVOs()
   {
      return paymentDetailVOs;
   }

   public void setPaymentDetailVOs( List< PaymentDetailVO > paymentDetailVOs )
   {
      this.paymentDetailVOs = paymentDetailVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return paymentHeaderVO;
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return paymentDetailVOs;
   }

   // 装载DTO时 处理相同科目
   //* Add by siuxia at 2014-03-17 *//
   public void dealPaymentDetailVO( final PaymentDetailVO paymentDetailVO )
   {
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 && KANUtil.filterEmpty( paymentDetailVO.getItemId() ) != null )
      {
         for ( PaymentDetailVO tempPaymentDetailVO : paymentDetailVOs )
         {
            if ( KANUtil.filterEmpty( tempPaymentDetailVO.getItemId() ) != null && tempPaymentDetailVO.getItemId().equals( paymentDetailVO.getItemId() ) )
            {
               tempPaymentDetailVO.setBillAmountCompany( String.valueOf( Float.valueOf( tempPaymentDetailVO.getBillAmountCompany() )
                     + Float.valueOf( paymentDetailVO.getBillAmountCompany() ) ) );
               tempPaymentDetailVO.setBillAmountPersonal( String.valueOf( Float.valueOf( tempPaymentDetailVO.getBillAmountPersonal() )
                     + Float.valueOf( paymentDetailVO.getBillAmountPersonal() ) ) );
               tempPaymentDetailVO.setCostAmountCompany( String.valueOf( Float.valueOf( tempPaymentDetailVO.getCostAmountCompany() )
                     + Float.valueOf( paymentDetailVO.getCostAmountCompany() ) ) );
               tempPaymentDetailVO.setCostAmountPersonal( String.valueOf( Float.valueOf( tempPaymentDetailVO.getCostAmountPersonal() )
                     + Float.valueOf( paymentDetailVO.getCostAmountPersonal() ) ) );

               break;
            }
         }
      }

   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

   public String getAmountCompany( final String itemId )
   {
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( PaymentDetailVO paymentDetailVO : paymentDetailVOs )
         {
            if ( KANUtil.filterEmpty( paymentDetailVO.getItemId() ) != null && KANUtil.filterEmpty( paymentDetailVO.getItemId() ).equals( itemId ) )
            {
               return paymentDetailVO.formatNumber( paymentDetailVO.getAmountCompany() );
            }
         }
      }

      return paymentHeaderVO.formatNumber( "0" );
   }

   public String getAmountPersonal( final String itemId )
   {
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( PaymentDetailVO paymentDetailVO : paymentDetailVOs )
         {
            if ( KANUtil.filterEmpty( paymentDetailVO.getItemId() ) != null && KANUtil.filterEmpty( paymentDetailVO.getItemId() ).equals( itemId ) )
            {
               return paymentDetailVO.formatNumber( paymentDetailVO.getAmountPersonal() );
            }
         }
      }

      return paymentHeaderVO.formatNumber( "0" );
   }
}
