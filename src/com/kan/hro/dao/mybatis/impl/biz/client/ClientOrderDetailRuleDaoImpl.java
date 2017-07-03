package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;

public class ClientOrderDetailRuleDaoImpl extends Context implements ClientOrderDetailRuleDao
{

   @Override
   public int countClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderDetailRuleVOsByCondition", clientOrderDetailRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return selectList( "getClientOrderDetailRuleVOsByCondition", clientOrderDetailRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailRuleVOsByCondition( final ClientOrderDetailRuleVO clientOrderDetailRuleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderDetailRuleVOsByCondition", clientOrderDetailRuleVO, rowBounds );
   }

   @Override
   public ClientOrderDetailRuleVO getClientOrderDetailRuleVOByClientOrderDetailRuleId( final String clientOrderDetailRuleId ) throws KANException
   {
      return ( ClientOrderDetailRuleVO ) select( "getClientOrderDetailRuleVOByOrderDetailRuleId", clientOrderDetailRuleId );
   }

   @Override
   public int updateClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return update( "updateClientOrderDetailRule", clientOrderDetailRuleVO );
   }

   @Override
   public int insertClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return insert( "insertClientOrderDetailRule", clientOrderDetailRuleVO );
   }

   @Override
   public int deleteClientOrderDetailRule( final ClientOrderDetailRuleVO clientOrderDetailRuleVO ) throws KANException
   {
      return delete( "deleteClientOrderDetailRule", clientOrderDetailRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException
   {
      return selectList( "getClientOrderDetailRuleVOsByClientOrderDetailId", clientOrderDetailId );
   }

}
