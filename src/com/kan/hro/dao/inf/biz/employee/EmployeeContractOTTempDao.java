package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public interface EmployeeContractOTTempDao
{
   public abstract int countEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractOTVO getEmployeeContractOTTempVOByEmployeeOTId( final String employeeOTId ) throws KANException;

   public abstract int insertEmployeeContractOTTemp ( final EmployeeContractOTVO employeeContractOTVO) throws KANException;

   public abstract int updateEmployeeContractOTTemp ( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int deleteEmployeeContractOTTemp ( final String employeeOTId  ) throws KANException;
   
   public abstract List< Object > getEmployeeContractOTTempVOsByContractId( final String contractId ) throws KANException;
   
}
