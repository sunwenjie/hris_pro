package com.kan.hro.service.inf.base.biz.client;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientContactVO;

public interface BaseClientContactService
{
   public abstract PagedListHolder getClientContactVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientContactVO getClientContactVOByClientContactId( final String clientContactId ) throws KANException;

}
