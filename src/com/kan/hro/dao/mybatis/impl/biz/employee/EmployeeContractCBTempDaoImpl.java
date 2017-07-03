package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public class EmployeeContractCBTempDaoImpl extends Context implements EmployeeContractCBTempDao
{

   @Override
   public int countEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractCBTempVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return selectList( "getEmployeeContractCBTempVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractCBTempVOsByCondition", employeeContractCBVO, rowBounds );
   }

   @Override
   public EmployeeContractCBVO getEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( EmployeeContractCBVO ) select( "getEmployeeContractCBTempVOByEmployeeCBId", employeeCBId );
   }

   @Override
   public int insertEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return insert( "insertEmployeeContractCBTemp", employeeContractCBVO );
   }

   @Override
   public int updateEmployeeContractCBTemp( final EmployeeContractCBVO employeeContractCBTempVO ) throws KANException
   {
      return update( "updateEmployeeContractCBTemp", employeeContractCBTempVO );
   }

   @Override
   public int deleteEmployeeContractCBTemp( final String employeeCBId ) throws KANException
   {
      return delete( "deleteEmployeeContractCBTemp", employeeCBId );
   }

   @Override
   public List< Object > getEmployeeContractCBTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractCBTempVOsByContractId", contractId );
   }

   @Override
   public int countFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeContractCBTempVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return selectList( "getFullEmployeeContractCBTempVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractCBTempVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeContractCBTempVOsByCondition", employeeContractCBVO, rowBounds );
   }

   @Override
   public EmployeeContractCBVO getFullEmployeeContractCBTempVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( EmployeeContractCBVO ) select( "getFullEmployeeContractCBTempVOByEmployeeCBId", employeeCBId );
   }

}
