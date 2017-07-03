package com.kan.hro.domain.biz.finance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class SystemInvoiceDetailVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -8413904783843881082L;
   
   // 订单从表Id
   private String invoiceDetailId;

   // 主表Id
   private String invoiceId;

   // 明细Id
   private String detailId;

   // 科目Id
   private String itemId;

   // 科目Id
   private String itemNo;

   // 科目中文名
   private String nameZH;

   // 科目英文名
   private String nameEN;


   // 合计（公司营收）
   private String billAmountCompany;


   // 合计（公司成本）
   private String costAmountCompany;



   // 税收
   private String taxAmount;

   // 描述
   private String description;

   /**
    * For App
    */
   // 批次ID
   private String batchId;

   // Monthly
   private String monthly;
   

   // 当前页面类型标记(Preview, Split, Merge)
   private String pageFlag;
   
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }
   
   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( invoiceDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.invoiceId = "";
      this.detailId = "";
      this.itemId = "";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.taxAmount="";
      this.billAmountCompany = "";
      this.costAmountCompany = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SystemInvoiceDetailVO systemInvoiceDetailVO = ( SystemInvoiceDetailVO ) object;
      this.invoiceId = systemInvoiceDetailVO.getInvoiceId();
      this.detailId = systemInvoiceDetailVO.getDetailId();
      this.itemId = systemInvoiceDetailVO.getItemId();
      this.itemNo = systemInvoiceDetailVO.getItemNo();
      this.nameZH = systemInvoiceDetailVO.getNameZH();
      this.nameEN = systemInvoiceDetailVO.getNameEN();
      this.billAmountCompany=systemInvoiceDetailVO.getBillAmountCompany();
      this.costAmountCompany=systemInvoiceDetailVO.getBillAmountCompany();
      this.taxAmount=systemInvoiceDetailVO.getTaxAmount();
      super.setStatus( systemInvoiceDetailVO.getStatus() );
      super.setModifyBy( systemInvoiceDetailVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( systemInvoiceDetailVO.getCorpId() );
   }

   // BatchId编码
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   public String getInvoiceDetailId()
   {
      return invoiceDetailId;
   }

   public void setInvoiceDetailId( String invoiceDetailId )
   {
      this.invoiceDetailId = invoiceDetailId;
   }

   public String getInvoiceId()
   {
      return invoiceId;
   }

   public void setInvoiceId( String invoiceId )
   {
      this.invoiceId = invoiceId;
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
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

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany);
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany);
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getTaxAmount()
   {
      return taxAmount;
   }

   public void setTaxAmount( String taxAmount )
   {
      this.taxAmount = taxAmount;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }
   
   
}
