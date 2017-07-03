package com.kan.base.dao.inf.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.util.KANException;

public interface WorkflowDefineDao
{
   
   public abstract int countWorkflowDefineVOsByCondition( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineVOsByCondition( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract List< Object > getWorkflowDefineVOsByCondition( final WorkflowDefineVO workflowDefineVO, final RowBounds rowBounds ) throws KANException;

   public abstract WorkflowDefineVO getWorkflowDefineVOByDefineId( final String defineId ) throws KANException;
   
   public abstract int updateWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract int insertWorkflowDefine( final WorkflowDefineVO workflowDefineVO ) throws KANException;

   public abstract int deleteWorkflowDefine( final String defineId ) throws KANException;

   public abstract List< Object > getWorkflowDefineVOByAccountId( final WorkflowDefineVO workflowDefineVO ) throws KANException;
}