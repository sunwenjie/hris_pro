/*
 * Created on 2012-05-06
 */
package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OptionsVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4319794840718321614L;

   /**
    * For DB
    */
   private String optionsId;

   private String language;

   private String useBrowserLanguage;

   private String dateFormat;

   private String timeFormat;

   private String pageStyle;

   private String smsGetway;

   private String logoFile;

   private String logoFileSize;

   private String mobileModuleRightIds;

   private String orderBindContract;

   private String sbGenerateCondition;

   private String cbGenerateCondition;

   private String settlementCondition;

   private String sbGenerateConditionSC;

   private String cbGenerateConditionSC;

   private String settlementConditionSC;

   private String accuracy;

   private String round;

   private String branchPrefer;

   private String isSumSubBranchHC;

   private String positionPrefer;

   private String independenceTax;

   private String monthAvg;

   private String otMinUnit;
   /**
    * For Application
    */
   @JsonIgnore
   private List< MappingVO > languages = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > dateFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > timeFormats = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > pageStyles = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > smsGetways = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > sbGenerateConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > cbGenerateConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > settlementConditions = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > sbGenerateConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > cbGenerateConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > settlementConditionSCs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > clientLogoFiles = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > clientMobileModuleRightIds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > rounds = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > branchPrefers = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > isSumSubBranchHCs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > positionPrefers = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > mobileModuleRights = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > otMinUnits = new ArrayList< MappingVO >();
   @JsonIgnore
   private String[] moduleIdArray;
   @JsonIgnore
   private String[] employeeModuleIdArray;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.languages = KANUtil.getMappings( request.getLocale(), "options.language" );
      this.dateFormats = KANUtil.getMappings( request.getLocale(), "options.dateformat" );
      this.timeFormats = KANUtil.getMappings( request.getLocale(), "options.timeformat" );
      this.pageStyles = KANUtil.getMappings( request.getLocale(), "options.pagestyle" );
      this.smsGetways = KANConstants.getSMSConfigs( request.getLocale().getLanguage() );

      if ( this.smsGetways != null )
      {
         this.smsGetways.add( 0, super.getEmptyMappingVO() );
      }

      this.sbGenerateConditions = KANUtil.getMappings( request.getLocale(), "options.sb.process.condition" );
      this.cbGenerateConditions = KANUtil.getMappings( request.getLocale(), "options.cb.process.condition" );
      this.settlementConditions = KANUtil.getMappings( request.getLocale(), "options.settlement.process.condition" );
      this.sbGenerateConditionSCs = KANUtil.getMappings( request.getLocale(), "options.sb.process.condition.service.contract" );
      this.cbGenerateConditionSCs = KANUtil.getMappings( request.getLocale(), "options.cb.process.condition.service.contract" );
      this.settlementConditionSCs = KANUtil.getMappings( request.getLocale(), "options.settlement.process.condition.service.contract" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.branchPrefers = KANUtil.getMappings( request.getLocale(), "options.branchPrefer" );
      this.isSumSubBranchHCs = KANUtil.getMappings( request.getLocale(), "options.isSumSubBranchHC" );
      this.positionPrefers = KANUtil.getMappings( request.getLocale(), "options.positionPrefer" );
      this.mobileModuleRights = KANUtil.getMappings( request.getLocale(), "options.mobile.module.right" );
      this.otMinUnits = KANUtil.getMappings( request.getLocale(), "options.otMinUnit" );
   }

   public String getDecodeLanguage()
   {
      return decodeField( language, languages );
   }

   public String getDecodeDateFormat()
   {
      return decodeField( dateFormat, dateFormats );
   }

   public String getDecodeTimeFormat()
   {
      return decodeField( timeFormat, timeFormats );
   }

   public String getDecodePageStyle()
   {
      return decodeField( pageStyle, pageStyles );
   }

   public List< MappingVO > getLanguages()
   {
      return this.languages;
   }

   @Override
   public void update( final Object object )
   {
      final OptionsVO optionsVO = ( OptionsVO ) object;
      this.language = optionsVO.getLanguage();
      this.useBrowserLanguage = optionsVO.getUseBrowserLanguage();
      this.dateFormat = optionsVO.getDateFormat();
      this.timeFormat = optionsVO.getTimeFormat();
      this.pageStyle = optionsVO.getPageStyle();
      this.smsGetway = optionsVO.getSmsGetway();
      this.logoFile = optionsVO.getLogoFile();
      this.logoFileSize = optionsVO.getLogoFileSize();
      this.orderBindContract = optionsVO.getOrderBindContract();
      this.sbGenerateCondition = optionsVO.getSbGenerateCondition();
      this.cbGenerateCondition = optionsVO.getCbGenerateCondition();
      this.settlementCondition = optionsVO.getSettlementCondition();
      this.sbGenerateConditionSC = optionsVO.getSbGenerateConditionSC();
      this.cbGenerateConditionSC = optionsVO.getCbGenerateConditionSC();
      this.settlementConditionSC = optionsVO.getSettlementConditionSC();
      this.accuracy = optionsVO.getAccuracy();
      this.round = optionsVO.getRound();
      this.branchPrefer = optionsVO.getBranchPrefer();
      this.isSumSubBranchHC = optionsVO.getIsSumSubBranchHC();
      this.positionPrefer = optionsVO.getPositionPrefer();
      this.independenceTax = optionsVO.getIndependenceTax();
      this.mobileModuleRightIds = optionsVO.getMobileModuleRightIds();
      this.monthAvg = optionsVO.getMonthAvg();
      this.otMinUnit = optionsVO.getOtMinUnit();
      super.setModifyBy( optionsVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.language = "0";
      this.useBrowserLanguage = "";
      this.dateFormat = "0";
      this.timeFormat = "0";
      this.pageStyle = "0";
      this.smsGetway = "0";
      this.logoFile = "";
      this.logoFileSize = "0";
      this.orderBindContract = "0";
      this.sbGenerateCondition = "0";
      this.cbGenerateCondition = "0";
      this.settlementCondition = "0";
      this.sbGenerateConditionSC = "0";
      this.cbGenerateConditionSC = "0";
      this.settlementConditionSC = "0";
      this.accuracy = "0";
      this.round = "0";
      this.branchPrefer = "0";
      this.positionPrefer = "0";
      this.independenceTax = "";
      this.mobileModuleRightIds = "";
      this.isSumSubBranchHC = "0";
      this.monthAvg = "21.75";
      super.setStatus( "0" );
   }

   public String getOptionsId()
   {
      return optionsId;
   }

   public void setOptionsId( String optionsId )
   {
      this.optionsId = optionsId;
   }

   public String getLanguage()
   {
      return language;
   }

   public void setLanguage( String language )
   {
      this.language = language;
   }

   public String getUseBrowserLanguage()
   {
      return useBrowserLanguage;
   }

   public void setUseBrowserLanguage( String useBrowserLanguage )
   {
      this.useBrowserLanguage = useBrowserLanguage;
   }

   public String getDateFormat()
   {
      return dateFormat;
   }

   public void setDateFormat( String dataFormat )
   {
      this.dateFormat = dataFormat;
   }

   public String getTimeFormat()
   {
      return timeFormat;
   }

   public void setTimeFormat( String timeFormat )
   {
      this.timeFormat = timeFormat;
   }

   public List< MappingVO > getDateFormats()
   {
      return dateFormats;
   }

   public void setDateFormats( List< MappingVO > dateFormats )
   {
      this.dateFormats = dateFormats;
   }

   public List< MappingVO > getTimeFormats()
   {
      return timeFormats;
   }

   public void setTimeFormats( List< MappingVO > timeFormats )
   {
      this.timeFormats = timeFormats;
   }

   public void setLanguages( List< MappingVO > languages )
   {
      this.languages = languages;
   }

   public String getPageStyle()
   {
      return pageStyle;
   }

   public void setPageStyle( String pageStyle )
   {
      this.pageStyle = pageStyle;
   }

   public List< MappingVO > getPageStyles()
   {
      return pageStyles;
   }

   public void setPageStyles( List< MappingVO > pageStyles )
   {
      this.pageStyles = pageStyles;
   }

   public String getSmsGetway()
   {
      return smsGetway;
   }

   public void setSmsGetway( String smsGetway )
   {
      this.smsGetway = smsGetway;
   }

   public List< MappingVO > getSmsGetways()
   {
      return smsGetways;
   }

   public void setSmsGetways( List< MappingVO > smsGetways )
   {
      this.smsGetways = smsGetways;
   }

   public String getOrderBindContract()
   {
      return orderBindContract;
   }

   public void setOrderBindContract( String orderBindContract )
   {
      this.orderBindContract = orderBindContract;
   }

   public String getSbGenerateCondition()
   {
      return sbGenerateCondition;
   }

   public void setSbGenerateCondition( String sbGenerateCondition )
   {
      this.sbGenerateCondition = sbGenerateCondition;
   }

   public String getCbGenerateCondition()
   {
      return cbGenerateCondition;
   }

   public void setCbGenerateCondition( String cbGenerateCondition )
   {
      this.cbGenerateCondition = cbGenerateCondition;
   }

   public String getSettlementCondition()
   {
      return settlementCondition;
   }

   public void setSettlementCondition( String settlementCondition )
   {
      this.settlementCondition = settlementCondition;
   }

   public List< MappingVO > getSbGenerateConditions()
   {
      return sbGenerateConditions;
   }

   public void setSbGenerateConditions( List< MappingVO > sbGenerateConditions )
   {
      this.sbGenerateConditions = sbGenerateConditions;
   }

   public List< MappingVO > getCbGenerateConditions()
   {
      return cbGenerateConditions;
   }

   public void setCbGenerateConditions( List< MappingVO > cbGenerateConditions )
   {
      this.cbGenerateConditions = cbGenerateConditions;
   }

   public String getSbGenerateConditionSC()
   {
      return sbGenerateConditionSC;
   }

   public void setSbGenerateConditionSC( String sbGenerateConditionSC )
   {
      this.sbGenerateConditionSC = sbGenerateConditionSC;
   }

   public String getCbGenerateConditionSC()
   {
      return cbGenerateConditionSC;
   }

   public void setCbGenerateConditionSC( String cbGenerateConditionSC )
   {
      this.cbGenerateConditionSC = cbGenerateConditionSC;
   }

   public String getSettlementConditionSC()
   {
      return settlementConditionSC;
   }

   public void setSettlementConditionSC( String settlementConditionSC )
   {
      this.settlementConditionSC = settlementConditionSC;
   }

   public List< MappingVO > getSbGenerateConditionSCs()
   {
      return sbGenerateConditionSCs;
   }

   public void setSbGenerateConditionSCs( List< MappingVO > sbGenerateConditionSCs )
   {
      this.sbGenerateConditionSCs = sbGenerateConditionSCs;
   }

   public List< MappingVO > getCbGenerateConditionSCs()
   {
      return cbGenerateConditionSCs;
   }

   public void setCbGenerateConditionSCs( List< MappingVO > cbGenerateConditionSCs )
   {
      this.cbGenerateConditionSCs = cbGenerateConditionSCs;
   }

   public List< MappingVO > getSettlementConditionSCs()
   {
      return settlementConditionSCs;
   }

   public void setSettlementConditionSCs( List< MappingVO > settlementConditionSCs )
   {
      this.settlementConditionSCs = settlementConditionSCs;
   }

   public List< MappingVO > getSettlementConditions()
   {
      return settlementConditions;
   }

   public void setSettlementConditions( List< MappingVO > settlementConditions )
   {
      this.settlementConditions = settlementConditions;
   }

   public String getAccuracy()
   {
      return accuracy;
   }

   public void setAccuracy( String accuracy )
   {
      this.accuracy = accuracy;
   }

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
   }

   public List< MappingVO > getAccuracys()
   {
      return accuracys;
   }

   public void setAccuracys( List< MappingVO > accuracys )
   {
      this.accuracys = accuracys;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( optionsId );
   }

   public String getLogoFile()
   {
      return logoFile;
   }

   public void setLogoFile( String logoFile )
   {
      this.logoFile = logoFile;
   }

   public String getLogoFileName()
   {
      if ( KANUtil.filterEmpty( this.logoFile ) != null )
      {
         int index = this.logoFile.lastIndexOf( "/" );
         if ( index >= 0 )
         {
            return this.logoFile.substring( index + 1, this.logoFile.length() );
         }
      }
      return "";
   }

   public String getLogoFileSize()
   {
      return logoFileSize;
   }

   public void setLogoFileSize( String logoFileSize )
   {
      this.logoFileSize = logoFileSize;
   }

   public String getBranchPrefer()
   {
      return branchPrefer;
   }

   public void setBranchPrefer( String branchPrefer )
   {
      this.branchPrefer = branchPrefer;
   }

   public String getPositionPrefer()
   {
      return positionPrefer;
   }

   public void setPositionPrefer( String positionPrefer )
   {
      this.positionPrefer = positionPrefer;
   }

   public List< MappingVO > getBranchPrefers()
   {
      return branchPrefers;
   }

   public void setBranchPrefers( List< MappingVO > branchPrefers )
   {
      this.branchPrefers = branchPrefers;
   }

   public List< MappingVO > getPositionPrefers()
   {
      return positionPrefers;
   }

   public void setPositionPrefers( List< MappingVO > positionPrefers )
   {
      this.positionPrefers = positionPrefers;
   }

   public List< MappingVO > getClientLogoFiles()
   {
      return clientLogoFiles;
   }

   public void setClientLogoFiles( List< MappingVO > clientLogoFiles )
   {
      this.clientLogoFiles = clientLogoFiles;
   }

   public String getIndependenceTax()
   {
      return independenceTax;
   }

   public void setIndependenceTax( String independenceTax )
   {
      this.independenceTax = independenceTax;
   }

   public String getMobileModuleRightIds()
   {
      return mobileModuleRightIds;
   }

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   public void setMobileModuleRightIds( String mobileModuleRightIds )
   {
      this.mobileModuleRightIds = mobileModuleRightIds;
   }

   public List< MappingVO > getMobileModuleRights()
   {
      return mobileModuleRights;
   }

   public void setMobileModuleRights( List< MappingVO > mobileModuleRights )
   {
      this.mobileModuleRights = mobileModuleRights;
   }

   public List< MappingVO > getClientMobileModuleRightIds()
   {
      return clientMobileModuleRightIds;
   }

   public void setClientMobileModuleRightIds( List< MappingVO > clientMobileModuleRightIds )
   {
      this.clientMobileModuleRightIds = clientMobileModuleRightIds;
   }

   public String[] getEmployeeModuleIdArray()
   {
      return employeeModuleIdArray;
   }

   public void setEmployeeModuleIdArray( String[] employeeModuleIdArray )
   {
      this.employeeModuleIdArray = employeeModuleIdArray;
   }

   public List< MappingVO > getIsSumSubBranchHCs()
   {
      return isSumSubBranchHCs;
   }

   public void setIsSumSubBranchHCs( List< MappingVO > isSumSubBranchHCs )
   {
      this.isSumSubBranchHCs = isSumSubBranchHCs;
   }

   public String getIsSumSubBranchHC()
   {
      return isSumSubBranchHC;
   }

   public void setIsSumSubBranchHC( String isSumSubBranchHC )
   {
      this.isSumSubBranchHC = isSumSubBranchHC;
   }

   public String getMonthAvg()
   {
      return monthAvg;
   }

   public void setMonthAvg( String monthAvg )
   {
      this.monthAvg = monthAvg;
   }

   public String getOtMinUnit()
   {
      return otMinUnit;
   }

   public void setOtMinUnit( String otMinUnit )
   {
      this.otMinUnit = otMinUnit;
   }

   public List< MappingVO > getOtMinUnits()
   {
      return otMinUnits;
   }

   public void setOtMinUnits( List< MappingVO > otMinUnits )
   {
      this.otMinUnits = otMinUnits;
   }

}