package com.kan.base.dao.inf.workflow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.util.KANException;

public interface WorkflowActualDao
{

   public abstract int countWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract List< Object > getWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract List< Object > getWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO, RowBounds rowBounds ) throws KANException;

   public abstract WorkflowActualVO getWorkflowActualVOByWorkflowId( final String workflwoId ) throws KANException;

   public abstract List< Object > getWorkflowActualVOByMap( final Map< String, ? > map ) throws KANException;

   public abstract int updateWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract int insertWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException;

   public abstract int deleteWorkflowActual( final String defineId ) throws KANException;

}