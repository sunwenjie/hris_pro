package com.kan.base.service.impl.system;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.IncomeTaxRangeDetailDao;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeDetailService;
import com.kan.base.util.KANException;

public class IncomeTaxRangeDetailServiceImpl extends ContextService implements IncomeTaxRangeDetailService
{

   @Override
   public PagedListHolder getIncomeTaxRangeDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final IncomeTaxRangeDetailDao socialBenefitDetailDao = ( IncomeTaxRangeDetailDao ) getDao();
      pagedListHolder.setHolderSize( socialBenefitDetailDao.countIncomeTaxRangeDetailVOsByCondition( ( IncomeTaxRangeDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getIncomeTaxRangeDetailVOsByCondition( ( IncomeTaxRangeDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( socialBenefitDetailDao.getIncomeTaxRangeDetailVOsByCondition( ( IncomeTaxRangeDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public IncomeTaxRangeDetailVO getIncomeTaxRangeDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( IncomeTaxRangeDetailDao ) getDao() ).getIncomeTaxRangeDetailVOByDetailId( detailId );
   }

   @Override
   public int updateIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return ( ( IncomeTaxRangeDetailDao ) getDao() ).updateIncomeTaxRangeDetail( incomeTaxRangeDetailVO );
   }

   @Override
   public int insertIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      return ( ( IncomeTaxRangeDetailDao ) getDao() ).insertIncomeTaxRangeDetail( incomeTaxRangeDetailVO );
   }

   @Override
   public void deleteIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException
   {
      incomeTaxRangeDetailVO.setDeleted( IncomeTaxRangeDetailVO.FALSE );
      updateIncomeTaxRangeDetail( incomeTaxRangeDetailVO );
   }

}
