package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailDTO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;

public interface ClientOrderDetailService
{
   public abstract PagedListHolder getClientOrderDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderDetailVO getClientOrderDetailVOByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

   public abstract int updateClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract int insertClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract int deleteClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< ClientOrderDetailDTO > getClientOrderDetailDTOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;
}
