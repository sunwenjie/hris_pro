package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EmployeeStatusService
{
   public abstract PagedListHolder getEmployeeStatusVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeStatusVO getEmployeeStatusVOByEmployeeStatusId( final String employeeStatusId ) throws KANException;

   public abstract int insertEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract int updateEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract void deleteEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract List< Object > getEmployeeStatusVOsByAccountId( final String accountId ) throws KANException;
}
