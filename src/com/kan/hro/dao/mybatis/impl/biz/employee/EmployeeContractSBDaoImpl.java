package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;

public class EmployeeContractSBDaoImpl extends Context implements EmployeeContractSBDao
{
   // ×¢ÈëEmployeeDao
   private EmployeeDao employeeDao;

   // ×¢ÈëEmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   public final EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public final void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public final void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   @Override
   public int countEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSBVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public EmployeeContractSBVO getEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( EmployeeContractSBVO ) select( "getEmployeeContractSBVOByEmployeeSBId", employeeSBId );
   }

   @Override
   public int insertEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return insert( "insertEmployeeContractSB", employeeContractSBVO );
   }

   @Override
   public int updateEmployeeContractSB( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return update( "updateEmployeeContractSB", employeeContractSBVO );
   }

   @Override
   public int deleteEmployeeContractSB( final String employeeSBId ) throws KANException
   {
      return delete( "deleteEmployeeContractSB", employeeSBId );
   }

   @Override
   public List< Object > getEmployeeContractSBVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractSBVOsByContractId", contractId );
   }
   

   @Override
   public int countFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getFullEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeContractSBVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public EmployeeContractSBVO getFullEmployeeContractSBVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( EmployeeContractSBVO ) select( "getFullEmployeeContractSBVOByEmployeeSBId", employeeSBId );
   }

   @Override
   public int countVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return ( Integer ) select( "countVendorEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

   @Override
   public List< Object > getVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getVendorEmployeeContractSBVOsByCondition", employeeContractSBVO, rowBounds );
   }

   @Override
   public List< Object > getVendorEmployeeContractSBVOsByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      return selectList( "getVendorEmployeeContractSBVOsByCondition", employeeContractSBVO );
   }

}
