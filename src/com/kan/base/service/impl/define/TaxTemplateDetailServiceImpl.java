package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.TaxTemplateDetailDao;
import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateDetailService;
import com.kan.base.util.KANException;

public class TaxTemplateDetailServiceImpl extends ContextService implements TaxTemplateDetailService
{

   @Override
   public PagedListHolder getTaxTemplateDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TaxTemplateDetailDao taxTemplateDetailDao = ( TaxTemplateDetailDao ) getDao();
      pagedListHolder.setHolderSize( taxTemplateDetailDao.countTaxTemplateDetailVOsByCondition( ( TaxTemplateDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( taxTemplateDetailDao.getTaxTemplateDetailVOsByCondition( ( TaxTemplateDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( taxTemplateDetailDao.getTaxTemplateDetailVOsByCondition( ( TaxTemplateDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public TaxTemplateDetailVO getTaxTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException
   {
      return ( ( TaxTemplateDetailDao ) getDao() ).getTaxTemplateDetailVOByTemplateDetailId( templateDetailId );
   }

   @Override
   public int insertTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return ( ( TaxTemplateDetailDao ) getDao() ).insertTaxTemplateDetail( taxTemplateDetailVO );
   }

   @Override
   public int updateTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      return ( ( TaxTemplateDetailDao ) getDao() ).updateTaxTemplateDetail( taxTemplateDetailVO );
   }

   @Override
   public int deleteTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException
   {
      taxTemplateDetailVO.setDeleted( TaxTemplateDetailVO.FALSE );
      return ( ( TaxTemplateDetailDao ) getDao() ).updateTaxTemplateDetail( taxTemplateDetailVO );
   }

   @Override
   public List< Object > getTaxTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( ( TaxTemplateDetailDao ) getDao() ).getTaxTemplateDetailVOsByTemplateHeaderId( templateHeaderId );
   }

}
