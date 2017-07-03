package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;

public class EmployeeContractSBDetailDaoImpl extends Context implements EmployeeContractSBDetailDao
{
   // ×¢ÈëEmployeeDao
   private EmployeeDao employeeDao;

   // ×¢ÈëEmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ×¢ÈëEmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

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

   public final EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public final void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   @Override
   public int countEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSBDetailVOsByCondition", employeeContractSBDetailVO );
   }

   @Override
   public List< Object > getEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return selectList( "getEmployeeContractSBDetailVOsByCondition", employeeContractSBDetailVO );
   }

   @Override
   public List< Object > getEmployeeContractSBDetailVOsByCondition( final EmployeeContractSBDetailVO employeeContractSBDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSBDetailVOsByCondition", employeeContractSBDetailVO, rowBounds );
   }

   @Override
   public EmployeeContractSBDetailVO getEmployeeContractSBDetailVOByEmployeeSBDetailId( final String employeeSBDetailId ) throws KANException
   {
      return ( EmployeeContractSBDetailVO ) select( "getEmployeeContractSBDetailVOByEmployeeSBDetailId", employeeSBDetailId );
   }

   @Override
   public int insertEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return insert( "insertEmployeeContractSBDetail", employeeContractSBDetailVO );
   }

   @Override
   public int updateEmployeeContractSBDetail( final EmployeeContractSBDetailVO employeeContractSBDetailVO ) throws KANException
   {
      return update( "updateEmployeeContractSBDetail", employeeContractSBDetailVO );
   }

   @Override
   public int deleteEmployeeContractSBDetail( final String employeeSBId ) throws KANException
   {
      return delete( "deleteEmployeeContractSBDetail", employeeSBId );
   }

   @Override
   public List< Object > getEmployeeContractSBDetailVOsByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return selectList( "getEmployeeContractSBDetailVOsByEmployeeSBId", employeeSBId );
   }

}
