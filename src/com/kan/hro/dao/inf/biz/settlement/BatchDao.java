package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.BatchVO;

public interface BatchDao
{
   public abstract int countBatchVOsByCondition( final BatchVO batchVO ) throws KANException;

   public abstract List< Object > getBatchVOsByCondition( final BatchVO batchVO ) throws KANException;

   public abstract List< Object > getBatchVOsByCondition( final BatchVO batchVO, RowBounds rowBounds ) throws KANException;

   public abstract BatchVO getBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract BatchVO getBatchVOByBatchTempId( final String batchTempId ) throws KANException;

   public abstract BatchVO getLastestBatchVOByAccountId( final String acountId ) throws KANException;

   public abstract List< Object > getBatchVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertBatch( final BatchVO batchVO ) throws KANException;

   public abstract int updateBatch( final BatchVO batchVO ) throws KANException;

   public abstract int deleteBatch( final String batchId ) throws KANException;

}
