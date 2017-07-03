package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TimesheetDetailVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 6995809997519152824L;

   /**
    * For DB
    */
   // 考勤从表Id
   private String detailId;

   // 考勤主表Id
   private String headerId;

   // 日期
   private String day;

   // 日期类型（参考日历中的日期类型）
   private String dayType;

   // 工作小时数（实际）
   private String workHours;

   // 工作时间段
   private String workPeriod;

   // 当天全勤小时数
   private String fullHours;

   // 打卡开始时间
   private String signIn;

   // 打卡结束时间
   private String signOut;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 日期类型
   private List< MappingVO > dayTypies = new ArrayList< MappingVO >();

   // 工作小时数（回补企业计算部分），结算使用
   private String additionalWorkHoursBill;

   // 工作小时数（回补个人计算部分），结算使用
   private String additionalWorkHoursCost;

   // 工作小时数，未按照Rate折算
   private String norateWorkHours;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.dayTypies = KANUtil.getMappings( request.getLocale(), "sys.calendar.header.day.type" );
   }

   @Override
   public void reset() throws KANException
   {
      this.day = "";
      this.dayType = "";
      this.workHours = "";
      this.fullHours = "";
      this.description = "";
      this.signIn = "";
      this.signOut = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final TimesheetDetailVO timeSheetDetailVO = ( TimesheetDetailVO ) object;
      this.day = timeSheetDetailVO.getDay();
      this.dayType = timeSheetDetailVO.getDayType();
      this.workHours = timeSheetDetailVO.getWorkHours();
      this.fullHours = timeSheetDetailVO.getFullHours();
      this.signIn = timeSheetDetailVO.getSignIn();
      this.signOut = timeSheetDetailVO.getSignOut();
      this.description = timeSheetDetailVO.getDescription();
      super.setStatus( timeSheetDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeDayType()
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

   public String getDay()
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

   public String getWorkHours()
   {
      return workHours;
   }

   public void setWorkHours( String workHours )
   {
      this.workHours = workHours;
   }

   public String getDescription()
   {
      return description == null ? "" : description;
   }

   public String getWorkPeriod()
   {
      return workPeriod;
   }

   public void setWorkPeriod( String workPeriod )
   {
      this.workPeriod = workPeriod;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getFullHours()
   {
      return fullHours;
   }

   public void setFullHours( String fullHours )
   {
      this.fullHours = fullHours;
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

   public String getAdditionalWorkHoursBill()
   {
      return additionalWorkHoursBill;
   }

   public void setAdditionalWorkHoursBill( String additionalWorkHoursBill )
   {
      this.additionalWorkHoursBill = additionalWorkHoursBill;
   }

   public final void addAdditionalWorkHoursBill( String additionalWorkHoursBill )
   {
      if ( additionalWorkHoursBill != null )
      {
         if ( this.additionalWorkHoursBill == null )
         {
            this.additionalWorkHoursBill = additionalWorkHoursBill;
         }
         else
         {
            this.additionalWorkHoursBill = String.valueOf( Double.valueOf( this.additionalWorkHoursBill ) + Double.valueOf( additionalWorkHoursBill ) );
         }
      }
   }

   public String getAdditionalWorkHoursCost()
   {
      return additionalWorkHoursCost;
   }

   public void setAdditionalWorkHoursCost( String additionalWorkHoursCost )
   {
      this.additionalWorkHoursCost = additionalWorkHoursCost;
   }

   public final void addAdditionalWorkHoursCost( String additionalWorkHoursCost )
   {
      if ( additionalWorkHoursCost != null )
      {
         if ( this.additionalWorkHoursCost == null )
         {
            this.additionalWorkHoursCost = additionalWorkHoursCost;
         }
         else
         {
            this.additionalWorkHoursCost = String.valueOf( Double.valueOf( this.additionalWorkHoursCost ) + Double.valueOf( additionalWorkHoursCost ) );
         }
      }
   }

   public final String getNorateWorkHours()
   {
      return norateWorkHours;
   }

   public final void setNorateWorkHours( String norateWorkHours )
   {
      this.norateWorkHours = norateWorkHours;
   }

   public final void addNorateWorkHours( String norateWorkHours )
   {
      if ( norateWorkHours != null )
      {
         if ( this.norateWorkHours == null )
         {
            this.norateWorkHours = norateWorkHours;
         }
         else
         {
            this.norateWorkHours = String.valueOf( Double.valueOf( this.norateWorkHours ) + Double.valueOf( norateWorkHours ) );
         }
      }
   }

   public String getSignIn()
   {
      return signIn;
   }

   public void setSignIn( String signIn )
   {
      this.signIn = signIn;
   }

   public String getSignOut()
   {
      return signOut;
   }

   public void setSignOut( String signOut )
   {
      this.signOut = signOut;
   }

   public String getDecodeWorkPeriod()
   {
      return getDecoded( KANUtil.filterEmpty( workPeriod ) );
   }

   private String getDecoded( final String source )
   {
      if ( source != null )
      {
         final String[] periodArray = KANUtil.jasonArrayToStringArray( source );

         if ( periodArray.length == 1 )
         {
            int periodId = Integer.parseInt( periodArray[ 0 ] );
            return getStartTime( periodId ) + " ~ " + getEndTime( periodId );
         }
         else
         {
            // 初始化StringBuilder
            final StringBuilder rb = new StringBuilder();

            int begin = Integer.parseInt( periodArray[ 0 ] );
            int end = Integer.parseInt( periodArray[ 0 ] );

            for ( int i = 1; i < periodArray.length; i++ )
            {
               int tempPeriodId = Integer.parseInt( periodArray[ i ] );
               if ( end + 1 == tempPeriodId )
               {
                  end = end + 1;
               }
               else
               {
                  rb.append( getStartTime( begin ) + " ~ " + getEndTime( end ) );
                  rb.append( "；" );
                  begin = tempPeriodId;
                  end = tempPeriodId;
               }
            }

            rb.append( getStartTime( begin ) + " ~ " + getEndTime( end ) );

            return rb.toString() + "；";
         }
      }

      return "";
   }

   private String getStartTime( int i )
   {
      int t = ( i - 1 ) / 2;
      int r = ( i - 1 ) % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

   private String getEndTime( int i )
   {
      int t = i / 2;
      int r = i % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

}
