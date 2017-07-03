package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionColumnRightRelationDao;
import com.kan.base.domain.security.PositionColumnRightRelationVO;
import com.kan.base.util.KANException;

public class PositionColumnRightRelationDaoImpl extends Context implements PositionColumnRightRelationDao
{

   @Override
   public int countPositionColumnRightRelationVOsByCondition( PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionColumnRightRelationVOsByCondition", positionColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionColumnRightRelationVOsByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException
   {
      return selectList( "getPositionColumnRightRelationVOsByCondition", positionColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionColumnRightRelationVOsByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO, final RowBounds rowBounds )
         throws KANException
   {
      return selectList( "getPositionColumnRightRelationVOsByCondition", positionColumnRightRelationVO, rowBounds );
   }

   @Override
   public PositionColumnRightRelationVO getPositionColumnRightRelationVOByPositionColumnRightRelationId( final String positionStaffRelationId ) throws KANException
   {
      return ( PositionColumnRightRelationVO ) select( "getPositionColumnRightRelationVOByPositionColumnRightRelationId", positionStaffRelationId );
   }

   @Override
   public int updatePositionColumnRightRelation( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException
   {
      return update( "updatePositionColumnRightRelation", positionColumnRightRelationVO );
   }

   @Override
   public int insertPositionColumnRightRelation( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException
   {
      return insert( "insertPositionColumnRightRelation", positionColumnRightRelationVO );
   }

   @Override
   public int deletePositionColumnRightRelationByCondition( final PositionColumnRightRelationVO positionColumnRightRelationVO ) throws KANException
   {
      return delete( "deletePositionColumnRightRelationByCondition", positionColumnRightRelationVO );
   }

   @Override
   public List< Object > getPositionColumnRightRelationVOsByPositionId( final String positionId ) throws KANException
   {
      return selectList( "getPositionColumnRightRelationVOsByPositionId", positionId );
   }

}
