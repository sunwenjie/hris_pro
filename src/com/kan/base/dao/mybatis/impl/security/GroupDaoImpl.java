package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.GroupDao;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.util.KANException;

public class GroupDaoImpl extends Context implements GroupDao
{
   @Override
   public int countGroupVOsByCondition( GroupVO groupVO ) throws KANException
   {
      return ( Integer ) select( "countGroupVOsByCondition", groupVO );
   }

   @Override
   public List< Object > getGroupVOsByCondition( GroupVO groupVO ) throws KANException
   {
      return selectList( "getGroupVOsByCondition", groupVO );
   }

   @Override
   public List< Object > getGroupVOsByCondition( GroupVO groupVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getGroupVOsByCondition", groupVO, rowBounds );
   }

   @Override
   public GroupVO getGroupVOByGroupId( String groupId ) throws KANException
   {
      return ( GroupVO ) select( "getGroupVOByGroupId", groupId );
   }

   @Override
   public int updateGroup( GroupVO groupVO ) throws KANException
   {
      return update( "updateGroup", groupVO );
   }

   @Override
   public int insertGroup( GroupVO groupVO ) throws KANException
   {
      return insert( "insertGroup", groupVO );
   }

   @Override
   public int deleteGroup( final String groupId ) throws KANException
   {
      return delete( "deleteGroup", groupId );
   }

   @Override
   public List< Object > getGroupVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getGroupVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getGroupBaseViewsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getGroupBaseViewsByAccountId", accountId );
   }

}
