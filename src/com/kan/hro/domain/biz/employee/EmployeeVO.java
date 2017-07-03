package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.web.actions.biz.employee.EmployeeAction;

/**
 * 项目名称：HRO_V1  
 * 类名称：EmployeeVO  
 * 类描述：  
 * 创建人：Jixiang  
 * 创建时间：2013-8-2 下午02:47:50  
 */

public class EmployeeVO extends BaseVO
{

   private static final long serialVersionUID = 20130802150043L;

   // 雇员Id
   private String employeeId;

   // 雇员编号
   private String employeeNo;

   // 中文名
   private String nameZH;

   // 英文名
   private String nameEN;

   // 称呼，即用作性别
   private String salutation;

   // 出生年月
   private String birthday;

   // 婚姻状况
   private String maritalStatus;

   // 国籍
   private String nationNality;

   // 出生地
   private String birthdayPlace;

   // 户籍城市（籍贯）下拉框
   private String residencyCityId;

   // 户籍城市（字符串）
   private String residencyCity;

   // 户籍地址
   private String residencyAddress;

   // 家庭地址
   private String personalAddress;

   // 家庭地址邮编
   private String personalPostcode;

   // 最高学历
   private String highestEducation;

   // 档案编号
   private String recordNo;

   // 档案所在地
   private String recordAddress;

   // 户籍性质，对应社保模块中的Residency
   private String residencyType;

   // 毕业时间
   private String graduationDate;

   // 进入公司时间
   private String onboardDate;

   // 首次参与工作时间
   private String startWorkDate;

   // 是否有外国人就业许可证
   private String hasForeignerWorkLicence;

   // 外国人就业许可证编号
   private String foreignerWorkLicenceNo;

   // 外国人就业许可证失效日期
   private String foreignerWorkLicenceEndDate;

   // 是否有居住证
   private String hasResidenceLicence;

   // 居住证编号
   private String residenceNo;

   // 居住证生效日期
   private String residenceStartDate;

   // 居住证失效日期
   private String residenceEndDate;

   // 证件类型
   private String certificateType;

   // 证件号码
   private String certificateNumber;

   // 证件生效日期
   private String certificateStartDate;

   // 证件失效日期
   private String certificateEndDate;

   // 证件颁发机构
   private String certificateAwardFrom;

   // 银行
   private String bankId;

   // 开户网点
   private String bankBranch;

   // 银行账户
   private String bankAccount;

   //phone1个人电话
   //phone2公司电话
   //mobile1个人手机
   //mobile2暂时未用到
   //email1公司邮箱
   //email2个人邮箱

   // 电话
   private String phone1;

   // 手机
   private String mobile1;

   // 邮箱
   private String email1;

   // 个人主页
   private String website1;

   // 电话（备用）
   private String phone2;

   // 手机（备用）
   private String mobile2;

   // 邮箱（备用）
   private String email2;

   // 个人主页（备用）
   private String website2;

   // 即时通讯 -1
   private String im1Type;

   // 即时通讯号码 1
   private String im1;

   // 即时通讯 - 2
   private String im2Type;

   // 即时通讯号码 -2
   private String im2;

   // 即时通讯 - 3
   private String im3Type;

   // 即时通讯号码 - 3
   private String im3;

   // 即时通讯 - 4
   private String im4Type;

   // 即时通讯号码 - 4
   private String im4;

   // 所属部门（Branch Id）
   private String branch;

   // 所属人（Position Id）
   private String owner;

   // 形象照片
   private String photo;

   // 附件
   private String attachment;

   // 简历（中文）
   @JsonIgnore
   private String resumeZH;
   @JsonIgnore
   // 简历（英文）
   private String resumeEN;

   // 雇员/员工个人密钥（公钥 - HRM使用）
   @JsonIgnore
   private String publicCode;

   // 冗余字段职位
   private String _tempPositionIds;

   // 冗余字段部门
   private String _tempBranchIds;

   // 冗余字段上级部门
   private String _tempParentBranchIds;

   // 冗余字段上级职位
   private String _tempParentPositionIds;

   // 冗余字段上级职位所属人
   private String _tempParentPositionOwners;

   // 冗余字段上级职位部门
   private String _tempParentPositionBranchIds;

   // 冗余字段办公地址
   private String _tempPositionLocationIds;

   // 冗余字段职级
   private String _tempPositionGradeIds;

   // 冗余字段用户名
   private String _tempUsername;

   private String accountName;

   // 个人描述
   private String description;

   // 项目Id
   private String businessTypeId;

   private String healthCardNo;

   private String healthCardStartDate;

   //是否返聘  1是 2否
   private String hireAgain;

   /**
    *  For Application
    */
   @JsonIgnore
   public String accessAction = EmployeeAction.accessAction;
   @JsonIgnore
   // 用于健康证过期提醒
   private String noticeFlag;

   @JsonIgnore
   public String getAccessAction()
   {
      return accessAction;
   }

   @JsonIgnore
   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   @JsonIgnore
   // 户籍省份
   private String residencyProvinceId;
   @JsonIgnore
   private String cityIdTemp;

