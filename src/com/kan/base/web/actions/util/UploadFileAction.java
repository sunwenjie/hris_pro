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
 * �н��������ϴ�
 */
public class UploadFileAction extends BaseAction
{

   // �ļ���չ���޶�
   protected HashMap< String, String > extMap = new HashMap< String, String >();

   // �ļ���С�޶� 100M
   protected long maxSize = 100;

   // Action��ʼ������
   public UploadFileAction()
   {
      //���������ϴ����ļ���չ��
      extMap.put( "common", "doc,docx,xls,xlsx,ppt,pdf,txt,zip,rar" );
      extMap.put( "office", "doc,docx,xls,xlsx,ppt,pdf" );
      extMap.put( "image", "gif,jpg,jpeg,png,bmp" );
      extMap.put( "flash", "swf,flv" );
      extMap.put( "media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb" );
      extMap.put( "all", extMap.get( "common" ) + "," + extMap.get( "office" ) + "," + extMap.get( "image" ) + "," + extMap.get( "flash" ) + "," + extMap.get( "media" ) );
   }

   /**
    * �ļ��ϴ�
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
      // ����ϴ�Ŀ¼
      final String folder = java.net.URLDecoder.decode( java.net.URLDecoder.decode( request.getParameter( "folder" ), "utf-8" ), "utf-8" );
      // ����ϴ��ļ�����
      final String extType = request.getParameter( "extType" );
      // ���������,�����ظ�
      final String tmpFileName = UUID.randomUUID().toString().replace( "-", "" );

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         int currentItem = 1;

         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );
         final Iterator< ? > iterator = items.iterator();
         while ( iterator.hasNext() )
         {
            final FileItem item = ( FileItem ) iterator.next();
            final String fileName;
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }

            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !Arrays.< String > asList( extMap.get( extType ).split( "," ) ).contains( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ������ϴ���" );
                  return mapping.findForward( "" );
               }

               // Share Folder Username, Password, IP, and Port
               final String username = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_USERNAME;
               final String password = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PASSWORD;
               final String host = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_HOST;
               final String port = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PORT;

               // Զ��·���ַ���
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

               // Զ���ļ��ַ���
               //final String remoteFileString = remoteFolderString + fileName;
               final String remoteFileString = remoteFolderString + tmpFileName + "." + ext;

               // Զ��·��
               final SmbFile remoteFolder = new SmbFile( remoteFolderString );
               // Զ��·���������򴴽�
               if ( !remoteFolder.exists() )
               {
                  remoteFolder.mkdirs();
               }
               // Զ���ļ�
               final SmbFile remoteFile = new SmbFile( remoteFileString );

               // ��ʼ��OS��IS
               final BufferedOutputStream os = new BufferedOutputStream( new SmbFileOutputStream( remoteFile ) );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬�����һ���ļ���С���ڱ��������ݿ�
               setStatusMsg( request, "2", remoteBaseFolder + tmpFileName + "." + ext + "##" + fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) + "##"
                     + item.getSize() );

               logger.info( "Upload File Success - Account ID: " + getAccountId( request, response ) + "; File Name: " + remoteBaseFolder + fileName );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }

            currentItem++;
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��ļ�ʧ�ܣ�" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * Excel�ļ��ϴ�
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
      // ����ID
      String importHeaderId = request.getParameter( "importHeaderId" );

      if ( KANUtil.filterEmpty( importHeaderId ) != null )
      {
         // ����Ǽ������ݣ���Ҫ����
         if ( !importHeaderId.matches( "[0-9]*" ) )
         {
            importHeaderId = KANUtil.decodeString( importHeaderId );
         }
      }
      else
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         return mapping.findForward( "" );
      }

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         //���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               ExcelImportUtil.importDB( localFileString, request, importHeaderId, description );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * 
    * ������Ϣ�Ĵ���
    * 
    * @param request
    * @param info -- 0:������1:���� �� 2:�ϴ���ɣ�3:�����У� 4:�������
    * @param message
    * @throws KANException 
    */
   public static void setStatusMsg( final HttpServletRequest request, final String info, final String message ) throws KANException
   {
      setStatusMsg( request, info, message, "0" );
   }

   /** 
    * ������Ϣ�Ĵ���
    * 
    * @param request
    * @param info -- 0:������1:���� �� 2:�ϴ���ɣ�3:�����У� 4:�������
    * @param type -- 0:������Ϣ��1:������Ϣ �� 2:������Ϣ��3��������Ϣ
    * @param message
    * @throws KANException 
    */
   // Reviewed by Kevin Jin at 2014-02-17
   public static void setStatusMsg( final HttpServletRequest request, final String info, final String message, final String type ) throws KANException
   {
      // ��ʼ���ļ��ϴ��������
      final String postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // ��ʼ��Session Name String
      String sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      // ��ȡ������Ϣ
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

      // ��ȡ������Ϣ��ջ
      UploadMessageQueueUtil.offer( request, sessionName, status );
   }

