package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportBatchDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;

public class SBAdjustmentImportBatchDaoImpl extends Context implements SBAdjustmentImportBatchDao
{

   @Override
   public int countSBAdjustmentImportBatchVOsByCondition( SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      return ( Integer ) select( "countSBAdjustmentImportBatchVOsByCondition", sbAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportBatchVOsByCondition( SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      return selectList( "getSBAdjustmentImportBatchVOsByCondition", sbAdjustmentImportBatchVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportBatchVOsByCondition( SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBAdjustmentImportBatchVOsByCondition", sbAdjustmentImportBatchVO, rowBounds );
   }

   @Override
   public SBAdjustmentImportBatchVO getSBAdjustmentImportBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( SBAdjustmentImportBatchVO ) select( "getSBAdjustmentImportBatchVOByBatchId", batchId );
   }

   @Override
   public void updateBathStatus( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO )
   {
      update( "updateSBAdjustmentImportBatchStatus", sbAdjustmentImportBatchVO );
   }
   
   @Override
   public void updateSBAdjustmentImportHeaderBybatchId( String batchId ) 
   {
      update( "updateSBAdjustmentImportHeaderBybatchId", batchId );
   }
}
