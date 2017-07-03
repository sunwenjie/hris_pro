package com.kan.hro.service.inf.biz.performance;

import java.util.List;
import java.util.Map;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;

public interface BudgetSettingHeaderService
{
   public abstract PagedListHolder getBudgetSettingHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BudgetSettingHeaderVO getBudgetSettingHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract int updateBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract int deleteBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract List< Object > getBudgetSettingHeaderVOsByMapParameter( final Map< String, Object > mapParameter ) throws KANException;

}
