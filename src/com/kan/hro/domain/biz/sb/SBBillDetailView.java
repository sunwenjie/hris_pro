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

   // �籣����ID
   private String adjustmentHeaderId;

   // ��ĿID
   private String itemId;

   // ��Ŀ����
   private String itemType;

   // ��Ŀ���
   private String itemNo;

   // ��Ŀ���ƣ����ģ�
   private String nameZH;

   // ��Ŀ���ƣ�Ӣ�ģ�
   private String nameEN;

   // ���������ˣ�
   private String basePersonal;

   // ��������˾��
   private String baseCompany;

   // ���ʣ����ˣ�
   private String ratePersonal;

   // ���ʣ���˾��
   private String rateCompany;

   // �ϼƣ���˾��
   private String amountCompany;

   // �ϼƣ����ˣ�
   private String amountPersonal;

   // н���·�
   private String monthly;

   // �����·�
   private String accountMonthly;

   // ����
   private String description;

   /**
    * For Application
    */
   // ����ID
   private String batchId;

   // ����ʵ�� 
   private String entityId;

   // ҵ������
   private String businessTypeId;

   // �ͻ�ID
   private String clientId;

   // �ͻ����
   private String clientNo;

   // �ͻ����ƣ����ģ�
   private String clientNameZH;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameEN;

   // ����ID
   private String orderId;

   // ��ԱID
   private String employeeId;

   // ��Ա���������ģ�
   private String employeeNameZH;

   // ��Ա������Ӣ�ģ�
   private String employeeNameEN;

   // ֤������
   private String certificateNumber;

   // ��Ա״̬
   private String employStatus;

   // ����Э��ID
   private String contractId;

   // ����Э��״̬
   private String contractStatus;

   // ��Ա�籣����Id
   private String employeeSBId;

   // ��Ա�籣�������ƣ����ģ�
   private String employeeSBNameZH;

   // ��Ա�籣�������ƣ�Ӣ�ģ�
   private String employeeSBNameEN;

   // ҽ�����ʺ�
   private String medicalNumber;

   // �籣���ʺ�
   private String sbNumber;

   // �������ʺ�
   private String fundNumber;

   // �籣����ID
   private String sbSolutionId;

   // ��Ա�籣״̬
   private String sbStatus;

   // ����״̬
   private String flag;

   // �籣����
   private String sbType;

   // ��Ӧ��Id
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   // �ӱ����ڣ�������£�
   private String startDate;

   // �˱�����
   private String endDate;

   /**
    * For Application
    */
   // �籣״̬����
   private String[] sbStatusArray = new String[] {};

   // �����ֶ�
   private String groupColumn;

   // α״̬���籣�����õ���
   private String pseudoStatus;
   
   // Ա��������ϢRemark1
   private String employeeRemark1;

   // �Ͷ���ͬRemark1
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
