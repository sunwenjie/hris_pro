package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PaymentAdjustmentImportDetailService
{

   public PagedListHolder getPaymentAdjustmentImportDetailVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
}
