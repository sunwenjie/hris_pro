package com.kan.base.service.impl.security;

import java.util.List;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.OrgShootDao;
import com.kan.base.domain.security.OrgShootVO;
import com.kan.base.service.inf.security.OrgShootService;
import com.kan.base.util.KANException;

public class OrgShootServiceImpl extends ContextService implements OrgShootService
{

   public List< Object > getOrgShootVOsByCond( OrgShootVO orgShootVO ) throws KANException
   {
      return ( ( OrgShootDao ) getDao() ).getOrgShootVOsByCond( orgShootVO );
   }

   @Override
   public int updateOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return ( ( OrgShootDao ) getDao() ).updateOrgShoot( orgShootVO );
   }

   @Override
   public int insertOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return ( ( OrgShootDao ) getDao() ).insertOrgShoot( orgShootVO );
   }

   @Override
   public int deleteOrgShoot( OrgShootVO orgShootVO ) throws KANException
   {
      return ( ( OrgShootDao ) getDao() ).deleteOrgShoot( orgShootVO );
   }

   @Override
   public OrgShootVO getOrgShootVOByShootId( String shootId ) throws KANException
   {
      return ( ( OrgShootDao ) getDao() ).getOrgShootVOByShootId( shootId );
   }

}
