package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;

public interface OrderHeaderTempService
{
   public abstract PagedListHolder getOrderHeaderTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OrderHeaderTempVO getOrderHeaderTempVOByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract int updateOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException;

   public abstract int insertOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException;

   public abstract int deleteOrderHeaderTemp( final String orderHeaderId ) throws KANException;
   
   public abstract List< Object > getOrderHeaderTempVOsByBatchId( final String batchId )throws KANException;
}
