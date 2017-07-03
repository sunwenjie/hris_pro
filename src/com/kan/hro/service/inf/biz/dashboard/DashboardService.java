package com.kan.hro.service.inf.biz.dashboard;

import java.util.Map;

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

public interface DashboardService
{

   public abstract AccountVO getAccountVOByAccountId( final String accountId ) throws KANException;

   public abstract int getVendorCountForDashboard( final VendorVO vendorVO ) throws KANException;

   public abstract int getClientCountForDashboard( final ClientVO clientVO ) throws KANException;

   public abstract int getClientContractCountForDashboard( final ClientContractVO clientContractVO ) throws KANException;

   public abstract int getClientOrdersCountForDashboard( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int getEmployeeCountForDashboard( final EmployeeVO employeeVO ) throws KANException;

   public abstract int getEmployeeContractCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;

   public abstract int getAttendanceLeaveDurationForDashboard( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int getAttendanceOTDurationForDashboard( final OTHeaderVO oTHeaderVO ) throws KANException;

   public abstract Map< String, Double > getSBAmountForDashboard( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract Map< String, Double > getSBAdjAmountForDashboard( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract Map< String, Double > getCBAmountForDashboard( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract Map< String, Double > getSettlementAmountForDashboard( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract Map< String, Double > getSettlementAdjAmountForDashboard( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract Map< String, Double > getPaymentAmountForDashboard( final PaymentHeaderVO paymentHeaderVO ) throws KANException;

   public abstract Map< String, Double > getPaymentAdjAmountForDashboard( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException;
   
   public abstract int getEmployeeOnJobCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
   
   public abstract int getEmployeeNewCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
   
   public abstract int getEmployeeDismissionCountForDashboard( final EmployeeContractVO employeeContractVO ) throws KANException;
}
