package com.kan.base.service.impl.workflow;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.workflow.WorkflowDefineDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.util.KANException;

public class WorkflowDefineServiceImpl extends ContextService implements WorkflowDefineService
{

   @Override
   public PagedListHolder getWorkflowDefineVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final WorkflowDefineDao workflowDefineateDao = ( WorkflowDefineDao ) getDao();
      pagedListHolder.setHolderSize( workflowDefineateDao.countWorkflowDefineVOsByCondition( ( WorkflowDefineVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( workflowDefineateDao.getWorkflowDefineVOsByCondition( ( WorkflowDefineVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( workflowDefineateDao.getWorkflowDefineVOsByCondition( ( WorkflowDefineVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public WorkflowDefineVO getWorkflowDefineVOByDefineId( final String defineId ) throws KANException
   {
      return ( ( WorkflowDefineDao ) getDao() ).getWorkflowDefineVOByDefineId( defineId );
   }

   @Override
   public int updateWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return ( ( WorkflowDefineDao ) getDao() ).updateWorkflowDefine( workflowDefineVO );
   }

   @Override
   public int insertWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return ( ( WorkflowDefineDao ) getDao() ).insertWorkflowDefine( workflowDefineVO );

   }

   @Override
   public void deleteWorkflowDefine( final WorkflowDefineVO... workflowDefineVOs ) throws KANException
   {
      if ( workflowDefineVOs != null && workflowDefineVOs.length > 0 )
      {
         try
         {
            startTransaction();
            for ( WorkflowDefineVO workflowDefineVO : workflowDefineVOs )
            {
               WorkflowDefineDao dao = ( WorkflowDefineDao ) getDao();
               WorkflowDefineVO workflowDefine = dao.getWorkflowDefineVOByDefineId( workflowDefineVO.getDefineId() );
               workflowDefine.setModifyBy( workflowDefineVO.getModifyBy() );
               workflowDefine.setModifyDate( new Date() );
               workflowDefine.setDeleted( BaseVO.FALSE );
               dao.updateWorkflowDefine( workflowDefine );
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
   public void deleteWorkflowDefineByDefineId( final String modifyUserId, final String... defineIds ) throws KANException
   {
      if ( defineIds != null && defineIds.length > 0 )
      {
         try
         {
            startTransaction();
            for ( String defineId : defineIds )
            {
               WorkflowDefineDao dao = ( WorkflowDefineDao ) getDao();
               WorkflowDefineVO workflowDefine = dao.getWorkflowDefineVOByDefineId( defineId );
               workflowDefine.setModifyBy( modifyUserId );
               workflowDefine.setModifyDate( new Date() );
               workflowDefine.setDeleted( BaseVO.FALSE );
               dao.updateWorkflowDefine( workflowDefine );
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
