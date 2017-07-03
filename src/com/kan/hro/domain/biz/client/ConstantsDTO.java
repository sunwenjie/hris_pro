package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANUtil;

/**
 * 
* 类名称：DeductDayDTO  
* 类描述：  
* 创建人：Kevin  
* 创建时间：2014-9-3  
*
 */
public class ConstantsDTO implements Serializable
{
   // Serial Version UID  
   private static final long serialVersionUID = 5709786113231153855L;

   private List< ConstantsDayDTO > constantsDayDTOs = new ArrayList< ConstantsDayDTO >();

   public final List< ConstantsDayDTO > getConstantsDayDTOs()
   {
      return constantsDayDTOs;
   }

   public final void setConstantsDayDTOs( List< ConstantsDayDTO > constantsDayDTOs )
   {
      this.constantsDayDTOs = constantsDayDTOs;
   }

   // 构造方法
   public ConstantsDTO()
   {

   }

   // 构造方法
   public ConstantsDTO( final String startDate, final String endDate, final double value )
   {
      addConstantsDay( startDate, endDate, value );
   }

   // 添加ConstantsDay
   public final void addConstantsDay( final String startDate, final String endDate, final double value )
   {
      constantsDayDTOs.add( new ConstantsDayDTO( startDate, endDate, value ) );
   }

   // 获取包含的所有科目
   public double getValue()
   {
      return getValue( null );
   }

   // 获取包含的所有科目
   public double getValue( final String day )
   {
      double value = 0;

      if ( constantsDayDTOs != null && constantsDayDTOs.size() > 0 )
      {
         for ( ConstantsDayDTO constantsDayDTO : constantsDayDTOs )
         {
            if ( KANUtil.filterEmpty( day ) == null
                  || ( KANUtil.filterEmpty( constantsDayDTO.getStartDate() ) == null && KANUtil.filterEmpty( constantsDayDTO.getEndDate() ) == null )
                  || ( KANUtil.filterEmpty( constantsDayDTO.getStartDate() ) == null && KANUtil.filterEmpty( constantsDayDTO.getEndDate() ) != null && KANUtil.getDays( KANUtil.createCalendar( day ) ) <= KANUtil.getDays( KANUtil.createCalendar( constantsDayDTO.getEndDate() ) ) )
                  || ( KANUtil.filterEmpty( constantsDayDTO.getStartDate() ) != null && KANUtil.filterEmpty( constantsDayDTO.getEndDate() ) == null && KANUtil.getDays( KANUtil.createCalendar( day ) ) >= KANUtil.getDays( KANUtil.createCalendar( constantsDayDTO.getStartDate() ) ) )
                  || ( KANUtil.filterEmpty( constantsDayDTO.getStartDate() ) != null && KANUtil.filterEmpty( constantsDayDTO.getEndDate() ) != null
                        && KANUtil.getDays( KANUtil.createCalendar( day ) ) <= KANUtil.getDays( KANUtil.createCalendar( constantsDayDTO.getEndDate() ) ) && KANUtil.getDays( KANUtil.createCalendar( day ) ) >= KANUtil.getDays( KANUtil.createCalendar( constantsDayDTO.getStartDate() ) ) ) )
            {
               value = value + constantsDayDTO.getValue();
            }
         }
      }

      return value;
   }
}
