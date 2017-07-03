package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public interface EmployeeContractLeaveTempDao
{
   public abstract int countEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractLeaveVO getEmployeeContractLeaveTempVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException;

   public abstract int insertEmployeeContractLeaveTemp ( final EmployeeContractLeaveVO employeeContractLeaveVO) throws KANException;

   public abstract int updateEmployeeContractLeaveTemp ( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int deleteEmployeeContractLeaveTemp ( final String employeeLeaveId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractLeaveTempVOsByContractId( final String contractId ) throws KANException;
   
}
