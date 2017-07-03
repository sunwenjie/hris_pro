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

public class ClientOrderDetailRuleVO extends BaseVO
{

   // SerialVersionUID  
   private static final long serialVersionUID = 6780142437097493345L;

   // 订单从表规则Id
   private String orderDetailRuleId;

   // 订单从表Id
   private String orderDetailId;

   // 规则类型
   private String ruleType;

   // 计算方式
   private String chargeType;

   // 规则值
   private String ruleValue;

   // 描述
   private String description;

   // 规则结果
   private String ruleResult;

   /**
    * For App
    */
   // 科目
   private String itemId;

   // 科目类型
   private List< MappingVO > items = new ArrayList< MappingVO >();

   // 规则类型
   private List< MappingVO > ruleTypes = new ArrayList< MappingVO >();

   // 计算方式
   private List< MappingVO > chargeTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.ruleTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.detail.rule.ruleTypes" );
      this.chargeTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.detail.rule.chargeTypes" );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.orderDetailRuleId );
   }

   public String getEncodedItemId() throws KANException
   {
      return encodedField( this.itemId );
   }

   public String getEncodedOrderDetailId() throws KANException
   {
      return encodedField( this.orderDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderDetailId = "";
      this.ruleType = "";
      this.chargeType = "";
      this.ruleValue = "";
      this.ruleResult = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) object;
      this.orderDetailId = clientOrderDetailRuleVO.getOrderDetailId();
      this.ruleType = clientOrderDetailRuleVO.getRuleType();
      this.chargeType = clientOrderDetailRuleVO.getChargeType();
      this.ruleValue = clientOrderDetailRuleVO.getRuleValue();
      this.ruleResult = clientOrderDetailRuleVO.getRuleResult();
      this.description = clientOrderDetailRuleVO.getDescription();
      super.setStatus( clientOrderDetailRuleVO.getStatus() );
      super.setModifyBy( clientOrderDetailRuleVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   // 解码RuleType
   public String getDecodeRuleType() throws KANException
   {
      return decodeField( this.ruleType, this.ruleTypes );
   }

   // 解码ItemId
   public String getDecodeItemId() throws KANException
   {
      return decodeField( this.itemId, this.items );
   }

   public String getOrderDetailRuleId()
   {
      return orderDetailRuleId;
   }

   public void setOrderDetailRuleId( String orderDetailRuleId )
   {
      this.orderDetailRuleId = orderDetailRuleId;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getRuleType()
   {
      return ruleType;
   }

   public void setRuleType( String ruleType )
   {
      this.ruleType = ruleType;
   }

   public String getChargeType()
   {
      return chargeType;
   }

   public void setChargeType( String chargeType )
   {
      this.chargeType = chargeType;
   }

   public String getRuleValue()
   {
      return KANUtil.filterEmpty( ruleValue );
   }

   public void setRuleValue( String ruleValue )
   {
      this.ruleValue = ruleValue;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getRuleTypes()
   {
      return ruleTypes;
   }

   public void setRuleTypes( List< MappingVO > ruleTypes )
   {
      this.ruleTypes = ruleTypes;
   }

   public List< MappingVO > getChargeTypes()
   {
      return chargeTypes;
   }

   public void setChargeTypes( List< MappingVO > chargeTypes )
   {
      this.chargeTypes = chargeTypes;
   }

   public String getRuleResult()
   {
      return KANUtil.filterEmpty( ruleResult );
   }

   public void setRuleResult( String ruleResult )
   {
      this.ruleResult = ruleResult;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

}
