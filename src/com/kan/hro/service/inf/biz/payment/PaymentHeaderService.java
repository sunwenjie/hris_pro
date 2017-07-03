package com.kan.hro.service.inf.biz.payment;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public interface PaymentHeaderService
{
   public abstract PagedListHolder getPaymentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getPaymentHeaderDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PaymentHeaderVO getPaymentHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updatePaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract int insertPaymentHeader( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract int deletePaymentHeader( final String paymentHeaderId ) throws KANException;

   public abstract List< Object > getPaymentHeaderVOsByCondition( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract List< Object > getMonthliesByPaymentHeaderVO( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

}
