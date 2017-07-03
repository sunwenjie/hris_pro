package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractTempVO;

public interface EmployeeContractTempDao
{

   public abstract int countEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO ) throws KANException;

   public abstract List< Object > getEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO ) throws KANException;

   public abstract List< Object > getEmployeeContractTempVOsByBatchId( final String batchId ) throws KANException;

   public abstract List< Object > getEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractTempVO getEmployeeContractTempVOByContractId( final String contractId ) throws KANException;

   public abstract int updateEmployeeContractTemp( final EmployeeContractTempVO employeeContractTempVO ) throws KANException;

   public abstract int insertEmployeeContractTemp( final EmployeeContractTempVO employeeContractTempVO ) throws KANException;

   public abstract int deleteEmployeeContractTemp( final String contractId ) throws KANException;

   public abstract int updateEmployeeContractClientIdFromOrderIdByBatchId( final String batchId ) throws KANException;

}
