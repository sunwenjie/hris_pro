package com.kan.base.dao.inf.security;

import java.util.List;

import com.kan.base.domain.security.OrgShootVO;
import com.kan.base.util.KANException;

public interface OrgShootDao
{

   public abstract List< Object > getOrgShootVOsByCond( final OrgShootVO orgShootVO ) throws KANException;

   public abstract int updateOrgShoot( final OrgShootVO orgShootVO ) throws KANException;

   public abstract int insertOrgShoot( final OrgShootVO orgShootVO ) throws KANException;

   public abstract int deleteOrgShoot( final OrgShootVO orgShootVO ) throws KANException;

   public abstract OrgShootVO getOrgShootVOByShootId( final String shootId ) throws KANException;

}
