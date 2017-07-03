package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientContactDao;
import com.kan.hro.domain.biz.client.ClientContactVO;

public class ClientContactDaoImpl extends Context implements ClientContactDao
{

   @Override
   public int countClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException
   {
      return ( Integer ) select( "countClientContactVOsByCondition", clientContactVO );
   }

   @Override
   public List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO ) throws KANException
   {
      return selectList( "getClientContactVOsByCondition", clientContactVO );
   }

   @Override
   public List< Object > getClientContactVOsByCondition( final ClientContactVO clientContactVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientContactVOsByCondition", clientContactVO, rowBounds );
   }

   @Override
   public ClientContactVO getClientContactVOByClientContactId( final String clientContactId ) throws KANException
   {
      return ( ClientContactVO ) select( "getClientContactVOByClientContactId", clientContactId );
   }

   @Override
   public int updateClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      return update( "updateClientContact", clientContactVO );
   }

   @Override
   public int insertClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      return insert( "insertClientContact", clientContactVO );
   }

   @Override
   public int deleteClientContact( final ClientContactVO clientContactVO ) throws KANException
   {
      return delete( "deleteClientContact", clientContactVO );
   }

   @Override
   public List< Object > getClientContactVOsByClientId( final String clientId ) throws KANException
   {
      return selectList( "getClientContactVOsByClientId", clientId );
   }

}
