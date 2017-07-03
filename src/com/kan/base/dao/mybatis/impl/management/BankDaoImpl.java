package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.BankDao;
import com.kan.base.domain.management.BankVO;
import com.kan.base.util.KANException;

public class BankDaoImpl extends Context implements BankDao
{

   @Override
   public int countBankVOsByCondition( final BankVO bankVO ) throws KANException
   {
      return ( Integer ) select( "countBankVOsByCondition", bankVO );
   }

   @Override
   public List< Object > getBankVOsByCondition( final BankVO bankVO ) throws KANException
   {
      return selectList( "getBankVOsByCondition", bankVO );
   }

   @Override
   public List< Object > getBankVOsByCondition( final BankVO bankVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBankVOsByCondition", bankVO, rowBounds );
   }

   @Override
   public BankVO getBankVOByBankId( final String bankId ) throws KANException
   {
      return ( BankVO ) select( "getBankVOByBankId", bankId );
   }

   @Override
   public int insertBank( final BankVO bankVO ) throws KANException
   {
      return insert( "insertBank", bankVO );
   }

   @Override
   public int updateBank( final BankVO bankVO ) throws KANException
   {
      return update( "updateBank", bankVO );
   }

   @Override
   public int deleteBank( final BankVO bankVO ) throws KANException
   {
      return delete( "deleteBank", bankVO );
   }

   @Override
   public List< Object > getBankVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBankVOsByAccountId", accountId );
   }

}
