package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;

public interface ClientOrderHeaderDao
{
   public abstract int countClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientOrderHeaderVO getClientOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract int updateClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int insertClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int deleteClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderBaseViewsByClientId( final String clientId ) throws KANException;

   public abstract List< Object > getSettlementClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract int markToDeleteClientOrderHeaderInbatches( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException;

   public abstract List< Object >  getClientOrderHeaderVOsByOrderHeaderIds(List<String> selectedIdList) throws KANException;

   public abstract void updateEmployeeSBBaseBySolution( String orderId, String sbSolutionId, String accountId )throws KANException;
}
