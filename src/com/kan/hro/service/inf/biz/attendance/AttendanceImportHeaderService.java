package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;

public interface AttendanceImportHeaderService
{
   public abstract PagedListHolder getAttendanceImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AttendanceImportHeaderVO getAttendanceImportHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract int updateAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract int deleteAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

   public abstract int submitAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException;

}
