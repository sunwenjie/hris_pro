package com.kan.base.dao.mybatis.impl.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.workflow.WorkflowDefineRequirementsDao;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.util.KANException;

public class WorkflowDefineRequirementsDaoImpl extends Context implements WorkflowDefineRequirementsDao
{

   @Override
   public int countWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowDefineRequirementsVOsByCondition", workflowDefineRequirementsVO );
   }

   @Override
   public List< Object > getWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return selectList( "getWorkflowDefineRequirementsVOsByCondition", workflowDefineRequirementsVO );
   }

   @Override
   public List< Object > getWorkflowDefineRequirementsVOsByCondition( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getWorkflowDefineRequirementsVOsByCondition", workflowDefineRequirementsVO, rowBounds );
   }

   @Override
   public int updateWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return insert( "updateWorkflowDefineRequirements", workflowDefineRequirementsVO );
   }

   @Override
   public int insertWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return insert( "insertWorkflowDefineRequirements", workflowDefineRequirementsVO );
   }

   @Override
   public int deleteWorkflowDefineRequirements( final String requirementId ) throws KANException
   {
      return delete( "deleteWorkflowDefineRequirements", requirementId );
   }

   @Override
   public WorkflowDefineRequirementsVO getWorkflowDefineRequirementsVOByRequirementId( final String requirementId ) throws KANException
   {
      return ( WorkflowDefineRequirementsVO ) select( "getWorkflowDefineRequirementsVOByRequirementId", requirementId );
   }

   @Override
   public List< Object > getWorkflowDefineRequirementsVOsByDefineId( final String defineId ) throws KANException
   {
      return selectList( "getWorkflowDefineRequirementsVOsByDefineId", defineId );
   }

}