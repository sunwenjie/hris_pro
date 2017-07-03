package com.kan.hro.domain.biz.cb;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：DetailVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-11  
 */
public class CBDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -5574260224408003061L;

   // 商保从表Id
   private String detailId;

   // 商保主表Id
   private String headerId;

   // 科目Id
   private String itemId;

   // 科目编号
   private String itemNo;

   // 科目中文名
   private String nameZH;

   // 科目英文名
   private String nameEN;

   // 合计（采购成本）
   private String amountPurchaseCost;

   // 合计（销售成本）
   private String amountSalesCost;

   // 合计（销售价格）
   private String amountSalesPrice;

   // 所属月份
   private String monthly;

   // 描述
   private String description;

   /**
    * 
    *  For App
    */
   // 描述
   private String itemType;
   
   private String employeeId;
   private String employeeNameZH;
   private String employeeNameEN;

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
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

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.cb.detail.status" ) );
   }

   @Override
   public void update( final Object object )
   {
      final String ignoreProperties[] = { "detailId", "accountId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.amountPurchaseCost = "";
      this.amountSalesCost = "";
      this.amountSalesPrice = "";
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
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

   public String getAmountPurchaseCost()
   {
      return formatNumber( amountPurchaseCost );
   }

   public void setAmountPurchaseCost( String amountPurchaseCost )
   {
      this.amountPurchaseCost = amountPurchaseCost;
   }

   public String getAmountSalesCost()
   {
      return formatNumber( amountSalesCost );
   }

   public void setAmountSalesCost( String amountSalesCost )
   {
      this.amountSalesCost = amountSalesCost;
   }

   public String getAmountSalesPrice()
   {
      return formatNumber( amountSalesPrice );
   }

   public void setAmountSalesPrice( String amountSalesPrice )
   {
      this.amountSalesPrice = amountSalesPrice;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public final String getItemNo()
   {
      return itemNo;
   }

   public final void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

}
