package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.EntityDao;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.util.KANException;

public class EntityDaoImpl extends Context implements EntityDao
{

   @Override
   public int countEntityVOsByCondition( final EntityVO entityVO ) throws KANException
   {
      return ( Integer ) select( "countEntityVOsByCondition", entityVO );
   }

   @Override
   public List< Object > getEntityVOsByCondition( final EntityVO entityVO ) throws KANException
   {
      return selectList( "getEntityVOsByCondition", entityVO );
   }

   @Override
   public List< Object > getEntityVOsByCondition( final EntityVO entityVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEntityVOsByCondition", entityVO, rowBounds );
   }

   @Override
   public EntityVO getEntityVOByEntityId( final String entityId ) throws KANException
   {
      return ( EntityVO ) select( "getEntityVOByEntityId", entityId );
   }

   @Override
   public int insertEntity( final EntityVO entityVO ) throws KANException
   {
      return insert( "insertEntity", entityVO );
   }

   @Override
   public int updateEntity( final EntityVO entityVO ) throws KANException
   {
      return update( "updateEntity", entityVO );
   }

   @Override
   public int deleteEntity( final EntityVO entityVO ) throws KANException
   {
      return delete( "deleteEntity", entityVO );
   }

   @Override
   public List< Object > getEntityVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getEntityVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getEntityBaseViews( final String accountId ) throws KANException
   {
      return selectList( "getEntityBaseViews", accountId );
   }

}
