package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.util.KANException;

public interface PositionGradeCurrencyDao
{
   public abstract int countPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;
   
   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract PositionGradeCurrencyVO getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   public abstract void deletePositionGradeCurrencyByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;
   
   public abstract void updatePositionGradeCurrencyByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;

   List< Object > getAllPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException;
   
}
