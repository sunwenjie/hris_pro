package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PayslipTaxHeaderView extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 94578349562668992L;

   private String headerId;

   // 雇员ID
   private String employeeId;

   // 月份
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

   // 工资个税（合计）
   private String salaryTax;

   // 应发工资
   private String addtionalBillAmountPersonal;

   // 人民币收入额(additionalBillAmountPersonal + taxAgentAmountPersonal)
   private String revenue;

   // 扣除额
   private String deduction;

   private String num;

   // 天数
   private String numDay;

   // 所得项目代码
   private String itemCode;

   // 含税标志
   private String taxFlag;

   // 国家地区
   private String nationNality;

   private String itemGroupId;
   /**
    * For Application
    */
   // 个税申报城市
   private String cityId;

   // 代扣税工资
   private String taxAgentAmountPersonal;

   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.employeeId = "";
      this.monthly = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.orderId = "";
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
      this.revenue = "0";
      this.num = "0";
      this.numDay = "";
      this.deduction = "0";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // No Use
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      // 去除状态为“新建”的薪酬
      super.getStatuses().remove( 1 );
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
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
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

   public String getSalaryTax()
   {
      return salaryTax;
   }

   public void setSalaryTax( String salaryTax )
   {
      this.salaryTax = salaryTax;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   // 是否红色显示个税（为负值时显示红色）
   public boolean getIsWarning()
   {
      return Float.parseFloat( getTaxAmountPersonal() ) < 0;
   }

   // 获得个税合计
   public String getTaxAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   // 获得实发工资
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAddtionalBillAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() ) ) );
   }

   // 应税工资
   public String getTaxableSalary()
   {
      if ( formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) )
            + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) ) != null )
      {
         return formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) )
               + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) );
      }
      return formatNumber( "0" );
   }

   public String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
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

   // 应发工资
   public void addAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = String.valueOf( Double.valueOf( this.addtionalBillAmountPersonal ) + Double.valueOf( addtionalBillAmountPersonal ) );
   }

   public void addSalaryTax( String salaryTax )
   {
      this.salaryTax = String.valueOf( Double.valueOf( this.salaryTax ) + Double.valueOf( salaryTax ) );
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getRevenue()
   {
      return formatNumber( revenue );
   }

   public void setRevenue( String revenue )
   {
      this.revenue = revenue;
   }

   public String getDeduction()
   {
      return deduction;
   }

   public void setDeduction( String deduction )
   {
      this.deduction = deduction;
   }

   public String getNum()
   {
      num = "0";
      return num;
   }

   public void setNum( String num )
   {
      this.num = num;
   }

   public String getNumDay()
   {
      return numDay;
   }

   public void setNumDay( String numDay )
   {
      this.numDay = numDay;
   }

   public String getItemCode()
   {
      return itemCode;
   }

   public void setItemCode( String itemCode )
   {
      this.itemCode = itemCode;
   }

   public List< MappingVO > getCertificateTypes()
   {
      return certificateTypes;
   }

   public void setCertificateTypes( List< MappingVO > certificateTypes )
   {
      this.certificateTypes = certificateTypes;
   }

   public String getDecodeCertificateType()
   {
      return decodeField( certificateType, certificateTypes );
   }

   public String getTaxFlag()
   {
      return taxFlag;
   }

   public void setTaxFlag( String taxFlag )
   {
      this.taxFlag = taxFlag;
   }

   public String getNationNality()
   {
      return nationNality;
   }

   public void setNationNality( String nationNality )
   {
      this.nationNality = nationNality;
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
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

   public String getTaxAgentAmountPersonal()
   {
      return taxAgentAmountPersonal;
   }

   public void setTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = taxAgentAmountPersonal;
   }

   public void addTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = String.valueOf( Double.valueOf( this.taxAgentAmountPersonal == null ? "0" : this.taxAgentAmountPersonal )
            + Double.valueOf( taxAgentAmountPersonal == null ? "0" : taxAgentAmountPersonal ) );
   }

}
