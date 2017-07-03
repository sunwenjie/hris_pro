package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;

public interface SBAdjustmentImportHeaderDao
{
   public abstract int countSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentImportHeaderVOsByCondition( final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO, final RowBounds rowBounds )
         throws KANException;

   public abstract void deleteSBAdjustmentImportHeaderTempByBatchId( final String batchId ) throws KANException;

   public abstract int insertSBAdjustmentHeaderTempToHeader( final String batchId ) throws KANException;

   public abstract void deleteHeaderTempRecord( final String[] ids ) throws KANException;

   public abstract SBAdjustmentImportHeaderVO getSBAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException;

   public abstract int getHeaderCountByBatchId( final String batchId );

   public abstract void updateHeaderStatus( final String batchId ) throws KANException;
}
