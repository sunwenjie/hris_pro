package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;

public interface EmployeeAddSubtractDao
{
   public abstract int countEmployeeAddSubtractsByCondition( final EmployeeAddSubtract employeeAddSubtract ) throws KANException;

   public abstract List< Object > getEmployeeAddSubtractsByCondition( final EmployeeAddSubtract employeeAddSubtract ) throws KANException;

   public abstract List< Object > getEmployeeAddSubtractsByCondition( final EmployeeAddSubtract employeeAddSubtract, RowBounds rowBounds ) throws KANException;

}
