package com.kan.base.dao.mybatis.impl.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.workflow.WorkflowDefineStepsDao;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.util.KANException;

public class WorkflowDefineStepsDaoImpl extends Context implements WorkflowDefineStepsDao
{

   @Override
   public int countWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowDefineStepsVOsByCondition", workflowDefineStepsVO );
   }

   @Override
   public List< Object > getWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return selectList( "getWorkflowDefineStepsVOsByCondition", workflowDefineStepsVO );
   }

   @Override
   public List< Object > getWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getWorkflowDefineStepsVOsByCondition", workflowDefineStepsVO, rowBounds );
   }

   @Override
   public int updateWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return insert( "updateWorkflowDefineSteps", workflowDefineStepsVO );
   }

   @Override
   public int insertWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return insert( "insertWorkflowDefineSteps", workflowDefineStepsVO );
   }

   @Override
   public int deleteWorkflowDefineSteps( final String stepId ) throws KANException
   {
      return delete( "deleteWorkflowDefineSteps", stepId );
   }

   @Override
   public WorkflowDefineStepsVO getWorkflowDefineStepsVOByStepId( final String stepId ) throws KANException
   {
      return ( WorkflowDefineStepsVO ) select( "getWorkflowDefineStepsVOByStepId", stepId );
   }

   @Override
   public List< Object > getWorkflowDefineStepsVOsByDefineId( final String defineId ) throws KANException
   {
      return selectList( "getWorkflowDefineStepsVOsByDefineId", defineId );
   }

}