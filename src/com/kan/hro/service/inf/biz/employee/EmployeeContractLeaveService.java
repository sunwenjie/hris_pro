package com.kan.hro.service.inf.biz.employee;

import java.util.List;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public interface EmployeeContractLeaveService
{
   public abstract PagedListHolder getEmployeeContractLeaveVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractLeaveVO getEmployeeContractLeaveVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException;

   public abstract int insertEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int updateEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int deleteEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException;

   public abstract PagedListHolder getEmployeeContractLeaveReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract XSSFWorkbook exportEmployeeContractLeaveReport( final List< MappingVO > titleMappingVOs, final PagedListHolder pagedListHolder, final String lang )
         throws KANException;

   public abstract PagedListHolder getAnnualLeaveDetailsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract XSSFWorkbook exportAnnualLeaveDetails( final List< MappingVO > titleMappingVOs, final PagedListHolder pagedListHolder, final Locale locale ) throws KANException;

}
