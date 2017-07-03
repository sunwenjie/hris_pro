package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ShiftHeaderDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetDetailDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.LeaveDTO;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTDTO;
import com.kan.hro.domain.biz.attendance.OTDetailVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetReportExportVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：TimesheetHeaderServiceImpl  
*   
*/
public class TimesheetHeaderServiceImpl extends ContextService implements TimesheetHeaderService
{
   // 对象类名
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.TimesheetHeaderVO";

   // Service Name
   public static final String SERVICE_BEAN = "timesheetHeaderService";

   // 注入TimesheetDetailDao
   private TimesheetDetailDao timesheetDetailDao;

   // 注入TimesheetAllowanceDao
   private TimesheetAllowanceDao timesheetAllowanceDao;

   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   // 注入EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // 注入ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入EmployeeContractSalaryDao
   private EmployeeContractSalaryDao employeeContractSalaryDao;

   // 注入WorkflowService
   private WorkflowService workflowService;

   // 注入LeaveHeaderDao
   private LeaveHeaderDao leaveHeaderDao;

   // 注入LeaveDetailDao
   private LeaveDetailDao leaveDetailDao;

   // 注入OTHeaderDao
   private OTHeaderDao otHeaderDao;

   // 注入OTDetailDao
   private OTDetailDao otDetailDao;

   // 注入ShiftHeaderDao
   private ShiftHeaderDao shiftHeaderDao;

   // 注入TimesheetBatchDao
   private TimesheetBatchDao timesheetBatchDao;

   public TimesheetBatchDao getTimesheetBatchDao()
   {
      return timesheetBatchDao;
   }

   public void setTimesheetBatchDao( TimesheetBatchDao timesheetBatchDao )
   {
      this.timesheetBatchDao = timesheetBatchDao;
   }

   public ShiftHeaderDao getShiftHeaderDao()
   {
      return shiftHeaderDao;
   }

   public void setShiftHeaderDao( ShiftHeaderDao shiftHeaderDao )
   {
      this.shiftHeaderDao = shiftHeaderDao;
   }

   public TimesheetDetailDao getTimesheetDetailDao()
   {
      return timesheetDetailDao;
   }

   public void setTimesheetDetailDao( TimesheetDetailDao timesheetDetailDao )
   {
      this.timesheetDetailDao = timesheetDetailDao;
   }

   public TimesheetAllowanceDao getTimesheetAllowanceDao()
   {
      return timesheetAllowanceDao;
   }

