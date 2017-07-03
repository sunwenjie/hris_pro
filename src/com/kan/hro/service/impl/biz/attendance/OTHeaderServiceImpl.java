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

   // ע��Ӱ�ӱ� dao
   private OTDetailDao otDetailDao;

   // ע�� ��Ա - ����Э��DAO
   private EmployeeContractDao employeeContractDao;

   // ע���Ա - �Ӱ෽��DAO
   private EmployeeContractOTDao employeeContractOTDao;

   // ע�� �ͻ� - ���񶩵�Service
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��ͻ� - �Ӱ෽��DAO
   private ClientOrderOTDao clientOrderOTDao;

   // ������ workflowService
   private WorkflowService workflowService;

   // ע�뿼�ڱ�Service
   private TimesheetHeaderService timesheetHeaderService;

   // ע�뿼�ڱ�Detail Dao
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

         // ��������
         startTransaction();

         // ��üӰ࿪ʼ������ʱ��                       vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    v
         if ( KANUtil.filterEmpty( otHeaderVO.getEstimateStartDate() ) == null || KANUtil.filterEmpty( otHeaderVO.getEstimateEndDate() ) == null )
         {
            return rows;
         }

         // ״̬��ʼ��Ĭ��Ϊ���½���
         otHeaderVO.setStatus( "1" );
         // ɾ����ʾ��ʼ��Ĭ��Ϊ��δɾ����
         otHeaderVO.setDeleted( "1" );
         // ��ʼ��itemId
         otHeaderVO.setItemId( "0" );
         // ����OTHeaderVO
         ( ( OTHeaderDao ) getDao() ).insertOTHeader( otHeaderVO );

         rows++;

         // ����OTDetailVO���������б�
         rows = rows + addOTDetails( otHeaderVO );

         // ����Ӱ�Сʱ���飨������ҳ���õ���
         fetchOTHoursDetail( otHeaderVO );

         // ������ύ���߹�����
         if ( KANUtil.filterEmpty( otHeaderVO.getSubAction() ) != null && KANUtil.filterEmpty( otHeaderVO.getSubAction() ).equalsIgnoreCase( BaseAction.SUBMIT_OBJECT ) )
         {
            submitOTHeader_nt( otHeaderVO );
         }

         // �ύ����
         this.commitTransaction();

         return rows;
      }
      catch ( final Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }

   // ���ز���OTDetailVO
   private int addOTDetails( final OTHeaderVO otHeaderVO ) throws KANException
   {
      int rows = 0;

      // ��ȡEmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

      // ��ȡClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

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

      // ����Ӱ��������룬���üӰ�ʵ��ʱ��
      if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst ).equals( "2" ) )
      {
         otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
         otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
         otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );
      }

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
         // ��������
         this.startTransaction();

         int rows = updateOTHeader_nt( otHeaderVO );

         // �ύ����
         this.commitTransaction();

         return rows;
      }
      catch ( final Exception e )
      {
         // �ع�����
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

         // �޸�OTHeaderVO
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         rows++;

         // ��ȡOTDetailVO�б�
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

         String timesheetId = "";
         // ����OTDetailVO�б���������ɾ��
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetailVOObject : otDetailVOs )
            {
               timesheetId = ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId();
               otHeaderVO.setTimesheetId( ( ( OTDetailVO ) otDetailVOObject ).getTimesheetId() );
               this.getOtDetailDao().deleteOTDetail( ( OTDetailVO ) otDetailVOObject );
            }
         }

         // ��Ӷ���OTDetailVO
         rows = rows + addOTDetails( otHeaderVO );

         // ״̬Ϊȷ�ϵ�
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

         // ���ɾ��OTHeaderVO
         otHeaderVO.setDeleted( OTHeaderVO.FALSE );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         // ��ȡOTDetailVO�б�
         final List< Object > otDetailVOs = this.otDetailDao.getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

         // �������ɾ��OTDetailVO
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
         // ��������
         startTransaction();

         rows = deleteOTHeader_nt( otHeaderVO );

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
         // ��ȡOTDetailVO�б�
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderId );

         String timesheetId = "";
         // ����OTDetailVO
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            for ( Object otDetalVOObject : otDetailVOs )
            {
               // ����ˡ�ȷ��ʱ�޸�ActualHours Modify by siuvan @2014-08-11
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

         // ״̬Ϊȷ�ϵ�
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
         // ��������
         this.startTransaction();

         submitOTHeader_nt( otHeaderVO );

         // �ύ����
         this.commitTransaction();
         return -1;
      }
      catch ( final Exception e )
      {
         // �ع�����
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

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );
         // ��ȡHistoryVO
         final HistoryVO historyVO = generateHistoryVO( otHeaderVO, employeeContractVO.getOwner() );

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( otHeaderVO );

         // ��ȡClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // ��ȡ�Ӱ���Ҫ����
         String applyOTFirst = employeeContractVO.getApplyOTFirst();
         if ( KANUtil.filterEmpty( applyOTFirst, "0" ) == null && clientOrderHeaderVO != null )
         {
            applyOTFirst = clientOrderHeaderVO.getApplyOTFirst();
         }

         // �Ƿ������ӱ�����
         boolean updated = false;
         // �ܾ����ύ�������ӱ�����
         boolean refuse = otHeaderVO.getStatus().equals( "6" );

         // ������ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( otHeaderVO.getStatus() ) != null
                  && ( KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "1" ) || KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "3" ) || KANUtil.filterEmpty( otHeaderVO.getStatus() ).equals( "6" ) ) )
            {
               // ״̬��Ϊ����ˣ����ռӰ��������ã�
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

               // ʱ�䲻������޸�����״̬
               if ( updated && !refuse )
               {
                  // �޸�OTHeaderVO
                  ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

                  // �ύOTDetailVO List
                  submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
               }
               // ��ԭ�ж���ʱ�䲻ͬ�������������detail����
               else
               {
                  updateOTHeader_nt( otHeaderVO );
               }
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submitOTHeader" );
            historyVO.setObjectId( otHeaderVO.getOtHeaderId() );

            // ����Ӱ�Сʱ���飨������ҳ���õ���
            fetchOTHoursDetail( otHeaderVO );

            // ״̬����
            if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst, "0" ).equals( "1" ) )
            {
               if ( otHeaderVO.getStatus().trim().equals( "4" ) )
               {
                  // ȷ��
                  otHeaderVO.setStatus( "5" );
               }
               else
               {
                  // ��׼
                  otHeaderVO.setStatus( "3" );
               }
            }
            else
            {
               // ȷ��
               otHeaderVO.setStatus( "5" );
            }

            // ����ͨ������
            final String passObject = KANUtil.toJSONObject( otHeaderVO ).toString();

            // �˻�״̬
            otHeaderVO.setStatus( "6" );
            // ������ͨ������
            final String failObject = KANUtil.toJSONObject( otHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            //����������role
            if ( workflowActualDTO.getWorkflowActualVO() != null && StringUtils.isNotBlank( otHeaderVO.getRole() ) )
            {
               workflowActualDTO.getWorkflowActualVO().setRole( otHeaderVO.getRole() );
            }
            workflowActualDTO.getWorkflowActualVO().setObjectId( otHeaderVO.getOtHeaderId() );
            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.attendance.OTHeaderVO" );
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, otHeaderVO );
         }
         // �����ڹ�����
         else
         {
            // ������Ҫ����
            if ( KANUtil.filterEmpty( applyOTFirst, "0" ) != null && KANUtil.filterEmpty( applyOTFirst, "0" ).equals( "1" ) )
            {
               if ( otHeaderVO.getStatus().trim().equals( "3" ) )
               {
                  // ȷ��
                  otHeaderVO.setStatus( "5" );

                  updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() ) && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
               }
               else
               {
                  // ��׼
                  otHeaderVO.setStatus( "3" );

                  updated = originalObject.getEstimateStartDate().equals( otHeaderVO.getEstimateStartDate() )
                        && originalObject.getEstimateEndDate().equals( otHeaderVO.getEstimateEndDate() );
               }
            }
            // ������������
            else
            {
               if ( KANUtil.filterEmpty( otHeaderVO.getActualStartDate() ) == null || KANUtil.filterEmpty( otHeaderVO.getActualEndDate() ) == null )
               {
                  otHeaderVO.setActualStartDate( otHeaderVO.getEstimateStartDate() );
                  otHeaderVO.setActualEndDate( otHeaderVO.getEstimateEndDate() );
                  otHeaderVO.setActualHours( otHeaderVO.getEstimateHours() );
               }
               updated = otHeaderVO.getEstimateStartDate().equals( otHeaderVO.getActualStartDate() ) && otHeaderVO.getEstimateEndDate().equals( otHeaderVO.getActualEndDate() );
               // ȷ��
               otHeaderVO.setStatus( "5" );
            }

            // ʱ�䲻������޸�����״̬
            if ( updated && !refuse )
            {
               // �޸�OTHeaderVO
               ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

               // �ύOTDetailVO List
               submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
            }
            // ��ԭ�ж���ʱ�䲻ͬ�������������detail����
            else
            {
               updateOTHeader_nt( otHeaderVO );
            }
         }
      }
      else
      {
         // �޸�OTHeaderVO
         final OTHeaderVO vo = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderVO.getOtHeaderId() );
         otHeaderVO.setDescription( vo.getDescription() );
         ( ( OTHeaderDao ) getDao() ).updateOTHeader( otHeaderVO );

         // �ύOTDetailVO List
         submitOTDetail( otHeaderVO.getOtHeaderId(), otHeaderVO.getStatus() );
      }

      return -1;
   }

   // ����OTHeaderVO����OTDetailVO
   private OTDetailVO generateOTDetailVO( final OTHeaderVO otHeaderVO ) throws KANException
   {
      // ��ʼ��OTDetailVO
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
      // ��2����ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( otHeaderVO.getAccountId() );
      history.setNameZH( otHeaderVO.getEmployeeNameZH() );
      history.setNameEN( otHeaderVO.getEmployeeNameEN() );
      history.setOwner( owner );
      return history;
   }

   @Override
   // ��ȡ��ʱOTHeaderVO�б�����ÿ�¼Ӱ����ޣ�
   public List< OTHeaderVO > getOTHeaderVOsByContracrId( final String contractId ) throws KANException
   {
      try
      {
         // ��ʼ������ֵ����
         final List< OTHeaderVO > otHeaderVOs = new ArrayList< OTHeaderVO >();

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( contractId );

         // ��ȡClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

         // ��ȡ��н��ʼ��������
         String circleStartDay = "";
         String circleEndDay = "";

         if ( clientOrderHeaderVO != null )
         {
            circleStartDay = clientOrderHeaderVO.getCircleStartDay();
            circleEndDay = clientOrderHeaderVO.getCircleEndDay();
         }

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

         // ��ȡOTDetailVO�б��Ѱ�ʱ��������
         final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( contractId );

         // ����OTDetailVO�б�
         if ( otDetailVOs != null && otDetailVOs.size() > 0 )
         {
            // Сʱ������
            double hours = 0;

            Calendar firstOTCalendar = KANUtil.createCalendar( ( ( OTDetailVO ) otDetailVOs.get( 0 ) ).getEstimateStartDate() );

            // ��ȡ��һ�������·�
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
                  // ��ʼ��OTDetailVO
                  final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

                  // ��ȡ��ǰ������
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
                  // ��ʼ����ʱOTHeaderVO
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

               // �·ݵ���
               firstMonthly = KANUtil.getMonthly( firstMonthly, 1 );

               // ���ü�����
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
         // ��ʼ��OTDTO�б� 
         final List< OTDTO > otDTOs = new ArrayList< OTDTO >();

         // ��ʼ��OTHeaderVO
         final OTHeaderVO otHeaderVO = new OTHeaderVO();
         otHeaderVO.setAccountId( accountId );
         otHeaderVO.setSortColumn( "estimateStartDate" );
         otHeaderVO.setSortOrder( "desc" );
         otHeaderVO.setStatus( BaseVO.TRUE );

         // ��ȡOTHeaderVO�б�
         final List< Object > otHeaderVOs = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOsByCondition( otHeaderVO );

         // ����OTHeaderVO�б�����װ��OTDTO
         if ( otHeaderVOs != null && otHeaderVOs.size() > 0 )
         {
            for ( Object otHeaderVOObject : otHeaderVOs )
            {
               // ��ʼ��OTDTO
               final OTDTO otDTO = new OTDTO();
               otDTO.setOtHeaderVO( ( OTHeaderVO ) otHeaderVOObject );

               // ��ȡOTDetailVO�б�
               final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( ( ( OTHeaderVO ) otHeaderVOObject ).getOtHeaderId() );

               // ����OTDetailVO�б�
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

   // �Ӱ�ȷ�������
   private void recalculateTimesheet( final String otHeaderId, final String timesheetId ) throws KANException
   {
      try
      {
         // ��ȡOTHeaderVO 
         final OTHeaderVO otHeaderVO = ( ( OTHeaderDao ) getDao() ).getOTHeaderVOByOTHeaderId( otHeaderId );

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( otHeaderVO.getContractId() );

         // ��ȡClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO != null ? employeeContractVO.getOrderId()
               : "0" );

         // �������ɷ�ʽ����Ϊ�����ܵ�������
         if ( KANUtil.filterEmpty( timesheetId ) == null && clientOrderHeaderVO != null && !clientOrderHeaderVO.getAttendanceGenerate().equals( "3" ) )
         {
            // ��н��������
            String circleEndDay = clientOrderHeaderVO.getCircleEndDay();
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getCircleEndDay(), "0 " ) == null )
            {
               circleEndDay = "31";
            }

            // ��ȡOTDetailVO List
            final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByOTHeaderId( otHeaderVO.getOtHeaderId() );

            // ��ȡ�Ӱ����²���
            final int monthGap = KANUtil.getGapMonth( circleEndDay, otHeaderVO.getEstimateStartDate(), otHeaderVO.getEstimateEndDate() );

            // ��ʼ���·�
            String monthly = KANUtil.getMonthlyByCondition( circleEndDay, otHeaderVO.getEstimateStartDate() );

            for ( int i = 0; i <= monthGap; i++ )
            {
               if ( i > 0 )
               {
                  monthly = KANUtil.getMonthly( monthly, i );
               }

               // ��ʼ��TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
               tempTimesheetHeaderVO.setAccountId( otHeaderVO.getAccountId() );
               tempTimesheetHeaderVO.setCorpId( otHeaderVO.getCorpId() );
               tempTimesheetHeaderVO.setContractId( otHeaderVO.getContractId() );
               tempTimesheetHeaderVO.setEmployeeId( otHeaderVO.getEmployeeId() );
               tempTimesheetHeaderVO.setMonthly( monthly );
               tempTimesheetHeaderVO.setStatus( "1" );

               // ��ȡTimesheetDTO�б�
               final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( tempTimesheetHeaderVO );

               // ����TimesheetDTO�б�
               if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
               {
                  // ��ȡTimesheetDTO
                  final TimesheetDTO timesheetDTO = timesheetDTOs.get( 0 );

                  // ����OTDetailVO�б�
                  if ( otDetailVOs != null && otDetailVOs.size() > 0 )
                  {
                     for ( Object otDetailVOObject : otDetailVOs )
                     {
                        // ��ʼ��OTDetailVO
                        final OTDetailVO otDetailVO = ( OTDetailVO ) otDetailVOObject;

                        // �Ƿ��ǵ�ǰ��
                        if ( KANUtil.getMonthlyByCondition( circleEndDay, otDetailVO.getEstimateStartDate() ).equals( tempTimesheetHeaderVO.getMonthly() ) )
                        {
                           if ( timesheetDTO.getTimesheetDetailVOs() != null && timesheetDTO.getTimesheetDetailVOs().size() > 0 )
                           {
                              for ( TimesheetDetailVO timesheetDetailVO : timesheetDTO.getTimesheetDetailVOs() )
                              {
                                 if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( otDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
                                 {
                                    // ��ʼ��StringBuffer
                                    final StringBuffer rs = new StringBuffer( timesheetDetailVO.getDescription() );

                                    rs.append( getRemarkString( otHeaderVO, otDetailVO ) + "��" );

                                    // ���ñ�ע
                                    timesheetDetailVO.setDescription( rs.toString() );

                                    // �޸�TimesheetDetailVO
                                    this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                                    // ���ÿ���ID
                                    otDetailVO.setTimesheetId( timesheetDTO.getTimesheetHeaderVO().getHeaderId() );

                                    // �޸�OTDetailVO
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
   // ɾ���Ӱ��¼��������Ӧ�Ŀ��ڱ�
   // Add by siuvan.xia at 2014-7-4 02:07:29
   public int deleteOTHeader_cleanTS( final OTHeaderVO otHeaderVO ) throws KANException
   {
      try
      {
         // ��ʼ����
         startTransaction();

         // ��ȡOTDetailVO�б�
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
                           final String remarkArray[] = timesheetDetailVO.getDescription().split( "��" );
                           if ( remarkArray != null && remarkArray.length > 0 )
                           {
                              for ( String r : remarkArray )
                              {
                                 if ( !r.equals( remark.toString() ) )
                                 {
                                    newRemark.append( r + "��" );
                                 }
                              }

                              timesheetDetailVO.setDescription( newRemark.toString() );
                              // ����TimesheetDetailVO
                              this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                              break;
                           }
                        }
                     }
                  }
               }
            }

         }

         // ɾ���Ӱ��¼
         deleteOTHeader_nt( otHeaderVO );

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

   private String getRemarkString( final OTHeaderVO otHeaderVO, final OTDetailVO otDetailVO ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      // ��ȡ�Ӱ��Ŀ
      final List< MappingVO > otItems = KANConstants.getKANAccountConstants( otHeaderVO.getAccountId() ).getOtItems( "ZH", otHeaderVO.getCorpId() );

      rs.append( KANUtil.getMappingValueByMappingList( otItems, otDetailVO.getItemId() ) + "���ࣩ" );

      rs.append( "��" + TimesheetDTO.getRemartkString( otDetailVO.getEstimateStartDate(), otDetailVO.getEstimateEndDate() ) );

      return rs.toString();
   }

   // �Ż������Ӱ�ҳ�棬�Ӱ���ϸ״�������磺OT1.5 - 2Сʱ��OT3.0 - 4Сʱ
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

            // ������½����ύ-����ˡ��˻�  �Ӱ�Сʱ��ȡEstimate
            if ( "1".equals( otHeaderVO.getStatus() ) || "2".equals( otHeaderVO.getStatus() ) || "6".equals( otHeaderVO.getStatus() ) )
            {
               tempOTHours = Double.valueOf( otDetailVO.getEstimateHours() );
            }
            // ������ύ-��ȷ��  �Ӱ�Сʱ��ȡActual
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
