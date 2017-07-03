package com.kan.hro.dao.mybatis.impl.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportDetailVO;

public class SBAdjustmentImportDetailDaoImpl extends Context implements SBAdjustmentImportDetailDao
{
   @Override
   public int insertSBAdjustmentDetailTempToDetail( final String batchId )
   {
      return insert( "insertSBAdjustmentDetailTempToDetail", batchId );
   }

   @Override
   public void deleteSBAdjustmentImportDetailTempByBatchId( final String batchId )
   {
      delete( "deleteSBAdjustmentImportDetailTempByBatchId", batchId );
   }

   @Override
   public int countSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSBAdjustmentImportDetailVOsByCondition", sbAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO ) throws KANException
   {
      return selectList( "getSBAdjustmentImportDetailVOsByCondition", sbAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getSBAdjustmentImportDetailVOsByCondition( final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSBAdjustmentImportDetailVOsByCondition", sbAdjustmentImportDetailVO, rowBounds );
   }

   @Override
   public void deleteDetailTempRecord( String[] ids )
   {
      update( "deleteSBAdjustmentImportDetailTempRecord", ids );
   }
}
