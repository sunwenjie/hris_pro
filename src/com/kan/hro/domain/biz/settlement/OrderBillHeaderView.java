package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class OrderBillHeaderView extends BaseVO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 10463623845218893L;

   private String orderId;

   private String contractId;

   private String headerId;

   private String detailId;

   private String monthly;

   private String billAmountCompany;

   private String costAmountCompany;

   private String clientNameZH;

   private String clientNameEN;

   private String employeeNameZH;

   private String employeeNameEN;

   private String clientNO;

   private String itemId;

   private String entityId;

   private String businessTypeId;

   // for export excel

   private Map< String, OrderBillDetailView > detailMap = new HashMap< String, OrderBillDetailView >();

   public OrderBillHeaderView()
   {

   }

   public OrderBillHeaderView( String clientId, String orderId, String monthly, String status, String itemId )
   {
      super.setClientId( clientId );
      this.orderId = orderId;
      this.monthly = monthly;
      super.setStatus( status );
      this.itemId = itemId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      this.orderId = "";
      this.contractId = "";
      this.headerId = "";
      this.detailId = "";
      this.monthly = "";
      this.billAmountCompany = "";
      this.costAmountCompany = "";
      this.clientNameZH = "";
      this.clientNameEN = "";
      super.setStatus( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final OrderBillHeaderView billHeaderView = ( OrderBillHeaderView ) object;
      this.orderId = billHeaderView.getOrderId();
      this.contractId = billHeaderView.getContractId();
      this.headerId = billHeaderView.getHeaderId();
      this.detailId = billHeaderView.getDetailId();
      this.monthly = billHeaderView.getMonthly();
      this.billAmountCompany = billHeaderView.getBillAmountCompany();
      this.costAmountCompany = billHeaderView.getCostAmountCompany();
      this.clientNameZH = billHeaderView.getClientNameZH();
      this.clientNameEN = billHeaderView.getClientNameEN();
      this.businessTypeId = billHeaderView.getBusinessTypeId();
      this.entityId = billHeaderView.getEntityId();
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getBillAmountCompany()
   {
      return billAmountCompany;
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountCompany()
   {
      return costAmountCompany;
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   public String getEncodedMonthly() throws KANException
   {
      return encodedField( this.monthly );
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( this.orderId );
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public Map< String, OrderBillDetailView > getDetailMap()
   {
      return detailMap;
   }

   public void setDetailMap( Map< String, OrderBillDetailView > detailMap )
   {
      this.detailMap = detailMap;
   }

   public String getClientNO()
   {
      return clientNO;
   }

   public void setClientNO( String clientNO )
   {
      this.clientNO = clientNO;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

}
