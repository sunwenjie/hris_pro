package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeSalaryAdjustmentTempDao;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentTempVO;

public class EmployeeSalaryAdjustmentTempDaoImpl extends Context implements EmployeeSalaryAdjustmentTempDao
{

   @Override
   public int countEmployeeSalaryAdjustmentTempVOsByCondition( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeSalaryAdjustmentTempVOsByCondition", employeeSalaryAdjustmentTempVO );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentTempVOsByCondition( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentTempVOsByCondition", employeeSalaryAdjustmentTempVO, rowBounds );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentTempVOsByCondition( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentTempVOsByCondition", employeeSalaryAdjustmentTempVO );
   }

   @Override
   public int insertEmployeeSalaryAdjustmentTemp( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException
   {
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getOldBase() ) != null )
      {
         employeeSalaryAdjustmentTempVO.setOldBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentTempVO.getEmployeeId() ), employeeSalaryAdjustmentTempVO.getOldBase() ) );
      }
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getNewBase() ) != null )
      {
         employeeSalaryAdjustmentTempVO.setNewBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentTempVO.getEmployeeId() ), employeeSalaryAdjustmentTempVO.getNewBase() ) );
      }
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getOldEndDate() ) == null )
      {
         employeeSalaryAdjustmentTempVO.setOldEndDate( KANUtil.formatDate( KANUtil.getDate( employeeSalaryAdjustmentTempVO.getNewStartDate(), 0, 0, -1 ), "yyyy-MM-dd" ) );
      }
      return insert( "insertEmployeeSalaryAdjustmentTemp", employeeSalaryAdjustmentTempVO );
   }

   @Override
   public EmployeeSalaryAdjustmentTempVO getEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentId( String salaryAdjustmentId ) throws KANException
   {
      return ( EmployeeSalaryAdjustmentTempVO ) select( "getEmployeeSalaryAdjustmentTempVOBySalaryAdjustmentId", salaryAdjustmentId );
   }

   @Override
   public void updateEmployeeSalaryAdjustmentTemp( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException
   {
      String original_oldBase = employeeSalaryAdjustmentTempVO.getOldBase();
      String original_newBase = employeeSalaryAdjustmentTempVO.getNewBase();
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getOldBase() ) != null )
      {
         employeeSalaryAdjustmentTempVO.setOldBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentTempVO.getEmployeeId() ), employeeSalaryAdjustmentTempVO.getOldBase() ) );
      }
      if ( KANUtil.filterEmpty( employeeSalaryAdjustmentTempVO.getNewBase() ) != null )
      {
         employeeSalaryAdjustmentTempVO.setNewBase( Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeSalaryAdjustmentTempVO.getEmployeeId() ), employeeSalaryAdjustmentTempVO.getNewBase() ) );
      }
      update( "updateEmployeeSalaryAdjustmentTemp", employeeSalaryAdjustmentTempVO );
      employeeSalaryAdjustmentTempVO.setOldBase( original_oldBase );
      employeeSalaryAdjustmentTempVO.setNewBase( original_newBase );
   }

   @Override
   public void deleteEmployeeSalaryAdjustmentTemp( EmployeeSalaryAdjustmentTempVO employeeSalaryAdjustmentTempVO ) throws KANException
   {
      delete( "deleteEmployeeSalaryAdjustmentTemp", employeeSalaryAdjustmentTempVO.getSalaryAdjustmentId() );
   }

   @Override
   public List< Object > getEmployeeSalaryAdjustmentTempVOsByBatchId( String batchId ) throws KANException
   {
      return selectList( "getEmployeeSalaryAdjustmentTempVOsByBatchId", batchId );
   }

}