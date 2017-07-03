package com.kan.hro.dao.inf.biz.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;

public interface EmployeeContractSalaryDao
{
   public abstract int countEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSalaryVO getEmployeeContractSalaryVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;

   public abstract int insertEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int updateEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int deleteEmployeeContractSalary( final String employeeSalaryId ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByContractId( final String contractId ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryVOsByContractIds( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;

   public abstract int updateEmployeeContractSalaryStatus( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException;
   
   public abstract List< Object > getEmployeeContractSalaryVOsByContractIdAndItemId( final Map< String, Object > parameters ) throws KANException;

}
