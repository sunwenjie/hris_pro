package com.kan.hro.service.inf.biz.sb;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface VendorSBTempService
{
   public abstract PagedListHolder getVendorSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int updateBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int rollbackBatch( final CommonBatchVO commonBatchVO ) throws KANException;

}
