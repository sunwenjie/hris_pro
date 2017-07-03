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
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�TimesheetHeaderServiceImpl  
*   
*/
public class TimesheetHeaderServiceImpl extends ContextService implements TimesheetHeaderService
{
   // ��������
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.TimesheetHeaderVO";

   // Service Name
   public static final String SERVICE_BEAN = "timesheetHeaderService";

   // ע��TimesheetDetailDao
   private TimesheetDetailDao timesheetDetailDao;

   // ע��TimesheetAllowanceDao
   private TimesheetAllowanceDao timesheetAllowanceDao;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   // ע��EmployeeContractDao
   private EmployeeContractDao employeeContractDao;

   // ע��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��EmployeeContractSalaryDao
   private EmployeeContractSalaryDao employeeContractSalaryDao;

   // ע��WorkflowService
   private WorkflowService workflowService;

   // ע��LeaveHeaderDao
   private LeaveHeaderDao leaveHeaderDao;

   // ע��LeaveDetailDao
   private LeaveDetailDao leaveDetailDao;

   // ע��OTHeaderDao
   private OTHeaderDao otHeaderDao;

   // ע��OTDetailDao
   private OTDetailDao otDetailDao;

   // ע��ShiftHeaderDao
   private ShiftHeaderDao shiftHeaderDao;

   // ע��TimesheetBatchDao
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
            // ��������
            this.startTransaction();

            // Ա������ͳ��
            final StringBuffer employeeNameList = new StringBuffer();

            // ֻͳ��50��Ա������
            int limit = 50;

