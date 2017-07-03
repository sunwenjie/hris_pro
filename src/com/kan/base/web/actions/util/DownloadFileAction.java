package com.kan.base.web.actions.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.define.ImportRender;
import com.kan.base.web.renders.define.ReportRender;
import com.kan.base.web.renders.util.ExportExcel4OrderBill;
import com.kan.base.web.renders.util.ListRender;
import com.kan.base.web.renders.util.UndefineExcelExport;

/**  
  * ��Ŀ���ƣ�HRO_V1  
  * �����ƣ�DownloadFileAction  
  * ���������ļ�����Action
  * �����ˣ�Kevin  
  * ����ʱ�䣺2013-8-8  
*/
public class DownloadFileAction extends BaseAction
{
   /**
    * �ļ�����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward download( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Զ���ļ�·��
         String fileString = request.getParameter( "fileString" );
         String fileName = request.getParameter( "fileName" );
         fileString = URLDecoder.decode( URLDecoder.decode( fileString, "UTF-8" ), "UTF-8" );

         // Share Folder Username, Password, IP, and Port
         final String username = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_USERNAME;
         final String password = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PASSWORD;
         final String host = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_HOST;
         final String port = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PORT;

         // Զ���ļ��ַ���
         String remoteFileString = fileString;
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

         // Զ���ļ�
         final SmbFile remoteFile = new SmbFile( remoteFileString );

         // ���Զ���ļ�����
         if ( remoteFile.exists() )
         {
            // ��ʼ��InputStream
            final InputStream is = new BufferedInputStream( new SmbFileInputStream( remoteFile ) );

            // ���ͻ��˴����ļ���
            download( response, is, fileName );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public static void download( final OutputStream os, String remoteFileString, final String accountId ) throws KANException
   {
      // Share Folder Username, Password, IP, and Port
      final String username = KANConstants.getKANAccountConstants( accountId ).SHAREFOLDER_USERNAME;
      final String password = KANConstants.getKANAccountConstants( accountId ).SHAREFOLDER_PASSWORD;
      final String host = KANConstants.getKANAccountConstants( accountId ).SHAREFOLDER_HOST;
      final String port = KANConstants.getKANAccountConstants( accountId ).SHAREFOLDER_PORT;

      // Զ���ļ��ַ���
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

      // Զ���ļ�
      try
      {
         final SmbFile remoteFile = new SmbFile( remoteFileString );

         // ���Զ���ļ�����
         if ( remoteFile.exists() )
         {
            // ��ʼ��InputStream
            final InputStream is = new BufferedInputStream( new SmbFileInputStream( remoteFile ) );

            // �ļ���ȡ��С���ֽ�
            byte buffer[] = new byte[ 10240 ];
            int length = 0;
            while ( ( length = is.read( buffer ) ) > 0 )
            {
               os.write( buffer, 0, length );
               os.flush();
            }
            //�ر���  
            os.close();
            is.close();
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * �б���
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // ��������
         final String accessAction = request.getParameter( "accessAction" );

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = new XSSFWorkbook();

            workbook = ListRender.generateListExcel( request, accessAction );

            // ��ʼ��ListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction ).getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

            // ��ʼ��FileName
            final String fileName = listDTO != null ? listDTO.getListName( request ) : "�½��б�";
            // ���ͻ��˴����ļ���
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
            // ����д���ļ�
            workbook.write( response.getOutputStream() );
            response.getOutputStream().flush();
            response.getOutputStream().close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward exportPositionExcel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // ��������
         final String accessAction = request.getParameter( "accessAction" );

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = new XSSFWorkbook();

            // ��ʼ��FileName
            String fileName = "ְλ��";

            // �������еȼ���ʽ
            final String exportType = request.getParameter( "exportType" );

            if ( "branch".equals( exportType ) )
            {
               fileName = "ְλ�������ŵȼ���";
               // �����Ų㼶��ϵ������ְλ��
               workbook = ListRender.generatePositionListExcelByBranch( request, accessAction );
            }
            else if ( "position".equals( exportType ) )
            {
               fileName = "ְλ����ְλ�ȼ���";
               // ��ְλ�㼶��ϵ������ְλ��
               workbook = ListRender.generatePositionListExcelByPosition( request, accessAction );
            }

            // ���ͻ��˴����ļ���
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
            // ����д���ļ�
            workbook.write( response.getOutputStream() );
            response.getOutputStream().flush();
            response.getOutputStream().close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward specialExportList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return specialExportListAddExtendColumns( mapping, form, request, response, null );
   }

   /**
    * �����б���
    * // addedExtendListDTO �������㵥��ʱ�򣬵������б���ʾ���ж༸��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward specialExportListAddExtendColumns( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response, final ListDTO addedExtendListDTO ) throws KANException
   {
      try
      {
         // ��ʼ��corpId
         final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // java�ӿ��޶���
         final String javaObjectName = request.getParameter( "javaObjectName" );

         // ģ������
         final String templateType = request.getParameter( "templateType" );

         // ģ��ID
         final String templateId = request.getParameter( "templateId" );

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = null;
            if ( addedExtendListDTO == null )
            {
               workbook = ListRender.generateSpecialListExcel( request, javaObjectName, templateType, templateId );
            }
            else
            {
               workbook = ListRender.generateSpecialListExcelAddExtendColumns( request, javaObjectName, templateType, templateId, addedExtendListDTO );
            }
            // excel��
            String fileName = "";

            // ����ǹ���ģ��
            if ( "1".equals( templateType ) && KANUtil.filterEmpty( templateId, "0" ) != null )
            {
               // ��ȡBankTemplateDTO
               final BankTemplateDTO bankTemplateDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBankTemplateDTOByTemplateHeaderId( templateId );

               if ( bankTemplateDTO != null && bankTemplateDTO.getBankTemplateHeaderVO() != null )
               {
                  fileName = bankTemplateDTO.getBankTemplateName( request );
               }
            }
            // ����Ǹ�˰ģ��
            else if ( "2".equals( templateType ) && KANUtil.filterEmpty( templateId, "0" ) != null )
            {
               // ��ȡTaxTemplateDTO
               final TaxTemplateDTO taxTemplateDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTaxTemplateDTOByTemplateHeaderId( templateId );

               if ( taxTemplateDTO != null && taxTemplateDTO.getTaxTemplateHeaderVO() != null )
               {
                  fileName = taxTemplateDTO.getTaxTemplateName( request );
               }
            }
            // ���û��ģ��
            else
            {
               // ��ȡListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

               if ( listDTO != null )
               {
                  fileName = listDTO.getListName( request );
               }
            }

            if ( KANUtil.filterEmpty( fileName ) == null )
            {
               fileName = "���ļ�";
            }

            // ���ͻ��˴����ļ���
            download( response, workbook, fileName + ".xlsx" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * ��������ģ��
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportImportTemplate( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // ��������
         final String importHeadId = KANUtil.decodeString( request.getParameter( "importHeaderId" ) );

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            final XSSFWorkbook workbook = ImportRender.generateImportTemplateExcel( request, importHeadId );
            final String accountId = BaseAction.getAccountId( request, null );
            final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            final ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeadId, corpId );

            // ���ͻ��˴����ļ���
            download( response, workbook, importDTO.getImportFileName( request ) + ".xlsx" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * ������
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportReport( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // ����ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // ��������
         String accessAction = request.getParameter( "accessAction" );

         if ( KANUtil.filterEmpty( accessAction ) == null )
         {
            final String tableId = request.getParameter( "tableId" );
            accessAction = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId ).getTableVO().getAccessAction();
         }

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            final XSSFWorkbook workbook = ReportRender.generateReportExcel( request, accessAction, headerId );

            final String fileName = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction ).getReportDTOByReportHeaderId( headerId ).getReportName( request );

            // ���ͻ��˴����ļ���
            download( response, workbook, fileName + ".xlsx" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * ������ͨ���б�
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public ActionForward commonExportList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response,
         final Boolean isDTO ) throws KANException
   {
      try
      {
         // ��ȡ�ļ���
         final String fileName = ( String ) request.getAttribute( "fileName" );

         // ����Excel�ļ�
         final XSSFWorkbook workbook = ListRender.generateCommonListExcel( request, fileName, isDTO );

         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();
         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );
         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         // Excel�ļ�д��OutputStream
         workbook.write( os );
         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   /**
    * Common download method
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final InputStream is, final String fileName ) throws KANException
   {
      try
      {
         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // �ļ���ȡ��С���ֽ�
         byte buffer[] = new byte[ 10240 ];
         int length = 0;
         while ( ( length = is.read( buffer ) ) > 0 )
         {
            os.write( buffer, 0, length );
            os.flush();
         }
         //�ر���  
         os.close();
         is.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Common download method
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final XSSFWorkbook workbook, final String fileName ) throws KANException
   {
      try
      {
         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "UTF-8" ), "iso-8859-1" ) );
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // Excel�ļ�д��OutputStream
         workbook.write( os );

         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Common download method
    * ʹ��SXSSFWorkbook �ɽ�����������ڴ����
    * @param response
    * @param SXSSFWorkbook
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final SXSSFWorkbook workbook, final String fileName ) throws KANException
   {
      try
      {
         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "UTF-8" ), "iso-8859-1" ) );
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // Excel�ļ�д��OutputStream
         workbook.write( os );

         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Common download method
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final ByteArrayOutputStream baos, final String fileName ) throws KANException
   {
      try
      {
         // ��ʼ��OutputStream
         response.reset();
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/pdf" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // PDF�ļ�д��OutputStream
         baos.writeTo( os );

         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Common download method
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public static void download( final OutputStream os, final InputStream is, final String fileName ) throws KANException
   {
      try
      {
         // ���÷����ļ�����
         // ����ļ���������������
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) );
         // �ļ���ȡ��С���ֽ�
         byte buffer[] = new byte[ 10240 ];
         int length = 0;
         while ( ( length = is.read( buffer ) ) > 0 )
         {
            os.write( buffer, 0, length );
            os.flush();
         }
         //�ر���  
         os.close();
         is.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward exportListForOrderBillHeaderView( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��corpId
         final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

         // �����ļ�����
         final String fileType = request.getParameter( "fileType" );

         // java�ӿ��޶���
         final String javaObjectName = request.getParameter( "javaObjectName" );

         // Excel�ļ�����
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = null;

            workbook = ExportExcel4OrderBill.generateListExcelForOrderBillHeaderView( request, javaObjectName );

            // excel��
            String fileName = "";
            // ��ȡListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

            if ( listDTO != null )
            {
               fileName = listDTO.getListName( request );
            }

            if ( KANUtil.filterEmpty( fileName ) == null )
            {
               fileName = "���ļ�";
            }

            // ���ͻ��˴����ļ���
            download( response, workbook, fileName + ".xlsx" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   // ���isPosition == true ,���ǵ���λ��
   public ActionForward exportExcel4Branch( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Excel�ļ�����
         final XSSFWorkbook workbook = ListRender.generateBranchExcel( request );

         // ��ʼ��FileName
         final boolean isPosition = new Boolean( "isPosition".equalsIgnoreCase( ( String ) request.getAttribute( "isPosition" ) ) );
         final String fileName = isPosition ? "ְλ�ܹ�����excel" : "���żܹ�����excel";

         // ���ͻ��˴����ļ���
         //            download( response, workbook, fileName + ".xlsx" );
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//����д���ļ�
         response.getOutputStream().flush();
         response.getOutputStream().close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward exportExcel4EmployeePositionChange( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Excel�ļ�����
         final XSSFWorkbook workbook = UndefineExcelExport.generateEmployeePositionChange( request );

         final String fileName = "Employee Position Change";

         // ���ͻ��˴����ļ���
         //            download( response, workbook, fileName + ".xlsx" );
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//����д���ļ�
         response.getOutputStream().flush();
         response.getOutputStream().close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward exportExcel4EmployeeSalaryAdjustment( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Excel�ļ�����
         final XSSFWorkbook workbook = UndefineExcelExport.generateEmployeeSalaryAdjustment( request );

         final String fileName = "Employee Salary Adjustment";

         // ���ͻ��˴����ļ���
         response.setContentType( "application/x-msdownload" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//����д���ļ�
         response.getOutputStream().flush();
         response.getOutputStream().close();
      }
      catch ( final Exception e )
      {
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

   /**
    * Common download method
    * 
    * @param response
    * @param fileString
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final File file, final String fileName ) throws KANException
   {
      try
      {
         // ��ʼ��OutputStream
         response.reset();
         final OutputStream os = response.getOutputStream();

         // ���÷����ļ�����
         response.setContentType( "application/pdf" );

         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // PDF�ļ�д��OutputStream
         FileInputStream fileInput = new FileInputStream( file );
         int i = fileInput.available();
         byte[] content = new byte[ i ];
         fileInput.read( content );

         os.write( content );
         fileInput.close();

         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * ����ͨѶ¼
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @param isDTO
    * @return
    * @throws KANException
    */
   public ActionForward exportContacts( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ�ļ���
         final String fileName = ( String ) request.getAttribute( "fileName" );

         // ����Excel�ļ�
         final XSSFWorkbook workbook = UndefineExcelExport.generateContact( request );
         // ��ʼ��OutputStream
         final OutputStream os = response.getOutputStream();
         // ���÷����ļ�����
         response.setContentType( "application/x-msdownload" );
         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         // Excel�ļ�д��OutputStream
         workbook.write( os );
         // ���OutputStream
         os.flush();
         //�ر���  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward exportWXContacts( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "application/x-msdownload" );
         // ����ļ���������������
         response.setHeader( "Content-Disposition", "attachment;filename=contacts_wechat.csv" );
         // ����Excel�ļ�
         String content = UndefineExcelExport.exportWXContactCSV( request, response );
         // ���÷����ļ�����
         OutputStream os = response.getOutputStream();
         os.write( content.getBytes( "GBK" ) );
         os.flush();
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }
}
