package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;

public interface EmployeeLogService
{
   public abstract PagedListHolder getEmployeeLogVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeLogVO getEmployeeLogVOByEmployeeLogId( final String employeeLogId ) throws KANException;

   public abstract int insertEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException;

   public abstract int updateEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException;

   public abstract int deleteEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException;
   
   public abstract List< Object > getEmployeeLogVOsByEmployeeId( final String employeeId ) throws KANException;

}
