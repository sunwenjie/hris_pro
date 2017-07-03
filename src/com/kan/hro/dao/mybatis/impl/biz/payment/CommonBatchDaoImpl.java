package com.kan.hro.dao.mybatis.impl.biz.payment;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.payment.CommonBatchDao;
import com.kan.hro.domain.biz.payment.CommonBatchVO;

public class CommonBatchDaoImpl extends Context implements CommonBatchDao
{

   @Override
   public int countSalaryBatchVOsByCondition( CommonBatchVO commonBatchVO ) throws KANException
   {
      return ( Integer ) select( "countSalaryBatchVOsByCondition", commonBatchVO );
   }

   @Override
   public List< Object > getSalaryBatchVOsByCondition( CommonBatchVO commonBatchVO ) throws KANException
   {
      return selectList( "getSalaryBatchVOsByCondition", commonBatchVO );
   }

   @Override
   public List< Object > getSalaryBatchVOsByCondition( CommonBatchVO commonBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSalaryBatchVOsByCondition", commonBatchVO, rowBounds );
   }

   @Override
   public CommonBatchVO getCommonBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( CommonBatchVO ) select( "com.kan.hro.domain.biz.payment.CommonBatchVO.getCommonBatchVOByBatchId", batchId );
   }

   @Override
   public int insertCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return insert( "com.kan.hro.domain.biz.payment.CommonBatchVO.insertCommonBatch", commonBatchVO );
   }

   @Override
   public int updateCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return update( "com.kan.hro.domain.biz.payment.CommonBatchVO.updateCommonBatch", commonBatchVO );
   }

   @Override
   public int deleteCommonBatch( String commonBatchId ) throws KANException
   {
      return delete( "com.kan.hro.domain.biz.payment.CommonBatchVO.deleteCommonBatch", commonBatchId );
   }

}
