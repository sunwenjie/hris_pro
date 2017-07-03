package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportBatchDao;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;

public class EmployeeInsuranceNoImportBatchDaoImpl extends Context implements EmployeeInsuranceNoImportBatchDao
{

   @Override
   public int countEmployeeInsuranceNoImportBatchVOsByCondition( EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeInsuranceNoImportBatchVOsByCondition", employeeInsuranceNoImportBatchVO );
   }

   @Override
   public List< Object > getEmployeeInsuranceNoImportBatchVOsByCondition( EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException
   {
      return selectList( "getEmployeeInsuranceNoImportBatchVOsByCondition", employeeInsuranceNoImportBatchVO );
   }

   @Override
   public List< Object > getEmployeeInsuranceNoImportBatchVOsByCondition( EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeInsuranceNoImportBatchVOsByCondition", employeeInsuranceNoImportBatchVO, rowBounds );
   }

   @Override
   public EmployeeInsuranceNoImportBatchVO getEmployeeInsuranceNoImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( EmployeeInsuranceNoImportBatchVO ) select( "getEmployeeInsuranceNoImportBatchVOByBatchId", batchId );
   }

   @Override
   public void updateBathStatus( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO )
   {
      update( "updateEmployeeInsuranceNoImportBatchStatus", employeeInsuranceNoImportBatchVO );
   }
}
