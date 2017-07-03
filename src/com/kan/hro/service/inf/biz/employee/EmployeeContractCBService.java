package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public interface EmployeeContractCBService
{
   public abstract PagedListHolder getEmployeeContractCBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractCBVO getEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract int insertEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int updateEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int deleteEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBVOsByContractId( final String contractId ) throws KANException;

   public abstract int submitEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract PagedListHolder getFullEmployeeContractCBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public EmployeeContractCBVO getFullEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException;

   public abstract String modifyEmployeeContractCBVO( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException;

   public abstract String modifyEmployeeContractCBVOs( final EmployeeContractCBVO employeeContractCBVO ) throws KANException, ParseException;
   
   public abstract int submitEmployeeContractCB_rollback( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;

   public abstract int submitEmployeeContractCB_rollback_nt( final EmployeeContractCBVO tempEmployeeContractCBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException;
}
