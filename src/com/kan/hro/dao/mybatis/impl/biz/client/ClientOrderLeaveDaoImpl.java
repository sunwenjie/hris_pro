package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;

public class ClientOrderLeaveDaoImpl extends Context implements ClientOrderLeaveDao
{

   @Override
   public int countClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderLeaveVOsByCondition", clientOrderLeaveVO );
   }

   @Override
   public List< Object > getClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return selectList( "getClientOrderLeaveVOsByCondition", clientOrderLeaveVO );
   }

   @Override
   public List< Object > getClientOrderLeaveVOsByCondition( final ClientOrderLeaveVO clientOrderLeaveVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderLeaveVOsByCondition", clientOrderLeaveVO, rowBounds );
   }

   @Override
   public ClientOrderLeaveVO getClientOrderLeaveVOByClientOrderLeaveId( final String clientOrderLeaveId ) throws KANException
   {
      return ( ClientOrderLeaveVO ) select( "getClientOrderLeaveVOByOrderLeaveId", clientOrderLeaveId );
   }

   @Override
   public int updateClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return update( "updateClientOrderLeave", clientOrderLeaveVO );
   }

   @Override
   public int insertClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return insert( "insertClientOrderLeave", clientOrderLeaveVO );
   }

   @Override
   public int deleteClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return delete( "deleteClientOrderLeave", clientOrderLeaveVO );
   }

   @Override
   public List< Object > getClientOrderLeaveVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderLeaveVOsByOrderHeaderId", orderHeaderId );
   }

}
