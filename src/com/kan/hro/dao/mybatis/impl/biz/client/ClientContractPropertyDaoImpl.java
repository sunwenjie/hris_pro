package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientContractPropertyDao;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;

public class ClientContractPropertyDaoImpl extends Context implements ClientContractPropertyDao
{

   @Override
   public int countClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return ( Integer ) select( "countClientContractPropertyVOsByCondition", clientContractPropertyVO );
   }

   @Override
   public List< Object > getClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return selectList( "getClientContractPropertyVOsByCondition", clientContractPropertyVO );
   }

   @Override
   public List< Object > getClientContractPropertyVOsByCondition( final ClientContractPropertyVO clientContractPropertyVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientContractPropertyVOsByCondition", clientContractPropertyVO, rowBounds );
   }

   @Override
   public ClientContractPropertyVO getClientContractPropertyVOByClientContractPropertyId( final String clientContractPropertyId ) throws KANException
   {
      return ( ClientContractPropertyVO ) select( "getClientContractPropertyVOByContractPropertyId", clientContractPropertyId );
   }

   @Override
   public int updateClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return update( "updateClientContractProperty", clientContractPropertyVO );
   }

   @Override
   public int insertClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return insert( "insertClientContractProperty", clientContractPropertyVO );
   }

   @Override
   public int deleteClientContractProperty( final String clientContractPropertyId ) throws KANException
   {
      return delete( "deleteClientContractProperty", clientContractPropertyId );
   }

   @Override
   public List< Object > getClientContractPropertyVOsByContractId( final String contractId ) throws KANException
   {
      return selectList( "getClientContractPropertyVOsByContractId", contractId );
   }

}
