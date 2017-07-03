package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;

public interface AttendanceImportHeaderDao
{
   public abstract int countAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract List< Object > getAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract List< Object > getAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract AttendanceImportHeaderVO getAttendanceImportHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract int updateAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract int deleteAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract List< Object > getAttendanceImportHeaderVOsByBatchId( final String batchId ) throws KANException;
}
