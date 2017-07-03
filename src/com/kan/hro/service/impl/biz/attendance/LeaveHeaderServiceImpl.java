package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetDetailDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction;

public class LeaveHeaderServiceImpl extends ContextService implements LeaveHeaderService
{
   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.LeaveHeaderVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "leaveHeaderService";

   // 工作流service
   private WorkflowService workflowService;

   // 注入雇员DAO
   private EmployeeDao employeeDao;

   // 注入雇员 - 服务协议DAO
   private EmployeeContractDao employeeContractDao;

   // 注入雇员 - 服务协议 - 请假设置DAO
   private EmployeeContractLeaveDao employeeContractLeaveDao;

   // 注入客户 - 服务订单DAO
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入客户 - 服务订单 - 请假设置DAO
   private ClientOrderLeaveDao clientOrderLeaveDao;

   // 注入LeaveDetailDao
   private LeaveDetailDao leaveDetailDao;

   // 注入考勤表Service
   private TimesheetHeaderService timesheetHeaderService;

   // 注入考勤表Header Dao
   private TimesheetHeaderDao timesheetHeaderDao;

   // 注入考勤表Detail Dao
   private TimesheetDetailDao timesheetDetailDao;

   // 注入加班Detail Dao
   private OTDetailDao otDetailDao;

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

   public EmployeeContractLeaveDao getEmployeeContractLeaveDao()
   {
      return employeeContractLeaveDao;
   }

