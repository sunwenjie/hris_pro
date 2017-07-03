package com.kan.base.service.impl;

import com.kan.base.core.ContextService;
import com.kan.base.dao.mybatis.impl.HistoryDaoImpl;
import com.kan.base.domain.HistoryVO;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.KANException;

public class HistoryServiceImpl extends ContextService implements HistoryService
{

   @Override
   public HistoryVO getHistoryVOByWorkflowId( String workflowId ) throws KANException
   {
    return ((HistoryDaoImpl)getDao()).getObjectByWorkflowId( workflowId );
   }
   
   @Override
   public HistoryVO getHistoryVOByHistoryId( String historyId ) throws KANException
   {
    return ((HistoryDaoImpl)getDao()).getObjectByHistoryId( historyId );
   }
   
   
}