            for ( TimesheetDTO timesheetDTO : timesheetDTOs )
            {
               if ( timesheetDTO.getTimesheetHeaderVO() != null
                     && ( ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) ) > 0 )
                           || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() ) ) > 0 )
                           || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays() ) ) > 0 ) || ( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() ) != null && Double.valueOf( KANUtil.filterEmpty( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() ) ) > 0 ) ) )
               {
                  limit--;

                  // ��������
                  if ( timesheetBatchVO != null && size == 0 )
                  {
                     timesheetBatchVO.setDescription( employeeNameList.toString() );
                     // ����ִ�н���ʱ��
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
                        employeeNameList.append( "��" + timesheetDTO.getTimesheetHeaderVO().getEmployeeNameZH() );
                     }
                  }

                  if ( timesheetBatchVO != null )
                  {
                     timesheetDTO.getTimesheetHeaderVO().setBatchId( timesheetBatchVO.getBatchId() );
                  }
                  // ����Timesheet Header
                  ( ( TimesheetHeaderDao ) getDao() ).insertTimesheetHeader( timesheetDTO.getTimesheetHeaderVO() );

                  // ����Timesheet Detail
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

                  // ����Timesheet Allowance
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

                  // LeaveDetailVO����timesheetId
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

                  // OTDetailVO����timesheetId
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

            // �ύ����
            this.commitTransaction();

            // �޸�����
            if ( timesheetBatchVO != null )
            {
               final TimesheetBatchVO tempTimesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetBatchVO.getBatchId() );
               tempTimesheetBatchVO.setDescription( employeeNameList.toString() );
               this.getTimesheetBatchDao().updateTimesheetBatch( tempTimesheetBatchVO );
            }
         }
         catch ( final Exception e )
         {
            // �ع�����
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
         // ��������
         startTransaction();

         updateTimesheetHeader_nt( timesheetHeaderVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
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

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( timesheetHeaderVO.getContractId() );

         // ��ȡClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO != null ? employeeContractVO.getOrderId()
               : "0" );

         // ��ȡshiftId
         String shiftId = employeeContractVO != null ? employeeContractVO.getShiftId() : "0";
         if ( KANUtil.filterEmpty( shiftId, "0" ) == null && clientOrderHeaderVO != null )
         {
            shiftId = clientOrderHeaderVO.getShiftId();
         }

         if ( timesheetHeaderVO.getWorkHoursArray() != null && timesheetHeaderVO.getWorkHoursArray().length > 0 )
         {
            // ������������Сʱ����Ϊ�ջ���Ϊ0�����ֵ���մӱ���Сʱ������
            if ( KANUtil.filterEmpty( timesheetHeaderVO.getTotalWorkHours(), "0" ) == null )
            {
               timesheetHeaderVO.setTotalWorkHours( String.valueOf( calculationWorkHours( timesheetHeaderVO.getWorkHoursArray() ) ) );
            }

            // ���� 
            for ( String workHoursString : timesheetHeaderVO.getWorkHoursArray() )
            {
               final String[] workHours = workHoursString.split( "_" );

               if ( workHours != null && workHours.length == 2 )
               {
                  String dayType = getDayType( timesheetHeaderVO.getDayTypeArray(), workHours[ 0 ] );

                  // ���TimesheetDetailVO
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

               // ���TimesheetAllowanceVO
               final TimesheetAllowanceVO timesheetAllowanceVO = this.timesheetAllowanceDao.getTimesheetAllowanceVOByAllowanceId( base[ 0 ] );
               timesheetAllowanceVO.setBase( base.length == 2 ? base[ 1 ] : "0.0" );
               timesheetAllowanceVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
               timesheetAllowanceVO.setModifyDate( timesheetHeaderVO.getModifyDate() );
               this.timesheetAllowanceDao.updateTimesheetAllowance( timesheetAllowanceVO );
            }
         }

         // �ܹ���Сʱ
         double totalWorkHours = calculationWorkHours( timesheetHeaderVO.getWorkHoursArray() );

         // ȫ��Сʱ��
         double totalFullHours = Double.valueOf( timesheetHeaderVO.getTotalFullHours() );

         // ȫ������
         double totalFullDays = Double.valueOf( timesheetHeaderVO.getTotalFullDays() );

         // �ܹ�������
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

         // �Ƿ���������
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
      // �Ƿ�����������Ķ���
      if ( !WorkflowService.isPassObject( timesheetHeaderVO ) )
      {
         // ����historyVO
         final HistoryVO historyVO = generateHistoryVO( timesheetHeaderVO );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetHeaderVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( timesheetHeaderVO.getStatus() != null && !timesheetHeaderVO.getStatus().trim().equals( "3" ) )
            {
               // �����
               submit( timesheetHeaderVO, "2" );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submit_header" );
            historyVO.setObjectId( timesheetHeaderVO.getHeaderId() );

            timesheetHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            // �˻�״̬
            timesheetHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetHeaderVO );

            return -1;
         }
         // û�й�����
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
         // ��������
         this.startTransaction();

         // ��ʼ��TimesheetHeaderVO�б�
         final List< Object > timesheetHeaderVOs = new ArrayList< Object >();
         // ��ʶ�Ƿ��޸�����
         boolean updated = false;
         // ���ο��ڱ����
         int originalSize = 0;
         // �������ύ���ڱ����
         int submitedSize = 0;

         // ���ĸ�����Header״̬
         timesheetHeaderVO.setStatus( status );
         ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );

         // ��ʼ��TimesheetHeaderVO
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

         // ��������״̬
         if ( updated )
         {
            final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( tempTimesheetHeaderVO.getBatchId() );
            timesheetBatchVO.setStatus( status );
            timesheetBatchVO.setModifyBy( timesheetHeaderVO.getModifyBy() );
            timesheetBatchVO.setModifyDate( new Date() );
            this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
         }

         // �������׼�����˻أ�����TimesheetBatchVO��Description�ֶ�
         if ( KANUtil.filterEmpty( status ) != null && status.equals( "6" ) )
         {
            // ��ʼ��StringBuffer
            final StringBuffer employeeNames = new StringBuffer();

            tempTimesheetHeaderVO.setStatus( "1,2,3,4,5" );
            timesheetHeaderVOs.addAll( ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );

            int index = 0;
            if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
            {
               for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
               {
                  // ��ȡEmployeeVO
                  final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( ( ( TimesheetHeaderVO ) timesheetHeaderVOObject ).getEmployeeId() );

                  if ( employeeVO != null )
                  {
                     if ( index == 0 )
                        employeeNames.append( employeeVO.getNameZH() );
                     else
                        employeeNames.append( "��" + employeeVO.getNameZH() );
                  }

                  index++;

                  if ( index > 500 )
                  {
                     break;
                  }
               }
            }

            final TimesheetBatchVO tempTimesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );
            // �޸���������
            tempTimesheetBatchVO.setDescription( employeeNames.toString() );
            this.getTimesheetBatchDao().updateTimesheetBatch( tempTimesheetBatchVO );
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
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

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetHeaderVO );
         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( timesheetHeaderVO.getStatus() != null && !timesheetHeaderVO.getStatus().trim().equals( "3" ) )
            {
               // ״̬��Ϊ�����
               timesheetHeaderVO.setStatus( "2" );
               ( ( TimesheetHeaderDao ) getDao() ).updateTimesheetHeader( timesheetHeaderVO );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submitTimesheetHeader" );
            historyVO.setObjectId( timesheetHeaderVO.getHeaderId() );

            // ��׼״̬
            timesheetHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            // �˻�״̬
            timesheetHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetHeaderVO );

            return -1;
         }
         // û�й�����
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
         // ��ʼ����
         startTransaction();

         // ��ȡTimesheetDetailVO�б�
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

         // ��ȡTimesheetAllowanceVO�б�
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

         // ��ȡ��ǰ���������TimesheetHeaderVO
         final List< Object > timesheetHeaderVOs = ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByBatchId( timesheetHeaderVO.getBatchId() );

         // ��ȡTimesheetBatchVO
         final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

         // ����������²����ڿ��ڱ�����ɾ��������
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

               final String[] employeeNameArray = timesheetBatchVO.getDescription().split( "��" );

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
                           rs.append( "��" + employeeName );
                        }
                     }
                  }
               }

               timesheetBatchVO.setDescription( rs.toString() );

               this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // �ع�����
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

      // ��ʼ��TimesheetHeaderVO - ��������
      TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();

      String userId = "";

      if ( timesheetHeaderVO != null )
      {
         tempTimesheetHeaderVO = timesheetHeaderVO;
         userId = timesheetHeaderVO.getCreateBy();
      }
      else
      {
         // ��ʼ��Calendar
         final Calendar calendar = KANUtil.getCalendar( new Date() );

         // ������������Timesheet��Ӧ�ķ���Э��Ŀ�ʼ��н���ں�Monthly
         tempTimesheetHeaderVO.setMonthly( KANUtil.getMonthly( calendar.getTime() ) );
         tempTimesheetHeaderVO.setCircleStartDay( String.valueOf( calendar.get( Calendar.DATE ) ) );

         // Ĭ�϶�ʱ������ÿ��00:00�Ժ�ִ�еģ��������ڼ���1��
         calendar.add( Calendar.DATE, -1 );
         // ������������Timesheet��Ӧ�ķ���Э��Ľ�����н���ں�Monthly
         tempTimesheetHeaderVO.setAdditionalMonthly( KANUtil.getMonthly( calendar.getTime() ) );
         // ͨ�����»����á�1-31��������31��������������óɡ�31��
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

      //��������Ȩ��
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

      // ����������������Э��
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

         // ��������Timesheet
         size = this.insertTimesheetDTO( timesheetDTOs, timesheetBatchVO );

      }

      logger.info( "Generate Timesheet: " + size + " counts" );

      return size;
   }

   @Override
   public List< TimesheetDTO > getTimesheetDTOsByCondition( TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // ��ʼ��TimesheetDTO List
      final List< TimesheetDTO > timesheetDTOs = new ArrayList< TimesheetDTO >();
      // ��ʼ��TimesheetHeaderVO List
      final List< Object > timesheetHeaderVOs = ( ( TimesheetHeaderDao ) getDao() ).getTimesheetHeaderVOsByCondition( timesheetHeaderVO );

      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            // ��ʼ������TimesheetHeaderVO����
            final TimesheetHeaderVO tempTimesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            // ��ʼ��TimesheetDTO����
            final TimesheetDTO timesheetDTO = new TimesheetDTO();

            // װ��TimesheetHeaderVO
            timesheetDTO.setTimesheetHeaderVO( ( tempTimesheetHeaderVO ) );

            // װ��TimesheetDetailVO List
            fetchTimesheetDetail( timesheetDTO, tempTimesheetHeaderVO );

            // װ��TimesheetAllowanceVO List
            fetchTimesheetAllowance( timesheetDTO, tempTimesheetHeaderVO );

            // װ��EmployeeContractVO
            timesheetDTO.setEmployeeContractVO( this.getEmployeeContractDao().getEmployeeContractVOByContractId( tempTimesheetHeaderVO.getContractId() ) );

            // װ��ClientOrderHeaderVO
            timesheetDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempTimesheetHeaderVO.getOrderId() ) );

            // װ��LeaveVO List
            fetchLeave( timesheetDTO, tempTimesheetHeaderVO.getMonthly() );

            // װ��OTHeaderVO List
            fetchOT( timesheetDTO, tempTimesheetHeaderVO.getMonthly() );

            // װ��EmployeeContractSalaryVO List
            fetchEmployeeContractSalary( timesheetDTO );

            timesheetDTOs.add( timesheetDTO );
         }
      }

      return timesheetDTOs;
   }

   private List< TimesheetDTO > prepareGenerateTimesheetDTOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // ��ʼ��TimesheetDTO List
      final List< TimesheetDTO > timesheetDTOs = new ArrayList< TimesheetDTO >();
      // ��ʼ��EmployeeContract List
      final List< Object > employeeContractVOs = new ArrayList< Object >();
      // ��ʼ��Monthly
      final String monthly = timesheetHeaderVO.getMonthly();

      // ����������ȡEmployeeContractVO List
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

      //��������Ȩ��
      //      employeeContractVO.setHasIn( timesheetHeaderVO.getHasIn() );
      //      employeeContractVO.setNotIn( timesheetHeaderVO.getNotIn() );
      employeeContractVO.setRulePublic( timesheetHeaderVO.getRulePublic() );
      employeeContractVO.setRulePrivateIds( timesheetHeaderVO.getRulePrivateIds() );
      employeeContractVO.setRulePositionIds( timesheetHeaderVO.getRulePositionIds() );
      employeeContractVO.setRuleBranchIds( timesheetHeaderVO.getRuleBranchIds() );
      employeeContractVO.setRuleBusinessTypeIds( timesheetHeaderVO.getRuleBusinessTypeIds() );
      employeeContractVO.setRuleEntityIds( timesheetHeaderVO.getRuleEntityIds() );
      List< Object > tsEmployeeContractVOs = this.getEmployeeContractDao().getTSEmployeeContractVOsByCondition( employeeContractVO );
      // ������������ռ�н��ʼ���ڲ���Timesheet
      employeeContractVOs.addAll( tsEmployeeContractVOs );

      if ( timesheetHeaderVO.getCircleEndDay() != null && !timesheetHeaderVO.getCircleEndDay().trim().equals( "" ) )
      {
         employeeContractVO.setCircleEndDay( timesheetHeaderVO.getCircleEndDay() );
         employeeContractVO.setMonthly( timesheetHeaderVO.getAdditionalMonthly() );
         // ���ռ�н�������ڲ���Timesheet
         employeeContractVOs.addAll( tsEmployeeContractVOs );
      }

      // �������EmployeeContractVO List���ݣ�����
      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

            // ��ȡEmployeeVO
            final EmployeeVO employeeVO = this.employeeDao.getEmployeeVOByEmployeeId( tempEmployeeContractVO.getEmployeeId() );

            if ( employeeVO != null )
            {
               tempEmployeeContractVO.setEmployeeNameZH( employeeVO.getNameZH() );
               tempEmployeeContractVO.setEmployeeNameEN( employeeVO.getNameEN() );
            }

            if ( KANUtil.filterEmpty( tempEmployeeContractVO.getMonthly() ) == null )
            {
               // ���ÿ����·�
               tempEmployeeContractVO.setMonthly( employeeContractVO.getMonthly() );
               // ��ʼ��TimesheetDTO����
               final TimesheetDTO timesheetDTO = new TimesheetDTO();

               // ��ʼ��ClientOrderHeaderVO
               final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() );

               if ( clientOrderHeaderVO != null )
               {
                  // װ��EmployeeContractVO
                  timesheetDTO.setEmployeeContractVO( tempEmployeeContractVO );

                  // װ��ClientOrderHeaderVO
                  timesheetDTO.setClientOrderHeaderVO( clientOrderHeaderVO );

                  // װ��LeaveVO List
                  fetchLeave( timesheetDTO, employeeContractVO.getMonthly() );

                  // װ��OTHeaderVO List
                  fetchOT( timesheetDTO, employeeContractVO.getMonthly() );

                  // װ��EmployeeContractSalaryVO List
                  fetchEmployeeContractSalary( timesheetDTO );

                  timesheetDTOs.add( timesheetDTO );
               }
            }
         }
      }

      return timesheetDTOs;
   }

   // װ�ؿ�����ϸ
   private void fetchTimesheetDetail( final TimesheetDTO timesheetDTO, final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // ��ʼ����װ�ؿ�����ϸ
      final List< Object > timesheetDetailVOs = this.getTimesheetDetailDao().getTimesheetDetailVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

      if ( timesheetDetailVOs != null && timesheetDetailVOs.size() > 0 )
      {
         for ( Object timesheetDetailVOObject : timesheetDetailVOs )
         {
            timesheetDTO.getTimesheetDetailVOs().add( ( TimesheetDetailVO ) timesheetDetailVOObject );
         }
      }
   }

   // װ�ؿ��ڽ���
   private void fetchTimesheetAllowance( final TimesheetDTO timesheetDTO, final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      // ��ʼ����װ�ؿ��ڽ���
      final List< Object > timesheetAllowanceVOs = this.getTimesheetAllowanceDao().getTimesheetAllowanceVOsByHeaderId( timesheetHeaderVO.getHeaderId() );

      if ( timesheetAllowanceVOs != null && timesheetAllowanceVOs.size() > 0 )
      {
         for ( Object timesheetAllowanceVOObject : timesheetAllowanceVOs )
         {
            timesheetDTO.getTimesheetAllowanceVOs().add( ( TimesheetAllowanceVO ) timesheetAllowanceVOObject );
         }
      }
   }

   // װ���ݼ�
   private void fetchLeave( final TimesheetDTO timesheetDTO, final String monthly ) throws KANException
   {
      // ��ʼ���ݼ��б�
      final List< Object > leaveVOs = new ArrayList< Object >();

      // ��ʼ��StartDate��EndDate
      String startDate = KANUtil.getStartDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleStartDay() );
      String endDate = KANUtil.getEndDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleEndDay() );

      // ��ʼ��TimesheetHeaderVO
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
      // ���δ�������
      leaveHeaderVO.setAccountId( timesheetDTO.getEmployeeContractVO().getAccountId() );
      leaveHeaderVO.setCorpId( timesheetDTO.getEmployeeContractVO().getCorpId() );
      leaveHeaderVO.setContractId( timesheetDTO.getEmployeeContractVO().getContractId() );
      leaveHeaderVO.setEmployeeId( timesheetDTO.getEmployeeContractVO().getEmployeeId() );
      leaveHeaderVO.setEstimateStartDate( startDate );
      leaveHeaderVO.setEstimateEndDate( endDate );
      leaveHeaderVO.setRetrieveStatus( "1" );
      leaveHeaderVO.setStatus( "3" );
      leaveVOs.addAll( this.getLeaveHeaderDao().getLeaveHeaderVOsByCondition( leaveHeaderVO ) );
      // ������������
      leaveHeaderVO.setActualStartDate( startDate );
      leaveHeaderVO.setActualEndDate( endDate );
      leaveHeaderVO.setRetrieveStatus( "3" );
      leaveHeaderVO.setStatus( "3" );
      leaveVOs.addAll( this.getLeaveHeaderDao().getLeaveHeaderVOsByCondition( leaveHeaderVO ) );

      if ( leaveVOs != null && leaveVOs.size() > 0 )
      {
         for ( Object leaveVOObject : leaveVOs )
         {
            // ��ʼ��LeaveHeaderVO
            final LeaveHeaderVO tempLeaveHeaderVO = ( LeaveHeaderVO ) leaveVOObject;

            // ��ʼ��LeaveDTO
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

   // װ�ؼӰ�
   private void fetchOT( final TimesheetDTO timesheetDTO, final String monthly ) throws KANException
   {
      // ��ʼ��StartDate��EndDate
      String startDate = KANUtil.getStartDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleStartDay() );
      String endDate = KANUtil.getEndDate( monthly, timesheetDTO.getClientOrderHeaderVO().getCircleEndDay() );

      // ��ʼ��TimesheetHeaderVO
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
      // ��ȷ�ϵ�
      otHeaderVO.setStatus( "5" );
      final List< Object > otHeaderVOs = this.getOtHeaderDao().getOTHeaderVOsByCondition( otHeaderVO );

      if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
      {
         for ( Object otHeaderVOObject : otHeaderVOs )
         {
            // ��ʼ��OTHeaderVO
            final OTHeaderVO tempOTHeaderVO = ( OTHeaderVO ) otHeaderVOObject;

            // ��ʼ��OTDTO
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

   // װ�ط���Э�� - н�ʷ���
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
      // ��ʾ�ǹ�������
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
    * ��ѯ�����detail��Ϣ
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
    * ��ѯ��̬����ͷ��������
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
    * ��ʽ��excel
    * 
    */
   @Override
   public XSSFWorkbook timeSheetReport( final List< Object > listTimesheetDatys, final List< Object > listTimesheetDetailVOs, final PagedListHolder timesheetRpoertExportHolder,
         TimesheetReportExportVO timesheetReportExportVO ) throws KANException
   {
      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      // ��������
      final XSSFFont font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // ������Ԫ����ʽ
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont( font );
      cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

      // ������Ԫ����ʽ
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont( font );
      cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

      // ������Ԫ����ʽ
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont( font );
      cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

      // ������Ԫ����ʽ(��ɫ)
      final XSSFFont fontRed = workbook.createFont();
      fontRed.setFontName( "Calibri" );
      fontRed.setFontHeightInPoints( ( short ) 11 );
      fontRed.setColor( Font.COLOR_RED );
      final XSSFCellStyle cellStyleCenterRed = workbook.createCellStyle();
      cellStyleCenterRed.setFont( fontRed );
      cellStyleCenterRed.setAlignment( CellStyle.ALIGN_CENTER );

      // �������
      final XSSFSheet sheet = workbook.createSheet();
      // ���ñ��Ĭ���п��Ϊ15���ֽ�
      sheet.setDefaultColumnWidth( 15 );

      // ����Excel Header Row
      final XSSFRow rowHeaderOne = sheet.createRow( 0 );
      final XSSFRow rowHeadertwo = sheet.createRow( 1 );

      // ���Ա�ʶHeader�����
      int headerColumnIndex = 0;
      int count = 0;

      try
      {
         List< MappingVO > attendanceItemMappingVOs = null;
         if ( timesheetRpoertExportHolder != null && timesheetRpoertExportHolder.getSource() != null && timesheetRpoertExportHolder.getSource().size() > 0 )
         {
            attendanceItemMappingVOs = ( ( TimesheetReportExportVO ) timesheetRpoertExportHolder.getSource().get( 0 ) ).getItems();
         }
         // ����Excel Header
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
                     cellTwo.setCellValue( "�����ڼ���" );
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
         // ��������Excel Body
         if ( timesheetRpoertExportHolder.getSource() != null && timesheetRpoertExportHolder.getSource().size() > 0 )
         {
            // ���Ա�ʶBody�����
            int bodyRowIndex = 2;
            // ������
            for ( Object object : timesheetRpoertExportHolder.getSource() )
            {
               final TimesheetReportExportVO tempTimesheetReportExportVO = ( TimesheetReportExportVO ) object;
               // ����Excel Body Row
               final XSSFRow rowBody = sheet.createRow( bodyRowIndex );
               // ���Ա�ʶBody�����
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
                              // ���뵱�칤��Сʱ��
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
    * �������ڻ������ 
    * @param date 
    * @return 
    */
   private String getDayOfWeek( final String date, final String[] weekDaysName )
   {
      final Date tempDate = KANUtil.createDate( KANUtil.formatDate( date, "yyyy-MM-dd", true ) );
      // ��ʼ��Calendar
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime( tempDate );
      // ��ʼ��
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
