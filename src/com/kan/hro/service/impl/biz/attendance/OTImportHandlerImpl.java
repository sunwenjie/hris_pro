package com.kan.hro.service.impl.biz.attendance;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderOTDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;

public class OTImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   private EmployeeDao employeeDao;

   private OTHeaderDao otHeaderDao;

   private OTDetailDao otDetailDao;

   private EmployeeContractOTDao employeeContractOTDao;

   private EmployeeContractDao employeeContractDao;

   private ClientOrderHeaderDao clientOrderHeaderDao;

   private ClientOrderOTDao clientOrderOTDao;

   private EmployeeContractOTService employeeContractOTService;

   private List< MappingVO > itemList;

   private Map< String, EmployeeContractVO > employeeContractVOMap = new HashMap< String, EmployeeContractVO >();

   private Map< String, ClientOrderHeaderVO > clientOrderHeaderVOMap = new HashMap< String, ClientOrderHeaderVO >();

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public OTHeaderDao getOtHeaderDao()
   {
      return otHeaderDao;
   }

   public void setOtHeaderDao( OTHeaderDao otHeaderDao )
   {
      this.otHeaderDao = otHeaderDao;
   }

   public OTDetailDao getOtDetailDao()
   {
      return otDetailDao;
   }

   public void setOtDetailDao( OTDetailDao otDetailDao )
   {
      this.otDetailDao = otDetailDao;
   }

   public EmployeeContractOTDao getEmployeeContractOTDao()
   {
      return employeeContractOTDao;
   }

   public void setEmployeeContractOTDao( EmployeeContractOTDao employeeContractOTDao )
   {
      this.employeeContractOTDao = employeeContractOTDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public ClientOrderOTDao getClientOrderOTDao()
   {
      return clientOrderOTDao;
   }

   public void setClientOrderOTDao( ClientOrderOTDao clientOrderOTDao )
   {
      this.clientOrderOTDao = clientOrderOTDao;
   }

   public EmployeeContractOTService getEmployeeContractOTService()
   {
      return employeeContractOTService;
   }

   public void setEmployeeContractOTService( EmployeeContractOTService employeeContractOTService )
   {
      this.employeeContractOTService = employeeContractOTService;
   }

   /**
    * 更换数据库表为临时表。不需要临时表的不需要这步操作。
    */
   @Override
   public void init( List< CellDataDTO > importData )
   {

      if ( importData != null )
      {
         for ( CellDataDTO cellDataDTO : importData )
         {
            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );
            if ( cellDataDTO.getSubCellDataDTOs() == null )
               continue;
            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
            }
         }
      }
   }

   /**
    * 同步到数据库之前校验数据是否合法
    */
   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      boolean result = true;

      SimpleDateFormat ymdhms = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

      SimpleDateFormat ymd = new SimpleDateFormat( "yyyy-MM-dd" );
      // query condition
      EmployeeContractOTVO condition = new EmployeeContractOTVO();

      // 补充excel中没有的列到数据库
      List< CellData > rowCellDatas = new ArrayList< CellData >();

      try
      {
         itemList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getOtItems( "ZH" );
         for ( CellDataDTO row : importData )
         {
            // reset query condition

            rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;

            // OT小时数
            String startDate = null;

            String endDate = null;

            // 员工id
            String employeeId = null;

            // itemID 验证加班项目
            //            String itemId = "0";
            boolean itemError = false;

            // rowNumber
            int rowNumber = 1;

            int clientRowNumber = -1;

            if ( row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               row.setErrorMessange( row.getErrorMessange() + "单元格[" );
               for ( int i = 0; i < row.getCellDatas().size(); i++ )
               {

                  CellData cell = row.getCellDatas().get( i );

                  if ( cell.getColumnVO() != null && "accountId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setAccountId( ( String ) cell.getDbValue() );
                  }

                  if ( StringUtils.isBlank( cell.getCellRef() ) )
                  {
                     continue;
                  }

                  // 不保存employeeNameZH字段
                  if ( cell.getColumnVO() != null && "employeeNameZH".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     cell.getColumnVO().setIsDBColumn( "2" );
                  }

                  // 不保存employeeNameZH字段
                  if ( cell.getColumnVO() != null && "employeeId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     employeeId = cell.getFormateValue();
                  }

                  if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setContractId( cell.getValue() );
                     cell.setDbValue( cell.getFormateValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }

                  if ( cell.getColumnVO() != null && "estimateStartDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     //过滤多个空格格式化数据
                     Pattern p = Pattern.compile( "[' ']+" );
                     Matcher m = p.matcher( cell.getValue() );
                     startDate = KANUtil.formatDate( m.replaceAll( " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( startDate );
                  }

                  if ( cell.getColumnVO() != null && "estimateEndDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     //过滤多个空格格式化数据
                     Pattern p = Pattern.compile( "[' ']+" );
                     Matcher m = p.matcher( cell.getValue() );
                     endDate = KANUtil.formatDate( m.replaceAll( " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( endDate );
                  }

                  if ( cell.getColumnVO() != null && "clientId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     clientRowNumber = i;
                  }

                  if ( cell.getColumnVO() != null && "itemId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                        for ( MappingVO item : itemList )
                        {
                           if ( item.getMappingId().equals( cell.getDbValue() ) )
                           {
                              //                              itemId = cell.getDbValue().toString();
                              itemError = true;
                              break;
                           }
                        }
                  }
               }

               if ( !itemError )
               {
                  row.setErrorMessange( rowNumber + "行,科目不正确,请检查后再上传。" );
                  result = false;
                  continue;
               }

               // 获取EmployeeContractVO
               EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               if ( employeeContractVO == null )
               {
                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议不正确,请检查后再上传。" );
                  result = false;
                  continue;
               }
               //批准、盖章、归档、结束（结束时间或离职时间没超过也行）
               String[] status = { "3", "5", "6", "7" };
               if ( !Arrays.asList( status ).contains( employeeContractVO.getStatus() ) )
               {

                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议未生效,请检查后再上传。" );
                  result = false;
                  continue;
               }

               if ( !employeeContractVO.getEmployeeId().equals( employeeId ) )
               {
                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议与员工ID/雇员ID不匹配,请检查后再上传。" );
                  result = false;
                  continue;
               }

               employeeContractVOMap.put( condition.getContractId(), employeeContractVO );

               long startDateLong = ymdhms.parse( startDate ).getTime();
               long endDateLong = ymdhms.parse( endDate ).getTime();

               long ckStartTimeLong = ymd.parse( employeeContractVO.getStartDate() ).getTime();
               long ckOverTimeLong = Long.MAX_VALUE;
               if ( employeeContractVO != null
                     && ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null || KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null ) )
               {
                  ckOverTimeLong = ymd.parse( employeeContractVO.getEndDate() ).getTime();
                  if ( KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null )
                  {
                     ckOverTimeLong = ymd.parse( employeeContractVO.getResignDate() ).getTime();
                  }
               }

               if ( startDateLong > endDateLong )
               {
                  row.setErrorMessange( rowNumber + "行,加班开始时间大于结束时间。" );
                  result = false;
                  continue;
               }

               if ( startDateLong < ckStartTimeLong || startDateLong > ckOverTimeLong || endDateLong < ckStartTimeLong || endDateLong > ckOverTimeLong )
               {
                  row.setErrorMessange( rowNumber + "行,加班时间超出合同生效时间范围。" );
                  result = false;
                  continue;
               }

               // 获取ClientOrderHeaderVO
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               clientOrderHeaderVOMap.put( employeeContractVO.getOrderId(), clientOrderHeaderVO );

               //获取ClientOrderOTVO列表
               final List< Object > clientOrderOTVOs = clientOrderOTDao.getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderVO == null ? "0"
                     : clientOrderHeaderVO.getOrderHeaderId() );
               // EmployeeContractOTVO
               final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( condition.getContractId() );

               if ( ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 ) || ( clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 ) )
               {
                  // 获取计薪开始、结束日
                  String circleStartDay = clientOrderHeaderVO.getCircleStartDay();
                  String circleEndDay = clientOrderHeaderVO.getCircleEndDay();
                  String extraOTHours = "0";
                  // 返回值
                  double reusltOTHours = 0;

                  // 不正常的计薪周期默认开始“1”、结束“31”
                  if ( KANUtil.filterEmpty( circleStartDay, "0" ) == null || KANUtil.filterEmpty( circleEndDay, "0" ) == null )
                  {
                     circleStartDay = "1";
                     circleEndDay = "31";
                  }

                  // 获取有效的加班每月上限小时数
                  String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
                  if ( KANUtil.filterEmpty( otLimitByMonth ) == null && clientOrderHeaderVO != null )
                  {
                     otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
                  }

                  // 不正常的加班每月上限小时数默认无限制“0”
                  if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
                  {
                     otLimitByMonth = "0";
                  }

                  // 获取每日加班小时数上限
                  String otLimitHoursByDay = employeeContractVO.getOtLimitByDay();
                  if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null && clientOrderHeaderVO != null )
                  {
                     otLimitHoursByDay = clientOrderHeaderVO.getOtLimitByDay();
                  }

                  // 不正常的每日加班小时数上限统默认“24h”
                  if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null )
                  {
                     otLimitHoursByDay = "24";
                  }

                  // 获取有效日历、排班ID
                  String calendarId = employeeContractVO.getCalendarId();
                  if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
                  {
                     calendarId = clientOrderHeaderVO.getCalendarId();
                  }

                  String shiftId = employeeContractVO.getShiftId();
                  if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
                  {
                     shiftId = clientOrderHeaderVO.getShiftId();
                  }

                  // 如果每月加班小时上限无限制，直接计算
                  if ( "0".equals( otLimitByMonth ) )
                  {
                     reusltOTHours = new TimesheetDTO().getOTHours( BaseAction.getAccountId( request, null ), calendarId, shiftId, startDate, endDate, Double.valueOf( otLimitHoursByDay ) );
                  }
                  // 有限制则需另外处理
                  else
                  {
                     final Calendar startCalendar = KANUtil.createCalendar( startDate );
                     final Calendar endCalendar = KANUtil.createCalendar( endDate );

                     // 初始化OTDetailVO列表
                     final List< Object > otDetailVOs = otDetailDao.getOTDetailVOsByContractId( condition.getContractId() );

                     // 获取加班跨天数
                     final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

                     // 遍历处理
                     for ( int i = 0; i <= gap; i++ )
                     {
                        String sDate = i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00";
                        String eDate = i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00";

                        startCalendar.add( Calendar.DATE, 1 );

                        if ( !sDate.equals( eDate ) )
                        {
                           // 获取当前天加班小时数
                           double currDayOTHours = new TimesheetDTO().getOTHours( BaseAction.getAccountId( request, null ), calendarId, shiftId, sDate, eDate, Double.valueOf( otLimitHoursByDay ) );

                           // 超过当天上线情况处理
                           if ( Double.valueOf( otLimitHoursByDay ) > 0 && currDayOTHours > Double.valueOf( otLimitHoursByDay ) )
                           {
                              currDayOTHours = Double.valueOf( otLimitHoursByDay );
                           }

                           // 获取当前天所属月份
                           final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, sDate );

                           // 每月上限处理
                           reusltOTHours = reusltOTHours
                                 + new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, reusltOTHours, currDayOTHours, Double.valueOf( extraOTHours ), otLimitByMonth, circleEndDay );

                        }
                     }
                  }

                  row.setErrorMessange( "" );
                  if ( reusltOTHours <= 0 )
                  {
                     row.setErrorMessange( "第" + rowNumber + "行,不符合加班条件,请检查后再上传。" );
                     mainHasError = true;
                  }

                  if ( clientRowNumber >= 0 )
                  {
                     row.getCellDatas().get( clientRowNumber ).setDbValue( clientOrderHeaderVO.getClientId() );
                  }
                  // 补全要保存的数据
                  if ( !mainHasError )
                  {
                     // 补全要保存的数据
                     addMainTableColumn( rowCellDatas, employeeContractVO, clientOrderHeaderVO, reusltOTHours, startDate, endDate );
                  }

               }
               else
               {
                  String rowMessange = row.getErrorMessange();
                  row.setErrorMessange( rowMessange.substring( 0, rowMessange.length() - 1 ) + "]无加班设置，请检查后再上传。" );
                  mainHasError = true;
               }
            }

            if ( mainHasError )
            {
               result = false;
               continue;// if main table has error, break check
            }
            else
            {
               row.getCellDatas().addAll( rowCellDatas );
            }

         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return false;
      }
      return result;
   }

   /**
    * 同步到数据库后操作
    */
   @Override
   public boolean excueEndInsert( List< CellDataDTO > importDatas, String batchId )
   {
      // 获取连接池的连接
      final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
      List< OTHeaderVO > otHeaders = transform( importDatas );
      // 获得数据库连接实例
      Connection connection = null;
      try
      {
         // 初始化数据库链接
         connection = dataSource.getConnection();
         // 开启事务
         connection.setAutoCommit( false );

         for ( OTHeaderVO otHeaderVO : otHeaders )
         {

            addOTDetails( otHeaderVO );
         }
         // 提交事务 - Finally
         connection.commit();
      }
      catch ( Exception e )
      {

         // 回滚事务
         try
         {
            connection.rollback();
         }
         catch ( SQLException e1 )
         {
            e1.printStackTrace();
         }

      }
      finally
      {
         try
         {
            connection.close();
         }
         catch ( SQLException e )
         {
            e.printStackTrace();
         }
      }

      return false;
   }

   /**
    * 补全主表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addMainTableColumn( final List< CellData > rowCellDatas, final EmployeeContractVO employeeContractVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final double estimateHours, final String startDate, final String endDate )
   {

      CellData headerId = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "otHeaderId" );
      headerIdColumn.setAccountId( "1" );
      headerIdColumn.setIsDBColumn( "1" );
      headerIdColumn.setIsPrimaryKey( "1" );
      headerId.setColumnVO( headerIdColumn );
      headerId.setJdbcDataType( JDBCDataType.INT );

      CellData status = new CellData();
      ColumnVO statusColumn = new ColumnVO();
      statusColumn.setNameDB( "status" );
      statusColumn.setAccountId( "1" );
      statusColumn.setIsDBColumn( "1" );
      status.setColumnVO( statusColumn );
      status.setDbValue( "1" );
      status.setJdbcDataType( JDBCDataType.INT );

      CellData clientIdCell = new CellData();
      ColumnVO clientIdColumn = new ColumnVO();
      clientIdColumn.setAccountId( "1" );
      clientIdColumn.setIsDBColumn( "1" );
      clientIdColumn.setNameDB( "clientId" );
      clientIdCell.setColumnVO( clientIdColumn );
      clientIdCell.setJdbcDataType( JDBCDataType.INT );
      clientIdCell.setDbValue( clientOrderHeaderVO.getClientId() );

      CellData estimateHoursCell = new CellData();
      ColumnVO estimateHoursColumn = new ColumnVO();
      estimateHoursColumn.setAccountId( "1" );
      estimateHoursColumn.setIsDBColumn( "1" );
      estimateHoursColumn.setNameDB( "estimateHours" );
      estimateHoursCell.setColumnVO( estimateHoursColumn );
      estimateHoursCell.setJdbcDataType( JDBCDataType.DOUBLE );
      estimateHoursCell.setDbValue( estimateHours );

      rowCellDatas.add( headerId );
      rowCellDatas.add( status );
      rowCellDatas.add( clientIdCell );
      rowCellDatas.add( estimateHoursCell );

      boolean applyOTFirst = true;

      if ( KANUtil.filterEmpty( employeeContractVO.getApplyOTFirst(), "0" ) != null )
      {
         if ( KANUtil.filterEmpty( employeeContractVO.getApplyOTFirst() ).trim().equals( "2" ) )
         {
            applyOTFirst = false;
         }
      }
      else if ( KANUtil.filterEmpty( clientOrderHeaderVO.getApplyOTFirst() ) != null && KANUtil.filterEmpty( clientOrderHeaderVO.getApplyOTFirst() ).trim().equals( "2" ) )
      {
         applyOTFirst = false;
      }

      //判断是否是预加班
      if ( !applyOTFirst )
      {
         CellData actualStartDateCell = new CellData();
         ColumnVO actualStartDateColumn = new ColumnVO();
         actualStartDateColumn.setAccountId( "1" );
         actualStartDateColumn.setIsDBColumn( "1" );
         actualStartDateColumn.setNameDB( "actualStartDate" );
         actualStartDateCell.setColumnVO( actualStartDateColumn );
         actualStartDateCell.setJdbcDataType( JDBCDataType.DATE );
         actualStartDateCell.setDbValue( startDate );
         rowCellDatas.add( actualStartDateCell );

         CellData actualEndDateCell = new CellData();
         ColumnVO actualEndDateColumn = new ColumnVO();
         actualEndDateColumn.setAccountId( "1" );
         actualEndDateColumn.setIsDBColumn( "1" );
         actualEndDateColumn.setNameDB( "actualEndDate" );
         actualEndDateCell.setColumnVO( actualEndDateColumn );
         actualEndDateCell.setJdbcDataType( JDBCDataType.DATE );
         actualEndDateCell.setDbValue( endDate );
         rowCellDatas.add( actualEndDateCell );

         CellData actualHoursCell = new CellData();
         ColumnVO actualHoursColumn = new ColumnVO();
         actualHoursColumn.setAccountId( "1" );
         actualHoursColumn.setIsDBColumn( "1" );
         actualHoursColumn.setNameDB( "actualHours" );
         actualHoursCell.setColumnVO( actualHoursColumn );
         actualHoursCell.setJdbcDataType( JDBCDataType.DOUBLE );
         actualHoursCell.setDbValue( estimateHours );
         rowCellDatas.add( actualHoursCell );

      }
   }

   /***
    * 将数据库对象转换成 OTHeaderVO transform
    * 
    * @param importDatas
    * @return
    */
   private List< OTHeaderVO > transform( List< CellDataDTO > importDatas )
   {
      List< OTHeaderVO > otHeaders = new ArrayList< OTHeaderVO >();
      if ( importDatas != null )
      {
         for ( CellDataDTO importData : importDatas )
         {
            if ( importData != null && importData.getCellDatas() != null )
            {
               OTHeaderVO otHeaderVO = new OTHeaderVO();
               List< CellData > cellDatas = importData.getCellDatas();
               final JSONObject remark1 = new JSONObject();
               Map< String, String > otherColumn = new HashMap< String, String >();
               // 将值装载到otHeaderVO
               for ( CellData cell : cellDatas )
               {
                  if ( cell != null )
                  {
                     String dbValue = null;
                     if ( KANUtil.filterEmpty( cell.getDbValue() ) == null )
                     {
                        dbValue = "";
                     }
                     else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.DATE ) )
                     {
                        dbValue = KANUtil.formatDate( cell.getDbValue(), "yyyy-MM-dd HH:mm:ss" );
                     }
                     else if ( cell.getJdbcDataType() != null && cell.getJdbcDataType().equals( JDBCDataType.NUMBER ) )
                     {
                        dbValue = cell.getDbValue().toString();
                     }
                     else
                     {
                        dbValue = cell.getDbValue().toString();
                     }

                     if ( cell.getColumnVO() != null )
                     {
                        if ( "1".equals( cell.getColumnVO().getIsDBColumn() ) && "1".equals( cell.getColumnVO().getAccountId() ) )
                        {
                           try
                           {
                              BeanUtils.copyProperty( otHeaderVO, cell.getColumnVO().getNameDB(), dbValue );
                           }
                           catch ( Exception e )
                           {
                              e.printStackTrace();
                           }
                        }
                        else if ( "2".equals( cell.getColumnVO().getIsDBColumn() ) )
                        {
                           otherColumn.put( cell.getColumnVO().getNameDB(), dbValue );
                        }
                        else
                        {
                           remark1.put( cell.getColumnVO().getNameDB(), dbValue );
                        }
                     }
                  }
               }
               otHeaderVO.setRemark1( remark1.toString() );

               otHeaders.add( otHeaderVO );
            }
         }
      }
      return otHeaders;
   }

   // 根据OTHeaderVO衍生OTDetailVO
   private OTDetailVO generateOTDetailVO( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // 初始化OTDetailVO
      final OTDetailVO tempOTDetailVO = new OTDetailVO();

      if ( KANUtil.filterEmpty( otHeaderVO.getTimesheetId() ) != null )
      {
         tempOTDetailVO.setTimesheetId( otHeaderVO.getTimesheetId() );
      }
      tempOTDetailVO.setOtHeaderId( otHeaderVO.getOtHeaderId() );
      tempOTDetailVO.setItemId( otHeaderVO.getItemId() );
      tempOTDetailVO.setEstimateStartDate( otHeaderVO.getEstimateStartDate() );
      tempOTDetailVO.setEstimateEndDate( otHeaderVO.getEstimateEndDate() );
      tempOTDetailVO.setActualStartDate( otHeaderVO.getActualStartDate() );
      tempOTDetailVO.setActualEndDate( otHeaderVO.getActualEndDate() );
      tempOTDetailVO.setEstimateHours( otHeaderVO.getEstimateHours() );
      tempOTDetailVO.setActualHours( otHeaderVO.getActualHours() );
      tempOTDetailVO.setStatus( otHeaderVO.getStatus() );
      tempOTDetailVO.setCreateBy( otHeaderVO.getCreateBy() );
      tempOTDetailVO.setModifyBy( otHeaderVO.getModifyBy() );

      return tempOTDetailVO;
   }

   // 多重插入OTDetailVO
   private int addOTDetails( final OTHeaderVO otHeaderVO ) throws KANException
   {
      int rows = 0;

      // 获取EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractVOMap.get( otHeaderVO.getContractId() );

      // 获取ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

      // 获取有效日历ID
      String calendarId = employeeContractVO.getCalendarId();
      if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
      {
         calendarId = clientOrderHeaderVO.getCalendarId();
      }

      // 获取有效排班ID
      String shiftId = employeeContractVO.getShiftId();
      if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
      {
         shiftId = clientOrderHeaderVO.getShiftId();
      }

      // 获取有效的加班每月上限小时数
      String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
      if ( KANUtil.filterEmpty( otLimitByMonth, "0" ) == null && clientOrderHeaderVO != null )
      {
         otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
      }

      // 未取到加班每月上限小时数，“0” - 默认无限制
      if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
      {
         otLimitByMonth = "0";
      }

      // 获取有效的加班每天上限小时数
      String otLimitByDay = employeeContractVO.getOtLimitByDay();
      if ( KANUtil.filterEmpty( otLimitByDay, "0" ) == null && clientOrderHeaderVO != null )
      {
         otLimitByDay = clientOrderHeaderVO.getOtLimitByDay();
      }

      // 未取到加班每天上限小时数，“0” - 默认无限制
      if ( KANUtil.filterEmpty( otLimitByDay ) == null )
      {
         otLimitByDay = "0";
      }

      // 获取加班需要申请
      String applyOTFirst = employeeContractVO.getApplyOTFirst();
      if ( KANUtil.filterEmpty( applyOTFirst, "0" ) == null && clientOrderHeaderVO != null )
      {
         applyOTFirst = clientOrderHeaderVO.getApplyOTFirst();
      }

      // 加班工作日科目
      String workdayOTItemId = employeeContractVO.getWorkdayOTItemId();
      if ( KANUtil.filterEmpty( workdayOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         workdayOTItemId = clientOrderHeaderVO.getWorkdayOTItemId();
      }

      // 加班休息日科目
      String weekendOTItemId = employeeContractVO.getWeekendOTItemId();
      if ( KANUtil.filterEmpty( weekendOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         weekendOTItemId = clientOrderHeaderVO.getWeekendOTItemId();
      }

      // 加班节假日科目
      String holidayOTItemId = employeeContractVO.getHolidayOTItemId();
      if ( KANUtil.filterEmpty( holidayOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         holidayOTItemId = clientOrderHeaderVO.getHolidayOTItemId();
      }

      // 计薪结束日
      final String circleEndDay = clientOrderHeaderVO.getCircleEndDay();

      // 初始化OTDetailVO列表
      final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( otHeaderVO.getContractId() );

      // 初始化StartDate和EndDate
      String startDate = otHeaderVO.getEstimateStartDate();
      String endDate = otHeaderVO.getEstimateEndDate();

      // 存在实际加班时间
      if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) != null )
      {
         startDate = otHeaderVO.getActualStartDate();
         endDate = otHeaderVO.getActualEndDate();
      }

      final Calendar startCalendar = KANUtil.createCalendar( startDate );
      final Calendar endCalendar = KANUtil.createCalendar( endDate );

      // 获取加班跨天数
      final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

      double totoalOTHours = 0;
      // 循环插入
      for ( int i = 0; i <= gap; i++ )
      {
         // 初始化OTDetailVO
         final OTDetailVO otDetailVO = generateOTDetailVO( otHeaderVO );

         // 设置加班时间起止
         otDetailVO.setEstimateStartDate( i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
         otDetailVO.setEstimateEndDate( i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

         if ( !otDetailVO.getEstimateStartDate().equals( otDetailVO.getEstimateEndDate() ) )
         {
            // 获取当前天所属月份
            final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, otDetailVO.getEstimateStartDate() );

            // 获取当前天加班小时数
            final double currDayOTHours = new TimesheetDTO().getOTHours( otHeaderVO.getAccountId(), calendarId, shiftId, otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate(), Double.valueOf( KANUtil.filterEmpty( otLimitByDay ) != null ? otLimitByDay
                  : "0" ) );

            // 获取当前天在该月有效加班小时数
            double availableOTHours = new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, totoalOTHours, currDayOTHours, 0.0, otLimitByMonth, circleEndDay );

            totoalOTHours = totoalOTHours + availableOTHours;
            // 超过当天上线情况处理
            if ( Double.valueOf( otLimitByDay ) > 0 && availableOTHours > Double.valueOf( otLimitByDay ) )
            {
               availableOTHours = Double.valueOf( otLimitByDay );
            }

            otDetailVO.setEstimateHours( String.valueOf( availableOTHours ) );

            // 获取ShiftDetailVO
            final ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( otHeaderVO.getAccountId(), calendarId, shiftId, otDetailVO.getEstimateStartDate(), TimesheetDTO.EXCEPTION_TYPE_OT );

            // 初始化ItemId
            String itemId = "0";

            // 设置加班科目
            if ( shiftDetailVO != null && KANUtil.filterEmpty( shiftDetailVO.getDayType() ) != null )
            {
               if ( KANUtil.filterEmpty( shiftDetailVO.getDayType() ).equals( "1" ) )
               {
                  itemId = workdayOTItemId;
               }
               else if ( KANUtil.filterEmpty( shiftDetailVO.getDayType() ).equals( "2" ) )
               {
                  itemId = weekendOTItemId;
               }
               else if ( KANUtil.filterEmpty( shiftDetailVO.getDayType() ).equals( "3" ) )
               {
                  itemId = holidayOTItemId;
               }
            }

            otDetailVO.setItemId( itemId );

            if ( otHeaderVO.getStatus().equals( "4" ) )
            {
               otDetailVO.setActualStartDate( otDetailVO.getEstimateStartDate() );
               otDetailVO.setActualEndDate( otDetailVO.getEstimateEndDate() );
               otDetailVO.setActualHours( otDetailVO.getEstimateHours() );
            }

            // 插入OTDetailVO（加班小时数大于“0”）
            if ( Double.valueOf( otDetailVO.getEstimateHours() ) > 0 )
            {
               this.otDetailDao.insertOTImportDetail( otDetailVO );
            }

            startCalendar.add( Calendar.DATE, 1 );

            rows++;
         }
      }

      return rows;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

}
