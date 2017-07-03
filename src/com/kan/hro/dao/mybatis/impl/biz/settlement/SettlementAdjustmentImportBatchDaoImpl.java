package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportBatchDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;

public class SettlementAdjustmentImportBatchDaoImpl extends Context implements SettlementAdjustmentImportBatchDao
{

   @Override
   public int countSettlementAdjustmentImportBatchVOsByCondition( SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException
   {
      return ( Integer ) select( "countSettlementAdjustmentImportBatchVOsByCondition", settlementAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportBatchVOsByCondition( SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportBatchVOsByCondition", settlementAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportBatchVOsByCondition( SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportBatchVOsByCondition", settlementAdjustmentImportBatchVO, rowBounds );
   }

   @Override
   public SettlementAdjustmentImportBatchVO getSettlementAdjustmentImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( SettlementAdjustmentImportBatchVO ) select( "getSettlementAdjustmentImportBatchVOByBatchId", batchId );
   }

   @Override
   public void updateBathStatus( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO )
   {
      update( "updateSettlementAdjustmentImportBatchStatus", settlementAdjustmentImportBatchVO );
   }
}
