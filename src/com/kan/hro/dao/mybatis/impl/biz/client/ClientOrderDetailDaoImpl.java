package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;

public class ClientOrderDetailDaoImpl extends Context implements ClientOrderDetailDao
{

   @Override
   public int countClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderDetailVOsByCondition", clientOrderDetailVO );
   }

   @Override
   public List< Object > getClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return selectList( "getClientOrderDetailVOsByCondition", clientOrderDetailVO );
   }

   @Override
   public List< Object > getClientOrderDetailVOsByCondition( final ClientOrderDetailVO clientOrderDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderDetailVOsByCondition", clientOrderDetailVO, rowBounds );
   }

   @Override
   public ClientOrderDetailVO getClientOrderDetailVOByClientOrderDetailId( final String clientOrderDetailId ) throws KANException
   {
      return ( ClientOrderDetailVO ) select( "getClientOrderDetailVOByOrderDetailId", clientOrderDetailId );
   }

   @Override
   public int updateClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return update( "updateClientOrderDetail", clientOrderDetailVO );
   }

   @Override
   public int insertClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return insert( "insertClientOrderDetail", clientOrderDetailVO );
   }

   @Override
   public int deleteClientOrderDetail( final ClientOrderDetailVO clientOrderDetailVO ) throws KANException
   {
      return delete( "deleteClientOrderDetail", clientOrderDetailVO );
   }

   @Override
   public List< Object > getClientOrderDetailVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderDetailVOsByClientOrderHeaderId", clientOrderHeaderId );
   }

}
