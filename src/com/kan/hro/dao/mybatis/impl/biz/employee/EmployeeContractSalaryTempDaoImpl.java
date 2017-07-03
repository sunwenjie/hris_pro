package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;

public class EmployeeContractSalaryTempDaoImpl extends Context implements EmployeeContractSalaryTempDao
{

   @Override
   public int countEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO, rowBounds );
   }

   @Override
   public EmployeeContractSalaryVO getEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( EmployeeContractSalaryVO ) select( "getEmployeeContractSalaryTempVOByEmployeeSalaryId", employeeSalaryId );
   }

   @Override
   public int countFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return selectList( "getFullEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSalaryTempVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryTempVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getFullEmployeeContractSalaryTempVOsByCondition", employeeContractSalaryTempVO, rowBounds );
   }

   @Override
   public EmployeeContractSalaryVO getFullEmployeeContractSalaryTempVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( EmployeeContractSalaryVO ) select( "getFullEmployeeContractSalaryTempVOByEmployeeSalaryId", employeeSalaryId );
   }

   @Override
   public int insertEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return insert( "insertEmployeeContractSalaryTemp", employeeContractSalaryTempVO );
   }

   @Override
   public int updateEmployeeContractSalaryTemp( final EmployeeContractSalaryVO employeeContractSalaryTempVO ) throws KANException
   {
      return update( "updateEmployeeContractSalaryTemp", employeeContractSalaryTempVO );
   }

   @Override
   public int deleteEmployeeContractSalaryTemp( final String employeeSalaryId ) throws KANException
   {
      return delete( "deleteEmployeeContractSalaryTemp", employeeSalaryId );
   }

   @Override
   public List< Object > getEmployeeContractSalaryTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryTempVOsByContractId", contractId );
   }
}
