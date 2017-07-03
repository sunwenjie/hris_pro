package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface OTImportHeaderService
{
   public abstract PagedListHolder getOTImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract void updateOTImportBase( String allowanceId, String base ) throws KANException;

   public abstract int backUpRecord( String[] ids, String batchId ) throws KANException;

   public abstract List< Object > getOTImportHeaderVOsByBatchId( final String batchId ) throws KANException;
}
