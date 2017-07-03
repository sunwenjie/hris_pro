package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;

public interface EmployeeContractImportBatchDao
{
   public abstract int countEmployeeContractImportBatchVOsByCondition( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

   public abstract List< Object > getEmployeeContractImportBatchVOsByCondition( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

   public abstract List< Object > getEmployeeContractImportBatchVOsByCondition( final EmployeeContractImportBatchVO EmployeeContractImportBatchVO, final RowBounds rowBounds )
         throws KANException;

   public abstract EmployeeContractImportBatchVO getEmployeeContractImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertEmployeeContractImportBatch( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

   public abstract int updateEmployeeContractImportBatch( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

   public abstract int deleteEmployeeContractImportBatch( final String employeeContractBatchId ) throws KANException;

   public abstract int deleteEmployeeContractSalaryByBatchId( final String employeeContractBatchId ) throws KANException;

   public abstract int deleteEmployeeContractSalaryByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractSBByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractSBByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractCBByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractCBByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractLeaveByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractLeaveByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractOTByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractOTByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractOtherByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractOtherByContractId( final String employeeContractId ) throws KANException;

   public abstract int deleteEmployeeContractByBatchId( final String employeeContractBatchId ) throws KANException;
   
   public abstract int deleteEmployeeContractByContractId( final String employeeContractId ) throws KANException;

}
