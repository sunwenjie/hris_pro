package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface WorkflowModuleService
{

   public abstract PagedListHolder getWorkflowModuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract WorkflowModuleVO getWorkflowModuleVOByModuleId(final String moduleId ) throws KANException;

   public abstract int updateWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract int insertWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract void deleteWorkflowModule( final WorkflowModuleVO ...workflowModuleVO ) throws KANException;
   
   public abstract void deleteWorkflowModuleByModuleId( final String modifyUserId, final String ...moduleIds ) throws KANException;
   
   public abstract List< Object> listWorkflowModuleVO() throws KANException;

   //getNotUseWorkflowModule
   public abstract List< Object > getNotUseWorkflowModules() throws KANException;

   public abstract List< WorkflowModuleDTO > getAccountWorkflowDTOsByAccountId( final String accountId ) throws KANException;
}
