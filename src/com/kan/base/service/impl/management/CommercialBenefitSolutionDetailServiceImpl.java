package com.kan.base.service.impl.management;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.CommercialBenefitSolutionDetailDao;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionDetailService;
import com.kan.base.util.KANException;

public class CommercialBenefitSolutionDetailServiceImpl extends ContextService implements CommercialBenefitSolutionDetailService
{

   @Override
   public PagedListHolder getCommercialBenefitSolutionDetailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final CommercialBenefitSolutionDetailDao commercialBenefitDetailDao = ( CommercialBenefitSolutionDetailDao ) getDao();
      pagedListHolder.setHolderSize( commercialBenefitDetailDao.countCommercialBenefitSolutionDetailVOsByCondition( ( CommercialBenefitSolutionDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( commercialBenefitDetailDao.getCommercialBenefitSolutionDetailVOsByCondition( ( CommercialBenefitSolutionDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( commercialBenefitDetailDao.getCommercialBenefitSolutionDetailVOsByCondition( ( CommercialBenefitSolutionDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public CommercialBenefitSolutionDetailVO getCommercialBenefitSolutionDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( CommercialBenefitSolutionDetailDao ) getDao() ).getCommercialBenefitSolutionDetailVOByDetailId( detailId );
   }

   @Override
   public int updateCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return ( ( CommercialBenefitSolutionDetailDao ) getDao() ).updateCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );
   }

   @Override
   public int insertCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      return ( ( CommercialBenefitSolutionDetailDao ) getDao() ).insertCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );
   }

   @Override
   public void deleteCommercialBenefitSolutionDetail( final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      commercialBenefitSolutionDetailVO.setDeleted( CalendarDetailVO.FALSE );
      ( ( CommercialBenefitSolutionDetailDao ) getDao() ).updateCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );
   }

}
