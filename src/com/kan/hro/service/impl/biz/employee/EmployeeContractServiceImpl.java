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
   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractVO";

   public final String SERVICE_BEAN = "employeeContractService";

   private WorkflowService workflowService;

   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   // 注入ClientDao
   private ClientDao clientDao;

   // 注入ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 注入ClientOrderSBDao
   private ClientOrderSBDao clientOrderSBDao;

   // 注入ClientOrderCBDao
   private ClientOrderCBDao clientOrderCBDao;

   // 注入EmployeeContractSalaryDao
   private EmployeeContractSalaryDao employeeContractSalaryDao;

   // 注入EmployeeContractSBService
   private EmployeeContractSBService employeeContractSBService;

   // 注入EmployeeContractCBDao
   private EmployeeContractCBDao employeeContractCBDao;

   // 注入EmployeeContractSBDao
   private EmployeeContractSBDao employeeContractSBDao;

   // 注入EmployeeContractSBDetailDao
   private EmployeeContractSBDetailDao employeeContractSBDetailDao;

   // 注入EmployeeContractLeaveDao
   private EmployeeContractLeaveDao employeeContractLeaveDao;

   // 注入EmployeeContractOTDao
   private EmployeeContractOTDao employeeContractOTDao;

   // 注入EmployeeContractOtherDao
   private EmployeeContractOtherDao employeeContractOtherDao;

   // 注入TimesheetHeaderService
   private TimesheetHeaderService timesheetHeaderService;

   // 注入SBHeaderService
   private SBHeaderService sbHeaderService;

   // 注入CBHeaderService
   private CBHeaderService cbHeaderService;

   // 注入EmployeeContractPropertyDao
   private EmployeeContractPropertyDao employeeContractPropertyDao;

   // 注入OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // 注入SBAdjustmentHeaderService
   private SBAdjustmentHeaderService sbAdjustmentHeaderService;

   // 注入SBAdjustmentHeaderService
   private SalaryHeaderService salaryHeaderService;

   // 注入PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // 注入staffDao
   private StaffDao staffDao;

   //注入itemGroupRelationDao
   private ItemGroupRelationDao itemGroupRelationDao;

   //注入itemGroupDao
   private ItemGroupDao itemGroupDao;

   private LeaveHeaderDao leaveHeaderDao;

   // 注入PaymentAdjustmentHeaderDao
   private PaymentAdjustmentHeaderDao paymentAdjustmentHeaderDao;

   // 注入 UserDao
   private UserDao userDao;

   // 注入 PositionStaffRelationDao
   private PositionStaffRelationDao positionStaffRelationDao;

   private EmployeePositionChangeDao employeePositionChangeDao;

   // 注入clientOrderLeaveDao;
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
         // 合同时限（月数）
         int monthGap = KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ), KANUtil.formatDate( employeeContractVO.getEndDate(), "yyyy-MM-dd" ) );
         employeeContractVO.setPeriod( String.valueOf( monthGap ) );
         if ( KANUtil.filterEmpty( employeeContractVO.getEndDate() ) == null )
         {
            employeeContractVO.setEndDate( null );
         }

         count = ( ( EmployeeContractDao ) getDao() ).insertEmployeeContract( employeeContractVO );

         //同步合同中项目到员工项目
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

      // 同步微信
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
            // 开启事务
            this.startTransaction();
         }

         //页面提交时存储常量数据
         if ( null != employeeContractVO && null != employeeContractVO.getConstantVOs() )
         {
            // Get EmployeeContractPropertyVO List
            final List< Object > objects = this.employeeContractPropertyDao.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );

            // 先物理删除商务合同对应的Properties
            if ( objects != null )
            {
               for ( Object object : objects )
               {
                  this.employeeContractPropertyDao.deleteEmployeeContractProperty( ( ( EmployeeContractPropertyVO ) object ).getPropertyId() );
               }
            }

            // 逐个插入劳动合同或服务协议对应的Properties
            if ( employeeContractVO.getConstantVOs() != null && employeeContractVO.getConstantVOs().size() > 0 )
            {
               for ( Object constantVO : employeeContractVO.getConstantVOs() )
               {
                  // 生成EmployeeContractPropertyVO对象
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
            // 审批通过修改雇员状态为在职
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            employeeVO.setStatus( "1" );
            if ( employeeVO != null && !"2".equals( employeeVO.getHireAgain() ) )
            {
               employeeVO.setHireAgain( employeeContractVO.getHireAgain() );
            }
            employeeDao.updateEmployee( employeeVO );

            // 将StaffVO改成在职
            if ( staffVO != null )
            {
               staffVO.setStatus( "1" );
               this.getStaffDao().updateStaff( staffVO );
            }
         }

         // 如果“雇员状态”符合条件修改协议为结束
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

         //同步合同中项目到员工项目
         EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
         if ( employeeVO != null )
         {
            employeeVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
            employeeDao.updateEmployee( employeeVO );
         }

         // 如果“雇员”无有效服务协议则修改雇员状态为离职
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
         // 同步微信
         BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );

      }
      catch ( final Exception e )
      {
         if ( notTransaction )
         {
            // 回滚事务
            this.rollbackTransaction();
         }
         e.printStackTrace();
         throw new KANException( e );
      }

      try
      {
         // 离职需要删除对应StaffDTO
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
         // 开启事务
         this.startTransaction();

         // 合同时限（月数）
         int monthGap = KANUtil.getGapMonth( KANUtil.formatDate( employeeContractVO.getStartDate(), "yyyy-MM-dd" ), KANUtil.formatDate( employeeContractVO.getEndDate(), "yyyy-MM-dd" ) );
         employeeContractVO.setPeriod( String.valueOf( monthGap ) );

         // Update EmployeeContractVO
         ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVO );

         // Get EmployeeContractPropertyVO List
         final List< Object > objects = this.employeeContractPropertyDao.getEmployeeContractPropertyVOsByContractId( employeeContractVO.getContractId() );

         // 先物理删除商务合同对应的Properties
         if ( objects != null )
         {
            for ( Object object : objects )
            {
               this.employeeContractPropertyDao.deleteEmployeeContractProperty( ( ( EmployeeContractPropertyVO ) object ).getPropertyId() );
            }
         }

         // 逐个插入劳动合同或服务协议对应的Properties
         if ( constantVOs != null && constantVOs.size() > 0 )
         {
            for ( Object constantVO : constantVOs )
            {
               // 生成EmployeeContractPropertyVO对象
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

         // 提交事务
         this.commitTransaction();
         // 同步微信
         BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
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
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-15
   // TODO 需要考率事物提交
   public int submitEmployeeContract_nt( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 更新记录数
      int rows = 0;

      // 合同是否被延期
      boolean extended = false;
      // 初始化当前使用中劳动合同（非审批中Pending的）
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
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 标识 - 当前合同是否是审批以上状态
         boolean approved = employeeContractVO.getStatus().equals( "3" ) || employeeContractVO.getStatus().equals( "5" ) || employeeContractVO.getStatus().equals( "6" );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         if ( workflowActualDTO != null )
         {
            // Service的方法
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
            // 批准状态
            employeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "3" );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // 退回状态
            tempEmployeeContractVO.setStatus( approved ? employeeContractVO.getStatus() : "4" );
            final String failObject = KANUtil.toJSONObject( tempEmployeeContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowActualDTO.getWorkflowActualVO().setRemark5( "com.kan.hro.domain.biz.employee.EmployeeContractVO" );
            workflowActualDTO.getWorkflowActualVO().setObjectId( employeeContractVO.getContractId() );
            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractVO );

            // 状态改为待审核
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

            // 审批通过修改雇员状态为在职
            final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
            employeeVO.setStatus( "1" );
            this.getEmployeeDao().updateEmployee( employeeVO );

            // 如果合同是续签的
            if ( KANUtil.filterEmpty( employeeContractVO.getMasterContractId() ) != null )
            {
               updateOriginalEmployeeContract( employeeContractVO.getMasterContractId() );
            }

            rows = updateEmployeeContract_nt( employeeContractVO, false );

            // 如果合同是延期的
            if ( extended )
            {
               extendEmployeeContract( employeeContractVO.getContractId(), employeeContractVO.getEndDate() );
            }

            // 更改合同
            return rows;
         }
      }

      // 如果是批准状态，再提交且退回的，不做任何操作；
      if ( KANUtil.filterEmpty( employeeContractVO.getStatus() ) != null && employeeContractVO.getStatus().equals( "3" ) && employeeContractVO.getHistoryVO() != null
            && KANUtil.filterEmpty( employeeContractVO.getHistoryVO().getTempStatus() ) != null && "2".equals( employeeContractVO.getHistoryVO().getTempStatus() ) )
      {
         return 0;
      }

      // 如果合同是续签的
      if ( KANUtil.filterEmpty( employeeContractVO.getMasterContractId() ) != null )
      {
         updateOriginalEmployeeContract( employeeContractVO.getMasterContractId() );
      }

      rows = updateEmployeeContract_nt( employeeContractVO, false );

      // 如果合同是延期的
      if ( extended )
      {
         extendEmployeeContract( employeeContractVO.getContractId(), employeeContractVO.getEndDate() );
      }

      return rows;
   }

   // 续签后更改原始合同相关信息
   // Added by Kevin Jin at 2014-08-27
   private void updateOriginalEmployeeContract( final String masterContractId ) throws KANException
   {
      if ( KANUtil.filterEmpty( masterContractId ) != null )
      {
         final EmployeeContractVO originalEmployeeContractVO = this.getEmployeeContractVOByContractId( masterContractId );

         if ( originalEmployeeContractVO != null )
         {
            // 原始劳动合同变更
            originalEmployeeContractVO.setIsContinued( "1" );

            // 雇佣状态变更
            if ( KANUtil.filterEmpty( originalEmployeeContractVO.getEmployStatus() ) != null && KANUtil.filterEmpty( originalEmployeeContractVO.getEmployStatus() ).equals( "1" ) )
            {
               originalEmployeeContractVO.setEmployStatus( "2" );
            }

            // 合同状态变更
            if ( KANUtil.filterEmpty( originalEmployeeContractVO.getStatus() ) != null && !KANUtil.filterEmpty( originalEmployeeContractVO.getStatus() ).equals( "7" ) )
            {
               originalEmployeeContractVO.setStatus( "7" );
            }

            updateEmployeeContract( originalEmployeeContractVO, null );

            // 原始劳动合同社保变更
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

            // 原始劳动合同商保变更
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

   // 劳动合同延期，更改劳动合同相关信息
   // Added by Kevin Jin at 2014-09-10
   private void extendEmployeeContract( final String contractId, final String endDate ) throws KANException
   {
      // 薪酬方案延期
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

      // 加班设置延期
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

      // 其他设置延期
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
      // 标记删除EmployeeContractVO
      final EmployeeContractVO employeeContractVOTemp = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
      employeeContractVOTemp.setModifyBy( employeeContractVO.getModifyBy() );
      employeeContractVOTemp.setModifyDate( new Date() );
      employeeContractVOTemp.setDeleted( EmployeeContractVO.FALSE );
      int count = ( ( EmployeeContractDao ) getDao() ).updateEmployeeContract( employeeContractVOTemp );
      // 同步微信
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
   // Flag(1:结算，2:社保，3:商保)
   public List< ServiceContractDTO > getServiceContractDTOsByCondition( final EmployeeContractVO employeeContractVO, final String flag ) throws KANException
   {
      // 初始化ServiceContractDTO List
      final List< ServiceContractDTO > serviceContractDTOs = new ArrayList< ServiceContractDTO >();

      // 参考时间，主要用于结束的合同最多往前推3个月
      employeeContractVO.setBufferDate( KANUtil.formatDate( KANUtil.getDate( KANUtil.getFirstDate( employeeContractVO.getMonthly() ), 0, -3, 0 ), "yyyy-MM-dd" ) );

      // 按照条件获取EmployeeContractVO List
      List< Object > employeeContractVOs = null;
      if ( flag != null )
      {
         // 初始化满足结算条件的服务协议
         if ( flag.trim().equals( FLAG_SETTLEMENT ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getSettlementEmployeeContractVOsByCondition( employeeContractVO );
         }
         // 初始化满足社保条件的服务协议
         else if ( flag.trim().equals( FLAG_SB ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getSBEmployeeContractVOsByCondition( employeeContractVO );
         }
         // 初始化满足商保条件的服务协议
         else if ( flag.trim().equals( FLAG_CB ) )
         {
            employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getCBEmployeeContractVOsByCondition( employeeContractVO );
         }
      }

      // 如果存在EmployeeContractVO List数据，遍历
      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            // 初始化EmployeeContractVO
            final EmployeeContractVO tempEmployeeContractVO = ( EmployeeContractVO ) employeeContractVOObject;

            // 设置费用月（预计），用于提取数据
            tempEmployeeContractVO.setMonthly( employeeContractVO.getMonthly() );

            // 初始化EmployeeVO
            final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( tempEmployeeContractVO.getEmployeeId() );

            logger.info( "Employee Contract Loading Start - " + tempEmployeeContractVO.getContractId() + " / " + employeeVO.getNameZH() );

            // 初始化ClientVO
            final ClientVO clientVO = this.getClientDao().getClientVOByClientId( tempEmployeeContractVO.getClientId() );

            if ( employeeVO != null && clientVO != null )
            {
               // 初始化ServiceContractDTO对象
               final ServiceContractDTO serviceContractDTO = new ServiceContractDTO();

               /** 装载DTO数据对象 */
               // 装载EmployeeContractVO
               serviceContractDTO.setEmployeeContractVO( tempEmployeeContractVO );

               // 装载EmployeeVO
               serviceContractDTO.setEmployeeVO( employeeVO );

               // 装载ClientVO
               serviceContractDTO.setClientVO( clientVO );

               // 装载结算所需数据
               if ( flag.trim().equals( FLAG_SETTLEMENT ) )
               {
                  // 装载客户服务订单
                  serviceContractDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() ) );

                  // 装载社保
                  fetchSB( serviceContractDTO, tempEmployeeContractVO );

                  // 装载社保调整
                  fetchSBAdjustment( serviceContractDTO, tempEmployeeContractVO );

                  // 装载商保
                  fetchCB( serviceContractDTO, tempEmployeeContractVO );

                  // 装载薪资方案
                  fetchEmployeeContractSalary( serviceContractDTO, tempEmployeeContractVO );

                  // 装载请假设置
                  fetchEmployeeContractLeave( serviceContractDTO, tempEmployeeContractVO );

                  // 装载加班设置
                  fetchEmployeeContractOT( serviceContractDTO, tempEmployeeContractVO );

                  // 装载其他设置
                  fetchEmployeeContractOther( serviceContractDTO, tempEmployeeContractVO );

                  // 装载考勤表
                  fetchTimesheet( serviceContractDTO, tempEmployeeContractVO );

                  // 装载薪资数据（导入）
                  fetchSalarys( serviceContractDTO, tempEmployeeContractVO );

                  // 装载ItemIds
                  fetchItemIds( serviceContractDTO, tempEmployeeContractVO );

                  // Set firstDayOfYearCircle，Set lastDayOfYearCircle
                  serviceContractDTO.setFirstDayOfYearCircle( firstDayOfYearCircle( serviceContractDTO ) );
                  serviceContractDTO.setLastDayOfYearCircle( lastDayOfYearCircle( serviceContractDTO ) );

                  //设置全年工作小时数和全年全勤小时数
                  getTotalFullHoursOfYear( serviceContractDTO );
               }
               // 装载社保所需数据
               else if ( flag.trim().equals( FLAG_SB ) )
               {
                  // 装载客户服务订单
                  serviceContractDTO.setClientOrderHeaderVO( this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( tempEmployeeContractVO.getOrderId() ) );

                  // 装载客户服务订单 - 社保方案
                  fetchClientOrderSB( serviceContractDTO, tempEmployeeContractVO );

                  tempEmployeeContractVO.setSbStartDate( employeeContractVO.getSbStartDate() );
                  tempEmployeeContractVO.setSbEndDate( employeeContractVO.getSbEndDate() );

                  //follow结算条件
                  tempEmployeeContractVO.setSbType( employeeContractVO.getSbType() );
                  tempEmployeeContractVO.setSbStatusArray( employeeContractVO.getSbStatusArray() );
                  // 装载社保方案
                  fetchEmployeeContractSB( serviceContractDTO, tempEmployeeContractVO );
               }
               // 装载商保所需数据
               else if ( flag.trim().equals( FLAG_CB ) )
               {
                  // 装载客户服务订单 - 商保方案
                  fetchClientOrderCB( serviceContractDTO, tempEmployeeContractVO );

                  // 装载商保方案
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

   // 装载服务协议 - 薪资
   private void fetchEmployeeContractSalary( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议薪资方案
      final List< Object > employeeContractSalaryVOs = this.getEmployeeContractSalaryDao().getEmployeeContractSalaryVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
      {
         for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
         {
            serviceContractDTO.getEmployeeContractSalaryVOs().add( ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject );
         }
      }
   }

   // 装载服务协议 - 社保
   private void fetchEmployeeContractSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议社保方案
      final List< EmployeeContractSBDTO > employeeContractSBDTOs = this.getEmployeeContractSBService().getEmployeeContractSBDTOsByContractId( employeeContractVO );

      if ( employeeContractSBDTOs != null && employeeContractSBDTOs.size() > 0 )
      {
         for ( EmployeeContractSBDTO employeeContractSBDTO : employeeContractSBDTOs )
         {
            // 初始化EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBDTO.getEmployeeContractSBVO();

            // 搜索当月此社保方案是否已计算
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

   // 装载服务协议 - 商保
   private void fetchEmployeeContractCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议商保方案
      final List< Object > employeeContractCBVOs = this.getEmployeeContractCBDao().getEmployeeContractCBVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
      {
         for ( Object employeeContractCBVOObject : employeeContractCBVOs )
         {
            // 初始化EmployeeContractCBVO
            final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObject;

            // 搜索当月此商保方案是否已计算
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

   // 装载服务协议 - 请假
   private void fetchEmployeeContractLeave( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议请假设置
      final List< Object > employeeContractLeaveVOs = this.getEmployeeContractLeaveDao().getEmployeeContractLeaveVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
      {
         for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
         {
            serviceContractDTO.getEmployeeContractLeaveVOs().add( ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject );
         }
      }
   }

   // 装载服务协议 - 加班
   private void fetchEmployeeContractOT( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议加班设置
      final List< Object > employeeContractOTVOs = this.getEmployeeContractOTDao().getEmployeeContractOTVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
      {
         for ( Object employeeContractOTVOObject : employeeContractOTVOs )
         {
            serviceContractDTO.getEmployeeContractOTVOs().add( ( EmployeeContractOTVO ) employeeContractOTVOObject );
         }
      }
   }

   // 装载服务协议 - 其他设置
   private void fetchEmployeeContractOther( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务协议其他设置
      final List< Object > employeeContractOtherVOs = this.getEmployeeContractOtherDao().getEmployeeContractOtherVOsByContractId( employeeContractVO.getContractId() );

      if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
      {
         for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
         {
            serviceContractDTO.getEmployeeContractOtherVOs().add( ( EmployeeContractOtherVO ) employeeContractOtherVOObject );
         }
      }
   }

   // 装载考勤表
   private void fetchTimesheet( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // 初始化TimesheetVO对象
      final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
      timesheetHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      timesheetHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      timesheetHeaderVO.setContractId( employeeContractVO.getContractId() );

      // 按照订单设定的工资月份获取Timesheet
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
      // 状态“3”，已批准
      timesheetHeaderVO.setStatus( "3" );
      timesheetHeaderVO.setSortColumn( "monthly" );
      timesheetHeaderVO.setSortOrder( "DESC" );
      final List< TimesheetDTO > timesheetDTOs = this.getTimesheetHeaderService().getTimesheetDTOsByCondition( timesheetHeaderVO );

      // 装载TimesheetDTO，一个服务协议每月对应一个考勤表
      if ( timesheetDTOs != null && timesheetDTOs.size() > 0 )
      {
         serviceContractDTO.setTimesheetDTO( timesheetDTOs.get( 0 ) );

         // 装载津贴
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

   // 翻译月份设置（2:上一月，3:上二月，4:下一月）
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

   // 装载导入工资表
   private void fetchSalarys( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化Salary月份
      String salaryMonth = employeeContractVO.getMonthly();
      // 初始化SB月份
      String sbMonth = employeeContractVO.getMonthly();
      // 初始化CB月份
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

      // 装载薪酬（状态“2”，提交）
      final SalaryHeaderVO salaryHeaderVO = new SalaryHeaderVO();
      salaryHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      salaryHeaderVO.setContractId( employeeContractVO.getContractId() );
      salaryHeaderVO.setOrderId( employeeContractVO.getOrderId() );
      salaryHeaderVO.setCorpId( employeeContractVO.getCorpId() );
      salaryHeaderVO.setStatus( "2" );
      // 薪酬月份
      salaryHeaderVO.setMonthly( salaryMonth );
      salaryHeaderVO.setItemTypes( "1,2,3,4,5" );

      List< SalaryDTO > salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // 装载社保
      salaryHeaderVO.setMonthly( sbMonth );
      salaryHeaderVO.setItemTypes( "7" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // 装载商保
      salaryHeaderVO.setMonthly( cbMonth );
      salaryHeaderVO.setItemTypes( "8" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // 装载其他
      salaryHeaderVO.setMonthly( employeeContractVO.getMonthly() );
      salaryHeaderVO.setItemTypes( "10,11,12" );

      salaryDTOs = this.getSalaryHeaderService().getSalaryDTOsByCondition( salaryHeaderVO );

      if ( salaryDTOs != null && salaryDTOs.size() > 0 )
      {
         serviceContractDTO.addSalaryDTOs( salaryDTOs );
      }

      // 如果工资导入存在实发工资，需要加载当月此人税前工资
      if ( serviceContractDTO.getSalaryDTOs() != null && serviceContractDTO.getSalaryDTOs().size() > 0 )
      {
         for ( SalaryDTO salaryDTO : serviceContractDTO.getSalaryDTOs() )
         {
            if ( salaryDTO.getSalaryHeaderVO() != null && KANUtil.filterEmpty( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != null
                  && Double.valueOf( salaryDTO.getSalaryHeaderVO().getActualSalary() ) != 0 )
            {
               // 获取Payment Header中的税前工资
               // 初始化PaymentHeaderVO（搜索条件）
               PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
               paymentHeaderVO.setCorpId( employeeContractVO.getCorpId() );
               paymentHeaderVO.setContractId( employeeContractVO.getContractId() );
               paymentHeaderVO.setMonthly( employeeContractVO.getMonthly() );
               // 获取新建状态的PaymentHeaderVO
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

               // 获取Payment Adjustment Header中的税前工资
               // 初始化PaymentAdjustmentHeaderVO（搜索条件）
               PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = new PaymentAdjustmentHeaderVO();
               paymentAdjustmentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
               paymentAdjustmentHeaderVO.setCorpId( employeeContractVO.getCorpId() );
               paymentAdjustmentHeaderVO.setContractId( employeeContractVO.getContractId() );
               paymentAdjustmentHeaderVO.setMonthly( employeeContractVO.getMonthly() );
               // 获取新建状态的PaymentHeaderVO
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

   // 装载ItemIds
   private void fetchItemIds( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化OrderDetailVO
      final OrderDetailVO orderDetailVO = new OrderDetailVO();
      orderDetailVO.setAccountId( employeeContractVO.getAccountId() );
      orderDetailVO.setEmployeeContractId( employeeContractVO.getContractId() );
      orderDetailVO.setMonthly( employeeContractVO.getMonthly() );

      // 获取已经Post完的明细（按照ContractId和Monthly）
      final List< Object > orderDetailVOs = this.getOrderDetailDao().getOrderDetailVOsByCondition( orderDetailVO );

      if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
      {
         for ( Object object : orderDetailVOs )
         {
            serviceContractDTO.getExistItemIds().add( ( ( OrderDetailVO ) object ).getItemId() );
         }
      }
   }

   // 装载服务订单 - 社保
   private void fetchClientOrderSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务订单商保方案
      final List< Object > clientOrderSBVOs = this.getClientOrderSBDao().getClientOrderSBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );

      if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
      {
         for ( Object clientOrderSBVOObject : clientOrderSBVOs )
         {
            serviceContractDTO.getClientOrderSBVOs().add( ( ClientOrderSBVO ) clientOrderSBVOObject );
         }
      }
   }

   // 装载服务订单 - 商保
   private void fetchClientOrderCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化并装载服务订单商保方案
      final List< Object > clientOrderCBVOs = this.getClientOrderCBDao().getClientOrderCBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );

      if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
      {
         for ( Object clientOrderCBVOObject : clientOrderCBVOs )
         {
            serviceContractDTO.getClientOrderCBVOs().add( ( ClientOrderCBVO ) clientOrderCBVOObject );
         }
      }
   }

   // 装载社保
   private void fetchSB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // 初始化SBHeaderVO对象
      final SBHeaderVO sbHeaderVO = new SBHeaderVO();
      sbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sbHeaderVO.setContractId( employeeContractVO.getContractId() );
      sbHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // 按照订单设定的社保月份获取SB
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

      // 状态“4”，提交
      sbHeaderVO.setStatus( "4" );
      serviceContractDTO.setSbDTOs( this.getSbHeaderService().getSBDTOsByCondition( sbHeaderVO ) );
   }

   // 装载社保调整
   private void fetchSBAdjustment( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化SBAdjustmentHeaderVO对象
      final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = new SBAdjustmentHeaderVO();
      sbAdjustmentHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      sbAdjustmentHeaderVO.setContractId( employeeContractVO.getContractId() );
      sbAdjustmentHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // 状态“3”，提交
      sbAdjustmentHeaderVO.setStatus( "3" );
      serviceContractDTO.setSbAdjustmentDTOs( this.getSbAdjustmentHeaderService().getSBAdjustmentDTOsByCondition( sbAdjustmentHeaderVO ) );
   }

   // 装载商保
   private void fetchCB( final ServiceContractDTO serviceContractDTO, final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = serviceContractDTO.getClientOrderHeaderVO();

      // 初始化CBHeaderVO对象
      final CBHeaderVO cbHeaderVO = new CBHeaderVO();
      cbHeaderVO.setAccountId( employeeContractVO.getAccountId() );
      cbHeaderVO.setContractId( employeeContractVO.getContractId() );
      cbHeaderVO.setCorpId( employeeContractVO.getCorpId() );

      // 按照订单设定的商保月份获取CB
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

      // 状态“4”，提交
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
      // 表示是工作流的
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
      //如果没有EntityId则不检查时间冲突性
      if ( KANUtil.filterEmpty( employeeContractVO.getEntityId(), "0" ) == null )
      {
         return false;
      }

      // 初始化EmployeeContractVO
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

         // 排除当前的劳动合同或者派送协议
         if ( KANUtil.filterEmpty( employeeContractVO.getContractId() ) != null
               && KANUtil.filterEmpty( employeeContractVO.getContractId() ).equals( tempEmployeeContractVO.getContractId() ) )
         {
            continue;
         }

         // 

         // Modify by siuvan 2014-08-27
         String tempContractStartDate = tempEmployeeContractVO.getStartDate();
         String tempContractEndDate = tempEmployeeContractVO.getEndDate();

         // 如果存在离职日期
         if ( KANUtil.filterEmpty( tempEmployeeContractVO.getResignDate() ) != null )
         {
            tempContractEndDate = tempEmployeeContractVO.getResignDate();
         }

         /*add Ian 如果有无固定期限合同，就不能在同一个结算规则下创建新的合同,保存状态除外
         if ( KANUtil.filterEmpty( tempContractEndDate ) == null && employeeContractVO.getOrderId() != null
               && employeeContractVO.getOrderId().equals( tempEmployeeContractVO.getOrderId() ) && !"1".equals( tempEmployeeContractVO.getStatus() ) )
         {
            flag = true;
            break;
         }*/

         // 时间不重叠比较
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
    * 续签派送协议/劳动合同
    * 
    * @param employeeContractVO
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-08-18
   public int continueEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      // 初始化影响记录数
      int count = 0;

      // 获取MasterContractId
      final String masterContractId = employeeContractVO.getMasterContractId();

      count = ( ( EmployeeContractDao ) getDao() ).insertEmployeeContract( employeeContractVO );

      if ( count > 0 )
      {
         // 插入CB
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

         // 插入Leave
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

         // 插入OT
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

         // 插入Other
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

         // 插入Salary
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

         // 插入SB
         final List< Object > employeeContractSBs = this.getEmployeeContractSBDao().getEmployeeContractSBVOsByContractId( masterContractId );

         if ( employeeContractSBs != null && employeeContractSBs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : employeeContractSBs )
            {
               final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
               // 获取劳动合同社保ID
               final String employeeSBId = employeeContractSBVO.getEmployeeSBId();

               employeeContractSBVO.setEmployeeSBId( "" );
               employeeContractSBVO.setContractId( employeeContractVO.getContractId() );
               employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
               employeeContractSBVO.setCreateBy( employeeContractVO.getCreateBy() );
               employeeContractSBVO.setCreateDate( new Date() );
               employeeContractSBVO.setModifyBy( employeeContractVO.getModifyBy() );
               employeeContractSBVO.setModifyDate( new Date() );
               this.getEmployeeContractSBDao().insertEmployeeContractSB( employeeContractSBVO );

               // 插入SBDetail
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
    * 员工离职
    * 1、根据是否有有效服务协议判断是否需要修改雇员状态
    * 2、产生一笔异动数据
    * 3、生成日志
    * @param employeeContractVO
    * @return
    * @throws KANException
    */
   private int dimissionEmployeeContract( final EmployeeContractVO employeeContractVO, final StaffVO staffVO ) throws KANException
   {
      // 查询雇员对应有效服务协议
      final EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
      tempEmployeeContractVO.setEmployeeId( employeeContractVO.getEmployeeId() );
      tempEmployeeContractVO.setAccountId( employeeContractVO.getAccountId() );
      // 状态只能为“批准”，“已盖章”，“归档”
      tempEmployeeContractVO.setStatus( "3, 5, 6" );

      final List< Object > employeeContractVOs = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOsByCondition( tempEmployeeContractVO );

      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         for ( Object employeeContractVOObject : employeeContractVOs )
         {
            final EmployeeContractVO tempEmployeeContractVO1 = ( EmployeeContractVO ) employeeContractVOObject;

            // 离职时间
            final String resignDate = employeeContractVO.getResignDate();
            // 服务协议开始时间
            final String startDate = tempEmployeeContractVO1.getStartDate();
            // 服务协议结束时间
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
      // 无有效服务协议修改雇员状态为离职
      employeeVO.setStatus( "3" );
      // 塞入各项值
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
         // 删除员工和职位的对应关系前插入异动历史
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

               // 插入日志
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

         // 删除员工和职位的对应关系
         this.positionStaffRelationDao.deletePositionStaffRelationByStaffId( staffVO.getStaffId() );

         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( employeeVO.getAccountId() );
         // 将<直线汇报线>保存下来,add by siuvan 2015-06-18
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
                              result = result + "、" + accountConstants.getStaffNameByStaffId( parentStaffVO.getStaffId(), true );
                        }
                     }
                  }
               }
            }

            if ( KANUtil.filterEmpty( result ) != null )
               employeeVO.setRemark5( result );

         }

         // 停用用户
         final UserVO userVO = this.userDao.getUserVOByStaffId( staffVO.getStaffId() );
         if ( userVO != null )
         {
            userVO.setStatus( "2" );
            userVO.setDeleted( "2" );
            userDao.updateUser( userVO );
         }

         // 将staff设为离职，add by siuvan 2015-06-18
         staffVO.setStatus( "3" );
         this.getStaffDao().updateStaff( staffVO );
      }

      // 更新employeeVO
      this.employeeDao.updateEmployee( employeeVO );

      return 1;
   }

   @Override
   // 离职提交工作流
   // Add by siuvan 2014-06-27
   public int submitEmployeeContract_leave( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      // 是否为审批后对象
      if ( !WorkflowService.isPassObject( employeeContractVO ) )
      {
         // 生成HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractVO );

         // 离职时触发
         historyVO.setRightId( "48" );

         // 获取有效工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            // Service的方法
            historyVO.setServiceMethod( "submitEmployeeContract_leave" );
            historyVO.setObjectId( employeeContractVO.getContractId() );

            // 同意离职
            employeeContractVO.setEmployStatus( employeeContractVO.getEmployStatus() );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // 反对离职
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
         // 没有工作流
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
   // 离职提交工作流(不包含事务)
   public int submitEmployeeContract_leave_nt( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      // 是否为审批后对象
      if ( !WorkflowService.isPassObject( employeeContractVO ) )
      {
         // 生成HistoryVO
         final HistoryVO historyVO = generateHistoryVO( employeeContractVO );

         // 离职时触发
         historyVO.setRightId( "48" );

         // 获取有效工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            // Service的方法
            historyVO.setServiceMethod( "submitEmployeeContract_leave_nt" );
            historyVO.setObjectId( employeeContractVO.getContractId() );

            // 同意离职
            employeeContractVO.setEmployStatus( employeeContractVO.getEmployStatus() );
            final String passObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            // 反对离职
            final EmployeeContractVO original = ( ( EmployeeContractDao ) getDao() ).getEmployeeContractVOByContractId( employeeContractVO.getContractId() );
            employeeContractVO.setEmployStatus( original.getEmployStatus() );
            employeeContractVO.setResignDate( null );
            final String failObject = KANUtil.toJSONObject( employeeContractVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual_nt( workflowActualDTO, employeeContractVO );

            rows = -1;
         }
         // 没有工作流
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
         //本年第一天                                                                                                                                                                                      
         Calendar yearFirstDay = KANUtil.getFirstCalendar( KANUtil.getYear( new Date() ) + "/01" );
         //本年12月最后一天
         //本年12月开始第一天
         Calendar firstDay = KANUtil.getFirstCalendar( KANUtil.getYear( new Date() ) + "/12" );
         //本年12月最后一天
         Calendar lastDay = KANUtil.getLastCalendar( KANUtil.getYear( new Date() ) + "/12" );
         //合同开始日期
         Calendar contractStartDate = null;
         //入职日期
         Calendar onboardDate = null;
         //开始当月不全的取当前合同开始日期
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

   //取结束日期（需考虑离职日期）跟12/31的较小值
   public String lastDayOfYearCircle( ServiceContractDTO serviceContractDTO )
   {
      try
      {
         EmployeeContractVO employeeContractVO = serviceContractDTO.getEmployeeContractVO();
         //合同结束日期
         Calendar endDate = null;
         Calendar compareDate = null;
         Calendar lastDay = KANUtil.getLastCalendar( KANUtil.getYear( new Date() ) + "/12" );
         if ( employeeContractVO.getResignDate() != null && employeeContractVO.getResignDate() != "" )
         { //离职日期
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
            //合同日期和离职日期都为空、去本年最后一天
            return KANUtil.getDays( lastDay ) + "";
         }
         //本年最后一天
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
    * 获取连续合同的第一个合同开始日期
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
               // 创建(日期)格式化
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
    * 获取全年全勤小时数和全年工作小时数
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
         //加入十三薪计算的类型
         itemGroupVO.setItemGroupType( "3" );
         itemGroupVO.setAccountId( employeeContractVO.getAccountId() );
         itemGroupVO.setCorpId( employeeContractVO.getCorpId() );
         Double workHours = 0.0;
         List< Object > itemGroup = itemGroupDao.getItemGroupVOsByCondition( itemGroupVO );
         if ( itemGroup != null && itemGroup.size() > 0 )
         {
            ItemGroupRelationVO itemGroupRelationVO = new ItemGroupRelationVO();
            itemGroupRelationVO.setItemGroupId( ( ( ItemGroupVO ) itemGroup.get( 0 ) ).getItemGroupId() );
            //获取科目分组所有参数
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
            //本年第一天
            String yearFirstDay = KANUtil.formatDate( KANUtil.getFirstDate( KANUtil.getYear( new Date() ) + "01" ), "yyyy-MM-dd HH:mm:ss" );
            //本年最后一天
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
   // 计算员工年假
   // Modify by siuvan.xia @2015-03-17
   public int calculateEmployeeAnnualLeave( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      int rows = 0;

      try
      {

         final List< Object > clientOrderLeaveVOs = this.getClientOrderLeaveDao().getClientOrderLeaveVOsByOrderHeaderId( employeeContractVO.getOrderId() );
         // 获取订单年假设置
         final List< ClientOrderLeaveVO > clientOrderAnnualLeaveVOs = getClientOrderAnnualLeaves( clientOrderLeaveVOs );

         if ( clientOrderAnnualLeaveVOs != null && clientOrderAnnualLeaveVOs.size() > 0 )
         {
            // 开启事务
            this.startTransaction();

            // 遍历年假
            for ( ClientOrderLeaveVO clientOrderAnnualLeaveVO : clientOrderAnnualLeaveVOs )
            {
               // 结算规则的年假规则
               if ( KANUtil.filterEmpty( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId(), "0" ) != null )
               {
                  // 获取年假规则DTO
                  final AnnualLeaveRuleDTO annualLeaveRuleDTO = KANConstants.getKANAccountConstants( employeeContractVO.getAccountId() ).getAnnualLeaveRuleDTOByHeaderId( clientOrderAnnualLeaveVO.getAnnualLeaveRuleId() );
                  // 年假规则有意义
                  if ( annualLeaveRuleDTO != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs() != null && annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs().size() > 0 )
                  {
                     // 年假所属年份
                     String year = clientOrderAnnualLeaveVO.getYear();
                     // 规则基于
                     String baseOn = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getBaseOn();
                     // 折算方式
                     String divideType = annualLeaveRuleDTO.getAnnualLeaveRuleHeaderVO().getDivideType();

                     // 只计算当前年份或将来年份
                     if ( KANUtil.filterEmpty( year ) == null || Integer.valueOf( year ) < Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) )
                     {
                        continue;
                     }

                     // 获取EmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );
                     // 入司时间/首次进入集团时间
                     final String baseDate = "1".equals( baseOn ) ? employeeVO.getOnboardDate() : employeeVO.getStartWorkDate();

                     // 员工入职时间必须早于当前年末
                     if ( KANUtil.filterEmpty( year ) != null && KANUtil.filterEmpty( baseDate ) != null
                           && KANUtil.createDate( year + "-12-31" ).getTime() > KANUtil.createDate( baseDate ).getTime() )
                     {
                        final EmployeeContractLeaveVO searchEmployeeContractLeaveVO = new EmployeeContractLeaveVO();
                        searchEmployeeContractLeaveVO.setContractId( employeeContractVO.getContractId() );
                        searchEmployeeContractLeaveVO.setItemId( "41" );
                        searchEmployeeContractLeaveVO.setYear( year );

                        // 搜索员工年假是否存在
                        final EmployeeContractLeaveVO currYearEmployeeContractLeaveVO = this.getEmployeeContractLeaveDao().getLastYearAnnualLeaveByCondition( searchEmployeeContractLeaveVO );

                        // 没有年假则需要系统计算生成
                        if ( currYearEmployeeContractLeaveVO == null )
                        {

                           // 是否需要折算
                           boolean isDivide = false;
                           // 法定小时数
                           double tempLegalHours = 0;
                           // 福利小时数
                           double tempBenefitHours = 0;
                           // 入职月数
                           int months = 0;
                           // 入职天数
                           long days = 0;
                           // 标记是否新建
                           boolean isNew = false;
                           // 入职多少年
                           int gapYear = Integer.valueOf( year ) - Integer.valueOf( KANUtil.formatDate( baseDate, "yyyy" ) );

                           // 需要折算
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

                           // 年假按员工职级计算
                           if ( KANUtil.filterEmpty( employeeVO.get_tempPositionGradeIds() ) != null )
                           {

                              // 存在多个职级时，找到最爽的年假规则
                              for ( String positionGradeId : employeeVO.get_tempPositionGradeIds().split( "," ) )
                              {
                                 // 找到合适的年假规则明细
                                 final AnnualLeaveRuleDetailVO searchAnnualLeaveRule = searchAnnualLeaveRuleDetail( annualLeaveRuleDTO.getAnnualLeaveRuleDetailVOs(), positionGradeId, gapYear );
                                 // 存在合适的年假规则明细
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
                                 // 需要折算
                                 if ( isDivide )
                                 {
                                    // 按月
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
                                    // 按天
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

            // 提交事务
            this.commitTransaction();
         }
      }
      catch ( Exception e )
      {
         // 回滚事务
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
    * 查找合适的年假规则
    * 
    * @param annualLeaveRuleDetailVOs 规则明细
    * @param positionGradeId 职级ID
    * @param monthNum 入职月数
    * @return AnnualLeaveRuleDetailVO
    */
   private AnnualLeaveRuleDetailVO searchAnnualLeaveRuleDetail( final List< AnnualLeaveRuleDetailVO > annualLeaveRuleDetailVOs, final String positionGradeId, final double seniority )
   {
      // 最终返回值
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
    * 新建员工休假设置
    * 
    * @param employeeContractVO 劳动合同
    * @param clientOrderAnnualLeaveVO 结算规则年假设置
    * @param userId 用户ID
    * @param year 年份
    * @param legalHours 法定小时数
    * @param benefitHours 福利小时数
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

   // 获取订单年假
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
                  // 如果这个合同的结束时间为空就不用判断了,同时防止下面的compareDate方法出错
                  if ( KANUtil.filterEmpty( returnEmployeeContractVO.getEndDate() ) == null )
                  {
                     break;
                  }
               }
               else
               {
                  // 结束时间为空或者是结束时间大得
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
      // 同步微信
      BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
      return count;
   }

   // 离职操作异动记录
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
