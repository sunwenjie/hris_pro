package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeBatchVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;

public interface EmployeePositionChangeBatchService
{
   public abstract PagedListHolder getEmployeePositionChangeBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int insertEmployeePositionChangeBatch( final CommonBatchVO commonBatchVO, final List< EmployeePositionChangeTempVO > listTempVO ) throws KANException;

   public abstract EmployeePositionChangeBatchVO getEmployeePositionChangeBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int submitEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;

   public abstract int rollbackEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;
}
