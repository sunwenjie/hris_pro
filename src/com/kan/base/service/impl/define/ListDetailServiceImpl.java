package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListDetailService;
import com.kan.base.util.KANException;

public class ListDetailServiceImpl extends ContextService implements ListDetailService
{

   @Override
   public PagedListHolder getListDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ListDetailDao listDetailDao = ( ListDetailDao ) getDao();
      pagedListHolder.setHolderSize( listDetailDao.countListDetailVOsByCondition( ( ListDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( listDetailDao.getListDetailVOsByCondition( ( ListDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( listDetailDao.getListDetailVOsByCondition( ( ListDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ListDetailVO getListDetailVOByListDetailId( final String listDetailId ) throws KANException
   {
      return ( ( ListDetailDao ) getDao() ).getListDetailVOByListDetailId( listDetailId );
   }

   @Override
   public int insertListDetail( final ListDetailVO listDetailVO ) throws KANException
   {
      return ( ( ListDetailDao ) getDao() ).insertListDetail( listDetailVO );
   }

   @Override
   public int updateListDetail( final ListDetailVO listDetailVO ) throws KANException
   {
      return ( ( ListDetailDao ) getDao() ).updateListDetail( listDetailVO );
   }

   @Override
   public int deleteListDetail( final ListDetailVO listDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      listDetailVO.setDeleted( ListDetailVO.FALSE );
      return ( ( ListDetailDao ) getDao() ).updateListDetail( listDetailVO );
   }

   @Override
   public List< Object > getListDetailVOsByListHeaderId( final String listHeaderId ) throws KANException
   {
      return ( ( ListDetailDao ) getDao() ).getListDetailVOsByListHeaderId( listHeaderId );
   }

   @Override
   public List< Object > getListDetailVOsByCondition( ListDetailVO listDetailVO ) throws KANException
   {
      return ( ( ListDetailDao ) getDao() ).getListDetailVOsByCondition( listDetailVO );
   }
}
