package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class IndustryTypeVO extends BaseVO
{

   /**  
   * serialVersionUID
   */

   private static final long serialVersionUID = -6129880016299778162L;
   
   // ��ҵ���Id
   private String typeId;
   
   // ��ҵ���ƣ����ģ�
   private String nameZH;
   
   // ��ҵ���ƣ�Ӣ�ģ�
   private String nameEN;
   
   // ������Ϣ
   private String description;

   @Override
   public void update( final Object object )
   {
      final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) object;
      this.nameZH = industryTypeVO.getNameZH();
      this.nameEN = industryTypeVO.getNameEN();
      this.description = industryTypeVO.getDescription();
      super.setStatus( industryTypeVO.getStatus() );
      super.setModifyBy( industryTypeVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameEN = "";
      this.nameZH = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( typeId == null || typeId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( typeId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getTypeId()
   {
      return typeId;
   }

   public void setTypeId( String typeId )
   {
      this.typeId = typeId;
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

}
