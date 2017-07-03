package com.kan.hro.service.impl.biz.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ItemGroupDao;
import com.kan.base.dao.inf.management.ItemGroupRelationDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.AnnualLeaveRuleDTO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.ItemGroupRelationVO;
import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.domain.system.LogVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.task.SyncTask;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.json.JsonMapper;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderCBDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOTDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractOtherDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractPropertyDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.employee.EmployeePositionChangeDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.payment.SalaryHeaderService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentHeaderService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

public class EmployeeContractServiceImpl extends ContextService implements EmployeeContractService
{
   // ���Logger����
   protected Log logger = LogFactory.getLog( getClass() );

   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractVO";

   public final String SERVICE_BEAN = "employeeContractService";

   private WorkflowService workflowService;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   // ע��ClientDao
   private ClientDao clientDao;

   // ע��ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // ע��ClientOrderSBDao
   private ClientOrderSBDao clientOrderSBDao;

   // ע��ClientOrderCBDao
   private ClientOrderCBDao clientOrderCBDao;

   // ע��EmployeeContractSalaryDao
   private EmployeeContractSalaryDao employeeContractSalaryDao;

   // ע��EmployeeContractSBService
   private EmployeeContractSBService employeeContractSBService;

   // ע��EmployeeContractCBDao
   private EmployeeContractCBDao employeeContractCBDao;

   // ע��EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   // ע��EmployeeContractSBDetailDao
   private EmployeeContractSBDetailDao employeeContractSBDetailDao;

   // ע��EmployeeContractLeaveDao
   private EmployeeContractLeaveDao employeeContractLeaveDao;

   // ע��EmployeeContractOTDao
   private EmployeeContractOTDao employeeContractOTDao;

   // ע��EmployeeContractOtherDao
   private EmployeeContractOtherDao employeeContractOtherDao;

   // ע��TimesheetHeaderService
   private TimesheetHeaderService timesheetHeaderService;

   // ע��SBHeaderService
   private SBHeaderService sbHeaderService;

   // ע��CBHeaderService
   private CBHeaderService cbHeaderService;

   // ע��EmployeeContractPropertyDao
   private EmployeeContractPropertyDao employeeContractPropertyDao;

   // ע��OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // ע��SBAdjustmentHeaderService
   private SBAdjustmentHeaderService sbAdjustmentHeaderService;

   // ע��SBAdjustmentHeaderService
   private SalaryHeaderService salaryHeaderService;

   // ע��PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // ע��staffDao
   private StaffDao staffDao;

   //ע��itemGroupRelationDao
   private ItemGroupRelationDao itemGroupRelationDao;

   //ע��itemGroupDao
   private ItemGroupDao itemGroupDao;

   private LeaveHeaderDao leaveHeaderDao;

   // ע��PaymentAdjustmentHeaderDao
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   // ע�� UserDao
   private UserDao userDao;

   // ע�� PositionStaffRelationDao
   private PositionStaffRelationDao positionStaffRelationDao;

   private EmployeePositionChangeDao employeePositionChangeDao;

   // ע��clientOrderLeaveDao;
   private ClientOrderLeaveDao clientOrderLeaveDao;

   private LogDao logDao;

   public LogDao getLogDao()
   {
      return logDao;
   }

   public void setLogDao( LogDao logDao )
   {
      this.logDao = logDao;
   }

   public ClientOrderLeaveDao getClientOrderLeaveDao()
   {
      return clientOrderLeaveDao;
   }

   public void setClientOrderLeaveDao( ClientOrderLeaveDao clientOrderLeaveDao )
   {
      this.clientOrderLeaveDao = clientOrderLeaveDao;
   }

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public ItemGroupRelationDao getItemGroupRelationDao()
   {
      return itemGroupRelationDao;
   }

   public void setItemGroupRelationDao( ItemGroupRelationDao itemGroupRelationDao )
   {
      this.itemGroupRelationDao = itemGroupRelationDao;
   }

   public ItemGroupDao getItemGroupDao()
   {
      return itemGroupDao;
   }

