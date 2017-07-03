package com.kan.hro.domain.biz.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaymentAdjustmentDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 6126177043048711905L;

   // Payment Adjustment Header
   private PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO;

   // Payment Adjustment Detail List
   private List< PaymentAdjustmentDetailVO > paymentAdjustmentDetailVOs = new ArrayList< PaymentAdjustmentDetailVO >();

   public final PaymentAdjustmentHeaderVO getPaymentAdjustmentHeaderVO()
   {
      return paymentAdjustmentHeaderVO;
   }

   public final void setPaymentAdjustmentHeaderVO( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO )
   {
      this.paymentAdjustmentHeaderVO = paymentAdjustmentHeaderVO;
   }

   public final List< PaymentAdjustmentDetailVO > getPaymentAdjustmentDetailVOs()
   {
      return paymentAdjustmentDetailVOs;
   }

   public final void setPaymentAdjustmentDetailVOs( List< PaymentAdjustmentDetailVO > paymentAdjustmentDetailVOs )
   {
      this.paymentAdjustmentDetailVOs = paymentAdjustmentDetailVOs;
   }

}
