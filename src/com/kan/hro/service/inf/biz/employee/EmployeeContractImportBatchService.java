package com.kan.hro.service.inf.biz.employee;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;

public interface EmployeeContractImportBatchService
{

   public abstract PagedListHolder getEmployeeContractImportBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractImportBatchVO getEmployeeContractImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateEmployeeContractImportBatch( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

   public abstract int insertEmployeeContractImportBatch( final EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException;

}
