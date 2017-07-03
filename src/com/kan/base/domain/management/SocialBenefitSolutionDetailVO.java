package com.kan.base.domain.management;

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

public class SocialBenefitSolutionDetailVO extends BaseVO
{

   /**  
    * Serial Version UID 
    */

   private static final long serialVersionUID = -3129574459749530206L;

   /**
    * For DB
    */

   private String detailId;

   private String headerId;

   private String sysDetailId;

   private String itemId;

   private String companyPercent;

   private String personalPercent;

   private String companyFloor;

   private String companyCap;

   private String personalFloor;

   private String personalCap;

   private String companyFixAmount;

   private String personalFixAmount;

   private String startDateLimit;

   private String endDateLimit;

   // 社保所属月
   private String attribute;

   // 社保发生月
   private String effective;

   // 开始月社保缴纳规则
   private String startRule;

   // 开始月社保缴纳规则备注
   private String startRuleRemark;

   // 结束月社保缴纳规则
   private String endRule;

   // 结束月社保缴纳规则备注
   private String endRuleRemark;

   private String description;

   /**
    * For Application
    */

   // 科目编号
   private String itemNo;

   // 科目列表
   private List< MappingVO > items = new ArrayList< MappingVO >();

   // 比例（公司） - 区间
   private List< MappingVO > companyPercentSection = new ArrayList< MappingVO >();

   // 比例（个人） - 区间
   private List< MappingVO > personalPercentSection = new ArrayList< MappingVO >();

   // 日期（1~31号）
   private List< MappingVO > dates = new ArrayList< MappingVO >();

   // 社保所属月
   private List< MappingVO > attributes = new ArrayList< MappingVO >();

   // 社保发生月
   private List< MappingVO > effectives = new ArrayList< MappingVO >();

   // 社保缴纳规则
   private List< MappingVO > rules = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getSbItems( request.getLocale().getLanguage() );
      if ( items != null && items.size() > 0 )
      {
         items.add( 0, getEmptyMappingVO() );
      }
      this.dates = KANUtil.getMappings( request.getLocale(), "business.client.dates" );
      this.attributes = KANUtil.getMappings( request.getLocale(), "sys.sb.attribute.months" );
      this.effectives = KANUtil.getMappings( request.getLocale(), "sys.sb.effctive.months" );
      this.rules = KANUtil.getMappings( request.getLocale(), "sys.sb.pay.rule" );
   }

   @Override
   public void reset() throws KANException
   {
      this.sysDetailId = "";
      this.itemId = "";
      this.companyPercent = "";
      this.personalPercent = "";
      this.companyFloor = "";
      this.companyCap = "";
      this.personalFloor = "";
      this.personalCap = "";
      this.companyFixAmount = "";
      this.personalFixAmount = "";
      this.startDateLimit = "";
      this.endDateLimit = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) object;
      this.sysDetailId = socialBenefitSolutionDetailVO.getSysDetailId();
      this.itemId = socialBenefitSolutionDetailVO.getItemId();
      this.companyPercent = socialBenefitSolutionDetailVO.getCompanyPercent();
      this.personalPercent = socialBenefitSolutionDetailVO.getPersonalPercent();
      this.companyFloor = socialBenefitSolutionDetailVO.getCompanyFloor();
      this.companyCap = socialBenefitSolutionDetailVO.getCompanyCap();
      this.personalFloor = socialBenefitSolutionDetailVO.getPersonalFloor();
      this.personalCap = socialBenefitSolutionDetailVO.getPersonalCap();
      this.companyFixAmount = socialBenefitSolutionDetailVO.getCompanyFixAmount();
      this.personalFixAmount = socialBenefitSolutionDetailVO.getPersonalFixAmount();
      this.attribute = socialBenefitSolutionDetailVO.getAttribute();
      this.effective = socialBenefitSolutionDetailVO.getEffective();
      this.startDateLimit = socialBenefitSolutionDetailVO.getStartDateLimit();
      this.endDateLimit = socialBenefitSolutionDetailVO.getEndDateLimit();
      this.startRule = socialBenefitSolutionDetailVO.getStartRule();
      this.startRuleRemark = socialBenefitSolutionDetailVO.getStartRuleRemark();
      this.endRule = socialBenefitSolutionDetailVO.getEndRule();
      this.endRuleRemark = socialBenefitSolutionDetailVO.getEndRuleRemark();
      super.setStatus( socialBenefitSolutionDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeItem()
   {
      return decodeField( itemId, items );
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

   public String getSysDetailId()
   {
      return sysDetailId;
   }

   public void setSysDetailId( String sysDetailId )
   {
      this.sysDetailId = sysDetailId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getCompanyPercent()
   {
      return KANUtil.filterEmpty( companyPercent ) == null ? null : companyPercent.trim();
   }

   public void setCompanyPercent( String companyPercent )
   {
      this.companyPercent = companyPercent;
   }

   public String getPersonalPercent()
   {
      return KANUtil.filterEmpty( personalPercent ) == null ? null : personalPercent.trim();
   }

   public void setPersonalPercent( String personalPercent )
   {
      this.personalPercent = personalPercent;
   }

   public String getCompanyFloor()
   {
      return KANUtil.filterEmpty( companyFloor ) == null ? null : companyFloor.trim();
   }

   public void setCompanyFloor( String companyFloor )
   {
      this.companyFloor = companyFloor;
   }

   public String getCompanyCap()
   {
      return KANUtil.filterEmpty( companyCap ) == null ? null : companyCap.trim();
   }

   public void setCompanyCap( String companyCap )
   {
      this.companyCap = companyCap;
   }

   public String getPersonalFloor()
   {
      return KANUtil.filterEmpty( personalFloor ) == null ? null : personalFloor.trim();
   }

   public void setPersonalFloor( String personalFloor )
   {
      this.personalFloor = personalFloor;
   }

   public String getPersonalCap()
   {
      return KANUtil.filterEmpty( personalCap ) == null ? null : personalCap.trim();
   }

   public void setPersonalCap( String personalCap )
   {
      this.personalCap = personalCap;
   }

   public String getCompanyFixAmount()
   {
      return KANUtil.filterEmpty( companyFixAmount ) == null ? null : companyFixAmount.trim();
   }

   public void setCompanyFixAmount( String companyFixAmount )
   {
      this.companyFixAmount = companyFixAmount;
   }

   public String getPersonalFixAmount()
   {
      return KANUtil.filterEmpty( personalFixAmount ) == null ? null : personalFixAmount.trim();
   }

   public void setPersonalFixAmount( String personalFixAmount )
   {
      this.personalFixAmount = personalFixAmount;
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

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getCompanyPercentSection()
   {
      return companyPercentSection;
   }

   public void setCompanyPercentSection( List< MappingVO > companyPercentSection )
   {
      this.companyPercentSection = companyPercentSection;
   }

   public List< MappingVO > getPersonalPercentSection()
   {
      return personalPercentSection;
   }

   public void setPersonalPercentSection( List< MappingVO > personalPercentSection )
   {
      this.personalPercentSection = personalPercentSection;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   public List< MappingVO > getDates()
   {
      return dates;
   }

   public void setDates( List< MappingVO > dates )
   {
      this.dates = dates;
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

}
