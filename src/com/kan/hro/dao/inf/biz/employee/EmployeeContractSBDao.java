package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;

public interface EmployeeContractSBDao
{
   public abstract int countEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSBVO getEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract int insertEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int updateEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int deleteEmployeeContractSB( final String employeeSBId ) throws KANException;

   public abstract List< Object > getEmployeeContractSBVOsByContractId( final String contractId ) throws KANException;

   public abstract int countFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractSBVO getFullEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public List< Object > getFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int countVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

}
