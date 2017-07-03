package com.kan.hro.service.impl.biz.attendance;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.OTHeaderDao;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportHeaderDao;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;
import com.kan.hro.service.inf.biz.attendance.AttendanceImportHeaderService;

public class AttendanceImportHeaderServiceImpl extends ContextService implements AttendanceImportHeaderService
{
   private LeaveHeaderDao leaveHeaderDao;

   private OTHeaderDao otHeaderDao;

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public OTHeaderDao getOtHeaderDao()
   {
      return otHeaderDao;
   }

   public void setOtHeaderDao( OTHeaderDao otHeaderDao )
   {
      this.otHeaderDao = otHeaderDao;
   }

   @Override
   public PagedListHolder getAttendanceImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final AttendanceImportHeaderDao attendanceImportHeaderDao = ( AttendanceImportHeaderDao ) getDao();
      pagedListHolder.setHolderSize( attendanceImportHeaderDao.countAttendanceImportHeaderVOsByCondition( ( AttendanceImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( attendanceImportHeaderDao.getAttendanceImportHeaderVOsByCondition( ( AttendanceImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( attendanceImportHeaderDao.getAttendanceImportHeaderVOsByCondition( ( AttendanceImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public AttendanceImportHeaderVO getAttendanceImportHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( ( AttendanceImportHeaderDao ) getDao() ).getAttendanceImportHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return ( ( AttendanceImportHeaderDao ) getDao() ).insertAttendanceImportHeader( attendanceImportHeaderVO );
   }

   @Override
   public int updateAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return ( ( AttendanceImportHeaderDao ) getDao() ).updateAttendanceImportHeader( attendanceImportHeaderVO );
   }

   @Override
   public int deleteAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      final AttendanceImportHeaderVO modifyObject = ( ( AttendanceImportHeaderDao ) getDao() ).getAttendanceImportHeaderVOByHeaderId( attendanceImportHeaderVO.getHeaderId() );
      modifyObject.setDeleted( AttendanceImportHeaderVO.FALSE );
      return ( ( AttendanceImportHeaderDao ) getDao() ).updateAttendanceImportHeader( modifyObject );
   }

   @Override
   public int submitAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      try
      {

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

}
