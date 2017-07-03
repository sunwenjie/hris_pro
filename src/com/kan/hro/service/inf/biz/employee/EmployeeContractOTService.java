package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public interface EmployeeContractOTService
{
   public abstract PagedListHolder getEmployeeContractOTVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractOTVO getEmployeeContractOTVOByEmployeeOTId( final String employeeOTId ) throws KANException;

   public abstract int insertEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int updateEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;

   public abstract int deleteEmployeeContractOT( final EmployeeContractOTVO employeeContractOTVO ) throws KANException;
   
   public abstract List< Object > getEmployeeContractOTVOsByContractId( final String contractId ) throws KANException;
}
