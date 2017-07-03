package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;

public interface OrderDetailTempService
{
   public abstract PagedListHolder getOrderDetailTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OrderDetailTempVO getOrderDetailTempVOByOrderDetailId( final String orderDetailId ) throws KANException;

   public abstract int updateOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException;

   public abstract int insertOrderDetailTemp( final OrderDetailTempVO orderDetailTempVO ) throws KANException;

   public abstract int deleteOrderDetailTemp( final String orderDetailId ) throws KANException;
   
   public abstract List< Object > getOrderDetailTempVOsByContractId( final String contractId )throws KANException;
}
