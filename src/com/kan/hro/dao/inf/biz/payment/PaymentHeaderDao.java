package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public interface PaymentHeaderDao
{
   public abstract int countPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract PaymentHeaderVO summaryPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract PaymentHeaderVO getPaymentHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertPaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract int updatePaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract int deletePaymentHeader( final String paymentHeaderId ) throws KANException;

   public abstract List< Object > getMonthliesByPaymentHeaderVO( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract List< Object > getPaymentHeaderVOsByBatchId( final String batchId ) throws KANException;
}
