package com.kan.hro.service.inf.biz.employee;

import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeChangeReportVO;

public interface EmployeeChangeReportService
{
   public abstract PagedListHolder getEmployeeChangeReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeChangeReportVO generateEmployeeChangeReportVO_forAdd( final LogVO logVO );
}
