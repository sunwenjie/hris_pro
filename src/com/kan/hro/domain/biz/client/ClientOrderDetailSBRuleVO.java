package com.kan.hro.domain.biz.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientOrderDetailSBRuleVO extends BaseVO
{

   // SerialVersionUID  
   private static final long serialVersionUID = 6780491424370973345L;

   private String sbRuleId;

   private String orderDetailId;

   private String sbSolutionId;

   private String sbRuleType;

   private String amount;

   private String description;

   // for app

   private List< MappingVO > sbRuleTypes = new ArrayList< MappingVO >();

   private List< MappingVO > sbSolutionIds = new ArrayList< MappingVO >();

   public String getSbRuleId()
   {
      return sbRuleId;
   }

   public void setSbRuleId( String sbRuleId )
   {
      this.sbRuleId = sbRuleId;
   }

   public String getOrderDetailId()
   {
      return orderDetailId;
   }

   public void setOrderDetailId( String orderDetailId )
   {
      this.orderDetailId = orderDetailId;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String getSbRuleType()
   {
      return sbRuleType;
   }

   public void setSbRuleType( String sbRuleType )
   {
      this.sbRuleType = sbRuleType;
   }

   public String getAmount()
   {
      return amount;
   }

   public void setAmount( String amount )
   {
      this.amount = amount;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public void reset( ActionMapping mapping, ServletRequest request )
   {
      super.reset( mapping, request );
      this.sbRuleTypes = KANUtil.getMappings( getLocale(), "business.client.order.detail.sb.rule" );
      this.sbSolutionIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( sbRuleId );
   }

   @Override
   public void reset() throws KANException
   {
      this.orderDetailId = "";
      this.sbSolutionId = "";
      this.sbRuleType = "0";
      this.amount = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ClientOrderDetailSBRuleVO clientOrderDetailOverduePayVO = ( ClientOrderDetailSBRuleVO ) object;
      this.orderDetailId = clientOrderDetailOverduePayVO.getOrderDetailId();
      this.sbSolutionId = clientOrderDetailOverduePayVO.getSbSolutionId();
      this.sbRuleType = clientOrderDetailOverduePayVO.getSbRuleType();
      this.amount = clientOrderDetailOverduePayVO.getAmount();
      this.description = clientOrderDetailOverduePayVO.getDescription();
      super.setStatus( clientOrderDetailOverduePayVO.getStatus() );
      super.setModifyBy( clientOrderDetailOverduePayVO.getModifyBy() );
   }

   public String getDecodeSBRuleType()
   {
      return decodeField( this.sbRuleType, sbRuleTypes );
   }

   public String getEncodedOrderDetailId() throws KANException
   {
      return encodedField( this.orderDetailId );
   }

   public String getDecodeSBSolutionId()
   {
      return decodeField( this.sbSolutionId, sbSolutionIds );
   }

   public List< MappingVO > getSbRuleTypes()
   {
      return sbRuleTypes;
   }

   public void setSbRuleTypes( List< MappingVO > sbRuleTypes )
   {
      this.sbRuleTypes = sbRuleTypes;
   }

   public List< MappingVO > getSbSolutionIds()
   {
      return sbSolutionIds;
   }

   public void setSbSolutionIds( List< MappingVO > sbSolutionIds )
   {
      this.sbSolutionIds = sbSolutionIds;
   }
   
}
