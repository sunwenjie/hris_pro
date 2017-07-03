package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface AccountService
{

   public abstract PagedListHolder getAccountVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getAccountBaseViews() throws KANException;

   public abstract AccountVO getAccountVOByAccountId( final String accountId ) throws KANException;
   
   public abstract AccountVO getAccountVOByAccountName( final String accountName ) throws KANException;

   public abstract int updateAccount( final AccountVO accountVO ) throws KANException;

   public abstract int insertAccount( final AccountVO accountVO ) throws KANException;

   public abstract void deleteAccount( final AccountVO accountVO ) throws KANException;
   
   public abstract boolean initiateAccount( final AccountVO accountVO ) throws KANException;

}
