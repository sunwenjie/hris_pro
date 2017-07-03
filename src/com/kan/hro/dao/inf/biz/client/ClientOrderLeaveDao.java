package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;

public interface ClientOrderLeaveDao
{
   public abstract int countClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract List< Object > getClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract List< Object > getClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderLeaveVO getClientOrderLeaveVOByClientOrderLeaveId( final String clientOrderLeaveId ) throws KANException;

   public abstract int updateClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract int insertClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract int deleteClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract List< Object > getClientOrderLeaveVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;
}
