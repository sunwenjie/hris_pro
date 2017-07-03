package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;

public interface ClientInvoiceDao
{
   public abstract int countClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract List< Object > getClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract List< Object > getClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientInvoiceVO getClientInvoiceVOByClientInvoiceId( final String clientInvoiceId ) throws KANException;

   public abstract int updateClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract int insertClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract int deleteClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException;

   public abstract List< Object > getClientInvoiceBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientInvoiceVOsByClientId( final String clientId) throws KANException;
}
