package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;

public interface EmployeeSalaryAdjustmentDao
{
   public int countEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public List< Object > getEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO, RowBounds rowBounds ) throws KANException;

   public List< Object > getEmployeeSalaryAdjustmentVOsByCondition( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public void insertEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public EmployeeSalaryAdjustmentVO getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( String salaryAdjustmentId ) throws KANException;

   public void updateEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public void deleteEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public List< Object > getEmployeeSalaryAdjustmentVOByStatusAndDate(final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO) throws KANException;

   public void updateEmployeeSalaryAdjustmentStatus( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public int getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO )throws KANException;
}
