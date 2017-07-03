package com.kan.hro.dao.inf.biz.dashboard;

import java.util.List;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;

public interface DashboardDao
{

   public abstract AccountVO getAccountVOByAccountId( final String accountId ) throws KANException;

   public abstract List< Object > getVendorCountForDashboard( final VendorVO vendorVO ) throws KANException;

   public abstract List< Object > getClientCountForDashboard( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientContractCountForDashboard( final ClientContractVO clientContractVO ) throws KANException;

   public abstract List< Object > getClientOrdersCountForDashboard( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object > getEmployeeCountForDashboard( final EmployeeVO employeeVO ) throws KANException;

   public abstract List< Object > getEmployeeContractCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract List< Object > getAttendanceLeaveDurationForDashboard( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract List< Object > getAttendanceOTDurationForDashboard( final OTHeaderVO oTHeaderVO ) throws KANException;

   public abstract List< Object > getSBAmountForDashboard( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBAdjAmountForDashboard( final SBAdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getCBAmountForDashboard( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< Object > getSettlementAmountForDashboard( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract List< Object > getSettlementAdjAmountForDashboard( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;
   
   public abstract List< Object > getPaymentAmountForDashboard( final PaymentHeaderVO paymentHeaderVO ) throws KANException;
   
   public abstract List< Object > getPaymentAdjAmountForDashboard( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;
   
   public abstract List< Object > getEmployeeOnJobCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
   
   public abstract List< Object > getEmployeeNewCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
   
   public abstract List< Object > getEmployeeDismissionCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
}