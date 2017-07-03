package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;

public interface EmployeeEducationService
{
   public abstract PagedListHolder getEmployeeEducationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeEducationVO getEmployeeEducationVOByEmployeeEducationId( final String employeeEducationId ) throws KANException;

   public abstract int insertEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException;

   public abstract int updateEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException;

   public abstract int deleteEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException;
   
   public abstract List< Object > getEmployeeEducationVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getSchoolNameBySchoolName(final String q )throws KANException;

   public abstract List< Object > getMajorByMajor(final String q )throws KANException;
}
