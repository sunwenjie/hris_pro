package com.kan.hro.service.inf.biz.employee;

import java.util.Locale;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface EmployeeSalaryAdjustmentTempService
{
   public void getEmployeeSalaryAdjustmentTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public int submitEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( final String[] salaryAdjustmentIds, final String userId, final String ip, final Locale locale )
         throws KANException;

   public int rollbackEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentIds( final String[] salaryAdjustmentIds, final String userId ) throws KANException;
}
