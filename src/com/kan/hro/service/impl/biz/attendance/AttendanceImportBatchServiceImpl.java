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
   // 注入各种Dao
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
         // 开启事务
         startTransaction();

         // 获取AttendanceImportHeaderVO列表
         final List< Object > attendanceImportHeaderVOs = this.getAttendanceImportHeaderDao().getAttendanceImportHeaderVOsByBatchId( commonBatchVO.getBatchId() );

         // 存在AttendanceImportHeaderVO列表
         if ( attendanceImportHeaderVOs != null && attendanceImportHeaderVOs.size() > 0 )
         {
            for ( Object oH : attendanceImportHeaderVOs )
            {
               ( ( AttendanceImportHeaderVO ) oH ).setModifyBy( commonBatchVO.getModifyBy() );
               ( ( AttendanceImportHeaderVO ) oH ).setModifyDate( new Date() );
               ( ( AttendanceImportHeaderVO ) oH ).setStatus( "3" );
               ( ( AttendanceImportHeaderVO ) oH ).setDeleted( "2" );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( ( AttendanceImportHeaderVO ) oH );

               // 获取AttendanceImportDetailVO列表
               final List< Object > attendanceImportDetailVOs = this.getAttendanceImportDetailDao().getAttendanceImportDetailVOsByHeaderId( ( ( AttendanceImportHeaderVO ) oH ).getHeaderId() );

               // 存在AttendanceImportDetailVO列表
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

         // 已退回
         commonBatchVO.setStatus( "3" );
         commonBatchVO.setDeleted( "2" );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int submitObject( final CommonBatchVO commonBatchVO ) throws KANException
   {
      int rows = 0;

      // 初始化参数
      String accountId = commonBatchVO.getAccountId();
      String corpId = commonBatchVO.getCorpId();
      String monthly = "";
      String userId = commonBatchVO.getModifyBy();
      try
      {
         // 获取AttendanceImportHeaderVO列表
         final List< Object > attendanceImportHeaderVOs = this.getAttendanceImportHeaderDao().getAttendanceImportHeaderVOsByBatchId( commonBatchVO.getBatchId() );

         if ( attendanceImportHeaderVOs != null && attendanceImportHeaderVOs.size() > 0 )
            monthly = ( ( AttendanceImportHeaderVO ) attendanceImportHeaderVOs.get( 0 ) ).getMonthly();
         else
            return -1;

         // 开启事务
         startTransaction();

         // 已更新
         commonBatchVO.setStatus( "2" );
         this.getCommonBatchDao().updateCommonBatch( commonBatchVO );

         // 生成考勤表批次
         String batchId = generateTimesheetBatch( accountId, corpId, monthly, userId );

         final List< Object > errorAttendanceImportHeaderVOs = new ArrayList< Object >();
         final List< Object > errorAttendanceImportDetailVOs = new ArrayList< Object >();
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            for ( Object o : attendanceImportHeaderVOs )
            {
               final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) o;
               // 获取EmployeeContractVO
               final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( attendanceImportHeaderVO.getContractId() );
               // 获取ClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

               String needAudit = "1";
               if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getAttendanceCheckType() != null && clientOrderHeaderVO.getAttendanceCheckType().trim().equals( "1" ) )
               {
                  needAudit = "2";
               }

               // 初始化TimesheetHeaderVO
               final TimesheetHeaderVO searchTimesheetHeaderVO = new TimesheetHeaderVO();
               searchTimesheetHeaderVO.setAccountId( accountId );
               searchTimesheetHeaderVO.setCorpId( corpId );
               searchTimesheetHeaderVO.setContractId( employeeContractVO.getContractId() );
               searchTimesheetHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
               searchTimesheetHeaderVO.setMonthly( monthly );

               if ( existsAttendanceData( searchTimesheetHeaderVO, userId ) == -1 )
               {
                  attendanceImportHeaderVO.setDescription( "提交失败，考勤表、请假、加班数据处在待审核状态，无法更新；" );
                  errorAttendanceImportHeaderVOs.add( attendanceImportHeaderVO );
               }

               // 生成考勤表
               String timesheetId = generateTimesheetHeader( employeeContractVO, clientOrderHeaderVO, attendanceImportHeaderVO, batchId, userId, needAudit );

               // 生成考勤数据
               errorAttendanceImportDetailVOs.addAll( generateAttendanceData( employeeContractVO, clientOrderHeaderVO, attendanceImportHeaderVO, userId, timesheetId ) );

               rows++;
            }
         }

         // 如果有不符合条件的AttendanceImportHeaderVO 或 AttendanceImportDetailVO
         if ( errorAttendanceImportHeaderVOs.size() > 0 || errorAttendanceImportDetailVOs.size() > 0 )
         {
            // 回滚事务
            this.rollbackTransaction();

            // 将错误信息传回
            for ( Object o : errorAttendanceImportHeaderVOs )
            {
               ( ( AttendanceImportHeaderVO ) o ).setModifyBy( userId );
               ( ( AttendanceImportHeaderVO ) o ).setModifyDate( new Date() );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( ( AttendanceImportHeaderVO ) o );
            }

            // 将错误信息传回
            for ( Object o : errorAttendanceImportDetailVOs )
            {
               ( ( AttendanceImportDetailVO ) o ).setModifyBy( userId );
               ( ( AttendanceImportDetailVO ) o ).setModifyDate( new Date() );
               this.getAttendanceImportDetailDao().updateAttendanceImportDetail( ( AttendanceImportDetailVO ) o );
            }

            return -1;
         }
         // 已更新
         else
         {
            for ( Object oH : attendanceImportHeaderVOs )
            {
               final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) oH;
               attendanceImportHeaderVO.setStatus( "2" );
               attendanceImportHeaderVO.setModifyBy( userId );
               attendanceImportHeaderVO.setModifyDate( new Date() );
               this.getAttendanceImportHeaderDao().updateAttendanceImportHeader( attendanceImportHeaderVO );

               // 获取AttendanceImportDetailVO列表
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

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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

      // 获取TimesheetDTO列表
      final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( searchTimesheetHeaderVO );

      // 存在TimesheetDTO列表
      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         // 考勤表待审核
         timesheetHeaderVO = timesheetDTOs.get( 0 ).getTimesheetHeaderVO();
         if ( timesheetHeaderVO.getStatus().equals( "2" ) )
            return -1;

         // 请假待审核
         leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByTimesheetId( timesheetHeaderVO.getHeaderId() );
         if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
         {
            leaveHeaderVO = this.getLeaveHeaderDao().getLeaveHeaderVOByLeaveHeaderId( ( ( LeaveDetailVO ) leaveDetailVOs.get( 0 ) ).getLeaveHeaderId() );
            if ( leaveHeaderVO != null && leaveHeaderVO.getStatus().equals( "2" ) )
               return -1;
         }

         // 加班待审核
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
         // 如果该批次下不存在考勤表，则标记删除该批次
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

   // 生成考勤数据 TimesheetBatchVO
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

   // 生成考勤数据 TimesheetHeaderVO
   private String generateTimesheetHeader( final EmployeeContractVO employeeContractVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final AttendanceImportHeaderVO attendanceImportHeaderVO, final String batchId, final String userId, final String needAudit ) throws KANException
   {
      // 初始化Circle Start Calendar
      final Calendar circleEndCalendar = KANUtil.getEndCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

      // 初始化Start Calendar
      Calendar startCalendar = KANUtil.getStartCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );
      // 初始化End Calendar
      Calendar endCalendar = KANUtil.getEndCalendar( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleEndDay() );

      // 初始化Contract Start Calendar
      final Calendar contractStartCalendar = KANUtil.createCalendar( employeeContractVO.getStartDate() );
      // 初始化ContractEnd Calendar
      final Calendar contractEndCalendar = KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null ? circleEndCalendar
            : KANUtil.createCalendar( employeeContractVO.getEndDate() );

      // 初始化Resign Calendar
      Calendar resignCalendar = null;

      if ( KANUtil.filterEmpty( employeeContractVO.getResignDate() ) != null )
      {
         resignCalendar = KANUtil.createCalendar( employeeContractVO.getResignDate() );
      }

      if ( KANUtil.getDays( startCalendar ) <= KANUtil.getDays( contractEndCalendar ) && KANUtil.getDays( endCalendar ) >= KANUtil.getDays( contractStartCalendar ) )
      {
         // 服务协议开始日期晚于薪资开始日期
         if ( KANUtil.getDays( startCalendar ) < KANUtil.getDays( contractStartCalendar ) )
         {
            startCalendar = contractStartCalendar;
         }

         // 服务协议结束日期早于薪资结束日期
         if ( KANUtil.getDays( endCalendar ) > KANUtil.getDays( contractEndCalendar ) )
         {
            endCalendar = contractEndCalendar;
         }

         // 离职日期早于服务协议结束日期
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

   // 生成请假OR加班(包含验证)return 不符合条件AttendanceImportDetailVO
   private List< Object > generateAttendanceData( final EmployeeContractVO employeeContractVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final AttendanceImportHeaderVO attendanceImportHeaderVO, final String userId, final String timesheetId ) throws KANException
   {
      // 初始化返回值
      final List< Object > errorObjects = new ArrayList< Object >();

      // 获取AttendanceImportDetailVO列表
      final List< Object > attendanceImportDetailVOs = this.getAttendanceImportDetailDao().getAttendanceImportDetailVOsByHeaderId( attendanceImportHeaderVO.getHeaderId() );

      // 初始化参数
      final String accountId = attendanceImportHeaderVO.getAccountId();
      final String corpId = attendanceImportHeaderVO.getCorpId();
      final String clientId = attendanceImportHeaderVO.getClientId();

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

      // 错误消息
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
               errorMsg = "提交失败，无效的科目；";
            }
            else
            {
               // 获取月份的第一天
               String monthFirstDay = KANUtil.getStartDate( attendanceImportHeaderVO.getMonthly(), clientOrderHeaderVO.getCircleStartDay() );

               // 请假或加班小时数不得为零
               if ( KANUtil.filterEmpty( hours ) == null || Double.valueOf( hours ) <= 0 )
                  errorMsg = "提交失败，" + ( "6".equals( itemVO.getItemType() ) ? "请假" : "加班" ) + "小时数无效；";

               // 如果是请假
               if ( KANUtil.filterEmpty( errorMsg ) == null && "6".equals( itemVO.getItemType() ) )
               {
                  // 获取该员工有效休假设置
                  final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = this.getLeaveHeaderService().getEmployeeContractLeaveVOsByContractId( employeeContractVO.getContractId() );
                  final EmployeeContractLeaveVO employeeContractLeaveVO = getEmployeeContractLeaveVOByItemId( employeeContractLeaveVOs, itemId );

                  // 必须有相应的休假设置
                  if ( employeeContractLeaveVO == null )
                  {
                     errorMsg = "提交失败，该员工没有['" + itemVO.getNameZH() + "']类别的休假设置；";
                  }
                  // 判断休假小时剩余
                  else
                  {
                     double leftHours = 0;
                     double totalHours = 0;
                     // 如果是年假
                     if ( itemId.equals( "41" ) )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() ) + Double.valueOf( employeeContractLeaveVO.getLeftLegalQuantity() );
                        if ( Double.valueOf( hours ) > leftHours )
                           errorMsg = "提交失败，该员工的['" + itemVO.getNameZH() + "']设置不足" + hours + "小时；";
                     }
                     // 如果是病假 - 全薪
                     else if ( itemId.equals( "42" ) )
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                        if ( Double.valueOf( hours ) > leftHours )
                           errorMsg = "提交失败，该员工的['" + itemVO.getNameZH() + "']设置不足" + hours + "小时；";
                     }
                     // 其他休假类别
                     else
                     {
                        leftHours = Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() );
                        totalHours = Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );
                        if ( totalHours > 0 && Double.valueOf( hours ) > leftHours )
                           errorMsg = "提交失败，该员工的['" + itemVO.getNameZH() + "']设置不足" + hours + "小时；";
                     }
                  }

                  // 通过验证，载入数据
                  if ( KANUtil.filterEmpty( errorMsg ) == null )
                  {
                     String esitmateEndDate = new LeaveHeaderAction().getLeaveEndDate( itemId, accountId, employeeContractVO.getContractId(), monthFirstDay, Double.valueOf( hours ), 0, employeeContractVO.getEndDate() );
                     // 初始化LeaveHeaderVO
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

                     // 如果是年假
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

                     // 回填TimesheetId
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
               // 如果是加班科目
               else if ( "4".equals( itemVO.getItemType() ) )
               {
                  // 加班科目必须
                  if ( !itemId.equals( workdayOTItemId ) && !itemId.equals( weekendOTItemId ) && !itemId.equals( holidayOTItemId ) )
                  {
                     errorMsg = "提交失败，该员工加班设置没有设置['" + itemVO.getNameZH() + "']；";
                  }
                  else
                  {
                     // 反向得到加班结束时间
                     String endDate = getOTEndDate( accountId, calendarId, shiftId, monthFirstDay, Double.valueOf( hours ) );

                     // 初始化OTHeaderVO
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
                     otHeaderVO.setDescription( itemVO.getNameZH() + "：" + KANUtil.formatNumber( hours, accountId ) + "小时；" );

                     this.getOtHeaderDao().insertOTHeader( otHeaderVO );

                     final Calendar startCalendar = KANUtil.createCalendar( otHeaderVO.getEstimateStartDate() );
                     final Calendar endCalendar = KANUtil.createCalendar( otHeaderVO.getEstimateEndDate() );
                     // 获取加班跨天数
                     final long gap = KANUtil.getGapDays( endCalendar, startCalendar );
                     // 循环插入
                     for ( int i = 0; i <= gap; i++ )
                     {
                        // 初始化OTDetailVO
                        final OTDetailVO otDetailVO = new OTDetailVO();
                        otDetailVO.setOtHeaderId( otHeaderVO.getOtHeaderId() );
                        otDetailVO.setTimesheetId( timesheetId );
                        otDetailVO.setItemId( otHeaderVO.getItemId() );
                        otDetailVO.setStatus( otHeaderVO.getStatus() );
                        otDetailVO.setCreateBy( otHeaderVO.getCreateBy() );
                        otDetailVO.setModifyBy( otHeaderVO.getModifyBy() );
                        // 设置加班时间起止
                        otDetailVO.setEstimateStartDate( i == 0 ? otHeaderVO.getEstimateStartDate() : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
                        otDetailVO.setEstimateEndDate( i == gap ? otHeaderVO.getEstimateEndDate()
                              : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

                        if ( !otDetailVO.getEstimateStartDate().equals( otDetailVO.getEstimateEndDate() ) )
                        {
                           // 获取当前天加班小时数
                           final double currDayOTHours = new TimesheetDTO().getOTHours( accountId, calendarId, shiftId, otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate(), 0 );
                           otDetailVO.setEstimateHours( String.valueOf( currDayOTHours ) );

                           // 插入OTDetailVO（加班小时数大于“0”）
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
                  errorMsg = "提交失败，该科目类型非加班或请假；";
               }
            }

            // 如果有错误，将错误对象纳入返回值
            if ( KANUtil.filterEmpty( errorMsg ) != null )
            {
               attendanceImportDetailVO.setDescription( errorMsg );
               errorObjects.add( attendanceImportDetailVO );
               // 重置错误消息
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

   // 已知X，Y，可求得X*Y=Z；那么已知X，Z，可求得Y=Z/X；
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
