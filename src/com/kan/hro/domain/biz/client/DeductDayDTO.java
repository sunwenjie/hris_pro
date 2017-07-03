package com.kan.hro.domain.biz.client;

import java.io.Serializable;

/**
 * 
* �����ƣ�DeductDayDTO  
* ��������  
* �����ˣ�Kevin  
* ����ʱ�䣺2014-8-19  
*
 */
public class DeductDayDTO implements Serializable
{
   // Serial Version UID
   private static final long serialVersionUID = -5648971769894054291L;

   // ��Ŀ
   private String itemId;

   // �������ڣ�ͳһʹ�á�yyyy-MM-dd��
   private String day;

   // ��������
   private double days;

   // ���췽��
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
