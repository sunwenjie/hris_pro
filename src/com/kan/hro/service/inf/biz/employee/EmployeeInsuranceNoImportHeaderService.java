package com.kan.hro.service.inf.biz.employee;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;

public interface EmployeeInsuranceNoImportHeaderService
{
   public abstract PagedListHolder getEmployeeInsuranceNoImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;
   
   public abstract EmployeeInsuranceNoImportHeaderVO getEmployeeInsuranceNoImportHeaderVOsById( final String headerId,final String accountId )throws KANException;
}
