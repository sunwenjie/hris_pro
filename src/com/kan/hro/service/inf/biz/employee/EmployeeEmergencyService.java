package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;

public interface EmployeeEmergencyService
{
   public abstract PagedListHolder getEmployeeEmergencyVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeEmergencyVO getEmployeeEmergencyVOByEmployeeEmergencyId( final String employeeEmergencyId ) throws KANException;

   public abstract int insertEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;

   public abstract int updateEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;

   public abstract int deleteEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException;
   
   public abstract List< Object > getEmployeeEmergencyVOsByEmployeeId( final String employeeId ) throws KANException;
}
