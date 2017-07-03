package com.kan.hro.domain.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/**  
 * 项目名称：HRO_V1  
 * 类名称：DetailVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class OrderDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 1851831971740080709L;

   // 订单从表Id
   private String orderDetailId;

   // 服务协议Id
   private String contractId;

   // 主表Id
   private String headerId;

   // 明细Id
   private String detailId;

   // 明细类型
   private String detailType;

   // 科目Id
   private String itemId;

   // 科目Id
   private String itemNo;

   // 科目中文名
   private String nameZH;

   // 科目英文名
   private String nameEN;

   // 直接输入的值或Base From的结果值
   private String base;

   // 数量（暂时不计算）
   private String quantity;

   // 折扣（暂时不计算）
   private String discount;

   // 倍率（暂时不计算）
   private String multiple;

   // 基数（公司）
   private String sbBaseCompany;

   // 基数（个人）
   private String sbBasePersonal;

   // 比率（个人收入）
   private String billRatePersonal;

   // 比率（公司营收）
   private String billRateCompany;

   // 比率（个人支出）
   private String costRatePersonal;

   // 比率（公司成本）
   private String costRateCompany;

   // 固定金（个人收入）
   private String billFixPersonal;

   // 固定金（公司营收）
   private String billFixCompany;

   // 固定金（个人支出）
   private String costFixPersonal;

   // 固定金（公司成本）
   private String costFixCompany;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 税收（实缴）
   private String taxAmountActual;

   // 税收（成本）
   private String taxAmountCost;

   // 税收（销售）
   private String taxAmountSales;

   // 描述
   private String description;

   /**
    * For App
    */
   // 批次ID
   private String batchId;

   // 订单ID
   private String orderHeaderId;

   // 服务协议ID
   private String employeeContractId;

   private String sbBillAmountCompany;

   private String sbCostAmountPersonal;

   private String sbHeaderId;

   private String personalSBBurden;
   
   // 是否是调整数据
   private String isAdjustment;
   
   private String sbMonthly;

   // Monthly
   private String monthly;

   // ListRender里面取值从该字段，里面存放的其实是billAmountCompany
   private String amountPersonal;

   private String personalSBBurdenValues = "0";

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final OrderDetailVO orderDetailVO = ( OrderDetailVO ) object;
      this.contractId = orderDetailVO.getContractId();
      this.headerId = orderDetailVO.getHeaderId();
      this.detailId = orderDetailVO.getDetailId();
      this.detailType = orderDetailVO.getDetailType();
      this.itemId = orderDetailVO.getItemId();
      this.itemNo = orderDetailVO.getItemNo();
      this.nameZH = orderDetailVO.getNameZH();
      this.nameEN = orderDetailVO.getNameEN();
      this.base = orderDetailVO.getBase();
      this.quantity = orderDetailVO.getQuantity();
      this.discount = orderDetailVO.getDiscount();
      this.multiple = orderDetailVO.getMultiple();
      this.sbBaseCompany = orderDetailVO.getSbBaseCompany();
      this.sbBasePersonal = orderDetailVO.getSbBasePersonal();
      this.billRatePersonal = orderDetailVO.getBillRatePersonal();
      this.billRateCompany = orderDetailVO.getBillRateCompany();
      this.costRatePersonal = orderDetailVO.getCostRatePersonal();
      this.costRateCompany = orderDetailVO.getCostRateCompany();
      this.billFixPersonal = orderDetailVO.getBillFixPersonal();
      this.billFixCompany = orderDetailVO.getBillFixCompany();
      this.costFixPersonal = orderDetailVO.getCostFixPersonal();
      this.costFixCompany = orderDetailVO.getCostFixCompany();
      this.billAmountPersonal = orderDetailVO.getBillAmountPersonal();
      this.billAmountCompany = orderDetailVO.getBillAmountCompany();
      this.costAmountPersonal = orderDetailVO.getCostAmountPersonal();
      this.costAmountCompany = orderDetailVO.getCostAmountCompany();
      this.taxAmountActual = orderDetailVO.getTaxAmountActual();
      this.taxAmountCost = orderDetailVO.getTaxAmountCost();
      this.taxAmountSales = orderDetailVO.getTaxAmountSales();
      this.description = orderDetailVO.getDescription();
      super.setStatus( orderDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.headerId = "";
      this.detailId = "";
      this.detailType = "";
      this.itemId = "";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.base = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "";
      this.sbBaseCompany = "";
      this.sbBasePersonal = "";
      this.billRatePersonal = "";
      this.billRateCompany = "";
      this.costRatePersonal = "";
      this.costRateCompany = "";
      this.billFixPersonal = "";
      this.billFixCompany = "";
      this.costFixPersonal = "";
      this.costFixCompany = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.taxAmountActual = "";
      this.taxAmountCost = "";
      this.taxAmountSales = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderDetailId );
   }

   // BatchId编码
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // OrderHeaderId编码
   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // ContractId编码
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getBase()
   {
      return base;
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getQuantity()
   {
      return quantity;
   }

   public void setQuantity( String quantity )
   {
      this.quantity = quantity;
   }

   public String getDiscount()
   {
      return discount;
   }

   public void setDiscount( String discount )
   {
      this.discount = discount;
   }

   public String getMultiple()
   {
      return multiple;
   }

   public void setMultiple( String multiple )
   {
      this.multiple = multiple;
   }

   public String getBillRatePersonal()
   {
      return billRatePersonal;
   }

   public void setBillRatePersonal( String billRatePersonal )
   {
      this.billRatePersonal = billRatePersonal;
   }

   public String getBillRateCompany()
   {
      return billRateCompany;
   }

   public void setBillRateCompany( String billRateCompany )
   {
      this.billRateCompany = billRateCompany;
   }

   public String getCostRatePersonal()
   {
      return costRatePersonal;
   }

   public void setCostRatePersonal( String costRatePersonal )
   {
      this.costRatePersonal = costRatePersonal;
   }

   public String getCostRateCompany()
   {
      return costRateCompany;
   }

   public void setCostRateCompany( String costRateCompany )
   {
      this.costRateCompany = costRateCompany;
   }

   public String getBillFixPersonal()
   {
      return billFixPersonal;
   }

   public void setBillFixPersonal( String billFixPersonal )
   {
      this.billFixPersonal = billFixPersonal;
   }

   public String getBillFixCompany()
   {
      return billFixCompany;
   }

   public void setBillFixCompany( String billFixCompany )
   {
      this.billFixCompany = billFixCompany;
   }

   public String getCostFixPersonal()
   {
      return costFixPersonal;
   }

   public void setCostFixPersonal( String costFixPersonal )
   {
      this.costFixPersonal = costFixPersonal;
   }

   public String getCostFixCompany()
   {
      return costFixCompany;
   }

   public void setCostFixCompany( String costFixCompany )
   {
      this.costFixCompany = costFixCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getTaxAmountActual()
   {
      return taxAmountActual;
   }

   public void setTaxAmountActual( String taxAmountActual )
   {
      this.taxAmountActual = taxAmountActual;
   }

   public String getTaxAmountCost()
   {
      return taxAmountCost;
   }

   public void setTaxAmountCost( String taxAmountCost )
   {
      this.taxAmountCost = taxAmountCost;
   }

   public String getTaxAmountSales()
   {
      return taxAmountSales;
   }

   public void setTaxAmountSales( String taxAmountSales )
   {
      this.taxAmountSales = taxAmountSales;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public void addBillAmountCompany( final String billAmountCompany )
   {
      if ( this.billAmountCompany == null || this.billAmountCompany.trim().equals( "" ) )
      {
         this.billAmountCompany = billAmountCompany;
      }
      else
      {
         this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
      }
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      if ( this.billAmountPersonal == null || this.billAmountPersonal.trim().equals( "" ) )
      {
         this.billAmountPersonal = billAmountPersonal;
      }
      else
      {
         this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
      }
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      if ( this.costAmountCompany == null || this.costAmountCompany.trim().equals( "" ) )
      {
         this.costAmountCompany = costAmountCompany;
      }
      else
      {
         this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
      }
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      if ( this.costAmountPersonal == null || this.costAmountPersonal.trim().equals( "" ) )
      {
         this.costAmountPersonal = costAmountPersonal;
      }
      else
      {
         this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
      }
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public final String getItemNo()
   {
      return itemNo;
   }

   public final void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public final String getEmployeeContractId()
   {
      return employeeContractId;
   }

   public final void setEmployeeContractId( String employeeContractId )
   {
      this.employeeContractId = employeeContractId;
   }

   public final String getMonthly()
   {
      return monthly;
   }

   public final void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public final String getDetailId()
   {
      return detailId;
   }

   public final void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public final String getDetailType()
   {
      return detailType;
   }

   public final void setDetailType( String detailType )
   {
      this.detailType = detailType;
   }

   public final String getHeaderId()
   {
      return headerId;
   }

   public final void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public final String getSbBaseCompany()
   {
      return sbBaseCompany;
   }

   public final void setSbBaseCompany( String sbBaseCompany )
   {
      this.sbBaseCompany = sbBaseCompany;
   }

   public final String getSbBasePersonal()
   {
      return sbBasePersonal;
   }

   public final void setSbBasePersonal( String sbBasePersonal )
   {
      this.sbBasePersonal = sbBasePersonal;
   }

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getSbCostAmountPersonal()
   {
      return sbCostAmountPersonal;
   }

   public void setSbCostAmountPersonal( String sbCostAmountPersonal )
   {
      this.sbCostAmountPersonal = sbCostAmountPersonal;
   }

   public String getSbHeaderId()
   {
      return sbHeaderId;
   }

   public void setSbHeaderId( String sbHeaderId )
   {
      this.sbHeaderId = sbHeaderId;
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

   public String getPersonalSBBurdenValues()
   {
      return personalSBBurdenValues;
   }

   public void setPersonalSBBurdenValues( String personalSBBurdenValues )
   {
      this.personalSBBurdenValues = personalSBBurdenValues;
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

}
