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
   // ���Logger����
   protected Log logger = LogFactory.getLog( getClass() );

   // �������������磬com.kan.base.domain.BaseVO��
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.LeaveHeaderVO";

   // Service Name�����磬Spring�����Bean���� spring�����ļ��� service��ӦBean��ID ��
   public static final String SERVICE_BEAN = "leaveHeaderService";

   // ������service
   private WorkflowService workflowService;

   // ע���ԱDAO
   private EmployeeDao employeeDao;

   // ע���Ա - ����Э��DAO
   private EmployeeContractDao employeeContractDao;

   // ע���Ա - ����Э�� - �������DAO
   private EmployeeContractLeaveDao employeeContractLeaveDao;

   // ע��ͻ� - ���񶩵�DAO
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��ͻ� - ���񶩵� - �������DAO
   private ClientOrderLeaveDao clientOrderLeaveDao;

   // ע��LeaveDetailDao
   private LeaveDetailDao leaveDetailDao;

   // ע�뿼�ڱ�Service
   private TimesheetHeaderService timesheetHeaderService;

   // ע�뿼�ڱ�Header Dao
   private TimesheetHeaderDao timesheetHeaderDao;

   // ע�뿼�ڱ�Detail Dao
   private TimesheetDetailDao timesheetDetailDao;

   // ע��Ӱ�Detail Dao
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
      logger.error( "###>>>>>>>>>>>>>>> Ԥ�Ƹ���Сʱ�� deBug:[" + leaveHeaderVO.getEstimateBenefitHours() + "]" );
      try
      {
         int rows = 0;

         // ��������
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

         // ״̬��ʼ��Ĭ��Ϊ���½���
         leaveHeaderVO.setStatus( "1" );
         // ɾ����ʾ��ʼ��Ĭ��Ϊ��δɾ����
         leaveHeaderVO.setDeleted( "1" );
         // ����LeaveHeaderVO
         ( ( LeaveHeaderDao ) getDao() ).insertLeaveHeader( leaveHeaderVO );

         rows++;

         // �����죬���ʲ���LeaveDetailVO
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
         // ���죬���ز���LeaveDetailVO
         else
         {
            rows = rows + addLeaveDetails( leaveHeaderVO );
         }

         // ������ύ���߹�����
         if ( KANUtil.filterEmpty( leaveHeaderVO.getSubAction() ) != null && KANUtil.filterEmpty( leaveHeaderVO.getSubAction() ).equals( BaseAction.SUBMIT_OBJECT ) )
         {
            submitLeaveHeader_nt( leaveHeaderVO );
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

   // ���LeaveDetailVO������ǿ�������Ӷ��
   // Reviewed by Kevin Jin at 2014-04-20
   private int addLeaveDetails( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      int rows = 0;

      // ��ȡEmployeeContractVO
      final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

      // ��ȡClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );

      // ��ȡEmployeeContractLeaveVO�б���Merge ClientOrderLeaveVO�б�
      final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = getEmployeeContractLeaveVOsByContractId( leaveHeaderVO.getContractId() );

      // ��ʼ��LegalQuantity
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
         leaveDetailVO.setEstimateStartDate( i == 0 ? startDate : KANUtil.formatDate( startCalendar.getTime(), "yyyy-MM-dd" ) + " 00:00" );
         leaveDetailVO.setEstimateEndDate( i == gap ? endDate : KANUtil.formatDate( KANUtil.getDate( startCalendar.getTime(), 0, 0, 1 ), "yyyy-MM-dd" ) + " 00:00" );

         // ��ȡ��ǰ�����Сʱ��
         final double currentHours = new TimesheetDTO().getLeaveHours( leaveHeaderVO.getItemId(), leaveHeaderVO.getAccountId(), calendarId, shiftId, leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() );

         // ��������
         if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && leaveHeaderVO.getItemId().equals( "41" ) )
         {
            // ��ȡ��ǰ����Э�飨��٣����õ�����Сʱ����
            final double legalHours = getUsedLegalHours( tempEmployeeContractLeaveVO );

            // ���(��ǰ����+���÷���) �Ƚ�   (�ܷ���)
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
         // ������Ŀ
         else
         {
            leaveDetailVO.setEstimateBenefitHours( String.valueOf( currentHours ) );
         }

         startCalendar.add( Calendar.DATE, 1 );

         // ����LeaveHeaderVO�����Сʱ�����ڡ�0����
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

         // ��������
         this.startTransaction();

         rows = updateLeaveHeader_nt( leaveHeaderVO );

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

   // Update LeaveHeaderVO
   // Reviewed by Kevin Jin at 2014-04-20
   private int updateLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // �޸�LeaveHeaderVO
         ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

         rows++;

         // ��ȡLeaveDetailVO�б�
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         String timesheetId = "";
         // ����LeaveDetailVO�б���������ɾ��
         if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
         {
            for ( Object leaveDetailVOObject : leaveDetailVOs )
            {
               timesheetId = ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId();
               leaveHeaderVO.setTimesheetId( ( ( LeaveDetailVO ) leaveDetailVOObject ).getTimesheetId() );
               this.getLeaveDetailDao().deleteLeaveDetail( ( LeaveDetailVO ) leaveDetailVOObject );
            }
         }

         // ��Ӷ���LeaveDetailVO
         rows = rows + addLeaveDetails( leaveHeaderVO );

         // ״̬Ϊ��׼��
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
         // ��ȡLeaveDetailVO�б�
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderId );

         String timesheetId = "";
         // ����LeaveDetailVO
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

         // ״̬Ϊ��׼��
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
   // ��-1����ʾ�ύ�ɹ�
   // Reviewed by Kevin Jin at 2014-04-20
   public int submitLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         submitLeaveHeader_nt( leaveHeaderVO );

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

   @Override
   public int sickLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( leaveHeaderVO ) )
      {
         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // ��ȡHistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "4" ) ) )
            {
               // ״̬��Ϊ�ύ-������
               leaveHeaderVO.setRetrieveStatus( "2" );

               // �޸�LeaveHeaderVO
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "sickLeaveHeader" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // ����״̬
            leaveHeaderVO.setRetrieveStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // �ܾ�����״̬
            leaveHeaderVO.setRetrieveStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            String employeeId = leaveHeaderVO.getEmployeeId();
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );

            if ( employeeVO != null )
            {
               // ׷�ӵ���������������
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }

            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         // û�й�����
         else
         {
            // ����
            leaveHeaderVO.setRetrieveStatus( "3" );

            // �޸�LeaveHeaderVO
            ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
         }
      }
      else
      {
         ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
      }

      return -1;
   }

   // ��-1����ʾ�ύ�ɹ�
   // Reviewed by Kevin Jin at 2014-04-20
   @Override
   public int submitLeaveHeader_nt( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( leaveHeaderVO ) )
      {
         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // ��ȡHistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );

         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            if ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getStatus() ).equals( "4" ) ) )
            {
               // ״̬��Ϊ�����
               leaveHeaderVO.setStatus( "2" );

               // �޸�LeaveHeaderVO
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

               // �ύLeaveDetailVO List
               submitLeaveDetail( leaveHeaderVO.getLeaveHeaderId(), leaveHeaderVO.getStatus() );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "submitLeaveHeader_nt" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // ��׼״̬
            leaveHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // �˻�״̬
            leaveHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            String employeeId = leaveHeaderVO.getEmployeeId();
            EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeId );
            if ( employeeVO != null )
            {
               // ׷�ӵ���������������
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }
            //����������role
            if ( workflowActualDTO.getWorkflowActualVO() != null && StringUtils.isNotBlank( leaveHeaderVO.getRole() ) )
            {
               workflowActualDTO.getWorkflowActualVO().setRole( leaveHeaderVO.getRole() );
            }
            workflowActualDTO.getWorkflowActualVO().setObjectId( leaveHeaderVO.getLeaveHeaderId() );
            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.attendance.LeaveHeaderVO" );
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         // û�й�����
         else
         {
            // ��׼
            leaveHeaderVO.setStatus( "3" );

            // �޸�LeaveHeaderVO
            ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

            // �ύLeaveDetailVO List
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

      // ���ɾ��LeaveHeaderVO
      leaveHeaderVO.setDeleted( LeaveHeaderVO.FALSE );
      ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );

      rows++;

      // ��ȡLeaveDetailVO�б�
      final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

      // �������ɾ��
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
         // ��������
         this.startTransaction();

         rows = deleteLeaveHeader_nt( leaveHeaderVO );

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
   // ��ȡԱ�����ݼ����ã������ݼ�����ʣ��Сʱ����
   public List< EmployeeContractLeaveVO > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException
   {
      try
      {
         // ��ʼ������ֵ
         final List< EmployeeContractLeaveVO > employeeContractLeaveVOs = new ArrayList< EmployeeContractLeaveVO >();

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.employeeContractDao.getEmployeeContractVOByContractId( contractId );

         if ( employeeContractVO == null )
         {
            return employeeContractLeaveVOs;
         }

         // ��ȡEmployeeContractLeaveVO�б�
         final List< Object > employeeContractLeaveVOObjects = this.employeeContractLeaveDao.getEmployeeContractLeaveVOsByContractId( contractId );

         // ��ȡClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = null;
         if ( KANUtil.filterEmpty( employeeContractVO.getOrderId() ) != null )
         {
            clientOrderHeaderVO = this.clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
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

         // ������٣�10�·ݿ���
         EmployeeContractLeaveVO nextYearEmployeeContractLeaveVO = null;
         // ������Ϣ�д����ݼ�����
         if ( employeeContractLeaveVOObjects != null && employeeContractLeaveVOObjects.size() > 0 )
         {
            for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOObjects )
            {
               // ��ʼ��EmployeeContractLeaveVO
               final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;

               // ״̬Ϊ����
               if ( employeeContractLeaveVO.getStatus().equals( "1" ) )
               {
                  String currYear = KANUtil.formatDate( new Date(), "yyyy" );
                  String nextYear = String.valueOf( Integer.valueOf( currYear ) + 1 );

                  // ���Ĭ��ֻȥ��ǰ���
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

                  // ״̬Ϊ���ã������������������
                  if ( !exist && clientOrderLeaveVO.getStatus().equals( "1" ) && !clientOrderLeaveVO.getItemId().equals( "41" ) )
                  {
                     final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
                     employeeContractLeaveVO.setEmployeeLeaveId( clientOrderLeaveVO.getOrderLeaveId() + "(���Խ����������)" );
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

         // װ�ؼӰ໻��
         fetchOvertimeChangeLeave( employeeContractLeaveVOs, employeeContractVO );

         // װ������
         //fetchSickLeave( employeeContractLeaveVOs, employeeContractVO );

         // װ����ٷ�����ȥ�꣩
         fetchLegalAnnualLeaveLastYear( employeeContractLeaveVOs, employeeContractVO );

         // װ����ٸ�����ȥ�꣩
         fetchBenefitAnnualLeaveLastYear( employeeContractLeaveVOs, employeeContractVO );

         // ����ʣ��Сʱ��
         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            setLeftHours( employeeContractLeaveVOs, employeeContractVO.getAccountId(), employeeContractVO.getCorpId(), contractId, calendarId, shiftId );
         }

         // 10�¿����������
         if ( Integer.valueOf( KANUtil.formatDate( new Date(), "dd" ) ) >= 10 && nextYearEmployeeContractLeaveVO != null )
         {
            for ( EmployeeContractLeaveVO vo : employeeContractLeaveVOs )
            {
               if ( "41".equals( vo.getItemId() ) && vo.getLeftBenefitQuantity() != null && Double.valueOf( vo.getLeftBenefitQuantity() ) <= 0 )
               {
                  // ����ʣ��Сʱ��
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

   // װ�ؼӰ໻��
   private void fetchOvertimeChangeLeave( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ȡ�Ӱ໻�ݼ�¼
      final List< Object > otDetailVOs = this.getOtDetailDao().getOTDetailVOsByContractId( employeeContractVO.getContractId() );

      // ���ڼӰ໻�ݼ�¼
      if ( otDetailVOs != null && otDetailVOs.size() > 0 )
      {
         // ��ʼ���Ӱ໻��Сʱ��
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
            // ��ʼ��EmployeeContractLeaveVO
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

   // װ������
   private void fetchSickLeave( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ȡ���ټ�¼��״̬Ϊ��׼
      final LeaveHeaderVO sickLeaveHeaderVO = new LeaveHeaderVO();
      sickLeaveHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sickLeaveHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      sickLeaveHeaderVO.setContractId( employeeContractVO.getContractId() );
      sickLeaveHeaderVO.setRetrieveStatus( "3" );
      final List< Object > sickLeaveHeaderVOs = ( ( LeaveHeaderDao ) getDao() ).getLeaveHeaderVOsByCondition( sickLeaveHeaderVO );

      // ������׼�����ټ�¼
      if ( sickLeaveHeaderVOs != null && sickLeaveHeaderVOs.size() > 0 )
      {
         // ��ʼ������Сʱ����������
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
            // ��ʼ��EmployeeContractLeaveVO
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

   // װ����ٷ�����ȥ�꣩
   private void fetchLegalAnnualLeaveLastYear( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int year = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      final EmployeeContractLeaveVO searchLastYearLeaveEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      searchLastYearLeaveEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      searchLastYearLeaveEmployeeContractLeaveVO.setItemId( "41" );
      searchLastYearLeaveEmployeeContractLeaveVO.setYear( String.valueOf( year - 1 ) );

      // ��ȡȥ���ݼ�����
      final EmployeeContractLeaveVO lastLeaveEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchLastYearLeaveEmployeeContractLeaveVO );

      // �����ǿ��ӳ�ʹ��
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

         // Сʱ��������ڡ�0�����ӳٿ��ò��ó�����ǰʱ��
         if ( Double.valueOf( lastLeaveEmployeeContractLeaveVO.getLegalQuantity() ) > 0
               && new Date().getTime() < KANUtil.createDate( tempLegalAnnualLeaveVO.getLeftLastYearLegalQuantityEndDate() ).getTime() )
         {
            employeeContractLeaveVOs.add( 0, tempLegalAnnualLeaveVO );
         }
      }
   }

   // װ����ٸ�����ȥ�꣩
   private void fetchBenefitAnnualLeaveLastYear( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int year = Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) );
      final EmployeeContractLeaveVO searchLastYearLeaveEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      searchLastYearLeaveEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      searchLastYearLeaveEmployeeContractLeaveVO.setItemId( "41" );
      searchLastYearLeaveEmployeeContractLeaveVO.setYear( String.valueOf( year - 1 ) );

      // ��ȡȥ���ݼ�����
      final EmployeeContractLeaveVO lastLeaveEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchLastYearLeaveEmployeeContractLeaveVO );

      // �����ǿ��ӳ�ʹ��
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

         // Сʱ��������ڡ�0�����ӳٿ��ò��ó�����ǰʱ��
         if ( Double.valueOf( lastLeaveEmployeeContractLeaveVO.getBenefitQuantity() ) > 0
               && new Date().getTime() < KANUtil.createDate( tempBenefitAnnualLeaveVO.getLeftLastYearBenefitQuantityEndDate() ).getTime() )
         {
            employeeContractLeaveVOs.add( 0, tempBenefitAnnualLeaveVO );
         }
      }
   }

   // ����ʣ��Сʱ��
   // Reviewed by Kevin Jin at 2013-11-25
   private void setLeftHours( final List< EmployeeContractLeaveVO > employeeContractLeaveVOs, final String accountId, final String corpId, final String contractId,
         final String calendarId, final String shiftId ) throws KANException
   {
      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveVOs )
         {
            // ��ȡ��ĿID 
            final String itemId = employeeContractLeaveVO.getItemId();

            // ��ȡ�������Сʱ
            final double benefitQuantity = employeeContractLeaveVO.getBenefitQuantity() == null ? 0.0 : Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() );

            // ��ȡ�������Сʱ��������٣���ٿ�ĿID = 41��
            double legalQuantity = employeeContractLeaveVO.getLegalQuantity() == null ? 0.0 : Double.valueOf( employeeContractLeaveVO.getLegalQuantity() );

            // ��١���٣�ȥ�꣩���
            if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "41" ) )
            {
               // ��õ�ǰ����Сʱ��
               double usedHours = getUsedHours( employeeContractLeaveVO );
               // ���ʱ��δ��������Сʱ
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
            // ��ٷ�����ȥ�꣩
            else if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "48" ) )
            {
               double usedLegalHours = getUsedLegalHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedLegalHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( "0" );
               employeeContractLeaveVO.setLeftLegalQuantity( String.valueOf( ( legalQuantity - usedLegalHours ) <= 0 ? 0.0 : ( legalQuantity - usedLegalHours ) ) );
            }
            // ��ٸ�����ȥ�꣩
            else if ( KANUtil.filterEmpty( itemId ) != null && itemId.equals( "49" ) )
            {
               double usedBenefitHours = getUsedBenefitHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedBenefitHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( ( benefitQuantity - usedBenefitHours ) <= 0 ? 0.0 : ( benefitQuantity - usedBenefitHours ) ) );
               employeeContractLeaveVO.setLeftLegalQuantity( "0" );
            }
            // �������
            else
            {
               // ��õ�ǰ����Сʱ��
               double usedHours = getUsedHours( employeeContractLeaveVO );
               employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedHours ) );
               employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( ( benefitQuantity - usedHours ) <= 0 ? 0.0 : ( benefitQuantity - usedHours ) ) );
               employeeContractLeaveVO.setLeftLegalQuantity( "0" );
            }
         }
      }
   }

   // ��õ�ǰ�����Сʱ��
   private double getUsedHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return getUsedBenefitHours( employeeContractLeaveVO ) + getUsedLegalHours( employeeContractLeaveVO );
   }

   // ����������ȡ����б����ã�������
   private double getUsedLegalHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      // ��ʼ��LeaveVO
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setContractId( employeeContractLeaveVO.getContractId() );
      leaveHeaderVO.setItemId( employeeContractLeaveVO.getItemId() );
      leaveHeaderVO.setYear( employeeContractLeaveVO.getYear() );

      return Double.valueOf( ( ( LeaveHeaderDao ) getDao() ).sumLegalLeaveHoursByCondition( leaveHeaderVO ) );
   }

   // ����������ȡ����б����ã�������
   private double getUsedBenefitHours( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      // ��ʼ��LeaveVO
      final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
      leaveHeaderVO.setContractId( employeeContractLeaveVO.getContractId() );
      leaveHeaderVO.setItemId( employeeContractLeaveVO.getItemId() );
      leaveHeaderVO.setYear( employeeContractLeaveVO.getYear() );

      return Double.valueOf( ( ( LeaveHeaderDao ) getDao() ).sumBenefitLeaveHoursByCondition( leaveHeaderVO ) );
   }

   // ����LeaveHeaderVO����LeaveDetailVO
   private LeaveDetailVO generateLeaveDetailVO( final LeaveHeaderVO leaveHeaderVO )
   {
      // ��ʼ��LeaveDetailVO
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

      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( leaveHeaderVO.getAccountId() );
      history.setNameZH( leaveHeaderVO.getEmployeeNameZH() );
      history.setNameEN( leaveHeaderVO.getEmployeeNameEN() );
      history.setOwner( owner );

      return history;
   }

   // ���¼���Timesheet����ٱ���׼�����
   // Reviewed by Kevin Jin at 2014-04-20
   private void recalculateTimesheet( final String leaveHeaderId, final String timesheetId ) throws KANException
   {
      try
      {
         // ��ȡLeaveHeaderVO
         final LeaveHeaderVO leaveHeaderVO = this.getLeaveHeaderVOByLeaveHeaderId( leaveHeaderId );

         // ��ȡLeaveDetailVO�б�
         final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );

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

            // ��ȡ��ٿ���²���
            final int monthGap = KANUtil.getGapMonth( circleEndDay, leaveHeaderVO.getEstimateStartDate(), leaveHeaderVO.getEstimateEndDate() );

            // ��ʼ���·�
            String monthly = KANUtil.getMonthlyByCondition( circleEndDay, leaveHeaderVO.getEstimateStartDate() );

            for ( int i = 0; i <= monthGap; i++ )
            {
               if ( i > 0 )
               {
                  monthly = KANUtil.getMonthly( monthly, i );
               }

               // ��ʼ��TimesheetHeaderVO
               final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
               tempTimesheetHeaderVO.setAccountId( leaveHeaderVO.getAccountId() );
               tempTimesheetHeaderVO.setCorpId( leaveHeaderVO.getCorpId() );
               tempTimesheetHeaderVO.setContractId( leaveHeaderVO.getContractId() );
               tempTimesheetHeaderVO.setEmployeeId( leaveHeaderVO.getEmployeeId() );
               tempTimesheetHeaderVO.setMonthly( monthly );
               tempTimesheetHeaderVO.setStatus( "1" );

               // ��ȡTimesheetDTO�б�
               final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( tempTimesheetHeaderVO );

               // ����TimesheetDTO�б�
               if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
               {
                  // ��ȡTimesheetDTO
                  final TimesheetDTO timesheetDTO = timesheetDTOs.get( 0 );

                  // ��ȡTimesheetHeaderVO
                  final TimesheetHeaderVO timesheetHeaderVO = timesheetDTO.getTimesheetHeaderVO();

                  // �Ƿ�Ӱ�쿼�ڱ�
                  boolean involveTimesheet = true;

                  // ��ٿ�ĿΪ�����١������Ӱ໻�ݡ��Ĳ��ı俼�ڱ�����
                  if ( KANUtil.filterEmpty( leaveHeaderVO.getItemId() ) != null && ( leaveHeaderVO.getItemId().equals( "25" ) || leaveHeaderVO.getItemId().equals( "60" ) ) )
                  {
                     involveTimesheet = false;
                  }

                  // �ı俼�����������
                  if ( involveTimesheet && timesheetHeaderVO != null )
                  {
                     // ����Сʱ��
                     double totalWorkHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours() )
                           - getLeaveHoursByMonthly( leaveDetailVOs, monthly, circleEndDay );
                     // ȫ��Сʱ��
                     double totalFullHours = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() );
                     // ȫ������
                     double totalFullDays = Double.valueOf( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() );

                     // ����Сʱ���쳣����
                     if ( totalWorkHours < 0 )
                     {
                        logger.info( "���ڱ�IDΪ" + timesheetHeaderVO.getHeaderId() + "����Сʱ�����ָ�����totalWorkHours = " + totalWorkHours );
                        totalWorkHours = 0;
                     }

                     // ����Сʱ��
                     timesheetDTO.getTimesheetHeaderVO().setTotalWorkHours( String.valueOf( totalWorkHours ) );
                     timesheetDTO.getTimesheetHeaderVO().setTotalWorkDays( totalWorkHours == 0 ? "0" : String.valueOf( totalWorkHours * totalFullDays / totalFullHours ) );

                     // �Ƿ���������
                     if ( !timesheetDTO.getTimesheetHeaderVO().getTotalWorkHours().equals( timesheetDTO.getTimesheetHeaderVO().getTotalFullHours() )
                           || !timesheetDTO.getTimesheetHeaderVO().getTotalWorkDays().equals( timesheetDTO.getTimesheetHeaderVO().getTotalFullDays() ) )
                     {
                        timesheetDTO.getTimesheetHeaderVO().setIsNormal( "2" );
                     }

                     // �޸�TinesheetHeaderVO
                     this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetDTO.getTimesheetHeaderVO() );
                  }

                  // ����LeaveDetailVO�б�
                  if ( leaveDetailVOs != null && leaveDetailVOs.size() > 0 )
                  {
                     // ����leaveDetailVOs
                     for ( Object leaveDetailVOObject : leaveDetailVOs )
                     {
                        // ��ʼ��LeaveDetailVO
                        final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) leaveDetailVOObject;

                        // �Ƿ��ǵ�ǰ��
                        if ( KANUtil.getMonthlyByCondition( circleEndDay, leaveDetailVO.getEstimateStartDate() ).equals( monthly ) )
                        {
                           if ( timesheetDTO.getTimesheetDetailVOs() != null && timesheetDTO.getTimesheetDetailVOs().size() > 0 )
                           {
                              for ( TimesheetDetailVO timesheetDetailVO : timesheetDTO.getTimesheetDetailVOs() )
                              {
                                 if ( timesheetDetailVO.getDay().equals( KANUtil.formatDate( leaveDetailVO.getEstimateStartDate(), "yyyy-MM-dd" ) ) )
                                 {
                                    // ���칤��Сʱ��
                                    double workHours = Double.valueOf( timesheetDetailVO.getWorkHours() );

                                    // ��LeaveDetailVO�ϲ�
                                    if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateBenefitHours() ) != null )
                                    {
                                       workHours = workHours - Double.valueOf( leaveDetailVO.getEstimateBenefitHours() );
                                    }

                                    if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateLegalHours() ) != null )
                                    {
                                       workHours = workHours - Double.valueOf( leaveDetailVO.getEstimateLegalHours() );
                                    }

                                    // �쳣�������
                                    if ( workHours < 0 )
                                    {
                                       logger.info( "���������IDΪ" + timesheetDetailVO.getDetailId() + "����Сʱ�����ָ�����workHours = " + workHours );
                                       workHours = 0.0;
                                    }

                                    // ���ù���Сʱ��
                                    if ( involveTimesheet )
                                    {
                                       timesheetDetailVO.setWorkHours( String.valueOf( workHours ) );
                                    }

                                    // ��ʼ��StringBuffer
                                    final StringBuffer rs = new StringBuffer( timesheetDetailVO.getDescription() );

                                    rs.append( getRemarkString( leaveHeaderVO, leaveDetailVO ) + "��" );

                                    // ���ñ�ע
                                    timesheetDetailVO.setDescription( rs.toString() );

                                    // �޸�TimesheetDetailVO
                                    this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                                    // ���ÿ���ID
                                    leaveDetailVO.setTimesheetId( timesheetHeaderVO.getHeaderId() );

                                    // �޸�LeaveDetailVO
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

   // �����·ݻ�ȡ�������Сʱ��
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
   // ɾ����ټ�¼�������ڱ�
   // Add by siuvan.xia @ 2014-07-03
   public int deleteLeaveHeader_cleanTS( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      try
      {
         // ��ʼ����
         startTransaction();

         // ��ȡLeaveDeailVO�б�
         final List< Object > leaveDeailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

         if ( leaveDeailVOs != null && leaveDeailVOs.size() > 0 )
         {
            for ( Object leaveDetailVOObject : leaveDeailVOs )
            {
               // ��ʼ��LeaveDetailVO
               final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) leaveDetailVOObject;

               // ���ٻ��ǼӰ໻��
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
                           }

                           if ( !update )
                           {
                              // ���칤��Сʱ��
                              double workHours = 0.0;
                              // ��LeaveDetailVO�ϲ�
                              if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateBenefitHours() ) != null )
                              {
                                 workHours = workHours + Double.valueOf( leaveDetailVO.getEstimateBenefitHours() );
                              }

                              if ( KANUtil.filterEmpty( leaveDetailVO.getEstimateLegalHours() ) != null )
                              {
                                 workHours = workHours + Double.valueOf( leaveDetailVO.getEstimateLegalHours() );
                              }

                              timesheetDetailVO.setWorkHours( String.valueOf( workHours + Double.valueOf( timesheetDetailVO.getWorkHours() ) ) );

                              // ����Сʱ��
                              double totalWorkHours = Double.valueOf( timesheetHeaderVO.getTotalWorkHours() ) + workHours;
                              // ȫ��Сʱ��
                              double totalFullHours = Double.valueOf( timesheetHeaderVO.getTotalFullHours() );
                              // ȫ������
                              double totalFullDays = Double.valueOf( timesheetHeaderVO.getTotalFullDays() );

                              // ����Сʱ��
                              timesheetHeaderVO.setTotalWorkHours( String.valueOf( totalWorkHours ) );
                              timesheetHeaderVO.setTotalWorkDays( String.valueOf( totalWorkHours * totalFullDays / totalFullHours ) );

                              // �Ƿ���������
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
                           // ����TimesheetDetailVO
                           this.getTimesheetDetailDao().updateTimesheetDetail( timesheetDetailVO );

                           break;
                        }
                     }
                  }
               }
            }
         }

         // ɾ����ټ�¼
         deleteLeaveHeader_nt( leaveHeaderVO );

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

   private String getRemarkString( final LeaveHeaderVO leaveHeaderVO, final LeaveDetailVO leaveDetailVO )
   {
      final StringBuffer rs = new StringBuffer();
      // ��ȡ��ٿ�Ŀ
      final List< MappingVO > leaveItems = KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getLeaveItems( "ZH", leaveHeaderVO.getCorpId() );

      if ( leaveItems != null )
      {
         leaveItems.add( KANConstants.getKANAccountConstants( leaveHeaderVO.getAccountId() ).getItemByItemId( "25", "ZH" ) );
      }
      rs.append( KANUtil.getMappingValueByMappingList( leaveItems, leaveDetailVO.getItemId() ) + "���٣�" );

      rs.append( "��" + TimesheetDTO.getRemartkString( leaveDetailVO.getEstimateStartDate(), leaveDetailVO.getEstimateEndDate() ) );

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
         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( leaveHeaderVO.getContractId() );
         // ��ȡHistoryVO
         final HistoryVO historyVO = generateHistoryVO( leaveHeaderVO, employeeContractVO.getOwner() );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );
         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( leaveHeaderVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( leaveHeaderVO.getEmployeeId() );
            // ״̬��Ϊ�ύ-������
            if ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ) != null
                  && ( KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "1" ) || KANUtil.filterEmpty( leaveHeaderVO.getRetrieveStatus() ).equals( "4" ) ) )
            {
               leaveHeaderVO.setRetrieveStatus( "2" );
               ( ( LeaveHeaderDao ) getDao() ).updateLeaveHeader( leaveHeaderVO );
            }

            // Service�ķ���
            historyVO.setServiceMethod( "sick_leave_nt" );
            historyVO.setObjectId( leaveHeaderVO.getLeaveHeaderId() );

            // ͬ������
            leaveHeaderVO.setRetrieveStatus( "3" );
            final String passObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            // �ܾ�����
            leaveHeaderVO.setRetrieveStatus( "4" );
            final String failObject = KANUtil.toJSONObject( leaveHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            if ( employeeVO != null )
            {
               // ׷�ӵ���������������
               historyVO.setNameZH( employeeVO.getNameZH() );
               historyVO.setNameEN( employeeVO.getNameEN() );
            }

            // ����������
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, leaveHeaderVO );
         }
         else
         {
           // û�й�����
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
    * ��������dealSickLeave
    * @param leaveHeaderVO
    * @throws KANException 
    */
   private void dealSickLeave( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      // ͬ������
      if ( "3".equals( leaveHeaderVO.getRetrieveStatus() ) )
      {
         double sickLeaveAfterHours = Double.valueOf( leaveHeaderVO.getActualLegalHours() ) + Double.valueOf( leaveHeaderVO.getActualBenefitHours() );
         // �������ȫ��������ɾ������
         if ( sickLeaveAfterHours == 0 )
         {
            deleteLeaveHeader_nt( leaveHeaderVO );
         }
         // ������ڲ����������޸�����
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

            // ��ȡLeaveDetailVO�б�
            final List< Object > leaveDetailVOs = this.getLeaveDetailDao().getLeaveDetailVOsByLeaveHeaderId( leaveHeaderVO.getLeaveHeaderId() );

            // �������ɾ��
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
      // �ܾ�����
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
