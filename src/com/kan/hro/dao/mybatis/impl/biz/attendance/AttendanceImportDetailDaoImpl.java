package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.AttendanceImportDetailDao;
import com.kan.hro.domain.biz.attendance.AttendanceImportDetailVO;

public class AttendanceImportDetailDaoImpl extends Context implements AttendanceImportDetailDao
{

   @Override
   public int countAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return ( Integer ) select( "countAttendanceImportDetailVOsByCondition", attendanceImportDetailVO );
   }

   @Override
   public List< Object > getAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return selectList( "getAttendanceImportDetailVOsByCondition", attendanceImportDetailVO );
   }

   @Override
   public List< Object > getAttendanceImportDetailVOsByCondition( final AttendanceImportDetailVO attendanceImportDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAttendanceImportDetailVOsByCondition", attendanceImportDetailVO, rowBounds );
   }

   @Override
   public AttendanceImportDetailVO getAttendanceImportDetailVOByDetailId( final String headerId ) throws KANException
   {
      return ( AttendanceImportDetailVO ) select( "getAttendanceImportDetailVOByDetailId", headerId );
   }

   @Override
   public int insertAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return insert( "insertAttendanceImportDetail", attendanceImportDetailVO );
   }

   @Override
   public int updateAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return update( "updateAttendanceImportDetail", attendanceImportDetailVO );
   }

   @Override
   public int deleteAttendanceImportDetail( final AttendanceImportDetailVO attendanceImportDetailVO ) throws KANException
   {
      return delete( "deleteAttendanceImportDetail", attendanceImportDetailVO );
   }

   @Override
   public List< Object > getAttendanceImportDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getAttendanceImportDetailVOsByHeaderId", headerId );
   }
}
