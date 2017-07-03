package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.EntityVO;
import com.kan.base.util.KANException;

public interface EntityDao
{
   public abstract int countEntityVOsByCondition( final EntityVO entityVO ) throws KANException;

   public abstract List< Object > getEntityVOsByCondition( final EntityVO entityVO ) throws KANException;

   public abstract List< Object > getEntityVOsByCondition( final EntityVO entityVO, final RowBounds rowBounds ) throws KANException;

   public abstract EntityVO getEntityVOByEntityId( final String entityId ) throws KANException;

   public abstract int insertEntity( final EntityVO entityVO ) throws KANException;

   public abstract int updateEntity( final EntityVO entityVO ) throws KANException;

   public abstract int deleteEntity( final EntityVO entityVO ) throws KANException;

   public abstract List< Object > getEntityVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEntityBaseViews( final String accountId ) throws KANException;
}
