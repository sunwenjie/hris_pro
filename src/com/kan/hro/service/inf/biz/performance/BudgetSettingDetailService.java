package com.kan.hro.service.inf.biz.performance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;

public interface BudgetSettingDetailService
{
   public abstract PagedListHolder getBudgetSettingDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BudgetSettingDetailVO getBudgetSettingDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract int updateBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract int deleteBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract List< Object > getBudgetSettingDetailVOsByHeaderId( final String headerId ) throws KANException;

}
