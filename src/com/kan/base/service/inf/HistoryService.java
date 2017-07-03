package com.kan.base.service.inf;

import com.kan.base.domain.HistoryVO;
import com.kan.base.util.KANException;

public interface HistoryService
{
   public abstract HistoryVO getHistoryVOByWorkflowId(final String workflowId ) throws KANException;

   public abstract HistoryVO getHistoryVOByHistoryId( String historyId ) throws KANException;

}
