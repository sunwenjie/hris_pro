package com.kan.hro.service.inf.biz.sb;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;

public interface SBAdjustmentImportBatchService
{

   public PagedListHolder getSBAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public SBAdjustmentImportBatchVO getSBAdjustmentImportBatchById( final String batchId ) throws KANException;

   public int updateSBAdjustmentImportBatch( final SBAdjustmentImportBatchVO submitObject ) throws KANException;

   public int backBatch( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException;
}
