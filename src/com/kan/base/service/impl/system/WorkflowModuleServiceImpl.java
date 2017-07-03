package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.WorkflowModuleDao;
import com.kan.base.dao.inf.workflow.WorkflowDefineDao;
import com.kan.base.dao.inf.workflow.WorkflowDefineRequirementsDao;
import com.kan.base.dao.inf.workflow.WorkflowDefineStepsDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.domain.workflow.WorkflowDefineDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.WorkflowModuleService;
import com.kan.base.util.KANException;

public class WorkflowModuleServiceImpl extends ContextService implements WorkflowModuleService
{

   private WorkflowDefineDao workflowDefineDao;
   private WorkflowDefineStepsDao workflowDefineStepsDao;
   private WorkflowDefineRequirementsDao workflowDefineRequirementsDao;

   public WorkflowDefineDao getWorkflowDefineDao()
   {
      return workflowDefineDao;
   }

   public void setWorkflowDefineDao( WorkflowDefineDao workflowDefineDao )
   {
      this.workflowDefineDao = workflowDefineDao;
   }

   public WorkflowDefineStepsDao getWorkflowDefineStepsDao()
   {
      return workflowDefineStepsDao;
   }

   public void setWorkflowDefineStepsDao( WorkflowDefineStepsDao workflowDefineStepsDao )
   {
      this.workflowDefineStepsDao = workflowDefineStepsDao;
   }

   public WorkflowDefineRequirementsDao getWorkflowDefineRequirementsDao()
   {
      return workflowDefineRequirementsDao;
   }

   public void setWorkflowDefineRequirementsDao( WorkflowDefineRequirementsDao workflowDefineRequirementsDao )
   {
      this.workflowDefineRequirementsDao = workflowDefineRequirementsDao;
   }

