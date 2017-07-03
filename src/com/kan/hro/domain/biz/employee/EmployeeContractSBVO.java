package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractSBVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */
   // 雇员社保方案Id，主键
   private String employeeSBId;

   // 劳动合同Id
   private String contractId;

   // 社保方案Id（绑定设置 - 业务 - 社保方案）
   private String sbSolutionId;

   // 供应商Id
   private String vendorId;

   // 供应商服务Id
   private String vendorServiceId;

   // 公司承担个人社保
   private String personalSBBurden;

   // 加保日期
   private String startDate;

   // 退保日期
   private String endDate;

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

   // 社保实际缴纳标识
   private String flag;

   // 描述
   private String description;

   // 社保基数
   private String sbBase;

   /**
    * For Application
    */
   // 供应商中文名
   private String vendorNameZH;

   // 供应商英文名
   private String vendorNameEN;

   // 劳动合同或派送协议名称（中文）
   private String contractNameZH;

   // 劳动合同或派送协议名称（英文）
   private String contractNameEN;

   // 劳动合同或派送协议名称（开始时间）
   private String contractStartDate;

   // 劳动合同或派送协议名称（结束时间）
   private String contractEndDate;

   // 单位（hro_tempBranchIds）
   private String department;

   // 岗位（hrm_tempPositionIds）
   private String positionId;

   // 订单ID
   private String orderId;

   // 雇员ID
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 雇员证件号码
   private String certificateNumber;

   // 客服人员（即派送协议所属人）
   private String owner;

   // 客户名称(中文)
   private String clientNameZH;

   // 客户名称(英文)
   private String clientNameEN;

   // 服务协议状态
   private String contractStatus;

   // 签约主体或结算规则
   private String orderDescription;
   @JsonIgnore
   // 客户有效社保方案
   private List< MappingVO > solutions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 合同状态
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   private String solutionDetailIdArray[] = new String[] {};

   private String baseCompanyArray[] = new String[] {};

   private String basePersonalArray[] = new String[] {};

   // 是否修改商保方案数据
   private String applyToAllHeader;

   // 是否修改商保方案数据
   private String applyToAllDetail;

   // 社保状态
   private String sbType;

   // 社保状态数组形式
   private String[] statusArray = new String[] {};
   @JsonIgnore
   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职位
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 供应商服务内容
   private List< MappingVO > serviceContents = new ArrayList< MappingVO >();

   // 合同备注
   private String contractDescription;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSBId );
   }

   public String getDecodeSbSolutionId() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage() ) );

      return decodeField( this.sbSolutionId, mappingVOs );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( this.contractStatus, this.getContractStatuses() );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.sbSolutionId = "";
      this.vendorId = "0";
      this.vendorServiceId = "0";
      this.startDate = "";
      this.endDate = "";
      this.needMedicalCard = "0";
      this.needSBCard = "0";
      this.medicalNumber = "";
      this.sbNumber = "";
      this.fundNumber = "";
      this.flag = "";
      this.description = "";
      this.contractNameZH = "";
      this.contractNameEN = "";
      // Add By Jack at 2013-12-29
      this.contractId = "";
      this.orderId = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.contractStatus = "";
      this.sbBase = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.employee.contract.sb.statuses" ) );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      this.solutions = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.solutions != null )
      {
         this.solutions.add( 0, super.getEmptyMappingVO() );
      }

      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );

      // 外部职位
      if ( KANConstants.ROLE_HR_SERVICE.equals( this.getRole() ) )
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getEmployeePositions( request.getLocale().getLanguage() );
      }
      // 内部职位
      else
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      }

      this.serviceContents = KANUtil.getMappings( request.getLocale(), "business.vendor.service.item" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;
      this.contractId = employeeContractSBVO.getContractId();
      this.sbSolutionId = employeeContractSBVO.getSbSolutionId();
      this.vendorId = employeeContractSBVO.getVendorId();
      this.vendorServiceId = employeeContractSBVO.getVendorServiceId();
      this.personalSBBurden = employeeContractSBVO.getPersonalSBBurden();
      this.startDate = employeeContractSBVO.getStartDate();
      this.endDate = employeeContractSBVO.getEndDate();
      this.needMedicalCard = employeeContractSBVO.getNeedMedicalCard();
      this.needSBCard = employeeContractSBVO.getNeedSBCard();
      this.medicalNumber = employeeContractSBVO.getMedicalNumber();
      this.sbNumber = employeeContractSBVO.getSbNumber();
      this.fundNumber = employeeContractSBVO.getFundNumber();
      this.flag = employeeContractSBVO.getFlag();
      this.description = employeeContractSBVO.getDescription();
      this.solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
      this.baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
      this.basePersonalArray = employeeContractSBVO.getBasePersonalArray();
      this.sbBase = employeeContractSBVO.getSbBase();
      super.setStatus( employeeContractSBVO.getStatus() );
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
   }

   public String getStartDate()
   {
      return this.startDate = KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return this.endDate = KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String[] getSolutionDetailIdArray()
   {
      return solutionDetailIdArray;
   }

   public void setSolutionDetailIdArray( String[] solutionDetailIdArray )
   {
      this.solutionDetailIdArray = solutionDetailIdArray;
   }

   public String[] getBaseCompanyArray()
   {
      return baseCompanyArray;
   }

   public void setBaseCompanyArray( String[] baseCompanyArray )
   {
      this.baseCompanyArray = baseCompanyArray;
   }

   public String[] getBasePersonalArray()
   {
      return basePersonalArray;
   }

   public void setBasePersonalArray( String[] basePersonalArray )
   {
      this.basePersonalArray = basePersonalArray;
   }

   public String getVendorServiceId()
   {
      return vendorServiceId;
   }

   public void setVendorServiceId( String vendorServiceId )
   {
      this.vendorServiceId = vendorServiceId;
   }

   public String getNeedMedicalCard()
   {
      return KANUtil.filterEmpty( needMedicalCard );
   }

   public void setNeedMedicalCard( String needMedicalCard )
   {
      this.needMedicalCard = needMedicalCard;
   }

   public String getNeedSBCard()
   {
      return KANUtil.filterEmpty( needSBCard );
   }

   public void setNeedSBCard( String needSBCard )
   {
      this.needSBCard = needSBCard;
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

   public final String getFlag()
   {
      return flag;
   }

   public final void setFlag( String flag )
   {
      this.flag = flag;
   }

   public final String getContractNameZH()
   {
      return contractNameZH;
   }

   public final void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public final String getContractNameEN()
   {
      return contractNameEN;
   }

   public final void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
   }

   public String getContractName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage() != null && this.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            return contractNameZH;
         }
         else
         {
            return contractNameEN;
         }
      }
      else
      {
         return contractNameZH;
      }
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

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public String getEncodedCorpId() throws KANException
   {
      return encodedField( super.getCorpId() );
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
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
         return this.getClientNameEN();
      }
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getEncodedEmployeeSBId() throws KANException
   {
      return encodedField( employeeSBId );
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public String getApplyToAllHeader()
   {
      return applyToAllHeader;
   }

   public void setApplyToAllHeader( String applyToAllHeader )
   {
      this.applyToAllHeader = applyToAllHeader;
   }

   public String getApplyToAllDetail()
   {
      return applyToAllDetail;
   }

   public void setApplyToAllDetail( String applyToAllDetail )
   {
      this.applyToAllDetail = applyToAllDetail;
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

   public final String getVendorName()
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

   public List< MappingVO > getSolutions()
   {
      return solutions;
   }

   public void setSolutions( List< MappingVO > solutions )
   {
      this.solutions = solutions;
   }

   public String getSbName()
   {
      if ( solutions != null && solutions.size() > 0 )
      {
         for ( MappingVO mappingVO : solutions )
         {
            if ( mappingVO.getMappingId().equals( getSbSolutionId() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return null;
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public final String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String[] getStatusArray()
   {
      return statusArray;
   }

   public void setStatusArray( String[] statusArray )
   {
      this.statusArray = statusArray;
   }

   public String getOrderDescription()
   {
      return orderDescription;
   }

   public void setOrderDescription( String orderDescription )
   {
      this.orderDescription = orderDescription;
   }

   public String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractStartDate ) );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractEndDate ) );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public List< MappingVO > getServiceContents()
   {
      return serviceContents;
   }

   public void setServiceContents( List< MappingVO > serviceContents )
   {
      this.serviceContents = serviceContents;
   }

   // 解译公司承担个人社保
   public String getDecodePersonalSBBurden()
   {
      return decodeField( personalSBBurden, super.getFlags() );
   }

   // 解译需要办医保卡
   public String getDecodeNeedMedicalCard()
   {
      return decodeField( needMedicalCard, super.getFlags() );
   }

   // 解译公司承担个人社保
   public String getDecodeNeedSBCard()
   {
      return decodeField( needSBCard, super.getFlags() );
   }

   // 解译派送协议所属人
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // 解译岗位（hrm_tempPositionIds）
   public String getDecodePositionId()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final String[] positionArray = positionId.split( "," );
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

   // 解译单位（hrm_tempBranchIds）
   public String getDecodeDepartment() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getCorpId() ) == null )
      {
         return department;
      }
      else
      {
         String returnStr = "";
         if ( KANUtil.filterEmpty( department ) != null && branchs != null && branchs.size() > 0 )
         {
            for ( String branchId : department.split( "," ) )
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
   }

   // 解译供应商服务类容
   public String getDecodeVendorServiceIds()
   {
      String returnString = "";
      if ( this.serviceContents != null && vendorServiceId != null )
      {
         final String serviceArray[] = KANUtil.jasonArrayToString( vendorServiceId ).split( "," );

         for ( String arrayItem : serviceArray )
         {
            for ( MappingVO mappingVO : this.serviceContents )
            {
               if ( mappingVO.getMappingId().equals( arrayItem ) )
               {
                  if ( returnString.equals( "" ) )
                  {
                     returnString = mappingVO.getMappingValue();
                  }
                  else
                  {
                     returnString = returnString + " + " + mappingVO.getMappingValue();
                  }
                  break;
               }
            }
         }
      }

      return returnString;
   }

   public String getSbBase()
   {
      return KANUtil.filterEmpty( sbBase ) == null ? "0" : sbBase;
   }

   public void setSbBase( String sbBase )
   {
      this.sbBase = sbBase;
   }

   public String getContractDescription()
   {
      return contractDescription;
   }

   public void setContractDescription( String contractDescription )
   {
      this.contractDescription = contractDescription;
   }

   public String getSubStrContractDescription()
   {
      String result = "";
      if ( KANUtil.filterEmpty( contractDescription ) != null )
      {
         if ( contractDescription.length() > 20 )
         {
            result = contractDescription.substring( 0, 20 ) + "...";

         }
         else
         {
            result = contractDescription;
         }
      }
      return result;
   }
}
