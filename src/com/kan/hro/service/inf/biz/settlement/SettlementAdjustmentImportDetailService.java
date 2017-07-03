package com.kan.hro.service.inf.biz.settlement;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SettlementAdjustmentImportDetailService
{

   public PagedListHolder getSettlementAdjustmentImportDetailVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

}
