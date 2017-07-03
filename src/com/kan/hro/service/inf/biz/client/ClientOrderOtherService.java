package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;

public interface ClientOrderOtherService
{
   public abstract PagedListHolder getClientOrderOtherVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderOtherVO getClientOrderOtherVOByClientOrderOtherId( final String clientOrderOtherId ) throws KANException;

   public abstract int updateClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract int insertClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract int deleteClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException;

   public abstract List< Object > getClientOrderOtherVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;

}
