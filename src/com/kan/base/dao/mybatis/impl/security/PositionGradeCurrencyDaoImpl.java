package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionGradeCurrencyDao;
import com.kan.base.domain.security.PositionGradeCurrencyVO;
import com.kan.base.util.KANException;

public class PositionGradeCurrencyDaoImpl extends Context implements PositionGradeCurrencyDao
{

   @Override
   public int countPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return ( Integer ) select( "countPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO );
   }

   @Override
   public List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO, rowBounds );
   }

   @Override
   public List< Object > getPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return selectList( "getPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO );
   }
   
   @Override
   public List< Object > getAllPositionGradeCurrencyVOsByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      
      return selectList( "getAllPositionGradeCurrencyVOsByCondition", positionGradeCurrencyVO );
   }

   @Override
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return ( PositionGradeCurrencyVO ) select( "getPositionGradeCurrencyVOByPositionGradeIdAndCurrencyId", positionGradeCurrencyVO );
   }

   @Override
   public int insertPositionGradeCurrency( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {

      return insert( "insertPositionGradeCurrency", positionGradeCurrencyVO );
   }

   @Override
   public void deletePositionGradeCurrencyByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      delete( "deletePositionGradeCurrencyByCondition", positionGradeCurrencyVO );
   }

   @Override
   public void updatePositionGradeCurrencyByCondition( PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      update( "updatePositionGradeCurrencyByCondition", positionGradeCurrencyVO );

   }

   @Override
   public PositionGradeCurrencyVO getPositionGradeCurrencyVOByCondition( final PositionGradeCurrencyVO positionGradeCurrencyVO ) throws KANException
   {
      return( PositionGradeCurrencyVO ) select( "getPositionGradeCurrencyVOByCondition", positionGradeCurrencyVO ); 
   }

}
