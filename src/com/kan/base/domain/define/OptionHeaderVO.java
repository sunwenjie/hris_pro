package com.kan.base.domain.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class OptionHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 2078516588406362270L;

   /**
    * For DB
    */
   private String optionHeaderId;

   private String nameZH;

   private String nameEN;

   private String description;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void reset()
   {
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setCorpId( "" );
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object )
   {
      final OptionHeaderVO optionHeaderVO = ( OptionHeaderVO ) object;
      this.nameZH = optionHeaderVO.getNameZH();
      this.nameEN = optionHeaderVO.getNameEN();
      this.description = optionHeaderVO.getDescription();
      super.setStatus( optionHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( optionHeaderVO.getCorpId() );
   }

   public String getOptionHeaderId()
   {
      return optionHeaderId;
   }

   public void setOptionHeaderId( String optionHeaderId )
   {
      this.optionHeaderId = optionHeaderId;
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
      return encodedField( optionHeaderId );
   }

}
