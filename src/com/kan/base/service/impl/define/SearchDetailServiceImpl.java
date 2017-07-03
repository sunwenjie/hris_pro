package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.SearchDetailService;
import com.kan.base.util.KANException;

public class SearchDetailServiceImpl extends ContextService implements SearchDetailService
{

   @Override
   public PagedListHolder getSearchDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SearchDetailDao searchDetailDao = ( SearchDetailDao ) getDao();
      pagedListHolder.setHolderSize( searchDetailDao.countSearchDetailVOsByCondition( ( SearchDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( searchDetailDao.getSearchDetailVOsByCondition( ( SearchDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( searchDetailDao.getSearchDetailVOsByCondition( ( SearchDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public SearchDetailVO getSearchDetailVOBySearchDetailId( final String searchDetailId ) throws KANException
   {
      return ( ( SearchDetailDao ) getDao() ).getSearchDetailVOBySearchDetailId( searchDetailId );
   }

   @Override
   public int insertSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return ( ( SearchDetailDao ) getDao() ).insertSearchDetail( searchDetailVO );
   }

   @Override
   public int updateSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return ( ( SearchDetailDao ) getDao() ).updateSearchDetail( searchDetailVO );
   }

   @Override
   public int deleteSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      searchDetailVO.setDeleted( SearchDetailVO.FALSE );
      return ( ( SearchDetailDao ) getDao() ).updateSearchDetail( searchDetailVO );
   }

   @Override
   public List< Object > getSearchDetailVOsBySearchHeaderId( final String searchHeaderId ) throws KANException
   {
      return ( ( SearchDetailDao ) getDao() ).getSearchDetailVOsBySearchHeaderId( searchHeaderId );
   }

   @Override
   public List< Object > getSearchDetailVOsByCondition( SearchDetailVO searchDetailVO ) throws KANException
   {
      return ( ( SearchDetailDao ) getDao() ).getSearchDetailVOsByCondition( searchDetailVO );
   }

}
