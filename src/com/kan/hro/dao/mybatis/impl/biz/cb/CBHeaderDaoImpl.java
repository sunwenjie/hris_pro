package com.kan.hro.dao.mybatis.impl.biz.cb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.domain.biz.cb.CBHeaderVO;

public class CBHeaderDaoImpl extends Context implements CBHeaderDao
{

   @Override
   public int countCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countCBHeaderVOsByCondition", cbHeaderVO );
   }
   
   @Override
   public int countContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countCBContractVOsByCondition", cbHeaderVO );
   }
   
   @Override
   public List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return selectList( "getCBHeaderVOsByCondition", cbHeaderVO );
   }

   @Override
   public List< Object > getContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return selectList( "getCBContractVOsByCondition", cbHeaderVO );
   }

   @Override
   public List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCBHeaderVOsByCondition", cbHeaderVO, rowBounds );
   }
   
   @Override
   public List< Object > getContractVOsByCondition( final CBHeaderVO cbHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCBContractVOsByCondition", cbHeaderVO, rowBounds );
   }

   @Override
   public CBHeaderVO getCBHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( CBHeaderVO ) select( "getCBHeaderVOByHeaderId", headerId );
   }

   @Override
   public int updateCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return update( "updateCBHeader", cbHeaderVO );
   }

   @Override
   public int insertCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return insert( "insertCBHeader", cbHeaderVO );
   }

   @Override
   public int deleteCBHeader( final String cbHeaderId ) throws KANException
   {
      return delete( "deleteCBHeader", cbHeaderId );
   }

   @Override
   public List< Object > getCBHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getCBHeaderVOsByBatchId", batchId );
   }

   @Override
   public List< Object > getCBContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException
   {
      return selectList( "getCBContractVOsByCondition", cbHeaderVO );
   }
   
}
