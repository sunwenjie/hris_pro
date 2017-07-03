package com.kan.hro.service.inf.biz.employee;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EmployeeAddSubtractService
{
   public abstract PagedListHolder getEmployeeAddSubtractsByCondition(final PagedListHolder pagedListHolder, final boolean isPaged) throws KANException;

   public abstract SXSSFWorkbook employeeAddSubtractReport( PagedListHolder employeeAddSubtractHolder );
}
