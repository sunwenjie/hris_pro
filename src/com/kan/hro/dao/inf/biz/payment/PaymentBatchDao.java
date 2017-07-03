package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;

public interface PaymentBatchDao
{
   public abstract int countPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract PaymentBatchVO getPaymentBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertPaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract int updatePaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract int deletePaymentBatch( final String paymentBatchId ) throws KANException;
   
}
