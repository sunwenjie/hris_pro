package com.kan.hro.domain.biz.client;

import java.io.Serializable;

/**
 * 
* �����ƣ�DeductDayDTO  
* ��������  
* �����ˣ�Kevin  
* ����ʱ�䣺2014-9-3  
*
 */
public class ConstantsDayDTO implements Serializable
{
   // Serial Version UID
   private static final long serialVersionUID = -8512994107533150088L;

   // ��ֵ��Ч��ʼ����
   private String startDate;

   // ��ֵ��Ч��������
   private String endDate;

   // ��ֵ
   private double value;

   // ���췽��
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
