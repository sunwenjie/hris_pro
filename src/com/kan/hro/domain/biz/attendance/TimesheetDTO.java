package com.kan.hro.domain.biz.attendance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.ShiftDTO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class TimesheetDTO implements Serializable
{
   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -1439726633072631214L;

   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   // Service Contract 
   private EmployeeContractVO employeeContractVO;

   // Client Order Header 
   private ClientOrderHeaderVO ClientOrderHeaderVO;

   // Leave - DTO
   private List< LeaveDTO > leaveDTOs = new ArrayList< LeaveDTO >();

   // OT - DTO
   private List< OTDTO > otDTOs = new ArrayList< OTDTO >();

   // Employee Contract Salary - VO
   private List< EmployeeContractSalaryVO > employeeContractSalaryVOs = new ArrayList< EmployeeContractSalaryVO >();

   // Timesheet Header (Calculate Result)
   private TimesheetHeaderVO timesheetHeaderVO;

   // Timesheet Detail (Calculate Result) - VO
   private List< TimesheetDetailVO > timesheetDetailVOs = new ArrayList< TimesheetDetailVO >();

   // Timesheet Allowance (Calculate Result) - VO
   private List< TimesheetAllowanceVO > timesheetAllowanceVOs = new ArrayList< TimesheetAllowanceVO >();

   // 请假
   public final static String EXCEPTION_TYPE_LEAVE = "1";

   // 加班
   public final static String EXCEPTION_TYPE_OT = "2";

   // 忽略请假周末、法定节假日也要克扣时间的itemId
   public final static String[] IGNORE_WEEKEND_AND_HOLIDAY_ITEMID_ARRAY = new String[] { "45", "46", "50", "10211" };

   public TimesheetHeaderVO getTimesheetHeaderVO()
   {
      return timesheetHeaderVO;
   }

   public void setTimesheetHeaderVO( TimesheetHeaderVO timesheetHeaderVO )
   {
      this.timesheetHeaderVO = timesheetHeaderVO;
   }

   public List< TimesheetDetailVO > getTimesheetDetailVOs()
   {
      return timesheetDetailVOs;
   }

   public void setTimesheetDetailVOs( List< TimesheetDetailVO > timesheetDetailVOs )
   {
      this.timesheetDetailVOs = timesheetDetailVOs;
   }

   public List< TimesheetAllowanceVO > getTimesheetAllowanceVOs()
   {
      return timesheetAllowanceVOs;
   }

   public void setTimesheetAllowanceVOs( List< TimesheetAllowanceVO > timesheetAllowanceVOs )
   {
      this.timesheetAllowanceVOs = timesheetAllowanceVOs;
   }

   public EmployeeContractVO getEmployeeContractVO()
   {
      return employeeContractVO;
   }

   public void setEmployeeContractVO( EmployeeContractVO employeeContractVO )
   {
      this.employeeContractVO = employeeContractVO;
   }

   public ClientOrderHeaderVO getClientOrderHeaderVO()
   {
      return ClientOrderHeaderVO;
   }

   public void setClientOrderHeaderVO( ClientOrderHeaderVO clientOrderHeaderVO )
   {
      ClientOrderHeaderVO = clientOrderHeaderVO;
   }

   public final List< LeaveDTO > getLeaveDTOs()
   {
      return leaveDTOs;
   }

   public final void setLeaveDTOs( List< LeaveDTO > leaveDTOs )
   {
      this.leaveDTOs = leaveDTOs;
   }

   public final List< OTDTO > getOtDTOs()
   {
      return otDTOs;
   }

   public final void setOtDTOs( List< OTDTO > otDTOs )
   {
      this.otDTOs = otDTOs;
   }

   public List< EmployeeContractSalaryVO > getEmployeeContractSalaryVOs()
   {
      return employeeContractSalaryVOs;
   }

   public void setEmployeeContractSalaryVOs( List< EmployeeContractSalaryVO > employeeContractSalaryVOs )
   {
      this.employeeContractSalaryVOs = employeeContractSalaryVOs;
   }

   // Modify by Siuvan Xia at 2014-5-30
   public List< LeaveDetailVO > getLeaveDetailVOsByDay( final String day ) throws KANException
   {
      // 初始化LeaveDetailVO列表
      final List< LeaveDetailVO > leaveDetailVOs = new ArrayList< LeaveDetailVO >();

      if ( this.leaveDTOs != null && this.leaveDTOs.size() > 0 )
      {
         for ( LeaveDTO leaveDTO : this.leaveDTOs )
         {
            if ( leaveDTO.getLeaveDetailVOs() != null && leaveDTO.getLeaveDetailVOs().size() > 0 )
            {
               for ( LeaveDetailVO leaveDetailVO : leaveDTO.getLeaveDetailVOs() )
               {
                  // 忽略 销假、加班换休
                  if ( KANUtil.filterEmpty( leaveDetailVO.getItemId() ) != null && !KANUtil.filterEmpty( leaveDetailVO.getItemId() ).equals( "60" )
                        && !KANUtil.filterEmpty( leaveDetailVO.getItemId() ).equals( "25" ) )
                  {
                     // 默认取Estimate日期
                     String dayString = KANUtil.formatDate( KANUtil.createDate( leaveDetailVO.getEstimateStartDate() ), "yyyy-MM-dd" );

                     // 销假情况
                     if ( leaveDetailVO.getRetrieveStatus() != null && leaveDetailVO.getRetrieveStatus().trim().equals( "3" ) )
                     {
                        dayString = KANUtil.formatDate( KANUtil.createDate( leaveDetailVO.getActualStartDate() ), "yyyy-MM-dd" );
                     }

                     if ( dayString.equals( KANUtil.formatDate( KANUtil.createDate( day ), "yyyy-MM-dd" ) ) )
                     {
                        leaveDetailVOs.add( leaveDetailVO );
                     }
                  }
               }
            }
         }
      }

      return leaveDetailVOs;
   }

   // 获取备注
   public static String getRemartkString( final String startDate, final String endDate )
   {
      if ( KANUtil.filterEmpty( startDate ) != null && KANUtil.filterEmpty( endDate ) != null )
      {
         return startDate.split( " " )[ 1 ] + " ~ " + ( endDate.split( " " )[ 1 ].equals( "00:00" ) ? "24:00" : endDate.split( " " )[ 1 ] );
      }

      return null;
   }

   private void setRemark( final TimesheetDetailVO timesheetDetailVO, final EmployeeContractVO employeeContractVO ) throws KANException
   {

      final StringBuffer rs = new StringBuffer();

      if ( leaveDTOs != null && leaveDTOs.size() > 0 )
      {
         final List< MappingVO > leaveItems = KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getLeaveItems( "ZH", employeeContractVO.getCorpId() );
         leaveItems.add( KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getItemByItemId( "25", "ZH" ) );

         for ( LeaveDTO leaveDTO : leaveDTOs )
         {
            final List< LeaveDetailVO > leaveDetailVOs = leaveDTO.getLeaveDetailVOs();

            if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
            {
               for ( LeaveDetailVO leaveDetailVO : leaveDetailVOs )
               {
                  if ( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ).equals( timesheetDetailVO.getDay() ) )
                  {
                     rs.append( KANUtil.getMappingValueByMappingList( leaveItems, leaveDetailVO.getItemId() ) + "（假）："
                           + getRemartkString( leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() ) + "；" );
                  }
               }
            }
         }
      }

      if ( otDTOs != null && otDTOs.size() > 0 )
      {
         final List< MappingVO > otItems = KANConstants.getOtItems( "ZH" );

         for ( OTDTO otDTO : otDTOs )
         {
            final List< OTDetailVO > otDetailVOs = otDTO.getOtDetailVOs();

            if ( otDetailVOs != null && otDetailVOs.size() > 0 )
            {
               for ( OTDetailVO otDetailVO : otDetailVOs )
               {
                  if ( KANUtil.formatDate( otDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ).equals( timesheetDetailVO.getDay() ) )
                  {
                     rs.append( KANUtil.getMappingValueByMappingList( otItems, otDetailVO.getItemId() ) + "（班）："
                           + getRemartkString( otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate() ) + "；" );
                  }
               }
            }
         }
      }

      timesheetDetailVO.setDescription( rs.toString() );
   }

   // 生成考勤表
   public boolean calculateTimesheet() throws KANException
   {
      // 初始化Circle Start Calendar
      final Calendar circleStartCalendar = KANUtil.getStartCalendar( this.getEmployeeContractVO().getMonthly(), this.getClientOrderHeaderVO().getCircleStartDay() );
      // 初始化Circle Start Calendar
      final Calendar circleEndCalendar = KANUtil.getEndCalendar( this.getEmployeeContractVO().getMonthly(), this.getClientOrderHeaderVO().getCircleEndDay() );

      // 初始化Start Calendar
      Calendar startCalendar = KANUtil.getStartCalendar( this.getEmployeeContractVO().getMonthly(), this.getClientOrderHeaderVO().getCircleStartDay() );
      // 初始化End Calendar
      Calendar endCalendar = KANUtil.getEndCalendar( this.getEmployeeContractVO().getMonthly(), this.getClientOrderHeaderVO().getCircleEndDay() );

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
            // 初始化AccountId
            final String accountId = this.getEmployeeContractVO().getAccountId();

            // 初始化开始日期和结束日期字符串
            final String startDateString = KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" );
            final String endDateString = KANUtil.formatDate( endCalendar.getTime(), "yyyy-MM-dd" );

            // 获取CalendarId
            String calendarId = this.employeeContractVO.getCalendarId();
            if ( KANUtil.filterEmpty( calendarId, "0" ) == null )
            {
               calendarId = this.ClientOrderHeaderVO.getCalendarId();
            }

            // 获取ShiftId
            String shiftId = this.employeeContractVO.getShiftId();
            if ( KANUtil.filterEmpty( shiftId, "0" ) == null )
            {
               shiftId = this.ClientOrderHeaderVO.getShiftId();
            }

            // 初始化统计参数
            double totalWorkHours = 0;
            double totalWorkDays = 0;
            double totalFullHours = 0;
            double totalFullDays = 0;

            // 初始化天数
            long circleStartDays = KANUtil.getDays( circleStartCalendar );
            final long circleEndDays = KANUtil.getDays( circleEndCalendar );
            final long startDays = KANUtil.getDays( startCalendar );
            final long endDays = KANUtil.getDays( endCalendar );

            // 装载TimesheetDetailVO
            while ( circleStartDays <= circleEndDays )
            {
               // 初始化当天全勤小时数
               double fullHours = getWorkHours( accountId, calendarId, shiftId, circleStartCalendar, circleStartCalendar );

               if ( circleStartDays >= startDays && circleStartDays <= endDays )
               {
                  // 初始化工作小时数（只取当天）
                  double workHours = fullHours == 0 ? 0 : fullHours
                        - getLeaveHours( accountId, calendarId, shiftId, this.getLeaveDetailVOsByDay( KANUtil.formatDate( circleStartCalendar.getTime(), "yyyy-MM-dd" ) ) );

                  // 工作小时负数处理
                  if ( workHours < 0 )
                  {
                     workHours = 0;
                  }

                  // 获得ShiftDetailVO
                  final ShiftDetailVO shiftDetailVO = getShiftDetailVO( accountId, calendarId, shiftId, circleStartCalendar, "0" );

                  // 初始化DayType
                  final String dayType = shiftDetailVO == null ? null : shiftDetailVO.getDayType();

                  // 初始化TimesheetDetailVO
                  final TimesheetDetailVO timesheetDetailVO = new TimesheetDetailVO();
                  timesheetDetailVO.setDay( KANUtil.formatDate( circleStartCalendar.getTime(), "yyyy-MM-dd" ) );
                  timesheetDetailVO.setDayType( dayType );
                  timesheetDetailVO.setWorkHours( String.valueOf( workHours ) );
                  timesheetDetailVO.setWorkPeriod( shiftDetailVO == null ? null : shiftDetailVO.getShiftPeriod() );
                  timesheetDetailVO.setFullHours( String.valueOf( fullHours ) );
                  timesheetDetailVO.setStatus( TimesheetDetailVO.TRUE );

                  setRemark( timesheetDetailVO, employeeContractVO );
                  this.getTimesheetDetailVOs().add( timesheetDetailVO );

                  // 设置统计字段
                  totalWorkHours = totalWorkHours + workHours;
                  if ( dayType != null && dayType.trim().equals( "1" ) )
                  {
                     if ( fullHours != 0 )
                     {
                        totalWorkDays = totalWorkDays + workHours / fullHours;
                     }
                  }
               }

               // 设置全勤天数
               if ( fullHours > 0 )
               {
                  totalFullDays++;
               }

               // 设置全勤小时数
               totalFullHours = totalFullHours + fullHours;

               circleStartCalendar.add( Calendar.DATE, 1 );
               circleStartDays++;
            }

            String needAudit = "1";
            if ( this.ClientOrderHeaderVO.getAttendanceCheckType() != null && this.ClientOrderHeaderVO.getAttendanceCheckType().trim().equals( "1" ) )
            {
               needAudit = "2";
            }

            // 装载TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
            timesheetHeaderVO.setAccountId( this.employeeContractVO.getAccountId() );
            timesheetHeaderVO.setEmployeeId( this.employeeContractVO.getEmployeeId() );
            timesheetHeaderVO.setEmployeeNameZH( this.employeeContractVO.getEmployeeNameZH() );
            timesheetHeaderVO.setEmployeeNameEN( this.employeeContractVO.getEmployeeNameEN() );
            timesheetHeaderVO.setContractId( this.employeeContractVO.getContractId() );
            timesheetHeaderVO.setClientId( this.employeeContractVO.getClientId() );
            timesheetHeaderVO.setCorpId( this.employeeContractVO.getCorpId() );
            timesheetHeaderVO.setOrderId( this.employeeContractVO.getOrderId() );
            timesheetHeaderVO.setMonthly( this.employeeContractVO.getMonthly() );
            timesheetHeaderVO.setWeekly( this.employeeContractVO.getWeekly() );
            timesheetHeaderVO.setStartDate( startDateString );
            timesheetHeaderVO.setEndDate( endDateString );
            timesheetHeaderVO.setTotalWorkDays( String.valueOf( totalWorkDays ) );
            timesheetHeaderVO.setTotalWorkHours( String.valueOf( totalWorkHours ) );
            timesheetHeaderVO.setTotalFullDays( String.valueOf( totalFullDays ) );
            timesheetHeaderVO.setTotalFullHours( String.valueOf( totalFullHours ) );
            timesheetHeaderVO.setNeedAudit( needAudit );
            timesheetHeaderVO.setIsNormal( totalFullHours == totalWorkHours ? "1" : "2" );
            timesheetHeaderVO.setStatus( TimesheetHeaderVO.TRUE );
            this.setTimesheetHeaderVO( timesheetHeaderVO );

            // 装载TimesheetAllowanceVO List
            if ( this.getEmployeeContractSalaryVOs() != null && this.getEmployeeContractSalaryVOs().size() > 0 )
            {
               for ( EmployeeContractSalaryVO employeeContractSalaryVO : this.getEmployeeContractSalaryVOs() )
               {
                  if ( employeeContractSalaryVO.getShowToTS() != null && employeeContractSalaryVO.getShowToTS().trim().equals( "1" ) )
                  {
                     final TimesheetAllowanceVO timesheetAllowanceVO = new TimesheetAllowanceVO();
                     timesheetAllowanceVO.setItemId( employeeContractSalaryVO.getItemId() );
                     timesheetAllowanceVO.setBase( employeeContractSalaryVO.getBase() );
                     timesheetAllowanceVO.setBaseFrom( employeeContractSalaryVO.getBaseFrom() );
                     timesheetAllowanceVO.setPercentage( employeeContractSalaryVO.getPercentage() );
                     timesheetAllowanceVO.setFix( employeeContractSalaryVO.getFix() );
                     timesheetAllowanceVO.setQuantity( employeeContractSalaryVO.getQuantity() );
                     timesheetAllowanceVO.setDiscount( employeeContractSalaryVO.getDiscount() );
                     timesheetAllowanceVO.setMultiple( employeeContractSalaryVO.getMultiple() );
                     timesheetAllowanceVO.setResultCap( employeeContractSalaryVO.getResultCap() );
                     timesheetAllowanceVO.setResultFloor( employeeContractSalaryVO.getResultFloor() );
                     timesheetAllowanceVO.setFormularType( employeeContractSalaryVO.getFormularType() );
                     timesheetAllowanceVO.setFormular( employeeContractSalaryVO.getFormular() );
                     timesheetAllowanceVO.setStatus( TimesheetAllowanceVO.TRUE );
                     this.getTimesheetAllowanceVOs().add( timesheetAllowanceVO );
                  }
               }
            }
         }
      }

      return true;
   }

   private ShiftDTO generateShiftDTO( final String accountId, final String shiftId, final Calendar startCalendar, final Calendar endCalendar ) throws KANException
   {
      // 初始化ShiftDTO
      final ShiftDTO shiftDTO = new ShiftDTO();

      if ( KANUtil.filterEmpty( shiftId, "0" ) != null && startCalendar != null && endCalendar != null )
      {
         // 初始化ShiftDetailVO List
         final List< ShiftDetailVO > shiftDetailVOs = new ArrayList< ShiftDetailVO >();
         // 初始化ShiftDTO
         final ShiftDTO systemShiftDTO = KANConstants.getKANAccountConstants( accountId ).getShiftDTOByHeaderId( shiftId );

         if ( systemShiftDTO == null || systemShiftDTO.getShiftHeaderVO() == null )
         {
            return systemShiftDTO;
         }

         shiftDTO.setShiftHeaderVO( systemShiftDTO.getShiftHeaderVO() );

         // 初始化ShiftType, ShiftIndex, StartDate
         final String shiftType = systemShiftDTO.getShiftHeaderVO().getShiftType();
         final String shiftIndex = systemShiftDTO.getShiftHeaderVO().getShiftIndex();
         final String startDateString = systemShiftDTO.getShiftHeaderVO().getStartDate();

         if ( shiftType != null )
         {
            // 按周排班
            if ( shiftType.trim().equals( "1" ) )
            {
               // 初始化Shift Start Calendar
               final Calendar shiftStartCalendar = KANUtil.getCalendar( KANUtil.createDate( startDateString ) );
               // 初始化Temp Calendar
               final Calendar tempCalendar = KANUtil.getCalendar( KANUtil.createDate( startDateString ) );
               tempCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) * 7 );

               // 遍历至最靠近目标时间最近的Calendar（按照ShiftIndex）
               while ( KANUtil.getDays( tempCalendar ) <= KANUtil.getDays( startCalendar ) )
               {
                  // 按照ShiftIndex逐渐时间推移
                  shiftStartCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) * 7 );
                  tempCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) * 7 );
               }

               // 获取起始日期星期几
               int index = shiftStartCalendar.get( Calendar.DAY_OF_WEEK );
               // 按天遍历至结束日期
               while ( KANUtil.getDays( shiftStartCalendar ) <= KANUtil.getDays( endCalendar ) )
               {
                  if ( KANUtil.getDays( shiftStartCalendar ) >= KANUtil.getDays( startCalendar ) )
                  {
                     // 获得当天的排班
                     final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                     shiftDetailVO.update( systemShiftDTO.getShiftDetailVOByDayIndex( String.valueOf( index ) ) );
                     shiftDetailVO.setShiftDay( KANUtil.formatDate( shiftStartCalendar.getTime(), "yyyy-MM-dd" ) );

                     shiftDetailVOs.add( shiftDetailVO );
                  }

                  // 日期按天推进
                  shiftStartCalendar.add( Calendar.DATE, 1 );
                  // Index累加
                  index++;
                  // 超出周期，重置
                  if ( index > Integer.valueOf( shiftIndex ) * 7 )
                  {
                     index = 1;
                  }
               }
            }
            // 按天排班
            else if ( shiftType.trim().equals( "2" ) )
            {
               // 初始化Shift Start Calendar
               final Calendar shiftStartCalendar = KANUtil.getCalendar( KANUtil.createDate( startDateString ) );
               // 初始化Temp Calendar
               final Calendar tempCalendar = KANUtil.getCalendar( KANUtil.createDate( startDateString ) );
               tempCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) );

               // 遍历至最靠近目标时间最近的Calendar（按照ShiftIndex）
               while ( KANUtil.getDays( tempCalendar ) <= KANUtil.getDays( startCalendar ) )
               {
                  // 按照ShiftIndex逐渐时间推移
                  tempCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) );
                  shiftStartCalendar.add( Calendar.DATE, Integer.valueOf( shiftIndex ) );
               }

               int index = 1;
               // 按天遍历至结束日期
               while ( KANUtil.getDays( shiftStartCalendar ) <= KANUtil.getDays( endCalendar ) )
               {
                  if ( KANUtil.getDays( shiftStartCalendar ) >= KANUtil.getDays( startCalendar ) )
                  {
                     // 获得当天的排班
                     final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();
                     shiftDetailVO.update( systemShiftDTO.getShiftDetailVOByDayIndex( String.valueOf( index ) ) );
                     shiftDetailVO.setShiftDay( KANUtil.formatDate( shiftStartCalendar.getTime(), "yyyy-MM-dd" ) );

                     shiftDetailVOs.add( shiftDetailVO );
                  }

                  // 日期按天推进
                  shiftStartCalendar.add( Calendar.DATE, 1 );
                  // Index累加
                  index++;
                  // 超出周期，重置
                  if ( index > Integer.valueOf( shiftIndex ) )
                  {
                     index = 1;
                  }
               }
            }
            // 自定义排班
            else if ( shiftType.trim().equals( "3" ) )
            {
               // 初始化Temp Calendar
               final Calendar tempCalendar = KANUtil.getCalendar( KANUtil.createDate( startDateString ) );

               // 按天遍历至结束日期
               while ( KANUtil.getDays( startCalendar ) <= KANUtil.getDays( endCalendar ) )
               {
                  // 获得当天的排班
                  final ShiftDetailVO shiftDetailVO = systemShiftDTO.getShiftDetailVOByShiftDay( KANUtil.formatDate( tempCalendar.getTime(), "yyyy-MM-dd" ) );
                  shiftDetailVOs.add( shiftDetailVO );

                  // 日期按天推进
                  tempCalendar.add( Calendar.DATE, 1 );
               }
            }
         }

         shiftDTO.setShiftDetailVOs( shiftDetailVOs );
         shiftDTO.setShiftExceptionVOs( systemShiftDTO.getShiftExceptionVOs() );
      }

      return shiftDTO;
   }

   // 按照给定日期获得考勤表明细
   public TimesheetDetailVO getTimesheetDetailVOByDay( final String day ) throws KANException
   {
      if ( day != null && this.timesheetDetailVOs != null && this.timesheetDetailVOs.size() > 0 )
      {
         for ( TimesheetDetailVO timesheetDetailVO : this.timesheetDetailVOs )
         {
            final Calendar targetCalendar = KANUtil.createCalendar( day );
            final Calendar tsCalendar = KANUtil.createCalendar( timesheetDetailVO.getDay() );

            // 如果日期相同则返回
            if ( KANUtil.getDays( tsCalendar ) == KANUtil.getDays( targetCalendar ) )
            {
               return timesheetDetailVO;
            }
         }
      }

      return null;
   }

   // 按照给定ItemId获得考勤表津贴
   public TimesheetAllowanceVO getTimesheetAllowanceVOByItemId( final String itemId )
   {
      if ( this.getTimesheetAllowanceVOs() != null && this.getTimesheetAllowanceVOs().size() > 0 )
      {
         for ( TimesheetAllowanceVO timesheetAllowanceVO : this.getTimesheetAllowanceVOs() )
         {
            if ( timesheetAllowanceVO.getItemId() != null && timesheetAllowanceVO.getItemId().trim().equals( itemId ) )
            {
               return timesheetAllowanceVO;
            }
         }
      }

      return null;
   }

   public double getLeaveHours( final String accountId, final String calendarId, final String shiftId, final List< LeaveDetailVO > leaveDetailVOs ) throws KANException
   {
      double leaveHours = 0;

      if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
      {
         for ( LeaveDetailVO leaveDetailVO : leaveDetailVOs )
         {
            // 初始化StartDate和EndDate
            String startDate = leaveDetailVO.getEstimateStartDate();
            String endDate = leaveDetailVO.getEstimateEndDate();

            // 如果是销假
            if ( leaveDetailVO.getRetrieveStatus() != null && leaveDetailVO.getRetrieveStatus().trim().equals( "3" ) )
            {
               startDate = leaveDetailVO.getActualStartDate();
               endDate = leaveDetailVO.getActualEndDate();
            }

            leaveHours = leaveHours + getLeaveHours( null, accountId, calendarId, shiftId, startDate, endDate );
         }
      }

      return leaveHours;
   }

   // Updated by Siuxia at 2013-12-11
   // 加入参数itemId（如果是产假、婚假、丧假，则不考虑周末和法定节假日）
   public double getLeaveHours( final String itemId, final String accountId, final String calendarId, final String shiftId, final Calendar startCalendar, final Calendar endCalendar )
         throws KANException
   {
      double leaveHours = 0;

      if ( KANUtil.filterEmpty( accountId ) != null && KANUtil.filterEmpty( calendarId ) != null && KANUtil.filterEmpty( shiftId ) != null && startCalendar != null
            && endCalendar != null )
      {
         final long startDays = KANUtil.getDays( startCalendar );
         long startTempDays = startDays;
         final long endDays = KANUtil.getDays( endCalendar );

         final Calendar tempStartCalendar = KANUtil.createCalendar( KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd HH:mm:ss" ) );

         while ( startTempDays <= endDays )
         {
            // 初始化ShiftDetailVO
            ShiftDetailVO shiftDetailVO = null;
            if ( KANUtil.filterEmpty( itemId ) != null && ArrayUtils.contains( IGNORE_WEEKEND_AND_HOLIDAY_ITEMID_ARRAY, itemId ) )
            {
               shiftDetailVO = getShiftDetailVO_ignoreWeekendAndHoliday( accountId, shiftId );
            }
            else
            {
               shiftDetailVO = getShiftDetailVO( accountId, calendarId, shiftId, tempStartCalendar, EXCEPTION_TYPE_LEAVE );
            }

            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
            {
               int startPeriod = tempStartCalendar.get( Calendar.HOUR_OF_DAY ) * 2 + ( tempStartCalendar.get( Calendar.MINUTE ) > 0 ? 1 : 0 ) + 1;
               final int endPeriod = endCalendar.get( Calendar.HOUR_OF_DAY ) * 2 + ( endCalendar.get( Calendar.MINUTE ) > 0 ? 1 : 0 );
               final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );

               if ( startTempDays != startDays && shiftPeriods.length > 0 )
               {
                  startPeriod = Integer.valueOf( shiftPeriods[ 0 ] );
               }

               // 如果假期不跨天
               if ( startDays == endDays )
               {
                  if ( shiftPeriods != null && shiftPeriods.length > 0 )
                  {
                     for ( String shiftPeriod : shiftPeriods )
                     {
                        if ( Integer.valueOf( shiftPeriod ) >= startPeriod && Integer.valueOf( shiftPeriod ) <= endPeriod )
                        {
                           leaveHours = leaveHours + 0.5;
                        }
                     }
                  }
               }
               // 如果假期跨天
               else
               {
                  if ( shiftPeriods != null && shiftPeriods.length > 0 )
                  {
                     for ( String shiftPeriod : shiftPeriods )
                     {
                        // 如果最后一天
                        if ( startTempDays == endDays )
                        {
                           if ( Integer.valueOf( shiftPeriod ) <= endPeriod )
                           {
                              leaveHours = leaveHours + 0.5;
                           }
                        }
                        else
                        {
                           if ( Integer.valueOf( shiftPeriod ) >= startPeriod )
                           {
                              leaveHours = leaveHours + 0.5;
                           }
                        }
                     }
                  }
               }
            }

            tempStartCalendar.add( Calendar.DATE, 1 );
            startTempDays = KANUtil.getDays( tempStartCalendar );
         }
      }

      return leaveHours;
   }

   public double getLeaveHours( final String itemId, final String accountId, final String calendarId, final String shiftId, final String startDateString, final String endDateString )
         throws KANException
   {
      // 初始化StartCalendar和EndCalendar
      final Calendar startCalendar = KANUtil.createCalendar( startDateString );
      final Calendar endCalendar = KANUtil.createCalendar( endDateString );

      return getLeaveHours( itemId, accountId, calendarId, shiftId, startCalendar, endCalendar );
   }

   // 返回例如“2013-10-31 15:30”
   // Updated by Siuxia at 2013-12-11
   public String getEndLeaveDatetime( final String itemId, final String accountId, final String calendarId, final String shiftId, final Calendar startCalendar,
         final double leftHours ) throws KANException
   {
      String endLeaveDatetime = "";
      double leaveHours = 0;

      if ( KANUtil.filterEmpty( accountId ) != null && KANUtil.filterEmpty( calendarId ) != null && KANUtil.filterEmpty( shiftId ) != null && startCalendar != null
            && leftHours > 0 )
      {
         final Calendar tempStartCalendar = KANUtil.createCalendar( KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd HH:mm:ss" ) );

         int index = 1;
         while ( leaveHours < leftHours )
         {
            // 初始化ShiftDetailVO
            ShiftDetailVO shiftDetailVO = null;
            if ( KANUtil.filterEmpty( itemId ) != null && ArrayUtils.contains( IGNORE_WEEKEND_AND_HOLIDAY_ITEMID_ARRAY, itemId ) )
            {
               shiftDetailVO = getShiftDetailVO_ignoreWeekendAndHoliday( accountId, shiftId );
            }
            else
            {
               shiftDetailVO = getShiftDetailVO( accountId, calendarId, shiftId, tempStartCalendar, EXCEPTION_TYPE_LEAVE );
            }

            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
            {
               final String[] shiftPeriods = KANUtil.jasonArrayToStringArray( shiftDetailVO.getShiftPeriod() );
               // 第一次循环period取开始时间
               int startPeriod = tempStartCalendar.get( Calendar.HOUR_OF_DAY ) * 2 + ( tempStartCalendar.get( Calendar.MINUTE ) > 0 ? 1 : 0 ) + 1;
               // 其他取ShiftDetailVO中ShiftPeriod的第一个
               if ( index != 1 && shiftPeriods.length > 0 )
               {
                  startPeriod = Integer.valueOf( shiftPeriods[ 0 ] );
               }

               if ( shiftPeriods != null && shiftPeriods.length > 0 )
               {
                  for ( String shiftPeriod : shiftPeriods )
                  {
                     if ( Integer.valueOf( shiftPeriod ) >= startPeriod )
                     {
                        leaveHours = leaveHours + 0.5;
                     }

                     if ( leaveHours >= leftHours )
                     {
                        endLeaveDatetime = KANUtil.formatDate( tempStartCalendar.getTime(), "yyyy-MM-dd" ) + " " + Integer.valueOf( shiftPeriod ) / 2 + ":"
                              + Integer.valueOf( shiftPeriod ) % 2 * 30;
                        break;
                     }
                  }
               }
            }

            if ( index == 365 )
            {
               break;
            }

            index++;
            tempStartCalendar.add( Calendar.DATE, 1 );
         }
      }

      return endLeaveDatetime;
   }

   public String getEndLeaveDatetime( final String itemId, final String accountId, final String calendarId, final String shiftId, final String startDateString,
         final double leftHours ) throws KANException
   {
      // 初始化StartCalendar
      final Calendar startCalendar = KANUtil.createCalendar( startDateString );

      return getEndLeaveDatetime( itemId, accountId, calendarId, shiftId, startCalendar, leftHours );
   }

   public double getOTHours( final String accountId, final String calendarId, final String shiftId, final Calendar startCalendar, final Calendar endCalendar,
         final double limitHoursByDay ) throws KANException
   {
      double otHours = 0;

      if ( KANUtil.filterEmpty( accountId ) != null && KANUtil.filterEmpty( calendarId ) != null && KANUtil.filterEmpty( shiftId ) != null && startCalendar != null
            && endCalendar != null )
      {
         final long startDays = KANUtil.getDays( startCalendar );
         long startTempDays = startDays;
         final long endDays = KANUtil.getDays( endCalendar );

         final Calendar tempStartCalendar = KANUtil.createCalendar( KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd HH:mm:ss" ) );

         logger.info( "加班计算开始........." );

         while ( startTempDays <= endDays )
         {
            double tempHours = 0;

            // 初始化ShiftDetailVO
            final ShiftDetailVO shiftDetailVO = getShiftDetailVO( accountId, calendarId, shiftId, tempStartCalendar, EXCEPTION_TYPE_OT );
            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null )
            {
               logger.info( "日期：" + shiftDetailVO.getShiftDay() );
               logger.info( "日期类型：" + shiftDetailVO.getDayType() );
               logger.info( "工作点：" + shiftDetailVO.getShiftPeriod() );
               // 初始化StartPeriod和EndPeriod
               int startPeriod = tempStartCalendar.get( Calendar.HOUR_OF_DAY ) * 2 + ( tempStartCalendar.get( Calendar.MINUTE ) > 0 ? 1 : 0 ) + 1;
               int endPeriod = endCalendar.get( Calendar.HOUR_OF_DAY ) * 2 + ( endCalendar.get( Calendar.MINUTE ) > 0 ? 1 : 0 );

               // 初始化ShiftPeriodList
               final List< String > shiftPeriodList = KANUtil.jasonArrayToStringList( shiftDetailVO.getShiftPeriod() );

               // 如果这天不是工作日
               if ( shiftDetailVO.getDayType().equals( "2" ) || shiftDetailVO.getDayType().equals( "3" ) )
               {
                  // 如果加班不跨天
                  if ( startDays == endDays )
                  {
                     for ( int i = startPeriod; i <= endPeriod; i++ )
                     {
                        if ( !shiftPeriodList.contains( String.valueOf( i ) ) )
                        {
                           tempHours = tempHours + 0.5;
                        }
                     }
                  }
                  else
                  {
                     // 如果第一天
                     startPeriod = startTempDays == startDays ? startPeriod : 1;
                     // 如果最后一天
                     endPeriod = startTempDays == endDays ? endPeriod : 48;

                     logger.info( "加班跨天，从" + startPeriod + " ~ " + endPeriod );

                     for ( int i = startPeriod; i <= endPeriod; i++ )
                     {
                        if ( !shiftPeriodList.contains( String.valueOf( i ) ) )
                        {
                           tempHours = tempHours + 0.5;
                        }
                     }
                  }

                  logger.info( "这天为休假时间，加班小时数为" + tempHours + "小时" );
               }
               else
               {
                  if ( shiftPeriodList != null && shiftPeriodList.size() > 0 )
                  {
                     // 如果加班不跨天
                     if ( startDays == endDays )
                     {
                        logger.info( "加班不跨天，从" + startPeriod + " ~ " + endPeriod );

                        for ( int i = startPeriod; i <= endPeriod; i++ )
                        {
                           if ( !shiftPeriodList.contains( String.valueOf( i ) ) )
                           {
                              tempHours = tempHours + 0.5;
                           }
                        }

                        logger.info( "这天有效加班小时数：" + tempHours + "小时" );
                     }
                     else
                     {
                        // 如果第一天
                        startPeriod = startTempDays == startDays ? startPeriod : 1;
                        // 如果最后一天
                        endPeriod = startTempDays == endDays ? endPeriod : 48;

                        logger.info( "加班跨天，从" + startPeriod + " ~ " + endPeriod );

                        for ( int i = startPeriod; i <= endPeriod; i++ )
                        {
                           if ( !shiftPeriodList.contains( String.valueOf( i ) ) )
                           {
                              tempHours = tempHours + 0.5;
                           }
                        }

                        logger.info( "这天有效加班小时数：" + tempHours + "小时" );
                     }
                  }
               }

               // 加班小时超过上限
               if ( limitHoursByDay > 0 && tempHours > limitHoursByDay )
               {
                  logger.info( "加班超过每天设置的限制" + limitHoursByDay );
                  tempHours = limitHoursByDay;
                  logger.info( "加班小时数改变" + limitHoursByDay );
               }

               otHours = otHours + tempHours;
            }

            tempStartCalendar.add( Calendar.DATE, 1 );
            startTempDays = KANUtil.getDays( tempStartCalendar );
         }
      }

      logger.info( "加班计算结束........." );
      logger.info( "计算结果：" + otHours + "小时" );

      // 看本地化加班最小单位
      if ( otHours > 0 && "2".equals( KANUtil.filterEmpty( KANConstants.getKANAccountConstants( accountId ).OPTIONS_OT_MIN_UNIT ) ) )
      {
         return Math.floor( otHours );
      }

      return otHours;
   }

   public double getOTHours( final String accountId, final String calendarId, final String shiftId, final String startDateString, final String endDateString,
         final double limitHoursByDay ) throws KANException
   {
      // 初始化StartCalendar和EndCalendar
      final Calendar startCalendar = KANUtil.createCalendar( KANUtil.formatDateForMinute( startDateString ) );
      final Calendar endCalendar = KANUtil.createCalendar( KANUtil.formatDateForMinute( endDateString ) );

      return getOTHours( accountId, calendarId, shiftId, startCalendar, endCalendar, limitHoursByDay );
   }

   public double getWorkHours( final String accountId, final String calendarId, final String shiftId, final Calendar startCalendar, final Calendar endCalendar )
         throws KANException
   {
      double workHours = 0;

      if ( KANUtil.filterEmpty( accountId ) != null && KANUtil.filterEmpty( calendarId ) != null && KANUtil.filterEmpty( shiftId ) != null && startCalendar != null
            && endCalendar != null )
      {
         final long startDays = KANUtil.getDays( startCalendar );
         long startTempDays = startDays;
         final long endDays = KANUtil.getDays( endCalendar );

         final Calendar tempStartCalendar = KANUtil.createCalendar( KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd HH:mm:ss" ) );

         while ( startTempDays <= endDays )
         {
            // 初始化ShiftDetailVO
            final ShiftDetailVO shiftDetailVO = getShiftDetailVO( accountId, calendarId, shiftId, tempStartCalendar, "0" );

            if ( shiftDetailVO != null && shiftDetailVO.getDayType() != null && shiftDetailVO.getDayType().trim().equals( "1" ) )
            {
               workHours = workHours + shiftDetailVO.getFullHours();
            }

            tempStartCalendar.add( Calendar.DATE, 1 );
            startTempDays = KANUtil.getDays( tempStartCalendar );
         }
      }

      return workHours;
   }

   public double getWorkHours( final String accountId, final String calendarId, final String shiftId, final String startDateString, final String endDateString )
         throws KANException
   {
      // 初始化StartCalendar和EndCalendar
      final Calendar startCalendar = KANUtil.createCalendar( startDateString );
      final Calendar endCalendar = KANUtil.createCalendar( endDateString );

      return getWorkHours( accountId, calendarId, shiftId, startCalendar, endCalendar );
   }

   // 不考虑双休和节假日
   public ShiftDetailVO getShiftDetailVO_ignoreWeekendAndHoliday( final String accountId, final String shiftId ) throws KANException
   {
      // 初始化ShiftDTO
      final ShiftDTO shiftDTO = KANConstants.getKANAccountConstants( accountId ).getShiftDTOByHeaderId( shiftId );

      if ( shiftDTO != null && shiftDTO.getShiftDetailVOs() != null && shiftDTO.getShiftDetailVOs().size() > 0 )
      {
         for ( ShiftDetailVO shiftDetailVO : shiftDTO.getShiftDetailVOs() )
         {
            if ( KANUtil.filterEmpty( shiftDetailVO.getShiftPeriod() ) != null )
            {
               shiftDetailVO.setDayType( "1" );
               return shiftDetailVO;
            }
         }
      }

      return null;
   }

   public ShiftDetailVO getShiftDetailVO( final String accountId, final String calendarId, final String shiftId, final Calendar calendar, final String exceptionType )
         throws KANException
   {
      // 初始化CalendarDTO
      final CalendarDTO calendarDTO = KANConstants.getKANAccountConstants( accountId ).getCalendarDTOByHeaderId( calendarId );

      // 初始化DayType
      String dayType = null;

      // 初始化ShiftDateString
      String shiftDateString = KANUtil.formatDate( calendar.getTime(), "yyyy-MM-dd" );

      // 初始化CalendarDetailVO
      CalendarDetailVO calendarDetailVO = null;

      if ( calendarDTO != null )
      {
         calendarDetailVO = calendarDTO.getCalendarDetailVOByDay( KANUtil.formatDate( calendar.getTime(), "yyyy-MM-dd" ) );
      }

      if ( calendarDetailVO != null )
      {
         dayType = calendarDetailVO.getDayType();

         if ( dayType != null && dayType.trim().equals( "1" ) )
         {
            shiftDateString = calendarDetailVO.getChangeDay();
         }
      }

      // 初始化ShiftDTO
      final ShiftDTO shiftDTO = generateShiftDTO( accountId, shiftId, KANUtil.createCalendar( shiftDateString ), KANUtil.createCalendar( shiftDateString ) );

      // 初始化ShiftDetailVO
      ShiftDetailVO shiftDetailVO = null;

      if ( shiftDTO != null )
      {
         shiftDetailVO = shiftDTO.getShiftDetailVOByShiftDay( shiftDateString );
      }

      if ( dayType == null )
      {
         if ( shiftDetailVO != null && shiftDetailVO.getFullHours() > 0 )
         {
            dayType = "1";
         }
         else
         {
            dayType = "2";
         }
      }

      // 设置当前DayType
      if ( shiftDetailVO != null )
      {
         shiftDetailVO.setDayType( dayType );

         if ( dayType.equals( "2" ) || dayType.equals( "3" ) )
         {
            shiftDetailVO.setShiftPeriod( null );
         }
      }

      // 装载排班例外
      if ( shiftDTO != null && shiftDTO.getShiftExceptionVOs() != null && shiftDTO.getShiftExceptionVOs().size() > 0 && shiftDetailVO != null )
      {
         for ( Object shiftExceptionVOObject : shiftDTO.getShiftExceptionVOs() )
         {
            final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) shiftExceptionVOObject;

            if ( shiftExceptionVO.getExceptionType().equals( exceptionType ) && shiftExceptionVO.getDayType().equals( dayType )
                  && KANUtil.filterEmpty( shiftExceptionVO.getItemId(), "0" ) == null && KANUtil.filterEmpty( shiftExceptionVO.getExceptionPeriod() ) != null )
            {
               final String mergeShiftPeried = ( KANUtil.filterEmpty( shiftDetailVO.getShiftPeriod() ) != null ? shiftDetailVO.getShiftPeriod().replace( "{", "" ).replace( "}", "" )
                     + ","
                     : "" )
                     + shiftExceptionVO.getExceptionPeriod().replace( "{", "" ).replace( "}", "" );
               shiftDetailVO.setShiftPeriod( KANUtil.afterStringArraySort( mergeShiftPeried.split( "," ) ) );
            }
         }
      }

      return shiftDetailVO;
   }

   public ShiftDetailVO getShiftDetailVO( final String accountId, final String calendarId, final String shiftId, final String date, final String excepitonType )
         throws KANException
   {
      // 初始化Calendar
      final Calendar calendar = KANUtil.createCalendar( date );

      return getShiftDetailVO( accountId, calendarId, shiftId, calendar, excepitonType );
   }

   /***
    * 
   * Get AvailableOTHours(获得合法加班小时数 )  
   * 
   * @param   otDetailVOs(加班记录按月统计)  
   * @param   currMonthly(当前月份) 
   * @param   reusltOTHours(当前总加班小时数)  
   * @param   currDayOTHours(当前天加班小时数)  
   * @param   extraOTHours(修改时使用)  
   * @param   otLimitByMonth(修改时使用)  
   * @param   otLimitByDay(修改时使用) 
   * @param   circleEndDay(计薪结束日)   
   * @param  @return 合法加班小时数 
   * @return Double    合法加班小时数 
    */
   // 是否合法加班小时数 Add by siuxia 2014-01-15
   // Reviewed by Kevin Jin & Siuvan Xia at 2014-04-21
   public double getAvailableOTHours( final List< Object > otDetailVOs, final String currMonthly, final double reusltOTHours, final double currDayOTHours,
         final double extraOTHours, final String otLimitByMonth, final String circleEndDay )
   {
      // 初始化AvaiableOTHours(有效小时)
      double avaiableOTHours = 0;

      // 初始化otHoursByMonth(每月记录/小时)
      double otHoursByMonth = 0;

      // 加上返回记录
      if ( reusltOTHours > 0 )
      {
         otHoursByMonth = otHoursByMonth + reusltOTHours;
      }

      // 存在加班记录
      if ( otDetailVOs != null && otDetailVOs.size() > 0 && KANUtil.filterEmpty( otLimitByMonth ) != null )
      {
         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;
            final String monthly = KANUtil.getMonthlyByCondition( circleEndDay, otDetailVO.getEstimateEndDate() );

            // 迭代比较月份
            if ( KANUtil.filterEmpty( monthly ) != null && KANUtil.filterEmpty( monthly ).equalsIgnoreCase( currMonthly ) )
            {
               if ( KANUtil.filterEmpty( otDetailVO.getActualHours() ) != null )
               {
                  otHoursByMonth = otHoursByMonth + Double.valueOf( KANUtil.filterEmpty( otDetailVO.getActualHours() ) );
               }
               else if ( KANUtil.filterEmpty( otDetailVO.getEstimateHours() ) != null )
               {
                  otHoursByMonth = otHoursByMonth + Double.valueOf( KANUtil.filterEmpty( otDetailVO.getEstimateHours() ) );
               }
            }
         }
      }

      // 如果加班是小数需要修改
      if ( extraOTHours > 0 )
      {
         // 回填已申请加班小时
         otHoursByMonth = otHoursByMonth - extraOTHours;
      }

      // 超过月上限情况处理
      if ( Double.valueOf( otLimitByMonth ) > 0 && ( otHoursByMonth + currDayOTHours ) > Double.valueOf( otLimitByMonth ) )
      {
         avaiableOTHours = Double.valueOf( otLimitByMonth ) - otHoursByMonth;
      }
      else
      {
         avaiableOTHours = currDayOTHours;
      }

      // 负数处理
      if ( avaiableOTHours < 0 )
      {
         avaiableOTHours = 0;
      }

      return avaiableOTHours;
   }

   // Reviewed by Kevin Jin & Siuvan Xia at 2014-04-21
   public double getAvailableOTHours( final List< Object > otDetailVOs, final String currMonthly, final double currDayOTHours, final String otLimitByMonth,
         final String circleEndDay )
   {
      return getAvailableOTHours( otDetailVOs, currMonthly, 0.0d, currDayOTHours, 0.0d, otLimitByMonth, circleEndDay );
   }

   public double getNonWorkingDays( final Calendar startCalendar, final Calendar endCalendar )
   {
      double nonWorkingDays = 0;

      if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
      {
         for ( TimesheetDetailVO timesheetDetailVO : timesheetDetailVOs )
         {
            if ( KANUtil.filterEmpty( timesheetDetailVO.getDayType() ) != null && !timesheetDetailVO.getDayType().equals( "1" )
                  && KANUtil.getDays( KANUtil.createCalendar( timesheetDetailVO.getDay() ) ) >= KANUtil.getDays( startCalendar )
                  && KANUtil.getDays( KANUtil.createCalendar( timesheetDetailVO.getDay() ) ) <= KANUtil.getDays( endCalendar ) )
            {
               nonWorkingDays = nonWorkingDays + 1;
            }
         }
      }

      return nonWorkingDays;
   }

}
