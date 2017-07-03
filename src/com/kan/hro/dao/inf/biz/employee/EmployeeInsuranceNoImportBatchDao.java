package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;

public interface EmployeeInsuranceNoImportBatchDao
{
   public abstract int countEmployeeInsuranceNoImportBatchVOsByCondition( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException;
   
   public abstract List< Object > getEmployeeInsuranceNoImportBatchVOsByCondition( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO ) throws KANException;

   public abstract List< Object > getEmployeeInsuranceNoImportBatchVOsByCondition( final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract EmployeeInsuranceNoImportBatchVO getEmployeeInsuranceNoImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void updateBathStatus( final EmployeeInsuranceNoImportBatchVO submitObject );
}
