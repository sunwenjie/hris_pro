package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.EmployeeStatusDao;
import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.util.KANException;

public class EmployeeStatusDaoImpl extends Context implements EmployeeStatusDao
{

   @Override
   public int countEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeStatusVOsByCondition", employeeStatusVO );
   }

   @Override
   public List< Object > getEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      return selectList( "getEmployeeStatusVOsByCondition", employeeStatusVO );
   }

   @Override
   public List< Object > getEmployeeStatusVOsByCondition( final EmployeeStatusVO employeeStatusVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeStatusVOsByCondition", employeeStatusVO, rowBounds );
   }

   @Override
   public EmployeeStatusVO getEmployeeStatusVOByEmployeeStatusId( final String employeeStatusId ) throws KANException
   {
      return ( EmployeeStatusVO ) select( "getEmployeeStatusVOByEmployeeStatusId", employeeStatusId );
   }

   @Override
   public int insertEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      return insert( "insertEmployeeStatus", employeeStatusVO );
   }

   @Override
   public int updateEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      return update( "updateEmployeeStatus", employeeStatusVO );
   }

   @Override
   public int deleteEmployeeStatus( final EmployeeStatusVO employeeStatusVO ) throws KANException
   {
      return delete( "deleteEmployeeStatus", employeeStatusVO );
   }

   @Override
   public List< Object > getEmployeeStatusVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getEmployeeStatusVOsByAccountId", accountId );
   }

}
