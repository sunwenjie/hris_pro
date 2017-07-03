package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.BankVO;
import com.kan.base.util.KANException;

public interface BankDao
{
   public abstract int countBankVOsByCondition( final BankVO bankVO ) throws KANException;

   public abstract List< Object > getBankVOsByCondition( final BankVO bankVO ) throws KANException;

   public abstract List< Object > getBankVOsByCondition( final BankVO bankVO, final RowBounds rowBounds ) throws KANException;

   public abstract BankVO getBankVOByBankId( final String bankId ) throws KANException;

   public abstract int insertBank( final BankVO bankVO ) throws KANException;

   public abstract int updateBank( final BankVO bankVO ) throws KANException;

   public abstract int deleteBank( final BankVO bankVO ) throws KANException;

   public abstract List< Object > getBankVOsByAccountId( final String accountId ) throws KANException;
}
