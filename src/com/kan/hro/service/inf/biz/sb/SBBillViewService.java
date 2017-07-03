package com.kan.hro.service.inf.biz.sb;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SBBillViewService
{
   public abstract PagedListHolder getSBBillDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
}
