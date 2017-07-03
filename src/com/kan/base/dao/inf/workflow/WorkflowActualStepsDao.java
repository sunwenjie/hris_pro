package com.kan.base.dao.inf.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.util.KANException;

public interface WorkflowActualStepsDao
{
   public abstract int countWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;

   public abstract List< Object > getWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;

   public abstract List< Object > getWorkflowActualStepsVOsByCondition( final WorkflowActualStepsVO workflowActualStepsVO, RowBounds rowBounds ) throws KANException;

   public abstract WorkflowActualStepsVO getWorkflowActualStepsVOByStepId( final String stepsId ) throws KANException;

   public abstract int updateWorkflowActualSteps( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;

   public abstract int insertWorkflowActualSteps( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;

   public abstract int deleteWorkflowActualSteps( final String defineId ) throws KANException;

   public abstract List< Object > getWorkflowActualStepsVOsByWorkflowId( final String workflowId ) throws KANException;

   public abstract List< Object > getWorkflowApprovedChainByworkflowId( String workflowId ) throws KANException;

   public abstract List< Object > getWorkflowActualStepsVOsByWorkflowIdAndStepIndex( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;
}