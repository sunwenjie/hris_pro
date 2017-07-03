package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.BranchDao;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.KANException;

public class BranchDaoImpl extends Context implements BranchDao
{

   @Override
   public int countBranchVOsByCondition( BranchVO branchVO ) throws KANException
   {
      return ( Integer ) select( "countBranchVOsByCondition", branchVO );
   }

   @Override
   public List< Object > getBranchVOsByCondition( BranchVO branchVO ) throws KANException
   {
      return selectList( "getBranchVOsByCondition", branchVO );
   }

   @Override
   public List< Object > getBranchVOsByCondition( BranchVO branchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBranchVOsByCondition", branchVO, rowBounds );
   }

   @Override
   public BranchVO getBranchVOByBranchId( String branchId ) throws KANException
   {
      return ( BranchVO ) select( "getBranchVOByBranchId", branchId );
   }

   @Override
   public int updateBranch( BranchVO branchVO ) throws KANException
   {
      return update( "updateBranch", branchVO );
   }

   @Override
   public int insertBranch( BranchVO branchVO ) throws KANException
   {
      return insert( "insertBranch", branchVO );
   }

   @Override
   public int deleteBranch( BranchVO branchVO ) throws KANException
   {
      return delete( "deleteBranch", branchVO );
   }

   @Override
   public List< Object > getBranchVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getBranchVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getBranchVOsByParentBranchId( BranchVO subBranchVO )
   {
      return selectList( "getBranchVOsByParentBranchId", subBranchVO );
   }

   @Override
   public List< Object > getBUFuction()
   {
      return selectList( "getBUFuction", null );
   }

}
