package com.kan.hro.service.inf.biz.employee;

import java.util.List;

import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeContractBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;

public interface EmployeeContractService
{
   public static String FLAG_SETTLEMENT = "1";

   public static String FLAG_SB = "2";

   public static String FLAG_CB = "3";

   public static String FLAG_TS = "4";

   public abstract PagedListHolder getEmployeeContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract EmployeeContractVO getEmployeeContractVOByContractId( final String contractId ) throws KANException;

   public abstract int insertEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int updateEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   /**
    * 仅修改单表.没有任何同步
    * @param employeeContractVO
    * @return
    * @throws KANException
    */
   public abstract int updateBaseEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public int submitEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;
   
   public int submitEmployeeContract_nt( final EmployeeContractVO employeeContractVO ) throws KANException;

   public int updateEmployeeContract( final EmployeeContractVO employeeContractVO, final List< ConstantVO > constantVOs ) throws KANException;

   public abstract int deleteEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByClientId( final EmployeeContractBaseView employeeContractBaseView ) throws KANException;

   public abstract List< Object > getEmployeeContractVOsByClientId( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< ServiceContractDTO > getServiceContractDTOsByCondition( final EmployeeContractVO employeeContractVO, final String flag ) throws KANException;

   public abstract EmployeeContractBaseView getEmployeeContractBaseViewByContractId( final String contractId ) throws KANException;

   public abstract List< Object > getEmployeeContractsDuringService( final List< String > selectedIdList ) throws KANException;

   public abstract List< Object > getEmployeeContractBaseViewsByEmployeeId( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getServiceEmployeeContractVOsByOrderId( final String orderId ) throws KANException;

   public abstract boolean checkContractConflict( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int continueEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public int submitEmployeeContract_leave( final EmployeeContractVO employeeContractVO ) throws KANException;

   public List< Object > getEmployeeContractVOsByContractIds( final List< String > selectedIdList ) throws KANException;

   public abstract int renewEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int submitEmployeeContract_leave_nt( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int calculateEmployeeAnnualLeave( final EmployeeContractVO employeeContractVO ) throws KANException;

   /**
    * 
    * @param employeeId
    * @return
    * @throws KANException
    */
   public abstract EmployeeContractVO getLastAvailEmployeeContract( final String employeeId ) throws KANException;
   
   public abstract String transferHROwner( final String oldOwner, final String newOwner, final String entityId ) throws KANException;

}
