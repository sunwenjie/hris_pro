package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;

public interface EmployeeContractPropertyService
{
   public abstract PagedListHolder getEmployeeContractPropertyVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractPropertyVO getEmployeeContractPropertyVOByEmployeeContractPropertyId( final String employeeContractPropertyId ) throws KANException;

   public abstract int updateEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract int insertEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract int deleteEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException;

   public abstract List< Object > getEmployeeContractPropertyVOsByContractId( final String contractId ) throws KANException;

}
