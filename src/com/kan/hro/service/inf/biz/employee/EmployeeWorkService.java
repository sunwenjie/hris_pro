package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;

public interface EmployeeWorkService
{
   public abstract PagedListHolder getEmployeeWorkVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeWorkVO getEmployeeWorkVOByEmployeeWorkId( final String employeeWorkId ) throws KANException;

   public abstract int insertEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException;

   public abstract int updateEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException;

   public abstract int deleteEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException;
   
   public abstract List< Object > getEmployeeWorkVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getCompanyNameByName( final String name )throws KANException;
}
