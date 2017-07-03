package com.kan.base.service.impl.workflow;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.HistoryDao;
import com.kan.base.dao.inf.workflow.WorkflowActualDao;
import com.kan.base.dao.inf.workflow.WorkflowActualStepsDao;
import com.kan.base.dao.inf.workflow.WorkflowDefineDao;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.message.KANSendMessageUtil;
import com.kan.base.service.inf.workflow.WorkflowActualStepsService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;

public class WorkflowActualStepsServiceImpl extends ContextService implements WorkflowActualStepsService
{
   // 添加Logger功能
   protected Log logger = LogFactory.getLog( getClass() );

   // 注入WorkflowDefineDao
   private WorkflowDefineDao workflowDefineDao;

   // 注入WorkflowActualDao
   private WorkflowActualDao workflowActualDao;

   // 注入HistoryDao
   private HistoryDao historyDao;

   /** 工作流用到的Service **/
   private EmployeeContractService employeeContractService;// 劳动合同 提交、离职
   private LeaveHeaderService leaveHeaderService;// 请假
   private EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService;// 调薪
   private EmployeePositionChangeService employeePositionChangeService;// 异动
   private EmployeeContractSalaryService employeeContractSalaryService;// 劳动合同调薪

   public EmployeeContractService getEmployeeContractService()
   {
      return employeeContractService;
   }

   public void setEmployeeContractService( EmployeeContractService employeeContractService )
   {
      this.employeeContractService = employeeContractService;
   }

   public LeaveHeaderService getLeaveHeaderService()
   {
      return leaveHeaderService;
   }

   public void setLeaveHeaderService( LeaveHeaderService leaveHeaderService )
   {
      this.leaveHeaderService = leaveHeaderService;
   }

   public EmployeeSalaryAdjustmentService getEmployeeSalaryAdjustmentService()
   {
      return employeeSalaryAdjustmentService;
   }

   public void setEmployeeSalaryAdjustmentService( EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService )
   {
      this.employeeSalaryAdjustmentService = employeeSalaryAdjustmentService;
   }

   public EmployeePositionChangeService getEmployeePositionChangeService()
   {
      return employeePositionChangeService;
   }

   public void setEmployeePositionChangeService( EmployeePositionChangeService employeePositionChangeService )
   {
      this.employeePositionChangeService = employeePositionChangeService;
   }

   public EmployeeContractSalaryService getEmployeeContractSalaryService()
   {
      return employeeContractSalaryService;
   }

   public void setEmployeeContractSalaryService( EmployeeContractSalaryService employeeContractSalaryService )
   {
      this.employeeContractSalaryService = employeeContractSalaryService;
   }

   public HistoryDao getHistoryDao()
   {
      return historyDao;
   }

   public void setHistoryDao( HistoryDao historyDao )
   {
      this.historyDao = historyDao;
   }

   public WorkflowActualDao getWorkflowActualDao()
   {
      return workflowActualDao;
   }

   public WorkflowDefineDao getWorkflowDefineDao()
   {
      return workflowDefineDao;
   }

   public void setWorkflowDefineDao( WorkflowDefineDao workflowDefineDao )
   {
      this.workflowDefineDao = workflowDefineDao;
   }

   public void setWorkflowActualDao( WorkflowActualDao workflowActualDao )
   {
      this.workflowActualDao = workflowActualDao;
   }

