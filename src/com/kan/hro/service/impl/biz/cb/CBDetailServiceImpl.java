package com.kan.hro.service.impl.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.service.inf.biz.cb.CBDetailService;

public class CBDetailServiceImpl extends ContextService implements CBDetailService
{

   @Override
   public PagedListHolder getCBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CBDetailDao cbDetailDao = ( CBDetailDao ) getDao();
      pagedListHolder.setHolderSize( cbDetailDao.countCBDetailVOsByCondition( ( CBDetailVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbDetailDao.getCBDetailVOsByCondition( ( CBDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbDetailDao.getCBDetailVOsByCondition( ( CBDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public CBDetailVO getCBDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( CBDetailDao ) getDao() ).getCBDetailVOByDetailId( detailId );
   }

   @Override
   public int updateCBDetail( final CBDetailVO cbDetailVO ) throws KANException
   {
      return ( ( CBDetailDao ) getDao() ).updateCBDetail( cbDetailVO );
   }

   @Override
   public int insertCBDetail( final CBDetailVO cbDetailVO ) throws KANException
   {
      return ( ( CBDetailDao ) getDao() ).insertCBDetail( cbDetailVO );
   }

   @Override
   public int deleteCBDetail( final String cbDetailId ) throws KANException
   {
      return ( ( CBDetailDao ) getDao() ).deleteCBDetail( cbDetailId );
   }

   @Override
   public List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException
   {
      return ( ( CBDetailDao ) getDao() ).getCBDetailVOsByCondition( cbDetailVO );
   }

}
