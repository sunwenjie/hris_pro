package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;

public interface EmployeeContractSBDetailService
{
   public abstract PagedListHolder getEmployeeContractSBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractSBDetailVO getEmployeeContractSBDetailVOByEmployeeSBDetailId( final String employeeSBDetailId ) throws KANException;

   public abstract int insertEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;

   public abstract int updateEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;

   public abstract int deleteEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException;
   
   public abstract List< Object > getEmployeeContractSBDetailVOsByEmployeeSBId( final String employeeSBId ) throws KANException;
}
