package com.kan.hro.service.inf.base.biz.client;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientVO;

public interface BaseClientService
{
   public abstract PagedListHolder getClientVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientVO getClientVOByClientId( final String clientId ) throws KANException;
   
   public abstract ClientVO getClientVOByCorpId( final String corpId ) throws KANException;
   
   public abstract ClientVO getClientVOByClientIdForPdf( final String clientId ) throws KANException;
   
}
