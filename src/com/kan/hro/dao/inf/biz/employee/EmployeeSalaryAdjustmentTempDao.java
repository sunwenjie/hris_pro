package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;

public interface EmployeeSalaryAdjustmentTempDao
{
   public int countEmployeeSalaryAdjustmentTempVOsByCondition( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException;

   public abstract List< Object > getEmployeeSalaryAdjustmentTempVOsByCondition( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO, RowBounds rowBounds )
         throws KANException;

   public abstract List< Object > getEmployeeSalaryAdjustmentTempVOsByCondition( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException;

   public abstract int insertEmployeeSalaryAdjustmentTemp( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException;

   public abstract EmployeeSalaryAdjustmentTempVO getEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentId( String salaryAdjustmentId ) throws KANException;

   public abstract void updateEmployeeSalaryAdjustmentTemp( final EmployeeSalaryAdjustmentTempVO EmployeeSalaryAemployeeSalaryAdjustmentTempVOdjustmentTempVO ) throws KANException;

   public abstract void deleteEmployeeSalaryAdjustmentTemp( final EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException;

   public abstract List< Object > getEmployeeSalaryAdjustmentTempVOsByBatchId( final String batchId ) throws KANException;

}