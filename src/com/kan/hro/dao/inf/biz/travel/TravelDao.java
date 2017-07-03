package com.kan.hro.dao.inf.biz.travel;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.travel.TravelVO;

public interface TravelDao
{
   public abstract int countTravelVOsByCondition( final TravelVO travelVO ) throws KANException;

   public abstract List< Object > getTravelVOsByCondition( final TravelVO travelVO ) throws KANException;

   public abstract List< Object > getTravelVOsByCondition( final TravelVO travelVO, RowBounds rowBounds ) throws KANException;

   public abstract TravelVO getTravelVOByTravelId( final String travelId ) throws KANException;

   public abstract int updateTravel( final TravelVO travelVO ) throws KANException;

   public abstract int insertTravel( final TravelVO travelVO ) throws KANException;

   public abstract int deleteTravel( final String travelId ) throws KANException;

}
