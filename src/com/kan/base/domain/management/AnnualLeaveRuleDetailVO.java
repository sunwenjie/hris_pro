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

public class AnnualLeaveRuleDetailVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -3887369362091856128L;

   // 年假规则从表ID
   private String detailId;

   // 年假规则主表ID
   private String headerId;

   // 工龄满X年
   private String seniority;

   // 职级ID
   private String positionGradeId;

   // 法定小时数
   private String legalHours;

   // 福利小时数
   private String benefitHours;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   private List< MappingVO > seniorities = new ArrayList< MappingVO >();
   @JsonIgnore
   // 职级
   private List< MappingVO > positionGradeIds = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // 通用职级
      final MappingVO defaultMappingVO = KANUtil.getEmptyMappingVO( this.getLocale() );
      defaultMappingVO.setMappingValue( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "默认" : "Default" );
      this.positionGradeIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositionGrades( this.getLocale().getLanguage(), super.getCorpId() );
      this.positionGradeIds.add( 0, defaultMappingVO );
      this.seniorities = KANUtil.getMappings( request.getLocale(), "annulLeaveRule.detail.seniority" );
   }

   @Override
   public void reset() throws KANException
   {
      this.seniority = "0";
      this.legalHours = "0";
      this.benefitHours = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) object;
      this.seniority = annualLeaveRuleDetailVO.getSeniority();
      this.positionGradeId = annualLeaveRuleDetailVO.getPositionGradeId();
      this.legalHours = annualLeaveRuleDetailVO.getLegalHours();
      this.benefitHours = annualLeaveRuleDetailVO.getBenefitHours();
      this.description = annualLeaveRuleDetailVO.getDescription();
      super.setStatus( annualLeaveRuleDetailVO.getStatus() );
      super.setModifyDate( new Date() );
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

   public String getLegalHours()
   {
      return legalHours;
   }

   public void setLegalHours( String legalHours )
   {
      this.legalHours = legalHours;
   }

   public String getBenefitHours()
   {
      return benefitHours;
   }

   public void setBenefitHours( String benefitHours )
   {
      this.benefitHours = benefitHours;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public List< MappingVO > getPositionGradeIds()
   {
      return positionGradeIds;
   }

   public void setPositionGradeIds( List< MappingVO > positionGradeIds )
   {
      this.positionGradeIds = positionGradeIds;
   }

   public String getDecodePositionGradeId()
   {
      if ( positionGradeIds != null && positionGradeIds.size() > 0 )
      {
         for ( MappingVO mappingVO : positionGradeIds )
         {
            if ( mappingVO != null && mappingVO.getMappingId() != null && mappingVO.getMappingId().equals( positionGradeId ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }

      return "";
   }

   public String getSeniority()
   {
      return seniority;
   }

   public void setSeniority( String seniority )
   {
      this.seniority = seniority;
   }

   public List< MappingVO > getSeniorities()
   {
      return seniorities;
   }

   public void setSeniorities( List< MappingVO > seniorities )
   {
      this.seniorities = seniorities;
   }

   public String getDecodeSeniority()
   {
      if ( seniorities != null && seniorities.size() > 0 )
      {
         for ( MappingVO mappingVO : seniorities )
         {
            if ( mappingVO != null && mappingVO.getMappingId() != null && mappingVO.getMappingId().equals( seniority ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }

      return "";
   }

}
