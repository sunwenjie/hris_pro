package com.kan.base.web.actions.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.FileUploadVO;
import com.kan.base.util.ExcelImport4EmployeeContract;
import com.kan.base.util.ExcelImport4EmployeeSalaryAdjustment;
import com.kan.base.util.ExcelImport4Performance;
import com.kan.base.util.ExcelImport4PositionChange;
import com.kan.base.util.ExcelImport4SelfAssessment;
import com.kan.base.util.ExcelImportUtil;
import com.kan.base.util.FileUploadProgressListener;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.util.UploadMessageQueueUtil;
import com.kan.base.web.action.BaseAction;

/**
 * 有进度条的上传
 */
public class UploadFileAction extends BaseAction
{

   // 文件扩展名限定
   protected HashMap< String, String > extMap = new HashMap< String, String >();

   // 文件大小限定 100M
   protected long maxSize = 100;

   // Action初始化设置
   public UploadFileAction()
   {
      //定义允许上传的文件扩展名
      extMap.put( "common", "doc,docx,xls,xlsx,ppt,pdf,txt,zip,rar" );
      extMap.put( "office", "doc,docx,xls,xlsx,ppt,pdf" );
      extMap.put( "image", "gif,jpg,jpeg,png,bmp" );
      extMap.put( "flash", "swf,flv" );
      extMap.put( "media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb" );
      extMap.put( "all", extMap.get( "common" ) + "," + extMap.get( "office" ) + "," + extMap.get( "image" ) + "," + extMap.get( "flash" ) + "," + extMap.get( "media" ) );
   }

   /**
    * 文件上传
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward upload( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws Exception
   {
      // 获得上传目录
      final String folder = java.net.URLDecoder.decode( java.net.URLDecoder.decode( request.getParameter( "folder" ), "utf-8" ), "utf-8" );
      // 获得上传文件类型
      final String extType = request.getParameter( "extType" );
      // 随机的名字,避免重复
      final String tmpFileName = UUID.randomUUID().toString().replace( "-", "" );

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         int currentItem = 1;

         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }

            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !Arrays.< String > asList( extMap.get( extType ).split( "," ) ).contains( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不允许上传！" );
                  return mapping.findForward( "" );
               }

               // Share Folder Username, Password, IP, and Port
               final String username = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_USERNAME;
               final String password = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PASSWORD;
               final String host = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_HOST;
               final String port = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PORT;

               // 远程路径字符串
               String remoteBaseFolder = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_DIRECTORY + folder;
               if ( remoteBaseFolder != null && !remoteBaseFolder.endsWith( "/" ) )
               {
                  remoteBaseFolder = remoteBaseFolder + "/";
               }

               String remoteFolderString = remoteBaseFolder;
               if ( username != null && !username.trim().equals( "" ) && port != null && !port.trim().equals( "" ) )
               {
                  remoteFolderString = "smb://" + username + ":" + password + "@" + host + ":" + port + remoteFolderString;
               }
               else if ( username != null && !username.trim().equals( "" ) )
               {
                  remoteFolderString = "smb://" + username + ":" + password + "@" + host + remoteFolderString;
               }
               else if ( port != null && !port.trim().equals( "" ) )
               {
                  remoteFolderString = "smb://" + host + ":" + port + remoteFolderString;
               }
               else
               {
                  remoteFolderString = "smb://" + host + remoteFolderString;
               }

               // 远程文件字符串
               //final String remoteFileString = remoteFolderString + fileName;
               final String remoteFileString = remoteFolderString + tmpFileName + "." + ext;

               // 远程路径
               final SmbFile remoteFolder = new SmbFile( remoteFolderString );
               // 远程路径不存在则创建
               if ( !remoteFolder.exists() )
               {
                  remoteFolder.mkdirs();
               }
               // 远程文件
               final SmbFile remoteFile = new SmbFile( remoteFileString );

               // 初始化OS和IS
               final BufferedOutputStream os = new BufferedOutputStream( new SmbFileOutputStream( remoteFile ) );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态，最后一个文件大小用于保存至数据库
               setStatusMsg( request, "2", remoteBaseFolder + tmpFileName + "." + ext + "##" + fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) + "##"
                     + item.getSize() );

               logger.info( "Upload File Success - Account ID: " + getAccountId( request, response ) + "; File Name: " + remoteBaseFolder + fileName );
            }
            else
            {
               // 表单字段在此处理
            }

            currentItem++;
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传文件失败！" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * Excel文件上传
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    * @throws InterruptedException 
    */
   public ActionForward importExcel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {
      // 导入ID
      String importHeaderId = request.getParameter( "importHeaderId" );

      if ( KANUtil.filterEmpty( importHeaderId ) != null )
      {
         // 如果是加密数据，需要解密
         if ( !importHeaderId.matches( "[0-9]*" ) )
         {
            importHeaderId = KANUtil.decodeString( importHeaderId );
         }
      }
      else
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         return mapping.findForward( "" );
      }

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         //接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               ExcelImportUtil.importDB( localFileString, request, importHeaderId, description );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * 
    * 错误信息的处理
    * 
    * @param request
    * @param info -- 0:正常；1:错误 ； 2:上传完成；3:导入中； 4:导入完成
    * @param message
    * @throws KANException 
    */
   public static void setStatusMsg( final HttpServletRequest request, final String info, final String message ) throws KANException
   {
      setStatusMsg( request, info, message, "0" );
   }

