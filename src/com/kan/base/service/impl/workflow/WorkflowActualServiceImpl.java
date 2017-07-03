package com.kan.base.service.impl.workflow;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.workflow.WorkflowActualDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.util.KANException;

public class WorkflowActualServiceImpl extends ContextService implements WorkflowActualService
{

   @Override
   public PagedListHolder getWorkflowActualVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final WorkflowActualVO workflowActual = ( WorkflowActualVO ) pagedListHolder.getObject();

      final WorkflowActualDao workflowActualateDao = ( WorkflowActualDao ) getDao();
      pagedListHolder.setHolderSize( workflowActualateDao.countWorkflowActualVOsByCondition( workflowActual ) );
      if ( isPaged )
      {
         pagedListHolder.getSource().addAll( workflowActualateDao.getWorkflowActualVOsByCondition( workflowActual, new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.getSource().addAll( workflowActualateDao.getWorkflowActualVOsByCondition( workflowActual ) );
      }

      return pagedListHolder;
   }

   @Override
   public List< Object > getNotFinishWorkflowActualVOsByPositionIds( final String positionIds[] ) throws KANException
   {
      Map< String, Object > searchMap = new HashMap< String, Object >();
      searchMap.put( "status", "1" );// 开始状态的工作流
      searchMap.put( "auditType", "1" );// 审批类型 1:内部职位ID
      searchMap.put( "auditTargetIds", positionIds );// 检查的职位
      searchMap.put( "actualStepStatuses", new String[] { "1", "2" } );

      final WorkflowActualDao workflowActualateDao = ( WorkflowActualDao ) getDao();
      return workflowActualateDao.getWorkflowActualVOByMap( searchMap );
   }

   @Override
   public WorkflowActualVO getWorkflowActualVOByWorkflowId( final String workflowId ) throws KANException
   {
      return ( ( WorkflowActualDao ) getDao() ).getWorkflowActualVOByWorkflowId( workflowId );
   }

   @Override
   public int updateWorkflowActual( final WorkflowActualVO workflowModuleVO ) throws KANException
   {
      return ( ( WorkflowActualDao ) getDao() ).updateWorkflowActual( workflowModuleVO );
   }

   @Override
   public int insertWorkflowActual( final WorkflowActualVO workflowModuleVO ) throws KANException
   {
      return ( ( WorkflowActualDao ) getDao() ).insertWorkflowActual( workflowModuleVO );

   }

   @Override
   public void deleteWorkflowActual( final WorkflowActualVO... workflowModuleVOs ) throws KANException
   {
      if ( workflowModuleVOs != null && workflowModuleVOs.length > 0 )
      {
         try
         {
            WorkflowActualDao dao = ( WorkflowActualDao ) getDao();
            startTransaction();
            for ( WorkflowActualVO workflowModuleVO : workflowModuleVOs )
            {
               WorkflowActualVO workflowModule = dao.getWorkflowActualVOByWorkflowId( workflowModuleVO.getWorkflowId() );
               workflowModule.setDeleted( BaseVO.FALSE );
               workflowModule.setModifyBy( workflowModuleVO.getModifyBy() );
               workflowModule.setModifyDate( new Date() );
               dao.updateWorkflowActual( workflowModule );
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
   public void deleteWorkflowActualByWorkflowId( final String modifyUserId, final String... workflowIds ) throws KANException
   {
      if ( workflowIds != null && workflowIds.length > 0 )
      {
         try
         {
            WorkflowActualDao dao = ( WorkflowActualDao ) getDao();
            startTransaction();
            for ( String workflowId : workflowIds )
            {
               WorkflowActualVO workflowModule = dao.getWorkflowActualVOByWorkflowId( workflowId );
               workflowModule.setDeleted( BaseVO.FALSE );
               workflowModule.setModifyBy( modifyUserId );
               workflowModule.setModifyDate( new Date() );
               dao.updateWorkflowActual( workflowModule );
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
   public int countWorkflowActualVOsByCondition( WorkflowActualVO workflowActualVO ) throws KANException
   {
      return ( ( WorkflowActualDao ) getDao() ).countWorkflowActualVOsByCondition( workflowActualVO );
   }

}
