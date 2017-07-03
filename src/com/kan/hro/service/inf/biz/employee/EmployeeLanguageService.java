package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;

public interface EmployeeLanguageService
{
   public abstract PagedListHolder getEmployeeLanguageVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeLanguageVO getEmployeeLanguageVOByEmployeeLanguageId( final String employeeLanguageId ) throws KANException;

   public abstract int insertEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;

   public abstract int updateEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;

   public abstract int deleteEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException;
   
   public abstract List< Object > getEmployeeLanguageVOsByEmployeeId( final String employeeId ) throws KANException;
}
