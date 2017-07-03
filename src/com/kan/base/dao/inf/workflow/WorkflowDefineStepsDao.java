package com.kan.base.dao.inf.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.util.KANException;

public interface WorkflowDefineStepsDao
{
   public abstract int countWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineStepsVOsByCondition( final WorkflowDefineStepsVO workflowDefineStepsVO, RowBounds rowBounds ) throws KANException;

   public abstract WorkflowDefineStepsVO getWorkflowDefineStepsVOByStepId( final String stepId ) throws KANException;

   public abstract int updateWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract int insertWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract int deleteWorkflowDefineSteps( final String stepId ) throws KANException;

   public abstract List< Object > getWorkflowDefineStepsVOsByDefineId( final String defineId ) throws KANException;

}