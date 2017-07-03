package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;

public interface AccountDao
{

   public abstract int countAccountVOsByCondition( final AccountVO accountVO ) throws KANException;

   public abstract List< Object > getAccountVOsByCondition( final AccountVO accountVO ) throws KANException;

   public abstract List< Object > getAccountVOsByCondition( final AccountVO accountVO, RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getAccountBaseViews() throws KANException;

   public abstract AccountVO getAccountVOByAccountId( final String accountId ) throws KANException;
   
   public abstract AccountVO getAccountVOByAccountName( final String accountName ) throws KANException;

   public abstract int updateAccount( final AccountVO accountVO ) throws KANException;

   public abstract int insertAccount( final AccountVO accountVO ) throws KANException;

   public abstract int deleteAccount( final AccountVO accountVO ) throws KANException;

}