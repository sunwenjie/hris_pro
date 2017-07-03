package com.kan.base.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

import com.kan.base.domain.FileUploadVO;
import com.kan.base.web.action.BaseAction;

/**
 * 上传监听器
 */
public class FileUploadProgressListener implements ProgressListener
{
   // 公共HttpServletRequest对象
   private HttpServletRequest request;

   // 文件上传随机参数
   private String postfixRandom;

   // 缓存名称
   private String sessionName;

   // 构造函数
   // Reviewed by Kevin Jin at 2014-02-17
   public FileUploadProgressListener( final HttpServletRequest request ) throws KANException
   {
      this.request = request;
      this.postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // 初始化Session Name String
      sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      final FileUploadVO upladeStatus = new FileUploadVO();
      UploadMessageQueueUtil.offer( request, sessionName, upladeStatus );
   }

   /**
    * 为了进度条监听器不会引起性能问题的解决方案：是减少进度条的活动数，比如，只有当上传了1兆字节的时候才反馈给用户
    */
   // Reviewed by Kevin Jin at 2014-02-17
   public void update( long bytesRead, long contentLength, int items )
   {
      try
      {
         final FileUploadVO status = ( FileUploadVO ) BaseAction.getObjectFromSession( request, sessionName );

         if ( contentLength == -1 )
         {
            status.setStatusMsg( "已完成" + items + "个文件的上传..." );
         }
         else
         {
            status.setStatusMsg( "正在上传第" + items + "个文件..." );
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