   public void setItemGroupDao( ItemGroupDao itemGroupDao )
   {
      this.itemGroupDao = itemGroupDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public EmployeeContractSBDetailDao getEmployeeContractSBDetailDao()
   {
      return employeeContractSBDetailDao;
   }

   public void setEmployeeContractSBDetailDao( EmployeeContractSBDetailDao employeeContractSBDetailDao )
   {
      this.employeeContractSBDetailDao = employeeContractSBDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public void setEmployeeDao( final EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final ClientDao getClientDao()
   {
      return clientDao;
   }

   public final void setClientDao( ClientDao clientDao )
   {
      this.clientDao = clientDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public final ClientOrderSBDao getClientOrderSBDao()
   {
      return clientOrderSBDao;
   }

   public final void setClientOrderSBDao( ClientOrderSBDao clientOrderSBDao )
   {
      this.clientOrderSBDao = clientOrderSBDao;
   }

   public ClientOrderCBDao getClientOrderCBDao()
   {
      return clientOrderCBDao;
   }

   public void setClientOrderCBDao( ClientOrderCBDao clientOrderCBDao )
   {
      this.clientOrderCBDao = clientOrderCBDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( final EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public EmployeeContractSBService getEmployeeContractSBService()
   {
      return employeeContractSBService;
   }

   public void setEmployeeContractSBService( final EmployeeContractSBService employeeContractSBService )
   {
      this.employeeContractSBService = employeeContractSBService;
   }

   public EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public void setEmployeeContractCBDao( final EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   public EmployeeContractLeaveDao getEmployeeContractLeaveDao()
   {
      return employeeContractLeaveDao;
   }

   public void setEmployeeContractLeaveDao( final EmployeeContractLeaveDao employeeContractLeaveDao )
   {
      this.employeeContractLeaveDao = employeeContractLeaveDao;
   }

   public EmployeeContractOTDao getEmployeeContractOTDao()
   {
      return employeeContractOTDao;
   }

   public void setEmployeeContractOTDao( final EmployeeContractOTDao employeeContractOTDao )
   {
      this.employeeContractOTDao = employeeContractOTDao;
   }

   public EmployeeContractOtherDao getEmployeeContractOtherDao()
   {
      return employeeContractOtherDao;
   }

   public void setEmployeeContractOtherDao( final EmployeeContractOtherDao employeeContractOtherDao )
   {
      this.employeeContractOtherDao = employeeContractOtherDao;
   }

   public TimesheetHeaderService getTimesheetHeaderService()
   {
      return timesheetHeaderService;
   }

   public void setTimesheetHeaderService( final TimesheetHeaderService timesheetHeaderService )
   {
      this.timesheetHeaderService = timesheetHeaderService;
   }

   public SBHeaderService getSbHeaderService()
   {
      return sbHeaderService;
   }

   public void setSbHeaderService( SBHeaderService sbHeaderService )
   {
      this.sbHeaderService = sbHeaderService;
   }

   public CBHeaderService getCbHeaderService()
   {
      return cbHeaderService;
   }

   public void setCbHeaderService( CBHeaderService cbHeaderService )
   {
      this.cbHeaderService = cbHeaderService;
   }

   public EmployeeContractPropertyDao getEmployeeContractPropertyDao()
   {
      return employeeContractPropertyDao;
   }

   public void setEmployeeContractPropertyDao( EmployeeContractPropertyDao employeeContractPropertyDao )
   {
      this.employeeContractPropertyDao = employeeContractPropertyDao;
   }

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final SBAdjustmentHeaderService getSbAdjustmentHeaderService()
   {
      return sbAdjustmentHeaderService;
   }

   public final void setSbAdjustmentHeaderService( SBAdjustmentHeaderService sbAdjustmentHeaderService )
   {
      this.sbAdjustmentHeaderService = sbAdjustmentHeaderService;
   }

   public SalaryHeaderService getSalaryHeaderService()
   {
      return salaryHeaderService;
   }

   public void setSalaryHeaderService( SalaryHeaderService salaryHeaderService )
   {
      this.salaryHeaderService = salaryHeaderService;
   }

   public final PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public final void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public final PaymentAdjustmentHeaderDao getPaymentAdjustmentHeaderDao()
   {
      return paymentAdjustmentHeaderDao;
   }

   public final void setPaymentAdjustmentHeaderDao( PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao )
   {
      this.paymentAdjustmentHeaderDao = paymentAdjustmentHeaderDao;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public EmployeePositionChangeDao getEmployeePositionChangeDao()
   {
      return employeePositionChangeDao;
   }

   public void setEmployeePositionChangeDao( EmployeePositionChangeDao employeePositionChangeDao )
   {
      this.employeePositionChangeDao = employeePositionChangeDao;
   }

   @Override
   public PagedListHolder getEmployeeContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractDao employeeContractDao = ( EmployeeContractDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractDao.countEmployeeContractVOsByCondition( ( EmployeeContractVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractDao.getEmployeeContractVOsByCondition( ( EmployeeContractVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractDao.getEmployeeContractVOsByCondition( ( EmployeeContractVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractVO getEmployeeContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( contractId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   public int insertEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int count = 0;
      try
      {
         startTransaction();
         // ��ͬʱ�ޣ�������
         int monthGap = KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ), KANUtil.formatDate( employeeContractVO.getEndDate(), "yyyy-MM-dd" ) );
         employeeContractVO.setPeriod( String.valueOf( monthGap ) );
         if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
         {
            employeeContractVO.setEndDate( null );
         }

         count = ( ( EmployeeContractDao ) getDao() ).insertEmployeeContract( employeeContractVO );

         //ͬ����ͬ����Ŀ��Ա����Ŀ
         EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
            employeeDao.updateEmployee( employeeVO );
         }
         commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         e.printStackTrace();
         throw new KANException( e );
      }

      // ͬ��΢��
      BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
      return count;
   }

   @Override
   public int updateEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return updateEmployeeContract_nt( employeeContractVO, true );
   }

   public int updateEmployeeContract_nt( final EmployeeContractVO employeeContractVO, final boolean notTransaction ) throws KANException
   {
      int i = 0;
      StaffVO staffVO = null;
      boolean dimission = false;

      try
      {
         if ( notTransaction )
         {
            // ��������
            this.startTransaction();
         }

         //ҳ���ύʱ�洢��������
         if ( null != employeeContractVO && null != employeeContractVO.getConstantVOs() )
         {
            // Get EmployeeContractPropertyVO List
            final List< Object > objects = this.employeeContractPropertyDao.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );

            // ������ɾ�������ͬ��Ӧ��Properties
            if ( objects != null )
            {
               for ( Object object : objects )
               {
                  this.employeeContractPropertyDao.deleteEmployeeContractProperty( ( ( EmployeeContractPropertyVO ) object ).getPropertyId() );
               }
            }

            // ��������Ͷ���ͬ�����Э���Ӧ��Properties
            if ( employeeContractVO.getConstantVOs() != null && employeeContractVO.getConstantVOs().size() > 0 )
            {
               for ( Object constantVO : employeeContractVO.getConstantVOs() )
               {
                  // ����EmployeeContractPropertyVO����
                  final EmployeeContractPropertyVO employeeContractPropertyVO = new EmployeeContractPropertyVO();
                  employeeContractPropertyVO.setContractId( employeeContractVO.getContractId() );
                  employeeContractPropertyVO.setPropertyName( BeanUtils.getProperty( constantVO, "propertyName" ) );
                  employeeContractPropertyVO.setPropertyValue( BeanUtils.getProperty( constantVO, "content" ) );
                  employeeContractPropertyVO.setStatus( EmployeeContractPropertyVO.TRUE );
                  employeeContractPropertyVO.setCreateBy( employeeContractVO.getCreateBy() );
                  employeeContractPropertyVO.setModifyBy( employeeContractVO.getModifyBy() );
                  this.employeeContractPropertyDao.insertEmployeeContractProperty( employeeContractPropertyVO );
               }
            }
         }

         staffVO = this.staffDao.getStaffVOByEmployeeId( employeeContractVO.getEmployeeId() );

         if ( "3".equals( employeeContractVO.getStatus() ) )
         {
            // ����ͨ���޸Ĺ�Ա״̬Ϊ��ְ
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            employeeVO.setStatus( "1" );
            if ( employeeVO != null && !"2".equals( employeeVO.getHireAgain() ) )
            {
               employeeVO.setHireAgain( employeeContractVO.getHireAgain() );
            }
            employeeDao.updateEmployee( employeeVO );

            // ��StaffVO�ĳ���ְ
            if ( staffVO != null )
            {
               staffVO.setStatus( "1" );
               this.getStaffDao().updateStaff( staffVO );
            }
         }

         // �������Ա״̬�����������޸�Э��Ϊ����
         if ( KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ) != null
               && ( KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "2" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "3" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "4" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "5" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "6" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "7" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "8" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "9" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "10" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "11" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "12" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "13" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "14" ) ) )
         {
            employeeContractVO.setStatus( "7" );
         }

         i = ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVO );

         //ͬ����ͬ����Ŀ��Ա����Ŀ
         EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
            employeeDao.updateEmployee( employeeVO );
         }

         // �������Ա������Ч����Э�����޸Ĺ�Ա״̬Ϊ��ְ
         if ( KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ) != null
               && ( KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "2" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "3" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "4" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "5" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "6" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "7" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "8" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "9" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "10" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "11" )
                     || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "12" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "13" ) || KANUtil.filterEmpty( employeeContractVO.getEmployStatus() ).equals( "14" ) ) )
         {
            dimissionEmployeeContract( employeeContractVO, staffVO );
            dimission = true;
         }
         if ( notTransaction )
         {
            this.commitTransaction();
         }
         // ͬ��΢��
         BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );

      }
      catch ( final Exception e )
      {
         if ( notTransaction )
         {
            // �ع�����
            this.rollbackTransaction();
         }
         e.printStackTrace();
         throw new KANException( e );
      }

      try
      {
         // ��ְ��Ҫɾ����ӦStaffDTO
         if ( dimission )
         {
            //BaseAction.constantsInit( "initStaffForDelete", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
            //BaseAction.constantsInit( "initStaffBaseView", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
            //BaseAction.constantsInit( "initPosition", staffVO.getAccountId() );
            //BaseAction.constantsInit( "initBranch", staffVO.getAccountId() );
            final SyncTask syncTask = new SyncTask();
            syncTask.addTask( "initStaffForDelete", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
            syncTask.addTask( "initStaffBaseView", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
            syncTask.addTask( "initPosition", staffVO.getAccountId() );
            syncTask.addTask( "initBranch", staffVO.getAccountId() );
            syncTask.start();
         }
         else
         {
            if ( staffVO != null )
            {
               //BaseAction.constantsInit( "initStaff", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
               //BaseAction.constantsInit( "initStaffBaseView", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
               //BaseAction.constantsInit( "initPosition", staffVO.getAccountId() );
               //BaseAction.constantsInit( "initBranch", staffVO.getAccountId() );
               final SyncTask syncTask = new SyncTask();
               syncTask.addTask( "initStaff", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
               syncTask.addTask( "initStaffBaseView", new String[] { staffVO.getAccountId(), staffVO.getStaffId() } );
               syncTask.addTask( "initPosition", staffVO.getAccountId() );
               syncTask.addTask( "initBranch", staffVO.getAccountId() );
               syncTask.start();
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return i;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   public int updateEmployeeContract( final EmployeeContractVO employeeContractVO, final List< ConstantVO > constantVOs ) throws KANException
   {
      try
      {
         // ��������
         this.startTransaction();

         // ��ͬʱ�ޣ�������
         int monthGap = KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ), KANUtil.formatDate( employeeContractVO.getEndDate(), "yyyy-MM-dd" ) );
         employeeContractVO.setPeriod( String.valueOf( monthGap ) );

         // Update EmployeeContractVO
         ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVO );

         // Get EmployeeContractPropertyVO List
         final List< Object > objects = this.employeeContractPropertyDao.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );

         // ������ɾ�������ͬ��Ӧ��Properties
         if ( objects != null )
         {
            for ( Object object : objects )
            {
               this.employeeContractPropertyDao.deleteEmployeeContractProperty( ( ( EmployeeContractPropertyVO ) object ).getPropertyId() );
            }
         }

         // ��������Ͷ���ͬ�����Э���Ӧ��Properties
         if ( constantVOs != null && constantVOs.size() > 0 )
         {
            for ( Object constantVO : constantVOs )
            {
               // ����EmployeeContractPropertyVO����
               final EmployeeContractPropertyVO employeeContractPropertyVO = new EmployeeContractPropertyVO();
               employeeContractPropertyVO.setContractId( employeeContractVO.getContractId() );
               employeeContractPropertyVO.setPropertyName( BeanUtils.getProperty( constantVO, "propertyName" ) );
               employeeContractPropertyVO.setPropertyValue( BeanUtils.getProperty( constantVO, "content" ) );
               employeeContractPropertyVO.setStatus( EmployeeContractPropertyVO.TRUE );
               employeeContractPropertyVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractPropertyVO.setModifyBy( employeeContractVO.getModifyBy() );
               this.employeeContractPropertyDao.insertEmployeeContractProperty( employeeContractPropertyVO );
            }
         }

         // �ύ����
         this.commitTransaction();
         // ͬ��΢��
         BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
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
   public int submitEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;
      try
      {
         this.startTransaction();
         rows = submitEmployeeContract_nt( employeeContractVO );
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   // TODO ��Ҫ���������ύ
   public int submitEmployeeContract_nt( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ���¼�¼��
      int rows = 0;

      // ��ͬ�Ƿ�����
      boolean extended = false;
      // ��ʼ����ǰʹ�����Ͷ���ͬ����������Pending�ģ�
      final EmployeeContractVO tempEmployeeContractVO = getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
      tempEmployeeContractVO.setLocale( employeeContractVO.getLocale() );

      if ( ( KANUtil.filterEmpty( tempEmployeeContractVO.getEndDate() ) != null && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
            || ( KANUtil.filterEmpty( tempEmployeeContractVO.getEndDate() ) != null && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null && KANUtil.getDays( KANUtil.createDate( employeeContractVO.getEndDate() ) ) > KANUtil.getDays( KANUtil.createDate( tempEmployeeContractVO.getEndDate() ) ) ) )
      {
         extended = true;
      }

      if ( !WorkflowService.isPassObject( employeeContractVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( employeeContractVO );
         // Ȩ��Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // ��ʶ - ��ǰ��ͬ�Ƿ�����������״̬
         boolean approved = employeeContractVO.getStatus().equals( "3" ) || employeeContractVO.getStatus().equals( "5" ) || employeeContractVO.getStatus().equals( "6" );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContract_nt" );
            historyVO.setObjectId( employeeContractVO.getContractId() );

            final List< Object > employeeContractSBVOs = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( employeeContractVO.getContractId() );
            if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
            {
               for ( Object o : employeeContractSBVOs )
               {
                  if ( KANUtil.filterEmpty( employeeContractVO.getSbSolutionIds() ) == null )
                  {
                     employeeContractVO.setSbSolutionIds( ( ( EmployeeContractSBVO ) o ).getSbSolutionId() );
                  }
                  else
                  {
                     employeeContractVO.setSbSolutionIds( employeeContractVO.getSbSolutionIds() + "," + ( ( EmployeeContractSBVO ) o ).getSbSolutionId() );
                  }
               }
            }
            // ��׼״̬
            employeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "3" );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // �˻�״̬
            tempEmployeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "4" );
            final String failObject = KANUtil.toJSONObject( tempEmployeeContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.employee.EmployeeContractVO" );
            workflowActualDTO.getWorkflowActualVO().setObjectId( employeeContractVO.getContractId() );
            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractVO );

            // ״̬��Ϊ�����
            employeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "2" );

            if ( !approved )
            {
               updateEmployeeContract_nt( employeeContractVO, false );
            }

            return -1;
         }
         else
         {
            employeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "3" );

            // ����ͨ���޸Ĺ�Ա״̬Ϊ��ְ
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            employeeVO.setStatus( "1" );
            this.getEmployeeDao().updateEmployee( employeeVO );

            // �����ͬ����ǩ��
            if ( KANUtil.filterEmpty( employeeContractVO.getMasterContractId() ) != null )
            {
               updateOriginalEmployeeContract( employeeContractVO.getMasterContractId() );
            }

            rows = updateEmployeeContract_nt( employeeContractVO, false );

            // �����ͬ�����ڵ�
            if ( extended )
            {
               extendEmployeeContract( employeeContractVO.getContractId(), employeeContractVO.getEndDate() );
            }

            // ���ĺ�ͬ
            return rows;
         }
      }

      // �������׼״̬�����ύ���˻صģ������κβ�����
      if ( KANUtil.filterEmpty( employeeContractVO.getStatus() ) != null && employeeContractVO.getStatus().equals( "3" ) && employeeContractVO.getHistoryVO() != null
            && KANUtil.filterEmpty( employeeContractVO.getHistoryVO().getTempStatus() ) != null && "2".equals( employeeContractVO.getHistoryVO().getTempStatus() ) )
      {
         return 0;
      }

      // �����ͬ����ǩ��
      if ( KANUtil.filterEmpty( employeeContractVO.getMasterContractId() ) != null )
      {
         updateOriginalEmployeeContract( employeeContractVO.getMasterContractId() );
      }

      rows = updateEmployeeContract_nt( employeeContractVO, false );

      // �����ͬ�����ڵ�
      if ( extended )
      {
         extendEmployeeContract( employeeContractVO.getContractId(), employeeContractVO.getEndDate() );
      }

      return rows;
   }

   // ��ǩ�����ԭʼ��ͬ�����Ϣ
   // Added by Kevin Jin at 2014-08-27
   private void updateOriginalEmployeeContract( final String masterContractId ) throws KANException
   {
      if ( KANUtil.filterEmpty( masterContractId ) != null )
      {
         final EmployeeContractVO originalEmployeeContractVO = this.getEmployeeContractVOByContractId( masterContractId );

         if ( originalEmployeeContractVO != null )
         {
            // ԭʼ�Ͷ���ͬ���
            originalEmployeeContractVO.setIsContinued( "1" );

            // ��Ӷ״̬���
            if ( KANUtil.filterEmpty( originalEmployeeContractVO.getEmployStatus() ) != null && KANUtil.filterEmpty( originalEmployeeContractVO.getEmployStatus() ).equals( "1" ) )
            {
               originalEmployeeContractVO.setEmployStatus( "2" );
            }

            // ��ͬ״̬���
            if ( KANUtil.filterEmpty( originalEmployeeContractVO.getStatus() ) != null && !KANUtil.filterEmpty( originalEmployeeContractVO.getStatus() ).equals( "7" ) )
            {
               originalEmployeeContractVO.setStatus( "7" );
            }

            updateEmployeeContract( originalEmployeeContractVO, null );

            // ԭʼ�Ͷ���ͬ�籣���
            final List< Object > employeeContractSBVOs = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( masterContractId );

            if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
            {
               for ( Object employeeContractSBVOObject : employeeContractSBVOs )
               {
                  final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
                  employeeContractSBVO.setEndDate( originalEmployeeContractVO.getEndDate() );
                  this.getEmployeeContractSBDao().updateEmployeeContractSB( employeeContractSBVO );
               }
            }

            // ԭʼ�Ͷ���ͬ�̱����
            final List< Object > employeeContractCBVOs = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( masterContractId );

            if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
            {
               for ( Object employeeContractCBVOObject : employeeContractCBVOs )
               {
                  final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObject;
                  employeeContractCBVO.setEndDate( originalEmployeeContractVO.getEndDate() );
                  this.getEmployeeContractCBDao().updateEmployeeContractCB( employeeContractCBVO );
               }
            }
         }
      }
   }

   // �Ͷ���ͬ���ڣ������Ͷ���ͬ�����Ϣ
   // Added by Kevin Jin at 2014-09-10
   private void extendEmployeeContract( final String contractId, final String endDate ) throws KANException
   {
      // н�귽������
      final List< Object > employeeContractSalaryVOs = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractId( contractId );

      if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
            employeeContractSalaryVO.setEndDate( endDate );
            this.getEmployeeContractSalaryDao().updateEmployeeContractSalary( employeeContractSalaryVO );
         }
      }

      // �Ӱ���������
      final List< Object > employeeContractOTVOs = this.getEmployeeContractOTDao().getEmployeeContractOTVOsByContractId( contractId );

      if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
      {
         for ( Object employeeContractOTVOObject : employeeContractOTVOs )
         {
            final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) employeeContractOTVOObject;
            employeeContractOTVO.setEndDate( endDate );
            this.getEmployeeContractOTDao().updateEmployeeContractOT( employeeContractOTVO );
         }
      }

      // ������������
      final List< Object > employeeContractOtherVOs = this.getEmployeeContractOtherDao().getEmployeeContractOtherVOsByContractId( contractId );

      if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
      {
         for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
         {
            final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) employeeContractOtherVOObject;
            employeeContractOtherVO.setEndDate( endDate );
            this.getEmployeeContractOtherDao().updateEmployeeContractOther( employeeContractOtherVO );
         }
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   public int deleteEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ���ɾ��EmployeeContractVO
      final EmployeeContractVO employeeContractVOTemp = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
      employeeContractVOTemp.setModifyBy( employeeContractVO.getModifyBy() );
      employeeContractVOTemp.setModifyDate( new Date() );
      employeeContractVOTemp.setDeleted( EmployeeContractVO.FALSE );
      int count = ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVOTemp );
      // ͬ��΢��
      BaseAction.syncWXContacts( employeeContractVO.getEmployeeId(), true );
      return count;
   }

   @Override
   public List< Object > getEmployeeContractVOsByEmployeeId( final String employeeId ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByEmployeeId( employeeId );
   }

   @Override
   public List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByCondition( employeeContractVO );
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByClientId( final EmployeeContractBaseView employeeContractBaseView ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractBaseViewsByClientId( employeeContractBaseView );
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractBaseViewsByAccountId( accountId );
   }

   @Override
   // Flag(1:���㣬2:�籣��3:�̱�)
   public List< ServiceContractDTO > getServiceContractDTOsByCondition( final EmployeeContractVO employeeContractVO, final String flag ) throws KANException
   {
      // ��ʼ��ServiceContractDTO List
      final List< ServiceContractDTO > serviceContractDTOs = new ArrayList< ServiceContractDTO >();

      // �ο�ʱ�䣬��Ҫ���ڽ����ĺ�ͬ�����ǰ��3����
      employeeContractVO.setBufferDate( KANUtil.formatDate( KANUtil.getDate( KANUtil.getFirstDate( employeeContractVO.getMonthly() ), 0, -3, 0 ), "yyyy-MM-dd" ) );

      // ����������ȡEmployeeContractVO List
      List< Object > employeeContractVOs = null;
      if ( flag != null )
      {
         // ��ʼ��������������ķ���Э��
         if ( flag.trim().equals( FLAG_SETTLEMENT ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getSettlementEmployeeContractVOsByCondition( employeeContractVO );
         }
         // ��ʼ�������籣�����ķ���Э��
         else if ( flag.trim().equals( FLAG_SB ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getSBEmployeeContractVOsByCondition( employeeContractVO );
         }
         // ��ʼ�������̱������ķ���Э��
         else if ( flag.trim().equals( FLAG_CB ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getCBEmployeeContractVOsByCondition( employeeContractVO );
         }
      }

      // �������EmployeeContractVO List���ݣ�����
      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            // ��ʼ��EmployeeContractVO
            final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

            // ���÷����£�Ԥ�ƣ���������ȡ����
            tempEmployeeContractVO.setMonthly( employeeContractVO.getMonthly() );

            // ��ʼ��EmployeeVO
            final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( tempEmployeeContractVO.getEmployeeId() );

            logger.info( "Employee Contract Loading Start - " + tempEmployeeContractVO.getContractId() + " / " + employeeVO.getNameZH() );

            // ��ʼ��ClientVO
            final ClientVO clientVO = this.getClientDao().getClientVOByClientId( tempEmployeeContractVO.getClientId() );

            if ( employeeVO != null && clientVO != null )
            {
               // ��ʼ��ServiceContractDTO����
               final ServiceContractDTO serviceContractDTO = new ServiceContractDTO();

               /** װ��DTO���ݶ��� */
               // װ��EmployeeContractVO
               serviceContractDTO.setEmployeeContractVO( tempEmployeeContractVO );

               // װ��EmployeeVO
               serviceContractDTO.setEmployeeVO( employeeVO );

               // װ��ClientVO
               serviceContractDTO.setClientVO( clientVO );

               // װ�ؽ�����������
               if ( flag.trim().equals( FLAG_SETTLEMENT ) )
               {
                  // װ�ؿͻ����񶩵�
                  serviceContractDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() ) );

                  // װ���籣
                  fetchSB( serviceContractDTO, tempEmployeeContractVO );

                  // װ���籣����
                  fetchSBAdjustment( serviceContractDTO, tempEmployeeContractVO );

                  // װ���̱�
                  fetchCB( serviceContractDTO, tempEmployeeContractVO );

                  // װ��н�ʷ���
                  fetchEmployeeContractSalary( serviceContractDTO, tempEmployeeContractVO );

                  // װ���������
                  fetchEmployeeContractLeave( serviceContractDTO, tempEmployeeContractVO );

                  // װ�ؼӰ�����
                  fetchEmployeeContractOT( serviceContractDTO, tempEmployeeContractVO );

                  // װ����������
                  fetchEmployeeContractOther( serviceContractDTO, tempEmployeeContractVO );

                  // װ�ؿ��ڱ�
                  fetchTimesheet( serviceContractDTO, tempEmployeeContractVO );

                  // װ��н�����ݣ����룩
                  fetchSalarys( serviceContractDTO, tempEmployeeContractVO );

                  // װ��ItemIds
                  fetchItemIds( serviceContractDTO, tempEmployeeContractVO );

                  // Set firstDayOfYearCircle��Set lastDayOfYearCircle
                  serviceContractDTO.setFirstDayOfYearCircle( firstDayOfYearCircle( serviceContractDTO ) );
                  serviceContractDTO.setLastDayOfYearCircle( lastDayOfYearCircle( serviceContractDTO ) );

                  //����ȫ�깤��Сʱ����ȫ��ȫ��Сʱ��
                  getTotalFullHoursOfYear( serviceContractDTO );
               }
               // װ���籣��������
               else if ( flag.trim().equals( FLAG_SB ) )
               {
                  // װ�ؿͻ����񶩵�
                  serviceContractDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() ) );

                  // װ�ؿͻ����񶩵� - �籣����
                  fetchClientOrderSB( serviceContractDTO, tempEmployeeContractVO );

                  tempEmployeeContractVO.setSbStartDate( employeeContractVO.getSbStartDate() );
                  tempEmployeeContractVO.setSbEndDate( employeeContractVO.getSbEndDate() );

                  //follow��������
                  tempEmployeeContractVO.setSbType( employeeContractVO.getSbType() );
                  tempEmployeeContractVO.setSbStatusArray( employeeContractVO.getSbStatusArray() );
                  // װ���籣����
                  fetchEmployeeContractSB( serviceContractDTO, tempEmployeeContractVO );
               }
               // װ���̱���������
               else if ( flag.trim().equals( FLAG_CB ) )
               {
                  // װ�ؿͻ����񶩵� - �̱�����
                  fetchClientOrderCB( serviceContractDTO, tempEmployeeContractVO );

                  // װ���̱�����
                  tempEmployeeContractVO.setCbStartDate( employeeContractVO.getCbStartDate() );
                  tempEmployeeContractVO.setCbEndDate( employeeContractVO.getCbEndDate() );
                  fetchEmployeeContractCB( serviceContractDTO, tempEmployeeContractVO );
               }

               serviceContractDTOs.add( serviceContractDTO );
            }

            logger.info( "Employee Contract Loading End - " + tempEmployeeContractVO.getContractId() + " / " + employeeVO.getNameZH() );
         }
      }

      return serviceContractDTOs;
   }

   // װ�ط���Э�� - н��
   private void fetchEmployeeContractSalary( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э��н�ʷ���
      final List< Object > employeeContractSalaryVOs = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            serviceContractDTO.getEmployeeContractSalaryVOs().add( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject );
         }
      }
   }

   // װ�ط���Э�� - �籣
   private void fetchEmployeeContractSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э���籣����
      final List< EmployeeContractSBDTO > employeeContractSBDTOs = this.getEmployeeContractSBService().getEmployeeContractSBDTOsByContractId( employeeContractVO );

      if ( employeeContractSBDTOs != null && employeeContractSBDTOs.size() > 0 )
      {
         for ( EmployeeContractSBDTO employeeContractSBDTO : employeeContractSBDTOs )
         {
            // ��ʼ��EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBDTO.getEmployeeContractSBVO();

            // �������´��籣�����Ƿ��Ѽ���
            final SBHeaderVO sbHeaderVO = new SBHeaderVO();
            sbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
            sbHeaderVO.setCorpId( employeeContractVO.getCorpId() );
            sbHeaderVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
            sbHeaderVO.setMonthly( employeeContractVO.getMonthly() );
            final List< Object > sbHeaderVOs = this.getSbHeaderService().getSBHeaderVOsByCondition( sbHeaderVO );

            if ( ( sbHeaderVOs == null || sbHeaderVOs.size() <= 0 )
                  && ( ( employeeContractSBVO.getStatus().equals( "2" ) && KANUtil.getDays( KANUtil.createCalendar( employeeContractSBVO.getStartDate() ) ) <= KANUtil.getDays( KANUtil.createCalendar( employeeContractVO.getSbStartDate() ) ) )
                        || employeeContractSBVO.getStatus().equals( "3" ) || ( employeeContractSBVO.getStatus().equals( "5" ) && KANUtil.getDays( KANUtil.createCalendar( employeeContractSBVO.getEndDate() ) ) >= KANUtil.getDays( KANUtil.createCalendar( employeeContractVO.getSbEndDate() ) ) ) ) )
            {
               serviceContractDTO.getEmployeeContractSBDTOs().add( employeeContractSBDTO );
            }
         }
      }
   }

   // װ�ط���Э�� - �̱�
   private void fetchEmployeeContractCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э���̱�����
      final List< Object > employeeContractCBVOs = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
      {
         for ( Object employeeContractCBVOObject : employeeContractCBVOs )
         {
            // ��ʼ��EmployeeContractCBVO
            final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObject;

            // �������´��̱������Ƿ��Ѽ���
            final CBHeaderVO cbHeaderVO = new CBHeaderVO();
            cbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
            cbHeaderVO.setCorpId( employeeContractVO.getCorpId() );
            cbHeaderVO.setEmployeeCBId( employeeContractCBVO.getEmployeeCBId() );
            cbHeaderVO.setMonthly( employeeContractVO.getMonthly() );
            final List< Object > cbHeaderVOs = this.getCbHeaderService().getCBHeaderVOsByCondition( cbHeaderVO );

            if ( ( cbHeaderVOs == null || cbHeaderVOs.size() <= 0 )
                  && ( ( employeeContractCBVO.getStatus().equals( "2" ) && KANUtil.getDays( KANUtil.createCalendar( employeeContractCBVO.getStartDate() ) ) <= KANUtil.getDays( KANUtil.createCalendar( employeeContractVO.getCbStartDate() ) ) )
                        || employeeContractCBVO.getStatus().equals( "3" ) || ( employeeContractCBVO.getStatus().equals( "5" ) && KANUtil.getDays( KANUtil.createCalendar( employeeContractCBVO.getEndDate() ) ) >= KANUtil.getDays( KANUtil.createCalendar( employeeContractVO.getCbEndDate() ) ) ) ) )
            {
               serviceContractDTO.getEmployeeContractCBVOs().add( employeeContractCBVO );
            }
         }
      }
   }

   // װ�ط���Э�� - ���
   private void fetchEmployeeContractLeave( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э���������
      final List< Object > employeeContractLeaveVOs = this.getEmployeeContractLeaveDao().getEmployeeContractLeaveVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
         {
            serviceContractDTO.getEmployeeContractLeaveVOs().add( ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject );
         }
      }
   }

   // װ�ط���Э�� - �Ӱ�
   private void fetchEmployeeContractOT( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э��Ӱ�����
      final List< Object > employeeContractOTVOs = this.getEmployeeContractOTDao().getEmployeeContractOTVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
      {
         for ( Object employeeContractOTVOObject : employeeContractOTVOs )
         {
            serviceContractDTO.getEmployeeContractOTVOs().add( ( EmployeeContractOTVO ) employeeContractOTVOObject );
         }
      }
   }

   // װ�ط���Э�� - ��������
   private void fetchEmployeeContractOther( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط���Э����������
      final List< Object > employeeContractOtherVOs = this.getEmployeeContractOtherDao().getEmployeeContractOtherVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
      {
         for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
         {
            serviceContractDTO.getEmployeeContractOtherVOs().add( ( EmployeeContractOtherVO ) employeeContractOtherVOObject );
         }
      }
   }

   // װ�ؿ��ڱ�
   private void fetchTimesheet( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // ��ʼ��TimesheetVO����
      final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
      timesheetHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      timesheetHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      timesheetHeaderVO.setContractId( employeeContractVO.getContractId() );

      // ���ն����趨�Ĺ����·ݻ�ȡTimesheet
      if ( clientOrderHeaderVO != null )
      {
         if ( clientOrderHeaderVO.getSalaryMonth() != null && clientOrderHeaderVO.getSalaryMonth().trim().equals( "2" ) )
         {
            timesheetHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -1 ) );
         }
         else if ( clientOrderHeaderVO.getSalaryMonth() != null && clientOrderHeaderVO.getSalaryMonth().trim().equals( "3" ) )
         {
            timesheetHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -2 ) );
         }
         else if ( clientOrderHeaderVO.getSalaryMonth() != null && clientOrderHeaderVO.getSalaryMonth().trim().equals( "4" ) )
         {
            timesheetHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), 1 ) );
         }
         else
         {
            timesheetHeaderVO.setMonthlyLimit( employeeContractVO.getMonthly() );
         }
      }

      timesheetHeaderVO.setWeekly( employeeContractVO.getWeekly() );
      // ״̬��3��������׼
      timesheetHeaderVO.setStatus( "3" );
      timesheetHeaderVO.setSortColumn( "monthly" );
      timesheetHeaderVO.setSortOrder( "DESC" );
      final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( timesheetHeaderVO );

      // װ��TimesheetDTO��һ������Э��ÿ�¶�Ӧһ�����ڱ�
      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         serviceContractDTO.setTimesheetDTO( timesheetDTOs.get( 0 ) );

         // װ�ؽ���
         for ( TimesheetDTO timesheetDTO : timesheetDTOs )
         {
            if ( timesheetDTO.getTimesheetAllowanceVOs() != null && timesheetDTO.getTimesheetAllowanceVOs().size() > 0 )
            {
               for ( TimesheetAllowanceVO timesheetAllowanceVO : timesheetDTO.getTimesheetAllowanceVOs() )
               {
                  EmployeeContractSalaryVO employeeContractSalaryVO = serviceContractDTO.getEmployeeContractSalaryVOByItemId( timesheetAllowanceVO.getItemId() );

                  if ( employeeContractSalaryVO == null )
                  {
                     employeeContractSalaryVO = new EmployeeContractSalaryVO();
                     employeeContractSalaryVO.setContractId( employeeContractVO.getContractId() );
                     employeeContractSalaryVO.setItemId( timesheetAllowanceVO.getItemId() );
                     employeeContractSalaryVO.setSalaryType( "1" );
                     employeeContractSalaryVO.setDivideType( "1" );
                     employeeContractSalaryVO.setResultCap( "0" );
                     employeeContractSalaryVO.setResultFloor( "0" );
                     employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
                     employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
                     employeeContractSalaryVO.setBase( timesheetAllowanceVO.getBase() );
                     employeeContractSalaryVO.setBaseFrom( "0" );
                     serviceContractDTO.getEmployeeContractSalaryVOs().add( employeeContractSalaryVO );
                  }
                  else
                  {
                     employeeContractSalaryVO.setBase( timesheetAllowanceVO.getBase() );
                  }
               }
            }
         }
      }
   }

   // �����·����ã�2:��һ�£�3:�϶��£�4:��һ�£�
   private int encodeMonthSetting( final String monthSetting )
   {
      if ( KANUtil.filterEmpty( monthSetting ) != null )
      {
         if ( KANUtil.filterEmpty( monthSetting ).equals( "2" ) )
         {
            return -1;
         }
         else if ( KANUtil.filterEmpty( monthSetting ).equals( "3" ) )
         {
            return -2;
         }
         else if ( KANUtil.filterEmpty( monthSetting ).equals( "4" ) )
         {
            return 1;
         }
      }

      return 0;
   }

   // װ�ص��빤�ʱ�
   private void fetchSalarys( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��Salary�·�
      String salaryMonth = employeeContractVO.getMonthly();
      // ��ʼ��SB�·�
      String sbMonth = employeeContractVO.getMonthly();
      // ��ʼ��CB�·�
      String cbMonth = employeeContractVO.getMonthly();

      if ( serviceContractDTO.getClientOrderHeaderVO() != null )
      {
         if ( KANUtil.filterEmpty( serviceContractDTO.getClientOrderHeaderVO().getSalaryMonth(), new String[] { "0", "1" } ) != null )
         {
            salaryMonth = KANUtil.getMonthly( salaryMonth, encodeMonthSetting( serviceContractDTO.getClientOrderHeaderVO().getSalaryMonth() ) );
         }

         if ( KANUtil.filterEmpty( serviceContractDTO.getClientOrderHeaderVO().getSbMonth(), new String[] { "0", "1" } ) != null )
         {
            sbMonth = KANUtil.getMonthly( sbMonth, encodeMonthSetting( serviceContractDTO.getClientOrderHeaderVO().getSalaryMonth() ) );
         }

         if ( KANUtil.filterEmpty( serviceContractDTO.getClientOrderHeaderVO().getCbMonth(), new String[] { "0", "1" } ) != null )
         {
            cbMonth = KANUtil.getMonthly( cbMonth, encodeMonthSetting( serviceContractDTO.getClientOrderHeaderVO().getSalaryMonth() ) );
         }
      }

      // װ��н�꣨״̬��2�����ύ��
      final SalaryHeaderVO salaryHeaderVO = new SalaryHeaderVO();
      salaryHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      salaryHeaderVO.setContractId( employeeContractVO.getContractId() );
      salaryHeaderVO.setOrderId( employeeContractVO.getOrderId() );
      salaryHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      salaryHeaderVO.setStatus( "2" );
      // н���·�
      salaryHeaderVO.setMonthly( salaryMonth );
      salaryHeaderVO.setItemTypes( "1,2,3,4,5" );

      List< SalaryDTO > salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // װ���籣
      salaryHeaderVO.setMonthly( sbMonth );
      salaryHeaderVO.setItemTypes( "7" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // װ���̱�
      salaryHeaderVO.setMonthly( cbMonth );
      salaryHeaderVO.setItemTypes( "8" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // װ������
      salaryHeaderVO.setMonthly( employeeContractVO.getMonthly() );
      salaryHeaderVO.setItemTypes( "10,11,12" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // ������ʵ������ʵ�����ʣ���Ҫ���ص��´���˰ǰ����
      if ( serviceContractDTO.getSalaryDTOs() != null && serviceContractDTO.getSalaryDTOs().size() > 0 )
      {
         for ( SalaryDTO salaryDTO : serviceContractDTO.getSalaryDTOs() )
         {
            if ( salaryDTO.getSalaryHeaderVO() != null && KANUtil.filterEmpty( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != null
                  && Double.valueOf( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != 0 )
            {
               // ��ȡPayment Header�е�˰ǰ����
               // ��ʼ��PaymentHeaderVO������������
               PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
               paymentHeaderVO.setCorpId( employeeContractVO.getCorpId() );
               paymentHeaderVO.setContractId( employeeContractVO.getContractId() );
               paymentHeaderVO.setMonthly( employeeContractVO.getMonthly() );
               // ��ȡ�½�״̬��PaymentHeaderVO
               paymentHeaderVO.setStatus( "2, 3" );
               final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

               if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
               {
                  for ( Object paymentHeaderVOObject : paymentHeaderVOs )
                  {
                     final PaymentHeaderVO tempPaymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

                     serviceContractDTO.addAddtionalBillAmountPersonal( tempPaymentHeaderVO.getAddtionalBillAmountPersonal() );
                     serviceContractDTO.addTaxAmountPersonal( tempPaymentHeaderVO.getTaxAmountPersonal() );
                  }
               }

               // ��ȡPayment Adjustment Header�е�˰ǰ����
               // ��ʼ��PaymentAdjustmentHeaderVO������������
               PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
               paymentAdjustmentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
               paymentAdjustmentHeaderVO.setCorpId( employeeContractVO.getCorpId() );
               paymentAdjustmentHeaderVO.setContractId( employeeContractVO.getContractId() );
               paymentAdjustmentHeaderVO.setMonthly( employeeContractVO.getMonthly() );
               // ��ȡ�½�״̬��PaymentHeaderVO
               paymentAdjustmentHeaderVO.setStatus( "3, 5" );
               final List< Object > paymentAdjustmentHeaderVOs = this.getPaymentAdjustmentHeaderDao().getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderVO );

               if ( paymentAdjustmentHeaderVOs != null && paymentAdjustmentHeaderVOs.size() > 0 )
               {
                  for ( Object paymentAdjustmentHeaderVOObject : paymentAdjustmentHeaderVOs )
                  {
                     final PaymentAdjustmentHeaderVO tempPaymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) paymentAdjustmentHeaderVOObject;

                     serviceContractDTO.addAddtionalBillAmountPersonal( tempPaymentAdjustmentHeaderVO.getAddtionalBillAmountPersonal() );
                     serviceContractDTO.addTaxAmountPersonal( tempPaymentAdjustmentHeaderVO.getTaxAmountPersonal() );
                  }
               }
            }
         }
      }
   }

   // װ��ItemIds
   private void fetchItemIds( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��OrderDetailVO
      final OrderDetailVO orderDetailVO = new OrderDetailVO();
      orderDetailVO.setAccountId( employeeContractVO.getAccountId() );
      orderDetailVO.setEmployeeContractId( employeeContractVO.getContractId() );
      orderDetailVO.setMonthly( employeeContractVO.getMonthly() );

      // ��ȡ�Ѿ�Post�����ϸ������ContractId��Monthly��
      final List< Object > orderDetailVOs = this.getOrderDetailDao().getOrderDetailVOsByCondition( orderDetailVO );

      if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
      {
         for ( Object object : orderDetailVOs )
         {
            serviceContractDTO.getExistItemIds().add( ( ( OrderDetailVO ) object ).getItemId() );
         }
      }
   }

   // װ�ط��񶩵� - �籣
   private void fetchClientOrderSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط��񶩵��̱�����
      final List< Object > clientOrderSBVOs = this.getClientOrderSBDao().getClientOrderSBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );

      if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
      {
         for ( Object clientOrderSBVOObject : clientOrderSBVOs )
         {
            serviceContractDTO.getClientOrderSBVOs().add( ( ClientOrderSBVO ) clientOrderSBVOObject );
         }
      }
   }

   // װ�ط��񶩵� - �̱�
   private void fetchClientOrderCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ����װ�ط��񶩵��̱�����
      final List< Object > clientOrderCBVOs = this.getClientOrderCBDao().getClientOrderCBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );

      if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
      {
         for ( Object clientOrderCBVOObject : clientOrderCBVOs )
         {
            serviceContractDTO.getClientOrderCBVOs().add( ( ClientOrderCBVO ) clientOrderCBVOObject );
         }
      }
   }

   // װ���籣
   private void fetchSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // ��ʼ��SBHeaderVO����
      final SBHeaderVO sbHeaderVO = new SBHeaderVO();
      sbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sbHeaderVO.setContractId( employeeContractVO.getContractId() );
      sbHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // ���ն����趨���籣�·ݻ�ȡSB
      if ( clientOrderHeaderVO != null )
      {
         if ( clientOrderHeaderVO.getSbMonth() != null && clientOrderHeaderVO.getSbMonth().trim().equals( "2" ) )
         {
            sbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -1 ) );
         }
         else if ( clientOrderHeaderVO.getSbMonth() != null && clientOrderHeaderVO.getSbMonth().trim().equals( "3" ) )
         {
            sbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -2 ) );
         }
         else if ( clientOrderHeaderVO.getSbMonth() != null && clientOrderHeaderVO.getSbMonth().trim().equals( "4" ) )
         {
            sbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), 1 ) );
         }
         else
         {
            sbHeaderVO.setMonthlyLimit( employeeContractVO.getMonthly() );
         }
      }

      // ״̬��4�����ύ
      sbHeaderVO.setStatus( "4" );
      serviceContractDTO.setSbDTOs( this.getSbHeaderService().getSBDTOsByCondition( sbHeaderVO ) );
   }

   // װ���籣����
   private void fetchSBAdjustment( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��SBAdjustmentHeaderVO����
      final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
      sbAdjustmentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sbAdjustmentHeaderVO.setContractId( employeeContractVO.getContractId() );
      sbAdjustmentHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // ״̬��3�����ύ
      sbAdjustmentHeaderVO.setStatus( "3" );
      serviceContractDTO.setSbAdjustmentDTOs( this.getSbAdjustmentHeaderService().getSBAdjustmentDTOsByCondition( sbAdjustmentHeaderVO ) );
   }

   // װ���̱�
   private void fetchCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // ��ʼ��CBHeaderVO����
      final CBHeaderVO cbHeaderVO = new CBHeaderVO();
      cbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      cbHeaderVO.setContractId( employeeContractVO.getContractId() );
      cbHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // ���ն����趨���̱��·ݻ�ȡCB
      if ( clientOrderHeaderVO != null )
      {
         if ( clientOrderHeaderVO.getCbMonth() != null && clientOrderHeaderVO.getCbMonth().trim().equals( "2" ) )
         {
            cbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -1 ) );
         }
         else if ( clientOrderHeaderVO.getCbMonth() != null && clientOrderHeaderVO.getCbMonth().trim().equals( "3" ) )
         {
            cbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), -2 ) );
         }
         else if ( clientOrderHeaderVO.getCbMonth() != null && clientOrderHeaderVO.getCbMonth().trim().equals( "4" ) )
         {
            cbHeaderVO.setMonthlyLimit( KANUtil.getMonthly( employeeContractVO.getMonthly(), 1 ) );
         }
         else
         {
            cbHeaderVO.setMonthlyLimit( employeeContractVO.getMonthly() );
         }
      }

      // ״̬��4�����ύ
      cbHeaderVO.setStatus( "4" );
      serviceContractDTO.setCbDTOs( this.getCbHeaderService().getCBDTOsByCondition( cbHeaderVO ) );
   }

   @Override
   public EmployeeContractBaseView getEmployeeContractBaseViewByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractBaseViewByContractId( contractId );
   }

   @Override
   public List< Object > getEmployeeContractVOsByClientId( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByClientId( employeeContractVO );
   }

   @Override
   public List< Object > getEmployeeContractsDuringService( final List< String > selectedIdList ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractsDuringService( selectedIdList );
   }

   @Override
   public List< Object > getServiceEmployeeContractVOsByOrderId( final String orderId ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getServiceEmployeeContractVOsByOrderId( orderId );
   }

   // Reviewed by Kevin Jin at 2013-11-15
   private HistoryVO generateHistoryVO( final EmployeeContractVO employeeContractVO )
   {
      final HistoryVO history = employeeContractVO.getHistoryVO();
      history.setModuleId( KANConstants.getModuleIdByAccessAction( history.getAccessAction() ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeContractVOByContractId" );
      // ��ʾ�ǹ�������
      history.setObjectType( "2" );
      history.setAccountId( employeeContractVO.getAccountId() );
      history.setRemark5( employeeContractVO.getStatus() );
      history.setNameZH( employeeContractVO.getNameZH() );
      history.setNameEN( employeeContractVO.getNameEN() );
      history.setOwner( employeeContractVO.getOwner() );
      return history;
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByEmployeeId( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractBaseViewsByEmployeeId( employeeContractVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2014-06-02
   public boolean checkContractConflict( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      //���û��EntityId�򲻼��ʱ���ͻ��
      if ( KANUtil.filterEmpty( employeeContractVO.getEntityId(), "0" ) == null )
      {
         return false;
      }

      // ��ʼ��EmployeeContractVO
      EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
      tempEmployeeContractVO.setClientId( employeeContractVO.getClientId() );
      tempEmployeeContractVO.setCorpId( employeeContractVO.getCorpId() );
      tempEmployeeContractVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      tempEmployeeContractVO.setEntityId( employeeContractVO.getEntityId() );
      tempEmployeeContractVO.setAccountId( employeeContractVO.getAccountId() );
      tempEmployeeContractVO.setFlag( employeeContractVO.getFlag() );
      final List< Object > employeeContractVOs = getEmployeeContractVOsByCondition( tempEmployeeContractVO );

      boolean flag = false;
      for ( Object employeeContractVOObject : employeeContractVOs )
      {
         tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

         // �ų���ǰ���Ͷ���ͬ��������Э��
         if ( KANUtil.filterEmpty( employeeContractVO.getContractId() ) != null
               && KANUtil.filterEmpty( employeeContractVO.getContractId() ).equals( tempEmployeeContractVO.getContractId() ) )
         {
            continue;
         }

         // 

         // Modify by siuvan 2014-08-27
         String tempContractStartDate = tempEmployeeContractVO.getStartDate();
         String tempContractEndDate = tempEmployeeContractVO.getEndDate();

         // ���������ְ����
         if ( KANUtil.filterEmpty( tempEmployeeContractVO.getResignDate() ) != null )
         {
            tempContractEndDate = tempEmployeeContractVO.getResignDate();
         }

         /*add Ian ������޹̶����޺�ͬ���Ͳ�����ͬһ����������´����µĺ�ͬ,����״̬����
         if ( KANUtil.filterEmpty( tempContractEndDate ) == null && employeeContractVO.getOrderId() != null
               && employeeContractVO.getOrderId().equals( tempEmployeeContractVO.getOrderId() ) && !"1".equals( tempEmployeeContractVO.getStatus() ) )
         {
            flag = true;
            break;
         }*/

         // ʱ�䲻�ص��Ƚ�
         if ( KANUtil.filterEmpty( employeeContractVO.getStartDate() ) != null && KANUtil.filterEmpty( employeeContractVO.getEndDate() ) != null
               && KANUtil.filterEmpty( tempContractStartDate ) != null && KANUtil.filterEmpty( tempContractEndDate ) != null )
         {
            if ( KANUtil.getDays( KANUtil.createDate( employeeContractVO.getStartDate() ) ) >= KANUtil.getDays( KANUtil.createDate( tempContractEndDate ) )
                  || KANUtil.getDays( KANUtil.createDate( employeeContractVO.getEndDate() ) ) <= KANUtil.getDays( KANUtil.createDate( tempContractStartDate ) ) )
            {
               flag = false;
            }
            else
            {
               flag = true;
               break;
            }
         }
      }

      return flag;
   }

   /**  
    * Continue Employee Contract
    * ��ǩ����Э��/�Ͷ���ͬ
    * 
    * @param employeeContractVO
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-08-18
   public int continueEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // ��ʼ��Ӱ���¼��
      int count = 0;

      // ��ȡMasterContractId
      final String masterContractId = employeeContractVO.getMasterContractId();

      count = ( ( EmployeeContractDao ) getDao() ).insertEmployeeContract( employeeContractVO );

      if ( count > 0 )
      {
         // ����CB
         final List< Object > employeeContractCBs = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( masterContractId );

         if ( employeeContractCBs != null && employeeContractCBs.size() > 0 )
         {
            for ( Object object : employeeContractCBs )
            {
               final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) object;
               employeeContractCBVO.setEmployeeCBId( "" );
               employeeContractCBVO.setContractId( employeeContractVO.getContractId() );
               employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractCBVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractCBVO.setCreateDate( new Date() );
               employeeContractCBVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractCBVO.setModifyDate( new Date() );
               this.getEmployeeContractCBDao().insertEmployeeContractCB( employeeContractCBVO );
            }
         }

         // ����Leave
         final List< Object > employeeContractLeaves = this.getEmployeeContractLeaveDao().getEmployeeContractLeaveVOsByContractId( masterContractId );

         if ( employeeContractLeaves != null && employeeContractLeaves.size() > 0 )
         {
            for ( Object object : employeeContractLeaves )
            {
               final EmployeeContractLeaveVO employeeContractLeaveVO = ( EmployeeContractLeaveVO ) object;
               employeeContractLeaveVO.setEmployeeLeaveId( "" );
               employeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
               employeeContractLeaveVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractLeaveVO.setCreateDate( new Date() );
               employeeContractLeaveVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractLeaveVO.setModifyDate( new Date() );
               this.getEmployeeContractLeaveDao().insertEmployeeContractLeave( employeeContractLeaveVO );
            }
         }

         // ����OT
         final List< Object > employeeContractOTs = this.getEmployeeContractOTDao().getEmployeeContractOTVOsByContractId( masterContractId );

         if ( employeeContractOTs != null && employeeContractOTs.size() > 0 )
         {
            for ( Object object : employeeContractOTs )
            {
               final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) object;
               employeeContractOTVO.setEmployeeOTId( "" );
               employeeContractOTVO.setContractId( employeeContractVO.getContractId() );
               employeeContractOTVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractOTVO.setEndDate( employeeContractVO.getEndDate() );
               employeeContractOTVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractOTVO.setCreateDate( new Date() );
               employeeContractOTVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractOTVO.setModifyDate( new Date() );
               this.getEmployeeContractOTDao().insertEmployeeContractOT( employeeContractOTVO );
            }
         }

         // ����Other
         final List< Object > employeeContractOthers = this.getEmployeeContractOtherDao().getEmployeeContractOtherVOsByContractId( masterContractId );

         if ( employeeContractOthers != null && employeeContractOthers.size() > 0 )
         {
            for ( Object object : employeeContractOthers )
            {
               final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) object;
               employeeContractOtherVO.setEmployeeOtherId( "" );
               employeeContractOtherVO.setContractId( employeeContractVO.getContractId() );
               employeeContractOtherVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractOtherVO.setEndDate( employeeContractVO.getEndDate() );
               employeeContractOtherVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractOtherVO.setCreateDate( new Date() );
               employeeContractOtherVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractOtherVO.setModifyDate( new Date() );
               this.getEmployeeContractOtherDao().insertEmployeeContractOther( employeeContractOtherVO );
            }
         }

         // ����Salary
         final List< Object > employeeContractSalarys = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractId( masterContractId );

         if ( employeeContractSalarys != null && employeeContractSalarys.size() > 0 )
         {
            for ( Object object : employeeContractSalarys )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) object;
               employeeContractSalaryVO.setEmployeeSalaryId( "" );
               employeeContractSalaryVO.setContractId( employeeContractVO.getContractId() );
               employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
               employeeContractSalaryVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractSalaryVO.setCreateDate( new Date() );
               employeeContractSalaryVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractSalaryVO.setModifyDate( new Date() );
               this.getEmployeeContractSalaryDao().insertEmployeeContractSalary( employeeContractSalaryVO );
            }
         }

         // ����SB
         final List< Object > employeeContractSBs = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( masterContractId );

         if ( employeeContractSBs != null && employeeContractSBs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : employeeContractSBs )
            {
               final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
               // ��ȡ�Ͷ���ͬ�籣ID
               final String employeeSBId = employeeContractSBVO.getEmployeeSBId();

               employeeContractSBVO.setEmployeeSBId( "" );
               employeeContractSBVO.setContractId( employeeContractVO.getContractId() );
               employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractSBVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractSBVO.setCreateDate( new Date() );
               employeeContractSBVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractSBVO.setModifyDate( new Date() );
               this.getEmployeeContractSBDao().insertEmployeeContractSB( employeeContractSBVO );

               // ����SBDetail
               final List< Object > employeeContractSBDetails = this.getEmployeeContractSBDetailDao().getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );

               if ( employeeContractSBDetails != null && employeeContractSBDetails.size() > 0 )
               {
                  for ( Object employeeContractSBDetailObject : employeeContractSBDetails )
                  {
                     final EmployeeContractSBDetailVO employeeContractSBDetailVO = ( EmployeeContractSBDetailVO ) employeeContractSBDetailObject;
                     employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                     employeeContractSBDetailVO.setCreateBy( employeeContractVO.getCreateBy() );
                     employeeContractSBDetailVO.setCreateDate( new Date() );
                     employeeContractSBDetailVO.setModifyBy( employeeContractVO.getModifyBy() );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     this.getEmployeeContractSBDetailDao().insertEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }
               }
            }
         }
      }

      return count;
   }

   /**  
    * Ա����ְ
    * 1�������Ƿ�����Ч����Э���ж��Ƿ���Ҫ�޸Ĺ�Ա״̬
    * 2������һ���춯����
    * 3��������־
    * @param employeeContractVO
    * @return
    * @throws KANException
    */
   private int dimissionEmployeeContract( final EmployeeContractVO employeeContractVO, final StaffVO staffVO ) throws KANException
   {
      // ��ѯ��Ա��Ӧ��Ч����Э��
      final EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
      tempEmployeeContractVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      tempEmployeeContractVO.setAccountId( employeeContractVO.getAccountId() );
      // ״ֻ̬��Ϊ����׼�������Ѹ��¡������鵵��
      tempEmployeeContractVO.setStatus( "3, 5, 6" );

      final List< Object > employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByCondition( tempEmployeeContractVO );

      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            final EmployeeContractVO tempEmployeeContractVO1 = ( EmployeeContractVO ) employeeContractVOObject;

            // ��ְʱ��
            final String resignDate = employeeContractVO.getResignDate();
            // ����Э�鿪ʼʱ��
            final String startDate = tempEmployeeContractVO1.getStartDate();
            // ����Э�����ʱ��
            final String endDate = tempEmployeeContractVO1.getEndDate();

            if ( KANUtil.filterEmpty( startDate ) == null || KANUtil.filterEmpty( endDate ) == null || KANUtil.filterEmpty( resignDate ) == null )
            {
               continue;
            }

            if ( java.sql.Date.valueOf( endDate ).before( java.sql.Date.valueOf( startDate ) ) || java.sql.Date.valueOf( endDate ).equals( java.sql.Date.valueOf( resignDate ) ) )
            {
               return 0;
            }
         }
      }

      final EmployeeVO employeeVO = this.employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
      // ����Ч����Э���޸Ĺ�Ա״̬Ϊ��ְ
      employeeVO.setStatus( "3" );
      // �������ֵ
      /*employeeVO.set_tempPositionIds( "" );
      employeeVO.set_tempBranchIds( "" );
      employeeVO.set_tempParentBranchIds( "" );
      employeeVO.set_tempParentPositionIds( "" );
      employeeVO.set_tempParentPositionOwners( "" );
      employeeVO.set_tempParentPositionBranchIds( "" );
      employeeVO.set_tempPositionLocationIds( "" );
      employeeVO.set_tempPositionGradeIds( "" );*/
      employeeVO.setModifyBy( employeeContractVO.getModifyBy() );
      employeeVO.setModifyDate( new Date() );
      if ( employeeVO != null && !"2".equals( employeeVO.getHireAgain() ) )
      {
         employeeVO.setHireAgain( employeeContractVO.getHireAgain() );
      }

      if ( staffVO != null && staffVO.getStaffId() != null )
      {
         // ɾ��Ա����ְλ�Ķ�Ӧ��ϵǰ�����춯��ʷ
         List< PositionDTO > positionList = KANConstants.getKANAccountConstants( staffVO.getAccountId() ).getPositionDTOsByStaffId( staffVO.getStaffId() );
         for ( PositionDTO positionDTO : positionList )
         {
            PositionVO positionVO = positionDTO.getPositionVO();
            if ( positionVO != null )
            {
               PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
               positionStaffRelationVO.setStaffId( staffVO.getStaffId() );
               positionStaffRelationVO.setPositionId( positionVO.getPositionId() );
               PositionStaffRelationVO positionStaffRelation = positionStaffRelationDao.getPositionStaffRelationVOByStaffAndPositionId( positionStaffRelationVO );

               final EmployeePositionChangeVO employeePositionChangeVO = new EmployeePositionChangeVO();
               employeePositionChangeVO.setEmployeeId( employeeContractVO.getEmployeeId() );
               employeePositionChangeVO.setStaffId( staffVO.getStaffId() );
               employeePositionChangeVO.setAccountId( staffVO.getAccountId() );
               employeePositionChangeVO.setCorpId( employeeContractVO.getCorpId() );
               employeePositionChangeVO.setOldBranchId( positionVO.getBranchId() );
               employeePositionChangeVO.setOldPositionId( positionVO.getPositionId() );
               employeePositionChangeVO.setOldStartDate( KANUtil.filterEmpty( positionStaffRelation.decodeDate( positionStaffRelation.getModifyDate() ) ) );
               employeePositionChangeVO.setOldEndDate( employeeContractVO.getResignDate() );
               employeePositionChangeVO.setNewStartDate( null );
               employeePositionChangeVO.setNewEndDate( null );
               employeePositionChangeVO.setNewBranchId( "0" );
               employeePositionChangeVO.setNewPositionId( "0" );
               employeePositionChangeVO.setPositionStatus( "4" );
               employeePositionChangeVO.setStatus( "5" );
               employeePositionChangeVO.setEffectiveDate( employeeContractVO.getResignDate() );
               employeePositionChangeVO.setIsImmediatelyEffective( "1" );
               employeePositionChangeVO.setCreateBy( employeeContractVO.getModifyBy() );
               employeePositionChangeVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeePositionChangeVO.setOldStaffPositionRelationId( positionStaffRelation.getRelationId() );
               employeePositionChangeVO.setRemark3( "10" );

               setChangeInfo( employeePositionChangeVO );
               employeePositionChangeDao.insertEmployeePositionChange( employeePositionChangeVO );
               BaseAction.syncWXContacts( employeeContractVO.getEmployeeId(), true );

               final EmployeePositionChangeVO employeePositionChangeVO_DB = this.getEmployeePositionChangeDao().getEmployeePositionChangeVOByPositionChangeId( employeePositionChangeVO.getPositionChangeId() );
               employeePositionChangeVO_DB.setLocale( employeeContractVO.getLocale() );

               // ������־
               final LogVO log = new LogVO();
               log.setEmployeeId( employeePositionChangeVO_DB.getEmployeeId() );
               log.setChangeReason( employeePositionChangeVO_DB.getRemark3() );
               log.setEmployeeNameZH( employeePositionChangeVO_DB.getEmployeeNameZH() );
               log.setEmployeeNameEN( employeePositionChangeVO_DB.getEmployeeNameEN() );
               log.setType( Operate.SUBMIT.getIndex() + "" );
               log.setModule( EmployeePositionChangeVO.class.getCanonicalName() );
               log.setContent( employeePositionChangeVO_DB == null ? "" : JsonMapper.toLogJson( employeePositionChangeVO_DB ) );
               log.setIp( employeeContractVO.getIp() );
               log.setOperateTime( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
               log.setOperateBy( employeeContractVO.getDecodeModifyBy() );
               log.setpKey( employeePositionChangeVO_DB.getPositionChangeId() );
               log.setRemark( "System auto generate" );

               this.getLogDao().insertLog( log );
            }
         }

         // ɾ��Ա����ְλ�Ķ�Ӧ��ϵ
         this.positionStaffRelationDao.deletePositionStaffRelationByStaffId( staffVO.getStaffId() );

         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeeVO.getAccountId() );
         // ��<ֱ�߻㱨��>��������,add by siuvan 2015-06-18
         final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeVO.getEmployeeId() );
         if ( staffDTOs != null && staffDTOs.size() == 1 )
         {
            String result = "";
            final PositionVO positionVO = accountConstants.getMainPositionVOByStaffId( staffDTOs.get( 0 ).getStaffVO().getStaffId() );
            if ( positionVO != null )
            {
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getParentPositionId() );
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO staffDTO : parentStaffDTOs )
                  {
                     final StaffVO parentStaffVO = staffDTO.getStaffVO();
                     if ( parentStaffVO != null )
                     {
                        final PositionVO parentMainPositionVO = accountConstants.getMainPositionVOByStaffId( parentStaffVO.getStaffId() );
                        if ( parentMainPositionVO != null )
                        {
                           if ( KANUtil.filterEmpty( result ) == null )
                              result = accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), true );
                           else
                              result = result + "��" + accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), true );
                        }
                     }
                  }
               }
            }

            if ( KANUtil.filterEmpty( result ) != null )
               employeeVO.setRemark5( result );

         }

         // ͣ���û�
         final UserVO userVO = this.userDao.getUserVOByStaffId( staffVO.getStaffId() );
         if ( userVO != null )
         {
            userVO.setStatus( "2" );
            userVO.setDeleted( "2" );
            userDao.updateUser( userVO );
         }

         // ��staff��Ϊ��ְ��add by siuvan 2015-06-18
         staffVO.setStatus( "3" );
         this.getStaffDao().updateStaff( staffVO );
      }

      // ����employeeVO
      this.employeeDao.updateEmployee( employeeVO );

      return 1;
   }

   @Override
   // ��ְ�ύ������
   // Add by siuvan 2014-06-27
   public int submitEmployeeContract_leave( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      // �Ƿ�Ϊ���������
      if ( !WorkflowService.isPassObject( employeeContractVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractVO );

         // ��ְʱ����
         historyVO.setRightId( "48" );

         // ��ȡ��Ч������
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContract_leave" );
            historyVO.setObjectId( employeeContractVO.getContractId() );

            // ͬ����ְ
            employeeContractVO.setEmployStatus( employeeContractVO.getEmployStatus() );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // ������ְ
            final EmployeeContractVO original = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
            employeeContractVO.setEmployStatus( original.getEmployStatus() );
            employeeContractVO.setResignDate( null );
            employeeContractVO.setLastWorkDate( null );
            employeeContractVO.setLeaveReasons( null );
            employeeContractVO.setPayment( null );
            employeeContractVO.setHireAgain( null );
            final String failObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractVO );

            rows = -1;
         }
         // û�й�����
         else
         {
            rows = updateEmployeeContract_nt( employeeContractVO, false );
         }
      }
      else
      {
         rows = updateEmployeeContract_nt( employeeContractVO, false );
      }

      return rows;
   }

   @Override
   // ��ְ�ύ������(����������)
   public int submitEmployeeContract_leave_nt( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      // �Ƿ�Ϊ���������
      if ( !WorkflowService.isPassObject( employeeContractVO ) )
      {
         // ����HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractVO );

         // ��ְʱ����
         historyVO.setRightId( "48" );

         // ��ȡ��Ч������
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            // Service�ķ���
            historyVO.setServiceMethod( "submitEmployeeContract_leave_nt" );
            historyVO.setObjectId( employeeContractVO.getContractId() );

            // ͬ����ְ
            employeeContractVO.setEmployStatus( employeeContractVO.getEmployStatus() );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // ������ְ
            final EmployeeContractVO original = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
            employeeContractVO.setEmployStatus( original.getEmployStatus() );
            employeeContractVO.setResignDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractVO );

            rows = -1;
         }
         // û�й�����
         else
         {
            rows = updateEmployeeContract_nt( employeeContractVO, false );
         }
      }
      else
      {
         rows = updateEmployeeContract_nt( employeeContractVO, false );
      }

      return rows;
   }

   public String firstDayOfYearCircle( ServiceContractDTO serviceContractDTO )
   {
      try
      {
         EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         //�����һ��                                                                                                                                                                                      
         Calendar yearFirstDay = KANUtil.getFirstCalendar( KANUtil.getYear( new Date() ) + "/01" );
         //����12�����һ��
         //����12�¿�ʼ��һ��
         Calendar firstDay = KANUtil.getFirstCalendar( KANUtil.getYear( new Date() ) + "/12" );
         //����12�����һ��
         Calendar lastDay = KANUtil.getLastCalendar( KANUtil.getYear( new Date() ) + "/12" );
         //��ͬ��ʼ����
         Calendar contractStartDate = null;
         //��ְ����
         Calendar onboardDate = null;
         //��ʼ���²�ȫ��ȡ��ǰ��ͬ��ʼ����
         if ( employeeContractVO.getStartDate() != null && employeeContractVO.getStartDate() != "" )
         {
            contractStartDate = KANUtil.createCalendar( employeeContractVO.getStartDate() );
            if ( contractStartDate.getTimeInMillis() > firstDay.getTimeInMillis() && contractStartDate.getTimeInMillis() < lastDay.getTimeInMillis() )
            {
               return KANUtil.getDays( contractStartDate ) + "";
            }
         }

         String onboard = getOnboardDate( serviceContractDTO );
         onboardDate = KANUtil.createCalendar( onboard );

         if ( onboardDate.getTimeInMillis() > yearFirstDay.getTimeInMillis() )
         {
            return KANUtil.getDays( onboardDate ) + "";
         }
         else if ( onboardDate.getTimeInMillis() < yearFirstDay.getTimeInMillis() )
         {
            return KANUtil.getDays( yearFirstDay ) + "";
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return "";
   }

   //ȡ�������ڣ��迼����ְ���ڣ���12/31�Ľ�Сֵ
   public String lastDayOfYearCircle( ServiceContractDTO serviceContractDTO )
   {
      try
      {
         EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         //��ͬ��������
         Calendar endDate = null;
         Calendar compareDate = null;
         Calendar lastDay = KANUtil.getLastCalendar( KANUtil.getYear( new Date() ) + "/12" );
         if ( employeeContractVO.getResignDate() != null && employeeContractVO.getResignDate() != "" )
         { //��ְ����
            Calendar resignDate = KANUtil.createCalendar( employeeContractVO.getResignDate() );
            if ( employeeContractVO.getEndDate() == null )
            {
               compareDate = resignDate;
            }
            else
            {
               endDate = KANUtil.createCalendar( employeeContractVO.getEndDate() );
               if ( endDate.getTimeInMillis() > resignDate.getTimeInMillis() )
               {
                  compareDate = resignDate;
               }
               else if ( endDate.getTimeInMillis() < resignDate.getTimeInMillis() )
               {
                  compareDate = endDate;
               }
            }
         }
         else if ( employeeContractVO.getEndDate() != null )
         {
            endDate = KANUtil.createCalendar( employeeContractVO.getEndDate() );
            compareDate = endDate;
         }
         else if ( employeeContractVO.getResignDate() == null && employeeContractVO.getEndDate() == null )
         {
            //��ͬ���ں���ְ���ڶ�Ϊ�ա�ȥ�������һ��
            return KANUtil.getDays( lastDay ) + "";
         }
         //�������һ��
         if ( lastDay.getTimeInMillis() < compareDate.getTimeInMillis() )
         {
            return KANUtil.getDays( lastDay ) + "";
         }
         else if ( lastDay.getTimeInMillis() > compareDate.getTimeInMillis() )
         {
            return KANUtil.getDays( compareDate ) + "";
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return "";
   }

   /**
    * ��ȡ������ͬ�ĵ�һ����ͬ��ʼ����
    * @author : Ian.huang
    * @date   : 2014-7-14
    * @param  : @param serviceContractDTO
    * @param  : @return
    * @return : String
    */
   private String getOnboardDate( ServiceContractDTO serviceContractDTO )
   {
      SimpleDateFormat sd = new SimpleDateFormat( "yyyy-MM-dd" );
      String onboardDate = null;
      if ( serviceContractDTO.getEmployeeVO().getOnboardDate() != null )
      {
         return KANUtil.formatDate( serviceContractDTO.getEmployeeVO().getOnboardDate(), "yyyy-MM-dd HH:mm:ss" );
      }
      try
      {
         EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         List< Object > employeeContractVOs = this.getEmployeeContractVOsByEmployeeId( employeeContractVO.getEmployeeId() );
         if ( employeeContractVOs.size() > 1 )
         {
            for ( int i = 0; i < employeeContractVOs.size(); i++ )
            {
               // ����(����)��ʽ��
               EmployeeContractVO contractVO = ( EmployeeContractVO ) employeeContractVOs.get( i );
               if ( ( sd.parse( KANUtil.formatDate( employeeContractVO.getEndDate(), "yyyy-MM-dd HH:mm:ss" ) ) ).getTime() < ( sd.parse( KANUtil.formatDate( contractVO.getEndDate(), "yyyy-MM-dd HH:mm:ss" ) ) ).getTime() )
               {
                  continue;
               }
               Calendar startDate = KANUtil.createCalendar( contractVO.getStartDate() );
               if ( i + 1 == employeeContractVOs.size() )
               {
                  EmployeeContractVO contractVOs = ( EmployeeContractVO ) employeeContractVOs.get( i );
                  onboardDate = KANUtil.formatDate( contractVOs.getStartDate(), "yyyy-MM-dd HH:mm:ss" );
                  break;
               }
               Calendar endDate = KANUtil.createCalendar( ( ( EmployeeContractVO ) employeeContractVOs.get( i + 1 ) ).getEndDate() );
               endDate.add( 5, 1 );
               if ( startDate.getTimeInMillis() == endDate.getTimeInMillis() )
               {
                  continue;
               }
               else
               {
                  onboardDate = KANUtil.formatDate( contractVO.getStartDate(), "yyyy-MM-dd HH:mm:ss" );
                  break;
               }
            }
         }
         else
         {
            onboardDate = KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd HH:mm:ss" );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
      catch ( ParseException e )
      {
         e.printStackTrace();
      }
      return onboardDate == null ? "" : onboardDate;
   }

   /**
    * ��ȡȫ��ȫ��Сʱ����ȫ�깤��Сʱ��
    * 
    * @author : Ian.huang
    * @date   : 2014-7-29
    * @param  : @param serviceContractDTO
    * @param  : @return
    * @return : String
    */
   private void getTotalFullHoursOfYear( ServiceContractDTO serviceContractDTO )
   {
      try
      {
         EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTotalFullHoursAndWorkHoursOfYear( employeeContractVO.getEmployeeId() );
         ItemGroupVO itemGroupVO = new ItemGroupVO();
         //����ʮ��н���������
         itemGroupVO.setItemGroupType( "3" );
         itemGroupVO.setAccountId( employeeContractVO.getAccountId() );
         itemGroupVO.setCorpId( employeeContractVO.getCorpId() );
         Double workHours = 0.0;
         List< Object > itemGroup = itemGroupDao.getItemGroupVOsByCondition( itemGroupVO );
         if ( itemGroup != null && itemGroup.size() > 0 )
         {
            ItemGroupRelationVO itemGroupRelationVO = new ItemGroupRelationVO();
            itemGroupRelationVO.setItemGroupId( ( ( ItemGroupVO ) itemGroup.get( 0 ) ).getItemGroupId() );
            //��ȡ��Ŀ�������в���
            List< Object > itemList = itemGroupRelationDao.getItemGroupRelationVOsByCondition( itemGroupRelationVO );
            String itemId = "";
            if ( itemList != null && itemList.size() > 0 )
            {
               for ( Object object : itemList )
               {
                  ItemGroupRelationVO itemVO = ( ItemGroupRelationVO ) object;
                  if ( itemId.equals( "" ) )
                  {
                     itemId = itemId + itemVO.getItemId();
                     continue;
                  }
                  itemId = itemId + "," + itemVO.getItemId();
               }
            }

            LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
            leaveHeaderVO.setItemId( itemId );
            leaveHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
            leaveHeaderVO.setAccountId( employeeContractVO.getAccountId() );
            leaveHeaderVO.setCorpId( employeeContractVO.getCorpId() );
            leaveHeaderVO.setClientId( employeeContractVO.getClientId() );
            leaveHeaderVO.setContractId( employeeContractVO.getContractId() );
            //�����һ��
            String yearFirstDay = KANUtil.formatDate( KANUtil.getFirstDate( KANUtil.getYear( new Date() ) + "01" ), "yyyy-MM-dd HH:mm:ss" );
            //�������һ��
            String yearLastDay = KANUtil.formatDate( KANUtil.getLastDate( KANUtil.getYear( new Date() ) + "12" ), "yyyy-MM-dd HH:mm:ss" );
            leaveHeaderVO.setEstimateStartDate( yearFirstDay );
            leaveHeaderVO.setEstimateEndDate( yearLastDay );
            List< Object > leaveList = leaveHeaderDao.getLeaveHeaderVOsByCondition( leaveHeaderVO );
            if ( leaveList != null && leaveList.size() > 0 )
            {
               for ( Object object : leaveList )
               {
                  LeaveHeaderVO tempLeaveHeaderVO = ( LeaveHeaderVO ) object;
                  workHours += Double.parseDouble( tempLeaveHeaderVO.getEstimateBenefitHours() );
               }
            }
         }
         if ( timesheetHeaderVO != null )
         {
            double totalFullHours = Double.parseDouble( timesheetHeaderVO.getTotalFullHours() );
            double totalWorkHours = Double.parseDouble( timesheetHeaderVO.getTotalWorkHours() ) + workHours;
            serviceContractDTO.setTotalFullHoursOfYear( timesheetHeaderVO.getTotalFullHours() );
            serviceContractDTO.setTotalWorkHoursOfYear( totalWorkHours > totalFullHours ? timesheetHeaderVO.getTotalFullHours() : totalWorkHours + "" );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
   }

   @Override
   public List< Object > getEmployeeContractVOsByContractIds( List< String > selectedIdList ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByContractIds( selectedIdList );
   }

   @Override
   public int renewEmployeeContract( EmployeeContractVO employeeContractVO ) throws KANException
   {
      return ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVO );
   }

   @Override
   // ����Ա�����
   // Modify by siuvan.xia @2015-03-17
   public int calculateEmployeeAnnualLeave( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      try
      {

         final List< Object > clientOrderLeaveVOs = this.getClientOrderLeaveDao().getClientOrderLeaveVOsByOrderHeaderId( employeeContractVO.getOrderId() );
         // ��ȡ�����������
         final List< ClientOrderLeaveVO > clientOrderAnnualLeaveVOs = getClientOrderAnnualLeaves( clientOrderLeaveVOs );

         if ( clientOrderAnnualLeaveVOs != null && clientOrderAnnualLeaveVOs.size() > 0 )
         {
            // ��������
            this.startTransaction();

            // �������
            for ( ClientOrderLeaveVO clientOrderAnnualLeaveVO : clientOrderAnnualLeaveVOs )
            {
               // ����������ٹ���
               if ( KANUtil.filterEmpty( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId(), "0" ) != null )
               {
                  // ��ȡ��ٹ���DTO
                  final AnnualLeaveRuleDTO annualLeaveRuleDTO = KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getAnnualLeaveRuleDTOByHeaderId( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId() );
                  // ��ٹ���������
                  if ( annualLeaveRuleDTO != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs() != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs().size() > 0 )
                  {
                     // ����������
                     String year = clientOrderAnnualLeaveVO.getYear();
                     // �������
                     String baseOn = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getBaseOn();
                     // ���㷽ʽ
                     String divideType = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getDivideType();

                     // ֻ���㵱ǰ��ݻ������
                     if ( KANUtil.filterEmpty( year ) == null || Integer.valueOf( year ) < Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) )
                     {
                        continue;
                     }

                     // ��ȡEmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
                     // ��˾ʱ��/�״ν��뼯��ʱ��
                     final String baseDate = "1".equals( baseOn ) ? employeeVO.getOnboardDate() : employeeVO.getStartWorkDate();

                     // Ա����ְʱ��������ڵ�ǰ��ĩ
                     if ( KANUtil.filterEmpty( year ) != null && KANUtil.filterEmpty( baseDate ) != null
                           && KANUtil.createDate( year + "-12-31" ).getTime() > KANUtil.createDate( baseDate ).getTime() )
                     {
                        final EmployeeContractLeaveVO searchEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
                        searchEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
                        searchEmployeeContractLeaveVO.setItemId( "41" );
                        searchEmployeeContractLeaveVO.setYear( year );

                        // ����Ա������Ƿ����
                        final EmployeeContractLeaveVO currYearEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchEmployeeContractLeaveVO );

                        // û���������Ҫϵͳ��������
                        if ( currYearEmployeeContractLeaveVO == null )
                        {

                           // �Ƿ���Ҫ����
                           boolean isDivide = false;
                           // ����Сʱ��
                           double tempLegalHours = 0;
                           // ����Сʱ��
                           double tempBenefitHours = 0;
                           // ��ְ����
                           int months = 0;
                           // ��ְ����
                           long days = 0;
                           // ����Ƿ��½�
                           boolean isNew = false;
                           // ��ְ������
                           int gapYear = Integer.valueOf( year ) - Integer.valueOf( KANUtil.formatDate( baseDate, "yyyy" ) );

                           // ��Ҫ����
                           if ( KANUtil.filterEmpty( divideType, "0" ) != null )
                           {
                              if ( KANUtil.createDate( baseDate ).getTime() >= KANUtil.createDate( year + "-01-01" ).getTime()
                                    && KANUtil.createDate( baseDate ).getTime() <= KANUtil.createDate( year + "-12-31" ).getTime() )
                              {
                                 isDivide = true;
                              }
                           }

                           months = KANUtil.getAboutGapMonth( baseDate, year + "-12-31" );
                           days = KANUtil.getDays( KANUtil.createDate( year + "-12-31" ) ) - KANUtil.getDays( KANUtil.createDate( baseDate ) );

                           // ��ٰ�Ա��ְ������
                           if ( KANUtil.filterEmpty( employeeVO.get_tempPositionGradeIds() ) != null )
                           {

                              // ���ڶ��ְ��ʱ���ҵ���ˬ����ٹ���
                              for ( String positionGradeId : employeeVO.get_tempPositionGradeIds().split( "," ) )
                              {
                                 // �ҵ����ʵ���ٹ�����ϸ
                                 final AnnualLeaveRuleDetailVO searchAnnualLeaveRule = searchAnnualLeaveRuleDetail( annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs(), positionGradeId, gapYear );
                                 // ���ں��ʵ���ٹ�����ϸ
                                 if ( searchAnnualLeaveRule != null )
                                 {
                                    isNew = true;
                                    if ( tempLegalHours < Double.valueOf( searchAnnualLeaveRule.getLegalHours() ) )
                                    {
                                       tempLegalHours = Double.valueOf( searchAnnualLeaveRule.getLegalHours() );
                                    }
                                    if ( tempBenefitHours < Double.valueOf( searchAnnualLeaveRule.getBenefitHours() ) )
                                    {
                                       tempBenefitHours = Double.valueOf( searchAnnualLeaveRule.getBenefitHours() );
                                    }
                                 }
                              }

                              if ( isNew )
                              {
                                 // ��Ҫ����
                                 if ( isDivide )
                                 {
                                    // ����
                                    if ( "1".equals( divideType ) )
                                    {
                                       if ( tempLegalHours > 0 )
                                       {
                                          tempLegalHours = tempLegalHours / 12 * months;
                                       }
                                       if ( tempBenefitHours > 0 )
                                       {
                                          tempBenefitHours = tempBenefitHours / 12 * months;
                                       }
                                    }
                                    // ����
                                    else if ( "2".equals( divideType ) )
                                    {
                                       if ( tempLegalHours > 0 )
                                       {
                                          tempLegalHours = tempLegalHours / 365 * days;
                                       }
                                       if ( tempBenefitHours > 0 )
                                       {
                                          tempBenefitHours = tempBenefitHours / 365 * days;
                                       }
                                    }
                                 }
                                 rows = rows
                                       + addEmployeeAnnualLeave( employeeContractVO, clientOrderAnnualLeaveVO, employeeContractVO.getModifyBy(), year, tempLegalHours, tempBenefitHours );
                              }

                           }
                        }
                     }
                  }
               }
            }

            // �ύ����
            this.commitTransaction();
         }
      }
      catch ( Exception e )
      {
         // �ع�����
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   public static void main( String[] args )
   {
      System.out.println( 60 / 12d );
   }

   /**  
    * ���Һ��ʵ���ٹ���
    * 
    * @param annualLeaveRuleDetailVOs ������ϸ
    * @param positionGradeId ְ��ID
    * @param monthNum ��ְ����
    * @return AnnualLeaveRuleDetailVO
    */
   private AnnualLeaveRuleDetailVO searchAnnualLeaveRuleDetail( final List< AnnualLeaveRuleDetailVO > annualLeaveRuleDetailVOs, final String positionGradeId, final double seniority )
   {
      // ���շ���ֵ
      AnnualLeaveRuleDetailVO finalResult = null;

      if ( annualLeaveRuleDetailVOs != null && annualLeaveRuleDetailVOs.size() > 0 )
      {
         for ( AnnualLeaveRuleDetailVO o : annualLeaveRuleDetailVOs )
         {
            if ( seniority >= Double.valueOf( o.getSeniority() ) )
            {
               if ( KANUtil.filterEmpty( o.getPositionGradeId(), "0" ) == null )
               {
                  finalResult = o;
               }
               else if ( KANUtil.filterEmpty( o.getPositionGradeId(), "0" ) != null && positionGradeId.equals( KANUtil.filterEmpty( o.getPositionGradeId() ) ) )
               {
                  if ( seniority - Double.valueOf( o.getSeniority() ) < 1 )
                     return o;
               }
            }
         }
      }

      return finalResult;
   }

   /**  
    * �½�Ա���ݼ�����
    * 
    * @param employeeContractVO �Ͷ���ͬ
    * @param clientOrderAnnualLeaveVO ��������������
    * @param userId �û�ID
    * @param year ���
    * @param legalHours ����Сʱ��
    * @param benefitHours ����Сʱ��
    * @return 1
    * @throws KANException 
    */
   private int addEmployeeAnnualLeave( final EmployeeContractVO employeeContractVO, final ClientOrderLeaveVO clientOrderAnnualLeaveVO, final String userId, final String year,
         final double legalHours, final double benefitHours ) throws KANException
   {
      double legalQuantity = Math.rint( legalHours );
      double benefitQuantity = Math.rint( benefitHours );

      legalQuantity = legalQuantity == 0.0 ? 0.0 : ( legalQuantity % 4 == 0 ? legalQuantity : ( legalQuantity - legalQuantity % 4 + 4 ) );
      benefitQuantity = benefitQuantity == 0.0 ? 0.0 : ( benefitQuantity % 4 == 0 ? benefitQuantity : ( benefitQuantity - benefitQuantity % 4 + 4 ) );

      final EmployeeContractLeaveVO newEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
      newEmployeeContractLeaveVO.setLegalQuantity( String.valueOf( legalQuantity ) );
      newEmployeeContractLeaveVO.setBenefitQuantity( String.valueOf( benefitQuantity ) );
      newEmployeeContractLeaveVO.setAccountId( employeeContractVO.getAccountId() );
      newEmployeeContractLeaveVO.setCorpId( employeeContractVO.getCorpId() );
      newEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
      newEmployeeContractLeaveVO.setItemId( "41" );
      newEmployeeContractLeaveVO.setYear( year );
      newEmployeeContractLeaveVO.setCycle( clientOrderAnnualLeaveVO.getCycle() );
      newEmployeeContractLeaveVO.setProbationUsing( clientOrderAnnualLeaveVO.getProbationUsing() );
      newEmployeeContractLeaveVO.setDelayUsing( clientOrderAnnualLeaveVO.getDelayUsing() );
      newEmployeeContractLeaveVO.setLegalQuantityDelayMonth( clientOrderAnnualLeaveVO.getLegalQuantityDelayMonth() );
      newEmployeeContractLeaveVO.setBenefitQuantityDelayMonth( clientOrderAnnualLeaveVO.getBenefitQuantityDelayMonth() );
      newEmployeeContractLeaveVO.setDescription( "Calculation of system generation" );
      newEmployeeContractLeaveVO.setCreateBy( userId );
      newEmployeeContractLeaveVO.setCreateDate( new Date() );
      newEmployeeContractLeaveVO.setModifyBy( userId );
      newEmployeeContractLeaveVO.setModifyDate( new Date() );
      newEmployeeContractLeaveVO.setStatus( "1" );

      return this.getEmployeeContractLeaveDao().insertEmployeeContractLeave( newEmployeeContractLeaveVO );
   }

   // ��ȡ�������
   private List< ClientOrderLeaveVO > getClientOrderAnnualLeaves( final List< Object > clientOrderLeaveVOs )
   {
      final List< ClientOrderLeaveVO > reusltAnnualLeaves = new ArrayList< ClientOrderLeaveVO >();
      if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
      {
         for ( Object o : clientOrderLeaveVOs )
         {
            if ( "41".equals( ( ( ClientOrderLeaveVO ) o ).getItemId() ) )
            {
               reusltAnnualLeaves.add( ( ClientOrderLeaveVO ) o );
            }
         }
      }

      return reusltAnnualLeaves;
   }

   @Override
   public EmployeeContractVO getLastAvailEmployeeContract( String employeeId ) throws KANException
   {
      EmployeeContractVO returnEmployeeContractVO = null;
      final EmployeeContractDao employeeContractDao = ( EmployeeContractDao ) getDao();
      final List< Object > employeeContractVOObjects = employeeContractDao.getEmployeeContractVOsByEmployeeId( employeeId );
      if ( employeeContractVOObjects != null && employeeContractVOObjects.size() > 0 )
      {
         if ( employeeContractVOObjects.size() == 1 )
         {
            returnEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObjects.get( 0 );
         }
         else
         {
            for ( Object obj : employeeContractVOObjects )
            {
               final EmployeeContractVO tmpContractVO = ( EmployeeContractVO ) obj;
               if ( returnEmployeeContractVO == null )
               {
                  returnEmployeeContractVO = tmpContractVO;
                  // ��������ͬ�Ľ���ʱ��Ϊ�վͲ����ж���,ͬʱ��ֹ�����compareDate��������
                  if ( KANUtil.filterEmpty( returnEmployeeContractVO.getEndDate() ) == null )
                  {
                     break;
                  }
               }
               else
               {
                  // ����ʱ��Ϊ�ջ����ǽ���ʱ����
                  if ( KANUtil.filterEmpty( tmpContractVO.getEndDate() ) == null
                        || ( KANUtil.compareDate( tmpContractVO.getEndDate(), returnEmployeeContractVO.getEndDate(), "yyyy-MM-dd" ) == -1 ) )
                  {
                     returnEmployeeContractVO = tmpContractVO;
                     if ( KANUtil.filterEmpty( returnEmployeeContractVO.getEndDate() ) == null )
                     {
                        break;
                     }
                  }
               }
            }
         }
      }
      return returnEmployeeContractVO;
   }

   @Override
   public int updateBaseEmployeeContract( EmployeeContractVO employeeContractVO ) throws KANException
   {
      int count = ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVO );
      // ͬ��΢��
      BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
      return count;
   }

   // ��ְ�����춯��¼
   private void setChangeInfo( EmployeePositionChangeVO employeePositionChangeVO ) throws KANException
   {
      final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );
      employeePositionChangeVO.setEmployeeNo( employeeVO.getEmployeeNo() );
      employeePositionChangeVO.setEmployeeNameZH( employeeVO.getNameZH() );
      employeePositionChangeVO.setEmployeeNameEN( employeeVO.getNameEN() );
      employeePositionChangeVO.setEmployeeCertificateNumber( employeeVO.getCertificateNumber() );

      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeePositionChangeVO.getAccountId() );

      //old
      final String oldBranchId = employeePositionChangeVO.getOldBranchId();
      if ( KANUtil.filterEmpty( oldBranchId ) != null )
      {
         final BranchVO oldBranchVO = accountConstants.getBranchVOByBranchId( oldBranchId );
         if ( oldBranchVO != null )
         {
            employeePositionChangeVO.setOldBranchNameZH( oldBranchVO.getNameZH() );
            employeePositionChangeVO.setOldBranchNameEN( oldBranchVO.getNameEN() );
            final String oldParentBranchId = oldBranchVO.getParentBranchId();
            final BranchVO oldParentBranchVO = accountConstants.getBranchVOByBranchId( oldParentBranchId );
            if ( oldParentBranchVO != null )
            {
               employeePositionChangeVO.setOldParentBranchId( oldParentBranchId );
               employeePositionChangeVO.setOldParentBranchNameZH( oldParentBranchVO.getNameZH() );
               employeePositionChangeVO.setOldParentBranchNameEN( oldParentBranchVO.getNameEN() );
            }
         }
      }

      final String oldPositionId = employeePositionChangeVO.getOldPositionId();
      if ( KANUtil.filterEmpty( oldPositionId ) != null )
      {
         final PositionVO oldPositionVO = accountConstants.getPositionVOByPositionId( oldPositionId );
         if ( oldPositionVO != null )
         {
            employeePositionChangeVO.setOldPositionNameZH( oldPositionVO.getTitleZH() );
            employeePositionChangeVO.setOldPositionNameEN( oldPositionVO.getTitleEN() );
            final String oldParentPositionId = oldPositionVO.getParentPositionId();
            final PositionVO oldParentPositionVO = accountConstants.getPositionVOByPositionId( oldParentPositionId );
            if ( oldParentPositionVO != null )
            {
               //oldParentPositionId,oldParentPositionNameZH,oldParentPositionNameEN
               employeePositionChangeVO.setOldParentPositionId( oldParentPositionId );
               employeePositionChangeVO.setOldParentPositionNameZH( oldParentPositionVO.getTitleZH() );
               employeePositionChangeVO.setOldParentPositionNameEN( oldParentPositionVO.getTitleEN() );
               employeePositionChangeVO.setOldParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", oldParentPositionId ) );
               employeePositionChangeVO.setOldParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", oldParentPositionId ) );
            }

            final String oldPositionGradeId = oldPositionVO.getPositionGradeId();
            final PositionGradeVO oldPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( oldPositionGradeId );
            if ( oldPositionGradeVO != null )
            {
               //oldPositionGradeId,oldPositionGradeNameZH,oldPositionGradeNameEN,
               employeePositionChangeVO.setOldPositionGradeId( oldPositionGradeId );
               employeePositionChangeVO.setOldPositionGradeNameZH( oldPositionGradeVO.getGradeNameZH() );
               employeePositionChangeVO.setOldPositionGradeNameEN( oldPositionGradeVO.getGradeNameEN() );
            }

         }
      }

      // end    old
      //new
      final String newBranchId = employeePositionChangeVO.getNewBranchId();
      if ( KANUtil.filterEmpty( newBranchId ) != null )
      {
         final BranchVO newBranchVO = accountConstants.getBranchVOByBranchId( newBranchId );
         if ( newBranchVO != null )
         {
            employeePositionChangeVO.setNewBranchNameZH( newBranchVO.getNameZH() );
            employeePositionChangeVO.setNewBranchNameEN( newBranchVO.getNameEN() );
            final String newParentBranchId = newBranchVO.getParentBranchId();
            final BranchVO newParentBranchVO = accountConstants.getBranchVOByBranchId( newParentBranchId );
            if ( newParentBranchVO != null )
            {
               employeePositionChangeVO.setNewParentBranchId( newParentBranchId );
               employeePositionChangeVO.setNewParentBranchNameZH( newParentBranchVO.getNameZH() );
               employeePositionChangeVO.setNewParentBranchNameEN( newParentBranchVO.getNameEN() );
            }
         }
      }

      final String newPositionId = employeePositionChangeVO.getNewPositionId();
      if ( KANUtil.filterEmpty( newPositionId ) != null )
      {
         final PositionVO newPositionVO = accountConstants.getPositionVOByPositionId( newPositionId );
         if ( newPositionVO != null )
         {
            employeePositionChangeVO.setNewPositionNameZH( newPositionVO.getTitleZH() );
            employeePositionChangeVO.setNewPositionNameEN( newPositionVO.getTitleEN() );
            final String newParentPositionId = newPositionVO.getParentPositionId();
            final PositionVO newParentPositionVO = accountConstants.getPositionVOByPositionId( newParentPositionId );
            if ( newParentPositionVO != null )
            {
               employeePositionChangeVO.setNewParentPositionId( newParentPositionId );
               employeePositionChangeVO.setNewParentPositionNameZH( newParentPositionVO.getTitleZH() );
               employeePositionChangeVO.setNewParentPositionNameEN( newParentPositionVO.getTitleEN() );
               employeePositionChangeVO.setNewParentPositionOwnersZH( accountConstants.getStaffNamesByPositionId( "zh", newParentPositionId ) );
               employeePositionChangeVO.setNewParentPositionOwnersEN( accountConstants.getStaffNamesByPositionId( "en", newParentPositionId ) );
            }

            final String newPositionGradeId = newPositionVO.getPositionGradeId();
            final PositionGradeVO newPositionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( newPositionGradeId );
            if ( newPositionGradeVO != null )
            {
               employeePositionChangeVO.setNewPositionGradeId( newPositionGradeId );
               employeePositionChangeVO.setNewPositionGradeNameZH( newPositionGradeVO.getGradeNameZH() );
               employeePositionChangeVO.setNewPositionGradeNameEN( newPositionGradeVO.getGradeNameEN() );
            }

         }
      }

      // end    old
   }

   @Override
   public String transferHROwner( String oldOwner, String newOwner, String entityId ) throws KANException
   {
      int employeeRows = 0;
      int employeeContractRows = 0;
      Map< String, Object > parameterMap = new HashMap< String, Object >();
      parameterMap.put( "oldHROwner", oldOwner );
      parameterMap.put( "newHROwner", newOwner );
      parameterMap.put( "entityId", entityId );

      try
      {
         this.startTransaction();
         employeeRows = this.getEmployeeDao().transferEmployeeHROwner( parameterMap );
         employeeContractRows = ( ( EmployeeContractDao ) getDao() ).transferEmployeeContractHROwner( parameterMap );
         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return employeeRows + "_" + employeeContractRows;
   }

}
