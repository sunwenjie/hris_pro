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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );

         // ���Action Form
         final EmployeeContractImportBatchVO employeeContactImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContactImportBatchVO.setAccessAction( EmployeeContractAction.getAccessAction( request, response ) );
         employeeContactImportBatchVO.setRemark4( null );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeContactImportBatchVO.getSortColumn() == null || employeeContactImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeContactImportBatchVO.setSortOrder( "desc" );
            employeeContactImportBatchVO.setSortColumn( "batchId" );
         }

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction,employeeContactImportBatchVO );
         setDataAuth( request, response, employeeContactImportBatchVO );

         // ����subAction
         dealSubAction( employeeContactImportBatchVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContactImportBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractImportBatchService.getEmployeeContractImportBatchVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeContractBatchHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmplyeeContractBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listEmplyeeContractBatch" );
   }

   /**
    * ���£�ֱ��update��ʽ����Ľ��
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
         // ��ȡ������Դ
         final String comeFrom = request.getParameter( "comeFrom" );

         // ��ȡActionForm
         final EmployeeContractImportBatchVO employeeContractImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContractImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��ù�ѡID
         final String batchIds = employeeContractImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
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
    * �˻�,����ɾ��temp��
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
         // ��ȡ������Դ
         final String comeFrom = request.getParameter( "comeFrom" );

         // ��ȡActionForm
         final EmployeeContractImportBatchVO employeeContractImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContractImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��ù�ѡID
         final String batchIds = employeeContractImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeContractImportBatchVO submitObject = employeeContractImportBatchService.getEmployeeContractImportBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + employeeContractTempService.rollbackBatch( submitObject );
            }

            success( request, MESSAGE_TYPE_SUBMIT, "�˻�" );

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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final EmployeeContractImportBatchService employeeContractImportBatchService = ( EmployeeContractImportBatchService ) getService( "employeeContractImportBatchService" );

         // ���Action Form
         final EmployeeContractImportBatchVO employeeContactImportBatchVO = ( EmployeeContractImportBatchVO ) form;
         employeeContactImportBatchVO.setAccessAction( EmployeeContractAction.getAccessAction( request, response ) );
         employeeContactImportBatchVO.setRemark4( "2" );

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeContactImportBatchVO.getSortColumn() == null || employeeContactImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeContactImportBatchVO.setSortOrder( "desc" );
            employeeContactImportBatchVO.setSortColumn( "batchId" );
         }

         //��������Ȩ��
         setDataAuth( request, response, employeeContactImportBatchVO );

         // ����subAction
         dealSubAction( employeeContactImportBatchVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContactImportBatchVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractImportBatchService.getEmployeeContractImportBatchVOsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeContractBatchHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmplyeeContractBatchUpdateTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listEmplyeeContractBatchUpdate" );
   }

   /***
    * downloadBatchUpdateTemplate ������������ģ��
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
         // ��ȡ�Ͷ���ͬ��TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByAccessAction( EmployeeContractAction.getAccessAction( request, response ) );

         final ColumnVO pKeyColumnVO = tableDTO.getColumnVOByNameDB( "contractId" );
         final ColumnVO employeeNameZHColumnVO = tableDTO.getColumnVOByNameDB( "employeeNameZH" );
         final ColumnVO employeeNameENColumnVO = tableDTO.getColumnVOByNameDB( "employeeNameEN" );

         // ��ʼ��������
         final XSSFWorkbook workbook = new XSSFWorkbook();
         // ����������Sheet����ģ����Ҫ����
         final XSSFSheet sheet1 = workbook.createSheet();
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet1.setDefaultColumnWidth( 15 );

         // ��һ����Title
         final XSSFRow sheet1RowHeader = sheet1.createRow( 0 );
         // ��һ�е�һ�з�����
         final XSSFCell cellPKey = sheet1RowHeader.createCell( 0 );
         cellPKey.setCellValue( getColumnName( pKeyColumnVO, request ) );
         // ��һ�еڶ���
         final XSSFCell cellEmployeeNameZH = sheet1RowHeader.createCell( 1 );
         cellEmployeeNameZH.setCellValue( getColumnName( employeeNameZHColumnVO, request ) );
         // ��һ�е�����
         final XSSFCell cellEmployeeNameEN = sheet1RowHeader.createCell( 2 );
         cellEmployeeNameEN.setCellValue( getColumnName( employeeNameENColumnVO, request ) );

         // �����ڶ���Sheet�����tableDTO����column
         final XSSFSheet sheet2 = workbook.createSheet();
         // ��һ����Title
         final XSSFRow sheet2RowHeader = sheet2.createRow( 0 );

         final XSSFRow sheet2RowBody = sheet2.createRow( 1 );

         int sheet2RowHeaderColumnIndex = 0;
         // ����TableDTO
         if ( tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
         {
            for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
            {
               // ����Column List
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
                        // ��ȡ�������Ӧ��ֵ
                        final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( request.getLocale(), columnVO, getAccountId( request, response ), getCorpId( request, response ) );
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           List< String > matchTextList = new ArrayList< String >();
                           // ������Ч������
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

                           // ���ø�sheet��������Ч��
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

         // ���ͻ��˴����ļ���
         new DownloadFileAction().download( response, workbook, "batch update.xlsx" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   private String getColumnName( final ColumnVO columnVO, final HttpServletRequest request )
   {
      // ��ʼ��Clomun Name
      String columnName = "";
      if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
      {
         columnName = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
      }
      else
      {
         columnName = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
      }
      // ���ColumnNameΪ�գ�ʹ��Column��ϵͳ����ʼ��
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
         // ��ʼ��DataValidationHelper
         final DataValidationHelper helper = sheet2.getDataValidationHelper();
         // ���������б�����    
         final DataValidationConstraint constraint = helper.createExplicitListConstraint( textList );
         // ���������Ч�Գ��ȴ���excel������Ч����Դ���������
         if ( ArrayUtils.toString( textList ).length() >= 255 )
         {
            // ��Դ��ĸ���
            final int currSubSheetIndex = workbook.getNumberOfSheets() + 1;
            // ��ȡXSSFWorkbook�ӱ�
            final XSSFSheet subSheet = workbook.createSheet( "Sheet" + currSubSheetIndex );
            // ����һ�С���һ�����title
            subSheet.createRow( 0 ).createCell( 0 ).setCellValue( sourceListTitle );
            // ��row�С���tempColumnIndex�������
            for ( int row = 0; row < textList.length; row++ )
            {
               subSheet.createRow( row + 1 ).createCell( 0 ).setCellValue( textList[ row ] );
            }
            // ������Դ����Sheet1
            constraint.setFormula1( "Sheet" + currSubSheetIndex + "!A2:A" + ( textList.length + 1 ) );
         }
         else
         {
            // ������Դ�����ַ�����
            constraint.setExplicitListValues( textList );
         }

         // ����������Ч�Լ������ĸ���Ԫ���ϣ� �ĸ������ֱ��ǣ���ʼ�С���ֹ�С���ʼ�С���ֹ��    
         final CellRangeAddressList regions = new CellRangeAddressList( ( short ) firstRow, ( short ) endRow, ( short ) firstCol, ( short ) endCol );

         // ����������Ч�Զ���    
         final DataValidation data_validation = helper.createValidation( constraint, regions );
         sheet2.addValidationData( data_validation );
      }
   }

}
