package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ReportDetailService
{
   public abstract PagedListHolder getReportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ReportDetailVO getReportDetailVOByReportDetailId( final String reportDetailId ) throws KANException;

   public abstract int insertReportDetail( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract int updateReportDetail( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract int deleteReportDetail( final ReportDetailVO reportDetailVO ) throws KANException;
   
   public abstract List< Object > getReportDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException;
}
