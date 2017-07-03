package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.OTDetailVO;

public interface OTDetailService
{
   public abstract PagedListHolder getOTDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OTDetailVO getOTDetailVOByOTDetailId( final String otDetailId ) throws KANException;

   public abstract int insertOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract int updateOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract int deleteOTDetail( final OTDetailVO otDetailVO ) throws KANException;

   public abstract List< Object > getOTDetailVOsByOTHeaderId( final String otHeaderId ) throws KANException;

   public abstract List< Object > getOTDetailVOsByContractId( final String contractId ) throws KANException;

}
