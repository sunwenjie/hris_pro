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

   // 员工ID
   private String employeeId;

   // 劳动合同ID
   private String contractId;

   // 订单ID
   private String orderId;

   // 所得年份
   private String year;

   // 填表日期
   private String inputDate;

   // 金额
   private String money;

   // 纳税人姓名
   private String employeeNameZH;

   // 纳税人姓名（英文）
   private String employeeNameEN;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 任职、受雇单位
   private String entityId;

   // 行业
   private String businessTypeId;

   // 职级ID
   private String positionGradeId;

   // 职级名称
   private String gradeName;

   // 职位
   private String positionId;

   // 职位名称
   private String positionName;

   // 境内有效联系地址
   private String personalAddress;

   // 境内有效邮编号码
   private String personalPostcode;

   // 联系电话
   private String phone1;

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

   /**
    * For Application
    */
   // 应税工资
   private String addtionalBillAmountPersonal;

   // 个税最大（Search时用到）
   private String taxAmountPersonalMax;

   // 个税最小（Search时用到）
   private String taxAmountPersonalMin;

   // 年份
   private List< MappingVO > years = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypeIds = new ArrayList< MappingVO >();

   // 证件类型
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
      return KANUtil.formatDate( inputDate, "yyyy年MM月dd日" );
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

   // 获得应发工资
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
