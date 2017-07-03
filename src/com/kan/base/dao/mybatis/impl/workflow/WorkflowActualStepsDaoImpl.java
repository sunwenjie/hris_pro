package com.kan.base.dao.mybatis.impl.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.workflow.WorkflowActualStepsDao;
import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.util.KANException;

public class WorkflowActualStepsDaoImpl extends Context implements WorkflowActualStepsDao
{

   @Override
   public int countWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowActualStepsVOsByCondition", workflowActualStepsVO );
   }

   @Override
   public List< Object > getWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      return selectList( "getWorkflowActualStepsVOsByCondition", workflowActualStepsVO );
   }

   @Override
   public List< Object > getWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getWorkflowActualStepsVOsByCondition", workflowActualStepsVO, rowBounds );
   }

   @Override
   public int updateWorkflowActualSteps( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      return update( "updateWorkflowActualSteps", workflowActualStepsVO );
   }

   @Override
   public int insertWorkflowActualSteps( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      return insert( "insertWorkflowActualSteps", workflowActualStepsVO );
   }

   @Override
   public int deleteWorkflowActualSteps( final String defineId ) throws KANException
   {
      return delete( "deleteWorkflowActualSteps", defineId );
   }

   @Override
   public WorkflowActualStepsVO getWorkflowActualStepsVOByStepId( final String stepId ) throws KANException
   {
      return ( WorkflowActualStepsVO ) select( "getWorkflowActualStepsVOByStepId", stepId );
   }

   @Override
   public List< Object > getWorkflowActualStepsVOsByWorkflowId( final String workflowId ) throws KANException
   {
      return selectList( "getWorkflowActualStepsVOsByWorkflowId", workflowId );
   }

   @Override
   public List< Object > getWorkflowApprovedChainByworkflowId( final String workflowId ) throws KANException
   {
      return selectList( "getWorkflowApprovedChainByworkflowId", workflowId );
   }

   @Override
   public List< Object > getWorkflowActualStepsVOsByWorkflowIdAndStepIndex( WorkflowActualStepsVO workflowActualStepsVO ) throws KANException
   {
      return selectList( "getWorkflowActualStepsVOsByWorkflowIdAndStepIndex", workflowActualStepsVO );
   }

}