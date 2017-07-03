package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBDTO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;

public interface SBHeaderService
{
   // Page Flag常量
   public static String PAGE_FLAG_VENDOR = "vendor";

   public static String PAGE_FLAG_HEADER = "header";

   public static String PAGE_FLAG_DETAIL = "detail";

   public abstract PagedListHolder getSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getSBContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getAmountVendorSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getVendorSBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBContractVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getAmountVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getVendorSBHeaderVOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< SBDTO > getSBDTOsByCondition( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract List< Object > getSBHeaderVOsByBatchId( final String sbBatchId ) throws KANException;

   public abstract SBHeaderVO getSBHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int insertSBHeader( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract int deleteSBHeader( final String sbHeaderId ) throws KANException;

   public abstract int submit( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract void getSBDTOsByCondition( final PagedListHolder sbHeaderHolder ) throws KANException;

   public abstract List< Object > getMonthliesBySBHeaderVO( final SBHeaderVO sbHeaderVO ) throws KANException;

   // 修改sbheader的状态未缴纳为正常
   public abstract int updateSBHeaderPaid( final SBHeaderVO sbHeaderVO ) throws KANException;

   public abstract String getSBToApplyForMoreStatusCountByHeaderIds( final String[] headerId ) throws KANException;

   public abstract String getSBToApplyForResigningStatusCountByByHeaderIds( final String[] headerId ) throws KANException;

}
