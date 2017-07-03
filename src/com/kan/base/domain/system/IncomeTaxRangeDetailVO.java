package com.kan.base.domain.system;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class IncomeTaxRangeDetailVO extends BaseVO
{

   /**  
   * Serial Version UID
   */

   private static final long serialVersionUID = -2703358112693409016L;

   // 税率从表ID
   private String detailId;

   // 税率从表ID
   private String headerId;

   // 收入基数（开始）
   private String rangeFrom;

   // 收入基数（结束）
   private String rangeTo;

   // 税率
   private String percentage;

   // 扣除金额
   private String deduct;

   // 描述
   private String description;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.rangeFrom = "";
      this.rangeTo = "";
      this.percentage = "";
      this.deduct = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) object;
      this.headerId = incomeTaxRangeDetailVO.getHeaderId();
      this.rangeFrom = incomeTaxRangeDetailVO.getRangeFrom();
      this.rangeTo = incomeTaxRangeDetailVO.getRangeTo();
      this.percentage = incomeTaxRangeDetailVO.getPercentage();
      this.deduct = incomeTaxRangeDetailVO.getDeduct();
      super.setStatus( incomeTaxRangeDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getRangeFrom()
   {
      return KANUtil.filterEmpty( rangeFrom );
   }

   public void setRangeFrom( String rangeFrom )
   {
      this.rangeFrom = rangeFrom;
   }

   public String getRangeTo()
   {
      return KANUtil.filterEmpty( rangeTo );
   }

   public void setRangeTo( String rangeTo )
   {
      this.rangeTo = rangeTo;
   }

   public String getPercentage()
   {
      return KANUtil.filterEmpty( percentage );
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getDeduct()
   {
      return KANUtil.filterEmpty( deduct );
   }

   public void setDeduct( String deduct )
   {
      this.deduct = deduct;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

}
