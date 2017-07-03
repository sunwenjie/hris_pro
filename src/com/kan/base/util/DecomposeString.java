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
    * �����ַ������������
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
    * getStrArrProperty ��������е����� ��˳�� 
    *                   ����˵��
    *                   index == 1 ��ȡ����Id
    *                   index == 2 ��ȡ�������ݵ�Id
    *                   index == 3 ��ȡ�����
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
