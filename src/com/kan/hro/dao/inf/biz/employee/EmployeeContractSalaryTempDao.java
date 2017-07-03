package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;

public interface EmployeeContractSalaryTempDao
{
   public abstract int countEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSalaryVO getEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;
   
   public abstract int countFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException;
   
   public abstract List< Object > getFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException;
   
   public abstract List< Object > getFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO, RowBounds rowBounds ) throws KANException;
   
   public abstract EmployeeContractSalaryVO getFullEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException;

   public abstract int insertEmployeeContractSalaryTemp ( final EmployeeContractSalaryVO employeeContractSalaryTempVO) throws KANException;

   public abstract int updateEmployeeContractSalaryTemp ( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException;

   public abstract int deleteEmployeeContractSalaryTemp ( final String employeeSalaryId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractSalaryTempVOsByContractId( final String contractId ) throws KANException;
   
}
