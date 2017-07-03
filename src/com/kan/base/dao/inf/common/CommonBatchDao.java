package com.kan.base.dao.inf.common;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.util.KANException;

public interface CommonBatchDao
{
   public abstract int countCommonBatchVOsByCondition( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract List< Object > getCommonBatchVOsByCondition( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract List< Object > getCommonBatchVOsByCondition( final CommonBatchVO commonBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract CommonBatchVO getCommonBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int updateCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int deleteCommonBatch( final String commonBatchId ) throws KANException;

}
