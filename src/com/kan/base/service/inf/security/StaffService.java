package com.kan.base.service.inf.security;

import java.util.List;

import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.StaffBaseView;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public interface StaffService
{
   public abstract PagedListHolder getStaffVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract StaffVO getStaffVOByStaffId( final String staffId ) throws KANException;

   public abstract int updateStaff( final StaffVO staffVO ) throws KANException;

   public abstract int insertStaff( final StaffVO staffVO ) throws KANException;

   public abstract int deleteStaff( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > logon( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > getStaffVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getPositionStaffRelationVOsByPositionId( final String positionId ) throws KANException;

   public abstract List< Object > getPositionStaffRelationVOsByPositionStaffRelationVO( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException;

   public abstract List< Object > getStaffBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getActiveStaffBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract StaffDTO getStaffDTOByStaffId( final String staffId ) throws KANException;

   public abstract List< StaffDTO > getStaffDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getActiveStaffVOsByCorpId( final StaffVO staffVO ) throws KANException;

   public abstract List< Object > getActiveStaffVOsByAccountId( final String accountId ) throws KANException;

   public abstract StaffVO getStaffVOByEmployeeId( final String employeeId ) throws KANException;

   public abstract int insertStaffByEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getStaffVOsByIds( final String accountId, final String[] userIds, final String[] positionIds, final String[] groupIds, final String[] branchIds,
         final String[] positionGradeIds ) throws KANException;

   public abstract PagedListHolder getStaffVOsByBranchId( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getStaffVOsByBranchId( final String branchId, final PagedListHolder pagedListHolder, final boolean isPaged, final String isSumSubBranchHC,
         final String accountId ) throws KANException;

   public abstract StaffBaseView getStaffBaseViewByStaffId( final String staffId ) throws KANException;

   public abstract int updateBaseStaff( final StaffVO staffVO ) throws KANException;
   
   public abstract void updateStaffModuleDTO ( final String accountId, final String staffId, final String reportHeaderId ,final List<ModuleDTO> moduleDTOs) throws KANException;

}
