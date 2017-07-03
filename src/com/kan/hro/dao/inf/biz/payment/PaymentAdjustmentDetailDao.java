package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;

public interface PaymentAdjustmentDetailDao
{
   public abstract int countPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentDetailVOsByCondition( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO, final RowBounds rowBounds )
         throws KANException;

   public abstract PaymentAdjustmentDetailVO getPaymentAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int insertPaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract int updatePaymentAdjustmentDetail( final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO ) throws KANException;

   public abstract int deletePaymentAdjustmentDetail( final String adjustmentDetailId ) throws KANException;

}
