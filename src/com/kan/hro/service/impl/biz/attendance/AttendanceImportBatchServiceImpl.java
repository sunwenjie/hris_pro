package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportDetailDao;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportBatchService;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction;

public class AttendanceImportBatchServiceImpl extends ContextService implements AttendanceImportBatchService
{
   // ע�����Dao
   private CommonBatchDao commonBatchDao;

   private ItemDao itemDao;

   private EmployeeContractDao employeeContractDao;

   private ClientOrderHeaderDao clientOrderHeaderDao;

   private AttendanceImportHeaderDao attendanceImportHeaderDao;

   private AttendanceImportDetailDao attendanceImportDetailDao;

   private OTHeaderDao otHeaderDao;

   private OTDetailDao otDetailDao;

   private LeaveHeaderDao leaveHeaderDao;

   private LeaveHeaderService leaveHeaderService;

   private LeaveDetailDao leaveDetailDao;

   private TimesheetBatchDao timesheetBatchDao;

   private TimesheetHeaderDao timesheetHeaderDao;

   private TimesheetHeaderService timesheetHeaderService;

   @Override
   // Add by siuvan 2014-12-26
   public int rollbackObject( final CommonBatchVO commonBatchVO ) throws KANException
   {
      try
      {
         // ��������
         startTransaction();

         // ��ȡAttendanceImportHeaderVO�б�
         final List< Object > attendanceImportHeaderVOs = this.getAttendanceImportHeaderDao().getAttendanceImportHeaderVOsByBatchId( commonBatchVO.getBatchId() );

         // ����AttendanceImportHeaderVO�б�
         if ( attendanceImportHeaderVOs != null && attendanceImportHeaderVOs.size() > 0 )
         {
            for ( Object oH : attendanceImportHeaderVOs )
            {
               ( ( AttendanceImportHeaderVO ) oH ).setModifyBy( commonBatchVO.getModifyBy() );
               ( ( AttendanceImportHeaderVO ) oH ).setModifyDate( new Date() );
               ( ( AttendanceImportHeaderVO ) oH ).setStatus( "3" );
               ( ( AttendanceImportHeaderVO ) oH ).setDeleted( "2" );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( ( AttendanceImportHeaderVO ) oH );

               // ��ȡAttendanceImportDetailVO�б�
               final List< Object > attendanceImportDetailVOs = this.getAttendanceImportDetailDao().getAttendanceImportDetailVOsByHeaderId( ( ( AttendanceImportHeaderVO ) oH ).getHeaderId() );

               // ����AttendanceImportDetailVO�б�
               if ( attendanceImportDetailVOs != null && attendanceImportDetailVOs.size() > 0 )
               {
                  for ( Object oD : attendanceImportDetailVOs )
                  {
                     ( ( AttendanceImportDetailVO ) oD ).setModifyBy( commonBatchVO.getModifyBy() );
                     ( ( AttendanceImportDetailVO ) oD ).setModifyDate( new Date() );
                     ( ( AttendanceImportDetailVO ) oD ).setStatus( "3" );
                     ( ( AttendanceImportDetailVO ) oD ).setDeleted( "2" );
                     this.getAttendanceImportDetailDao().updateAttendanceImportDetail( ( ( AttendanceImportDetailVO ) oD ) );
                  }
               }
            }
         }

         // ���˻�
         commonBatchVO.setStatus( "3" );
         commonBatchVO.setDeleted( "2" );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int submitObject( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int rows = 0;

      // ��ʼ������
      String accountId = commonBatchVO.getAccountId();
      String corpId = commonBatchVO.getCorpId();
      String monthly = "";
      String userId = commonBatchVO.getModifyBy();
      try
      {
         // ��ȡAttendanceImportHeaderVO�б�
         final List< Object > attendanceImportHeaderVOs = this.getAttendanceImportHeaderDao().getAttendanceImportHeaderVOsByBatchId( commonBatchVO.getBatchId() );

         if ( attendanceImportHeaderVOs != null && attendanceImportHeaderVOs.size() > 0 )
            monthly = ( ( AttendanceImportHeaderVO ) attendanceImportHeaderVOs.get( 0 ) ).getMonthly();
         else
            return -1;

         // ��������
         startTransaction();

         // �Ѹ���
         commonBatchVO.setStatus( "2" );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );

         // ���ɿ��ڱ�����
         String batchId = generateTimesheetBatch( accountId, corpId, monthly, userId );

         final List< Object > errorAttendanceImportHeaderVOs = new ArrayList< Object >();
         final List< Object > errorAttendanceImportDetailVOs = new ArrayList< Object >();
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            for ( Object o : attendanceImportHeaderVOs )
            {
               final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) o;
               // ��ȡEmployeeContractVO
               final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( attendanceImportHeaderVO.getContractId() );
               // ��ȡClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

               String needAudit = "1";
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getAttendanceCheckType() != null && clientOrderHeaderVO.getAttendanceCheckType().trim().equals( "1" ) )
               {
                  needAudit = "2";
               }

               // ��ʼ��TimesheetHeaderVO
               final TimesheetHeaderVO searchTimesheetHeaderVO = new TimesheetHeaderVO();
               searchTimesheetHeaderVO.setAccountId( accountId );
               searchTimesheetHeaderVO.setCorpId( corpId );
               searchTimesheetHeaderVO.setContractId( employeeContractVO.getContractId() );
               searchTimesheetHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
               searchTimesheetHeaderVO.setMonthly( monthly );

               if ( existsAttendanceData( searchTimesheetHeaderVO, userId ) == -1 )
               {
                  attendanceImportHeaderVO.setDescription( "�ύʧ�ܣ����ڱ���١��Ӱ����ݴ��ڴ����״̬���޷����£�" );
                  errorAttendanceImportHeaderVOs.add( attendanceImportHeaderVO );
               }

               // ���ɿ��ڱ�
               String timesheetId = generateTimesheetHeader( employeeContractVO, clientOrderHeaderVO, attendanceImportHeaderVO, batchId, userId, needAudit );

               // ���ɿ�������
               errorAttendanceImportDetailVOs.addAll( generateAttendanceData( employeeContractVO, clientOrderHeaderVO, attendanceImportHeaderVO, userId, timesheetId ) );

               rows++;
            }
         }

