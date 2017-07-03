package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.util.KANException;

public interface EmployeeStatusDao
{
   public abstract int countEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract List< Object > getEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract List< Object > getEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeStatusVO getEmployeeStatusVOByEmployeeStatusId( final String employeeStatusId ) throws KANException;

   public abstract int insertEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract int updateEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;

   public abstract int deleteEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException;
   
   public abstract List< Object > getEmployeeStatusVOsByAccountId( final String accountId ) throws KANException;
}
