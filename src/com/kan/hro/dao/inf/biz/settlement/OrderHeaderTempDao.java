package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;

public interface OrderHeaderTempDao
{
   public abstract int countOrderHeaderTempVOsByCondition(final OrderHeaderTempVO orderHeaderTempVO) throws KANException ; 
   
   public abstract List< Object > getOrderHeaderTempVOsByCondition( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException;

   public abstract List< Object > getOrderHeaderTempVOsByCondition( final OrderHeaderTempVO orderHeaderTempVO, RowBounds rowBounds ) throws KANException;

   public abstract OrderHeaderTempVO getOrderHeaderTempVOByOrderHeaderId( final String orderHeaderId ) throws KANException;
   
   public abstract int insertOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException;

   public abstract int updateOrderHeaderTemp( final OrderHeaderTempVO orderHeaderTempVO ) throws KANException;

   public abstract int deleteOrderHeaderTemp( final String orderHeaderId ) throws KANException;
   
   public abstract List< Object > getOrderHeaderTempVOsByBatchId( final String batchId ) throws KANException;
   
}
