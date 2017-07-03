package com.kan.hro.service.inf.biz.employee;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public interface EmployeeContractTempService
{
   public abstract PagedListHolder getEmployeeContractTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractVO getEmployeeContractTempVOByContractId( final String contractId ) throws KANException;

   public abstract int updateBatch( final EmployeeContractImportBatchVO submitObject ) throws KANException;

   public abstract int rollbackBatch( final EmployeeContractImportBatchVO submitObject ) throws KANException;

   public abstract int rollbackByTempContractIds( final String tempContractIds[] ) throws KANException;

   public abstract int updateByTempContractIds( final String tempContractIds[], final String ip ) throws KANException;

   public abstract int updateEmployeeContractClientIdFromOrderIdByBatchId( final String batchId ) throws KANException;

}
