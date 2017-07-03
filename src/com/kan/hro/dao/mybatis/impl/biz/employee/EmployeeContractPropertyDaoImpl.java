package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractPropertyDao;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;

public class EmployeeContractPropertyDaoImpl extends Context implements EmployeeContractPropertyDao
{

   @Override
   public int countEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractPropertyVOsByCondition", employeeContractPropertyVO );
   }

   @Override
   public List< Object > getEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return selectList( "getEmployeeContractPropertyVOsByCondition", employeeContractPropertyVO );
   }

   @Override
   public List< Object > getEmployeeContractPropertyVOsByCondition( final EmployeeContractPropertyVO employeeContractPropertyVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractPropertyVOsByCondition", employeeContractPropertyVO, rowBounds );
   }

   @Override
   public EmployeeContractPropertyVO getEmployeeContractPropertyVOByEmployeeContractPropertyId( final String employeeContractPropertyId ) throws KANException
   {
      return ( EmployeeContractPropertyVO ) select( "getEmployeeContractPropertyVOByContractPropertyId", employeeContractPropertyId );
   }

   @Override
   public int updateEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return update( "updateEmployeeContractProperty", employeeContractPropertyVO );
   }

   @Override
   public int insertEmployeeContractProperty( final EmployeeContractPropertyVO employeeContractPropertyVO ) throws KANException
   {
      return insert( "insertEmployeeContractProperty", employeeContractPropertyVO );
   }

   @Override
   public int deleteEmployeeContractProperty( final String propertyId ) throws KANException
   {
      return delete( "deleteEmployeeContractProperty", propertyId );
   }

   @Override
   public List< Object > getEmployeeContractPropertyVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractPropertyVOsByContractId", contractId );
   }

}
