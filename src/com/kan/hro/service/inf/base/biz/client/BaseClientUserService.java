package com.kan.hro.service.inf.base.biz.client;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientUserVO;

public interface BaseClientUserService
{
   public abstract PagedListHolder getClientUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientUserVO getClientUserVOByUserId( final String userId ) throws KANException;

}
