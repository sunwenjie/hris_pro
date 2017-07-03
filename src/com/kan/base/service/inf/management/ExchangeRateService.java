package com.kan.base.service.inf.management;

import java.util.List;
import java.util.Map;

import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ExchangeRateService
{
   public abstract PagedListHolder getExchangeRateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ExchangeRateVO getExchangeRateVOByExchangeRateId( final String exchangeRateId ) throws KANException;

   public abstract int insertExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract int updateExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract int deleteExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException;

   public abstract List< Object > getExchangeRateVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getExchangeRateVOsByMapParameter( final Map< String, Object > mapParameter );
}
