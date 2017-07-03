package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeWorkDao;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;

public class EmployeeWorkDaoImpl extends Context implements EmployeeWorkDao
{

   @Override
   public int countEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeWorkVOsByCondition", employeeWorkVO );
   }

   @Override
   public List< Object > getEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return selectList( "getEmployeeWorkVOsByCondition", employeeWorkVO );
   }

   @Override
   public List< Object > getEmployeeWorkVOsByCondition( final EmployeeWorkVO employeeWorkVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeWorkVOsByCondition", employeeWorkVO, rowBounds );
   }

   @Override
   public EmployeeWorkVO getEmployeeWorkVOByEmployeeWorkId( final String employeeWorkId ) throws KANException
   {

      return ( EmployeeWorkVO ) select( "getEmployeeWorkVOByEmployeeWorkId", employeeWorkId );
   }

   @Override
   public int insertEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return insert( "insertEmployeeWork", employeeWorkVO );
   }

   @Override
   public int updateEmployeeWork( final EmployeeWorkVO employeeWorkVO ) throws KANException
   {

      return update( "updateEmployeeWork", employeeWorkVO );
   }

   @Override
   public int deleteEmployeeWork( final String employeeWorkId ) throws KANException
   {
      return delete( "deleteEmployeeWork", employeeWorkId );
   }
   @Override
   public  List< Object > getEmployeeWorkVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeWorkVOsByEmployeeId", employeeId );
   }
   
   @Override
   public  List< Object > getCompanyNameByName( final String name ) throws KANException{
      return selectList( "getCompanyNameByName", name );
   }

}
