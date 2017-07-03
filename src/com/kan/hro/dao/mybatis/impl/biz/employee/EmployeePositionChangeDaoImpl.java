package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;

public class EmployeePositionChangeDaoImpl extends Context implements EmployeePositionChangeDao
{

   @Override
   public int countEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeePositionChangeVOsByCondition", employeePositionChangeVO );
   }
   
   @Override
   public List< Object > getEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      return selectList( "getEmployeePositionChangeVOsByCondition", employeePositionChangeVO);
   }

   @Override
   public List< Object > getEmployeePositionChangeVOsByCondition( final EmployeePositionChangeVO employeePositionChangeVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeePositionChangeVOsByCondition", employeePositionChangeVO,rowBounds );
   }

   @Override
   public void insertEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      insert( "insertEmployeePositionChange", employeePositionChangeVO );
   }

   @Override
   public EmployeePositionChangeVO getEmployeePositionChangeVOByPositionChangeId( final String positionChangeId ) throws KANException
   {
      return ( EmployeePositionChangeVO ) select( "getEmployeePositionChangeVOByPositionChangeId", positionChangeId );
   }

   @Override
   public void updateEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      update( "updateEmployeePositionChange", employeePositionChangeVO );
   }
   
   @Override
   public void updateEmployeePositionChangeStatus( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      update( "updateEmployeePositionChangeStatus", employeePositionChangeVO );
   }
   
   @Override
   public void deleteEmployeePositionChange( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      delete( "deleteEmployeePositionChange", employeePositionChangeVO );
   }

   @Override
   public List< Object > getEmployeePositionChangeVOsByDateAndStatus( final EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      return selectList( "getEmployeePositionChangeVOsByDateAndStatus", employeePositionChangeVO);
   }

   @Override
   public int getEffectivePositionChangeVOCountByEmployeeId( EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      return ( Integer ) select( "getEffectivePositionChangeVOCountByEmployeeId", employeePositionChangeVO );
   }
}
