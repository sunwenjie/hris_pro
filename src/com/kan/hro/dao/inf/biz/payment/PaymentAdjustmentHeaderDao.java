package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;

public interface PaymentAdjustmentHeaderDao
{
   public abstract int countPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO, final RowBounds rowBounds )
         throws KANException;

   public abstract PaymentAdjustmentHeaderVO getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int insertPaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract int updatePaymentAdjustmentHeader( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract int deletePaymentAdjustmentHeader( final String adjustmentHeaderId ) throws KANException;

   public abstract List< Object > getGroupPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getEmployeeGroupPaymentAdjustmentHeaderVOsByCondition( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;

}
