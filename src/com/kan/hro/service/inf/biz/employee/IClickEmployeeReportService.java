package com.kan.hro.service.inf.biz.employee;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface IClickEmployeeReportService
{
   public abstract PagedListHolder getFullEmployeeReportViewsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getFullEmployeeReportViewsByCondition_r4( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract XSSFWorkbook generatePerformanceReport( final PagedListHolder pagedListHolder ) throws KANException;
}
