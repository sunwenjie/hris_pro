package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.LocationVO;
import com.kan.base.util.KANException;

public interface LocationDao
{
   public abstract int countLocationVOsByCondition(final LocationVO locationVO) throws KANException ; 
   
   public abstract List< Object > getLocationVOsByCondition( final LocationVO locationVO ) throws KANException;

   public abstract List< Object > getLocationVOsByCondition( final LocationVO locationVO, RowBounds rowBounds ) throws KANException;

   public abstract LocationVO getLocationVOByLocationId( final String locationId ) throws KANException;
   
   public abstract List< Object > getLocationVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertLocation( final LocationVO locationVO ) throws KANException;

   public abstract int updateLocation( final LocationVO locationVO ) throws KANException;

   public abstract int deleteLocation( final LocationVO locationVO ) throws KANException;
   
}
