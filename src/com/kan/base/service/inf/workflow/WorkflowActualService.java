package com.kan.base.service.inf.workflow;

import java.util.List;

import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowActualService
{

   public abstract PagedListHolder getWorkflowActualVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowActualVO getWorkflowActualVOByWorkflowId( final String workflowId ) throws KANException;

   public abstract int updateWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract int insertWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract void deleteWorkflowActual( final WorkflowActualVO... workflowActualVO ) throws KANException;

   public abstract void deleteWorkflowActualByWorkflowId( final String modifyUserId, final String... workflowIds ) throws KANException;

   public abstract int countWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract List< Object > getNotFinishWorkflowActualVOsByPositionIds( final String positionIds[] ) throws KANException;
}
