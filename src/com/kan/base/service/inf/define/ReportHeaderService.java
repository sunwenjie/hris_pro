package com.kan.base.service.inf.define;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.page.PagedReportListHolder;
import com.kan.base.util.KANException;

public interface ReportHeaderService
{
   public abstract PagedListHolder getReportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ReportHeaderVO getReportHeaderVOByReportHeaderId( final String reportHeaderId ) throws KANException;

   public abstract int insertReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract int updateReportHeader( final ReportHeaderVO reportHeaderVO ,final Map<String,Map<String,ColumnVO>> columnVOMap) throws KANException;

   public abstract int deleteReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract List< ReportDTO > getReportDTOsByAccountId( final String accountId ) throws KANException;

   public abstract ReportDTO getReportDTOByReportHeaderId( final String reportHeaderId ) throws KANException;

   public abstract PagedReportListHolder executeReportHeader( final String sql, final PagedReportListHolder pagedListHolder, final boolean isPaged )
         throws KANException;
   
   public abstract List<Object> getReportRelationVOsByReportHeaderId( final String reportHeaderId ) throws KANException;
   
   public abstract List<Object> getReportColumnVOsByReportHeaderId( final String reportHeaderId ) throws KANException;

   public abstract int updateReportColumn( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract List<Object> getReportHeaderVOsByCondition(ReportHeaderVO reportHeaderVO)
         throws KANException;
}
