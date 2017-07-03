package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;

public interface OrderHeaderService
{
   public abstract PagedListHolder getOrderHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OrderHeaderVO getOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract int updateOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract int insertOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract int deleteOrderHeader( final String orderId ) throws KANException;

   public abstract List< Object > getOrderHeaderVOsByBatchId( final String batchId ) throws KANException;
}
