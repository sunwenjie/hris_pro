package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;

public interface EmployeeContractSBTempDao
{
   public abstract int countEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBTempVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSBTempVO getEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract int insertEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int updateEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int deleteEmployeeContractSBTemp( final String employeeSBId ) throws KANException;

   public abstract List< Object > getEmployeeContractSBTempVOsByContractId( final String contractId ) throws KANException;

   public abstract int countFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSBTempVO getFullEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public List< Object > getFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int countVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

}
