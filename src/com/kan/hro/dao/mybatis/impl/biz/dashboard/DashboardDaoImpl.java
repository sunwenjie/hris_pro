package com.kan.hro.dao.mybatis.impl.biz.dashboard;

import java.util.List;

import com.kan.base.core.Context;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.dashboard.DashboardDao;
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

public class DashboardDaoImpl extends Context implements DashboardDao
{

   @Override
   public AccountVO getAccountVOByAccountId( String accountId ) throws KANException
   {
      return ( AccountVO ) select( "getAccountVOByAccountIdForDashboard", accountId );
   }

   @Override
   public List< Object > getVendorCountForDashboard( VendorVO vendorVO ) throws KANException
   {
      return selectList( "getVendorCountForDashboard", vendorVO );
   }

   @Override
   public List< Object > getClientCountForDashboard( ClientVO clientVO ) throws KANException
   {
      return selectList( "getClientCountForDashboard", clientVO );
   }

   @Override
   public List< Object > getClientContractCountForDashboard( ClientContractVO clientContractVO ) throws KANException
   {
      return selectList( "getClientContractCountForDashboard", clientContractVO );
   }

   @Override
   public List< Object > getClientOrdersCountForDashboard( ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return selectList( "getClientOrdersCountForDashboard", clientOrderHeaderVO );
   }

   @Override
   public List< Object > getEmployeeCountForDashboard( EmployeeVO employeeVO ) throws KANException
   {
      return selectList( "getEmployeeCountForDashboard", employeeVO );
   }

   @Override
   public List< Object > getEmployeeContractCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      return selectList( "getEmployeeContractCountForDashboard", employeeContractVO );
   }

   @Override
   public List< Object > getAttendanceLeaveDurationForDashboard( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return selectList( "getAttendanceLeaveDurationForDashboard", leaveHeaderVO );
   }

   @Override
   public List< Object > getAttendanceOTDurationForDashboard( OTHeaderVO oTHeaderVO ) throws KANException
   {
      return selectList( "getAttendanceOTDurationForDashboard", oTHeaderVO );
   }

   @Override
   public List< Object > getSBAmountForDashboard( SBHeaderVO sbHeaderVO ) throws KANException
   {
      return selectList( "getSBAmountForDashboard", sbHeaderVO );
   }

   @Override
   public List< Object > getSBAdjAmountForDashboard( SBAdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return selectList( "getSBAdjAmountForDashboard", adjustmentHeaderVO );
   }

   @Override
   public List< Object > getCBAmountForDashboard( CBHeaderVO cbHeaderVO ) throws KANException
   {
      return selectList( "getCBAmountForDashboard", cbHeaderVO );
   }

   @Override
   public List< Object > getSettlementAmountForDashboard( ServiceContractVO serviceContractVO ) throws KANException
   {
      return selectList( "getSettlementAmountForDashboard", serviceContractVO );
   }

   @Override
   public List< Object > getSettlementAdjAmountForDashboard( AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      return selectList( "getSettlementAdjAmountForDashboard", adjustmentHeaderVO );
   }

   @Override
   public List< Object > getPaymentAmountForDashboard( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      return selectList( "getPaymentAmountForDashboard", paymentHeaderVO );
   }

   @Override
   public List< Object > getPaymentAdjAmountForDashboard( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      return selectList( "getPaymentAdjAmountForDashboard", paymentAdjustmentHeaderVO );
   }

   @Override
   public List< Object > getEmployeeOnJobCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      return selectList( "getEmployeeOnJobCountForDashboard", employeeContractVO );
   }

   @Override
   public List< Object > getEmployeeNewCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      return selectList( "getEmployeeNewCountForDashboard", employeeContractVO );
   }

   @Override
   public List< Object > getEmployeeDismissionCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      return selectList( "getEmployeeDismissionCountForDashboard", employeeContractVO );
   }

}