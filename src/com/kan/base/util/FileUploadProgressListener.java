package com.kan.base.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

import com.kan.base.domain.FileUploadVO;
import com.kan.base.web.action.BaseAction;

/**
 * �ϴ�������
 */
public class FileUploadProgressListener implements ProgressListener
{
   // ����HttpServletRequest����
   private HttpServletRequest request;

   // �ļ��ϴ��������
   private String postfixRandom;

   // ��������
   private String sessionName;

   // ���캯��
   // Reviewed by Kevin Jin at 2014-02-17
   public FileUploadProgressListener( final HttpServletRequest request ) throws KANException
   {
      this.request = request;
      this.postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // ��ʼ��Session Name String
      sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      final FileUploadVO upladeStatus = new FileUploadVO();
      UploadMessageQueueUtil.offer( request, sessionName, upladeStatus );
   }

   /**
    * Ϊ�˽���������������������������Ľ���������Ǽ��ٽ������Ļ�������磬ֻ�е��ϴ���1���ֽڵ�ʱ��ŷ������û�
    */
   // Reviewed by Kevin Jin at 2014-02-17
   public void update( long bytesRead, long contentLength, int items )
   {
      try
      {
         final FileUploadVO status = ( FileUploadVO ) BaseAction.getObjectFromSession( request, sessionName );

         if ( contentLength == -1 )
         {
            status.setStatusMsg( "�����" + items + "���ļ����ϴ�..." );
         }
         else
         {
            status.setStatusMsg( "�����ϴ���" + items + "���ļ�..." );
         }

         //status.setInfo( "0" );
         status.setReadedBytes( bytesRead );
         status.setTotalBytes( contentLength );
         status.setCurrentItem( items );

         UploadMessageQueueUtil.offer( request, sessionName, status );
      }
      catch ( final Exception e )
      {
      }
   }

   public String getPostfixRandom()
   {
      return postfixRandom;
   }

   public void setPostfixRandom( String postfixRandom )
   {
      this.postfixRandom = postfixRandom;
   }

}
