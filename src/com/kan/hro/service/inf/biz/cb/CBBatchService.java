package com.kan.hro.service.inf.biz.cb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;

public interface CBBatchService
{
   // Page Flag常量
   public static String PAGE_FLAG_BATCH = "batch";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   // Status Flag常量
   public static String STATUS_FLAG_PREVIEW = "preview";

   public static String STATUS_FLAG_APPROVE = "approve";

   public static String STATUS_FLAG_CONFIRM = "confirm";

   public static String STATUS_FLAG_SUBMIT = "submit";

   public abstract PagedListHolder getCBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract CBBatchVO getCBBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateCBBatch( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract int insertCBBatch( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract int rollback( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract int insertCBBatch( final CBBatchVO cbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException;

   public abstract int submit( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract PagedListHolder getCBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException;

}
