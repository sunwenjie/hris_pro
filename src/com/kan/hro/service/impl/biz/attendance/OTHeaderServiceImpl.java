package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetDetailDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderOTDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.domain.biz.attendance.OTDTO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.OTHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.OTHeaderAction;

public class OTHeaderServiceImpl extends ContextService implements OTHeaderService
{
   private String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.OTHeaderVO";

   private String SERVICE_BEAN = "otHeaderService";

   // 注入加班从表 dao
   private OTDetailDao otDetailDao;

   // 注入 雇员 - 服务协议DAO
   private EmployeeContractDao employeeContractDao;

   // 注入雇员 - 加班方案DAO
   private EmployeeContractOTDao employeeContractOTDao;

   // 注入 客户 - 服务订单Service
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入客户 - 加班方案DAO
   private ClientOrderOTDao clientOrderOTDao;

   // 工作流 workflowService
   private WorkflowService workflowService;

   // 注入考勤表Service
   private TimesheetHeaderService timesheetHeaderService;

   // 注入考勤表Detail Dao
   private TimesheetDetailDao timesheetDetailDao;

   public OTDetailDao getOtDetailDao()
   {
      return otDetailDao;
   }

   public void setOtDetailDao( OTDetailDao otDetailDao )
   {
      this.otDetailDao = otDetailDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractOTDao getEmployeeContractOTDao()
   {
      return employeeContractOTDao;
   }

   public void setEmployeeContractOTDao( EmployeeContractOTDao employeeContractOTDao )
   {
      this.employeeContractOTDao = employeeContractOTDao;
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

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public TimesheetHeaderService getTimesheetHeaderService()
   {
      return timesheetHeaderService;
   }

   public void setTimesheetHeaderService( TimesheetHeaderService timesheetHeaderService )
   {
      this.timesheetHeaderService = timesheetHeaderService;
   }

   public TimesheetDetailDao getTimesheetDetailDao()
   {
      return timesheetDetailDao;
   }

   public void setTimesheetDetailDao( TimesheetDetailDao timesheetDetailDao )
   {
      this.timesheetDetailDao = timesheetDetailDao;
   }

   @Override
   public PagedListHolder getOTHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final OTHeaderDao oTHeaderDao = ( OTHeaderDao ) getDao();
      pagedListHolder.setHolderSize( oTHeaderDao.countOTHeaderVOsByCondition( ( OTHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( oTHeaderDao.getOTHeaderVOsByCondition( ( OTHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( oTHeaderDao.getOTHeaderVOsByCondition( ( OTHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public OTHeaderVO getOTHeaderVOByOTHeaderId( final String otHeaderId ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderId );
   }

   @Override
   // Reviewed by Kevin Jin & Siuvan Xia at 2014-04-21
   public int insertOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 开启事务
         startTransaction();

         // 获得加班开始、结束时间                       vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    v
         if ( KANUtil.filterEmpty( otHeaderVO.getEstimateStartDate() ) == null || KANUtil.filterEmpty( otHeaderVO.getEstimateEndDate() ) == null )
         {
            return rows;
         }

         // 状态初始化默认为“新建”
         otHeaderVO.setStatus( "1" );
         // 删除表示初始化默认为“未删除”
         otHeaderVO.setDeleted( "1" );
         // 初始化itemId
         otHeaderVO.setItemId( "0" );
         // 插入OTHeaderVO
         ( ( OTHeaderDao ) getDao() ).insertOTHeader( otHeaderVO );

         rows++;

         // 插入OTDetailVO（单个或列表）
         rows = rows + addOTDetails( otHeaderVO );

         // 处理加班小时详情（工作流页面用到）
         fetchOTHoursDetail( otHeaderVO );

         // 如果是提交，走工作流
         if ( KANUtil.filterEmpty( otHeaderVO.getSubAction() ) != null && KANUtil.filterEmpty( otHeaderVO.getSubAction() ).equalsIgnoreCase( BaseAction.SUBMIT_OBJECT ) )
         {
            submitOTHeader_nt( otHeaderVO );
         }

         // 提交事务
         this.commitTransaction();

         return rows;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   // 多重插入OTDetailVO
   private int addOTDetails( final OTHeaderVO otHeaderVO ) throws KANException
   {
      int rows = 0;

      // 获取EmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

      // 获取ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

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

      // 如果加班无需申请，设置加班实际时间
      if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst ).equals( "2" ) )
      {
         otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
         otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
         otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );
      }

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
               this.getOtDetailDao().insertOTDetail( otDetailVO );
            }

            startCalendar.add( Calendar.DATE, 1 );

            rows++;
         }
      }

      return rows;
   }

   @Override
   public int updateOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         int rows = updateOTHeader_nt( otHeaderVO );

         // 提交事务
         this.commitTransaction();

         return rows;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   // Update OTHeader
   // Reviewed by Kevin Jin & Siuvan Xia at 2014-04-21
   public int updateOTHeader_nt( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 修改OTHeaderVO
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         rows++;

         // 获取OTDetailVO列表
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

         String timesheetId = "";
         // 存在OTDetailVO列表，遍历物理删除
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetailVOObject : otDetailVOs )
            {
               timesheetId = ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId();
               otHeaderVO.setTimesheetId( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() );
               this.getOtDetailDao().deleteOTDetail( ( OTDetailVO ) otDetailVOObject );
            }
         }

         // 添加多条OTDetailVO
         rows = rows + addOTDetails( otHeaderVO );

         // 状态为确认的
         if ( otHeaderVO.getStatus().equals( "5" ) )
         {
            recalculateTimesheet( otHeaderVO.getOtHeaderId(), timesheetId );
         }

         return rows;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public int deleteOTHeader_nt( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {

         // 标记删除OTHeaderVO
         otHeaderVO.setDeleted( OTHeaderVO.FALSE );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         // 获取OTDetailVO列表
         final List< Object > otDetailVOs = this.otDetailDao.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

         // 遍历标记删除OTDetailVO
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetailVOObject : otDetailVOs )
            {
               ( ( OTDetailVO ) otDetailVOObject ).setDeleted( OTHeaderVO.FALSE );
               this.otDetailDao.updateOTDetail( ( OTDetailVO ) otDetailVOObject );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return -1;
   }

   @Override
   public int deleteOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      int rows = 0;
      try
      {
         // 开启事务
         startTransaction();

         rows = deleteOTHeader_nt( otHeaderVO );

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

   // Submit OTDetailVO List
   // Reviewed by Kevin Jin at 2014-04-21
   private int submitOTDetail( final String otHeaderId, final String status ) throws KANException
   {
      int rows = 0;

      if ( KANUtil.filterEmpty( otHeaderId ) == null || KANUtil.filterEmpty( status ) == null )
      {
         return rows;
      }

      try
      {
         // 获取OTDetailVO列表
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderId );

         String timesheetId = "";
         // 遍历OTDetailVO
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetalVOObject : otDetailVOs )
            {
               // 待审核、确认时修改ActualHours Modify by siuvan @2014-08-11
               if ( KANUtil.filterEmpty( status ) != null && ( status.equals( "4" ) || status.equals( "5" ) ) )
               {
                  ( ( OTDetailVO ) otDetalVOObject ).setActualStartDate( ( ( OTDetailVO ) otDetalVOObject ).getEstimateStartDate() );
                  ( ( OTDetailVO ) otDetalVOObject ).setActualEndDate( ( ( OTDetailVO ) otDetalVOObject ).getEstimateEndDate() );
                  ( ( OTDetailVO ) otDetalVOObject ).setActualHours( ( ( OTDetailVO ) otDetalVOObject ).getEstimateHours() );
               }
               timesheetId = ( ( OTDetailVO ) otDetalVOObject ).getTimesheetId();
               ( ( OTDetailVO ) otDetalVOObject ).setStatus( status );
               this.getOtDetailDao().updateOTDetail( ( OTDetailVO ) otDetalVOObject );

               rows++;
            }
         }

         // 状态为确认的
         if ( KANUtil.filterEmpty( status ).equals( "5" ) )
         {
            recalculateTimesheet( otHeaderId, timesheetId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   public int submitOTHeader( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         submitOTHeader_nt( otHeaderVO );

         // 提交事务
         this.commitTransaction();
         return -1;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         e.printStackTrace();
      }

      return 1;
   }

   public int submitOTHeader_nt( final OTHeaderVO otHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( otHeaderVO ) )
      {

         final OTHeaderVO originalObject = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );
         // 获取HistoryVO
         final HistoryVO historyVO = generateHistoryVO( otHeaderVO, employeeContractVO.getOwner() );

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( otHeaderVO );

         // 获取ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // 获取加班需要申请
         String applyOTFirst = employeeContractVO.getApplyOTFirst();
         if ( KANUtil.filterEmpty( applyOTFirst, "0" ) == null && clientOrderHeaderVO != null )
         {
            applyOTFirst = clientOrderHeaderVO.getApplyOTFirst();
         }

         // 是否重组子表数据
         boolean updated = false;
         // 拒绝再提交需重组子表数据
         boolean refuse = otHeaderVO.getStatus().equals( "6" );

         // 如果存在工作流
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( otHeaderVO.getStatus() ) != null
                  && ( KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "1" ) || KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "3" ) || KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "6" ) ) )
            {
               // 状态改为待审核（按照加班申请设置）
               if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst, "0" ).equals( "1" ) )
               {
                  otHeaderVO.setStatus( ( otHeaderVO.getStatus().equals( "1" ) || otHeaderVO.getStatus().equals( "6" ) ) ? "2" : "4" );
                  if ( otHeaderVO.getStatus().equals( "2" ) )
                  {
                     updated = originalObject.getEstimateStartDate().equals( otHeaderVO.getEstimateStartDate() )
                           && originalObject.getEstimateEndDate().equals( otHeaderVO.getEstimateEndDate() );
                  }
                  else if ( otHeaderVO.getStatus().equals( "4" ) )
                  {
                     updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() )
                           && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
                  }
               }
               else
               {
                  if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) == null || KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) == null )
                  {
                     otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
                     otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
                     otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
                  }
                  updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() ) && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
                  otHeaderVO.setStatus( "4" );
               }

               // 时间不变仅仅修改数据状态
               if ( updated && !refuse )
               {
                  // 修改OTHeaderVO
                  ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

                  // 提交OTDetailVO List
                  submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
               }
               // 和原有对象时间不同，则需重新添加detail数据
               else
               {
                  updateOTHeader_nt( otHeaderVO );
               }
            }

            // Service的方法
            historyVO.setServiceMethod( "submitOTHeader" );
            historyVO.setObjectId( otHeaderVO.getOtHeaderId() );

            // 处理加班小时详情（工作流页面用到）
            fetchOTHoursDetail( otHeaderVO );

            // 状态处理
            if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst, "0" ).equals( "1" ) )
            {
               if ( otHeaderVO.getStatus().trim().equals( "4" ) )
               {
                  // 确认
                  otHeaderVO.setStatus( "5" );
               }
               else
               {
                  // 批准
                  otHeaderVO.setStatus( "3" );
               }
            }
            else
            {
               // 确认
               otHeaderVO.setStatus( "5" );
            }

            // 审批通过对象
            final String passObject = KANUtil.toJSONObject( otHeaderVO ).toString();

            // 退回状态
            otHeaderVO.setStatus( "6" );
            // 审批不通过对象
            final String failObject = KANUtil.toJSONObject( otHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            //工作流设置role
            if ( workflowActualDTO.getWorkflowActualVO() != null && StringUtils.isNotBlank( otHeaderVO.getRole() ) )
            {
               workflowActualDTO.getWorkflowActualVO().setRole( otHeaderVO.getRole() );
            }
            workflowActualDTO.getWorkflowActualVO().setObjectId( otHeaderVO.getOtHeaderId() );
            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.attendance.OTHeaderVO" );
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, otHeaderVO );
         }
         // 不存在工作流
         else
         {
            // 考勤需要审批
            if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst, "0" ).equals( "1" ) )
            {
               if ( otHeaderVO.getStatus().trim().equals( "3" ) )
               {
                  // 确认
                  otHeaderVO.setStatus( "5" );

                  updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() ) && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
               }
               else
               {
                  // 批准
                  otHeaderVO.setStatus( "3" );

                  updated = originalObject.getEstimateStartDate().equals( otHeaderVO.getEstimateStartDate() )
                        && originalObject.getEstimateEndDate().equals( otHeaderVO.getEstimateEndDate() );
               }
            }
            // 考勤无需审批
            else
            {
               if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) == null || KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) == null )
               {
                  otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
                  otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
                  otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
               }
               updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() ) && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
               // 确认
               otHeaderVO.setStatus( "5" );
            }

            // 时间不变仅仅修改数据状态
            if ( updated && !refuse )
            {
               // 修改OTHeaderVO
               ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

               // 提交OTDetailVO List
               submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
            }
            // 和原有对象时间不同，则需重新添加detail数据
            else
            {
               updateOTHeader_nt( otHeaderVO );
            }
         }
      }
      else
      {
         // 修改OTHeaderVO
         final OTHeaderVO vo = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );
         otHeaderVO.setDescription( vo.getDescription() );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         // 提交OTDetailVO List
         submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
      }

      return -1;
   }

   // 根据OTHeaderVO衍生OTDetailVO
   private OTDetailVO generateOTDetailVO( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // 初始化OTDetailVO
      final OTDetailVO otDetailVO = new OTDetailVO();

      if ( KANUtil.filterEmpty( otHeaderVO.getTimesheetId() ) != null )
      {
         otDetailVO.setTimesheetId( otHeaderVO.getTimesheetId() );
      }

      otDetailVO.setOtHeaderId( otHeaderVO.getOtHeaderId() );
      otDetailVO.setItemId( otHeaderVO.getItemId() );
      otDetailVO.setStatus( otHeaderVO.getStatus() );
      otDetailVO.setCreateBy( otHeaderVO.getCreateBy() );
      otDetailVO.setModifyBy( otHeaderVO.getModifyBy() );

      return otDetailVO;
   }

   // Generate HistoryVO
   private HistoryVO generateHistoryVO( final OTHeaderVO otHeaderVO, final String owner )
   {
      final HistoryVO history = otHeaderVO.getHistoryVO();
      history.setAccessAction( OTHeaderAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( OTHeaderAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getOTHeaderVOByOTHeaderId" );
      // “2”表示是工作流的
      history.setObjectType( "2" );
      history.setAccountId( otHeaderVO.getAccountId() );
      history.setNameZH( otHeaderVO.getEmployeeNameZH() );
      history.setNameEN( otHeaderVO.getEmployeeNameEN() );
      history.setOwner( owner );
      return history;
   }

   @Override
   // 获取临时OTHeaderVO列表（处理每月加班上限）
   public List< OTHeaderVO > getOTHeaderVOsByContracrId( final String contractId ) throws KANException
   {
      try
      {
         // 初始化返回值对象
         final List< OTHeaderVO > otHeaderVOs = new ArrayList< OTHeaderVO >();

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( contractId );

         // 获取ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // 获取计薪开始、结束日
         String circleStartDay = "";
         String circleEndDay = "";

         if ( clientOrderHeaderVO != null )
         {
            circleStartDay = clientOrderHeaderVO.getCircleStartDay();
            circleEndDay = clientOrderHeaderVO.getCircleEndDay();
         }

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

         // 获取OTDetailVO列表（已按时间排序处理）
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( contractId );

         // 存在OTDetailVO列表
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            // 小时计数器
            double hours = 0;

            Calendar firstOTCalendar = KANUtil.createCalendar( ( ( OTDetailVO ) otDetailVOs.get( 0 ) ).getEstimateStartDate() );

            // 获取第一条数据月份
            String firstMonthly = KANUtil.getMonthly( firstOTCalendar );

            int count = 0;
            for ( int i = 0; i < otDetailVOs.size(); i++ )
            {
               if ( count == otDetailVOs.size() )
               {
                  break;
               }

               for ( Object otDetailVOObject : otDetailVOs )
               {
                  // 初始化OTDetailVO
                  final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

                  // 获取当前天天数
                  long currDays = KANUtil.getDays( KANUtil.createCalendar( otDetailVO.getEstimateStartDate() ) );

                  long startDate = KANUtil.getDays( KANUtil.createCalendar( KANUtil.getStartDate( firstMonthly, circleStartDay ) ) );

                  long endDate = KANUtil.getDays( KANUtil.createCalendar( KANUtil.getEndDate( firstMonthly, circleEndDay ) ) );

                  if ( currDays >= startDate && currDays <= endDate )
                  {
                     hours = hours + Double.valueOf( otDetailVO.getEstimateHours() );
                     count++;
                  }
               }

               if ( hours > 0 )
               {
                  // 初始化临时OTHeaderVO
                  OTHeaderVO tempOTHeaderVO = new OTHeaderVO();
                  tempOTHeaderVO.setMonthly( firstMonthly );

                  if ( Double.valueOf( circleEndDay ) - Double.valueOf( circleStartDay ) > 28 )
                  {
                     tempOTHeaderVO.setCircleStartDay( KANUtil.formatDate( firstMonthly + "/" + circleStartDay, "yyyy-MM-dd" ) );
                     tempOTHeaderVO.setCircleEndDay( KANUtil.formatDate( KANUtil.getLastDate( firstMonthly ), "yyyy-MM-dd" ) );
                  }
                  else
                  {
                     tempOTHeaderVO.setCircleStartDay( KANUtil.formatDate( KANUtil.getMonthly( firstMonthly, -1 ) + "/" + circleStartDay, "yyyy-MM-dd" ) );
                     tempOTHeaderVO.setCircleEndDay( KANUtil.formatDate( firstMonthly + "/" + circleEndDay, "yyyy-MM-dd" ) );
                  }
                  tempOTHeaderVO.setOtLimitByMonth( otLimitByMonth );
                  tempOTHeaderVO.setTotalOTHours( String.valueOf( hours ) );
                  otHeaderVOs.add( tempOTHeaderVO );
               }

               // 月份递增
               firstMonthly = KANUtil.getMonthly( firstMonthly, 1 );

               // 重置计数器
               hours = 0;
            }
         }

         return otHeaderVOs;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public List< OTDTO > getOTDTOsByAccountId( final String accountId ) throws KANException
   {
      try
      {
         // 初始化OTDTO列表 
         final List< OTDTO > otDTOs = new ArrayList< OTDTO >();

         // 初始化OTHeaderVO
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( accountId );
         otHeaderVO.setSortColumn( "estimateStartDate" );
         otHeaderVO.setSortOrder( "desc" );
         otHeaderVO.setStatus( BaseVO.TRUE );

         // 获取OTHeaderVO列表
         final List< Object > otHeaderVOs = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOsByCondition( otHeaderVO );

         // 存在OTHeaderVO列表，迭代装载OTDTO
         if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
         {
            for ( Object otHeaderVOObject : otHeaderVOs )
            {
               // 初始化OTDTO
               final OTDTO otDTO = new OTDTO();
               otDTO.setOtHeaderVO( ( OTHeaderVO ) otHeaderVOObject );

               // 获取OTDetailVO列表
               final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( ( ( OTHeaderVO ) otHeaderVOObject ).getOtHeaderId() );

               // 存在OTDetailVO列表
               if ( otDetailVOs != null && otDetailVOs.size() > 0 )
               {
                  for ( Object otDetailVOObject : otDetailVOs )
                  {
                     otDTO.getOtDetailVOs().add( ( OTDetailVO ) otDetailVOObject );
                  }
               }

               otDTOs.add( otDTO );
            }
         }

         return otDTOs;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 加班确认情况下
   private void recalculateTimesheet( final String otHeaderId, final String timesheetId ) throws KANException
   {
      try
      {
         // 获取OTHeaderVO 
         final OTHeaderVO otHeaderVO = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderId );

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

         // 获取ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO != null ? employeeContractVO.getOrderId()
               : "0" );

         // 考勤生成方式不能为：汇总导入生成
         if ( KANUtil.filterEmpty( timesheetId ) == null && clientOrderHeaderVO != null && !clientOrderHeaderVO.getAttendanceGenerate().equals( "3" ) )
         {
            // 计薪结束日期
            String circleEndDay = clientOrderHeaderVO.getCircleEndDay();
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getCircleEndDay(), "0 " ) == null )
            {
               circleEndDay = "31";
            }

            // 获取OTDetailVO List
            final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

            // 获取加班跨度月差数
            final int monthGap = KANUtil.getGapMonth( circleEndDay, otHeaderVO.getEstimateStartDate(), otHeaderVO.getEstimateEndDate() );

            // 初始化月份
            String monthly = KANUtil.getMonthlyByCondition( circleEndDay, otHeaderVO.getEstimateStartDate() );

            for ( int i = 0; i <= monthGap; i++ )
            {
               if ( i > 0 )
               {
                  monthly = KANUtil.getMonthly( monthly, i );
               }

               // 初始化TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
               tempTimesheetHeaderVO.setAccountId( otHeaderVO.getAccountId() );
               tempTimesheetHeaderVO.setCorpId( otHeaderVO.getCorpId() );
               tempTimesheetHeaderVO.setContractId( otHeaderVO.getContractId() );
               tempTimesheetHeaderVO.setEmployeeId( otHeaderVO.getEmployeeId() );
               tempTimesheetHeaderVO.setMonthly( monthly );
               tempTimesheetHeaderVO.setStatus( "1" );

               // 获取TimesheetDTO列表
               final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( tempTimesheetHeaderVO );

               // 存在TimesheetDTO列表
               if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
               {
                  // 获取TimesheetDTO
                  final TimesheetDTO timesheetDTO = timesheetDTOs.get( 0 );

                  // 存在OTDetailVO列表
                  if ( otDetailVOs != null && otDetailVOs.size() > 0 )
                  {
                     for ( Object otDetailVOObject : otDetailVOs )
                     {
                        // 初始化OTDetailVO
                        final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

                        // 是否是当前月
                        if ( KANUtil.getMonthlyByCondition( circleEndDay, otDetailVO.getEstimateStartDate() ).equals( tempTimesheetHeaderVO.getMonthly() ) )
                        {
                           if ( timesheetDTO.getTimesheetDetailVOs() != null && timesheetDTO.getTimesheetDetailVOs().size() > 0 )
                           {
                              for ( TimesheetDetailVO timesheetDetailVO : timesheetDTO.getTimesheetDetailVOs() )
                              {
                                 if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( otDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
                                 {
                                    // 初始化StringBuffer
                                    final StringBuffer rs = new StringBuffer( timesheetDetailVO.getDescription() );

                                    rs.append( getRemarkString( otHeaderVO, otDetailVO ) + "；" );

                                    // 设置备注
                                    timesheetDetailVO.setDescription( rs.toString() );

                                    // 修改TimesheetDetailVO
                                    this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                                    // 设置考勤ID
                                    otDetailVO.setTimesheetId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );

                                    // 修改OTDetailVO
                                    this.getOtDetailDao().updateOTDetail( otDetailVO );

                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).getOTHeaderVOsByCondition( otHeaderVO );
   }

   @Override
   public int count_OTUnread( OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).count_OTUnread( otHeaderVO );
   }

   @Override
   public int read_OT( OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).read_OT( otHeaderVO );
   }

   @Override
   // 删除加班记录并清理相应的考勤表
   // Add by siuvan.xia at 2014-7-4 02:07:29
   public int deleteOTHeader_cleanTS( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         // 开始事务
         startTransaction();

         // 获取OTDetailVO列表
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetailVOObject : otDetailVOs )
            {
               final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;
               if ( KANUtil.filterEmpty( otDetailVO.getTimesheetId() ) != null )
               {
                  final String remark = getRemarkString( otHeaderVO, otDetailVO );
                  final List< Object > timesheetDetailVOs = this.getTimesheetDetailDao().getTimesheetDetailVOsByHeaderId( otDetailVO.getTimesheetId() );
                  if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
                  {
                     for ( Object timesheetDetailVOObject : timesheetDetailVOs )
                     {
                        final TimesheetDetailVO timesheetDetailVO = ( TimesheetDetailVO ) timesheetDetailVOObject;
                        if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( otDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
                        {
                           final StringBuffer newRemark = new StringBuffer();
                           final String remarkArray[] = timesheetDetailVO.getDescription().split( "；" );
                           if ( remarkArray != null && remarkArray.length > 0 )
                           {
                              for ( String r : remarkArray )
                              {
                                 if ( !r.equals( remark.toString() ) )
                                 {
                                    newRemark.append( r + "；" );
                                 }
                              }

                              timesheetDetailVO.setDescription( newRemark.toString() );
                              // 更新TimesheetDetailVO
                              this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                              break;
                           }
                        }
                     }
                  }
               }
            }

         }

         // 删除加班记录
         deleteOTHeader_nt( otHeaderVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return -1;
   }

   private String getRemarkString( final OTHeaderVO otHeaderVO, final OTDetailVO otDetailVO ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // 获取加班科目
      final List< MappingVO > otItems = KANConstants.getKANAccountConstants( otHeaderVO.getAccountId() ).getOtItems( "ZH", otHeaderVO.getCorpId() );

      rs.append( KANUtil.getMappingValueByMappingList( otItems, otDetailVO.getItemId() ) + "（班）" );

      rs.append( "：" + TimesheetDTO.getRemartkString( otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate() ) );

      return rs.toString();
   }

   // 优化审批加班页面，加班详细状况。例如：OT1.5 - 2小时；OT3.0 - 4小时
   // Added by siuvan @2014-08-11
   private void fetchOTHoursDetail( final OTHeaderVO otHeaderVO ) throws KANException
   {
      final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         double tempOTHours = 0;
         final JSONObject jsonObject = new JSONObject();
         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

            // 如果是新建、提交-待审核、退回  加班小时数取Estimate
            if ( "1".equals( otHeaderVO.getStatus() ) || "2".equals( otHeaderVO.getStatus() ) || "6".equals( otHeaderVO.getStatus() ) )
            {
               tempOTHours = Double.valueOf( otDetailVO.getEstimateHours() );
            }
            // 如果是提交-待确认  加班小时数取Actual
            else if ( "4".equals( otHeaderVO.getStatus() ) )
            {
               tempOTHours = Double.valueOf( otDetailVO.getActualHours() );
            }

            if ( jsonObject.get( otDetailVO.getItemId() ) == null )
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( tempOTHours ) );
            }
            else
            {
               jsonObject.put( otDetailVO.getItemId(), String.valueOf( Double.valueOf( String.valueOf( jsonObject.get( otDetailVO.getItemId() ) ) ) + tempOTHours ) );
            }
         }

         otHeaderVO.setOtDetail( jsonObject.toString() );
      }
   }

   @Override
   public List< Object > exportOTDetailByCondition( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).exportOTDetailByCondition( otHeaderVO );
   }

   @Override
   public int updateOTHeader_onlyUP( final OTHeaderVO otHeaderVO ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );
   }

   @Override
   public OTHeaderVO getOTHeaderVOByOTImportHeaderId( final String otImportHeaderId ) throws KANException
   {
      return ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTImportHeaderId( otImportHeaderId );
   }

}
