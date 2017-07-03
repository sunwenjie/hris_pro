package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;

public class SettlementAdjustmentImportHeaderDaoImpl extends Context implements SettlementAdjustmentImportHeaderDao
{
   @Override
   public int insertSettlementAdjustmentHeaderTempToHeader( final String batchId )
   {
      return insert( "insertSettlementAdjustmentHeaderTempToHeader", batchId );
   }

   @Override
   public void deleteSettlementAdjustmentImportHeaderTempByBatchId( final String batchId )
   {
      delete( "deleteSettlementAdjustmentImportHeaderTempByBatchId", batchId );
   }

   @Override
   public int countSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSettlementAdjustmentImportHeaderVOsByCondition", settlementAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportHeaderVOsByCondition", settlementAdjustmentImportHeaderVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportHeaderVOsByCondition", settlementAdjustmentImportHeaderVO, rowBounds );
   }

   @Override
   public void deleteHeaderTempRecord( String[] ids ) throws KANException
   {
      delete( "deleteSettlementAdjustmentImportHeaderTempRecord", ids );
   }
   
   @Override
   public int getHeaderCountByBatchId( String batchId )
   {
      return ( Integer ) select( "getSettlementAdjustmentImportHeaderCountByBatchId", batchId );
   }

   @Override
   public SettlementAdjustmentImportHeaderVO getSettlementAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      Map< String, String > args = new HashMap< String, String >();
      args.put( "headerId", headerId );
      args.put( "accountId", accountId );
      return ( SettlementAdjustmentImportHeaderVO ) select( "getSettlementAdjustmentImportHeaderVOsById", args );
   }

   @Override
   public void updateHeaderStatus( final String batchId ) throws KANException
   {
      update( "updateSettlementAdjustmentImportHeaderStatus", batchId );
   }
}
