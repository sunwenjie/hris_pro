package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientGroupVO;

public interface ClientGroupService
{
   public abstract PagedListHolder getClientGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientGroupVO getClientGroupVOByClientGroupId( final String clientGroupId ) throws KANException;

   public abstract int updateClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract int insertClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract int deleteClientGroup( final ClientGroupVO clientGroupVO ) throws KANException;

   public abstract List< Object > getClientGroupBaseViews( final String accountId ) throws KANException;

   public abstract Object getClientGroupBaseViewByClientGroupVO( final ClientGroupVO clientGroupVO ) throws KANException;
   
   public abstract Object getClientGroupDTOsByClientGroupVO( final ClientGroupVO clientGroupVO ) throws KANException;
   
}
