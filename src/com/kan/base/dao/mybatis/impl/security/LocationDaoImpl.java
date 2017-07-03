package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.LocationDao;
import com.kan.base.domain.security.LocationVO;
import com.kan.base.util.KANException;

public class LocationDaoImpl extends Context implements LocationDao
{

   @Override
   public int countLocationVOsByCondition( LocationVO locationVO ) throws KANException
   {
      return ( Integer ) select( "countLocationVOsByCondition", locationVO );
   }

   @Override
   public List< Object > getLocationVOsByCondition( LocationVO locationVO ) throws KANException
   {
      return selectList( "getLocationVOsByCondition", locationVO );
   }

   @Override
   public List< Object > getLocationVOsByCondition( LocationVO locationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLocationVOsByCondition", locationVO, rowBounds );
   }

   @Override
   public LocationVO getLocationVOByLocationId( String locationId ) throws KANException
   {
      return ( LocationVO ) select( "getLocationVOByLocationId", locationId );
   }

   @Override
   public int updateLocation( LocationVO locationVO ) throws KANException
   {
      return update( "updateLocation", locationVO );
   }

   @Override
   public int insertLocation( LocationVO locationVO ) throws KANException
   {
      return insert( "insertLocation", locationVO );
   }

   @Override
   public int deleteLocation( LocationVO locationVO ) throws KANException
   {
      return delete( "deleteLocation", locationVO );
   }

   @Override
   public List< Object > getLocationVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getLocationVOsByAccountId", accountId );
   }

}
