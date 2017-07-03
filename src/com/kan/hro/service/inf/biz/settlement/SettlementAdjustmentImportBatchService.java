package com.kan.hro.service.inf.biz.settlement;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;

public interface SettlementAdjustmentImportBatchService
{

   public PagedListHolder getSettlementAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public SettlementAdjustmentImportBatchVO getSettlementAdjustmentImportBatchById( final String batchId ) throws KANException;

   public int updateSettlementAdjustmentImportBatch( final SettlementAdjustmentImportBatchVO submitObject ) throws KANException;

   public int backBatch( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException;
}
