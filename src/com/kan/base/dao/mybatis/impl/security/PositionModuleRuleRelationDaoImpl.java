package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionModuleRuleRelationDao;
import com.kan.base.domain.security.PositionModuleRuleRelationVO;
import com.kan.base.util.KANException;

public class PositionModuleRuleRelationDaoImpl extends Context implements PositionModuleRuleRelationDao
{

   @Override
   public int countPositionModuleRuleRelationVOsByCondition( PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionModuleRuleRelationVOsByCondition", positionModuleRuleRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRuleRelationVOsByCondition( PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return selectList( "getPositionModuleRuleRelationVOsByCondition", positionModuleRuleRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRuleRelationVOsByCondition( PositionModuleRuleRelationVO positionModuleRuleRelationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionModuleRuleRelationVOsByCondition", positionModuleRuleRelationVO, rowBounds );
   }

   @Override
   public PositionModuleRuleRelationVO getPositionModuleRuleRelationVOByPositionModuleRuleRelationId( String positionStaffRelationId ) throws KANException
   {
      return ( PositionModuleRuleRelationVO ) select( "getPositionModuleRuleRelationVOByPositionModuleRuleRelationId", positionStaffRelationId );
   }

   @Override
   public int updatePositionModuleRuleRelation( PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return update( "updatePositionModuleRuleRelation", positionModuleRuleRelationVO );
   }

   @Override
   public int insertPositionModuleRuleRelation( PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return insert( "insertPositionModuleRuleRelation", positionModuleRuleRelationVO );
   }

   @Override
   public int deletePositionModuleRuleRelationByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException
   {
      return delete( "deletePositionModuleRuleRelationByCondition", positionModuleRuleRelationVO );
   }

   @Override
   public List< Object > getPositionModuleRuleRelationVOsByPositionId( String positionId ) throws KANException
   {
      return selectList( "getPositionModuleRuleRelationVOsByPositionId", positionId );
   }

}
