package com.kan.hro.service.impl.biz.attendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
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
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;

public class LeaveImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   private EmployeeDao employeeDao;

   private LeaveHeaderDao leaveHeaderDao;

   private LeaveDetailDao leaveDetailDao;

   private EmployeeContractLeaveDao employeeContractLeaveDao;

   private EmployeeContractDao employeeContractDao;

   private ClientOrderHeaderDao clientOrderHeaderDao;

   private ClientOrderLeaveDao clientOrderLeaveDao;

   private LeaveHeaderService leaveHeaderService;

   private List< MappingVO > itemList;

   private Map< String, EmployeeContractVO > employeeContractVOMap = new HashMap< String, EmployeeContractVO >();

   private Map< String, ClientOrderHeaderVO > clientOrderHeaderVOMap = new HashMap< String, ClientOrderHeaderVO >();

   private Map< String, List< EmployeeContractLeaveVO >> employeeContractLeaveVOMap = new HashMap< String, List< EmployeeContractLeaveVO >>();

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public LeaveDetailDao getLeaveDetailDao()
   {
      return leaveDetailDao;
   }

   public void setLeaveDetailDao( LeaveDetailDao leaveDetailDao )
   {
      this.leaveDetailDao = leaveDetailDao;
   }

   public EmployeeContractLeaveDao getEmployeeContractLeaveDao()
   {
      return employeeContractLeaveDao;
   }

   public void setEmployeeContractLeaveDao( EmployeeContractLeaveDao employeeContractLeaveDao )
   {
      this.employeeContractLeaveDao = employeeContractLeaveDao;
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

   public LeaveHeaderService getLeaveHeaderService()
   {
      return leaveHeaderService;
   }

   public void setLeaveHeaderService( LeaveHeaderService leaveHeaderService )
   {
      this.leaveHeaderService = leaveHeaderService;
   }

   public ClientOrderLeaveDao getClientOrderLeaveDao()
   {
      return clientOrderLeaveDao;
   }

   public void setClientOrderLeaveDao( ClientOrderLeaveDao clientOrderLeaveDao )
   {
      this.clientOrderLeaveDao = clientOrderLeaveDao;
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
      // 初始化返回值
      boolean result = true;

      // 格式化日期
      final SimpleDateFormat ymdhms = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
      // 格式化日期yyyy-MM-dd
      final SimpleDateFormat ymd = new SimpleDateFormat( "yyyy-MM-dd" );

      // query condition
      EmployeeContractLeaveVO condition = new EmployeeContractLeaveVO();

      // 补充excel中没有的列到数据库
      List< CellData > rowCellDatas = new ArrayList< CellData >();

      // 实时记录Excel员工请假小时数Map< employeeId, Map< itemId, hours > >
      final Map< String, Map< String, Double > > excelLeaveHoursMap = new HashMap< String, Map< String, Double > >();

      try
      {
         itemList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemsByType( "6", "ZH", BaseAction.getCorpId( request, null ) );

         // 遍历Excel row
         for ( CellDataDTO row : importData )
         {
            // reset query condition
            rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;

            // 初始化一些数据
            String employeeId = null;
            String itemId = "0";
            String startDate = null;
            String endDate = null;
            // 法定假小时数
            double estimateLegalHours = 0.0;
            // 福利假小时数
            double estimateBenefitHours = 0.0;

            int clientRowNumber = -1;

            // rowNumber
            int rowNumber = 1;

            // 遍历Excel column
            if ( row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               for ( int i = 0; i < row.getCellDatas().size(); i++ )
               {
                  final CellData cell = row.getCellDatas().get( i );

                  if ( StringUtils.isBlank( cell.getCellRef() ) )
                  {
                     continue;
                  }

                  if ( cell.getColumnVO() != null && "accountId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setAccountId( ( String ) cell.getDbValue() );
                  }

                  // 不保存employeeNameZH字段
                  if ( cell.getColumnVO() != null && "employeeNameZH".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     cell.getColumnVO().setIsDBColumn( "2" );
                  }

                  if ( cell.getColumnVO() != null && "employeeId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     employeeId = cell.getValue().trim();
                  }

                  if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setContractId( cell.getValue() );
                     cell.setDbValue( cell.getValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }

                  // 格式化数据去多个空格
                  if ( cell.getColumnVO() != null && "estimateStartDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     startDate = KANUtil.formatDate( cell.getValue().replaceAll( "\\s+", " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( startDate );
                  }

                  // 格式化数据去多个空格
                  if ( cell.getColumnVO() != null && "estimateEndDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     endDate = KANUtil.formatDate( cell.getValue().replaceAll( "\\s+", " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( endDate );
                  }

                  // 找到itemId
                  if ( cell.getColumnVO() != null && "itemId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        for ( MappingVO item : itemList )
                        {
                           if ( item.getMappingId().equals( cell.getDbValue() ) )
                           {
                              itemId = cell.getDbValue().toString();
                              break;
                           }
                        }
                     }
                  }
               }

               // 查找劳动合同是否存在
               final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               // 劳动合同不存在，填写错误日志
               if ( employeeContractVO == null )
               {
                  row.setErrorMessange( "第" + rowNumber + "行，劳动合同没有找到，请检查后再上传。" );
                  result = false;
                  continue;
               }

               // 劳动合同合同不是有效状态，填写错误日志
               if ( !Arrays.asList( new String[] { "3", "5", "6", "7" } ).contains( employeeContractVO.getStatus() ) )
               {
                  row.setErrorMessange( "第" + rowNumber + "行，劳动合同状态未生效，请检查后再上传。" );
                  result = false;
                  continue;
               }

               // 劳动合同ID和员工ID不匹配，填写错误日志
               if ( !employeeContractVO.getEmployeeId().equals( employeeId ) )
               {
                  row.setErrorMessange( "第" + rowNumber + "行，劳动合同ID与员工ID不匹配，请检查后再上传。" );
                  result = false;
                  continue;
               }

               employeeContractVOMap.put( condition.getContractId(), employeeContractVO );

               // 获取ClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

               clientOrderHeaderVOMap.put( employeeContractVO.getOrderId().trim(), clientOrderHeaderVO );
               if ( clientRowNumber >= 0 )
               {
                  row.getCellDatas().get( clientRowNumber ).setDbValue( clientOrderHeaderVO.getClientId() );
               }

               // 根据劳动合同ID获取休假情况
               final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( condition.getContractId() );

               if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
               {
                  // 查询员工请假方案放入map
                  employeeContractLeaveVOMap.put( condition.getContractId(), employeeContractLeaveVOs );

                  final EmployeeContractLeaveVO thisEmployeeContractLeaveVO = findEmployeeContractLeaveVOByCondition( employeeContractLeaveVOs, itemId );

                  // 员工无此类型假期，填写错误日志
                  if ( thisEmployeeContractLeaveVO == null )
                  {
                     row.setErrorMessange( "第" + rowNumber + "行，无此类型假期，请检查后再上传。" );
                     mainHasError = true;
                  }
                  // 存在此类型假期，才开始验证时间
                  else
                  {
                     // 将请假开始、结束时间转换成long类型
                     long startDateLong = ymd.parse( startDate ).getTime();
                     long endDateLong = ymd.parse( endDate ).getTime();

                     // 将合同开始、结束时间转换成long类型
                     long ckStartTimeLong = ymd.parse( employeeContractVO.getStartDate() ).getTime();
                     long ckOverTimeLong = Long.MAX_VALUE;

                     // 固定期劳动合同结束时间
                     if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
                     {
                        ckOverTimeLong = ymd.parse( employeeContractVO.getEndDate() ).getTime();
                     }

                     // 离职日期替代劳动合同结束时间
                     if ( KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null )
                     {
                        ckOverTimeLong = ymd.parse( employeeContractVO.getResignDate() ).getTime();
                     }

                     // 请假开始时间大于结束时间，填写错误日志
                     if ( startDateLong > endDateLong )
                     {
                        row.setErrorMessange( "第" + rowNumber + "行，请假开始时间不能大于结束时间，请检查后再上传。" );
                        result = false;
                        continue;
                     }

                     // 请假时间不在劳动合同有效期间内，填写错误日志
                     if ( startDateLong < ckStartTimeLong || startDateLong > ckOverTimeLong || endDateLong < ckStartTimeLong || endDateLong > ckOverTimeLong )
                     {
                        row.setErrorMessange( "第" + rowNumber + "行，请假时间超出劳动合同有效期间范围，请检查后再上传。" );
                        result = false;
                        continue;
                     }

                     // 获取有效日历、排班ID
                     String calendarId = employeeContractVO.getCalendarId();
                     String shiftId = employeeContractVO.getShiftId();

                     if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
                     {
                        calendarId = clientOrderHeaderVO.getCalendarId();
                     }

                     if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
                     {
                        shiftId = clientOrderHeaderVO.getShiftId();
                     }

                     // 计算请假小时数
                     double hours = new TimesheetDTO().getLeaveHours( itemId, BaseAction.getAccountId( request, null ), calendarId, shiftId, startDate, endDate );

                     // 计算请假小时数不合法
                     if ( hours <= 0 )
                     {
                        row.setErrorMessange( "第" + rowNumber + "行，计算请假时间有误，请检查后再上传。" );
                        mainHasError = true;
                     }

                     // 如果是坑die的年假，需要校验剩余时间是否足够
                     if ( "41".equals( thisEmployeeContractLeaveVO.getItemId() ) )
                     {
                        // 数据库查得剩余小时（法定）
                        final double dbLeftHours_FD = Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() )
                              + Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() );

                        // 数据库查得剩余小时（福利）
                        final double dbLeftHours_FL = Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() )
                              + Double.valueOf( thisEmployeeContractLeaveVO.getLeftBenefitQuantity() );

                        // 缓存已用数额小时（法定）
                        double cacheUsedHours_FD = 0;
                        // 缓存已用数额小时（福利）
                        double cacheUsedHours_FL = 0;

                        if ( excelLeaveHoursMap.get( condition.getContractId() ) != null && excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FD" ) != null )
                           cacheUsedHours_FD = excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FD" );

                        if ( excelLeaveHoursMap.get( condition.getContractId() ) != null && excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FL" ) != null )
                           cacheUsedHours_FL = excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FL" );

                        if ( dbLeftHours_FD + dbLeftHours_FL - cacheUsedHours_FD - cacheUsedHours_FL < hours )
                        {
                           row.setErrorMessange( "第" + rowNumber + "行，请假可用小时数不够，请检查后再上传。" );
                           mainHasError = true;
                        }
                        else
                        {
                           // 赋值
                           if ( hours > dbLeftHours_FD - cacheUsedHours_FD )
                           {
                              estimateLegalHours = dbLeftHours_FD - cacheUsedHours_FD;
                              estimateBenefitHours = hours - dbLeftHours_FD - cacheUsedHours_FD;
                           }
                           else
                           {
                              estimateLegalHours = hours;
                              estimateBenefitHours = 0;
                           }
                        }
                     }
                     else
                     {
                        // 赋值
                        estimateLegalHours = 0;
                        estimateBenefitHours = hours;
                     }

                     // 将请假小时数放入Map & 补全要保存的数据
                     if ( !mainHasError )
                     {
                        if ( "41".equals( thisEmployeeContractLeaveVO.getItemId() ) )
                        {
                           setExcelLeaveMap( excelLeaveHoursMap, condition.getContractId(), "41_FD", estimateLegalHours );
                           setExcelLeaveMap( excelLeaveHoursMap, condition.getContractId(), "41_FL", estimateBenefitHours );
                        }
                        else
                        {
                           setExcelLeaveMap( excelLeaveHoursMap, condition.getContractId(), itemId, hours );
                        }

                        addMainTableColumn( rowCellDatas, employeeContractVO, clientOrderHeaderVO, estimateLegalHours, estimateBenefitHours );
                     }
                  }
               }
            }

            // if main table has error, break check
            if ( mainHasError )
            {
               result = false;
               continue;
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

      List< LeaveHeaderVO > leaveHeaders = transform( importDatas );
      try
      {
         startTransaction();
         for ( LeaveHeaderVO leaveHeaderVO : leaveHeaders )
         {
            // 不跨天，单笔插入LeaveDetailVO
            if ( leaveHeaderVO.getEstimateStartDate().split( " " )[ 0 ].equals( leaveHeaderVO.getEstimateEndDate().split( " " )[ 0 ] ) )
            {
               final LeaveDetailVO tempLeaveDetailVO = generateLeaveDetailVO( leaveHeaderVO );
               if ( tempLeaveDetailVO.getItemId().equals( "41" ) )
               {
                  int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
                  int dataYear = Integer.valueOf( KANUtil.formatDate( tempLeaveDetailVO.getEstimateStartDate(), "yyyy" ) );
                  if ( dataYear > currYear )
                  {
                     tempLeaveDetailVO.setItemId( "49" );
                  }
               }
               this.leaveDetailDao.insertLeaveDetailtemp( tempLeaveDetailVO );
            }
            // 跨天，多重插入LeaveDetailVO
            else
            {
               addMultipleLeaveDetail( leaveHeaderVO );
            }
         }
         // 提交事务
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // TODO: handle exception
         // 回滚事务
         this.rollbackTransaction();
         e.printStackTrace();
      }

      return false;
   }

   /**
    * 补全主表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addMainTableColumn( List< CellData > rowCellDatas, EmployeeContractVO employeeContractVO, ClientOrderHeaderVO clientOrderHeaderVO, double estimateLegalHours,
         double estimateBenefitHours )
   {

      CellData headerId = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "leaveHeaderId" );
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

      CellData estimateBenefitHoursCell = new CellData();
      ColumnVO estimateBenefitHoursColumn = new ColumnVO();
      estimateBenefitHoursColumn.setAccountId( "1" );
      estimateBenefitHoursColumn.setIsDBColumn( "1" );
      estimateBenefitHoursColumn.setNameDB( "estimateBenefitHours" );
      estimateBenefitHoursCell.setColumnVO( estimateBenefitHoursColumn );
      estimateBenefitHoursCell.setJdbcDataType( JDBCDataType.DOUBLE );
      estimateBenefitHoursCell.setDbValue( estimateBenefitHours );

      CellData estimateLegalHoursCell = new CellData();
      ColumnVO estimateLegalHoursColumn = new ColumnVO();
      estimateLegalHoursColumn.setAccountId( "1" );
      estimateLegalHoursColumn.setIsDBColumn( "1" );
      estimateLegalHoursColumn.setNameDB( "estimateLegalHours" );
      estimateLegalHoursCell.setColumnVO( estimateLegalHoursColumn );
      estimateLegalHoursCell.setJdbcDataType( JDBCDataType.DOUBLE );
      estimateLegalHoursCell.setDbValue( estimateLegalHours );

      CellData retrieveStatusCell = new CellData();
      ColumnVO retrieveStatusColumn = new ColumnVO();
      retrieveStatusColumn.setNameDB( "retrieveStatus" );
      retrieveStatusColumn.setAccountId( "1" );
      retrieveStatusColumn.setIsDBColumn( "1" );
      retrieveStatusCell.setColumnVO( retrieveStatusColumn );
      retrieveStatusCell.setDbValue( "1" );
      retrieveStatusCell.setJdbcDataType( JDBCDataType.INT );

      rowCellDatas.add( headerId );
      rowCellDatas.add( status );
      rowCellDatas.add( clientIdCell );
      rowCellDatas.add( estimateBenefitHoursCell );
      rowCellDatas.add( estimateLegalHoursCell );
      rowCellDatas.add( retrieveStatusCell );

   }

   /***
    * 将数据库对象转换成 LeaveHeaderVO transform
    * 
    * @param importDatas
    * @return
    */
   private List< LeaveHeaderVO > transform( List< CellDataDTO > importDatas )
   {
      List< LeaveHeaderVO > leaveHeaders = new ArrayList< LeaveHeaderVO >();
      if ( importDatas != null )
      {
         for ( CellDataDTO importData : importDatas )
         {
            if ( importData != null && importData.getCellDatas() != null )
            {
               LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
               List< CellData > cellDatas = importData.getCellDatas();
               final JSONObject remark1 = new JSONObject();
               Map< String, String > otherColumn = new HashMap< String, String >();
               // 将值装载到leaveHeaderVO
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
                              BeanUtils.copyProperty( leaveHeaderVO, cell.getColumnVO().getNameDB(), dbValue );
                           }
                           catch ( Exception e )
                           {
                              // TODO Auto-generated catch block
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
               leaveHeaderVO.setRemark1( remark1.toString() );

               leaveHeaders.add( leaveHeaderVO );
            }
         }
      }
      return leaveHeaders;
   }

   /**
    * 添加多个LeaveDetailVO
    * 
    * @param leaveHeaderVO
    * @throws KANException
    */
   private void addMultipleLeaveDetail( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // 获取EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractVOMap.get( leaveHeaderVO.getContractId() );

      // 获取ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

      // 获取EmployeeContractLeaveVO列表（已处理Left Hours）
      final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = employeeContractLeaveVOMap.get( leaveHeaderVO.getContractId() );

      // 初始化EmployeeContractLeaveVO
      EmployeeContractLeaveVO employeeContractLeaveVO = null;

      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( EmployeeContractLeaveVO tempEmployeeContractLeaveVO : employeeContractLeaveVOs )
         {
            if ( tempEmployeeContractLeaveVO.getItemId().equals( leaveHeaderVO.getItemId() ) )
            {
               employeeContractLeaveVO = tempEmployeeContractLeaveVO;
               break;
            }
         }
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

      final String startDate = leaveHeaderVO.getEstimateStartDate();
      final String endDate = leaveHeaderVO.getEstimateEndDate();
      final Calendar startCalendar = KANUtil.createCalendar( startDate );
      final Calendar endCalendar = KANUtil.createCalendar( endDate );

      // 获取请假跨天数
      final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

      // 循环插入
      for ( int i = 0; i <= gap; i++ )
      {
         // 初始化LeaveDetailVO
         final LeaveDetailVO leaveDetailVO = generateLeaveDetailVO( leaveHeaderVO );

         // 设置请假时间起止

         final ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( leaveHeaderVO.getAccountId(), calendarId, shiftId, KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ), TimesheetDTO.EXCEPTION_TYPE_LEAVE );

         if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
         {
            final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );
            int startPeriod = Integer.valueOf( shiftPeriods[ 0 ] );
            int endPeriod = Integer.valueOf( shiftPeriods[ shiftPeriods.length - 1 ] );
            String tempStartDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + shiftDetailVO.getStartTime( Integer.valueOf( startPeriod ) );
            String tempEndDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + shiftDetailVO.getEndTime( Integer.valueOf( endPeriod ) );
            final Calendar tempStartCalendar = KANUtil.createCalendar( tempStartDate );
            final Calendar tempEndCalendar = KANUtil.createCalendar( tempEndDate );

            leaveDetailVO.setEstimateStartDate( ( i == 0 && tempStartCalendar.before( startCalendar ) ) ? startDate : tempStartDate );
            leaveDetailVO.setEstimateEndDate( ( i == gap && endCalendar.before( tempEndCalendar ) ) ? endDate : tempEndDate );

         }
         else
         {
            leaveDetailVO.setEstimateStartDate( i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
            leaveDetailVO.setEstimateEndDate( i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

         }

         // 获取当前天请假小时数
         final double currHours = new TimesheetDTO().getLeaveHours( leaveHeaderVO.getItemId(), leaveHeaderVO.getAccountId(), calendarId, shiftId, leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() );

         // 如果是年假
         if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "41" ) )
         {
            if ( employeeContractLeaveVO != null )
            {
               // 获取当前服务协议（年假）已用掉法定小时数；
               final double legalHours = getUsedLegalHours( leaveHeaderVO.getAccountId(), leaveHeaderVO.getContractId(), leaveHeaderVO.getItemId() );
               final double legalQuantity = Double.valueOf( employeeContractLeaveVO.getLegalQuantity() != null ? employeeContractLeaveVO.getLegalQuantity() : "0" );

               // 如果(当前法定+已用法定) 比较 (总法定)
               if ( currHours + legalHours > legalQuantity )
               {
                  leaveDetailVO.setEstimateLegalHours( String.valueOf( legalQuantity - legalHours ) );
                  leaveDetailVO.setEstimateBenefitHours( String.valueOf( currHours + legalHours - legalQuantity ) );
               }
               else
               {
                  leaveDetailVO.setEstimateLegalHours( String.valueOf( currHours ) );
                  leaveDetailVO.setEstimateBenefitHours( "0" );
               }

               int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
               int dataYear = Integer.valueOf( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy" ) );
               if ( dataYear > currYear )
               {
                  leaveDetailVO.setItemId( "49" );
               }
            }
         }
         // 其他科目
         else
         {
            leaveDetailVO.setEstimateBenefitHours( String.valueOf( currHours ) );
         }

         startCalendar.add( Calendar.DATE, 1 );

         // 插入LeaveHeaderVO（请假小时数大于“0”）
         if ( !leaveDetailVO.getEstimateStartDate().equals( leaveDetailVO.getEstimateEndDate() ) && currHours > 0 )
         {
            this.leaveDetailDao.insertLeaveDetailtemp( leaveDetailVO );
         }
      }
   }

   /**
    * 根据LeaveHeaderVO填充LeaveDetailVO
    */
   private LeaveDetailVO generateLeaveDetailVO( final LeaveHeaderVO leaveHeaderVO )
   {
      // 初始化LeaveDetailVO
      LeaveDetailVO tempLeaveDetailVO = new LeaveDetailVO();

      if ( KANUtil.filterEmpty( leaveHeaderVO.getTimesheetId() ) != null )
      {
         tempLeaveDetailVO.setTimesheetId( leaveHeaderVO.getTimesheetId() );
      }
      tempLeaveDetailVO.setLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
      tempLeaveDetailVO.setItemId( leaveHeaderVO.getItemId() );
      tempLeaveDetailVO.setEstimateStartDate( leaveHeaderVO.getEstimateStartDate() );
      tempLeaveDetailVO.setEstimateEndDate( leaveHeaderVO.getEstimateEndDate() );
      tempLeaveDetailVO.setActualStartDate( leaveHeaderVO.getActualStartDate() );
      tempLeaveDetailVO.setActualEndDate( leaveHeaderVO.getActualEndDate() );
      tempLeaveDetailVO.setEstimateLegalHours( leaveHeaderVO.getEstimateLegalHours() );
      tempLeaveDetailVO.setEstimateBenefitHours( leaveHeaderVO.getEstimateBenefitHours() );
      tempLeaveDetailVO.setActualLegalHours( leaveHeaderVO.getActualLegalHours() );
      tempLeaveDetailVO.setActualBenefitHours( leaveHeaderVO.getActualBenefitHours() );
      tempLeaveDetailVO.setStatus( leaveHeaderVO.getStatus() );
      tempLeaveDetailVO.setCreateBy( leaveHeaderVO.getCreateBy() );
      tempLeaveDetailVO.setModifyBy( leaveHeaderVO.getModifyBy() );
      tempLeaveDetailVO.setRetrieveStatus( leaveHeaderVO.getRetrieveStatus() );

      if ( leaveHeaderVO.getEstimateStartDate().split( " " )[ 0 ].equals( leaveHeaderVO.getEstimateEndDate().split( " " )[ 0 ] ) )
      {
         tempLeaveDetailVO = generateLeaveDetailVO( leaveHeaderVO, tempLeaveDetailVO );
      }

      return tempLeaveDetailVO;
   }

   private LeaveDetailVO generateLeaveDetailVO( final LeaveHeaderVO leaveHeaderVO, LeaveDetailVO leaveDetailVO )
   {

      final EmployeeContractVO employeeContractVO = employeeContractVOMap.get( leaveHeaderVO.getContractId() );

      // 获取ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

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

      final String startDate = leaveHeaderVO.getEstimateStartDate();
      final String endDate = leaveHeaderVO.getEstimateEndDate();
      final Calendar startCalendar = KANUtil.createCalendar( startDate );
      final Calendar endCalendar = KANUtil.createCalendar( endDate );

      try
      {
         ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( leaveHeaderVO.getAccountId(), calendarId, shiftId, KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ), TimesheetDTO.EXCEPTION_TYPE_LEAVE );

         if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
         {
            final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );
            int startPeriod = Integer.valueOf( shiftPeriods[ 0 ] ) - 1;
            int endPeriod = Integer.valueOf( shiftPeriods[ shiftPeriods.length - 1 ] );
            String tempStartDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( startPeriod ) / 2 + ":" + Integer.valueOf( startPeriod )
                  % 2;
            String tempEndDate = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( endPeriod ) / 2 + ":" + Integer.valueOf( endPeriod ) % 2 * 30;
            final Calendar tempStartCalendar = KANUtil.createCalendar( tempStartDate );
            final Calendar tempEndCalendar = KANUtil.createCalendar( tempEndDate );

            leaveDetailVO.setEstimateStartDate( tempStartCalendar.before( startCalendar ) ? startDate : tempStartDate );
            leaveDetailVO.setEstimateEndDate( endCalendar.before( tempEndCalendar ) ? endDate : tempEndDate );

         }
      }
      catch ( KANException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return leaveDetailVO;
   }

   // 根据条件获取请假列表已用法定小时数
   private double getUsedLegalHours( final String accountId, final String contractId, final String itemId ) throws KANException
   {
      // 初始化返回值
      double legalQuantity = 0.0;
      // 初始化查找条件
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setAccountId( accountId );
      leaveHeaderVO.setContractId( contractId );
      leaveHeaderVO.setItemId( itemId );

      // 获取已请假列表
      final List< Object > leaveHeaderVOs = this.leaveHeaderDao.getLeaveHeaderVOsByCondition( leaveHeaderVO );

      if ( leaveHeaderVOs != null && leaveHeaderVOs.size() > 0 )
      {
         for ( Object leaveHeaderVOObject : leaveHeaderVOs )
         {
            legalQuantity = legalQuantity + Double.valueOf( ( ( LeaveHeaderVO ) leaveHeaderVOObject ).getEstimateLegalHours() );
         }
      }

      return legalQuantity;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

   // 将员工的请假信息即时放在Map中
   private void setExcelLeaveMap( final Map< String, Map< String, Double > > excelLeaveMap, final String contractId, final String itemId, final Double hours )
   {
      if ( excelLeaveMap == null || contractId == null || itemId == null || hours == null )
         return;

      if ( excelLeaveMap.get( contractId ) == null )
      {
         final Map< String, Double > subMap = new HashMap< String, Double >();
         subMap.put( itemId, hours );
         excelLeaveMap.put( contractId, subMap );
      }
      else
      {
         if ( excelLeaveMap.get( contractId ).get( itemId ) == null )
         {
            excelLeaveMap.get( contractId ).put( itemId, hours );
         }
         else
         {
            final double d = excelLeaveMap.get( contractId ).get( itemId );
            excelLeaveMap.get( contractId ).put( itemId, d + hours );
         }
      }
   }

   // 根据itemId找到员工的休假设置
   private EmployeeContractLeaveVO findEmployeeContractLeaveVOByCondition( final List< EmployeeContractLeaveVO > list, final String itemId )
   {
      for ( EmployeeContractLeaveVO employeeContractLeaveVO : list )
      {
         if ( itemId != null && itemId.equals( employeeContractLeaveVO.getItemId() ) )
            return employeeContractLeaveVO;
      }

      return null;
   }

}
