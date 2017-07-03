package com.kan.hro.domain.biz.client;

import java.io.Serializable;

/**
 * 
* 类名称：DeductDayDTO  
* 类描述：  
* 创建人：Kevin  
* 创建时间：2014-9-3  
*
 */
public class ConstantsDayDTO implements Serializable
{
   // Serial Version UID
   private static final long serialVersionUID = -8512994107533150088L;

   // 数值有效开始日期
   private String startDate;

   // 数值有效结束日期
   private String endDate;

   // 数值
   private double value;

   // 构造方法
   public ConstantsDayDTO( final String startDate, final String endDate, final double value )
   {
      this.startDate = startDate;
      this.endDate = endDate;
      this.value = value;
   }

   public final String getStartDate()
   {
      return startDate;
   }

   public final void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public final String getEndDate()
   {
      return endDate;
   }

   public final void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public final double getValue()
   {
      return value;
   }

   public final void setValue( double value )
   {
      this.value = value;
   }

}
