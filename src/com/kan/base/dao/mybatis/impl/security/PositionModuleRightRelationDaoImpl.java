package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionModuleRightRelationDao;
import com.kan.base.domain.security.PositionModuleRightRelationVO;
import com.kan.base.util.KANException;

public class PositionModuleRightRelationDaoImpl extends Context implements PositionModuleRightRelationDao
{

   @Override
   public int countPositionModuleRightRelationVOsByCondition( PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionModuleRightRelationVOsByCondition", positionModuleRightRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRightRelationVOsByCondition( PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return selectList( "getPositionModuleRightRelationVOsByCondition", positionModuleRightRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRightRelationVOsByCondition( PositionModuleRightRelationVO positionModuleRightRelationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionModuleRightRelationVOsByCondition", positionModuleRightRelationVO, rowBounds );
   }

   @Override
   public PositionModuleRightRelationVO getPositionModuleRightRelationVOByPositionModuleRightRelationId( String positionGroupRelationId ) throws KANException
   {
      return ( PositionModuleRightRelationVO ) select( "getPositionModuleRightRelationVOByPositionModuleRightRelationId", positionGroupRelationId );
   }

   @Override
   public int updatePositionModuleRightRelation( PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return update( "updatePositionModuleRightRelation", positionModuleRightRelationVO );
   }

   @Override
   public int insertPositionModuleRightRelation( PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return insert( "insertPositionModuleRightRelation", positionModuleRightRelationVO );
   }

   @Override
   public int deletePositionModuleRightRelationByCondition( final PositionModuleRightRelationVO positionModuleRightRelationVO ) throws KANException
   {
      return delete( "deletePositionModuleRightRelationByCondition", positionModuleRightRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRightRelationVOsByPositionId( String positionId ) throws KANException
   {
      return selectList( "getPositionModuleRightRelationVOsByPositionId", positionId );
   }

}
