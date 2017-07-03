package com.kan.hro.service.inf.biz.settlement;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface OrderBillHeaderViewService
{
   public abstract PagedListHolder getSumOrderBillHeaderViewsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract PagedListHolder getOrderBillHeaderViewsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getOrderBillDetailViewsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getOrderBillDetailViewsByConditionForExport( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

}
