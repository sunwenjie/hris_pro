package com.kan.base.dao.inf.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.util.KANException;

public interface ExchangeRateDao
{
   public abstract int countExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract List< Object > getExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract List< Object > getExchangeRateVOsByCondition( final ExchangeRateVO exchangeRateVO, RowBounds rowBounds ) throws KANException;

   public abstract ExchangeRateVO getExchangeRateVOByExchangeRateId( final String id ) throws KANException;

   public abstract int insertExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract int updateExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract int deleteExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract List< Object > getExchangeRateVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getExchangeRateVOsByMapParameter( final Map< String, Object > mapParameter );
}
