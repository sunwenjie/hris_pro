package com.kan.hro.dao.inf.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;

public interface BudgetSettingDetailDao
{
   public abstract int countBudgetSettingDetailVOsByCondition( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract List< Object > getBudgetSettingDetailVOsByCondition( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract List< Object > getBudgetSettingDetailVOsByCondition( final BudgetSettingDetailVO budgetSettingDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract BudgetSettingDetailVO getBudgetSettingDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract int updateBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract int deleteBudgetSettingDetail( final BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException;

   public abstract List< Object > getBudgetSettingDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract BudgetSettingDetailVO matchBudgetSettingDetailVOByMapParameter( final Map< String, Object > mapParameter ) throws KANException;

}
