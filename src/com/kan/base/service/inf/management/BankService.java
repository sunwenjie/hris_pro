package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.BankVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface BankService
{
   public abstract PagedListHolder getBankVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BankVO getBankVOByBankId( final String bankId ) throws KANException;

   public abstract int insertBank( final BankVO bankVO ) throws KANException;

   public abstract int updateBank( final BankVO bankVO ) throws KANException;

   public abstract int deleteBank( final BankVO bankVO ) throws KANException;
   
   public abstract List< Object > getBankVOsByAccountId( final String accountId ) throws KANException;
}
