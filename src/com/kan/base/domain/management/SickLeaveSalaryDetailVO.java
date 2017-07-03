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
   // ���ٹ��ʴӱ�ID
   private String detailId;

   // ���ٹ�������ID
   private String headerId;

   // ������������ʼ��������ѡ�0-120��
   private String rangeFrom;

   // ����������������������ѡ�0-120����������ڻ���ڿ�ʼ
   private String rangeTo;

   //  ��н�������ٷֱȣ�����0-100��
   private String percentage;

   // �̶���
   private String fix;

   private String deduct;

   private String description;

   @JsonIgnore
   // ��������
   private List< MappingVO > rangeFroms = new ArrayList< MappingVO >();
   @JsonIgnore
   //  ����������������
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
