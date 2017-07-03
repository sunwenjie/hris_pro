/*
 * Created on 2012-05-07
 */
package com.kan.base.domain.message;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author iori luo
 */

public class MessageRegularVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1552070218064077854L;

   // for DB
   private String regularId;

   // 定时时间
   private String startDateTime;

   // 失效时间
   private String endDateTime;

   // 重复模式（按日，按周，按月）
   private String repeatType;

   //间隔
   private String period;

   //附加间隔时间
   private String additionalPeriod;

   //星期间隔
   private String weekPeriod;

   @Override
   public String getEncodedId() throws KANException
   {
      return KANUtil.filterEmpty( regularId ) != null ? KANUtil.encodeString( this.regularId ) : null;
   }

   @Override
   public void reset() throws KANException
   {
      this.startDateTime = null;
      this.endDateTime = null;
      this.repeatType = "0";
      this.period = null;
      this.additionalPeriod = null;
      this.weekPeriod = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final MessageRegularVO messageRegularVO = ( MessageRegularVO ) object;
      this.startDateTime = messageRegularVO.getStartDateTime();
      this.endDateTime = messageRegularVO.getEndDateTime();
      this.repeatType = messageRegularVO.getRepeatType();
      this.period = messageRegularVO.getPeriod();
      this.additionalPeriod = messageRegularVO.getAdditionalPeriod();
      this.weekPeriod = messageRegularVO.getWeekPeriod();
      super.setCreateDate( messageRegularVO.getCreateDate() );
      super.setModifyBy( messageRegularVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getRegularId()
   {
      return regularId;
   }

   public void setRegularId( String regularId )
   {
      this.regularId = regularId;
   }

   public String getStartDateTime()
   {
      return startDateTime;
   }

   public void setStartDateTime( String startDateTime )
   {
      this.startDateTime = startDateTime;
   }

   public String getEndDateTime()
   {
      return endDateTime;
   }

   public void setEndDateTime( String endDateTime )
   {
      this.endDateTime = endDateTime;
   }

   public String getRepeatType()
   {
      return repeatType;
   }

   public void setRepeatType( String repeatType )
   {
      this.repeatType = repeatType;
   }

   public String getPeriod()
   {
      return period;
   }

   public void setPeriod( String period )
   {
      this.period = period;
   }

   public String getAdditionalPeriod()
   {
      return additionalPeriod;
   }

   public void setAdditionalPeriod( String additionalPeriod )
   {
      this.additionalPeriod = additionalPeriod;
   }

   public String getWeekPeriod()
   {
      return weekPeriod;
   }

   public void setWeekPeriod( String weekPeriod )
   {
      this.weekPeriod = weekPeriod;
   }

}