package com.kan.base.dao.inf;

import com.kan.base.domain.HistoryVO;

public interface HistoryDao
{
   public abstract int insertObject(final HistoryVO historyVO);
   
   public abstract int updateObject(final HistoryVO historyVO);
   
   public abstract int deleteObject(final String historyId);
   
   public abstract HistoryVO getObjectByHistoryId(final String historyId);
   
   public abstract HistoryVO getObjectByWorkflowId(final String workflowId );
}
