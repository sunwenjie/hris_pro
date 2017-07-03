package com.kan.base.dao.mybatis.impl.workflow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.workflow.WorkflowActualDao;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.util.KANException;

public class WorkflowActualDaoImpl extends Context implements WorkflowActualDao
{

   @Override
   public int countWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowActualVOsByCondition", workflowActualVO );
   }

   @Override
   public List< Object > getWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO ) throws KANException
   {
      return selectList( "getWorkflowActualVOsByCondition", workflowActualVO );
   }

   @Override
   public List< Object > getWorkflowActualVOsByCondition( final WorkflowActualVO workflowActualVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getWorkflowActualVOsByCondition", workflowActualVO, rowBounds );
   }
   
   @Override
   public List< Object > getWorkflowActualVOByMap( final Map< String, ? > map ) throws KANException
   {
      return selectList( "getWorkflowActualVOByMap", map );
   }

   @Override
   public int updateWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException
   {
      return update( "updateWorkflowActual", workflowActualVO );
   }

   @Override
   public int insertWorkflowActual( final WorkflowActualVO workflowActualVO ) throws KANException
   {
      return insert( "insertWorkflowActual", workflowActualVO );
   }

   @Override
   public int deleteWorkflowActual( final String defineId ) throws KANException
   {
      return delete( "deleteWorkflowActual", defineId );
   }

   @Override
   public WorkflowActualVO getWorkflowActualVOByWorkflowId( final String defineId ) throws KANException
   {
      return ( WorkflowActualVO ) select( "getWorkflowActualVOByWorkflowId", defineId );
   }

}