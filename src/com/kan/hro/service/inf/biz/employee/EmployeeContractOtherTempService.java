package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;

public interface EmployeeContractOtherTempService
{

   public abstract PagedListHolder getEmployeeContractOtherTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractOtherVO getEmployeeContractOtherTempVOByEmployeeOtherId( final String employeeOtherId ) throws KANException;

   public abstract int insertEmployeeContractOtherTemp( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract int updateEmployeeContractOtherTemp( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract int deleteEmployeeContractOtherTemp( final EmployeeContractOtherVO employeeContractOtherVO ) throws KANException;

   public abstract List< Object > getEmployeeContractOtherTempVOsByContractId( final String contractId ) throws KANException;

}
