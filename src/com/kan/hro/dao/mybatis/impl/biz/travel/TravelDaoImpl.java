package com.kan.hro.dao.mybatis.impl.biz.travel;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.travel.TravelDao;
import com.kan.hro.domain.biz.travel.TravelVO;

public class TravelDaoImpl extends Context implements TravelDao
{

   @Override
   public int countTravelVOsByCondition( final TravelVO travelVO ) throws KANException
   {
      return ( Integer ) select( "countTravelVOsByCondition", travelVO );
   }

   @Override
   public List< Object > getTravelVOsByCondition( final TravelVO travelVO ) throws KANException
   {
      return selectList( "getTravelVOsByCondition", travelVO );
   }

   @Override
   public List< Object > getTravelVOsByCondition( final TravelVO travelVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTravelVOsByCondition", travelVO, rowBounds );
   }

   @Override
   public TravelVO getTravelVOByTravelId( final String travelId ) throws KANException
   {
      return ( TravelVO ) select( "getTravelVOByTravelId", travelId );
   }

   @Override
   public int updateTravel( final TravelVO travelVO ) throws KANException
   {

      return update( "updateTravel", travelVO );
   }

   @Override
   public int insertTravel( final TravelVO travelVO ) throws KANException
   {
      return insert( "insertTravel", travelVO );
   }

   @Override
   public int deleteTravel( final String travelId ) throws KANException
   {
      return delete( "deleteTravel", travelId );
   }

}
