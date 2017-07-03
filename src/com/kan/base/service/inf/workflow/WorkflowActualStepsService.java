package com.kan.base.service.inf.workflow;

import java.util.List;

import com.kan.base.domain.workflow.WorkflowActualStepsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowActualStepsService
{
   public abstract PagedListHolder getWorkflowActualStepsVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowActualStepsVO getWorkflowActualStepsVOByStepsId( final String stepId ) throws KANException;

   public abstract int updateWorkflowActualStepsVO( final WorkflowActualStepsVO workflowActualStepsVO ) throws KANException;

   public abstract List< Object > getWorkflowActualStepsVOsByWorkflowId( final String workflowId ) throws KANException;

   public abstract boolean reSendApprovalMail( final String workflowId ) throws KANException;
}
