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

public class IncomeTaxYearView extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -1241166724388040015L;

   // Ա��ID
   private String employeeId;

   // �Ͷ���ͬID
   private String contractId;

   // ����ID
   private String orderId;

   // �������
   private String year;

   // �������
   private String inputDate;

   // ���
   private String money;

   // ��˰������
   private String employeeNameZH;

   // ��˰��������Ӣ�ģ�
   private String employeeNameEN;

   // ֤������
   private String certificateType;

   // ֤������
   private String certificateNumber;

   // ��ְ���ܹ͵�λ
   private String entityId;

   // ��ҵ
   private String businessTypeId;

   // ְ��ID
   private String positionGradeId;

   // ְ������
   private String gradeName;

   // ְλ
   private String positionId;

   // ְλ����
   private String positionName;

   // ������Ч��ϵ��ַ
   private String personalAddress;

   // ������Ч�ʱ����
   private String personalPostcode;

   // ��ϵ�绰
   private String phone1;

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

   /**
    * For Application
    */
   // Ӧ˰����
   private String addtionalBillAmountPersonal;

   // ��˰���Searchʱ�õ���
   private String taxAmountPersonalMax;

   // ��˰��С��Searchʱ�õ���
   private String taxAmountPersonalMin;

   // ���
   private List< MappingVO > years = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypeIds = new ArrayList< MappingVO >();

   // ֤������
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.years = KANUtil.getYears( Integer.valueOf( KANUtil.getYear( new Date() ) ) - 10, Integer.valueOf( KANUtil.getYear( new Date() ) ) + 1 );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypeIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public void reset() throws KANException
   {
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // No Use
   }

   public String getYear()
   {
      return year;
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getInputDate()
   {
      return KANUtil.formatDate( inputDate, "yyyy��MM��dd��" );
   }

   public void setInputDate( String inputDate )
   {
      this.inputDate = inputDate;
   }

   public String getMoney()
   {
      return money;
   }

   public void setMoney( String money )
   {
      this.money = money;
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

   public String getCertificateType()
   {
      return decodeField( certificateType, certificateTypes );
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

   public String getEntityId()
   {
      return decodeField( entityId, entitys );
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return decodeField( businessTypeId, businessTypeIds );
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getPersonalAddress()
   {
      return personalAddress;
   }

   public void setPersonalAddress( String personalAddress )
   {
      this.personalAddress = personalAddress;
   }

   public String getPhone1()
   {
      return phone1;
   }

   public void setPhone1( String phone1 )
   {
      this.phone1 = phone1;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( String.valueOf( Float.valueOf( KANUtil.filterEmpty( billAmountCompany ) == null ? "0" : billAmountCompany ) ) );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( String.valueOf( Float.valueOf( KANUtil.filterEmpty( billAmountPersonal ) == null ? "0" : billAmountPersonal ) ) );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( String.valueOf( Float.valueOf( KANUtil.filterEmpty( costAmountCompany ) == null ? "0" : costAmountCompany ) ) );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( String.valueOf( Float.valueOf( KANUtil.filterEmpty( costAmountPersonal ) == null ? "0" : costAmountPersonal ) ) );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getTaxAmountPersonal()
   {
      return formatNumber( String.valueOf( Float.parseFloat( KANUtil.filterEmpty( taxAmountPersonal ) == null ? "0" : taxAmountPersonal ) ) );
   }

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
   }

   // ���Ӧ������
   public String getBeforeTaxSalary()
   {
      return formatNumber( String.valueOf( Float.valueOf( getBillAmountPersonal() ) - Float.valueOf( getCostAmountPersonal() ) ) );
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getGradeName()
   {
      return gradeName;
   }

   public void setGradeName( String gradeName )
   {
      this.gradeName = gradeName;
   }

   public String getPositionName()
   {
      return positionName;
   }

   public void setPositionName( String positionName )
   {
      this.positionName = positionName;
   }

   public List< MappingVO > getYears()
   {
      return years;
   }

   public void setYears( List< MappingVO > years )
   {
      this.years = years;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypeIds()
   {
      return businessTypeIds;
   }

   public void setBusinessTypeIds( List< MappingVO > businessTypeIds )
   {
      this.businessTypeIds = businessTypeIds;
   }

   public List< MappingVO > getCertificateTypes()
   {
      return certificateTypes;
   }

   public void setCertificateTypes( List< MappingVO > certificateTypes )
   {
      this.certificateTypes = certificateTypes;
   }

   public String getTaxAmountPersonalMax()
   {
      return taxAmountPersonalMax;
   }

   public void setTaxAmountPersonalMax( String taxAmountPersonalMax )
   {
      this.taxAmountPersonalMax = taxAmountPersonalMax;
   }

   public String getTaxAmountPersonalMin()
   {
      return taxAmountPersonalMin;
   }

   public void setTaxAmountPersonalMin( String taxAmountPersonalMin )
   {
      this.taxAmountPersonalMin = taxAmountPersonalMin;
   }

   public String getPersonalPostcode()
   {
      return personalPostcode;
   }

   public void setPersonalPostcode( String personalPostcode )
   {
      this.personalPostcode = personalPostcode;
   }

   public String getAddtionalBillAmountPersonal()
   {
      return formatNumber( KANUtil.filterEmpty( addtionalBillAmountPersonal ) == null ? "0" : addtionalBillAmountPersonal );
   }

   public void setAddtionalBillAmountPersonal( String addtionalBillAmountPersonal )
   {
      this.addtionalBillAmountPersonal = addtionalBillAmountPersonal;
   }

}
