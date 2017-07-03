package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;

public interface AttendanceImportDetailDao
{
   public abstract int countAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract List< Object > getAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract List< Object > getAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract AttendanceImportDetailVO getAttendanceImportDetailVOByDetailId( final String headerId ) throws KANException;

   public abstract int insertAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract int updateAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract int deleteAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract List< Object > getAttendanceImportDetailVOsByHeaderId( final String headerId ) throws KANException;
}
