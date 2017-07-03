package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.PositionGradeDao;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.util.KANException;

public class PositionGradeDaoImpl extends Context implements PositionGradeDao
{

   @Override
   public List< Object > getPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return selectList( "getPositionGradeVOsByCondition", positionGradeVO );
   }

   @Override
   public List< Object > getPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getPositionGradeVOsByCondition", positionGradeVO, rowBounds );
   }

   @Override
   public PositionGradeVO getPositionGradeVOByPositionGradeId( final String positionGardeId ) throws KANException
   {
      return ( PositionGradeVO ) select( "getPositionGradeVOByPositionGradeId", positionGardeId );
   }

   @Override
   public int insertPositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return insert( "insertPositionGrade", positionGradeVO );
   }

   @Override
   public int deletePositionGrade( final String positionGardeId ) throws KANException
   {

      return delete( "deletePositionGrade", positionGardeId );
   }

   @Override
   public int countPositionGradeVOsByCondition( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return ( Integer ) select( "countPositionGradeVOsByCondition", positionGradeVO );
   }

   @Override
   public int updatePositionGrade( final PositionGradeVO positionGradeVO ) throws KANException
   {
      return update( "updatePositionGrade", positionGradeVO );
   }

   @Override
   public List< Object > getPositionGradeVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getPositionGradeVOsByAccountId", accountId );
   }

}
