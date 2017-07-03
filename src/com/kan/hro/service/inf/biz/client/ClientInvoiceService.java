package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;

public interface ClientInvoiceService
{
   public abstract PagedListHolder getClientInvoiceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ClientInvoiceVO getClientInvoiceVOByClientInvoiceId( final String clientInvoiceId ) throws KANException;

   public abstract int updateClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract int insertClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract int deleteClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract List< Object > getClientInvoiceBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientInvoiceVOsByClientId( final String clientId ) throws KANException;

}
