package com.kan.base.domain.define;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class OptionDetailVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 5924411329979984916L;

   /**
    * For DB
    */
   private String optionDetailId;

   private String optionHeaderId;

   private String optionId;

   private String optionIndex = "1";

   private String optionNameZH;

   private String optionNameEN;

   private String optionValue;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.optionHeaderId = "";
      this.optionId = "";
      this.optionIndex = "";
      this.optionNameZH = "";
      this.optionNameEN = "";
      this.optionValue = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final OptionDetailVO optionDetailVO = ( OptionDetailVO ) object;
      this.optionId = optionDetailVO.getOptionId();
      this.optionIndex = optionDetailVO.getOptionIndex();
      this.optionNameZH = optionDetailVO.getOptionNameZH();
      this.optionNameEN = optionDetailVO.getOptionNameEN();
      this.optionValue = optionDetailVO.getOptionValue();
      this.description = optionDetailVO.getDescription();
      super.setStatus( optionDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getOptionDetailId()
   {
      return optionDetailId;
   }

   public void setOptionDetailId( String optionDetailId )
   {
      this.optionDetailId = optionDetailId;
   }

   public String getOptionHeaderId()
   {
      return optionHeaderId;
   }

   public void setOptionHeaderId( String optionHeaderId )
   {
      this.optionHeaderId = optionHeaderId;
   }

   public String getOptionId()
   {
      return optionId;
   }

   public void setOptionId( String optionId )
   {
      this.optionId = optionId;
   }

   public String getOptionIndex()
   {
      return optionIndex == null || optionIndex.trim().equals( "" ) ? "1" : optionIndex;
   }

   public void setOptionIndex( String optionIndex )
   {
      this.optionIndex = optionIndex;
   }

   public String getOptionNameZH()
   {
      return optionNameZH;
   }

   public void setOptionNameZH( String optionNameZH )
   {
      this.optionNameZH = optionNameZH;
   }

   public String getOptionNameEN()
   {
      return optionNameEN;
   }

   public void setOptionNameEN( String optionNameEN )
   {
      this.optionNameEN = optionNameEN;
   }

   public String getOptionValue()
   {
      return optionValue;
   }

   public void setOptionValue( String optionValue )
   {
      this.optionValue = optionValue;
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
      return encodedField( optionDetailId );
   }

}
