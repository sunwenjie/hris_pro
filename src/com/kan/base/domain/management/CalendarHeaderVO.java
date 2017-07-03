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

public class CalendarHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -6876473824465511923L;

   /**
    * For DB
    */

   // 日历主表Id
   private String headerId;

   // 日历名称（中文）
   private String nameZH;

   // 日历名称（英文）
   private String nameEN;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 年
   private String year;
   @JsonIgnore
   // 月 
   private String month;
   @JsonIgnore
   // 1900 - 2050年
   private List< MappingVO > years = new ArrayList< MappingVO >();
   @JsonIgnore
   // 12月份
   private List< MappingVO > months = new ArrayList< MappingVO >();

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      years = KANUtil.getYears( 1900, Integer.valueOf( KANUtil.getYear( new Date() ) ) + 1 );
      months = KANUtil.getMonths();
      year = KANUtil.getYear( new Date() );
      month = KANUtil.getMonth( new Date() );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) object;
      this.nameZH = calendarHeaderVO.getNameZH();
      this.nameEN = calendarHeaderVO.getNameEN();
      this.description = calendarHeaderVO.getDescription();
      super.setStatus( calendarHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
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

   public List< MappingVO > getYears()
   {
      return years;
   }

   public void setYears( List< MappingVO > years )
   {
      this.years = years;
   }

   public List< MappingVO > getMonths()
   {
      return months;
   }

   public void setMonths( List< MappingVO > months )
   {
      this.months = months;
   }

   public String getYear()
   {
      return year;
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getMonth()
   {
      return month;
   }

   public void setMonth( String month )
   {
      this.month = month;
   }

}