   // 职位数组
   @JsonIgnore
   private String[] positionIdArray = new String[] {};
   @JsonIgnore
   private String[] attachmentArray = new String[] {};
   @JsonIgnore
   private String[] skillIdArray = new String[] {};

   @JsonIgnore
   private List< MappingVO > residencyProvinces = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > salutations = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > maritalStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > highestEducations = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > changeReasons = new ArrayList< MappingVO >();
   // 劳动合同数量
   @JsonIgnore
   private String numberOfLaborContract;

   // 服务协议数量
   @JsonIgnore
   private String numberOfServiceContract;

   // 登录账户
   @JsonIgnore
   private String username;
   @JsonIgnore
   private String ownerBizEmail;
   @JsonIgnore
   private String ownerPersonalEmail;

   // 部门
   @JsonIgnore
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   // 职位
   @JsonIgnore
   private List< MappingVO > positions = new ArrayList< MappingVO >();

   // 职级
   @JsonIgnore
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();

   // 办公地点
   @JsonIgnore
   private List< MappingVO > locations = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< String > employeeNoList = new ArrayList< String >();

   // 毕业学校
   private String graduateSchool;

   // 专业
   private String major;

   // 办公地点
   private String workPlace;

   private String parentBranchs;

   @JsonIgnore
   private List< MappingVO > countries = new ArrayList< MappingVO >();

   // 银行
   @JsonIgnore
   private List< MappingVO > banks = new ArrayList< MappingVO >();

   // 业务类型
   @JsonIgnore
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   @JsonIgnore
   private TableDTO tableDTO;

   /**
    *  For Search
    */
   // “订单ID”

   private String orderId;

   //11 “客户编号”
   private String clientNumber;

   // “客户名称（中文）”
   private String clientNameZH;

   // “客户名称（英文）”
   private String clientNameEN;

   // “劳动合同（开始有效期）”
   private String contractStartDate;

   // “劳动合同（结束有效期）”
   private String contractEndDate;

   // “出生年月（开始）”
   private String birthdayStart;

   // “出生年月（结束）”
   private String birthdayEnd;

   // “首次参加工作日期（开始）”
   private String startWorkDateStart;

   // “首次参加工作日期（结束）”
   private String startWorkDateEnd;

   // 是否包含“在职”服务协议
   private String containValidContractFlag;

   public String[] getSkillIdArray()
   {
      return skillIdArray;
   }

