package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeBatchVO;

public interface EmployeePositionChangeBatchDao
{
   public abstract int countEmployeePositionChangeBatchVOsByCondition( final EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;

   public abstract List< Object > getEmployeePositionChangeBatchVOsByCondition( final EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;

   public abstract List< Object > getEmployeePositionChangeBatchVOsByCondition( final EmployeePositionChangeBatchVO employeePositionChangeBatchVO, final RowBounds rowBounds )
         throws KANException;

   public abstract int updateEmployeePositionChangeBatch( final EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;

   public abstract void deleteEmployeePositionChangeBatch( final EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException;

   public abstract EmployeePositionChangeBatchVO getEmployeePositionChangeBatchVOByBatchId( final String batchId ) throws KANException;

}
