package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;

public interface OrderHeaderDao
{
   public abstract int countOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract List< Object > getOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract List< Object > getOrderHeaderVOsByCondition( final OrderHeaderVO orderHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract OrderHeaderVO getOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract OrderHeaderVO getOrderHeaderVOByOrderHeaderTempId( final String orderHeaderTempId ) throws KANException;

   public abstract List< Object > getOrderHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int insertOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract int updateOrderHeader( final OrderHeaderVO orderHeaderVO ) throws KANException;

   public abstract int deleteOrderHeader( final String orderHeaderId ) throws KANException;

}
