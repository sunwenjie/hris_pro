package com.kan.hro.service.inf.biz.travel;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.travel.TravelVO;

public interface TravelService
{
   public abstract PagedListHolder getTravelVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TravelVO getTravelVOByTravelId( final String travelId ) throws KANException;

   public abstract int insertTravel( final TravelVO travelVO ) throws KANException;

   public abstract int updateTravel( final TravelVO travelVO ) throws KANException;

   public abstract int deleteTravel( final TravelVO travelVO ) throws KANException;

   public abstract int submitTravel( TravelVO submitTravelVO )throws KANException;

}
