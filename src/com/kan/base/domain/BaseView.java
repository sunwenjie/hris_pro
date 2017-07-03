package com.kan.base.domain;

import java.io.Serializable;
import java.net.URLEncoder;

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�BaseView  
* ��������  ���빦����BaseView
* �����ˣ�Jack  
* ����ʱ�䣺2013-7-12 ����01:18:32  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-7-12 ����01:18:32  
* �޸ı�ע��  
* @version   
*   
*/
public abstract class BaseView implements Serializable
{
   /**  
   * serialVersionUID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = 3480226222362656266L;

   private String id;

   private String name;

   public BaseView()
   {

   }

   public BaseView( final String id, final String name )
   {
      this.id = id;
      this.name = name;
   }

   public String getEncodedId() throws KANException
   {
      if ( id == null || id.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( id ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

}
