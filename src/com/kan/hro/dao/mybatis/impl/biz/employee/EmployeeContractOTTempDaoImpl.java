package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;

public class EmployeeContractOTTempDaoImpl extends Context implements EmployeeContractOTTempDao
{

   @Override
   public int countEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractOTTempVOsByCondition", employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return selectList( "getEmployeeContractOTTempVOsByCondition", employeeContractOTVO );
   }

   @Override
   public List< Object > getEmployeeContractOTTempVOsByCondition( final EmployeeContractOTVO employeeContractOTVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractOTTempVOsByCondition", employeeContractOTVO, rowBounds );
   }

   @Override
   public EmployeeContractOTVO getEmployeeContractOTTempVOByEmployeeOTId( final String employeeOTId ) throws KANException
   {
      return ( EmployeeContractOTVO ) select( "getEmployeeContractOTTempVOByEmployeeOTId", employeeOTId );
   }

   @Override
   public int insertEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return insert( "insertEmployeeContractOTTemp", employeeContractOTVO );
   }

   @Override
   public int updateEmployeeContractOTTemp( final EmployeeContractOTVO employeeContractOTVO ) throws KANException
   {
      return update( "updateEmployeeContractOTTemp", employeeContractOTVO );
   }

   @Override
   public int deleteEmployeeContractOTTemp( final String employeeOTId ) throws KANException
   {
      return delete( "deleteEmployeeContractOTTemp", employeeOTId );
   }

   @Override
   public List< Object > getEmployeeContractOTTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractOTTempVOsByContractId", contractId );
   }

}
