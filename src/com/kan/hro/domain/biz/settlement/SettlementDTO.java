package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.sb.SpecialDTO;

/**
  * 类名称：SettlementDTO  
  * 创建人：Kevin  
  * 创建时间：2013-12-03  
  */
public class SettlementDTO implements Serializable, SpecialDTO< Object, List< ? > >
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 7046362359416158893L;

   // ClientOrderHeaderVO
   private ClientOrderHeaderVO clientOrderHeaderVO;

   // EmployeeContractVO
   private EmployeeContractVO employeeContractVO;

   // TimesheetHeaderVO
   private TimesheetHeaderVO timesheetHeaderVO;

   // ServiceContractVO
   private ServiceContractVO serviceContractVO;

   // OrderDetailVO List
   private List< OrderDetailVO > orderDetailVOs = new ArrayList< OrderDetailVO >();
   
   // for 结算单
   private String isAdjustment;

   public final ClientOrderHeaderVO getClientOrderHeaderVO()
   {
      return clientOrderHeaderVO;
   }

   public final void setClientOrderHeaderVO( ClientOrderHeaderVO clientOrderHeaderVO )
   {
      this.clientOrderHeaderVO = clientOrderHeaderVO;
   }

   public final EmployeeContractVO getEmployeeContractVO()
   {
      return employeeContractVO;
   }

   public final void setEmployeeContractVO( EmployeeContractVO employeeContractVO )
   {
      this.employeeContractVO = employeeContractVO;
   }

   public final TimesheetHeaderVO getTimesheetHeaderVO()
   {
      return timesheetHeaderVO;
   }

   public final void setTimesheetHeaderVO( TimesheetHeaderVO timesheetHeaderVO )
   {
      this.timesheetHeaderVO = timesheetHeaderVO;
   }

   public ServiceContractVO getServiceContractVO()
   {
      return serviceContractVO;
   }

   public void setServiceContractVO( ServiceContractVO serviceContractVO )
   {
      this.serviceContractVO = serviceContractVO;
   }

   public List< OrderDetailVO > getOrderDetailVOs()
   {
      return orderDetailVOs;
   }

   public void setOrderDetailVOs( List< OrderDetailVO > orderDetailVOs )
   {
      this.orderDetailVOs = orderDetailVOs;
   }

   @Override
   public Object getHeaderVO()
   {
      return serviceContractVO;
   }

   @Override
   public List< ? > getDetailVOs()
   {
      return orderDetailVOs;
   }

   @Override
   public Map< ?, ? > getFlags()
   {
      return null;
   }

   public String getIsAdjustment()
   {
      return isAdjustment;
   }

   public void setIsAdjustment( String isAdjustment )
   {
      this.isAdjustment = isAdjustment;
   }

}
