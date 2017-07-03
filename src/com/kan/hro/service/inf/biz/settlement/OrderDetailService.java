package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;

public interface OrderDetailService
{
   public abstract PagedListHolder getOrderDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OrderDetailVO getOrderDetailVOByOrderDetailId( final String orderDetailId ) throws KANException;

   public abstract int updateOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract int insertOrderDetail( final OrderDetailVO orderDetailVO ) throws KANException;

   public abstract int deleteOrderDetail( final String orderDetailId ) throws KANException;

   public abstract List< Object > getOrderDetailVOsByContractId( final String contractId ) throws KANException;
}
