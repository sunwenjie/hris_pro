package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PaymentReportService
{
   public abstract PagedListHolder getAVGPaymentReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
}
