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
* ����ʱ�䣺2014-8-19  
*
 */
public class DeductDTO implements Serializable
{
   // Serial Version UID
   private static final long serialVersionUID = -6056069176003189901L;

   private List< DeductDayDTO > deductDayDTOs = new ArrayList< DeductDayDTO >();

   public final List< DeductDayDTO > getDeductDayDTOs()
   {
      return deductDayDTOs;
   }

   public final void setDeductDayDTOs( List< DeductDayDTO > deductDayDTOs )
   {
      this.deductDayDTOs = deductDayDTOs;
   }

   // ���DeductDay
   public final void addDeductDay( final String itemId, final String day, final double days )
   {
      deductDayDTOs.add( new DeductDayDTO( itemId, day, days ) );
   }

   // ��ȡ���������п�Ŀ
   public List< String > getItems()
   {
      final List< String > items = new ArrayList< String >();

      if ( deductDayDTOs != null && deductDayDTOs.size() > 0 )
      {
         for ( DeductDayDTO deductDayDTO : deductDayDTOs )
         {
            if ( !items.contains( deductDayDTO.getItemId() ) )
            {
               items.add( deductDayDTO.getItemId() );
            }
         }
      }

      return items;
   }

   // ��ȡ���пۼ����� - ���տ�ʼ�ͽ������ڶ�
   public double getTotalDeductDays( final String itemId, final String startDate, final String endDate )
   {
      double tempTotalDeductDays = 0;

      if ( deductDayDTOs != null && deductDayDTOs.size() > 0 )
      {
         for ( DeductDayDTO deductDayDTO : deductDayDTOs )
         {
            if ( itemId != null && itemId.equals( deductDayDTO.getItemId() ) && KANUtil.filterEmpty( deductDayDTO.getDay() ) != null
                  && KANUtil.getDays( KANUtil.createDate( deductDayDTO.getDay() ) ) >= KANUtil.getDays( KANUtil.createDate( startDate ) )
                  && KANUtil.getDays( KANUtil.createDate( deductDayDTO.getDay() ) ) <= KANUtil.getDays( KANUtil.createDate( endDate ) ) )
            {
               tempTotalDeductDays = tempTotalDeductDays + deductDayDTO.getDays();
            }
         }
      }

      return tempTotalDeductDays;
   }
}
