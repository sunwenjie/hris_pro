package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionGroupRelationDao;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.util.KANException;

public class PositionGroupRelationDaoImpl extends Context implements PositionGroupRelationDao
{

   @Override
   public int countPositionGroupRelationVOsByCondition( PositionGroupRelationVO positionGroupRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionGroupRelationVOsByCondition", positionGroupRelationVO );
   }
   
   @Override
   public int countPositionGroupRelationVOsByGroupId( String groupId ) throws KANException
   {
      return ( Integer ) select( "countPositionGroupRelationVOsByGroupId", groupId );
   }

   @Override
   public List< Object > getPositionGroupRelationVOsByCondition( PositionGroupRelationVO positionGroupRelationVO ) throws KANException
   {
      return selectList( "getPositionGroupRelationVOsByCondition", positionGroupRelationVO );
   }

   @Override
   public List< Object > getPositionGroupRelationVOsByCondition( PositionGroupRelationVO positionGroupRelationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionGroupRelationVOsByCondition", positionGroupRelationVO, rowBounds );
   }

   @Override
   public PositionGroupRelationVO getPositionGroupRelationVOByPositionGroupRelationId( String positionGroupRelationId ) throws KANException
   {
      return ( PositionGroupRelationVO ) select( "getPositionGroupRelationVOByPositionGroupRelationId", positionGroupRelationId );
   }

   @Override
   public int updatePositionGroupRelation( PositionGroupRelationVO positionGroupRelationVO ) throws KANException
   {
      return update( "updatePositionGroupRelation", positionGroupRelationVO );
   }

   @Override
   public int insertPositionGroupRelation( PositionGroupRelationVO positionGroupRelationVO ) throws KANException
   {
      return insert( "insertPositionGroupRelation", positionGroupRelationVO );
   }

   @Override
   public int deletePositionGroupRelationByGroupId( String groupId ) throws KANException
   {
      return delete( "deletePositionGroupRelationByGroupId", groupId );
   }

   @Override
   public List< Object > getPositionGroupRelationVOsByGroupId( String groupId ) throws KANException
   {
      return selectList( "getPositionGroupRelationVOsByGroupId", groupId );
   }

   @Override
   public List< Object > getPositionGroupRelationVOsByPositionId( String positionId ) throws KANException
   {
      return selectList( "getPositionGroupRelationVOsByPositionId", positionId );
   }

   @Override
   public int deletePositionGroupRelationByPositionId( String positionId ) throws KANException
   {
      return delete( "deletePositionGroupRelationByPositionId", positionId );
   }

}
