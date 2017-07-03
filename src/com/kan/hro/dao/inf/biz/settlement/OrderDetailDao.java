package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;

public interface OrderDetailDao
{
   public abstract int countOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract List< Object > getOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract List< Object > getOrderDetailVOsByCondition( final OrderDetailVO orderDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract OrderDetailVO getOrderDetailVOByOrderDetailId( final String orderDetailId ) throws KANException;

   public abstract List< Object > getOrderDetailVOsByContractId( final String contractId ) throws KANException;

   public abstract int insertOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract int updateOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract int deleteOrderDetail( final String orderDetailId ) throws KANException;

}
