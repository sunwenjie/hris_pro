package com.kan.base.domain.system;

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

public class SocialBenefitDetailVO extends BaseVO
{

   /**  
    *  Serial Version UID
    */
   private static final long serialVersionUID = -5909953666568502557L;

   /**
    * For DB
    */
   // �籣�ӱ�ID
   private String detailId;

   // �籣����ID
   private String headerId;

   // ��ĿID
   private String itemId;

   // ��˾�������ͣ�
   private String companyPercentLow;

   // ��˾�������ߣ�
   private String companyPercentHight;

   // ���˱������ͣ�
   private String personalPercentLow;

   // ���˱������ߣ�
   private String personalPercentHight;

   // ��˾�������ͣ�
   private String companyFloor;

   // ��˾�������ߣ�
   private String companyCap;

   // ���˻������ͣ�
   private String personalFloor;

   // ���˻������ߣ�
   private String personalCap;

   // ��˾�̶���
   private String companyFixAmount;

   // ���˹̶���
   private String personalFixAmount;

   // ��������
   private String termMonth;

   // ����
   private String residency;

   // ÿ������·�
   private String adjustMonth;

   // �籣������
   private String attribute;

   // �籣������
   private String effective;

   // �걨��ʼʱ��
   private String startDateLimit;

   // �걨��ֹʱ��
   private String endDateLimit;

   // ��ʼ���籣���ɹ���
   private String startRule;

   // ��ʼ���籣���ɹ���ע
   private String startRuleRemark;

   // �������籣���ɹ���
   private String endRule;

   // �������籣���ɹ���ע
   private String endRuleRemark;

   // �Ƿ���Բ���
   private String makeup;

   // �ɲ�������
   private String makeupMonth;

   // �ܷ���겹��
   private String makeupCrossYear;

   // �籣����
   private String description;

   /**
    * @author Siuvan @DATE:2013-8-14 18:12
    */

   // Add Column ����С��λ
   private String companyAccuracy;

   // Add Column ����С��λ
   private String personalAccuracy;

   // Add Column С��λ������ʽ
   private String round;

   /**
    * For Application
    */

   // ����
   private List< MappingVO > residencys;

   // ÿ������·�
   private List< MappingVO > adjustMonths;

   // ��������
   private String[] termMonthArray;

   // �籣������
   private List< MappingVO > attributes;

   // �籣������
   private List< MappingVO > effectives;

   // �籣���ɹ���
   private List< MappingVO > rules;

   // ʡID
   private String provinceId;

   private String cityIdTemp;

   private List< MappingVO > provinces;
   // ��Ŀ
   private List< MappingVO > items;

   // ��Ŀ���
   private String itemNo;

