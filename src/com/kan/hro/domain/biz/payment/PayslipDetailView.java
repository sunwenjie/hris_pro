package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：PaymentView  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2014-2-17  
 */
public class PayslipDetailView extends BaseVO
{
   // serialVersionUID
   private static final long serialVersionUID = 3551355206506124655L;

   /**
    * For View
    */

   // 主键标识
   private String headerId;

   // 科目Id
   private String itemId;

   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String nameZH;

   // 科目名称（英文）
   private String nameEN;

   // 科目类型
   private String itemType;

   // 雇员ID
   private String employeeId;

   // 服务协议ID
   private String contractId;

   // 薪酬月份
   private String monthly;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 订单Id
   private String orderId;

   // 供应商ID
   private String vendorId;

   // 供应商名称（中文）
   private String vendorNameZH;

   // 供应商名称（英文）
   private String vendorNameEN;

   // 详单
   private String orderDetailId;

   // 雇员姓名（中文）
   private String employeeNameZH;

   // 雇员姓名（英文）
   private String employeeNameEN;

   // 银行Id
   private String bankId;

   // 银行名称（中文）
   private String bankNameZH;

   // 银行名称（英文）
   private String bankNameEN;

   // 银行账户
   private String bankAccount;

   private String bankBranch;

   // 薪酬开始日期
   private String startDate;

   // 薪酬结束日期
   private String endDate;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 工资个税
   private String salaryTax;

   //年终奖个税
   private String annualBonusTax;

   //年终奖
   private String annualBonus;

   // 合同工资合计
   private String salaryBase;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   // 代扣税工资
   private String taxAgentAmountPersonal;

   /**
    * For Application
    */
   // 是否是导出
   private String isExport;

   // 财务编码
   private String clientNO;

   // 职位名称
   private String tempPositionIds;

   // 部门名称
   private String tempBranchIds;

   // 上级部门名称
   private String tempParentBranchIds;

   // 个税申报城市
   private String cityId;

   // 客户名（中文）
   private String clientNameZH;

   // 客户名（英文）
   private String clientNameEN;

   // 劳动合同模板ID
   private String templateId;

   // 开始月份
   private String monthlyBegin;

   // 截止月份
   private String monthlyEnd;

   // 员工信息自定义字段
   private String employeeRemark1;

   // 劳动合同自定义字段
   private String contractRemark1;

   // 合同开始时间
   private String contractStartDate;

   // 薪酬计算开始日
   private String circleStartDay;

   // 薪酬计算结束日
   private String circleEndDay;

   //货币类型
   private String currency;

   private String settlementBranch;

   // 首次工作时间
   private String startWorkDate;

   // 最后工作时间
   private String lastWorkDate;

   // 工资模板
   private List< MappingVO > bankTemplates = new ArrayList< MappingVO >();

   // 个税模板
   private List< MappingVO > taxTemplates = new ArrayList< MappingVO >();

   // 银行账户和entity对应
   private Map< String, String > BANK_ACCOUNT = new HashMap< String, String >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      bankTemplates = KANConstants.getKANAccountConstants( super.getAccountId() ).getBankTemplate( super.getLocale().getLanguage(), super.getCorpId() );
      bankTemplates.add( 0, super.getEmptyMappingVO() );
      taxTemplates = KANConstants.getKANAccountConstants( super.getAccountId() ).getTaxTemplate( super.getLocale().getLanguage(), super.getCorpId() );
      taxTemplates.add( 0, super.getEmptyMappingVO() );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      BANK_ACCOUNT.put( "93", "809534910838" );
      BANK_ACCOUNT.put( "91", "808371728838" );
      BANK_ACCOUNT.put( "99", "848428199838" );
      BANK_ACCOUNT.put( "95", "640115564838" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.itemType = "";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.employeeId = "";
      this.contractId = "";
      this.monthly = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.orderId = "";
      this.orderDetailId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.bankId = "";
      this.bankNameZH = "";
      this.bankNameEN = "";
      this.bankAccount = "";
      this.startDate = "";
      this.endDate = "";
      this.certificateType = "";
      this.certificateNumber = "";
      this.salaryTax = "";
      this.addtionalBillAmountPersonal = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // No Use
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
      return KANUtil.filterEmpty( orderId );
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

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getCertificateType()
   {
      return certificateType;
   }

   public void setCertificateType( String certificateType )
   {
      this.certificateType = certificateType;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public final String getBankNameZH()
   {
      return bankNameZH;
   }

   public final void setBankNameZH( String bankNameZH )
   {
      this.bankNameZH = bankNameZH;
   }

   public final String getBankNameEN()
   {
      return bankNameEN;
   }

   public final void setBankNameEN( String bankNameEN )
   {
      this.bankNameEN = bankNameEN;
   }

   public final String getBankName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getBankNameZH();
         }
         else
         {
            return this.getBankNameEN();
         }
      }
      else
      {
         return this.getBankNameZH();
      }
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
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