   @Override
   public PagedListHolder getWorkflowModuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final WorkflowModuleDao workflowModuleateDao = ( WorkflowModuleDao ) getDao();
      pagedListHolder.setHolderSize( workflowModuleateDao.countWorkflowModuleVOsByCondition( ( WorkflowModuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( workflowModuleateDao.getWorkflowModuleVOsByCondition( ( WorkflowModuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( workflowModuleateDao.getWorkflowModuleVOsByCondition( ( WorkflowModuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public WorkflowModuleVO getWorkflowModuleVOByModuleId( final String moduleId ) throws KANException
   {
      return ( ( WorkflowModuleDao ) getDao() ).getWorkflowModuleVOByWorkflowModuleId( moduleId );
   }

   @Override
   public int updateWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return ( ( WorkflowModuleDao ) getDao() ).updateWorkflowModule( workflowModuleVO );
   }

   @Override
   public int insertWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return ( ( WorkflowModuleDao ) getDao() ).insertWorkflowModule( workflowModuleVO );

   }

   @Override
   public void deleteWorkflowModule( final WorkflowModuleVO... workflowModuleVOs ) throws KANException
   {
      if ( workflowModuleVOs != null && workflowModuleVOs.length > 0 )
      {
         try
         {
            WorkflowModuleDao dao = ( WorkflowModuleDao ) getDao();
            startTransaction();
            for ( WorkflowModuleVO workflowModuleVO : workflowModuleVOs )
            {
               WorkflowModuleVO workflowModule = dao.getWorkflowModuleVOByWorkflowModuleId( workflowModuleVO.getWorkflowModuleId() );
               workflowModule.setDeleted( BaseVO.FALSE );
               workflowModule.setModifyBy( workflowModuleVO.getModifyBy() );
               workflowModule.setModifyDate( new Date() );
               dao.updateWorkflowModule( workflowModule );
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
   public void deleteWorkflowModuleByModuleId( final String modifyUserId, final String... moduleIds ) throws KANException
   {
      if ( moduleIds != null && moduleIds.length > 0 )
      {
         try
         {
            WorkflowModuleDao dao = ( WorkflowModuleDao ) getDao();
            startTransaction();
            for ( String moduleId : moduleIds )
            {
               WorkflowModuleVO workflowModule = dao.getWorkflowModuleVOByWorkflowModuleId( moduleId );
               workflowModule.setDeleted( BaseVO.FALSE );
               workflowModule.setModifyBy( modifyUserId );
               workflowModule.setModifyDate( new Date() );
               dao.updateWorkflowModule( workflowModule );
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
   public List< Object > listWorkflowModuleVO() throws KANException
   {
      return ( ( WorkflowModuleDao ) getDao() ).listWorkflowModuleVO();
   }

   @Override
   public List< Object > getNotUseWorkflowModules() throws KANException
   {
      return ( ( WorkflowModuleDao ) getDao() ).getNotUseWorkflowModules();
   }

   @Override
   // Code Reviewed by Siuvan Xia at 2014-5-26
   public List< WorkflowModuleDTO > getAccountWorkflowDTOsByAccountId( final String accountId ) throws KANException
   {
      // ��ʼ������ֵ
      final List< WorkflowModuleDTO > workflowModuleDTOs = new ArrayList< WorkflowModuleDTO >();

      // ��ȡsystem WorkflowModuleVO�б�
      final List< Object > systemWorkflowModuleVOs = ( ( WorkflowModuleDao ) getDao() ).listUseWorkflowModuleByAccountId( accountId );

      // system����WorkflowModuleVO�б�
      if ( systemWorkflowModuleVOs != null && systemWorkflowModuleVOs.size() > 0 )
      {
         // ����WorkflowModuleVO�б�
         for ( Object systemWorkflowModuleVOObject : systemWorkflowModuleVOs )
         {
            final WorkflowModuleVO workflowModuleVO = ( WorkflowModuleVO ) systemWorkflowModuleVOObject;

            // ��ʼ��WorkflowModuleDTO
            final WorkflowModuleDTO workflowModuleDTO = new WorkflowModuleDTO();

            // ����WorkflowModuleDTO��WorkflowModule
            workflowModuleDTO.setWorkflowModuleVO( workflowModuleVO );

            // ��ʼ��workflowDefineVOSearch���ڴ���
            final WorkflowDefineVO workflowDefineVOSearch = new WorkflowDefineVO();
            workflowDefineVOSearch.setWorkflowModuleId( workflowModuleVO.getWorkflowModuleId() );
            workflowDefineVOSearch.setAccountId( accountId );

            // ��ʼ��WorkflowDefineDTO�б�
            final List< WorkflowDefineDTO > workflowDefineDTOs = new ArrayList< WorkflowDefineDTO >();

            // ��ȡaccountId�µ�WorkflowDefineVOs
            final List< Object > workflowDefineVOs = this.getWorkflowDefineDao().getWorkflowDefineVOByAccountId( workflowDefineVOSearch );

            //����workflowDefineVOs 
            for ( Object workflowDefineVOObject : workflowDefineVOs )
            {
               final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) workflowDefineVOObject;

               // ��ʼ��WorkflowDefineDTO
               final WorkflowDefineDTO workflowDefineDTO = new WorkflowDefineDTO();
               workflowDefineDTO.setWorkflowDefineVO( workflowDefineVO );

               // ��ȡWorkflowDefineStepsVOs
               final List< Object > workflowDefineStepsVOs = this.getWorkflowDefineStepsDao().getWorkflowDefineStepsVOsByDefineId( workflowDefineVO.getDefineId() );

               // ����WorkflowDefineStepsVOs
               if ( workflowDefineStepsVOs != null && workflowDefineStepsVOs.size() > 0 )
               {
                  for ( Object workflowDefineStepsVOObject : workflowDefineStepsVOs )
                  {
                     workflowDefineDTO.getWorkflowDefineStepsVOs().add( ( WorkflowDefineStepsVO ) workflowDefineStepsVOObject );
                  }
               }

               // ��ȡWorkflowDefineRequirementsVOs
               final List< Object > workflowDefineRequirementsVOs = this.getWorkflowDefineRequirementsDao().getWorkflowDefineRequirementsVOsByDefineId( workflowDefineVO.getDefineId() );

               // ����WorkflowDefineRequirementsVOs
               if ( workflowDefineRequirementsVOs != null && workflowDefineRequirementsVOs.size() > 0 )
               {
                  for ( Object workflowDefineRequirementsVOObject : workflowDefineRequirementsVOs )
                  {
                     workflowDefineDTO.getWorkflowDefineRequirementsVOs().add( ( WorkflowDefineRequirementsVO ) workflowDefineRequirementsVOObject );
                  }
               }

               workflowDefineDTOs.add( workflowDefineDTO );
            }

            // ����workflowModuleDTO��workflowDefineDTOs
            workflowModuleDTO.setWorkflowDefineDTO( workflowDefineDTOs );

            // workflowModuleDTO��ӵ�workflowModuleDTOs�б�
            workflowModuleDTOs.add( workflowModuleDTO );
         }
      }

      return workflowModuleDTOs;
   }

}
