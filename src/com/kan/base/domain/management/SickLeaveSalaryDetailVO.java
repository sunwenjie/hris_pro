package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SickLeaveSalaryDetailVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -6795893259892539066L;
   /**
    * For DB
    */
   /**
    * For DB
    */
   // 病假工资从表ID
   private String detailId;

   // 病假工资主表ID
   private String headerId;

   // 工作月数（开始），下拉选项（0-120）
   private String rangeFrom;

   // 工作月数（结束），下拉选项（0-120），必须大于或等于开始
   private String rangeTo;

   //  带薪比例，百分比（介于0-100）
   private String percentage;

   // 固定金
   private String fix;

   private String deduct;

   private String description;

   @JsonIgnore
   // 工作月数
   private List< MappingVO > rangeFroms = new ArrayList< MappingVO >();
   @JsonIgnore
   //  工作月数（结束）
   private List< MappingVO > rangeTos = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.rangeFrom = "0";
      this.rangeTo = "0";
      this.percentage = "";
      this.fix = "";
      this.description = "";
      this.deduct = "";
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.rangeFroms = KANUtil.getMappings( request.getLocale(), "sickleavesalarydetail.rangeFrom.type" );
      this.rangeTos = KANUtil.getMappings( request.getLocale(), "sickleavesalarydetail.rangeTo.type" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) object;
      this.headerId = sickLeaveSalaryDetailVO.getHeaderId();
      this.rangeFrom = sickLeaveSalaryDetailVO.getRangeFrom();
      this.rangeTo = sickLeaveSalaryDetailVO.getRangeTo();
      this.percentage = sickLeaveSalaryDetailVO.getPercentage();
      this.description = sickLeaveSalaryDetailVO.getDescription();
      this.fix = sickLeaveSalaryDetailVO.getFix();
      if ( sickLeaveSalaryDetailVO.getDeduct().equals( "" ) )
      {
         this.deduct = null;
      }
      else
      {
         this.deduct = sickLeaveSalaryDetailVO.getDeduct();
      }
      super.setStatus( sickLeaveSalaryDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeDeduct()
   {
      if ( this.deduct == null )
         return formatNumber( "0" );
      else
         return formatNumber( deduct );
   }

   public String getDecodePercentage()
   {
      return formatNumber( percentage );
   }

   public String getDecodeFix()
   {
      return formatNumber( fix );
   }

   public String getDecodeRangeFrom()
   {
      return decodeField( rangeFrom, rangeFroms );
   }

   public String getDecodeRangeTo()
   {
      return decodeField( rangeTo, rangeTos );
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

   public String getRangeFrom()
   {
      return KANUtil.filterEmpty( rangeFrom );
   }

   public void setRangeFrom( String rangeFrom )
   {
      this.rangeFrom = rangeFrom;
   }

   public String getRangeTo()
   {
      return KANUtil.filterEmpty( rangeTo );
   }

   public void setRangeTo( String rangeTo )
   {
      this.rangeTo = rangeTo;
   }

   public String getPercentage()
   {
      return percentage;
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return fix;
   }

   public void setFix( String fix )
   {
      this.fix = fix;
   }

   public List< MappingVO > getRangeFroms()
   {
      return rangeFroms;
   }

   public void setRangeFroms( List< MappingVO > rangeFroms )
   {
      this.rangeFroms = rangeFroms;
   }

   public List< MappingVO > getRangeTos()
   {
      return rangeTos;
   }

   public void setRangeTos( List< MappingVO > rangeTos )
   {
      this.rangeTos = rangeTos;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getDeduct()
   {
      return deduct;
   }

   public void setDeduct( String deduct )
   {
      this.deduct = deduct;
   }

}
