package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderTempDao;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;

public class OrderHeaderTempDaoImpl extends Context implements OrderHeaderTempDao
{

   @Override
   public int countOrderHeaderTempVOsByCondition( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return ( Integer ) select( "countOrderHeaderTempVOsByCondition", orderHeaderTempVO );
   }

   @Override
   public List< Object > getOrderHeaderTempVOsByCondition( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return selectList( "getOrderHeaderTempVOsByCondition", orderHeaderTempVO );
   }

   @Override
   public List< Object > getOrderHeaderTempVOsByCondition( final OrderHeaderTempVO orderHeaderTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOrderHeaderTempVOsByCondition", orderHeaderTempVO, rowBounds );
   }

   @Override
   public OrderHeaderTempVO getOrderHeaderTempVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( OrderHeaderTempVO ) select( "getOrderHeaderTempVOByOrderHeaderId", orderHeaderId );
   }

   @Override
   public int updateOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return update( "updateOrderHeaderTemp", orderHeaderTempVO );
   }

   @Override
   public int insertOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException
   {
      return insert( "insertOrderHeaderTemp", orderHeaderTempVO );
   }

   @Override
   public int deleteOrderHeaderTemp( final String orderHeaderId ) throws KANException
   {
      return delete( "deleteOrderHeaderTemp", orderHeaderId );
   }

   @Override
   public List< Object > getOrderHeaderTempVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getOrderHeaderTempVOsByBatchId", batchId );
   }

}
