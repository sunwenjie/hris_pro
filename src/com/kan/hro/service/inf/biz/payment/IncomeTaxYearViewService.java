package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;

public interface IncomeTaxYearViewService
{
   public abstract PagedListHolder getIncomeTaxYearViewsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract IncomeTaxYearView getIncomeTaxYearViewByCondition( final IncomeTaxYearView incomeTaxYearView ) throws KANException;
}
