package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientContractDao;
import com.kan.hro.domain.biz.client.ClientContractVO;

public class ClientContractDaoImpl extends Context implements ClientContractDao
{

   @Override
   public int countClientContractVOsByCondition( final ClientContractVO clientContractVO ) throws KANException
   {
      return ( Integer ) select( "countClientContractVOsByCondition", clientContractVO );
   }

   @Override
   public List< Object > getClientContractVOsByCondition( final ClientContractVO clientContractVO ) throws KANException
   {
      return selectList( "getClientContractVOsByCondition", clientContractVO );
   }

   @Override
   public List< Object > getClientContractVOsByCondition( final ClientContractVO clientContractVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientContractVOsByCondition", clientContractVO, rowBounds );
   }

   @Override
   public ClientContractVO getClientContractVOByContractId( final String contractId ) throws KANException
   {
      return ( ClientContractVO ) select( "getClientContractVOByContractId", contractId );
   }

   @Override
   public int updateClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      return update( "updateClientContract", clientContractVO );
   }

   @Override
   public int insertClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      return insert( "insertClientContract", clientContractVO );
   }

   @Override
   public int deleteClientContract( final ClientContractVO clientContractVO ) throws KANException
   {
      return delete( "deleteClientContract", clientContractVO );
   }

   @Override
   public List< Object > getClientContractBaseViews( final String accountId ) throws KANException
   {
      return selectList( "getClientContractBaseViews", accountId );
   }

   @Override
   public List< Object > getClientContractVOsByClientId( final String clientId ) throws KANException
   {
      return selectList( "getClientContractVOsByClientId", clientId );
   }

   @Override
   public int getArchiveClientContractCount( final String clientId )
   {
      return ( Integer ) select( "getArchiveClientContractCount", clientId );
   }
}
