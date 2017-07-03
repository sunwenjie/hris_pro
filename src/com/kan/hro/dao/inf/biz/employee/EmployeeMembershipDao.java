package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;

public interface EmployeeMembershipDao
{
   public abstract int countEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;

   public abstract List< Object > getEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;

   public abstract List< Object > getEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeMembershipVO getEmployeeMembershipVOByEmployeeMembershipId( final String employeeMembershipId ) throws KANException;

   public abstract int insertEmployeeMembership ( final EmployeeMembershipVO employeeMembershipVO) throws KANException;

   public abstract int updateEmployeeMembership ( final EmployeeMembershipVO employeeMembershipVO ) throws KANException;

   public abstract int deleteEmployeeMembership ( final String employeeMembershipId  ) throws KANException;
   
   public abstract List< Object > getEmployeeMembershipVOsByEmployeeId( final String employeeId ) throws KANException;
   
}
