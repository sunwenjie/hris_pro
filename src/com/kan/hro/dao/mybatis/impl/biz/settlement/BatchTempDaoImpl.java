package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.BatchTempDao;
import com.kan.hro.domain.biz.settlement.BatchTempVO;

public class BatchTempDaoImpl extends Context implements BatchTempDao
{

   @Override
   public int countBatchTempVOsByCondition( final BatchTempVO batchTempVO ) throws KANException
   {
      return ( Integer ) select( "countBatchTempVOsByCondition", batchTempVO );
   }

   @Override
   public List< Object > getBatchTempVOsByCondition( final BatchTempVO batchTempVO ) throws KANException
   {
      return selectList( "getBatchTempVOsByCondition", batchTempVO );
   }

   @Override
   public List< Object > getBatchTempVOsByCondition( final BatchTempVO batchTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBatchTempVOsByCondition", batchTempVO, rowBounds );
   }

   @Override
   public BatchTempVO getBatchTempVOByBatchId( final String batchId ) throws KANException
   {
      return ( BatchTempVO ) select( "getBatchTempVOByBatchId", batchId );
   }

   @Override
   public int updateBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      return update( "updateBatchTemp", batchTempVO );
   }

   @Override
   public int insertBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      return insert( "insertBatchTemp", batchTempVO );
   }

   @Override
   public int deleteBatchTemp( final String batchId ) throws KANException
   {
      return delete( "deleteBatchTemp", batchId );
   }

   @Override
   public List< Object > getBatchTempVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBatchTempVOsByAccountId", accountId );
   }

}
