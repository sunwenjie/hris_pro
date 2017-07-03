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
      //创建一个字节数组输出流实例  
      ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
      try
      {
         //获取当前session ID  
         //         String captchaId = request.getSession().getId();
         String captchaId = request.getParameter( "token" );
         //利用验证码生成类生成验证码的缓存图片实例  
         BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID( captchaId, request.getLocale() );

         //JPEG图片编码器  
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
      //获取图片验证码的字节数组  
      captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

      // 添加以下响应头字段：要求客户端不缓存响应消息体内容  
      response.setHeader( "Cache-Control", "no-store" );
      response.setHeader( "Pragma", "no-cache" );
      response.setDateHeader( "Expires", 0 );
      //设置响应内容类型  
      response.setContentType( "image/jpeg" );

      ServletOutputStream sos = response.getOutputStream();
      //把图片验证码的字节数组输出到输出流中  
      sos.write( captchaChallengeAsJpeg );
      sos.flush();
      sos.close();
   }
}
