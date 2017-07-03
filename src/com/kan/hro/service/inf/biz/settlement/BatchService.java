package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.BatchVO;

public interface BatchService
{
   public abstract PagedListHolder getBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BatchVO getBatchVOByBatchId( final String batchId ) throws KANException;
   
   public abstract BatchVO getLastestBatchVOByAccountId( final String accountId ) throws KANException;

   public abstract int updateBatch( final BatchVO batchVO ) throws KANException;

   public abstract int insertBatch( final BatchVO batchVO ) throws KANException;

   public abstract int deleteBatch( final String batchId ) throws KANException;
   
   public abstract List< Object > getBatchVOsByAccountId( final String accountId )throws KANException;
}
