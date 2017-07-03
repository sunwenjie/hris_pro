package com.kan.hro.dao.inf.biz.performance;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;

public interface BudgetSettingHeaderDao
{
   public abstract int countBudgetSettingHeaderVOsByCondition( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract List< Object > getBudgetSettingHeaderVOsByCondition( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract List< Object > getBudgetSettingHeaderVOsByCondition( final BudgetSettingHeaderVO budgetSettingHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract BudgetSettingHeaderVO getBudgetSettingHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract int updateBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;

   public abstract int deleteBudgetSettingHeader( final BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException;
   
   public abstract List< Object > getBudgetSettingHeaderVOsByMapParameter( final Map< String, Object > mapParameter ) throws KANException;

}
