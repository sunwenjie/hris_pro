package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.EntityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EntityService
{
   public abstract PagedListHolder getEntityVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EntityVO getEntityVOByEntityId( final String entityId ) throws KANException;

   public abstract int insertEntity( final EntityVO entityVO ) throws KANException;

   public abstract int updateEntity( final EntityVO entityVO ) throws KANException;

   public abstract int deleteEntity( final EntityVO entityVO ) throws KANException;

   public abstract List< Object > getEntityVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEntityBaseViews( final String accountId ) throws KANException;

}
