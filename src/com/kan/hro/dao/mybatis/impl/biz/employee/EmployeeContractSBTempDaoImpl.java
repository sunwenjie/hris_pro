package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;

public class EmployeeContractSBTempDaoImpl extends Context implements EmployeeContractSBTempDao
{

   @Override
   public int countEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBTempVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSBTempVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public EmployeeContractSBTempVO getEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( EmployeeContractSBTempVO ) select( "getEmployeeContractSBTempVOByEmployeeSBId", employeeSBId );
   }

   @Override
   public int insertEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return insert( "insertEmployeeContractSBTemp", employeeContractSBVO );
   }

   @Override
   public int updateEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return update( "updateEmployeeContractSBTemp", employeeContractSBVO );
   }

   @Override
   public int deleteEmployeeContractSBTemp( final String employeeSBId ) throws KANException
   {
      return delete( "deleteEmployeeContractSBTemp", employeeSBId );
   }

   @Override
   public List< Object > getEmployeeContractSBTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractSBTempVOsByContractId", contractId );
   }

   @Override
   public int countFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getFullEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeContractSBTempVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public EmployeeContractSBTempVO getFullEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( EmployeeContractSBTempVO ) select( "getFullEmployeeContractSBTempVOByEmployeeSBId", employeeSBId );
   }

   @Override
   public int countVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countVendorEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorEmployeeContractSBTempVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public List< Object > getVendorEmployeeContractSBTempVOsByCondition( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getVendorEmployeeContractSBTempVOsByCondition", employeeContractSBVO );
   }

}
