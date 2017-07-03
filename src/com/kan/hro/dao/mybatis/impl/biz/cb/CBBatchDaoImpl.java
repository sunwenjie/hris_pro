package com.kan.hro.dao.mybatis.impl.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.domain.biz.cb.CBBatchVO;

public class CBBatchDaoImpl extends Context implements CBBatchDao
{

   @Override
   public int countCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException
   {
      return ( Integer ) select( "countCBBatchVOsByCondition", cbBatchVO );
   }

   @Override
   public List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException
   {
      return selectList( "getCBBatchVOsByCondition", cbBatchVO );
   }

   @Override
   public List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCBBatchVOsByCondition", cbBatchVO, rowBounds );
   }

   @Override
   public CBBatchVO getCBBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( CBBatchVO ) select( "getCBBatchVOByBatchId", batchId );
   }

   @Override
   public int updateCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return update( "updateCBBatch", cbBatchVO );
   }

   @Override
   public int insertCBBatch( final CBBatchVO cbBatchVO ) throws KANException
   {
      return insert( "insertCBBatch", cbBatchVO );
   }

   @Override
   public int deleteCBBatch( final String cbBatchId ) throws KANException
   {
      return delete( "deleteCBBatch", cbBatchId );
   }

}
