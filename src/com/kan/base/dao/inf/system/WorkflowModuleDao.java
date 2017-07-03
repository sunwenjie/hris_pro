package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.util.KANException;

public interface WorkflowModuleDao
{
   
   public abstract int countWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract List< Object > getWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract List< Object > getWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO, RowBounds rowBounds ) throws KANException;

   public abstract WorkflowModuleVO getWorkflowModuleVOByWorkflowModuleId( final String moduleId ) throws KANException;
   
   public abstract int updateWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract int insertWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException;

   public abstract int deleteWorkflowModule( final  String moduleId) throws KANException;
   
   public abstract List< Object > listWorkflowModuleVO()throws KANException;
   
   public abstract List< Object > listUseWorkflowModuleByAccountId(final String accountId) throws KANException;
   
   //getNotUseWorkflowModule
   public abstract List< Object > getNotUseWorkflowModules() throws KANException;
   

}