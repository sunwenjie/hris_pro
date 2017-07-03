package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PayslipHeaderView extends BaseVO
{
   // Serial Version UID
   private static final long serialVersionUID = 5917628317712118027L;

   // 存放PaymentHeaderId或者AdjustmentHeaderId
   private String headerId;

   // 雇员ID
   private String employeeId;

   // 服务协议ID
   private String contractId;

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

   // 支行名称
   private String bankBranch;

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

   // 工资个税（合计），存放台帐或调整的Tax
   private String salaryTax;

   // 年终奖
   private String annualBonus;

   // 年终奖个税
   private String annualBonusTax;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   // 代扣税工资
   private String taxAgentAmountPersonal;

   /**
    * For Application
    */
   // 社保公司合计
   private String sbAmountCompany;

   // 社保个人合计
   private String sbAmountPersonal;

   // 社保扣款合计
   private String sbAmount;

   // 职位名称
   private String tempPositionIds;

   // 财务编码
   private String clientNO;

   // 部门名称
   private String tempBranchIds;

   // 上级部门名称
   private String tempParentBranchIds;

   // 劳务合同模板ID
   private String templateId;

   // 劳务合同模板名称
   private String templateName;

   // 个税申报城市
   private String cityId;

   //户口性质
   private String residencyType;

   //科目分组编号
   private String itemGroupId;

   //工资差额
   private String salaryBalance;

   //合同工资合计
   private String salaryBase;

   //货币类型
   private String currency;

   // 成本部门/ 营收部门
   private String settlementBranch;

   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   // 职位
   private List< MappingVO > positions = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   private List< MappingVO > banks = new ArrayList< MappingVO >();

   // 员工信息自定义字段
   private String employeeRemark1;

   // 劳动合同自定义字段
   private String contractRemark1;

   // 合同开始日期
   private String contractStartDate;

   // 薪酬计算开始日
   private String circleStartDay;

   // 薪酬计算结束日
   private String circleEndDay;

   // 首次工作时间
   private String startWorkDate;

   // 最后工作时间
   private String lastWorkDate;

   // 缓存第一次缴纳强基金数
   private String fisrtPayMPF;

   // 存放自定义字段
   private Map< String, String > dynaColumns = new HashMap< String, String >();

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
      this.employeeId = "";
      this.contractId = "";
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
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.salaryTax = "";
      this.addtionalBillAmountPersonal = "";
      this.itemGroupId = "";
      this.salaryBalance = "";
      this.orderDetailId = "";
      this.currency = "0";
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
      this.branchs = KANConstants.getKANAccountConstants( super.getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.banks = KANConstants.getKANAccountConstants( getAccountId() ).getBanks( getLocale().getLanguage(), getCorpId() );
   }

   public final String getHeaderId()
   {
      return headerId;
   }

   public final void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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

   public final String getAddtionalBillAmountPersonal()
   {
      return addtionalBillAmountPersonal;
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public String getDecodeBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public String getDecodeCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public String getDecodeCurrency()
   {
      return getCurrencyByOrderId( currency );
   }

   public String getFormatTaxAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   public String getFormatBeforeTaxSalary()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getAddtionalBillAmountPersonal() ) ) );
   }

   // 应发工资
   public String getAccruedWages()
   {
      return formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) ) - Double.valueOf( formatNumber( this.getSbAmount() ) ) ) );
   }

   public String getFormatAccruedWages()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAddtionalBillAmountPersonal() ) ) - Double.valueOf( formatNumber( this.getSbAmount() ) ) ) );
   }

   // 应税工资
   public String getTaxableSalary()
   {
      if ( formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) ) != null )
      {
         return formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) );
      }
      return formatNumber( "0" );
   }

   public String getFormatTaxableSalary()
   {
      if ( formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) ) != null )
      {
         return getCurrencyByOrderId( currency )
               + formatNumber( String.valueOf( Double.valueOf( formatNumber( this.getAccruedWages() ) ) + Double.valueOf( formatNumber( this.getTaxAgentAmountPersonal() ) ) ) );
      }
      return getCurrencyByOrderId( currency ) + formatNumber( "0" );
   }

   // 工会费个人(仅限iClick)
   public String getUnionFeePersonal()
   {
      return formatNumber( "0" );
   }

   // 工会费个人(仅限iClick)加上金额符号
   public String getFormatUnionFeePersonal()
   {
      return getCurrencyByOrderId( currency ) + getUnionFeePersonal();
   }

   // 工会费公司(仅限iClick)
   public String getUnionFeeCompany()
   {
      // 指定法务实体
      final String[] entityArray = new String[] { "101", "103", "105" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return formatNumber( "0" );
      }

      return formatNumber( String.valueOf( Double.valueOf( getTaxableSalary() ) * 0.02 ) );
   }

   // 工会费公司(仅限iClick)加上金额符号
   public String getFormatUnionFeeCompany()
   {
      return getCurrencyByOrderId( currency ) + getUnionFeeCompany();
   }

   // 获取强基金起缴日
   @SuppressWarnings("unchecked")
   private String getMPFPayDay()
   {
      // 劳动合同自定义字段
      final Map< String, Object > contractDefineColumnMap = new HashMap< String, Object >();
      contractDefineColumnMap.putAll( JSONObject.fromObject( contractRemark1 ) );
      return contractDefineColumnMap.get( "mpfqijiaori" ) == null ? null : contractDefineColumnMap.get( "mpfqijiaori" ).toString();
   }

   private String getSalaryEndDate()
   {
      if ( KANUtil.filterEmpty( lastWorkDate ) != null )
      {
         String tmpMonthly = KANUtil.getMonthlyByCondition( circleEndDay, lastWorkDate );
         if ( monthly.equals( tmpMonthly ) )
         {
            return lastWorkDate;
         }
      }

      return KANUtil.getEndDate( monthly, circleEndDay );
   }

   // 获取是否为第一次缴纳强基金（仅限iClick）
   public boolean isFirstPayMPF()
   {
      // 强基金起缴日
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
         return false;

      // 薪资计算开始日
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      // 薪资计算结束日
      final String salaryEndDate = getSalaryEndDate();
      // 符合第一次缴纳条件
      if ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) )
            && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) )
         return true;

      return false;
   }

   // 获取共薪月数
   public int getSumSalaryMonthNum()
   {
      int num = 0;
      if ( isFirstPayMPF() )
      {
         final String mpfStartDate = getMPFPayDay();
         final Calendar tempCalendar = KANUtil.createCalendar( mpfStartDate );
         // 60天后需要交纳强基金日期
         tempCalendar.add( Calendar.DATE, -59 );

         String tmpMonthly = "";
         for ( int i = 1; i <= 2; i++ )
         {
            tmpMonthly = KANUtil.getMonthly( monthly, -i );
            final String salaryStartDate = KANUtil.getStartDate( tmpMonthly, circleStartDay );
            final String salaryEndDate = getSalaryEndDate();

            if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( tempCalendar.getTime() )
                  || ( KANUtil.getDays( tempCalendar.getTime() ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( tempCalendar.getTime() ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
            {
               num++;
            }
         }
      }

      return num;
   }

   // 强基金公司(仅限iClick)
   // 公司部分每月都需要缴纳，第一次供薪等于之前的总和（前一月/前两月，取缓存）
   // 强基金（公司）= 应税工资  * 0.05 > 1500 ? 1500 : 应税工资  * 0.05
   public String getMPFCompany()
   {
      // 指定法务实体
      final String[] entityArray = new String[] { "91", "93", "95", "99" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return "0";
      }

      // 强基金起缴日
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
      {
         return formatNumber( "0" );
      }

      // 初实话返回值
      double result = 0;

      // 不在缴纳范围
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      final String salaryEndDate = getSalaryEndDate();
      if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( mpfStartDate ) )
            || ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
      {
         // 如果是第一次缴纳，需要综合前一月/前两月的缴纳金额
         if ( isFirstPayMPF() && fisrtPayMPF != null )
         {
            result = result + Double.valueOf( fisrtPayMPF );
         }

         result = result + ( Double.valueOf( getTaxableSalary() ) * 0.05 > 1500 ? 1500 : Double.valueOf( getTaxableSalary() ) * 0.05 );
      }

      return formatNumber( String.valueOf( result ) );
   }

   // 强基金个人(仅限iClick)
   // 入职60天以内无需缴纳，60天后正常缴纳
   // 强基金（个人）= 应税工资  < 7100 ? 0 else if 应税工资  >30000 then 1500 else 0.05*应税工资  
   public String getMPFPersonal()
   {
      // 指定法务实体
      final String[] entityArray = new String[] { "91", "93", "95", "99" };
      if ( !ArrayUtils.contains( entityArray, entityId ) )
      {
         return "0";
      }

      // 强基金起缴日
      final String mpfStartDate = getMPFPayDay();
      if ( mpfStartDate == null )
      {
         return formatNumber( "0" );
      }

      // 不在缴纳范围
      final String salaryStartDate = KANUtil.getStartDate( monthly, circleStartDay );
      final String salaryEndDate = getSalaryEndDate();

      if ( KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( mpfStartDate ) )
            || ( KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) >= KANUtil.getDays( KANUtil.createDate( salaryStartDate ) ) && KANUtil.getDays( KANUtil.createDate( mpfStartDate ) ) <= KANUtil.getDays( KANUtil.createDate( salaryEndDate ) ) ) )
      {
         double tempTaxableSalary = Double.valueOf( getTaxableSalary() );
         if ( tempTaxableSalary < 7100 )
         {
            return formatNumber( "0" );
         }
         else if ( tempTaxableSalary > 30000 )
         {
            return formatNumber( "1500" );
         }
         else
         {
            return formatNumber( String.valueOf( tempTaxableSalary * 0.05 ) );
         }
      }

      return formatNumber( "0" );
   }

   // 强基金公司(仅限iClick)加上金额符号
   public String getFormatMPFCompany()
   {
      return getCurrencyByOrderId( currency ) + getMPFCompany();
   }

   // 强基金个人(仅限iClick)加上金额符号
   public String getFormatMPFPersonal()
   {
      return getCurrencyByOrderId( currency ) + getMPFPersonal();
   }

   public String getSbAmount()
   {
      return sbAmount;
   }

   public void setSbAmount( String sbAmount )
   {
      this.sbAmount = sbAmount;
   }

   public void addSbAmount( String sbAmount )
   {
      this.sbAmount = String.valueOf( Double.valueOf( this.sbAmount == null ? "0" : this.sbAmount ) + Double.valueOf( sbAmount == null ? "0" : sbAmount ) );
   }

   public String getFormatAfterTaxSalary()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
                  - Double.valueOf( getAnnualBonusTax() ) ) );
   }

   // 获得实发工资(iClick)
   public String getFormatIClickAfterTaxSalary()
   {
      return getCurrencyByOrderId( currency )
            + formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
                  - Double.valueOf( getAnnualBonusTax() ) - Double.valueOf( getMPFCompany() ) ) );
   }

   // 获得个人科目金额数量
   public String getAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) );
   }

   public String getFormatAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( String.valueOf( Double.valueOf( getDecodeBillAmountPersonal() ) - Double.valueOf( getDecodeCostAmountPersonal() ) ) );
   }

   public void addBillAmountCompany( String billAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.billAmountCompany ) == null )
      {
         this.billAmountCompany = "0";
      }
      this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.billAmountPersonal ) == null )
      {
         this.billAmountPersonal = "0";
      }
      this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.costAmountCompany ) == null )
      {
         this.costAmountCompany = "0";
      }
      this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.costAmountPersonal ) == null )
      {
         this.costAmountPersonal = "0";
      }
      this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
   }

   public void addSbAmountCompany( String sbAmountCompany )
   {
      if ( KANUtil.filterEmpty( this.sbAmountCompany ) == null )
      {
         this.sbAmountCompany = "0";
      }
      this.sbAmountCompany = String.valueOf( Double.valueOf( this.sbAmountCompany ) + Double.valueOf( sbAmountCompany ) );
   }

   public void addSbAmountPersonal( String sbAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.sbAmountPersonal ) == null )
      {
         this.sbAmountPersonal = "0";
      }
      this.sbAmountPersonal = String.valueOf( Double.valueOf( this.sbAmountPersonal ) + Double.valueOf( sbAmountPersonal ) );
   }

   public void addSalaryTax( String salaryTax )
   {
      if ( KANUtil.filterEmpty( this.salaryTax ) == null )
      {
         this.salaryTax = "0";
      }
      this.salaryTax = String.valueOf( Double.valueOf( this.salaryTax ) + Double.valueOf( salaryTax ) );
   }

   public void addTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      if ( KANUtil.filterEmpty( this.taxAgentAmountPersonal ) == null )
      {
         this.taxAgentAmountPersonal = "0";
      }
      this.taxAgentAmountPersonal = String.valueOf( Double.valueOf( this.taxAgentAmountPersonal ) + Double.valueOf( taxAgentAmountPersonal ) );
   }

   public void addAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
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

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   // decode职位名称
   public String getDecodeTempPositionIds()
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( tempPositionIds ) != null && positions != null && positions.size() > 0 )
      {
         for ( String positionId : tempPositionIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( positionId, positions );
            }
            else
            {
               returnStr = returnStr + "、" + decodeField( positionId, positions );
            }
         }
      }

      return returnStr;
   }

   public String getDecodeJobRole() throws KANException
   {
      if ( KANUtil.filterEmpty( contractRemark1 ) == null )
         return "";
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE" );
      final JSONObject o = JSONObject.fromObject( contractRemark1 );
      if ( o != null && o.get( "jobrole" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "13365" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "jobrole" ).toString(), mappingVOs );
      }

      return "";
   }

   // decode部门名称
   public String getDecodeTempBranchIds()
   {
      return decodeBranch( tempBranchIds );
   }

   // decode上级部门名称
   public String getDecodeTempParentBranchIds()
   {
      return decodeBranch( tempParentBranchIds );
   }

   // decode法务实体
   public String getDecodeEntity()
   {
      return decodeField( entityId, entitys );
   }

   // decode法务实体
   public String getDecodeEntityId()
   {
      return getDecodeEntity();
   }

   public String decodeBranch( final String branchIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( branchIds ) != null && branchs != null && branchs.size() > 0 )
      {
         for ( String branchId : branchIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( branchId, branchs, true );
            }
            else
            {
               returnStr = returnStr + "、" + decodeField( branchId, branchs, true );
            }
         }
      }

      return returnStr;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getClientNO()
   {
      return clientNO;
   }

   public void setClientNO( String clientNO )
   {
      this.clientNO = clientNO;
   }

   public String getFormatTaxAgentAmountPersonal()
   {
      return getCurrencyByOrderId( currency ) + formatNumber( taxAgentAmountPersonal );
   }

   public void setTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = taxAgentAmountPersonal;
   }

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

   public String getDecodeBankId()
   {
      return decodeField( bankId, banks );
   }

   public String getBankBranch()
   {
      return bankBranch;
   }

   public void setBankBranch( String bankBranch )
   {
      this.bankBranch = bankBranch;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
   }

   public String getSalaryBalance()
   {
      return formatNumber( salaryBalance );
   }

   public void setSalaryBalance( String salaryBalance )
   {
      this.salaryBalance = salaryBalance;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getTemplateName()
   {
      return templateName;
   }

   public void setTemplateName( String templateName )
   {
      this.templateName = templateName;
   }

   public String getDecodeTemplateId() throws KANException
   {
      return decodeField( this.templateId, KANConstants.getKANAccountConstants( getAccountId() ).getLaborContractTemplates( getLocale().getLanguage(), getCorpId() ) );
   }

   public String getParentBranchs()
   {
      String result = "";
      String positionId = "";
      if ( KANUtil.filterEmpty( this.tempPositionIds ) != null )
      {
         if ( tempPositionIds.contains( "," ) )
         {
            positionId = tempPositionIds.split( "," )[ 0 ];
         }
         else
         {
            positionId = tempPositionIds;
         }
      }
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final PositionVO positionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( positionId );
         final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( super.getAccountId() ).getParentBranchVOsByBranchId( positionVO.getBranchId() );
         if ( branchVOs != null && branchVOs.size() > 0 )
         {
            for ( BranchVO branchVO : branchVOs )
            {
               result += branchVO.getNameZH() + ">>";
            }
         }
      }
      return KANUtil.filterEmpty( result ) == null ? "" : result.substring( 0, result.length() - 2 );
   }

   public String getSbAmountCompany()
   {
      return formatNumber( sbAmountCompany );
   }

   public void setSbAmountCompany( String sbAmountCompany )
   {
      this.sbAmountCompany = sbAmountCompany;
   }

   public String getSbAmountPersonal()
   {
      return formatNumber( sbAmountPersonal ).replace( "-", "" );
   }

   public void setSbAmountPersonal( String sbAmountPersonal )
   {
      this.sbAmountPersonal = sbAmountPersonal;
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

   @SuppressWarnings("unchecked")
   public Map< String, String > getDynaColumns()
   {
      if ( KANUtil.filterEmpty( employeeRemark1 ) != null )
      {
         dynaColumns.putAll( JSONObject.fromObject( employeeRemark1 ) );
      }
      return dynaColumns;
   }

   public void setDynaColumns( Map< String, String > dynaColumns )
   {
      this.dynaColumns = dynaColumns;
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

   public String getFisrtPayMPF()
   {
      return fisrtPayMPF;
   }

   public void setFisrtPayMPF( String fisrtPayMPF )
   {
      this.fisrtPayMPF = fisrtPayMPF;
   }

   public String getLastWorkDate()
   {
      return lastWorkDate;
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   // 成本部门
   public String getDecodeSettlementBranch()
   {
      return decodeField( this.getSettlementBranch(), this.branchs, true );
   }

   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() ) );
   }

   public void setAnnualBonusTax( String annualBonusTax )
   {
      this.annualBonusTax = annualBonusTax;
   }

   public void setAnnualBonus( String annualBonus )
   {
      this.annualBonus = annualBonus;
   }

   /**====================各种工资项Start======================**/
   // 获得税前工资
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAddtionalBillAmountPersonal() ) ) );
   }

   // 获取年终奖
   public String getAnnualBonus()
   {
      return formatNumber( annualBonus );
   }

   // 获得代扣税工资
   public final String getTaxAgentAmountPersonal()
   {
      return formatNumber( taxAgentAmountPersonal );
   }

   // 获得个税合计
   public String getTaxAmountPersonal()
   {
      return formatNumber( String.valueOf( Double.valueOf( getSalaryTax() ) ) );
   }

   // 获得年终奖个税  
   public String getAnnualBonusTax()
   {
      return formatNumber( annualBonusTax );
   }

   /***
    * 实发工资 = 税前工资 - 个税 - 年终奖（不含年终奖噢）
    * @return 实发工资
    */
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
            - Double.valueOf( getAnnualBonus() ) ) );
   }

   /***
    * 实发年终奖 = 年终奖 - 年终奖个税 
    * @return 实发年终奖
    */
   public String getAfterTaxAnnualBonus()
   {
      return formatNumber( super.getRemark5() );
      //return formatNumber( String.valueOf( Double.valueOf( getAnnualBonus() ) - Double.valueOf( getAnnualBonusTax() ) ) );
   }

   // 获得实发工资(iClick)
   public String getIClickAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getAfterTaxSalary() ) - Double.valueOf( getMPFPersonal() ) ) );
   }

}
