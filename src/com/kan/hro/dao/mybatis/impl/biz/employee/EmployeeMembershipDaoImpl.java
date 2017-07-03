package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeMembershipDao;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;

public class EmployeeMembershipDaoImpl extends Context implements EmployeeMembershipDao
{

   @Override
   public int countEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeMembershipVOsByCondition", employeeMembershipVO );
   }

   @Override
   public List< Object > getEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return selectList( "getEmployeeMembershipVOsByCondition", employeeMembershipVO );
   }

   @Override
   public List< Object > getEmployeeMembershipVOsByCondition( final EmployeeMembershipVO employeeMembershipVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeMembershipVOsByCondition", employeeMembershipVO, rowBounds );
   }

   @Override
   public EmployeeMembershipVO getEmployeeMembershipVOByEmployeeMembershipId( final String employeeMembershipId ) throws KANException
   {

      return ( EmployeeMembershipVO ) select( "getEmployeeMembershipVOByEmployeeMembershipId", employeeMembershipId );
   }

   @Override
   public int insertEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return insert( "insertEmployeeMembership", employeeMembershipVO );
   }

   @Override
   public int updateEmployeeMembership( final EmployeeMembershipVO employeeMembershipVO ) throws KANException
   {

      return update( "updateEmployeeMembership", employeeMembershipVO );
   }

   @Override
   public int deleteEmployeeMembership( final String employeeMembershipId ) throws KANException
   {
      return delete( "deleteEmployeeMembership", employeeMembershipId );
   }
   @Override
   public  List< Object > getEmployeeMembershipVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeMembershipVOsByEmployeeId", employeeId );
   }

}
