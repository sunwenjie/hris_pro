package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public interface LeaveHeaderService
{
   public abstract PagedListHolder getLeaveHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LeaveHeaderVO getLeaveHeaderVOByLeaveHeaderId( final String leaveHeaderId ) throws KANException;

   public abstract int insertLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int updateLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int submitLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int submitLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int deleteLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int deleteLeaveHeader_cleanTS( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract List< LeaveHeaderVO > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int sickLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int count_leaveUnread( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int read_Leave( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   // TODO Remove
   public abstract List< EmployeeContractLeaveVO > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException;

   public abstract List< Object > exportLeaveDetailByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int sick_leave( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int sick_leave_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException;
}
