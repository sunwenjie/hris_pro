package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.TaxDao;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.TaxService;
import com.kan.base.util.KANException;

public class TaxServiceImpl extends ContextService implements TaxService
{

   @Override
   public PagedListHolder getTaxVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TaxDao taxDao = ( TaxDao ) getDao();
      pagedListHolder.setHolderSize( taxDao.countTaxVOsByCondition( ( TaxVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( taxDao.getTaxVOsByCondition( ( TaxVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( taxDao.getTaxVOsByCondition( ( TaxVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public TaxVO getTaxVOByTaxId( final String taxId ) throws KANException
   {
      return ( ( TaxDao ) getDao() ).getTaxVOByTaxId( taxId );
   }

   @Override
   public int insertTax( final TaxVO taxVO ) throws KANException
   {
      return ( ( TaxDao ) getDao() ).insertTax( taxVO );
   }

   @Override
   public int updateTax( final TaxVO taxVO ) throws KANException
   {
      return ( ( TaxDao ) getDao() ).updateTax( taxVO );
   }

   @Override
   public int deleteTax( final TaxVO taxVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final TaxVO modifyObject = ( ( TaxDao ) getDao() ).getTaxVOByTaxId( taxVO.getTaxId() );
      modifyObject.setDeleted( TaxVO.FALSE );
      return ( ( TaxDao ) getDao() ).updateTax( modifyObject );
   }

   @Override
   public List< Object > getTaxVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( TaxDao ) getDao() ).getTaxVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getTaxVOsByTaxVO( final TaxVO taxVO ) throws KANException
   {
      return ( ( TaxDao ) getDao() ).getTaxVOsByTaxVO( taxVO );
   }

}
