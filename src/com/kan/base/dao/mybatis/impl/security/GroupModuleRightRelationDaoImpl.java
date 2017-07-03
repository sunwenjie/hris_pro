package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.GroupModuleRightRelationDao;
import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.util.KANException;

public class GroupModuleRightRelationDaoImpl extends Context implements GroupModuleRightRelationDao
{

   @Override
   public int countGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return ( Integer ) select( "countGroupModuleRightRelationVOsByCondition", groupModuleRightRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return selectList( "getGroupModuleRightRelationVOsByCondition", groupModuleRightRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO, RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getGroupModuleRightRelationVOsByCondition", groupModuleRightRelationVO, rowBounds );
   }

   @Override
   public GroupModuleRightRelationVO getGroupModuleRightRelationVOByRelationId( final String relationId ) throws KANException
   {
      return ( GroupModuleRightRelationVO ) select( "getGroupModuleRightRelationVOByRelationId", relationId );
   }

   @Override
   public int updateGroupModuleRightRelation( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return update( "updateGroupModuleRightRelation", groupModuleRightRelationVO );
   }

   @Override
   public int insertGroupModuleRightRelation( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return insert( "insertGroupModuleRightRelation", groupModuleRightRelationVO );
   }

   @Override
   public int deleteGroupModuleRightRelationByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException
   {
      return delete( "deleteGroupModuleRightRelationByCondition", groupModuleRightRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRightRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return selectList( "getGroupModuleRightRelationVOsByGroupId", groupId );
   }

}
