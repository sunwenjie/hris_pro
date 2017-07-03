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
      // ��ʼ������ֵ
      boolean result = true;

      // ��ʽ������
      final SimpleDateFormat ymdhms = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
      // ��ʽ������yyyy-MM-dd
      final SimpleDateFormat ymd = new SimpleDateFormat( "yyyy-MM-dd" );

      // query condition
      EmployeeContractLeaveVO condition = new EmployeeContractLeaveVO();

      // ����excel��û�е��е����ݿ�
      List< CellData > rowCellDatas = new ArrayList< CellData >();

      // ʵʱ��¼ExcelԱ�����Сʱ��Map< employeeId, Map< itemId, hours > >
      final Map< String, Map< String, Double > > excelLeaveHoursMap = new HashMap< String, Map< String, Double > >();

      try
      {
         itemList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemsByType( "6", "ZH", BaseAction.getCorpId( request, null ) );

         // ����Excel row
         for ( CellDataDTO row : importData )
         {
            // reset query condition
            rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;

            // ��ʼ��һЩ����
            String employeeId = null;
            String itemId = "0";
            String startDate = null;
            String endDate = null;
            // ������Сʱ��
            double estimateLegalHours = 0.0;
            // ������Сʱ��
            double estimateBenefitHours = 0.0;

            int clientRowNumber = -1;

            // rowNumber
            int rowNumber = 1;

            // ����Excel column
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

                  // ������employeeNameZH�ֶ�
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

                  // ��ʽ������ȥ����ո�
                  if ( cell.getColumnVO() != null && "estimateStartDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     startDate = KANUtil.formatDate( cell.getValue().replaceAll( "\\s+", " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( startDate );
                  }

                  // ��ʽ������ȥ����ո�
                  if ( cell.getColumnVO() != null && "estimateEndDate".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     endDate = KANUtil.formatDate( cell.getValue().replaceAll( "\\s+", " " ).trim(), "yyyy-MM-dd HH:mm:ss" );
                     cell.setDbValue( endDate );
                  }

                  // �ҵ�itemId
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

               // �����Ͷ���ͬ�Ƿ����
               final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               // �Ͷ���ͬ�����ڣ���д������־
               if ( employeeContractVO == null )
               {
                  row.setErrorMessange( "��" + rowNumber + "�У��Ͷ���ͬû���ҵ�����������ϴ���" );
                  result = false;
                  continue;
               }

               // �Ͷ���ͬ��ͬ������Ч״̬����д������־
               if ( !Arrays.asList( new String[] { "3", "5", "6", "7" } ).contains( employeeContractVO.getStatus() ) )
               {
                  row.setErrorMessange( "��" + rowNumber + "�У��Ͷ���ͬ״̬δ��Ч����������ϴ���" );
                  result = false;
                  continue;
               }

               // �Ͷ���ͬID��Ա��ID��ƥ�䣬��д������־
               if ( !employeeContractVO.getEmployeeId().equals( employeeId ) )
               {
                  row.setErrorMessange( "��" + rowNumber + "�У��Ͷ���ͬID��Ա��ID��ƥ�䣬��������ϴ���" );
                  result = false;
                  continue;
               }

               employeeContractVOMap.put( condition.getContractId(), employeeContractVO );

               // ��ȡClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

               clientOrderHeaderVOMap.put( employeeContractVO.getOrderId().trim(), clientOrderHeaderVO );
               if ( clientRowNumber >= 0 )
               {
                  row.getCellDatas().get( clientRowNumber ).setDbValue( clientOrderHeaderVO.getClientId() );
               }

               // �����Ͷ���ͬID��ȡ�ݼ����
               final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = leaveHeaderService.getEmployeeContractLeaveVOsByContractId( condition.getContractId() );

               if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
               {
                  // ��ѯԱ����ٷ�������map
                  employeeContractLeaveVOMap.put( condition.getContractId(), employeeContractLeaveVOs );

                  final EmployeeContractLeaveVO thisEmployeeContractLeaveVO = findEmployeeContractLeaveVOByCondition( employeeContractLeaveVOs, itemId );

                  // Ա���޴����ͼ��ڣ���д������־
                  if ( thisEmployeeContractLeaveVO == null )
                  {
                     row.setErrorMessange( "��" + rowNumber + "�У��޴����ͼ��ڣ���������ϴ���" );
                     mainHasError = true;
                  }
                  // ���ڴ����ͼ��ڣ��ſ�ʼ��֤ʱ��
                  else
                  {
                     // ����ٿ�ʼ������ʱ��ת����long����
                     long startDateLong = ymd.parse( startDate ).getTime();
                     long endDateLong = ymd.parse( endDate ).getTime();

                     // ����ͬ��ʼ������ʱ��ת����long����
                     long ckStartTimeLong = ymd.parse( employeeContractVO.getStartDate() ).getTime();
                     long ckOverTimeLong = Long.MAX_VALUE;

                     // �̶����Ͷ���ͬ����ʱ��
                     if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null )
                     {
                        ckOverTimeLong = ymd.parse( employeeContractVO.getEndDate() ).getTime();
                     }

                     // ��ְ��������Ͷ���ͬ����ʱ��
                     if ( KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null )
                     {
                        ckOverTimeLong = ymd.parse( employeeContractVO.getResignDate() ).getTime();
                     }

                     // ��ٿ�ʼʱ����ڽ���ʱ�䣬��д������־
                     if ( startDateLong > endDateLong )
                     {
                        row.setErrorMessange( "��" + rowNumber + "�У���ٿ�ʼʱ�䲻�ܴ��ڽ���ʱ�䣬��������ϴ���" );
                        result = false;
                        continue;
                     }

                     // ���ʱ�䲻���Ͷ���ͬ��Ч�ڼ��ڣ���д������־
                     if ( startDateLong < ckStartTimeLong || startDateLong > ckOverTimeLong || endDateLong < ckStartTimeLong || endDateLong > ckOverTimeLong )
                     {
                        row.setErrorMessange( "��" + rowNumber + "�У����ʱ�䳬���Ͷ���ͬ��Ч�ڼ䷶Χ����������ϴ���" );
                        result = false;
                        continue;
                     }

                     // ��ȡ��Ч�������Ű�ID
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

                     // �������Сʱ��
                     double hours = new TimesheetDTO().getLeaveHours( itemId, BaseAction.getAccountId( request, null ), calendarId, shiftId, startDate, endDate );

                     // �������Сʱ�����Ϸ�
                     if ( hours <= 0 )
                     {
                        row.setErrorMessange( "��" + rowNumber + "�У��������ʱ��������������ϴ���" );
                        mainHasError = true;
                     }

                     // ����ǿ�die����٣���ҪУ��ʣ��ʱ���Ƿ��㹻
                     if ( "41".equals( thisEmployeeContractLeaveVO.getItemId() ) )
                     {
                        // ���ݿ���ʣ��Сʱ��������
                        final double dbLeftHours_FD = Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() )
                              + Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() );

                        // ���ݿ���ʣ��Сʱ��������
                        final double dbLeftHours_FL = Double.valueOf( thisEmployeeContractLeaveVO.getLeftLegalQuantity() )
                              + Double.valueOf( thisEmployeeContractLeaveVO.getLeftBenefitQuantity() );

                        // ������������Сʱ��������
                        double cacheUsedHours_FD = 0;
                        // ������������Сʱ��������
                        double cacheUsedHours_FL = 0;

                        if ( excelLeaveHoursMap.get( condition.getContractId() ) != null && excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FD" ) != null )
                           cacheUsedHours_FD = excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FD" );

                        if ( excelLeaveHoursMap.get( condition.getContractId() ) != null && excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FL" ) != null )
                           cacheUsedHours_FL = excelLeaveHoursMap.get( condition.getContractId() ).get( "41_FL" );

                        if ( dbLeftHours_FD + dbLeftHours_FL - cacheUsedHours_FD - cacheUsedHours_FL < hours )
                        {
                           row.setErrorMessange( "��" + rowNumber + "�У���ٿ���Сʱ����������������ϴ���" );
                           mainHasError = true;
                        }
                        else
                        {
                           // ��ֵ
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
                        // ��ֵ
                        estimateLegalHours = 0;
                        estimateBenefitHours = hours;
                     }

                     // �����Сʱ������Map & ��ȫҪ���������
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
    * ͬ�������ݿ�����
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
            // �����죬���ʲ���LeaveDetailVO
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
            // ���죬���ز���LeaveDetailVO
            else
            {
               addMultipleLeaveDetail( leaveHeaderVO );
            }
         }
         // �ύ����
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // TODO: handle exception
         // �ع�����
         this.rollbackTransaction();
         e.printStackTrace();
      }

      return false;
   }

   /**
    * ��ȫ�����е��ֶ�
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
    * �����ݿ����ת���� LeaveHeaderVO transform
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
               // ��ֵװ�ص�leaveHeaderVO
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
    * ��Ӷ��LeaveDetailVO
    * 
    * @param leaveHeaderVO
    * @throws KANException
    */
   private void addMultipleLeaveDetail( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // ��ȡEmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractVOMap.get( leaveHeaderVO.getContractId() );

      // ��ȡClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

      // ��ȡEmployeeContractLeaveVO�б��Ѵ���Left Hours��
      final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = employeeContractLeaveVOMap.get( leaveHeaderVO.getContractId() );

      // ��ʼ��EmployeeContractLeaveVO
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

      final String startDate = leaveHeaderVO.getEstimateStartDate();
      final String endDate = leaveHeaderVO.getEstimateEndDate();
      final Calendar startCalendar = KANUtil.createCalendar( startDate );
      final Calendar endCalendar = KANUtil.createCalendar( endDate );

      // ��ȡ��ٿ�����
      final long gap = KANUtil.getGapDays( endCalendar, startCalendar );

      // ѭ������
      for ( int i = 0; i <= gap; i++ )
      {
         // ��ʼ��LeaveDetailVO
         final LeaveDetailVO leaveDetailVO = generateLeaveDetailVO( leaveHeaderVO );

         // �������ʱ����ֹ

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

         // ��ȡ��ǰ�����Сʱ��
         final double currHours = new TimesheetDTO().getLeaveHours( leaveHeaderVO.getItemId(), leaveHeaderVO.getAccountId(), calendarId, shiftId, leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() );

         // ��������
         if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "41" ) )
         {
            if ( employeeContractLeaveVO != null )
            {
               // ��ȡ��ǰ����Э�飨��٣����õ�����Сʱ����
               final double legalHours = getUsedLegalHours( leaveHeaderVO.getAccountId(), leaveHeaderVO.getContractId(), leaveHeaderVO.getItemId() );
               final double legalQuantity = Double.valueOf( employeeContractLeaveVO.getLegalQuantity() != null ? employeeContractLeaveVO.getLegalQuantity() : "0" );

               // ���(��ǰ����+���÷���) �Ƚ� (�ܷ���)
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
         // ������Ŀ
         else
         {
            leaveDetailVO.setEstimateBenefitHours( String.valueOf( currHours ) );
         }

         startCalendar.add( Calendar.DATE, 1 );

         // ����LeaveHeaderVO�����Сʱ�����ڡ�0����
         if ( !leaveDetailVO.getEstimateStartDate().equals( leaveDetailVO.getEstimateEndDate() ) && currHours > 0 )
         {
            this.leaveDetailDao.insertLeaveDetailtemp( leaveDetailVO );
         }
      }
   }

   /**
    * ����LeaveHeaderVO���LeaveDetailVO
    */
   private LeaveDetailVO generateLeaveDetailVO( final LeaveHeaderVO leaveHeaderVO )
   {
      // ��ʼ��LeaveDetailVO
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

      // ��ȡClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderVOMap.get( employeeContractVO.getOrderId() );

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

   // ����������ȡ����б����÷���Сʱ��
   private double getUsedLegalHours( final String accountId, final String contractId, final String itemId ) throws KANException
   {
      // ��ʼ������ֵ
      double legalQuantity = 0.0;
      // ��ʼ����������
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setAccountId( accountId );
      leaveHeaderVO.setContractId( contractId );
      leaveHeaderVO.setItemId( itemId );

      // ��ȡ������б�
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

   // ��Ա���������Ϣ��ʱ����Map��
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

   // ����itemId�ҵ�Ա�����ݼ�����
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
