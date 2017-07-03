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

public class ShiftDetailVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 6471166151798069409L;

   /**
    * For DB
    */

   // 排班从表Id
   private String detailId;

   // 排班主表Id
   private String headerId;

   // 日期名称（中文），按周和按天自动生成，自定义不使用
   private String nameZH;

   // 日期名称（英文）
   private String nameEN;

   // 日期序列（从1开始排）
   private String dayIndex;

   // 排班日期，自定义使用，其他不使用
   private String shiftDay;

   // 工作时间段
   private String shiftPeriod;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 排班时间区段数组
   private String[] shiftPeriodArray;
   @JsonIgnore
   // 排班时间区段MappingVO
   private List< MappingVO > shiftPeriods = new ArrayList< MappingVO >();
   @JsonIgnore
   // 缓存日期类型
   private String dayType;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.shiftPeriods = KANUtil.getMappings( request.getLocale(), "sys.shift.detail.shift.period" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.dayIndex = "";
      this.shiftDay = "";
      this.shiftPeriod = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) object;
      this.nameZH = shiftDetailVO.getNameZH();
      this.nameEN = shiftDetailVO.getNameEN();
      this.dayIndex = shiftDetailVO.getDayIndex();
      this.shiftDay = shiftDetailVO.getShiftDay();
      this.shiftPeriod = shiftDetailVO.getShiftPeriod();
      this.description = shiftDetailVO.getDescription();
      super.setStatus( shiftDetailVO.getStatus() );
      super.setModifyDate( new Date() );
      this.shiftPeriodArray = shiftDetailVO.getShiftPeriodArray();
   }

   public String getDecodedShiftPeriod()
   {
      return getDecoded( KANUtil.filterEmpty( shiftPeriod ) );
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
                  rb.append( "       " );
                  begin = tempPeriodId;
                  end = tempPeriodId;
               }
            }

            rb.append( getStartTime( begin ) + " ~ " + getEndTime( end ) );

            return rb.toString();
         }
      }

      return null;
   }

   public String getStartTime( int i )
   {
      int t = ( i - 1 ) / 2;
      int r = ( i - 1 ) % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

   public String getEndTime( int i )
   {
      int t = i / 2;
      int r = i % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

   public MappingVO getMappingById( String id )
   {
      return shiftPeriods.get( Integer.parseInt( id ) );
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

   public String getDayIndex()
   {
      return dayIndex;
   }

   public void setDayIndex( String dayIndex )
   {
      this.dayIndex = dayIndex;
   }

   public String getShiftDay()
   {
      return KANUtil.filterEmpty( decodeDate( shiftDay ) );
   }

   public void setShiftDay( String shiftDay )
   {
      this.shiftDay = shiftDay;
   }

   public String getShiftPeriod()
   {
      return shiftPeriod;
   }

   public void setShiftPeriod( String shiftPeriod )
   {
      this.shiftPeriod = shiftPeriod;
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

   public String[] getShiftPeriodArray()
   {
      return shiftPeriodArray;
   }

   public void setShiftPeriodArray( String[] shiftPeriodArray )
   {
      this.shiftPeriodArray = shiftPeriodArray;
   }

   public List< MappingVO > getShiftPeriods()
   {
      return shiftPeriods;
   }

   public void setShiftPeriods( List< MappingVO > shiftPeriods )
   {
      this.shiftPeriods = shiftPeriods;
   }

   public double getFullHours()
   {
      if ( shiftPeriod != null && !shiftPeriod.trim().equals( "" ) )
      {
         return KANUtil.jasonArrayToStringArray( shiftPeriod ).length * 0.5;
      }

      return 0;
   }

   public final String getDayType()
   {
      return dayType;
   }

   public final void setDayType( String dayType )
   {
      this.dayType = dayType;
   }

}
