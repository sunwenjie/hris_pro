package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportDetailVO;

public interface SBAdjustmentImportDetailDao
{
   public abstract int countSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO ) throws KANException;
   
   public abstract List< Object > getSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO, final RowBounds rowBounds ) throws KANException;
   
   public abstract void deleteSBAdjustmentImportDetailTempByBatchId( final String batchId );
   
   public abstract int insertSBAdjustmentDetailTempToDetail( final String batchId );

   public abstract void deleteDetailTempRecord( final String[] ids );
}
