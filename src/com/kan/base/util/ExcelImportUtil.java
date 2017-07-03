package com.kan.base.util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.poi.ReadXlsxHander;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.util.poi.bean.XssfDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.UploadFileAction;

public class ExcelImportUtil
{

   /**
    * 
   *  ImportDB
   *  
   *  @param localFileString
   * @param request
   * @param importHeaderId
   * @param remark2
   *  @throws Exception
    */
   @SuppressWarnings("unchecked")
   public static void importDB( final String localFileString, final HttpServletRequest request, final String importHeaderId, final String remark2 ) throws Exception
   {
      // 初始化UserId，AccountId，corpId
      final String userId = BaseAction.getUserId( request, null );
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );

      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final String fileName = localFileString.substring( localFileString.lastIndexOf( "/" ) + 1, localFileString.length() );
      final String positionId = BaseAction.getPositionId( request, null );

      String accessAction = request.getParameter( "accessAction" );

      // 初始化ImportDTO
      final ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeaderId, corpId );

      if ( importDTO != null && importDTO.getImportDetailVOs() != null && importDTO.getImportDetailVOs().size() > 0 )
      {
         final List< List< CellDataDTO > > cellDataDTOPackages = new ArrayList< List< CellDataDTO > >();
         final List< ColumnVO > columnVOs = new ArrayList< ColumnVO >();

         new ReadXlsxHander()
         {
            // 初始化CellDataDTO
            List< CellDataDTO > cellDataDTOs = null;

            @Override
            public void optRows( int sheetIndex, int curRow, final List< CellData > row ) throws SQLException
            {
               // 默认只处理第一个Sheet对应数据
               try
               {
                  if ( sheetIndex == 0 )
                  {
                     if ( curRow % 500 == 0 )
                     {
                        cellDataDTOs = new ArrayList< CellDataDTO >();
                        cellDataDTOPackages.add( cellDataDTOs );

                        LogFactory.getLog( getClass() ).info( "Prepare Rows: " + curRow );
                     }
                     // 表头验证和匹配 
                     if ( curRow == 0 )
                     {
                        columnVOs.addAll( validateHeader( request, accountConstants, row, importDTO ) );
                     }
                     else
                     {
                        // 验证数据格式是否正确 并提示
                        if ( columnVOs != null && columnVOs.size() > 0 && !isEmptyRow( row ) )
                        {
                           cellDataDTOs.add( prepareCellDataDTO( accountConstants, columnVOs, row, corpId ) );
                        }
                     }
                  }
               }
               catch ( final Exception e )
               {
                  e.printStackTrace();
               }
            }
         }.process( localFileString );

         if ( columnVOs == null || cellDataDTOPackages == null || columnVOs.size() == 0 || cellDataDTOPackages.size() == 0
               || ( cellDataDTOPackages.size() > 0 && cellDataDTOPackages.get( 0 ).size() == 0 ) )
         {
            UploadFileAction.setStatusMsg( request, "1", "没有数据可以导入！", "2" );
            return;
         }

         for ( ColumnVO columnVO : columnVOs )
         {
            // 如果是选择框  { 2：下拉框，3：单选框，4：复选框}，加载选择项
            if ( columnVO != null
                  && KANUtil.filterEmpty( columnVO.getInputType() ) != null
                  && ( KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "2" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "3" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "4" ) ) )
            {
               columnVO.loadOptionValues( request );
            }
         }

         UploadFileAction.setStatusMsg( request, "3", "数据验证中..." );

         boolean validFlag = true;

         for ( List< CellDataDTO > cellDataDTOs : cellDataDTOPackages )
         {
            if ( !validateData( request, KANConstants.getKANAccountConstants( accountId ), cellDataDTOs, columnVOs ) )
            {
               validFlag = false;
            }
         }

         if ( validFlag )
         {
            // 装载AccountId，CorpId，UserId导入CellDataDTOs
            CellData accountIDCell = new CellData();
            ColumnVO accountIdColumn = new ColumnVO();
            accountIdColumn.setNameDB( "accountId" );
            accountIdColumn.setAccountId( "1" );
            accountIdColumn.setIsDBColumn( "1" );
            accountIDCell.setColumnVO( accountIdColumn );
            accountIDCell.setJdbcDataType( JDBCDataType.INT );
            accountIDCell.setDbValue( accountId );

            CellData corpIdCell = new CellData();
            ColumnVO corpIdColumn = new ColumnVO();
            corpIdColumn.setAccountId( "1" );
            corpIdColumn.setIsDBColumn( "1" );
            corpIdColumn.setNameDB( "corpId" );
            corpIdCell.setColumnVO( corpIdColumn );
            corpIdCell.setJdbcDataType( JDBCDataType.INT );
            corpIdCell.setDbValue( KANUtil.filterEmpty( corpId ) );

            CellData createByCell = new CellData();
            ColumnVO createByColumn = new ColumnVO();
            createByColumn.setAccountId( "1" );
            createByColumn.setIsDBColumn( "1" );
            createByColumn.setNameDB( "createBy" );
            createByCell.setColumnVO( createByColumn );
            createByCell.setJdbcDataType( JDBCDataType.INT );
            createByCell.setDbValue( userId );

            CellData modifyByCell = new CellData();
            ColumnVO modifyByColumn = new ColumnVO();
            modifyByColumn.setAccountId( "1" );
            modifyByColumn.setIsDBColumn( "1" );
            modifyByColumn.setNameDB( "modifyBy" );
            modifyByCell.setColumnVO( modifyByColumn );
            modifyByCell.setJdbcDataType( JDBCDataType.INT );
            modifyByCell.setDbValue( userId );

            // 初始化HandleCounts
            int handleCounts = 0;
            // 批次ID
            String batchId = null;
            // 导入是否成功
            boolean insertFlag = true;
            for ( int i = 0; i < cellDataDTOPackages.size(); i++ )
            {
               // 获取CellDataDTO List
               final List< CellDataDTO > cellDataDTOs = cellDataDTOPackages.get( i );

               for ( CellDataDTO cellDataDTO : cellDataDTOs )
               {
                  cellDataDTO.getCellDatas().add( accountIDCell );
                  cellDataDTO.getCellDatas().add( corpIdCell );
                  cellDataDTO.getCellDatas().add( createByCell );
                  cellDataDTO.getCellDatas().add( modifyByCell );
                  if ( cellDataDTO.getSubCellDataDTOs() != null )
                  {
                     for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
                     {
                        if ( subCellDataDTO != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
                        {
                           subCellDataDTO.getCellDatas().add( createByCell );
                           subCellDataDTO.getCellDatas().add( modifyByCell );
                        }
                     }
                  }
               }

               String handlerBeanId = importDTO.getImportHeaderVO().getHandlerBeanId();
               final ExcelImportHandler< List< ? >> excelImportHandler;
               if ( KANUtil.filterEmpty( handlerBeanId, "0" ) != null )
               {
                  excelImportHandler = ( ExcelImportHandler< List< ? >> ) ServiceLocator.getService( handlerBeanId );
               }
               else
               {
                  excelImportHandler = null;
               }
               if ( excelImportHandler != null && cellDataDTOs != null && cellDataDTOs.size() > 0 )
               {

                  excelImportHandler.init( cellDataDTOs );

                  validFlag = excelImportHandler.excuteBeforInsert( cellDataDTOs, request );

                  // 获得 "excuteBeforInsert" 之后额外信息
                  final String extraMessage = KANUtil.filterEmpty( getExtraMessage( request, cellDataDTOs ) );

                  if ( extraMessage != null )
                  {
                     UploadFileAction.setStatusMsg( request, "3", extraMessage, "3" );
                  }

               }
               // 插入到数据库
               if ( validFlag )
               {

                  handleCounts = handleCounts + cellDataDTOs.size();
                  if ( excelImportHandler != null && cellDataDTOs != null && cellDataDTOs.size() > 0 )
                  {
                     //重新分组
                     excelImportHandler.excuteRegroupmentBeforInsert( cellDataDTOs, request );
                  }

                  // 判断是否需要操作
                  int num = 0;

                  if ( cellDataDTOs != null && cellDataDTOs.size() > 0 )
                  {
                     for ( CellDataDTO cellDataDTO2 : cellDataDTOs )
                     {
                        if ( !cellDataDTO2.getNeedInsert() )
                        {
                           num++;
                        }
                     }
                  }

                  // 表内所有数据均已存在，只修改
                  if ( num == cellDataDTOs.size() )
                  {
                     UploadFileAction.setStatusMsg( request, "3", "已经更新" + handleCounts + "条信息", "0" );
                  }
                  else
                  {
                     batchId = insertDB( cellDataDTOs, accountId, userId, corpId, importDTO.getImportHeaderVO().getNeedBatchId(), fileName, accessAction, batchId, positionId, remark2 );

                     final String tempBatchId = batchId;
                     // 线程的方式执行
                     if ( excelImportHandler != null )
                     {
                        excelImportHandler.excueEndInsert( cellDataDTOs, tempBatchId );
                     }
                     else
                     {
                        cellDataDTOs.clear();
                     }
                     UploadFileAction.setStatusMsg( request, "3", "已经导入" + handleCounts + "条", "0" );

                     // 获得 "excueEndInsert" 之后额外信息
                     final String extraMessage = KANUtil.filterEmpty( getExtraMessage( request, cellDataDTOs ) );

                     if ( extraMessage != null )
                     {
                        UploadFileAction.setStatusMsg( request, "3", extraMessage, "3" );
                     }
                  }

                  LogFactory.getLog( ExcelImportUtil.class.getClass() ).info( "Update or Insert Rows: " + handleCounts );
               }
               //add by Jack
               else
               {
                  insertFlag = false;
                  uploadErrorMessage( request, cellDataDTOs );
               }
               //end by Jack
            }
            if ( insertFlag )
            {
               UploadFileAction.setStatusMsg( request, "4", handleCounts + "条导入成功！" );
            }
         }
         else
         {
            for ( List< CellDataDTO > cellDataDTOs : cellDataDTOPackages )
            {
               uploadErrorMessage( request, cellDataDTOs );
            }

         }
      }
   }

   /**
    * 
   *  Prepare CellData DTO
   *  
   *  @param columnVOs
   *  @param row
   *  @return
   *  @throws KANException
    */
   private static CellDataDTO prepareCellDataDTO( final KANAccountConstants accountConstants, final List< ColumnVO > columnVOs, final List< CellData > row, final String corpId )
         throws KANException
   {
      // 初始化CellDataDTO
      final CellDataDTO cellDataDTO = new CellDataDTO();

      for ( int i = 0; i < columnVOs.size(); i++ )
      {
         // 获取CellData和ColumnVO
         final CellData cell = row.get( i );
         final ColumnVO columnVO = columnVOs.get( i );
         // 初始化缓存CellDataDTO
         if ( columnVO != null )
         {
            CellDataDTO tempCellDataDTO = cellDataDTO;
            // 判断当前对象是否为新建
            boolean isNew = false;
            boolean isSub = false;

            // 主对象为空
            if ( KANUtil.filterEmpty( tempCellDataDTO.getTableId() ) == null )
            {
               isNew = true;
            }
            // 主对象不为空，但是跟当前数据表不匹配
            else if ( !tempCellDataDTO.getTableId().equals( columnVO.getTableId() ) )
            {
               // 存在从表数据
               if ( tempCellDataDTO.getSubCellDataDTOs() != null && tempCellDataDTO.getSubCellDataDTOs().size() > 0 )
               {
                  boolean found = false;

                  // 遍历从表数据
                  for ( CellDataDTO subCellDataDTO : tempCellDataDTO.getSubCellDataDTOs() )
                  {
                     // 从表中数据表相同，并且字段没有被重复添加
                     if ( KANUtil.filterEmpty( subCellDataDTO.getTableId() ) != null && subCellDataDTO.getTableId().equals( columnVO.getTableId() )
                           && !subCellDataDTO.containColumnId( columnVO.getColumnId() ) )
                     {
                        tempCellDataDTO = subCellDataDTO;
                        found = true;
                     }
                  }

                  if ( !found )
                  {
                     tempCellDataDTO = new CellDataDTO();
                     cellDataDTO.getSubCellDataDTOs().add( tempCellDataDTO );
                     isNew = true;
                  }
               }
               // 从表数据不存在
               else
               {
                  tempCellDataDTO = new CellDataDTO();
                  cellDataDTO.getSubCellDataDTOs().add( tempCellDataDTO );
                  isNew = true;
               }

               isSub = true;
            }

            // 新创建的DTO对象需要设置TableId和TableName
            if ( isNew )
            {
               tempCellDataDTO.setTableId( columnVO.getTableId() );
               tempCellDataDTO.setTableName( columnVO.getTableName() );

               String importHeadId = columnVO.getTempImportId();
               ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeadId, corpId );
               List< ? > dataObjects = null;
               String compareFieldName = null;
               JSONArray copyPropertyConfigs = null;

               try
               {
                  if ( importDTO != null && importDTO.getImportHeaderVO() != null )
                  {
                     String matchConfig = KANUtil.filterEmpty( importDTO.getImportHeaderVO().getMatchConfig() );
                     if ( matchConfig != null && !"0".equals( matchConfig ) )
                     {
                        String matchConfigValue = KANUtil.filterEmpty( KANUtil.getPropertiesValue( "/KAN-HRO-INF/import.properties", matchConfig ) );
                        if ( matchConfigValue != null )
                        {
                           JSONObject matchConfigObj = JSONObject.fromObject( matchConfigValue );
                           // 获得匹配数据源对象列表
                           JSONObject matchObjectsOfSource = ( JSONObject ) matchConfigObj.get( "matchObjectsOfSource" );
                           if ( matchObjectsOfSource != null )
                           {
                              String type = matchObjectsOfSource.getString( "type" );
                              String method = matchObjectsOfSource.getString( "method" );
                              JSONArray parameters = matchObjectsOfSource.getJSONArray( "parameters" );
                              Object[] parameterArray = ( Object[] ) parameters.toArray();
                              // 账户常量
                              if ( "2".equals( type ) )
                              {
                                 Class[] para = null;
                                 if ( parameterArray != null )
                                 {
                                    para = new Class[ parameterArray.length ];
                                    for ( int v = 0; v < parameterArray.length; v++ )
                                    {
                                       para[ v ] = String.class;
                                    }
                                 }
                                 Method getObjectsMethod;
                                 getObjectsMethod = accountConstants.getClass().getMethod( method, para );

                                 if ( getObjectsMethod != null )
                                 {
                                    dataObjects = ( List ) getObjectsMethod.invoke( accountConstants, parameterArray );

                                    // 

                                 }
                              }
                              // 系统常量
                              else if ( "1".equals( type ) )
                              {

                              }
                           }

                           // 获得匹配字段名
                           compareFieldName = matchConfigObj.getString( "compareFieldName" );
                           // 获得 备份匹配对象的属性的配置
                           copyPropertyConfigs = matchConfigObj.getJSONArray( "copyPropertyConfigs" );

                        }
                     }
                  }
               }
               catch ( final Exception e )
               {
                  throw new KANException( e );
               }

               // 从对象拓展字段
               if ( isSub && copyPropertyConfigs != null && copyPropertyConfigs.size() > 0 )
               {
                  Object targetObject = null;

                  if ( KANUtil.filterEmpty( compareFieldName ) != null && KANUtil.filterEmpty( columnVO.getNameZH() ) != null )
                  {
                     // ColumnVO中NameZH字段用于缓存Excel表头
                     final String decodeName = columnVO.getNameZH().split( "-" )[ 0 ];

                     for ( Object object : dataObjects )
                     {
                        final Object valueObject = KANUtil.getValue( object, compareFieldName );

                        if ( valueObject != null && ( ( String ) valueObject ).equalsIgnoreCase( decodeName ) )
                        {
                           targetObject = object;
                        }
                     }
                  }

                  // 复制对象的值
                  for ( int k = 0; k < copyPropertyConfigs.size(); k++ )
                  {
                     JSONObject copyProperty = copyPropertyConfigs.getJSONObject( k );
                     if ( copyProperty != null )
                     {
                        String columnName = copyProperty.getString( "columnName" );
                        String isForeignKey = copyProperty.getString( "isForeignKey" );
                        String jdbcDataType = copyProperty.getString( "jdbcDataType" );
                        CellData cellData = new CellData();
                        ColumnVO cellColumnVO = new ColumnVO();
                        try
                        {
                           cellColumnVO.setNameDB( columnName );
                           if ( !"1".equals( isForeignKey ) && targetObject != null )
                           {
                              cellColumnVO.setColumnId( BeanUtils.getProperty( targetObject, columnName ) );
                           }
                           cellColumnVO.setIsForeignKey( isForeignKey );
                           cellData.setJdbcDataType( JDBCDataType.INT.getValueByName( jdbcDataType ) );
                           cellColumnVO.setAccountId( "1" );
                           cellColumnVO.setIsDBColumn( "1" );
                           cellData.setColumnVO( cellColumnVO );

                        }
                        catch ( final Exception e )
                        {
                           throw new KANException( e );
                        }

                        if ( targetObject != null )
                        {
                           cellData.setDbValue( KANUtil.getValue( targetObject, cellData.getColumnVO().getNameDB() ) );
                        }

                        tempCellDataDTO.getCellDatas().add( cellData );
                     }
                  }
               }
            }

            // 单元格属性设置
            cell.setColumnIndex( String.valueOf( i ) );
            cell.setColumnVO( columnVO );

            // 添加单元格至列表
            tempCellDataDTO.getCellDatas().add( cell );
         }
      }

      return cellDataDTO;
   }

   /*
   private static CellDataDTO prepareCellDataDTO( final KANAccountConstants accountConstants, final List< ColumnVO > columnVOs, final List< CellData > row, final String corpId )
        throws KANException
   {
     // 初始化CellDataDTO
     final CellDataDTO cellDataDTO = new CellDataDTO();

     for ( int i = 0; i < columnVOs.size(); i++ )
     {
        // 获取CellData和ColumnVO
        final CellData cell = row.get( i );
        final ColumnVO columnVO = columnVOs.get( i );
        // 初始化缓存CellDataDTO
        if ( columnVO != null )
        {
           CellDataDTO tempCellDataDTO = cellDataDTO;
           // 判断当前对象是否为新建
           boolean isNew = false;

           // 主对象为空
           if ( KANUtil.filterEmpty( tempCellDataDTO.getTableId() ) == null )
           {
              isNew = true;
           }
           // 主对象不为空，但是跟当前数据表不匹配
           else if ( !tempCellDataDTO.getTableId().equals( columnVO.getTableId() ) )
           {
              // 存在从表数据
              if ( tempCellDataDTO.getSubCellDataDTOs() != null && tempCellDataDTO.getSubCellDataDTOs().size() > 0 )
              {
                 boolean found = false;

                 // 遍历从表数据
                 for ( CellDataDTO subCellDataDTO : tempCellDataDTO.getSubCellDataDTOs() )
                 {
                    // 从表中数据表相同，并且字段没有被重复添加
                    if ( KANUtil.filterEmpty( subCellDataDTO.getTableId() ) != null && subCellDataDTO.getTableId().equals( columnVO.getTableId() )
                          && subCellDataDTO.containColumnByName( columnVO ) )
                    {
                       tempCellDataDTO = subCellDataDTO;
                       found = true;
                       break;
                    }
                 }

                 if ( !found )
                 {
                    tempCellDataDTO = new CellDataDTO();
                    cellDataDTO.getSubCellDataDTOs().add( tempCellDataDTO );
                    isNew = true;
                 }
              }
              // 从表数据不存在
              else
              {
                 tempCellDataDTO = new CellDataDTO();
                 cellDataDTO.getSubCellDataDTOs().add( tempCellDataDTO );
                 isNew = true;
              }
           }

           // 新创建的DTO对象需要设置TableId和TableName
           if ( isNew )
           {
              tempCellDataDTO.setTableId( columnVO.getTableId() );
              tempCellDataDTO.setTableName( columnVO.getTableName() );
           }

           // 单元格属性设置
           cell.setColumnIndex( String.valueOf( i ) );
           cell.setColumnVO( columnVO );

           // 添加单元格至列表
           tempCellDataDTO.getCellDatas().add( cell );
        }
     }

     return cellDataDTO;
   }
   */

   /**
    * 
   *  Get Update SQL String
   *  
   *  @param cellDataDTO
   *  @param connection
   *  @return
   *  @throws KANException
    */
   private static String getUpdateSQLString( final CellDataDTO cellDataDTO, final Connection connection ) throws KANException
   {
      Statement statement = null;
      ResultSet res = null;
      try
      {

         boolean isUpdate = false;

         final CellData primaryKeyCellData = cellDataDTO.getPrimaryKeyCellData();

         if ( primaryKeyCellData != null )
         {
            final StringBuilder stringBuider = new StringBuilder();
            final String tableName = cellDataDTO.getTableName();

            stringBuider.append( "select count(0) from `" + tableName + "` where " );
            stringBuider.append( "`" + primaryKeyCellData.getColumnVO().getNameDB() + "` = '" + primaryKeyCellData.getDbValue() + "' " );
            stringBuider.toString();
            try
            {
               statement = connection.createStatement();
               res = statement.executeQuery( stringBuider.toString() );
               if ( res.next() )
               {
                  int count = res.getInt( 1 );
                  if ( count > 0 )
                  {
                     isUpdate = true;
                  }
               }
            }
            catch ( final Exception e )
            {
               throw new KANException( e );
            }
         }

         if ( isUpdate )
         {
            final StringBuilder sb = new StringBuilder();
            final StringBuilder whereSql = new StringBuilder();
            sb.append( "update `" + cellDataDTO.getTableName() + "` set " );
            int count = 0;
            // 初始化Map对象，用于装载自定义Column Value
            Map< String, String > defineColumnValues = new HashMap< String, String >();
            // 初始化数据库原有自定义Column Value
            Map< String, String > defineColumnValues_inDB = new HashMap< String, String >();

            // 拼updateSQL
            for ( CellData cell : cellDataDTO.getCellDatas() )
            {
               if ( cell != null && cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getIsDBColumn() ) && !"1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
               {

                  String dbsql = "null,";
                  String dbValue = null;

                  if ( KANUtil.filterEmpty( cell.getDbValue() ) == null )
                  {
                     dbsql = "null, ";
                  }
                  else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.DATE ) )
                  {
                     dbsql = "'" + KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) + "', ";
                     dbValue = KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" );
                  }
                  else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.NUMBER ) )
                  {
                     dbsql = cell.getDbValue() + ", ";
                     dbValue = cell.getDbValue().toString();
                  }
                  else
                  {
                     dbsql = "'" + cell.getDbValue() + "', ";
                     dbValue = cell.getDbValue().toString();
                  }

                  if ( cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getAccountId() ) )
                  {
                     sb.append( "`" + cell.getColumnVO().getNameDB() + "` = " + dbsql );
                  }
                  else
                  {
                     defineColumnValues.put( cell.getColumnVO().getNameDB(), dbValue );
                  }
                  count++;
               }
               else if ( cell != null && cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
               {
                  whereSql.append( "`" + cell.getColumnVO().getNameDB() + "` = '" + cell.getDbValue().toString() + "' and" );
               }
            }

            if ( count > 0 )
            {
               /**
                *  获得原有自定义字段数据库中对应数据
                */
               // 初始化查询数据库自定义字段语句
               final StringBuilder searchDefineColumnValuesInDB_sb = new StringBuilder();
               searchDefineColumnValuesInDB_sb.append( "select `remark1` from" );
               searchDefineColumnValuesInDB_sb.append( cellDataDTO.getTableName() );
               searchDefineColumnValuesInDB_sb.append( " where " ).append( whereSql.substring( 0, whereSql.length() - 4 ) );

               if ( KANUtil.filterEmpty( searchDefineColumnValuesInDB_sb.toString() ) != null )
               {
                  try
                  {
                     PreparedStatement preparedStatement = connection.prepareStatement( searchDefineColumnValuesInDB_sb.toString() );
                     ResultSet rs = preparedStatement.executeQuery();
                     if ( rs.next() )
                     {
                        defineColumnValues_inDB = JSONObject.fromObject( rs.getString( "'remark1'" ) );
                        // 处理自定义字段信息
                        defineColumnValues = handleDefineColumnValues( defineColumnValues_inDB, defineColumnValues );
                     }

                  }
                  catch ( SQLException e )
                  {
                     e.printStackTrace();
                  }
               }

               final JSONObject jsonObject = new JSONObject();
               jsonObject.putAll( defineColumnValues );

               // 针对劳动合同合同年薪加密
               String remark1 = jsonObject.toString();
               final CellData cellDataEmployeeId = cellDataDTO.getCellDataByColumnNameDB( "employeeId" );
               if ( cellDataEmployeeId != null && cellDataEmployeeId.getDbValue() != null && !"".equals( cellDataEmployeeId.getDbValue().toString().trim() ) )
               {
                  remark1 = Cryptogram.encodeAnnualRemuneration( jsonObject.toString(), cellDataEmployeeId.getDbValue().toString() );
               }

               sb.append( "`remark1` = '" + remark1 + "'" );
               sb.append( " where " ).append( whereSql.substring( 0, whereSql.length() - 4 ) );
               return sb.toString();
            }

         }
      }
      finally
      {
         try
         {
            if ( statement != null )
            {
               statement.close();

            }
            if ( res != null )
            {
               res.close();
            }
         }
         catch ( SQLException e )
         {
            e.printStackTrace();
         }
      }
      return null;

   }

   /**  
    * handleDefineColumnValues
    *	处理自定义导入字段
    *	@param defineColumnValues_inDB
    *	@param defineColumnValues_inExcel
    * Add By：Jack  
    * Add Date：2015-2-27  
    */
   private static Map< String, String > handleDefineColumnValues( final Map< String, String > defineColumnValues_inDB, final Map< String, String > defineColumnValues_inExcel )
   {
      // 初始化返回自定义字段
      Map< String, String > defineColumnValues_return = new HashMap< String, String >();

      if ( defineColumnValues_inDB != null && defineColumnValues_inDB.size() > 0 )
      {
         // 遍历自定义字段数据库已有数据
         Iterator< Entry< String, String >> it_inDB = defineColumnValues_inDB.entrySet().iterator();

         while ( it_inDB.hasNext() )
         {
            final Entry< String, String > entry = it_inDB.next();
            // 字段名
            final String columnNameDB = entry.getKey();
            // 字段值
            final String value_inDB = entry.getValue();

            // 如果数据库已有值
            if ( KANUtil.filterEmpty( value_inDB ) != null )
            {
               // 导入数据对应字段值
               final String value_inExcel = defineColumnValues_inExcel.get( columnNameDB );

               // 如果导入值有效
               if ( KANUtil.filterEmpty( value_inExcel ) != null )
               {
                  // 返回字段值以导入数据为主
                  defineColumnValues_return.put( columnNameDB, value_inExcel );
               }
               else
               {
                  // 返回字段值还是原来数据
                  defineColumnValues_return.put( columnNameDB, value_inDB );
               }

            }

         }

         // 遍历自定义字段导入值
         Iterator< Entry< String, String >> it_inExcel = defineColumnValues_inExcel.entrySet().iterator();

         while ( it_inExcel.hasNext() )
         {
            final Entry< String, String > entry = it_inExcel.next();
            // 字段名
            final String columnNameDB = entry.getKey();
            // 字段值（导入）
            final String value_InExcel = entry.getValue();

            if ( defineColumnValues_return.get( columnNameDB ) == null && KANUtil.filterEmpty( value_InExcel ) != null )
            {
               defineColumnValues_return.put( columnNameDB, value_InExcel );
            }

         }

      }

      return defineColumnValues_return;
   }

   /**
    * 
   *  Get Insert SQL String
   *  
   *  @param cellDataDTO
   *  @param accountId
   *  @param userId
   *  @param corpId
   *  @return
    */
   private static String getInsertSQLString( final CellDataDTO cellDataDTO, final String accountId, final String userId, final String corpId, final String batchId )
   {
      // 初始化SQL字符串
      final StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append( "insert into `" + cellDataDTO.getTableName() + "` ( " );

      //处理主键不自增
      CellData primaryKeyCell = cellDataDTO.getPrimaryKeyCellData();
      if ( primaryKeyCell != null && primaryKeyCell.getColumnVO() != null && "0".equals( primaryKeyCell.getColumnVO().getAutoIncrement() ) )
      {
         sqlBuffer.append( "`" + primaryKeyCell.getColumnVO().getNameDB() + "`, " );
      }

      if ( batchId != null )
      {
         sqlBuffer.append( "`batchId`," );
      }

      for ( CellData cell : cellDataDTO.getCellDatas() )
      {
         if ( cell != null && cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getIsDBColumn() ) && "1".equals( cell.getColumnVO().getAccountId() )
               && !"1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
         {
            sqlBuffer.append( "`" + cell.getColumnVO().getNameDB() + "`, " );
         }
      }

      // 公共字段处理
      sqlBuffer.append( "`createDate`, `modifyDate`, " );

      // Delete字段处理
      sqlBuffer.append( "`remark1`,`deleted` ) value ( " );

      //处理主键不自增
      if ( primaryKeyCell != null && primaryKeyCell.getColumnVO() != null && "0".equals( primaryKeyCell.getColumnVO().getAutoIncrement() ) )
      {
         sqlBuffer.append( "'" + primaryKeyCell.getDbValue() + "', " );
      }

      if ( batchId != null )
      {
         sqlBuffer.append( "'" + batchId + "'," );
      }

      // 初始化Map对象，用于装载自定义Column Value
      final Map< String, String > defineColumnValues = new HashMap< String, String >();
      for ( CellData cell : cellDataDTO.getCellDatas() )
      {
         if ( cell != null && cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getIsDBColumn() ) && !"1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
         {
            String dbsql = "null,";
            String dbValue = null;

            if ( KANUtil.filterEmpty( cell.getDbValue() ) == null )
            {
               dbsql = "null, ";
            }
            else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.DATE ) )
            {
               dbsql = "'" + KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) + "', ";
               dbValue = KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" );
            }
            else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.NUMBER ) )
            {
               dbsql = cell.getDbValue() + ", ";
               dbValue = cell.getDbValue().toString();
            }
            else
            {
               dbsql = "'" + cell.getDbValue() + "', ";
               dbValue = cell.getDbValue().toString();
            }

            if ( "1".equals( cell.getColumnVO().getAccountId() ) )
            {
               sqlBuffer.append( dbsql );
            }
            else
            {
               defineColumnValues.put( cell.getColumnVO().getNameDB(), dbValue );
            }
         }
      }

      final JSONObject jsonObject = new JSONObject();
      jsonObject.putAll( defineColumnValues );

      // 针对劳动合同合同年薪加密
      String remark1 = jsonObject.toString();
      final CellData cellDataEmployeeId = cellDataDTO.getCellDataByColumnNameDB( "employeeId" );
      if ( cellDataEmployeeId != null && cellDataEmployeeId.getDbValue() != null && !"".equals( cellDataEmployeeId.getDbValue().toString().trim() ) )
      {
         remark1 = Cryptogram.encodeAnnualRemuneration( jsonObject.toString(), cellDataEmployeeId.getDbValue().toString() );
      }

      // 公共字段处理
      sqlBuffer.append( " now(), now()," );
      sqlBuffer.append( "'" + remark1 + "'," );
      sqlBuffer.append( "1 )" );

      if ( batchId != null )
      {
         CellData batchIdCell = new CellData();
         ColumnVO batchIdColumn = new ColumnVO();
         batchIdColumn.setNameDB( "batchId" );
         batchIdColumn.setAccountId( "1" );
         batchIdColumn.setIsDBColumn( "1" );
         batchIdCell.setColumnVO( batchIdColumn );
         batchIdCell.setJdbcDataType( JDBCDataType.INT );
         batchIdCell.setDbValue( batchId );
         cellDataDTO.getCellDatas().add( batchIdCell );
      }

      LogFactory.getLog( ExcelImportUtil.class.getClass() ).info( "inser sql = " + sqlBuffer.toString() + ";" );
      return sqlBuffer.toString();
   }

   /**
    * 
   *  Get Sub SQL String
   *  
   *  @param cellDataDTO
   *  @param accountId
   *  @param userId
   *  @param corpId
   *  @param foreignKey
   *  @return
    */
   private static String getSubSQLString( final CellDataDTO cellDataDTO, final String foreignKey )
   {
      // 初始化SQL字符串
      final StringBuffer sqlBuffer = new StringBuffer();

      sqlBuffer.append( "insert into `" + cellDataDTO.getTableName() + "` ( " );

      //处理主键不自增
      CellData primaryKeyCell = cellDataDTO.getPrimaryKeyCellData();

      if ( primaryKeyCell != null && primaryKeyCell.getColumnVO() != null && "0".equals( primaryKeyCell.getColumnVO().getAutoIncrement() ) )
      {
         sqlBuffer.append( "`" + primaryKeyCell.getColumnVO().getNameDB() + "`, " );
      }

      for ( CellData cell : cellDataDTO.getCellDatas() )
      {
         if ( cell != null )
         {
            if ( cell.getColumnVO() != null && "1".equals( cell.getColumnVO().getAccountId() ) && !"1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
            {
               sqlBuffer.append( "`" + cell.getColumnVO().getNameDB() + "`, " );
            }
         }
      }

      boolean containStatus = true;

      // 如果数据集中不包含“状态”
      if ( !sqlBuffer.toString().contains( "`status`" ) )
      {
         sqlBuffer.append( "`status`, " );
         containStatus = false;
      }

      // 公共字段处理
      sqlBuffer.append( "`createDate`, `modifyDate`, `deleted`, `remark1` ) value ( " );
      // 初始化Map对象，用于装载自定义Column Value
      final Map< String, String > defineColumnValues = new HashMap< String, String >();

      //处理主键不自增
      if ( primaryKeyCell != null && primaryKeyCell.getColumnVO() != null && "0".equals( primaryKeyCell.getColumnVO().getAutoIncrement() ) )
      {
         sqlBuffer.append( "'" + primaryKeyCell.getDbValue() + "', " );
      }

      for ( CellData cell : cellDataDTO.getCellDatas() )
      {
         // 初始化DBValue
         Object dbObject = cell.getDbValue();
         String dbSql = "null,";
         String dbValue = null;

         // 外键处理
         if ( KANUtil.filterEmpty( cell.getColumnVO().getIsForeignKey() ) != null && cell.getColumnVO().getIsForeignKey().equals( "1" ) )
         {
            dbObject = foreignKey;
            dbSql = "'" + foreignKey + "', ";
         }
         else if ( KANUtil.filterEmpty( cell.getDbValue() ) == null )
         {
            dbSql = "null, ";
         }
         else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.DATE ) )
         {
            dbSql = "'" + KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) + "', ";
            dbValue = KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" );
         }
         else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.NUMBER ) )
         {
            dbSql = dbObject + ", ";
            dbValue = dbObject.toString();
         }
         else
         {
            dbSql = "'" + dbObject + "', ";
            dbValue = dbObject.toString();
         }

         if ( cell.getColumnVO() != null && !"1".equals( cell.getColumnVO().getIsPrimaryKey() ) )
         {
            if ( "1".equals( cell.getColumnVO().getAccountId() ) )
            {
               sqlBuffer.append( dbSql );
            }
            else
            {
               defineColumnValues.put( cell.getColumnVO().getNameDB(), dbValue );
            }
         }
      }

      final JSONObject jsonObject = new JSONObject();
      jsonObject.putAll( defineColumnValues );

      // 公共字段处理
      // 如果数据集中不包含“状态”
      if ( !containStatus )
      {
         sqlBuffer.append( "1, " );
      }

      // 针对劳动合同合同年薪加密
      String remark1 = jsonObject.toString();
      final CellData cellDataEmployeeId = cellDataDTO.getCellDataByColumnNameDB( "employeeId" );
      if ( cellDataEmployeeId != null && cellDataEmployeeId.getDbValue() != null && !"".equals( cellDataEmployeeId.getDbValue().toString().trim() ) )
      {
         remark1 = Cryptogram.encodeAnnualRemuneration( jsonObject.toString(), cellDataEmployeeId.getDbValue().toString() );
      }

      sqlBuffer.append( " now(), now(), 1, '" + remark1 + "')" );
      LogFactory.getLog( ExcelImportUtil.class.getClass() ).info( "inser subSql = " + sqlBuffer.toString() + ";" );
      return sqlBuffer.toString();
   }

   /**
    * 
   *  Validate Data
   *  
   *  @param request
   *  @param accountConstants
   *  @param cellDataDTOs
   *  @param columnVOs
   *  @return
   *  @throws KANException
    */
   private static boolean validateData( final HttpServletRequest request, final KANAccountConstants accountConstants, final List< CellDataDTO > cellDataDTOs,
         final List< ColumnVO > columnVOs ) throws KANException
   {
      // 验证标记
      boolean flag = true;

      // 遍历主对象
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {
         if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() >= 0 )
         {
            if ( !validateRow( request, accountConstants, cellDataDTO, columnVOs ) )
            {
               flag = false;
            }
         }

         if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() >= 0 )
         {
            // 遍历从对象
            for ( int i = cellDataDTO.getSubCellDataDTOs().size() - 1; i >= 0; i-- )
            {
               final CellDataDTO subCellDataDTO = cellDataDTO.getSubCellDataDTOs().get( i );

               // 从表所有数据都为空则删除掉从表数据
               if ( subCellDataDTO.getCellDatas() == null || isEmptyRow( subCellDataDTO.getCellDatas() ) )
               {
                  cellDataDTO.getSubCellDataDTOs().remove( i );
               }
               else
               {
                  if ( !validateRow( request, accountConstants, subCellDataDTO, columnVOs ) )
                  {
                     // 验证失败也删掉从表数据  
                     //这个不能删，出了bug太坑了，找不到原因。
                     /* cellDataDTO.getSubCellDataDTOs().remove( i );*/
                     flag = false;
                  }
               }
            }
         }
      }

      return flag;
   }

   private static boolean validateRow( final HttpServletRequest request, final KANAccountConstants accountConstants, final CellDataDTO cellDataDTO, final List< ColumnVO > columnVOs )
         throws KANException
   {
      boolean flag = true;

      int columnVOSize = columnVOs.size();
      CellData cellOwner = null;
      CellData cellBranch = null;
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null )
      {
         for ( CellData cell : cellDataDTO.getCellDatas() )
         {
            if ( cell.getColumnIndex() != null )
            {
               int columnIndex = Integer.valueOf( cell.getColumnIndex() );
               if ( columnVOSize > columnIndex )
               {
                  final ColumnVO columnVO = columnVOs.get( Integer.valueOf( cell.getColumnIndex() ) );
                  switch ( validateCell( request, accountConstants, cell, columnVO ) )
                  {
                     case 1:
                        cellOwner = cell;
                        break;
                     case -1:
                        flag = false;
                        break;
                     case 2:
                        cellBranch = cell;
                        break;

                     default:
                        break;
                  }
               }
            }
         }
      }

      // 特殊处理所属人，所属部门
      if ( cellBranch != null && cellOwner != null )
      {
         final String branchId = ( String ) cellBranch.getDbValue();

         final List< PositionVO > positionVOs = accountConstants.getPositionVOsByBranchId( branchId );
         for ( PositionVO positionVO : positionVOs )
         {
            final List< StaffVO > staffNameArray = accountConstants.getStaffArrayByPositionId( positionVO.getPositionId() );
            for ( StaffVO staffVO : staffNameArray )
            {
               String cellValue = cellOwner.getValue();
               if ( cellValue != null )
               {
                  String staffName = null;
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     staffName = staffVO.getNameZH();
                  }
                  else
                  {
                     staffName = staffVO.getNameEN();
                  }

                  if ( staffName != null && cellValue.trim().equals( staffName.trim() ) )
                  {
                     cellOwner.setDataType( XssfDataType.NUMBER );
                     cellOwner.setDbValue( positionVO.getPositionId() );
                     break;
                  }
               }
            }
         }
      }

      return flag;
   }

   /**
    * 
   *  Validate Cell
   *  
   *  @param request
   *  @param accountConstants
   *  @param cell
   *  @param columnVO
   *  @return  -1:验证出错,0:验证通过,1:所属人,2:所属部门
    */
   private static int validateCell( final HttpServletRequest request, final KANAccountConstants accountConstants, final CellData cell, final ColumnVO columnVO )
   {
      // 验证标记
      int flag = 0;
      Pattern pattern = Pattern.compile( "^(?:(?!0000)[0-9]{4}[-/\\.](?:(?:0[1-9]|1[0-2])[-/\\.](?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])[-/\\.](?:29|30)|(?:0[13578]|1[02])[-/\\.]31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)[-/\\.]02[-/\\.]29)(\\s+([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?)?$" );

      if ( "1".equals( columnVO.getIsIgnoreDefaultValidate() ) )
      {
         cell.setDbValue( cell.getValue() );
         return 0;
      }

      // 验证是否必填
      if ( KANUtil.filterEmpty( columnVO.getIsRequired() ) != null && columnVO.getIsRequired().equals( "1" ) )
      {
         if ( cell == null || KANUtil.filterEmpty( cell.getValue() ) == null )
         {
            cell.setErrorMessange( "[" + cell.getCellRef() + "]的值不能为空 ," );
            flag = -1;
         }
      }

      // 不为空才验证
      if ( cell != null && KANUtil.filterEmpty( cell.getValue() ) != null )
      {

         if ( cell.getValue() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
         {

            if ( cell.getValue().trim().matches( "(?!0000)[0-9]{4}[-/\\.](?:0[1-9]|1[0-2])" ) )
            {
               Date date = KANUtil.createDate( cell.getValue() );
               if ( validateTime( date ) < 0 )
               {
                  cell.setErrorMessange( "[" + cell.getCellRef() + "]的时间不在10年有效期范围内" );
                  flag = -1;
               }
            }
            else
            {
               cell.setErrorMessange( "[" + cell.getCellRef() + "]的时间不正确。" );
               flag = -1;
            }
         }

         // 是否是选择框  { 2：下拉框，3：单选框，4：复选框}
         boolean isSelect = KANUtil.filterEmpty( columnVO.getInputType() ) != null
               && ( KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "2" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "3" ) || KANUtil.filterEmpty( columnVO.getInputType() ).trim().equals( "4" ) );

         // 验证下拉框
         if ( isSelect )
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );

            if ( KANUtil.filterEmpty( cell.getValue() ) == null )
            {
               cell.setDbValue( "0" );
            }
            // 城市
            else if ( KANUtil.filterEmpty( columnVO.getSpecialField() ) != null && columnVO.getSpecialField().equals( "1" ) )
            {
               Map< String, String > cityMap = KANConstants.getCityMap( request.getLocale().getLanguage() );
               String cityName = cell.getValue() != null ? cell.getValue().replace( "市", "" ) : null;
               String cityId = cityMap.get( cityName );

               if ( cityId != null )
               {
                  cell.setDbValue( cityId );
               }
               else
               {
                  cell.setDbValue( "0" );
               }
            }
            // 所属人
            else if ( KANUtil.filterEmpty( columnVO.getSpecialField() ) != null && columnVO.getSpecialField().equals( "2" ) )
            {
               // 缓存起来，循环完成后再处理
               flag = 1;
            }
            else
            {
               final String value = columnVO.getOptionValueByOptionName( cell.getValue() );

               if ( KANUtil.filterEmpty( value ) != null && value.equals( "0" ) )
               {
               }

               cell.setDbValue( value );

               // 所属部门
               if ( columnVO.getNameDB().equalsIgnoreCase( "branch" ) )
               {
                  // 缓存起来，循环完成后再处理
                  flag = 2;
               }

            }
         }
         // 验证字段值的类型  --- 数字类型
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "1" ) )
         {
            cell.setJdbcDataType( JDBCDataType.NUMBER );

            if ( !KANUtil.round( cell.getValue(), accountConstants.OPTIONS_ACCURACY, accountConstants.OPTIONS_ROUND ).matches( "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,4})?$" ) )
            {
               cell.setErrorMessange( "[" + cell.getCellRef() + "]不是有效数值," );
               flag = -1;
            }
            else
            {
               // 初始化字段大小(数字)
               double maxRange = 0;
               if ( KANUtil.filterEmpty( columnVO.getValidateRangeMax() ) != null )
               {
                  maxRange = Double.parseDouble( columnVO.getValidateRangeMax() );
               }

               // 初始化字段大小(数字)
               double minRange = 0;
               if ( KANUtil.filterEmpty( columnVO.getValidateRangeMin() ) != null )
               {
                  minRange = Double.parseDouble( columnVO.getValidateRangeMin() );
               }

               if ( maxRange != minRange && maxRange != 0 )
               {
                  double cellValue = Double.parseDouble( cell.getValue() );
                  if ( cellValue > maxRange || cellValue < minRange )
                  {
                     cell.setErrorMessange( "[" + cell.getCellRef() + "]的值不在" + columnVO.getValidateRangeMin() + "和" + columnVO.getValidateRangeMax() + "之间," );
                     flag = -1;
                  }
               }

               cell.setDbValue( cell.getValue() );
            }

         }
         //  字段值的类型  --- 字符类型
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "2" ) )
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );

            // 初始化字段长度上限
            int maxlength = 0;
            if ( KANUtil.filterEmpty( columnVO.getValidateLengthMax() ) != null )
            {
               maxlength = Integer.parseInt( columnVO.getValidateLengthMax() );
            }

            // 初始化字段长度下限
            int minlength = 0;
            if ( KANUtil.filterEmpty( columnVO.getValidateLengthMin() ) != null )
            {
               minlength = Integer.parseInt( columnVO.getValidateLengthMin() );
            }

            if ( maxlength != minlength && maxlength != 0 )
            {
               int cellValueLength = cell.getValue().length();
               if ( cellValueLength > maxlength || cellValueLength < minlength )
               {
                  cell.setErrorMessange( "[" + cell.getCellRef() + "]的字符长度不在" + columnVO.getValidateRangeMin() + "和" + columnVO.getValidateRangeMax() + "之间," );
                  flag = -1;
               }

            }

            cell.setDbValue( cell.getValue() );
         }
         //  字段值的类型  --- 日期类型
         else if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getValueType().equals( "3" ) )
         {
            cell.setJdbcDataType( JDBCDataType.DATE );

            if ( cell.getDataType() == XssfDataType.NUMBER )
            {
               cell.setDbValue( KANUtil.formatDate( HSSFDateUtil.getJavaDate( Double.parseDouble( cell.getValue() ) ), "yyyy-MM-dd HH:mm:ss" ) );
               cell.setFormateValue( KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) );
               cell.setValue( KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) );
            }
            else
            {
               if ( KANUtil.filterEmpty( cell.getValue() ) != null )
               {
                  if ( pattern.matcher( cell.getValue().trim() ).matches() )
                  {
                     cell.setDbValue( KANUtil.createDate( cell.getValue() ) );
                  }
                  else
                  {
                     cell.setErrorMessange( "[" + cell.getCellRef() + "]的时间不正确。" );
                     flag = -1;
                  }
               }
            }
         }
         else
         {
            cell.setJdbcDataType( JDBCDataType.VARCHAR );
            cell.setDbValue( cell.getValue() );
         }
      }

      return flag;
   }

   /**
    * 
   *  Insert into DB
   *  
   *  @param cellDataDTOs
   *  @param accountId
   *  @param userId
   *  @param corpId
   *  @param needBatch
   *  @param fileName
   *  @throws KANException
   *  @throws InterruptedException
    */
   // Reviewed by Kevin Jin at 2014-04-10
   private static String insertDB( final List< CellDataDTO > cellDataDTOs, final String accountId, final String userId, final String corpId, final String needBatch,
         final String fileName, final String accessAction, String batchId, String positionId, String remark2 ) throws KANException, InterruptedException
   {
      // 获取连接池的连接
      final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
      // 插入记录数（主表）
      int insertCounts = 0;
      // 插入记录数（从表）
      int insertSubCounts = 0;
      // 获得数据库连接实例
      Connection connection = null;

      try
      {
         // 初始化数据库链接
         connection = dataSource.getConnection();
         // 开启事务
         connection.setAutoCommit( false );

         // 需要创建batch  的才创建Batch ， 判断是否为空的目的是包装多次循环式只有一个批次（需要配合外面额调用和重新赋值）
         if ( KANUtil.filterEmpty( batchId ) == null && KANUtil.filterEmpty( needBatch ) != null && needBatch.equals( "1" ) )
         {
            batchId = createCommonBatch( connection, accountId, userId, corpId, fileName, accessAction, positionId, remark2 );
         }

         // 遍历主对象
         for ( CellDataDTO cellDataDTO : cellDataDTOs )
         {
            String primaryKey = null;

            // 不需要操作
            if ( !cellDataDTO.getNeedInsert() )
            {
               continue;
            }

            // 生成Update语句
            final String updateSql = getUpdateSQLString( cellDataDTO, connection );

            if ( KANUtil.filterEmpty( updateSql ) != null )
            {
               final PreparedStatement preparedStatement = connection.prepareStatement( updateSql );
               preparedStatement.executeUpdate();

               //更新从对象Add by Jack
               updateDBSub( preparedStatement, cellDataDTO );
               if ( preparedStatement != null )
               {
                  preparedStatement.close();
               }

               // 回填主键
               CellData keyCellData = cellDataDTO.getPrimaryKeyCellData();
               if ( keyCellData != null && keyCellData.getDbValue() != null )
               {
                  primaryKey = keyCellData.getDbValue().toString();
               }
            }
            else
            {
               // 生成Insert语句
               final String sql = getInsertSQLString( cellDataDTO, accountId, userId, corpId, batchId );
               final PreparedStatement preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
               preparedStatement.executeUpdate();
               primaryKey = getPrimaryKey( preparedStatement );

               // 回填主键
               CellData keyCellData = cellDataDTO.getPrimaryKeyCellData();
               if ( keyCellData != null )
               {
                  keyCellData.setDbValue( primaryKey );
               }

               insertCounts++;

               // 插入从对象
               int insetSubCount = insertDBSub( preparedStatement, cellDataDTO, primaryKey );
               insertSubCounts = insertSubCounts + insetSubCount;
               if ( preparedStatement != null )
               {
                  preparedStatement.close();
               }

            }

            // 如果是员工导入，主键缓存
            if ( "HRO_BIZ_EMPLOYEE_IN_HOUSE".equals( accessAction ) )
            {
               CellData pkCacheCellData = new CellData();
               ColumnVO pkCacheColumnVO = new ColumnVO();

               pkCacheColumnVO.setNameDB( "pkCache" );
               pkCacheColumnVO.setIsForeignKey( "2" );
               pkCacheColumnVO.setAccountId( "1" );
               pkCacheColumnVO.setIsDBColumn( "1" );
               pkCacheCellData.setColumnVO( pkCacheColumnVO );
               pkCacheCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               pkCacheCellData.setDbValue( primaryKey );

               cellDataDTO.getCellDatas().add( pkCacheCellData );
            }
         }

         // 提交事务 - Finally
         connection.commit();

      }
      catch ( final Exception e )
      {
         try
         {
            // 回滚事务
            connection.rollback();
         }
         catch ( final Exception e1 )
         {
            throw new KANException( e );
         }

         throw new KANException( e );
      }
      finally
      {
         try
         {
            if ( connection != null )
            {
               connection.close();
            }
         }
         catch ( final Exception e1 )
         {
            throw new KANException( e1 );
         }
      }

      return batchId;
   }

   private static int insertDBSub( final PreparedStatement preparedStatement, final CellDataDTO cellDataDTO, String primaryKey ) throws SQLException
   {
      int insertSubCounts = 0;

      if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
      {
         // 遍历从对象
         for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
         {
            if ( subCellDataDTO != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
            {
               preparedStatement.addBatch( getSubSQLString( subCellDataDTO, primaryKey ) );
               insertSubCounts++;
            }
         }
      }

      preparedStatement.executeBatch();

      return insertSubCounts;
   }

   private static int updateDBSub( final PreparedStatement preparedStatement, final CellDataDTO cellDataDTO ) throws SQLException, KANException
   {
      int insertSubCounts = 0;

      if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
      {
         // 遍历从对象
         for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
         {
            preparedStatement.addBatch( getUpdateSQLString( subCellDataDTO, preparedStatement.getConnection() ) );
            insertSubCounts++;
         }
      }

      preparedStatement.executeBatch();

      return insertSubCounts;
   }

   /***
    * Validate Header
    *    
    * 匹配表头数据是不是和定义的相同
    * @param request
    * @param accountConstants 
    * @param row
    * @param importDTO
    * @return  表头数据对应的column
    * @throws KANException
    */
   public static List< ColumnVO > validateHeader( final HttpServletRequest request, final KANAccountConstants accountConstants, final List< CellData > row,
         final ImportDTO importDTO ) throws KANException
   {
      // 初始化ColumnVOs
      final List< ColumnVO > columnVOs = new ArrayList< ColumnVO >( row.size() );

      // 赋值
      for ( int i = 0; i < row.size(); i++ )
      {
         columnVOs.add( null );
      }

      // 初始化未匹配导入列表
      final List< ImportDetailVO > notMatchedImportDetailVOs = new ArrayList< ImportDetailVO >();
      final List< ImportDetailVO > notMatchedImportSubDetailVOs = new ArrayList< ImportDetailVO >();

      // 主表
      if ( importDTO.getImportDetailVOs() != null && importDTO.getImportDetailVOs().size() > 0 )
      {
         // 初始化TableDTO
         final TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDTO.getImportHeaderVO().getTableId() );

         for ( ImportDetailVO importDetailVO : importDTO.getImportDetailVOs() )
         {
            // 标记是否找到
            boolean flag = false;

            // 标记列的顺序
            int index = 0;
            for ( CellData cell : row )
            {
               if ( importDetailVO.getNameZH().toUpperCase().equals( cell.getValue().toUpperCase() )
                     || importDetailVO.getNameEN().toUpperCase().equals( cell.getValue().toUpperCase() ) )
               {
                  // 初始化ColumnVO
                  final ColumnVO cacheColumnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );
                  // 如果没有找到对应的Column则忽略该列字段
                  if ( cacheColumnVO == null )
                  {
                     break;
                  }
                  final ColumnVO columnVO = new ColumnVO();
                  try
                  {
                     BeanUtils.copyProperties( columnVO, cacheColumnVO );
                  }
                  catch ( final Exception e )
                  {
                     // Debug 
                     System.out.println( "##导入错误#error importDetailID = " + importDetailVO.getColumnId() + "  columnId=" + importDetailVO.getColumnId() );
                     e.printStackTrace();
                     break;
                  }

                  // 设置特殊值，NameZH复用（临时存放Excel表头字符串）
                  columnVO.setNameZH( importDetailVO.getNameZH() );
                  columnVO.setSpecialField( importDetailVO.getSpecialField() );

                  if ( "2".equals( tableDTO.getTableVO().getTableType() ) )
                  {
                     columnVO.setTableName( tableDTO.getTableVO().getAccessTempName() );
                  }
                  else
                  {
                     columnVO.setTableName( tableDTO.getTableVO().getAccessName() );
                  }

                  columnVO.setTempImportId( importDTO.getImportHeaderVO().getImportHeaderId() );
                  columnVO.setIsIgnoreDefaultValidate( importDetailVO.getIsIgnoreDefaultValidate() );
                  columnVO.setIsForeignKey( importDetailVO.getIsForeignKey() );

                  // 1必填 2不必填 3跟随系统
                  if ( "1".equals( importDetailVO.getIsIgnoreDefaultValidate() ) )
                  {
                     columnVO.setIsRequired( "1" );
                  }
                  else if ( "2".equals( importDetailVO.getIsIgnoreDefaultValidate() ) )
                  {
                     columnVO.setIsRequired( "2" );
                  }
                  columnVOs.set( index, columnVO );

                  flag = true;
                  break;
               }

               index++;
            }

            if ( !flag )
            {
               importDetailVO.setTableId( importDTO.getImportHeaderVO().getTableId() );
               notMatchedImportDetailVOs.add( importDetailVO );
            }
         }
      }

      // 从表
      if ( importDTO.getSubImportDTOs() != null && importDTO.getSubImportDTOs().size() > 0 )
      {
         for ( ImportDTO subImportDTO : importDTO.getSubImportDTOs() )
         {
            // 初始化TableDTO
            final TableDTO tableDTO = accountConstants.getTableDTOByTableId( subImportDTO.getImportHeaderVO().getTableId() );

            if ( subImportDTO.getImportDetailVOs() != null && subImportDTO.getImportDetailVOs().size() > 0 )
            {
               for ( ImportDetailVO importDetailVO : subImportDTO.getImportDetailVOs() )
               {
                  // 标记是否找到
                  boolean flag = false;

                  // 标记列的顺序
                  int index = 0;
                  for ( CellData cell : row )
                  {
                     if ( importDetailVO.getNameZH().toUpperCase().equals( cell.getValue().toUpperCase() )
                           || importDetailVO.getNameEN().toUpperCase().equals( cell.getValue().toUpperCase() ) )
                     {
                        // 初始化ColumnVO
                        final ColumnVO cacheColumnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );
                        final ColumnVO columnVO = new ColumnVO();
                        try
                        {
                           if ( cacheColumnVO != null )
                           {
                              BeanUtils.copyProperties( columnVO, cacheColumnVO );
                           }
                        }
                        catch ( final Exception e )
                        {
                           throw new KANException( e );
                        }

                        // 设置特殊值，NameZH复用（临时存放Excel表头字符串）
                        columnVO.setNameZH( importDetailVO.getNameZH() );
                        columnVO.setSpecialField( importDetailVO.getSpecialField() );
                        columnVO.setTableName( tableDTO.getTableVO().getAccessName() );
                        columnVO.setTempImportId( subImportDTO.getImportHeaderVO().getImportHeaderId() );
                        columnVO.setIsForeignKey( importDetailVO.getIsForeignKey() );
                        columnVO.setIsPrimaryKey( importDetailVO.getIsPrimaryKey() );
                        columnVOs.set( index, columnVO );

                        flag = true;
                        break;
                     }

                     index++;
                  }

                  if ( !flag )
                  {
                     importDetailVO.setTableId( subImportDTO.getImportHeaderVO().getTableId() );
                     notMatchedImportSubDetailVOs.add( importDetailVO );
                  }
               }
            }
         }
      }

      // 主表缺失列处理
      if ( notMatchedImportDetailVOs.size() > 0 )
      {
         // 初始化Error和Warning字符串
         final StringBuilder error = new StringBuilder();
         final StringBuilder warning = new StringBuilder();

         // 错误消息加入到消息
         for ( ImportDetailVO importDetailVO : notMatchedImportDetailVOs )
         {
            // 初始化TableDTO
            final TableDTO tableDTO = accountConstants.getTableDTOByTableId( importDetailVO.getTableId() );

            final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );

            // 验证是否必填
            if ( columnVO != null )
            {
               if ( KANUtil.filterEmpty( columnVO.getIsRequired() ) != null && columnVO.getIsRequired().equals( "1" ) )
               {
                  if ( "EN".equals( request.getLocale().getLanguage() ) )
                  {
                     error.append( importDetailVO.getNameEN() + "，" );
                  }
                  else
                  {
                     error.append( importDetailVO.getNameZH() + "，" );
                  }

               }
               else
               {
                  if ( "EN".equals( request.getLocale().getLanguage() ) )
                  {
                     warning.append( importDetailVO.getNameEN() + "，" );
                  }
                  else
                  {
                     warning.append( importDetailVO.getNameZH() + "，" );
                  }
               }
            }
         }

         if ( error.length() > 0 )
         {
            UploadFileAction.setStatusMsg( request, "1", "必填列[ " + error.toString() + " ]未找到！\n" + ( warning.length() > 0 ? ( "列[ " + warning.toString() + " ]缺失！\n" ) : "" ), "2" );
            columnVOs.clear();
            return columnVOs;
         }
         else
         {
            UploadFileAction.setStatusMsg( request, "3", warning.length() > 0 ? ( "列[ " + warning.toString() + " ]缺失！\n" ) : "", "1" );
         }
      }

      // 从表缺失列处理
      if ( notMatchedImportSubDetailVOs.size() > 0 )
      {
         // 忽略不管
      }

      return columnVOs;
   }

   // Reviewed by Kevin Jin at 2014-05-12
   public static String createCommonBatch( final Connection connection, final String accountId, final String userId, final String corpId, final String fileName,
         final String accessAction, final String positionId, final String remark2 ) throws SQLException
   {
      PreparedStatement preparedStatement = null;
      ResultSet rs = null;
      String primaryKey = null;
      try
      {
         // 初始化Primary Key

         // Batch SQL - Insert
         final String insertBatchSql = "INSERT INTO `hro_common_batch` (`accountId`, `corpId`, `accessAction`, `importExcelName`, `createBy`, `modifyBy`, `deleted`, `status`, `createDate`, `modifyDate`, `owner`, `remark2` ) "
               + "VALUES (?, ?, ?, ?, ?, ?, 1, 1, now(), now(), ?, ?)";

         // 初始化PreparedStatement
         preparedStatement = connection.prepareStatement( insertBatchSql, PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.setObject( 1, accountId );
         preparedStatement.setObject( 2, StringUtils.isBlank( corpId ) ? null : corpId );
         preparedStatement.setObject( 3, accessAction );
         preparedStatement.setObject( 4, fileName );
         preparedStatement.setObject( 5, userId );
         preparedStatement.setObject( 6, userId );
         preparedStatement.setObject( 7, positionId );
         //导入Excel时候录入的备注
         preparedStatement.setObject( 8, remark2 );
         preparedStatement.executeUpdate();

         // 获取主键
         rs = preparedStatement.getGeneratedKeys();

         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
      }
      finally
      {
         if ( preparedStatement != null )
         {
            preparedStatement.close();
         }
         if ( rs != null )
         {
            rs.close();
         }
      }
      return primaryKey;
   }

   /**
    * 更新错误消息到Catch
   *  uploadErrorMessage
    * @throws KANException 
   *
    */
   private static void uploadErrorMessage( final HttpServletRequest request, final List< CellDataDTO > cellDataDTOs ) throws KANException
   {
      //逻辑校验错误提示信息
      StringBuffer sb = new StringBuffer( "<br/>" );
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {

         //行级错误
         if ( StringUtils.isNotBlank( cellDataDTO.getErrorMessange() ) )
         {
            sb.append( cellDataDTO.getErrorMessange() );
            sb.append( "<br/>" );
         }

         //cell级别错误
         if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
         {
            for ( CellData cell : cellDataDTO.getCellDatas() )
            {
               if ( StringUtils.isNotBlank( cell.getErrorMessange() ) )
               {
                  sb.append( cell.getErrorMessange() );
                  sb.append( "<br/>" );
               }
            }
         }
         if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
         {
            for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               if ( subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
               {
                  for ( CellData subCell : subCellDataDTO.getCellDatas() )
                  {
                     if ( StringUtils.isNotBlank( subCell.getErrorMessange() ) )
                     {
                        sb.append( subCell.getErrorMessange() );
                        sb.append( "<br/>" );
                     }
                  }
               }
            }
         }
      }

      UploadFileAction.setStatusMsg( request, "1", sb.toString(), "2" );

   }

   /**  
    * getExtraMessage
    *	获取额外信息
    *	@param request
    *	@param cellDataDTOs
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-11  
    */
   private static String getExtraMessage( final HttpServletRequest request, final List< CellDataDTO > cellDataDTOs )
   {
      //逻辑校验错误提示信息
      StringBuffer sb = new StringBuffer( "" );
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {

         //行级额外信息
         if ( StringUtils.isNotBlank( cellDataDTO.getExtraMessage() ) )
         {
            sb.append( cellDataDTO.getExtraMessage() );
            sb.append( "<br/>" );
         }

         //cell级别额外信息
         if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
         {
            for ( CellData cell : cellDataDTO.getCellDatas() )
            {
               if ( StringUtils.isNotBlank( cell.getExtraMessage() ) )
               {
                  sb.append( cell.getExtraMessage() );
                  sb.append( "<br/>" );
               }
            }
         }
         if ( cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
         {
            for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               if ( subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
               {
                  for ( CellData subCell : subCellDataDTO.getCellDatas() )
                  {
                     if ( StringUtils.isNotBlank( subCell.getExtraMessage() ) )
                     {
                        sb.append( subCell.getExtraMessage() );
                        sb.append( "<br/>" );
                     }
                  }
               }
            }
         }
      }

      // 移除最后的 "<br/>"
      if ( KANUtil.filterEmpty( sb.toString() ) != null )
      {
         sb.substring( 0, sb.length() - 5 );
      }

      return sb.toString();
   }

   public static boolean isEmptyRow( List< CellData > row )
   {
      boolean flag = true;

      for ( CellData cell : row )
      {
         if ( cell != null && KANUtil.filterEmpty( cell.getValue() ) != null && !cell.getValue().trim().startsWith( "*" ) )
         {
            flag = false;
            break;
         }
      }
      return flag;
   }

   public static String getPrimaryKey( final PreparedStatement preparedStatement ) throws SQLException
   {
      ResultSet rs = preparedStatement.getGeneratedKeys();
      if ( rs.next() )
      {
         return rs.getString( 1 );
      }
      return null;
   }

   public static List< String > getPrimaryKeys( final Statement statement ) throws SQLException
   {
      List< String > list = new ArrayList< String >();
      ResultSet rs = statement.getGeneratedKeys();
      int i = 1;
      if ( rs.next() )
      {
         list.add( rs.getString( i++ ) );
      }
      return list;
   }

   public static int validateTime( Date dateValue )
   {

      Calendar currentDateValue = Calendar.getInstance();
      currentDateValue.setTime( dateValue );

      Calendar min = Calendar.getInstance();
      min.set( Calendar.YEAR, min.get( Calendar.YEAR ) - 10 );

      Calendar max = Calendar.getInstance();
      max.set( Calendar.YEAR, max.get( Calendar.YEAR ) + 10 );

      if ( currentDateValue.getTimeInMillis() < min.getTimeInMillis() || currentDateValue.getTimeInMillis() > max.getTimeInMillis() )
      {
         return -1;
      }

      return 1;
   }
}
