package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;

public interface AttendanceImportDetailService
{
   public abstract PagedListHolder getAttendanceImportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AttendanceImportDetailVO getAttendanceImportDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract int updateAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

   public abstract int deleteAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException;

}
