package com.kan.base.dao.inf;

import java.util.List;

import com.kan.base.domain.BaseVO;

public interface BaseHistoryDao<T extends BaseVO>
{
   public int insertHistory(final T baseVO);
   
   public int updateHistory(final T baseVO);
   
   public int deleteHistory(final String objectId);
   
   public T getHistoryByHistoryId(final String objectId);
   
   public List<T> getObjectsByWorkflowId(final String workflowId);
   
   
}
