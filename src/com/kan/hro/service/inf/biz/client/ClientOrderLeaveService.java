package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;

public interface ClientOrderLeaveService
{
   public abstract PagedListHolder getClientOrderLeaveVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderLeaveVO getClientOrderLeaveVOByClientOrderLeaveId( final String clientOrderLeaveId ) throws KANException;

   public abstract int updateClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract int insertClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract int deleteClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException;

   public abstract List< Object > getClientOrderLeaveVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;
}
