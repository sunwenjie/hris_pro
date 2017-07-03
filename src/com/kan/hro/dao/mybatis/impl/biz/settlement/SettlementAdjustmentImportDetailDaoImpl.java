package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportDetailDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportDetailVO;

public class SettlementAdjustmentImportDetailDaoImpl extends Context implements SettlementAdjustmentImportDetailDao
{
   @Override
   public int insertSettlementAdjustmentDetailTempToDetail( final String batchId )
   {
      return insert("insertSettlementAdjustmentDetailTempToDetail", batchId);
   }
   
   @Override
   public void deleteSettlementAdjustmentImportDetailTempByBatchId( final String batchId )
   {
      delete("deleteSettlementAdjustmentImportDetailTempByBatchId", batchId);
   }

   @Override
   public int countSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSettlementAdjustmentImportDetailVOsByCondition", settlementAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportDetailVOsByCondition", settlementAdjustmentImportDetailVO );
   }

   @Override
   public List< Object > getSettlementAdjustmentImportDetailVOsByCondition( final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSettlementAdjustmentImportDetailVOsByCondition", settlementAdjustmentImportDetailVO ,rowBounds);
   }

   @Override
   public void deleteDetailTempRecord( String[] ids )
   {
      delete("deleteSettlementAdjustmentImportDetailTempRecord", ids);
   }
}
