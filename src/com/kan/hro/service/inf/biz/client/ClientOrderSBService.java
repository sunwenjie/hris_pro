package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;

public interface ClientOrderSBService
{
   public abstract PagedListHolder getClientOrderSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderSBVO getClientOrderSBVOByClientOrderSBId( final String clientOrderSBId ) throws KANException;

   public abstract int updateClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract int insertClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract int deleteClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

   public abstract List< Object > getClientOrderSBVOsByEmployeeContractId( final String employeeContractId ) throws KANException;
}
