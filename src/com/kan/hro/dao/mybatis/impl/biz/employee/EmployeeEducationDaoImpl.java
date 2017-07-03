package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeEducationDao;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;

public class EmployeeEducationDaoImpl extends Context implements EmployeeEducationDao
{

   @Override
   public int countEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeEducationVOsByCondition", employeeEducationVO );
   }

   @Override
   public List< Object > getEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return selectList( "getEmployeeEducationVOsByCondition", employeeEducationVO );
   }

   @Override
   public List< Object > getEmployeeEducationVOsByCondition( final EmployeeEducationVO employeeEducationVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeEducationVOsByCondition", employeeEducationVO, rowBounds );
   }

   @Override
   public EmployeeEducationVO getEmployeeEducationVOByEmployeeEducationId( final String employeeEducationId ) throws KANException
   {

      return ( EmployeeEducationVO ) select( "getEmployeeEducationVOByEmployeeEducationId", employeeEducationId );
   }

   @Override
   public int insertEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return insert( "insertEmployeeEducation", employeeEducationVO );
   }

   @Override
   public int updateEmployeeEducation( final EmployeeEducationVO employeeEducationVO ) throws KANException
   {

      return update( "updateEmployeeEducation", employeeEducationVO );
   }

   @Override
   public int deleteEmployeeEducation( final String employeeEducationId) throws KANException
   {
      return delete( "deleteEmployeeEducation", employeeEducationId );
   }
   @Override
   public  List< Object > getEmployeeEducationVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeEducationVOsByEmployeeId", employeeId );
   }

   @Override
   public List< Object > getSchoolNameBySchoolName( String name ) throws KANException
   {
      return selectList( "getSchoolNameBySchoolName", name );
   }

   @Override
   public List< Object > getMajorByMajor( String name ) throws KANException
   {
      // TODO Auto-generated method stub
      return selectList( "getMajorByMajor", name );
   }
   
   

}
