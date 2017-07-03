package com.kan.base.util;

import java.util.List;
import com.shcm.bean.BalanceResultBean;
import com.shcm.bean.ReplyBean;
import com.shcm.bean.SendResultBean;
import com.shcm.bean.SendStateBean;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;

/**
 * @author Chinafish
 *
 */
public class Sampler_KAN
{
   private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
   private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";

   // �ӿ��ʺ�
   private static final String account = "1001@500948720001";

   // �ӿ���Կ
   private static final String authkey = "E4ABFF26E36CCB44BF423FF1A35ADEEE";

   // ͨ������
   private static final int cgid = 2275;

   // Ĭ��ʹ�õ�ǩ�����(δָ��ǩ�����ʱ����ֵ��������)
   private static final int csid = 0;

   public static List< SendResultBean > sendOnce( String mobile, String content ) throws Exception
   {
      // ���Ͷ���
      return OpenApi.sendOnce( mobile, content, 0, 0, null );
      //return OpenApi.sendOnce(new String[]{"18297974783","15102110086"}, "��������", 0, 0, null);
      //return OpenApi.sendBatch("18297974783,15102110086", "��������{|}��������", 0, 0, null);
      //return OpenApi.sendBatch(new String[]{"18297974783","15102110086"}, new String[]{"��������","��������"}, 0, 0, null);
      //return OpenApi.sendParam("18297974783,15102110086", "��������{p1}", new String[]{"a{|}b"}, 0, 0, null);
      //return OpenApi.sendParam(new String[]{"18297974783","15102110086"}, "��������{p1}", new String[]{"a{|}b"}, 0, 0, null);
   }

   public static void main( String[] args ) throws Exception
   {
      // ���Ͳ���
      OpenApi.initialzeAccount( sOpenUrl, account, authkey, cgid, csid );

      // ״̬���ظ�����
      DataApi.initialzeAccount( sDataUrl, account, authkey );

      // ȡ�ʻ����
      BalanceResultBean br = OpenApi.getBalance();
      if ( br.getResult() < 1 )
      {
         System.out.println( "��ȡ�������ʧ��: " + br.getErrMsg() );
         return;
      }
      System.out.println( "��������: " + br.getRemain() );

      // ���½ӿ���Կ
      //String sAuthKey = OpenApi.updateKey();
      //if(!sAuthKey.isEmpty())
      //{
      //	System.out.println("�ѳɹ�������Կ,�½ӿ���ԿΪ: " + sAuthKey);
      //}

      List< SendResultBean > listItem = sendOnce( "18930534082", "����ã�Good afternoon!" );
      if ( listItem != null )
      {
         for ( SendResultBean t : listItem )
         {
            if ( t.getResult() < 1 )
            {
               System.out.println( "�����ύʧ��: " + t.getErrMsg() );
               return;
            }

            System.out.println( "���ͳɹ�: ��Ϣ���<" + t.getMsgId() + "> ����<" + t.getTotal() + "> ���<" + t.getRemain() + ">" );
         }
      }

      // ѭ����ȡ״̬����ͻظ�
      while ( true )
      {
         List< SendStateBean > listSendState = DataApi.getSendState();
         if ( listSendState != null )
         {
            for ( SendStateBean t : listSendState )
            {
               System.out.println( "״̬���� => ���<" + t.getId() + "> ��Ϣ���<" + t.getMsgId() + "> �ֻ�����<" + t.getMobile() + "> ���<"
                     + ( t.getStatus() > 1 ? "���ͳɹ�" : t.getStatus() > 0 ? "����ʧ��" : "δ֪״̬" ) + "> ��Ӫ�̷���<" + t.getDetail() + ">" );
            }
         }

         // ȡ�ظ�
         List< ReplyBean > listReply = DataApi.getReply();
         if ( listReply != null )
         {
            for ( ReplyBean t : listReply )
            {
               System.out.println( "�ظ���Ϣ => ���<" + t.getId() + "> �ظ�ʱ��<" + t.getReplyTime() + "> �ֻ�����<" + t.getMobile() + "> �ظ�����<" + t.getContent() + ">" );
            }
         }
         Thread.sleep( 6000 );
      }
   }
}
