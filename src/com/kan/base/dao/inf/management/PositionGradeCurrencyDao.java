package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.util.KANException;

public interface PositionGradeCurrencyDao
{
   public abstract int countPositionGradeCurrencyVOByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByCurrencyId( final String currencyId ) throws KANException;

   public abstract int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void deletePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void updatePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

}
