package com.kan.hro.service.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBDetailTempDao;
import com.kan.hro.domain.biz.sb.SBDetailTempVO;
import com.kan.hro.service.inf.biz.sb.SBFeedbackDetailTempService;

public class SBFeedbackDetailTempServiceImpl extends ContextService implements SBFeedbackDetailTempService
{

   @Override
   public PagedListHolder getSBDetailTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBDetailTempDao sbDetailTempDao = ( SBDetailTempDao ) getDao();
      pagedListHolder.setHolderSize( sbDetailTempDao.countSBDetailTempVOsByCondition( ( SBDetailTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbDetailTempDao.getSBDetailTempVOsByCondition( ( SBDetailTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbDetailTempDao.getSBDetailTempVOsByCondition( ( SBDetailTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SBDetailTempVO getSBDetailTempVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SBDetailTempDao ) getDao() ).getSBDetailTempVOByDetailId( detailId );
   }

   @Override
   public int updateSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return ( ( SBDetailTempDao ) getDao() ).updateSBDetailTemp( sbDetailTempVO );
   }

   @Override
   public int insertSBDetailTemp( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return ( ( SBDetailTempDao ) getDao() ).insertSBDetailTemp( sbDetailTempVO );
   }

   @Override
   public int deleteSBDetailTemp( final String sbDetailId ) throws KANException
   {
      return ( ( SBDetailTempDao ) getDao() ).deleteSBDetailTemp( sbDetailId );
   }

   @Override
   public List< Object > getSBDetailTempVOsByCondition( final SBDetailTempVO sbDetailTempVO ) throws KANException
   {
      return ( ( SBDetailTempDao ) getDao() ).getSBDetailTempVOsByCondition( sbDetailTempVO );
   }

}
