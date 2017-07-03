package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public interface EmployeeContractCBTempDao
{

   public abstract int countEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractCBVO getEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract int insertEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int updateEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int deleteEmployeeContractCBTemp( final String employeeCBId ) throws KANException;

   public abstract List< Object > getEmployeeContractCBTempVOsByContractId( final String contractId ) throws KANException;

   public abstract int countFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractCBVO getFullEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public List< Object > getFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

}
