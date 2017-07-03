package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.util.KANException;

public class AccountModuleRelationDaoImpl extends Context implements AccountModuleRelationDao
{

   @Override
   public int countAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException
   {
      return ( Integer ) select( "countAccountModuleRelationVOsByCondition", accountModuleRelationVO );
   }

   @Override
   public List< Object > getAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException
   {
      return selectList( "getAccountModuleRelationVOsByCondition", accountModuleRelationVO );
   }

   @Override
   public List< Object > getAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAccountModuleRelationVOsByCondition", accountModuleRelationVO, rowBounds );
   }

   @Override
   public AccountModuleRelationVO getAccountModuleRelationVOByRelationId( final String relationId ) throws KANException
   {
      return ( AccountModuleRelationVO ) select( "getAccountModuleRelationVOByRelationId", relationId );
   }

   @Override
   public int updateAccountModuleRelation( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException
   {
      return update( "updateAccountModuleRelation", accountModuleRelationVO );
   }

   @Override
   public int insertAccountModuleRelation( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException
   {
      return insert( "insertAccountModuleRelation", accountModuleRelationVO );
   }

   @Override
   public int deleteAccountModuleRelationByAccountId( final String accountId ) throws KANException
   {          
      return delete( "deleteAccountModuleRelationByAccountId", accountId );
   }

   @Override
   public List< Object > getAccountModuleRelationVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getAccountModuleRelationVOsByAccountId", accountId );
   }

}
