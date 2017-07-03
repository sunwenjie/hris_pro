package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.OrgShootDao;
import com.kan.base.domain.security.OrgShootVO;
import com.kan.base.util.KANException;

public class OrgShootDaoImpl extends Context implements OrgShootDao
{
   @Override
   public List< Object > getOrgShootVOsByCond( OrgShootVO orgShootVO ) throws KANException
   {
      return selectList( "getOrgShootVOsByCond", orgShootVO );
   }

   @Override
   public int updateOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return update( "updateOrgShoot", orgShootVO );
   }

   @Override
   public int insertOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return insert( "insertOrgShoot", orgShootVO );
   }

   @Override
   public int deleteOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return delete( "deleteOrgShoot", orgShootVO );
   }

   @Override
   public OrgShootVO getOrgShootVOByShootId( String shootId ) throws KANException
   {
      return ( OrgShootVO ) select( "getOrgShootVOByShootId", shootId );
   }

}
