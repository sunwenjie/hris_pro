package com.kan.hro.service.inf.biz.employee;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;

public interface EmployeeInsuranceNoImportBatchService
{

   public PagedListHolder getEmployeeInsuranceNoImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public EmployeeInsuranceNoImportBatchVO getEmployeeInsuranceNoImportBatchById( final String batchId ) throws KANException;

   public int updateEmployeeInsuranceNoImportBatch( final EmployeeInsuranceNoImportBatchVO submitObject ) throws KANException;

   public int backBatch( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException;
}
