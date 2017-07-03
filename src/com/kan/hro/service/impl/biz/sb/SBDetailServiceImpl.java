package com.kan.hro.service.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBDetailService;

public class SBDetailServiceImpl extends ContextService implements SBDetailService
{

   @Override
   public PagedListHolder getSBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBDetailDao sbDetailDao = ( SBDetailDao ) getDao();
      pagedListHolder.setHolderSize( sbDetailDao.countSBDetailVOsByCondition( ( SBDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbDetailDao.getSBDetailVOsByCondition( ( SBDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbDetailDao.getSBDetailVOsByCondition( ( SBDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SBDetailVO getSBDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).getSBDetailVOByDetailId( detailId );
   }

   @Override
   public int updateSBDetail( final SBDetailVO sbDetailVO ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).updateSBDetail( sbDetailVO );
   }

   @Override
   public int insertSBDetail( final SBDetailVO sbDetailVO ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).insertSBDetail( sbDetailVO );
   }

   @Override
   public int deleteSBDetail( final String sbDetailId ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).deleteSBDetail( sbDetailId );
   }

   @Override
   public List< Object > getSBDetailVOsByCondition( final SBDetailVO sbDetailVO ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).getSBDetailVOsByCondition( sbDetailVO );
   }

   @Override
   public List< Object > getSBDetailVOsBySbHeaderCond( final SBHeaderVO sbHeaderVO ) throws KANException
   {
      return ( ( SBDetailDao ) getDao() ).getSBDetailVOsBySbHeaderCond( sbHeaderVO );
   }

}
