package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetBatchService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction;

public class TimesheetBatchServiceImpl extends ContextService implements TimesheetBatchService
{
   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.attendance.TimesheetBatchVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "timesheetBatchService";

   // 注入WorkflowService
   private WorkflowService workflowService;

   // 注入TimesheetHeaderService
   private TimesheetHeaderService timesheetHeaderService;

   // 注入TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // 注入EmployeeDao
   private EmployeeDao employeeDao;

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
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

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   @Override
   public PagedListHolder getTimesheetBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final TimesheetBatchDao timesheetBatchDao = ( TimesheetBatchDao ) getDao();
      pagedListHolder.setHolderSize( timesheetBatchDao.countTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( batchId );
   }

   @Override
   public int generateTimesheet( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      // 标识批次个数
      int size = 0;

      try
      {
         // 批次执行开始时间
         timesheetBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // 初始化TimesheetHeaderVO
         final TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
         timesheetHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
         timesheetHeaderVO.setAccountId( timesheetBatchVO.getAccountId() );
         timesheetHeaderVO.setEmployeeId( timesheetBatchVO.getEmployeeId() );
         timesheetHeaderVO.setEntityId( timesheetBatchVO.getEntityId() );
         timesheetHeaderVO.setBusinessTypeId( timesheetBatchVO.getBusinessTypeId() );
         timesheetHeaderVO.setClientId( timesheetBatchVO.getClientId() );
         timesheetHeaderVO.setCorpId( timesheetBatchVO.getCorpId() );
         timesheetHeaderVO.setOrderId( timesheetBatchVO.getOrderId() );
         timesheetHeaderVO.setEmployeeId( timesheetBatchVO.getEmployeeId() );
         timesheetHeaderVO.setContractId( timesheetBatchVO.getContractId() );
         timesheetHeaderVO.setMonthly( timesheetBatchVO.getMonthly() );
         timesheetHeaderVO.setCreateBy( timesheetBatchVO.getCreateBy() );
         timesheetHeaderVO.setModifyBy( timesheetBatchVO.getModifyBy() );

         // 生成考勤表
         int timesheets = this.timesheetHeaderService.generateTimesheet( timesheetHeaderVO, timesheetBatchVO );

         if ( timesheets > 0 )
         {
            size = 1;
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return size;
   }

   @Override
   public int submit_batch( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      // 是否是审批过后的对象
      if ( !WorkflowService.isPassObject( timesheetBatchVO ) )
      {
         // 生成historyVO
         final HistoryVO historyVO = generateHistoryVO( timesheetBatchVO );
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( timesheetBatchVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( timesheetBatchVO.getStatus() != null && !timesheetBatchVO.getStatus().trim().equals( "3" ) )
            {
               // 状态改为待审核
               submit( timesheetBatchVO, "2", true );
            }

            // Service的方法
            historyVO.setServiceMethod( "submit_batch" );
            historyVO.setObjectId( timesheetBatchVO.getBatchId() );
            String nameZH = timesheetBatchVO.getEmployeeNameTop3();
            if ( KANUtil.filterEmpty( nameZH ) != null && nameZH.split( "、" ).length > 3 )
            {
               nameZH = nameZH + "等人";
            }
            historyVO.setNameZH( nameZH );
            historyVO.setNameEN( nameZH );

            // 批准状态
            timesheetBatchVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( timesheetBatchVO ).toString();

            // 退回状态
            timesheetBatchVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( timesheetBatchVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, timesheetBatchVO );

            return -1;
         }
         // 没有工作流
         else
         {
            // 批准状态
            return submit( timesheetBatchVO, "3", false );
         }
      }

      return submit( timesheetBatchVO, timesheetBatchVO.getStatus(), true );
   }

   private int submit( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();

         submit_batch( timesheetBatchVO, status, isWorkflow );

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

   // 提交批次
   private int submit_batch( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      // 初始化TimesheetHeaderVO列表
      final List< Object > timesheetHeaderVOs = new ArrayList< Object >();

      // 标识是否修改批次
      boolean updated = false;

      // 批次下考勤表个数
      int totalSize = 0;
      // 当前改动考勤表个数
      int currentSize = 0;
      // 原有考勤表个数
      int originalSize = 0;

      // 初始化StringBuffer
      final StringBuffer headerIds = new StringBuffer();
      final StringBuffer employeeName = new StringBuffer();

      // 初始化TimesheetHeaderVO用于搜素
      final TimesheetHeaderVO tempTimesheetHeaderVO = new TimesheetHeaderVO();
      tempTimesheetHeaderVO.setAccountId( timesheetBatchVO.getAccountId() );
      tempTimesheetHeaderVO.setBatchId( timesheetBatchVO.getBatchId() );
      tempTimesheetHeaderVO.setCorpId( timesheetBatchVO.getCorpId() );

      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      totalSize = timesheetHeaderVOs.size();

      timesheetHeaderVOs.clear();
      tempTimesheetHeaderVO.setStatus( status );
      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      originalSize = timesheetHeaderVOs.size();

      // 工作流提交
      if ( isWorkflow )
      {
         // 如果是待审核，装载新建、退回状态TimesheetHeaderVO
         if ( status.equals( "2" ) )
         {
            tempTimesheetHeaderVO.setStatus( "1,4" );
         }
         // 如果是批准或者退回，装载待审核状态TimesheetHeaderVO
         else if ( status.equals( "3" ) || status.equals( "4" ) )
         {
            tempTimesheetHeaderVO.setStatus( "2" );
         }
      }
      // 非工作流提交
      else
      {
         // 装载新建、退回状态TimesheetHeaderVO
         tempTimesheetHeaderVO.setStatus( "1,4" );
      }

      timesheetHeaderVOs.clear();
      timesheetHeaderVOs.addAll( this.getTimesheetHeaderDao().getTimesheetHeaderVOsByCondition( tempTimesheetHeaderVO ) );
      currentSize = timesheetHeaderVOs.size();

      updated = totalSize == originalSize + currentSize;

      // 改变header状态
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            if ( KANUtil.filterEmpty( headerIds.toString() ) == null && KANUtil.filterEmpty( employeeName.toString() ) == null )
            {
               employeeName.append( timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( timesheetHeaderVO.getHeaderId() );
            }
            else
            {
               employeeName.append( "、" + timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( "," + timesheetHeaderVO.getHeaderId() );
            }
            submit_header( timesheetHeaderVO, status, timesheetBatchVO.getModifyBy() );
         }
      }

      // 更改该批次状态
      if ( updated )
      {
         timesheetBatchVO.setStatus( status );
         timesheetBatchVO.setModifyDate( new Date() );
         ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( timesheetBatchVO );
      }

      // 重新获取汇总TimesheetBatchVO
      TimesheetBatchVO tempTimesheetBatchVO = null;
      if ( KANUtil.filterEmpty( headerIds.toString() ) != null )
      {
         tempTimesheetBatchVO = getTimesheetBatchVOByHeaderIds( headerIds.toString() );
      }

      if ( tempTimesheetBatchVO != null )
      {
         timesheetBatchVO.setCountOrderId( tempTimesheetBatchVO.getCountOrderId() );
         timesheetBatchVO.setCountContractId( tempTimesheetBatchVO.getCountContractId() );
         timesheetBatchVO.setCountHeaderId( tempTimesheetBatchVO.getCountHeaderId() );
         timesheetBatchVO.setTotalWortHours( tempTimesheetBatchVO.getTotalWortHours() );
         timesheetBatchVO.setTotalLeaveHours( tempTimesheetBatchVO.getTotalLeaveHours() );
         timesheetBatchVO.setTotalOTHours( tempTimesheetBatchVO.getTotalOTHours() );
         timesheetBatchVO.setDescription( employeeName.toString() );
      }

      return 0;
   }

   /*提交
   private int submit1( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 提交批次
         submitBatch( timesheetBatchVO, status, isWorkflow );

         // 提交事务
         this.commitTransaction();

         // 如果是批准或者退回，重组TimesheetBatchVO中description字段
         if ( KANUtil.filterEmpty( status ) != null && ( status.equals( "3" ) || status.equals( "4" ) ) )
         {
            // 初始化StringBuffer
            final StringBuffer employeeNameList = new StringBuffer();

            // 获取TimesheetHeaderVO列表
            final List< Object > timesheetHeaderVOs = this.getTimesheetHeaderDao().getTimesheetHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

            // 存在TimesheetHeaderVO列表
            if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
            {
               int size = 0;
               for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
               {
                  final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
                  // 非退回状态
                  if ( KANUtil.filterEmpty( timesheetHeaderVO.getStatus() ) != null && !timesheetHeaderVO.getStatus().equals( "4" ) )
                  {
                     // 获取EmployeeVO
                     final EmployeeVO employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( ( ( TimesheetHeaderVO ) timesheetHeaderVOObject ).getEmployeeId() );

                     if ( employeeVO != null )
                     {
                        if ( size == 0 )
                           employeeNameList.append( employeeVO.getNameZH() );
                        else
                           employeeNameList.append( "、" + employeeVO.getNameZH() );
                     }
                  }

                  size++;
               }

               final TimesheetBatchVO tempTimesheetBatchVO = ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( timesheetBatchVO.getBatchId() );
               // 修改批次
               tempTimesheetBatchVO.setDescription( employeeNameList.toString() );
               ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( tempTimesheetBatchVO );
            }

         }
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   /**
    * 
   * submitBatch(提交批次)  
   * @param  timesheetBatchVO  提交对象
   * @param  status            提交状态
   * @param  isWorkflow        是否走工作流审批
    */
   /*/ 提交批次
   private void submitBatch( final TimesheetBatchVO timesheetBatchVO, final String status, final boolean isWorkflow ) throws KANException
   {
      // 初始化TimesheetHeaderVO列表
      final List< Object > timesheetHeaderVOs = new ArrayList< Object >();

      // 初始化考勤表总个数
      int tsTotalSize = 0;

      // 初始化当前状态考勤表个数
      int tsStatusSize = 0;

      // 是否改变批次
      boolean updated = false;

      // 获取该批次下所有TimesheetHeaderVO
      final List< Object > allTimesheetHeaderVOs = this.timesheetHeaderDao.getTimesheetHeaderVOsByBatchId( timesheetBatchVO.getBatchId() );

      tsTotalSize = allTimesheetHeaderVOs != null ? allTimesheetHeaderVOs.size() : 0;

      // 初始化TimesheetHeaderVO用于搜素
      final TimesheetHeaderVO tempSearch = new TimesheetHeaderVO();
      tempSearch.setAccountId( timesheetBatchVO.getAccountId() );
      tempSearch.setBatchId( timesheetBatchVO.getBatchId() );
      tempSearch.setStatus( status );

      // 获取原有当前状态TimesheetHeaderVO
      final List< Object > currStatusTimesheetHeaderVOs = this.timesheetHeaderDao.getTimesheetHeaderVOsByCondition( tempSearch );

      // 原有当前状态个数
      tsStatusSize = currStatusTimesheetHeaderVOs != null ? currStatusTimesheetHeaderVOs.size() : 0;

      // 存在选中的headerIds，则是header提交
      if ( KANUtil.filterEmpty( timesheetBatchVO.getHeaderIds() ) != null )
      {
         // 选中的headerId
         final String[] headerIdArray = timesheetBatchVO.getHeaderIds().split( "," );

         for ( String headerId : headerIdArray )
         {
            timesheetHeaderVOs.add( this.timesheetHeaderDao.getTimesheetHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( headerId ) ) );
         }

         updated = tsTotalSize == tsStatusSize + headerIdArray.length;
      }
      // 若按batch提交
      else
      {
         // 工作流提交
         if ( isWorkflow )
         {
            // 如果是待审核
            if ( status.equals( "2" ) )
            {
               // 装载新建、退回状态TimesheetHeaderVO
               tempSearch.setStatus( "1,4" );
            }
            // 如果是批准或者退回
            else if ( status.equals( "3" ) || status.equals( "4" ) )
            {
               // 装载待审核状态TimesheetHeaderVO
               tempSearch.setStatus( "2" );
            }
         }
         // 非工作流提交
         else
         {
            // 装载新建、退回状态TimesheetHeaderVO
            tempSearch.setStatus( "1,4" );
         }

         timesheetHeaderVOs.addAll( this.timesheetHeaderDao.getTimesheetHeaderVOsByCondition( tempSearch ) );

         updated = tsTotalSize == tsStatusSize + timesheetHeaderVOs.size();
      }

      // 存在TimesheetHeaderVO列表，迭代提交Header
      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            submitHeader( ( TimesheetHeaderVO ) timesheetHeaderVOObject, status, timesheetBatchVO.getModifyBy() );
         }
      }

      // 更改该批次状态
      if ( updated )
      {
         timesheetBatchVO.setStatus( status );
         timesheetBatchVO.setModifyDate( new Date() );
         ( ( TimesheetBatchDao ) getDao() ).updateTimesheetBatch( timesheetBatchVO );
      }

      // 重组批次统计信息
      dealTimesheetBatchVO( timesheetBatchVO, timesheetHeaderVOs );
   }

   /* 处理TimesheetBatchVO的统计信息
   private void dealTimesheetBatchVO( final TimesheetBatchVO timesheetBatchVO, final List< Object > timesheetHeaderVOs ) throws KANException
   {
      // 初始化StringBuffer
      final StringBuffer headerIds = new StringBuffer();
      final StringBuffer employeeName = new StringBuffer();

      if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
      {
         for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
         {
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;
            if ( KANUtil.filterEmpty( headerIds.toString() ) == null && KANUtil.filterEmpty( employeeName.toString() ) == null )
            {
               employeeName.append( timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( timesheetHeaderVO.getHeaderId() );
            }
            else
            {
               employeeName.append( "、" + timesheetHeaderVO.getEmployeeNameZH() );
               headerIds.append( "," + timesheetHeaderVO.getHeaderId() );
            }
         }
      }

      // 重新获取汇总TimesheetBatchVO
      final TimesheetBatchVO tempTimesheetBatchVO = getTimesheetBatchVOByHeaderIds( headerIds.toString() );

      if ( tempTimesheetBatchVO != null )
      {
         timesheetBatchVO.setCountOrderId( tempTimesheetBatchVO.getCountOrderId() );
         timesheetBatchVO.setCountContractId( tempTimesheetBatchVO.getCountContractId() );
         timesheetBatchVO.setCountHeaderId( tempTimesheetBatchVO.getCountHeaderId() );
         timesheetBatchVO.setTotalWortHours( tempTimesheetBatchVO.getTotalWortHours() );
         timesheetBatchVO.setTotalLeaveHours( tempTimesheetBatchVO.getTotalLeaveHours() );
         timesheetBatchVO.setTotalOTHours( tempTimesheetBatchVO.getTotalOTHours() );
         timesheetBatchVO.setDescription( employeeName.toString() );
      }

   }*/

   /* 提交Header
   private void submitHeader( final TimesheetHeaderVO timesheetHeaderVO, final String status, final String userId ) throws KANException
   {
      // 更改批次Header
      timesheetHeaderVO.setStatus( status );
      timesheetHeaderVO.setModifyBy( userId );
      timesheetHeaderVO.setModifyDate( new Date() );
      this.timesheetHeaderDao.updateTimesheetHeader( timesheetHeaderVO );
   }*/

   // 提交Header
   private void submit_header( final TimesheetHeaderVO timesheetHeaderVO, final String status, final String userId ) throws KANException
   {
      // 更改批次Header
      timesheetHeaderVO.setStatus( status );
      timesheetHeaderVO.setModifyBy( userId );
      timesheetHeaderVO.setModifyDate( new Date() );
      this.timesheetHeaderDao.updateTimesheetHeader( timesheetHeaderVO );
   }

   // Generate HistoryVO
   private HistoryVO generateHistoryVO( final TimesheetBatchVO timesheetBatchVO )
   {
      final HistoryVO history = timesheetBatchVO.getHistoryVO();

      history.setAccessAction( TimesheetBatchAction.accessActionInHouse );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( TimesheetBatchAction.accessActionInHouse ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getTimesheetBatchVOByBatchId" );

      // 表示是工作流的
      history.setObjectType( "2" );
      history.setAccountId( timesheetBatchVO.getAccountId() );

      return history;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByHeaderIds( final String headerIds ) throws KANException
   {
      return ( ( TimesheetBatchDao ) getDao() ).getTimesheetBatchVOByHeaderIds( headerIds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByTimesheetBatchVO( TimesheetBatchVO condition ) throws KANException
   {
      final TimesheetBatchDao timesheetBatchDao = ( TimesheetBatchDao ) getDao();
      List< Object > list = timesheetBatchDao.getTimesheetBatchVOsByCondition( condition );

      if ( list != null && list.size() > 0 )
      {
         return ( TimesheetBatchVO ) list.get( 0 );
      }
      return new TimesheetBatchVO();
   }

}
