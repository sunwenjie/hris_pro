package com.kan.hro.service.inf.biz.cb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.cb.CBDTO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;

public interface CBHeaderService
{
   public abstract PagedListHolder getCBHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract PagedListHolder getContractVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CBHeaderVO getCBHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract int insertCBHeader( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract int deleteCBHeader( final String cbHeaderId ) throws KANException;

   public abstract List< Object > getCBHeaderVOsByBatchId( final String cbBatchId ) throws KANException;

   public abstract List< Object > getCBHeaderVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< Object > getCBContractVOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

   public abstract List< CBDTO > getCBDTOsByCondition( final CBHeaderVO cbHeaderVO ) throws KANException;

}
