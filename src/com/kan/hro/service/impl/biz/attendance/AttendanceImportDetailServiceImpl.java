package com.kan.hro.service.impl.biz.attendance;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportDetailDao;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportDetailService;

public class AttendanceImportDetailServiceImpl extends ContextService implements AttendanceImportDetailService
{

   @Override
   public PagedListHolder getAttendanceImportDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final AttendanceImportDetailDao attendanceImportDetailDao = ( AttendanceImportDetailDao ) getDao();
      pagedListHolder.setHolderSize( attendanceImportDetailDao.countAttendanceImportDetailVOsByCondition( ( AttendanceImportDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( attendanceImportDetailDao.getAttendanceImportDetailVOsByCondition( ( AttendanceImportDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( attendanceImportDetailDao.getAttendanceImportDetailVOsByCondition( ( AttendanceImportDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public AttendanceImportDetailVO getAttendanceImportDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( AttendanceImportDetailDao ) getDao() ).getAttendanceImportDetailVOByDetailId( detailId );
   }

   @Override
   public int insertAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return ( ( AttendanceImportDetailDao ) getDao() ).insertAttendanceImportDetail( attendanceImportDetailVO );
   }

   @Override
   public int updateAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return ( ( AttendanceImportDetailDao ) getDao() ).updateAttendanceImportDetail( attendanceImportDetailVO );
   }

   @Override
   public int deleteAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      final AttendanceImportDetailVO modifyObject = ( ( AttendanceImportDetailDao ) getDao() ).getAttendanceImportDetailVOByDetailId( attendanceImportDetailVO.getDetailId() );
      modifyObject.setDeleted( AttendanceImportDetailVO.FALSE );
      return ( ( AttendanceImportDetailDao ) getDao() ).updateAttendanceImportDetail( modifyObject );
   }

}
