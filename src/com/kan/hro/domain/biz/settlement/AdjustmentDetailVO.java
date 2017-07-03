package com.kan.hro.domain.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class AdjustmentDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -57341527980474746L;

   /**
    * For DB
    */

   // 调整从表Id
   private String adjustmentDetailId;

   // 调整主表Id
   private String adjustmentHeaderId;

   // 科目Id
   private String itemId;

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

   // 月份（例如2013/9）
   private String monthly;

   // 描述
   private String description;

   /**
    * For Application
    */
   private String itemNo;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) object;
      this.itemId = adjustmentDetailVO.getItemId();
      this.billAmountPersonal = adjustmentDetailVO.getBillAmountPersonal();
      this.billAmountCompany = adjustmentDetailVO.getBillAmountCompany();
      this.costAmountPersonal = adjustmentDetailVO.getCostAmountPersonal();
      this.costAmountCompany = adjustmentDetailVO.getCostAmountCompany();
      this.monthly = adjustmentDetailVO.getMonthly();
      this.description = adjustmentDetailVO.getDescription();
      super.setStatus( adjustmentDetailVO.getStatus() );
      super.setModifyBy( adjustmentDetailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.adjustmentHeaderId = "";
      this.itemId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.base = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "";
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
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentDetailId );
   }

   public String getOrderDetailId()
   {
      return adjustmentDetailId;
   }

   public void setOrderDetailId( String adjustmentDetailId )
   {
      this.adjustmentDetailId = adjustmentDetailId;
   }

   public String getContractId()
   {
      return adjustmentHeaderId;
   }

   public void setContractId( String adjustmentHeaderId )
   {
      this.adjustmentHeaderId = adjustmentHeaderId;
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

   public String getAdjustmentDetailId()
   {
      return adjustmentDetailId;
   }

   public void setAdjustmentDetailId( String adjustmentDetailId )
   {
      this.adjustmentDetailId = adjustmentDetailId;
   }

   public String getAdjustmentHeaderId()
   {
      return adjustmentHeaderId;
   }

   public void setAdjustmentHeaderId( String adjustmentHeaderId )
   {
      this.adjustmentHeaderId = adjustmentHeaderId;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
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
