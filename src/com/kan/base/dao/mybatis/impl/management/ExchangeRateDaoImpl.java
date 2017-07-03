package com.kan.base.dao.mybatis.impl.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ExchangeRateDao;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.util.KANException;

public class ExchangeRateDaoImpl extends Context implements ExchangeRateDao
{

   @Override
   public int countExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return ( Integer ) select( "countExchangeRateVOsByCondition", exchangeRateVO );
   }

   @Override
   public List< Object > getExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return selectList( "getExchangeRateVOsByCondition", exchangeRateVO );
   }

   @Override
   public List< Object > getExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getExchangeRateVOsByCondition", exchangeRateVO, rowBounds );
   }

   @Override
   public ExchangeRateVO getExchangeRateVOByExchangeRateId( final String exchangeRateId ) throws KANException
   {
      return ( ExchangeRateVO ) select( "getExchangeRateVOByExchangeRateId", exchangeRateId );
   }

   @Override
   public int insertExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return insert( "insertExchangeRate", exchangeRateVO );
   }

   @Override
   public int updateExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return update( "updateExchangeRate", exchangeRateVO );
   }

   @Override
   public int deleteExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return delete( "deleteExchangeRate", exchangeRateVO );
   }

   @Override
   public List< Object > getExchangeRateVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getExchangeRateVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getExchangeRateVOsByMapParameter( Map< String, Object > mapParameter )
   {
      return selectList( "getExchangeRateVOsByMapParameter", mapParameter );
   }

}
