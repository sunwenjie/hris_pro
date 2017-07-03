package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.RightDao;
import com.kan.base.domain.system.RightVO;
import com.kan.base.util.KANException;

public class RightDaoImpl extends Context implements RightDao
{

   @Override
   public int countRightVOsByCondition( RightVO rightVO ) throws KANException
   {
      return ( Integer ) select( "countRightVOsByCondition", rightVO );
   }

   @Override
   public List< Object > getRightVOsByCondition( RightVO rightVO ) throws KANException
   {
      return selectList( "getRightVOsByCondition", rightVO );
   }

   @Override
   public List< Object > getRightVOsByCondition( RightVO rightVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getRightVOsByCondition", rightVO, rowBounds );
   }

   @Override
   public RightVO getRightVOByRightId( String rightId ) throws KANException
   {
      return ( RightVO ) select( "getRightVOByRightId", rightId );
   }

   @Override
   public int updateRight( RightVO rightVO ) throws KANException
   {
      return insert( "updateRight", rightVO );
   }

   @Override
   public int insertRight( RightVO rightVO ) throws KANException
   {
      return insert( "insertRight", rightVO );
   }

   @Override
   public int deleteRight( RightVO rightVO ) throws KANException
   {
      return delete( "deleteRight", rightVO );
   }

   @Override
   public List< Object > getRightVOs() throws KANException
   {
      return selectList( "getRightVOs", null );
   }

}