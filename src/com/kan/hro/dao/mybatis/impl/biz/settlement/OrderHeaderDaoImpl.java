package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderDao;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;

public class OrderHeaderDaoImpl extends Context implements OrderHeaderDao
{

   @Override
   public int countOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countOrderHeaderVOsByCondition", orderHeaderVO );
   }

   @Override
   public List< Object > getOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return selectList( "getOrderHeaderVOsByCondition", orderHeaderVO );
   }

   @Override
   public List< Object > getOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOrderHeaderVOsByCondition", orderHeaderVO, rowBounds );
   }

   @Override
   public OrderHeaderVO getOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( OrderHeaderVO ) select( "getOrderHeaderVOByOrderHeaderId", orderHeaderId );
   }

   @Override
   public int updateOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return update( "updateOrderHeader", orderHeaderVO );
   }

   @Override
   public int insertOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException
   {
      return insert( "insertOrderHeader", orderHeaderVO );
   }

   @Override
   public int deleteOrderHeader( final String orderHeaderId ) throws KANException
   {
      return delete( "deleteOrderHeader", orderHeaderId );
   }

   @Override
   public List< Object > getOrderHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getOrderHeaderVOsByBatchId", batchId );
   }

   @Override
   public OrderHeaderVO getOrderHeaderVOByOrderHeaderTempId( final String orderHeaderTempId ) throws KANException
   {
      return ( OrderHeaderVO ) select( "getOrderHeaderVOByOrderHeaderTempId", orderHeaderTempId );
   }

}
