package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;

public interface ClientOrderOTDao
{
   public abstract int countClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract List< Object > getClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract List< Object > getClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderOTVO getClientOrderOTVOByClientOrderOTId( final String clientOrderOTId ) throws KANException;

   public abstract int updateClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract int insertClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract int deleteClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract List< Object > getClientOrderOTVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

}
