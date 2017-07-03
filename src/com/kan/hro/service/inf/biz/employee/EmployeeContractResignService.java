package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractResignDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;

public interface EmployeeContractResignService
{
   public abstract PagedListHolder getEmployeeContractResignVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public List< Object > getEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract EmployeeContractResignVO getEmployeeContractResignVOByEmployeeContractResignId( final String employeeContractResignId ) throws KANException;

   public abstract int updateEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract int insertEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract int deleteEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException;

   public abstract void submitEmployeeContractResignDTO( final EmployeeContractResignDTO employeeContractResignDTO ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;

}
