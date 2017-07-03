package com.kan.hro.dao.inf.biz.employee;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;

public interface EmployeeContractSettlementDao
{
   public abstract int countEmployeeContractSettlementVOsByCondition( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSettlementVOsByCondition( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSettlementVOsByCondition( final EmployeeContractSettlementVO employeeContractSettlementVO, RowBounds rowBounds )
         throws KANException;

   public abstract EmployeeContractSettlementVO getEmployeeContractSettlementVOByEmployeeSettlementId( final String employeeSettlementId ) throws KANException;

   public abstract int insertEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract int updateEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract int deleteEmployeeContractSettlement( final EmployeeContractSettlementVO employeeContractSettlementVO ) throws KANException;

   public abstract List< Object > getEmployeeContractSettlementVOsByContractId( final String contractId ) throws KANException;

}
