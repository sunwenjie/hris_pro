package com.kan.hro.service.impl.biz.performance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingDetailDao;
import com.kan.hro.dao.inf.biz.performance.BudgetSettingHeaderDao;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;

public class BudgetSettingHeaderServiceImpl extends ContextService implements BudgetSettingHeaderService
{

   private BudgetSettingDetailDao budgetSettingDetailDao;

   public BudgetSettingDetailDao getBudgetSettingDetailDao()
   {
      return budgetSettingDetailDao;
   }

   public void setBudgetSettingDetailDao( BudgetSettingDetailDao budgetSettingDetailDao )
   {
      this.budgetSettingDetailDao = budgetSettingDetailDao;
   }

   @Override
   public PagedListHolder getBudgetSettingHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final BudgetSettingHeaderDao dao = ( BudgetSettingHeaderDao ) getDao();
      final BudgetSettingHeaderVO object = ( BudgetSettingHeaderVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( dao.countBudgetSettingHeaderVOsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getBudgetSettingHeaderVOsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getBudgetSettingHeaderVOsByCondition( object ) );
      }

      return pagedListHolder;
   }

   @Override
   public BudgetSettingHeaderVO getBudgetSettingHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( ( BudgetSettingHeaderDao ) getDao() ).getBudgetSettingHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return ( ( BudgetSettingHeaderDao ) getDao() ).insertBudgetSettingHeader( budgetSettingHeaderVO );
   }

   @Override
   public int updateBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      return ( ( BudgetSettingHeaderDao ) getDao() ).updateBudgetSettingHeader( budgetSettingHeaderVO );
   }

   @Override
   public int deleteBudgetSettingHeader( BudgetSettingHeaderVO budgetSettingHeaderVO ) throws KANException
   {
      try
      {
         startTransaction();

         List< Object > list = budgetSettingDetailDao.getBudgetSettingDetailVOsByHeaderId( budgetSettingHeaderVO.getHeaderId() );

         if ( list != null && list.size() > 0 )
         {
            for ( Object o : list )
            {
               BudgetSettingDetailVO vo = ( BudgetSettingDetailVO ) o;
               vo.setDeleted( "2" );
               vo.setModifyBy( budgetSettingHeaderVO.getModifyBy() );
               vo.setModifyDate( new Date() );
               budgetSettingDetailDao.updateBudgetSettingDetail( vo );
            }
         }

         budgetSettingHeaderVO.setDeleted( "2" );
         ( ( BudgetSettingHeaderDao ) getDao() ).updateBudgetSettingHeader( budgetSettingHeaderVO );

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public List< Object > getBudgetSettingHeaderVOsByMapParameter( Map< String, Object > mapParameter ) throws KANException
   {
      return ( ( BudgetSettingHeaderDao ) getDao() ).getBudgetSettingHeaderVOsByMapParameter( mapParameter );
   }

}
