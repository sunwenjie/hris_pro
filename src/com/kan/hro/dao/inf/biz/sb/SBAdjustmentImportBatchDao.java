package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;

public interface SBAdjustmentImportBatchDao
{
   public abstract int countSBAdjustmentImportBatchVOsByCondition( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException;
   
   public abstract List< Object > getSBAdjustmentImportBatchVOsByCondition( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentImportBatchVOsByCondition( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBAdjustmentImportBatchVO getSBAdjustmentImportBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void updateBathStatus( final SBAdjustmentImportBatchVO submitObject );

   public abstract void  updateSBAdjustmentImportHeaderBybatchId( String batchId );
}
