package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;

public interface ClientContractPropertyService
{
   public abstract PagedListHolder getClientContractPropertyVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientContractPropertyVO getClientContractPropertyVOByClientContractPropertyId( final String clientContractPropertyId ) throws KANException;

   public abstract int updateClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract int insertClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract int deleteClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException;

   public abstract List< Object > getClientContractPropertyVOsByContractId( final String ContractId ) throws KANException;
}
