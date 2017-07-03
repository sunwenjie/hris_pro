package com.kan.hro.service.impl.biz.payment;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.IncomeTaxYearViewDao;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;
import com.kan.hro.service.inf.biz.payment.IncomeTaxYearViewService;

public class IncomeTaxYearViewServiceImpl extends ContextService implements IncomeTaxYearViewService
{

   @Override
   public PagedListHolder getIncomeTaxYearViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final IncomeTaxYearViewDao incomeTaxYearViewDao = ( IncomeTaxYearViewDao ) getDao();
      pagedListHolder.setHolderSize( incomeTaxYearViewDao.getIncomeTaxYearViewsByCondition( ( IncomeTaxYearView ) pagedListHolder.getObject() ).size() );

      if ( isPaged )
      {
         pagedListHolder.setSource( incomeTaxYearViewDao.getIncomeTaxYearViewsByCondition( ( IncomeTaxYearView ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( incomeTaxYearViewDao.getIncomeTaxYearViewsByCondition( ( IncomeTaxYearView ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public IncomeTaxYearView getIncomeTaxYearViewByCondition( IncomeTaxYearView incomeTaxYearView ) throws KANException
   {
      return ( ( IncomeTaxYearViewDao ) getDao() ).getIncomeTaxYearViewByCondition( incomeTaxYearView );
   }

}
