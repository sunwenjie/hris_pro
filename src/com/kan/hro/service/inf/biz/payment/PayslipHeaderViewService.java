package com.kan.hro.service.inf.biz.payment;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PayslipHeaderViewService
{
   //���������Ŀ������
   public  final String INDEPENDENT_CALCULATE_TIEMGROUP="2";
   public abstract PagedListHolder getPayslipTaxDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
}
