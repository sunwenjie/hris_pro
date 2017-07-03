package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;

public interface LeaveImportHeaderDao
{
   public abstract int countLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract int insertLeaveImportHeaderToLeaveHeader( final String batchId ) throws KANException;

   public abstract int insertLeaveDetailtempToLeaveDetail( final String batchId ) throws KANException;

   public abstract void deleteLeaveImportDetailTempByHeaderIds( List< String > selectIds );

   public abstract void deleteLeaveImportHeaderTempByHeaderIds( List< String > selectIds );

   public abstract int countLeaveImportHeaderVOsByBatchId( LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveImportHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int updateLeaveImportHeader( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException;

}
