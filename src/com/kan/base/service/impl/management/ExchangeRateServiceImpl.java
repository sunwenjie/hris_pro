package com.kan.base.service.impl.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ExchangeRateDao;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ExchangeRateService;
import com.kan.base.util.KANException;

public class ExchangeRateServiceImpl extends ContextService implements ExchangeRateService
{

   @Override
   public PagedListHolder getExchangeRateVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ExchangeRateDao exchangeRateDao = ( ExchangeRateDao ) getDao();
      pagedListHolder.setHolderSize( exchangeRateDao.countExchangeRateVOsByCondition( ( ExchangeRateVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( exchangeRateDao.getExchangeRateVOsByCondition( ( ExchangeRateVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( exchangeRateDao.getExchangeRateVOsByCondition( ( ExchangeRateVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ExchangeRateVO getExchangeRateVOByExchangeRateId( final String exchangeRateId ) throws KANException
   {
      return ( ( ExchangeRateDao ) getDao() ).getExchangeRateVOByExchangeRateId( exchangeRateId );
   }

   @Override
   public int insertExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return ( ( ExchangeRateDao ) getDao() ).insertExchangeRate( exchangeRateVO );
   }

   @Override
   public int updateExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      return ( ( ExchangeRateDao ) getDao() ).updateExchangeRate( exchangeRateVO );
   }

   @Override
   public int deleteExchangeRate( final ExchangeRateVO exchangeRateVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final ExchangeRateVO modifyObject = ( ( ExchangeRateDao ) getDao() ).getExchangeRateVOByExchangeRateId( exchangeRateVO.getExchangeRateId() );
      modifyObject.setDeleted( ExchangeRateVO.FALSE );
      return ( ( ExchangeRateDao ) getDao() ).updateExchangeRate( modifyObject );
   }

   @Override
   public List< Object > getExchangeRateVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ExchangeRateDao ) getDao() ).getExchangeRateVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getExchangeRateVOsByMapParameter( Map< String, Object > mapParameter )
   {
      return ( ( ExchangeRateDao ) getDao() ).getExchangeRateVOsByMapParameter( mapParameter );
   }

}
