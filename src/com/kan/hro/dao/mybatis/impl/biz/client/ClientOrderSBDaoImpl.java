package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderSBDao;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;

public class ClientOrderSBDaoImpl extends Context implements ClientOrderSBDao
{

   @Override
   public int countClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderSBVOsByCondition", clientOrderSBVO );
   }

   @Override
   public List< Object > getClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return selectList( "getClientOrderSBVOsByCondition", clientOrderSBVO );
   }

   @Override
   public List< Object > getClientOrderSBVOsByCondition( final ClientOrderSBVO clientOrderSBVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderSBVOsByCondition", clientOrderSBVO, rowBounds );
   }

   @Override
   public ClientOrderSBVO getClientOrderSBVOByClientOrderSBId( final String clientOrderSBId ) throws KANException
   {
      return ( ClientOrderSBVO ) select( "getClientOrderSBVOByOrderSbId", clientOrderSBId );
   }

   @Override
   public int updateClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return update( "updateClientOrderSB", clientOrderSBVO );
   }

   @Override
   public int insertClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return insert( "insertClientOrderSB", clientOrderSBVO );
   }

   @Override
   public int deleteClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return delete( "deleteClientOrderSB", clientOrderSBVO );
   }

   @Override
   public List< Object > getClientOrderSBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderSBVOsByClientOrderHeaderId", clientOrderHeaderId );
   }

   @Override
   public List< Object > getClientOrderSBVOsByEmployeeContractId( final String employeeContractId ) throws KANException
   {
      return selectList( "getClientOrderSBVOsByEmployeeContractId", employeeContractId );
   }

}
