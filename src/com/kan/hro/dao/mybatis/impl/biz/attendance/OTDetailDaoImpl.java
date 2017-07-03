package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTDetailDao;
import com.kan.hro.domain.biz.attendance.OTDetailVO;

public class OTDetailDaoImpl extends Context implements OTDetailDao
{

   @Override
   public int countOTDetailVOsByCondition( final OTDetailVO otDetailVO ) throws KANException
   {
      return ( Integer ) select( "countOTDetailVOsByCondition", otDetailVO );
   }

   @Override
   public List< Object > getOTDetailVOsByCondition( final OTDetailVO otDetailVO ) throws KANException
   {
      return selectList( "getOTDetailVOsByCondition", otDetailVO );
   }

   @Override
   public List< Object > getOTDetailVOsByCondition( final OTDetailVO otDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOTDetailVOsByCondition", otDetailVO, rowBounds );
   }

   @Override
   public OTDetailVO getOTDetailVOByOTDetailId( final String otDetailId ) throws KANException
   {
      return ( OTDetailVO ) select( "getOTDetailVOByOTDetailId", otDetailId );
   }

   @Override
   public int insertOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return insert( "insertOTDetail", otDetailVO );
   }

   @Override
   public int insertOTImportDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return insert( "insertOTImportDetail", otDetailVO );
   }

   @Override
   public int updateOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return update( "updateOTDetail", otDetailVO );
   }

   @Override
   public int deleteOTDetail( final OTDetailVO otDetailVO ) throws KANException
   {
      return delete( "deleteOTDetail", otDetailVO.getOtDetailId() );
   }

   @Override
   public List< Object > getOTDetailVOsByOTHeaderId( final String otHeaderId ) throws KANException
   {
      return selectList( "getOTDetailVOsByOTHeaderId", otHeaderId );
   }

   @Override
   public List< Object > getOTDetailVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getOTDetailVOsByContractId", contractId );
   }

   @Override
   public List< Object > getOTDetailVOsByTimesheetId( final String timesheetId ) throws KANException
   {
      return selectList( "getOTDetailVOsByTimesheetId", timesheetId );
   }

}
