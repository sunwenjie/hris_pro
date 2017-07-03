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

public class SocialBenefitSolutionHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -5342330133057673144L;

   /**
    * For DB
    */
   private String headerId;

   private String sysHeaderId;

   // 社保方案名称（中文）
   private String nameZH;

   // 社保方案名称（英文）
   private String nameEN;

   // 申报开始日期
   private String startDateLimit;

   // 申报结束日期
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

   // 公司承担个人社保
   private String personalSBBurden;

   // 附件
   private String attachment;

   // 描述
   private String description;

   // 社保方案类型
   private String sbType;

   /**
    * For Application
    */
   // 系统社保
   private List< MappingVO > sysHeaderIds;

   // 社保详情字段，以数组形式存储。
   private String[] indexArray;

   private String[] detailIdArray;

   private String[] sysDetailIdArray;

   private String[] itemIdArray;

   private String[] companyPercentArray;

   private String[] personalPercentArray;

   private String[] companyFloorArray;

   private String[] companyCapArray;

   private String[] personalFloorArray;

   private String[] personalCapArray;

   private String[] companyFixAmountArray;

   private String[] personalFixAmountArray;

   private String[] startDateLimitArray;

   private String[] endDateLimitArray;

   private String[] attributeArray;

   private String[] effectiveArray;

   private String[] startRuleArray;

   private String[] startRuleRemarkArray;

   private String[] endRuleArray;

   private String[] endRuleRemarkArray;

   private String[] statusArray;

   // 省份ID
   private String provinceId;

   // 城市ID
   private String cityIdTemp;

   // 省份列表
   private List< MappingVO > provinces;

   // 城市ID
   private String cityId;

   // 社保（系统）名称 （中文）
   private String sbNameZH;

   // 社保（系统）名称 （英文）
   private String sbNameEN;
   @JsonIgnore
   // 日期（1~31号）
   private List< MappingVO > dates = new ArrayList< MappingVO >();
   @JsonIgnore
   // 附件
   private String[] attachmentArray = new String[] {};
   @JsonIgnore
   // 方案集合
   private List< String > headerIds = new ArrayList< String >();
   @JsonIgnore
   // 方案类型集合
   private List< MappingVO > sbTypes = new ArrayList< MappingVO >();

   // 社保服务类型ID
   private String serviceIds;
   @JsonIgnore
   // 社保所属月
   private List< MappingVO > attributes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 社保发生月
   private List< MappingVO > effectives = new ArrayList< MappingVO >();
   @JsonIgnore
   // 社保缴纳规则
   private List< MappingVO > rules = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.sysHeaderIds = KANConstants.getSocialBenefitHeaders( request.getLocale().getLanguage() );
      this.sbTypes = KANUtil.getMappings( request.getLocale(), "def.socialBenefit.solution.sbType" );
      if ( sysHeaderIds != null && sysHeaderIds.size() > 0 )
      {
         sysHeaderIds.add( 0, getEmptyMappingVO() );
      }
      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      if ( this.provinces != null )
      {
         this.provinces.add( 0, super.getEmptyMappingVO() );
      }
      this.dates = KANUtil.getMappings( request.getLocale(), "business.client.dates" );
      this.attributes = KANUtil.getMappings( request.getLocale(), "sys.sb.attribute.months" );
      this.effectives = KANUtil.getMappings( request.getLocale(), "sys.sb.effctive.months" );
      this.rules = KANUtil.getMappings( request.getLocale(), "sys.sb.pay.rule" );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.cityId = "0";
      this.startDateLimit = "0";
      this.endDateLimit = "0";
      this.attachment = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) object;
      this.nameZH = socialBenefitSolutionHeaderVO.getNameZH();
      this.nameEN = socialBenefitSolutionHeaderVO.getNameEN();
      this.sbType = socialBenefitSolutionHeaderVO.getSbType();
      this.attribute = socialBenefitSolutionHeaderVO.getAttribute();
      this.effective = socialBenefitSolutionHeaderVO.getEffective();
      this.startDateLimit = socialBenefitSolutionHeaderVO.getStartDateLimit();
      this.endDateLimit = socialBenefitSolutionHeaderVO.getEndDateLimit();
      this.startRule = socialBenefitSolutionHeaderVO.getStartRule();
      this.startRuleRemark = socialBenefitSolutionHeaderVO.getStartRuleRemark();
      this.endRule = socialBenefitSolutionHeaderVO.getEndRule();
      this.endRuleRemark = socialBenefitSolutionHeaderVO.getEndRuleRemark();
      this.personalSBBurden = socialBenefitSolutionHeaderVO.getPersonalSBBurden();
      this.attachment = socialBenefitSolutionHeaderVO.getAttachment();
      this.description = socialBenefitSolutionHeaderVO.getDescription();
      this.indexArray = socialBenefitSolutionHeaderVO.getIndexArray();
      this.detailIdArray = socialBenefitSolutionHeaderVO.getDetailIdArray();
      this.sysDetailIdArray = socialBenefitSolutionHeaderVO.getSysDetailIdArray();
      this.itemIdArray = socialBenefitSolutionHeaderVO.getItemIdArray();
      this.companyPercentArray = socialBenefitSolutionHeaderVO.getCompanyPercentArray();
      this.personalPercentArray = socialBenefitSolutionHeaderVO.getPersonalPercentArray();
      this.companyFloorArray = socialBenefitSolutionHeaderVO.getCompanyFloorArray();
      this.companyCapArray = socialBenefitSolutionHeaderVO.getCompanyCapArray();
      this.personalFloorArray = socialBenefitSolutionHeaderVO.getPersonalFloorArray();
      this.personalCapArray = socialBenefitSolutionHeaderVO.getPersonalCapArray();
      this.companyFixAmountArray = socialBenefitSolutionHeaderVO.getCompanyFixAmountArray();
      this.personalFixAmountArray = socialBenefitSolutionHeaderVO.getPersonalFixAmountArray();
      this.startDateLimitArray = socialBenefitSolutionHeaderVO.getStartDateLimitArray();
      this.endDateLimitArray = socialBenefitSolutionHeaderVO.getEndDateLimitArray();
      this.attributeArray = socialBenefitSolutionHeaderVO.getAttributeArray();
      this.effectiveArray = socialBenefitSolutionHeaderVO.getEffectiveArray();
      this.startRuleArray = socialBenefitSolutionHeaderVO.getStartRuleArray();
      this.startRuleRemarkArray = socialBenefitSolutionHeaderVO.getStartRuleRemarkArray();
      this.endRuleArray = socialBenefitSolutionHeaderVO.getEndRuleArray();
      this.endRuleRemarkArray = socialBenefitSolutionHeaderVO.getEndRuleRemarkArray();
      super.setStatus( socialBenefitSolutionHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      this.statusArray = socialBenefitSolutionHeaderVO.getStatusArray();
      this.attachmentArray = new String[] {};
   }

   public String getDecodeCity()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   public String getDecodeSbType()
   {
      return decodeField( sbType, this.sbTypes );
   }

   public List< MappingVO > getCities( final String provinceId, final HttpServletRequest request )
   {
      return KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
   }

   // 申报开始时间
   public String getDecodeStartDateLimit()
   {
      return decodeField( startDateLimit, this.dates );
   }

   // 申报结束时间
   public String getDecodeEndDateLimit()
   {
      return decodeField( endDateLimit, this.dates );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getSysHeaderId()
   {
      return sysHeaderId;
   }

   public void setSysHeaderId( String sysHeaderId )
   {
      this.sysHeaderId = sysHeaderId;
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

   public List< MappingVO > getSysHeaderIds()
   {
      return sysHeaderIds;
   }

   public void setSysHeaderIds( List< MappingVO > sysHeaderIds )
   {
      this.sysHeaderIds = sysHeaderIds;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   /**
    * For Application get And set method
    */

   public String[] getIndexArray()
   {
      return indexArray;
   }

   public void setIndexArray( String[] indexArray )
   {
      this.indexArray = indexArray;
   }

   public String[] getDetailIdArray()
   {
      return detailIdArray;
   }

   public void setDetailIdArray( String[] detailIdArray )
   {
      this.detailIdArray = detailIdArray;
   }

   public String[] getStartDateLimitArray()
   {
      return startDateLimitArray;
   }

   public void setStartDateLimitArray( String[] startDateLimitArray )
   {
      this.startDateLimitArray = startDateLimitArray;
   }

   public String[] getEndDateLimitArray()
   {
      return endDateLimitArray;
   }

   public void setEndDateLimitArray( String[] endDateLimitArray )
   {
      this.endDateLimitArray = endDateLimitArray;
   }

   public String[] getSysDetailIdArray()
   {
      return sysDetailIdArray;
   }

   public void setSysDetailIdArray( String[] sysDetailIdArray )
   {
      this.sysDetailIdArray = sysDetailIdArray;
   }

   public String[] getItemIdArray()
   {
      return itemIdArray;
   }

   public void setItemIdArray( String[] itemIdArray )
   {
      this.itemIdArray = itemIdArray;
   }

   public String[] getCompanyPercentArray()
   {
      return companyPercentArray;
   }

   public void setCompanyPercentArray( String[] companyPercentArray )
   {
      this.companyPercentArray = companyPercentArray;
   }

   public String[] getPersonalPercentArray()
   {
      return personalPercentArray;
   }

   public void setPersonalPercentArray( String[] personalPercentArray )
   {
      this.personalPercentArray = personalPercentArray;
   }

   public String[] getCompanyFloorArray()
   {
      return companyFloorArray;
   }

   public void setCompanyFloorArray( String[] companyFloorArray )
   {
      this.companyFloorArray = companyFloorArray;
   }

   public String[] getCompanyCapArray()
   {
      return companyCapArray;
   }

   public void setCompanyCapArray( String[] companyCapArray )
   {
      this.companyCapArray = companyCapArray;
   }

   public String[] getPersonalFloorArray()
   {
      return personalFloorArray;
   }

   public void setPersonalFloorArray( String[] personalFloorArray )
   {
      this.personalFloorArray = personalFloorArray;
   }

   public String[] getPersonalCapArray()
   {
      return personalCapArray;
   }

   public void setPersonalCapArray( String[] personalCapArray )
   {
      this.personalCapArray = personalCapArray;
   }

   public String[] getCompanyFixAmountArray()
   {
      return companyFixAmountArray;
   }

   public void setCompanyFixAmountArray( String[] companyFixAmountArray )
   {
      this.companyFixAmountArray = companyFixAmountArray;
   }

   public String[] getPersonalFixAmountArray()
   {
      return personalFixAmountArray;
   }

   public void setPersonalFixAmountArray( String[] personalFixAmountArray )
   {
      this.personalFixAmountArray = personalFixAmountArray;
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

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public List< MappingVO > getDates()
   {
      return dates;
   }

   public void setDates( List< MappingVO > dates )
   {
      this.dates = dates;
   }

   public String[] getStatusArray()
   {
      return statusArray;
   }

   public void setStatusArray( String[] statusArray )
   {
      this.statusArray = statusArray;
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

   public String getId()
   {
      return this.getHeaderId();
   }

   public String getSbNameZH()
   {
      return sbNameZH;
   }

   public void setSbNameZH( String sbNameZH )
   {
      this.sbNameZH = sbNameZH;
   }

   public String getSbNameEN()
   {
      return sbNameEN;
   }

   public void setSbNameEN( String sbNameEN )
   {
      this.sbNameEN = sbNameEN;
   }

   public String getDecodeSbName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return sbNameZH;
         }
         else
         {
            return sbNameEN;
         }
      }
      else
      {
         return sbNameZH;
      }
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public List< String > getHeaderIds()
   {
      return headerIds;
   }

   public void setHeaderIds( List< String > headerIds )
   {
      this.headerIds = headerIds;
   }

   public String getServiceIds()
   {
      return serviceIds;
   }

   public void setServiceIds( String serviceIds )
   {
      this.serviceIds = serviceIds;
   }

   public String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public List< MappingVO > getSbTypes()
   {
      return sbTypes;
   }

   public void setSbTypes( List< MappingVO > sbTypes )
   {
      this.sbTypes = sbTypes;
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

   public String[] getAttributeArray()
   {
      return attributeArray;
   }

   public void setAttributeArray( String[] attributeArray )
   {
      this.attributeArray = attributeArray;
   }

   public String[] getEffectiveArray()
   {
      return effectiveArray;
   }

   public void setEffectiveArray( String[] effectiveArray )
   {
      this.effectiveArray = effectiveArray;
   }

   public String[] getStartRuleArray()
   {
      return startRuleArray;
   }

   public void setStartRuleArray( String[] startRuleArray )
   {
      this.startRuleArray = startRuleArray;
   }

   public String[] getStartRuleRemarkArray()
   {
      return startRuleRemarkArray;
   }

   public void setStartRuleRemarkArray( String[] startRuleRemarkArray )
   {
      this.startRuleRemarkArray = startRuleRemarkArray;
   }

   public String[] getEndRuleArray()
   {
      return endRuleArray;
   }

   public void setEndRuleArray( String[] endRuleArray )
   {
      this.endRuleArray = endRuleArray;
   }

   public String[] getEndRuleRemarkArray()
   {
      return endRuleRemarkArray;
   }

   public void setEndRuleRemarkArray( String[] endRuleRemarkArray )
   {
      this.endRuleRemarkArray = endRuleRemarkArray;
   }

}