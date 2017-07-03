package com.kan.hro.service.impl.biz.performance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingDetailDao;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;
import com.kan.hro.service.inf.biz.performance.BudgetSettingDetailService;

public class BudgetSettingDetailServiceImpl extends ContextService implements BudgetSettingDetailService
{

   @Override
   public PagedListHolder getBudgetSettingDetailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final BudgetSettingDetailDao dao = ( BudgetSettingDetailDao ) getDao();
      final BudgetSettingDetailVO object = ( BudgetSettingDetailVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( dao.countBudgetSettingDetailVOsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getBudgetSettingDetailVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getBudgetSettingDetailVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public BudgetSettingDetailVO getBudgetSettingDetailVOByDetailId( String headerId ) throws KANException
   {
      return ( ( BudgetSettingDetailDao ) getDao() ).getBudgetSettingDetailVOByDetailId( headerId );
   }

   @Override
   public int insertBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return ( ( BudgetSettingDetailDao ) getDao() ).insertBudgetSettingDetail( budgetSettingDetailVO );
   }

   @Override
   public int updateBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      return ( ( BudgetSettingDetailDao ) getDao() ).updateBudgetSettingDetail( budgetSettingDetailVO );
   }

   @Override
   public int deleteBudgetSettingDetail( BudgetSettingDetailVO budgetSettingDetailVO ) throws KANException
   {
      budgetSettingDetailVO.setDeleted( "2" );
      return ( ( BudgetSettingDetailDao ) getDao() ).updateBudgetSettingDetail( budgetSettingDetailVO );
   }

   @Override
   public List< Object > getBudgetSettingDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return ( ( BudgetSettingDetailDao ) getDao() ).getBudgetSettingDetailVOsByHeaderId( headerId );
   }

}
