package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderDetailSBRuleDao;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;

public class ClientOrderDetailSBRuleDaoImpl extends Context implements ClientOrderDetailSBRuleDao
{

   @Override
   public int countClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return ( Integer ) select( "countClientOrderDetailSBRuleVOsByCondition", clientOrderDetailSBRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return selectList( "getClientOrderDetailSBRuleVOsByCondition", clientOrderDetailSBRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailSBRuleVOsByCondition( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientOrderDetailSBRuleVOsByCondition", clientOrderDetailSBRuleVO, rowBounds );
   }

   @Override
   public ClientOrderDetailSBRuleVO getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( final String clientOrderDetailSBRuleId ) throws KANException
   {
      return ( ClientOrderDetailSBRuleVO ) select( "getClientOrderDetailRuleVOBysbRuleId", clientOrderDetailSBRuleId );
   }

   @Override
   public int updateClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return update( "updateClientOrderDetailSBRule", clientOrderDetailSBRuleVO );
   }

   @Override
   public int insertClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return insert( "insertClientOrderDetailSBRule", clientOrderDetailSBRuleVO );
   }

   @Override
   public int deleteClientOrderDetailSBRule( final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO ) throws KANException
   {
      return delete( "deleteClientOrderDetailSBRule", clientOrderDetailSBRuleVO );
   }

   @Override
   public List< Object > getClientOrderDetailSBRuleVOsByClientOrderDetailId( final String clientOrderDetailId ) throws KANException
   {
      return selectList( "getClientOrderDetailSBRuleVOsByClientOrderDetailId", clientOrderDetailId );
   }

}
