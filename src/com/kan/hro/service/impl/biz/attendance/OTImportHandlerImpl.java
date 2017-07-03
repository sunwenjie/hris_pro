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
    * �������ݿ��Ϊ��ʱ������Ҫ��ʱ��Ĳ���Ҫ�ⲽ������
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
    * ͬ�������ݿ�֮ǰУ�������Ƿ�Ϸ�
    */
   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      boolean result = true;

      SimpleDateFormat ymdhms = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

      SimpleDateFormat ymd = new SimpleDateFormat( "yyyy-MM-dd" );
      // query condition
      EmployeeContractOTVO condition = new EmployeeContractOTVO();

      // ����excel��û�е��е����ݿ�
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

            // OTСʱ��
            String startDate = null;

            String endDate = null;

            // Ա��id
            String employeeId = null;

            // itemID ��֤�Ӱ���Ŀ
            //            String itemId = "0";
            boolean itemError = false;

            // rowNumber
            int rowNumber = 1;

            int clientRowNumber = -1;

            if ( row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               row.setErrorMessange( row.getErrorMessange() + "��Ԫ��[" );
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

                  // ������employeeNameZH�ֶ�
                  if ( cell.getColumnVO() != null && "employeeNameZH".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     cell.getColumnVO().setIsDBColumn( "2" );
                  }

                  // ������employeeNameZH�ֶ�
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
                     //���˶���ո��ʽ������
                     Pattern p = Pattern.compile( "[' ']+" );
                     Matcher m = p.matcher( cell.getValue() );
                     startDate = KANUtil.formatDate( m.replaceAll( " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( startDate );
                  }

                  if ( cell.getColumnVO() != null && "estimateEndDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     //���˶���ո��ʽ������
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
                  row.setErrorMessange( rowNumber + "��,��Ŀ����ȷ,��������ϴ���" );
                  result = false;
                  continue;
               }

               // ��ȡEmployeeContractVO
               EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               if ( employeeContractVO == null )
               {
                  row.setErrorMessange( rowNumber + "��,�Ͷ���ͬ/����Э�鲻��ȷ,��������ϴ���" );
                  result = false;
                  continue;
               }
               //��׼�����¡��鵵������������ʱ�����ְʱ��û����Ҳ�У�
               String[] status = { "3", "5", "6", "7" };
               if ( !Arrays.asList( status ).contains( employeeContractVO.getStatus() ) )
               {

                  row.setErrorMessange( rowNumber + "��,�Ͷ���ͬ/����Э��δ��Ч,��������ϴ���" );
                  result = false;
                  continue;
               }

               if ( !employeeContractVO.getEmployeeId().equals( employeeId ) )
               {
                  row.setErrorMessange( rowNumber + "��,�Ͷ���ͬ/����Э����Ա��ID/��ԱID��ƥ��,��������ϴ���" );
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
                  row.setErrorMessange( rowNumber + "��,�Ӱ࿪ʼʱ����ڽ���ʱ�䡣" );
                  result = false;
                  continue;
               }

               if ( startDateLong < ckStartTimeLong || startDateLong > ckOverTimeLong || endDateLong < ckStartTimeLong || endDateLong > ckOverTimeLong )
               {
                  row.setErrorMessange( rowNumber + "��,�Ӱ�ʱ�䳬����ͬ��Чʱ�䷶Χ��" );
                  result = false;
                  continue;
               }

               // ��ȡClientOrderHeaderVO
               ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
               clientOrderHeaderVOMap.put( employeeContractVO.getOrderId(), clientOrderHeaderVO );

               //��ȡClientOrderOTVO�б�
               final List< Object > clientOrderOTVOs = clientOrderOTDao.getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderVO == null ? "0"
                     : clientOrderHeaderVO.getOrderHeaderId() );
               // EmployeeContractOTVO
               final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTVOsByContractId( condition.getContractId() );

               if ( ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 ) || ( clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 ) )
               {
                  // ��ȡ��н��ʼ��������
                  String circleStartDay = clientOrderHeaderVO.getCircleStartDay();
                  String circleEndDay = clientOrderHeaderVO.getCircleEndDay();
                  String extraOTHours = "0";
                  // ����ֵ
                  double reusltOTHours = 0;

                  // �������ļ�н����Ĭ�Ͽ�ʼ��1����������31��
                  if ( KANUtil.filterEmpty( circleStartDay, "0" ) == null || KANUtil.filterEmpty( circleEndDay, "0" ) == null )
                  {
                     circleStartDay = "1";
                     circleEndDay = "31";
                  }

                  // ��ȡ��Ч�ļӰ�ÿ������Сʱ��
                  String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
                  if ( KANUtil.filterEmpty( otLimitByMonth ) == null && clientOrderHeaderVO != null )
                  {
                     otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
                  }

                  // �������ļӰ�ÿ������Сʱ��Ĭ�������ơ�0��
                  if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
                  {
                     otLimitByMonth = "0";
                  }

                  // ��ȡÿ�ռӰ�Сʱ������
                  String otLimitHoursByDay = employeeContractVO.getOtLimitByDay();
                  if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null && clientOrderHeaderVO != null )
                  {
                     otLimitHoursByDay = clientOrderHeaderVO.getOtLimitByDay();
                  }

                  // ��������ÿ�ռӰ�Сʱ������ͳĬ�ϡ�24h��
                  if ( KANUtil.filterEmpty( otLimitHoursByDay, "0" ) == null )
                  {
                     otLimitHoursByDay = "24";
                  }

                  // ��ȡ��Ч�������Ű�ID
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

                  // ���ÿ�¼Ӱ�Сʱ���������ƣ�ֱ�Ӽ���
                  if ( "0".equals( otLimitByMonth ) )
                  {
                     reusltOTHours = new TimesheetDTO().getOTHours( BaseAction.getAccountId( request, null ), calendarId, shiftId, startDate, endDate, Double.valueOf( otLimitHoursByDay ) );
                  }
                  // �������������⴦��
                  else
                  {
                     final Calendar startCalendar = KANUtil.createCalendar( startDate );
                     final Calendar endCalendar = KANUtil.createCalendar( endDate );

                     // ��ʼ��OTDetailVO�б�
                     final List< Object > otDetailVOs = otDetailDao.getOTDetailVOsByContractId( condition.getContractId() );

                     // ��ȡ�Ӱ������
                     final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

                     // ��������
                     for ( int i = 0; i <= gap; i++ )
                     {
                        String sDate = i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00";
                        String eDate = i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00";

                        startCalendar.add( Calendar.DATE, 1 );

                        if ( !sDate.equals( eDate ) )
                        {
                           // ��ȡ��ǰ��Ӱ�Сʱ��
                           double currDayOTHours = new TimesheetDTO().getOTHours( BaseAction.getAccountId( request, null ), calendarId, shiftId, sDate, eDate, Double.valueOf( otLimitHoursByDay ) );

                           // �������������������
                           if ( Double.valueOf( otLimitHoursByDay ) > 0 && currDayOTHours > Double.valueOf( otLimitHoursByDay ) )
                           {
                              currDayOTHours = Double.valueOf( otLimitHoursByDay );
                           }

                           // ��ȡ��ǰ�������·�
                           final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, sDate );

                           // ÿ�����޴���
                           reusltOTHours = reusltOTHours
                                 + new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, reusltOTHours, currDayOTHours, Double.valueOf( extraOTHours ), otLimitByMonth, circleEndDay );

                        }
                     }
                  }

                  row.setErrorMessange( "" );
                  if ( reusltOTHours <= 0 )
                  {
                     row.setErrorMessange( "��" + rowNumber + "��,�����ϼӰ�����,��������ϴ���" );
                     mainHasError = true;
                  }

                  if ( clientRowNumber >= 0 )
                  {
                     row.getCellDatas().get( clientRowNumber ).setDbValue( clientOrderHeaderVO.getClientId() );
                  }
                  // ��ȫҪ���������
                  if ( !mainHasError )
                  {
                     // ��ȫҪ���������
                     addMainTableColumn( rowCellDatas, employeeContractVO, clientOrderHeaderVO, reusltOTHours, startDate, endDate );
                  }

               }
               else
               {
                  String rowMessange = row.getErrorMessange();
                  row.setErrorMessange( rowMessange.substring( 0, rowMessange.length() - 1 ) + "]�޼Ӱ����ã���������ϴ���" );
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
    * ͬ�������ݿ�����
    */
   @Override
   public boolean excueEndInsert( List< CellDataDTO > importDatas, String batchId )
   {
      // ��ȡ���ӳص�����
      final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
      List< OTHeaderVO > otHeaders = transform( importDatas );
      // ������ݿ�����ʵ��
      Connection connection = null;
      try
      {
         // ��ʼ�����ݿ�����
         connection = dataSource.getConnection();
         // ��������
         connection.setAutoCommit( false );

         for ( OTHeaderVO otHeaderVO : otHeaders )
         {

            addOTDetails( otHeaderVO );
         }
         // �ύ���� - Finally
         connection.commit();
      }
      catch ( Exception e )
      {

         // �ع�����
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
    * ��ȫ�����е��ֶ�
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

      //�ж��Ƿ���Ԥ�Ӱ�
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
    * �����ݿ����ת���� OTHeaderVO transform
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
               // ��ֵװ�ص�otHeaderVO
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

   // ����OTHeaderVO����OTDetailVO
   private OTDetailVO generateOTDetailVO( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // ��ʼ��OTDetailVO
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

   // ���ز���OTDetailVO
   private int addOTDetails( final OTHeaderVO otHeaderVO ) throws KANException
   {
      int rows = 0;

      // ��ȡEmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractVOMap.get( otHeaderVO.getContractId() );

      // ��ȡClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

      // ��ȡ��Ч����ID
      String calendarId = employeeContractVO.getCalendarId();
      if ( KANUtil.filterEmpty( calendarId, "0" ) == null && clientOrderHeaderVO != null )
      {
         calendarId = clientOrderHeaderVO.getCalendarId();
      }

      // ��ȡ��Ч�Ű�ID
      String shiftId = employeeContractVO.getShiftId();
      if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
      {
         shiftId = clientOrderHeaderVO.getShiftId();
      }

      // ��ȡ��Ч�ļӰ�ÿ������Сʱ��
      String otLimitByMonth = employeeContractVO.getOtLimitByMonth();
      if ( KANUtil.filterEmpty( otLimitByMonth, "0" ) == null && clientOrderHeaderVO != null )
      {
         otLimitByMonth = clientOrderHeaderVO.getOtLimitByMonth();
      }

      // δȡ���Ӱ�ÿ������Сʱ������0�� - Ĭ��������
      if ( KANUtil.filterEmpty( otLimitByMonth ) == null )
      {
         otLimitByMonth = "0";
      }

      // ��ȡ��Ч�ļӰ�ÿ������Сʱ��
      String otLimitByDay = employeeContractVO.getOtLimitByDay();
      if ( KANUtil.filterEmpty( otLimitByDay, "0" ) == null && clientOrderHeaderVO != null )
      {
         otLimitByDay = clientOrderHeaderVO.getOtLimitByDay();
      }

      // δȡ���Ӱ�ÿ������Сʱ������0�� - Ĭ��������
      if ( KANUtil.filterEmpty( otLimitByDay ) == null )
      {
         otLimitByDay = "0";
      }

      // ��ȡ�Ӱ���Ҫ����
      String applyOTFirst = employeeContractVO.getApplyOTFirst();
      if ( KANUtil.filterEmpty( applyOTFirst, "0" ) == null && clientOrderHeaderVO != null )
      {
         applyOTFirst = clientOrderHeaderVO.getApplyOTFirst();
      }

      // �Ӱ๤���տ�Ŀ
      String workdayOTItemId = employeeContractVO.getWorkdayOTItemId();
      if ( KANUtil.filterEmpty( workdayOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         workdayOTItemId = clientOrderHeaderVO.getWorkdayOTItemId();
      }

      // �Ӱ���Ϣ�տ�Ŀ
      String weekendOTItemId = employeeContractVO.getWeekendOTItemId();
      if ( KANUtil.filterEmpty( weekendOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         weekendOTItemId = clientOrderHeaderVO.getWeekendOTItemId();
      }

      // �Ӱ�ڼ��տ�Ŀ
      String holidayOTItemId = employeeContractVO.getHolidayOTItemId();
      if ( KANUtil.filterEmpty( holidayOTItemId, "0" ) == null && clientOrderHeaderVO != null )
      {
         holidayOTItemId = clientOrderHeaderVO.getHolidayOTItemId();
      }

      // ��н������
      final String circleEndDay = clientOrderHeaderVO.getCircleEndDay();

      // ��ʼ��OTDetailVO�б�
      final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( otHeaderVO.getContractId() );

      // ��ʼ��StartDate��EndDate
      String startDate = otHeaderVO.getEstimateStartDate();
      String endDate = otHeaderVO.getEstimateEndDate();

      // ����ʵ�ʼӰ�ʱ��
      if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) != null && KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) != null )
      {
         startDate = otHeaderVO.getActualStartDate();
         endDate = otHeaderVO.getActualEndDate();
      }

      final Calendar startCalendar = KANUtil.createCalendar( startDate );
      final Calendar endCalendar = KANUtil.createCalendar( endDate );

      // ��ȡ�Ӱ������
      final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

      double totoalOTHours = 0;
      // ѭ������
      for ( int i = 0; i <= gap; i++ )
      {
         // ��ʼ��OTDetailVO
         final OTDetailVO otDetailVO = generateOTDetailVO( otHeaderVO );

         // ���üӰ�ʱ����ֹ
         otDetailVO.setEstimateStartDate( i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
         otDetailVO.setEstimateEndDate( i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

         if ( !otDetailVO.getEstimateStartDate().equals( otDetailVO.getEstimateEndDate() ) )
         {
            // ��ȡ��ǰ�������·�
            final String currMonthly = KANUtil.getMonthlyByCondition( circleEndDay, otDetailVO.getEstimateStartDate() );

            // ��ȡ��ǰ��Ӱ�Сʱ��
            final double currDayOTHours = new TimesheetDTO().getOTHours( otHeaderVO.getAccountId(), calendarId, shiftId, otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate(), Double.valueOf( KANUtil.filterEmpty( otLimitByDay ) != null ? otLimitByDay
                  : "0" ) );

            // ��ȡ��ǰ���ڸ�����Ч�Ӱ�Сʱ��
            double availableOTHours = new TimesheetDTO().getAvailableOTHours( otDetailVOs, currMonthly, totoalOTHours, currDayOTHours, 0.0, otLimitByMonth, circleEndDay );

            totoalOTHours = totoalOTHours + availableOTHours;
            // �������������������
            if ( Double.valueOf( otLimitByDay ) > 0 && availableOTHours > Double.valueOf( otLimitByDay ) )
            {
               availableOTHours = Double.valueOf( otLimitByDay );
            }

            otDetailVO.setEstimateHours( String.valueOf( availableOTHours ) );

            // ��ȡShiftDetailVO
            final ShiftDetailVO shiftDetailVO = new TimesheetDTO().getShiftDetailVO( otHeaderVO.getAccountId(), calendarId, shiftId, otDetailVO.getEstimateStartDate(), TimesheetDTO.EXCEPTION_TYPE_OT );

            // ��ʼ��ItemId
            String itemId = "0";

            // ���üӰ��Ŀ
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

            // ����OTDetailVO���Ӱ�Сʱ�����ڡ�0����
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
