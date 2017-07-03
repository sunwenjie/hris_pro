package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;

public interface EmployeeContractSBTempService
{
   public abstract PagedListHolder getEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSBTempVO getEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract PagedListHolder getVendorEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int insertEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int updateEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int submitEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract int deleteEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBTempVOsByContractId( final String contractId ) throws KANException;

   public abstract List< EmployeeContractSBDTO > getEmployeeContractSBTempDTOsByContractId( final String contractId ) throws KANException;

   public abstract PagedListHolder getFullEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public EmployeeContractSBTempVO getFullEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract String modifyEmployeeContractSBTempVO( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException;

   public abstract String modifyEmployeeContractSBTempVOs( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException;

}
