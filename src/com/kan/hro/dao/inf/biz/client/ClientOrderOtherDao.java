package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;

public interface ClientOrderOtherDao
{
   public abstract int countClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract List< Object > getClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract List< Object > getClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderOtherVO getClientOrderOtherVOByClientOrderOtherId( final String clientOrderOtherId ) throws KANException;

   public abstract int updateClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract int insertClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract int deleteClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract List< Object > getClientOrderOtherVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;

}
