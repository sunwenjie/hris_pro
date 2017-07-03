package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeBatchDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeBatchVO;

public class EmployeePositionChangeBatchDaoImpl extends Context implements EmployeePositionChangeBatchDao
{

   @Override
   public int countEmployeePositionChangeBatchVOsByCondition( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeePositionChangeBatchVOsByCondition", employeePositionChangeBatchVO );
   }

   @Override
   public List< Object > getEmployeePositionChangeBatchVOsByCondition( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      return selectList( "getEmployeePositionChangeBatchVOsByCondition", employeePositionChangeBatchVO );
   }

   @Override
   public List< Object > getEmployeePositionChangeBatchVOsByCondition( EmployeePositionChangeBatchVO employeePositionChangeBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeePositionChangeBatchVOsByCondition", employeePositionChangeBatchVO, rowBounds );
   }

   @Override
   public int updateEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      return update( "updateEmployeePositionChangeBatch", employeePositionChangeBatchVO );
   }

   @Override
   public void deleteEmployeePositionChangeBatch( EmployeePositionChangeBatchVO employeePositionChangeBatchVO ) throws KANException
   {
      delete( "deleteEmployeePositionChangeBatch", employeePositionChangeBatchVO );
   }

   @Override
   public EmployeePositionChangeBatchVO getEmployeePositionChangeBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( EmployeePositionChangeBatchVO ) select( "getEmployeePositionChangeBatchVOByBatchId", batchId );
   }

}
