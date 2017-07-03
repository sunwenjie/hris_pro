package com.kan.base.service.impl.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.AnnualLeaveRuleDetailDao;
import com.kan.base.dao.inf.management.AnnualLeaveRuleHeaderDao;
import com.kan.base.domain.management.AnnualLeaveRuleDTO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.util.KANException;

public class AnnualLeaveRuleHeaderServiceImpl extends ContextService implements AnnualLeaveRuleHeaderService
{

   // ×¢ÈëDao
   private AnnualLeaveRuleDetailDao annualLeaveRuleDetailDao;

   public AnnualLeaveRuleDetailDao getAnnualLeaveRuleDetailDao()
   {
      return annualLeaveRuleDetailDao;
   }

   public void setAnnualLeaveRuleDetailDao( AnnualLeaveRuleDetailDao annualLeaveRuleDetailDao )
   {
      this.annualLeaveRuleDetailDao = annualLeaveRuleDetailDao;
   }

   @Override
   public PagedListHolder getAnnualLeaveRuleHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final AnnualLeaveRuleHeaderDao annualLeaveRuleHeaderDao = ( AnnualLeaveRuleHeaderDao ) getDao();
      pagedListHolder.setHolderSize( annualLeaveRuleHeaderDao.countAnnualLeaveRuleHeaderVOsByCondition( ( AnnualLeaveRuleHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( annualLeaveRuleHeaderDao.getAnnualLeaveRuleHeaderVOsByCondition( ( AnnualLeaveRuleHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( annualLeaveRuleHeaderDao.getAnnualLeaveRuleHeaderVOsByCondition( ( AnnualLeaveRuleHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public AnnualLeaveRuleHeaderVO getAnnualLeaveRuleHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( AnnualLeaveRuleHeaderDao ) getDao() ).getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertAnnualLeaveRuleHeader( AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException
   {
      return ( ( AnnualLeaveRuleHeaderDao ) getDao() ).insertAnnualLeaveRuleHeader( annualLeaveHeaderRuleVO );
   }

   @Override
   public int updateAnnualLeaveRuleHeader( AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException
   {
      return ( ( AnnualLeaveRuleHeaderDao ) getDao() ).updateAnnualLeaveRuleHeader( annualLeaveHeaderRuleVO );
   }

   @Override
   public int deleteAnnualLeaveRuleHeader( AnnualLeaveRuleHeaderVO annualLeaveHeaderRuleVO ) throws KANException
   {
      annualLeaveHeaderRuleVO.setDeleted( "2" );
      return ( ( AnnualLeaveRuleHeaderDao ) getDao() ).updateAnnualLeaveRuleHeader( annualLeaveHeaderRuleVO );
   }

   @Override
   public List< AnnualLeaveRuleDTO > getAnnualLeaveRuleDTOsByAccountId( final String accountId ) throws KANException
   {
      final List< AnnualLeaveRuleDTO > rusultAnnualLeaveRuleDTOs = new ArrayList< AnnualLeaveRuleDTO >();
      try
      {
         final List< Object > annualLeaveRuleHeaderVOs = ( ( AnnualLeaveRuleHeaderDao ) getDao() ).getAnnualLeaveRuleHeaderVOsByAccountId( accountId );
         if ( annualLeaveRuleHeaderVOs != null && annualLeaveRuleHeaderVOs.size() > 0 )
         {
            for ( Object o : annualLeaveRuleHeaderVOs )
            {
               final AnnualLeaveRuleDTO tempAnnualLeaveRuleDTO = new AnnualLeaveRuleDTO();
               tempAnnualLeaveRuleDTO.setAnnualLeaveRuleHeaderVO( ( AnnualLeaveRuleHeaderVO ) o );

               final List< Object > annualLeaveRuleDetailVOs = this.getAnnualLeaveRuleDetailDao().getAnnualLeaveRuleDetailVOsByHeaderId( ( ( AnnualLeaveRuleHeaderVO ) o ).getHeaderId() );
               if ( annualLeaveRuleDetailVOs != null && annualLeaveRuleDetailVOs.size() > 0 )
               {
                  for ( Object oo : annualLeaveRuleDetailVOs )
                  {
                     tempAnnualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs().add( ( AnnualLeaveRuleDetailVO ) oo );
                  }
               }

               rusultAnnualLeaveRuleDTOs.add( tempAnnualLeaveRuleDTO );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rusultAnnualLeaveRuleDTOs;
   }
}
