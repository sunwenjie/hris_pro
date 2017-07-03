package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;

public interface OrderDetailTempDao
{
   public abstract int countOrderDetailTempVOsByCondition(final OrderDetailTempVO orderDetailTempVO) throws KANException ; 
   
   public abstract List< Object > getOrderDetailTempVOsByCondition( final OrderDetailTempVO orderDetailTempVO ) throws KANException;

   public abstract List< Object > getOrderDetailTempVOsByCondition( final OrderDetailTempVO orderDetailTempVO, RowBounds rowBounds ) throws KANException;

   public abstract OrderDetailTempVO getOrderDetailTempVOByOrderDetailId( final String orderDetailId ) throws KANException;
   
   public abstract int insertOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException;

   public abstract int updateOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException;

   public abstract int deleteOrderDetailTemp( final String orderDetailTempId ) throws KANException;

   public abstract List< Object > getOrderDetailTempVOsByContractId( final String contractId ) throws KANException;;
   
}
