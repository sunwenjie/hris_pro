package com.kan.hro.service.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.service.inf.biz.payment.SalaryDetailService;

public class SalaryDetailServiceImpl extends ContextService implements SalaryDetailService
{
   @Override
   public PagedListHolder getSalaryDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SalaryDetailDao salaryDetailDao = ( SalaryDetailDao ) getDao();
      pagedListHolder.setHolderSize( salaryDetailDao.countSalaryDetailVOsByCondition( ( SalaryDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( salaryDetailDao.getSalaryDetailVOsByCondition( ( SalaryDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( salaryDetailDao.getSalaryDetailVOsByCondition( ( SalaryDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SalaryDetailVO getSalaryDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SalaryDetailDao ) getDao() ).getSalaryDetailVOByDetailId( detailId );
   }

   @Override
   public int updateSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      return ( ( SalaryDetailDao ) getDao() ).updateSalaryDetail( salaryDetailVO );
   }

   @Override
   public int insertSalaryDetail( final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      return ( ( SalaryDetailDao ) getDao() ).insertSalaryDetail( salaryDetailVO );
   }

   @Override
   public int deleteSalaryDetail( final String salaryDetailId ) throws KANException
   {
      return ( ( SalaryDetailDao ) getDao() ).deleteSalaryDetail( salaryDetailId );
   }

   @Override
   public List< Object > getSalaryDetailVOsByCondition( final SalaryDetailVO salaryDetailVO ) throws KANException
   {
      return ( ( SalaryDetailDao ) getDao() ).getSalaryDetailVOsByCondition( salaryDetailVO );
   }

}
