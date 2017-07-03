package com.kan.base.dao.mybatis.impl.workflow;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.workflow.WorkflowDefineDao;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.util.KANException;

public class WorkflowDefineDaoImpl extends Context implements WorkflowDefineDao
{

   @Override
   public int countWorkflowDefineVOsByCondition(final WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowDefineVOsByCondition", workflowDefineVO );
   }

   @Override
   public List< Object > getWorkflowDefineVOsByCondition(final  WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return selectList( "getWorkflowDefineVOsByCondition", workflowDefineVO );
   }

   @Override
   public List< Object > getWorkflowDefineVOsByCondition(final  WorkflowDefineVO workflowDefineVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getWorkflowDefineVOsByCondition", workflowDefineVO, rowBounds );
   }
  
   @Override
   public int updateWorkflowDefine(final  WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return insert( "updateWorkflowDefine", workflowDefineVO );
   }

   @Override
   public int insertWorkflowDefine(final  WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return insert( "insertWorkflowDefine", workflowDefineVO );
   }

   @Override
   public int deleteWorkflowDefine(final String defineId ) throws KANException
   {
      return delete( "deleteWorkflowDefine", defineId );
   }

   @Override
   public WorkflowDefineVO getWorkflowDefineVOByDefineId(final  String defineId ) throws KANException
   {
      return ( WorkflowDefineVO ) select( "getWorkflowDefineVOByDefineId", defineId );
   }
   @Override
   public List< Object > getWorkflowDefineVOByAccountId(final  WorkflowDefineVO workflowDefineVO ) throws KANException
   {
      return selectList( "getWorkflowDefineVOByAccountId", workflowDefineVO );
   }

}