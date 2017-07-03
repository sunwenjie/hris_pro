package com.kan.hro.dao.inf.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBBatchVO;

public interface CBBatchDao
{
   public abstract int countCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract List< Object > getCBBatchVOsByCondition( final CBBatchVO cbBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract CBBatchVO getCBBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertCBBatch( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract int updateCBBatch( final CBBatchVO cbBatchVO ) throws KANException;

   public abstract int deleteCBBatch( final String cbBatchId ) throws KANException;
}