   // ����
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // ȡֵ��Χ
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   // ���ڣ�1~31�ţ�
   private List< MappingVO > dates = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.residencys = KANUtil.getMappings( request.getLocale(), "sys.sb.residency" );
      this.adjustMonths = KANUtil.getMappings( request.getLocale(), "sys.sb.adjustment.months" );
      this.attributes = KANUtil.getMappings( request.getLocale(), "sys.sb.attribute.months" );
      this.effectives = KANUtil.getMappings( request.getLocale(), "sys.sb.effctive.months" );
      this.rules = KANUtil.getMappings( request.getLocale(), "sys.sb.pay.rule" );
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getSbItems( request.getLocale().getLanguage() );
      if ( this.items != null )
      {
         this.items.add( 0, super.getEmptyMappingVO() );
      }
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.dates = KANUtil.getMappings( request.getLocale(), "business.client.dates" );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.companyPercentLow = "";
      this.companyPercentHight = "";
      this.personalPercentLow = "";
      this.personalPercentHight = "";
      this.companyFloor = "";
      this.companyCap = "";
      this.personalFloor = "";
      this.personalCap = "";
      this.companyFixAmount = "";
      this.personalFixAmount = "";
      this.termMonth = "";
      this.residency = "";
      this.adjustMonth = "";
      this.attribute = "";
      this.effective = "";
      this.startDateLimit = "";
      this.endDateLimit = "";
      this.startRule = "";
      this.startRuleRemark = "";
      this.endRule = "";
      this.endRuleRemark = "";
      this.makeup = "";
      this.makeupMonth = "";
      this.makeupCrossYear = "";
      this.description = "";
      this.personalAccuracy = "";
      this.companyAccuracy = "";
      this.round = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) object;
      this.itemId = socialBenefitDetailVO.getItemId();
      this.companyPercentLow = socialBenefitDetailVO.getCompanyPercentLow();
      this.companyPercentHight = socialBenefitDetailVO.getCompanyPercentHight();
      this.personalPercentLow = socialBenefitDetailVO.getPersonalPercentLow();
      this.personalPercentHight = socialBenefitDetailVO.getPersonalPercentHight();
      this.companyFloor = socialBenefitDetailVO.getCompanyFloor();
      this.companyCap = socialBenefitDetailVO.getCompanyCap();
      this.personalFloor = socialBenefitDetailVO.getPersonalFloor();
      this.personalCap = socialBenefitDetailVO.getPersonalCap();
      this.companyFixAmount = socialBenefitDetailVO.getCompanyFixAmount();
      this.personalFixAmount = socialBenefitDetailVO.getPersonalFixAmount();
      this.termMonth = socialBenefitDetailVO.getTermMonth();
      this.residency = socialBenefitDetailVO.getResidency();
      this.adjustMonth = socialBenefitDetailVO.getAdjustMonth();
      this.attribute = socialBenefitDetailVO.getAttribute();
      this.effective = socialBenefitDetailVO.getEffective();
      this.startDateLimit = socialBenefitDetailVO.getStartDateLimit();
      this.endDateLimit = socialBenefitDetailVO.getEndDateLimit();
      this.startRule = socialBenefitDetailVO.getStartRule();
      this.startRuleRemark = socialBenefitDetailVO.getStartRuleRemark();
      this.endRule = socialBenefitDetailVO.getEndRule();
      this.endRuleRemark = socialBenefitDetailVO.getEndRuleRemark();
      this.makeup = socialBenefitDetailVO.getMakeup();
      this.makeupMonth = socialBenefitDetailVO.getMakeupMonth();
      this.makeupCrossYear = socialBenefitDetailVO.getMakeupCrossYear();
      this.description = socialBenefitDetailVO.getDescription();
      this.personalAccuracy = socialBenefitDetailVO.getPersonalAccuracy();
      this.companyAccuracy = socialBenefitDetailVO.getCompanyAccuracy();
      this.round = socialBenefitDetailVO.getRound();
      super.setStatus( socialBenefitDetailVO.getStatus() );
      super.setModifyDate( new Date() );
      this.termMonthArray = socialBenefitDetailVO.getTermMonthArray();
   }

   public String getDecodeItem()
   {
      return decodeField( itemId, items );
   }

   public String getDecodePersonalAccuracy()
   {
      return decodeField( personalAccuracy, accuracys );
   }

   public String getDecodeCompanyAccuracy()
   {
      return decodeField( companyAccuracy, accuracys );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getCompanyPercentLow()
   {
      return KANUtil.filterEmpty( companyPercentLow == null ? "0" : companyPercentLow );
   }

   public void setCompanyPercentLow( String companyPercentLow )
   {
      this.companyPercentLow = companyPercentLow;
   }

   public String getCompanyPercentHight()
   {
      return KANUtil.filterEmpty( formatNumber( companyPercentHight == null ? "0" : companyPercentHight ) );
   }

   public void setCompanyPercentHight( String companyPercentHight )
   {
      this.companyPercentHight = companyPercentHight;
   }

   public String getPersonalPercentLow()
   {
      return KANUtil.filterEmpty( personalPercentLow == null ? "0" : personalPercentLow );
   }

   public void setPersonalPercentLow( String personalPercentLow )
   {
      this.personalPercentLow = personalPercentLow;
   }

   public String getPersonalPercentHight()
   {
      return KANUtil.filterEmpty( formatNumber( personalPercentHight == null ? "0" : personalPercentHight ) );
   }

   public void setPersonalPercentHight( String personalPercentHight )
   {
      this.personalPercentHight = personalPercentHight;
   }

   public String getCompanyFloor()
   {
      return KANUtil.filterEmpty( formatNumber( companyFloor ) );
   }

   public void setCompanyFloor( String companyFloor )
   {
      this.companyFloor = companyFloor;
   }

   public String getCompanyCap()
   {
      return KANUtil.filterEmpty( formatNumber( companyCap ) );
   }

   public void setCompanyCap( String companyCap )
   {
      this.companyCap = companyCap;
   }

   public String getPersonalFloor()
   {
      return KANUtil.filterEmpty( formatNumber( personalFloor ) );
   }

   public void setPersonalFloor( String personalFloor )
   {
      this.personalFloor = personalFloor;
   }

   public String getPersonalCap()
   {
      return KANUtil.filterEmpty( formatNumber( personalCap ) );
   }

   public void setPersonalCap( String personalCap )
   {
      this.personalCap = personalCap;
   }

   public String getCompanyFixAmount()
   {
      return KANUtil.filterEmpty( formatNumber( companyFixAmount ) );
   }

   public void setCompanyFixAmount( String companyFixAmount )
   {
      this.companyFixAmount = companyFixAmount;
   }

   public String getPersonalFixAmount()
   {
      return KANUtil.filterEmpty( formatNumber( personalFixAmount ) );
   }

   public void setPersonalFixAmount( String personalFixAmount )
   {
      this.personalFixAmount = personalFixAmount;
   }

   public String getTermMonth()
   {
      return termMonth;
   }

   public void setTermMonth( String termMonth )
   {
      this.termMonth = termMonth;
   }

   public String getResidency()
   {
      return residency;
   }

   public void setResidency( String residency )
   {
      this.residency = residency;
   }

   public String getAdjustMonth()
   {
      return adjustMonth;
   }

   public void setAdjustMonth( String adjustMonth )
   {
      this.adjustMonth = adjustMonth;
   }

   public String getAttribute()
   {
      return attribute;
   }

   public void setAttribute( String attribute )
   {
      this.attribute = attribute;
   }

   public String getEffective()
   {
      return effective;
   }

   public void setEffective( String effective )
   {
      this.effective = effective;
   }

   public String getStartDateLimit()
   {
      return startDateLimit;
   }

   public void setStartDateLimit( String startDateLimit )
   {
      this.startDateLimit = startDateLimit;
   }

   public String getEndDateLimit()
   {
      return endDateLimit;
   }

   public void setEndDateLimit( String endDateLimit )
   {
      this.endDateLimit = endDateLimit;
   }

   public String getStartRule()
   {
      return startRule;
   }

   public void setStartRule( String startRule )
   {
      this.startRule = startRule;
   }

   public String getStartRuleRemark()
   {
      return startRuleRemark;
   }

   public void setStartRuleRemark( String startRuleRemark )
   {
      this.startRuleRemark = startRuleRemark;
   }

   public String getEndRule()
   {
      return endRule;
   }

   public void setEndRule( String endRule )
   {
      this.endRule = endRule;
   }

   public String getEndRuleRemark()
   {
      return endRuleRemark;
   }

   public void setEndRuleRemark( String endRuleRemark )
   {
      this.endRuleRemark = endRuleRemark;
   }

   public String getMakeup()
   {
      return makeup;
   }

   public void setMakeup( String makeup )
   {
      this.makeup = makeup;
   }

   public String getMakeupMonth()
   {
      return makeupMonth;
   }

   public void setMakeupMonth( String makeupMonth )
   {
      this.makeupMonth = makeupMonth;
   }

   public String getMakeupCrossYear()
   {
      return makeupCrossYear;
   }

   public void setMakeupCrossYear( String makeupCrossYear )
   {
      this.makeupCrossYear = makeupCrossYear;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getResidencys()
   {
      return residencys;
   }

   public void setResidencys( List< MappingVO > residencys )
   {
      this.residencys = residencys;
   }

   public List< MappingVO > getAdjustMonths()
   {
      return adjustMonths;
   }

   public void setAdjustMonths( List< MappingVO > adjustMonths )
   {
      this.adjustMonths = adjustMonths;
   }

   public List< MappingVO > getAttributes()
   {
      return attributes;
   }

   public void setAttributes( List< MappingVO > attributes )
   {
      this.attributes = attributes;
   }

   public List< MappingVO > getEffectives()
   {
      return effectives;
   }

   public void setEffectives( List< MappingVO > effectives )
   {
      this.effectives = effectives;
   }

   public List< MappingVO > getRules()
   {
      return rules;
   }

   public void setRules( List< MappingVO > rules )
   {
      this.rules = rules;
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

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   public String getCompanyAccuracy()
   {
      return companyAccuracy;
   }

   public void setCompanyAccuracy( String companyAccuracy )
   {
      this.companyAccuracy = companyAccuracy;
   }

   public String getPersonalAccuracy()
   {
      return personalAccuracy;
   }

   public void setPersonalAccuracy( String personalAccuracy )
   {
      this.personalAccuracy = personalAccuracy;
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

   public List< MappingVO > getDates()
   {
      return dates;
   }

   public void setDates( List< MappingVO > dates )
   {
      this.dates = dates;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public String[] getTermMonthArray()
   {
      return termMonthArray;
   }

   public void setTermMonthArray( String[] termMonthArray )
   {
      this.termMonthArray = termMonthArray;
   }

   public String getDecodePersonalAccuracyTemp()
   {
      return decodeField( personalAccuracy, accuracys, true );
   }
   
   public String getDecodeCompanyAccuracyTemp()
   {
      return decodeField( companyAccuracy, accuracys, true );
   }
}
