package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;

public interface ClientOrderCBService
{
   public abstract PagedListHolder getClientOrderCBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderCBVO getClientOrderCBVOByClientOrderCBId( final String clientOrderCBId ) throws KANException;

   public abstract int updateClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract int insertClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract int deleteClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< Object > getClientOrderCBVOsByEmployeeContractId( final String employeeContractId ) throws KANException;
}
