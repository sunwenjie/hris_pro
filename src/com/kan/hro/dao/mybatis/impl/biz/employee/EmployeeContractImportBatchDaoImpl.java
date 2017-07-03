package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractImportBatchDao;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;

public class EmployeeContractImportBatchDaoImpl extends Context implements EmployeeContractImportBatchDao
{

   @Override
   public int countEmployeeContractImportBatchVOsByCondition( EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractImportBatchVOsByCondition", employeeContractImportBatchVO );
   }

   @Override
   public List< Object > getEmployeeContractImportBatchVOsByCondition( EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException
   {
      return selectList( "getEmployeeContractImportBatchVOsByCondition", employeeContractImportBatchVO );
   }

   @Override
   public List< Object > getEmployeeContractImportBatchVOsByCondition( EmployeeContractImportBatchVO EmployeeContractImportBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractImportBatchVOsByCondition", EmployeeContractImportBatchVO, rowBounds );
   }

   @Override
   public EmployeeContractImportBatchVO getEmployeeContractImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( EmployeeContractImportBatchVO ) select( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.getEmployeeContractImportBatchVOByBatchId", batchId );
   }

   @Override
   public int insertEmployeeContractImportBatch( EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException
   {
      return insert( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.insertEmployeeContractImportBatch", employeeContractImportBatchVO );
   }

   @Override
   public int updateEmployeeContractImportBatch( EmployeeContractImportBatchVO employeeContractImportBatchVO ) throws KANException
   {
      return update( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.updateEmployeeContractImportBatch", employeeContractImportBatchVO );
   }

   @Override
   public int deleteEmployeeContractImportBatch( String employeeContractImportBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractImportBatch", employeeContractImportBatchId );
   }

   @Override
   public int deleteEmployeeContractSalaryByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractSalaryByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractSalaryByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractSalaryByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractSBByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractSBByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractSBByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractSBByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractCBByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractCBByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractCBByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractCBByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractLeaveByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractLeaveByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractLeaveByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractLeaveByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractOTByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractOTByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractOTByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractOTByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractOtherByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractOtherByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractOtherByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractOtherByContractId", employeeContractId );
   }

   @Override
   public int deleteEmployeeContractByBatchId( String employeeContractBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractByBatchId", employeeContractBatchId );
   }

   @Override
   public int deleteEmployeeContractByContractId( String employeeContractId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO.deleteEmployeeContractByContractId", employeeContractId );
   }

}
