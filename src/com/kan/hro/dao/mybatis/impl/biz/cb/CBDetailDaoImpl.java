package com.kan.hro.dao.mybatis.impl.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.domain.biz.cb.CBDetailVO;

public class CBDetailDaoImpl extends Context implements CBDetailDao
{

   @Override
   public int countCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException
   {
      return ( Integer ) select( "countCBDetailVOsByCondition", cbDetailVO );
   }

   @Override
   public List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException
   {
      return selectList( "getCBDetailVOsByCondition", cbDetailVO );
   }

   @Override
   public List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCBDetailVOsByCondition", cbDetailVO, rowBounds );
   }

   @Override
   public CBDetailVO getCBDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( CBDetailVO ) select( "getCBDetailVOByDetailId", detailId );
   }

   @Override
   public int updateCBDetail( final CBDetailVO cbDetailVO ) throws KANException
   {
      return update( "updateCBDetail", cbDetailVO );
   }

   @Override
   public int insertCBDetail( final CBDetailVO cbDetailVO ) throws KANException
   {
      return insert( "insertCBDetail", cbDetailVO );
   }

   @Override
   public int deleteCBDetail( final String cbDetailId ) throws KANException
   {
      return delete( "deleteCBDetail", cbDetailId );
   }

   @Override
   public List< Object > getCBDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getCBDetailVOsByHeaderId", headerId );
   }

   @Override
   public List< Object > getCBDetailVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getCBDetailVOsByBatchId", batchId );
   }

}
