package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeTempDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;

public class EmployeePositionChangeTempDaoImpl extends Context implements EmployeePositionChangeTempDao
{

   @Override
   public int countEmployeePositionChangeTempVOsByCondition( EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeePositionChangeTempVOsByCondition", employeePositionChangeTempVO );
   }

   @Override
   public List< Object > getEmployeePositionChangeTempVOsByCondition( EmployeePositionChangeTempVO employeePositionChangeTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeePositionChangeTempVOsByCondition", employeePositionChangeTempVO, rowBounds );
   }

   @Override
   public List< Object > getEmployeePositionChangeTempVOsByCondition( EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      return selectList( "getEmployeePositionChangeTempVOsByCondition", employeePositionChangeTempVO );
   }

   @Override
   public int insertEmployeePositionChangeTemp( EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      return insert( "insertEmployeePositionChangeTemp", employeePositionChangeTempVO );
   }

   @Override
   public EmployeePositionChangeTempVO getEmployeePositionChangeTempVOByPositionChangeId( String PositionChangeId ) throws KANException
   {
      return ( EmployeePositionChangeTempVO ) select( "getEmployeePositionChangeTempVOByPositionChangeId", PositionChangeId );
   }

   @Override
   public void updateEmployeePositionChangeTemp( EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      update( "updateEmployeePositionChangeTemp", employeePositionChangeTempVO );
   }

   @Override
   public void deleteEmployeePositionChangeTemp( EmployeePositionChangeTempVO employeePositionChangeTempVO ) throws KANException
   {
      delete( "deleteEmployeePositionChangeTemp", employeePositionChangeTempVO.getPositionChangeId() );
   }

   @Override
   public List< Object > getEmployeePositionChangeTempVOsByBatchId( String batchId ) throws KANException
   {
      return selectList( "getEmployeePositionChangeTempVOsByBatchId", batchId );
   }

}