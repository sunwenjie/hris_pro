package com.kan.base.domain.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin 
 */
public class RightVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -5975546012585976105L;

   /**
    * For DB
    */
   private String rightId;

   private String rightType;

   private String nameZH;

   private String nameEN;
   /**
    * For Application
    */
   private List< MappingVO > rightTypes;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.rightTypes = KANUtil.getMappings( this.getLocale(), "sys.right.types" );
   }

   public String getDecodeRightType()
   {
     return decodeField( rightType, rightTypes );
   }

   @Override
   public void update( final Object object )
   {
      final RightVO rightVO = ( RightVO ) object;
      this.rightType = rightVO.getRightType();
      this.nameZH = rightVO.getNameZH();
      this.nameEN = rightVO.getNameEN();
      super.setStatus( rightVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.rightType = "0";
      this.nameZH = "";
      this.nameEN = "";
      super.setStatus( "0" );
   }

   public String getRightId()
   {
      return rightId;
   }

   public void setRightId( String rightId )
   {
      this.rightId = rightId;
   }

   public String getRightType()
   {
      return rightType;
   }

   public void setRightType( String rightType )
   {
      this.rightType = rightType;
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

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( rightId );
   }

   public List< MappingVO > getRightTypes()
   {
      return rightTypes;
   }

   public void setRightTypes( List< MappingVO > rightTypes )
   {
      this.rightTypes = rightTypes;
   }
}
