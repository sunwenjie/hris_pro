package com.kan.hro.service.inf.base.biz.client;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContractVO;

public interface BaseClientContractService
{
   public abstract PagedListHolder getClientContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientContractVO getClientContractVOByContractId( final String contractId ) throws KANException;
}
