package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ShiftDTO implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 3126858752820792660L;

   // 排班主表
   private ShiftHeaderVO shiftHeaderVO = new ShiftHeaderVO();

   // 对应从表集合
   private List< ShiftDetailVO > shiftDetailVOs = new ArrayList< ShiftDetailVO >();

   // 排班例外集合
   private List< ShiftExceptionVO > shiftExceptionVOs = new ArrayList< ShiftExceptionVO >();

   public ShiftHeaderVO getShiftHeaderVO()
   {
      return shiftHeaderVO;
   }

   public void setShiftHeaderVO( ShiftHeaderVO shiftHeaderVO )
   {
      this.shiftHeaderVO = shiftHeaderVO;
   }

   public List< ShiftDetailVO > getShiftDetailVOs()
   {
      return shiftDetailVOs;
   }

   public void setShiftDetailVOs( List< ShiftDetailVO > shiftDetailVOs )
   {
      this.shiftDetailVOs = shiftDetailVOs;
   }

   public ShiftDetailVO getShiftDetailVOByDayIndex( final String dayIndex ) throws KANException
   {
      if ( shiftDetailVOs != null && shiftDetailVOs.size() > 0 )
      {
         for ( ShiftDetailVO shiftDetailVO : shiftDetailVOs )
         {
            if ( shiftDetailVO.getDayIndex() != null && shiftDetailVO.getDayIndex().trim().equals( dayIndex ) )
            {
               return shiftDetailVO;
            }
         }
      }

      return null;
   }

   public ShiftDetailVO getShiftDetailVOByShiftDay( final String shiftDay ) throws KANException
   {
      if ( shiftDay != null && !shiftDay.trim().equals( "" ) && shiftDetailVOs != null && shiftDetailVOs.size() > 0 )
      {
         for ( ShiftDetailVO shiftDetailVO : shiftDetailVOs )
         {
            final String dayString = KANUtil.formatDate( KANUtil.createDate( shiftDetailVO.getShiftDay() ), "yyyy-MM-dd" );

            if ( dayString.equals( KANUtil.formatDate( KANUtil.createDate( shiftDay ), "yyyy-MM-dd" ) ) )
            {
               return shiftDetailVO;
            }
         }
      }

      return null;
   }

   public List< ShiftExceptionVO > getShiftExceptionVOs()
   {
      return shiftExceptionVOs;
   }

   public void setShiftExceptionVOs( List< ShiftExceptionVO > shiftExceptionVOs )
   {
      this.shiftExceptionVOs = shiftExceptionVOs;
   }
}
