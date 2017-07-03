package com.kan.base.dao.mybatis.impl;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.HistoryDao;
import com.kan.base.domain.HistoryVO;

public class HistoryDaoImpl extends Context implements HistoryDao
{
  
   @Override
   public int insertObject(final HistoryVO baseVO )
   {
      return insert( "insertHistory", baseVO );
   }

   @Override
   public int updateObject(final HistoryVO baseVO )
   {
      return insert( "updateHistory", baseVO );
   }

   @Override
   public int deleteObject(final String historyId )
   {
      return delete( "deleteHistory", historyId );
   }

   @Override
   public HistoryVO getObjectByHistoryId(final String historyId )
   {
      return (HistoryVO) select( "getHistoryByHistoryId", historyId );
   }
   
   @Override
   public HistoryVO getObjectByWorkflowId(final String workflowId )
   {
      return ( HistoryVO ) select( "getHistoryByWorkflowId", workflowId );
   }

}