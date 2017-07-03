package com.kan.hro.domain.biz.performance;

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

public class BudgetSettingHeaderVO extends BaseVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = -4574106075949626663L;

   /***
    * For db
    */
   private String headerId;
   private String year;
   private String startDate;
   private String endDate;
   private String startDate_bl;
   private String endDate_bl;
   private String startDate_pm;
   private String endDate_pm;
   private String startDate_final;
   private String endDate_final;
   private String startDate_bu;
   private String endDate_bu;
   private int maxInvitation;
   private String noticeLetterTemplate;
   private String description;

   @JsonIgnore
   private List< Object > detailVOs = new ArrayList< Object >();

   @JsonIgnore
   private List< MappingVO > years = new ArrayList< MappingVO >();

   @JsonIgnore
   private List< MappingVO > templates = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      years.add( getEmptyMappingVO() );
      years.addAll( KANUtil.getYears( currYear - 5, currYear ) );
      templates = KANConstants.getKANAccountConstants( super.getAccountId() ).getMessageTemplateByType( super.getLocale().getLanguage(), "1" );
      templates.add( 0, getEmptyMappingVO() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   @Override
   public void reset() throws KANException
   {
      this.year = "0";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final BudgetSettingHeaderVO budgetSettingHeaderVO = ( BudgetSettingHeaderVO ) object;
      this.year = budgetSettingHeaderVO.getYear();
      this.startDate = budgetSettingHeaderVO.getStartDate();
      this.endDate = budgetSettingHeaderVO.getEndDate();
      this.startDate_bl = budgetSettingHeaderVO.getStartDate_bl();
      this.endDate_bl = budgetSettingHeaderVO.getEndDate_bl();
      this.startDate_pm = budgetSettingHeaderVO.getStartDate_pm();
      this.endDate_pm = budgetSettingHeaderVO.getEndDate_pm();
      this.startDate_final = budgetSettingHeaderVO.getStartDate_final();
      this.endDate_final = budgetSettingHeaderVO.getEndDate_final();
      this.startDate_bu = budgetSettingHeaderVO.getStartDate_bu();
      this.endDate_bu = budgetSettingHeaderVO.getEndDate_bu();
      this.maxInvitation = budgetSettingHeaderVO.getMaxInvitation();
      this.noticeLetterTemplate = budgetSettingHeaderVO.getNoticeLetterTemplate();
      this.description = budgetSettingHeaderVO.getDescription();
      super.setStatus( budgetSettingHeaderVO.getStatus() );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getYear()
   {
      return year;
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getStartDate()
   {
      return startDate == null ? null : KANUtil.formatDate( startDate, "yyyy-MM-dd" );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate == null ? null : KANUtil.formatDate( endDate, "yyyy-MM-dd" );
   }

   public String getStartDate_bl()
   {
      return startDate_bl == null ? null : KANUtil.formatDate( startDate_bl, "yyyy-MM-dd" );
   }

   public void setStartDate_bl( String startDate_bl )
   {
      this.startDate_bl = startDate_bl;
   }

   public String getEndDate_bl()
   {
      return endDate_bl == null ? null : KANUtil.formatDate( endDate_bl, "yyyy-MM-dd" );
   }

   public void setEndDate_bl( String endDate_bl )
   {
      this.endDate_bl = endDate_bl;
   }

   public String getStartDate_pm()
   {
      return startDate_pm == null ? null : KANUtil.formatDate( startDate_pm, "yyyy-MM-dd" );
   }

   public void setStartDate_pm( String startDate_pm )
   {
      this.startDate_pm = startDate_pm;
   }

   public String getEndDate_pm()
   {
      return endDate_pm == null ? null : KANUtil.formatDate( endDate_pm, "yyyy-MM-dd" );
   }

   public void setEndDate_pm( String endDate_pm )
   {
      this.endDate_pm = endDate_pm;
   }

   public String getStartDate_final()
   {
      return startDate_final == null ? null : KANUtil.formatDate( startDate_final, "yyyy-MM-dd" );
   }

   public void setStartDate_final( String startDate_final )
   {
      this.startDate_final = startDate_final;
   }

   public String getEndDate_final()
   {
      return endDate_final == null ? null : KANUtil.formatDate( endDate_final, "yyyy-MM-dd" );
   }

   public String getStartDate_bu()
   {
      return startDate_bu == null ? null : KANUtil.formatDate( startDate_bu, "yyyy-MM-dd" );
   }

   public void setStartDate_bu( String startDate_bu )
   {
      this.startDate_bu = startDate_bu;
   }

   public String getEndDate_bu()
   {
      return endDate_bu == null ? null : KANUtil.formatDate( endDate_bu, "yyyy-MM-dd" );
   }

   public void setEndDate_bu( String endDate_bu )
   {
      this.endDate_bu = endDate_bu;
   }

   public void setEndDate_final( String endDate_final )
   {
      this.endDate_final = endDate_final;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public int getMaxInvitation()
   {
      return maxInvitation;
   }

   public void setMaxInvitation( int maxInvitation )
   {
      this.maxInvitation = maxInvitation;
   }

   public String getNoticeLetterTemplate()
   {
      return noticeLetterTemplate;
   }

   public void setNoticeLetterTemplate( String noticeLetterTemplate )
   {
      this.noticeLetterTemplate = noticeLetterTemplate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getYears()
   {
      return years;
   }

   public void setYears( List< MappingVO > years )
   {
      this.years = years;
   }

   public List< MappingVO > getTemplates()
   {
      return templates;
   }

   public void setTemplates( List< MappingVO > templates )
   {
      this.templates = templates;
   }

   public List< Object > getDetailVOs()
   {
      return detailVOs;
   }

   public void setDetailVOs( List< Object > detailVOs )
   {
      this.detailVOs = detailVOs;
   }

}