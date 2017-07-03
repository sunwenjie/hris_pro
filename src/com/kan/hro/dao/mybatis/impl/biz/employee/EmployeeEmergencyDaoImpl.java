package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeEmergencyDao;
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;


public class EmployeeEmergencyDaoImpl extends Context implements EmployeeEmergencyDao
{

   @Override
   public int countEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeEmergencyVOsByCondition", employeeEmergencyVO );
   }

   @Override
   public List< Object > getEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return selectList( "getEmployeeEmergencyVOsByCondition", employeeEmergencyVO );
   }

   @Override
   public List< Object > getEmployeeEmergencyVOsByCondition( final EmployeeEmergencyVO employeeEmergencyVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeEmergencyVOsByCondition", employeeEmergencyVO, rowBounds );
   }

   @Override
   public EmployeeEmergencyVO getEmployeeEmergencyVOByEmployeeEmergencyId( final String employeeEmergencyId ) throws KANException
   {

      return ( EmployeeEmergencyVO ) select( "getEmployeeEmergencyVOByEmployeeEmergencyId", employeeEmergencyId );
   }

   @Override
   public int insertEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return insert( "insertEmployeeEmergency", employeeEmergencyVO );
   }

   @Override
   public int updateEmployeeEmergency( final EmployeeEmergencyVO employeeEmergencyVO ) throws KANException
   {

      return update( "updateEmployeeEmergency", employeeEmergencyVO );
   }

   @Override
   public int deleteEmployeeEmergency(  final String employeeEmergencyId ) throws KANException
   {
      return delete( "deleteEmployeeEmergency", employeeEmergencyId );
   }
   @Override
   public  List< Object > getEmployeeEmergencyVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeEmergencyVOsByEmployeeId", employeeId );
   }

}
