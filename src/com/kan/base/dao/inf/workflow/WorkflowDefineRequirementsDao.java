package com.kan.base.dao.inf.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.util.KANException;

public interface WorkflowDefineRequirementsDao
{
   public abstract int countWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO, final RowBounds rowBounds )
         throws KANException;

   public abstract WorkflowDefineRequirementsVO getWorkflowDefineRequirementsVOByRequirementId( final String requirementId ) throws KANException;

   public abstract int updateWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract int insertWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException;

   public abstract int deleteWorkflowDefineRequirements( final String requirementId ) throws KANException;

   public abstract List< Object > getWorkflowDefineRequirementsVOsByDefineId( final String defineId ) throws KANException;
}