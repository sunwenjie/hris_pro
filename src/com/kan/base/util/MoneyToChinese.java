package com.kan.base.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// �Ѱ��������ֽ��ת����������ʽ
public class MoneyToChinese implements Serializable
{
   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 7861464153481147914L;

   private static String[] digit = { "", "ʰ", "��", "Ǫ", "��", "ʰ��", "����", "Ǫ��", "��", "ʰ��", "����", "Ǫ��", "����" };

   private static final String FEN = "��";

   private static final String JIAO = "��";

   private static final String YUAN = "Բ";

   private static final String ZHENG = "��";

   // �Ѱ��������ֽ��ת����������ʽ
   public static void main( String[] args )
   {
      System.out.println( getMoneyString( 18.27 ) );
   }

   // ȡ�����ֶ�Ӧ������
   public static String getMoneyString( double money )
   {
      // ���ַ���תΪΪBigDecimal��ʽ 
      BigDecimal b = new BigDecimal( String.valueOf( money ) );
      // ���þ���Ϊ2��С�����2λ 
      String strMoney = "" + b.setScale( 2, BigDecimal.ROUND_UNNECESSARY );
      // ��С�����Ϊ ���� �� С�� ������ 
      String[] amt = strMoney.split( "\\." );
      // ���ú�����ȡ Ԫ �� С�� ���ֵ��ַ��� 
      strMoney = getYuan( amt[ 0 ] ) + YUAN + getJIAOFEN( amt[ 1 ] );
      // �������յõ����ַ��� 
      return strMoney;
   }

   // �õ�Ԫ�Ĳ���
   public static String getYuan( String s )
   {
      char[] c = s.toCharArray();
      StringBuffer chSb = new StringBuffer();
      int len = c.length;
      List< String > list = new ArrayList< String >();
      String d = "";
      for ( int i = 0; i < c.length; i++ )
      {
         // ����м���0����һ��ʱ, ֻ��ʾһ���㼴�� 
         if ( i > 0 && c[ i ] == '0' && c[ i ] == c[ i - 1 ] )
         {
            --len;
            continue;
         }
         // �õ����ֶ�Ӧ������ 
         chSb.append( getChinese( c[ i ] ) );

         // ����ʱ, ��ʾ�Ǽ���, ���Ǽ�Ǫ 
         if ( !getChinese( c[ i ] ).equals( "��" ) )
         {
            d = digit[ --len ];
            list.add( d );// �������������ʮ��ʱ, Ҫȥ��ʮ�� 
            chSb.append( d );
         }
         else
         {
            --len; // �����0��ȡλ�� 
         }
      }
      String chStr = chSb.toString();
      // ���ͬʱ���������ʮ��, ��ʮ���е���ȥ�� 
      if ( list.contains( "��" ) && list.contains( "ʰ��" ) )
      {
         chStr = chStr.replaceAll( "ʰ��", "ʰ" );
      }
      if ( list.contains( "��" ) && list.contains( "����" ) )
      {
         chStr = chStr.replaceAll( "����", "��" );
      }
      if ( list.contains( "��" ) && list.contains( "Ǫ��" ) )
      {
         chStr = chStr.replaceAll( "Ǫ��", "Ǫ" );
      }
      // ���ͬʱ�����ں�ʮ��, ��ʮ���е�����ȥ�� 
      if ( list.contains( "��" ) && list.contains( "ʰ��" ) )
      {
         chStr = chStr.replaceAll( "ʰ��", "ʰ" );
      }
      if ( list.contains( "��" ) && list.contains( "����" ) )
      {
         chStr = chStr.replaceAll( "����", "��" );
      }
      if ( list.contains( "��" ) && list.contains( "Ǫ��" ) )
      {
         chStr = chStr.replaceAll( "Ǫ��", "Ǫ" );
      }
      if ( list.contains( "��" ) && list.contains( "����" ) )
      {
         chStr = chStr.replaceAll( "����", "��" );
      }
      // ������һλ�� 0, ��ȥ�� 
      if ( ( chSb.charAt( chSb.length() - 1 ) ) == '��' )
      {
         chStr = chStr.substring( 0, chStr.length() - 1 );
      }
      return chStr;
   }

   // �ֽ�ת��Ϊ�ַ���  
   private static String getJIAOFEN( String FENJIAO )
   {
      // �ֽ��ַ���תΪΪ�ַ����� 
      char[] ch = FENJIAO.toCharArray();
      // ������Ϊ0��Ϊ1��Ϊ2 �����֡� 
      if ( ch.length == 0 )
      {
         return ZHENG;
      }
      else if ( ch.length == 1 )
      {
         if ( ch[ 0 ] == '0' )
         {
            return ZHENG;
         }
         else
         {
            return getChinese( ch[ 0 ] ) + JIAO;
         }
      }
      else
      {
         if ( ch[ 0 ] == '0' && ch[ 1 ] == '0' )
         {
            return ZHENG;
         }
         else if ( ch[ 0 ] == '0' && ch[ 1 ] != '0' )
         {
            return getChinese( ch[ 0 ] ) + getChinese( ch[ 1 ] ) + FEN;
         }
         else if ( ch[ 0 ] != '0' && ch[ 1 ] == '0' )
         {
            return getChinese( ch[ 0 ] ) + JIAO;
         }
         else
         {
            return getChinese( ch[ 0 ] ) + JIAO + getChinese( ch[ 1 ] ) + FEN;
         }
      }
   }

   // ȡ�����ֶ�Ӧ������
   private static String getChinese( char i )
   {
      String ch = "";
      switch ( i )
      {
         case '0':
            ch = "��";
            break;
         case '1':
            ch = "Ҽ";
            break;
         case '2':
            ch = "��";
            break;
         case '3':
            ch = "��";
            break;
         case '4':
            ch = "��";
            break;
         case '5':
            ch = "��";
            break;
         case '6':
            ch = "½";
            break;
         case '7':
            ch = "��";
            break;
         case '8':
            ch = "��";
            break;
         case '9':
            ch = "��";
            break;
      }
      return ch;
   }

}
