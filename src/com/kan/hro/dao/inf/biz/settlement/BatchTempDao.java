package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.BatchTempVO;

public interface BatchTempDao
{
   public abstract int countBatchTempVOsByCondition(final BatchTempVO batchTempVO) throws KANException ; 
   
   public abstract List< Object > getBatchTempVOsByCondition( final BatchTempVO batchTempVO ) throws KANException;

   public abstract List< Object > getBatchTempVOsByCondition( final BatchTempVO batchTempVO, RowBounds rowBounds ) throws KANException;

   public abstract BatchTempVO getBatchTempVOByBatchId( final String batchId ) throws KANException;
   
   public abstract List< Object > getBatchTempVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int updateBatchTemp( final BatchTempVO batchTempVO ) throws KANException;

   public abstract int deleteBatchTemp( final String batchId ) throws KANException;
   
}
