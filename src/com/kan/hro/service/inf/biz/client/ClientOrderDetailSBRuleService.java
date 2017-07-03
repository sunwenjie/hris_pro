package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;

public interface ClientOrderDetailSBRuleService
{
   public abstract PagedListHolder getClientOrderDetailSBRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderDetailSBRuleVO getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( final String clientOrderDetailSBRuleId ) throws KANException;

   public abstract int updateClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract int insertClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract int deleteClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailSBRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

}
