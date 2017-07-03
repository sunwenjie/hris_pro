package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.OTDTO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;

public interface OTHeaderService
{
   public abstract PagedListHolder getOTHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract OTHeaderVO getOTHeaderVOByOTHeaderId( final String otHeaderId ) throws KANException;

   public abstract int insertOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int updateOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int submitOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int deleteOTHeader( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int deleteOTHeader_cleanTS( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract List< OTDTO > getOTDTOsByAccountId( final String accountId ) throws KANException;

   public abstract List< OTHeaderVO > getOTHeaderVOsByContracrId( final String contractId ) throws KANException;

   public abstract List< Object > getOTHeaderVOsByCondition( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int count_OTUnread( final OTHeaderVO otHeaderVO ) throws KANException;

   public abstract int read_OT( final OTHeaderVO otHeaderVO ) throws KANException;
   
   public abstract List< Object > exportOTDetailByCondition( final OTHeaderVO otHeaderVO ) throws KANException;
   
   public abstract int updateOTHeader_onlyUP( final OTHeaderVO otHeaderVO ) throws KANException;
   
   public abstract OTHeaderVO getOTHeaderVOByOTImportHeaderId( final String otImportHeaderId ) throws KANException;

}