   public String getSalaryTax()
   {
      return salaryTax;
   }

   public void setSalaryTax( String salaryTax )
   {
      this.salaryTax = salaryTax;
   }

   public final String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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
      // 显示负数的：其他补贴、个税调整、五险一金（补缴）
      String[] noReplaceArray = new String[] {  "10217", "10219", "10221", "10223", "10225", "10227" };
      if ( ArrayUtils.contains( noReplaceArray, itemId ) )
      {
         return formatNumber( String.valueOf( Float.valueOf( getDecodeCostAmountCompany() ) ) );
      }
      return formatNumber( String.valueOf( Float.valueOf( getDecodeCostAmountCompany() ) ) ).replace( "-", "" );
   }

   public String getFormatAmountCompany()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Float.valueOf( getDecodeCostAmountCompany() ) ) ).replace( "-", "" );
   }

   // 获得个人科目金额数量
   public String getAmountPersonal()
   {
      // 显示负数的：其他补贴、个税调整、五险一金（补缴）
      String[] noReplaceArray = new String[] { "13", "10284", "10217", "10219", "10221", "10223", "10225", "10227" };
      if ( ArrayUtils.contains( noReplaceArray, itemId ) )
      {
         return formatNumber( String.valueOf( Float.valueOf( getDecodeBillAmountPersonal() ) - Float.valueOf( getDecodeCostAmountPersonal() ) ) );
      }
      return formatNumber( String.valueOf( Float.valueOf( getDecodeBillAmountPersonal() ) - Float.valueOf( getDecodeCostAmountPersonal() ) ) ).replace( "-", "" );
   }

   public String getFormatAmountPersonal()
   {
      //      return formatNumber( String.valueOf( Math.abs( Float.valueOf( getDecodeBillAmountPersonal() ) - Float.valueOf( getDecodeCostAmountPersonal() ) ) ) );
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Float.valueOf( getDecodeBillAmountPersonal() ) - Float.valueOf( getDecodeCostAmountPersonal() ) ) ).replace( "-", "" );
   }

   public List< MappingVO > getBankTemplates()
   {
      return bankTemplates;
   }

   public void setBankTemplates( List< MappingVO > bankTemplates )
   {
      this.bankTemplates = bankTemplates;
   }

   // 获得个税合计
   public String getTaxAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   // 获得应发工资
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) ) );
   }

   // 获得实发工资
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBeforeTaxSalary() ) - Double.valueOf( getTaxAmountPersonal() ) ) );
   }

   // 获得应发工资
   public String getFormatBeforeTaxSalary()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) ) );
   }

   // 获得实发工资
   public String getFormatAfterTaxSalary()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getBeforeTaxSalary() ) - Double.valueOf( getTaxAmountPersonal() ) ) );
   }

   public List< MappingVO > getTaxTemplates()
   {
      return taxTemplates;
   }

   public void setTaxTemplates( List< MappingVO > taxTemplates )
   {
      this.taxTemplates = taxTemplates;
   }

   public String getIsExport()
   {
      return isExport;
   }

   public void setIsExport( String isExport )
   {
      this.isExport = isExport;
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

   public void addAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      /* this.addtionalBillAmountPersonal = String.valueOf( Double.valueOf( this.addtionalBillAmountPersonal == null ? "0" : this.addtionalBillAmountPersonal )
             + Double.valueOf( this.addtionalBillAmountPersonal == null ? "0" : this.addtionalBillAmountPersonal ) );*/
      this.addtionalBillAmountPersonal = String.valueOf( ( KANUtil.filterEmpty( this.addtionalBillAmountPersonal ) != null ? Double.valueOf( this.addtionalBillAmountPersonal ) : 0 )
            + ( KANUtil.filterEmpty( addtionalBillAmountPersonal ) != null ? Double.valueOf( addtionalBillAmountPersonal ) : 0 ) );
   }

   public String getTempPositionIds()
   {
      return tempPositionIds;
   }

   public void setTempPositionIds( String tempPositionIds )
   {
      this.tempPositionIds = tempPositionIds;
   }

   public String getTempBranchIds()
   {
      return tempBranchIds;
   }

   public void setTempBranchIds( String tempBranchIds )
   {
      this.tempBranchIds = tempBranchIds;
   }

   public String getTempParentBranchIds()
   {
      return tempParentBranchIds;
   }

   public void setTempParentBranchIds( String tempParentBranchIds )
   {
      this.tempParentBranchIds = tempParentBranchIds;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getClientNO()
   {
      return clientNO;
   }

   public void setClientNO( String clientNO )
   {
      this.clientNO = clientNO;
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

   public String getTaxAgentAmountPersonal()
   {
      return formatNumber( taxAgentAmountPersonal );
   }

   public String getFormatTaxAgentAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( taxAgentAmountPersonal );
   }

   public void setTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = taxAgentAmountPersonal;
   }

   public String getBankBranch()
   {
      return bankBranch;
   }

   public void setBankBranch( String bankBranch )
   {
      this.bankBranch = bankBranch;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getCalValue()
   {
      return formatNumber( String.valueOf( Double.parseDouble( billAmountPersonal ) - Double.parseDouble( costAmountPersonal ) ) );
   }

   public String getFormatCalValue()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.parseDouble( billAmountPersonal ) - Double.parseDouble( costAmountPersonal ) ) );
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

   public String getMonthlyBegin()
   {
      return monthlyBegin;
   }

   public void setMonthlyBegin( String monthlyBegin )
   {
      this.monthlyBegin = monthlyBegin;
   }

   public String getMonthlyEnd()
   {
      return monthlyEnd;
   }

   public void setMonthlyEnd( String monthlyEnd )
   {
      this.monthlyEnd = monthlyEnd;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public String getContractStartDate()
   {
      return contractStartDate;
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getCircleEndDay()
   {
      return circleEndDay;
   }

   public void setCircleEndDay( String circleEndDay )
   {
      this.circleEndDay = circleEndDay;
   }

   public String getContractRemark1()
   {
      return contractRemark1;
   }

   public void setContractRemark1( String contractRemark1 )
   {
      this.contractRemark1 = contractRemark1;
   }

   public String getCircleStartDay()
   {
      return circleStartDay;
   }

   public void setCircleStartDay( String circleStartDay )
   {
      this.circleStartDay = circleStartDay;
   }

   public String getCurrency()
   {
      return currency;
   }

   public void setCurrency( String currency )
   {
      this.currency = currency;
   }

   public String getSalaryBase()
   {
      return formatNumber( salaryBase );
   }

   public void setSalaryBase( String salaryBase )
   {
      this.salaryBase = salaryBase;
   }

   public String getStartWorkDate()
   {
      return startWorkDate;
   }

   public void setStartWorkDate( String startWorkDate )
   {
      this.startWorkDate = startWorkDate;
   }

   public String getLastWorkDate()
   {
      return lastWorkDate;
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }

   public Map< String, String > getBANK_ACCOUNT()
   {
      return BANK_ACCOUNT;
   }

   public void setBANK_ACCOUNT( Map< String, String > bANK_ACCOUNT )
   {
      BANK_ACCOUNT = bANK_ACCOUNT;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public String getAnnualBonusTax()
   {
      return formatNumber( annualBonusTax );
   }

   public void setAnnualBonusTax( String annualBonusTax )
   {
      this.annualBonusTax = annualBonusTax;
   }

   public String getAnnualBonus()
   {
      return annualBonus;
   }

   public void setAnnualBonus( String annualBonus )
   {
      this.annualBonus = annualBonus;
   }

}
