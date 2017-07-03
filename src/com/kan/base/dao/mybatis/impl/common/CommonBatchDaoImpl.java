package com.kan.base.dao.mybatis.impl.common;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.util.KANException;

public class CommonBatchDaoImpl extends Context implements CommonBatchDao
{

   @Override
   public int countCommonBatchVOsByCondition( CommonBatchVO commonBatchVO ) throws KANException
   {
      return ( Integer ) select( "countCommonBatchVOsByCondition", commonBatchVO );
   }

   @Override
   public List< Object > getCommonBatchVOsByCondition( CommonBatchVO commonBatchVO ) throws KANException
   {
      return selectList( "getCommonBatchVOsByCondition", commonBatchVO );
   }

   @Override
   public List< Object > getCommonBatchVOsByCondition( CommonBatchVO commonBatchVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCommonBatchVOsByCondition",commonBatchVO, rowBounds );
   }

   @Override
   public CommonBatchVO getCommonBatchVOByBatchId( String batchId ) throws KANException
   {
      return ( CommonBatchVO ) select( "com.kan.base.domain.common.CommonBatchVO.getCommonBatchVOByBatchId", batchId );
   }

   @Override
   public int insertCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return insert( "com.kan.base.domain.common.CommonBatchVO.insertCommonBatch", commonBatchVO );
   }

   @Override
   public int updateCommonBatch( CommonBatchVO commonBatchVO ) throws KANException
   {
      return update( "com.kan.base.domain.common.CommonBatchVO.updateCommonBatch", commonBatchVO );
   }

   @Override
   public int deleteCommonBatch( String commonBatchId ) throws KANException
   {
      return delete( "com.kan.base.domain.common.CommonBatchVO.deleteCommonBatch", commonBatchId );
   }

}
