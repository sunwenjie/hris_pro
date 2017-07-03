package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;

public interface EmployeeSalaryAdjustmentBatchService
{
   public abstract PagedListHolder getEmployeeSalaryAdjustmentBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int insertEmployeeSalaryAdjustmentBatch( final CommonBatchVO commonBatchVO, final List< EmployeeSalaryAdjustmentTempVO > listTempVO ) throws KANException;

   public abstract EmployeeSalaryAdjustmentBatchVO getEmployeeSalaryAdjustmentBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int submitEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;

   public abstract int rollbackEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException;
}
