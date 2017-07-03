package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;

public interface ClientOrderDetailDao
{
   public abstract int countClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderDetailVO getClientOrderDetailVOByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

   public abstract int updateClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract int insertClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract int deleteClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

}
