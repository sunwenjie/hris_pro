package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.util.KANException;

public interface ReportSearchDetailDao
{
   public abstract int countReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract List< Object > getReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract List< Object > getReportSearchDetailVOsByCondition( final ReportSearchDetailVO reportSearchDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract ReportSearchDetailVO getReportSearchDetailVOByReportSearchDetailId( final String reportSearchDetailId ) throws KANException;

   public abstract int insertReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract int updateReportSearchDetail( final ReportSearchDetailVO reportSearchDetailVO ) throws KANException;

   public abstract int deleteReportSearchDetail( final String reportSearchDetailId ) throws KANException;

   public abstract List< Object > getReportSearchDetailVOsByReportHeaderId( final String reportHeaderId ) throws KANException;
}
