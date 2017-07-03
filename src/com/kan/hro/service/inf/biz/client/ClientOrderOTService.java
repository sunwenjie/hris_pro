package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;

public interface ClientOrderOTService
{
   public abstract PagedListHolder getClientOrderOTVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderOTVO getClientOrderOTVOByClientOrderOTId( final String clientOrderOTId ) throws KANException;

   public abstract int updateClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract int insertClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract int deleteClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException;

   public abstract List< Object > getClientOrderOTVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

}
