package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;

public interface PaymentAdjustmentImportBatchService
{
   public PagedListHolder getPaymentAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public PaymentAdjustmentImportBatchVO getPaymentAdjustmentImportBatchById( final String batchId ) throws KANException;

   public int updatePaymentAdjustmentImportBatch( final PaymentAdjustmentImportBatchVO submitObject ) throws KANException;

   public int backBatch( final PaymentAdjustmentImportBatchVO PaymentAdjustmentImportBatchVO ) throws KANException;
}
