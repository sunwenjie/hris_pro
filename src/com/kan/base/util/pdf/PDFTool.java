package com.kan.base.util.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.kan.base.util.KANConstants;
import com.kan.base.web.action.BaseAction;

import freemarker.template.Template;

public class PDFTool
{
   private FreeMarkerConfigurer freemarkerconfiguration;

   private final String dzorderftl = "pdftemplete/dzorder.ftl";

   private static String tmpdir = PDFTool.class.getResource( "/" ).getPath() + "tmpdir";
   {
      tmpdir = tmpdir.replaceAll( "^/", "" );
   }

   public InputStream generationPdfDzOrder( Map< String, Object > params ) throws Exception
   {
      final Template template = freemarkerconfiguration.getConfiguration().getTemplate( dzorderftl );
      String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString( template, params );
      String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名  
      File dir = new File( tmpdir );
      if ( !dir.exists() )
         dir.mkdirs();
      String htmlFileName = "c:/test/" + tmpFileName + ".htm/";
      String pdfFileName = "c:/" + tmpFileName + ".pdf";
      File htmlFile = new File( htmlFileName ); //html文件  
      File pdfFile = new File( pdfFileName ); //pdf文件  
      IOUtils.write( htmlText, new FileOutputStream( htmlFile ) ); //将内容写入html文件  
      String command = getCommand( htmlFileName, pdfFileName );
      Runtime.getRuntime().exec( command );
      TimeUnit.SECONDS.sleep( 3 );
      return new FileInputStream( pdfFile );
   }

   public InputStream generationPdfDzOrder( String htmlFileName, String pdfFileName ) throws Exception
   {

      String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名  
      String command = getCommand( htmlFileName, pdfFileName );
      System.out.println( command );
      File pdfFile = new File( pdfFileName ); //pdf文件  
      if ( !pdfFile.exists() )
      {
         pdfFile.createNewFile();
      }
      Runtime.getRuntime().exec( command );
      //    TimeUnit.SECONDS.sleep(3);   
      return new FileInputStream( pdfFile );
   }


   
   public static String smbGet(final String remoteUrl,final HttpServletRequest request) {  
      InputStream in = null;  
      OutputStream out = null; 
      String localFileName = "";
      try {  
         final String username = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SHAREFOLDER_USERNAME;
         final String password = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SHAREFOLDER_PASSWORD;
         final String host = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SHAREFOLDER_HOST;
         final String port = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).SHAREFOLDER_PORT;

         // 远程文件字符串
         String remoteFileString = remoteUrl;
         if ( username != null && !username.trim().equals( "" ) && port != null && !port.trim().equals( "" ) )
         {
            remoteFileString = "smb://" + username + ":" + password + "@" + host + ":" + port + remoteFileString;
         }
         else if ( username != null && !username.trim().equals( "" ) )
         {
            remoteFileString = "smb://" + username + ":" + password + "@" + host + remoteFileString;
         }
         else if ( port != null && !port.trim().equals( "" ) )
         {
            remoteFileString = "smb://" + host + ":" + port + remoteFileString;
         }
         else
         {
            remoteFileString = "smb://" + host + remoteFileString;
         }
          SmbFile remoteFile = new SmbFile(remoteFileString);  
          if (remoteFile == null) {  
              return localFileName;  
          }  
          String fileName = remoteFile.getName();  
          String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名  
          localFileName = KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH + File.separatorChar + tmpFileName + fileName;
          File localFile = new File(localFileName); 
          in = new BufferedInputStream(new SmbFileInputStream(remoteFile));  
          out = new BufferedOutputStream(new FileOutputStream(localFile));  
          byte[] buffer = new byte[1024];  
          while (in.read(buffer) != -1) {  
              out.write(buffer);  
              buffer = new byte[1024];  
          }  
      } catch (Exception e) {  
          e.printStackTrace();  
      } finally {  
          try {  
             if(out!=null)
              out.close();
             if(in!=null)
              in.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
          }  
      }
      return localFileName;  
  }  

   public static File generationPdfDzOrder( String htmlText ) throws Exception
   {

      String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名  
      //      File dir = new File( tmpdir );
      //      if ( !dir.exists() )
      //         dir.mkdirs();
      String htmlFileName = KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH + File.separatorChar + tmpFileName + ".htm";
      String pdfFileName = KANConstants.WKHTMLTOPDF_TEMP_FILE_PATH + File.separatorChar + tmpFileName + ".pdf";
      File htmlFile = new File( htmlFileName ); //html文件  
      File pdfFile = new File( pdfFileName ); //pdf文件  
      IOUtils.write( htmlText, new FileOutputStream( htmlFile ) ); //将内容写入html文件  

      String command = getCommand( htmlFileName, pdfFileName );
      System.out.println( "-------------wkhtmltopdf----------------------" + command );
      //      File pdfFile = new  File(pdfFileName); //pdf文件  
      if ( !pdfFile.exists() )
      {
         pdfFile.createNewFile();
      }
      Process proc = Runtime.getRuntime().exec( command );
      proc.waitFor();
      //  final OutputStream os = new FileOutputStream( uploadedFile );
      return pdfFile;
   }

   public static String getCommand( String htmlName, String pdfName )
   {
      //margin-bottom <unitreal>  设置页面下边距 (default 10mm) 
      //margin-left <unitreal>  将左边页边距 (default 10mm) 
      //margin-right <unitreal>  设置页面右边距 (default 10mm) 
      //margin-top <unitreal>  设置页面上边距 (default 10mm) 
      //String system = System.getProperty( "os.name" );
      return KANConstants.WKHTMLTOPDF_PATH + " " + KANConstants.WKHTMLTOPDF_PARAMETER + " " + htmlName + " " + pdfName;
      //if ( system.toLowerCase().contains( "windows" ) ) //xp系统  
      //else if ( system.toLowerCase().contains( "linux" ) ) //linux 系统  
      //return "wkhtmltopdf --footer-center [page]/[topage] " + htmlName + " " + pdfName;
      //return "";
   }
}
