package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class CalendarDTO implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -3016636665614649609L;

   // 日历主表
   private CalendarHeaderVO calendarHeaderVO = new CalendarHeaderVO();

   // 对应从表集合
   private List< CalendarDetailVO > calendarDetailVOs = new ArrayList< CalendarDetailVO >();

   public CalendarHeaderVO getCalendarHeaderVO()
   {
      return calendarHeaderVO;
   }

   public void setCalendarHeaderVO( CalendarHeaderVO calendarHeaderVO )
   {
      this.calendarHeaderVO = calendarHeaderVO;
   }

   public List< CalendarDetailVO > getCalendarDetailVOs()
   {
      return calendarDetailVOs;
   }

   public void setCalendarDetailVOs( List< CalendarDetailVO > calendarDetailVOs )
   {
      this.calendarDetailVOs = calendarDetailVOs;
   }

   public List< CalendarDetailVO > getCalendarDetailVOsByMonthly( final String monthly ) throws KANException
   {
      // 初始化CalendarDetailVO列表
      final List< CalendarDetailVO > returnCalendarDetailVOs = new ArrayList< CalendarDetailVO >();

      if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
      {
         for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
         {
            final String dayString = KANUtil.getYear( KANUtil.createDate( calendarDetailVO.getDay() ) ) + "/"
                  + KANUtil.getMonth( KANUtil.createDate( calendarDetailVO.getDay() ) ).replace( "0", "" );

            if ( dayString.equals( monthly ) )
            {
               returnCalendarDetailVOs.add( calendarDetailVO );
            }
         }
      }

      return returnCalendarDetailVOs;
   }

   public CalendarDetailVO getCalendarDetailVOByDay( final String day ) throws KANException
   {
      if ( calendarDetailVOs != null && calendarDetailVOs.size() > 0 )
      {
         for ( CalendarDetailVO calendarDetailVO : calendarDetailVOs )
         {
            final String dayString = KANUtil.formatDate( KANUtil.createDate( calendarDetailVO.getDay() ), "yyyy-MM-dd" );

            if ( dayString.equals( KANUtil.formatDate( KANUtil.createDate( day ), "yyyy-MM-dd" ) ) )
            {
               return calendarDetailVO;
            }
         }
      }

      return null;
   }

}
