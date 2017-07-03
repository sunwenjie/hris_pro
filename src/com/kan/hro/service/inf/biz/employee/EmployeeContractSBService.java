package com.kan.hro.service.inf.biz.employee;

import java.text.ParseException;
import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public interface EmployeeContractSBService
{
   public abstract PagedListHolder getEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSBVO getEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract PagedListHolder getVendorEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int insertEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int updateEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int submitEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract int deleteEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBVOsByContractId( final String contractId ) throws KANException;

   public abstract List< EmployeeContractSBDTO > getEmployeeContractSBDTOsByContractId( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract PagedListHolder getFullEmployeeContractSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public EmployeeContractSBVO getFullEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException;

   public abstract String modifyEmployeeContractSBVO( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException;

   public abstract String modifyEmployeeContractSBVOs( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException;

   public abstract int submitEmployeeContractSB_rollback( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   int submitEmployeeContractSB_rollback_nt( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException;

}
