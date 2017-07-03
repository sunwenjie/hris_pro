package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;


public interface EmployeeContractOtherService
{
   
	  public abstract PagedListHolder getEmployeeContractOtherVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

	   public abstract EmployeeContractOtherVO getEmployeeContractOtherVOByEmployeeOtherId( final String employeeOtherId ) throws KANException;

	   public abstract int insertEmployeeContractOther( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

	   public abstract int updateEmployeeContractOther( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

	   public abstract int deleteEmployeeContractOther( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;
	   
	   public abstract List< Object > getEmployeeContractOtherVOsByContractId( final String contractId ) throws KANException;

}
