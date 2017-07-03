package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractResignDao;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;

public class EmployeeContractResignDaoImpl extends Context implements EmployeeContractResignDao
{
   @Override
   public int countEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractResignVOsByCondition", employeeContractResignVO );
   }

   @Override
   public List< Object > getEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return selectList( "getEmployeeContractResignVOsByCondition", employeeContractResignVO );
   }

   @Override
   public List< Object > getEmployeeContractResignVOsByCondition( final EmployeeContractResignVO employeeContractResignVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractResignVOsByCondition", employeeContractResignVO, rowBounds );
   }

   @Override
   public EmployeeContractResignVO getEmployeeContractResignVOByEmployeeContractResignId( final String employeeContractResignId ) throws KANException
   {
      return ( EmployeeContractResignVO ) select( "getEmployeeContractResignVOByEmployeeContractResignId", employeeContractResignId );
   }

   @Override
   public int updateEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return update( "updateEmployeeContractResign", employeeContractResignVO );
   }

   @Override
   public int insertEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return insert( "insertEmployeeContractResign", employeeContractResignVO );
   }

   @Override
   public int deleteEmployeeContractResign( final EmployeeContractResignVO employeeContractResignVO ) throws KANException
   {
      return delete( "deleteEmployeeContractResign", employeeContractResignVO );
   }

   @Override
   public void deleteHeaderTempRecord( String[] ids ) throws KANException
   {
      delete( "deleteEmployeeContractResignTempRecord", ids );
   }

   @Override
   public int getHeaderCountByBatchId( String batchId ) throws KANException
   {
      return ( Integer ) select( "getEmployeeContractResignCountByBatchId", batchId );
   }

}
