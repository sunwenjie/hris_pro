package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public interface EmployeeContractOTTempService
{
   public abstract PagedListHolder getEmployeeContractOTTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractOTVO getEmployeeContractOTTempVOByEmployeeOTId( final String employeeOTId ) throws KANException;

   public abstract int insertEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int updateEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int deleteEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOTTempVOsByContractId( final String contractId ) throws KANException;
}
