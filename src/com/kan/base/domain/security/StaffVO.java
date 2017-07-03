package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class StaffVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7052837621610457799L;

   private String staffId;

   private String staffNo;

   private String employeeId;

   private String salutation;

   private String nameZH;

   private String nameEN;

   private String birthday;

   private String bizPhone;

   private String bizExt;

   private String personalPhone;

   private String personalMobile;

   private String bizMobile;

   private String otherPhone;

   private String fax;

   private String bizEmail;

   private String personalEmail;

   private String certificateType;

   private String certificateNumber;

   private String maritalStatus;

   private String registrationHometown;

   private String registrationAddress;

   private String personalAddress;

   private String personalPostcode;

   private String description;

   /**
    * For Application
    */
  
   private String username;

   private String accountName;
   @JsonIgnore
   private String[] positionIdArray = new String[] {};
   @JsonIgnore
   private List< MappingVO > salutations = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > staffStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > certificateTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > maritalStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // 银行
   private List< MappingVO > banks = new ArrayList< MappingVO >();
   @JsonIgnore
   // 当前Staff属于哪些Position
   private List< PositionStaffRelationVO > positionStaffRelationVOs = new ArrayList< PositionStaffRelationVO >();
   @JsonIgnore
   private UserVO userVO;
   @JsonIgnore
   private String userId;
   @JsonIgnore
   private String pTitle;
   @JsonIgnore
   // 职级
   private String positionGrade;
   @JsonIgnore
   private EmployeeVO employeeVO;
   @JsonIgnore
   //上级职位
   private PositionVO positionVO;
   @JsonIgnore
   private String bankId;
   @JsonIgnore
   private String bankAccount;
   @JsonIgnore
   // for branch list staff only
   private List< BranchVO > parentBranchVOs;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.staffStatuses = KANUtil.getMappings( this.getLocale(), "security.staff.statuses" );
      this.certificateTypes = KANUtil.getMappings( this.getLocale(), "security.staff.certificate.types" );
      this.maritalStatuses = KANUtil.getMappings( this.getLocale(), "security.staff.marital.statuses" );
      this.banks = KANConstants.getKANAccountConstants( super.getAccountId() ).getBanks( request.getLocale().getLanguage(), super.getCorpId() );
      this.banks.add( 0, this.getEmptyMappingVO() );
   }

   // 重载父类方法
   public String getDecodeStatus()
   {
      if ( this.staffStatuses != null )
      {
         for ( MappingVO mappingVO : this.staffStatuses )
         {
            if ( mappingVO.getMappingId().equals( super.getStatus() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public String getDecodeSalutation()
   {
      return decodeField( salutation, salutations );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( staffId == null || staffId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( staffId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getDecodeBirthday()
   {
      if ( this.birthday != null )
      {
         return new SimpleDateFormat( KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT ).format( KANUtil.createDate( this.birthday ) );
      }
      else
      {
         return "";
      }
   }

   public void update( Object object )
   {
      final StaffVO staffVO = ( StaffVO ) object;
      this.employeeId = staffVO.getEmployeeId();
      this.birthday = staffVO.birthday;
      this.bizEmail = staffVO.bizEmail;
      this.bizExt = staffVO.bizExt;
      this.bizMobile = staffVO.bizMobile;
      this.bizPhone = staffVO.bizPhone;
      this.certificateNumber = staffVO.certificateNumber;
      this.certificateType = staffVO.certificateType;
      this.description = staffVO.description;
      this.fax = staffVO.fax;
      this.staffNo = staffVO.staffNo;
      this.salutation = staffVO.salutation;
      this.nameEN = staffVO.nameEN;
      this.nameZH = staffVO.nameZH;
      this.personalPhone = staffVO.personalPhone;
      this.personalMobile = staffVO.personalMobile;
      this.otherPhone = staffVO.otherPhone;
      this.personalEmail = staffVO.personalEmail;
      this.maritalStatus = staffVO.maritalStatus;
      this.registrationHometown = staffVO.registrationHometown;
      this.registrationAddress = staffVO.registrationAddress;
      this.personalAddress = staffVO.personalAddress;
      this.personalPostcode = staffVO.personalPostcode;
      this.username = staffVO.username;
      this.positionIdArray = staffVO.positionIdArray;
      super.setStatus( staffVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public void update_mobile( Object object )
   {
      final StaffVO staffVO = ( StaffVO ) object;
      this.setNameZH( staffVO.getNameZH() );
      this.setNameEN( staffVO.getNameEN() );
      this.setSalutation( staffVO.getSalutation() );
      this.setCertificateNumber( staffVO.getCertificateNumber() );
      this.setPersonalMobile( staffVO.getPersonalMobile() );
      this.setPersonalPhone( staffVO.getPersonalPhone() );
      this.setPersonalEmail( staffVO.getPersonalEmail() );
      this.setPersonalAddress( staffVO.getPersonalAddress() );
      this.setBankId( staffVO.getBankId() );
      this.setBankAccount( staffVO.getBankAccount() );
      super.setModifyDate( new Date() );
   }
   
   public void update_mobile4iclick( Object object )
   {
      final StaffVO staffVO = ( StaffVO ) object;
      this.setMaritalStatus( staffVO.getMaritalStatus() );
      this.setRegistrationAddress( staffVO.getRegistrationAddress() );
      this.setPersonalAddress( staffVO.getPersonalAddress() );
      this.setBizEmail( staffVO.getBizEmail() );
      this.setPersonalEmail( staffVO.getPersonalEmail() );
      this.setBankId( staffVO.getBankId() );
      this.setBankAccount( staffVO.getBankAccount() );
      super.setModifyDate( new Date() );
   }

   public void reset()
   {
      this.employeeId = "";
      this.birthday = "";
      this.bizEmail = "";
      this.bizExt = "";
      this.bizMobile = "";
      this.bizPhone = "";
      this.certificateNumber = "";
      this.certificateType = "";
      this.description = "";
      this.fax = "";
      this.staffNo = "";
      this.salutation = "";
      this.nameEN = "";
      this.nameZH = "";
      this.personalPhone = "";
      this.personalMobile = "";
      this.otherPhone = "";
      this.personalEmail = "";
      this.maritalStatus = "0";
      this.registrationHometown = "";
      this.registrationAddress = "";
      this.personalAddress = "";
      this.personalPostcode = "";
      this.username = "";
      this.positionIdArray = new String[] {};
      super.setStatus( "0" );
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public String getStaffNo()
   {
      return staffNo;
   }

   public void setStaffNo( String staffNo )
   {
      this.staffNo = staffNo;
   }

   public String getSalutation()
   {
      return ( salutation == null || "".equals( salutation ) ) ? null : salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
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

   public String getBirthday()
   {
      return KANUtil.filterEmpty( KANUtil.formatDate( birthday, super.getAccountId() != null ? KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT
            : null ) );
   }

   public void setBirthday( String birthday )
   {
      this.birthday = birthday;
   }

   public String getBizPhone()
   {
      return bizPhone;
   }

   public void setBizPhone( String bizPhone )
   {
      this.bizPhone = bizPhone;
   }

   public String getBizExt()
   {
      return bizExt;
   }

   public void setBizExt( String bizExt )
   {
      this.bizExt = bizExt;
   }

   public String getPersonalPhone()
   {
      return personalPhone;
   }

   public void setPersonalPhone( String personalPhone )
   {
      this.personalPhone = personalPhone;
   }

   public String getBizMobile()
   {
      return bizMobile;
   }

   public void setBizMobile( String bizMobile )
   {
      this.bizMobile = bizMobile;
   }

   public String getOtherPhone()
   {
      return otherPhone;
   }

   public void setOtherPhone( String otherPhone )
   {
      this.otherPhone = otherPhone;
   }

   public String getFax()
   {
      return fax;
   }

   public void setFax( String fax )
   {
      this.fax = fax;
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

   public String getCertificateType()
   {
      return ( certificateType == null || "".equals( certificateType ) ) ? null : certificateType;
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

   public String getMaritalStatus()
   {
      return ( maritalStatus == null || "".equals( maritalStatus ) ) ? null : maritalStatus;
   }

   public void setMaritalStatus( String maritalStatus )
   {
      this.maritalStatus = maritalStatus;
   }

   public String getRegistrationHometown()
   {
      return registrationHometown;
   }

   public void setRegistrationHometown( String registrationHometown )
   {
      this.registrationHometown = registrationHometown;
   }

   public String getRegistrationAddress()
   {
      return registrationAddress;
   }

   public void setRegistrationAddress( String registrationAddress )
   {
      this.registrationAddress = registrationAddress;
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getSalutations()
   {
      return salutations;
   }

   public void setSalutations( List< MappingVO > salutations )
   {
      this.salutations = salutations;
   }

   public String getPersonalMobile()
   {
      return personalMobile;
   }

   public void setPersonalMobile( String personalMobile )
   {
      this.personalMobile = personalMobile;
   }

   public List< MappingVO > getStaffStatuses()
   {
      return staffStatuses;
   }

   public void setStaffStatuses( List< MappingVO > staffStatuses )
   {
      this.staffStatuses = staffStatuses;
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

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getUserId()
   {
      return userId;
   }

   public void setUserId( String userId )
   {
      this.userId = userId;
   }

   public String getpTitle()
   {
      return pTitle;
   }

   public void setpTitle( String pTitle )
   {
      this.pTitle = pTitle;
   }

   public List< PositionStaffRelationVO > getPositionStaffRelationVOs()
   {
      return positionStaffRelationVOs;
   }

   public void setPositionStaffRelationVOs( List< PositionStaffRelationVO > positionStaffRelationVOs )
   {
      this.positionStaffRelationVOs = positionStaffRelationVOs;
   }

   public UserVO getUserVO()
   {
      return userVO;
   }

   public void setUserVO( UserVO userVO )
   {
      this.userVO = userVO;
   }

   public EmployeeVO getEmployeeVO()
   {
      return employeeVO;
   }

   public void setEmployeeVO( EmployeeVO employeeVO )
   {
      this.employeeVO = employeeVO;
   }

   public String getPositionGrade()
   {
      return positionGrade;
   }

   public void setPositionGrade( String positionGrade )
   {
      this.positionGrade = positionGrade;
   }

   public PositionVO getPositionVO()
   {
      return positionVO;
   }

   public void setPositionVO( PositionVO positionVO )
   {
      this.positionVO = positionVO;
   }

   public List< BranchVO > getParentBranchVOs()
   {
      return parentBranchVOs;
   }

   public void setParentBranchVOs( List< BranchVO > parentBranchVOs )
   {
      this.parentBranchVOs = parentBranchVOs;
   }

   public final String getAccountName()
   {
      return accountName;
   }

   public final void setAccountName( String accountName )
   {
      this.accountName = accountName;
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

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

}
