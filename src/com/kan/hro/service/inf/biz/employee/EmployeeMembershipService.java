package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;

public interface EmployeeMembershipService
{
   public abstract PagedListHolder getEmployeeMembershipVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeMembershipVO getEmployeeMembershipVOByEmployeeMembershipId( final String employeeMembershipId ) throws KANException;

   public abstract int insertEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;

   public abstract int updateEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;

   public abstract int deleteEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;
   
   public abstract List< Object > getEmployeeMembershipVOsByEmployeeId( final String employeeId ) throws KANException;
}
