package com.kan.hro.dao.inf.biz.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public interface EmployeeContractDao
{
   public abstract int countEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO, RowBounds rowBounds ) throws KANException;

   public abstract EmployeeContractVO getEmployeeContractVOByContractId( final String contractId ) throws KANException;

   public abstract int updateEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int insertEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int deleteEmployeeContract( final String contractId ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByClientId( final EmployeeContractBaseView employeeContractBaseView ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByClientId( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getSettlementEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getSBEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getCBEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getTSEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract EmployeeContractBaseView getEmployeeContractBaseViewByContractId( final String contractId ) throws KANException;

   public abstract List< Object > getEmployeeContractsDuringService( final List< String > selectedIdArray ) throws KANException;

   public abstract List< Object > getServiceEmployeeContractVOsByOrderId( final String orderId ) throws KANException;
   
   public abstract List< Object >  getEmployeeContractBaseViewsByEmployeeId(final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByContractIds(List<String> selectedIdList) throws KANException;
   
   public abstract int transferEmployeeContractHROwner( Map< String, Object > parameterMap ) throws KANException;
}
