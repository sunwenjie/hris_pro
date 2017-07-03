package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientGroupDao;
import com.kan.hro.domain.biz.client.ClientGroupVO;

public class ClientGroupDaoImpl extends Context implements ClientGroupDao
{

   @Override
   public int countClientGroupVOsByCondition( ClientGroupVO clientGroupVO ) throws KANException
   {
      return ( Integer ) select( "countClientGroupVOsByCondition", clientGroupVO );
   }

   @Override
   public List< Object > getClientGroupVOsByCondition( ClientGroupVO clientGroupVO ) throws KANException
   {
      return selectList( "getClientGroupVOsByCondition", clientGroupVO );
   }

   @Override
   public List< Object > getClientGroupVOsByCondition( ClientGroupVO clientGroupVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientGroupVOsByCondition", clientGroupVO, rowBounds );
   }

   @Override
   public ClientGroupVO getClientGroupVOByClientGroupId( String clientGroupId ) throws KANException
   {
      return ( ClientGroupVO ) select( "getClientGroupVOByClientGroupId", clientGroupId );
   }

   @Override
   public int updateClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      return update( "updateClientGroup", clientGroupVO );
   }

   @Override
   public int insertClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      return insert( "insertClientGroup", clientGroupVO );
   }

   @Override
   public int deleteClientGroup( ClientGroupVO clientGroupVO ) throws KANException
   {
      return delete( "deleteClientGroup", clientGroupVO );
   }

   @Override
   public List< Object > getClientGroupBaseViews( String accountId ) throws KANException
   {
      return selectList( "getClientGroupBaseViews", accountId );
   }

   @Override
   public Object getClientBaseViewByClientGroupVO ( ClientGroupVO clientGroupVO ) throws KANException
   {
      return select("getClientGroupBaseViewByCondition", clientGroupVO );
   }

}
