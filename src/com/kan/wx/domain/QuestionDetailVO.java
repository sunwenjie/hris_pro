package com.kan.wx.domain;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class QuestionDetailVO extends BaseVO
{

   /**
    * SerialVersionUID
    */
   private static final long serialVersionUID = 1747536996607872728L;

   // For DB
   private String detailId;
   private String headerId;
   private String optionIndex;
   private String nameZH;
   private String nameEN;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.optionIndex = "";
      this.nameZH = "";
      this.nameEN = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) object;
      this.optionIndex = questionDetailVO.getOptionIndex();
      this.nameZH = questionDetailVO.getNameZH();
      this.nameEN = questionDetailVO.getNameEN();
      super.setStatus( questionDetailVO.getStatus() );
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

   public String getOptionIndex()
   {
      return optionIndex;
   }

   public void setOptionIndex( String optionIndex )
   {
      this.optionIndex = optionIndex;
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

}
