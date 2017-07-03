package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.KANException;

public interface BranchDao
{
   public abstract int countBranchVOsByCondition(final BranchVO branchVO) throws KANException ; 
   
   public abstract List< Object > getBranchVOsByCondition( final BranchVO branchVO ) throws KANException;

   public abstract List< Object > getBranchVOsByCondition( final BranchVO branchVO, RowBounds rowBounds ) throws KANException;

   public abstract BranchVO getBranchVOByBranchId( final String branchId ) throws KANException;
   
   public abstract List< Object > getBranchVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertBranch( final BranchVO branchVO ) throws KANException;

   public abstract int updateBranch( final BranchVO branchVO ) throws KANException;

   public abstract int deleteBranch( final BranchVO branchVO ) throws KANException;

   public abstract List< Object > getBranchVOsByParentBranchId( BranchVO subBranchVO );

   public abstract List< Object > getBUFuction();
   
}
