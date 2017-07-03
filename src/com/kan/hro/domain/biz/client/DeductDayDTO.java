package com.kan.hro.domain.biz.client;

import java.io.Serializable;

/**
 * 
* 类名称：DeductDayDTO  
* 类描述：  
* 创建人：Kevin  
* 创建时间：2014-8-19  
*
 */
public class DeductDayDTO implements Serializable
{
   // Serial Version UID
   private static final long serialVersionUID = -5648971769894054291L;

   // 科目
   private String itemId;

   // 日历日期，统一使用“yyyy-MM-dd”
   private String day;

   // 折算天数
   private double days;

   // 构造方法
   public DeductDayDTO( final String itemId, final String day, final double days )
   {
      this.itemId = itemId;
      this.day = day;
      this.days = days;
   }

   public final String getItemId()
   {
      return itemId;
   }

   public final void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public final String getDay()
   {
      return day;
   }

   public final void setDay( String day )
   {
      this.day = day;
   }

   public final double getDays()
   {
      return days;
   }

   public final void setDays( double days )
   {
      this.days = days;
   }

}
