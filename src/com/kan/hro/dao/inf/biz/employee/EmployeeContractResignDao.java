package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;

public interface EmployeeContractResignDao
{
   public abstract int countEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract List< Object > getEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract List< Object > getEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractResignVO getEmployeeContractResignVOByEmployeeContractResignId( final String employeeContractResignId ) throws KANException;

   public abstract int updateEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract int insertEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract int deleteEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract void deleteHeaderTempRecord( final String[] ids ) throws KANException;

   public abstract int getHeaderCountByBatchId( final String batchId ) throws KANException;

}
