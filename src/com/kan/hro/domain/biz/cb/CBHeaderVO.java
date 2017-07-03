package com.kan.hro.domain.biz.cb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：HeaderVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-9-12  
 */
public class CBHeaderVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -7782351239615635338L;

   // 商保主表Id
   private String headerId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 批次Id
   private String batchId;

   // 客户编号
   private String clientNO;

   // 客户中文名
   private String clientNameZH;

   // 客户英文名
   private String clientNameEN;

   // 订单Id
   private String orderId;

   // 服务协议Id
   private String contractId;

   // 雇员Id
   private String employeeId;

   // 雇员编号
   private String employeeNo;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 雇员商保方案Id
   private String employeeCBId;

   // 雇员工作地点
   private String workPlace;

   // 性别，从称呼转译
   private String gender;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 户籍性质
   private String residencyType;

   // 户籍城市
   private String residencyCityId;

   // 户籍地址
   private String residencyAddress;

   // 最高学历
   private String highestEducation;

   // 婚姻状况
   private String maritalStatus;

   // 雇员状态
   private String employStatus;

   // 商保状态
   private String cbStatus;

   // 起购日期
   private String startDate;

   // 退购日期
   private String endDate;

   // 入职日期（跟服务协议开始时间一致）
   private String onboardDate;

   // 离职日期
   private String resignDate;

   // 所属月份
   private String monthly;

   // 描述
   private String description;

   // 商保中文名
   private String employeeCBNameZH;

   // 商保英文名
   private String employeeCBNameEN;

   // 服务协议开始日期
   private String contractStartDate;

   // 服务协议结束日期
   private String contractEndDate;

   // 服务协议所属部门
   private String contractBranch;

   // 服务协议所属人
   private String contractOwner;

   /**
    * For Application
    */
   // 商保状态数组
   private String[] cbStatusArray = new String[] {};

   // 合计（采购成本）
   private String amountPurchaseCost;
   
   // 销售成本汇总
   private String amountSalesCost;

   // 销售价格汇总
   private String amountSalesPrice;
   
   

   // 包含科目数
   private String countItemId;

   // 额外状态 - 社保方案明细的最小状态
   private String additionalStatus;

   // 雇员状态
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   // 商保状态
   private List< MappingVO > cbStatuses = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 证件类型
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();

   // 证件类型
   private List< MappingVO > residencyTypes = new ArrayList< MappingVO >();

   // 证件类型
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 有效商保方案
   private List< MappingVO > commercialBenefitSolutions = new ArrayList< MappingVO >();

   // 截至月份 - 数据提取使用
   private String monthlyLimit;

   // 商保方案ID
   private String cbId;

   /**
    * 服务协议层用（服务协议包含商保方案数）
    */
   private String countHeaderId;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.setEmployStatuses( KANUtil.getMappings( getLocale(), "business.employee.statuses" ) );
      this.setCbStatuses( KANUtil.getMappings( getLocale(), "business.employee.contract.cb.statuses" ) );
      this.cbStatuses.add( 0, super.getEmptyMappingVO() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      this.residencyTypes = KANUtil.getMappings( this.getLocale(), "sys.sb.residency" );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.monthlies.add( 0, super.getEmptyMappingVO() );
      this.commercialBenefitSolutions = KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );
      this.commercialBenefitSolutions.add( 0, super.getEmptyMappingVO() );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.cb.header.status" ) );

   }

   @Override
   public void update( final Object object )
   {
      final String ignoreProperties[] = { "headerId", "accountId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.batchId = "";
      this.orderId = "";
      this.contractId = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.employeeCBId = "";
      this.workPlace = "";
      this.gender = "0";
      this.certificateType = "";
      this.certificateNumber = "";
      this.residencyType = "";
      this.residencyCityId = "0";
      this.residencyAddress = "";
      this.highestEducation = "0";
      this.maritalStatus = "0";
      this.employStatus = "0";
      this.cbStatus = "";
      this.startDate = "";
      this.endDate = "";
      this.onboardDate = "";
      this.resignDate = "";
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   // 编码批次ID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // 编码服务协议ID
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // 获得雇员状态
   public String getDecodeEmployStatus() throws KANException
   {
      return decodeField( employStatus, employStatuses );
   }

   // 订单ID编码
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // 雇员ID编码
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // 获得商保状态
   public String getDecodeCbStatus() throws KANException
   {
      return decodeField( cbStatus, cbStatuses );
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

   // 获得所属部门名称
   public String getDecodeBranch() throws KANException
   {
      return decodeField( this.contractBranch, KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( super.getLocale().getLanguage() ) );
   }

   // 获得所属人姓名
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.contractOwner );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
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

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
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

   public String getEmployeeName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeNameZH();
         }
         else
         {
            return this.getEmployeeNameEN();
         }
      }
      else
      {
         return this.getEmployeeNameZH();
      }
   }

   public String getEmployeeCBId()
   {
      return employeeCBId;
   }

   public void setEmployeeCBId( String employeeCBId )
   {
      this.employeeCBId = employeeCBId;
   }

   public String getWorkPlace()
   {
      return workPlace;
   }

   public void setWorkPlace( String workPlace )
   {
      this.workPlace = workPlace;
   }

   public String getGender()
   {
      return gender;
   }

   public void setGender( String gender )
   {
      this.gender = gender;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getDecodeResidencyType()
   {
      return decodeField( this.residencyType, this.residencyTypes );
   }

   public String getResidencyCityId()
   {
      return residencyCityId;
   }

   public void setResidencyCityId( String residencyCityId )
   {
      this.residencyCityId = residencyCityId;
   }

   public String getDecodeResidencyCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( residencyCityId, super.getLocale().getLanguage() );
   }

   public String getResidencyAddress()
   {
      return residencyAddress;
   }

   public void setResidencyAddress( String residencyAddress )
   {
      this.residencyAddress = residencyAddress;
   }

   public String getHighestEducation()
   {
      return highestEducation;
   }

   public void setHighestEducation( String highestEducation )
   {
      this.highestEducation = highestEducation;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getOnboardDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.onboardDate ) );
   }

   public void setOnboardDate( String onboardDate )
   {
      this.onboardDate = onboardDate;
   }

   public String getResignDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.resignDate ) );
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
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

   public String getCbStatus()
   {
      return cbStatus;
   }

   public void setCbStatus( String cbStatus )
   {
      this.cbStatus = cbStatus;
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public List< MappingVO > getCbStatuses()
   {
      return cbStatuses;
   }

   public void setCbStatuses( List< MappingVO > cbStatuses )
   {
      this.cbStatuses = cbStatuses;
   }

   public String getMonthlyLimit()
   {
      return monthlyLimit;
   }

   public void setMonthlyLimit( String monthlyLimit )
   {
      this.monthlyLimit = monthlyLimit;
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

   public String getAmountSalesCost()
   {
      return amountSalesCost;
   }

   public void setAmountSalesCost( String amountSalesCost )
   {
      this.amountSalesCost = amountSalesCost;
   }

   public String getDecodeAmountSalesCost()
   {
      return formatNumber( amountSalesCost );
   }

   public String getAmountSalesPrice()
   {
      return amountSalesPrice;
   }

   public void setAmountSalesPrice( String amountSalesPrice )
   {
      this.amountSalesPrice = amountSalesPrice;
   }

   public String getDecodeAmountSalesPrice()
   {
      return formatNumber( amountSalesPrice );
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
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

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
   }

   public String getClientName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getClientNameZH();
         }
         else
         {
            return this.getClientNameEN();
         }
      }
      else
      {
         return this.getClientNameZH();
      }
   }

   public String getEmployeeCBNameZH()
   {
      return employeeCBNameZH;
   }

   public void setEmployeeCBNameZH( String employeeCBNameZH )
   {
      this.employeeCBNameZH = employeeCBNameZH;
   }

   public String getEmployeeCBNameEN()
   {
      return employeeCBNameEN;
   }

   public void setEmployeeCBNameEN( String employeeCBNameEN )
   {
      this.employeeCBNameEN = employeeCBNameEN;
   }

   public String getEmployeeCBName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeCBNameZH();
         }
         else
         {
            return this.getEmployeeCBNameEN();
         }
      }
      else
      {
         return this.getEmployeeCBNameZH();
      }
   }

   public String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractStartDate ) );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractEndDate ) );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public String getContractBranch()
   {
      return contractBranch;
   }

   public void setContractBranch( String contractBranch )
   {
      this.contractBranch = contractBranch;
   }

   public String getContractOwner()
   {
      return contractOwner;
   }

   public void setContractOwner( String contractOwner )
   {
      this.contractOwner = contractOwner;
   }

   public String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public final String getDecodeAdditionalStatus()
   {
      return decodeField( this.additionalStatus, KANUtil.getMappings( this.getLocale(), "business.cb.header.status" ) );
   }

   public String getDecodeGender()
   {
      return decodeField( this.gender, KANUtil.getMappings( this.getLocale(), "salutation" ) );
   }

   public String getCertificateType()
   {
      return certificateType;
   }

   public void setCertificateType( String certificateType )
   {
      this.certificateType = certificateType;
   }

   public String getDecodeCertificateType()
   {
      return decodeField( this.certificateType, this.certificateTypes );
   }

   public String getMaritalStatus()
   {
      return maritalStatus;
   }

   public void setMaritalStatus( String maritalStatus )
   {
      this.maritalStatus = maritalStatus;
   }

   public String getDecodeMaritalStatus()
   {
      return decodeField( this.maritalStatus, KANUtil.getMappings( this.getLocale(), "security.staff.marital.statuses" ) );
   }

   public String getCbId()
   {
      return cbId;
   }

   public void setCbId( String cbId )
   {
      this.cbId = cbId;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public List< MappingVO > getCertificateTypes()
   {
      return certificateTypes;
   }

   public void setCertificateTypes( List< MappingVO > certificateTypes )
   {
      this.certificateTypes = certificateTypes;
   }

   public List< MappingVO > getResidencyTypes()
   {
      return residencyTypes;
   }

   public void setResidencyTypes( List< MappingVO > residencyTypes )
   {
      this.residencyTypes = residencyTypes;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public List< MappingVO > getCommercialBenefitSolutions()
   {
      return commercialBenefitSolutions;
   }

   public void setCommercialBenefitSolutions( List< MappingVO > commercialBenefitSolutions )
   {
      this.commercialBenefitSolutions = commercialBenefitSolutions;
   }

   public String[] getCbStatusArray()
   {
      return cbStatusArray;
   }

   public void setCbStatusArray( String[] cbStatusArray )
   {
      this.cbStatusArray = cbStatusArray;
   }

   public String getAmountPurchaseCost()
   {
      return amountPurchaseCost;
   }

   public void setAmountPurchaseCost( String amountPurchaseCost )
   {
      this.amountPurchaseCost = amountPurchaseCost;
   }
   public String getDecodeAmountPurchaseCost()
   {
      return formatNumber( amountPurchaseCost );
   }
}
