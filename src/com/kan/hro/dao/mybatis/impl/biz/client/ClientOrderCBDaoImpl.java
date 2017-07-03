package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderCBDao;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;

public class ClientOrderCBDaoImpl extends Context implements ClientOrderCBDao
{

   @Override
   public int countClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderCBVOsByCondition", clientOrderCBVO );
   }

   @Override
   public List< Object > getClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return selectList( "getClientOrderCBVOsByCondition", clientOrderCBVO );
   }

   @Override
   public List< Object > getClientOrderCBVOsByCondition( final ClientOrderCBVO clientOrderCBVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderCBVOsByCondition", clientOrderCBVO, rowBounds );
   }

   @Override
   public ClientOrderCBVO getClientOrderCBVOByClientOrderCBId( final String clientOrderCBId ) throws KANException
   {
      return ( ClientOrderCBVO ) select( "getClientOrderCBVOByOrderCbId", clientOrderCBId );
   }

   @Override
   public int updateClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return update( "updateClientOrderCB", clientOrderCBVO );
   }

   @Override
   public int insertClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return insert( "insertClientOrderCB", clientOrderCBVO );
   }

   @Override
   public int deleteClientOrderCB( final ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return delete( "deleteClientOrderCB", clientOrderCBVO );
   }

   @Override
   public List< Object > getClientOrderCBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderCBVOsByClientOrderHeaderId", clientOrderHeaderId );
   }

   @Override
   public List< Object > getClientOrderCBVOsByEmployeeContractId( final String employeeContractId ) throws KANException
   {
      return selectList( "getClientOrderCBVOsByEmployeeContractId", employeeContractId );
   }

}
