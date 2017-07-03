package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.util.KANException;

public interface ReportDetailDao
{
   public abstract int countReportDetailVOsByCondition( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract List< Object > getReportDetailVOsByCondition( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract List< Object > getReportDetailVOsByCondition( final ReportDetailVO reportDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract ReportDetailVO getReportDetailVOByReportDetailId( final String reportDetailId ) throws KANException;

   public abstract int insertReportDetail( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract int updateReportDetail( final ReportDetailVO reportDetailVO ) throws KANException;

   public abstract int deleteReportDetail( final String reportDetailId ) throws KANException;

   public abstract List< Object > getReportDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException;

}
