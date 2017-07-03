package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderOtherDao;
import com.kan.hro.domain.biz.client.ClientOrderOtherVO;

public class ClientOrderOtherDaoImpl extends Context implements ClientOrderOtherDao
{

   @Override
   public int countClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderOtherVOsByCondition", clientOrderOtherVO );
   }

   @Override
   public List< Object > getClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return selectList( "getClientOrderOtherVOsByCondition", clientOrderOtherVO );
   }

   @Override
   public List< Object > getClientOrderOtherVOsByCondition( final ClientOrderOtherVO clientOrderOtherVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderOtherVOsByCondition", clientOrderOtherVO, rowBounds );
   }

   @Override
   public ClientOrderOtherVO getClientOrderOtherVOByClientOrderOtherId( final String clientOrderOtherId ) throws KANException
   {
      return ( ClientOrderOtherVO ) select( "getClientOrderOtherVOByOrderOtherId", clientOrderOtherId );
   }

   @Override
   public int updateClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return update( "updateClientOrderOther", clientOrderOtherVO );
   }

   @Override
   public int insertClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return insert( "insertClientOrderOther", clientOrderOtherVO );
   }

   @Override
   public int deleteClientOrderOther( final ClientOrderOtherVO clientOrderOtherVO ) throws KANException
   {
      return delete( "deleteClientOrderOther", clientOrderOtherVO );
   }

   @Override
   public List< Object > getClientOrderOtherVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderOtherVOsByOrderHeaderId", orderHeaderId );
   }

}
