package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;

public interface EmployeeContractSettlementService
{

   public abstract PagedListHolder getEmployeeContractSettlementVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSettlementVO getEmployeeContractSettlementVOByEmployeeSettlementId( final String employeeSettlementId ) throws KANException;

   public abstract int insertEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract int updateEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract int deleteEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSettlementVOsByContractId( final String contractId ) throws KANException;

}
