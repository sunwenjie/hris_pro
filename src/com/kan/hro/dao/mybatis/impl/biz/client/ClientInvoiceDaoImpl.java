package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientInvoiceDao;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;

public class ClientInvoiceDaoImpl extends Context implements ClientInvoiceDao
{

   @Override
   public int countClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return ( Integer ) select( "countClientInvoiceVOsByCondition", clientInvoiceVO );
   }

   @Override
   public List< Object > getClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return selectList( "getClientInvoiceVOsByCondition", clientInvoiceVO );
   }

   @Override
   public List< Object > getClientInvoiceVOsByCondition( final ClientInvoiceVO clientInvoiceVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientInvoiceVOsByCondition", clientInvoiceVO, rowBounds );
   }

   @Override
   public ClientInvoiceVO getClientInvoiceVOByClientInvoiceId( final String clientInvoiceId ) throws KANException
   {
      return ( ClientInvoiceVO ) select( "getClientInvoiceVOByClientInvoiceId", clientInvoiceId );
   }

   @Override
   public int updateClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return update( "updateClientInvoice", clientInvoiceVO );
   }

   @Override
   public int insertClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return insert( "insertClientInvoice", clientInvoiceVO );
   }

   @Override
   public int deleteClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return delete( "deleteClientInvoice", clientInvoiceVO );
   }

   @Override
   public List< Object > getClientInvoiceBaseViews( final String accountId ) throws KANException
   {
      return selectList( "getClientInvoiceBaseViews", accountId );
   }

   @Override
   public List< Object > getClientInvoiceVOsByClientId( final String clientId ) throws KANException
   {
      return selectList( "getClientInvoiceVOsByClientId", clientId );
   }

}
