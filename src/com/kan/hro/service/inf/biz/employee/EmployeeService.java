package com.kan.hro.service.inf.biz.employee;

import java.util.List;
import java.util.Map;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeBaseView;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public interface EmployeeService
{
   public abstract PagedListHolder getEmployeeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeVO getEmployeeVOByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > employeeByLogon( final EmployeeVO employeeVO ) throws KANException;

   public abstract int insertEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract int updateEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract int deleteEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEmployeeBaseViewsByCondition( final EmployeeBaseView employeeBaseView ) throws KANException;

   public abstract List< Object > getEmployeeVOsByPositionId( final String positionId ) throws KANException;

   public int insertPositionStaffRelation( final StaffVO staffVO, final EmployeeVO employeeVO ) throws KANException;

   public abstract int updateBaseEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeNosByEmployeeNoList( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeVOsByAbsEquEmpNo( final String employeeNo ) throws KANException;

   public abstract int quickCreateEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract int addPositionForEmployee( final EmployeeVO employeeVO ) throws KANException;

   public abstract boolean emailIsRegister( final Map< String, Object > parameters ) throws KANException;

   public abstract List< Object > getEmployeeVOsByCondition( final EmployeeVO employeeVO ) throws KANException;

}
