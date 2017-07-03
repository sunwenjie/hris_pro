package com.kan.hro.web.actions.biz.importExcel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeContractImportBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractImportBatchService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractTempService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

public class EmployeeContractBatchAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_BATCH_IMPORT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );

         // 获得Action Form
         final EmployeeContractImportBatchVO employeeContactImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContactImportBatchVO.setAccessAction( EmployeeContractAction.getAccessAction( request, response ) );
         employeeContactImportBatchVO.setRemark4( null );

         // 如果没有指定排序则默认按 batchId排序
         if ( employeeContactImportBatchVO.getSortColumn() == null || employeeContactImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeContactImportBatchVO.setSortOrder( "desc" );
            employeeContactImportBatchVO.setSortColumn( "batchId" );
         }

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction,employeeContactImportBatchVO );
         setDataAuth( request, response, employeeContactImportBatchVO );

         // 处理subAction
         dealSubAction( employeeContactImportBatchVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeContactImportBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractImportBatchService.getEmployeeContractImportBatchVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeContractBatchHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmplyeeContractBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listEmplyeeContractBatch" );
   }

   /**
    * 更新，直接update正式表里的金额
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取请求来源
         final String comeFrom = request.getParameter( "comeFrom" );

         // 获取ActionForm
         final EmployeeContractImportBatchVO employeeContractImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContractImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得勾选ID
         final String batchIds = employeeContractImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeContractImportBatchVO submitObject = employeeContractImportBatchService.getEmployeeContractImportBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setIp( getIPAddress( request ) );
               rows = rows + employeeContractTempService.updateBatch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractImportBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( batchIds ) );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractImportBatchVO, Operate.MODIFY, null, KANUtil.decodeSelectedIds( batchIds ) );
            }

         }

         if ( KANUtil.filterEmpty( comeFrom ) != null )
         {
            return list_batch( mapping, form, request, response );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * 退回,物理删除temp表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取请求来源
         final String comeFrom = request.getParameter( "comeFrom" );

         // 获取ActionForm
         final EmployeeContractImportBatchVO employeeContractImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContractImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得勾选ID
         final String batchIds = employeeContractImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeContractImportBatchVO submitObject = employeeContractImportBatchService.getEmployeeContractImportBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + employeeContractTempService.rollbackBatch( submitObject );
            }

            success( request, MESSAGE_TYPE_SUBMIT, "退回" );

            insertlog( request, employeeContractImportBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( batchIds ) );
         }

         if ( KANUtil.filterEmpty( comeFrom ) != null )
         {
            return list_batch( mapping, form, request, response );
         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   public ActionForward list_batch( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );

         // 获得Action Form
         final EmployeeContractImportBatchVO employeeContactImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContactImportBatchVO.setAccessAction( EmployeeContractAction.getAccessAction( request, response ) );
         employeeContactImportBatchVO.setRemark4( "2" );

         // 如果没有指定排序则默认按 batchId排序
         if ( employeeContactImportBatchVO.getSortColumn() == null || employeeContactImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeContactImportBatchVO.setSortOrder( "desc" );
            employeeContactImportBatchVO.setSortColumn( "batchId" );
         }

         //处理数据权限
         setDataAuth( request, response, employeeContactImportBatchVO );

         // 处理subAction
         dealSubAction( employeeContactImportBatchVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeContactImportBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractImportBatchService.getEmployeeContractImportBatchVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeContractBatchHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmplyeeContractBatchUpdateTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listEmplyeeContractBatchUpdate" );
   }

   /***
    * downloadBatchUpdateTemplate 下载批量更新模板
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void downloadBatchUpdateTemplate( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取劳动合同的TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByAccessAction( EmployeeContractAction.getAccessAction( request, response ) );

         final ColumnVO pKeyColumnVO = tableDTO.getColumnVOByNameDB( "contractId" );
         final ColumnVO employeeNameZHColumnVO = tableDTO.getColumnVOByNameDB( "employeeNameZH" );
         final ColumnVO employeeNameENColumnVO = tableDTO.getColumnVOByNameDB( "employeeNameEN" );

         // 初始化工作薄
         final XSSFWorkbook workbook = new XSSFWorkbook();
         // 创建表格，这个Sheet放置模板主要内容
         final XSSFSheet sheet1 = workbook.createSheet();
         // 设置表格默认列宽度为15个字节
         sheet1.setDefaultColumnWidth( 15 );

         // 第一行是Title
         final XSSFRow sheet1RowHeader = sheet1.createRow( 0 );
         // 第一行第一列放主键
         final XSSFCell cellPKey = sheet1RowHeader.createCell( 0 );
         cellPKey.setCellValue( getColumnName( pKeyColumnVO, request ) );
         // 第一行第二列
         final XSSFCell cellEmployeeNameZH = sheet1RowHeader.createCell( 1 );
         cellEmployeeNameZH.setCellValue( getColumnName( employeeNameZHColumnVO, request ) );
         // 第一行第三列
         final XSSFCell cellEmployeeNameEN = sheet1RowHeader.createCell( 2 );
         cellEmployeeNameEN.setCellValue( getColumnName( employeeNameENColumnVO, request ) );

         // 创建第二个Sheet，存放tableDTO所有column
         final XSSFSheet sheet2 = workbook.createSheet();
         // 第一行是Title
         final XSSFRow sheet2RowHeader = sheet2.createRow( 0 );

         final XSSFRow sheet2RowBody = sheet2.createRow( 1 );

         int sheet2RowHeaderColumnIndex = 0;
         // 遍历TableDTO
         if ( tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
         {
            for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
            {
               // 遍历Column List
               if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
               {
                  for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
                  {
                     final XSSFCell cellHeader = sheet2RowHeader.createCell( sheet2RowHeaderColumnIndex );
                     cellHeader.setCellValue( getColumnName( columnVO, request ) );

                     final XSSFCell cellBody = sheet2RowBody.createCell( sheet2RowHeaderColumnIndex );
                     if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "2" ) )
                     {
                        String defualtValue = "";
                        // 获取下拉框对应的值
                        final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( request.getLocale(), columnVO, getAccountId( request, response ), getCorpId( request, response ) );
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           List< String > matchTextList = new ArrayList< String >();
                           // 数据有效性设置
                           for ( int j = 0; j < mappingVOs.size(); j++ )
                           {
                              MappingVO mappingVO = mappingVOs.get( j );
                              if ( !"0".equals( mappingVO.getMappingId() ) )
                              {
                                 matchTextList.add( mappingVO.getMappingValue() );
                              }
                              if ( j == 1 )
                              {
                                 defualtValue = mappingVO.getMappingValue();
                              }
                           }
                           // to array
                           String[] matchTextArray = new String[ matchTextList.size() ];
                           for ( int j = 0; j < matchTextArray.length; j++ )
                           {
                              matchTextArray[ j ] = matchTextList.get( j );
                           }

                           // 设置该sheet的数据有效性
                           SetDataValidation( workbook, sheet2, matchTextArray, 1, sheet2RowHeaderColumnIndex, 1000, sheet2RowHeaderColumnIndex, ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? columnVO.getNameZH()
                                 : columnVO.getNameEN() ) );
                        }

                        cellBody.setCellValue( defualtValue );
                     }

                     sheet2RowHeaderColumnIndex++;
                  }
               }
            }
         }

         // 往客户端传送文件流
         new DownloadFileAction().download( response, workbook, "batch update.xlsx" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   private String getColumnName( final ColumnVO columnVO, final HttpServletRequest request )
   {
      // 初始化Clomun Name
      String columnName = "";
      if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
      {
         columnName = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
      }
      else
      {
         columnName = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
      }
      // 如果ColumnName为空，使用Column的系统名初始化
      if ( columnName == null || columnName.equals( "" ) )
      {
         columnName = columnVO.getNameSys();
      }

      return columnName;
   }

   public static void SetDataValidation( final XSSFWorkbook workbook, final XSSFSheet sheet2, final String[] textList, int firstRow, int firstCol, int endRow, int endCol,
         final String sourceListTitle )
   {
      if ( textList != null && textList.length > 0 )
      {
         // 初始化DataValidationHelper
         final DataValidationHelper helper = sheet2.getDataValidationHelper();
         // 加载下拉列表内容    
         final DataValidationConstraint constraint = helper.createExplicitListConstraint( textList );
         // 如果数据有效性长度大于excel数据有效性来源的最大限制
         if ( ArrayUtils.toString( textList ).length() >= 255 )
         {
            // 资源表的个数
            final int currSubSheetIndex = workbook.getNumberOfSheets() + 1;
            // 获取XSSFWorkbook子表
            final XSSFSheet subSheet = workbook.createSheet( "Sheet" + currSubSheetIndex );
            // 给第一行、第一列添加title
            subSheet.createRow( 0 ).createCell( 0 ).setCellValue( sourceListTitle );
            // 给row行、第tempColumnIndex添加数据
            for ( int row = 0; row < textList.length; row++ )
            {
               subSheet.createRow( row + 1 ).createCell( 0 ).setCellValue( textList[ row ] );
            }
            // 数据来源引用Sheet1
            constraint.setFormula1( "Sheet" + currSubSheetIndex + "!A2:A" + ( textList.length + 1 ) );
         }
         else
         {
            // 数据来源引用字符数据
            constraint.setExplicitListValues( textList );
         }

         // 设置数据有效性加载在哪个单元格上， 四个参数分别是：起始行、终止行、起始列、终止列    
         final CellRangeAddressList regions = new CellRangeAddressList( ( short ) firstRow, ( short ) endRow, ( short ) firstCol, ( short ) endCol );

         // 创建数据有效性对象    
         final DataValidation data_validation = helper.createValidation( constraint, regions );
         sheet2.addValidationData( data_validation );
      }
   }

}
