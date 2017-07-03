package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;

public class EmployeeContractLeaveTempDaoImpl extends Context implements EmployeeContractLeaveTempDao
{

   @Override
   public int countEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractLeaveTempVOsByCondition", employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveTempVOsByCondition", employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveTempVOsByCondition( final EmployeeContractLeaveVO employeeContractLeaveVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveTempVOsByCondition", employeeContractLeaveVO, rowBounds );
   }

   @Override
   public EmployeeContractLeaveVO getEmployeeContractLeaveTempVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException
   {
      return ( EmployeeContractLeaveVO ) select( "getEmployeeContractLeaveTempVOByEmployeeLeaveId", employeeLeaveId );
   }

   @Override
   public int insertEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return insert( "insertEmployeeContractLeaveTemp", employeeContractLeaveVO );
   }

   @Override
   public int updateEmployeeContractLeaveTemp( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return update( "updateEmployeeContractLeaveTemp", employeeContractLeaveVO );
   }

   @Override
   public int deleteEmployeeContractLeaveTemp( final String employeeLeaveId ) throws KANException
   {
      return delete( "deleteEmployeeContractLeaveTemp", employeeLeaveId );
   }

   @Override
   public List< Object > getEmployeeContractLeaveTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractLeaveTempVOsByContractId", contractId );
   }

}
