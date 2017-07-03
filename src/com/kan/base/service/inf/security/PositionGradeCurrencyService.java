package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionGradeCurrencyService
{
   public abstract PagedListHolder getPositionGradeCurrencyVOByCondition( final PagedListHolder pagedListGradeCurrencyHolder, final boolean isPaged ) throws KANException;

   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void deletePositionGradeCurrencyByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void updatePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;
  
   public abstract List< Object > getAllPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByCondition( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;
}
