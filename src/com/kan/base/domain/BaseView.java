package com.kan.base.domain;

import java.io.Serializable;
import java.net.URLEncoder;

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：BaseView  
* 类描述：  联想功能用BaseView
* 创建人：Jack  
* 创建时间：2013-7-12 下午01:18:32  
* 修改人：Jack  
* 修改时间：2013-7-12 下午01:18:32  
* 修改备注：  
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
