package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public interface EmployeeContractCBTempService
{
   public abstract PagedListHolder getEmployeeContractCBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractCBVO getEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract int insertEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int updateEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int deleteEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBTempVOsByContractId( final String contractId ) throws KANException;

   public abstract PagedListHolder getFullEmployeeContractCBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public EmployeeContractCBVO getFullEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract String modifyEmployeeContractCBTempVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException;

   public abstract String modifyEmployeeContractCBTempVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException;
}
