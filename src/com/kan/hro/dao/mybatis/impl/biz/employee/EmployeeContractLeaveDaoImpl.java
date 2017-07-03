package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public class EmployeeContractLeaveDaoImpl extends Context implements EmployeeContractLeaveDao
{
   @Override
   public int countEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractLeaveVOsByCondition", employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveVOsByCondition", employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveVOsByCondition", employeeContractLeaveVO, rowBounds );
   }

   @Override
   public EmployeeContractLeaveVO getEmployeeContractLeaveVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException
   {
      return ( EmployeeContractLeaveVO ) select( "getEmployeeContractLeaveVOByEmployeeLeaveId", employeeLeaveId );
   }

   @Override
   public int insertEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return insert( "insertEmployeeContractLeave", employeeContractLeaveVO );
   }

   @Override
   public int updateEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return update( "updateEmployeeContractLeave", employeeContractLeaveVO );
   }

   @Override
   public int deleteEmployeeContractLeave( final String employeeLeaveId ) throws KANException
   {
      return delete( "deleteEmployeeContractLeave", employeeLeaveId );
   }

   @Override
   public List< Object > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveVOsByContractId", contractId );
   }

   @Override
   public EmployeeContractLeaveVO getLastYearAnnualLeaveByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      final List< Object > list = selectList( "getLastYearAnnualLeaveByCondition", employeeContractLeaveVO );
      return list == null || list.size() < 1 ? null : ( EmployeeContractLeaveVO ) list.get( 0 );
   }

   @Override
   public int countAnnualLeaveDetailsByCondition( EmployeeContractLeaveReportVO employeeContractLeaveReportVO ) throws KANException
   {
      return ( int ) select( "countAnnualLeaveDetailsByCondition", employeeContractLeaveReportVO );
   }

   @Override
   public List< Object > getAnnualLeaveDetailsByCondition( EmployeeContractLeaveReportVO employeeContractLeaveReportVO ) throws KANException
   {
      return selectList( "getAnnualLeaveDetailsByCondition", employeeContractLeaveReportVO );
   }

   @Override
   public List< Object > getAnnualLeaveDetailsByCondition( EmployeeContractLeaveReportVO employeeContractLeaveReportVO, RowBounds rowBound ) throws KANException
   {
      return selectList( "getAnnualLeaveDetailsByCondition", employeeContractLeaveReportVO, rowBound );
   }

}
