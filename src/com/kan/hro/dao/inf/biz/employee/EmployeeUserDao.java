package com.kan.hro.dao.inf.biz.employee;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;

public interface EmployeeUserDao
{
   public abstract EmployeeUserVO getEmployeeUserVOByCondition ( final EmployeeUserVO employeeUserVO ) throws KANException;

   public abstract int updateEmployeeUser ( final EmployeeUserVO employeeUserVO  ) throws KANException;
   
   public abstract boolean checkUsernameExist(final EmployeeUserVO employeeUserVO ) throws KANException;
   
   public abstract int insertEmployeeUser(final EmployeeUserVO employeeUserVO ) throws KANException;
}
