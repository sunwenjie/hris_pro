package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeCertificationDao;
import com.kan.hro.domain.biz.employee.EmployeeCertificationVO;

public class EmployeeCertificationDaoImpl extends Context implements EmployeeCertificationDao
{

   @Override
   public int countEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeCertificationVOsByCondition", employeeCertificationVO );
   }

   @Override
   public List< Object > getEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return selectList( "getEmployeeCertificationVOsByCondition", employeeCertificationVO );
   }

   @Override
   public List< Object > getEmployeeCertificationVOsByCondition( final EmployeeCertificationVO employeeCertificationVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeCertificationVOsByCondition", employeeCertificationVO, rowBounds );
   }

   @Override
   public EmployeeCertificationVO getEmployeeCertificationVOByEmployeeCertificationId( final String employeeCertificationId ) throws KANException
   {

      return ( EmployeeCertificationVO ) select( "getEmployeeCertificationVOByEmployeeCertificationId", employeeCertificationId );
   }

   @Override
   public int insertEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return insert( "insertEmployeeCertification", employeeCertificationVO );
   }

   @Override
   public int updateEmployeeCertification( final EmployeeCertificationVO employeeCertificationVO ) throws KANException
   {

      return update( "updateEmployeeCertification", employeeCertificationVO );
   }

   @Override
   public int deleteEmployeeCertification( final String employeeCertificationId ) throws KANException
   {
      return delete( "deleteEmployeeCertification", employeeCertificationId );
   }
   @Override
   public  List< Object > getEmployeeCertificationVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeCertificationVOsByEmployeeId", employeeId );
   }

}