   /**
    * ��ȡ״̬��Ϣ
    * 
    * @param request
    * @param out
    * @throws InterruptedException 
    */
   // Reviewed by Kevin Jin at 2014-02-17
   private void getStatusMsg( final HttpServletRequest request, final PrintWriter out ) throws KANException, InterruptedException
   {
      // ��ʼ���ļ��ϴ��������
      final String postfixRandom = KANUtil.filterEmpty( request.getParameter( "postfixRandom" ) );

      // ��ʼ��Session Name String
      String sessionName = BaseAction.SESSION_NAME_UPLOAD_STATUS;

      if ( KANUtil.filterEmpty( postfixRandom ) != null )
      {
         sessionName = sessionName + "_" + postfixRandom;
      }

      // ��ȡ������Ϣ

      final FileUploadVO status = ( FileUploadVO ) UploadMessageQueueUtil.poll( request, sessionName );

      if ( status != null && KANUtil.filterEmpty( status.getInfo() ) != null )
      {
         // ��ȡJSon�ַ���
         final String statusJSonString = status.toJSon();

         // �ϴ�ʧ�ܻ��ϴ��ɹ���ɾ������
         if ( status.getInfo().trim().equals( "1" ) || status.getInfo().trim().equals( "4" ) )
         {
            removeObjectFromSession( request, sessionName );
            // �ӳ�250΢��
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
    * ��ȡ�ļ��ϴ�״̬
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
         // ��ʼ�����ز���
         request.setCharacterEncoding( "UTF-8" );
         response.setContentType( "text/html; charset=UTF-8" );

         // ��ʼ��PrintWriter����
         final PrintWriter out = response.getWriter();

         // ���ص�ǰStatus
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
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         //���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               ExcelImport4Performance.importDB( localFileString, request );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeeContract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {

      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         // ���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               request.setAttribute( "description_temp", description );
               ExcelImport4EmployeeContract.importDB( localFileString, request );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeeSalaryAdjustment( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         //���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               ExcelImport4EmployeeSalaryAdjustment.importDB( localFileString, request );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4EmployeePositionChange( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         //���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               ExcelImport4PositionChange.importDB( localFileString, request );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward importExcel4SelfAssessment( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, InterruptedException
   {
      final DiskFileItemFactory factory = new DiskFileItemFactory();
      // ʹ���ڴ泬��50Mʱ����������ʱ�ļ����洢����ʱĿ¼��
      factory.setSizeThreshold( 50 * 1024 * 1024 );
      // ��ʱ�ļ��洢Ŀ¼
      factory.setRepository( new File( KANUtil.tempFileUploadBase ) );

      final ServletFileUpload upload = new ServletFileUpload( factory );
      // �ļ����������봦��
      upload.setHeaderEncoding( "GBK" );
      // �����ļ��ϴ���С
      upload.setFileSizeMax( maxSize * 1024 * 1024 );

      // �������ȼ�����
      final FileUploadProgressListener progressListener = new FileUploadProgressListener( request );
      upload.setProgressListener( progressListener );

      try
      {
         // ����ϴ��ļ�
         final List< ? > items = upload.parseRequest( request );

         //���ձ�ע����
         String description = "";
         //�����ļ�
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
            //ͳһlinux��windows��·���ָ���
            String name = item.getName().replaceAll( "/", "\\" );
            //�õ��ļ���
            int index = name.lastIndexOf( "\\" );
            if ( index == -1 )
            {
               fileName = name;
            }
            else
            {
               fileName = name.substring( index + 1 );
            }
            // ������Ǳ��ֶδ���
            if ( !item.isFormField() )
            {
               // ����ļ���С
               if ( item.getSize() > maxSize * 1024 * 1024 )
               {
                  setStatusMsg( request, "1", "�ļ���С�������� (" + maxSize + "M) ��" );
                  return mapping.findForward( "" );
               }

               // ����ļ���չ��
               final String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 ).toLowerCase();
               if ( !"xlsx".equals( ext ) )
               {
                  setStatusMsg( request, "1", "�ļ����Ͳ���.xlsx��ʽ�ļ���" );
                  return mapping.findForward( "" );
               }

               // �����ļ���
               final String tempFolderName = RandomUtil.getRandomString( 16 );

               // �����ļ���·��
               final String localFileString = KANUtil.fileUploadBase + "/" + tempFolderName + "/" + fileName;

               // �������Ŀ¼�����ڣ�����Ŀ¼
               KANUtil.createFolder( KANUtil.fileUploadBase + "/" + tempFolderName );

               final File uploadedFile = new File( localFileString );
               final OutputStream os = new FileOutputStream( uploadedFile );
               final InputStream is = item.getInputStream();

               // �ļ���ȡ��С���ֽ�
               byte buffer[] = new byte[ 10240 ];
               int length = 0;
               while ( ( length = is.read( buffer ) ) > 0 )
               {
                  os.write( buffer, 0, length );
               }
               //�ر���  
               os.flush();
               os.close();
               is.close();

               // �����ϴ��ɹ�����״̬
               setStatusMsg( request, "2", fileName + " &nbsp; " + KANUtil.getSizeString( item.getSize() ) );

               // �����ļ����뵽���ݿ�
               ExcelImport4SelfAssessment.importDB( localFileString, request );
            }
            else
            {
               // ���ֶ��ڴ˴���
            }
         }
      }
      catch ( final Exception e )
      {
         setStatusMsg( request, "1", "�ϴ��������ļ�ʧ�ܣ�", "2" );
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
