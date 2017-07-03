package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentDao;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;

public class EmployeeSalaryAdjustmentDaoImpl extends Context implements EmployeeSalaryAdjustmentDao
{

   @Override
   public int countEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeSalaryAdjustmentVOsByCondition", employeeSalaryAdjustmentVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentVOsByCondition", employeeSalaryAdjustmentVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentVOsByCondition", employeeSalaryAdjustmentVO, rowBounds );
   }

   @Override
   public void insertEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getOldBase() ) != null )
      {
         employeeSalaryAdjustmentVO.setOldBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentVO.getEmployeeId() ), employeeSalaryAdjustmentVO.getOldBase() ) );
      }
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getNewBase() ) != null )
      {
         employeeSalaryAdjustmentVO.setNewBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentVO.getEmployeeId() ), employeeSalaryAdjustmentVO.getNewBase() ) );
      }

      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getOldEndDate() ) == null )
      {
         employeeSalaryAdjustmentVO.setOldEndDate( KANUtil.formatDate( KANUtil.getDate( employeeSalaryAdjustmentVO.getNewStartDate(), 0, 0, -1 ), "yyyy-MM-dd" ) );
      }

      insert( "insertEmployeeSalaryAdjustment", employeeSalaryAdjustmentVO );
   }

   @Override
   public EmployeeSalaryAdjustmentVO getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( final String salaryAdjustmentId ) throws KANException
   {
      return ( EmployeeSalaryAdjustmentVO ) select( "getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId", salaryAdjustmentId );
   }

   @Override
   public void updateEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      String original_oldBase = employeeSalaryAdjustmentVO.getOldBase();
      String original_newBase = employeeSalaryAdjustmentVO.getNewBase();
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getOldBase() ) != null )
      {
         employeeSalaryAdjustmentVO.setOldBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentVO.getEmployeeId() ), employeeSalaryAdjustmentVO.getOldBase() ) );
      }
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentVO.getNewBase() ) != null )
      {
         employeeSalaryAdjustmentVO.setNewBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentVO.getEmployeeId() ), employeeSalaryAdjustmentVO.getNewBase() ) );
      }
      update( "updateEmployeeSalaryAdjustment", employeeSalaryAdjustmentVO );
      employeeSalaryAdjustmentVO.setOldBase( original_oldBase );
      employeeSalaryAdjustmentVO.setNewBase( original_newBase );
   }

   @Override
   public void deleteEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      delete( "deleteEmployeeSalaryAdjustment", employeeSalaryAdjustmentVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentVOByStatusAndDate( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentVOByStatusAndDate", employeeSalaryAdjustmentVO );
   }

   @Override
   public void updateEmployeeSalaryAdjustmentStatus( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      update( "updateEmployeeSalaryAdjustmentStatus", employeeSalaryAdjustmentVO );
   }

   @Override
   public int getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException
   {
      return ( Integer ) select( "getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId", employeeSalaryAdjustmentVO );
   }
}
