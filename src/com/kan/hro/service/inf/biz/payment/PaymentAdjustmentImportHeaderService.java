package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;

public interface PaymentAdjustmentImportHeaderService
{
   public abstract PagedListHolder getPaymentAdjustmentImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;
   
   public abstract PaymentAdjustmentImportHeaderVO getPaymentAdjustmentImportHeaderVOsById( final String headerId,final String accountId )throws KANException;
}
