package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONObject;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class IClickEmployeeReportView extends BaseVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 7945165786371478268L;

   /**
    * For View
    */
   private String contractId;
   private String employeeId;
   private String employeeNameZH;
   private String employeeNameEN;
   private String employStatus;
   private String entityId;
   private String entityNameZH;
   private String entityNameEN;
   private String entityTitle;
   private String parentBranchNameEN;
   private String branchNameZH;
   private String branchNameEN;
   private String settlementBranch;
   private String businessTypeId;
   private String nationNality;
   private String salutation;
   private String certificateType;
   private String certificateNumber;
   private String certificateEndDate;
   private String birthday;
   private String maritalStatus;
   private String highestEducation;
   private String schoolNames;
   private String majors;
   private String graduateDates;
   private String residencyAddress;
   private String residencyType;
   private String personalAddress;
   private String mobile1;
   private String phone1;
   private String emergencyNames;
   private String relationshipIds;
   private String mobiles;
   private String phones;
   private String bizEmail;
   private String personalEmail;
   private String bankId;
   private String bankAccount;
   private String startWorkDate;
   private String contractStartDate;
   private String probationEndDate;
   private String residencyCityId;
   private String resignDate;
   private String leaveReasons;

   private String employeeRemark1;
   private String employeeContractRemark1;

   private String _tempParentBranchIds;
   private String _tempBranchIds;
   private String _tempPositionGradeIds;
   private String _tempParentPositionOwners;

   private String contractStatus;
   private String owner;

   /**
    * For APP
    */
   // 薪资方案开始时间
   private String salaryStartDateStart;

   //薪资方案 结束时间
   private String salaryStartDateEnd;

   // 薪资方案开始时间
   private String salaryEndDateStart;

   //薪资方案 结束时间
   private String salaryEndDateEnd;

   // 显示审核链接
   private String otherLink;
   
   // 自定义字段搜索集合1
   private Set< String > remark1Set;
   
   // 自定义字段搜索集合2
   private Set< String > remark2Set;

   // 雇佣状态
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();
   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();
   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   private List< MappingVO > branchs_zh = new ArrayList< MappingVO >();
   private List< MappingVO > branchs_en = new ArrayList< MappingVO >();
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   // 职级
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   // 国籍
   private List< MappingVO > countries = new ArrayList< MappingVO >();
   // 性別
   private List< MappingVO > salutations = new ArrayList< MappingVO >();
   // 证件类型
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();
   // 婚姻状况
   private List< MappingVO > maritalStatuses = new ArrayList< MappingVO >();
   // 最高学历
   private List< MappingVO > highestEducations = new ArrayList< MappingVO >();
   // 户口性质
   private List< MappingVO > residencyTypes = new ArrayList< MappingVO >();
   // 紧急联系人关系
   private List< MappingVO > relationships = new ArrayList< MappingVO >();
   // 银行
   private List< MappingVO > banks = new ArrayList< MappingVO >();
   // 附加薪资信息
   private List< MappingVO > salarys = new ArrayList< MappingVO >();
   // 合同状态
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.entitys.add( 0, getEmptyMappingVO() );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      this.branchs_zh = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( "zh", super.getCorpId() );
      this.branchs_en = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( "en", super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.positionGrades = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionGrades( request.getLocale().getLanguage(), super.getCorpId() );
      this.countries = KANConstants.getKANAccountConstants( super.getAccountId() ).getCountries( request.getLocale().getLanguage(), super.getCorpId() );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      this.maritalStatuses = KANUtil.getMappings( this.getLocale(), "security.staff.marital.statuses" );
      this.highestEducations = KANConstants.getKANAccountConstants( super.getAccountId() ).getEducations( request.getLocale().getLanguage(), super.getCorpId() );
      this.residencyTypes = KANUtil.getMappings( this.getLocale(), "sys.sb.residency" );
      this.relationships = KANUtil.getMappings( this.getLocale(), "business.employee.emergency.relationshipIds" );
      this.banks = KANConstants.getKANAccountConstants( super.getAccountId() ).getBanks( request.getLocale().getLanguage(), super.getCorpId() );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
   }

   @SuppressWarnings("unchecked")
   @Override
   public String getRemark1()
   {
      final JSONObject jsonObject = new JSONObject();
      final Map< String, String > map = new HashMap< String, String >();
      if ( KANUtil.filterEmpty( employeeRemark1 ) != null )
      {
         map.putAll( JSONObject.fromObject( employeeRemark1 ) );
      }
      if ( KANUtil.filterEmpty( employeeContractRemark1 ) != null )
      {
         map.putAll( JSONObject.fromObject( employeeContractRemark1 ) );
      }
      jsonObject.putAll( map );
      return map.isEmpty() ? super.getRemark1() : jsonObject.toString();
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // NO Use
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // NO Use
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // NO Use
   }

   /**
    * Decode Method 
    */
   public String getDecodeEmployStatus()
   {
      return decodeField( this.getEmployStatus(), this.employStatuses );
   }

   public String getDecodeBUFunction()
   {
      return getDecodeStrByCondition( this._tempParentBranchIds, branchs_en, true );
   }

   public String getDecodeBranchNameZH()
   {
      return getDecodeStrByCondition( this._tempBranchIds, branchs_zh, true );
   }

   public String getDecodeBranchNameEN()
   {
      return getDecodeStrByCondition( this._tempBranchIds, branchs_en, true );
   }

   public String getDecodeSettlementBranch()
   {
      return decodeField( this.getSettlementBranch(), this.branchs, true );
   }

   public String getDecodeBusinessTypeId()
   {
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }

   public String getDecode_tempPositionGradeIds()
   {
      return getDecodeStrByCondition( this._tempPositionGradeIds, positionGrades );
   }

   public String getDecodeNationNality()
   {
      return decodeField( this.nationNality, this.countries );
   }

   public String getDecodeSalutation()
   {
      return decodeField( this.getSalutation(), this.getSalutations() );
   }

   public String getDecodeCertificateType()
   {
      return decodeField( this.certificateType, this.certificateTypes );
   }

   public String getDecodeMaritalStatus()
   {
      return decodeField( this.getMaritalStatus(), this.getMaritalStatuses() );
   }

   public String getDecodeHighestEducation()
   {
      return decodeField( this.highestEducation, this.highestEducations );
   }

   public String getDecodeResidencyType()
   {
      return decodeField( this.residencyType, this.residencyTypes );
   }

   public String getDecodeRelationshipIds()
   {
      return getDecodeStrByCondition( relationshipIds, relationships );
   }

   public String getDecodeBankId()
   {
      return decodeField( this.bankId, this.banks );
   }

   public String getDecodeResidencyCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( residencyCityId, super.getLocale().getLanguage() );
   }

   public String getDecodeGraduateDates()
   {
      return getDecodeDates( graduateDates );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( contractStatus, contractStatuses );
   }

   public String getDecodeDates( final String dates )
   {
      String result = "";
      if ( KANUtil.filterEmpty( dates ) != null )
      {
         for ( String date : dates.split( "、" ) )
         {
            if ( KANUtil.filterEmpty( result ) == null )
            {
               result = KANUtil.formatDate( date, "yyyy-MM-dd" );
            }
            else
            {
               result = result + "、" + KANUtil.formatDate( date, "yyyy-MM-dd" );
            }
         }
      }

      return result;
   }

   public String getDecodeContactType()
   {
      String result = "";
      if ( KANUtil.filterEmpty( this.phones ) != null )
      {
         result = result + " T:" + phones;
      }
      if ( KANUtil.filterEmpty( this.mobiles ) != null )
      {
         result = result + " M:" + mobiles;
      }
      return result;
   }

   public String getDecode_tempParentPositionOwners()
   {
      String result = "";
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( super.getAccountId() );
      if ( accountConstants != null )
      {
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
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

   private String getDecodeStrByCondition( final String ids, final List< MappingVO > mappingVOs, final boolean isTemp )
   {
      String result = "";
      if ( KANUtil.filterEmpty( ids ) != null )
      {
         for ( String id : ids.split( "," ) )
         {
            if ( KANUtil.filterEmpty( result ) == null )
            {
               result = decodeField( id, mappingVOs, isTemp );
            }
            else
            {
               result = result + "、" + decodeField( id, mappingVOs, isTemp );
            }
         }
      }

      return result;
   }

   /**
    * Get Set Method
    */
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

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getEntityNameZH()
   {
      return entityNameZH;
   }

   public void setEntityNameZH( String entityNameZH )
   {
      this.entityNameZH = entityNameZH;
   }

   public String getEntityNameEN()
   {
      return entityNameEN;
   }

   public void setEntityNameEN( String entityNameEN )
   {
      this.entityNameEN = entityNameEN;
   }

   public String getEntityTitle()
   {
      return entityTitle;
   }

   public void setEntityTitle( String entityTitle )
   {
      this.entityTitle = entityTitle;
   }

   public String getParentBranchNameEN()
   {
      return parentBranchNameEN;
   }

   public void setParentBranchNameEN( String parentBranchNameEN )
   {
      this.parentBranchNameEN = parentBranchNameEN;
   }

   public String getBranchNameZH()
   {
      return branchNameZH;
   }

   public void setBranchNameZH( String branchNameZH )
   {
      this.branchNameZH = branchNameZH;
   }

   public String getBranchNameEN()
   {
      return branchNameEN;
   }

   public void setBranchNameEN( String branchNameEN )
   {
      this.branchNameEN = branchNameEN;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getNationNality()
   {
      return nationNality;
   }

   public void setNationNality( String nationNality )
   {
      this.nationNality = nationNality;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
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

   public String getCertificateEndDate()
   {
      return certificateEndDate;
   }

   public void setCertificateEndDate( String certificateEndDate )
   {
      this.certificateEndDate = certificateEndDate;
   }

   public String getBirthday()
   {
      return birthday;
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

   public String getHighestEducation()
   {
      return highestEducation;
   }

   public void setHighestEducation( String highestEducation )
   {
      this.highestEducation = highestEducation;
   }

   public String getSchoolNames()
   {
      return schoolNames;
   }

   public void setSchoolNames( String schoolNames )
   {
      this.schoolNames = schoolNames;
   }

   public String getMajors()
   {
      return majors;
   }

   public void setMajors( String majors )
   {
      this.majors = majors;
   }

   public String getGraduateDates()
   {
      return graduateDates;
   }

   public void setGraduateDates( String graduateDates )
   {
      this.graduateDates = graduateDates;
   }

   public String getResidencyAddress()
   {
      return residencyAddress;
   }

   public void setResidencyAddress( String residencyAddress )
   {
      this.residencyAddress = residencyAddress;
   }

   public String getResidencyType()
   {
      return residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getPersonalAddress()
   {
      return personalAddress;
   }

   public void setPersonalAddress( String personalAddress )
   {
      this.personalAddress = personalAddress;
   }

   public String getMobile1()
   {
      return mobile1;
   }

   public void setMobile1( String mobile1 )
   {
      this.mobile1 = mobile1;
   }

   public String getPhone1()
   {
      return phone1;
   }

   public void setPhone1( String phone1 )
   {
      this.phone1 = phone1;
   }

   public String getEmergencyNames()
   {
      return emergencyNames;
   }

   public void setEmergencyNames( String emergencyNames )
   {
      this.emergencyNames = emergencyNames;
   }

   public String getRelationshipIds()
   {
      return relationshipIds;
   }

   public void setRelationshipIds( String relationshipIds )
   {
      this.relationshipIds = relationshipIds;
   }

   public String getMobiles()
   {
      return mobiles;
   }

   public void setMobiles( String mobiles )
   {
      this.mobiles = mobiles;
   }

   public String getPhones()
   {
      return phones;
   }

   public void setPhones( String phones )
   {
      this.phones = phones;
   }

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getPersonalEmail()
   {
      return personalEmail;
   }

   public void setPersonalEmail( String personalEmail )
   {
      this.personalEmail = personalEmail;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
   }

   public String getBankAccount()
   {
      return bankAccount;
   }

   public void setBankAccount( String bankAccount )
   {
      this.bankAccount = bankAccount;
   }

   public String getStartWorkDate()
   {
      return startWorkDate;
   }

   public void setStartWorkDate( String startWorkDate )
   {
      this.startWorkDate = startWorkDate;
   }

   public String getContractStartDate()
   {
      return contractStartDate;
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getProbationEndDate()
   {
      return probationEndDate;
   }

   public void setProbationEndDate( String probationEndDate )
   {
      this.probationEndDate = probationEndDate;
   }

   public String getResidencyCityId()
   {
      return residencyCityId;
   }

   public void setResidencyCityId( String residencyCityId )
   {
      this.residencyCityId = residencyCityId;
   }

   public String getResignDate()
   {
      return resignDate;
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getLeaveReasons()
   {
      return leaveReasons;
   }

   public void setLeaveReasons( String leaveReasons )
   {
      this.leaveReasons = leaveReasons;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public String getEmployeeContractRemark1()
   {
      return employeeContractRemark1;
   }

   public void setEmployeeContractRemark1( String employeeContractRemark1 )
   {
      this.employeeContractRemark1 = employeeContractRemark1;
   }

   public String get_tempParentBranchIds()
   {
      return _tempParentBranchIds;
   }

   public void set_tempParentBranchIds( String _tempParentBranchIds )
   {
      this._tempParentBranchIds = _tempParentBranchIds;
   }

   public String get_tempBranchIds()
   {
      return _tempBranchIds;
   }

   public void set_tempBranchIds( String _tempBranchIds )
   {
      this._tempBranchIds = _tempBranchIds;
   }

   public String get_tempPositionGradeIds()
   {
      return _tempPositionGradeIds;
   }

   public void set_tempPositionGradeIds( String _tempPositionGradeIds )
   {
      this._tempPositionGradeIds = _tempPositionGradeIds;
   }

   public String get_tempParentPositionOwners()
   {
      return _tempParentPositionOwners;
   }

   public void set_tempParentPositionOwners( String _tempParentPositionOwners )
   {
      this._tempParentPositionOwners = _tempParentPositionOwners;
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getBranchs_zh()
   {
      return branchs_zh;
   }

   public void setBranchs_zh( List< MappingVO > branchs_zh )
   {
      this.branchs_zh = branchs_zh;
   }

   public List< MappingVO > getBranchs_en()
   {
      return branchs_en;
   }

   public void setBranchs_en( List< MappingVO > branchs_en )
   {
      this.branchs_en = branchs_en;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public List< MappingVO > getCountries()
   {
      return countries;
   }

   public void setCountries( List< MappingVO > countries )
   {
      this.countries = countries;
   }

   private String getDecodeStrByCondition( final String ids, final List< MappingVO > mappingVOs )
   {
      return getDecodeStrByCondition( ids, mappingVOs, false );
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

   public List< MappingVO > getMaritalStatuses()
   {
      return maritalStatuses;
   }

   public void setMaritalStatuses( List< MappingVO > maritalStatuses )
   {
      this.maritalStatuses = maritalStatuses;
   }

   public List< MappingVO > getHighestEducations()
   {
      return highestEducations;
   }

   public void setHighestEducations( List< MappingVO > highestEducations )
   {
      this.highestEducations = highestEducations;
   }

   public List< MappingVO > getResidencyTypes()
   {
      return residencyTypes;
   }

   public void setResidencyTypes( List< MappingVO > residencyTypes )
   {
      this.residencyTypes = residencyTypes;
   }

   public List< MappingVO > getRelationships()
   {
      return relationships;
   }

   public void setRelationships( List< MappingVO > relationships )
   {
      this.relationships = relationships;
   }

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

   public List< MappingVO > getSalarys()
   {
      return salarys;
   }

   public void setSalarys( List< MappingVO > salarys )
   {
      this.salarys = salarys;
   }

   public String getSalaryStartDateStart()
   {
      return salaryStartDateStart;
   }

   public void setSalaryStartDateStart( String salaryStartDateStart )
   {
      this.salaryStartDateStart = salaryStartDateStart;
   }

   public String getSalaryStartDateEnd()
   {
      return salaryStartDateEnd;
   }

   public void setSalaryStartDateEnd( String salaryStartDateEnd )
   {
      this.salaryStartDateEnd = salaryStartDateEnd;
   }

   public String getSalaryEndDateStart()
   {
      return salaryEndDateStart;
   }

   public void setSalaryEndDateStart( String salaryEndDateStart )
   {
      this.salaryEndDateStart = salaryEndDateStart;
   }

   public String getSalaryEndDateEnd()
   {
      return salaryEndDateEnd;
   }

   public void setSalaryEndDateEnd( String salaryEndDateEnd )
   {
      this.salaryEndDateEnd = salaryEndDateEnd;
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

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getOtherLink()
   {
      if ( KANUtil.filterEmpty( super.getWorkflowId() ) != null )
      {
         otherLink = "&nbsp;&nbsp;<img src='images/magnifer.png' title='" + KANUtil.getProperty( getLocale(), "img.title.tips.view.detials" ) + "' onclick=popupWorkflow('"
               + this.getWorkflowId() + "'); style=\"cursor: pointer\" />";
      }
      return otherLink;
   }

   public void setOtherLink( String otherLink )
   {
      this.otherLink = otherLink;
   }

   public Set< String > getRemark1Set()
   {
      return remark1Set;
   }

   public void setRemark1Set( Set< String > remark1Set )
   {
      this.remark1Set = remark1Set;
   }

   public Set< String > getRemark2Set()
   {
      return remark2Set;
   }

   public void setRemark2Set( Set< String > remark2Set )
   {
      this.remark2Set = remark2Set;
   }
   
   

}
