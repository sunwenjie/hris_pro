package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeInsuranceNoImportHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;

public class EmployeeInsuranceNoImportHeaderDaoImpl extends Context implements EmployeeInsuranceNoImportHeaderDao
{
   @Override
   public int countEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeInsuranceNoImportHeaderVOsByCondition", employeeInsuranceNoImportHeaderVO );
   }

   @Override
   public List< Object > getEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO ) throws KANException
   {
      return selectList( "getEmployeeInsuranceNoImportHeaderVOsByCondition", employeeInsuranceNoImportHeaderVO );
   }

   @Override
   public List< Object > getEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO, RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getEmployeeInsuranceNoImportHeaderVOsByCondition", employeeInsuranceNoImportHeaderVO, rowBounds );
   }

   @Override
   public int getHeaderCountByBatchId( String batchId )
   {
      return ( Integer ) select( "getEmployeeInsuranceNoImportHeaderCountByBatchId", batchId );
   }

   @Override
   public EmployeeInsuranceNoImportHeaderVO getEmployeeInsuranceNoImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      Map< String, String > args = new HashMap< String, String >();
      args.put( "cardnoId", headerId );
      args.put( "accountId", accountId );
      return ( EmployeeInsuranceNoImportHeaderVO ) select( "getEmployeeInsuranceNoImportHeaderVOsById", args );
   }

   @Override
   public void updateHeaderStatus( final String batchId ) throws KANException
   {
      delete( "updateEmployeeInsuranceNoImportHeaderStatus", batchId );
   }

   @Override
   public int updateEmployeeContractCarsNumber( final String batchId ) throws KANException
   {
      return insert( "updateEmployeeContractCarsNumber", batchId );
   }

   @Override
   public int updateEmployeeContractCBNumber( final String batchId) throws KANException
   {
      return insert( "updateEmployeeContractCBNumber", batchId )+insert( "updateEmployeeContractCBNumberB", batchId );
      
   }
   
   @Override
   public void deleteEmployeeInsuranceNoImportHeaderTempByBatchId( final String batchId )
   {
      delete( "deleteEmployeeInsuranceNoImportHeaderTempByBatchId", batchId );
   }
   
   @Override
   public void deleteHeaderTempRecord( final String[] ids ) throws KANException
   {
      delete( "deleteEmployeeInsuranceNoImportHeaderTempRecord", ids );
   }
}
