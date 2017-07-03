package com.kan.hro.domain.biz.payment;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：PaymentDetailVO  
 * 类描述：  薪资科目信息
 * 创建人：Jack  
 * 创建时间：2013-11-23  
 */
public class PaymentDetailVO extends BaseVO
{
   // serialVersionUID
   private static final long serialVersionUID = 6077784650785066541L;

   // 薪酬从表Id
   private String paymentDetailId;

   // 薪酬主表Id
   private String paymentHeaderId;

   // 结算明细Id
   private String orderDetailId;

   // 科目Id
   private String itemId;

   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String nameZH;

   // 科目名称（英文）
   private String nameEN;

   // 基数（公司）
   private String baseCompany;

   // 基数（个人）
   private String basePersonal;

   // 比率（公司营收）
   private String billRateCompany;

   // 比率（个人收入）
   private String billRatePersonal;

   // 比率（公司成本）
   private String costRateCompany;

   // 比率（个人支出）
   private String costRatePersonal;

   // 固定金（公司营收）
   private String billFixCompany;

   // 固定金（个人收入）
   private String billFixPersonal;

   // 固定金（公司成本）
   private String costFixCompany;

   // 固定金（个人支出）
   private String costFixPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   private String description;

   /**
    * For App
    */
   // 批次ID
   private String batchId;

   // 科目类型
   private String itemType;

   private String employeeId;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( paymentDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.paymentHeaderId = null;
      this.orderDetailId = null;
      this.itemId = null;
      this.itemNo = null;
      this.nameZH = null;
      this.nameEN = null;
      this.baseCompany = null;
      this.basePersonal = null;
      this.billRateCompany = null;
      this.billRatePersonal = null;
      this.costRateCompany = null;
      this.costRatePersonal = null;
      this.billFixCompany = null;
      this.billFixPersonal = null;
      this.costFixCompany = null;
      this.costFixPersonal = null;
      this.billAmountCompany = null;
      this.billAmountPersonal = null;
      this.costAmountCompany = null;
      this.costAmountPersonal = null;
      this.addtionalBillAmountPersonal = null;
      this.description = null;
      super.setStatus( "0" );

   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) object;

      this.paymentHeaderId = paymentDetailVO.getPaymentHeaderId();
      this.orderDetailId = paymentDetailVO.getOrderDetailId();
      this.itemId = paymentDetailVO.getItemId();
      this.itemNo = paymentDetailVO.getItemNo();
      this.nameZH = paymentDetailVO.getNameZH();
      this.nameEN = paymentDetailVO.getNameEN();
      this.baseCompany = paymentDetailVO.getBaseCompany();
      this.basePersonal = paymentDetailVO.getBasePersonal();
      this.billRateCompany = paymentDetailVO.getBillRateCompany();
      this.billRatePersonal = paymentDetailVO.getBillRatePersonal();
      this.costRateCompany = paymentDetailVO.getCostRateCompany();
      this.costRatePersonal = paymentDetailVO.getCostRatePersonal();
      this.billFixCompany = paymentDetailVO.getBillFixCompany();
      this.billFixPersonal = paymentDetailVO.getBillFixPersonal();
      this.costFixCompany = paymentDetailVO.getCostFixCompany();
      this.costFixPersonal = paymentDetailVO.getCostFixPersonal();
      this.billAmountCompany = paymentDetailVO.getBillAmountCompany();
      this.billAmountPersonal = paymentDetailVO.getBillAmountPersonal();
      this.costAmountCompany = paymentDetailVO.getCostAmountCompany();
      this.costAmountPersonal = paymentDetailVO.getCostAmountPersonal();
      this.addtionalBillAmountPersonal = paymentDetailVO.getAddtionalBillAmountPersonal();
      this.description = paymentDetailVO.getDescription();
      super.setStatus( paymentDetailVO.getStatus() );
      super.setModifyBy( paymentDetailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getPaymentDetailId()
   {
      return paymentDetailId;
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
   }

   public void setPaymentDetailId( String paymentDetailId )
   {
      this.paymentDetailId = paymentDetailId;
   }

   public String getPaymentHeaderId()
   {
      return paymentHeaderId;
   }

   public void setPaymentHeaderId( String paymentHeaderId )
   {
      this.paymentHeaderId = paymentHeaderId;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
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

   public String getBillRateCompany()
   {
      return billRateCompany;
   }

   public void setBillRateCompany( String billRateCompany )
   {
      this.billRateCompany = billRateCompany;
   }

   public String getBillRatePersonal()
   {
      return billRatePersonal;
   }

   public void setBillRatePersonal( String billRatePersonal )
   {
      this.billRatePersonal = billRatePersonal;
   }

   public String getCostRateCompany()
   {
      return costRateCompany;
   }

   public void setCostRateCompany( String costRateCompany )
   {
      this.costRateCompany = costRateCompany;
   }

   public String getCostRatePersonal()
   {
      return costRatePersonal;
   }

   public void setCostRatePersonal( String costRatePersonal )
   {
      this.costRatePersonal = costRatePersonal;
   }

   public String getBillFixCompany()
   {
      return billFixCompany;
   }

   public void setBillFixCompany( String billFixCompany )
   {
      this.billFixCompany = billFixCompany;
   }

   public String getBillFixPersonal()
   {
      return billFixPersonal;
   }

   public void setBillFixPersonal( String billFixPersonal )
   {
      this.billFixPersonal = billFixPersonal;
   }

   public String getCostFixCompany()
   {
      return costFixCompany;
   }

   public void setCostFixCompany( String costFixCompany )
   {
      this.costFixCompany = costFixCompany;
   }

   public String getCostFixPersonal()
   {
      return costFixPersonal;
   }

   public void setCostFixPersonal( String costFixPersonal )
   {
      this.costFixPersonal = costFixPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public final String getAddtionalBillAmountPersonal()
   {
      return formatNumber( addtionalBillAmountPersonal );
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDecodeBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public String getDecodeBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public String getDecodeCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public String getDecodeCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   // 获得公司科目金额数量
   public String getAmountCompany()
   {
      return formatNumber( String.valueOf( Double.valueOf( getDecodeCostAmountCompany() ) ) ).replace( "-", "" );
   }

   // 获得个人科目金额数量
   public String getAmountPersonal()
   {
      //      return formatNumber( String.valueOf( Math.abs( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) ) );
      if ( "10284".equals( itemId ) )
         return formatNumber( String.valueOf( Float.valueOf( getDecodeBillAmountPersonal() ) - Float.valueOf( getDecodeCostAmountPersonal() ) ) );
      return formatNumber( String.valueOf( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) ).replace( "-", "" );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

   public void addBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

}
