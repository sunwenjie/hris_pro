package com.kan.hro.domain.biz.settlement;

import java.io.Serializable;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class OrderBillDetailView extends BaseVO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 10845218463623893L;

   private String itemId;

   private String billAmountCompany;

   private String costAmountPersonal;
   
   private String sbBillAmountCompany;

   private String sbCostAmountPersonal;
   
   private String personalSBBurden;

   private String base;

   private String contractId;

   private String employeeNameZH;

   private String employeeNameEN;

   private String employeeId;

   private String baseCompany;

   private String basePersonal;

   private String rateCompany;

   private String ratePersonal;

   private String clientNameZH;

   private String clientNameEN;

   private String sbheaderId;

   private String entityId;

   private String businessTypeId;
   
   private String isAdjustment;
   
   private String sbMonthly;
   
   private String monthly;

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getBillAmountCompany()
   {
      return billAmountCompany;
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   @Override
   public void reset() throws KANException
   {
   }

   @Override
   public void update( Object object ) throws KANException
   {

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

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getCostAmountPersonal()
   {
      return costAmountPersonal;
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getBase()
   {
      return base;
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseCompany()
   {
      return baseCompany;
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getBasePersonal()
   {
      return basePersonal;
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getRateCompany()
   {
      return rateCompany;
   }

   public void setRateCompany( String rateCompany )
   {
      this.rateCompany = rateCompany;
   }

   public String getRatePersonal()
   {
      return ratePersonal;
   }

   public void setRatePersonal( String ratePersonal )
   {
      this.ratePersonal = ratePersonal;
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

   public String getSbCostAmountPersonal()
   {
      return sbCostAmountPersonal;
   }

   public void setSbCostAmountPersonal( String sbCostAmountPersonal )
   {
      this.sbCostAmountPersonal = sbCostAmountPersonal;
   }

   public String getSbheaderId()
   {
      return sbheaderId;
   }

   public void setSbheaderId( String sbheaderId )
   {
      this.sbheaderId = sbheaderId;
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

   public String getSbBillAmountCompany()
   {
      return sbBillAmountCompany;
   }

   public void setSbBillAmountCompany( String sbBillAmountCompany )
   {
      this.sbBillAmountCompany = sbBillAmountCompany;
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public String getIsAdjustment()
   {
      return isAdjustment;
   }

   public void setIsAdjustment( String isAdjustment )
   {
      this.isAdjustment = isAdjustment;
   }

   public String getSbMonthly()
   {
      return sbMonthly;
   }

   public void setSbMonthly( String sbMonthly )
   {
      this.sbMonthly = sbMonthly;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

}
