package com.kan.hro.service.inf.biz.cb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBDetailVO;

public interface CBDetailService
{
   public abstract PagedListHolder getCBDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CBDetailVO getCBDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateCBDetail( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract int insertCBDetail( final CBDetailVO cbDetailVO ) throws KANException;

   public abstract int deleteCBDetail( final String cbDetailId ) throws KANException;

   public abstract List< Object > getCBDetailVOsByCondition( final CBDetailVO cbDetailVO ) throws KANException;
}
