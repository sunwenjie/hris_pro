package com.kan.base.domain.define;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/**
 * @author Siuvan
 *
 */
public class ColumnGroupVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7833037409014028366L;

   /**
    * For DB
    */
   private String groupId;

   private String nameZH;

   private String nameEN;

   private String useName;

   private String useBorder;

   private String usePadding;

   private String useMargin;

   private String isDisplayed;

   private String isFlexable;

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
      this.useName = "";
      this.useBorder = "";
      this.usePadding = "";
      this.useMargin = "";
      this.isDisplayed = "";
      this.isFlexable = "";
      this.description = "";
      super.setStatus( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object )
   {
      final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) object;
      this.nameZH = columnGroupVO.getNameZH();
      this.nameEN = columnGroupVO.getNameEN();
      this.useName = columnGroupVO.getUseName();
      this.useBorder = columnGroupVO.getUseBorder();
      this.usePadding = columnGroupVO.getUsePadding();
      this.useMargin = columnGroupVO.getUseMargin();
      this.isDisplayed = columnGroupVO.getIsDisplayed();
      this.isFlexable = columnGroupVO.getIsFlexable();
      this.description = columnGroupVO.getDescription();
      super.setStatus( columnGroupVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( columnGroupVO.getCorpId() );
   }

   public String getDecodeUseName()
   {
      return decodeField( useName, super.getFlags() );
   }

   public String getDecodeUsePadding()
   {
      return decodeField( usePadding, super.getFlags() );
   }

   public String getDecodeUseMargin()
   {
      return decodeField( useMargin, super.getFlags() );
   }

   public String getDecodeIsDisplayed()
   {
      return decodeField( isDisplayed, super.getFlags() );
   }

   public String getDecodeIsFlexable()
   {
      return decodeField( isFlexable, super.getFlags() );
   }

   public String getDecodeUseBorder()
   {
      return decodeField( useBorder, super.getFlags() );
   }

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
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

   public String getUseName()
   {
      return useName;
   }

   public void setUseName( String useName )
   {
      this.useName = useName;
   }

   public String getUseBorder()
   {
      return useBorder;
   }

   public void setUseBorder( String useBorder )
   {
      this.useBorder = useBorder;
   }

   public String getUsePadding()
   {
      return usePadding;
   }

   public void setUsePadding( String usePadding )
   {
      this.usePadding = usePadding;
   }

   public String getUseMargin()
   {
      return useMargin;
   }

   public void setUseMargin( String useMargin )
   {
      this.useMargin = useMargin;
   }

   public String getIsDisplayed()
   {
      return isDisplayed;
   }

   public void setIsDisplayed( String isDisplayed )
   {
      this.isDisplayed = isDisplayed;
   }

   public String getIsFlexable()
   {
      return isFlexable;
   }

   public void setIsFlexable( String isFlexable )
   {
      this.isFlexable = isFlexable;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEncodedId() throws KANException
   {
      return encodedField( groupId );
   }

}
