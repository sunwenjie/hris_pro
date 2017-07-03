package com.kan.hro.domain.biz.client;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderHeaderRuleVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = -4207849522716642070L;

   // 订单主表规则Id，主键（规则的原则是：设置后费用下降）
   private String orderHeaderRuleId;

   // 订单主表Id
   private String orderHeaderId;

   // 规则类型（1:订单人数 - 大于，按比例打折，2:订单金额 - 大于，按比例打折） 
   private String ruleType;

   // 规则值（百分比）
   private String ruleValue;
   
   // 规则结果（百分比）
   private String ruleResult;

   // For App
   private List< MappingVO > ruleTypes;

   // 订单主键名称
   private String orderHeaderName;

   // 描述
   private String description;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.ruleTypes = KANUtil.getMappings( this.getLocale(), "business.client.order.header.rule.ruleTypes" );
   }
   
   public String getEncodedOrderHeaderId() throws KANException
   {
      if ( orderHeaderId == null || orderHeaderId.trim().equals( "" ) )
      {
         return "";
      }
      
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( orderHeaderId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( orderHeaderRuleId == null || orderHeaderRuleId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( orderHeaderRuleId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 获得订单规则
   public String getDecodeRuleType() throws KANException
   {
      if ( this.ruleTypes != null )
      {
         for ( MappingVO mappingVO : this.ruleTypes )
         {
            if ( mappingVO.getMappingId().equals( this.getRuleType() ) && ( !"0".equals( mappingVO.getMappingId() ) ) && ( !"".equals( mappingVO.getMappingId() ) ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   @Override
   public void reset() throws KANException
   {
      this.orderHeaderId = "";
      this.ruleType = "0";
      this.ruleValue = "";
      this.ruleResult = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) object;
      this.orderHeaderId = clientOrderHeaderRuleVO.getOrderHeaderId();
      this.ruleType = clientOrderHeaderRuleVO.getRuleType();
      this.ruleValue = clientOrderHeaderRuleVO.getRuleValue();
      this.ruleResult = clientOrderHeaderRuleVO.getRuleResult();
      this.description = clientOrderHeaderRuleVO.getDescription();
      super.setStatus( clientOrderHeaderRuleVO.getStatus() );
      super.setModifyBy( clientOrderHeaderRuleVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getOrderHeaderRuleId()
   {
      return orderHeaderRuleId;
   }

   public void setOrderHeaderRuleId( String orderHeaderRuleId )
   {
      this.orderHeaderRuleId = orderHeaderRuleId;
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
   }

   public String getRuleType()
   {
      return ruleType;
   }

   public void setRuleType( String ruleType )
   {
      this.ruleType = ruleType;
   }

   public String getRuleValue()
   {
      return ruleValue;
   }

   public void setRuleValue( String ruleValue )
   {
      this.ruleValue = ( ruleValue == null || ruleValue.trim().isEmpty() ) ? "0" : ruleValue;
   }

   public List< MappingVO > getRuleTypes()
   {
      return ruleTypes;
   }

   public void setRuleTypes( List< MappingVO > ruleTypes )
   {
      this.ruleTypes = ruleTypes;
   }

   public String getOrderHeaderName()
   {
      return orderHeaderName;
   }

   public void setOrderHeaderName( String orderHeaderName )
   {
      this.orderHeaderName = orderHeaderName;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getRuleResult()
   {
      return ruleResult;
   }

   public void setRuleResult( String ruleResult )
   {
      this.ruleResult = ( ruleResult == null || ruleResult.trim().isEmpty() ) ? "0" : ruleResult;
   }

}