   /** 
    * 错误信息的处理
    * 
    * @param request
    * @param info -- 0:正常；1:错误 ； 2:上传完成；3:导入中； 4:导入完成
    * @param type -- 0:正常消息；1:警告消息 ； 2:出错消息；3：额外信息
    * @param message
    * @throws KANException 
    */
   // Reviewed by Kevin Jin at 2014-02-17
   public static void setStatusMsg( final HttpServletRequest request, final String info, final String message, final String type ) throws KANException
   {
      // 初始化文件上传随机参数
      final String postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // 初始化Session Name String
      String sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      // 获取缓存信息
      FileUploadVO status = new FileUploadVO();

      status.setInfo( info );

      if ( KANUtil.filterEmpty( type ) != null )
      {
         if ( type.trim().equals( "0" ) )
         {
            status.setStatusMsg( message );
         }
         else if ( type.trim().equals( "1" ) )
         {
            status.setWarningMsg( message );
         }
         else if ( type.trim().equals( "2" ) )
         {
            status.setErrorMsg( message );
         }
         else if ( type.trim().equals( "3" ) )
         {
            status.setExtraMsg( message );
         }
      }

      // 获取缓存信息堆栈
      UploadMessageQueueUtil.offer( request, sessionName, status );
   }

   /**
    * 获取状态信息
    * 
    * @param request
    * @param out
    * @throws InterruptedException 
    */
   // Reviewed by Kevin Jin at 2014-02-17
   private void getStatusMsg( final HttpServletRequest request, final PrintWriter out ) throws KANException, InterruptedException
   {
      // 初始化文件上传随机参数
      final String postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // 初始化Session Name String
      String sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      // 获取缓存信息

      final FileUploadVO status = ( FileUploadVO ) UploadMessageQueueUtil.poll( request, sessionName );

      if ( status != null && KANUtil.filterEmpty( status.getInfo() ) != null )
      {
         // 获取JSon字符串
         final String statusJSonString = status.toJSon();

         // 上传失败或上传成功则删除缓存
         if ( status.getInfo().trim().equals( "1" ) || status.getInfo().trim().equals( "4" ) )
         {
            removeObjectFromSession( request, sessionName );
            // 延迟250微妙
            Thread.sleep( 250 );
         }

         out.println( statusJSonString );
      }
      else
      {
         out.println( new FileUploadVO().toJSon() );
      }
   }

   /**
    * 获取文件上传状态
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward getStatusMessage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化返回参数
         request.setCharacterEncoding( "UTF-8" );
         response.setContentType( "text/html; charset=UTF-8" );

         // 初始化PrintWriter对象
         final PrintWriter out = response.getWriter();

         // 返回当前Status
         getStatusMsg( request, out );

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4Performance( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         //接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               ExcelImport4Performance.importDB( localFileString, request );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeeContract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         // 接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               request.setAttribute( "description_temp", description );
               ExcelImport4EmployeeContract.importDB( localFileString, request );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeeSalaryAdjustment( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         //接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               ExcelImport4EmployeeSalaryAdjustment.importDB( localFileString, request );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeePositionChange( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         //接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               ExcelImport4PositionChange.importDB( localFileString, request );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4SelfAssessment( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // 使用内存超过50M时，将产生临时文件并存储于临时目录中
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // 临时文件存储目录
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // 文件名中文乱码处理
      upload.setHeaderEncoding( "GBK" );
      // 设置文件上传大小
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // 创建进度监听器
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // 逐个上传文件
         final List< ? > items = upload.parseRequest( request );

         //接收备注参数
         String description = "";
         //处理文件
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            if ( item.isFormField() && ( ( String ) item.getFieldName() ).equals( "description" ) )
            {
               if ( StringUtils.isNotBlank( item.getString() ) )
               {
                  description = new String( item.getString().getBytes( "ISO-8859-1" ), "GBK" );
               }
               continue;
            }
            final String fileName;
            //统一linux和windows的路径分隔符
            String name = item.getName().replaceAll( "/", "\\" );
            //得到文件名
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // 如果不是表单字段处理
            if ( !item.isFormField() )
            {
               // 检查文件大小
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "文件大小超过限制 (" + maxSize + "M) ！" );
                  return mapping.findForward( "" );
               }

               // 检查文件扩展名
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "文件类型不是.xlsx格式文件！" );
                  return mapping.findForward( "" );
               }

               // 缓存文件夹
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // 本地文件及路径
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // 如果本地目录不存在，创建目录
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // 文件读取大小，字节
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //关闭流  
               os.flush();
               os.close();
               is.close();

               // 设置上传成功返回状态
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // 解析文件导入到数据库
               ExcelImport4SelfAssessment.importDB( localFileString, request );
            }
            else
            {
               // 表单字段在此处理
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "上传、导入文件失败！", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // Un-use
   }

}
