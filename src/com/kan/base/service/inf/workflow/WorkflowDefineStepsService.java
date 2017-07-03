package com.kan.base.service.inf.workflow;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowDefineStepsService
{

   public abstract PagedListHolder getWorkflowDefineStepsVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowDefineStepsVO getWorkflowDefineStepsVOByStepsId(final String stepId ) throws KANException;

   public abstract int updateWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract int insertWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException;

   public abstract void deleteWorkflowDefineSteps( final WorkflowDefineStepsVO ...workflowDefineStepsVOs ) throws KANException;

   public abstract void deleteWorkflowDefineStepsByStepsId( final String modifyUserId , final String ...stepId ) throws KANException;
}
