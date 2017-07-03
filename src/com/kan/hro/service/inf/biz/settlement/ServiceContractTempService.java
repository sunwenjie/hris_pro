package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;

public interface ServiceContractTempService
{
   public abstract PagedListHolder getServiceContractTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ServiceContractTempVO getServiceContractTempVOByContractId( final String contractId ) throws KANException;

   public abstract int updateServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException;

   public abstract int insertServiceContractTemp( final ServiceContractTempVO serviceContractTempVO ) throws KANException;

   public abstract int deleteServiceContractTemp( final String contractId ) throws KANException;
   
   public abstract List< Object > getServiceContractTempVOsByOrderHeaderId( final String orderHeaderId )throws KANException;
}
