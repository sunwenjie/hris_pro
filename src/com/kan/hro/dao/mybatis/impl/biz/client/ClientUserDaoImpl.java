package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientUserDao;
import com.kan.hro.domain.biz.client.ClientUserVO;

public class ClientUserDaoImpl extends Context implements ClientUserDao
{

   @Override
   public int countClientUserVOsByCondition( final ClientUserVO clientUserVO ) throws KANException
   {
      return ( Integer ) select( "countClientUserVOsByCondition", clientUserVO );
   }

   @Override
   public List< Object > getClientUserVOsByCondition( final ClientUserVO clientUserVO ) throws KANException
   {
      return selectList( "getClientUserVOsByCondition", clientUserVO );
   }

   @Override
   public List< Object > getClientUserVOsByCondition( final ClientUserVO clientUserVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientUserVOsByCondition", clientUserVO, rowBounds );
   }

   @Override
   public ClientUserVO getClientUserVOByUserId( final String userId ) throws KANException
   {
      return ( ClientUserVO ) select( "getClientUserVOByClientContactId", userId );
   }

   @Override
   public int insertClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return insert( "insertClientUser", clientUserVO );
   }

   @Override
   public int updateClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return update( "updateClientUser", clientUserVO );
   }

   @Override
   public int deleteClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return delete( "deleteClientUser", clientUserVO );
   }

   @Override
   public ClientUserVO getClientUserByName( ClientUserVO clientUserVO )
   {
      return ( ClientUserVO ) select( "getClientUserVOByUsername", clientUserVO );
   }
}
