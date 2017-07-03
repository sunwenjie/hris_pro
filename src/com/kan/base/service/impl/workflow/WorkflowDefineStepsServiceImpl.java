package com.kan.base.service.impl.workflow;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.workflow.WorkflowDefineStepsDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineStepsService;
import com.kan.base.util.KANException;

public class WorkflowDefineStepsServiceImpl extends ContextService implements WorkflowDefineStepsService
{

   @Override
   public PagedListHolder getWorkflowDefineStepsVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final WorkflowDefineStepsDao workflowDefineStepsateDao = ( WorkflowDefineStepsDao ) getDao();
      pagedListHolder.setHolderSize( workflowDefineStepsateDao.countWorkflowDefineStepsVOsByCondition( ( WorkflowDefineStepsVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( workflowDefineStepsateDao.getWorkflowDefineStepsVOsByCondition( ( WorkflowDefineStepsVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( workflowDefineStepsateDao.getWorkflowDefineStepsVOsByCondition( ( WorkflowDefineStepsVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public WorkflowDefineStepsVO getWorkflowDefineStepsVOByStepsId( final String moduleId ) throws KANException
   {
      return ( ( WorkflowDefineStepsDao ) getDao() ).getWorkflowDefineStepsVOByStepId( moduleId );
   }

   @Override
   public int updateWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return ( ( WorkflowDefineStepsDao ) getDao() ).updateWorkflowDefineSteps( workflowDefineStepsVO );
   }

   @Override
   public int insertWorkflowDefineSteps( final WorkflowDefineStepsVO workflowDefineStepsVO ) throws KANException
   {
      return ( ( WorkflowDefineStepsDao ) getDao() ).insertWorkflowDefineSteps( workflowDefineStepsVO );

   }

   @Override
   public void deleteWorkflowDefineSteps( final WorkflowDefineStepsVO... workflowDefineStepsVOs ) throws KANException
   {
      if ( workflowDefineStepsVOs != null && workflowDefineStepsVOs.length > 0 )
      {
         try
         {
            startTransaction();
            for ( WorkflowDefineStepsVO workflowDefineStepsVO : workflowDefineStepsVOs )
            {
               WorkflowDefineStepsDao dao = ( WorkflowDefineStepsDao ) getDao();
               WorkflowDefineStepsVO workflowDefineSteps = dao.getWorkflowDefineStepsVOByStepId( workflowDefineStepsVO.getStepId() );
               workflowDefineSteps.setModifyBy( workflowDefineStepsVO.getModifyBy() );
               workflowDefineSteps.setModifyDate( new Date() );
               dao.updateWorkflowDefineSteps( workflowDefineSteps );
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
   public void deleteWorkflowDefineStepsByStepsId( final String modifyUserId, final String... stepIds ) throws KANException
   {
      if ( stepIds != null && stepIds.length > 0 )
      {
         try
         {
            startTransaction();
            for ( String stepId : stepIds )
            {
               WorkflowDefineStepsDao dao = ( WorkflowDefineStepsDao ) getDao();
               WorkflowDefineStepsVO workflowDefineSteps = dao.getWorkflowDefineStepsVOByStepId( stepId );
               workflowDefineSteps.setModifyBy( modifyUserId );
               workflowDefineSteps.setDeleted( BaseVO.FALSE );
               workflowDefineSteps.setModifyDate( new Date() );
               dao.updateWorkflowDefineSteps( workflowDefineSteps );
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
