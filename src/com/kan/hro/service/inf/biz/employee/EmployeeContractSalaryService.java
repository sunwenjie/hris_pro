package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;

public interface EmployeeContractSalaryService
{
   public abstract PagedListHolder getEmployeeContractSalaryVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSalaryVO getEmployeeContractSalaryVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;

   public abstract int insertEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int updateEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int deleteEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByContractId( final String contractId ) throws KANException;

   public abstract int insertEmployeeContractSalaryPopup( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException;

   public abstract boolean hasConflictContractSalaryInOneItem( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException, ParseException;

   public abstract int submitEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public void generateHistoryVOForWorkflow( final BaseVO baseVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByContractIdAndItemId( final Map< String, Object > parameters ) throws KANException;

}
