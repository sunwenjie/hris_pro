package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;

public interface SettlementAdjustmentImportBatchDao
{
   public abstract int countSettlementAdjustmentImportBatchVOsByCondition( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException;
   
   public abstract List< Object > getSettlementAdjustmentImportBatchVOsByCondition( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException;

   public abstract List< Object > getSettlementAdjustmentImportBatchVOsByCondition( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract SettlementAdjustmentImportBatchVO getSettlementAdjustmentImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void updateBathStatus( final SettlementAdjustmentImportBatchVO submitObject );
}
