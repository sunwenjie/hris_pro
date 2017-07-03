package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.util.KANException;

public class PositionStaffRelationDaoImpl extends Context implements PositionStaffRelationDao
{

   @Override
   public int countPositionStaffRelationVOsByCondition( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return ( Integer ) select( "countPositionStaffRelationVOsByCondition", positionStaffRelationVO );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByCondition( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return selectList( "getPositionStaffRelationVOsByCondition", positionStaffRelationVO );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByCondition( PositionStaffRelationVO positionStaffRelationVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionStaffRelationVOsByCondition", positionStaffRelationVO, rowBounds );
   }

   @Override
   public PositionStaffRelationVO getPositionStaffRelationVOByPositionStaffRelationId( String positionStaffRelationId ) throws KANException
   {
      return ( PositionStaffRelationVO ) select( "getPositionStaffRelationVOByRelationId", positionStaffRelationId );
   }

   @Override
   public int updatePositionStaffRelation( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return update( "updatePositionStaffRelation", positionStaffRelationVO );
   }

   @Override
   public int insertPositionStaffRelation( PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return insert( "insertPositionStaffRelation", positionStaffRelationVO );
   }

   @Override
   public int deletePositionStaffRelationByStaffId( final String staffId ) throws KANException
   {
      return delete( "deletePositionStaffRelationByStaffId", staffId );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getPositionStaffRelationVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByStaffId( String staffId ) throws KANException
   {
      return selectList( "getPositionStaffRelationVOsByStaffId", staffId );
   }

   @Override
   public List< Object > getPositionStaffRelationVOsByPositionId( String positionId ) throws KANException
   {
      return selectList( "getPositionStaffRelationVOsByPositionId", positionId );
   }

   @Override
   public int deletePositionStaffRelationByPositionId( String positionId ) throws KANException
   {
      return delete( "deletePositionStaffRelationByPositionId", positionId );
   }

   @Override
   public void deletePositionStaffRelationByStaffIdAndPostionId( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      delete( "deletePositionStaffRelationByStaffIdAndPostionId", positionStaffRelationVO );
   }
   
   @Override
   public PositionStaffRelationVO getPositionStaffRelationVOByStaffAndPositionId( final PositionStaffRelationVO positionStaffRelationVO ) throws KANException
   {
      return ( PositionStaffRelationVO ) select( "getPositionStaffRelationVOByStaffAndPositionId", positionStaffRelationVO );
   }
}
