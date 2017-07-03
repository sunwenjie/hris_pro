package com.kan.base.service.impl.workflow;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.workflow.WorkflowDefineRequirementsDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineRequirementsService;
import com.kan.base.util.KANException;

public class WorkflowDefineRequirementsServiceImpl extends ContextService implements WorkflowDefineRequirementsService
{
   @Override
   public PagedListHolder getWorkflowDefineRequirementsVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final WorkflowDefineRequirementsDao workflowDefineRequirementsateDao = ( WorkflowDefineRequirementsDao ) getDao();
      pagedListHolder.setHolderSize( workflowDefineRequirementsateDao.countWorkflowDefineRequirementsVOsByCondition( ( WorkflowDefineRequirementsVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( workflowDefineRequirementsateDao.getWorkflowDefineRequirementsVOsByCondition( ( WorkflowDefineRequirementsVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( workflowDefineRequirementsateDao.getWorkflowDefineRequirementsVOsByCondition( ( WorkflowDefineRequirementsVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public WorkflowDefineRequirementsVO getWorkflowDefineRequirementsVOByRequirementsId( final String moduleId ) throws KANException
   {
      return ( ( WorkflowDefineRequirementsDao ) getDao() ).getWorkflowDefineRequirementsVOByRequirementId( moduleId );
   }

   @Override
   public int updateWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return ( ( WorkflowDefineRequirementsDao ) getDao() ).updateWorkflowDefineRequirements( workflowDefineRequirementsVO );
   }

   @Override
   public int insertWorkflowDefineRequirements( final WorkflowDefineRequirementsVO workflowDefineRequirementsVO ) throws KANException
   {
      return ( ( WorkflowDefineRequirementsDao ) getDao() ).insertWorkflowDefineRequirements( workflowDefineRequirementsVO );

   }

   @Override
   public void deleteWorkflowDefineRequirements( final WorkflowDefineRequirementsVO... workflowDefineRequirementsVOs ) throws KANException
   {
      if ( workflowDefineRequirementsVOs != null && workflowDefineRequirementsVOs.length > 0 )
      {
         try
         {
            startTransaction();
            for ( WorkflowDefineRequirementsVO workflowDefineRequirementsVO : workflowDefineRequirementsVOs )
            {
               WorkflowDefineRequirementsDao dao = ( WorkflowDefineRequirementsDao ) getDao();
               WorkflowDefineRequirementsVO workflowDefineRequirements = dao.getWorkflowDefineRequirementsVOByRequirementId( workflowDefineRequirementsVO.getRequirementId() );
               workflowDefineRequirements.setModifyBy( workflowDefineRequirementsVO.getModifyBy() );
               workflowDefineRequirements.setModifyDate( new Date() );
               dao.updateWorkflowDefineRequirements( workflowDefineRequirements );
            }
            commitTransaction();
         }
         catch ( Exception e )
         {
            rollbackTransaction();
            throw new KANException( e );
         }
      }
   }

   @Override
   public void deleteWorkflowDefineRequirementsByRequirementsId( final String modifyUserId, final String... requirementIds ) throws KANException
   {
      if ( requirementIds != null && requirementIds.length > 0 )
      {
         try
         {
            startTransaction();
            for ( String requirementId : requirementIds )
            {
               WorkflowDefineRequirementsDao dao = ( WorkflowDefineRequirementsDao ) getDao();
               WorkflowDefineRequirementsVO workflowDefineRequirements = dao.getWorkflowDefineRequirementsVOByRequirementId( requirementId );
               workflowDefineRequirements.setModifyBy( modifyUserId );
               workflowDefineRequirements.setDeleted( BaseVO.FALSE );
               workflowDefineRequirements.setModifyDate( new Date() );
               dao.updateWorkflowDefineRequirements( workflowDefineRequirements );
            }
            commitTransaction();
         }
         catch ( Exception e )
         {
            rollbackTransaction();
            throw new KANException( e );
         }
      }
   }

}
