package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.GroupModuleRuleRelationDao;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.util.KANException;

public class GroupModuleRuleRelationDaoImpl extends Context implements GroupModuleRuleRelationDao
{

   @Override
   public int countGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return ( Integer ) select( "countGroupModuleRuleRelationVOsByCondition", groupModuleRuleRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return selectList( "getGroupModuleRuleRelationVOsByCondition", groupModuleRuleRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getGroupModuleRuleRelationVOsByCondition", groupModuleRuleRelationVO, rowBounds );
   }

   @Override
   public GroupModuleRuleRelationVO getGroupModuleRuleRelationVOByRelationId( final String relationId ) throws KANException
   {
      return ( GroupModuleRuleRelationVO ) select( "getGroupModuleRuleRelationVOByRelationId", relationId );
   }

   @Override
   public int updateGroupModuleRuleRelation( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return update( "updateGroupModuleRuleRelation", groupModuleRuleRelationVO );
   }

   @Override
   public int insertGroupModuleRuleRelation( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return insert( "insertGroupModuleRuleRelation", groupModuleRuleRelationVO );
   }

   @Override
   public int deleteGroupModuleRuleRelationByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return delete( "deleteGroupModuleRuleRelationByCondition", groupModuleRuleRelationVO );
   }

   @Override
   public List< Object > getGroupModuleRuleRelationVOsByGroupId( final String groupId ) throws KANException
   {
      return selectList( "getGroupModuleRuleRelationVOsByGroupId", groupId );
   }

   @Override
   public int deleteGroupModuleRuleRelationByGroupId( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException
   {
      return delete( "deleteGroupModuleRuleRelationByGroupId", groupModuleRuleRelationVO );
   }
}
