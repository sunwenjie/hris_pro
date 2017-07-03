package com.kan.hro.service.inf.biz.employee;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;


public interface EmployeeUserService
{
   public abstract EmployeeUserVO login( final EmployeeUserVO employeeUserVO ) throws KANException;
   
   public abstract int updateEmployeeUser( final EmployeeUserVO employeeUserVO ) throws KANException;
   
   public abstract EmployeeUserVO getEmployeeUserById( final String employeeUserId ) throws KANException;
   
   public abstract EmployeeUserVO getEmployeeUserByEmployeeId( final String employeeId ) throws KANException;
   
   public abstract boolean checkUsernameExist( final EmployeeUserVO employeeUserVO ) throws KANException;
   
}