   public void setTimesheetAllowanceDao( TimesheetAllowanceDao timesheetAllowanceDao )
   {
      this.timesheetAllowanceDao = timesheetAllowanceDao;
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

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public OTHeaderDao getOtHeaderDao()
   {
      return otHeaderDao;
   }

   public void setOtHeaderDao( OTHeaderDao otHeaderDao )
   {
      this.otHeaderDao = otHeaderDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public final LeaveDetailDao getLeaveDetailDao()
   {
      return leaveDetailDao;
   }

   public final void setLeaveDetailDao( LeaveDetailDao leaveDetailDao )
   {
      this.leaveDetailDao = leaveDetailDao;
   }

   public final OTDetailDao getOtDetailDao()
   {
      return otDetailDao;
   }

   public final void setOtDetailDao( OTDetailDao otDetailDao )
   {
      this.otDetailDao = otDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   @Override
   public PagedListHolder getTimesheetHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TimesheetHeaderDao timesheetHeaderDao = ( TimesheetHeaderDao ) getDao();
      pagedListHolder.setHolderSize( timesheetHeaderDao.countTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetHeaderVOsByCondition( ( TimesheetHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetHeaderVO getTimesheetHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return ( ( TimesheetHeaderDao ) getDao() ).insertTimesheetHeader( timesheetHeaderVO );
   }

   @Override
   public int insertTimesheetDTO( final List< TimesheetDTO > timesheetDTOs ) throws KANException
   {
      return insertTimesheetDTO( timesheetDTOs, null );
   }

   @Override
   public int insertTimesheetDTO( final List< TimesheetDTO > timesheetDTOs, final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      int size = 0;

      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         try
         {
            // 开启事务
            this.startTransaction();

            // 员工姓名统计
            final StringBuffer employeeNameList = new StringBuffer();

            // 只统计50个员工姓名
            int limit = 50;

            for ( TimesheetDTO timesheetDTO : timesheetDTOs )
            {
               if ( timesheetDTO.getTimesheetHeaderVO() != null
                     && ( ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) ) > 0 )
                           || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() ) ) > 0 )
                           || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() ) ) > 0 ) || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() ) ) > 0 ) ) )
               {
                  limit--;

                  // 插入批次
                  if ( timesheetBatchVO != null && size == 0 )
                  {
                     timesheetBatchVO.setDescription( employeeNameList.toString() );
                     // 批次执行结束时间
                     timesheetBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
                     this.getTimesheetBatchDao().insertTimesheetBatch( timesheetBatchVO );
                  }

                  if ( limit >= 0 )
                  {
                     if ( size == 0 )
                     {
                        employeeNameList.append( timesheetDTO.getTimesheetHeaderVO().getEmployeeNameZH() );
                     }
                     else
                     {
                        employeeNameList.append( "、" + timesheetDTO.getTimesheetHeaderVO().getEmployeeNameZH() );
                     }
                  }

                  if ( timesheetBatchVO != null )
                  {
                     timesheetDTO.getTimesheetHeaderVO().setBatchId( timesheetBatchVO.getBatchId() );
                  }
                  // 插入Timesheet Header
                  ( ( TimesheetHeaderDao ) getDao() ).insertTimesheetHeader( timesheetDTO.getTimesheetHeaderVO() );

                  // 插入Timesheet Detail
                  if ( timesheetDTO.getTimesheetDetailVOs() != null && timesheetDTO.getTimesheetDetailVOs().size() > 0 )
                  {
                     for ( TimesheetDetailVO timesheetDetailVO : timesheetDTO.getTimesheetDetailVOs() )
                     {
                        timesheetDetailVO.setAccountId( timesheetDTO.getTimesheetHeaderVO().getAccountId() );
                        timesheetDetailVO.setHeaderId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );
                        timesheetDetailVO.setCreateBy( timesheetDTO.getTimesheetHeaderVO().getCreateBy() );
                        timesheetDetailVO.setModifyBy( timesheetDTO.getTimesheetHeaderVO().getModifyBy() );
                        this.getTimesheetDetailDao().insertTimesheetDetail( timesheetDetailVO );
                     }
                  }

                  // 插入Timesheet Allowance
                  if ( timesheetDTO.getTimesheetAllowanceVOs() != null && timesheetDTO.getTimesheetAllowanceVOs().size() > 0 )
                  {
                     for ( TimesheetAllowanceVO timesheetAllowanceVO : timesheetDTO.getTimesheetAllowanceVOs() )
                     {
                        timesheetAllowanceVO.setAccountId( timesheetDTO.getTimesheetHeaderVO().getAccountId() );
                        timesheetAllowanceVO.setHeaderId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );
                        timesheetAllowanceVO.setCreateBy( timesheetDTO.getTimesheetHeaderVO().getCreateBy() );
                        timesheetAllowanceVO.setModifyBy( timesheetDTO.getTimesheetHeaderVO().getModifyBy() );
                        this.getTimesheetAllowanceDao().insertTimesheetAllowance( timesheetAllowanceVO );
                     }
                  }

                  // LeaveDetailVO载入timesheetId
                  if ( timesheetDTO.getLeaveDTOs() != null && timesheetDTO.getLeaveDTOs().size() > 0 )
                  {
                     for ( LeaveDTO leaveDTO : timesheetDTO.getLeaveDTOs() )
                     {
                        if ( leaveDTO != null && leaveDTO.getLeaveDetailVOs() != null && leaveDTO.getLeaveDetailVOs().size() > 0 )
                        {
                           for ( LeaveDetailVO leaveDetailVO : leaveDTO.getLeaveDetailVOs() )
                           {
                              leaveDetailVO.setTimesheetId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );
                              leaveDetailVO.setModifyBy( timesheetDTO.getTimesheetHeaderVO().getModifyBy() );
                              leaveDetailVO.setModifyDate( new Date() );
                              this.getLeaveDetailDao().updateLeaveDetail( leaveDetailVO );
                           }
                        }
                     }
                  }

                  // OTDetailVO载入timesheetId
                  if ( timesheetDTO.getOtDTOs() != null && timesheetDTO.getOtDTOs().size() > 0 )
                  {
                     for ( OTDTO otDTO : timesheetDTO.getOtDTOs() )
                     {
                        if ( otDTO != null && otDTO.getOtDetailVOs() != null && otDTO.getOtDetailVOs().size() > 0 )
                        {
                           for ( OTDetailVO otDetailVO : otDTO.getOtDetailVOs() )
                           {
                              otDetailVO.setTimesheetId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );
                              otDetailVO.setModifyBy( timesheetDTO.getTimesheetHeaderVO().getModifyBy() );
                              otDetailVO.setModifyDate( new Date() );
                              this.getOtDetailDao().updateOTDetail( otDetailVO );
                           }
                        }
                     }
                  }

                  size++;
               }
            }

            // 提交事务
            this.commitTransaction();

            // 修改批次
            if ( timesheetBatchVO != null )
            {
               final TimesheetBatchVO tempTimesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetBatchVO.getBatchId() );
               tempTimesheetBatchVO.setDescription( employeeNameList.toString() );
               this.getTimesheetBatchDao().updateTimesheetBatch( tempTimesheetBatchVO );
            }
         }
         catch ( final Exception e )
         {
            // 回滚事务
            this.rollbackTransaction();
            throw new KANException( e );
         }
      }

      return size;
   }

   @Override
   public int updateTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         updateTimesheetHeader_nt( timesheetHeaderVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   private int updateTimesheetHeader_nt( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      if ( timesheetHeaderVO != null && KANUtil.filterEmpty( timesheetHeaderVO.getDayTypeArray() ) != null
            && ( timesheetHeaderVO.getDayTypeArray().length > 0 || timesheetHeaderVO.getWorkHoursArray().length > 0 || timesheetHeaderVO.getBaseArray().length > 0 ) )
      {

         // 获取EmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( timesheetHeaderVO.getContractId() );

         // 获取ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO != null ? employeeContractVO.getOrderId()
               : "0" );

         // 获取shiftId
         String shiftId = employeeContractVO != null ? employeeContractVO.getShiftId() : "0";
         if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
         {
            shiftId = clientOrderHeaderVO.getShiftId();
         }

         if ( timesheetHeaderVO.getWorkHoursArray() != null && timesheetHeaderVO.getWorkHoursArray().length > 0 )
         {
            // 如果主表对象工作小时总数为空或者为0；则该值按照从表工作小时数计算
            if ( KANUtil.filterEmpty( timesheetHeaderVO.getTotalWorkHours(), "0" ) == null )
            {
               timesheetHeaderVO.setTotalWorkHours( String.valueOf( calculationWorkHours( timesheetHeaderVO.getWorkHoursArray() ) ) );
            }

            // 迭代 
            for ( String workHoursString : timesheetHeaderVO.getWorkHoursArray() )
            {
               final String[] workHours = workHoursString.split( "_" );

               if ( workHours != null && workHours.length == 2 )
               {
                  String dayType = getDayType( timesheetHeaderVO.getDayTypeArray(), workHours[ 0 ] );

                  // 获得TimesheetDetailVO
                  final TimesheetDetailVO timesheetDetailVO = this.timesheetDetailDao.getTimesheetDetailVOByDetailId( workHours[ 0 ] );
                  timesheetDetailVO.setWorkHours( workHours[ 1 ] );
                  timesheetDetailVO.setDayType( KANUtil.filterEmpty( dayType ) == null ? timesheetDetailVO.getDayType() : dayType );
                  timesheetDetailVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
                  timesheetDetailVO.setModifyDate( timesheetHeaderVO.getModifyDate() );
                  this.timesheetDetailDao.updateTimesheetDetail( timesheetDetailVO );
               }
            }
         }

         if ( timesheetHeaderVO.getBaseArray() != null && timesheetHeaderVO.getBaseArray().length > 0 )
         {
            for ( String baseStr : timesheetHeaderVO.getBaseArray() )
            {
               final String[] base = baseStr.split( "_" );

               // 获得TimesheetAllowanceVO
               final TimesheetAllowanceVO timesheetAllowanceVO = this.timesheetAllowanceDao.getTimesheetAllowanceVOByAllowanceId( base[ 0 ] );
               timesheetAllowanceVO.setBase( base.length == 2 ? base[ 1 ] : "0.0" );
               timesheetAllowanceVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
               timesheetAllowanceVO.setModifyDate( timesheetHeaderVO.getModifyDate() );
               this.timesheetAllowanceDao.updateTimesheetAllowance( timesheetAllowanceVO );
            }
         }

         // 总工作小时
         double totalWorkHours = calculationWorkHours( timesheetHeaderVO.getWorkHoursArray() );

         // 全勤小时数
         double totalFullHours = Double.valueOf( timesheetHeaderVO.getTotalFullHours() );

         // 全勤天数
         double totalFullDays = Double.valueOf( timesheetHeaderVO.getTotalFullDays() );

         // 总工作天数
         double totalWorkDays = totalWorkHours == 0 || totalFullHours == 0 ? 0 : totalWorkHours * totalFullDays / totalFullHours;

         if ( totalWorkHours > totalFullHours && totalWorkDays > totalFullDays )
         {
            totalFullHours = totalWorkHours;
            totalFullDays = totalWorkDays;
         }

         timesheetHeaderVO.setTotalWorkHours( String.valueOf( totalWorkHours ) );
         timesheetHeaderVO.setTotalWorkDays( String.valueOf( totalWorkDays ) );
         timesheetHeaderVO.setTotalFullHours( String.valueOf( totalFullHours ) );
         timesheetHeaderVO.setTotalFullDays( String.valueOf( totalFullDays ) );

         // 是否正常出勤
         if ( totalWorkHours != totalFullHours && totalWorkDays != totalFullDays )
         {
            timesheetHeaderVO.setIsNormal( "2" );
         }
         else
         {
            timesheetHeaderVO.setIsNormal( "1" );
         }
      }
      else
      {
         timesheetHeaderVO.setIsNormal( timesheetHeaderVO.getTotalWorkHours().equals( timesheetHeaderVO.getTotalFullHours() ) ? "1" : "2" );
      }

      ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );

      return 0;
   }

   @Override
   public int submit_header( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // 是否是审批过后的对象
      if ( !WorkflowService.isPassObject( timesheetHeaderVO ) )
      {
         // 生成historyVO
         final HistoryVO historyVO = generateHistoryVO( timesheetHeaderVO );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetHeaderVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( timesheetHeaderVO.getStatus() != null && !timesheetHeaderVO.getStatus().trim().equals( "3" ) )
            {
               // 待审核
               submit( timesheetHeaderVO, "2" );
            }

            // Service的方法
            historyVO.setServiceMethod( "submit_header" );
            historyVO.setObjectId( timesheetHeaderVO.getHeaderId() );

            timesheetHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            // 退回状态
            timesheetHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetHeaderVO );

            return -1;
         }
         // 没有工作流
         else
         {
            return submit( timesheetHeaderVO, "3" );
         }
      }

      return submit( timesheetHeaderVO, timesheetHeaderVO.getStatus() );
   }

   // Reviewed by Kevin Jin at 2014-06-15
   private int submit( final TimesheetHeaderVO timesheetHeaderVO, final String status ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         // 初始化TimesheetHeaderVO列表
         final List< Object > timesheetHeaderVOs = new ArrayList< Object >();
         // 标识是否修改批次
         boolean updated = false;
         // 批次考勤表个数
         int originalSize = 0;
         // 批次已提交考勤表个数
         int submitedSize = 0;

         // 更改该批次Header状态
         timesheetHeaderVO.setStatus( status );
         ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );

         // 初始化TimesheetHeaderVO
         final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
         tempTimesheetHeaderVO.setAccountId( timesheetHeaderVO.getAccountId() );
         tempTimesheetHeaderVO.setBatchId( timesheetHeaderVO.getBatchId() );
         tempTimesheetHeaderVO.setCorpId( timesheetHeaderVO.getCorpId() );
         originalSize = ( ( TimesheetHeaderDao ) getDao() ).countTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO );

         tempTimesheetHeaderVO.setStatus( "3,5,6" );
         submitedSize = ( ( TimesheetHeaderDao ) getDao() ).countTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO );

         if ( originalSize == submitedSize )
         {
            updated = true;
         }

         // 更改批次状态
         if ( updated )
         {
            final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( tempTimesheetHeaderVO.getBatchId() );
            timesheetBatchVO.setStatus( status );
            timesheetBatchVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
            timesheetBatchVO.setModifyDate( new Date() );
            this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
         }

         // 如果是批准或者退回，重组TimesheetBatchVO中Description字段
         if ( KANUtil.filterEmpty( status ) != null && status.equals( "6" ) )
         {
            // 初始化StringBuffer
            final StringBuffer employeeNames = new StringBuffer();

            tempTimesheetHeaderVO.setStatus( "1,2,3,4,5" );
            timesheetHeaderVOs.addAll( ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );

            int index = 0;
            if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
            {
               for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
               {
                  // 获取EmployeeVO
                  final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( ( ( TimesheetHeaderVO ) timesheetHeaderVOObject ).getEmployeeId() );

                  if ( employeeVO != null )
                  {
                     if ( index == 0 )
                        employeeNames.append( employeeVO.getNameZH() );
                     else
                        employeeNames.append( "、" + employeeVO.getNameZH() );
                  }

                  index++;

                  if ( index > 500 )
                  {
                     break;
                  }
               }
            }

            final TimesheetBatchVO tempTimesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );
            // 修改批次描述
            tempTimesheetBatchVO.setDescription( employeeNames.toString() );
            this.getTimesheetBatchDao().updateTimesheetBatch( tempTimesheetBatchVO );
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public int submitTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( timesheetHeaderVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( timesheetHeaderVO );

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetHeaderVO );
         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( timesheetHeaderVO.getStatus() != null && !timesheetHeaderVO.getStatus().trim().equals( "3" ) )
            {
               // 状态改为待审核
               timesheetHeaderVO.setStatus( "2" );
               ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );
            }

            // Service的方法
            historyVO.setServiceMethod( "submitTimesheetHeader" );
            historyVO.setObjectId( timesheetHeaderVO.getHeaderId() );

            // 批准状态
            timesheetHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            // 退回状态
            timesheetHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetHeaderVO );

            return -1;
         }
         // 没有工作流
         else
         {
            timesheetHeaderVO.setStatus( "3" );

            return dealSubmitMethodReturn( timesheetHeaderVO );
         }
      }

      return dealSubmitMethodReturn( timesheetHeaderVO );
   }

   private int dealSubmitMethodReturn( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      if ( timesheetHeaderVO != null && KANUtil.filterEmpty( timesheetHeaderVO.getDayTypeArray() ) != null
            && ( timesheetHeaderVO.getDayTypeArray().length > 0 || timesheetHeaderVO.getWorkHoursArray().length > 0 || timesheetHeaderVO.getBaseArray().length > 0 ) )
      {
         return updateTimesheetHeader( timesheetHeaderVO );
      }

      return ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );
   }

   @Override
   public int deleteTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      try
      {
         // 开始事务
         startTransaction();

         // 获取TimesheetDetailVO列表
         final List< Object > timesheetDetailVOs = this.getTimesheetDetailDao().getTimesheetDetailVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

         if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
         {
            for ( Object timesheetDetailVOObject : timesheetDetailVOs )
            {
               ( ( TimesheetDetailVO ) timesheetDetailVOObject ).setModifyBy( timesheetHeaderVO.getModifyBy() );
               ( ( TimesheetDetailVO ) timesheetDetailVOObject ).setModifyDate( new Date() );
               ( ( TimesheetDetailVO ) timesheetDetailVOObject ).setDeleted( BaseVO.FALSE );
               this.getTimesheetDetailDao().updateTimesheetDetail( ( ( TimesheetDetailVO ) timesheetDetailVOObject ) );
            }
         }

         // 获取TimesheetAllowanceVO列表
         final List< Object > timesheetAllowanceVOs = this.getTimesheetAllowanceDao().getTimesheetAllowanceVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

         if ( timesheetAllowanceVOs != null && timesheetAllowanceVOs.size() > 0 )
         {
            for ( Object timesheetAllovanceVOObject : timesheetAllowanceVOs )
            {
               ( ( TimesheetAllowanceVO ) timesheetAllovanceVOObject ).setModifyBy( timesheetHeaderVO.getModifyBy() );
               ( ( TimesheetAllowanceVO ) timesheetAllovanceVOObject ).setModifyDate( new Date() );
               ( ( TimesheetAllowanceVO ) timesheetAllovanceVOObject ).setDeleted( BaseVO.FALSE );
               this.getTimesheetAllowanceDao().updateTimesheetAllowance( ( ( TimesheetAllowanceVO ) timesheetAllovanceVOObject ) );
            }
         }

         timesheetHeaderVO.setDeleted( BaseVO.FALSE );
         ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );

         // 获取当前批次下面的TimesheetHeaderVO
         final List< Object > timesheetHeaderVOs = ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByBatchId( timesheetHeaderVO.getBatchId() );

         // 获取TimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

         // 如果该批次下不存在考勤表，则标记删除该批次
         if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() == 0 )
         {
            timesheetBatchVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
            timesheetBatchVO.setModifyDate( new Date() );
            timesheetBatchVO.setDeleted( BaseVO.FALSE );

            this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
         }
         else
         {
            if ( KANUtil.filterEmpty( timesheetBatchVO.getDescription() ) != null )
            {
               final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( timesheetHeaderVO.getEmployeeId() );

               final StringBuffer rs = new StringBuffer();

               final String[] employeeNameArray = timesheetBatchVO.getDescription().split( "、" );

               if ( employeeNameArray != null && employeeNameArray.length > 0 )
               {
                  for ( String employeeName : employeeNameArray )
                  {
                     if ( !employeeName.equals( employeeVO.getNameZH() ) )
                     {
                        if ( KANUtil.filterEmpty( rs.toString() ) == null )
                        {
                           rs.append( employeeName );
                        }
                        else
                        {
                           rs.append( "、" + employeeName );
                        }
                     }
                  }
               }

               timesheetBatchVO.setDescription( rs.toString() );

               this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
            }
         }

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

   @Override
   public int generateTimesheet() throws KANException
   {
      return generateTimesheet( null );
   }

   @Override
   public int generateTimesheet( TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return generateTimesheet( timesheetHeaderVO, null );
   }

   @Override
   public int generateTimesheet( final TimesheetHeaderVO timesheetHeaderVO, final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      int size = 0;

      // 初始化TimesheetHeaderVO - 用于搜索
      TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();

      String userId = "";

      if ( timesheetHeaderVO != null )
      {
         tempTimesheetHeaderVO = timesheetHeaderVO;
         userId = timesheetHeaderVO.getCreateBy();
      }
      else
      {
         // 初始化Calendar
         final Calendar calendar = KANUtil.getCalendar( new Date() );

         // 设置所需生成Timesheet对应的服务协议的开始计薪周期和Monthly
         tempTimesheetHeaderVO.setMonthly( KANUtil.getMonthly( calendar.getTime() ) );
         tempTimesheetHeaderVO.setCircleStartDay( String.valueOf( calendar.get( Calendar.DATE ) ) );

         // 默认定时器是在每天00:00以后执行的，所以日期减“1”
         calendar.add( Calendar.DATE, -1 );
         // 设置所需生成Timesheet对应的服务协议的结束计薪周期和Monthly
         tempTimesheetHeaderVO.setAdditionalMonthly( KANUtil.getMonthly( calendar.getTime() ) );
         // 通常正月会设置“1-31”，不足31天的月需特殊设置成“31”
         if ( calendar.get( Calendar.DATE ) == calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) )
         {
            tempTimesheetHeaderVO.setCircleEndDay( "31" );
         }
         else
         {
            tempTimesheetHeaderVO.setCircleEndDay( String.valueOf( calendar.get( Calendar.DATE ) ) );
         }

         userId = KANConstants.SUPER_ADMIN_ID;
      }

      //处理数据权限
      //      tempTimesheetHeaderVO.setHasIn( timesheetBatchVO.getHasIn() );
      //      tempTimesheetHeaderVO.setNotIn( timesheetBatchVO.getNotIn() );
      if ( timesheetBatchVO != null )
      {
         tempTimesheetHeaderVO.setRulePublic( timesheetBatchVO.getRulePublic() );
         tempTimesheetHeaderVO.setRulePrivateIds( timesheetBatchVO.getRulePrivateIds() );
         tempTimesheetHeaderVO.setRulePositionIds( timesheetBatchVO.getRulePositionIds() );
         tempTimesheetHeaderVO.setRuleBranchIds( timesheetBatchVO.getRuleBranchIds() );
         tempTimesheetHeaderVO.setRuleBusinessTypeIds( timesheetBatchVO.getRuleBusinessTypeIds() );
         tempTimesheetHeaderVO.setRuleEntityIds( timesheetBatchVO.getRuleEntityIds() );
      }
      final List< TimesheetDTO > timesheetDTOs = this.prepareGenerateTimesheetDTOsByCondition( tempTimesheetHeaderVO );

      // 遍历并逐个计算服务协议
      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         for ( TimesheetDTO timesheetDTO : timesheetDTOs )
         {
            timesheetDTO.calculateTimesheet();

            if ( timesheetDTO.getTimesheetHeaderVO() != null )
            {
               timesheetDTO.getTimesheetHeaderVO().setCreateBy( userId );
               timesheetDTO.getTimesheetHeaderVO().setModifyBy( userId );
            }
         }

         // 批量插入Timesheet
         size = this.insertTimesheetDTO( timesheetDTOs, timesheetBatchVO );

      }

      logger.info( "Generate Timesheet: " + size + " counts" );

      return size;
   }

   @Override
   public List< TimesheetDTO > getTimesheetDTOsByCondition( TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // 初始化TimesheetDTO List
      final List< TimesheetDTO > timesheetDTOs = new ArrayList< TimesheetDTO >();
      // 初始化TimesheetHeaderVO List
      final List< Object > timesheetHeaderVOs = ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByCondition( timesheetHeaderVO );

      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            // 初始化缓存TimesheetHeaderVO对象
            final TimesheetHeaderVO tempTimesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            // 初始化TimesheetDTO对象
            final TimesheetDTO timesheetDTO = new TimesheetDTO();

            // 装载TimesheetHeaderVO
            timesheetDTO.setTimesheetHeaderVO( ( tempTimesheetHeaderVO ) );

            // 装载TimesheetDetailVO List
            fetchTimesheetDetail( timesheetDTO, tempTimesheetHeaderVO );

            // 装载TimesheetAllowanceVO List
            fetchTimesheetAllowance( timesheetDTO, tempTimesheetHeaderVO );

            // 装载EmployeeContractVO
            timesheetDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( tempTimesheetHeaderVO.getContractId() ) );

            // 装载ClientOrderHeaderVO
            timesheetDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempTimesheetHeaderVO.getOrderId() ) );

            // 装载LeaveVO List
            fetchLeave( timesheetDTO, tempTimesheetHeaderVO.getMonthly() );

            // 装载OTHeaderVO List
            fetchOT( timesheetDTO, tempTimesheetHeaderVO.getMonthly() );

            // 装载EmployeeContractSalaryVO List
            fetchEmployeeContractSalary( timesheetDTO );

            timesheetDTOs.add( timesheetDTO );
         }
      }

      return timesheetDTOs;
   }

   private List< TimesheetDTO > prepareGenerateTimesheetDTOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // 初始化TimesheetDTO List
      final List< TimesheetDTO > timesheetDTOs = new ArrayList< TimesheetDTO >();
      // 初始化EmployeeContract List
      final List< Object > employeeContractVOs = new ArrayList< Object >();
      // 初始化Monthly
      final String monthly = timesheetHeaderVO.getMonthly();

      // 按照条件获取EmployeeContractVO List
      final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
      employeeContractVO.setAccountId( timesheetHeaderVO.getAccountId() );
      employeeContractVO.setEntityId( timesheetHeaderVO.getEntityId() );
      employeeContractVO.setBusinessTypeId( timesheetHeaderVO.getBusinessTypeId() );
      employeeContractVO.setClientId( timesheetHeaderVO.getClientId() );
      employeeContractVO.setCorpId( timesheetHeaderVO.getCorpId() );
      employeeContractVO.setOrderId( timesheetHeaderVO.getOrderId() );
      employeeContractVO.setContractId( timesheetHeaderVO.getContractId() );
      employeeContractVO.setEmployeeId( timesheetHeaderVO.getEmployeeId() );
      employeeContractVO.setMonthly( monthly );
      employeeContractVO.setBufferDate( KANUtil.formatDate( KANUtil.getDate( KANUtil.getFirstDate( monthly ), 0, -3, 0 ), "yyyy-MM-dd" ) );

      if ( timesheetHeaderVO.getCircleStartDay() != null && !timesheetHeaderVO.getCircleStartDay().trim().equals( "" ) )
      {
         employeeContractVO.setCircleStartDay( timesheetHeaderVO.getCircleStartDay() );
      }

      //处理数据权限
      //      employeeContractVO.setHasIn( timesheetHeaderVO.getHasIn() );
      //      employeeContractVO.setNotIn( timesheetHeaderVO.getNotIn() );
      employeeContractVO.setRulePublic( timesheetHeaderVO.getRulePublic() );
      employeeContractVO.setRulePrivateIds( timesheetHeaderVO.getRulePrivateIds() );
      employeeContractVO.setRulePositionIds( timesheetHeaderVO.getRulePositionIds() );
      employeeContractVO.setRuleBranchIds( timesheetHeaderVO.getRuleBranchIds() );
      employeeContractVO.setRuleBusinessTypeIds( timesheetHeaderVO.getRuleBusinessTypeIds() );
      employeeContractVO.setRuleEntityIds( timesheetHeaderVO.getRuleEntityIds() );
      List< Object > tsEmployeeContractVOs = this.getEmployeeContractDao().getTSEmployeeContractVOsByCondition( employeeContractVO );
      // 正常情况及按照计薪开始周期产生Timesheet
      employeeContractVOs.addAll( tsEmployeeContractVOs );

      if ( timesheetHeaderVO.getCircleEndDay() != null && !timesheetHeaderVO.getCircleEndDay().trim().equals( "" ) )
      {
         employeeContractVO.setCircleEndDay( timesheetHeaderVO.getCircleEndDay() );
         employeeContractVO.setMonthly( timesheetHeaderVO.getAdditionalMonthly() );
         // 按照计薪结束周期产生Timesheet
         employeeContractVOs.addAll( tsEmployeeContractVOs );
      }

      // 如果存在EmployeeContractVO List数据，遍历
      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

            // 获取EmployeeVO
            final EmployeeVO employeeVO = this.employeeDao.getEmployeeVOByEmployeeId( tempEmployeeContractVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               tempEmployeeContractVO.setEmployeeNameZH( employeeVO.getNameZH() );
               tempEmployeeContractVO.setEmployeeNameEN( employeeVO.getNameEN() );
            }

            if ( KANUtil.filterEmpty( tempEmployeeContractVO.getMonthly() ) == null )
            {
               // 设置考勤月份
               tempEmployeeContractVO.setMonthly( employeeContractVO.getMonthly() );
               // 初始化TimesheetDTO对象
               final TimesheetDTO timesheetDTO = new TimesheetDTO();

               // 初始化ClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() );

               if ( clientOrderHeaderVO != null )
               {
                  // 装载EmployeeContractVO
                  timesheetDTO.setEmployeeContractVO( tempEmployeeContractVO );

                  // 装载ClientOrderHeaderVO
                  timesheetDTO.setClientOrderHeaderVO( clientOrderHeaderVO );

                  // 装载LeaveVO List
                  fetchLeave( timesheetDTO, employeeContractVO.getMonthly() );

                  // 装载OTHeaderVO List
                  fetchOT( timesheetDTO, employeeContractVO.getMonthly() );

                  // 装载EmployeeContractSalaryVO List
                  fetchEmployeeContractSalary( timesheetDTO );

                  timesheetDTOs.add( timesheetDTO );
               }
            }
         }
      }

      return timesheetDTOs;
   }

   // 装载考勤明细
   private void fetchTimesheetDetail( final TimesheetDTO timesheetDTO, final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // 初始化并装载考勤明细
      final List< Object > timesheetDetailVOs = this.getTimesheetDetailDao().getTimesheetDetailVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

      if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
      {
         for ( Object timesheetDetailVOObject : timesheetDetailVOs )
         {
            timesheetDTO.getTimesheetDetailVOs().add( ( TimesheetDetailVO ) timesheetDetailVOObject );
         }
      }
   }

   // 装载考勤津贴
   private void fetchTimesheetAllowance( final TimesheetDTO timesheetDTO, final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // 初始化并装载考勤津贴
      final List< Object > timesheetAllowanceVOs = this.getTimesheetAllowanceDao().getTimesheetAllowanceVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

      if ( timesheetAllowanceVOs != null && timesheetAllowanceVOs.size() > 0 )
      {
         for ( Object timesheetAllowanceVOObject : timesheetAllowanceVOs )
         {
            timesheetDTO.getTimesheetAllowanceVOs().add( ( TimesheetAllowanceVO ) timesheetAllowanceVOObject );
         }
      }
   }

   // 装载休假
   private void fetchLeave( final TimesheetDTO timesheetDTO, final String monthly ) throws KANException
   {
      // 初始化休假列表
      final List< Object > leaveVOs = new ArrayList< Object >();

      // 初始化StartDate和EndDate
      String startDate = KANUtil.getStartDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleStartDay() );
      String endDate = KANUtil.getEndDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleEndDay() );

      // 初始化TimesheetHeaderVO
      final TimesheetHeaderVO timesheetHeaderVO = timesheetDTO.getTimesheetHeaderVO();
      if ( timesheetHeaderVO != null )
      {
         if ( KANUtil.filterEmpty( timesheetHeaderVO.getStartDate() ) != null )
         {
            startDate = timesheetHeaderVO.getStartDate();
         }
         else if ( KANUtil.filterEmpty( timesheetHeaderVO.getEndDate() ) != null )
         {
            endDate = timesheetHeaderVO.getEndDate();
         }
      }

      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      // 请假未销假情况
      leaveHeaderVO.setAccountId( timesheetDTO.getEmployeeContractVO().getAccountId() );
      leaveHeaderVO.setCorpId( timesheetDTO.getEmployeeContractVO().getCorpId() );
      leaveHeaderVO.setContractId( timesheetDTO.getEmployeeContractVO().getContractId() );
      leaveHeaderVO.setEmployeeId( timesheetDTO.getEmployeeContractVO().getEmployeeId() );
      leaveHeaderVO.setEstimateStartDate( startDate );
      leaveHeaderVO.setEstimateEndDate( endDate );
      leaveHeaderVO.setRetrieveStatus( "1" );
      leaveHeaderVO.setStatus( "3" );
      leaveVOs.addAll( this.getLeaveHeaderDao().getLeaveHeaderVOsByCondition( leaveHeaderVO ) );
      // 请假有销假情况
      leaveHeaderVO.setActualStartDate( startDate );
      leaveHeaderVO.setActualEndDate( endDate );
      leaveHeaderVO.setRetrieveStatus( "3" );
      leaveHeaderVO.setStatus( "3" );
      leaveVOs.addAll( this.getLeaveHeaderDao().getLeaveHeaderVOsByCondition( leaveHeaderVO ) );

      if ( leaveVOs != null && leaveVOs.size() > 0 )
      {
         for ( Object leaveVOObject : leaveVOs )
         {
            // 初始化LeaveHeaderVO
            final LeaveHeaderVO tempLeaveHeaderVO = ( LeaveHeaderVO ) leaveVOObject;

            // 初始化LeaveDTO
            final LeaveDTO leaveDTO = new LeaveDTO();
            leaveDTO.setLeaveHeaderVO( tempLeaveHeaderVO );

            final List< Object > objects = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( tempLeaveHeaderVO.getLeaveHeaderId() );

            if ( objects != null && objects.size() > 0 )
            {
               for ( Object object : objects )
               {
                  final LeaveDetailVO o = ( LeaveDetailVO ) object;
                  if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( o.getEstimateStartDate() ) )
                        || KANUtil.getDays( KANUtil.createDate( endDate ) ) < KANUtil.getDays( KANUtil.createDate( o.getEstimateStartDate() ) ) )
                     continue;
                  leaveDTO.getLeaveDetailVOs().add( ( LeaveDetailVO ) object );
               }
            }

            timesheetDTO.getLeaveDTOs().add( leaveDTO );
         }
      }
   }

   // 装载加班
   private void fetchOT( final TimesheetDTO timesheetDTO, final String monthly ) throws KANException
   {
      // 初始化StartDate和EndDate
      String startDate = KANUtil.getStartDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleStartDay() );
      String endDate = KANUtil.getEndDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleEndDay() );

      // 初始化TimesheetHeaderVO
      final TimesheetHeaderVO timesheetHeaderVO = timesheetDTO.getTimesheetHeaderVO();
      if ( timesheetHeaderVO != null )
      {
         if ( KANUtil.filterEmpty( timesheetHeaderVO.getStartDate() ) != null )
         {
            startDate = timesheetHeaderVO.getStartDate();
         }
         else if ( KANUtil.filterEmpty( timesheetHeaderVO.getEndDate() ) != null )
         {
            endDate = timesheetHeaderVO.getEndDate();
         }
      }

      final OTHeaderVO otHeaderVO = new OTHeaderVO();
      otHeaderVO.setAccountId( timesheetDTO.getEmployeeContractVO().getAccountId() );
      otHeaderVO.setCorpId( timesheetDTO.getEmployeeContractVO().getCorpId() );
      otHeaderVO.setContractId( timesheetDTO.getEmployeeContractVO().getContractId() );
      otHeaderVO.setEmployeeId( timesheetDTO.getEmployeeContractVO().getEmployeeId() );
      otHeaderVO.setActualStartDate( startDate );
      otHeaderVO.setActualEndDate( endDate );
      // 已确认的
      otHeaderVO.setStatus( "5" );
      final List< Object > otHeaderVOs = this.getOtHeaderDao().getOTHeaderVOsByCondition( otHeaderVO );

      if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
      {
         for ( Object otHeaderVOObject : otHeaderVOs )
         {
            // 初始化OTHeaderVO
            final OTHeaderVO tempOTHeaderVO = ( OTHeaderVO ) otHeaderVOObject;

            // 初始化OTDTO
            final OTDTO otDTO = new OTDTO();
            otDTO.setOtHeaderVO( tempOTHeaderVO );

            final List< Object > objects = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( tempOTHeaderVO.getOtHeaderId() );

            if ( objects != null && objects.size() > 0 )
            {
               for ( Object object : objects )
               {
                  final OTDetailVO o = ( OTDetailVO ) object;
                  if ( KANUtil.getDays( KANUtil.createDate( startDate ) ) > KANUtil.getDays( KANUtil.createDate( o.getEstimateStartDate() ) )
                        || KANUtil.getDays( KANUtil.createDate( endDate ) ) < KANUtil.getDays( KANUtil.createDate( o.getEstimateStartDate() ) ) )
                     continue;
                  otDTO.getOtDetailVOs().add( ( OTDetailVO ) object );
               }
            }

            timesheetDTO.getOtDTOs().add( otDTO );
         }
      }
   }

   // 装载服务协议 - 薪资方案
   private void fetchEmployeeContractSalary( final TimesheetDTO timesheetDTO ) throws KANException
   {
      final List< Object > employeeContractSalaryVOs = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractId( timesheetDTO.getEmployeeContractVO().getContractId() );
      if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            timesheetDTO.getEmployeeContractSalaryVOs().add( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject );
         }
      }
   }

   private String getDayType( final String[] dayTypeArray, final String detailId )
   {
      if ( dayTypeArray != null && dayTypeArray.length > 0 && KANUtil.filterEmpty( detailId ) != null )
      {
         for ( String dayTypeStr : dayTypeArray )
         {
            final String[] dayType = dayTypeStr.split( "_" );

            if ( dayType.length == 2 && dayType[ 0 ].equals( detailId ) )
            {
               return dayType[ 1 ];
            }
         }
      }

      return "";
   }

   private double calculationWorkHours( final String[] hoursArray )
   {
      double hours = 0.0;
      if ( hoursArray != null && hoursArray.length > 0 )
      {
         for ( String tempHours : hoursArray )
         {
            if ( tempHours.split( "_" ).length == 2 )
            {
               hours = hours + Double.valueOf( tempHours.split( "_" )[ 1 ] );
            }
         }
      }

      return hours;
   }

   private HistoryVO generateHistoryVO( final TimesheetHeaderVO timesheetHeaderVO )
   {
      final HistoryVO history = timesheetHeaderVO.getHistoryVO();
      history.setAccessAction( TimesheetBatchAction.accessActionInHouse );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( TimesheetBatchAction.accessActionInHouse ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getTimesheetHeaderVOByHeaderId" );
      // 表示是工作流的
      history.setObjectType( "2" );
      history.setAccountId( timesheetHeaderVO.getAccountId() );
      history.setNameZH( timesheetHeaderVO.getEmployeeNameZH() );
      history.setNameEN( timesheetHeaderVO.getEmployeeNameEN() );
      return history;
   }

   @Override
   public PagedListHolder getTimesheetReportExportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged )
   {
      final TimesheetHeaderDao timesheetHeaderDao = ( TimesheetHeaderDao ) getDao();
      pagedListHolder.setHolderSize( timesheetHeaderDao.countTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetHeaderDao.getTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   /**
    * 查询报表的detail信息
    */
   @Override
   public List< Object > getTimesheetDetailVOsForReportByHeaderIds( final Object object ) throws KANException
   {
      TimesheetReportExportVO timesheetHeaderVO = ( TimesheetReportExportVO ) object;
      List< String > headerIds = new ArrayList< String >();
      final String[] selectedIdArray = timesheetHeaderVO.getSelectedIds().split( "," );
      for ( String encodedSelectId : selectedIdArray )
      {
         headerIds.add( encodedSelectId );
      }

      return timesheetDetailDao.getTimesheetDetailVOsForReportByHeaderIds( headerIds );
   }

   /**
    * 查询动态报表头的日期列
    */
   @Override
   public List< Object > getTimesheetDetailDaysForReportByHeaderIds( final Object object ) throws KANException
   {
      TimesheetReportExportVO timesheetHeaderVO = ( TimesheetReportExportVO ) object;
      List< String > headerIds = new ArrayList< String >();
      final String[] selectedIdArray = timesheetHeaderVO.getSelectedIds().split( "," );
      for ( String encodedSelectId : selectedIdArray )
      {
         headerIds.add( encodedSelectId );
      }
      return timesheetDetailDao.getTimesheetDetailDaysForReportByHeaderIds( headerIds );
   }

   /**
    * 格式化excel
    * 
    */
   @Override
   public XSSFWorkbook timeSheetReport( final List< Object > listTimesheetDatys, final List< Object > listTimesheetDetailVOs, final PagedListHolder timesheetRpoertExportHolder,
         TimesheetReportExportVO timesheetReportExportVO ) throws KANException
   {
      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      // 创建字体
      final XSSFFont font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont( font );
      cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

      // 创建单元格样式
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont( font );
      cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

      // 创建单元格样式
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont( font );
      cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

      // 创建单元格样式(红色)
      final XSSFFont fontRed = workbook.createFont();
      fontRed.setFontName( "Calibri" );
      fontRed.setFontHeightInPoints( ( short ) 11 );
      fontRed.setColor( Font.COLOR_RED );
      final XSSFCellStyle cellStyleCenterRed = workbook.createCellStyle();
      cellStyleCenterRed.setFont( fontRed );
      cellStyleCenterRed.setAlignment( CellStyle.ALIGN_CENTER );

      // 创建表格
      final XSSFSheet sheet = workbook.createSheet();
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth( 15 );

      // 生成Excel Header Row
      final XSSFRow rowHeaderOne = sheet.createRow( 0 );
      final XSSFRow rowHeadertwo = sheet.createRow( 1 );

      // 用以标识Header列序号
      int headerColumnIndex = 0;
      int count = 0;

      try
      {
         List< MappingVO > attendanceItemMappingVOs = null;
         if ( timesheetRpoertExportHolder != null && timesheetRpoertExportHolder.getSource() != null && timesheetRpoertExportHolder.getSource().size() > 0 )
         {
            attendanceItemMappingVOs = ( ( TimesheetReportExportVO ) timesheetRpoertExportHolder.getSource().get( 0 ) ).getItems();
         }
         // 遍历Excel Header
         for ( String tabTitle : timesheetReportExportVO.getTitleNameList().split( "," ) )
         {
            count++;
            if ( count == 11 )
            {
               for ( int i = 0; i < listTimesheetDatys.size(); i++ )
               {
                  final TimesheetDetailVO timesheetDetailVO = ( TimesheetDetailVO ) listTimesheetDatys.get( i );
                  final XSSFCell cellOne = rowHeaderOne.createCell( headerColumnIndex );
                  final XSSFCell cellTwo = rowHeadertwo.createCell( headerColumnIndex );

                  cellOne.setCellValue( timesheetDetailVO.getDay() );
                  if ( timesheetDetailVO.getDayType().equals( "1" ) )
                  {
                     cellOne.setCellStyle( cellStyleCenter );
                     cellTwo.setCellStyle( cellStyleCenter );
                  }
                  else
                  {
                     cellOne.setCellStyle( cellStyleCenterRed );
                     cellTwo.setCellStyle( cellStyleCenterRed );
                  }

                  if ( timesheetDetailVO.getDayType().equals( "3" ) )
                  {
                     cellTwo.setCellValue( "法定节假日" );
                  }
                  else
                  {
                     cellTwo.setCellValue( getDayOfWeek( timesheetDetailVO.getDay(), timesheetReportExportVO.getWeekDaysName() ) );
                  }

                  headerColumnIndex++;
               }

               if ( attendanceItemMappingVOs != null && attendanceItemMappingVOs.size() > 0 )
               {
                  for ( MappingVO mappingVO : attendanceItemMappingVOs )
                  {
                     final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
                     cell.setCellValue( mappingVO.getMappingId() );
                     cell.setCellStyle( cellStyleLeft );
                     sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
                     headerColumnIndex++;
                  }
               }
            }

            final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
            cell.setCellValue( tabTitle );
            cell.setCellStyle( cellStyleLeft );
            sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
            headerColumnIndex++;
         }

         count = 0;
         // 遍历生成Excel Body
         if ( timesheetRpoertExportHolder.getSource() != null && timesheetRpoertExportHolder.getSource().size() > 0 )
         {
            // 用以标识Body行序号
            int bodyRowIndex = 2;
            // 遍历行
            for ( Object object : timesheetRpoertExportHolder.getSource() )
            {
               final TimesheetReportExportVO tempTimesheetReportExportVO = ( TimesheetReportExportVO ) object;
               // 生成Excel Body Row
               final XSSFRow rowBody = sheet.createRow( bodyRowIndex );
               // 用以标识Body列序号
               int bodyColumnIndex = 0;

               for ( String tabTitleId : timesheetReportExportVO.getTitleIdList().split( "," ) )
               {
                  count++;
                  if ( count == 11 )
                  {
                     for ( int j = 0; j < listTimesheetDatys.size(); j++ )
                     {
                        final TimesheetDetailVO timesheetDetailDays = ( TimesheetDetailVO ) listTimesheetDatys.get( j );
                        for ( int k = 0; k < listTimesheetDetailVOs.size(); k++ )
                        {
                           final TimesheetDetailVO timesheetDetailVO = ( TimesheetDetailVO ) listTimesheetDetailVOs.get( k );
                           if ( timesheetDetailVO.getHeaderId().equals( tempTimesheetReportExportVO.getHeaderId() )
                                 && timesheetDetailDays.getDay().equals( timesheetDetailVO.getDay() ) )
                           {
                              // 填入当天工作小时数
                              final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                              cell.setCellValue( timesheetDetailVO.getDay() );
                              if ( timesheetDetailVO.getDayType().equals( "1" ) )
                                 cell.setCellStyle( cellStyleCenter );
                              else
                                 cell.setCellStyle( cellStyleCenterRed );
                              cell.setCellValue( timesheetDetailVO.getWorkHours() );
                              continue;
                           }
                        }
                        bodyColumnIndex++;
                     }

                     for ( MappingVO mappingVO : tempTimesheetReportExportVO.getItems() )
                     {
                        rowBody.createCell( bodyColumnIndex ).setCellValue( mappingVO.getMappingValue() );
                        bodyColumnIndex++;
                     }
                  }

                  rowBody.createCell( bodyColumnIndex ).setCellValue( KANUtil.getFilterEmptyValue( tempTimesheetReportExportVO, tabTitleId ) );
                  bodyColumnIndex++;
               }

               bodyRowIndex++;
               count = 0;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return workbook;
   }

   /** 
    * 根据日期获得星期 
    * @param date 
    * @return 
    */
   private String getDayOfWeek( final String date, final String[] weekDaysName )
   {
      final Date tempDate = KANUtil.createDate( KANUtil.formatDate( date, "yyyy-MM-dd", true ) );
      // 初始化Calendar
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime( tempDate );
      // 初始化
      int dayOfWeek = calendar.get( Calendar.DAY_OF_WEEK ) - 1;
      return weekDaysName[ dayOfWeek ];
   }

   @Override
   public TimesheetHeaderVO getTotalFullHoursAndWorkHoursOfYear( final String employeeId ) throws KANException
   {
      final TimesheetHeaderDao timesheetHeaderDao = ( TimesheetHeaderDao ) getDao();
      final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
      String yearFirstDay = KANUtil.formatDate( KANUtil.getFirstDate( KANUtil.getYear( new Date() ) + "01" ), "yyyy-MM-dd HH:mm:ss" );
      String yearLastDay = KANUtil.formatDate( KANUtil.getLastDate( KANUtil.getYear( new Date() ) + "12" ), "yyyy-MM-dd HH:mm:ss" );
      timesheetHeaderVO.setEmployeeId( employeeId );
      timesheetHeaderVO.setStartDate( yearFirstDay );
      timesheetHeaderVO.setEndDate( yearLastDay );
      return timesheetHeaderDao.getTotalFullHoursAndWorkHoursOfYear( timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByCondition( timesheetHeaderVO );
   }

   @Override
   public PagedListHolder getAVGTimesheetReportExportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final TimesheetHeaderDao timesheetHeaderDao = ( TimesheetHeaderDao ) getDao();
      pagedListHolder.setHolderSize( timesheetHeaderDao.countAVGTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetHeaderDao.getAVGTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetHeaderDao.getAVGTimesheetReportExportVOsByCondition( ( TimesheetReportExportVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }
}
