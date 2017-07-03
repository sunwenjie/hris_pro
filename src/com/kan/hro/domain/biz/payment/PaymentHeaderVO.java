package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�PaymentHeaderVO  
 * ��������  н�ʷ���
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-11-23  
 */
public class PaymentHeaderVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -8070660448645069041L;

   /**
    * for DB
    */
   //  н������Id
   private String paymentHeaderId;

   // �������Э��Id������Ա����Э��Id��һ��
   private String orderContractId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ����Id
   private String batchId;

   // ����Id
   private String orderId;

   // ����Э��Id
   private String contractId;

   // ��ԱId
   private String employeeId;

   // ��Ա���
   private String employeeNo;

   // ��Ա���������ģ�
   private String employeeNameZH;

   // ��Ա������Ӣ�ģ�
   private String employeeNameEN;

   // ����Id
   private String bankId;

   // �������ƣ����ģ�
   private String bankNameZH;

   // �������ƣ�Ӣ�ģ�
   private String bankNameEN;

   // �����˻�
   private String bankAccount;

   // н�꿪ʼ����
   private String startDate;

   // н���������
   private String endDate;

   // ֤������
   private String certificateType;

   // ֤������
   private String certificateNumber;

   // ��������
   private String residencyType;

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

   // ���ս�
   private String annualBonus;

   // ���ս���˰
   private String annualBonusTax;

   // ���Ӻϼƣ��������룩������˰ǰ�ӵĽ��
   private String addtionalBillAmountPersonal;

   // ����˰����
   private String taxAgentAmountPersonal;

   // н���·�
   private String monthly;

   // ��˰���
   private String taxFlag;

   // ��Ӧ��Id
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   // ��Ӧ�̷�������
   private String vendorServiceIds;

   // ��Ӧ�̷����
   private String vendorServiceFee;

   // ����
   private String description;

   // ����״̬
   private String additionalStatus;

   // ��Ŀ����
   private String itemGroupId;

   // ������Ŀ
   private List< MappingVO > items = new ArrayList< MappingVO >();

   // �·�
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   //��������
   private List< MappingVO > residencyTypes = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // �ͻ�ID �ܺ�
   private String countClientIds;

   // ����ID �ܺ�
   private String countOrderIds;

   // ��ͬID �ܺ�
   private String countContractIds;

   // Ա��ID �ܺ�
   private String countEmployeeIds;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( paymentHeaderId );
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
      this.orderContractId = "";
      this.entityId = "";
      this.businessTypeId = "";
      this.batchId = "";
      this.orderId = "";
      this.contractId = "";
      this.employeeId = "";
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
      this.taxAmountPersonal = "";
      this.addtionalBillAmountPersonal = "";
      this.taxAgentAmountPersonal = "";
      this.monthly = "";
      this.taxFlag = "";
      this.vendorId = "";
      this.vendorNameZH = "";
      this.vendorNameEN = "";
      this.vendorServiceIds = "";
      this.vendorServiceFee = "";
      this.description = "";
      this.itemGroupId = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) object;
      this.orderContractId = paymentHeaderVO.getOrderContractId();
      this.entityId = paymentHeaderVO.getEntityId();
      this.businessTypeId = paymentHeaderVO.getBusinessTypeId();
      this.batchId = paymentHeaderVO.getBatchId();
      this.orderId = paymentHeaderVO.getOrderId();
      this.contractId = paymentHeaderVO.getContractId();
      this.employeeId = paymentHeaderVO.getEmployeeId();
      this.employeeNameZH = paymentHeaderVO.getEmployeeNameZH();
      this.employeeNameEN = paymentHeaderVO.getEmployeeNameEN();
      this.bankId = paymentHeaderVO.getBankId();
      this.bankNameZH = paymentHeaderVO.getBankNameZH();
      this.bankNameEN = paymentHeaderVO.getBankNameEN();
      this.bankAccount = paymentHeaderVO.getBankAccount();
      this.startDate = paymentHeaderVO.getStartDate();
      this.endDate = paymentHeaderVO.getEndDate();
      this.certificateType = paymentHeaderVO.getCertificateType();
      this.certificateNumber = paymentHeaderVO.getCertificateNumber();
      this.billAmountCompany = paymentHeaderVO.getBillAmountCompany();
      this.billAmountPersonal = paymentHeaderVO.getBillAmountPersonal();
      this.costAmountCompany = paymentHeaderVO.getCostAmountCompany();
      this.costAmountPersonal = paymentHeaderVO.getCostAmountPersonal();
      this.taxAmountPersonal = paymentHeaderVO.getTaxAmountPersonal();
      this.addtionalBillAmountPersonal = paymentHeaderVO.getAddtionalBillAmountPersonal();
      this.taxAgentAmountPersonal = paymentHeaderVO.getTaxAgentAmountPersonal();
      this.monthly = paymentHeaderVO.getMonthly();
      this.taxFlag = paymentHeaderVO.getTaxFlag();
      this.vendorId = paymentHeaderVO.getVendorId();
      this.vendorNameZH = paymentHeaderVO.getVendorNameZH();
      this.vendorNameEN = paymentHeaderVO.getVendorNameEN();
      this.vendorServiceIds = paymentHeaderVO.getVendorServiceIds();
      this.vendorServiceFee = paymentHeaderVO.getVendorServiceFee();
      this.description = paymentHeaderVO.getDescription();
      this.itemGroupId = paymentHeaderVO.getItemGroupId();
      this.annualBonus = paymentHeaderVO.getAnnualBonus();
      this.annualBonusTax = paymentHeaderVO.getAnnualBonusTax();
      super.setStatus( paymentHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( paymentHeaderVO.getCorpId() );
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.payment.status" ) );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.monthlies.add( 0, super.getEmptyMappingVO() );
      this.residencyTypes = KANUtil.getMappings( this.getLocale(), "sys.sb.residency" );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
   }

   public void formatNumber()
   {

   }

   public String getPaymentHeaderId()
   {
      return paymentHeaderId;
   }

   public void setPaymentHeaderId( String paymentHeaderId )
   {
      this.paymentHeaderId = paymentHeaderId;
   }

   public String getOrderContractId()
   {
      return orderContractId;
   }

   public void setOrderContractId( String orderContractId )
   {
      this.orderContractId = orderContractId;
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

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public final String getTaxFlag()
   {
      return taxFlag;
   }

   public final void setTaxFlag( String taxFlag )
   {
      this.taxFlag = taxFlag;
   }

   public final String getVendorId()
   {
      return vendorId;
   }

   public final void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public final String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public final void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public final String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public final void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

   public final String getVendorServiceIds()
   {
      return vendorServiceIds;
   }

   public final void setVendorServiceIds( String vendorServiceIds )
   {
      this.vendorServiceIds = vendorServiceIds;
   }

   public final String getVendorServiceFee()
   {
      return vendorServiceFee;
   }

   public final void setVendorServiceFee( String vendorServiceFee )
   {
      this.vendorServiceFee = vendorServiceFee;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
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

   public final String getAddtionalBillAmountPersonal()
   {
      return formatNumber( addtionalBillAmountPersonal );
   }

   public final String getIClickAfterTaxSalary()
   {
      return getAfterTaxSalary();
   }

   public final void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

   public final void setTaxAgentAmountPersonal( String taxAgentAmountPersonal )
   {
      this.taxAgentAmountPersonal = taxAgentAmountPersonal;
   }

   public final void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public List< MappingVO > getResidencyTypes()
   {
      return residencyTypes;
   }

   public void setResidencyTypes( List< MappingVO > residencyTypes )
   {
      this.residencyTypes = residencyTypes;
   }

   public String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public void addBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany == null ? "0" : this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal == null ? "0" : this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany == null ? "0" : this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal == null ? "0" : this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
   }

   public String getItemGroupId()
   {
      return itemGroupId;
   }

   public void setItemGroupId( String itemGroupId )
   {
      this.itemGroupId = itemGroupId;
   }

   public String getCountClientIds()
   {
      return countClientIds;
   }

   public void setCountClientIds( String countClientIds )
   {
      this.countClientIds = countClientIds;
   }

   public String getCountOrderIds()
   {
      return countOrderIds;
   }

   public void setCountOrderIds( String countOrderIds )
   {
      this.countOrderIds = countOrderIds;
   }

   public String getCountContractIds()
   {
      return countContractIds;
   }

   public void setCountContractIds( String countContractIds )
   {
      this.countContractIds = countContractIds;
   }

   public String getCountEmployeeIds()
   {
      return countEmployeeIds;
   }

   public void setCountEmployeeIds( String countEmployeeIds )
   {
      this.countEmployeeIds = countEmployeeIds;
   }

   public void setAnnualBonusTax( String annualBonusTax )
   {
      this.annualBonusTax = annualBonusTax;
   }

   public void setAnnualBonus( String annualBonus )
   {
      this.annualBonus = annualBonus;
   }

   // decode����ʵ��
   public String getDecodeEntity()
   {
      return decodeField( entityId, entitys );
   }

   // decode����ʵ��
   public String getDecodeEntityId()
   {
      return getDecodeEntity();
   }

   /**====================���ֹ�����Start======================**/

   // ���Ӧ������/˰ǰ����
   public final String getBeforeTaxSalary()
   {
      return formatNumber( addtionalBillAmountPersonal );
   }

   // ��ȡ���ս�
   public String getAnnualBonus()
   {
      return formatNumber( annualBonus );
   }

   // ��ô���˰����
   public final String getTaxAgentAmountPersonal()
   {
      return formatNumber( taxAgentAmountPersonal );
   }

   // ��ø�˰
   public String getTaxAmountPersonal()
   {
      return formatNumber( taxAmountPersonal );
   }

   // ������ս���˰  
   public String getAnnualBonusTax()
   {
      return formatNumber( annualBonusTax );
   }

   /***
    * ʵ������ = ˰ǰ���� - ��˰ - ���ս����������ս��ޣ�
    * @return ʵ������
    */
   public String getAfterTaxSalary()
   {
      return formatNumber( String.valueOf( Double.valueOf( getBillAmountPersonal() ) - Double.valueOf( getCostAmountPersonal() ) - Double.valueOf( getTaxAmountPersonal() )
            - Double.valueOf( getAnnualBonus() ) ) );
   }

   /***
    * ʵ�����ս� = ���ս� - ���ս���˰ 
    * @return ʵ�����ս�
    */
   public String getAfterTaxAnnualBonus()
   {
      return formatNumber( super.getRemark5() );
      // return formatNumber( String.valueOf( Double.valueOf( getAnnualBonus() ) - Double.valueOf( getAnnualBonusTax() ) ) );
   }

   public String getActualSalary()
   {
      System.out.println(formatNumber( super.getRemark5() ));
      return formatNumber( String.valueOf( Double.valueOf( getAfterTaxSalary() ) + Double.valueOf( getAfterTaxAnnualBonus() ) ) );
   }
}
