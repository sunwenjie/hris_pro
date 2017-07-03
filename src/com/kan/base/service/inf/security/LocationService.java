package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.LocationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface LocationService
{
   public abstract PagedListHolder getLocationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LocationVO getLocationVOByLocationId( final String locationId ) throws KANException;

   public abstract int updateLocation( final LocationVO locationVO, final String positionId ) throws KANException;

   public abstract int insertLocation( final LocationVO locationVO, final String positionId ) throws KANException;

   public abstract int deleteLocation( final LocationVO locationVO ) throws KANException;
   
   public abstract List< Object > getLocationVOsByAccountId( final String accountId )throws KANException;
}
