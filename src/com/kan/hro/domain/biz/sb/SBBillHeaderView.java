package com.kan.hro.domain.biz.sb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class SBBillHeaderView extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -2188110148359722600L;

   // �籣ID
   private String headerId;

   // �籣����ID
   private String adjustmentHeaderId;

   // ��ԱId
   private String employeeId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   // ֤������
   private String certificateNumber;

   // ����Э��ID
   private String contractId;

   // ����Э��״̬
   private String contractStatus;

   // ��Ա�籣����Id
   private String employeeSBId;

   // �籣������ȴdetail���������ֵ��
   private String basePersonal;

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

   // ��Ա�籣״̬
   private String sbStatus;

   // �籣����
   private String sbType;

   // ��Ա״̬
   private String employStatus;

   // ��Ӧ��Id
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   // �˵��·�
   private String monthly;

   // �����·�
   private String accountMonthly;

   // ����
   private String description;

   // �ӱ����ڣ�������£�
   private String startDate;

   // �˱�����
   private String endDate;

   /***
    * For Application
    */
   // ����ID
   private String batchId;

   // ����ʵ�� 
   private String entityId;

   // �ͻ�ID
   private String clientId;

   // �ͻ����
   private String clientNo;

   // �ͻ����ƣ����ģ�
   private String clientNameZH;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameEN;

   // �ϼƣ���˾��
   private String amountCompany;

   // �ϼƣ����ˣ�
   private String amountPersonal;

   // ����״̬
   private String flag;

   // ��Ա״̬
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   // ��ͬ״̬
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   // �籣״̬
   private List< MappingVO > sbStatuses = new ArrayList< MappingVO >();

   // ����״̬
   private List< MappingVO > flags = new ArrayList< MappingVO >();

   // �籣����
   private List< MappingVO > sbTypes = new ArrayList< MappingVO >();

   // �籣����״̬
   private List< MappingVO > sbAdjustmentStatuses = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // α״̬���籣�����õ���
   private String pseudoStatus;

   // Ա��������ϢRemark1
   private String employeeRemark1;

   // �Ͷ���ͬRemark1
   private String contractRemark1;

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // No Use
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.sb.header.status" ) );
      this.employStatuses = KANUtil.getMappings( getLocale(), "business.employee.statuses" );
      this.sbStatuses = KANUtil.getMappings( getLocale(), "business.employee.contract.sb.statuses" );
      this.sbTypes = KANUtil.getMappings( getLocale(), "def.socialBenefit.solution.sbType" );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      this.sbAdjustmentStatuses = KANUtil.getMappings( this.getLocale(), "business.sb.adjustment.header.status" );
      this.flags = KANUtil.getMappings( this.getLocale(), "def.socialBenefit.payType" );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      try
      {
         if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_CLIENT ) )
         {
            List< MappingVO > statuses = new ArrayList< MappingVO >();
            List< MappingVO > clientStatus = KANUtil.getMappings( getLocale(), "business.sb.header.status" );
            for ( MappingVO mappingVO : clientStatus )
            {
               if ( !mappingVO.getMappingId().equals( "1" ) && !mappingVO.getMappingId().equals( "2" ) )
               {
                  statuses.add( mappingVO );
               }
            }
            super.setStatuses( statuses );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SBBillDetailView sbBillDetailView = ( SBBillDetailView ) object;
      super.setAccountId( sbBillDetailView.getAccountId() );
      this.headerId = sbBillDetailView.getHeaderId();
      this.adjustmentHeaderId = sbBillDetailView.getAdjustmentHeaderId();
      this.employeeId = sbBillDetailView.getEmployeeId();
      this.employeeNameZH = sbBillDetailView.getEmployeeNameZH();
      this.employeeNameEN = sbBillDetailView.getEmployeeNameEN();
      this.certificateNumber = sbBillDetailView.getCertificateNumber();
      this.contractId = sbBillDetailView.getContractId();
      this.employeeSBId = sbBillDetailView.getEmployeeSBId();
      this.basePersonal = sbBillDetailView.getBasePersonal();
      this.employeeSBNameZH = sbBillDetailView.getEmployeeSBNameZH();
      this.employeeSBNameEN = sbBillDetailView.getEmployeeSBNameEN();
      this.medicalNumber = sbBillDetailView.getMedicalNumber();
      this.sbNumber = sbBillDetailView.getSbNumber();
      this.fundNumber = sbBillDetailView.getFundNumber();
      this.employStatus = sbBillDetailView.getEmployStatus();
      this.sbStatus = sbBillDetailView.getSbStatus();
      this.contractStatus = sbBillDetailView.getContractStatus();
      super.setStatus( sbBillDetailView.getStatus() );
      this.clientId = sbBillDetailView.getClientId();
      this.clientNo = sbBillDetailView.getClientNo();
      this.clientNameZH = sbBillDetailView.getClientNameZH();
      this.clientNameEN = sbBillDetailView.getClientNameEN();
      this.vendorId = sbBillDetailView.getVendorId();
      this.vendorNameZH = sbBillDetailView.getVendorNameZH();
      this.vendorNameEN = sbBillDetailView.getVendorNameEN();
      this.monthly = sbBillDetailView.getMonthly();
      this.accountMonthly = sbBillDetailView.getAccountMonthly();
      this.amountCompany = sbBillDetailView.getAmountCompany();
      this.amountPersonal = sbBillDetailView.getAmountPersonal();
      this.pseudoStatus = sbBillDetailView.getPseudoStatus();
      this.description = sbBillDetailView.getDescription();
      this.entityId = sbBillDetailView.getEntityId();
      this.startDate = sbBillDetailView.getStartDate();
      this.endDate = sbBillDetailView.getEndDate();
      this.employeeRemark1 = sbBillDetailView.getEmployeeRemark1();
      this.contractRemark1 = sbBillDetailView.getContractRemark1();
   }

   // ����״̬
   public String getDecodeStatus()
   {
      // �����sbHeader����
      if ( KANUtil.filterEmpty( headerId ) != null )
      {
         return decodeField( super.getStatus(), super.getStatuses() );
      }
      // �����sbAdjustment����
      else if ( KANUtil.filterEmpty( headerId ) == null && KANUtil.filterEmpty( adjustmentHeaderId ) != null )
      {
         return decodeField( super.getStatus(), sbAdjustmentStatuses );
      }

      return "";
   }

   // ��ù�Ա״̬
   public String getDecodeEmployStatus() throws KANException
   {
      return decodeField( employStatus, employStatuses );
   }

   // ����籣״̬
   public String getDecodeSbStatus() throws KANException
   {
      return decodeField( sbStatus, sbStatuses );
   }

   // ��ú�ͬ״̬
   public String getDecodeContractStatus() throws KANException
   {
      return decodeField( contractStatus, contractStatuses );
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

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
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

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
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

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public List< MappingVO > getSbStatuses()
   {
      return sbStatuses;
   }

   public void setSbStatuses( List< MappingVO > sbStatuses )
   {
      this.sbStatuses = sbStatuses;
   }

   public String getAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
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

   public List< MappingVO > getSbAdjustmentStatuses()
   {
      return sbAdjustmentStatuses;
   }

   public void setSbAdjustmentStatuses( List< MappingVO > sbAdjustmentStatuses )
   {
      this.sbAdjustmentStatuses = sbAdjustmentStatuses;
   }

   public String getPseudoStatus()
   {
      return pseudoStatus;
   }

   public void setPseudoStatus( String pseudoStatus )
   {
      this.pseudoStatus = pseudoStatus;
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

   public List< MappingVO > getSbTypes()
   {
      return sbTypes;
   }

   public void setSbTypes( List< MappingVO > sbTypes )
   {
      this.sbTypes = sbTypes;
   }

   public List< MappingVO > getFlags()
   {
      return flags;
   }

   public void setFlags( List< MappingVO > flags )
   {
      this.flags = flags;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
   }

   public String getBasePersonal()
   {
      return formatNumber( basePersonal );
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public String getDecodeEntityId()
   {
      return decodeField( entityId, entitys );
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
