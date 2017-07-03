package com.kan.base.dao.inf.define;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.util.KANException;

public interface ReportHeaderDao
{
   public abstract int countReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract List< Object > getReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract List< Object > getReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract ReportHeaderVO getReportHeaderVOByReportHeaderId( final String reportHeaderId ) throws KANException;

   public abstract int insertReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract int updateReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException;

   public abstract int deleteReportHeader( final String reportHeaderId ) throws KANException;

   public abstract int countReportHeader( final String sql) throws KANException;

   public abstract List< Map <String,Object> > executeReportHeader( final String sql, final RowBounds rowBounds, final Object object ) throws KANException;

   public abstract List< Object > getAccountReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException;
}
