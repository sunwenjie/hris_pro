package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class EmployeeContractSalaryDaoImpl extends Context implements EmployeeContractSalaryDao
{
   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   // 注入EmployeeContractDao
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
   public int countEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSalaryVOsByCondition", employeeContractSalaryVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryVOsByCondition", employeeContractSalaryVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByCondition( final EmployeeContractSalaryVO employeeContractSalaryVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryVOsByCondition", employeeContractSalaryVO, rowBounds );
   }

   @Override
   public EmployeeContractSalaryVO getEmployeeContractSalaryVOByEmployeeSalaryId( final String employeeSalaryId ) throws KANException
   {
      return ( EmployeeContractSalaryVO ) select( "getEmployeeContractSalaryVOByEmployeeSalaryId", employeeSalaryId );
   }

   @Override
   public int insertEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );

      // 敏感数据加密
      if ( KANUtil.filterEmpty( employeeContractSalaryVO.getBase() ) != null && employeeContractVO != null )
      {
         employeeContractSalaryVO.setBase( Cryptogram.encodeNumber( employeeContractVO.getPublicCode(), employeeContractSalaryVO.getBase() ) );
      }

      return insert( "insertEmployeeContractSalary", employeeContractSalaryVO );
   }

   @Override
   public int updateEmployeeContractSalary( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );

      // 敏感数据加密
      if ( KANUtil.filterEmpty( employeeContractSalaryVO.getBase() ) != null && employeeContractVO != null )
      {
         employeeContractSalaryVO.setBase( Cryptogram.encodeNumber( employeeContractVO.getPublicCode(), employeeContractSalaryVO.getBase() ) );
      }

      return update( "updateEmployeeContractSalary", employeeContractSalaryVO );
   }

   @Override
   public int deleteEmployeeContractSalary( final String employeeSalaryId ) throws KANException
   {
      return delete( "deleteEmployeeContractSalary", employeeSalaryId );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryVOsByContractId", contractId );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByContractIds( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryVOsByContractIds", employeeContractSalaryVO );
   }

   @Override
   public int updateEmployeeContractSalaryStatus( final EmployeeContractSalaryVO employeeContractSalaryVO ) throws KANException
   {
      return update( "updateEmployeeContractSalaryStatus", employeeContractSalaryVO );
   }

   @Override
   public List< Object > getEmployeeContractSalaryVOsByContractIdAndItemId( Map< String, Object > parameters ) throws KANException
   {
      return selectList( "getEmployeeContractSalaryVOsByContractIdAndItemId", parameters );
   }

}
