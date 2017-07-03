package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.util.KANException;

public class PositionDaoImpl extends Context implements PositionDao
{

   @Override
   public int countPositionVOsByCondition( PositionVO positionVO ) throws KANException
   {
      return ( Integer ) select( "countMgtPositionVOsByCondition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByCondition( PositionVO positionVO ) throws KANException
   {
      return selectList( "getMgtPositionVOsByCondition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByCondition( PositionVO positionVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getMgtPositionVOsByCondition", positionVO, rowBounds );
   }

   @Override
   public PositionVO getPositionVOByPositionId( String positionId ) throws KANException
   {
      return ( PositionVO ) select( "getMgtPositionVOByPositionId", positionId );
   }

   @Override
   public int updatePosition( PositionVO positionVO ) throws KANException
   {
      return update( "updateMgtPosition", positionVO );
   }

   @Override
   public int insertPosition( PositionVO positionVO ) throws KANException
   {
      return insert( "insertMgtPosition", positionVO );
   }

   @Override
   public int deletePositionVO( PositionVO positionVO ) throws KANException
   {
      return delete( "deleteMgtPosition", positionVO );
   }

   @Override
   public List< Object > getPositionVOsByParentPositionId( final PositionVO positionVO ) throws KANException
   {
      return selectList( "getMgtPositionVOsByParentPositionId", positionVO );
   }

   @Override
   public List< Object > getPositionBaseViewsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getMgtPositionBaseViewsByAccountId", accountId );
   }

}
