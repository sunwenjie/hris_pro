package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;

public interface EmployeeSalaryAdjustmentBatchDao
{
   public abstract int countEmployeeSalaryAdjustmentBatchVOsByCondition( final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;

   public abstract List< Object > getEmployeeSalaryAdjustmentBatchVOsByCondition( final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;

   public abstract List< Object > getEmployeeSalaryAdjustmentBatchVOsByCondition( final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO, final RowBounds rowBounds )
         throws KANException;

   public abstract int updateEmployeeSalaryAdjustmentBatch( final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;

   public abstract void deleteEmployeeSalaryAdjustmentBatch( final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;

   public abstract EmployeeSalaryAdjustmentBatchVO getEmployeeSalaryAdjustmentBatchVOByBatchId( final String batchId ) throws KANException;

}
