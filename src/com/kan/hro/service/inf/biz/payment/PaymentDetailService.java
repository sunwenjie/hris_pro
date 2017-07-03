package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public interface PaymentDetailService
{
   public abstract PagedListHolder getPaymentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PaymentDetailVO getPaymentDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updatePaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract int insertPaymentDetail( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract int deletePaymentDetail( final String paymentDetailId ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByCondition( final PaymentDetailVO paymentDetailVO ) throws KANException;

   public abstract List< Object > getPaymentDetailVOsByPaymentHeaderCond( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

}
