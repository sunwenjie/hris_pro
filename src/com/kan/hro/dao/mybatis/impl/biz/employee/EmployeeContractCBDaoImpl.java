package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;

public class EmployeeContractCBDaoImpl extends Context implements EmployeeContractCBDao
{

   private EmployeeDao employeeDao;
   private EmployeeContractDao employeeContractDao;
   private EmployeeContractSBDao employeeContractSBDao;

   @Override
   public int countEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractCBVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return selectList( "getEmployeeContractCBVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractCBVOsByCondition", employeeContractCBVO, rowBounds );
   }

   @Override
   public EmployeeContractCBVO getEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( EmployeeContractCBVO ) select( "getEmployeeContractCBVOByEmployeeCBId", employeeCBId );
   }

   @Override
   public int insertEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return insert( "insertEmployeeContractCB", employeeContractCBVO );
   }

   @Override
   public int updateEmployeeContractCB( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return update( "updateEmployeeContractCB", employeeContractCBVO );
   }

   @Override
   public int deleteEmployeeContractCB( final String employeeCBId ) throws KANException
   {
      return delete( "deleteEmployeeContractCB", employeeCBId );
   }

   @Override
   public List< Object > getEmployeeContractCBVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractCBVOsByContractId", contractId );
   }

   @Override
   public int countFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return ( Integer ) select( "countFullEmployeeContractCBVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return selectList( "getFullEmployeeContractCBVOsByCondition", employeeContractCBVO );
   }

   @Override
   public List< Object > getFullEmployeeContractCBVOsByCondition( final EmployeeContractCBVO employeeContractCBVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getFullEmployeeContractCBVOsByCondition", employeeContractCBVO, rowBounds );
   }

   @Override
   public EmployeeContractCBVO getFullEmployeeContractCBVOByEmployeeCBId( final String employeeCBId ) throws KANException
   {
      return ( EmployeeContractCBVO ) select( "getFullEmployeeContractCBVOByEmployeeCBId", employeeCBId );
   }

   @Override
   public List< Object > getEmployeeContractCBVOsBySolutionNameZH( final EmployeeContractCBVO employeeContractCBVO ) throws KANException
   {
      return selectList( "getEmployeeContractCBVOsBySolutionNameZH", employeeContractCBVO );
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }
}
