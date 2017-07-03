package com.kan.base.service.inf.management;

import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionGradeCurrencyService
{
   public abstract PagedListHolder getPositionGradeCurrencyVOsByCondition( final PagedListHolder pagedListGradeCurrencyHolder, final boolean isPaged ) throws KANException;

   public abstract int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void deletePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void updatePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByCurrencyId( final String currencyId ) throws KANException;

}
