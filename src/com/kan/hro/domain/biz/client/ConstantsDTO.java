package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANUtil;

/**
 * 
* �����ƣ�DeductDayDTO  
* ��������  
* �����ˣ�Kevin  
* ����ʱ�䣺2014-9-3  
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

   // ���췽��
   public ConstantsDTO()
   {

   }

   // ���췽��
   public ConstantsDTO( final String startDate, final String endDate, final double value )
   {
      addConstantsDay( startDate, endDate, value );
   }

   // ���ConstantsDay
   public final void addConstantsDay( final String startDate, final String endDate, final double value )
   {
      constantsDayDTOs.add( new ConstantsDayDTO( startDate, endDate, value ) );
   }

   // ��ȡ���������п�Ŀ
   public double getValue()
   {
      return getValue( null );
   }

   // ��ȡ���������п�Ŀ
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
