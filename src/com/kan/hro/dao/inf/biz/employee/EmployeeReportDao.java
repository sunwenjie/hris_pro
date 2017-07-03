package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;

public interface EmployeeReportDao
{
   public abstract int countEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract List< Object > getEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract List< Object > getEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO, final RowBounds rowBounds ) throws KANException;

   public abstract int countEmployeeSalaryReportVOsByCondition( EmployeeReportVO object ) throws KANException;

   public abstract List< Object > getEmployeeSalaryReportVOsByCondition( EmployeeReportVO object, RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getEmployeeSalaryReportVOsByCondition( EmployeeReportVO object ) throws KANException;

   public abstract int countEmployeePerformanceReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract List< Object > getEmployeePerformanceReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract List< Object > getEmployeePerformanceReportVOsByCondition( final EmployeeReportVO employeeReportVO, final RowBounds rowBounds ) throws KANException;

   public abstract int countContactsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

   public abstract List< Object > getContactsByCondition( final EmployeeReportVO employeeReportVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getContactsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException;

}
