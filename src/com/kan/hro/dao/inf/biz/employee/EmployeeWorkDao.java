package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;

public interface EmployeeWorkDao
{
   public abstract int countEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO ) throws KANException;

   public abstract List< Object > getEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO ) throws KANException;

   public abstract List< Object > getEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeWorkVO getEmployeeWorkVOByEmployeeWorkId( final String employeeWorkId ) throws KANException;

   public abstract int insertEmployeeWork ( final EmployeeWorkVO employeeWorkVO) throws KANException;

   public abstract int updateEmployeeWork ( final EmployeeWorkVO employeeWorkVO ) throws KANException;

   public abstract int deleteEmployeeWork ( final String employeeWorkId  ) throws KANException;
   
   public abstract List< Object > getEmployeeWorkVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getCompanyNameByName(final String name )throws KANException;
   
}
