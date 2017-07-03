package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.BatchDao;
import com.kan.hro.domain.biz.settlement.BatchVO;

public class BatchDaoImpl extends Context implements BatchDao
{

   @Override
   public int countBatchVOsByCondition( final BatchVO batchVO ) throws KANException
   {
      return ( Integer ) select( "countBatchVOsByCondition", batchVO );
   }

   @Override
   public List< Object > getBatchVOsByCondition( final BatchVO batchVO ) throws KANException
   {
      return selectList( "getBatchVOsByCondition", batchVO );
   }

   @Override
   public List< Object > getBatchVOsByCondition( final BatchVO batchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBatchVOsByCondition", batchVO, rowBounds );
   }

   @Override
   public BatchVO getBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( BatchVO ) select( "getBatchVOByBatchId", batchId );
   }

   @Override
   public int updateBatch( final BatchVO batchVO ) throws KANException
   {
      return update( "updateBatch", batchVO );
   }

   @Override
   public int insertBatch( final BatchVO batchVO ) throws KANException
   {
      return insert( "insertBatch", batchVO );
   }

   @Override
   public int deleteBatch( final String batchId ) throws KANException
   {
      return delete( "deleteBatch", batchId );
   }

   @Override
   public List< Object > getBatchVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBatchVOsByAccountId", accountId );
   }

   @Override
   public BatchVO getLastestBatchVOByAccountId( final String acountId ) throws KANException
   {
      return ( BatchVO ) select( "getLastestBatchVOByAccountId", acountId );
   }

   @Override
   public BatchVO getBatchVOByBatchTempId( final String batchTempId ) throws KANException
   {
      return ( BatchVO ) select( "getBatchVOByBatchTempId", batchTempId );
   }

}
