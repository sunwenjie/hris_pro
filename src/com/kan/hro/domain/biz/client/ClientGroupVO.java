package com.kan.hro.domain.biz.client;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ClientGroupVO extends BaseVO
{

   /**  
    * serialVersionUID:（客户集团）  
    *  
    * @since Ver 1.1  
    */

   private static final long serialVersionUID = -6745591873521806449L;

   // 集团ID
   private String clientGroupId;

   // 集团编号
   private String number;

   // 集团中文名
   private String nameZH;

   // 集团英文名
   private String nameEN;

   // 描述信息
   private String description;
   /**
     * (For App)  
     * @see com.kan.base.domain.BaseVO#getEncodedId()
     */
   // 集团包含客户ID
   private String[] clientIdArray;

   @Override
   public String getEncodedId() throws KANException
   {
      if ( clientGroupId == null || clientGroupId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( clientGroupId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void update( Object object )
   {
      final ClientGroupVO clientGroupVO = ( ClientGroupVO ) object;
      this.nameZH = clientGroupVO.getNameZH();
      this.nameEN = clientGroupVO.getNameEN();
      this.number = clientGroupVO.getNumber();
      this.clientIdArray = clientGroupVO.getClientIdArray();
      this.description = clientGroupVO.getDescription();
      super.setStatus( clientGroupVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.number = "";
      this.description = "";
      this.clientIdArray = null;
      super.setStatus( "0" );
   }

   public String getClientGroupId()
   {
      return clientGroupId;
   }

   public void setClientGroupId( String clientGroupId )
   {
      this.clientGroupId = clientGroupId;
   }

   public String getNumber()
   {
      return number;
   }

   public void setNumber( String number )
   {
      this.number = number;
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

   public String[] getClientIdArray()
   {
      return clientIdArray;
   }

   public void setClientIdArray( String[] clientIdArray )
   {
      this.clientIdArray = clientIdArray;
   }
}
