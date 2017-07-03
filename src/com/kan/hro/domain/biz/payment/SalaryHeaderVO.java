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

/**
 * 项目名称：HRO_V1 类名称：SalaryHeaderVO 类描述： 创建人：Kevin 创建时间：2014-01-17
 */
public class SalaryHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7409890123688725095L;

   // 工资主表Id
   private String salaryHeaderId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 客户名称（中文）
   private String clientNameZH;

   // 客户名称（英文）
   private String clientNameEN;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 雇员Id
   private String employeeId;

   // 雇员姓名（中文）
   private String employeeNameZH;

   // 雇员姓名（英文）
   private String employeeNameEN;

   // 薪酬开始日期
   private String startDate;

   // 薪酬结束日期
   private String endDate;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 银行Id
   private String bankId;

   // 银行名称（中文）
   private String bankNameZH;

   // 银行名称（英文）
   private String bankNameEN;

   // 银行账户
   private String bankAccount;

   // 合计（公司营收）
   private String billAmountCompany;

   // 合计（个人收入）
   private String billAmountPersonal;

   // 合计（公司成本）
   private String costAmountCompany;

   // 合计（个人支出）
   private String costAmountPersonal;

   // 合计（个税）
   private String taxAmountPersonal;

   // 附加合计（个人收入），用于税前加的金额
   private String addtionalBillAmountPersonal;

   // 税前工资
   private String estimateSalary;

   // 税后收入，用于倒算税的导入 
   private String actualSalary;

   // 薪酬月份
   private String monthly;

   private String description;

   // For Application
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   private String batchId;

   private String importExcelName;

   private String owner;

   private String itemTypes;

   private List< MappingVO > batchStatuses = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( salaryHeaderId );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      this.setBatchStatuses( KANUtil.getMappings( getLocale(), "def.common.batch.importsalary.status" ) );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }

      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }

      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.clientNameZH = "";
      this.clientNameEN = "";
      this.orderId = "";
      this.contractId = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.certificateType = "0";
      this.certificateNumber = "";
      this.bankId = "";
      this.bankNameZH = "";
      this.bankNameEN = "";
      this.bankAccount = "";
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.taxAmountPersonal = "";
      this.addtionalBillAmountPersonal = "";
      this.actualSalary = "";
      this.estimateSalary = "";
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
      super.setRemark1( "" );
      super.setRemark2( "" );
      super.setRemark3( "" );
      super.setRemark4( "" );
      super.setRemark5( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) object;
      this.entityId = salaryHeaderVO.getEntityId();
      this.businessTypeId = salaryHeaderVO.getBusinessTypeId();
      this.clientNameZH = salaryHeaderVO.getClientNameZH();
      this.clientNameEN = salaryHeaderVO.getClientNameEN();
      this.orderId = salaryHeaderVO.getOrderId();
      this.contractId = salaryHeaderVO.getContractId();
      this.employeeId = salaryHeaderVO.getEmployeeId();
      this.employeeNameZH = salaryHeaderVO.getEmployeeNameZH();
      this.employeeNameEN = salaryHeaderVO.getEmployeeNameEN();
      this.startDate = salaryHeaderVO.getStartDate();
      this.endDate = salaryHeaderVO.getEndDate();
      this.certificateType = salaryHeaderVO.getCertificateType();
      this.certificateNumber = salaryHeaderVO.getCertificateNumber();
      this.bankId = salaryHeaderVO.getBankId();
      this.bankNameZH = salaryHeaderVO.getBankNameZH();
      this.bankNameEN = salaryHeaderVO.getBankNameEN();
      this.bankAccount = salaryHeaderVO.getBankAccount();
      this.billAmountCompany = salaryHeaderVO.getBillAmountCompany();
      this.billAmountPersonal = salaryHeaderVO.getBillAmountPersonal();
      this.costAmountCompany = salaryHeaderVO.getCostAmountCompany();
      this.costAmountPersonal = salaryHeaderVO.getCostAmountPersonal();
      this.taxAmountPersonal = salaryHeaderVO.getTaxAmountPersonal();
      this.addtionalBillAmountPersonal = salaryHeaderVO.getAddtionalBillAmountPersonal();
      this.actualSalary = salaryHeaderVO.getActualSalary();
      this.estimateSalary = salaryHeaderVO.getEstimateSalary();
      this.monthly = salaryHeaderVO.getMonthly();
      this.description = salaryHeaderVO.getDescription();
      super.setDeleted( salaryHeaderVO.getDeleted() );
      super.setStatus( salaryHeaderVO.getStatus() );
      super.setRemark1( salaryHeaderVO.getRemark1() );
      super.setRemark2( salaryHeaderVO.getRemark2() );
      super.setRemark3( salaryHeaderVO.getRemark3() );
      super.setRemark4( salaryHeaderVO.getRemark4() );
      super.setRemark5( salaryHeaderVO.getRemark5() );
      super.setCorpId( salaryHeaderVO.getCorpId() );
   }

   public String getSalaryHeaderId()
   {
      return salaryHeaderId;
   }

   public void setSalaryHeaderId( String salaryHeaderId )
   {
      this.salaryHeaderId = salaryHeaderId;
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

   public String getOrderId()
   {
      return orderId;
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

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( this.employeeId );
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( this.orderId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( this.contractId );
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

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public String getBankNameZH()
   {
      return bankNameZH;
   }

   public void setBankNameZH( String bankNameZH )
   {
      this.bankNameZH = bankNameZH;
   }

   public String getBankNameEN()
   {
      return bankNameEN;
   }

   public void setBankNameEN( String bankNameEN )
   {
      this.bankNameEN = bankNameEN;
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getBillAmountCompany()
   {
      return formatNumber(billAmountCompany  );
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
      return formatNumber(costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber(costAmountPersonal);
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getTaxAmountPersonal()
   {
      return formatNumber( taxAmountPersonal );
   }

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
   }

   public String getAddtionalBillAmountPersonal()
   {
      return formatNumber(addtionalBillAmountPersonal);
   }

   public void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
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

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   // 获得法务实体名称
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.entityId, this.entitys );
   }

   // 获得业务类型名称
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, this.businessTypes );
   }

   // 获得应发工资
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getActualSalary() ) + Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) ) );
   }

   // 获得实发工资
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBeforeTaxSalary() ) - Double.valueOf( getTaxAmountPersonal() ) ) );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getImportExcelName()
   {
      return importExcelName;
   }

   public void setImportExcelName( String importExcelName )
   {
      this.importExcelName = importExcelName;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public final String getItemTypes()
   {
      return itemTypes;
   }

   public final void setItemTypes( String itemTypes )
   {
      this.itemTypes = itemTypes;
   }

   public List< MappingVO > getBatchStatuses()
   {
      return batchStatuses;
   }

   public void setBatchStatuses( List< MappingVO > batchStatuses )
   {
      this.batchStatuses = batchStatuses;
   }

   public String getEstimateSalary()
   {
      return formatNumber( estimateSalary );
   }

   public void setEstimateSalary( String estimateSalary )
   {
      this.estimateSalary = estimateSalary;
   }

   // 获得实发工资 - 倒算税使用
   public String getActualSalary()
   {
      return formatNumber( actualSalary );
   }

   public void setActualSalary( String actualSalary )
   {
      this.actualSalary = actualSalary;
   }

}