   @Override
   public PagedListHolder getWorkflowActualStepsVOByCondition( final PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {

      final WorkflowActualStepsDao workflowActualStepsDao = ( WorkflowActualStepsDao ) getDao();
      pagedListHolder.setHolderSize( workflowActualStepsDao.countWorkflowActualStepsVOsByCondition( ( WorkflowActualStepsVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( workflowActualStepsDao.getWorkflowActualStepsVOsByCondition( ( WorkflowActualStepsVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( workflowActualStepsDao.getWorkflowActualStepsVOsByCondition( ( WorkflowActualStepsVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public WorkflowActualStepsVO getWorkflowActualStepsVOByStepsId( final String stepId ) throws KANException
   {
      return ( ( WorkflowActualStepsDao ) getDao() ).getWorkflowActualStepsVOByStepId( stepId );
   }

   @Override
   public int updateWorkflowActualStepsVO( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      //是否是最后一个审批
      boolean isLastApproving = false;
      // 开始事务  
      // this.startTransaction();

      // 初始化WorkflowActualStepsDao
      final WorkflowActualStepsDao dao = ( WorkflowActualStepsDao ) getDao();

      // 清空邮件审批秘钥
      workflowActualStepsVO.setRandomKey( "" );
      // 处理操作时间
      workflowActualStepsVO.setHandleDate( new Date() );
      workflowActualStepsVO.setModifyDate( new Date() );
      dao.updateWorkflowActualSteps( workflowActualStepsVO );

      // 获取WorkflowActualVO
      final WorkflowActualVO workflowActualVO = this.getWorkflowActualDao().getWorkflowActualVOByWorkflowId( workflowActualStepsVO.getWorkflowId() );

      // 获取WorkflowDefineVO
      final WorkflowDefineVO workflowDefineVO = this.getWorkflowDefineDao().getWorkflowDefineVOByDefineId( workflowActualVO.getDefineId() );

      // 获取HistoryVO
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualStepsVO.getWorkflowId() );

      try
      {
         // 如果审批意见是通过
         if ( "3".equals( workflowActualStepsVO.getStatus() ) )
         {
            // 初始化WorkflowActualStepsVO查找下一步审核人
            final WorkflowActualStepsVO searchWorkflowActualStepsVO = new WorkflowActualStepsVO();
            searchWorkflowActualStepsVO.setStatus( "2" );
            searchWorkflowActualStepsVO.setStepIndex( workflowActualStepsVO.getStepIndex() );
            searchWorkflowActualStepsVO.setWorkflowId( workflowActualStepsVO.getWorkflowId() );

            // 查找处于相同级别的审核步骤
            final List< Object > workflowActualStepsVOs = dao.getWorkflowActualStepsVOsByCondition( searchWorkflowActualStepsVO );

            if ( workflowActualStepsVOs == null || workflowActualStepsVOs.size() > 0 )
            {
               // 等待下一审核人审核,此处不用任何操作
            }
            else
            {
               // 查找“未开始”、“未通知”的下一步审核人
               //searchWorkflowActualStepsVO.setStatus( "1,6" );
               searchWorkflowActualStepsVO.setStepIndex( workflowActualStepsVO.getStepIndex() + 1 );

               // 获取WorkflowActualStepsVO（下一步审核人列表）
               final List< Object > nextWorkflowActualStepsVOs = dao.getWorkflowActualStepsVOsByWorkflowIdAndStepIndex( searchWorkflowActualStepsVO );

               // 存在下一步审核人列表
               if ( nextWorkflowActualStepsVOs != null && nextWorkflowActualStepsVOs.size() > 0 )
               {
                  hasNextWorkflowActualStep( dao, workflowActualVO, nextWorkflowActualStepsVOs, historyVO, workflowDefineVO );
               }
               // 不存在下一步审核人列表
               else
               {
                  invokeXX( "3", historyVO, workflowDefineVO, workflowActualVO, workflowActualStepsVO, "1" );
                  // 是最后一个审批
                  isLastApproving = true;
               }
            }
         }
         // 如果审批意见是退回
         else if ( "4".equals( workflowActualStepsVO.getStatus() ) )
         {
            invokeXX( "2", historyVO, workflowDefineVO, workflowActualVO, workflowActualStepsVO, "2" );
         }

         // 提交事务
         //this.commitTransaction();
         //是最后一个审批,同步微信信息
         try
         {
            if ( isLastApproving )
            {
               final String objClass = historyVO.getObjectClass();
               if ( "com.kan.hro.domain.biz.employee.EmployeeContractVO".equals( objClass ) )
               {
                  final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getPassObject() ), Class.forName( objClass ) );
                  if ( employeeContractVO != null )
                  {
                     BaseAction.syncWXContacts( employeeContractVO.getEmployeeId() );
                  }
               }
               else if ( "com.kan.hro.domain.biz.employee.EmployeePositionChangeVO".equals( objClass ) )
               {
                  final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) JSONObject.toBean( JSONObject.fromObject( historyVO.getPassObject() ), Class.forName( objClass ) );
                  if ( employeePositionChangeVO != null )
                  {
                     BaseAction.syncWXContacts( employeePositionChangeVO.getEmployeeId() );
                  }
               }

            }
         }
         catch ( Exception e )
         {
            logger.error( "工作流微信同步出错" );
            e.printStackTrace();
         }
      }
      catch ( Exception e )
      {
         // 回滚事务
         //this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   // 存在下一步实际工作流审核步骤
   // Add by siuvan 2014-09-01
   private void hasNextWorkflowActualStep( final WorkflowActualStepsDao dao, final WorkflowActualVO workflowActualVO, final List< Object > nextWorkflowActualStepsVOs,
         final HistoryVO historyVO, final WorkflowDefineVO workflowDefineVO ) throws Exception
   {
      // 遍历发送消息提醒
      for ( Object workfowActualStepsVOObject : nextWorkflowActualStepsVOs )
      {
         // 初始化WorkflowActualStepsVO
         final WorkflowActualStepsVO nextWorkfowActualStepsVO = ( WorkflowActualStepsVO ) workfowActualStepsVOObject;

         // 参考步骤（只发通知不参与审批）
         if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
         {
            // 改为未通知
            nextWorkfowActualStepsVO.setStatus( "6" );
         }
         else
         {
            // 待操作
            nextWorkfowActualStepsVO.setStatus( "2" );
         }

         // 发消息提醒
         if ( workflowActualVO != null )
         {
            nextWorkfowActualStepsVO.setHistoryVO( historyVO );
            KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, nextWorkfowActualStepsVO );

            // 改为已通知
            if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
            {
               nextWorkfowActualStepsVO.setStatus( "5" );
            }

            ( ( WorkflowActualStepsDao ) getDao() ).updateWorkflowActualSteps( nextWorkfowActualStepsVO );
         }
      }

      // 重新遍历
      for ( Object workfowActualStepsVOObject : nextWorkflowActualStepsVOs )
      {
         // 初始化WorkflowActualStepsVO
         final WorkflowActualStepsVO nextWorkfowActualStepsVO = ( WorkflowActualStepsVO ) workfowActualStepsVOObject;

         // 如果是参考步骤，继续查找下一步审核人
         if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
         {
            // 初始化WorkflowActualStepsVO查找下一步审核人
            final WorkflowActualStepsVO searchWorkflowActualStepsVO = new WorkflowActualStepsVO();
            searchWorkflowActualStepsVO.setStatus( "1,2,6" );
            searchWorkflowActualStepsVO.setStepIndex( nextWorkfowActualStepsVO.getStepIndex() + 1 );
            searchWorkflowActualStepsVO.setWorkflowId( nextWorkfowActualStepsVO.getWorkflowId() );

            // 获取WorkflowActualStepsVO（下一步审核人列表）
            final List< Object > workflowActualStepsVOs = dao.getWorkflowActualStepsVOsByCondition( searchWorkflowActualStepsVO );

            // 存在下一步审核人列表
            if ( workflowActualStepsVOs != null && workflowActualStepsVOs.size() > 0 )
            {
               hasNextWorkflowActualStep( dao, workflowActualVO, workflowActualStepsVOs, historyVO, workflowDefineVO );
            }
            // 不存在下一步审核人列表
            else
            {
               invokeXX( "3", historyVO, workflowDefineVO, workflowActualVO, nextWorkfowActualStepsVO, "1" );
            }
         }
      }
   }

   //   private void invokeXX( final String workflowActualStatus, final HistoryVO historyVO, final WorkflowDefineVO workflowDefineVO, final WorkflowActualVO workflowActualVO,
   //         final WorkflowActualStepsVO workflowActualStepsVO, final String tempStatus ) throws Exception
   //   {
   //      // 1、标记审核流程已经完成或者停止
   //      workflowActualVO.setStatus( workflowActualStatus );
   //      this.getWorkflowActualDao().updateWorkflowActual( workflowActualVO );
   //
   //      // 2、将对象从通过的“history” “Update” 到实际表中
   //      if ( historyVO != null && KANUtil.filterEmpty( historyVO.getPassObject() ) != null )
   //      {
   //         // 初始化Service
   //         Object targetService = null;
   //         targetService = ServiceLocator.getService( historyVO.getServiceBean() );
   //
   //         // 格式化审核通过对象
   //         JSONObject object = null;
   //         // 如果是通过
   //         if ( tempStatus.equals( "1" ) )
   //         {
   //            object = JSONObject.fromObject( historyVO.getPassObject() );
   //         }
   //         else
   //         {
   //            object = JSONObject.fromObject( historyVO.getFailObject() );
   //         }
   //         // 反射得到Service对象
   //         final Class< ? > targetServiceClass = targetService.getClass();
   //         // 反射得到审核对象
   //         final Class< ? > objClass = Class.forName( historyVO.getObjectClass() );
   //
   //         // 获取审核对象的“update”方法
   //         Method method = null;
   //
   //         method = targetServiceClass.getMethod( historyVO.getServiceMethod(), objClass );
   //
   //         // 执行“update”方法
   //         if ( method != null )
   //         {
   //            if ( "3".equals( historyVO.getObjectType() ) && tempStatus.equals( "2" ) )
   //            {
   //               //新工作流退回不需要修改状态
   //            }
   //            else
   //            {
   //               // 审核对象强转BaseVO
   //               final BaseVO targetObject = ( BaseVO ) JSONObject.toBean( object, objClass );
   //               targetObject.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
   //               // 当前审批意见“不同意”或“同意”
   //               targetObject.getHistoryVO().setTempStatus( tempStatus );
   //               targetObject.setLocale( workflowActualStepsVO.getLocale() );
   //               method.invoke( targetService, targetObject );
   //            }
   //         }
   //
   //         // 3、标记这个history已经是历史 31 为新工作流的审批历史记录
   //         if ( "3".equals( historyVO.getObjectType() ) )
   //         {
   //            //2为退回
   //            if ( tempStatus.equals( "2" ) )
   //            {
   //               historyVO.setObjectType( "32" );
   //            }
   //            else
   //            {
   //               historyVO.setObjectType( "31" );
   //            }
   //         }
   //         else
   //         {
   //            historyVO.setObjectType( "1" );
   //         }
   //         this.getHistoryDao().updateObject( historyVO );
   //
   //         if ( tempStatus.equals( "2" ) )
   //         {
   //            workflowActualStepsVO.setHistoryVO( historyVO );
   //         }
   //
   //         // 4、发消息提醒上报人
   //         KANSendMessageUtil.newInstance().sendMessageToCreateBy( workflowDefineVO, workflowActualVO, workflowActualStepsVO, tempStatus );
   //      }
   //   }

   private void invokeXX( final String workflowActualStatus, final HistoryVO historyVO, final WorkflowDefineVO workflowDefineVO, final WorkflowActualVO workflowActualVO,
         final WorkflowActualStepsVO workflowActualStepsVO, final String tempStatus ) throws Exception
   {
      // 1、标记审核流程已经完成或者停止
      workflowActualVO.setStatus( workflowActualStatus );
      this.getWorkflowActualDao().updateWorkflowActual( workflowActualVO );

      // 2、将对象从通过的“history” “Update” 到实际表中
      if ( historyVO != null && KANUtil.filterEmpty( historyVO.getPassObject() ) != null )
      {
         final Class< ? > objClass = Class.forName( historyVO.getObjectClass() );

         // 格式化审核通过对象
         JSONObject object = null;
         // 如果是通过
         if ( tempStatus.equals( "1" ) )
         {
            object = JSONObject.fromObject( historyVO.getPassObject() );
         }
         else
         {
            object = JSONObject.fromObject( historyVO.getFailObject() );
         }

         if ( "3".equals( historyVO.getObjectType() ) && tempStatus.equals( "2" ) )
         {
            //新工作流退回不需要修改状态
         }
         else
         {
            // 请假工作流执行
            if ( LeaveHeaderVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) JSONObject.toBean( object, objClass );
               leaveHeaderVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               leaveHeaderVO.getHistoryVO().setTempStatus( tempStatus );
               leaveHeaderVO.setLocale( workflowActualStepsVO.getLocale() );
               // 请假提交
               if ( "submitLeaveHeader_nt".equals( historyVO.getServiceMethod() ) )
               {
                  leaveHeaderService.submitLeaveHeader_nt( leaveHeaderVO );
               }
               // 销假
               else if ( "sick_leave_nt".equals( historyVO.getServiceMethod() ) )
               {
                  leaveHeaderService.sick_leave_nt( leaveHeaderVO );
               }
            }
            // 劳动合同工作流执行
            else if ( EmployeeContractVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) JSONObject.toBean( object, objClass );
               employeeContractVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeeContractVO.getHistoryVO().setTempStatus( tempStatus );
               employeeContractVO.setLocale( workflowActualStepsVO.getLocale() );
               // 普通提交
               if ( "submitEmployeeContract_nt".equals( historyVO.getServiceMethod() ) )
               {
                  employeeContractService.submitEmployeeContract_nt( employeeContractVO );
               }
               // 离职提交
               else if ( "submitEmployeeContract_leave".equals( historyVO.getServiceMethod() ) )
               {
                  employeeContractService.submitEmployeeContract_leave( employeeContractVO );
               }
            }
            // 职位异动工作流执行
            else if ( EmployeePositionChangeVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) JSONObject.toBean( object, objClass );
               employeePositionChangeVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeePositionChangeVO.getHistoryVO().setTempStatus( tempStatus );
               employeePositionChangeVO.setLocale( workflowActualStepsVO.getLocale() );
               employeePositionChangeService.submitEmployeePositionChange( employeePositionChangeVO );
            }
            // 薪酬调整工作流执行
            else if ( EmployeeSalaryAdjustmentVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) JSONObject.toBean( object, objClass );
               employeeSalaryAdjustmentVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeeSalaryAdjustmentVO.getHistoryVO().setTempStatus( tempStatus );
               employeeSalaryAdjustmentVO.setLocale( workflowActualStepsVO.getLocale() );
               employeeSalaryAdjustmentService.submitEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
            }
            else if ( EmployeeContractSalaryVO.class.getCanonicalName().equalsIgnoreCase( historyVO.getObjectClass() ) )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) JSONObject.toBean( object, objClass );
               employeeContractSalaryVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeeContractSalaryVO.getHistoryVO().setTempStatus( tempStatus );
               employeeContractSalaryVO.setLocale( workflowActualStepsVO.getLocale() );
               employeeContractSalaryService.submitEmployeeContractSalary( employeeContractSalaryVO );
            }
         }

         // 3、标记这个history已经是历史 31 为新工作流的审批历史记录
         if ( "3".equals( historyVO.getObjectType() ) )
         {
            //2为退回
            if ( tempStatus.equals( "2" ) )
            {
               historyVO.setObjectType( "32" );
            }
            else
            {
               historyVO.setObjectType( "31" );
            }
         }
         else
         {
            historyVO.setObjectType( "1" );
         }
         this.getHistoryDao().updateObject( historyVO );

         if ( tempStatus.equals( "2" ) )
         {
            workflowActualStepsVO.setHistoryVO( historyVO );
         }

         // 4、发消息提醒上报人
         KANSendMessageUtil.newInstance().sendMessageToCreateBy( workflowDefineVO, workflowActualVO, workflowActualStepsVO, tempStatus );
      }
   }

   @Override
   public List< Object > getWorkflowActualStepsVOsByWorkflowId( final String workflowId ) throws KANException
   {
      return ( ( WorkflowActualStepsDao ) getDao() ).getWorkflowActualStepsVOsByWorkflowId( workflowId );
   }

   @Override
   public boolean reSendApprovalMail( String workflowId ) throws KANException
   {
      boolean flag = false;
      try
      {
         final WorkflowActualVO workflowActualVO = this.getWorkflowActualDao().getWorkflowActualVOByWorkflowId( workflowId );
         final List< Object > steps = ( ( WorkflowActualStepsDao ) getDao() ).getWorkflowActualStepsVOsByWorkflowId( workflowId );
         //startTransaction();

         if ( workflowActualVO != null && steps != null && steps.size() > 0 )
         {
            for ( Object step : steps )
            {
               final WorkflowActualStepsVO tempStep = ( WorkflowActualStepsVO ) step;
               if ( tempStep.getStatus().equals( "2" ) )
               {
                  if ( StringUtils.isEmpty( tempStep.getRemark4() ) || !KANUtil.formatDate( new Date(), "yyyy-MM-dd" ).equals( tempStep.getRemark4() ) )
                  {
                     tempStep.setRemark4( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
                     tempStep.setRemark5( "1" );
                     ( ( WorkflowActualStepsDao ) getDao() ).updateWorkflowActualSteps( tempStep );
                     KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, tempStep );
                     flag = true;
                     break;
                  }
               }
            }
         }

         //this.commitTransaction();
      }
      catch ( Exception e )
      {
         //this.rollbackTransaction();
         throw new KANException( e );
      }

      return flag;
   }

}
