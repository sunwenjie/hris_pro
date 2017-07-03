package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;

public interface ClientOrderDetailRuleService
{
   public abstract PagedListHolder getClientOrderDetailRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderDetailRuleVO getClientOrderDetailRuleVOByClientOrderDetailRuleId( final String clientOrderDetailRuleId ) throws KANException;

   public abstract int updateClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract int insertClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract int deleteClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderDetailRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException;

}
