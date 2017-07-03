package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;

public interface EmployeeLogDao
{
   public abstract int countEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO ) throws KANException;

   public abstract List< Object > getEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO ) throws KANException;

   public abstract List< Object > getEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeLogVO getEmployeeLogVOByEmployeeLogId( final String employeeLogId ) throws KANException;

   public abstract int insertEmployeeLog ( final EmployeeLogVO employeeLogVO) throws KANException;

   public abstract int updateEmployeeLog ( final EmployeeLogVO employeeLogVO ) throws KANException;

   public abstract int deleteEmployeeLog ( final String employeeLogId  ) throws KANException;
   
   public abstract List< Object > getEmployeeLogVOsByEmployeeId( final String employeeId ) throws KANException;

}
