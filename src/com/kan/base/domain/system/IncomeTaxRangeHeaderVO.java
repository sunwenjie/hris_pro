package com.kan.base.domain.system;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class IncomeTaxRangeHeaderVO extends BaseVO
{

   /**  
   * Serial Version UID
   */

   private static final long serialVersionUID = -2703358112693409016L;

   // 税率主表ID
   private String headerId;

   // 名称（中文）
   private String nameZH;

   // 名称（英文）
   private String nameEN;

   // 生效日期
   private String startDate;

   // 失效日期
   private String endDate;

   // 默认的
   private String isDefault;

   // 描述
   private String description;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.isDefault = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) object;
      this.nameZH = incomeTaxRangeHeaderVO.getNameZH();
      this.nameEN = incomeTaxRangeHeaderVO.getNameEN();
      this.startDate = incomeTaxRangeHeaderVO.getStartDate();
      this.endDate = incomeTaxRangeHeaderVO.getEndDate();
      this.isDefault = incomeTaxRangeHeaderVO.getIsDefault();
      super.setStatus( incomeTaxRangeHeaderVO.getStatus() );
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

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getIsDefault()
   {
      return isDefault;
   }

   public void setIsDefault( String isDefault )
   {
      this.isDefault = isDefault;
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
