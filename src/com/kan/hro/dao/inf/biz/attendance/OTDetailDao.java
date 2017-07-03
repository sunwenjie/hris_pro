package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.OTDetailVO;

public interface OTDetailDao
{
   public abstract int countOTDetailVOsByCondition( final OTDetailVO otDetailVO ) throws KANException;

   public abstract List< Object > getOTDetailVOsByCondition( final OTDetailVO otDetailVO ) throws KANException;

   public abstract List< Object > getOTDetailVOsByCondition( final OTDetailVO otDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract OTDetailVO getOTDetailVOByOTDetailId( final String otDetailId ) throws KANException;

   public abstract int insertOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract int updateOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract int deleteOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract List< Object > getOTDetailVOsByOTHeaderId( final String otHeaderId ) throws KANException;

   public abstract List< Object > getOTDetailVOsByContractId( final String contractId ) throws KANException;

   public abstract int insertOTImportDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract List< Object > getOTDetailVOsByTimesheetId( final String timesheetId ) throws KANException;
}
