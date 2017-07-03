package com.kan.base.dao.inf.define;

import java.util.List;

import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.util.KANException;

public interface ReportRelationDao
{
   public abstract int insertReportRelation( final ReportRelationVO reportRelationVO ) throws KANException;

   public abstract List< Object > getReportRelationVOsByReportHeaderId( final String reportHeaderId ) throws KANException;

   public abstract int updateReportRelation( final ReportRelationVO reportRelationVO ) throws KANException;
}
