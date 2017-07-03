package com.kan.hro.domain.biz.sb;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.web.actions.biz.sb.SBBillViewAction;

public class SBBillDetailView extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = -2261964558728525946L;

   /**
    * For View
    */
   // ID
   private String headerId;

   // 社保调整ID
   private String adjustmentHeaderId;

   // 科目ID
   private String itemId;

   // 科目类型
   private String itemType;

   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String nameZH;

   // 科目名称（英文）
   private String nameEN;

   // 基数（个人）
   private String basePersonal;

   // 基数（公司）
   private String baseCompany;

   // 比率（个人）
   private String ratePersonal;

   // 比率（公司）
   private String rateCompany;

   // 合计（公司）
   private String amountCompany;

   // 合计（个人）
   private String amountPersonal;

   // 薪酬月份
   private String monthly;

   // 所属月份
   private String accountMonthly;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 批次ID
   private String batchId;

   // 法务实体 
   private String entityId;

   // 业务类型
   private String businessTypeId;

   // 客户ID
   private String clientId;

   // 客户编号
   private String clientNo;

   // 客户名称（中文）
   private String clientNameZH;

   // 客户名称（英文）
   private String clientNameEN;

   // 订单ID
   private String orderId;

   // 雇员ID
   private String employeeId;

   // 雇员姓名（中文）
   private String employeeNameZH;

   // 雇员姓名（英文）
   private String employeeNameEN;

   // 证件号码
   private String certificateNumber;

   // 雇员状态
   private String employStatus;

   // 服务协议ID
   private String contractId;

   // 服务协议状态
   private String contractStatus;

   // 雇员社保方案Id
   private String employeeSBId;

   // 雇员社保方案名称（中文）
   private String employeeSBNameZH;

   // 雇员社保方案名称（英文）
   private String employeeSBNameEN;

   // 医保卡帐号
   private String medicalNumber;

   // 社保卡帐号
   private String sbNumber;

   // 公积金帐号
   private String fundNumber;

   // 社保方案ID
   private String sbSolutionId;

   // 雇员社保状态
   private String sbStatus;

   // 缴纳状态
   private String flag;

   // 社保类型
   private String sbType;

   // 供应商Id
   private String vendorId;

   // 供应商名称（中文）
   private String vendorNameZH;

   // 供应商名称（英文）
   private String vendorNameEN;

   // 加保日期（起缴年月）
   private String startDate;

   // 退保日期
   private String endDate;

   /**
    * For Application
    */
   // 社保状态数组
   private String[] sbStatusArray = new String[] {};

   // 分组字段
   private String groupColumn;

   // 伪状态（社保调整用到）
   private String pseudoStatus;
   
   // 员工基本信息Remark1
   private String employeeRemark1;

   // 劳动合同Remark1
   private String contractRemark1;

   @Override
   public String getEncodedId() throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // No Use
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SBBillDetailView sbBillDetailView = ( SBBillDetailView ) object;
      this.itemId = sbBillDetailView.getItemId();
      this.itemType = sbBillDetailView.getItemType();
      this.itemNo = sbBillDetailView.getItemNo();
      this.nameZH = sbBillDetailView.getNameZH();
      this.nameEN = sbBillDetailView.getNameEN();
      this.monthly = sbBillDetailView.getMonthly();
      this.entityId = sbBillDetailView.getEntityId();
      this.businessTypeId = sbBillDetailView.getBusinessTypeId();
      this.orderId = sbBillDetailView.getOrderId();
      this.employeeId = sbBillDetailView.getEmployeeId();
      this.employeeNameZH = sbBillDetailView.getEmployeeNameZH();
      this.employeeNameEN = sbBillDetailView.getEmployeeNameEN();
      this.certificateNumber = sbBillDetailView.getCertificateNumber();
      this.contractId = sbBillDetailView.getContractId();
      this.employeeSBId = sbBillDetailView.getEmployeeSBId();
      this.employeeSBNameZH = sbBillDetailView.getEmployeeSBNameZH();
      this.employeeSBNameEN = sbBillDetailView.getEmployeeSBNameEN();
      this.medicalNumber = sbBillDetailView.getMedicalNumber();
      this.sbNumber = sbBillDetailView.getSbNumber();
      this.fundNumber = sbBillDetailView.getFundNumber();
      this.vendorId = sbBillDetailView.getVendorId();
      this.vendorNameZH = sbBillDetailView.getVendorNameZH();
      this.vendorNameEN = sbBillDetailView.getVendorNameEN();
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getAdjustmentHeaderId()
   {
      return adjustmentHeaderId;
   }

   public void setAdjustmentHeaderId( String adjustmentHeaderId )
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

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
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

   public String getAmountCompany()
   {
      return KANUtil.formatNumber( amountCompany, getAccountId() );
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getAmountPersonal()
   {
      return KANUtil.formatNumber( amountPersonal, getAccountId() );
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
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

   public String getEmployeeSBNameZH()
   {
      return employeeSBNameZH;
   }

   public void setEmployeeSBNameZH( String employeeSBNameZH )
   {
      this.employeeSBNameZH = employeeSBNameZH;
   }

   public String getEmployeeSBNameEN()
   {
      return employeeSBNameEN;
   }

   public void setEmployeeSBNameEN( String employeeSBNameEN )
   {
      this.employeeSBNameEN = employeeSBNameEN;
   }

   public String getMedicalNumber()
   {
      return medicalNumber;
   }

   public void setMedicalNumber( String medicalNumber )
   {
      this.medicalNumber = medicalNumber;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getFundNumber()
   {
      return fundNumber;
   }

   public void setFundNumber( String fundNumber )
   {
      this.fundNumber = fundNumber;
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

   public String getSbStatus()
   {
      return sbStatus;
   }

   public void setSbStatus( String sbStatus )
   {
      this.sbStatus = sbStatus;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String accessAction = SBBillViewAction.accessAction;

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public String[] getSbStatusArray()
   {
      return sbStatusArray;
   }

   public void setSbStatusArray( String[] sbStatusArray )
   {
      this.sbStatusArray = sbStatusArray;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
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

   public String getGroupColumn()
   {
      return groupColumn;
   }

   public void setGroupColumn( String groupColumn )
   {
      this.groupColumn = groupColumn;
   }

   public String getPseudoStatus()
   {
      return pseudoStatus;
   }

   public void setPseudoStatus( String pseudoStatus )
   {
      this.pseudoStatus = pseudoStatus;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String getAccountMonthly()
   {
      return accountMonthly;
   }

   public void setAccountMonthly( String accountMonthly )
   {
      this.accountMonthly = accountMonthly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
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

   public String getBasePersonal()
   {
      return formatNumber( basePersonal );
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getBaseCompany()
   {
      return formatNumber( baseCompany );
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getRatePersonal()
   {
      return ratePersonal;
   }

   public void setRatePersonal( String ratePersonal )
   {
      this.ratePersonal = ratePersonal;
   }

   public String getRateCompany()
   {
      return rateCompany;
   }

   public void setRateCompany( String rateCompany )
   {
      this.rateCompany = rateCompany;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public String getContractRemark1()
   {
      return contractRemark1;
   }

   public void setContractRemark1( String contractRemark1 )
   {
      this.contractRemark1 = contractRemark1;
   }

}
