package com.kan.hro.service.inf.biz.employee;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;

public interface EmployeeSalaryAdjustmentService
{
   // ÄêÖÕ½±¡¢¹©Å¯²¹Ìù
   public static final String[] CYCLE_12_ITEM_IDS = new String[] { "18", "10147" };

   // ½±½ð£¨¼¾¶È£©
   public static final String[] CYCLE_3_ITEM_IDS = new String[] { "10153" };

   public void getSalaryAdjustmentVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public void insertEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public EmployeeSalaryAdjustmentVO getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( final String salaryAdjustmentId ) throws KANException;

   public void updateEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public void deleteEmployeeSalaryAdjustment( final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public void synchronizedEmployeeSalaryContract() throws KANException;

   public void synchronizedEmployeeSalaryContract_nt() throws KANException;

   public void submitEmployeeSalaryAdjustment( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;

   public void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException;

   public int getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO ) throws KANException;
}
