package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeLanguageDao;
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;

public class EmployeeLanguageDaoImpl extends Context implements EmployeeLanguageDao
{

   @Override
   public int countEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return ( Integer ) select( "countEmployeeLanguageVOsByCondition", employeeLanguageVO );
   }

   @Override
   public List< Object > getEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return selectList( "getEmployeeLanguageVOsByCondition", employeeLanguageVO );
   }

   @Override
   public List< Object > getEmployeeLanguageVOsByCondition( final EmployeeLanguageVO employeeLanguageVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getEmployeeLanguageVOsByCondition", employeeLanguageVO, rowBounds );
   }

   @Override
   public EmployeeLanguageVO getEmployeeLanguageVOByEmployeeLanguageId( final String employeeLanguageId ) throws KANException
   {

      return ( EmployeeLanguageVO ) select( "getEmployeeLanguageVOByEmployeeLanguageId", employeeLanguageId );
   }

   @Override
   public int insertEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return insert( "insertEmployeeLanguage", employeeLanguageVO );
   }

   @Override
   public int updateEmployeeLanguage( final EmployeeLanguageVO employeeLanguageVO ) throws KANException
   {

      return update( "updateEmployeeLanguage", employeeLanguageVO );
   }

   @Override
   public int deleteEmployeeLanguage( final String employeeLanguageId  ) throws KANException
   {
      return delete( "deleteEmployeeLanguage", employeeLanguageId );
   }
   @Override
   public  List< Object > getEmployeeLanguageVOsByEmployeeId( final String employeeId ) throws KANException{
      return selectList( "getEmployeeLanguageVOsByEmployeeId", employeeId );
   }

}
