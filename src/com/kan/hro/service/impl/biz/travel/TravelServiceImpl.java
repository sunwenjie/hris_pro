package com.kan.hro.service.impl.biz.travel;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.travel.TravelDao;
import com.kan.hro.domain.biz.travel.TravelVO;
import com.kan.hro.service.inf.biz.travel.TravelService;

public class TravelServiceImpl extends ContextService implements TravelService
{

   @Override
   public PagedListHolder getTravelVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final TravelDao travelDao = ( TravelDao ) getDao();
      pagedListHolder.setHolderSize( travelDao.countTravelVOsByCondition( ( TravelVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( travelDao.getTravelVOsByCondition( ( TravelVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( travelDao.getTravelVOsByCondition( ( TravelVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TravelVO getTravelVOByTravelId( final String travelId ) throws KANException
   {

      return ( ( TravelDao ) getDao() ).getTravelVOByTravelId( travelId );
   }

   @Override
   public int insertTravel( final TravelVO travelVO ) throws KANException
   {
      return ( ( TravelDao ) getDao() ).insertTravel( travelVO );
   }

   @Override
   public int updateTravel( final TravelVO travelVO ) throws KANException
   {

      return ( ( TravelDao ) getDao() ).updateTravel( travelVO );

   }

   @Override
   public int deleteTravel( final TravelVO travelVO ) throws KANException
   {
      if ( travelVO != null && KANUtil.filterEmpty( travelVO.getTravelId(), "0" ) != null )
      {
         return ( ( TravelDao ) getDao() ).deleteTravel( travelVO.getTravelId() );
      }

      return 0;
   }

   @Override
   public int submitTravel( TravelVO submitTravelVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 批准
         submitTravelVO.setStatus( "3" );

         // 修改TravelVO
         ( ( TravelDao ) getDao() ).updateTravel( submitTravelVO );

         // 提交事务
         this.commitTransaction();

         return -1;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         e.printStackTrace();
      }

      return 1;
   }

}
