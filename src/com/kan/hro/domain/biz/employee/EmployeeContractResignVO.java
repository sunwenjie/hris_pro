package com.kan.hro.domain.biz.employee;

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

public class EmployeeContractResignVO extends BaseVO
{
   // serialVersionUID  
   private static final long serialVersionUID = -1169410995775207114L;

   /**
    * For DB
    */
   // 离职序号Id，主键
   private String employeeContractResignId;

   // 批次Id
   private String batchId;

   // 劳动合同Id
   private String contractId;

   // 雇员Id
   private String employeeId;

   // 合同开始时间
   private String startDate;

   // 离职日期
   private String resignDate;
   
   // 离职原因
   private String leaveReasons;
   
   // 最后工作日期
   private String lastWorkDate;

   // 标明是劳动合同还是派送协议
   private String flag;

   // 雇员姓名中文
   private String employeeNameZH;

   // 雇员姓名英文
   private String employeeNameEN;

   // 证件号码
   private String certificateNumber;

   // 雇佣状态
   private String employStatus;

   // 社保方案1Id
   private String sb1Id;

   // 社保方案1名称
   private String sb1Name;

   // 社保方案1开始日期
   private String sb1StartDate;

   // 社保方案1结束日期
   private String sb1EndDate;

   // 社保方案2Id
   private String sb2Id;

   // 社保方案2名称
   private String sb2Name;

   // 社保方案2开始日期
   private String sb2StartDate;

   // 社保方案2结束日期
   private String sb2EndDate;

   // 社保方案3Id
   private String sb3Id;

   // 社保方案3名称
   private String sb3Name;

   // 社保方案3开始日期
   private String sb3StartDate;

   // 社保方案3结束日期
   private String sb3EndDate;

   // 商保方案1Id
   private String cb1Id;

   // 商保方案1名称
   private String cb1Name;

   // 商保方案1开始日期
   private String cb1StartDate;

   // 商保方案1结束日期
   private String cb1EndDate;

   // 商保方案2Id
   private String cb2Id;

   // 商保方案2名称
   private String cb2Name;

   // 商保方案2开始日期
   private String cb2StartDate;

   // 商保方案2结束日期
   private String cb2EndDate;

