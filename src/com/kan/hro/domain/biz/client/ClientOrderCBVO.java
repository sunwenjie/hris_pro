package com.kan.hro.domain.biz.client;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class ClientOrderCBVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = 7440694497772277760L;

   // �����̱�Id������
   private String orderCbId;

   // ��������Id
   private String orderHeaderId;

   // �̱�����Id   
   private String cbSolutionId;

   // ��ȫ�����
   private String freeShortOfMonth;

   // ��ȫ�¼Ʒ�
   private String chargeFullMonth;

   // ����
   private String description;

   /**
    * For App
    */
   @JsonIgnore
   // �������ƣ����ģ�
   private String orderHeaderNameZH;
   @JsonIgnore
   // �������ƣ�Ӣ�ģ�
   private String orderHeaderNameEN;
   @JsonIgnore
   // �̱��������ƣ����ģ�
   private String cbSolutionNameZH;
   @JsonIgnore
   // �̱��������ƣ�Ӣ�ģ�
   private String cbSolutionNameEN;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderCbId );
   }

   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.cbSolutionId = "";
      this.cbSolutionId = "";
      this.freeShortOfMonth = "";
      this.chargeFullMonth = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderCBVO clientOrderHeaderRuleVO = ( ClientOrderCBVO ) object;
      this.orderHeaderId = clientOrderHeaderRuleVO.getOrderHeaderId();
      this.cbSolutionId = clientOrderHeaderRuleVO.getCbSolutionId();
      this.freeShortOfMonth = clientOrderHeaderRuleVO.getFreeShortOfMonth();
      this.chargeFullMonth = clientOrderHeaderRuleVO.getChargeFullMonth();
      this.description = clientOrderHeaderRuleVO.getDescription();
      super.setStatus( clientOrderHeaderRuleVO.getStatus() );
      super.setModifyBy( clientOrderHeaderRuleVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getOrderCbId()
   {
      return orderCbId;
   }

   public void setOrderCbId( String orderCbId )
   {
      this.orderCbId = orderCbId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getCbSolutionId()
   {
      return cbSolutionId;
   }

   public void setCbSolutionId( String cbSolutionId )
   {
      this.cbSolutionId = cbSolutionId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getOrderHeaderName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getOrderHeaderNameZH();
         }
         else
         {
            return this.getOrderHeaderNameEN();
         }
      }
      else
      {
         return this.getOrderHeaderNameZH();
      }
   }

   public String getOrderHeaderNameZH()
   {
      return orderHeaderNameZH;
   }

   public void setOrderHeaderNameZH( String orderHeaderNameZH )
   {
      this.orderHeaderNameZH = orderHeaderNameZH;
   }

   public String getOrderHeaderNameEN()
   {
      return orderHeaderNameEN;
   }

   public void setOrderHeaderNameEN( String orderHeaderNameEN )
   {
      this.orderHeaderNameEN = orderHeaderNameEN;
   }

   public String getCbSolutionName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getCbSolutionNameZH();
         }
         else
         {
            return this.getCbSolutionNameEN();
         }
      }
      else
      {
         return this.getCbSolutionNameZH();
      }
   }

   public String getCbSolutionNameZH()
   {
      return cbSolutionNameZH;
   }

   public void setCbSolutionNameZH( String cbSolutionNameZH )
   {
      this.cbSolutionNameZH = cbSolutionNameZH;
   }

   public String getCbSolutionNameEN()
   {
      return cbSolutionNameEN;
   }

   public void setCbSolutionNameEN( String cbSolutionNameEN )
   {
      this.cbSolutionNameEN = cbSolutionNameEN;
   }

   public String getFreeShortOfMonth()
   {
      return freeShortOfMonth;
   }

   public void setFreeShortOfMonth( String freeShortOfMonth )
   {
      this.freeShortOfMonth = freeShortOfMonth;
   }

   public String getChargeFullMonth()
   {
      return chargeFullMonth;
   }

   public void setChargeFullMonth( String chargeFullMonth )
   {
      this.chargeFullMonth = chargeFullMonth;
   }

}
