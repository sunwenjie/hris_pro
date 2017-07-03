package com.kan.hro.domain.biz.sb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class SBHeaderVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = 2199250413563709760L;

   // 社保主表Id
   private String headerId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 批次Id
   private String batchId;

   // 客户编号
   private String clientNo;

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

   // 雇员编号
   private String employeeNo;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 雇员社保方案Id
   private String employeeSBId;

   // 雇员社保方案名称（中文）
   private String employeeSBNameZH;

   // 雇员社保方案名称（英文）
   private String employeeSBNameEN;

   // 服务协议开始时间
   private String contractStartDate;

   // 服务协议结束时间
   private String contractEndDate;

   // 服务协议所属部门（Branch Id）
   private String contractBranch;

   // 服务协议所属人（Position Id）
   private String contractOwner;

   // 社保城市Id
   private String cityId;

   // 供应商Id
   private String vendorId;

   // 供应商名称（中文）
   private String vendorNameZH;

   // 供应商名称（英文）
   private String vendorNameEN;

   // 供应商服务内容
   private String vendorServiceIds;

   // 供应商服务费
   private String vendorServiceFee;

   // 雇员工作地点
   private String workPlace;

   // 性别，从称呼转译
   private String gender;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 需要办理医保卡
   private String needMedicalCard;

   // 需要办理社保卡
   private String needSBCard;

   // 医保卡帐号
   private String medicalNumber;

   // 社保卡帐号
   private String sbNumber;

   // 公积金帐号
   private String fundNumber;

   // 社保个人部分公司承担
   private String personalSBBurden;

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

   // 社保状态
   private String sbStatus;

   // 加保日期（起缴年月）
   private String startDate;

   // 退保日期
   private String endDate;

   // 入职日期（跟服务协议开始时间一致）
   private String onboardDate;

   // 离职日期
   private String resignDate;

   // 账单月份
   private String monthly;

   // 社保实际缴纳标识。1正常,2未缴纳
   private String flag;

   // 描述
   private String description;

   private String countClientId;

   private List< MappingVO > flags = new ArrayList< MappingVO >();

   // 对应的所属月份数组
   private String[] accountMonthlys = new String[] {};

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   /**
    * For App
    */
   // 社保状态数组形式
   private String[] sbStatusArray = new String[] {};

   // 省份Id
   private String provinceId;

   // 城市TempId
   private String cityIdTemp;

   // 个人费用汇总
   private String amountPersonal;

   // 公司费用汇总
   private String amountCompany;

   // 包含社保方案数量（服务协议层用）
   private String countHeaderId;

   // 包含科目数量
   private String countItemId;

   // 包含订单数量
   private String countOrderId;

   // 包含劳动合同数量
   private String countContractId;

   // 包含社保方案数量
   private String countEmployeeSBId;

   // 截至月份 - 数据提取使用
   private String monthlyLimit;

   // 额外状态 - 社保方案明细的最小状态
   private String additionalStatus;

   // 页面标示
   private String pageFlag;

   // 社保方案ID
   private String sbSolutionId;

   // 签约主体或结算规则
   private String orderDescription;

   // 合同状态
   private String contractStatus;

   // 包含的SBDetailVO 集合
   private List< Object > sbDetailVOObjects = new ArrayList< Object >();

   // 包含的员工集合（MappingVO中mappingId对应雇员ID、mappingValue对应雇员中文名、mappingTemp对应雇员英文名）
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   // 省份
   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   // 雇员状态
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   // 社保状态
   private List< MappingVO > sbStatuses = new ArrayList< MappingVO >();

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 证件类型
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();

   // 证件类型
   private List< MappingVO > residencyTypes = new ArrayList< MappingVO >();

   // 供应商
   private List< MappingVO > vendors = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 有效社保方案
   private List< MappingVO > socialBenefitSolutions = new ArrayList< MappingVO >();

   // 合同状态
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   private List< String > certificateNumbers = new ArrayList< String >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( getLocale(), "business.sb.header.status" ) );
      this.employStatuses = KANUtil.getMappings( getLocale(), "business.employee.statuses" );
      this.sbStatuses = KANUtil.getMappings( getLocale(), "business.employee.contract.sb.statuses" );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.socialBenefitSolutions = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      this.residencyTypes = KANUtil.getMappings( this.getLocale(), "sys.sb.residency" );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );

      this.monthlies.add( 0, super.getEmptyMappingVO() );

      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }

      // 雇员姓名排序
      if ( this.employees != null && this.employees.size() > 1 )
      {
         Collections.sort( employees, new Comparator< MappingVO >()
         {

            @Override
            public int compare( MappingVO o1, MappingVO o2 )
            {
               // 中文名称排序
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  return o1.getMappingValue().compareTo( o2.getMappingValue() );
               }
               // 英文排序
               else
               {
                  return o1.getMappingTemp().compareTo( o2.getMappingTemp() );
               }
            }

         } );
      }

      this.sbStatuses.add( 0, super.getEmptyMappingVO() );

      this.socialBenefitSolutions.add( 0, super.getEmptyMappingVO() );
      this.flags = KANUtil.getMappings( this.getLocale(), "def.socialBenefit.payType" );
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
      this.clientNo = "";
      this.clientNameZH = "";
      this.clientNameEN = "";
      this.orderId = "";
      this.contractId = "";
      this.contractStartDate = "";
      this.contractEndDate = "";
      this.contractBranch = "";
      this.contractOwner = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.employeeSBId = "";
      this.employeeSBNameZH = "";
      this.employeeSBNameEN = "";
      this.cityId = "";
      this.vendorId = "";
      this.vendorNameZH = "";
      this.vendorNameEN = "";
      this.vendorServiceIds = "";
      this.vendorServiceFee = "";
      this.workPlace = "";
      this.gender = "0";
      this.certificateType = "";
      this.certificateNumber = "";
      this.needMedicalCard = "0";
      this.needSBCard = "0";
      this.medicalNumber = "";
      this.sbNumber = "";
      this.fundNumber = "";
      this.personalSBBurden = "0";
      this.residencyType = "";
      this.residencyCityId = "0";
      this.residencyAddress = "";
      this.highestEducation = "0";
      this.maritalStatus = "0";
      this.employStatus = "0";
      this.sbStatus = "";
      this.startDate = "";
      this.endDate = "";
      this.onboardDate = "";
      this.resignDate = "";
      this.monthly = "";
      this.description = "";
      this.flag = "";
      super.setStatus( "0" );

      // For APP
      this.cityIdTemp = "";
      this.amountPersonal = "";
      this.amountCompany = "";
      this.countHeaderId = "";
      this.countItemId = "";
      this.countOrderId = "";
      this.countContractId = "";
      this.countEmployeeSBId = "";
      this.monthlyLimit = "";
      this.additionalStatus = "";
      this.pageFlag = "";
      this.sbSolutionId = "";
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   // 批次ID编码
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // 服务协议ID编码
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
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

   // 供应商ID编码
   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
   }

   // 获得雇员状态
   public String getDecodeEmployStatus() throws KANException
   {
      return decodeField( employStatus, employStatuses );
   }

   // 获得社保状态
   public String getDecodeSbStatus() throws KANException
   {
      return decodeField( sbStatus, sbStatuses );
   }

   // 获得社保状态
   public String getDecodeContractStatus() throws KANException
   {
      return decodeField( contractStatus, contractStatuses );
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

   // 获得城市名称
   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   // 获得所属部门名称
   public String getDecodeBranch() throws KANException
   {
      return decodeField( this.contractBranch, KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( super.getLocale().getLanguage() ), true );
   }

   // 获得所属人姓名
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.contractOwner );
   }

   // 获得社保实际缴纳标识
   public String getDecodeFlag() throws KANException
   {
      return decodeField( this.flag, this.flags );
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

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
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

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getVendorServiceIds()
   {
      return vendorServiceIds;
   }

   public void setVendorServiceIds( String vendorServiceIds )
   {
      this.vendorServiceIds = vendorServiceIds;
   }

   public String getVendorServiceFee()
   {
      return vendorServiceFee;
   }

   public void setVendorServiceFee( String vendorServiceFee )
   {
      this.vendorServiceFee = vendorServiceFee;
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

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getNeedMedicalCard()
   {
      return needMedicalCard;
   }

   public void setNeedMedicalCard( String needMedicalCard )
   {
      this.needMedicalCard = needMedicalCard;
   }

   public String getDecodeNeedMedicalCard()
   {
      return decodeField( this.needMedicalCard, super.getFlags() );
   }

   public String getNeedSBCard()
   {
      return needSBCard;
   }

   public void setNeedSBCard( String needSBCard )
   {
      this.needSBCard = needSBCard;
   }

   public String getDecodeNeedSBCard()
   {
      return decodeField( this.needSBCard, super.getFlags() );
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

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCityIdTemp()
   {
      return cityIdTemp;
   }

   public void setCityIdTemp( String cityIdTemp )
   {
      this.cityIdTemp = cityIdTemp;
   }

   public String getSbStatus()
   {
      return sbStatus;
   }

   public void setSbStatus( String sbStatus )
   {
      this.sbStatus = sbStatus;
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

   public String getMonthlyLimit()
   {
      return monthlyLimit;
   }

   public void setMonthlyLimit( String monthlyLimit )
   {
      this.monthlyLimit = monthlyLimit;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
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

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getDecodeAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public String getFormatAmountPersonal()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountPersonal ) );
   }

   public String getAmountCompany()
   {
      return amountCompany;
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getDecodeAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public String getFormatAmountCompany()
   {
      return KANUtil.formatNumber2( Double.parseDouble( amountCompany ) );
   }

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
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

   public final String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractStartDate ) );
   }

   public final void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public final String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractEndDate ) );
   }

   public final void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public final String getContractBranch()
   {
      return contractBranch;
   }

   public final void setContractBranch( String contractBranch )
   {
      this.contractBranch = contractBranch;
   }

   public final String getContractOwner()
   {
      return contractOwner;
   }

   public final void setContractOwner( String contractOwner )
   {
      this.contractOwner = contractOwner;
   }

   public final List< MappingVO > getCertificateTypes()
   {
      return certificateTypes;
   }

   public final void setCertificateTypes( List< MappingVO > certificateTypes )
   {
      this.certificateTypes = certificateTypes;
   }

   public final List< MappingVO > getResidencyTypes()
   {
      return residencyTypes;
   }

   public final void setResidencyTypes( List< MappingVO > residencyTypes )
   {
      this.residencyTypes = residencyTypes;
   }

   public final String getClientNo()
   {
      return clientNo;
   }

   public final void setClientNo( String clientNo )
   {
      this.clientNo = clientNo;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public String getCountEmployeeSBId()
   {
      return countEmployeeSBId;
   }

   public void setCountEmployeeSBId( String countEmployeeSBId )
   {
      this.countEmployeeSBId = countEmployeeSBId;
   }

   public List< MappingVO > getVendors()
   {
      return vendors;
   }

   public void setVendors( List< MappingVO > vendors )
   {
      this.vendors = vendors;
   }

   public final String getEmployeeSBNameZH()
   {
      return employeeSBNameZH;
   }

   public final void setEmployeeSBNameZH( String employeeSBNameZH )
   {
      this.employeeSBNameZH = employeeSBNameZH;
   }

   public final String getEmployeeSBNameEN()
   {
      return employeeSBNameEN;
   }

   public final void setEmployeeSBNameEN( String employeeSBNameEN )
   {
      this.employeeSBNameEN = employeeSBNameEN;
   }

   public String getEmployeeSBName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeSBNameZH();
         }
         else
         {
            return this.getEmployeeSBNameEN();
         }
      }
      else
      {
         return this.getEmployeeSBNameZH();
      }
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

   public String getVendorName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getVendorNameZH();
         }
         else
         {
            return this.getVendorNameEN();
         }
      }
      else
      {
         return this.getVendorNameZH();
      }
   }

   public final String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public final void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public final String getDecodeAdditionalStatus()
   {
      return decodeField( this.additionalStatus, KANUtil.getMappings( this.getLocale(), "business.sb.header.status" ) );
   }

   public List< MappingVO > getSocialBenefitSolutions()
   {
      return socialBenefitSolutions;
   }

   public void setSocialBenefitSolutions( List< MappingVO > socialBenefitSolutions )
   {
      this.socialBenefitSolutions = socialBenefitSolutions;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public List< MappingVO > getEmployees()
   {
      return employees;
   }

   public void setEmployees( List< MappingVO > employees )
   {
      this.employees = employees;
   }

   // 获得员工姓名（中文）集合
   public String getEmployeeNameZHList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "、" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // 获得员工姓名（英文）集合
   public String getEmployeeNameENList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "、" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // 获得员工集合数量
   public String getEmployeeListSize()
   {
      return String.valueOf( this.employees.size() );
   }

   // 获得员工姓名集合
   public String getEmployeeNameList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            for ( MappingVO mappingVO : employees )
            {
               str.append( mappingVO.getMappingValue() + "、" );
            }
            return str.substring( 0, str.length() - 1 );
         }
         else
         {
            for ( MappingVO mappingVO : employees )
            {
               str.append( mappingVO.getMappingTemp() + "、" );
            }
            return str.substring( 0, str.length() - 1 );
         }
      }

      return str.toString();
   }

   // 获得部分雇员
   public String getEmployeeNameTop3List()
   {
      if ( employees != null )
      {
         if ( employees.size() <= 3 )
         {
            return getEmployeeNameList();
         }
         else
         {
            final StringBuffer str = new StringBuffer();

            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( int i = 0; i < 3; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }
            else
            {
               for ( int i = 0; i < 3; i++ )
               {
                  str.append( employees.get( i ).getMappingTemp() + "、" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }

         }
      }
      return null;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
   }

   public String[] getSbStatusArray()
   {
      return sbStatusArray;
   }

   public void setSbStatusArray( String[] sbStatusArray )
   {
      this.sbStatusArray = sbStatusArray;
   }

   public String getOrderDescription()
   {
      return orderDescription;
   }

   public void setOrderDescription( String orderDescription )
   {
      this.orderDescription = orderDescription;
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public final String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public final void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
   }

   public List< MappingVO > getFlags()
   {
      return flags;
   }

   public void setFlags( List< MappingVO > flags )
   {
      this.flags = flags;
   }

   public List< String > getCertificateNumbers()
   {
      return certificateNumbers;
   }

   public void setCertificateNumbers( List< String > certificateNumbers )
   {
      this.certificateNumbers = certificateNumbers;
   }

   public String[] getAccountMonthlys()
   {
      return accountMonthlys;
   }

   public void setAccountMonthlys( String[] accountMonthlys )
   {
      this.accountMonthlys = accountMonthlys;
   }

   public List< Object > getSbDetailVOObjects()
   {
      return sbDetailVOObjects;
   }

   public void setSbDetailVOObjects( List< Object > sbDetailVOObjects )
   {
      this.sbDetailVOObjects = sbDetailVOObjects;
   }

   // 获得所属月份集合
   public String getAccountMonthlyList()
   {
      final StringBuffer str = new StringBuffer();

      if ( accountMonthlys != null && accountMonthlys.length > 0 )
      {
         for ( String accountMonthly : accountMonthlys )
         {
            str.append( accountMonthly.toString() + "、" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return "";
   }
}