   /**
    * For Application
    */
   // 雇员离职状态
   private List< MappingVO > employStatuses = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "def.common.batch.status" ) );
      // business.employee.contract.employStatuses
      this.employStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.work.statuses" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeContractResignId );
   }

   public String getDecodeEmployStatus() throws KANException
   {
      return decodeField( this.employStatus, this.getEmployStatuses() );
   }

   public String getDecodeFlag() throws KANException
   {
      if ( this.flag != null )
      {
         if ( this.flag.trim().equals( "1" ) )
         {
            return "劳动合同";
         }
         else if ( this.flag.trim().equals( "2" ) )
         {
            return "派送协议";
         }
      }

      return "";
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.employeeId = "";
      this.startDate = "";
      this.resignDate = "";
      this.leaveReasons = "";
      this.lastWorkDate = "";
      this.flag = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.certificateNumber = "";
      this.employStatus = "";
      this.sb1Id = "";
      this.sb1Name = "";
      this.sb1StartDate = "";
      this.sb1EndDate = "";
      this.sb2Id = "";
      this.sb2Name = "";
      this.sb2StartDate = "";
      this.sb2EndDate = "";
      this.sb3Id = "";
      this.sb3Name = "";
      this.sb3StartDate = "";
      this.sb3EndDate = "";
      this.cb1Id = "";
      this.cb1Name = "";
      this.cb1StartDate = "";
      this.cb1EndDate = "";
      this.cb2Id = "";
      this.cb2Name = "";
      this.cb2StartDate = "";
      this.cb2EndDate = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) object;
      this.contractId = employeeContractResignVO.getContractId();
      this.employeeId = employeeContractResignVO.getEmployeeId();
      this.startDate = employeeContractResignVO.getStartDate();
      this.resignDate = employeeContractResignVO.getResignDate();
      this.leaveReasons = employeeContractResignVO.getLeaveReasons();
      this.lastWorkDate = employeeContractResignVO.getLastWorkDate();
      this.flag = employeeContractResignVO.getFlag();
      this.employeeNameZH = employeeContractResignVO.getEmployeeNameZH();
      this.employeeNameEN = employeeContractResignVO.getEmployeeNameEN();
      this.certificateNumber = employeeContractResignVO.getCertificateNumber();
      this.employStatus = employeeContractResignVO.getEmployStatus();
      this.sb1Id = employeeContractResignVO.getSb1Id();
      this.sb1Name = employeeContractResignVO.getSb1Name();
      this.sb1StartDate = employeeContractResignVO.getSb1StartDate();
      this.sb1EndDate = employeeContractResignVO.getSb1EndDate();
      this.sb2Id = employeeContractResignVO.getSb2Id();
      this.sb2Name = employeeContractResignVO.getSb2Name();
      this.sb2StartDate = employeeContractResignVO.getSb2StartDate();
      this.sb2EndDate = employeeContractResignVO.getSb2EndDate();
      this.sb3Id = employeeContractResignVO.getSb3Id();
      this.sb3Name = employeeContractResignVO.getSb3Name();
      this.sb3StartDate = employeeContractResignVO.getSb3StartDate();
      this.sb3EndDate = employeeContractResignVO.getSb3EndDate();
      this.cb1Id = employeeContractResignVO.getCb1Id();
      this.cb1Name = employeeContractResignVO.getCb1Name();
      this.cb1StartDate = employeeContractResignVO.getCb1StartDate();
      this.cb1EndDate = employeeContractResignVO.getCb1EndDate();
      this.cb2Id = employeeContractResignVO.getCb2Id();
      this.cb2Name = employeeContractResignVO.getCb2Name();
      this.cb2StartDate = employeeContractResignVO.getCb2StartDate();
      this.cb2EndDate = employeeContractResignVO.getCb2EndDate();
      super.setStatus( employeeContractResignVO.getStatus() );
      super.setModifyBy( employeeContractResignVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getResignDate()
   {
      return KANUtil.filterEmpty( decodeDate( resignDate ) );
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getEmployeeContractResignId()
   {
      return employeeContractResignId;
   }

   public void setEmployeeContractResignId( String employeeContractResignId )
   {
      this.employeeContractResignId = employeeContractResignId;
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

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEmployStatus()
   {
      return employStatus;
   }

   public void setEmployStatus( String employStatus )
   {
      this.employStatus = employStatus;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
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
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return employeeNameZH;
         }
         else
         {
            return employeeNameEN;
         }
      }
      return employeeNameZH;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public List< MappingVO > getEmployStatuses()
   {
      return employStatuses;
   }

   public void setEmployStatuses( List< MappingVO > employStatuses )
   {
      this.employStatuses = employStatuses;
   }

   public String getSb1Id()
   {
      return sb1Id;
   }

   public void setSb1Id( String sb1Id )
   {
      this.sb1Id = sb1Id;
   }

   public String getSb1Name()
   {
      return sb1Name;
   }

   public void setSb1Name( String sb1Name )
   {
      this.sb1Name = sb1Name;
   }

   public String getSb1StartDate()
   {
      return KANUtil.filterEmpty( decodeDate( sb1StartDate ) );
   }

   public void setSb1StartDate( String sb1StartDate )
   {
      this.sb1StartDate = sb1StartDate;
   }

   public String getSb1EndDate()
   {
      return KANUtil.filterEmpty( decodeDate( sb1EndDate ) );
   }

   public void setSb1EndDate( String sb1EndDate )
   {
      this.sb1EndDate = sb1EndDate;
   }

   public String getSb2Id()
   {
      return sb2Id;
   }

   public void setSb2Id( String sb2Id )
   {
      this.sb2Id = sb2Id;
   }

   public String getSb2Name()
   {
      return sb2Name;
   }

   public void setSb2Name( String sb2Name )
   {
      this.sb2Name = sb2Name;
   }

   public String getSb2StartDate()
   {

      return KANUtil.filterEmpty( decodeDate( sb2StartDate ) );
   }

   public void setSb2StartDate( String sb2StartDate )
   {
      this.sb2StartDate = sb2StartDate;
   }

   public String getSb2EndDate()
   {
      return KANUtil.filterEmpty( decodeDate( sb2EndDate ) );
   }

   public void setSb2EndDate( String sb2EndDate )
   {
      this.sb2EndDate = sb2EndDate;
   }

   public String getSb3Id()
   {
      return sb3Id;
   }

   public void setSb3Id( String sb3Id )
   {
      this.sb3Id = sb3Id;
   }

   public String getSb3Name()
   {
      return sb3Name;
   }

   public void setSb3Name( String sb3Name )
   {
      this.sb3Name = sb3Name;
   }

   public String getSb3StartDate()
   {
      return KANUtil.filterEmpty( decodeDate( sb3StartDate ) );
   }

   public void setSb3StartDate( String sb3StartDate )
   {
      this.sb3StartDate = sb3StartDate;
   }

   public String getSb3EndDate()
   {
      return KANUtil.filterEmpty( decodeDate( sb3EndDate ) );
   }

   public void setSb3EndDate( String sb3EndDate )
   {
      this.sb3EndDate = sb3EndDate;
   }

   public String getCb1Id()
   {
      return cb1Id;
   }

   public void setCb1Id( String cb1Id )
   {
      this.cb1Id = cb1Id;
   }

   public String getCb1Name()
   {
      return cb1Name;
   }

   public void setCb1Name( String cb1Name )
   {
      this.cb1Name = cb1Name;
   }

   public String getCb1StartDate()
   {
      return KANUtil.filterEmpty( decodeDate( cb1StartDate ) );
   }

   public void setCb1StartDate( String cb1StartDate )
   {
      this.cb1StartDate = cb1StartDate;
   }

   public String getCb1EndDate()
   {
      return KANUtil.filterEmpty( decodeDate( cb1EndDate ) );
   }

   public void setCb1EndDate( String cb1EndDate )
   {
      this.cb1EndDate = cb1EndDate;
   }

   public String getCb2Id()
   {
      return cb2Id;
   }

   public void setCb2Id( String cb2Id )
   {
      this.cb2Id = cb2Id;
   }

   public String getCb2Name()
   {
      return cb2Name;
   }

   public void setCb2Name( String cb2Name )
   {
      this.cb2Name = cb2Name;
   }

   public String getCb2StartDate()
   {
      return KANUtil.filterEmpty( decodeDate( cb2StartDate ) );
   }

   public void setCb2StartDate( String cb2StartDate )
   {
      this.cb2StartDate = cb2StartDate;
   }

   public String getCb2EndDate()
   {
      return KANUtil.filterEmpty( decodeDate( cb2EndDate ) );
   }

   public void setCb2EndDate( String cb2EndDate )
   {
      this.cb2EndDate = cb2EndDate;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getDecodeSb1Id() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.sb1Id, mappingVOs );
   }

   public String getDecodeSb2Id() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.sb2Id, mappingVOs );
   }

   public String getDecodeSb3Id() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.sb3Id, mappingVOs );
   }

   public String getDecodeCb1Id() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.cb1Id, mappingVOs );
   }

   public String getDecodeCb2Id() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.cb2Id, mappingVOs );
   }

   public String getLeaveReasons()
   {
      return leaveReasons;
   }

   public void setLeaveReasons( String leaveReasons )
   {
      this.leaveReasons = leaveReasons;
   }

   public String getLastWorkDate()
   {
      return lastWorkDate;
   }

   public void setLastWorkDate( String lastWorkDate )
   {
      this.lastWorkDate = lastWorkDate;
   }
}
