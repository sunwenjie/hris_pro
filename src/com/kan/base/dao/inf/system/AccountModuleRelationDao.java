package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.util.KANException;

public interface AccountModuleRelationDao
{
   public abstract int countAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException;

   public abstract List< Object > getAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException;

   public abstract List< Object > getAccountModuleRelationVOsByCondition( final AccountModuleRelationVO accountModuleRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract AccountModuleRelationVO getAccountModuleRelationVOByRelationId( final String relationId ) throws KANException;

   public abstract int insertAccountModuleRelation( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException;

   public abstract int updateAccountModuleRelation( final AccountModuleRelationVO accountModuleRelationVO ) throws KANException;

   public abstract int deleteAccountModuleRelationByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getAccountModuleRelationVOsByAccountId( final String accountId ) throws KANException;

}
