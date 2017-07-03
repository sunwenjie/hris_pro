package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ReportSearchDetailService
{
   public abstract PagedListHolder getReportSearchDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ReportSearchDetailVO getReportSearchDetailVOByReportSearchDetailId( final String reportSearchDetailId ) throws KANException;

   public abstract int insertReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract int updateReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract int deleteReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract List< Object > getReportSearchDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException;
}
