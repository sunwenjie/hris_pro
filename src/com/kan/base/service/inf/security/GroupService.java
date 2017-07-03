package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.GroupDTO;
import com.kan.base.domain.security.GroupModuleRightRelationVO;
import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.domain.security.GroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface GroupService
{
   public abstract PagedListHolder getGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract GroupVO getGroupVOByGroupId( final String groupId ) throws KANException;

   public abstract int countPositionGroupRelationVOsByGroupId( final String groupId ) throws KANException;

   public abstract int updateGroup( final GroupVO groupVO ) throws KANException;

   public abstract int updateGroupModule( final GroupVO groupVO ) throws KANException;

   public abstract int insertGroup( final GroupVO groupVO ) throws KANException;
   
   public abstract int updateGroupModuleRelationPopup( final GroupVO groupVO, final String moduleId ) throws KANException;

   public abstract int deleteGroup( final GroupVO groupVO ) throws KANException;

   public abstract List< Object > getGroupVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getRelationVOsByPositionId( final String positionId ) throws KANException;

   public abstract List< GroupDTO > getGroupDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getGroupModuleRightRelationVOsByCondition( final GroupModuleRightRelationVO groupModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getGroupBaseViewsByAccountId( final String accountId ) throws KANException;
}
