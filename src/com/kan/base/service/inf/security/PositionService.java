package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionModuleRightRelationVO;
import com.kan.base.domain.security.PositionModuleRuleRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface PositionService
{

   public abstract PagedListHolder getPositionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PositionVO getPositionVOByPositionId( final String positionId ) throws KANException;

   public abstract int updatePosition( final PositionVO positionVO ) throws KANException;

   public abstract int updatePositionModule( final PositionVO positionVO ) throws KANException;

   public abstract int updatePositionModuleRelationPopup( final PositionVO positionVO, final String moduleId ) throws KANException;

   public abstract int insertPosition( final PositionVO positionVO ) throws KANException;

   public abstract int deletePosition( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getRelationVOsByGroupId( final String groupId ) throws KANException;

   public abstract List< Object > getRelationVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getRelationVOsByStaffId( final String staffId ) throws KANException;

   public abstract List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< PositionDTO > getPositionDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getPositionModuleRightRelationVOsByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract List< PositionVO > getPositionVOsByPositionVO( final PositionVO positionVO ) throws KANException;

   public abstract List< Object > getPositionVOsByEmployeeId( final StaffVO staffVO ) throws KANException;

   public abstract int deletePositionStaffRelationByPositionId( final String positionId ) throws KANException;
   
   public abstract int insertPositionStaffRelation( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;
   
   public abstract List< Object > getPositionStaffRelationVOsByStaffId( final String staffId ) throws KANException;

}
