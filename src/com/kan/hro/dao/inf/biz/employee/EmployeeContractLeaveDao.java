package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public interface EmployeeContractLeaveDao
{
   public abstract int countEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractLeaveVO getEmployeeContractLeaveVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException;

   public abstract int insertEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int updateEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int deleteEmployeeContractLeave( final String employeeLeaveId ) throws KANException;

   public abstract List< Object > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException;

   public abstract EmployeeContractLeaveVO getLastYearAnnualLeaveByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException;

   public abstract int countAnnualLeaveDetailsByCondition( final EmployeeContractLeaveReportVO employeeContractLeaveReportVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveDetailsByCondition( final EmployeeContractLeaveReportVO employeeContractLeaveReportVO ) throws KANException;

   public abstract List< Object > getAnnualLeaveDetailsByCondition( final EmployeeContractLeaveReportVO employeeContractLeaveReportVO, RowBounds rowBound ) throws KANException;

}
