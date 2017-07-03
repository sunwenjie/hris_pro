package com.kan.hro.service.inf.biz.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;

public interface EmployeeReportService
{
   public abstract PagedListHolder getEmployeeReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getEmployeeSalaryReportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged, List< MappingVO > itemVOs ) throws KANException;

   public abstract XSSFWorkbook EmployeeSalaryReport( PagedListHolder employeeReportHolder, EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract PagedListHolder getEmployeePerformanceReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract XSSFWorkbook generatePerformanceReport( final PagedListHolder pagedListHolder ) throws KANException;

   public final Map< String, String > FORMULA_MAP = new HashMap< String, String >();

   public abstract PagedListHolder getContactsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
}
