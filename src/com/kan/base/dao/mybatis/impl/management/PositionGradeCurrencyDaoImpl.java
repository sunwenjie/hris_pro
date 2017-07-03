package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.PositionGradeCurrencyDao;
import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.util.KANException;

public class PositionGradeCurrencyDaoImpl extends Context implements PositionGradeCurrencyDao
{

   @Override
   public int countPositionGradeCurrencyVOByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return ( Integer ) select( "countMgtPositionGradeCurrencyVOByCondition", positionGradeCurrencyVO );
   }

   @Override
   public List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getMgtPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO, rowBounds );
   }

   @Override
   public List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return selectList( "getMgtPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO );
   }

   @Override
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByCurrencyId( final String currencyId ) throws KANException
   {
      
      return ( PositionGradeCurrencyVO ) select( "getMgtPositionGradeCurrencyVOByCurrencyId", currencyId );
   }

   @Override
   public int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return insert( "insertMgtPositionGradeCurrency", positionGradeCurrencyVO );
   }

   @Override
   public void deletePositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      delete( "deleteMgtPositionGradeCurrency", positionGradeCurrencyVO );
   }

   @Override
   public void updatePositionGradeCurrency(final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      update( "updateMgtPositionGradeCurrency", positionGradeCurrencyVO );

   }

}
