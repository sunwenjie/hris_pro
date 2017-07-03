package com.kan.base.service.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.LocationDao;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.util.KANException;

public class LocationServiceImpl extends ContextService implements LocationService
{
   private PositionDao positionDao;

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   @Override
   public PagedListHolder getLocationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LocationDao locationDao = ( LocationDao ) getDao();
      pagedListHolder.setHolderSize( locationDao.countLocationVOsByCondition( ( LocationVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( locationDao.getLocationVOsByCondition( ( LocationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( locationDao.getLocationVOsByCondition( ( LocationVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public LocationVO getLocationVOByLocationId( final String locationId ) throws KANException
   {
      return ( ( LocationDao ) getDao() ).getLocationVOByLocationId( locationId );
   }

   @Override
   public int updateLocation( final LocationVO locationVO, final String positionId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         ( ( LocationDao ) getDao() ).updateLocation( locationVO );

         // 获取刚刚插入的LocationVO的locationId
         String locationId = locationVO.getLocationId();

         if ( positionId != null && !"".equals( positionId.trim() ) )
         {
            // 获取前端传过来的positionId对应的positionVO
            PositionVO positionVO = positionDao.getPositionVOByPositionId( positionId );
            positionVO.setLocationId( locationId );
            positionVO.update( positionVO );
            positionDao.updatePosition( positionVO );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int insertLocation( final LocationVO locationVO, final String positionId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( LocationDao ) getDao() ).insertLocation( locationVO );

         // 获取刚刚插入的LocationVO的locationId
         String locationId = locationVO.getLocationId();

         if ( positionId != null && !"".equals( positionId.trim() ) )
         {
            // 获取前端传过来的positionId对应的positionVO
            PositionVO positionVO = positionDao.getPositionVOByPositionId( positionId );
            positionVO.setLocationId( locationId );
            positionVO.update( positionVO );
            positionDao.updatePosition( positionVO );
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int deleteLocation( final LocationVO locationVO ) throws KANException
   {
      return ( ( LocationDao ) getDao() ).deleteLocation( locationVO );
   }

   @Override
   public List< Object > getLocationVOsByAccountId( String accountId ) throws KANException
   {
      return ( ( LocationDao ) getDao() ).getLocationVOsByAccountId( accountId );
   }

}
