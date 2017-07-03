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
 * ��Ŀ���ƣ�HRO_V1 �����ƣ�SalaryHeaderVO �������� �����ˣ�Kevin ����ʱ�䣺2014-01-17
 */
public class SalaryHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7409890123688725095L;

   // ��������Id
   private String salaryHeaderId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // �ͻ����ƣ����ģ�
   private String clientNameZH;

   // �ͻ����ƣ�Ӣ�ģ�
   private String clientNameEN;

   // ����Id
   private String orderId;

   // ����Э��Id
   private String contractId;

   // ��ԱId
   private String employeeId;

   // ��Ա���������ģ�
   private String employeeNameZH;

   // ��Ա������Ӣ�ģ�
   private String employeeNameEN;

   // н�꿪ʼ����
   private String startDate;

   // н���������
   private String endDate;

   // ֤������
   private String certificateType;

   // ֤������
   private String certificateNumber;

   // ����Id
   private String bankId;

   // �������ƣ����ģ�
   private String bankNameZH;

   // �������ƣ�Ӣ�ģ�
   private String bankNameEN;

   // �����˻�
   private String bankAccount;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // �ϼƣ���˰��
   private String taxAmountPersonal;

   // ���Ӻϼƣ��������룩������˰ǰ�ӵĽ��
   private String addtionalBillAmountPersonal;

   // ˰ǰ����
   private String estimateSalary;

   // ˰�����룬���ڵ���˰�ĵ��� 
   private String actualSalary;

   // н���·�
   private String monthly;

   private String description;

   // For Application
   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // �·�
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

   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.entityId, this.entitys );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, this.businessTypes );
   }

   // ���Ӧ������
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getActualSalary() ) + Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) ) );
   }

   // ���ʵ������
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

   // ���ʵ������ - ����˰ʹ��
   public String getActualSalary()
   {
      return formatNumber( actualSalary );
   }

   public void setActualSalary( String actualSalary )
   {
      this.actualSalary = actualSalary;
   }

}
