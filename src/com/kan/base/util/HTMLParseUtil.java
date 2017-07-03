package com.kan.base.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HTMLParseUtil
{
   public static ByteArrayOutputStream htmlToPDF( final String content, final String identity, final String logo ) throws KANException
   {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try
      {
         String fontFamily = "SimSun";
         String author = "";//"@上海凯桉华  版权所有";
         String htmlTitle = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
         htmlTitle = htmlTitle + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd \">";
         htmlTitle = htmlTitle + "<html xmlns=\"http://www.w3.org/1999/xhtml \">";
         htmlTitle = htmlTitle + "<head>";
         htmlTitle = htmlTitle + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
         htmlTitle = htmlTitle + "<style type=\"text/css\">";
         htmlTitle = htmlTitle + "body {font-family: " + fontFamily + ";}";
         htmlTitle = htmlTitle + "@page{ ";
         htmlTitle = htmlTitle + "margin: 0.25in; ";
         htmlTitle = htmlTitle + "padding: 1em; ";
         htmlTitle = htmlTitle + "@bottom-center{ ";
         htmlTitle = htmlTitle + "content:'" + author + "';";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 9px; ";
         htmlTitle = htmlTitle + "color:red; ";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + " @top-center { content: element(header) };";
         htmlTitle = htmlTitle + "@bottom-right{";
         htmlTitle = htmlTitle + "content:\"第\" counter(page) \"页  共\"counter(pages)\"页\";";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 9px;";
         htmlTitle = htmlTitle + "color:#000;";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "div#myheader {";
         htmlTitle = htmlTitle + "display: block;";
         htmlTitle = htmlTitle + "position: running(header);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "#pagenumber:before {";
         htmlTitle = htmlTitle + "content: counter(page);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "#pagecount:before {";
         htmlTitle = htmlTitle + "content: counter(pages);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "table {";
         htmlTitle = htmlTitle + "table-layout:fixed;";
         htmlTitle = htmlTitle + "word-break:break-strict;";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "</style>";
         htmlTitle = htmlTitle + "</head>";
         htmlTitle = htmlTitle + "<body>";
         htmlTitle = htmlTitle + "<img src=\"" + KANUtil.basePath + "/" + logo + "\" border=\"0\"/>";
         if ( StringUtils.isNotEmpty( content ) )
         {
            htmlTitle = htmlTitle
                  + content.replaceAll( "&ldquo;", "\"" ).replaceAll( "&rdquo;", "\"" ).replaceAll( "&quot;", "\"" ).replaceAll( "&#39;", "\'" ).replaceAll( "&lt;", "<" ).replaceAll( "&gt;", ">" ).replaceAll( "pre", "p" );
         }
         htmlTitle = htmlTitle + "</body>";
         htmlTitle = htmlTitle + "</html>";
         ITextRenderer renderer = new ITextRenderer();
         ITextFontResolver fontResolver = renderer.getFontResolver();
         fontResolver.addFont( KANUtil.basePath + "/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED );
         renderer.setDocumentFromString( htmlTitle );
         renderer.layout();
         renderer.createPDF( baos );
         renderer.finishPDF();
      }
      catch ( Exception e )
      {
         String errorMessage = e.getMessage();
         if ( StringUtils.contains( e.getMessage(), "lineNumber" ) && StringUtils.contains( e.getMessage(), "columnNumber" ) )
         {
            errorMessage = errorMessage.substring( errorMessage.indexOf( "lineNumber" ), errorMessage.length() ).replace( "lineNumber", "行号" ).replace( "columnNumber", "列号" ).replace( "null", "" );
         }
         throw new KANException( errorMessage );
      }
      return baos;
   }

   public static ByteArrayOutputStream htmlToPDF( final String content, final String identity, final String logo, final HttpServletRequest request, final boolean isShowBarCode )
         throws KANException
   {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try
      {
         String imageName = "";
         File fileTemp = null;
         if ( isShowBarCode )
         {
            String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            URL url = new URL( serverPath + "barcode?type=code128&fmt=jpg" );
            HttpURLConnection urlConnection = ( HttpURLConnection ) url.openConnection();
            urlConnection.setRequestMethod( "GET" );
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            imageName = UUID.randomUUID() + ".jpg";
            fileTemp = new File( KANUtil.basePath + "/images/" + imageName );
            FileOutputStream fos = new FileOutputStream( fileTemp );
            ImageIO.write( ImageIO.read( inputStream ), "jpg", fos );
            fos.close();
         }

         String fontFamily = "SimSun";
         String author = "&nbsp;&nbsp;&nbsp;&nbsp;";
         String htmlTitle = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
         htmlTitle = htmlTitle + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd \">";
         htmlTitle = htmlTitle + "<html xmlns=\"http://www.w3.org/1999/xhtml \">";
         htmlTitle = htmlTitle + "<head>";
         htmlTitle = htmlTitle + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
         htmlTitle = htmlTitle + "<style type=\"text/css\">";
         htmlTitle = htmlTitle + "body {font-family: " + fontFamily + ";}";
         htmlTitle = htmlTitle + "@page{ ";
         htmlTitle = htmlTitle + "size: A4 landscape;";
         htmlTitle = htmlTitle + "margin: 0.5in; ";
         htmlTitle = htmlTitle + "padding: 1em;";
         htmlTitle = htmlTitle + "@bottom-left{background-image:url(\"" + KANUtil.basePath + "/images/" + imageName + "\");background-repeat:no-repeat;background-size:cover;};";
         htmlTitle = htmlTitle + "@bottom-center{ ";
         htmlTitle = htmlTitle + "content:'" + author + "';";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 12px; ";
         htmlTitle = htmlTitle + "color:red; ";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + "@bottom-right{";
         htmlTitle = htmlTitle + "content:\"第\" counter(page) \"页  共\"counter(pages)\"页\";";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 12px;";
         htmlTitle = htmlTitle + "color:#000;";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "table {";
         htmlTitle = htmlTitle + "table-layout:fixed;";
         htmlTitle = htmlTitle + "word-break:break-strict;";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "</style>";
         htmlTitle = htmlTitle + "</head>";
         htmlTitle = htmlTitle + "<body>";
         htmlTitle = htmlTitle + "<img src=\"" + KANUtil.basePath + "/" + logo + "\" border=\"0\"/>";
         if ( StringUtils.isNotEmpty( content ) )
         {
            String contentNew = content.replaceAll( "&ldquo;", "\"" ).replaceAll( "&rdquo;", "\"" ).replaceAll( "&quot;", "\"" ).replaceAll( "&#39;", "\'" ).replaceAll( "&lt;", "<" ).replaceAll( "&gt;", ">" ).replaceAll( "pre", "p" );
            while ( contentNew.contains( "font-family" ) )
            {
               int beginIndex = contentNew.indexOf( "font-family" );
               int endIndex = contentNew.indexOf( ";", beginIndex );
               String temp = contentNew.substring( beginIndex + "font-family".length() - 1, endIndex );
               contentNew = contentNew.replaceAll( temp, "" );
            }

            htmlTitle = htmlTitle + contentNew;
         }
         htmlTitle = htmlTitle + "</body>";
         htmlTitle = htmlTitle + "</html>";
         ITextRenderer renderer = new ITextRenderer();
         ITextFontResolver fontResolver = renderer.getFontResolver();
         fontResolver.addFont( KANUtil.basePath + "/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED );
         renderer.setDocumentFromString( htmlTitle );
         renderer.layout();
         renderer.createPDF( baos );
         renderer.finishPDF();
         if ( fileTemp != null )
         {
            fileTemp.delete();
         }
      }
      catch ( Exception e )
      {
         String errorMessage = e.getMessage();
         if ( StringUtils.contains( e.getMessage(), "lineNumber" ) && StringUtils.contains( e.getMessage(), "columnNumber" ) )
         {
            errorMessage = errorMessage.substring( errorMessage.indexOf( "lineNumber" ), errorMessage.length() ).replace( "lineNumber", "行号" ).replace( "columnNumber", "列号" ).replace( "null", "" );
         }
         throw new KANException( errorMessage );
      }
      return baos;
   }

   public static void checkHtmlStyle( final String content ) throws KANException
   {
      try
      {
         String fontFamily = "SimSun";
         String author = "";//"@上海凯桉华  版权所有";
         String htmlTitle = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
         htmlTitle = htmlTitle + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd \">";
         htmlTitle = htmlTitle + "<html xmlns=\"http://www.w3.org/1999/xhtml \">";
         htmlTitle = htmlTitle + "<head>";
         htmlTitle = htmlTitle + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
         htmlTitle = htmlTitle + "<style type=\"text/css\">";
         htmlTitle = htmlTitle + "body {font-family: " + fontFamily + ";}";
         htmlTitle = htmlTitle + "@page{ ";
         htmlTitle = htmlTitle + "margin: 0.25in; ";
         htmlTitle = htmlTitle + "padding: 1em; ";
         htmlTitle = htmlTitle + "@bottom-center{ ";
         htmlTitle = htmlTitle + "content:'" + author + "';";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 9px; ";
         htmlTitle = htmlTitle + "color:red; ";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + " @top-center { content: element(header) };";
         htmlTitle = htmlTitle + "@bottom-right{";
         htmlTitle = htmlTitle + "content:\"第\" counter(page) \"页  共\"counter(pages)\"页\";";
         htmlTitle = htmlTitle + "font-family: " + fontFamily + ";";
         htmlTitle = htmlTitle + "font-size: 9px;";
         htmlTitle = htmlTitle + "color:#000;";
         htmlTitle = htmlTitle + "};";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "div#myheader {";
         htmlTitle = htmlTitle + "display: block;";
         htmlTitle = htmlTitle + "position: running(header);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "#pagenumber:before {";
         htmlTitle = htmlTitle + "content: counter(page);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "#pagecount:before {";
         htmlTitle = htmlTitle + "content: counter(pages);";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "table {";
         htmlTitle = htmlTitle + "table-layout:fixed;";
         htmlTitle = htmlTitle + "word-break:break-strict;";
         htmlTitle = htmlTitle + "}";
         htmlTitle = htmlTitle + "</style>";
         htmlTitle = htmlTitle + "</head>";
         htmlTitle = htmlTitle + "<body>";
         if ( StringUtils.isNotEmpty( content ) )
         {
            htmlTitle = htmlTitle
                  + content.replaceAll( "&ldquo;", "\"" ).replaceAll( "&rdquo;", "\"" ).replaceAll( "&quot;", "\"" ).replaceAll( "&#39;", "\'" ).replaceAll( "&lt;", "<" ).replaceAll( "&gt;", ">" ).replaceAll( "pre", "p" );
         }
         htmlTitle = htmlTitle + "</body>";
         htmlTitle = htmlTitle + "</html>";
         ITextRenderer renderer = new ITextRenderer();
         renderer.setDocumentFromString( htmlTitle );
      }
      catch ( Exception e )
      {
         String errorMessage = e.getMessage();
         if ( StringUtils.contains( e.getMessage(), "lineNumber" ) && StringUtils.contains( e.getMessage(), "columnNumber" ) )
         {
            errorMessage = errorMessage.substring( errorMessage.indexOf( "lineNumber" ), errorMessage.length() ).replace( "lineNumber", "行号" ).replace( "columnNumber", "列号" ).replace( "null", "" );
         }
         throw new KANException( errorMessage );
      }
   }

   @Deprecated
   public static ByteArrayOutputStream htmlParsePDF( final String content, final String identity, final String logo ) throws KANException
   {
      // 初始化输出字节流
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try
      {
         // 初始化iText文档
         final Document document = new Document( PageSize.A4, 75, 56, 75, 56 );
         // 初始化PDF打印实例
         final PdfWriter writer = PdfWriter.getInstance( document, baos );
         // 初始化iText页眉和页脚
         final HeaderFooter event = new HeaderFooter( identity, logo );
         writer.setPageEvent( event );
         if ( content != null && !content.trim().isEmpty() )
         {

            // 打开iText文档
            document.open();
            // 初始位移
            int beginIndex = 0;
            int endIndex = 0;
            // 循环直到从位移开始匹配不到字符串
            while ( content.indexOf( "<p", endIndex ) >= 0 )
            {
               // 获取下一个匹配到的开始位移
               beginIndex = content.indexOf( "<p", endIndex );
               // 获取下一个匹配到的结束位移
               endIndex = content.indexOf( "</p>", beginIndex ) + 4;

               document.add( generateParagraph( content.substring( beginIndex, endIndex ) ) );
            }
            // 关闭iText文档
            document.close();
         }
         else
         {
            document.open();
            writer.setPageEmpty( true );
            document.add( new Paragraph( "Empty Data!" ) );
            document.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return baos;
   }

   @Deprecated
   private static Paragraph generateParagraph( final String htmlParagraph ) throws KANException
   {
      try
      {
         if ( htmlParagraph != null && !htmlParagraph.trim().equals( "" ) )
         {
            // <p>定位
            final int beginOpenP = 0;
            final int endOpenP = htmlParagraph.indexOf( ">", beginOpenP );
            final String openPString = htmlParagraph.substring( beginOpenP + 2, endOpenP ).replace( " ", "" );

            // </p>定位
            final int beginCloseP = htmlParagraph.indexOf( "</p" );
            @SuppressWarnings("unused")
            final int endCloseP = htmlParagraph.indexOf( ">", beginCloseP );

            // 初始化Alignment和Font Size
            int alignment = Paragraph.ALIGN_LEFT;
            float fontSize = 10;

            // 设置段落对齐
            if ( openPString.contains( "text-align:center" ) )
            {
               alignment = Paragraph.ALIGN_CENTER;
            }
            else if ( openPString.contains( "text-align:right" ) )
            {
               alignment = Paragraph.ALIGN_RIGHT;
            }
            else if ( openPString.contains( "font-size:" ) )
            {
               int begin = openPString.indexOf( "font-size:" ) + 10;
               int end = openPString.indexOf( "px", begin );
               fontSize = new Float( openPString.substring( begin, end ) );
            }

            // 初始化中文字体
            final BaseFont bfChinese = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED );
            final Font fontChinese = new Font( bfChinese, fontSize, Font.NORMAL );
            // 初始化Paragraph
            final Paragraph paragraph = new Paragraph( "", fontChinese );
            paragraph.setAlignment( alignment );

            paragraph.addAll( generateChunk( htmlParagraph.substring( endOpenP + 1, beginCloseP ) ) );

            // 返回iText段落
            return paragraph;
         }
         else
         {
            return null;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Deprecated
   private static List< Chunk > generateChunk( final String htmlContent ) throws KANException
   {
      try
      {
         // 初始化iText块
         final List< Chunk > chunks = new ArrayList< Chunk >();

         if ( htmlContent != null && !htmlContent.trim().equals( "" ) )
         {
            // 初始化Font
            float fontSize = 10;
            boolean underLine = false;
            boolean highlight = false;
            int style = Font.NORMAL;
            final BaseFont bfChinese = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED );
            Font fontChinese = new Font( bfChinese, fontSize, style );

            // 初始化 Chunk Content
            String chunkContent = htmlContent;
            // 初始位移
            int beginIndex = 0;
            int endIndex = 0;

            // 如果当前字符串存在标签
            if ( chunkContent.contains( "<" ) )
            {
               // 循环直到从位移开始匹配不到字符串
               while ( chunkContent.indexOf( "<", endIndex ) >= 0 )
               {
                  // 获取下一个匹配到的开始位移
                  beginIndex = chunkContent.indexOf( "<", endIndex );

                  // 如果是内容
                  if ( beginIndex > 0 )
                  {
                     fontChinese.setSize( fontSize );

                     final Chunk chunk = new Chunk( chunkContent.substring( 0, beginIndex ).replace( "&nbsp;", " " ), fontChinese );

                     if ( underLine )
                     {
                        chunk.setUnderline( 0.1f, -1f );
                     }

                     if ( highlight )
                     {
                        fontChinese.setColor( BaseColor.PINK );
                     }

                     fontChinese.setStyle( style );

                     chunks.add( chunk );
                     fontSize = 10;
                     underLine = false;
                     highlight = false;
                     style = Font.NORMAL;
                  }

                  // 获取下一个匹配到的结束位移
                  endIndex = chunkContent.indexOf( ">", beginIndex ) + 1;

                  // 获取标签字符串
                  String tag = chunkContent.substring( beginIndex, endIndex ).replace( " ", "" ).toLowerCase();

                  // HTML标签处理
                  if ( !tag.contains( "</" ) )
                  {
                     // 如果Chunk需要设置字体大小
                     if ( tag.contains( "font-size:" ) )
                     {
                        fontSize = new Float( tag.substring( tag.indexOf( "font-size:" ) + 10, tag.indexOf( "px" ) ) );
                     }

                     // 如果需要设置Highlight
                     if ( tag.contains( "background:" ) )
                     {
                        highlight = true;
                     }

                     // 如果Chunk是粗体
                     if ( tag.contains( "<strong" ) )
                     {
                        style = Font.BOLD;
                     }

                     // 如果Chunk含有下划线
                     if ( tag.contains( "<u>" ) )
                     {
                        underLine = true;
                     }
                  }

                  chunkContent = chunkContent.substring( endIndex );
                  endIndex = 0;
               }

               if ( StringUtils.isNotEmpty( chunkContent ) )
               {
                  chunks.add( new Chunk( chunkContent.replace( "&nbsp;", " " ), fontChinese ) );
               }
            }
            // 如果当前字符串不存在标签
            else
            {
               chunks.add( new Chunk( chunkContent.replace( "&nbsp;", " " ), fontChinese ) );
            }
         }

         // 返回iText块
         return chunks;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Deprecated
   static class HeaderFooter extends PdfPageEventHelper
   {
      // Template
      public PdfTemplate tpl;
      // Base Font
      public BaseFont bfChinese;
      // Identity
      public String identity;
      // Logo
      public String logo;
      // 页面大小，计算logo 位置
      public Rectangle rectangle;
      // 是否旋转
      public boolean rotate;

      // 构造函数
      public HeaderFooter( final String identity, final String logo ) throws DocumentException, IOException
      {
         this.bfChinese = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED );
         this.identity = identity;
         this.logo = logo;
      }

      public HeaderFooter( final String identity, final String logo, final Rectangle rectangle, final boolean rotate ) throws DocumentException, IOException
      {
         this.bfChinese = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED );
         this.identity = identity;
         this.logo = logo;
         this.rectangle = rectangle;
         this.rotate = rotate;
      }

      // 打开文档事件
      public void onOpenDocument( PdfWriter writer, Document document )
      {
         try
         {
            tpl = writer.getDirectContent().createTemplate( 649, 75 );
         }
         catch ( final Exception e )
         {
            e.printStackTrace();
         }
      }

      // 每页结束事件
      public void onEndPage( PdfWriter writer, Document document )
      {
         try
         {
            // 在每页结束的时候把页数信息写到模版指定位置
            final PdfContentByte cb = writer.getDirectContent();
            if ( KANUtil.filterEmpty( identity ) != null )
            {
               final String text = "页数：" + writer.getPageNumber() + "/";

               cb.beginText();
               cb.setFontAndSize( bfChinese, 9 );

               // 定位第几页
               cb.setTextMatrix( 498, 30 );
               cb.showText( text );
               cb.endText();
            }
            // 定位总共多少页（模板）
            cb.addTemplate( tpl, 536, 30 );

            // 添加Logo
            Image image = Image.getInstance( KANUtil.basePath + "/" + logo );
            image.scaleToFit( 114, 27 );
            if ( rectangle != null )
            {
               image.setRotationDegrees( rotate ? 90 : 0 );
               if ( rotate )
               {
                  // 如果旋转
                  image.setAbsolutePosition( 65, 75 );
               }
               else
               {
                  image.setAbsolutePosition( 65, rectangle.getHeight() - 75 );
               }
            }
            else
            {
               image.setAbsolutePosition( 65, 785 );
            }
            cb.addImage( image );

            // 添加Barcode
            if ( KANUtil.filterEmpty( identity ) != null )
            {
               final Barcode128 code = new Barcode128();
               code.setCode( identity );
               cb.addTemplate( code.createTemplateWithBarcode( writer.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK ), 75, 20 );
            }
            cb.saveState();
            cb.stroke();
            cb.restoreState();
            cb.closePath();
         }
         catch ( final Exception e )
         {
            e.printStackTrace();
         }
      }

      // 文档关闭事件
      public void onCloseDocument( PdfWriter writer, Document document )
      {
         // 关闭Document的时候获取总页数，并把总页数按模版写到之前预留的位置
         if ( KANUtil.filterEmpty( identity ) != null )
         {
            tpl.beginText();
            tpl.setFontAndSize( bfChinese, 9 );

            // 如果页数是两位数，需要额外处理
            if ( writer.getPageNumber() - 1 >= 10 )
            {
               tpl.showText( " " + Integer.toString( writer.getPageNumber() - 1 ) );
            }
            else
            {
               tpl.showText( Integer.toString( writer.getPageNumber() - 1 ) );
            }

            tpl.endText();
         }
         tpl.closePath();
      }
   }

   public static ByteArrayOutputStream imageParsePDF( BufferedImage image, String accountId, boolean rotate, String logo ) throws KANException
   {
      // 初始化输出字节流
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try
      {
         // 初始化iText文档
         final Rectangle rectangle;
         if ( rotate )
         {
            rectangle = whatSize( image.getHeight() + 130, image.getWidth() + 100 );
         }
         else
         {
            rectangle = whatSize( image.getWidth() + 100, image.getHeight() + 130 );
         }
         final Document document = new Document( rectangle, 45, 56, 75, 56 );
         // 初始化PDF打印实例
         final PdfWriter writer = PdfWriter.getInstance( document, baos );
         // 初始化iText页眉和页脚
         final HeaderFooter event = new HeaderFooter( null, logo, rectangle, rotate );
         writer.setPageEvent( event );
         if ( image != null )
         {
            // 缓存文件夹
            final String tempFolderName = RandomUtil.getRandomString( 16 );

            // 本地文件及路径
            final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + UUID.randomUUID() + ".jpg";

            // 如果本地目录不存在，创建目录
            KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );
            ImageIO.write( image, "jpg", new File( localFileString ) );
            document.open();
            Image tempImage = Image.getInstance( localFileString );
            tempImage.setRotationDegrees( rotate ? 90 : 0 );
            document.add( tempImage );
            writer.setPageEmpty( true );
            document.close();
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return baos;
   }

   private static Rectangle whatSize( int width, int height )
   {
      List< Rectangle > rectangles = new ArrayList< Rectangle >();
      rectangles.add( PageSize.A4 );
      rectangles.add( PageSize.A3 );
      rectangles.add( PageSize.A2 );
      rectangles.add( PageSize.A1 );
      rectangles.add( PageSize.A0 );
      Rectangle targetRect = new Rectangle( width, height );
      for ( int i = 0; i < rectangles.size(); i++ )
      {
         if ( rectangles.get( i ).getWidth() > targetRect.getWidth() && rectangles.get( i ).getHeight() > targetRect.getHeight() )
         {
            return rectangles.get( i );
         }
      }
      return targetRect;
   }
}
