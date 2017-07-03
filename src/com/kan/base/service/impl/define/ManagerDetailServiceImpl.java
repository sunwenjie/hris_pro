package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ManagerDetailDao;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerDetailService;
import com.kan.base.util.KANException;

public class ManagerDetailServiceImpl extends ContextService implements ManagerDetailService
{

   @Override
   public PagedListHolder getManagerDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ManagerDetailDao managerDetailDao = ( ManagerDetailDao ) getDao();
      pagedListHolder.setHolderSize( managerDetailDao.countManagerDetailVOsByCondition( ( ManagerDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( managerDetailDao.getManagerDetailVOsByCondition( ( ManagerDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( managerDetailDao.getManagerDetailVOsByCondition( ( ManagerDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ManagerDetailVO getManagerDetailVOByManagerDetailId( final String managerDetailId ) throws KANException
   {
      return ( ( ManagerDetailDao ) getDao() ).getManagerDetailVOByManagerDetailId( managerDetailId );
   }

   @Override
   public int insertManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return ( ( ManagerDetailDao ) getDao() ).insertManagerDetail( managerDetailVO );
   }

   @Override
   public int updateManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      return ( ( ManagerDetailDao ) getDao() ).updateManagerDetail( managerDetailVO );
   }

   @Override
   public int deleteManagerDetail( final ManagerDetailVO managerDetailVO ) throws KANException
   {
      managerDetailVO.setDeleted( ManagerDetailVO.FALSE );
      return ( ( ManagerDetailDao ) getDao() ).updateManagerDetail( managerDetailVO );
   }

   @Override
   public List< Object > getManagerDetailVOsByManagerHeaderId( final String managerHeaderId ) throws KANException
   {
      return ( ( ManagerDetailDao ) getDao() ).getManagerDetailVOsByManagerHeaderId( managerHeaderId );
   }

}
