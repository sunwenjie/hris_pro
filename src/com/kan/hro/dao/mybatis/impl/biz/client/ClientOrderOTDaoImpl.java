package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderOTDao;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;

public class ClientOrderOTDaoImpl extends Context implements ClientOrderOTDao
{

   @Override
   public int countClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderOTVOsByCondition", clientOrderOTVO );
   }

   @Override
   public List< Object > getClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return selectList( "getClientOrderOTVOsByCondition", clientOrderOTVO );
   }

   @Override
   public List< Object > getClientOrderOTVOsByCondition( final ClientOrderOTVO clientOrderOTVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderOTVOsByCondition", clientOrderOTVO, rowBounds );
   }

   @Override
   public ClientOrderOTVO getClientOrderOTVOByClientOrderOTId( final String clientOrderOTId ) throws KANException
   {
      return ( ClientOrderOTVO ) select( "getClientOrderOTVOByOrderOTId", clientOrderOTId );
   }

   @Override
   public int updateClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return update( "updateClientOrderOT", clientOrderOTVO );
   }

   @Override
   public int insertClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return insert( "insertClientOrderOT", clientOrderOTVO );
   }

   @Override
   public int deleteClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return delete( "deleteClientOrderOT", clientOrderOTVO );
   }

   @Override
   public List< Object > getClientOrderOTVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderOTVOsByClientOrderHeaderId", clientOrderHeaderId );
   }

}
