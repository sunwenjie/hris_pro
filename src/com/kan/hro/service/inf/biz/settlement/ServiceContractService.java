package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;

public interface ServiceContractService
{
   public abstract PagedListHolder getServiceContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ServiceContractVO getServiceContractVOByContractId( final String contractId ) throws KANException;

   public abstract int updateServiceContract( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int insertServiceContract( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract int deleteServiceContract( final String contractId ) throws KANException;

   public abstract List< Object > getServiceContractVOsByOrderHeaderId( final String orderHeaderId ) throws KANException;

   public abstract List< EMPPaymentDTO > getEMPPaymentDTOsByCondition( final ServiceContractVO serviceContractVO ) throws KANException;

   public abstract EMPPaymentDTO getEMPPaymentDTOByCondition( final ServiceContractVO serviceContractVO, final String adjustmentStatus ) throws KANException;
}
