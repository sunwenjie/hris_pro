package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.AnnualLeaveRuleDetailDao;
import com.kan.base.dao.inf.management.AnnualLeaveRuleHeaderDao;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleDetailService;
import com.kan.base.util.KANException;

public class AnnualLeaveRuleDetailServiceImpl extends ContextService implements AnnualLeaveRuleDetailService
{

   private AnnualLeaveRuleHeaderDao annualLeaveRuleHeaderDao;

   public AnnualLeaveRuleHeaderDao getAnnualLeaveRuleHeaderDao()
   {
      return annualLeaveRuleHeaderDao;
   }

   public void setAnnualLeaveRuleHeaderDao( AnnualLeaveRuleHeaderDao annualLeaveRuleHeaderDao )
   {
      this.annualLeaveRuleHeaderDao = annualLeaveRuleHeaderDao;
   }

   @Override
   public PagedListHolder getAnnualLeaveRuleDetailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final AnnualLeaveRuleDetailDao annualLeaveRuleDetailDao = ( AnnualLeaveRuleDetailDao ) getDao();
      pagedListHolder.setHolderSize( annualLeaveRuleDetailDao.countAnnualLeaveRuleDetailVOsByCondition( ( AnnualLeaveRuleDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( annualLeaveRuleDetailDao.getAnnualLeaveRuleDetailVOsByCondition( ( AnnualLeaveRuleDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( annualLeaveRuleDetailDao.getAnnualLeaveRuleDetailVOsByCondition( ( AnnualLeaveRuleDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public AnnualLeaveRuleDetailVO getAnnualLeaveRuleDetailVOByDetailId( String detailId ) throws KANException
   {
      return ( ( AnnualLeaveRuleDetailDao ) getDao() ).getAnnualLeaveRuleDetailVOByDetailId( detailId );
   }

   @Override
   public int insertAnnualLeaveRuleDetail( AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return ( ( AnnualLeaveRuleDetailDao ) getDao() ).insertAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );
   }

   @Override
   public int updateAnnualLeaveRuleDetail( AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      return ( ( AnnualLeaveRuleDetailDao ) getDao() ).updateAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );
   }

   @Override
   public int deleteAnnualLeaveRuleDetail( AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO ) throws KANException
   {
      annualLeaveRuleDetailVO.setDeleted( "2" );
      return ( ( AnnualLeaveRuleDetailDao ) getDao() ).updateAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );
   }

   @Override
   public List< Object > getAnnualLeaveRuleDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return ( ( AnnualLeaveRuleDetailDao ) getDao() ).getAnnualLeaveRuleDetailVOsByHeaderId( headerId );
   }
}
