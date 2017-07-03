package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;

public class SBAdjustmentImportHeaderDaoImpl extends Context implements SBAdjustmentImportHeaderDao
{
   @Override
   public int insertSBAdjustmentHeaderTempToHeader( final String batchId )
   {
      return insert( "insertSBAdjustmentHeaderTempToHeader", batchId );
   }

   @Override
   public void deleteSBAdjustmentImportHeaderTempByBatchId( final String batchId )
   {
      delete( "deleteSBAdjustmentImportHeaderTempByBatchId", batchId );
   }

   @Override
   public int countSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSBAdjustmentImportHeaderVOsByCondition", sbAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO ) throws KANException
   {
      return selectList( "getSBAdjustmentImportHeaderVOsByCondition", sbAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBAdjustmentImportHeaderVOsByCondition", sbAdjustmentImportHeaderVO, rowBounds );
   }

   @Override
   public void deleteHeaderTempRecord( final String[] ids ) throws KANException
   {
      delete( "deleteSBAdjustmentImportHeaderTempRecord", ids );
   }

   @Override
   public int getHeaderCountByBatchId( String batchId )
   {
      return ( Integer ) select( "getSBAdjustmentImportHeaderCountByBatchId", batchId );
   }

   @Override
   public SBAdjustmentImportHeaderVO getSBAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      Map< String, String > args = new HashMap< String, String >();
      args.put( "headerId", headerId );
      args.put( "accountId", accountId );
      return ( SBAdjustmentImportHeaderVO ) select( "getSBAdjustmentImportHeaderVOsById", args );
   }

   @Override
   public void updateHeaderStatus( final String batchId ) throws KANException
   {
      delete( "updateSBAdjustmentImportHeaderStatus", batchId );
   }
}
