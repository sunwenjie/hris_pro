package com.kan.base.web.actions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kan.base.util.CaptchaServiceSingleton;
import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptchaServlet extends HttpServlet
{
   private static final long serialVersionUID = -3718663907013663827L;

   protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
   {

      byte[] captchaChallengeAsJpeg = null;
      //����һ���ֽ����������ʵ��  
      ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
      try
      {
         //��ȡ��ǰsession ID  
         //         String captchaId = request.getSession().getId();
         String captchaId = request.getParameter( "token" );
         //������֤��������������֤��Ļ���ͼƬʵ��  
         BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID( captchaId, request.getLocale() );

         //JPEGͼƬ������  
         JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder( jpegOutputStream );
         jpegEncoder.encode( challenge );
      }
      catch ( IllegalArgumentException e )
      {
         response.sendError( HttpServletResponse.SC_NOT_FOUND );
         return;
      }
      catch ( CaptchaServiceException e )
      {
         response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
         return;
      }
      //��ȡͼƬ��֤����ֽ�����  
      captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

      // ���������Ӧͷ�ֶΣ�Ҫ��ͻ��˲�������Ӧ��Ϣ������  
      response.setHeader( "Cache-Control", "no-store" );
      response.setHeader( "Pragma", "no-cache" );
      response.setDateHeader( "Expires", 0 );
      //������Ӧ��������  
      response.setContentType( "image/jpeg" );

      ServletOutputStream sos = response.getOutputStream();
      //��ͼƬ��֤����ֽ�����������������  
      sos.write( captchaChallengeAsJpeg );
      sos.flush();
      sos.close();
   }
}
