/*
 * Created on 2013-05-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class WorkflowAspect implements MethodInterceptor
{

   // ������service
   private WorkflowService workflowService;

   // ���Logger����
   protected Log logger = LogFactory.getLog( getClass() );


   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   @Override
   public Object invoke( MethodInvocation invo ) throws Throwable
   {
      Object[] arg1 = invo.getArguments();
      BaseVO baseVO = null;
      //����Ϊ���ҽ��뷽����
      if ( arg1 == null || arg1.length <= 0 )
      {
         Exception e = new KANException( "��������������������ȷ" );
         logger.info( "��������������������ȷ��", e );
         throw e;
      }
      for ( Object argO : arg1 )
      {
         if ( argO instanceof BaseVO )
         {
            baseVO = ( BaseVO ) argO;
            break;
         }
      }
      //�������Ϸ�
      if ( baseVO == null )
      {
         Exception e = new KANException( "��������������������ȷ" );
         logger.info( "��������������������ȷ��", e );
         throw e;
      }

      if ( baseVO.getHistoryVO() == null )
      {
         Exception e = new KANException( "������������historyVo��������ȷ" );
         logger.info( "������������historyVo��������ȷ��", e );
         throw e;
      }
      if ( !WorkflowService.isPassObject( baseVO ) )
      {
         //         //��ִ̬��
         //         ((WorkflowInterface)arg2).generateHistoryVO( baseVO, arg1 );
         //��ʼhistoryVoĬ������
         //����Ĭ��ֵ
         HistoryVO historyVO = baseVO.getHistoryVO();
         if ( StringUtils.isBlank( historyVO.getObjectClass() ) )
         {
            historyVO.setObjectClass( baseVO.getClass().getName() );
         }
         if ( StringUtils.isBlank( historyVO.getAccountId() ) || "0".equals( historyVO.getAccountId() ) )
         {
            historyVO.setAccountId( baseVO.getAccountId() );
         }
         if ( StringUtils.isBlank( historyVO.getObjectType() ) || "0".equals( historyVO.getObjectType() ) )
         {
            historyVO.setObjectType( "3" );
         }
         if ( StringUtils.isBlank( historyVO.getServiceBean() ) )
         {
            historyVO.setServiceBean( invo.getThis().getClass().getName() );
         }
         if ( StringUtils.isBlank( historyVO.getServiceMethod() ) )
         {
            historyVO.setServiceMethod( invo.getMethod().getName() );
         }
         if ( StringUtils.isBlank( historyVO.getPassObject() ) )
         {
            historyVO.setPassObject( KANUtil.toJSONObject( baseVO ).toString() );
         }
         // ���㹤����
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( baseVO );

         // ���ڹ�����
         if ( workflowActualDTO != null )
         {
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, baseVO );
         }
         else
         {
            return invo.proceed();
         }
         //�����ڹ����� ������������
      }
      else
      {
         return invo.proceed();
      }
      return new Integer( "0" );
   }
   
   
}