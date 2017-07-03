package com.kan.hro.dao.mybatis.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailTempDao;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;

public class OrderDetailTempDaoImpl extends Context implements OrderDetailTempDao
{

   @Override
   public int countOrderDetailTempVOsByCondition( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return ( Integer ) select( "countOrderDetailTempVOsByCondition", orderDetailTempVO );
   }

   @Override
   public List< Object > getOrderDetailTempVOsByCondition( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return selectList( "getOrderDetailTempVOsByCondition", orderDetailTempVO );
   }

   @Override
   public List< Object > getOrderDetailTempVOsByCondition( final OrderDetailTempVO orderDetailTempVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOrderDetailTempVOsByCondition", orderDetailTempVO, rowBounds );
   }

   @Override
   public OrderDetailTempVO getOrderDetailTempVOByOrderDetailId( final String orderDetailId ) throws KANException
   {
      return ( OrderDetailTempVO ) select( "getOrderDetailTempVOByOrderDetailId", orderDetailId );
   }

   @Override
   public int updateOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return update( "updateOrderDetailTemp", orderDetailTempVO );
   }

   @Override
   public int insertOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException
   {
      return insert( "insertOrderDetailTemp", orderDetailTempVO );
   }

   @Override
   public int deleteOrderDetailTemp( final String orderDetailTempId ) throws KANException
   {
      return delete( "deleteOrderDetailTemp", orderDetailTempId );
   }

   @Override
   public List< Object > getOrderDetailTempVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getOrderDetailTempVOsByContractId", contractId );
   }

}
