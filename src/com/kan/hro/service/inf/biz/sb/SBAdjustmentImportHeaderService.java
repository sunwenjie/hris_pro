package com.kan.hro.service.inf.biz.sb;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;

public interface SBAdjustmentImportHeaderService
{
   public abstract PagedListHolder getSBAdjustmentImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;
   
   public abstract SBAdjustmentImportHeaderVO getSBAdjustmentImportHeaderVOsById( final String headerId,final String accountId )throws KANException;
}
