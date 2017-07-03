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

   // 工作流service
   private WorkflowService workflowService;

   // 添加Logger功能
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
      //参数为空我进入方法体
      if ( arg1 == null || arg1.length <= 0 )
      {
         Exception e = new KANException( "工作流拦截器参数不正确" );
         logger.info( "工作流拦截器参数不正确！", e );
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
      //参数不合法
      if ( baseVO == null )
      {
         Exception e = new KANException( "工作流拦截器参数不正确" );
         logger.info( "工作流拦截器参数不正确！", e );
         throw e;
      }

      if ( baseVO.getHistoryVO() == null )
      {
         Exception e = new KANException( "工作流拦截器historyVo参数不正确" );
         logger.info( "工作流拦截器historyVo参数不正确！", e );
         throw e;
      }
      if ( !WorkflowService.isPassObject( baseVO ) )
      {
         //         //动态执行
         //         ((WorkflowInterface)arg2).generateHistoryVO( baseVO, arg1 );
         //初始historyVo默认设置
         //设置默认值
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
         // 计算工作流
         final WorkflowActualDTO workflowActualDTO = this.getWorkflowService().getValidWorkflowActualDTO( baseVO );

         // 存在工作流
         if ( workflowActualDTO != null )
         {
            this.getWorkflowService().createWorkflowActual_nt( workflowActualDTO, baseVO );
         }
         else
         {
            return invo.proceed();
         }
         //不存在工作流 继续后续操作
      }
      else
      {
         return invo.proceed();
      }
      return new Integer( "0" );
   }
   
   
}