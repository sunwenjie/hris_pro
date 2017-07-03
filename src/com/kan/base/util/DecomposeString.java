package com.kan.base.util;

import java.util.List;

import com.kan.base.domain.MappingVO;

public class DecomposeString
{

   private List< MappingVO > serviceContents;

   public List< MappingVO > getServiceContents()
   {
      return serviceContents;
   }

   public void setServiceContents( List< MappingVO > serviceContents )
   {
      this.serviceContents = serviceContents;
   }

   /**
    * 
    * 1_1,2,3_50;2_1,2,3_10;
    * 解析字符串，获得数组
    */
   public static String[] getStrArray( final String resourceStr )
   {
      String arr[] = null;
      if ( resourceStr != null )
      {
         int i = resourceStr.split( ";" ).length;
         arr = new String[ i * 3 ];
         int j = 0;
         for ( String str : resourceStr.split( ";" ) )
         {
            for ( String str_ : str.split( "_" ) )
            {
               arr[ j ] = str_;
               j++;
            }
         }

      }
      return arr;
   }

   /**
    * getStrArrProperty 获得数组中的属性 按顺序 
    *                   参数说明
    *                   index == 1 获取城市Id
    *                   index == 2 获取服务内容的Id
    *                   index == 3 获取服务费
    * @param parameter
    * @param index
    * @return
    */
   public static String[] getStrArrProperty( final String parameter[], int index )
   {
      if ( index == 3 )
      {
         index = 0;
      }
      int i = parameter.length / 3;
      String str[] = new String[ i ];
      int k = 0;
      for ( int j = 1; j <= parameter.length; j++ )
      {

         if ( j % 3 == index || j == index )
         {
            str[ k ] = parameter[ j - 1 ];
            k++;
         }
      }

      return str;
   }

}
