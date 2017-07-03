package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;

public interface BatchTempService
{

   // Page Flag常量（页面跳转用）
   public static String BATCH = "batch";

   public static String HEADER = "header";

   public static String CONTRACT = "contract";

   public static String DETAIL = "detail";

   public abstract PagedListHolder getBatchTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract BatchTempVO getBatchTempVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int insertBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int insertBatchTemp( final BatchTempVO batchTempVO, final List< ClientOrderDTO > clientOrderDTOs ) throws KANException;

   public abstract int insertBatchTemp_nt( final BatchTempVO batchTempVO, final List< ClientOrderDTO > clientOrderDTOs ) throws KANException;

   public abstract int deleteBatchTemp( final String batchId ) throws KANException;

   public abstract List< Object > getBatchTempVOsByAccountId( final String accountId ) throws KANException;

   public abstract int rollbackBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int submitBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int submitBatchTemp_nt( final BatchTempVO batchTempVO ) throws KANException;

}
