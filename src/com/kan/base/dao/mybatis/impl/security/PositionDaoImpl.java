package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.KANException;

public class PositionDaoImpl extends Context implements PositionDao
{

   @Override
   public int countPositionVOsByCondition( final PositionVO positionVO ) throws KANException
   {
      return ( Integer ) select( "countPositionVOsByCondition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByCondition( final PositionVO positionVO ) throws KANException
   {
      return selectList( "getPositionVOsByCondition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByCondition( final PositionVO positionVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionVOsByCondition", positionVO, rowBounds );
   }

   @Override
   public PositionVO getPositionVOByPositionId( final String positionId ) throws KANException
   {
      return ( PositionVO ) select( "getPositionVOByPositionId", positionId );
   }

   @Override
   public int updatePosition( final PositionVO positionVO ) throws KANException
   {
      return update( "updatePosition", positionVO );
   }

   @Override
   public int insertPosition( final PositionVO positionVO ) throws KANException
   {
      return insert( "insertPosition", positionVO );
   }

   @Override
   public int deletePosition( final PositionVO positionVO ) throws KANException
   {
      return delete( "deletePosition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getPositionVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getPositionBaseViewsByAccountId", accountId );
   }

   @Override
   public List< Object > getPositionVOsByParentPositionId( final PositionVO positionVO ) throws KANException
   {
      return selectList( "getPositionVOsByParentPositionId", positionVO );
   }

   @Override
   public List< Object > getPositionVOByPositionVO( final PositionVO positionVO ) throws KANException
   {
      return selectList( "getPositionVOsByPositionVO", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByStaffIds( PositionVO positionVO ) throws KANException
   {
      return selectList( "getPositionVOsByStaffIds", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByEmployeeId( StaffVO staffVO ) throws KANException
   {
      return selectList( "getPositionVOsByEmployeeId", staffVO );
   }

   @Override
   public int getPositionVOBalancesByPositionVO( PositionVO positionVO ) throws KANException
   {
      return ( Integer ) select( "getPositionVOBalancesByPositionVO", positionVO );
   }

}
