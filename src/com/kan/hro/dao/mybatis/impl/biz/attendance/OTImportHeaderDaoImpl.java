package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTImportHeaderDao;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;

public class OTImportHeaderDaoImpl extends Context implements OTImportHeaderDao
{

   @Override
   public int countOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countOTImportHeaderVOsByCondition", otImportHeaderVO );
   }

   @Override
   public List< Object > getOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO ) throws KANException
   {
      return selectList( "getOTImportHeaderVOsByCondition", otImportHeaderVO );
   }

   @Override
   public List< Object > getOTImportHeaderVOsByCondition( final OTImportHeaderVO otImportHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOTImportHeaderVOsByCondition", otImportHeaderVO, rowBounds );
   }

   @Override
   public int insertOTImportHeaderToOTHeader( final String batchId ) throws KANException
   {
      return insert( "insertOTImportHeaderToOTHeader", batchId );
   }

   @Override
   public int insertOTDetailtempToOTDetail( final String batchId ) throws KANException
   {
      return insert( "insertOTDetailtempToOTDetail", batchId );
   }

   @Override
   public void deleteDetailTempRecord( final String[] ids )
   {
      delete( "deleteDetailTempRecord", ids );
   }

   @Override
   public void deleteHeaderTempRecord( final String[] ids )
   {
      delete( "deleteHeaderTempRecord", ids );
   }

   @Override
   public List< Object > getOTImportHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getOTImportHeaderVOsByBatchId", batchId );
   }

   @Override
   public int updateOTImportHeader( final OTImportHeaderVO otImportHeaderVO ) throws KANException
   {
      return update( "updateOTImportHeader", otImportHeaderVO );
   }

   @Override
   public int insertOTImportHeaderToOTHeader_shengjoy( final String batchId ) throws KANException
   {
      return insert( "insertOTImportHeaderToOTHeader_shengjoy", batchId );
   }

   @Override
   public int insertOTDetailtempToOTDetail_shengjoy( final String batchId ) throws KANException
   {
      return insert( "insertOTDetailtempToOTDetail_shengjoy", batchId );
   }

}
