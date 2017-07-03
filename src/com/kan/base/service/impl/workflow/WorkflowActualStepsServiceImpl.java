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
   // ���Logger����
   protected Log logger = LogFactory.getLog( getClass() );

   // ע��WorkflowDefineDao
   private WorkflowDefineDao workflowDefineDao;

   // ע��WorkflowActualDao
   private WorkflowActualDao workflowActualDao;

   // ע��HistoryDao
   private HistoryDao historyDao;

   /** �������õ���Service **/
   private EmployeeContractService employeeContractService;// �Ͷ���ͬ �ύ����ְ
   private LeaveHeaderService leaveHeaderService;// ���
   private EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService;// ��н
   private EmployeePositionChangeService employeePositionChangeService;// �춯
   private EmployeeContractSalaryService employeeContractSalaryService;// �Ͷ���ͬ��н

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
      //�Ƿ������һ������
      boolean isLastApproving = false;
      // ��ʼ����  
      // this.startTransaction();

      // ��ʼ��WorkflowActualStepsDao
      final WorkflowActualStepsDao dao = ( WorkflowActualStepsDao ) getDao();

      // ����ʼ�������Կ
      workflowActualStepsVO.setRandomKey( "" );
      // �������ʱ��
      workflowActualStepsVO.setHandleDate( new Date() );
      workflowActualStepsVO.setModifyDate( new Date() );
      dao.updateWorkflowActualSteps( workflowActualStepsVO );

      // ��ȡWorkflowActualVO
      final WorkflowActualVO workflowActualVO = this.getWorkflowActualDao().getWorkflowActualVOByWorkflowId( workflowActualStepsVO.getWorkflowId() );

      // ��ȡWorkflowDefineVO
      final WorkflowDefineVO workflowDefineVO = this.getWorkflowDefineDao().getWorkflowDefineVOByDefineId( workflowActualVO.getDefineId() );

      // ��ȡHistoryVO
      final HistoryVO historyVO = this.getHistoryDao().getObjectByWorkflowId( workflowActualStepsVO.getWorkflowId() );

      try
      {
         // ������������ͨ��
         if ( "3".equals( workflowActualStepsVO.getStatus() ) )
         {
            // ��ʼ��WorkflowActualStepsVO������һ�������
            final WorkflowActualStepsVO searchWorkflowActualStepsVO = new WorkflowActualStepsVO();
            searchWorkflowActualStepsVO.setStatus( "2" );
            searchWorkflowActualStepsVO.setStepIndex( workflowActualStepsVO.getStepIndex() );
            searchWorkflowActualStepsVO.setWorkflowId( workflowActualStepsVO.getWorkflowId() );

            // ���Ҵ�����ͬ�������˲���
            final List< Object > workflowActualStepsVOs = dao.getWorkflowActualStepsVOsByCondition( searchWorkflowActualStepsVO );

            if ( workflowActualStepsVOs == null || workflowActualStepsVOs.size() > 0 )
            {
               // �ȴ���һ��������,�˴������κβ���
            }
            else
            {
               // ���ҡ�δ��ʼ������δ֪ͨ������һ�������
               //searchWorkflowActualStepsVO.setStatus( "1,6" );
               searchWorkflowActualStepsVO.setStepIndex( workflowActualStepsVO.getStepIndex() + 1 );

               // ��ȡWorkflowActualStepsVO����һ��������б�
               final List< Object > nextWorkflowActualStepsVOs = dao.getWorkflowActualStepsVOsByWorkflowIdAndStepIndex( searchWorkflowActualStepsVO );

               // ������һ��������б�
               if ( nextWorkflowActualStepsVOs != null && nextWorkflowActualStepsVOs.size() > 0 )
               {
                  hasNextWorkflowActualStep( dao, workflowActualVO, nextWorkflowActualStepsVOs, historyVO, workflowDefineVO );
               }
               // ��������һ��������б�
               else
               {
                  invokeXX( "3", historyVO, workflowDefineVO, workflowActualVO, workflowActualStepsVO, "1" );
                  // �����һ������
                  isLastApproving = true;
               }
            }
         }
         // �������������˻�
         else if ( "4".equals( workflowActualStepsVO.getStatus() ) )
         {
            invokeXX( "2", historyVO, workflowDefineVO, workflowActualVO, workflowActualStepsVO, "2" );
         }

         // �ύ����
         //this.commitTransaction();
         //�����һ������,ͬ��΢����Ϣ
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
            logger.error( "������΢��ͬ������" );
            e.printStackTrace();
         }
      }
      catch ( Exception e )
      {
         // �ع�����
         //this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   // ������һ��ʵ�ʹ�������˲���
   // Add by siuvan 2014-09-01
   private void hasNextWorkflowActualStep( final WorkflowActualStepsDao dao, final WorkflowActualVO workflowActualVO, final List< Object > nextWorkflowActualStepsVOs,
         final HistoryVO historyVO, final WorkflowDefineVO workflowDefineVO ) throws Exception
   {
      // ����������Ϣ����
      for ( Object workfowActualStepsVOObject : nextWorkflowActualStepsVOs )
      {
         // ��ʼ��WorkflowActualStepsVO
         final WorkflowActualStepsVO nextWorkfowActualStepsVO = ( WorkflowActualStepsVO ) workfowActualStepsVOObject;

         // �ο����裨ֻ��֪ͨ������������
         if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
         {
            // ��Ϊδ֪ͨ
            nextWorkfowActualStepsVO.setStatus( "6" );
         }
         else
         {
            // ������
            nextWorkfowActualStepsVO.setStatus( "2" );
         }

         // ����Ϣ����
         if ( workflowActualVO != null )
         {
            nextWorkfowActualStepsVO.setHistoryVO( historyVO );
            KANSendMessageUtil.newInstance().sendMessageForWorkflow( workflowActualVO, nextWorkfowActualStepsVO );

            // ��Ϊ��֪ͨ
            if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
            {
               nextWorkfowActualStepsVO.setStatus( "5" );
            }

            ( ( WorkflowActualStepsDao ) getDao() ).updateWorkflowActualSteps( nextWorkfowActualStepsVO );
         }
      }

      // ���±���
      for ( Object workfowActualStepsVOObject : nextWorkflowActualStepsVOs )
      {
         // ��ʼ��WorkflowActualStepsVO
         final WorkflowActualStepsVO nextWorkfowActualStepsVO = ( WorkflowActualStepsVO ) workfowActualStepsVOObject;

         // ����ǲο����裬����������һ�������
         if ( "3".equals( nextWorkfowActualStepsVO.getStepType() ) )
         {
            // ��ʼ��WorkflowActualStepsVO������һ�������
            final WorkflowActualStepsVO searchWorkflowActualStepsVO = new WorkflowActualStepsVO();
            searchWorkflowActualStepsVO.setStatus( "1,2,6" );
            searchWorkflowActualStepsVO.setStepIndex( nextWorkfowActualStepsVO.getStepIndex() + 1 );
            searchWorkflowActualStepsVO.setWorkflowId( nextWorkfowActualStepsVO.getWorkflowId() );

            // ��ȡWorkflowActualStepsVO����һ��������б�
            final List< Object > workflowActualStepsVOs = dao.getWorkflowActualStepsVOsByCondition( searchWorkflowActualStepsVO );

            // ������һ��������б�
            if ( workflowActualStepsVOs != null && workflowActualStepsVOs.size() > 0 )
            {
               hasNextWorkflowActualStep( dao, workflowActualVO, workflowActualStepsVOs, historyVO, workflowDefineVO );
            }
            // ��������һ��������б�
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
   //      // 1�������������Ѿ���ɻ���ֹͣ
   //      workflowActualVO.setStatus( workflowActualStatus );
   //      this.getWorkflowActualDao().updateWorkflowActual( workflowActualVO );
   //
   //      // 2���������ͨ���ġ�history�� ��Update�� ��ʵ�ʱ���
   //      if ( historyVO != null && KANUtil.filterEmpty( historyVO.getPassObject() ) != null )
   //      {
   //         // ��ʼ��Service
   //         Object targetService = null;
   //         targetService = ServiceLocator.getService( historyVO.getServiceBean() );
   //
   //         // ��ʽ�����ͨ������
   //         JSONObject object = null;
   //         // �����ͨ��
   //         if ( tempStatus.equals( "1" ) )
   //         {
   //            object = JSONObject.fromObject( historyVO.getPassObject() );
   //         }
   //         else
   //         {
   //            object = JSONObject.fromObject( historyVO.getFailObject() );
   //         }
   //         // ����õ�Service����
   //         final Class< ? > targetServiceClass = targetService.getClass();
   //         // ����õ���˶���
   //         final Class< ? > objClass = Class.forName( historyVO.getObjectClass() );
   //
   //         // ��ȡ��˶���ġ�update������
   //         Method method = null;
   //
   //         method = targetServiceClass.getMethod( historyVO.getServiceMethod(), objClass );
   //
   //         // ִ�С�update������
   //         if ( method != null )
   //         {
   //            if ( "3".equals( historyVO.getObjectType() ) && tempStatus.equals( "2" ) )
   //            {
   //               //�¹������˻ز���Ҫ�޸�״̬
   //            }
   //            else
   //            {
   //               // ��˶���ǿתBaseVO
   //               final BaseVO targetObject = ( BaseVO ) JSONObject.toBean( object, objClass );
   //               targetObject.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
   //               // ��ǰ�����������ͬ�⡱��ͬ�⡱
   //               targetObject.getHistoryVO().setTempStatus( tempStatus );
   //               targetObject.setLocale( workflowActualStepsVO.getLocale() );
   //               method.invoke( targetService, targetObject );
   //            }
   //         }
   //
   //         // 3��������history�Ѿ�����ʷ 31 Ϊ�¹�������������ʷ��¼
   //         if ( "3".equals( historyVO.getObjectType() ) )
   //         {
   //            //2Ϊ�˻�
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
   //         // 4������Ϣ�����ϱ���
   //         KANSendMessageUtil.newInstance().sendMessageToCreateBy( workflowDefineVO, workflowActualVO, workflowActualStepsVO, tempStatus );
   //      }
   //   }

   private void invokeXX( final String workflowActualStatus, final HistoryVO historyVO, final WorkflowDefineVO workflowDefineVO, final WorkflowActualVO workflowActualVO,
         final WorkflowActualStepsVO workflowActualStepsVO, final String tempStatus ) throws Exception
   {
      // 1�������������Ѿ���ɻ���ֹͣ
      workflowActualVO.setStatus( workflowActualStatus );
      this.getWorkflowActualDao().updateWorkflowActual( workflowActualVO );

      // 2���������ͨ���ġ�history�� ��Update�� ��ʵ�ʱ���
      if ( historyVO != null && KANUtil.filterEmpty( historyVO.getPassObject() ) != null )
      {
         final Class< ? > objClass = Class.forName( historyVO.getObjectClass() );

         // ��ʽ�����ͨ������
         JSONObject object = null;
         // �����ͨ��
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
            //�¹������˻ز���Ҫ�޸�״̬
         }
         else
         {
            // ��ٹ�����ִ��
            if ( LeaveHeaderVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final LeaveHeaderVO leaveHeaderVO = ( LeaveHeaderVO ) JSONObject.toBean( object, objClass );
               leaveHeaderVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               leaveHeaderVO.getHistoryVO().setTempStatus( tempStatus );
               leaveHeaderVO.setLocale( workflowActualStepsVO.getLocale() );
               // ����ύ
               if ( "submitLeaveHeader_nt".equals( historyVO.getServiceMethod() ) )
               {
                  leaveHeaderService.submitLeaveHeader_nt( leaveHeaderVO );
               }
               // ����
               else if ( "sick_leave_nt".equals( historyVO.getServiceMethod() ) )
               {
                  leaveHeaderService.sick_leave_nt( leaveHeaderVO );
               }
            }
            // �Ͷ���ͬ������ִ��
            else if ( EmployeeContractVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) JSONObject.toBean( object, objClass );
               employeeContractVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeeContractVO.getHistoryVO().setTempStatus( tempStatus );
               employeeContractVO.setLocale( workflowActualStepsVO.getLocale() );
               // ��ͨ�ύ
               if ( "submitEmployeeContract_nt".equals( historyVO.getServiceMethod() ) )
               {
                  employeeContractService.submitEmployeeContract_nt( employeeContractVO );
               }
               // ��ְ�ύ
               else if ( "submitEmployeeContract_leave".equals( historyVO.getServiceMethod() ) )
               {
                  employeeContractService.submitEmployeeContract_leave( employeeContractVO );
               }
            }
            // ְλ�춯������ִ��
            else if ( EmployeePositionChangeVO.class.getCanonicalName().equals( historyVO.getObjectClass() ) )
            {
               final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) JSONObject.toBean( object, objClass );
               employeePositionChangeVO.getHistoryVO().setHistoryId( historyVO.getHistoryId() );
               employeePositionChangeVO.getHistoryVO().setTempStatus( tempStatus );
               employeePositionChangeVO.setLocale( workflowActualStepsVO.getLocale() );
               employeePositionChangeService.submitEmployeePositionChange( employeePositionChangeVO );
            }
            // н�����������ִ��
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

         // 3��������history�Ѿ�����ʷ 31 Ϊ�¹�������������ʷ��¼
         if ( "3".equals( historyVO.getObjectType() ) )
         {
            //2Ϊ�˻�
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

         // 4������Ϣ�����ϱ���
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
