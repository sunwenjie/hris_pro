package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.util.KANException;

public interface GroupModuleRightRelationDao
{
   public abstract int countGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO )
         throws KANException;

   public abstract List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO,
         RowBounds rowBounds ) throws KANException;

   public abstract GroupModuleRightRelationVO getGroupModuleRightRelationVOByRelationId( final String groupModuleRightRelationId )
         throws KANException;

   public abstract int insertGroupModuleRightRelation( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException;

   public abstract int updateGroupModuleRightRelation( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException;

   public abstract int deleteGroupModuleRightRelationByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getGroupModuleRightRelationVOsByGroupId( final String groupId ) throws KANException;

}
