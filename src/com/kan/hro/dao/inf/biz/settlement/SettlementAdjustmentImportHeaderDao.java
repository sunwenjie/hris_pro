package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;

public interface SettlementAdjustmentImportHeaderDao
{
   public abstract int countSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getSettlementAdjustmentImportHeaderVOsByCondition( final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO, final RowBounds rowBounds )
         throws KANException;

   public abstract void deleteSettlementAdjustmentImportHeaderTempByBatchId( final String batchId ) throws KANException;

   public abstract int insertSettlementAdjustmentHeaderTempToHeader( final String batchId ) throws KANException;

   public abstract void deleteHeaderTempRecord( final String[] ids ) throws KANException;

   public abstract SettlementAdjustmentImportHeaderVO getSettlementAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException;

   public abstract int getHeaderCountByBatchId( final String batchId );

   public abstract void updateHeaderStatus( final String batchId ) throws KANException;
}
