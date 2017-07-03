package com.kan.hro.dao.inf.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.CommonBatchVO;

public interface CommonBatchDao
{
   public abstract int countSalaryBatchVOsByCondition( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract List< Object > getSalaryBatchVOsByCondition( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract List< Object > getSalaryBatchVOsByCondition( final CommonBatchVO commonBatchVO, final RowBounds rowBounds ) throws KANException;
   
   public abstract CommonBatchVO getCommonBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int updateCommonBatch( final CommonBatchVO commonBatchVO ) throws KANException;

   public abstract int deleteCommonBatch( final String commonBatchId ) throws KANException;

}
