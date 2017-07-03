package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class EmployeeStatusVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4247190238552745358L;

   /**
    * For DB
    */
   private String employeeStatusId;

   private String nameZH;

   private String nameEN;
   
   private String defaults;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.defaults = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) object;
      this.nameZH = employeeStatusVO.getNameZH();
      this.nameEN = employeeStatusVO.getNameEN();
      this.defaults = employeeStatusVO.getSetDefault();
      this.description = employeeStatusVO.getDescription();
      super.setStatus( employeeStatusVO.getStatus() );
      super.setModifyBy( employeeStatusVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeStatusId()
   {
      return employeeStatusId;
   }

   public void setEmployeeStatusId( String employeeStatusId )
   {
      this.employeeStatusId = employeeStatusId;
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

   public String getSetDefault()
   {
      return defaults;
   }

   public void setSetDefault( String setDefault )
   {
      this.defaults = setDefault;
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
      if ( employeeStatusId == null || employeeStatusId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( employeeStatusId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
