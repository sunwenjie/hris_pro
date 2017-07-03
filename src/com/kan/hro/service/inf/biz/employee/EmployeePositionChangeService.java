package com.kan.hro.service.inf.biz.employee;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;

public interface EmployeePositionChangeService
{
   public void getPositionChangeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public void insertEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public EmployeePositionChangeVO getEmployeePositionChangeVOByPositionChangeId( final String positionChangeId ) throws KANException;

   public void updateEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void deleteEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void synchronizedEmployeePosition() throws KANException;

   public void submitEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException;

   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException;

   public int getEffectivePositionChangeVOCountByEmployeeId( final EmployeePositionChangeVO employeePositionChangeVO )throws KANException;
}