         // ����в�����������AttendanceImportHeaderVO �� AttendanceImportDetailVO
         if ( errorAttendanceImportHeaderVOs.size() > 0 || errorAttendanceImportDetailVOs.size() > 0 )
         {
            // �ع�����
            this.rollbackTransaction();

            // ��������Ϣ����
            for ( Object o : errorAttendanceImportHeaderVOs )
            {
               ( ( AttendanceImportHeaderVO ) o ).setModifyBy( userId );
               ( ( AttendanceImportHeaderVO ) o ).setModifyDate( new Date() );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( ( AttendanceImportHeaderVO ) o );
            }

            // ��������Ϣ����
            for ( Object o : errorAttendanceImportDetailVOs )
            {
               ( ( AttendanceImportDetailVO ) o ).setModifyBy( userId );
               ( ( AttendanceImportDetailVO ) o ).setModifyDate( new Date() );
               this.getAttendanceImportDetailDao().updateAttendanceImportDetail( ( AttendanceImportDetailVO ) o );
            }

            return -1;
         }
         // �Ѹ���
         else
         {
            for ( Object oH : attendanceImportHeaderVOs )
            {
               final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) oH;
               attendanceImportHeaderVO.setStatus( "2" );
               attendanceImportHeaderVO.setModifyBy( userId );
               attendanceImportHeaderVO.setModifyDate( new Date() );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( attendanceImportHeaderVO );

               // ��ȡAttendanceImportDetailVO�б�
               final List< Object > attendanceImportDetailVOs = this.getAttendanceImportDetailDao().getAttendanceImportDetailVOsByHeaderId( attendanceImportHeaderVO.getHeaderId() );
               if ( attendanceImportDetailVOs != null && attendanceImportDetailVOs.size() > 0 )
               {
                  for ( Object oD : attendanceImportDetailVOs )
                  {
                     final AttendanceImportDetailVO attendanceImportDetailVO = ( AttendanceImportDetailVO ) oD;
                     attendanceImportDetailVO.setStatus( "2" );
                     attendanceImportDetailVO.setModifyBy( userId );
                     attendanceImportDetailVO.setModifyDate( new Date() );
                     this.getAttendanceImportDetailDao().updateAttendanceImportDetail( attendanceImportDetailVO );
                  }
               }
            }
         }

         final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( batchId );
         timesheetBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd hh:mm:ss" ) );
         this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   private int existsAttendanceData( final TimesheetHeaderVO searchTimesheetHeaderVO, final String userId ) throws KANException
   {
      TimesheetHeaderVO timesheetHeaderVO = null;
      LeaveHeaderVO leaveHeaderVO = null;
      List< Object > leaveDetailVOs = null;
      OTHeaderVO otHeaderVO = null;
      List< Object > otDetailVOs = null;

      // ��ȡTimesheetDTO�б�
      final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( searchTimesheetHeaderVO );

      // ����TimesheetDTO�б�
      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         // ���ڱ�����
         timesheetHeaderVO = timesheetDTOs.get( 0 ).getTimesheetHeaderVO();
         if ( timesheetHeaderVO.getStatus().equals( "2" ) )
            return -1;

         // ��ٴ����
         leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByTimesheetId( timesheetHeaderVO.getHeaderId() );
         if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
         {
            leaveHeaderVO = this.getLeaveHeaderDao().getLeaveHeaderVOByLeaveHeaderId( ( ( LeaveDetailVO ) leaveDetailVOs.get( 0 ) ).getLeaveHeaderId() );
            if ( leaveHeaderVO != null && leaveHeaderVO.getStatus().equals( "2" ) )
               return -1;
         }

         // �Ӱ�����
         otDetailVOs = this.getOtDetailDao().getOTDetailVOsByTimesheetId( timesheetHeaderVO.getHeaderId() );
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            otHeaderVO = this.getOtHeaderDao().getOTHeaderVOByOTHeaderId( ( ( OTDetailVO ) otDetailVOs.get( 0 ) ).getOtHeaderId() );
            if ( otHeaderVO != null && ( otHeaderVO.getStatus().equals( "2" ) || otHeaderVO.getStatus().equals( "4" ) ) )
               return -1;
         }
      }

      if ( timesheetHeaderVO != null )
      {
         timesheetHeaderVO.setModifyBy( userId );
         timesheetHeaderVO.setModifyDate( new Date() );
         timesheetHeaderVO.setDeleted( "2" );
         this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );

         final List< Object > timesheetHeaderVOs = this.getTimesheetHeaderDao().getTimesheetHeaderVOsByBatchId( timesheetHeaderVO.getBatchId() );
         // ����������²����ڿ��ڱ�����ɾ��������
         if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() == 0 )
         {
            final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );
            timesheetBatchVO.setModifyBy( userId );
            timesheetBatchVO.setModifyDate( new Date() );
            timesheetBatchVO.setDeleted( BaseVO.FALSE );

            this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
         }
      }

      if ( leaveHeaderVO != null )
      {
         leaveHeaderVO.setModifyBy( userId );
         leaveHeaderVO.setModifyDate( new Date() );
         leaveHeaderVO.setDeleted( "2" );
         this.getLeaveHeaderDao().updateLeaveHeader( leaveHeaderVO );
      }

      if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
      {
         for ( Object o : leaveDetailVOs )
         {
            ( ( LeaveDetailVO ) o ).setModifyBy( userId );
            ( ( LeaveDetailVO ) o ).setModifyDate( new Date() );
            ( ( LeaveDetailVO ) o ).setDeleted( "2" );
            this.getLeaveDetailDao().updateLeaveDetail( ( ( LeaveDetailVO ) o ) );
         }
      }

      if ( otHeaderVO != null )
      {
         otHeaderVO.setModifyBy( userId );
         otHeaderVO.setModifyDate( new Date() );
         otHeaderVO.setDeleted( "2" );
         this.getOtHeaderDao().updateOTHeader( otHeaderVO );
      }

      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         for ( Object o : otDetailVOs )
         {
            ( ( OTDetailVO ) o ).setModifyBy( userId );
            ( ( OTDetailVO ) o ).setModifyDate( new Date() );
            ( ( OTDetailVO ) o ).setDeleted( "2" );
            this.getOtDetailDao().updateOTDetail( ( ( OTDetailVO ) o ) );
         }
      }

      return 1;
   }

   // ���ɿ������� TimesheetBatchVO
   private String generateTimesheetBatch( final String accountId, final String corpId, final String monthly, final String userId ) throws KANException
   {
      final TimesheetBatchVO timesheetBatchVO = new TimesheetBatchVO();
      timesheetBatchVO.setAccountId( accountId );
      timesheetBatchVO.setEntityId( "0" );
      timesheetBatchVO.setBusinessTypeId( "0" );
      timesheetBatchVO.setClientId( null );
      timesheetBatchVO.setCorpId( corpId );
      timesheetBatchVO.setMonthly( monthly );
      timesheetBatchVO.setDeleted( "1" );
      timesheetBatchVO.setStatus( "1" );
      timesheetBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd hh:mm:ss" ) );
      timesheetBatchVO.setCreateBy( userId );
      timesheetBatchVO.setCreateDate( new Date() );
      timesheetBatchVO.setModifyBy( userId );
      timesheetBatchVO.setModifyDate( new Date() );

      this.getTimesheetBatchDao().insertTimesheetBatch( timesheetBatchVO );

      return timesheetBatchVO.getBatchId();
   }

   // ���ɿ������� TimesheetHeaderVO
   private String generateTimesheetHeader( final EmployeeContractVO employeeContractVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final AttendanceImportHeaderVO attendanceImportHeaderVO, final String batchId, final String userId, final String needAudit ) throws KANException
   {
      // ��ʼ��Circle Start Calendar
      final Calendar circleEndCalendar = KANUtil.getEndCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

      // ��ʼ��Start Calendar
      Calendar startCalendar = KANUtil.getStartCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );
      // ��ʼ��End Calendar
      Calendar endCalendar = KANUtil.getEndCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

      // ��ʼ��Contract Start Calendar
      final Calendar contractStartCalendar = KANUtil.createCalendar( employeeContractVO.getStartDate() );
      // ��ʼ��ContractEnd Calendar
      final Calendar contractEndCalendar = KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null ? circleEndCalendar
            : KANUtil.createCalendar( employeeContractVO.getEndDate() );

      // ��ʼ��Resign Calendar
      Calendar resignCalendar = null;

      if ( KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null )
      {
         resignCalendar = KANUtil.createCalendar( employeeContractVO.getResignDate() );
      }

      if ( KANUtil.getDays( startCalendar ) <= KANUtil.getDays( contractEndCalendar ) && KANUtil.getDays( endCalendar ) >= KANUtil.getDays( contractStartCalendar ) )
      {
         // ����Э�鿪ʼ��������н�ʿ�ʼ����
         if ( KANUtil.getDays( startCalendar ) < KANUtil.getDays( contractStartCalendar ) )
         {
            startCalendar = contractStartCalendar;
         }

         // ����Э�������������н�ʽ�������
         if ( KANUtil.getDays( endCalendar ) > KANUtil.getDays( contractEndCalendar ) )
         {
            endCalendar = contractEndCalendar;
         }

         // ��ְ�������ڷ���Э���������
         if ( resignCalendar != null && KANUtil.getDays( endCalendar ) > KANUtil.getDays( resignCalendar ) )
         {
            endCalendar = resignCalendar;
         }

         if ( startCalendar != null && endCalendar != null && KANUtil.getDays( startCalendar ) <= KANUtil.getDays( endCalendar ) )
         {
            final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
            timesheetHeaderVO.setAccountId( attendanceImportHeaderVO.getAccountId() );
            timesheetHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
            timesheetHeaderVO.setContractId( attendanceImportHeaderVO.getContractId() );
            timesheetHeaderVO.setClientId( attendanceImportHeaderVO.getClientId() );
            timesheetHeaderVO.setCorpId( attendanceImportHeaderVO.getCorpId() );
            timesheetHeaderVO.setOrderId( employeeContractVO.getOrderId() );
            timesheetHeaderVO.setBatchId( batchId );
            timesheetHeaderVO.setMonthly( attendanceImportHeaderVO.getMonthly() );
            timesheetHeaderVO.setStartDate( KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) );
            timesheetHeaderVO.setEndDate( KANUtil.formatDate( endCalendar.getTime(), "yyyy-MM-dd" ) );
            timesheetHeaderVO.setTotalWorkHours( attendanceImportHeaderVO.getTotalWorkHours() );
            timesheetHeaderVO.setTotalWorkDays( attendanceImportHeaderVO.getTotalWorkDays() );
            timesheetHeaderVO.setTotalFullHours( attendanceImportHeaderVO.getTotalFullHours() );
            timesheetHeaderVO.setTotalFullDays( attendanceImportHeaderVO.getTotalFullDays() );
            timesheetHeaderVO.setNeedAudit( needAudit );
            timesheetHeaderVO.setIsNormal( attendanceImportHeaderVO.getTotalWorkHours().equals( attendanceImportHeaderVO.getTotalFullHours() ) ? "1" : "2" );
            timesheetHeaderVO.setDeleted( "1" );
            timesheetHeaderVO.setStatus( "1" );
            timesheetHeaderVO.setCreateBy( userId );
            timesheetHeaderVO.setCreateDate( new Date() );
            timesheetHeaderVO.setModifyBy( userId );
            timesheetHeaderVO.setModifyDate( new Date() );

            this.getTimesheetHeaderDao().insertTimesheetHeader( timesheetHeaderVO );
            return timesheetHeaderVO.getHeaderId();
         }
      }

      return null;
   }

   // �������OR�Ӱ�(������֤)return ����������AttendanceImportDetailVO
   private List< Object > generateAttendanceData( final EmployeeContractVO employeeContractVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final AttendanceImportHeaderVO attendanceImportHeaderVO, final String userId, final String timesheetId ) throws KANException
   {
      // ��ʼ������ֵ
      final List< Object > errorObjects = new ArrayList< Object >();

      // ��ȡAttendanceImportDetailVO�б�
      final List< Object > attendanceImportDetailVOs = this.getAttendanceImportDetailDao().getAttendanceImportDetailVOsByHeaderId( attendanceImportHeaderVO.getHeaderId() );

      // ��ʼ������
      final String accountId = attendanceImportHeaderVO.getAccountId();
      final String corpId = attendanceImportHeaderVO.getCorpId();
      final String clientId = attendanceImportHeaderVO.getClientId();

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

      // ������Ϣ
      String errorMsg = "";
      if ( attendanceImportDetailVOs != null && attendanceImportDetailVOs.size() > 0 )
      {
         for ( Object o : attendanceImportDetailVOs )
         {
            final AttendanceImportDetailVO attendanceImportDetailVO = ( AttendanceImportDetailVO ) o;
            final String itemId = attendanceImportDetailVO.getItemId();
            final String hours = attendanceImportDetailVO.getHours();
            final ItemVO itemVO = this.getItemDao().getItemVOByItemId( itemId );

            attendanceImportDetailVO.setStatus( "2" );
            attendanceImportDetailVO.setModifyBy( userId );
            attendanceImportDetailVO.setModifyDate( new Date() );

            if ( itemVO == null )
            {
               errorMsg = "�ύʧ�ܣ���Ч�Ŀ�Ŀ��";
            }
            else
            {
               // ��ȡ�·ݵĵ�һ��
               String monthFirstDay = KANUtil.getStartDate( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );

               // ��ٻ�Ӱ�Сʱ������Ϊ��
               if ( KANUtil.filterEmpty( hours ) == null || Double.valueOf( hours ) <= 0 )
                  errorMsg = "�ύʧ�ܣ�" + ( "6".equals( itemVO.getItemType() ) ? "���" : "�Ӱ�" ) + "Сʱ����Ч��";

               // ��������
               if ( KANUtil.filterEmpty( errorMsg ) == null && "6".equals( itemVO.getItemType() ) )
               {
                  // ��ȡ��Ա����Ч�ݼ�����
                  final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = this.getLeaveHeaderService().getEmployeeContractLeaveVOsByContractId( employeeContractVO.getContractId() );
                  final EmployeeContractLeaveVO employeeContractLeaveVO = getEmployeeContractLeaveVOByItemId( employeeContractLeaveVOs, itemId );

                  // ��������Ӧ���ݼ�����
                  if ( employeeContractLeaveVO == null )
                  {
                     errorMsg = "�ύʧ�ܣ���Ա��û��['" + itemVO.getNameZH() + "']�����ݼ����ã�";
                  }
                  // �ж��ݼ�Сʱʣ��
                  else
                  {
                     double leftHours = 0;
                     double totalHours = 0;
                     // ��������
                     if ( itemId.equals( "41" ) )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() );
                        if ( Double.valueOf( hours ) > leftHours )
                           errorMsg = "�ύʧ�ܣ���Ա����['" + itemVO.getNameZH() + "']���ò���" + hours + "Сʱ��";
                     }
                     // ����ǲ��� - ȫн
                     else if ( itemId.equals( "42" ) )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                        if ( Double.valueOf( hours ) > leftHours )
                           errorMsg = "�ύʧ�ܣ���Ա����['" + itemVO.getNameZH() + "']���ò���" + hours + "Сʱ��";
                     }
                     // �����ݼ����
                     else
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                        totalHours = Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );
                        if ( totalHours > 0 && Double.valueOf( hours ) > leftHours )
                           errorMsg = "�ύʧ�ܣ���Ա����['" + itemVO.getNameZH() + "']���ò���" + hours + "Сʱ��";
                     }
                  }

                  // ͨ����֤����������
                  if ( KANUtil.filterEmpty( errorMsg ) == null )
                  {
                     String esitmateEndDate = new LeaveHeaderAction().getLeaveEndDate( itemId, accountId, employeeContractVO.getContractId(), monthFirstDay, Double.valueOf( hours ), 0, employeeContractVO.getEndDate() );
                     // ��ʼ��LeaveHeaderVO
                     final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
                     leaveHeaderVO.setAccountId( accountId );
                     leaveHeaderVO.setCorpId( corpId );
                     leaveHeaderVO.setClientId( clientId );
                     leaveHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
                     leaveHeaderVO.setContractId( employeeContractVO.getContractId() );
                     leaveHeaderVO.setItemId( itemId );
                     leaveHeaderVO.setEstimateStartDate( monthFirstDay );
                     leaveHeaderVO.setEstimateEndDate( esitmateEndDate );
                     leaveHeaderVO.setRetrieveStatus( "1" );
                     leaveHeaderVO.setDataFrom( "2" );
                     leaveHeaderVO.setDeleted( "1" );
                     leaveHeaderVO.setStatus( "1" );
                     leaveHeaderVO.setCreateBy( userId );
                     leaveHeaderVO.setModifyBy( userId );

                     // ��������
                     if ( itemId.equals( "41" ) )
                     {
                        double left_legal = Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() );
                        leaveHeaderVO.setEstimateLegalHours( Double.valueOf( hours ) > left_legal ? employeeContractLeaveVO.getLeftLegalQuantity() : hours );
                        leaveHeaderVO.setEstimateBenefitHours( Double.valueOf( hours ) > left_legal ? String.valueOf( Double.valueOf( hours ) - left_legal ) : "0" );
                     }
                     else
                     {
                        leaveHeaderVO.setEstimateBenefitHours( hours );
                     }

                     this.getLeaveHeaderService().insertLeaveHeader( leaveHeaderVO );

                     // ����TimesheetId
                     final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
                     if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
                     {
                        for ( Object tempLeaveDetailVO : leaveDetailVOs )
                        {
                           final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) tempLeaveDetailVO;
                           leaveDetailVO.setTimesheetId( timesheetId );
                           this.getLeaveDetailDao().updateLeaveDetail( leaveDetailVO );
                        }
                     }
                  }
               }
               // ����ǼӰ��Ŀ
               else if ( "4".equals( itemVO.getItemType() ) )
               {
                  // �Ӱ��Ŀ����
                  if ( !itemId.equals( workdayOTItemId ) && !itemId.equals( weekendOTItemId ) && !itemId.equals( holidayOTItemId ) )
                  {
                     errorMsg = "�ύʧ�ܣ���Ա���Ӱ�����û������['" + itemVO.getNameZH() + "']��";
                  }
                  else
                  {
                     // ����õ��Ӱ����ʱ��
                     String endDate = getOTEndDate( accountId, calendarId, shiftId, monthFirstDay, Double.valueOf( hours ) );

                     // ��ʼ��OTHeaderVO
                     final OTHeaderVO otHeaderVO = new OTHeaderVO();
                     otHeaderVO.setAccountId( accountId );
                     otHeaderVO.setCorpId( corpId );
                     otHeaderVO.setClientId( clientId );
                     otHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
                     otHeaderVO.setContractId( employeeContractVO.getContractId() );
                     otHeaderVO.setItemId( itemId );
                     otHeaderVO.setEstimateStartDate( monthFirstDay );
                     otHeaderVO.setEstimateEndDate( endDate );
                     otHeaderVO.setEstimateHours( hours );
                     otHeaderVO.setDataFrom( "2" );
                     otHeaderVO.setDeleted( "1" );
                     otHeaderVO.setStatus( "1" );
                     otHeaderVO.setCreateBy( userId );
                     otHeaderVO.setModifyBy( userId );
                     otHeaderVO.setDescription( itemVO.getNameZH() + "��" + KANUtil.formatNumber( hours, accountId ) + "Сʱ��" );

                     this.getOtHeaderDao().insertOTHeader( otHeaderVO );

                     final Calendar startCalendar = KANUtil.createCalendar( otHeaderVO.getEstimateStartDate() );
                     final Calendar endCalendar = KANUtil.createCalendar( otHeaderVO.getEstimateEndDate() );
                     // ��ȡ�Ӱ������
                     final long gap = KANUtil.getGapDays( endCalendar, startCalendar );
                     // ѭ������
                     for ( int i = 0; i <= gap; i++ )
                     {
                        // ��ʼ��OTDetailVO
                        final OTDetailVO otDetailVO = new OTDetailVO();
                        otDetailVO.setOtHeaderId( otHeaderVO.getOtHeaderId() );
                        otDetailVO.setTimesheetId( timesheetId );
                        otDetailVO.setItemId( otHeaderVO.getItemId() );
                        otDetailVO.setStatus( otHeaderVO.getStatus() );
                        otDetailVO.setCreateBy( otHeaderVO.getCreateBy() );
                        otDetailVO.setModifyBy( otHeaderVO.getModifyBy() );
                        // ���üӰ�ʱ����ֹ
                        otDetailVO.setEstimateStartDate( i == 0 ? otHeaderVO.getEstimateStartDate() : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
                        otDetailVO.setEstimateEndDate( i == gap ? otHeaderVO.getEstimateEndDate()
                              : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

                        if ( !otDetailVO.getEstimateStartDate().equals( otDetailVO.getEstimateEndDate() ) )
                        {
                           // ��ȡ��ǰ��Ӱ�Сʱ��
                           final double currDayOTHours = new TimesheetDTO().getOTHours( accountId, calendarId, shiftId, otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate(), 0 );
                           otDetailVO.setEstimateHours( String.valueOf( currDayOTHours ) );

                           // ����OTDetailVO���Ӱ�Сʱ�����ڡ�0����
                           if ( Double.valueOf( otDetailVO.getEstimateHours() ) > 0 )
                           {
                              this.getOtDetailDao().insertOTDetail( otDetailVO );
                           }

                           startCalendar.add( Calendar.DATE, 1 );
                        }
                     }
                  }
               }
               else
               {
                  errorMsg = "�ύʧ�ܣ��ÿ�Ŀ���ͷǼӰ����٣�";
               }
            }

            // ����д��󣬽�����������뷵��ֵ
            if ( KANUtil.filterEmpty( errorMsg ) != null )
            {
               attendanceImportDetailVO.setDescription( errorMsg );
               errorObjects.add( attendanceImportDetailVO );
               // ���ô�����Ϣ
               errorMsg = "";
            }
         }
      }

      return errorObjects;
   }

   private EmployeeContractLeaveVO getEmployeeContractLeaveVOByItemId( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final String itemId )
   {
      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
         {
            if ( employeeContractLeaveVO.getItemId().equals( itemId ) )
            {
               return employeeContractLeaveVO;
            }
         }
      }

      return null;
   }

   // ��֪X��Y�������X*Y=Z����ô��֪X��Z�������Y=Z/X��
   private String getOTEndDate( final String accountId, final String calendarId, final String shiftId, final String startDate, double otHours ) throws KANException
   {
      int count = 2000;
      String tempEndDate = "";
      double tempOTHours = 0;
      Calendar tempCalendar = KANUtil.createCalendar( startDate );
      while ( 1 > 0 )
      {
         tempCalendar.add( Calendar.MINUTE, 30 );
         tempEndDate = KANUtil.formatDate( tempCalendar.getTime(), "yyyy-MM-dd hh:mm:ss" );
         tempOTHours = new TimesheetDTO().getOTHours( accountId, calendarId, shiftId, startDate, tempEndDate, 0 );

         if ( otHours == tempOTHours )
            break;

         if ( count == 0 )
            break;
         count--;
      }
      return tempEndDate;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public ItemDao getItemDao()
   {
      return itemDao;
   }

   public void setItemDao( ItemDao itemDao )
   {
      this.itemDao = itemDao;
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

   public AttendanceImportHeaderDao getAttendanceImportHeaderDao()
   {
      return attendanceImportHeaderDao;
   }

   public void setAttendanceImportHeaderDao( AttendanceImportHeaderDao attendanceImportHeaderDao )
   {
      this.attendanceImportHeaderDao = attendanceImportHeaderDao;
   }

   public AttendanceImportDetailDao getAttendanceImportDetailDao()
   {
      return attendanceImportDetailDao;
   }

   public void setAttendanceImportDetailDao( AttendanceImportDetailDao attendanceImportDetailDao )
   {
      this.attendanceImportDetailDao = attendanceImportDetailDao;
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

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public LeaveHeaderService getLeaveHeaderService()
   {
      return leaveHeaderService;
   }

   public void setLeaveHeaderService( LeaveHeaderService leaveHeaderService )
   {
      this.leaveHeaderService = leaveHeaderService;
   }

   public LeaveDetailDao getLeaveDetailDao()
   {
      return leaveDetailDao;
   }

   public void setLeaveDetailDao( LeaveDetailDao leaveDetailDao )
   {
      this.leaveDetailDao = leaveDetailDao;
   }

   public TimesheetBatchDao getTimesheetBatchDao()
   {
      return timesheetBatchDao;
   }

   public void setTimesheetBatchDao( TimesheetBatchDao timesheetBatchDao )
   {
      this.timesheetBatchDao = timesheetBatchDao;
   }

   public TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public TimesheetHeaderService getTimesheetHeaderService()
   {
      return timesheetHeaderService;
   }

   public void setTimesheetHeaderService( TimesheetHeaderService timesheetHeaderService )
   {
      this.timesheetHeaderService = timesheetHeaderService;
   }

}
