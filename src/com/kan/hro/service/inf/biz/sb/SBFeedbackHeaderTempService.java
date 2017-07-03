package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBDTOTemp;
import com.kan.hro.domain.biz.sb.SBHeaderTempVO;

public interface SBFeedbackHeaderTempService
{
   // Page Flag³£Á¿
   public static String PAGE_FLAG_VENDOR = "vendor";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   public abstract PagedListHolder getSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getAmountVendorSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getVendorSBHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getVendorSBHeaderTempVOsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< SBDTOTemp > getSBDTOTempsByCondition( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract List< Object > getSBHeaderTempVOsByBatchId( final String sbBatchId ) throws KANException;

   public abstract SBHeaderTempVO getSBHeaderTempVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int insertSBHeaderTemp( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract int deleteSBHeaderTemp( final String sbHeaderId ) throws KANException;

   public abstract int submit( final SBHeaderTempVO sbHeaderTempVO ) throws KANException;

   public abstract void getSBDTOTempsByCondition( final PagedListHolder sbHeaderTempHolder ) throws KANException;

   public abstract int updateBatch( final CommonBatchVO commonBatchVO ) throws KANException;

}