   public void setSkillIdArray( String[] skillIdArray )
   {
      this.skillIdArray = skillIdArray;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
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

   public String getSalutation()
   {
      return salutation;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
   }

   public String getBirthday()
   {
      return KANUtil.filterEmpty( decodeDate( this.birthday ) );
   }

   public void setBirthday( String birthday )
   {
      this.birthday = birthday;
   }

   public String getMaritalStatus()
   {
      return maritalStatus;
   }

   public void setMaritalStatus( String maritalStatus )
   {
      this.maritalStatus = maritalStatus;
   }

   public String getNationNality()
   {
      return nationNality;
   }

   public void setNationNality( String nationNality )
   {
      this.nationNality = nationNality;
   }

   public String getBirthdayPlace()
   {
      return birthdayPlace;
   }

   public void setBirthdayPlace( String birthdayPlace )
   {
      this.birthdayPlace = birthdayPlace;
   }

   public String getResidencyCityId()
   {
      return KANUtil.filterEmpty( residencyCityId );
   }

   public void setResidencyCityId( String residencyCityId )
   {
      this.residencyCityId = residencyCityId;
   }

   public String getResidencyAddress()
   {
      return residencyAddress;
   }

   public void setResidencyAddress( String residencyAddress )
   {
      this.residencyAddress = residencyAddress;
   }

   public String getPersonalAddress()
   {
      return personalAddress;
   }

   public void setPersonalAddress( String personalAddress )
   {
      this.personalAddress = personalAddress;
   }

   public String getPersonalPostcode()
   {
      return personalPostcode;
   }

   public void setPersonalPostcode( String personalPostcode )
   {
      this.personalPostcode = personalPostcode;
   }

   public String getHighestEducation()
   {
      return highestEducation;
   }

   public void setHighestEducation( String highestEducation )
   {
      this.highestEducation = highestEducation;
   }

   public String getRecordNo()
   {
      return recordNo;
   }

   public void setRecordNo( String recordNo )
   {
      this.recordNo = recordNo;
   }

   public String getRecordAddress()
   {
      return recordAddress;
   }

   public void setRecordAddress( String recordAddress )
   {
      this.recordAddress = recordAddress;
   }

   public String getResidencyType()
   {
      return KANUtil.filterEmpty( residencyType ) == null ? "0" : residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getGraduationDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.graduationDate ) );
   }

   public void setGraduationDate( String graduationDate )
   {
      this.graduationDate = graduationDate;
   }

   public String getStartWorkDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.startWorkDate ) );
   }

   public void setStartWorkDate( String startWorkDate )
   {
      this.startWorkDate = startWorkDate;
   }

   public String getHasForeignerWorkLicence()
   {
      return hasForeignerWorkLicence;
   }

   public void setHasForeignerWorkLicence( String hasForeignerWorkLicence )
   {
      this.hasForeignerWorkLicence = hasForeignerWorkLicence;
   }

   public String getForeignerWorkLicenceNo()
   {
      return foreignerWorkLicenceNo;
   }

   public void setForeignerWorkLicenceNo( String foreignerWorkLicenceNo )
   {
      this.foreignerWorkLicenceNo = foreignerWorkLicenceNo;
   }

   public String getForeignerWorkLicenceEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.foreignerWorkLicenceEndDate ) );
   }

   public void setForeignerWorkLicenceEndDate( String foreignerWorkLicenceEndDate )
   {
      this.foreignerWorkLicenceEndDate = foreignerWorkLicenceEndDate;
   }

   public String getHasResidenceLicence()
   {
      return hasResidenceLicence;
   }

   public void setHasResidenceLicence( String hasResidenceLicence )
   {
      this.hasResidenceLicence = hasResidenceLicence;
   }

   public String getResidenceNo()
   {
      return residenceNo;
   }

   public void setResidenceNo( String residenceNo )
   {
      this.residenceNo = residenceNo;
   }

   public String getResidenceStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.residenceStartDate ) );
   }

   public void setResidenceStartDate( String residenceStartDate )
   {
      this.residenceStartDate = residenceStartDate;
   }

   public String getResidenceEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.residenceEndDate ) );
   }

   public void setResidenceEndDate( String residenceEndDate )
   {
      this.residenceEndDate = residenceEndDate;
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

   public String getCertificateStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.certificateStartDate ) );
   }

   public void setCertificateStartDate( String certificateStartDate )
   {
      this.certificateStartDate = certificateStartDate;
   }

   public String getCertificateEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.certificateEndDate ) );
   }

   public void setCertificateEndDate( String certificateEndDate )
   {
      this.certificateEndDate = certificateEndDate;
   }

   public String getCertificateAwardFrom()
   {
      return certificateAwardFrom;
   }

   public void setCertificateAwardFrom( String certificateAwardFrom )
   {
      this.certificateAwardFrom = certificateAwardFrom;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public final String getBankBranch()
   {
      return bankBranch;
   }

   public final void setBankBranch( String bankBranch )
   {
      this.bankBranch = bankBranch;
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getPhone1()
   {
      return phone1;
   }

   public void setPhone1( String phone1 )
   {
      this.phone1 = phone1;
   }

   public String getMobile1()
   {
      return mobile1;
   }

   public void setMobile1( String mobile1 )
   {
      this.mobile1 = mobile1;
   }

   public String getEmail1()
   {
      return email1;
   }

   public void setEmail1( String email1 )
   {
      this.email1 = email1;
   }

   public String getWebsite1()
   {
      return website1;
   }

   public void setWebsite1( String website1 )
   {
      this.website1 = website1;
   }

   public String getPhone2()
   {
      return phone2;
   }

   public void setPhone2( String phone2 )
   {
      this.phone2 = phone2;
   }

   public String getMobile2()
   {
      return mobile2;
   }

   public void setMobile2( String mobile2 )
   {
      this.mobile2 = mobile2;
   }

   public String getEmail2()
   {
      return email2;
   }

   public void setEmail2( String email2 )
   {
      this.email2 = email2;
   }

   public String getWebsite2()
   {
      return website2;
   }

   public void setWebsite2( String website2 )
   {
      this.website2 = website2;
   }

   public String getIm1Type()
   {
      return im1Type;
   }

   public void setIm1Type( String im1Type )
   {
      this.im1Type = im1Type;
   }

   public String getIm1()
   {
      return im1;
   }

   public void setIm1( String im1 )
   {
      this.im1 = im1;
   }

   public String getIm2Type()
   {
      return im2Type;
   }

   public void setIm2Type( String im2Type )
   {
      this.im2Type = im2Type;
   }

   public String getIm2()
   {
      return im2;
   }

   public void setIm2( String im2 )
   {
      this.im2 = im2;
   }

   public String getIm3Type()
   {
      return im3Type;
   }

   public void setIm3Type( String im3Type )
   {
      this.im3Type = im3Type;
   }

   public String getIm3()
   {
      return im3;
   }

   public void setIm3( String im3 )
   {
      this.im3 = im3;
   }

   public String getIm4Type()
   {
      return im4Type;
   }

   public void setIm4Type( String im4Type )
   {
      this.im4Type = im4Type;
   }

   public String getIm4()
   {
      return im4;
   }

   public void setIm4( String im4 )
   {
      this.im4 = im4;
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getPhoto()
   {
      return photo;
   }

   public void setPhoto( String photo )
   {
      this.photo = photo;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String getResumeZH()
   {
      return resumeZH;
   }

   public void setResumeZH( String resumeZH )
   {
      this.resumeZH = resumeZH;
   }

   public String getResumeEN()
   {
      return resumeEN;
   }

   public void setResumeEN( String resumeEN )
   {
      this.resumeEN = resumeEN;
   }

   public String getPublicCode()
   {
      if ( KANUtil.filterEmpty( this.employeeId ) != null )
      {
         setPublicCode( Cryptogram.getPublicCode( this.employeeId ) );
      }
      return this.publicCode;
   }

   public final void setPublicCode( String publicCode )
   {
      this.publicCode = publicCode;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getResidencyProvinceId()
   {
      return residencyProvinceId;
   }

   public void setResidencyProvinceId( String residencyProvinceId )
   {
      this.residencyProvinceId = residencyProvinceId;
   }

   public String getCityIdTemp()
   {
      return cityIdTemp;
   }

   public void setCityIdTemp( String cityIdTemp )
   {
      this.cityIdTemp = cityIdTemp;
   }

   public List< MappingVO > getResidencyProvinces()
   {
      return residencyProvinces;
   }

   public void setResidencyProvinces( List< MappingVO > residencyProvinces )
   {
      this.residencyProvinces = residencyProvinces;
   }

   public List< MappingVO > getSalutations()
   {
      return salutations;
   }

   public void setSalutations( List< MappingVO > salutations )
   {
      this.salutations = salutations;
   }

   public List< MappingVO > getCertificateTypes()
   {
      return certificateTypes;
   }

   public void setCertificateTypes( List< MappingVO > certificateTypes )
   {
      this.certificateTypes = certificateTypes;
   }

   public String getNumberOfLaborContract()
   {
      return numberOfLaborContract;
   }

   public void setNumberOfLaborContract( String numberOfLaborContract )
   {
      this.numberOfLaborContract = numberOfLaborContract;
   }

   public String getNumberOfServiceContract()
   {
      return numberOfServiceContract;
   }

   public void setNumberOfServiceContract( String getCountNumberOfServiceContract )
   {
      this.numberOfServiceContract = getCountNumberOfServiceContract;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeId );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.employeeNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.salutation = "0";
      this.birthday = "";
      this.maritalStatus = "0";
      this.nationNality = "0";
      this.birthdayPlace = "";
      this.residencyCityId = "";
      this.residencyAddress = "";
      this.personalAddress = "";
      this.personalPostcode = "";
      this.highestEducation = "0";
      this.recordNo = "";
      this.recordAddress = "";
      this.residencyType = "0";
      this.graduationDate = "";
      this.startWorkDate = "";
      this.hasForeignerWorkLicence = "";
      this.foreignerWorkLicenceNo = "";
      this.foreignerWorkLicenceEndDate = "";
      this.hasResidenceLicence = "";
      this.residenceNo = "";
      this.residenceStartDate = "";
      this.residenceEndDate = "";
      this.certificateType = "0";
      this.certificateNumber = "";
      this.certificateStartDate = "";
      this.certificateEndDate = "";
      this.certificateAwardFrom = "";
      this.bankId = "0";
      this.bankBranch = "";
      this.bankAccount = "";
      this.phone1 = "";
      this.mobile1 = "";
      this.email1 = "";
      this.website1 = "";
      this.phone2 = "";
      this.mobile2 = "";
      this.email2 = "";
      this.website2 = "";
      this.im1Type = "0";
      this.im1 = "";
      this.im2Type = "0";
      this.im2 = "";
      this.im3Type = "0";
      this.im3 = "";
      this.im4Type = "0";
      this.im4 = "";
      this.branch = "0";
      this.owner = "0";
      this.photo = "";
      this.attachment = "";
      this.resumeZH = "";
      this.resumeEN = "";
      this.description = "";
      this.skillIdArray = new String[] {};
      this.positionIdArray = new String[] {};
      this.attachmentArray = new String[] {};
      this.businessTypeId = "0";
      super.setStatus( "0" );
      this.healthCardNo = "";
      this.healthCardStartDate = "";
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.residencyProvinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      this.maritalStatuses = KANUtil.getMappings( this.getLocale(), "security.staff.marital.statuses" );

      if ( this.residencyProvinces != null )
      {
         this.residencyProvinces.add( 0, super.getEmptyMappingVO() );
      }
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      this.positionGrades = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionGrades( request.getLocale().getLanguage(), super.getCorpId() );
      this.locations = KANConstants.getKANAccountConstants( super.getAccountId() ).getLocations( request.getLocale().getLanguage(), super.getCorpId() );
      this.highestEducations = KANConstants.getKANAccountConstants( super.getAccountId() ).getEducations( request.getLocale().getLanguage(), super.getCorpId() );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.employee.statuses" ) );
      this.countries = KANConstants.getKANAccountConstants( super.getAccountId() ).getCountries( request.getLocale().getLanguage(), super.getCorpId() );

      this.branchs.add( 0, super.getEmptyMappingVO() );
      this.positionGrades.add( 0, super.getEmptyMappingVO() );
      this.banks = KANConstants.getKANAccountConstants( super.getAccountId() ).getBanks( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.tableDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( "HRO_BIZ_EMPLOYEE_IN_HOUSE" );
      this.changeReasons = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final EmployeeVO employeeVO = ( EmployeeVO ) object;
      this.employeeNo = employeeVO.getEmployeeNo();
      this.nameZH = employeeVO.getNameZH();
      this.nameEN = employeeVO.getNameEN();
      this.salutation = employeeVO.getSalutation();
      this.birthday = employeeVO.getBirthday();
      this.maritalStatus = employeeVO.getMaritalStatus();
      this.nationNality = employeeVO.getNationNality();
      this.birthdayPlace = employeeVO.getBirthdayPlace();
      this.residencyCityId = employeeVO.getResidencyCityId();
      this.residencyCity = employeeVO.getResidencyCity();
      this.residencyAddress = employeeVO.getResidencyAddress();
      this.personalAddress = employeeVO.getPersonalAddress();
      this.personalPostcode = employeeVO.getPersonalPostcode();
      this.highestEducation = employeeVO.getHighestEducation();
      this.recordNo = employeeVO.getRecordNo();
      this.recordAddress = employeeVO.getRecordAddress();
      this.residencyType = employeeVO.getResidencyType();
      this.graduationDate = employeeVO.getGraduationDate();
      this.onboardDate = employeeVO.getOnboardDate();
      this.startWorkDate = employeeVO.getStartWorkDate();
      this.hasForeignerWorkLicence = employeeVO.getHasForeignerWorkLicence();
      this.foreignerWorkLicenceNo = employeeVO.getForeignerWorkLicenceNo();
      this.foreignerWorkLicenceEndDate = employeeVO.getForeignerWorkLicenceEndDate();
      this.hasResidenceLicence = employeeVO.getHasResidenceLicence();
      this.residenceNo = employeeVO.getRecordNo();
      this.residenceStartDate = employeeVO.getResidenceStartDate();
      this.residenceEndDate = employeeVO.getResidenceEndDate();
      this.certificateType = employeeVO.getCertificateType();
      this.certificateNumber = employeeVO.getCertificateNumber();
      this.certificateStartDate = employeeVO.getCertificateStartDate();
      this.certificateEndDate = employeeVO.getCertificateEndDate();
      this.certificateAwardFrom = employeeVO.getCertificateAwardFrom();
      this.bankId = employeeVO.getBankId();
      this.bankBranch = employeeVO.getBankBranch();
      this.bankAccount = employeeVO.getBankAccount();
      this.phone1 = employeeVO.getPhone1();
      this.mobile1 = employeeVO.getMobile1();
      this.email1 = employeeVO.getEmail1() == null ? null : employeeVO.getEmail1().trim();
      this.website1 = employeeVO.getWebsite1();
      this.phone2 = employeeVO.getPhone2();
      this.mobile2 = employeeVO.getMobile2();
      this.email2 = employeeVO.getEmail2();
      this.website2 = employeeVO.getWebsite2();
      this.im1Type = employeeVO.getIm1Type();
      this.im1 = employeeVO.getIm1();
      this.im2Type = employeeVO.getIm2Type();
      this.im2 = employeeVO.getIm2();
      this.im3Type = employeeVO.getIm3Type();
      this.im3 = employeeVO.getIm3();
      this.im4Type = employeeVO.getIm4Type();
      this.im4 = employeeVO.getIm4();
      this.branch = employeeVO.getBranch();
      this.owner = employeeVO.getOwner();
      this.photo = employeeVO.getPhoto();
      this.attachment = employeeVO.getAttachment();
      this.resumeZH = employeeVO.getResumeZH();
      this.resumeEN = employeeVO.getResumeEN();
      this.description = employeeVO.getDescription();
      this.skillIdArray = employeeVO.getSkillIdArray();
      this.positionIdArray = employeeVO.getPositionIdArray();
      this.attachmentArray = employeeVO.getAttachmentArray();
      this.username = employeeVO.getUsername();
      this.businessTypeId = employeeVO.getBusinessTypeId();
      super.setStatus( employeeVO.getStatus() );
      super.setModifyDate( new Date() );
      this.healthCardNo = employeeVO.getHealthCardNo();
      this.healthCardStartDate = employeeVO.getHealthCardStartDate();
   }

   @JsonIgnore
   public String getName()
   {
      if ( this.getLocale() != null )
      {
         return getName( this.getLocale().getLanguage() );
      }
      else
      {
         return nameZH;
      }
   }

   public String getName( final String language )
   {
      if ( language != null && language.equalsIgnoreCase( "ZH" ) )
      {
         return nameZH;
      }
      else
      {
         return nameEN;
      }
   }

   public String getDecodeResidencyCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( residencyCityId, super.getLocale().getLanguage() );
   }

   public List< MappingVO > getResidencyCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
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

   public String getContractStartDate()
   {
      return contractStartDate;
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return contractEndDate;
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public String getBirthdayStart()
   {
      return birthdayStart;
   }

   public void setBirthdayStart( String birthdayStart )
   {
      this.birthdayStart = birthdayStart;
   }

   public String getBirthdayEnd()
   {
      return birthdayEnd;
   }

   public void setBirthdayEnd( String birthdayEnd )
   {
      this.birthdayEnd = birthdayEnd;
   }

   public String getStartWorkDateStart()
   {
      return startWorkDateStart;
   }

   public void setStartWorkDateStart( String startWorkDateStart )
   {
      this.startWorkDateStart = startWorkDateStart;
   }

   public String getStartWorkDateEnd()
   {
      return startWorkDateEnd;
   }

   public void setStartWorkDateEnd( String startWorkDateEnd )
   {
      this.startWorkDateEnd = startWorkDateEnd;
   }

   public List< MappingVO > getMaritalStatuses()
   {
      return maritalStatuses;
   }

   public void setMaritalStatuses( List< MappingVO > maritalStatuses )
   {
      this.maritalStatuses = maritalStatuses;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String getContainValidContractFlag()
   {
      return containValidContractFlag;
   }

   public void setContainValidContractFlag( String containValidContractFlag )
   {
      this.containValidContractFlag = containValidContractFlag;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public void update_mobile( StaffVO staffVO )
   {
      this.setNameZH( staffVO.getNameZH() );
      this.setNameEN( staffVO.getNameEN() );
      this.setSalutation( staffVO.getSalutation() );
      this.setCertificateNumber( staffVO.getCertificateNumber() );
      this.setMobile1( staffVO.getPersonalMobile() );
      this.setPhone1( staffVO.getPersonalPhone() );
      this.setEmail1( staffVO.getPersonalEmail() );
      this.setPersonalAddress( staffVO.getPersonalAddress() );
      this.setBankId( staffVO.getBankId() );
      this.setBankAccount( staffVO.getBankAccount() );
      super.setModifyDate( new Date() );
   }

   public String getOnboardDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.onboardDate ) );
   }

   public void setOnboardDate( String onboardDate )
   {
      this.onboardDate = onboardDate;
   }

   public String get_tempParentPositionIds()
   {
      return _tempParentPositionIds;
   }

   public void set_tempParentPositionIds( String _tempParentPositionIds )
   {
      this._tempParentPositionIds = _tempParentPositionIds;
   }

   public String get_tempUsername()
   {
      return _tempUsername;
   }

   public void set_tempUsername( String _tempUsername )
   {
      this._tempUsername = _tempUsername;
   }

   public String get_tempPositionIds()
   {
      return _tempPositionIds;
   }

   public void set_tempPositionIds( String _tempPositionIds )
   {
      this._tempPositionIds = _tempPositionIds;
   }

   public String get_tempBranchIds()
   {
      return _tempBranchIds;
   }

   public void set_tempBranchIds( String _tempBranchIds )
   {
      this._tempBranchIds = _tempBranchIds;
   }

   public String get_tempParentBranchIds()
   {
      return _tempParentBranchIds;
   }

   public void set_tempParentBranchIds( String _tempParentBranchIds )
   {
      this._tempParentBranchIds = _tempParentBranchIds;
   }

   public String get_tempParentPositionOwners()
   {
      return _tempParentPositionOwners;
   }

   public void set_tempParentPositionOwners( String _tempParentPositionOwners )
   {
      this._tempParentPositionOwners = _tempParentPositionOwners;
   }

   public String get_tempParentPositionBranchIds()
   {
      return _tempParentPositionBranchIds;
   }

   public void set_tempParentPositionBranchIds( String _tempParentPositionBranchIds )
   {
      this._tempParentPositionBranchIds = _tempParentPositionBranchIds;
   }

   public String get_tempPositionLocationIds()
   {
      return _tempPositionLocationIds;
   }

   public void set_tempPositionLocationIds( String _tempPositionLocationIds )
   {
      this._tempPositionLocationIds = _tempPositionLocationIds;
   }

   public List< MappingVO > getHighestEducations()
   {
      return highestEducations;
   }

   public void setHighestEducations( List< MappingVO > highestEducations )
   {
      this.highestEducations = highestEducations;
   }

   public List< String > getEmployeeNoList()
   {
      return employeeNoList;
   }

   public void setEmployeeNoList( List< String > employeeNoList )
   {
      this.employeeNoList = employeeNoList;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public String decodeBranchIds( final String branchIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( branchIds ) != null && branchs != null && branchs.size() > 0 )
      {
         for ( String branchId : branchIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( branchId, branchs, true );
            }
            else
            {
               returnStr = returnStr + "、" + decodeField( branchId, branchs, true );
            }
         }
      }

      return returnStr;
   }

   public String getDecode_tempParentBranchIds()
   {
      return decodeBranchIds( _tempParentBranchIds );
   }

   public String getDecode_tempParentPositionIds()
   {
      return decodeParentPositionIds( _tempPositionIds );
   }

   private String decodeParentPositionIds( String positionId )
   {
      String result = "";
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         String[] positionIds = positionId.split( "," );
         if ( positionIds != null && positionIds.length > 0 )
         {
            for ( String pid : positionIds )
            {
               final PositionVO positionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( pid );
               if ( positionVO != null )
               {
                  final PositionVO parentPositionVO = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionVOByPositionId( positionVO.getParentPositionId() );
                  if ( parentPositionVO != null )
                  {
                     result += parentPositionVO.getTitleZH() + ",";
                  }
               }
            }
         }
      }
      return KANUtil.filterEmpty( result ) == null ? "" : result.substring( 0, result.length() - 1 );
   }

   public String getDecode_tempBranchIds()
   {
      return decodeBranchIds( _tempBranchIds );
   }

   public String getDecode_tempPositionIds()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( _tempPositionIds ) != null )
      {
         final String[] positionArray = _tempPositionIds.split( "," );
         if ( positionArray != null && positionArray.length > 0 )
         {
            for ( String position : positionArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( position, positions );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( position, positions );
               }
            }
         }
      }

      return returnString;
   }

   public String getDecodePositionGrades()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( _tempPositionIds ) != null )
      {
         final String[] positionArray = _tempPositionIds.split( "," );
         if ( positionArray != null && positionArray.length > 0 )
         {
            KANAccountConstants kanAccountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
            for ( String position : positionArray )
            {
               kanAccountConstants.getPositionVOByPositionId( position );
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  final PositionVO positionVO = kanAccountConstants.getPositionVOByPositionId( position );
                  if ( positionVO != null )
                  {
                     returnString = positionVO.getDecodePositionGradeId();
                  }
                  else
                  {
                     returnString = "";
                  }
               }
               else
               {
                  returnString = returnString + "、" + kanAccountConstants.getPositionVOByPositionId( position ).getDecodePositionGradeId();
               }
            }
         }
      }

      return returnString;
   }

   public String getDecode_tempPositionGradeIds()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( _tempPositionGradeIds ) != null )
      {
         final String[] positionGradeArray = _tempPositionGradeIds.split( "," );
         if ( positionGradeArray != null && positionGradeArray.length > 0 )
         {
            for ( String positionGrade : positionGradeArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( positionGrade, positionGrades );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( positionGrade, positionGrades );
               }
            }
         }
      }

      return returnString;
   }

   public String getDecode_tempPositionLocationIds()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( _tempPositionLocationIds ) != null )
      {
         final String[] positionLocationArray = _tempPositionLocationIds.split( "," );
         if ( positionLocationArray != null && positionLocationArray.length > 0 )
         {
            for ( String locationId : positionLocationArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( locationId, locations );
               }
               else
               {
                  returnString = returnString + "、" + decodeField( locationId, locations );
               }
            }
         }
      }

      return returnString;
   }

   public String getOwnerBizEmail()
   {
      return ownerBizEmail;
   }

   public void setOwnerBizEmail( String ownerBizEmail )
   {
      this.ownerBizEmail = ownerBizEmail;
   }

   public String getOwnerPersonalEmail()
   {
      return ownerPersonalEmail;
   }

   public void setOwnerPersonalEmail( String ownerPersonalEmail )
   {
      this.ownerPersonalEmail = ownerPersonalEmail;
   }

   public String getGraduateSchool()
   {
      return graduateSchool;
   }

   public void setGraduateSchool( String graduateSchool )
   {
      this.graduateSchool = graduateSchool;
   }

   public String getMajor()
   {
      return major;
   }

   public void setMajor( String major )
   {
      this.major = major;
   }

   public String getWorkPlace()
   {
      workPlace = getDecode_tempPositionLocationIds();
      return workPlace;
   }

   public void setWorkPlace( String workPlace )
   {
      this.workPlace = workPlace;
   }

   public String getParentBranchs()
   {
      return parentBranchs;
   }

   public void setParentBranchs( String parentBranchs )
   {
      this.parentBranchs = parentBranchs;
   }

   public String getResidencyCity()
   {
      return residencyCity;
   }

   public void setResidencyCity( String residencyCity )
   {
      this.residencyCity = residencyCity;
   }

   public final String getAccountName()
   {
      return accountName;
   }

   public final void setAccountName( String accountName )
   {
      this.accountName = accountName;
   }

   public List< MappingVO > getCountries()
   {
      return countries;
   }

   public void setCountries( List< MappingVO > countries )
   {
      this.countries = countries;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String get_tempPositionGradeIds()
   {
      return _tempPositionGradeIds;
   }

   public void set_tempPositionGradeIds( String _tempPositionGradeIds )
   {
      this._tempPositionGradeIds = _tempPositionGradeIds;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

   public String getHealthCardStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( healthCardStartDate ) );
   }

   public void setHealthCardStartDate( String healthCardStartDate )
   {
      this.healthCardStartDate = healthCardStartDate;
   }

   public String getHealthCardNo()
   {
      return healthCardNo;
   }

   public void setHealthCardNo( String healthCardNo )
   {
      this.healthCardNo = healthCardNo;
   }

   public String getNoticeFlag()
   {
      return noticeFlag;
   }

   public void setNoticeFlag( String noticeFlag )
   {
      this.noticeFlag = noticeFlag;
   }

   public String getHireAgain()
   {
      return hireAgain;
   }

   public void setHireAgain( String hireAgain )
   {
      this.hireAgain = hireAgain;
   }

   public List< MappingVO > getLocations()
   {
      return locations;
   }

   public void setLocations( List< MappingVO > locations )
   {
      this.locations = locations;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getChangeReasons()
   {
      return changeReasons;
   }

   public void setChangeReasons( List< MappingVO > changeReasons )
   {
      this.changeReasons = changeReasons;
   }

   /*
    * Decode method list
    */
   // 解译性别
   public String getDecodeSalutation()
   {
      return decodeField( this.getSalutation(), this.getSalutations() );
   }

   // 解译婚否
   public String getDecodeMaritalStatus()
   {
      return decodeField( this.getMaritalStatus(), this.getMaritalStatuses() );
   }

   // 解译最高学历
   public String getDecodeHighestEducation()
   {
      return decodeField( highestEducation, highestEducations );
   }

   // 解译户籍类型
   public String getDecodeResidencyType()
   {
      return decodeField( this.getResidencyType(), KANUtil.getMappings( this.getLocale(), "sys.sb.residency" ) );
   }

   // 解译国籍
   public String getDecodeNationNality()
   {
      return decodeField( this.nationNality, this.countries );
   }

   // 解译对接部门
   public String getDecodeBranch()
   {
      return decodeField( branch, branchs, true );
   }

   // 解译对接人
   public String getDecodeOwner()
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // 解译证件类型
   public String getDecodeCertificateType()
   {
      return decodeField( this.certificateType, this.certificateTypes );
   }

   // 解译银行
   public String getDecodeBankId()
   {
      return decodeField( this.bankId, this.banks );
   }

   // 解译业务类型
   public String getDecodeBusinessTypeId()
   {
      return decodeField( businessTypeId, businessTypes );
   }

   // 外国人就业许可证
   public String getDecodeHasForeignerWorkLicence()
   {
      return decodeField( hasForeignerWorkLicence, super.getFlags() );
   }

   // 居住证
   public String getDecodeHasResidenceLicence()
   {
      return decodeField( hasResidenceLicence, super.getFlags() );
   }

   // 异动原因
   public String getDecodeChangeReason()
   {
      return decodeField( super.getRemark3(), changeReasons );
   }

   // 特殊 民族
   public String getDecodeMinzu() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";

      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "minzu" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11223" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "minzu" ).toString(), mappingVOs );
      }

      return "";
   }

   // 特殊 雇员类型
   public String getDecodeGuyuanleixing() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "guyuanleixing" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "13368" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "guyuanleixing" ).toString(), mappingVOs );
      }

      return "";
   }

   // 特殊 户口性质2
   public String getDecodeHukouxingzhier() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "hukouxingzhier" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11263" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "hukouxingzhier" ).toString(), mappingVOs );
      }

      return "";
   }

   // 特殊 办公地点
   public String getDecodeBangongdidian() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "bangongdidian" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11257" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "bangongdidian" ).toString(), mappingVOs );
      }

      return "";
   }

   // 特殊 招聘预算
   public String getDecodeZhaopinyuanyin() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "zhaopinyuanyin" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11229" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "zhaopinyuanyin" ).toString(), mappingVOs );
      }

      return "";
   }

   // 特殊 招聘渠道
   public String getDecodeZhaopinqudao() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getRemark1() ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( super.getRemark1() );
      if ( o != null && o.get( "zhaopinqudao" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11197" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "zhaopinqudao" ).toString(), mappingVOs );
      }

      return "";
   }
   
   public String getDecode_tempParentPositionOwners()
   {
      if ( KANUtil.filterEmpty( super.getRemark5() ) != null && "3".equals( super.getStatus() ) )
      {
         return super.getRemark5();
      }

      String result = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( this.getEmployeeId() );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getParentPositionId() );
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO staffDTO : parentStaffDTOs )
                  {
                     final StaffVO parentStaffVO = staffDTO.getStaffVO();
                     if ( parentStaffVO != null )
                     {
                        final PositionVO parentMainPositionVO = accountConstants.getMainPositionVOByStaffId( parentStaffVO.getStaffId() );
                        if ( parentMainPositionVO != null )
                        {
                           if ( KANUtil.filterEmpty( result ) == null )
                              result = accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), super.getAccountId().equals( "100017" ) ? true : false );
                           else
                              result = result + "、" + accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), super.getAccountId().equals( "100017" ) ? true : false );
                        }
                     }
                  }
               }
            }
         }
      }

      return result;
   }
   
   public String getShortName()
   {
      String shortName = "";
      try
      {
         if ( KANUtil.filterEmpty( super.getRemark1() ) != null )
         {
            JSONObject jsonObject = JSONObject.fromObject( super.getRemark1() );

            if ( KANUtil.filterEmpty( "jiancheng" ) != null && jsonObject != null )
            {
               shortName = ( String ) jsonObject.get( "jiancheng" );
            }
         }
      }
      catch ( Exception e )
      {
         return "";
      }
      return shortName;
   }

}