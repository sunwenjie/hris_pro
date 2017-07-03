package com.kan.hro.domain.biz.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -6940426782686484733L;

   // �����ӱ�Id������
   private String orderDetailId;

   // ��������Id
   private String orderHeaderId;

   // ��ĿId
   private String itemId;

   // ���㷽ʽ
   private String calculateType;

   // �����ʽ          
   private String packageType;

   // ���㷽ʽ
   private String divideType;

   // ��������
   private String cycle;

   // ��ʼʱ��
   private String startDate;

   // ����ʱ��
   private String endDate;

   // ����
   private String base;

   // ������Դ
   private String baseFrom;

   // ����
   private String percentage;

   // �̶���
   private String fix;

   // ����
   private String quantity;

   // �ۿ�
   private String discount;

   // ����
   private String multiple;

   // ���������� 
   private String resultCap;

   // ����������
   private String resultFloor;

   // ���㹫ʽ����
   private String formularType;

   // ���㹫ʽ
   private String formular;

   // ��ְ���շ����
   private String onboardNoCharge;

   // ��ְ���շ����
   private String offDutyNoCharge;

   // �Զ���ְ���շ����
   private String resignNoCharge;

   // �����ڲ��շ����
   private String probationNoCharge;

   // ��ְ���첻�Ʒ�
   private String onboardWithout;

   // ��ְ���첻�Ʒ�
   private String offDutyWidthout;

   // ����
   private String description;

   /**
    *    For App
    */

   // ��������Name
   private String orderHeaderName;

   // �Ƿ�Ӽ�
   private String isPriceMarkup;

   // ����ѿ�Ŀ���͵����п�Ŀ
   private List< MappingVO > items = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderDetailId );
   }

   // ����ID����
   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   // ���㷽ʽ
   public String getDecodeCalculateType() throws KANException
   {
      return decodeField( this.packageType, KANUtil.getMappings( this.getLocale(), "business.client.order.header.detail.calculateTypes" ) );
   }

   // �����ʽ
   public String getDecodePackageType() throws KANException
   {
      return decodeField( this.packageType, KANUtil.getMappings( this.getLocale(), "business.client.order.header.detail.packageTypes" ) );
   }

   // ���㹫ʽ����
   public String getDecodeFormularType() throws KANException
   {
      return decodeField( this.formularType, KANUtil.getMappings( this.getLocale(), "business.client.order.header.detail.formularTypes" ) );
   }

   // ���� itemId ��ö�Ӧ��name
   public String getDecodeItemId() throws KANException
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeMultiple()
   {
      return decodeField( this.multiple, KANUtil.getMappings( this.getLocale(), "business.multiples" ), true );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.itemId = "";
      this.calculateType = "0";
      this.packageType = "0";
      this.divideType = "0";
      this.cycle = "";
      this.startDate = "";
      this.endDate = "";
      this.base = "";
      this.baseFrom = "";
      this.percentage = "";
      this.fix = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "";
      this.resultCap = "";
      this.resultFloor = "";
      this.formularType = "0";
      this.formular = "";
      this.onboardNoCharge = "";
      this.offDutyNoCharge = "";
      this.resignNoCharge = "";
      this.probationNoCharge = "";
      this.onboardWithout = "";
      this.offDutyWidthout = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) object;
      this.orderHeaderId = clientOrderDetailVO.getOrderHeaderId();
      this.itemId = clientOrderDetailVO.getItemId();
      this.calculateType = clientOrderDetailVO.getCalculateType();
      this.packageType = clientOrderDetailVO.getPackageType();
      this.divideType = clientOrderDetailVO.getDivideType();
      this.cycle = clientOrderDetailVO.getCycle();
      this.startDate = clientOrderDetailVO.getStartDate();
      this.endDate = clientOrderDetailVO.getEndDate();
      this.base = clientOrderDetailVO.getBase();
      this.baseFrom = clientOrderDetailVO.getBaseFrom();
      this.percentage = clientOrderDetailVO.getPercentage();
      this.fix = clientOrderDetailVO.getFix();
      this.quantity = clientOrderDetailVO.getQuantity();
      this.discount = clientOrderDetailVO.getDiscount();
      this.multiple = clientOrderDetailVO.getMultiple();
      this.resultCap = clientOrderDetailVO.getResultCap();
      this.resultFloor = clientOrderDetailVO.getResultFloor();
      this.formularType = clientOrderDetailVO.getFormularType();
      this.formular = clientOrderDetailVO.getFormular();
      this.onboardNoCharge = clientOrderDetailVO.getOnboardNoCharge();
      this.offDutyNoCharge = clientOrderDetailVO.getOffDutyNoCharge();
      this.resignNoCharge = clientOrderDetailVO.getResignNoCharge();
      this.probationNoCharge = clientOrderDetailVO.getProbationNoCharge();
      this.onboardWithout = clientOrderDetailVO.getOnboardNoCharge();
      this.offDutyWidthout = clientOrderDetailVO.getOffDutyWidthout();
      this.description = clientOrderDetailVO.getDescription();
      super.setStatus( clientOrderDetailVO.getStatus() );
      super.setModifyBy( clientOrderDetailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
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

   public String getCalculateType()
   {
      return calculateType;
   }

   public void setCalculateType( String calculateType )
   {
      this.calculateType = calculateType;
   }

   public String getPackageType()
   {
      return packageType;
   }

   public void setPackageType( String packageType )
   {
      this.packageType = packageType;
   }

   public String getCycle()
   {
      return cycle;
   }

   public void setCycle( String cycle )
   {
      this.cycle = cycle;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( KANUtil.formatDate( startDate, super.getAccountId() != null ? KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT
            : null ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = KANUtil.filterEmpty( startDate );
   }

   public String getBase()
   {
      return base;
   }

   public void setBase( String base )
   {
      this.base = KANUtil.filterEmpty( base );
   }

   public String getBaseFrom()
   {
      return baseFrom;
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = KANUtil.filterEmpty( baseFrom );
   }

   public String getPercentage()
   {
      return percentage;
   }

   public void setPercentage( String percentage )
   {
      this.percentage = KANUtil.filterEmpty( percentage );
   }

   public String getFix()
   {
      return fix;
   }

   public void setFix( String fix )
   {
      this.fix = KANUtil.filterEmpty( fix );
   }

   public String getQuantity()
   {
      return quantity;
   }

   public void setQuantity( String quantity )
   {
      this.quantity = KANUtil.filterEmpty( quantity );
   }

   public String getDiscount()
   {
      return discount;
   }

   public void setDiscount( String discount )
   {
      this.discount = KANUtil.filterEmpty( discount );
   }

   public String getMultiple()
   {
      return multiple;
   }

   public void setMultiple( String multiple )
   {
      this.multiple = KANUtil.filterEmpty( multiple );
   }

   public String getResultCap()
   {
      return resultCap;
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = KANUtil.filterEmpty( resultCap );
   }

   public String getResultFloor()
   {
      return resultFloor;
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = KANUtil.filterEmpty( resultFloor );
   }

   public String getFormularType()
   {
      return formularType;
   }

   public void setFormularType( String formularType )
   {
      this.formularType = formularType;
   }

   public String getFormular()
   {
      return formular;
   }

   public void setFormular( String formular )
   {
      this.formular = formular;
   }

   public String getOnboardNoCharge()
   {
      return onboardNoCharge;
   }

   public void setOnboardNoCharge( String onboardNoCharge )
   {
      this.onboardNoCharge = onboardNoCharge;
   }

   public String getOffDutyNoCharge()
   {
      return offDutyNoCharge;
   }

   public void setOffDutyNoCharge( String offDutyNoCharge )
   {
      this.offDutyNoCharge = offDutyNoCharge;
   }

   public String getResignNoCharge()
   {
      return resignNoCharge;
   }

   public void setResignNoCharge( String resignNoCharge )
   {
      this.resignNoCharge = resignNoCharge;
   }

   public String getProbationNoCharge()
   {
      return probationNoCharge;
   }

   public void setProbationNoCharge( String probationNoCharge )
   {
      this.probationNoCharge = probationNoCharge;
   }

   public String getOnboardWithout()
   {
      return onboardWithout;
   }

   public void setOnboardWithout( String onboardWithout )
   {
      this.onboardWithout = onboardWithout;
   }

   public String getOffDutyWidthout()
   {
      return offDutyWidthout;
   }

   public void setOffDutyWidthout( String offDutyWidthout )
   {
      this.offDutyWidthout = offDutyWidthout;
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
      return orderHeaderName;
   }

   public void setOrderHeaderName( String orderHeaderName )
   {
      this.orderHeaderName = orderHeaderName;
   }

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public String getIsPriceMarkup()
   {
      return isPriceMarkup;
   }

   public void setIsPriceMarkup( String isPriceMarkup )
   {
      this.isPriceMarkup = isPriceMarkup;
   }

   public String getDivideType()
   {
      return divideType;
   }

   public void setDivideType( String divideType )
   {
      this.divideType = divideType;
   }

   public String getEndDate()
   {
      return endDate == null ? null
            : ( KANUtil.filterEmpty( KANUtil.formatDate( endDate, super.getAccountId() != null ? KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT
                  : null ) ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = KANUtil.filterEmpty( endDate );
   }

}
