package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;

public interface EmployeePositionChangeTempDao
{
   public int countEmployeePositionChangeTempVOsByCondition( final EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException;

   public abstract List< Object > getEmployeePositionChangeTempVOsByCondition( final EmployeePositionChangeTempVO employeePositionChangeTempVO, RowBounds rowBounds )
         throws KANException;

   public abstract List< Object > getEmployeePositionChangeTempVOsByCondition( final EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException;

   public abstract int insertEmployeePositionChangeTemp( final EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException;

   public abstract EmployeePositionChangeTempVO getEmployeePositionChangeTempVOByPositionChangeId( String positionChangeId ) throws KANException;

   public abstract void updateEmployeePositionChangeTemp( final EmployeePositionChangeTempVO EmployeeSalaryAemployeePositionChangeTempVOdjustmentTempVO ) throws KANException;

   public abstract void deleteEmployeePositionChangeTemp( final EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException;

   public abstract List< Object > getEmployeePositionChangeTempVOsByBatchId( final String batchId ) throws KANException;

}