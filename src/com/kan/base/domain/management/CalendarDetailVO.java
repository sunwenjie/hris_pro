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

public class CalendarDetailVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -329400327043313372L;

   /**
    * For DB
    */

   // 日历从表Id
   private String detailId;

   // 日历主表Id
   private String headerId;

   // 日期名称（中文）
   private String nameZH;

   // 日期名称（英文）
   private String nameEN;

   // 日期
   private String day;

   // 日期类型（1:工作日，2:休息日，3:节假日）
   private String dayType;

   // 调换日期
   private String changeDay;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 标识周几
   private String week;
   @JsonIgnore
   // 日期类型
   private List< MappingVO > dayTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 标识月份类型（1：上月；2：本月；3：下月）
   public String monthType;
   @JsonIgnore
   public final static String CALENDAR_DAY_TYPE_P = "previous";

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.dayTypies = KANUtil.getMappings( request.getLocale(), "sys.calendar.header.day.type" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.day = "";
      this.dayType = "0";
      this.changeDay = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) object;
      this.nameZH = calendarDetailVO.getNameZH();
      this.nameEN = calendarDetailVO.getNameEN();
      this.day = calendarDetailVO.getDay();
      this.dayType = calendarDetailVO.getDayType();
      this.changeDay = calendarDetailVO.getChangeDay();
      this.description = calendarDetailVO.getDescription();
      super.setStatus( calendarDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeDayType() throws KANException
   {
      return decodeField( dayType, dayTypies );
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

   public String getDay() throws KANException
   {
      return KANUtil.formatDate( day, "yyyy-MM-dd" );
   }

   public void setDay( String day )
   {
      this.day = day;
   }

   public String getDayType()
   {
      return dayType;
   }

   public void setDayType( String dayType )
   {
      this.dayType = dayType;
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
      return encodedField( detailId );
   }

   public List< MappingVO > getDayTypies()
   {
      return dayTypies;
   }

   public void setDayTypies( List< MappingVO > dayTypies )
   {
      this.dayTypies = dayTypies;
   }

   public String getChangeDay()
   {
      return KANUtil.formatDate( changeDay, "yyyy-MM-dd" );
   }

   public void setChangeDay( String changeDay )
   {
      this.changeDay = changeDay;
   }

   public String getWeek()
   {
      return week;
   }

   public void setWeek( String week )
   {
      this.week = week;
   }

   public String getMonthType()
   {
      return monthType;
   }

   public void setMonthType( String monthType )
   {
      this.monthType = monthType;
   }

}
