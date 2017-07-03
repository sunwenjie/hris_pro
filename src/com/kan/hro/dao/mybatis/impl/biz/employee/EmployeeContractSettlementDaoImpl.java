package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSettlementDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;

public class EmployeeContractSettlementDaoImpl extends Context implements EmployeeContractSettlementDao
{

   @Override
   public int countEmployeeContractSettlementVOsByCondition( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractSettlementVOsByCondition", employeeContractSettlementVO );
   }

   @Override
   public List< Object > getEmployeeContractSettlementVOsByCondition( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return selectList( "getEmployeeContractSettlementVOsByCondition", employeeContractSettlementVO );
   }

   @Override
   public List< Object > getEmployeeContractSettlementVOsByCondition( EmployeeContractSettlementVO employeeContractSettlementVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractSettlementVOsByCondition", employeeContractSettlementVO, rowBounds );
   }

   @Override
   public EmployeeContractSettlementVO getEmployeeContractSettlementVOByEmployeeSettlementId( String employeeSettlementId ) throws KANException
   {
      return ( EmployeeContractSettlementVO ) select( "getEmployeeContractSettlementVOByEmployeeSettlementId", employeeSettlementId );
   }

   @Override
   public int insertEmployeeContractSettlement( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return insert( "insertEmployeeContractSettlement", employeeContractSettlementVO );
   }

   @Override
   public int updateEmployeeContractSettlement( EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return update( "updateEmployeeContractSettlement", employeeContractSettlementVO );
   }

   @Override
   public int deleteEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException
   {
      return delete( "deleteEmployeeContractSettlement", employeeContractSettlementVO );
   }

   @Override
   public List< Object > getEmployeeContractSettlementVOsByContractId( String contractId ) throws KANException
   {
      return selectList( "getEmployeeContractSettlementVOsByContractId", contractId );
   }

}
