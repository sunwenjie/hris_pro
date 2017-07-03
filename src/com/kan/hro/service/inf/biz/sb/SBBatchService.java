package com.kan.hro.service.inf.biz.sb;

import java.util.List;
import java.util.Map;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.sb.SBBatchVO;

public interface SBBatchService
{
   // Page Flag常量
   public static String PAGE_FLAG_BATCH = "batch";

   public static String PAGE_FLAG_CONTRACT = "contract";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   // Status Flag常量
   public static String STATUS_FLAG_PREVIEW = "preview";

   public static String STATUS_FLAG_APPROVE = "approve";

   public static String STATUS_FLAG_CONFIRM = "confirm";

   public static String STATUS_FLAG_SUBMIT = "submit";

   public abstract PagedListHolder getSBBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SBBatchVO getSBBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract List< Object > getSBBatchVOsByCondition( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract int updateSBBatch( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract int insertSBBatch( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract int insertSBBatch( final SBBatchVO sbBatchVO, final List< ServiceContractDTO > serviceContractDTOs ) throws KANException;

   public abstract int rollback( final SBBatchVO sbBatchVO, final Map< String, String > statusArgs ) throws KANException;

   public abstract Integer submit( final SBBatchVO sbBatchVO ) throws KANException;

   public abstract PagedListHolder getSBDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException;

   public abstract String getSBToApplyForMoreStatusCountByBatchIds( final String[] batchId ) throws KANException;

   public abstract String getSBToApplyForResigningStatusCountByBatchIds( final String[] batchId ) throws KANException;

   public abstract void generateHistoryVOForWorkflow( BaseVO baseVO ) throws KANException;
}
