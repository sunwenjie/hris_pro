package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;

public class ClientOrderHeaderDaoImpl extends Context implements ClientOrderHeaderDao
{

   @Override
   public int countClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderHeaderVOsByCondition", clientOrderHeaderVO );
   }

   @Override
   public List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return selectList( "getClientOrderHeaderVOsByCondition", clientOrderHeaderVO );
   }

   @Override
   public List< Object > getClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderHeaderVOsByCondition", clientOrderHeaderVO, rowBounds );
   }

   @Override
   public ClientOrderHeaderVO getClientOrderHeaderVOByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ClientOrderHeaderVO ) select( "getClientOrderHeaderVOByOrderHeaderId", orderHeaderId );
   }

   @Override
   public List< Object > getClientOrderHeaderVOsByOrderHeaderIds( final List< String > selectedIdList ) throws KANException
   {
      return selectList( "getClientOrderHeaderVOsByOrderHeaderIds", selectedIdList );
   }

   @Override
   public int updateClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return update( "updateClientOrderHeader", clientOrderHeaderVO );
   }

   @Override
   public int insertClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return insert( "insertClientOrderHeader", clientOrderHeaderVO );
   }

   @Override
   public int deleteClientOrderHeader( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return delete( "deleteClientOrderHeader", clientOrderHeaderVO );
   }

   @Override
   public List< Object > getClientOrderHeaderBaseViewsByClientId( final String clientId ) throws KANException
   {
      return selectList( "getClientOrderHeaderBaseViewsByClientId", clientId );
   }

   @Override
   public List< Object > getSettlementClientOrderHeaderVOsByCondition( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return selectList( "getSettlementClientOrderHeaderVOsByCondition", clientOrderHeaderVO );
   }

   @Override
   public int markToDeleteClientOrderHeaderInbatches( final ClientOrderHeaderVO clientOrderHeaderVO ) throws KANException
   {
      return update( "markToDeleteClientOrderHeaderInbatches", clientOrderHeaderVO );
   }

   @Override
   public void updateEmployeeSBBaseBySolution( String orderId, String sbSolutionId, String accountId ) throws KANException
   {
      Map< String, String > args = new HashMap< String, String >();
      args.put( "orderId", orderId );
      args.put( "sbSolutionId", sbSolutionId );
      args.put( "accountId", accountId );
      update( "updateEmployeeSBBaseBySolution", args );
   }
}