   public void setEmployeeContractLeaveDao( EmployeeContractLeaveDao employeeContractLeaveDao )
   {
      this.employeeContractLeaveDao = employeeContractLeaveDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public ClientOrderLeaveDao getClientOrderLeaveDao()
   {
      return clientOrderLeaveDao;
   }

   public void setClientOrderLeaveDao( ClientOrderLeaveDao clientOrderLeaveDao )
   {
      this.clientOrderLeaveDao = clientOrderLeaveDao;
   }

   public final LeaveDetailDao getLeaveDetailDao()
   {
      return leaveDetailDao;
   }

   public final void setLeaveDetailDao( LeaveDetailDao leaveDetailDao )
   {
      this.leaveDetailDao = leaveDetailDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public TimesheetHeaderService getTimesheetHeaderService()
   {
      return timesheetHeaderService;
   }

   public void setTimesheetHeaderService( TimesheetHeaderService timesheetHeaderService )
   {
      this.timesheetHeaderService = timesheetHeaderService;
   }

   public TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
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
   public PagedListHolder getLeaveHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LeaveHeaderDao leaveHeaderDao = ( LeaveHeaderDao ) getDao();
      pagedListHolder.setHolderSize( leaveHeaderDao.countLeaveHeaderVOsByCondition( ( LeaveHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( leaveHeaderDao.getLeaveHeaderVOsByCondition( ( LeaveHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( leaveHeaderDao.getLeaveHeaderVOsByCondition( ( LeaveHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public LeaveHeaderVO getLeaveHeaderVOByLeaveHeaderId( final String leaveHeaderId ) throws KANException
   {
      return ( ( LeaveHeaderDao ) getDao() ).getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );
   }

   @Override
   // Reviewed by Kevin Jin at 2014-04-20
   public int insertLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      logger.error( "###>>>>>>>>>>>>>>> 预计福利小时数 deBug:[" + leaveHeaderVO.getEstimateBenefitHours() + "]" );
      try
      {
         int rows = 0;

         // 开启事务
         startTransaction();

         if ( KANUtil.filterEmpty( leaveHeaderVO.getEstimateStartDate() ) == null || KANUtil.filterEmpty( leaveHeaderVO.getEstimateEndDate() ) == null )
         {
            return rows;
         }

         int cYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
         int sYear = Integer.valueOf( KANUtil.formatDate( leaveHeaderVO.getEstimateStartDate(), "yyyy" ) );

         if ( "41".equals( leaveHeaderVO.getItemId() ) && sYear > cYear && !"1".equals( leaveHeaderVO.getUseNextYearHours() ) )
         {
            leaveHeaderVO.setItemId( "49" );
         }

         // 状态初始化默认为“新建”
         leaveHeaderVO.setStatus( "1" );
         // 删除表示初始化默认为“未删除”
         leaveHeaderVO.setDeleted( "1" );
         // 插入LeaveHeaderVO
         ( ( LeaveHeaderDao ) getDao() ).insertLeaveHeader( leaveHeaderVO );

         rows++;

         // 不跨天，单笔插入LeaveDetailVO
         if ( KANUtil.getDays( KANUtil.createDate( leaveHeaderVO.getEstimateStartDate() ) ) == KANUtil.getDays( KANUtil.createDate( leaveHeaderVO.getEstimateEndDate() ) ) )
         {
            final LeaveDetailVO tempLeaveDetailVO = generateLeaveDetailVO( leaveHeaderVO );
            if ( tempLeaveDetailVO.getItemId().equals( "41" ) && !"1".equals( leaveHeaderVO.getUseNextYearHours() ) )
            {
               int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
               int dataYear = Integer.valueOf( KANUtil.formatDate( tempLeaveDetailVO.getEstimateStartDate(), "yyyy" ) );
               if ( dataYear > currYear && !"1".equals( leaveHeaderVO.getUseNextYearHours() ) )
               {
                  tempLeaveDetailVO.setItemId( "49" );
               }
            }

            this.getLeaveDetailDao().insertLeaveDetail( tempLeaveDetailVO );
         }
         // 跨天，多重插入LeaveDetailVO
         else
         {
            rows = rows + addLeaveDetails( leaveHeaderVO );
         }

         // 如果是提交，走工作流
         if ( KANUtil.filterEmpty( leaveHeaderVO.getSubAction() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getSubAction() ).equals( BaseAction.SUBMIT_OBJECT ) )
         {
            submitLeaveHeader_nt( leaveHeaderVO );
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

   // 添加LeaveDetailVO，如果是跨天则添加多个
   // Reviewed by Kevin Jin at 2014-04-20
   private int addLeaveDetails( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      int rows = 0;

      // 获取EmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

      // 获取ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

      // 获取EmployeeContractLeaveVO列表（已Merge ClientOrderLeaveVO列表）
      final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = getEmployeeContractLeaveVOsByContractId( leaveHeaderVO.getContractId() );

      // 初始化LegalQuantity
      double legalQuantity = 0;

      EmployeeContractLeaveVO tempEmployeeContractLeaveVO = null;
      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
         {
            if ( employeeContractLeaveVO.getItemId().equals( leaveHeaderVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getLegalQuantity() ) != null )
            {
               legalQuantity = Double.valueOf( employeeContractLeaveVO.getLegalQuantity() );
               tempEmployeeContractLeaveVO = employeeContractLeaveVO;
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
         leaveDetailVO.setEstimateStartDate( i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
         leaveDetailVO.setEstimateEndDate( i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

         // 获取当前天请假小时数
         final double currentHours = new TimesheetDTO().getLeaveHours( leaveHeaderVO.getItemId(), leaveHeaderVO.getAccountId(), calendarId, shiftId, leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() );

         // 如果是年假
         if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "41" ) )
         {
            // 获取当前服务协议（年假）已用掉法定小时数；
            final double legalHours = getUsedLegalHours( tempEmployeeContractLeaveVO );

            // 如果(当前法定+已用法定) 比较   (总法定)
            if ( currentHours + legalHours > legalQuantity )
            {
               leaveDetailVO.setEstimateLegalHours( String.valueOf( legalQuantity - legalHours ) );
               leaveDetailVO.setEstimateBenefitHours( String.valueOf( currentHours + legalHours - legalQuantity ) );
            }
            else
            {
               leaveDetailVO.setEstimateLegalHours( String.valueOf( currentHours ) );
               leaveDetailVO.setEstimateBenefitHours( "0" );
            }

            int currYear = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
            int dataYear = Integer.valueOf( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy" ) );
            if ( dataYear > currYear && !"1".equals( leaveHeaderVO.getUseNextYearHours() ) )
            {
               leaveDetailVO.setItemId( "49" );
            }
         }
         else if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "48" ) )
         {
            leaveDetailVO.setEstimateLegalHours( String.valueOf( currentHours ) );
         }
         else if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "49" ) )
         {
            leaveDetailVO.setEstimateBenefitHours( String.valueOf( currentHours ) );
         }
         // 其他科目
         else
         {
            leaveDetailVO.setEstimateBenefitHours( String.valueOf( currentHours ) );
         }

         startCalendar.add( Calendar.DATE, 1 );

         // 插入LeaveHeaderVO（请假小时数大于“0”）
         if ( currentHours > 0 )
         {
            this.getLeaveDetailDao().insertLeaveDetail( leaveDetailVO );

            rows++;
         }
      }

      return rows;
   }

   // Update LeaveHeaderVO
   // Reviewed by Kevin Jin at 2014-04-20
   public int updateLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 开启事务
         this.startTransaction();

         rows = updateLeaveHeader_nt( leaveHeaderVO );

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

   // Update LeaveHeaderVO
   // Reviewed by Kevin Jin at 2014-04-20
   private int updateLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 修改LeaveHeaderVO
         ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

         rows++;

         // 获取LeaveDetailVO列表
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         String timesheetId = "";
         // 存在LeaveDetailVO列表，遍历物理删除
         if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
         {
            for ( Object leaveDetailVOObject : leaveDetailVOs )
            {
               timesheetId = ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId();
               leaveHeaderVO.setTimesheetId( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() );
               this.getLeaveDetailDao().deleteLeaveDetail( ( LeaveDetailVO ) leaveDetailVOObject );
            }
         }

         // 添加多条LeaveDetailVO
         rows = rows + addLeaveDetails( leaveHeaderVO );

         // 状态为批准的
         if ( leaveHeaderVO.getStatus().equals( "3" ) )
         {
            recalculateTimesheet( leaveHeaderVO.getLeaveHeaderId(), timesheetId );
         }

         return rows;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Submit LeaveDetailVO List
   // Reviewed by Kevin Jin at 2014-04-20
   private int submitLeaveDetail( final String leaveHeaderId, final String status ) throws KANException
   {
      int rows = 0;

      if ( KANUtil.filterEmpty( leaveHeaderId ) == null || KANUtil.filterEmpty( status ) == null )
      {
         return rows;
      }

      try
      {
         // 获取LeaveDetailVO列表
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderId );

         String timesheetId = "";
         // 遍历LeaveDetailVO
         if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
         {
            for ( Object leaveDetalVOObject : leaveDetailVOs )
            {
               timesheetId = ( ( LeaveDetailVO ) leaveDetalVOObject ).getTimesheetId();
               ( ( LeaveDetailVO ) leaveDetalVOObject ).setStatus( status );
               this.getLeaveDetailDao().updateLeaveDetail( ( LeaveDetailVO ) leaveDetalVOObject );

               rows++;
            }
         }

         // 状态为批准的
         if ( KANUtil.filterEmpty( status ).equals( "3" ) )
         {
            recalculateTimesheet( leaveHeaderId, timesheetId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // “-1”表示提交成功
   // Reviewed by Kevin Jin at 2014-04-20
   public int submitLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         submitLeaveHeader_nt( leaveHeaderVO );

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

   @Override
   public int sickLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( leaveHeaderVO ) )
      {
         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // 获取HistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "4" ) ) )
            {
               // 状态改为提交-待销假
               leaveHeaderVO.setRetrieveStatus( "2" );

               // 修改LeaveHeaderVO
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
            }

            // Service的方法
            historyVO.setServiceMethod( "sickLeaveHeader" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // 销假状态
            leaveHeaderVO.setRetrieveStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // 拒绝销假状态
            leaveHeaderVO.setRetrieveStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            String employeeId = leaveHeaderVO.getEmployeeId();
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );

            if ( employeeVO != null )
            {
               // 追加到工作流名称上面
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }

            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         // 没有工作流
         else
         {
            // 销假
            leaveHeaderVO.setRetrieveStatus( "3" );

            // 修改LeaveHeaderVO
            ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
         }
      }
      else
      {
         ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
      }

      return -1;
   }

   // “-1”表示提交成功
   // Reviewed by Kevin Jin at 2014-04-20
   @Override
   public int submitLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( leaveHeaderVO ) )
      {
         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // 获取HistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getStatus() ).equals( "4" ) ) )
            {
               // 状态改为待审核
               leaveHeaderVO.setStatus( "2" );

               // 修改LeaveHeaderVO
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

               // 提交LeaveDetailVO List
               submitLeaveDetail( leaveHeaderVO.getLeaveHeaderId(), leaveHeaderVO.getStatus() );
            }

            // Service的方法
            historyVO.setServiceMethod( "submitLeaveHeader_nt" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // 批准状态
            leaveHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // 退回状态
            leaveHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            String employeeId = leaveHeaderVO.getEmployeeId();
            EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
            if ( employeeVO != null )
            {
               // 追加到工作流名称上面
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }
            //工作流设置role
            if ( workflowActualDTO.getWorkflowActualVO() != null && StringUtils.isNotBlank( leaveHeaderVO.getRole() ) )
            {
               workflowActualDTO.getWorkflowActualVO().setRole( leaveHeaderVO.getRole() );
            }
            workflowActualDTO.getWorkflowActualVO().setObjectId( leaveHeaderVO.getLeaveHeaderId() );
            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.attendance.LeaveHeaderVO" );
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         // 没有工作流
         else
         {
            // 批准
            leaveHeaderVO.setStatus( "3" );

            // 修改LeaveHeaderVO
            ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

            // 提交LeaveDetailVO List
            submitLeaveDetail( leaveHeaderVO.getLeaveHeaderId(), leaveHeaderVO.getStatus() );
         }
      }
      else
      {
         updateLeaveHeader_nt( leaveHeaderVO );
      }

      return -1;
   }

   public int deleteLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      int rows = 0;

      // 标记删除LeaveHeaderVO
      leaveHeaderVO.setDeleted( LeaveHeaderVO.FALSE );
      ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

      rows++;

      // 获取LeaveDetailVO列表
      final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

      // 遍历标记删除
      if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
      {
         for ( Object leaveDetailVOObject : leaveDetailVOs )
         {
            ( ( LeaveDetailVO ) leaveDetailVOObject ).setDeleted( LeaveHeaderVO.FALSE );
            this.getLeaveDetailDao().updateLeaveDetail( ( ( LeaveDetailVO ) leaveDetailVOObject ) );

            rows++;
         }
      }

      return rows;
   }

   @Override
   public int deleteLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // 开启事务
         this.startTransaction();

         rows = deleteLeaveHeader_nt( leaveHeaderVO );

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

   @Override
   public List< LeaveHeaderVO > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      final List< Object > leaveVOObjects = ( ( LeaveHeaderDao ) getDao() ).getLeaveHeaderVOsByCondition( leaveHeaderVO );
      final List< LeaveHeaderVO > leaveHeaderVOs = new ArrayList< LeaveHeaderVO >();

      if ( leaveVOObjects != null && leaveVOObjects.size() > 0 )
      {
         for ( Object leaveObj : leaveVOObjects )
         {
            leaveHeaderVOs.add( ( ( LeaveHeaderVO ) leaveObj ) );
         }
      }

      return leaveHeaderVOs;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-25
   // Updated by siuvan 2015-02-02
   // 获取员工的休假设置（包含休假设置剩余小时数）
   public List< EmployeeContractLeaveVO > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException
   {
      try
      {
         // 初始化返回值
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = new ArrayList< EmployeeContractLeaveVO >();

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( contractId );

         if ( employeeContractVO == null )
         {
            return employeeContractLeaveVOs;
         }

         // 获取EmployeeContractLeaveVO列表
         final List< Object > employeeContractLeaveVOObjects = this.employeeContractLeaveDao.getEmployeeContractLeaveVOsByContractId( contractId );

         // 获取ClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = null;
         if ( KANUtil.filterEmpty( employeeContractVO.getOrderId() ) != null )
         {
            clientOrderHeaderVO = this.clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
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

         // 明年年假，10月份开启
         EmployeeContractLeaveVO nextYearEmployeeContractLeaveVO = null;
         // 派送信息中存在休假设置
         if ( employeeContractLeaveVOObjects != null && employeeContractLeaveVOObjects.size() > 0 )
         {
            for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOObjects )
            {
               // 初始化EmployeeContractLeaveVO
               final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;

               // 状态为启用
               if ( employeeContractLeaveVO.getStatus().equals( "1" ) )
               {
                  String currYear = KANUtil.formatDate( new Date(), "yyyy" );
                  String nextYear = String.valueOf( Integer.valueOf( currYear ) + 1 );

                  // 年假默认只去当前年份
                  if ( ( !"41".equals( employeeContractLeaveVO.getItemId() ) )
                        || ( employeeContractLeaveVO.getItemId().equals( "41" ) && KANUtil.filterEmpty( employeeContractLeaveVO.getYear() ) != null && employeeContractLeaveVO.getYear().equals( currYear ) ) )
                  {
                     employeeContractLeaveVOs.add( ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject );
                     ( ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject ).setDataFrom( "1" );
                  }

                  if ( "41".equals( employeeContractLeaveVO.getItemId() ) && KANUtil.filterEmpty( employeeContractLeaveVO.getYear() ) != null
                        && employeeContractLeaveVO.getYear().equals( nextYear ) )
                  {
                     nextYearEmployeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;
                     ( ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject ).setDataFrom( "1" );
                  }
               }
            }
         }

         if ( clientOrderHeaderVO != null )
         {
            final List< Object > clientOrderLeaveVOs = this.clientOrderLeaveDao.getClientOrderLeaveVOsByOrderHeaderId( clientOrderHeaderVO.getOrderHeaderId() );

            if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
            {
               for ( Object clientOrderHeaderVOObject : clientOrderLeaveVOs )
               {
                  final ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) clientOrderHeaderVOObject;

                  boolean exist = false;

                  if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
                  {
                     for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
                     {
                        if ( employeeContractLeaveVO.getItemId().equals( clientOrderLeaveVO.getItemId() ) )
                        {
                           exist = true;
                           break;
                        }
                     }
                  }

                  // 状态为启用，不包含结算规则的年假
                  if ( !exist && clientOrderLeaveVO.getStatus().equals( "1" ) && !clientOrderLeaveVO.getItemId().equals( "41" ) )
                  {
                     final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
                     employeeContractLeaveVO.setEmployeeLeaveId( clientOrderLeaveVO.getOrderLeaveId() + "(来自结算规则设置)" );
                     employeeContractLeaveVO.setContractId( contractId );
                     employeeContractLeaveVO.setItemId( clientOrderLeaveVO.getItemId() );
                     employeeContractLeaveVO.setCycle( clientOrderLeaveVO.getCycle() );
                     employeeContractLeaveVO.setYear( clientOrderLeaveVO.getYear() );
                     employeeContractLeaveVO.setProbationUsing( clientOrderLeaveVO.getProbationUsing() );
                     employeeContractLeaveVO.setBenefitQuantity( clientOrderLeaveVO.getBenefitQuantity() );
                     employeeContractLeaveVO.setLegalQuantity( clientOrderLeaveVO.getLegalQuantity() );
                     employeeContractLeaveVO.setStatus( "1" );
                     employeeContractLeaveVO.setDataFrom( "2" );
                     employeeContractLeaveVOs.add( employeeContractLeaveVO );
                  }
               }
            }
         }

         // 装载加班换休
         fetchOvertimeChangeLeave( employeeContractLeaveVOs, employeeContractVO );

         // 装载销假
         //fetchSickLeave( employeeContractLeaveVOs, employeeContractVO );

         // 装载年假法定（去年）
         fetchLegalAnnualLeaveLastYear( employeeContractLeaveVOs, employeeContractVO );

         // 装载年假福利（去年）
         fetchBenefitAnnualLeaveLastYear( employeeContractLeaveVOs, employeeContractVO );

         // 计算剩余小时数
         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            setLeftHours( employeeContractLeaveVOs, employeeContractVO.getAccountId(), employeeContractVO.getCorpId(), contractId, calendarId, shiftId );
         }

         // 10月开启下年年假
         if ( Integer.valueOf( KANUtil.formatDate( new Date(), "dd" ) ) >= 10 && nextYearEmployeeContractLeaveVO != null )
         {
            for ( EmployeeContractLeaveVO vo : employeeContractLeaveVOs )
            {
               if ( "41".equals( vo.getItemId() ) && vo.getLeftBenefitQuantity() != null && Double.valueOf( vo.getLeftBenefitQuantity() ) <= 0 )
               {
                  // 计算剩余小时数
                  if ( nextYearEmployeeContractLeaveVO != null )
                  {
                     final List< EmployeeContractLeaveVO > nextYearList = new ArrayList< EmployeeContractLeaveVO >();
                     nextYearList.add( nextYearEmployeeContractLeaveVO );
                     setLeftHours( nextYearList, employeeContractVO.getAccountId(), employeeContractVO.getCorpId(), contractId, calendarId, shiftId );

                     vo.setUseNextYearHours( "1" );
                     vo.setYear( nextYearList.get( 0 ).getYear() );
                     vo.setBenefitQuantity( nextYearList.get( 0 ).getBenefitQuantity() );
                     vo.setLeftBenefitQuantity( nextYearList.get( 0 ).getLeftBenefitQuantity() );
                     break;
                  }
               }
            }
         }

         return employeeContractLeaveVOs;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 装载加班换休
   private void fetchOvertimeChangeLeave( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 获取加班换休记录
      final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( employeeContractVO.getContractId() );

      // 存在加班换休记录
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         // 初始化加班换休小时数
         double totalOTShiftHours = 0.0;

         for ( Object otDetailVOObject : otDetailVOs )
         {
            final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;
            if ( ( otDetailVO.getStatus().equals( "5" ) || otDetailVO.getStatus().equals( "7" ) ) && KANUtil.filterEmpty( otDetailVO.getItemId() ) != null
                  && otDetailVO.getItemId().equals( "25" ) )
            {
               totalOTShiftHours = totalOTShiftHours + Double.valueOf( otDetailVO.getActualHours() );
            }
         }

         if ( totalOTShiftHours > 0 )
         {
            // 初始化EmployeeContractLeaveVO
            final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
            employeeContractLeaveVO.setEmployeeLeaveId( "N/A" );
            employeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
            employeeContractLeaveVO.setItemId( "25" );
            employeeContractLeaveVO.setBenefitQuantity( String.valueOf( totalOTShiftHours ) );
            employeeContractLeaveVO.setLegalQuantity( "0" );
            employeeContractLeaveVO.setStatus( "1" );
            employeeContractLeaveVO.setProbationUsing( "1" );

            employeeContractLeaveVOs.add( employeeContractLeaveVO );
         }
      }
   }

   // 装载销假
   private void fetchSickLeave( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 获取销假记录，状态为批准
      final LeaveHeaderVO sickLeaveHeaderVO = new LeaveHeaderVO();
      sickLeaveHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sickLeaveHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      sickLeaveHeaderVO.setContractId( employeeContractVO.getContractId() );
      sickLeaveHeaderVO.setRetrieveStatus( "3" );
      final List< Object > sickLeaveHeaderVOs = ( ( LeaveHeaderDao ) getDao() ).getLeaveHeaderVOsByCondition( sickLeaveHeaderVO );

      // 存在批准的销假记录
      if ( sickLeaveHeaderVOs != null && sickLeaveHeaderVOs.size() > 0 )
      {
         // 初始化销假小时数（福利）
         double estimateHours = 0.0;
         double actualHours = 0.0;

         for ( Object o : sickLeaveHeaderVOs )
         {
            final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) o;

            if ( KANUtil.filterEmpty( leaveHeaderVO.getEstimateLegalHours() ) != null )
            {
               estimateHours = estimateHours + Double.valueOf( leaveHeaderVO.getEstimateLegalHours() );
            }

            if ( KANUtil.filterEmpty( leaveHeaderVO.getEstimateBenefitHours() ) != null )
            {
               estimateHours = estimateHours + Double.valueOf( leaveHeaderVO.getEstimateBenefitHours() );
            }

            if ( KANUtil.filterEmpty( leaveHeaderVO.getActualLegalHours() ) != null )
            {
               actualHours = actualHours + Double.valueOf( leaveHeaderVO.getActualLegalHours() );
            }

            if ( KANUtil.filterEmpty( leaveHeaderVO.getActualBenefitHours() ) != null )
            {
               actualHours = actualHours + Double.valueOf( leaveHeaderVO.getActualBenefitHours() );
            }
         }

         if ( estimateHours - actualHours > 0 )
         {
            // 初始化EmployeeContractLeaveVO
            final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
            employeeContractLeaveVO.setEmployeeLeaveId( "N/A" );
            employeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
            employeeContractLeaveVO.setItemId( "60" );
            employeeContractLeaveVO.setLegalQuantity( "0" );
            employeeContractLeaveVO.setBenefitQuantity( String.valueOf( estimateHours - actualHours ) );
            employeeContractLeaveVO.setStatus( "1" );
            employeeContractLeaveVO.setProbationUsing( "1" );

            employeeContractLeaveVOs.add( employeeContractLeaveVO );
         }
      }
   }

   // 装载年假法定（去年）
   private void fetchLegalAnnualLeaveLastYear( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int year = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      final EmployeeContractLeaveVO searchLastYearLeaveEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      searchLastYearLeaveEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      searchLastYearLeaveEmployeeContractLeaveVO.setItemId( "41" );
      searchLastYearLeaveEmployeeContractLeaveVO.setYear( String.valueOf( year - 1 ) );

      // 获取去年休假设置
      final EmployeeContractLeaveVO lastLeaveEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchLastYearLeaveEmployeeContractLeaveVO );

      // 必须是可延迟使用
      if ( lastLeaveEmployeeContractLeaveVO != null && "1".equals( lastLeaveEmployeeContractLeaveVO.getDelayUsing() )
            && KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth(), "0" ) != null )
      {
         String legalQuantionDelayMonth = KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth() ) == null ? "0"
               : lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth();
         String benefitQuantionDelayMonth = KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth() ) == null ? "0"
               : lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth();
         final EmployeeContractLeaveVO tempLegalAnnualLeaveVO = new EmployeeContractLeaveVO();
         tempLegalAnnualLeaveVO.setEmployeeLeaveId( "N/A" );
         tempLegalAnnualLeaveVO.setContractId( lastLeaveEmployeeContractLeaveVO.getContractId() );
         tempLegalAnnualLeaveVO.setCycle( "5" );
         tempLegalAnnualLeaveVO.setItemId( "48" );
         tempLegalAnnualLeaveVO.setLegalQuantityDelayMonth( lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth() );
         tempLegalAnnualLeaveVO.setBenefitQuantityDelayMonth( lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth() );
         tempLegalAnnualLeaveVO.setYear( lastLeaveEmployeeContractLeaveVO.getYear() );
         tempLegalAnnualLeaveVO.setLegalQuantity( lastLeaveEmployeeContractLeaveVO.getLegalQuantity() );
         tempLegalAnnualLeaveVO.setBenefitQuantity( "0" );
         tempLegalAnnualLeaveVO.setLeftLastYearLegalQuantityEndDate( KANUtil.formatDate( KANUtil.getDate( ( String.valueOf( year - 1 ) + "-12-31" ), 0, Integer.valueOf( legalQuantionDelayMonth ) ), null ) );
         tempLegalAnnualLeaveVO.setLeftLastYearBenefitQuantityEndDate( KANUtil.formatDate( KANUtil.getDate( ( String.valueOf( year - 1 ) + "-12-31" ), 0, Integer.valueOf( benefitQuantionDelayMonth ) ), null ) );
         tempLegalAnnualLeaveVO.setStatus( "1" );
         tempLegalAnnualLeaveVO.setProbationUsing( lastLeaveEmployeeContractLeaveVO.getProbationUsing() );

         // 小时数必须大于“0”，延迟可用不得超过当前时间
         if ( Double.valueOf( lastLeaveEmployeeContractLeaveVO.getLegalQuantity() ) > 0
               && new Date().getTime() < KANUtil.createDate( tempLegalAnnualLeaveVO.getLeftLastYearLegalQuantityEndDate() ).getTime() )
         {
            employeeContractLeaveVOs.add( 0, tempLegalAnnualLeaveVO );
         }
      }
   }

   // 装载年假福利（去年）
   private void fetchBenefitAnnualLeaveLastYear( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int year = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      final EmployeeContractLeaveVO searchLastYearLeaveEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      searchLastYearLeaveEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      searchLastYearLeaveEmployeeContractLeaveVO.setItemId( "41" );
      searchLastYearLeaveEmployeeContractLeaveVO.setYear( String.valueOf( year - 1 ) );

      // 获取去年休假设置
      final EmployeeContractLeaveVO lastLeaveEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchLastYearLeaveEmployeeContractLeaveVO );

      // 必须是可延迟使用
      if ( lastLeaveEmployeeContractLeaveVO != null && "1".equals( lastLeaveEmployeeContractLeaveVO.getDelayUsing() )
            && KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth(), "0" ) != null )
      {
         String legalQuantionDelayMonth = KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth() ) == null ? "0"
               : lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth();
         String benefitQuantionDelayMonth = KANUtil.filterEmpty( lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth() ) == null ? "0"
               : lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth();
         final EmployeeContractLeaveVO tempBenefitAnnualLeaveVO = new EmployeeContractLeaveVO();
         tempBenefitAnnualLeaveVO.setEmployeeLeaveId( "N/A" );
         tempBenefitAnnualLeaveVO.setContractId( lastLeaveEmployeeContractLeaveVO.getContractId() );
         tempBenefitAnnualLeaveVO.setCycle( "5" );
         tempBenefitAnnualLeaveVO.setItemId( "49" );
         tempBenefitAnnualLeaveVO.setLegalQuantityDelayMonth( lastLeaveEmployeeContractLeaveVO.getLegalQuantityDelayMonth() );
         tempBenefitAnnualLeaveVO.setBenefitQuantityDelayMonth( lastLeaveEmployeeContractLeaveVO.getBenefitQuantityDelayMonth() );
         tempBenefitAnnualLeaveVO.setYear( lastLeaveEmployeeContractLeaveVO.getYear() );
         tempBenefitAnnualLeaveVO.setLegalQuantity( "0" );
         tempBenefitAnnualLeaveVO.setBenefitQuantity( lastLeaveEmployeeContractLeaveVO.getBenefitQuantity() );
         tempBenefitAnnualLeaveVO.setLeftLastYearLegalQuantityEndDate( KANUtil.formatDate( KANUtil.getDate( ( String.valueOf( year - 1 ) + "-12-31" ), 0, Integer.valueOf( legalQuantionDelayMonth ), 1 ), null ) );
         tempBenefitAnnualLeaveVO.setLeftLastYearBenefitQuantityEndDate( KANUtil.formatDate( KANUtil.getDate( ( String.valueOf( year - 1 ) + "-12-31" ), 0, Integer.valueOf( benefitQuantionDelayMonth ), 1 ), null ) );
         tempBenefitAnnualLeaveVO.setStatus( "1" );
         tempBenefitAnnualLeaveVO.setProbationUsing( lastLeaveEmployeeContractLeaveVO.getProbationUsing() );

         // 小时数必须大于“0”，延迟可用不得超过当前时间
         if ( Double.valueOf( lastLeaveEmployeeContractLeaveVO.getBenefitQuantity() ) > 0
               && new Date().getTime() < KANUtil.createDate( tempBenefitAnnualLeaveVO.getLeftLastYearBenefitQuantityEndDate() ).getTime() )
         {
            employeeContractLeaveVOs.add( 0, tempBenefitAnnualLeaveVO );
         }
      }
   }

