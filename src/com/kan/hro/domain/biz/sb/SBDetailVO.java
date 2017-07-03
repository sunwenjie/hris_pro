package com.kan.hro.domain.biz.sb;

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
public class SBDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 7064174007246987791L;

   // 社保从表Id
   private String detailId;

   // 社保主表Id
   private String headerId;

   // 科目Id
   private String itemId;

   // 科目编号
   private String itemNo;

   // 科目中文名
   private String nameZH;

   // 科目英文名
   private String nameEN;

   // 基数（个人）
   private String basePersonal;

   // 基数（公司）
   private String baseCompany;

   // 比率（个人）
   private String ratePersonal;

   // 比率（公司）
   private String rateCompany;

   // 固定金（个人）
   private String fixPersonal;

   // 固定金（公司）
   private String fixCompany;

   // 合计（个人）
   private String amountPersonal;

   // 合计（公司）
   private String amountCompany;

   // 账单月份
   private String monthly;

   // 所属月份
   private String accountMonthly;

   // 描述
   private String description;

   /**
    * 
    *	 For App
    */
   // 派送协议ID
   private String contractId;

   // 科目类型
   private String itemType;

   // 客户编号
   private String clientNo;

   // 客户名称（英文）
   private String clientNameZH;

   // 客户名称（中文）
   private String clientNameEN;

   // 雇员ID
   private String employeeId;

   // 雇员姓名（中）
   private String employeeNameZH;

   // 雇员姓名（英）
   private String employeeNameEN;

   // 社保卡号
   private String sbNumber;

   // 利息
   private String interest;

   // 滞纳金
   private String overdueFine;

   // 社保方案
   private String employeeSBId;

   // 标示
   private boolean updataed;

   // 法务实体ID
   private String entityId;

   // 业务类型ID
   private String businessTypeId;

   // 订单Id
   private String orderId;

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
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.detail.status" ) );
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
      this.basePersonal = "";
      this.baseCompany = "";
      this.ratePersonal = "";
      this.rateCompany = "";
      this.fixPersonal = "";
      this.fixCompany = "";
      this.amountPersonal = "";
      this.amountCompany = "";
      this.monthly = "";
      this.accountMonthly = "";
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

   public String getBasePersonal()
   {
      return basePersonal;
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getDecodeBasePersonal()
   {
      return formatNumber( basePersonal );
   }

   public String getBaseCompany()
   {
      return baseCompany;
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getDecodeBaseCompany()
   {
      return formatNumber( baseCompany );
   }

   public String getRatePersonal()
   {
      return ratePersonal;
   }

   public void setRatePersonal( String ratePersonal )
   {
      this.ratePersonal = ratePersonal;
   }

   public String getDecodeRatePersonal()
   {
      return formatNumber( ratePersonal );
   }

   public String getRateCompany()
   {
      return rateCompany;
   }

   public void setRateCompany( String rateCompany )
   {
      this.rateCompany = rateCompany;
   }

   public String getDecodeRateCompany()
   {
      return formatNumber( rateCompany );
   }

   public String getFixPersonal()
   {
      return fixPersonal;
   }

   public void setFixPersonal( String fixPersonal )
   {
      this.fixPersonal = fixPersonal;
   }

   public String getDecodeFixPersonal()
   {
      return formatNumber( fixPersonal );
   }

   public String getFixCompany()
   {
      return fixCompany;
   }

   public void setFixCompany( String fixCompany )
   {
      this.fixCompany = fixCompany;
   }

   public String getDecodeFixCompany()
   {
      return formatNumber( fixCompany );
   }

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getDecodeAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public String getFormatAmountPersonal()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountPersonal ) );
   }

   public String getAmountCompany()
   {
      return amountCompany;
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getDecodeAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public String getFormatAmountCompany()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountCompany ) );
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

   public final String getAccountMonthly()
   {
      return accountMonthly;
   }

   public final void setAccountMonthly( String accountMonthly )
   {
      this.accountMonthly = accountMonthly;
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

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public void addAmountCompany( String amountCompany )
   {
      this.amountCompany = String.valueOf( Double.valueOf( this.amountCompany ) + Double.valueOf( amountCompany ) );
   }

   public void addAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = String.valueOf( Double.valueOf( this.amountPersonal ) + Double.valueOf( amountPersonal ) );
   }

   public boolean isUpdataed()
   {
      return updataed;
   }

   public void setUpdataed( boolean updataed )
   {
      this.updataed = updataed;
   }

   public String getClientNo()
   {
      return clientNo;
   }

   public void setClientNo( String clientNo )
   {
      this.clientNo = clientNo;
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

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getInterest()
   {
      return interest;
   }

   public void setInterest( String interest )
   {
      this.interest = interest;
   }

   public String getOverdueFine()
   {
      return overdueFine;
   }

   public void setOverdueFine( String overdueFine )
   {
      this.overdueFine = overdueFine;
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

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

}
