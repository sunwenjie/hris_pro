package com.kan.hro.domain.biz.client;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderOTVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -6940426782686484733L;

   // 订单加班方案Id，主键
   private String orderOTId;

   // 订单主表Id
   private String orderHeaderId;

   // 科目Id
   private String itemId;

   // 基数
   private String base;

   // 基数来源
   private String baseFrom;

   // 比例
   private String percentage;

   // 固定金
   private String fix;

   // 数量
   private String quantity;

   // 折扣
   private String discount;

   // 倍率
   private String multiple;

   // 计算周期
   private String cycle;

   // 生效日期
   private String startDate;

   // 结束日期
   private String endDate;

   // 计算结果上限
   private String resultCap;

   // 计算结果下限
   private String resultFloor;

   // 计算公式类型
   private String formularType;

   // 计算公式
   private String formular;

   // 描述
   private String description;

   /**
    *    For App
    */
   @JsonIgnore
   // 订单名称（中文）
   private String orderHeaderNameZH;
   @JsonIgnore
   // 订单名称（英文）
   private String orderHeaderNameEN;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderOTId );
   }

   public String getEncodedOrderHeaderId() throws KANException
   {
      return encodedField( orderHeaderId );
   }

   public String getDecodeBaseFrom()
   {
      return decodeField( this.baseFrom, KANConstants.getKANAccountConstants( super.getAccountId() ).getItemGroups( this.getLocale().getLanguage() ) );
   }

   public String getDecodeItemId()
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   public String getDecodeMultiple()
   {
      return decodeField( this.multiple, KANUtil.getMappings( this.getLocale(), "business.multiples" ), true );
   }

   public String getDecodeCycles()
   {
      return decodeField( this.cycle, KANUtil.getMappings( this.getLocale(), "business.cycles" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.itemId = "";
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
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) object;
      this.orderHeaderId = clientOrderOTVO.getOrderHeaderId();
      this.itemId = clientOrderOTVO.getItemId();
      this.cycle = clientOrderOTVO.getCycle();
      this.startDate = clientOrderOTVO.getStartDate();
      this.endDate = clientOrderOTVO.getEndDate();
      this.base = clientOrderOTVO.getBase();
      this.baseFrom = clientOrderOTVO.getBaseFrom();
      this.percentage = clientOrderOTVO.getPercentage();
      this.fix = clientOrderOTVO.getFix();
      this.quantity = clientOrderOTVO.getQuantity();
      this.discount = clientOrderOTVO.getDiscount();
      this.multiple = clientOrderOTVO.getMultiple();
      this.resultCap = clientOrderOTVO.getResultCap();
      this.resultFloor = clientOrderOTVO.getResultFloor();
      this.formularType = clientOrderOTVO.getFormularType();
      this.formular = clientOrderOTVO.getFormular();
      this.description = clientOrderOTVO.getDescription();
      super.setStatus( clientOrderOTVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getOrderOTId()
   {
      return orderOTId;
   }

   public void setOrderOTId( String orderOTId )
   {
      this.orderOTId = orderOTId;
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

   public String getBase()
   {
      return KANUtil.filterEmpty( base );
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseFrom()
   {
      return baseFrom;
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = baseFrom;
   }

   public String getPercentage()
   {
      return KANUtil.filterEmpty( percentage );
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return KANUtil.filterEmpty( fix );
   }

   public void setFix( String fix )
   {
      this.fix = fix;
   }

   public String getQuantity()
   {
      return KANUtil.filterEmpty( quantity );
   }

   public void setQuantity( String quantity )
   {
      this.quantity = quantity;
   }

   public String getDiscount()
   {
      return KANUtil.filterEmpty( discount );
   }

   public void setDiscount( String discount )
   {
      this.discount = discount;
   }

   public String getMultiple()
   {
      return multiple;
   }

   public void setMultiple( String multiple )
   {
      this.multiple = multiple;
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

   public String getResultCap()
   {
      return KANUtil.filterEmpty( resultCap );
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = resultCap;
   }

   public String getResultFloor()
   {
      return KANUtil.filterEmpty( resultFloor );
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = resultFloor;
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

   public String getEndDate()
   {
      return KANUtil.filterEmpty( KANUtil.formatDate( this.endDate, super.getAccountId() != null ? KANConstants.getKANAccountConstants( super.getAccountId() ).OPTIONS_DATE_FORMAT
            : null ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = KANUtil.filterEmpty( endDate );
   }

}
