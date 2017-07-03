package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public interface PaymentDetailDao
{
   public abstract int countPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract PaymentDetailVO getPaymentDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertPaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract int updatePaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract int deletePaymentDetail( final String paymentDetailId ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByContractId( final String contractId ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByBatchVO( final PaymentBatchVO paymentBatchVO ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByPaymentHeaderCond( final PaymentHeaderVO paymentHeaderVO ) throws KANException;
}
