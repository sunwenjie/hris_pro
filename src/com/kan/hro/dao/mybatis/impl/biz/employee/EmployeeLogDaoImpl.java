package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeLogDao;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;

public class EmployeeLogDaoImpl extends Context implements EmployeeLogDao
{

   @Override
   public int countEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeLogVOsByCondition", employeeLogVO );
   }

   @Override
   public List< Object > getEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return selectList( "getEmployeeLogVOsByCondition", employeeLogVO );
   }

   @Override
   public List< Object > getEmployeeLogVOsByCondition( final EmployeeLogVO employeeLogVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeLogVOsByCondition", employeeLogVO, rowBounds );
   }

   @Override
   public EmployeeLogVO getEmployeeLogVOByEmployeeLogId( final String employeeLogId ) throws KANException
   {

      return ( EmployeeLogVO ) select( "getEmployeeLogVOByEmployeeLogId", employeeLogId );
   }

   @Override
   public int insertEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return insert( "insertEmployeeLog", employeeLogVO );
   }

   @Override
   public int updateEmployeeLog( final EmployeeLogVO employeeLogVO ) throws KANException
   {

      return update( "updateEmployeeLog", employeeLogVO );
   }

   @Override
   public int deleteEmployeeLog( final String employeeLogId ) throws KANException
   {
      return delete( "deleteEmployeeLog", employeeLogId );
   }

   @Override
   public List< Object > getEmployeeLogVOsByEmployeeId( final String employeeId ) throws KANException
   {
      return selectList( "getEmployeeLogVOsByEmployeeId", employeeId );
   }

}
