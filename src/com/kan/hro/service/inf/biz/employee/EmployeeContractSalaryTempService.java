package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;

public interface EmployeeContractSalaryTempService
{
   public abstract PagedListHolder getEmployeeContractSalaryTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSalaryVO getEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;

   public abstract PagedListHolder getFullEmployeeContractSalaryTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSalaryVO getFullEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;

   public abstract int insertEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int updateEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int deleteEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryTempVOsByContractId( final String contractId ) throws KANException;

   public abstract boolean hasConflictContractSalaryTempInOneItem( EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException;
}
