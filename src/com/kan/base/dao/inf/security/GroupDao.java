package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.GroupVO;
import com.kan.base.util.KANException;

public interface GroupDao
{
   public abstract int countGroupVOsByCondition(final GroupVO groupVO) throws KANException ; 
   
   public abstract List< Object > getGroupVOsByCondition( final GroupVO groupVO ) throws KANException;

   public abstract List< Object > getGroupVOsByCondition( final GroupVO groupVO, RowBounds rowBounds ) throws KANException;

   public abstract GroupVO getGroupVOByGroupId( final String groupId ) throws KANException;

   public abstract int insertGroup( final GroupVO groupVO ) throws KANException;

   public abstract int updateGroup( final GroupVO groupVO ) throws KANException;

   public abstract int deleteGroup( final String groupId ) throws KANException;

   public abstract List< Object > getGroupVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getGroupBaseViewsByAccountId( final String accountId ) throws KANException;
}
