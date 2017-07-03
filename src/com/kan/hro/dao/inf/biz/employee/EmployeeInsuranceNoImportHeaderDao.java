package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;

public interface EmployeeInsuranceNoImportHeaderDao
{
   public abstract int countEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO ) throws KANException;

   public abstract List< Object > getEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO ) throws KANException;

   public abstract List< Object > getEmployeeInsuranceNoImportHeaderVOsByCondition( final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO,
         final RowBounds rowBounds ) throws KANException;

   public abstract void deleteEmployeeInsuranceNoImportHeaderTempByBatchId( final String batchId ) throws KANException;

   public abstract int updateEmployeeContractCarsNumber( final String batchId ) throws KANException;

   public abstract int updateEmployeeContractCBNumber( final String batchId) throws KANException;

   public abstract void deleteHeaderTempRecord( final String[] ids ) throws KANException;

   public abstract EmployeeInsuranceNoImportHeaderVO getEmployeeInsuranceNoImportHeaderVOsById( final String headerId, final String accountId ) throws KANException;

   public abstract int getHeaderCountByBatchId( final String batchId );

   public abstract void updateHeaderStatus( final String batchId ) throws KANException;
}
