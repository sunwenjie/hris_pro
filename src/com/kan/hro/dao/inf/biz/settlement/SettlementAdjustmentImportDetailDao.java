package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportDetailVO;

public interface SettlementAdjustmentImportDetailDao
{
   public abstract int countSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO ) throws KANException;
   
   public abstract List< Object > getSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO ) throws KANException;

   public abstract List< Object > getSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO, final RowBounds rowBounds ) throws KANException;
   
   public abstract void deleteSettlementAdjustmentImportDetailTempByBatchId( final String batchId );
   
   public abstract int insertSettlementAdjustmentDetailTempToDetail( final String batchId );

   public abstract void deleteDetailTempRecord( final String[] ids );
}
