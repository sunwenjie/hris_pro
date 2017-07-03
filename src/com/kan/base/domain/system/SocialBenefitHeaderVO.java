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

public class SocialBenefitHeaderVO extends BaseVO
{

   /**  
    *  Serial Version UID
    */
   private static final long serialVersionUID = -5909953666568502557L;

   /**
    * For DB
    */
   // �籣����ID
   private String headerId;

   // �籣���ƣ����ģ�
   private String nameZH;

   // �籣���ƣ�Ӣ�ģ�
   private String nameEN;

   // �籣����   
   private String cityId;

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
   private String attachment;

   // �籣����
   private String description;

   // Add Column ����С��λ ��˾����
   private String companyAccuracy;

   // Add Column ����С��λ ���˲���
   private String personalAccuracy;
   // Add Column С��λ������ʽ
   private String round;

   /**
    * For Application
    */
   // ��������
   private List< MappingVO > residencys = new ArrayList< MappingVO >();

   // ÿ������·�
   private List< MappingVO > adjustMonths = new ArrayList< MappingVO >();

   // �籣������
   private List< MappingVO > attributes = new ArrayList< MappingVO >();

   // �籣������
   private List< MappingVO > effectives = new ArrayList< MappingVO >();

   // �籣���ɹ���
   private List< MappingVO > rules = new ArrayList< MappingVO >();

   // ����
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // ȡֵ��Χ
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   // ���ڣ�1~31�ţ�
   private List< MappingVO > dates = new ArrayList< MappingVO >();

   // ʡID
   private String provinceId;

   private String cityIdTemp;

   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   // ��������
   private String[] residencyArray = new String[] {};

   // ��������
   private String[] termMonthArray = new String[] {};

   // ����
   private String[] attachmentArray;

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
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
      this.dates = KANUtil.getMappings( request.getLocale(), "business.client.dates" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.cityId = "";
      this.termMonth = "";
      this.residency = "0";
      this.adjustMonth = "0";
      this.attribute = "0";
      this.effective = "";
      this.startDateLimit = "0";
      this.endDateLimit = "0";
      this.startRule = "0";
      this.startRuleRemark = "";
      this.endRule = "0";
      this.endRuleRemark = "";
      this.makeup = "0";
      this.makeupMonth = "0";
      this.makeupCrossYear = "0";
      this.attachment = "";
      this.description = "";
      this.personalAccuracy = "";
      this.companyAccuracy = "";
      this.round = "0";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) object;
      this.nameZH = socialBenefitHeaderVO.getNameZH();
      this.nameEN = socialBenefitHeaderVO.getNameEN();
      this.cityId = socialBenefitHeaderVO.getCityId();
      this.termMonth = socialBenefitHeaderVO.getTermMonth();
      this.residency = socialBenefitHeaderVO.getResidency();
      this.adjustMonth = socialBenefitHeaderVO.getAdjustMonth();
      this.attribute = socialBenefitHeaderVO.getAttribute();
      this.effective = socialBenefitHeaderVO.getEffective();
      this.startDateLimit = socialBenefitHeaderVO.getStartDateLimit();
      this.endDateLimit = socialBenefitHeaderVO.getEndDateLimit();
      this.startRule = socialBenefitHeaderVO.getStartRule();
      this.startRuleRemark = socialBenefitHeaderVO.getStartRuleRemark();
      this.endRule = socialBenefitHeaderVO.getEndRule();
      this.endRuleRemark = socialBenefitHeaderVO.getEndRuleRemark();
      this.makeup = socialBenefitHeaderVO.getMakeup();
      this.makeupMonth = socialBenefitHeaderVO.getMakeupMonth();
      this.makeupCrossYear = socialBenefitHeaderVO.getMakeupCrossYear();
      this.attachment = socialBenefitHeaderVO.getAttachment();
      this.personalAccuracy = socialBenefitHeaderVO.getPersonalAccuracy();
      this.companyAccuracy = socialBenefitHeaderVO.getCompanyAccuracy();
      this.round = socialBenefitHeaderVO.getRound();
      this.description = socialBenefitHeaderVO.getDescription();
      super.setStatus( socialBenefitHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      this.termMonthArray = socialBenefitHeaderVO.getTermMonthArray();
      this.residencyArray = socialBenefitHeaderVO.getResidencyArray();
   }

   public String getDecodeCity()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   // ��������
   public String getDecodeResidency()
   {
      String returnString = "";

      final String[] residencyArray = KANUtil.jasonArrayToStringArray( residency );

      if ( residencyArray != null && residencyArray.length > 0 && residencys != null && residencys.size() > 0 )
      {
         for ( String arrayItem : residencyArray )
         {
            for ( MappingVO mappingVO : residencys )
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

      if ( KANUtil.hasContain( residencyArray, "7" ) )
      {
         return residencys.get( 7 ).getMappingValue();
      }

      return returnString;
   }

   // ÿ������·�
   public String getDecodeAdjustMonth()
   {
      return decodeField( adjustMonth, adjustMonths );
   }

   // ÿ������·�
   public String getDecodeMakeup()
   {
      return decodeField( makeup, super.getFlags() );
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   public String getDecodePersonalAccuracy()
   {
      return decodeField( personalAccuracy, accuracys );
   }

   public String getDecodeCompanyAccuracy()
   {
      return decodeField( companyAccuracy, accuracys );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
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

   public List< MappingVO > getEffectives()
   {
      return effectives;
   }

   public void setEffectives( List< MappingVO > effectives )
   {
      this.effectives = effectives;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
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

   public String[] getTermMonthArray()
   {
      return termMonthArray;
   }

   public void setTermMonthArray( String[] termMonthArray )
   {
      this.termMonthArray = termMonthArray;
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

   public String[] getResidencyArray()
   {
      return residencyArray;
   }

   public void setResidencyArray( String[] residencyArray )
   {
      this.residencyArray = residencyArray;
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
