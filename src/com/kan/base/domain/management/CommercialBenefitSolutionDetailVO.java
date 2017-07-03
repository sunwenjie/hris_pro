package com.kan.base.domain.management;

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

public class CommercialBenefitSolutionDetailVO extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = 6774331126533615560L;

   /**
    * For DB
    */
   // 商保方案从表ID，一个商保方案可以有多个商保科目
   private String detailId;

   // 商保方案主表ID
   private String headerId;

   // 科目ID
   private String itemId;

   // 采购成本
   private String purchaseCost;

   // 销售成本
   private String salesCost;

   // 销售价格
   private String salesPrice;

   // 付费方式
   private String calculateType;

   // Add Column 保留小数位
   private String accuracy;

   // Add Column 小数位保留方式
   private String round;

   // 描述
   private String description;

   /**
    * For Application
    */

   // 科目编号
   private String itemNo;

   // 商保科目
   private List< MappingVO > cbItems = new ArrayList< MappingVO >();

   // 费用方式
   private List< MappingVO > calculateTypies = new ArrayList< MappingVO >();

   // 精度
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // 取值范围
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.cbItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getCbItems( request.getLocale().getLanguage(), super.getCorpId() );
      if ( cbItems != null )
      {
         cbItems.add( 0, getEmptyMappingVO() );
      }
      this.calculateTypies = KANUtil.getMappings( request.getLocale(), "cb.calculate.type" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
   }

   @Override
   public void reset() throws KANException
   {
      this.itemId = "0";
      this.purchaseCost = "";
      this.salesCost = "";
      this.salesPrice = "";
      this.calculateType = "0";
      this.accuracy = "0";
      this.round = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = ( CommercialBenefitSolutionDetailVO ) object;
      this.itemId = commercialBenefitSolutionDetailVO.getItemId();
      this.purchaseCost = commercialBenefitSolutionDetailVO.getPurchaseCost();
      this.salesCost = commercialBenefitSolutionDetailVO.getSalesCost();
      this.salesPrice = commercialBenefitSolutionDetailVO.getSalesPrice();
      this.calculateType = commercialBenefitSolutionDetailVO.getCalculateType();
      this.accuracy = commercialBenefitSolutionDetailVO.getAccuracy();
      this.round = commercialBenefitSolutionDetailVO.getRound();
      this.description = commercialBenefitSolutionDetailVO.getDescription();
      super.setStatus( commercialBenefitSolutionDetailVO.getStatus() );
      super.setModifyDate( new Date() );

   }

   public String getDecodeItem()
   {
      return decodeField( itemId, cbItems );
   }

   public String getDecodeAccuracy()
   {
      return decodeField( accuracy, accuracys );
   }

   public String getDecodeAccuracyTemp()
   {
      return decodeField( accuracy, accuracys, true );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getPurchaseCost()
   {
      return KANUtil.formatNumber( purchaseCost, getAccountId() );
   }

   public void setPurchaseCost( String purchaseCost )
   {
      this.purchaseCost = purchaseCost;
   }

   public String getSalesCost()
   {
      return KANUtil.formatNumber( salesCost, getAccountId() );
   }

   public void setSalesCost( String salesCost )
   {
      this.salesCost = salesCost;
   }

   public String getSalesPrice()
   {
      return KANUtil.formatNumber( salesPrice, getAccountId() );
   }

   public void setSalesPrice( String salesPrice )
   {
      this.salesPrice = salesPrice;
   }

   public String getCalculateType()
   {
      return calculateType;
   }

   public void setCalculateType( String calculateType )
   {
      this.calculateType = calculateType;
   }

   public void setCalculateTypies( List< MappingVO > calculateTypies )
   {
      this.calculateTypies = calculateTypies;
   }

   public String getAccuracy()
   {
      return accuracy;
   }

   public void setAccuracy( String accuracy )
   {
      this.accuracy = accuracy;
   }

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
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
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   public List< MappingVO > getCbItems()
   {
      return cbItems;
   }

   public void setCbItems( List< MappingVO > cbItems )
   {
      this.cbItems = cbItems;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public List< MappingVO > getCalculateTypies()
   {
      return calculateTypies;
   }

   public List< MappingVO > getAccuracys()
   {
      return accuracys;
   }

   public void setAccuracys( List< MappingVO > accuracys )
   {
      this.accuracys = accuracys;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   public String getDecodeCalculateType()
   {
      return decodeField( this.calculateType, this.calculateTypies );
   }

}