   // 计算剩余小时数
   // Reviewed by Kevin Jin at 2013-11-25
   private void setLeftHours( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final String accountId, final String corpId, final String contractId,
         final String calendarId, final String shiftId ) throws KANException
   {
      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
         {
            // 获取科目ID 
            final String itemId = employeeContractLeaveVO.getItemId();

            // 获取福利请假小时
            final double benefitQuantity = employeeContractLeaveVO.getBenefitQuantity() == null ? 0.0 : Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );

            // 获取法定请假小时（仅限年假，年假科目ID = 41）
            double legalQuantity = employeeContractLeaveVO.getLegalQuantity() == null ? 0.0 : Double.valueOf( employeeContractLeaveVO.getLegalQuantity() );

            // 年假、年假（去年）情况
            if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "41" ) )
            {
               // 获得当前已请小时数
               double usedHours = getUsedHours( employeeContractLeaveVO );
               // 请假时间未超过法定小时
               if ( usedHours <= legalQuantity )
               {
                  employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( benefitQuantity ) );
                  employeeContractLeaveVO.setLeftLegalQuantity( String.valueOf( legalQuantity - usedHours ) );
               }
               else
               {
                  employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( legalQuantity + benefitQuantity - usedHours ) );
                  employeeContractLeaveVO.setLeftLegalQuantity( "0" );
               }
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedHours ) );
            }
            // 年假法定（去年）
            else if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "48" ) )
            {
               double usedLegalHours = getUsedLegalHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedLegalHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( "0" );
               employeeContractLeaveVO.setLeftLegalQuantity( String.valueOf( ( legalQuantity - usedLegalHours ) <= 0 ? 0.0 : ( legalQuantity - usedLegalHours ) ) );
            }
            // 年假福利（去年）
            else if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "49" ) )
            {
               double usedBenefitHours = getUsedBenefitHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedBenefitHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( ( benefitQuantity - usedBenefitHours ) <= 0 ? 0.0 : ( benefitQuantity - usedBenefitHours ) ) );
               employeeContractLeaveVO.setLeftLegalQuantity( "0" );
            }
            // 其他情况
            else
            {
               // 获得当前已请小时数
               double usedHours = getUsedHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( ( benefitQuantity - usedHours ) <= 0 ? 0.0 : ( benefitQuantity - usedHours ) ) );
               employeeContractLeaveVO.setLeftLegalQuantity( "0" );
            }
         }
      }
   }

   // 获得当前已请假小时数
   private double getUsedHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return getUsedBenefitHours( employeeContractLeaveVO ) + getUsedLegalHours( employeeContractLeaveVO );
   }

   // 根据条件获取请假列表已用（法定）
   private double getUsedLegalHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      // 初始化LeaveVO
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setContractId( employeeContractLeaveVO.getContractId() );
      leaveHeaderVO.setItemId( employeeContractLeaveVO.getItemId() );
      leaveHeaderVO.setYear( employeeContractLeaveVO.getYear() );

      return Double.valueOf( ( ( LeaveHeaderDao ) getDao() ).sumLegalLeaveHoursByCondition( leaveHeaderVO ) );
   }

   // 根据条件获取请假列表已用（福利）
   private double getUsedBenefitHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      // 初始化LeaveVO
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setContractId( employeeContractLeaveVO.getContractId() );
      leaveHeaderVO.setItemId( employeeContractLeaveVO.getItemId() );
      leaveHeaderVO.setYear( employeeContractLeaveVO.getYear() );

      return Double.valueOf( ( ( LeaveHeaderDao ) getDao() ).sumBenefitLeaveHoursByCondition( leaveHeaderVO ) );
   }

   // 根据LeaveHeaderVO衍生LeaveDetailVO
   private LeaveDetailVO generateLeaveDetailVO( final LeaveHeaderVO leaveHeaderVO )
   {
      // 初始化LeaveDetailVO
      final LeaveDetailVO leaveDetailVO = new LeaveDetailVO();

      if ( KANUtil.filterEmpty( leaveHeaderVO.getTimesheetId() ) != null )
      {
         leaveDetailVO.setTimesheetId( leaveHeaderVO.getTimesheetId() );
      }
      leaveDetailVO.setLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
      leaveDetailVO.setItemId( leaveHeaderVO.getItemId() );
      leaveDetailVO.setEstimateStartDate( leaveHeaderVO.getEstimateStartDate() );
      leaveDetailVO.setEstimateEndDate( leaveHeaderVO.getEstimateEndDate() );
      leaveDetailVO.setActualStartDate( leaveHeaderVO.getActualStartDate() );
      leaveDetailVO.setActualEndDate( leaveHeaderVO.getActualEndDate() );
      leaveDetailVO.setEstimateLegalHours( leaveHeaderVO.getEstimateLegalHours() );
      leaveDetailVO.setEstimateBenefitHours( leaveHeaderVO.getEstimateBenefitHours() );
      leaveDetailVO.setActualLegalHours( leaveHeaderVO.getActualLegalHours() );
      leaveDetailVO.setActualBenefitHours( leaveHeaderVO.getActualBenefitHours() );
      leaveDetailVO.setStatus( leaveHeaderVO.getStatus() );
      leaveDetailVO.setCreateBy( leaveHeaderVO.getCreateBy() );
      leaveDetailVO.setModifyBy( leaveHeaderVO.getModifyBy() );
      leaveDetailVO.setRetrieveStatus( leaveHeaderVO.getRetrieveStatus() );

      return leaveDetailVO;
   }

   // Generate HistoryVO
   private HistoryVO generateHistoryVO( final LeaveHeaderVO leaveHeaderVO, final String owner )
   {
      final HistoryVO history = leaveHeaderVO.getHistoryVO();

      history.setAccessAction( LeaveHeaderAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( LeaveHeaderAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getLeaveHeaderVOByLeaveHeaderId" );

      // 表示是工作流的
      history.setObjectType( "2" );
      history.setAccountId( leaveHeaderVO.getAccountId() );
      history.setNameZH( leaveHeaderVO.getEmployeeNameZH() );
      history.setNameEN( leaveHeaderVO.getEmployeeNameEN() );
      history.setOwner( owner );

      return history;
   }

   // 重新计算Timesheet，请假被批准的情况
   // Reviewed by Kevin Jin at 2014-04-20
   private void recalculateTimesheet( final String leaveHeaderId, final String timesheetId ) throws KANException
   {
      try
      {
         // 获取LeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = this.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );

         // 获取LeaveDetailVO列表
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

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

            // 获取请假跨度月差数
            final int monthGap = KANUtil.getGapMonth( circleEndDay, leaveHeaderVO.getEstimateStartDate(), leaveHeaderVO.getEstimateEndDate() );

            // 初始化月份
            String monthly = KANUtil.getMonthlyByCondition( circleEndDay, leaveHeaderVO.getEstimateStartDate() );

            for ( int i = 0; i <= monthGap; i++ )
            {
               if ( i > 0 )
               {
                  monthly = KANUtil.getMonthly( monthly, i );
               }

               // 初始化TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
               tempTimesheetHeaderVO.setAccountId( leaveHeaderVO.getAccountId() );
               tempTimesheetHeaderVO.setCorpId( leaveHeaderVO.getCorpId() );
               tempTimesheetHeaderVO.setContractId( leaveHeaderVO.getContractId() );
               tempTimesheetHeaderVO.setEmployeeId( leaveHeaderVO.getEmployeeId() );
               tempTimesheetHeaderVO.setMonthly( monthly );
               tempTimesheetHeaderVO.setStatus( "1" );

               // 获取TimesheetDTO列表
               final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( tempTimesheetHeaderVO );

               // 存在TimesheetDTO列表
               if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
               {
                  // 获取TimesheetDTO
                  final TimesheetDTO timesheetDTO = timesheetDTOs.get( 0 );

                  // 获取TimesheetHeaderVO
                  final TimesheetHeaderVO timesheetHeaderVO = timesheetDTO.getTimesheetHeaderVO();

                  // 是否影响考勤表
                  boolean involveTimesheet = true;

                  // 请假科目为“销假”、“加班换休”的不改变考勤表数据
                  if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && ( leaveHeaderVO.getItemId().equals( "25" ) || leaveHeaderVO.getItemId().equals( "60" ) ) )
                  {
                     involveTimesheet = false;
                  }

                  // 改变考勤主表的数据
                  if ( involveTimesheet && timesheetHeaderVO != null )
                  {
                     // 工作小时数
                     double totalWorkHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() )
                           - getLeaveHoursByMonthly( leaveDetailVOs, monthly, circleEndDay );
                     // 全勤小时数
                     double totalFullHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() );
                     // 全勤天数
                     double totalFullDays = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() );

                     // 工作小时数异常处理
                     if ( totalWorkHours < 0 )
                     {
                        logger.info( "考勤表ID为" + timesheetHeaderVO.getHeaderId() + "工作小时数出现负数：totalWorkHours = " + totalWorkHours );
                        totalWorkHours = 0;
                     }

                     // 工作小时数
                     timesheetDTO.getTimesheetHeaderVO().setTotalWorkHours( String.valueOf( totalWorkHours ) );
                     timesheetDTO.getTimesheetHeaderVO().setTotalWorkDays( totalWorkHours == 0 ? "0" : String.valueOf( totalWorkHours * totalFullDays / totalFullHours ) );

                     // 是否正常出勤
                     if ( !timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours().equals( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() )
                           || !timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays().equals( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) )
                     {
                        timesheetDTO.getTimesheetHeaderVO().setIsNormal( "2" );
                     }

                     // 修改TinesheetHeaderVO
                     this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetDTO.getTimesheetHeaderVO() );
                  }

                  // 存在LeaveDetailVO列表
                  if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
                  {
                     // 遍历leaveDetailVOs
                     for ( Object leaveDetailVOObject : leaveDetailVOs )
                     {
                        // 初始化LeaveDetailVO
                        final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) leaveDetailVOObject;

                        // 是否是当前月
                        if ( KANUtil.getMonthlyByCondition( circleEndDay, leaveDetailVO.getEstimateStartDate() ).equals( monthly ) )
                        {
                           if ( timesheetDTO.getTimesheetDetailVOs() != null && timesheetDTO.getTimesheetDetailVOs().size() > 0 )
                           {
                              for ( TimesheetDetailVO timesheetDetailVO : timesheetDTO.getTimesheetDetailVOs() )
                              {
                                 if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
                                 {
                                    // 当天工作小时数
                                    double workHours = Double.valueOf( timesheetDetailVO.getWorkHours() );

                                    // 和LeaveDetailVO合并
                                    if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateBenefitHours() ) != null )
                                    {
                                       workHours = workHours - Double.valueOf( leaveDetailVO.getEstimateBenefitHours() );
                                    }

                                    if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateLegalHours() ) != null )
                                    {
                                       workHours = workHours - Double.valueOf( leaveDetailVO.getEstimateLegalHours() );
                                    }

                                    // 异常情况处理
                                    if ( workHours < 0 )
                                    {
                                       logger.info( "考勤详情表ID为" + timesheetDetailVO.getDetailId() + "工作小时数出现负数：workHours = " + workHours );
                                       workHours = 0.0;
                                    }

                                    // 设置工作小时数
                                    if ( involveTimesheet )
                                    {
                                       timesheetDetailVO.setWorkHours( String.valueOf( workHours ) );
                                    }

                                    // 初始化StringBuffer
                                    final StringBuffer rs = new StringBuffer( timesheetDetailVO.getDescription() );

                                    rs.append( getRemarkString( leaveHeaderVO, leaveDetailVO ) + "；" );

                                    // 设置备注
                                    timesheetDetailVO.setDescription( rs.toString() );

                                    // 修改TimesheetDetailVO
                                    this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                                    // 设置考勤ID
                                    leaveDetailVO.setTimesheetId( timesheetHeaderVO.getHeaderId() );

                                    // 修改LeaveDetailVO
                                    this.getLeaveDetailDao().updateLeaveDetail( leaveDetailVO );

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
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 根据月份获取该月请假小时数
   private double getLeaveHoursByMonthly( final List< Object > leaveDetailVOs, final String monthly, final String circleEndDay )
   {
      double hours = 0.0;

      for ( Object leaveDetailVOObject : leaveDetailVOs )
      {
         final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) leaveDetailVOObject;
         if ( KANUtil.getMonthlyByCondition( circleEndDay, leaveDetailVO.getEstimateStartDate() ).equals( monthly ) )
         {
            if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateBenefitHours() ) != null )
            {
               hours = hours + Double.valueOf( leaveDetailVO.getEstimateBenefitHours() );
            }

            if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateLegalHours() ) != null )
            {
               hours = hours + Double.valueOf( leaveDetailVO.getEstimateLegalHours() );
            }
         }
      }

      return hours;
   }

   @Override
   public int count_leaveUnread( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( ( LeaveHeaderDao ) getDao() ).count_leaveUnread( leaveHeaderVO );
   }

   @Override
   public int read_Leave( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( ( LeaveHeaderDao ) getDao() ).read_Leave( leaveHeaderVO );
   }

   @Override
   // 删除请假记录并清理考勤表
   // Add by siuvan.xia @ 2014-07-03
   public int deleteLeaveHeader_cleanTS( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         // 开始事务
         startTransaction();

         // 获取LeaveDeailVO列表
         final List< Object > leaveDeailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         if ( leaveDeailVOs != null && leaveDeailVOs.size() > 0 )
         {
            for ( Object leaveDetailVOObject : leaveDeailVOs )
            {
               // 初始化LeaveDetailVO
               final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) leaveDetailVOObject;

               // 销假或是加班换休
               boolean update = "25".equals( leaveDetailVO.getItemId() ) || "60".equals( leaveDetailVO.getItemId() );
               if ( KANUtil.filterEmpty( leaveDetailVO.getTimesheetId() ) != null )
               {
                  final String remark = getRemarkString( leaveHeaderVO, leaveDetailVO );
                  final TimesheetHeaderVO timesheetHeaderVO = this.getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( leaveDetailVO.getTimesheetId() );
                  final List< Object > timesheetDetailVOs = this.getTimesheetDetailDao().getTimesheetDetailVOsByHeaderId( leaveDetailVO.getTimesheetId() );
                  if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
                  {
                     for ( Object timesheetDetailVOObject : timesheetDetailVOs )
                     {
                        final TimesheetDetailVO timesheetDetailVO = ( TimesheetDetailVO ) timesheetDetailVOObject;

                        if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
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
                           }

                           if ( !update )
                           {
                              // 当天工作小时数
                              double workHours = 0.0;
                              // 和LeaveDetailVO合并
                              if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateBenefitHours() ) != null )
                              {
                                 workHours = workHours + Double.valueOf( leaveDetailVO.getEstimateBenefitHours() );
                              }

                              if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateLegalHours() ) != null )
                              {
                                 workHours = workHours + Double.valueOf( leaveDetailVO.getEstimateLegalHours() );
                              }

                              timesheetDetailVO.setWorkHours( String.valueOf( workHours + Double.valueOf( timesheetDetailVO.getWorkHours() ) ) );

                              // 工作小时数
                              double totalWorkHours = Double.valueOf( timesheetHeaderVO.getTotalWorkHours() ) + workHours;
                              // 全勤小时数
                              double totalFullHours = Double.valueOf( timesheetHeaderVO.getTotalFullHours() );
                              // 全勤天数
                              double totalFullDays = Double.valueOf( timesheetHeaderVO.getTotalFullDays() );

                              // 工作小时数
                              timesheetHeaderVO.setTotalWorkHours( String.valueOf( totalWorkHours ) );
                              timesheetHeaderVO.setTotalWorkDays( String.valueOf( totalWorkHours * totalFullDays / totalFullHours ) );

                              // 是否正常出勤
                              if ( !timesheetHeaderVO.getTotalWorkHours().equals( timesheetHeaderVO.getTotalFullHours() )
                                    || !timesheetHeaderVO.getTotalWorkDays().equals( timesheetHeaderVO.getTotalFullDays() ) )
                              {
                                 timesheetHeaderVO.setIsNormal( "2" );
                              }
                              else
                              {
                                 timesheetHeaderVO.setIsNormal( "1" );
                              }

                              this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );
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

         // 删除请假记录
         deleteLeaveHeader_nt( leaveHeaderVO );

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

   private String getRemarkString( final LeaveHeaderVO leaveHeaderVO, final LeaveDetailVO leaveDetailVO )
   {
      final StringBuffer rs = new StringBuffer();
      // 获取请假科目
      final List< MappingVO > leaveItems = KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getLeaveItems( "ZH", leaveHeaderVO.getCorpId() );

      if ( leaveItems != null )
      {
         leaveItems.add( KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getItemByItemId( "25", "ZH" ) );
      }
      rs.append( KANUtil.getMappingValueByMappingList( leaveItems, leaveDetailVO.getItemId() ) + "（假）" );

      rs.append( "：" + TimesheetDTO.getRemartkString( leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() ) );

      return rs.toString();
   }

   @Override
   public List< Object > exportLeaveDetailByCondition( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( ( LeaveHeaderDao ) getDao() ).exportLeaveDetailByCondition( leaveHeaderVO );
   }

   @Override
   public int sick_leave( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      int rows = 0;
      try
      {
         startTransaction();
         rows = sick_leave_nt( leaveHeaderVO );
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }
      return rows;
   }

   @Override
   public int sick_leave_nt( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( leaveHeaderVO ) )
      {
         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // 获取HistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );
         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );
            // 状态改为提交-待销假
            if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "4" ) ) )
            {
               leaveHeaderVO.setRetrieveStatus( "2" );
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
            }

            // Service的方法
            historyVO.setServiceMethod( "sick_leave_nt" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // 同意销假
            leaveHeaderVO.setRetrieveStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // 拒绝销假
            leaveHeaderVO.setRetrieveStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            if ( employeeVO != null )
            {
               // 追加到工作流名称上面
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }

            // 产生工作流
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         else
         {
           // 没有工作流
            leaveHeaderVO.setRetrieveStatus("3");
            dealSickLeave( leaveHeaderVO );
         }
      }
      else
      {
         dealSickLeave( leaveHeaderVO );
      }

      return -1;
   }

   /***
    * 处理销假dealSickLeave
    * @param leaveHeaderVO
    * @throws KANException 
    */
   private void dealSickLeave( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // 同意销假
      if ( "3".equals( leaveHeaderVO.getRetrieveStatus() ) )
      {
         double sickLeaveAfterHours = Double.valueOf( leaveHeaderVO.getActualLegalHours() ) + Double.valueOf( leaveHeaderVO.getActualBenefitHours() );
         // 如果假期全部消掉，删除数据
         if ( sickLeaveAfterHours == 0 )
         {
            deleteLeaveHeader_nt( leaveHeaderVO );
         }
         // 如果假期部分消掉，修改数据
         else
         {
            leaveHeaderVO.setEstimateStartDate( leaveHeaderVO.getActualStartDate() );
            leaveHeaderVO.setEstimateEndDate( leaveHeaderVO.getActualEndDate() );
            leaveHeaderVO.setEstimateLegalHours( leaveHeaderVO.getActualLegalHours() );
            leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getActualBenefitHours() );
            leaveHeaderVO.setActualStartDate( null );
            leaveHeaderVO.setActualEndDate( null );
            leaveHeaderVO.setActualBenefitHours( "0.0" );
            leaveHeaderVO.setActualLegalHours( "0.0" );
            ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

            // 获取LeaveDetailVO列表
            final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

            // 遍历标记删除
            if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
            {
               for ( Object leaveDetailVOObject : leaveDetailVOs )
               {
                  ( ( LeaveDetailVO ) leaveDetailVOObject ).setDeleted( LeaveHeaderVO.FALSE );
                  this.getLeaveDetailDao().updateLeaveDetail( ( ( LeaveDetailVO ) leaveDetailVOObject ) );
               }
            }

            addLeaveDetails( leaveHeaderVO );
         }
      }
      // 拒绝销假
      else if ( "4".equals( leaveHeaderVO.getRetrieveStatus() ) )
      {
         final LeaveHeaderVO baseLeaveHeaderVO = ( ( LeaveHeaderDao ) getDao() ).getLeaveHeaderVOByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );
         baseLeaveHeaderVO.setRemark2( baseLeaveHeaderVO.getActualStartDate() );
         baseLeaveHeaderVO.setRemark3( baseLeaveHeaderVO.getActualEndDate() );
         baseLeaveHeaderVO.setRemark5( baseLeaveHeaderVO.getActualHours() );
         baseLeaveHeaderVO.setRetrieveStatus( "4" );
         baseLeaveHeaderVO.setActualStartDate( null );
         baseLeaveHeaderVO.setActualEndDate( null );
         baseLeaveHeaderVO.setActualLegalHours( "0.0" );
         baseLeaveHeaderVO.setActualBenefitHours( "0.0" );
         ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( baseLeaveHeaderVO );
      }
   }
}
