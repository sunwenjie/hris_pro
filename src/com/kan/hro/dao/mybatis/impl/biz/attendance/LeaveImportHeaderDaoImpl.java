package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportHeaderDao;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;

public class LeaveImportHeaderDaoImpl extends Context implements LeaveImportHeaderDao
{

   @Override
   public int countLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countLeaveImportHeaderVOsByCondition", leaveImportHeaderVO );
   }

   @Override
   public int countLeaveImportHeaderVOsByBatchId( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countLeaveImportHeaderVOsByBatchId", leaveImportHeaderVO );
   }

   @Override
   public List< Object > getLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException
   {
      return selectList( "getLeaveImportHeaderVOsByCondition", leaveImportHeaderVO );
   }

   @Override
   public List< Object > getLeaveImportHeaderVOsByCondition( final LeaveImportHeaderVO leaveImportHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLeaveImportHeaderVOsByCondition", leaveImportHeaderVO, rowBounds );
   }

   @Override
   public int insertLeaveImportHeaderToLeaveHeader( final String batchId ) throws KANException
   {
      return insert( "insertLeaveImportHeaderToLeaveHeader", batchId );
   }

   @Override
   public int insertLeaveDetailtempToLeaveDetail( final String batchId ) throws KANException
   {
      return ( Integer ) insert( "insertLeaveDetailtempToLeaveDetail", batchId );
   }

   @Override
   public void deleteLeaveImportDetailTempByHeaderIds( final List< String > selectIds )
   {
      delete( "deleteLeaveImportDetailTempByHeaderIds", selectIds );
   }

   @Override
   public void deleteLeaveImportHeaderTempByHeaderIds( final List< String > selectIds )
   {
      delete( "deleteLeaveImportHeaderTempByHeaderIds", selectIds );
   }

   @Override
   public List< Object > getLeaveImportHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getLeaveImportHeaderVOsByBatchId", batchId );
   }

   @Override
   public int updateLeaveImportHeader( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException
   {
      return update( "updateLeaveImportHeader", leaveImportHeaderVO );
   }

}
