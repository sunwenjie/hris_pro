package com.kan.base.service.inf.workflow;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowDefineRequirementsService
{

   public abstract PagedListHolder getWorkflowDefineRequirementsVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowDefineRequirementsVO getWorkflowDefineRequirementsVOByRequirementsId(final String requirementId ) throws KANException;

   public abstract int updateWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract int insertWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract void deleteWorkflowDefineRequirements( final WorkflowDefineRequirementsVO ...workflowDefineRequirementsVOs ) throws KANException;

   public abstract void deleteWorkflowDefineRequirementsByRequirementsId( final String modifyUserId , final String ...requirementId ) throws KANException;
}
