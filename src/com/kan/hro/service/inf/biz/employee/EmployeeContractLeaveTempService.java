package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public interface EmployeeContractLeaveTempService
{
   public abstract PagedListHolder getEmployeeContractLeaveTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractLeaveVO getEmployeeContractLeaveTempVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException;

   public abstract int insertEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int updateEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int deleteEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveTempVOsByContractId( final String contractId ) throws KANException;

}
