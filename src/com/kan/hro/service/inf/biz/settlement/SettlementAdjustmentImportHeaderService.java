package com.kan.hro.service.inf.biz.settlement;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;

public interface SettlementAdjustmentImportHeaderService
{
   public abstract PagedListHolder getSettlementAdjustmentImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;
   
   public abstract SettlementAdjustmentImportHeaderVO getSettlementAdjustmentImportHeaderVOsById( final String headerId,final String accountId )throws KANException;
}
