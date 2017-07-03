package com.kan.hro.domain.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderLeaveVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -6940426782686484733L;

   // �����ݼٷ���Id������
   private String orderLeaveId;

   // ��������Id
   private String orderHeaderId;

   // ��ĿId
   private String itemId;

   // ��ٹ���ID
   private String annualLeaveRuleId;

   // ����������Сʱ��
   private String legalQuantity;

   // ����������Сʱ��
   private String benefitQuantity;

   // ʹ������
   private String cycle;

   // �������
   private String year;

   // �������Ƿ��ʹ��
   private String probationUsing;

   // �ӳ�ʹ������
   private String delayUsing;

   // ������δʹ�������������
   private String legalQuantityDelayMonth;

   // ������δʹ�������������
   private String benefitQuantityDelayMonth;

   // ����
   private String description;

   /**
    *    For App
    */
   @JsonIgnore
   // �������ƣ����ģ�
   private String orderHeaderNameZH;
   @JsonIgnore
   // �������ƣ�Ӣ�ģ�
   private String orderHeaderNameEN;
   @JsonIgnore
   // ��Ŀ���ƣ����ģ�
   private String itemNameZH;
   @JsonIgnore
   // ��Ŀ���ƣ�Ӣ�ģ�
   private String itemNameEN;
   @JsonIgnore
   // �ݼٿ�Ŀ���͵����п�Ŀ
   private List< MappingVO > items = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��������ʣ�ࣨСʱ�� ��siuvan��
   private String leftLegalQuantity;
   @JsonIgnore
   // ��������ʣ�ࣨСʱ�� ��siuvan��
   private String leftBenefitQuantity;
   @JsonIgnore
   // ��ٹ���
   private List< MappingVO > annualLeaveRules = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), super.getCorpId() );
      if ( this.items != null )
      {
         this.items.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }
      this.annualLeaveRules = KANConstants.getKANAccountConstants( super.getAccountId() ).getAnnualLeaveRules( request.getLocale().getLanguage(), super.getCorpId() );

      if ( annualLeaveRules != null )
      {
         this.annualLeaveRules.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderLeaveId );
   }

   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   public String getDecodeItemId()
   {
      for ( MappingVO itemMappingVO : items )
      {
         if ( itemMappingVO.getMappingId().equals( this.itemId ) )
         {
            return itemMappingVO.getMappingValue();
         }
      }
      return "";
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.itemId = "";
      this.annualLeaveRuleId = "";
      this.cycle = "0";
      this.benefitQuantityDelayMonth = "";
      this.legalQuantity = "";
      this.benefitQuantity = "";
      this.probationUsing = "";
      this.delayUsing = "";
      this.legalQuantityDelayMonth = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) object;
      this.orderHeaderId = clientOrderLeaveVO.getOrderHeaderId();
      this.itemId = clientOrderLeaveVO.getItemId();
      this.annualLeaveRuleId = clientOrderLeaveVO.getAnnualLeaveRuleId();
      this.cycle = clientOrderLeaveVO.getCycle();
      this.year = clientOrderLeaveVO.getYear();
      this.benefitQuantityDelayMonth = clientOrderLeaveVO.getBenefitQuantityDelayMonth();
      this.legalQuantity = clientOrderLeaveVO.getLegalQuantity();
      this.benefitQuantity = clientOrderLeaveVO.getBenefitQuantity();
      this.probationUsing = clientOrderLeaveVO.getProbationUsing();
      this.delayUsing = clientOrderLeaveVO.getDelayUsing();
      this.legalQuantityDelayMonth = clientOrderLeaveVO.getLegalQuantityDelayMonth();
      this.description = clientOrderLeaveVO.getDescription();
      super.setStatus( clientOrderLeaveVO.getStatus() );
      super.setModifyBy( clientOrderLeaveVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getOrderLeaveId()
   {
      return orderLeaveId;
   }

   public void setOrderLeaveId( String orderLeaveId )
   {
      this.orderLeaveId = orderLeaveId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getLegalQuantity()
   {
      return KANUtil.filterEmpty( legalQuantity );
   }

   public void setLegalQuantity( String legalQuantity )
   {
      this.legalQuantity = legalQuantity;
   }

   public String getBenefitQuantity()
   {
      return KANUtil.filterEmpty( benefitQuantity );
   }

   public void setBenefitQuantity( String benefitQuantity )
   {
      this.benefitQuantity = benefitQuantity;
   }

   public String getCycle()
   {
      return cycle;
   }

   public void setCycle( String cycle )
   {
      this.cycle = cycle;
   }

   public String getProbationUsing()
   {
      return probationUsing;
   }

   public void setProbationUsing( String probationUsing )
   {
      this.probationUsing = probationUsing;
   }

   public String getDelayUsing()
   {
      return delayUsing;
   }

   public void setDelayUsing( String delayUsing )
   {
      this.delayUsing = delayUsing;
   }

   public String getLegalQuantityDelayMonth()
   {
      return legalQuantityDelayMonth;
   }

   public void setLegalQuantityDelayMonth( String legalQuantityDelayMonth )
   {
      this.legalQuantityDelayMonth = legalQuantityDelayMonth;
   }

   public String getBenefitQuantityDelayMonth()
   {
      return benefitQuantityDelayMonth;
   }

   public void setBenefitQuantityDelayMonth( String benefitQuantityDelayMonth )
   {
      this.benefitQuantityDelayMonth = benefitQuantityDelayMonth;
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

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public String getItemName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getItemNameZH();
         }
         else
         {
            return this.getItemNameEN();
         }
      }
      else
      {
         return this.getItemNameZH();
      }
   }

   public String getItemNameZH()
   {
      return itemNameZH;
   }

   public void setItemNameZH( String itemNameZH )
   {
      this.itemNameZH = itemNameZH;
   }

   public String getItemNameEN()
   {
      return itemNameEN;
   }

   public void setItemNameEN( String itemNameEN )
   {
      this.itemNameEN = itemNameEN;
   }

   public String getLeftLegalQuantity()
   {
      return leftLegalQuantity;
   }

   public void setLeftLegalQuantity( String leftLegalQuantity )
   {
      this.leftLegalQuantity = leftLegalQuantity;
   }

   public String getLeftBenefitQuantity()
   {
      return leftBenefitQuantity;
   }

   public void setLeftBenefitQuantity( String leftBenefitQuantity )
   {
      this.leftBenefitQuantity = leftBenefitQuantity;
   }

   public String getYear()
   {
      return KANUtil.filterEmpty( year );
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getAnnualLeaveRuleId()
   {
      return KANUtil.filterEmpty( annualLeaveRuleId );
   }

   public void setAnnualLeaveRuleId( String annualLeaveRuleId )
   {
      this.annualLeaveRuleId = annualLeaveRuleId;
   }

   public List< MappingVO > getAnnualLeaveRules()
   {
      return annualLeaveRules;
   }

   public void setAnnualLeaveRules( List< MappingVO > annualLeaveRules )
   {
      this.annualLeaveRules = annualLeaveRules;
   }

   public String getRemark()
   {
      String ret = "";
      if ( KANUtil.filterEmpty( itemId ) != null )
      {
         // ��������
         if ( "41".equals( itemId ) )
         {
            if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
            {
               ret = year + getDecodeItemId() + "�� " + getBenefitQuantity() + " Сʱ������ " + getLegalQuantity() + " Сʱ ��";
            }
            else
            {
               ret = year + " " + getDecodeItemId() + " ( " + getBenefitQuantity() + " hours, Legal: " + getLegalQuantity() + " hours)";
            }
         }
         else
         {
            if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
            {
               ret = getDecodeItemId() + "�� " + getBenefitQuantity() + " Сʱ ��";
            }
            else
            {
               ret = getDecodeItemId() + " ( " + getBenefitQuantity() + " hours )";
            }
         }
      }

      return ret;
   }

}
