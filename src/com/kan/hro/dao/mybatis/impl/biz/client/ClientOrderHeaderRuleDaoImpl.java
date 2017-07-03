package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;

public class ClientOrderHeaderRuleDaoImpl extends Context implements ClientOrderHeaderRuleDao
{

   @Override
   public int countClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderHeaderRuleVOsByCondition", clientOrderHeaderRuleVO );
   }

   @Override
   public List< Object > getClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return selectList( "getClientOrderHeaderRuleVOsByCondition", clientOrderHeaderRuleVO );
   }

   @Override
   public List< Object > getClientOrderHeaderRuleVOsByCondition( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderHeaderRuleVOsByCondition", clientOrderHeaderRuleVO, rowBounds );
   }

   @Override
   public ClientOrderHeaderRuleVO getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( final String clientOrderHeaderRuleId ) throws KANException
   {
      return ( ClientOrderHeaderRuleVO ) select( "getClientOrderHeaderRuleVOByOrderHeaderRuleId", clientOrderHeaderRuleId );
   }

   @Override
   public int updateClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return update( "updateClientOrderHeaderRule", clientOrderHeaderRuleVO );
   }

   @Override
   public int insertClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return insert( "insertClientOrderHeaderRule", clientOrderHeaderRuleVO );
   }

   @Override
   public int deleteClientOrderHeaderRule( final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO ) throws KANException
   {
      return delete( "deleteClientOrderHeaderRule", clientOrderHeaderRuleVO );
   }

   @Override
   public List< Object > getClientOrderHeaderRuleVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return selectList( "getClientOrderHeaderRuleVOsByClientOrderHeaderId", clientOrderHeaderId );
   }

}
