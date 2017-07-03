package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentBatchDao;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;

public class EmployeeSalaryAdjustmentBatchDaoImpl extends Context implements EmployeeSalaryAdjustmentBatchDao
{

   @Override
   public int countEmployeeSalaryAdjustmentBatchVOsByCondition( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeSalaryAdjustmentBatchVOsByCondition", employeeSalaryAdjustmentBatchVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentBatchVOsByCondition( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentBatchVOsByCondition", employeeSalaryAdjustmentBatchVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentBatchVOsByCondition( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentBatchVOsByCondition", employeeSalaryAdjustmentBatchVO, rowBounds );
   }

   @Override
   public int updateEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      return update( "updateEmployeeSalaryAdjustmentBatch", employeeSalaryAdjustmentBatchVO );
   }

   @Override
   public void deleteEmployeeSalaryAdjustmentBatch( EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO ) throws KANException
   {
      delete( "deleteEmployeeSalaryAdjustmentBatch", employeeSalaryAdjustmentBatchVO );
   }

   @Override
   public EmployeeSalaryAdjustmentBatchVO getEmployeeSalaryAdjustmentBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( EmployeeSalaryAdjustmentBatchVO ) select( "getEmployeeSalaryAdjustmentBatchVOByBatchId", batchId );
   }

}
