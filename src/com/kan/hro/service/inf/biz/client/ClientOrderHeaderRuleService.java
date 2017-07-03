package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;

public interface ClientOrderHeaderRuleService
{
   public abstract PagedListHolder getClientOrderHeaderRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientOrderHeaderRuleVO getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( final String clientOrderHeaderRuleId ) throws KANException;

   public abstract int updateClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract int insertClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract int deleteClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException;

   public abstract List< Object > getClientOrderHeaderRuleVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException;

}
