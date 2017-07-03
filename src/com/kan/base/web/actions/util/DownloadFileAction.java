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
  * 项目名称：HRO_V1  
  * 类名称：DownloadFileAction  
  * 类描述：文件下载Action
  * 创建人：Kevin  
  * 创建时间：2013-8-8  
*/
public class DownloadFileAction extends BaseAction
{
   /**
    * 文件下载
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
         // 远程文件路径
         String fileString = request.getParameter( "fileString" );
         String fileName = request.getParameter( "fileName" );
         fileString = URLDecoder.decode( URLDecoder.decode( fileString, "UTF-8" ), "UTF-8" );

         // Share Folder Username, Password, IP, and Port
         final String username = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_USERNAME;
         final String password = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PASSWORD;
         final String host = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_HOST;
         final String port = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).SHAREFOLDER_PORT;

         // 远程文件字符串
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

         // 远程文件
         final SmbFile remoteFile = new SmbFile( remoteFileString );

         // 如果远程文件存在
         if ( remoteFile.exists() )
         {
            // 初始化InputStream
            final InputStream is = new BufferedInputStream( new SmbFileInputStream( remoteFile ) );

            // 往客户端传送文件流
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

      // 远程文件字符串
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

      // 远程文件
      try
      {
         final SmbFile remoteFile = new SmbFile( remoteFileString );

         // 如果远程文件存在
         if ( remoteFile.exists() )
         {
            // 初始化InputStream
            final InputStream is = new BufferedInputStream( new SmbFileInputStream( remoteFile ) );

            // 文件读取大小，字节
            byte buffer[] = new byte[ 10240 ];
            int length = 0;
            while ( ( length = is.read( buffer ) ) > 0 )
            {
               os.write( buffer, 0, length );
               os.flush();
            }
            //关闭流  
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
    * 列表导出
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
         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // 访问连接
         final String accessAction = request.getParameter( "accessAction" );

         // Excel文件类型
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = new XSSFWorkbook();

            workbook = ListRender.generateListExcel( request, accessAction );

            // 初始化ListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction ).getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

            // 初始化FileName
            final String fileName = listDTO != null ? listDTO.getListName( request ) : "新建列表";
            // 往客户端传送文件流
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
            // 最终写入文件
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
         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // 访问连接
         final String accessAction = request.getParameter( "accessAction" );

         // Excel文件类型
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 初始化FileName
            String fileName = "职位表";

            // 导出排列等级方式
            final String exportType = request.getParameter( "exportType" );

            if ( "branch".equals( exportType ) )
            {
               fileName = "职位表（按部门等级）";
               // 按部门层级关系导出“职位”
               workbook = ListRender.generatePositionListExcelByBranch( request, accessAction );
            }
            else if ( "position".equals( exportType ) )
            {
               fileName = "职位表（按职位等级）";
               // 按职位层级关系导出“职位”
               workbook = ListRender.generatePositionListExcelByPosition( request, accessAction );
            }

            // 往客户端传送文件流
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
            // 最终写入文件
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
    * 特殊列表导出
    * // addedExtendListDTO 导出结算单的时候，导出的列比显示的列多几项
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
         // 初始化corpId
         final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // java接口限定名
         final String javaObjectName = request.getParameter( "javaObjectName" );

         // 模板类型
         final String templateType = request.getParameter( "templateType" );

         // 模板ID
         final String templateId = request.getParameter( "templateId" );

         // Excel文件类型
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
            // excel名
            String fileName = "";

            // 如果是工资模板
            if ( "1".equals( templateType ) && KANUtil.filterEmpty( templateId, "0" ) != null )
            {
               // 获取BankTemplateDTO
               final BankTemplateDTO bankTemplateDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBankTemplateDTOByTemplateHeaderId( templateId );

               if ( bankTemplateDTO != null && bankTemplateDTO.getBankTemplateHeaderVO() != null )
               {
                  fileName = bankTemplateDTO.getBankTemplateName( request );
               }
            }
            // 如果是个税模板
            else if ( "2".equals( templateType ) && KANUtil.filterEmpty( templateId, "0" ) != null )
            {
               // 获取TaxTemplateDTO
               final TaxTemplateDTO taxTemplateDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTaxTemplateDTOByTemplateHeaderId( templateId );

               if ( taxTemplateDTO != null && taxTemplateDTO.getTaxTemplateHeaderVO() != null )
               {
                  fileName = taxTemplateDTO.getTaxTemplateName( request );
               }
            }
            // 如果没有模板
            else
            {
               // 获取ListDTO
               final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

               if ( listDTO != null )
               {
                  fileName = listDTO.getListName( request );
               }
            }

            if ( KANUtil.filterEmpty( fileName ) == null )
            {
               fileName = "新文件";
            }

            // 往客户端传送文件流
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
    * 导出导入模板
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
         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // 访问连接
         final String importHeadId = KANUtil.decodeString( request.getParameter( "importHeaderId" ) );

         // Excel文件类型
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            final XSSFWorkbook workbook = ImportRender.generateImportTemplateExcel( request, importHeadId );
            final String accountId = BaseAction.getAccountId( request, null );
            final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            final ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeadId, corpId );

            // 往客户端传送文件流
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
    * 报表导出
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
         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // 报表ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 访问连接
         String accessAction = request.getParameter( "accessAction" );

         if ( KANUtil.filterEmpty( accessAction ) == null )
         {
            final String tableId = request.getParameter( "tableId" );
            accessAction = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( tableId ).getTableVO().getAccessAction();
         }

         // Excel文件类型
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            final XSSFWorkbook workbook = ReportRender.generateReportExcel( request, accessAction, headerId );

            final String fileName = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction ).getReportDTOByReportHeaderId( headerId ).getReportName( request );

            // 往客户端传送文件流
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
    * 导出普通的列表
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
         // 获取文件名
         final String fileName = ( String ) request.getAttribute( "fileName" );

         // 生成Excel文件
         final XSSFWorkbook workbook = ListRender.generateCommonListExcel( request, fileName, isDTO );

         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();
         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );
         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         // Excel文件写入OutputStream
         workbook.write( os );
         // 输出OutputStream
         os.flush();
         //关闭流  
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
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // 文件读取大小，字节
         byte buffer[] = new byte[ 10240 ];
         int length = 0;
         while ( ( length = is.read( buffer ) ) > 0 )
         {
            os.write( buffer, 0, length );
            os.flush();
         }
         //关闭流  
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
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "UTF-8" ), "iso-8859-1" ) );
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // Excel文件写入OutputStream
         workbook.write( os );

         // 输出OutputStream
         os.flush();
         //关闭流  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Common download method
    * 使用SXSSFWorkbook 可解决数据量大内存溢出
    * @param response
    * @param SXSSFWorkbook
    * @param fileName
    * @throws KANException
    */
   public void download( final HttpServletResponse response, final SXSSFWorkbook workbook, final String fileName ) throws KANException
   {
      try
      {
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( fileName.getBytes( "UTF-8" ), "iso-8859-1" ) );
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // Excel文件写入OutputStream
         workbook.write( os );

         // 输出OutputStream
         os.flush();
         //关闭流  
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
         // 初始化OutputStream
         response.reset();
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/pdf" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // PDF文件写入OutputStream
         baos.writeTo( os );

         // 输出OutputStream
         os.flush();
         //关闭流  
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
         // 设置返回文件下载
         // 解决文件中文名下载问题
         //         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) );
         // 文件读取大小，字节
         byte buffer[] = new byte[ 10240 ];
         int length = 0;
         while ( ( length = is.read( buffer ) ) > 0 )
         {
            os.write( buffer, 0, length );
            os.flush();
         }
         //关闭流  
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
         // 初始化corpId
         final String corpId = KANUtil.filterEmpty( getCorpId( request, null ) );

         // 导出文件类型
         final String fileType = request.getParameter( "fileType" );

         // java接口限定名
         final String javaObjectName = request.getParameter( "javaObjectName" );

         // Excel文件类型
         if ( fileType != null && fileType.trim().equalsIgnoreCase( "excel" ) )
         {
            XSSFWorkbook workbook = null;

            workbook = ExportExcel4OrderBill.generateListExcelForOrderBillHeaderView( request, javaObjectName );

            // excel名
            String fileName = "";
            // 获取ListDTO
            final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

            if ( listDTO != null )
            {
               fileName = listDTO.getListName( request );
            }

            if ( KANUtil.filterEmpty( fileName ) == null )
            {
               fileName = "新文件";
            }

            // 往客户端传送文件流
            download( response, workbook, fileName + ".xlsx" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   // 如果isPosition == true ,则是导出位置
   public ActionForward exportExcel4Branch( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Excel文件类型
         final XSSFWorkbook workbook = ListRender.generateBranchExcel( request );

         // 初始化FileName
         final boolean isPosition = new Boolean( "isPosition".equalsIgnoreCase( ( String ) request.getAttribute( "isPosition" ) ) );
         final String fileName = isPosition ? "职位架构导出excel" : "部门架构导出excel";

         // 往客户端传送文件流
         //            download( response, workbook, fileName + ".xlsx" );
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//最终写入文件
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
         // Excel文件类型
         final XSSFWorkbook workbook = UndefineExcelExport.generateEmployeePositionChange( request );

         final String fileName = "Employee Position Change";

         // 往客户端传送文件流
         //            download( response, workbook, fileName + ".xlsx" );
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//最终写入文件
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
         // Excel文件类型
         final XSSFWorkbook workbook = UndefineExcelExport.generateEmployeeSalaryAdjustment( request );

         final String fileName = "Employee Salary Adjustment";

         // 往客户端传送文件流
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         workbook.write( response.getOutputStream() );//最终写入文件
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
         // 初始化OutputStream
         response.reset();
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/pdf" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );

         // PDF文件写入OutputStream
         FileInputStream fileInput = new FileInputStream( file );
         int i = fileInput.available();
         byte[] content = new byte[ i ];
         fileInput.read( content );

         os.write( content );
         fileInput.close();

         // 输出OutputStream
         os.flush();
         //关闭流  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 导出通讯录
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
         // 获取文件名
         final String fileName = ( String ) request.getAttribute( "fileName" );

         // 生成Excel文件
         final XSSFWorkbook workbook = UndefineExcelExport.generateContact( request );
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();
         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );
         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( fileName + ".xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
         // Excel文件写入OutputStream
         workbook.write( os );
         // 输出OutputStream
         os.flush();
         //关闭流  
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
         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=contacts_wechat.csv" );
         // 生成Excel文件
         String content = UndefineExcelExport.exportWXContactCSV( request, response );
         // 设置返回文件下载
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
