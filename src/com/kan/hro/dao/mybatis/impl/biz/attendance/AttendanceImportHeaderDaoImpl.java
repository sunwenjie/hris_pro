package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportHeaderDao;
import com.kan.hro.domain.biz.attendance.AttendanceImportHeaderVO;

public class AttendanceImportHeaderDaoImpl extends Context implements AttendanceImportHeaderDao
{

   @Override
   public int countAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countAttendanceImportHeaderVOsByCondition", attendanceImportHeaderVO );
   }

   @Override
   public List< Object > getAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return selectList( "getAttendanceImportHeaderVOsByCondition", attendanceImportHeaderVO );
   }

   @Override
   public List< Object > getAttendanceImportHeaderVOsByCondition( final AttendanceImportHeaderVO attendanceImportHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAttendanceImportHeaderVOsByCondition", attendanceImportHeaderVO, rowBounds );
   }

   @Override
   public AttendanceImportHeaderVO getAttendanceImportHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( AttendanceImportHeaderVO ) select( "getAttendanceImportHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return insert( "insertAttendanceImportHeader", attendanceImportHeaderVO );
   }

   @Override
   public int updateAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return update( "updateAttendanceImportHeader", attendanceImportHeaderVO );
   }

   @Override
   public int deleteAttendanceImportHeader( final AttendanceImportHeaderVO attendanceImportHeaderVO ) throws KANException
   {
      return delete( "deleteAttendanceImportHeader", attendanceImportHeaderVO );
   }

   @Override
   public List< Object > getAttendanceImportHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getAttendanceImportHeaderVOsByBatchId", batchId );
   }
}
