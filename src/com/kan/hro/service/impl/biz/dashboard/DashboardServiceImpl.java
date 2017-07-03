package com.kan.hro.service.impl.biz.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kan.base.core.ContextService;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.dashboard.DashboardDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.dashboard.DashboardService;

public class DashboardServiceImpl extends ContextService implements DashboardService
{
   @Override
   public AccountVO getAccountVOByAccountId( String accountId ) throws KANException
   {
      return ( ( DashboardDao ) getDao() ).getAccountVOByAccountId( accountId );
   }

   @Override
   public int getVendorCountForDashboard( VendorVO vendorVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getVendorCountForDashboard( vendorVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getClientCountForDashboard( ClientVO clientVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getClientCountForDashboard( clientVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getClientContractCountForDashboard( ClientContractVO clientContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getClientContractCountForDashboard( clientContractVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getClientOrdersCountForDashboard( ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getClientOrdersCountForDashboard( clientOrderHeaderVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getEmployeeCountForDashboard( EmployeeVO employeeVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getEmployeeCountForDashboard( employeeVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getEmployeeContractCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getEmployeeContractCountForDashboard( employeeContractVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getAttendanceLeaveDurationForDashboard( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getAttendanceLeaveDurationForDashboard( leaveHeaderVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getAttendanceOTDurationForDashboard( OTHeaderVO oTHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getAttendanceOTDurationForDashboard( oTHeaderVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public Map< String, Double > getSBAmountForDashboard( SBHeaderVO sbHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getSBAmountForDashboard( sbHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double sumPersonal = 0d;
      double sumCompany = 0d;
      SBDetailVO sbDetail = null;
      for ( Object obj : objects )
      {
         sbDetail = ( SBDetailVO ) obj;
         sumPersonal += ( Double.parseDouble( sbDetail.getAmountPersonal() ) );
         sumCompany += ( Double.parseDouble( sbDetail.getAmountCompany() ) );
      }
      map.put( "sumPersonal", sumPersonal );
      map.put( "sumCompany", sumCompany );
      sbDetail = null;
      return map;
   }

   @Override
   public Map< String, Double > getSBAdjAmountForDashboard( SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getSBAdjAmountForDashboard( sbAdjustmentHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double sumPersonal = 0d;
      double sumCompany = 0d;
      SBDetailVO sbDetail = null;
      for ( Object obj : objects )
      {
         // xml里面用的是detail来装的。只用到2个类通用字段
         sbDetail = ( SBDetailVO ) obj;
         sumPersonal += ( Double.parseDouble( sbDetail.getAmountPersonal() ) );
         sumCompany += ( Double.parseDouble( sbDetail.getAmountCompany() ) );
      }
      map.put( "sumPersonal", sumPersonal );
      map.put( "sumCompany", sumCompany );
      sbDetail = null;
      return map;
   }

   @Override
   public Map< String, Double > getCBAmountForDashboard( CBHeaderVO cbHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getCBAmountForDashboard( cbHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double amountSalesCost = 0d;
      double amountSalesPrice = 0d;
      CBDetailVO cbDetail = null;
      for ( Object obj : objects )
      {
         cbDetail = ( CBDetailVO ) obj;
         amountSalesCost += ( Double.parseDouble( cbDetail.getAmountSalesCost() ) );
         amountSalesPrice += ( Double.parseDouble( cbDetail.getAmountSalesPrice() ) );
      }
      map.put( "amountSalesCost", amountSalesCost );
      map.put( "amountSalesPrice", amountSalesPrice );
      cbDetail = null;
      return map;
   }

   @Override
   public Map< String, Double > getSettlementAmountForDashboard( ServiceContractVO serviceContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getSettlementAmountForDashboard( serviceContractVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double billAmountPersonal = 0d;
      double billAmountCompany = 0d;
      double costAmountPersonal = 0d;
      double costAmountCompany = 0d;
      OrderDetailVO orderDetailVO = null;
      for ( Object obj : objects )
      {
         orderDetailVO = ( OrderDetailVO ) obj;
         billAmountPersonal += ( Double.parseDouble( orderDetailVO.getBillAmountPersonal() ) );
         billAmountCompany += ( Double.parseDouble( orderDetailVO.getBillAmountCompany() ) );
         costAmountPersonal += ( Double.parseDouble( orderDetailVO.getCostAmountPersonal() ) );
         costAmountCompany += ( Double.parseDouble( orderDetailVO.getCostAmountCompany() ) );
      }
      map.put( "billAmountPersonal", billAmountPersonal );
      map.put( "billAmountCompany", billAmountCompany );
      map.put( "costAmountPersonal", costAmountPersonal );
      map.put( "costAmountCompany", costAmountCompany );
      orderDetailVO = null;
      return map;
   }

   @Override
   public Map< String, Double > getSettlementAdjAmountForDashboard( AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getSettlementAdjAmountForDashboard( adjustmentHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double billAmountCompany = 0d;
      double costAmountCompany = 0d;
      AdjustmentDetailVO detailVO = null;
      for ( Object obj : objects )
      {
         detailVO = ( AdjustmentDetailVO ) obj;
         billAmountCompany += ( Double.parseDouble( detailVO.getBillAmountCompany() ) );
         costAmountCompany += ( Double.parseDouble( detailVO.getCostAmountCompany() ) );
      }
      map.put( "billAmountCompany", billAmountCompany );
      map.put( "costAmountCompany", costAmountCompany );
      detailVO = null;
      return map;
   }

   @Override
   public Map< String, Double > getPaymentAmountForDashboard( PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getPaymentAmountForDashboard( paymentHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double addtionalBillAmountPersonal = 0d;
      double taxAmountPersonal = 0d;
      PaymentHeaderVO headerVO = null;
      for ( Object obj : objects )
      {
         headerVO = ( PaymentHeaderVO ) obj;
         addtionalBillAmountPersonal += Double.parseDouble( headerVO.getAddtionalBillAmountPersonal() );
         taxAmountPersonal += Double.parseDouble( headerVO.getTaxAmountPersonal() );
      }
      map.put( "addtionalBillAmountPersonal", addtionalBillAmountPersonal );
      map.put( "taxAmountPersonal", taxAmountPersonal );
      headerVO = null;
      return map;
   }

   @Override
   public Map< String, Double > getPaymentAdjAmountForDashboard( PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getPaymentAdjAmountForDashboard( paymentAdjustmentHeaderVO );
      final Map< String, Double > map = new HashMap< String, Double >();
      double addtionalBillAmountPersonal = 0d;
      double taxAmountPersonal = 0d;
      PaymentAdjustmentHeaderVO headerVO = null;
      for ( Object obj : objects )
      {
         headerVO = ( PaymentAdjustmentHeaderVO ) obj;
         addtionalBillAmountPersonal += Double.parseDouble( headerVO.getAddtionalBillAmountPersonal() );
         taxAmountPersonal += Double.parseDouble( headerVO.getTaxAmountPersonal() );
      }
      map.put( "addtionalBillAmountPersonal", addtionalBillAmountPersonal );
      map.put( "taxAmountPersonal", taxAmountPersonal );
      headerVO = null;
      return map;
   }

   @Override
   public int getEmployeeOnJobCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getEmployeeOnJobCountForDashboard( employeeContractVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getEmployeeNewCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getEmployeeNewCountForDashboard( employeeContractVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

   @Override
   public int getEmployeeDismissionCountForDashboard( EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objects = ( ( DashboardDao ) getDao() ).getEmployeeDismissionCountForDashboard( employeeContractVO );
      int sum = 0;
      for ( Object obj : objects )
      {
         sum += ( obj == null ? 0 : ( Integer ) obj );
      }
      return sum;
   }

}
