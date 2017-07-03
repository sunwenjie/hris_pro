package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.AccountDao;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;

public class AccountDaoImpl extends Context implements AccountDao
{

   @Override
   public int countAccountVOsByCondition( AccountVO accountVO ) throws KANException
   {
      return ( Integer ) select( "countAccountVOsByCondition", accountVO );
   }

   @Override
   public List< Object > getAccountVOsByCondition( AccountVO accountVO ) throws KANException
   {
      return selectList( "getAccountVOsByCondition", accountVO );
   }

   @Override
   public List< Object > getAccountVOsByCondition( AccountVO accountVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAccountVOsByCondition", accountVO, rowBounds );
   }

   @Override
   public AccountVO getAccountVOByAccountId( String accountId ) throws KANException
   {
      return ( AccountVO ) select( "getAccountVOByAccountId", accountId );
   }

   @Override
   public int updateAccount( AccountVO accountVO ) throws KANException
   {
      return insert( "updateAccount", accountVO );
   }

   @Override
   public int insertAccount( AccountVO accountVO ) throws KANException
   {
      return insert( "insertAccount", accountVO );
   }

   @Override
   public int deleteAccount( AccountVO accountVO ) throws KANException
   {
      return delete( "deleteAccount", accountVO );
   }

   @Override
   public List< Object > getAccountBaseViews() throws KANException
   {
      return selectList( "getAccountBaseViews", null );
   }

   @Override
   public AccountVO getAccountVOByAccountName(final String accountName ) throws KANException
   {
      return ( AccountVO ) select( "getAccountVOByAccountName", accountName );
   }

}